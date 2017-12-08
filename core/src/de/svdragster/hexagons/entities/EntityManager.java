package de.svdragster.hexagons.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.components.Component;
import de.svdragster.hexagons.components.ComponentMovement;
import de.svdragster.hexagons.components.ComponentType;

/**
 * Created by z003p2yd on 08.12.2017.
 */

public class EntityManager {

    private Map<Integer, List<Component>> entities;

    private int currentId = 0;
    private Queue<Integer> freeIds;

    public EntityManager() {
        this.entities = new HashMap<Integer, List<Component>>();
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
        return nextId();
    }

    public int createEntity(Component... component){

        int id = createEntity();

        for (Component c : component) {
            addComponent(id, c);
        }
        return id;
    }


    public void addComponent(int entity, Component component){
        Hexagons.getInstance().getComponentManager()
                .getComponentList().get(ComponentType.MOVEMENT).add(new ComponentMovement());

        if (hasComponents(entity, component.getType())) {
            return;
        }

        List<Component> list;
        if (this.entities.containsKey(entity)) {
            list = this.entities.get(entity);
        } else {
            list = new ArrayList<Component>();
        }
        this.entities.put(entity, list);
        list.add(component);
    }

    public boolean hasComponent(int entityId, ComponentType type) {
        List<Component> components = this.entities.get(entityId);

        for(Component c : components)
            if( c.getType() == type)
                return true;
        return false;
    }

    public boolean hasComponents(int entityId, ComponentType... types) {

            for(ComponentType t : types)
                if(!hasComponent(entityId, t))
                    return false;
        return true;
    }

}
