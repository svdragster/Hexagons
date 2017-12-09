package de.svdragster.hexagons.components;

/**
 * Created by Sven on 08.12.2017.
 */

public abstract class Component {
    public void setType(ComponentType type) {
        this.type = type;
    }

    private ComponentType type;

    public ComponentType getType() {
        return type;
    }
}
