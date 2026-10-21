/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package final_program;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

import final_program.camera.FPCameraController;

public class Final_Program {
    
    private DisplayMode displayMode;
    private FPCameraController fp = new FPCameraController(0.0f, 0.0f, -3.0f);
    
    public void start(){
        try {
           createWindow();
           initGL();
           fp.gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
  
    public static void main(String[] args) {
        Final_Program program = new Final_Program();
        program.start();
    }
    
}
