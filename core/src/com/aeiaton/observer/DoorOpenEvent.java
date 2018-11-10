package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class DoorOpenEvent extends Event {
    
    public int id;
    
    public DoorOpenEvent(int i) {
        id = i;
    }

    @Override
    public short getID() {
        return EntitySystem.DoorSystem;
    }

    @Override
    public String getMessage() {
        return "DoorOpening";
    }

    @Override
    public String getName() {
        return "DoorOpenEvent";
    }

}
