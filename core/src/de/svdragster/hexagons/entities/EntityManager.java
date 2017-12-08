package de.svdragster.hexagons.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.components.Component;
import de.svdragster.hexagons.components.ComponentType;

/**
 * Created by z003p2yd on 08.12.2017.
 */

public class EntityManager {

    private Map<Integer, List<ComponentType>> entities;

    private int currentId = 0;
    private Queue<Integer> freeIds;

    public EntityManager() {
        this.entities = new HashMap<Integer, List<ComponentType>>();
        this.freeIds = new LinkedList<Integer>();
    }

    private int nextId() {
        if (freeIds.size() > 0) {
            return freeIds.poll();
        }
        if (currentId == Integer.MAX_VALUE) {
            return -1;
        }
        currentId++;
        return currentId;
    }

    private void freeId(int id) {
        freeIds.offer(id);
    }

    public int createEntity(){
        return 0;
    }

    public int createEntity(Component... component){

        // create entity here

        for (Component c : component) {
            addComponent(0, c);
        }
        return 0;
    }


    public void addComponent(int entity, Component component){

    }

    public boolean hasComponents(int entityId, ComponentType... types) {
        List<ComponentType> components = this.entities.get(entityId);
        for (ComponentType t : types) {
            if (!components.contains(t)) {
                return false;
            }
        }
        return true;
    }

}
