package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class LaserActivatedEvent extends Event {
    
    public int id;
    
    public LaserActivatedEvent (int id) {
        this.id = id;
    }
    
    @Override
    public short getID() {
        return EntitySystem.InteractableItemSystem;
    }

    @Override
    public String getMessage() {
        return "Laser Activated Event: id = " + id;
    }

    @Override
    public String getName() {
        return "LaserActivatedEvent";
    }

}
