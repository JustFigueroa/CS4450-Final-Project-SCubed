/*****************************************************************************
 *   File: SimplexNoise.java
 *   Author: Adam Gonzales
 *   Contributors: Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program 
 *   Date last modified: 11/5/2026
 *
 *   *Provided by course
 *   Purpose: Generates multi-octave Simplex noise for procedural 2D and 3D terrain
 *            heightmaps.
 *            Calculates the necessary number of octaves from feature size, sets
 *            up frequency and persistence amplitude weightings, and evaluates
 *            composite noise values
 **************************************************************************** */

package Main.render;
import java.util.Random;

public class SimplexNoise {

    // Instance Variables
    SimplexNoise_octave[] octaves;
    double[] frequencys;
    double[] amplitudes;

    int largestFeature;
    double persistence;
    int seed;

    // Public Method: Constructor that determines the required number of octaves,
    //                initializes frequency and amplitude arrays, and instantiates octave
    //                generators using a seeded pseudorandom number generator
    public SimplexNoise(int largestFeature,double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

        octaves=new SimplexNoise_octave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];

        Random rnd=new Random(seed);

        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoise_octave(rnd.nextInt());

            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);
        }
    }

    // Public Method: getNoise
    // Purpose: Computes and returns composite 2D Simplex at coordinates
    //          by summing each octave's noise scaled by frequency and amplitude
    public double getNoise(int x, int y){
        double result=0;

        for(int i=0;i<octaves.length;i++){
          //double frequency = Math.pow(2,i);
          //double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }
        return result;
    }

    // Public Method: getNoise
    // Purpose: Compute and returns composite 3D simplex noise at coordinates
    //          by evaluating each octave at scaled spatial frequencies and weighting
    //          by amplitude
    public double getNoise(int x,int y, int z){
        double result=0;

        for(int i=0;i<octaves.length;i++){
          double frequency = Math.pow(2,i);
          double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }
        
        return result;
    }
} 