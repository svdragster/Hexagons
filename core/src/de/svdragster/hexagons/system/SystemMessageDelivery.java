package de.svdragster.hexagons.system;

import java.util.Observable;

import de.svdragster.hexagons.components.ComponentMailbox;
import de.svdragster.hexagons.components.ComponentType;
import de.svdragster.hexagons.entities.EntityManager;

/**
 * Created by Johannes on 10.12.2017.
 */

/**
 * @brief Does deliver Messages from one entity to another entity.
 */
public class SystemMessageDelivery extends System {

    private ComponentMailbox.Message CurrentProcessedMessage = null;

    public SystemMessageDelivery(EntityManager entityManager){
        setGlobalEntityContext(entityManager);
        subscribe();
    }

    private boolean checkAvailability(){
        return getGlobalEntityContext().isEntityAlive(CurrentProcessedMessage.To()) && !(CurrentProcessedMessage.To() < 0);
    }
    private boolean checkDeliverability(ComponentMailbox.Message message){
        return getGlobalEntityContext().hasComponent(message.To(),ComponentType.MESSAGE);
    }

    private boolean isBroadcastingMessage(){
        return  CurrentProcessedMessage.To() == 0;
    }

    private void retrieveMessageFrom(ComponentMailbox componentMailbox){
        CurrentProcessedMessage = componentMailbox.Outbox.poll();
    }
    private void sendMessageToRecipient(ComponentMailbox.Message message){
        ((ComponentMailbox)getGlobalEntityContext()
                .retrieveComponent(message.To(),ComponentType.MESSAGE))
                .Inbox.add(message);
    }

    //TODO: needs improvment for readability
    @Override
    public void process(double delta) {
        //Because ANY entity could receive a message from another entity we check every entity if they
        //have mailboxes available.
        for(int entity : getGlobalEntityContext().getEntityContext().keySet()){
            if(getGlobalEntityContext().hasComponent(entity, ComponentType.MESSAGE)){ //An entity has a mailbox so we are going to retrieve it
                ComponentMailbox box = (ComponentMailbox) getGlobalEntityContext().retrieveComponent(entity,ComponentType.MESSAGE);
                if(box == null)
                    continue;
                //Check if there any messages left to be delivered to another entity
                if(box.hasOutGoingMail()) {
                   while(!box.Outbox.isEmpty()) {
                       this.retrieveMessageFrom(box); //Remove message from the senders queue
                       if(this.isBroadcastingMessage())  //Start check the recipients ID
                       { //if true it means every entity existing to that point in time will receive this message
                           // Broadcasting to every entity
                       }else if(checkAvailability()) { //Check if the recipient does exist, if not the message will be ignored
                           if(this.checkDeliverability(CurrentProcessedMessage)){ //Checks if the recipient has an mailbox
                                this.sendMessageToRecipient(CurrentProcessedMessage);
                           }else{ //Recipient has no mailbox yet. So for now I force one on them, muahahahhaha
                           //TODO(anyone): might be a good idea not to do this, for dev purposes quite useful right now and only now.
                               ComponentMailbox mailbox = new ComponentMailbox();
                               mailbox.Inbox.add(CurrentProcessedMessage);
                               getGlobalEntityContext().addComponent(CurrentProcessedMessage.To(),mailbox);
                           }
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
            if(this.checkDeliverability(msg)) {
                this.sendMessageToRecipient(msg);
            }
        }


    }
}
