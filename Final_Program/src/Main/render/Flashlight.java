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

    public Flashlight() {
        position.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        direction.put(0.0f).put(0.0f).put(-1.0f).put(0.0f).flip();
        white.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        glLight(GL_LIGHT1, GL_DIFFUSE, white);
        glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 35.0f);
        glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 8.0f);
    }

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
