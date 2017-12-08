package de.svdragster.hexagons.system;

import java.util.ArrayList;
import java.util.List;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.components.ComponentType;

/**
 * Created by z003p2yd on 08.12.2017.
 */

public class SystemMovement extends System {

    private List<Integer> idCache;

    public SystemMovement() {
        idCache = new ArrayList<Integer>();

        for( int id : Hexagons.getInstance()
                .getWorldLogicEngine()
                .getEntityManager()
                .getEntityContext().keySet()){

            if(Hexagons.getInstance()
                    .getWorldLogicEngine()
                    .getEntityManager()
                    .hasComponents(id,ComponentType.POSITION,ComponentType.MOVEMENT))
                idCache.add(id);
        }
    }



    @Override
    public void process(double delta) {



    }
}
