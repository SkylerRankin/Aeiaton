package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class ObjectActivationEvent extends Event {
    
    public int id;
    
    public ObjectActivationEvent(int i) {
        id = i;
    }

    @Override
    public short getID() {
        return EntitySystem.InteractableItemSystem;
    }

    @Override
    public String getMessage() {
        return "Object was activated";
    }

    @Override
    public String getName() {
        return "ObjectActivationEvent";
    }

}
