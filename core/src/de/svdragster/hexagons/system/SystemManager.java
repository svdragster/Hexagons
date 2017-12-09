package de.svdragster.hexagons.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.function.Consumer;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.components.Component;
import de.svdragster.hexagons.components.ComponentType;

/**
 * Created by Sven on 08.12.2017.
 */

public class SystemManager extends Observable implements Iterable<System> {

    private List<System> SystemPool;

    public SystemManager() {
        this.SystemPool = new ArrayList<System>();
    }

    public void addSystem(System... systems){
        for(System s : systems)
            if(!SystemPool.contains(s))
                SystemPool.add(s);
    }
    public void removeSystem(System system) {
            if(!SystemPool.contains(system))
                SystemPool.remove(system);
    }


    @Override
    public Iterator<System> iterator() {
        return SystemPool.iterator();
    }


}
