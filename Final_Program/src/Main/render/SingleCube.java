/*****************************************************************************
 *   File: SingleCube.java
 *   Author: Jose Salas
 *   Contributors: Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program 
 *   Date last modified:
 *
 *   Purpose: Uses a provided cube render from course material to implement
 *            the single cube for checkpoint 1.
 *
 **************************************************************************** */

package Main.render;

import static org.lwjgl.opengl.GL11.*;

public class SingleCube {
    
    // Public Method: SingleCube
    // Purpose: Default constructor
    public SingleCube(){};
   
    // Public Method: render
    // Purpose: Renders the 3D cube using code from the CS4450 "3D Object Representation" lecture slides
    public void render(){
        try{
            // Top
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 0.5f, 0.5f);
                glVertex3f( 1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f,  1.0f);
                glVertex3f( 1.0f, 1.0f,  1.0f);
            glEnd();
            // Bottom
            glBegin(GL_POLYGON);
                glColor3f(0.0f, 1.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f,  1.0f);
                glVertex3f(-1.0f,-1.0f,  1.0f);
                glVertex3f(-1.0f,-1.0f, -1.0f);
                glVertex3f( 1.0f,-1.0f, -1.0f);
            glEnd();    
            // Front
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
            glEnd();    
            // Back
            glBegin(GL_POLYGON);
                glColor3f(0.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
            glEnd();   
            // Left
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 1.0f, 0.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
            glEnd();    
           // Right
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 0.0f, 1.0f);
                glVertex3f(1.0f, 1.0f, -1.0f);
                glVertex3f(1.0f, 1.0f,  1.0f);
                glVertex3f(1.0f,-1.0f,  1.0f);
                glVertex3f(1.0f,-1.0f, -1.0f);
            glEnd();
            
            // Black outline 
            // Top
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
            glEnd();
            
            // Bottom
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
            glEnd();
            
            // Front
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
            
            glEnd();
            
            // Back
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
            glEnd();
            
            // Left
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
            glEnd();
            
            // Right
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);    
            glEnd();
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
