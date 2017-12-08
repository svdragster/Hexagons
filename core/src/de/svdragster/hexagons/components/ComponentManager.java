package de.svdragster.hexagons.components;

import java.util.List;
import java.util.Map;

/**
 * Created by z003p2yd on 08.12.2017.
 */

public class ComponentManager {

    private Map<ComponentType, List<Component>> componentList;

    public Map<ComponentType, List<Component>> getComponentList() {
        return componentList;
    }
}
