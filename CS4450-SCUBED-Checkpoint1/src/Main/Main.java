/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/** ***************************************************************************
 *   File: Main.java
 *   Author: Jose Salas
 *   Contributors: Justin Figueroa, Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program
 *   Date last modified: 10/22/2026
 *
 *   Purpose: Serves as starting point for the application, initializing the
 *            display window and establishing a 3D projection using the
 *            CameraController and SingleCube Java classes.
 **************************************************************************** */
package Main;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

import Main.camera.FPCameraController;

public class Main {
    
    private DisplayMode displayMode;
    private FPCameraController fp;
    
    // Public Method: Final_Program (Constructor)
    // Purpose: Acts as the default constructor that initializes the instance variables
    public Main() {
        fp = new FPCameraController(0.0f, 0.0f, 3.0f);
    }
    
    // Public Method: start
    // Purpose: Directs the application setup by creating the display window,
    //          configuring the OpenGL settings, and starting the camera controller game loop
    public void start(){
        try {
           createWindow();
           initGL();
           fp.gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Public Method: createWindow
    // Purpose: Configures the window properties, creates the OpenGL display window,
    //          and identifies matching 640x480 display mode
    public void createWindow() throws Exception{
        Display.setFullscreen(false);
        
        DisplayMode d[] = Display.getAvailableDisplayModes();
        
        for (DisplayMode d1 : d) {
            if (d1.getWidth() == 640 && d1.getHeight() == 480 && d1.getBitsPerPixel() == 32) {
                displayMode = d1;
            }
        }
        
        if (displayMode == null){
            displayMode = new DisplayMode(640, 480);
        }
        
        Display.setDisplayMode(displayMode);
        
        Display.setTitle("SCubed Final Program");
        Display.create();
    }
    
    // Public Method: initGL
    // Purpose: Sets up a 3D projection matrix using GLU and enables depth testing and client state arrays
    public void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        GLU.gluPerspective(100.0f, (float) displayMode.getWidth()/ (float) displayMode.getHeight(), 0.1f, 300.0f);

        glMatrixMode(GL_MODELVIEW); 
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        
        
    }
  
    // Public Method: main
    // Purpose: Instantiates the main class and starts execution
    public static void main(String[] args) {
        Main program = new Main();
        program.start();
    }
    
}
