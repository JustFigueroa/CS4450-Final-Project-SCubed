/*****************************************************************************
 *   File: Flashlight.java
 *   Author: Adam Gonzales
 *   Contributors: Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program 
 *   Date last modified: 11/19/2026
 *
 *   Purpose: Allows the user to use a flashlight that brightens an area of the
 *              visible surface
 **************************************************************************** */

package Main.render;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;


public class Flashlight {
    private final FloatBuffer position = BufferUtils.createFloatBuffer(4);
    private final FloatBuffer direction = BufferUtils.createFloatBuffer(4);
    private final FloatBuffer white = BufferUtils.createFloatBuffer(4);

    private boolean enabled;
    private boolean previousKeyDown;

    // Method: Flashlight (Constructor)
    // Purpose: Creates a flashlight object
    public Flashlight() {
        position.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        direction.put(0.0f).put(0.0f).put(-1.0f).put(0.0f).flip();
        white.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        glLight(GL_LIGHT1, GL_DIFFUSE, white);
        glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 35.0f);
        glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 8.0f);
    }

    // Method: update
    // Purpose: Updates the status of the flashlight when the keybind is activated
    public void update() {
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_F);

        if (keyDown && !previousKeyDown) {
            enabled = !enabled;
        }

        previousKeyDown = keyDown;

        if (enabled) {
            glEnable(GL_LIGHT1);
            glLight(GL_LIGHT1, GL_POSITION, position);
            glLight(GL_LIGHT1, GL_SPOT_DIRECTION, direction);
        } else {
            glDisable(GL_LIGHT1);
        }
    }
}
