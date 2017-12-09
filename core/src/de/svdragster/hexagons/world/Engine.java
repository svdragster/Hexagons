package de.svdragster.hexagons.world;

import de.svdragster.hexagons.components.ComponentManager;
import de.svdragster.hexagons.entities.EntityManager;
import de.svdragster.hexagons.system.System;
import de.svdragster.hexagons.system.SystemManager;

/**
 * Created by Sven on 08.12.2017.
 */

public class Engine {

    private ComponentManager componentManager;
    private EntityManager entityManager;
    private SystemManager systemManager;


    public Engine(){
        this.componentManager = new ComponentManager();
        this.entityManager = new EntityManager();
        this.systemManager = new SystemManager();
    }


    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public void run() {
        for(System s : systemManager)
            if(s.isActive())
                s.process( 1);
    }

}
