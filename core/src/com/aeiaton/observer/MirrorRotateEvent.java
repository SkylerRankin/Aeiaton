package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class MirrorRotateEvent extends Event {
    
    public int id;
    public boolean horizontal;
    
    public MirrorRotateEvent(int id, boolean hor) {
        this.id = id;
        horizontal = hor;
    }
    
    @Override
    public short getID() {
        return EntitySystem.InteractableItemSystem;
    }

        @Override
    public String getMessage() {
        return "Mirror Rotate Event: id="+id+" horizontal="+horizontal;
    }

    @Override
    public String getName() {
        return "MirrorRotateEvent";
    }

}
