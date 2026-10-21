/*****************************************************************************
 *   file: SingleCube.java
 *   author: 
 *   class: CS 4450 - Computer Graphics
 *
 *   assignment: Final Program 
 *   date last modified:
 *
 *   purpose: 
 *
 **************************************************************************** */

package final_program.render;

import static org.lwjgl.opengl.GL11.*;

public class SingleCube {
    
    public SingleCube(){};
   
    public void render(){
        try{
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 0.5f, 0.5f);
                glVertex3f( 1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f,  1.0f);
                glVertex3f( 1.0f, 1.0f,  1.0f);
            glEnd();
                //bottom
            glBegin(GL_POLYGON);
                glColor3f(0.0f, 1.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f,  1.0f);
                glVertex3f(-1.0f,-1.0f,  1.0f);
                glVertex3f(-1.0f,-1.0f, -1.0f);
                glVertex3f( 1.0f,-1.0f, -1.0f);
            glEnd();    
                // front
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
            glEnd();    
                //back
            glBegin(GL_POLYGON);
                glColor3f(0.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
            glEnd();   
                //left
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 1.0f, 0.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
            glEnd();    
                //right side
            glBegin(GL_POLYGON);
                glColor3f(1.0f, 0.0f, 1.0f);
                glVertex3f(1.0f, 1.0f, -1.0f);
                glVertex3f(1.0f, 1.0f,  1.0f);
                glVertex3f(1.0f,-1.0f,  1.0f);
                glVertex3f(1.0f,-1.0f, -1.0f);
            glEnd();
            // Black outline 
            //top
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
            glEnd();
            
            //bottom
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
            glEnd();
            
            //front
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
            
            glEnd();
            
            //back
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
            glEnd();
            
            //left
            glBegin(GL_LINE_LOOP);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
            glEnd();
            
            //right
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
