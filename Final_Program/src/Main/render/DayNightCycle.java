/*****************************************************************************
 *   File: DayNightCycle.java
 *   Author: Adam Gonzales
 *   Contributors: Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program 
 *   Date last modified: 11/19/2026
 *
 *   Purpose: Allows the program to run a working day and night cycle
 **************************************************************************** */

package Main.render;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class DayNightCycle {
    private static final float CYCLE_SECONDS = 60.0f;

    private final FloatBuffer ambient = BufferUtils.createFloatBuffer(4);
    private final FloatBuffer diffuse = BufferUtils.createFloatBuffer(4);
    private float elapsed;

    // Method: update
    // Purpose: Taking the change of time as a parameter, updates the "time" for the day/night cycle and
    //          changes the sky color depending on said "time"
    public void update(float deltaTime) {
        elapsed = (elapsed + deltaTime) % CYCLE_SECONDS;
        float daylight = (float) ((Math.cos(2.0 * Math.PI * elapsed / CYCLE_SECONDS) + 1.0) / 2.0);

        float ambientLevel = 0.08f + (0.32f * daylight);
        float diffuseLevel = 0.15f + (0.85f * daylight);

        ambient.clear();
        ambient.put(ambientLevel).put(ambientLevel).put(ambientLevel).put(1.0f).flip();
        glLightModel(GL_LIGHT_MODEL_AMBIENT, ambient);
        glLight(GL_LIGHT0, GL_AMBIENT, ambient);

        diffuse.clear();
        diffuse.put(diffuseLevel).put(diffuseLevel).put(diffuseLevel).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_DIFFUSE, diffuse);

        glClearColor(
                0.02f + (0.43f * daylight),
                0.03f + (0.57f * daylight),
                0.10f + (0.80f * daylight),
                1.0f
        );
    }
}
