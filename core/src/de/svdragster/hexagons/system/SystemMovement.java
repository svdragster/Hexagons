package de.svdragster.hexagons.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.components.ComponentMovement;
import de.svdragster.hexagons.components.ComponentPosition;
import de.svdragster.hexagons.components.ComponentType;

/**
 * Created by Sven on 08.12.2017.
 */

public class SystemMovement extends System {

    private List<Integer> idCache;
    private int width,height;

    public SystemMovement(int width,int height) {
        super.setGlobalEntityContext(Hexagons.getInstance().getWorldLogicEngine().getEntityManager());
        super.subscribe();

        this.width = width;
        this.height = height;
        idCache = new ArrayList<Integer>();

        //Caching already existing entities in locally
        for( int id : getGlobalEntityContext().getEntityContext().keySet()){
            if(getGlobalEntityContext().hasComponents(id,ComponentType.POSITION,ComponentType.MOVEMENT))
                idCache.add(id);
        }
    }

    @Override
    public void process(double delta) {

        for(int entity : idCache){

            ComponentPosition position = (ComponentPosition) getGlobalEntityContext().retrieveComponent(entity,ComponentType.POSITION);
            ComponentMovement movement = (ComponentMovement) getGlobalEntityContext().retrieveComponent(entity,ComponentType.MOVEMENT);

           if(position.X < 0)
               movement.dX *= -1;
           if(position.X > width)
               movement.dX *= -1;
            if(position.Y < 0)
                movement.dY *= -1;
            if(position.Y > height)
                movement.dY *= -1;

            position.X += movement.dX * delta;
            position.Y += movement.dY * delta;
        }

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
