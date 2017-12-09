package de.svdragster.hexagons.system;

import java.util.Observer;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.entities.EntityManager;
import de.svdragster.hexagons.map.Hexagon;

/**
 * Created by Sven on 08.12.2017.
 */

public abstract class System  implements Observer {


    private EntityManager GlobalEntityContext;


    public abstract  void process( double delta );

    public void subscribe(){
        Hexagons.getInstance().getWorldLogicEngine().getSystemManager().addObserver(this);
    }

    public EntityManager getGlobalEntityContext() {
        return GlobalEntityContext;
    }

    public void setGlobalEntityContext(EntityManager globalEntityContext) {
        GlobalEntityContext = globalEntityContext;
    }
}
