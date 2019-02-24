package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class MirrorRotateEvent extends Event {
    
    public int id;
    public boolean horizontal;
    public boolean clicked;
    public int index;
    public int direction;
        
    public MirrorRotateEvent(int id, boolean hor) {
        this.id = id;
        horizontal = hor;
        clicked = false;
    }
    
    public MirrorRotateEvent(int id, boolean hor, boolean clicked) {
        this.id = id;
        horizontal = hor;
        this.clicked = clicked;
    }
    
    @Override
    public short getID() {
        return EntitySystem.InteractableItemSystem | EntitySystem.CombatSystem;
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
