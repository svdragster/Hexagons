package de.svdragster.hexagons.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

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

    public void NotifySystems(Object arg){
        setChanged();
        notifyObservers(arg);
    }


    @Override
    public Iterator<System> iterator() {
        return SystemPool.iterator();
    }


}
