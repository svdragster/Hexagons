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
 * Created by Sven on 08.12.2017.
 */

public class EntityManager {

    private Map<Integer, List<Component>> entityContext;

    private int currentId = 0;
    private Queue<Integer> freeIds;

    public EntityManager() {
        this.entityContext = new HashMap<Integer, List<Component>>();
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
        int id =  nextId();
        entityContext.put(id,new ArrayList<Component>());
        return id;
    }

    public int createEntity(Component... component){

        int id = createEntity();

        for (Component c : component) {
            addComponent(id, c);
        }
        return id;
    }

    public void removeEntity(int entity) {

        for (Component c : this.entityContext.get(entity))
            removeComponent(entity, c);
        this.entityContext.remove(entity);
        freeId(entity);
    }


    public void addComponent(int entity, Component component){
        if (hasComponent(entity, component.getType())) {
            return;
        }

        Hexagons.getInstance().getWorldLogicEngine()
                .getComponentManager()
                .getComponentList()
                .get(component.getType())
                .add(component);

        List<Component> list;
        if (this.entityContext.containsKey(entity)) {
            list = this.entityContext.get(entity);
        } else {
            list = new ArrayList<Component>();
            this.entityContext.put(entity, list);
        }
        list.add(component);
    }

    public void removeComponent(int entity, Component component) {
        if (!hasComponent(entity, component.getType())) {
            return;
        }

        Hexagons.getInstance().getWorldLogicEngine()
                .getComponentManager()
                .getComponentList()
                .get(component.getType())
                .remove(component);

        this.entityContext.get(entity).remove(component);
    }

    public boolean hasComponent(int entityId, ComponentType type) {
        List<Component> components = this.entityContext.get(entityId);

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

    public Component retrieveComponent(int entityID, ComponentType type){
        List<Component> entity = entityContext.get(entityID);
        for(Component c : entity)
            if(c.getType() == type)
                return c;
        return null;
    }

    public Map<Integer, List<Component>> getEntityContext() {
        return entityContext;
    }
}
