package de.svdragster.hexagons.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.components.ComponentType;
import de.svdragster.hexagons.entities.EntityManager;

/**
 * Created by Sven on 08.12.2017.
 */

public class SystemMovement extends System {

    private List<Integer> idCache;

    public SystemMovement() {
        super.setGlobalEntityContext(Hexagons.getInstance().getWorldLogicEngine().getEntityManager());
        super.subscribe();
        idCache = new ArrayList<Integer>();

        //Caching already existing entities in locally
        for( int id : getGlobalEntityContext().getEntityContext().keySet()){
            if(getGlobalEntityContext().hasComponents(id,ComponentType.POSITION,ComponentType.MOVEMENT))
                idCache.add(id);
        }
    }



    @Override
    public void process(double delta) {

    }

    @Override
    public void update(Observable observable, Object o) {
        if( o instanceof Integer){
            int entity = (Integer)o;
           if( getGlobalEntityContext().hasComponents(entity,ComponentType.POSITION,ComponentType.MOVEMENT))
               idCache.add(entity);
        }
    }
}
