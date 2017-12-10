package de.svdragster.hexagons.system;

import java.util.Observable;

import de.svdragster.hexagons.components.ComponentMailbox;
import de.svdragster.hexagons.components.ComponentType;
import de.svdragster.hexagons.entities.EntityManager;

/**
 * Created by z003pksw on 10.12.2017.
 */

public class SystemMessageDelivery extends System {


    public SystemMessageDelivery(EntityManager entityManager){
        setGlobalEntityContext(entityManager);
        subscribe();
    }

    @Override
    public void process(double delta) {
        for(int entity : getGlobalEntityContext().getEntityContext().keySet()){
            if(getGlobalEntityContext().hasComponent(entity, ComponentType.MESSAGE)){
                ComponentMailbox box = (ComponentMailbox) getGlobalEntityContext().retrieveComponent(entity,ComponentType.MESSAGE);


                if(box.hasOutGoingMail()){
                   while(!box.Outbox.isEmpty()){
                       ComponentMailbox.Message msg = box.Outbox.poll();

                       if(msg.To() == 0){
                           //Broadcasting to every entity
                       }
                       else if(getGlobalEntityContext().hasComponent(msg.To(),ComponentType.MESSAGE)){
                           ((ComponentMailbox)getGlobalEntityContext()
                                   .retrieveComponent(msg.To(),ComponentType.MESSAGE))
                                   .Inbox.add(msg);

                       }else{ //Recipient has no mailbox yet. So for now I force one on them, muahahahhaha
                           ComponentMailbox mailbox = new ComponentMailbox();
                           mailbox.Inbox.add(msg);

                           getGlobalEntityContext().addComponent(msg.To(),mailbox);
                       }
                   }
                }



            }
        }

    }

    @Override
    public void update(Observable observable, Object o) {

        if(o instanceof ComponentMailbox.Message){
            ComponentMailbox.Message msg = (ComponentMailbox.Message) o;
            if(getGlobalEntityContext().hasComponent(msg.To(), ComponentType.MESSAGE)) {

                ((ComponentMailbox)getGlobalEntityContext()
                        .retrieveComponent(msg.To(),ComponentType.MESSAGE))
                        .Inbox.add(msg);
            }
        }


    }
}
