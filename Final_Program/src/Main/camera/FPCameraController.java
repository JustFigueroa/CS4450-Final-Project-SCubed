/** ***************************************************************************
 *   File: FPCameraController.java
 *   Author: Jose Salas
 *   Contributors: Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program
 *   Date last modified: 10/22/2026
 *
 *   Purpose: Implements the first-person camera controller for navigating
 *   a 3D scene.
 *
 **************************************************************************** */
package Main.camera;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

import Main.render.SingleCube;
import Main.render.Chunk;

public class FPCameraController {

    // Camera's current and initial position vectors
    private Vector3f position = null;
    private Vector3f IPosition = null;

    private float yaw = 0.0f;   // rotation around the y axis
    private float pitch = 0.0f; // rotation around the x axis
    
    // Constructor for the camera controller
    public FPCameraController(float x, float y, float z) {
        // Initialize the camera's position and initial position vectors
        position = new Vector3f(x, y, z);
        IPosition = new Vector3f(x, y, z);
        IPosition.x = 0f;
        IPosition.y = 15f;
        IPosition.z = 0f;
    }
    
    // Public Method: setCurrentPosition
    // Purpose: sets the current position of the Camera Controller object
    public void setCurrentPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    
    // Public Method: setInitialPosition
    // Purpose: sets the initial position of the Camera Controller object
    public void setInitialPosition(float x, float y, float z) {
        this.IPosition.x = x;
        this.IPosition.y = y;
        this.IPosition.z = z;
    }
    
    // Public Method: getCurrentPosition
    // Purpose: gets the current position of the Camera Controller object
    public Vector3f getCurrentPosition() {
        return this.position;
    }
    
    // Public Method: getInitialPosition
    // Purpose: gets the initial position of the Camera Controller object
    public Vector3f getInitialPosition() {
        return this.IPosition;
    }
    
    // Public Method: setYaw
    // Purpose: sets the yaw to a float value y
    public void setYaw(float y) {
        this.yaw = y;
    }
    
    // Public Method: getYaw
    // Purpose: gets the current yaw value
    public float getYaw() {
        return this.yaw;
    }
    
    // Public Method: setPitch
    // Purpose: sets the pitch to a float value p
    public void setPitch(float p) {
        this.pitch = p;
    }
    
    // Public Method: getPitch
    // Purpose: Gets the current pitch value
    public float getPitch() {
        return this.pitch;
    }

    // Public Method: yaw
    // Purpose: Increment the camera's current yaw rotation
    public void yaw(float amount) {
        yaw += amount;
    }

    // Public Method: pitch
    // Purpose: Increment the camera's current pitch rotation
    public void pitch(float amount) {
        pitch -= amount;
    }

    // Public Method: walkForward
    // Purpose: Move camera foward relative to current yaw rotation
    public void walkFoward(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));

        // Update camera's position based on the offset calculations
        position.x += xOffset;
        position.z -= zOffset;
    }

    // Public Method: walkBackwards
    // Purpose: Move camera backwards relative to current yaw rotation
    public void walkBackwards(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));

        position.x -= xOffset;
        position.z += zOffset;
    }

    // Public Method: strafeLeft
    // Purpose: Strafe the camera to the left relative to current yaw rotation
    public void strafeLeft(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw - 90));

        position.x += xOffset;
        position.z -= zOffset;
    }

    // Public Method: strafeRight
    // Purpose: Strafe the camera to the righ relative to current yaw rotation
    public void strafeRight(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw + 90));

        position.x += xOffset;
        position.z -= zOffset;
    }

    // Public Method: moveUp
    // Purpose: Move camera up by a certain distance
    public void moveUp(float distance) {
        position.y += distance;
    }

    // Public Method: moveDown
    // Purpose: Move camera down by a certain distance
    public void moveDown(float distance) {
        position.y -= distance;
    }

    // Public Method: lookThrough
    // Purpose: Translate and rotate the local modelview matrix to look through the camera plane
    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f); // rotate the pitch around the x axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f); // rotate the yaw around the y axis
        glTranslatef(-position.x, -position.y, -position.z); // translate to the position vector's location
    }

    // Public Method: keyboardControls
    // Purpose: Handles camera's movement based on keyboard input
    public void keyboardControls(float movementSpeed, float dt) {

        float distance = movementSpeed * dt;
        
        // movement controls using w, a, s, d, and arrow keys for traversing the x and z planes
        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            walkFoward(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            walkBackwards(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            strafeLeft(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            strafeRight(distance);
        }
        
        // movement controls for vertical movement along the y plane
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            moveUp(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            moveDown(distance);
        }
    }

    // Public Method: gameLoop
    // Purpose: Main loop handling input, camera movement, and rendering
    public void gameLoop() {
        // Initialize delta values used to calcualte movement values
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;

        long lastTime = Sys.getTime(); // time of the last frame
        long time = 0;

        // Sensitivity and movement speed settings
        float mouseSensitivity = 0.04f;
        float movementSpeed = 3.0f;

        Mouse.setGrabbed(true); // grab and hide mouse cursor

        Chunk chunk = new Chunk(-30,-90,-20);
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            //Calculate delta time for this frame
            time = Sys.getTime();
            dt = (float) (time - lastTime) / Sys.getTimerResolution();
            lastTime = time;

            // Get the change in mouse's x and y position since last call of Mouse.getDX() and Mouse.getDY()
            dx = Mouse.getDX();
            dy = Mouse.getDY();

            // Update the camera's yaw and pitch based on the mouse movement
            yaw(dx * mouseSensitivity);
            pitch(dy * mouseSensitivity);

            keyboardControls(movementSpeed, dt);

            glLoadIdentity(); // Reset the modelview matrix back to the identity
            lookThrough(); // Set position to look through camera before drawing

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            chunk.render(); // Render the single cube in the scene

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

}
