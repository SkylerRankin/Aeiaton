package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class TerminalEvent extends Event {
    
    public int id;
    
    public TerminalEvent(int id) {
        this.id = id;
    }

    @Override
    public short getID() {
        return EntitySystem.UISystem;
    }

    @Override
    public String getMessage() {
        return "Terminal "+id;
    }

    @Override
    public String getName() {
        return "TerminalEvent";
    }

}
