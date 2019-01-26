package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class FreezeEvent extends Event {
    
    public boolean frozen;
    
    public FreezeEvent(boolean f) {
        frozen = f;
    }

    @Override
    public short getID() {
        return EntitySystem.InputSystem;
    }

    @Override
    public String getMessage() {
        return "Player input frozen: "+frozen;
    }

    @Override
    public String getName() {
        return "FreezeEvent";
    }

}
