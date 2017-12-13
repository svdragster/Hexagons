package de.svdragster.hexagons.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import de.svdragster.hexagons.components.Component;
import de.svdragster.hexagons.components.ComponentManager;
import de.svdragster.hexagons.components.ComponentType;

/**
 * Created by Sven on 08.12.2017.
 */

public class EntityManager {

    private Map<Integer, List<Component>>   entityContext;
    private Map<Integer, List<UUID>>        EntityContext;
    private ComponentManager                ComponentStorage;

    private int currentId = 0;
    private Queue<Integer> freeIds;

    public EntityManager(ComponentManager componentStorage) {
        this.ComponentStorage = componentStorage;
        this.entityContext = new HashMap<Integer, List<Component>>();
        this.freeIds = new LinkedList<Integer>();
    }

    /**
     * @return returns the next free available ID components can associate against it.
     */
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


    /**
     * @param id reclaims the given entity ID.
     */
    private void freeId(int id) {
        freeIds.offer(id);
    }

    /**
     * @return creates entity ID with an empty component context.
     */
    public int createEntity(){
        int id =  nextId();
        entityContext.put(id,new ArrayList<Component>());
        return id;
    }

    /**
     * @return entity ID which has components associated with it
     */
    public int createEntity(Component... component){

        int id = createEntity();

        for (Component c : component) {
            addComponent(id, c);
        }
        return id;
    }

    /**
     * @param entity reclaims the entity ID and frees the components
     */
    public void removeEntity(int entity) {

        if(isEntityAlive(entity))
        {
            List<Component> e = getEntity(entity);
            this.entityContext.remove(entity);

            if(e != null)
                for (Component c : e)
                    ComponentStorage.remove(c);

            freeId(entity);
        }

    }

    /**
     * @param entity
     * @param component
     */
    public void addComponent(int entity, Component component){
        if (hasComponent(entity, component.getType())) {
            return;
        }

        ComponentStorage.emplaceComponent(component);

        List<Component> list; // Create List Reference Holder
        if (this.entityContext.containsKey(entity)) {
            list = this.entityContext.get(entity); //Fill Holder with existing Reference
        } else {
            list = new ArrayList<Component>();
            this.entityContext.put(entity, list); // Create new Reference Holder
        }
        list.add(component);
    }

    /**
     * @param entity
     * @param component
     */
    public void removeComponent(int entity, Component component) {
        if (!hasComponent(entity, component.getType())) {
            return;
        }

        ComponentStorage.remove(component);

        this.entityContext.get(entity).remove(component);
    }

    /**
     * @param entityId
     * @param type
     * @return true if an Entity has a certain component
     */
    public boolean hasComponent(int entityId, ComponentType type) {
        List<Component> components = this.entityContext.get(entityId);

        for(Component c : components)
            if( c.getType() == type)
                return true;
        return false;
    }

    /**
     * @param entityId
     * @param types
     * @return true if an Entity has a certain set of components
     */
    public boolean hasComponents(int entityId, ComponentType... types) {

            for(ComponentType t : types)
                if(!hasComponent(entityId, t))
                    return false;
        return true;
    }

    /**
     * @param entityID
     * @param type
     * @return the instance of a component requested via the type and is associated to the entityID
     */
    public Component retrieveComponent(int entityID, ComponentType type){
        List<Component> entity = entityContext.get(entityID);
        if(entity != null)
            for(Component c : entity)
                if(c != null && c.getType() == type)
                    return c;
        return null;
    }

    /**
     * @param entityID
     * @return
     */
    public boolean isEntityAlive(int entityID){
        for(int id : this.entityContext.keySet())
            if(id==entityID)
                return true;
        return false;
    }

    /**
     * @return context of entities.
     */
    public Map<Integer, List<Component>> getEntityContext() {
        return entityContext;
    }

    /**
     * @param entityID key to the associated components
     * @return returns a list of components which define an entity
     */
    public  List<Component> getEntity(int entityID) { return entityContext.get(entityID);}
}
