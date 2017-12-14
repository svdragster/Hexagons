package de.svdragster.hexagons;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by z003p2yd on 11.12.2017.
 */

public class InputListener implements InputProcessor {


    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.A) {
            Hexagons.getInstance().getCamera().moveX(-200);
        }
        if(keycode == Input.Keys.D) {
            Hexagons.getInstance().getCamera().moveX(200);
        }
        if(keycode == Input.Keys.W) {
            Hexagons.getInstance().getCamera().moveY(200);
        }
        if(keycode == Input.Keys.S) {
            Hexagons.getInstance().getCamera().moveY(-200);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W
                || keycode == Input.Keys.S) {
            Hexagons.getInstance().getCamera().setMoveY(false);
            return true;
        }
        if(keycode == Input.Keys.A
                || keycode == Input.Keys.D) {
            Hexagons.getInstance().getCamera().setMoveX(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
