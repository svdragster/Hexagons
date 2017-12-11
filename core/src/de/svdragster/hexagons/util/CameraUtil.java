package de.svdragster.hexagons.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by z003p2yd on 11.12.2017.
 */

public class CameraUtil {

    private Camera camera;
    private float velX = 0;
    private float velY = 0;
    private boolean moving = false;

    public CameraUtil(Camera camera) {
        this.camera = camera;
    }

    public void moveX(float x) {
        velX = x;
        moving = true;
    }

    public void moveY(float y) {
        velY = y;
        moving = true;
    }

    public void update() {
        if (velX == 0 && velY == 0) {
            return;
        }
        camera.translate(velX, velY, 0);
        camera.update();

        if (!moving) {
            velX *= 0.8;
            velY *= 0.8;
            if (velX <= 0.01 && velX >= -0.01) {
                velX = 0;
            }
            if (velY <= 0.01 && velY >= -0.01) {
                velY = 0;
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public Vector3 getMousePosInGameWorld() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
