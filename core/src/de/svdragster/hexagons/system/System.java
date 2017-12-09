package de.svdragster.hexagons.system;

import java.util.Observer;

import de.svdragster.hexagons.Hexagons;
import de.svdragster.hexagons.entities.EntityManager;

/**
 * Created by Sven on 08.12.2017.
 */

/**
 * @brief Abstract base class for any system in the application. A System describes a rule for
 * c      certain kind of entities. Systems do not know about any entities directly they simply
 *        process components which are bundled together via an association. Any bundle of components
 *        are identified via an ID which describes unique entities.
 *
 *        Systems can be enabled and disabled. But the user is responsible for asking if thats the
 *        case.
 *
 *
 */
public abstract class System  implements Observer {


    private EntityManager GlobalEntityContext;
    private boolean isActive=true;

    public boolean isActive() {
        return isActive;
    }

    protected void setActive(boolean active) {
        isActive = active;
    }

    public EntityManager getGlobalEntityContext() {
        return GlobalEntityContext;
    }

    public void setGlobalEntityContext(EntityManager globalEntityContext) {
        GlobalEntityContext = globalEntityContext;
    }


    /**
     * @brief The process-method is called every game tick. Any system is required to implement
     *        this method.
     * @param delta is the time between two ticks the application needs to process everything
    */
    public abstract  void process( double delta );

    /**
     * @brief The subscribe method enables you get notified if something has changed so that
     *        your system can react on it. It is not required to let you subscribe to notifications.
     *        but it is quite useful. E.g. new entities has been created but your system does not
     *        know about themm through the subscription the system learns about it and can decide
     *        what to do about it.
     */
    protected void subscribe(){
        Hexagons.getInstance().getWorldLogicEngine().getSystemManager().addObserver(this);
    }

    /**
     * @brief The unsubscribe method enables you to reverse the, you guessed it, subscribe method.
     */
    protected void unsubscribe(){
        Hexagons.getInstance().getWorldLogicEngine().getSystemManager().deleteObserver(this);
    }

    public void disableSystem(){
        setActive(false);
    }

    public void enableSystem(){
        setActive(true);
    }
}
