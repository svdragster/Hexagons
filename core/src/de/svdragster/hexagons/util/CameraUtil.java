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
    private boolean moveX = false;
    private boolean moveY = false;
    private float scalePosition = 100;

    private double x = 0;
    private double y = 0;

    public CameraUtil(Camera camera) {
        this.camera = camera;
    }

    public void moveX(float x) {
        velX = x;
        moveX = true;
    }

    public void moveY(float y) {
        velY = y;
        moveY = true;
    }

    public void update() {
        if (velX == 0 && velY == 0) {
            return;
        }
        //camera.translate(Math.round(velX*Gdx.graphics.getDeltaTime() * scalePosition) / scalePosition, Math.round(velY*Gdx.graphics.getDeltaTime() * scalePosition) / scalePosition, 0);
        //camera.translate(velX*Gdx.graphics.getDeltaTime(), velY*Gdx.graphics.getDeltaTime(), 0);

        x += velX*Gdx.graphics.getDeltaTime();
        y += velY*Gdx.graphics.getDeltaTime();

        camera.position.x = (int) x;
        camera.position.y = (int) y;

        if (!moveX) {
            velX *= 0.9;
            if (velX <= 0.01 && velX >= -0.01) {
                velX = 0;
            }
        }
        if (!moveY) {
            velY *= 0.9;
            if (velY <= 0.01 && velY >= -0.01) {
                velY = 0;
            }
        }

        camera.update();
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

    public boolean isMoveX() {
        return moveX;
    }

    public void setMoveX(boolean moveX) {
        this.moveX = moveX;
    }

    public boolean isMoveY() {
        return moveY;
    }

    public void setMoveY(boolean moveY) {
        this.moveY = moveY;
    }
}
