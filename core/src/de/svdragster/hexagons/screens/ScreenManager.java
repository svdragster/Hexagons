package de.svdragster.hexagons.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;
import java.util.List;

import de.svdragster.hexagons.Hexagons;

/**
 * Created by Sven on 14.12.2017.
 */

public class ScreenManager {

    private List<HexagonScreen> screenList;

    private HexagonScreen currentScreen;

    public ScreenManager() {
        screenList = new ArrayList<>();
        screenList.add(new ScreenLoading());
        screenList.add(new ScreenMainMenu());
        screenList.add(new ScreenGame());

        this.currentScreen = screenList.get(0); // loading screen
    }

    public HexagonScreen setCurrentScreen(ScreenType type) {
        for (HexagonScreen screen : screenList) {
            if (screen.getScreenType() == type) {
                this.currentScreen = screen;
                Hexagons.getInstance().setScreen(screen);
                return screen;
            }
        }
        // this shouldn't happen
        Gdx.app.error("Hexagon", "Screen null");
        Gdx.app.exit();
        return null;
    }

    public List<HexagonScreen> getScreenList() {
        return screenList;
    }

    public HexagonScreen getCurrentScreen() {
        return currentScreen;
    }
}
