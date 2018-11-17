package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class ChangeLevelEvent extends Event {
    
    public String level;
    
    public ChangeLevelEvent(String l) {
        level = l;
    }

    @Override
    public short getID() {
        return EntitySystem.GameStateSystem;
    }

    @Override
    public String getMessage() {
        return "Change Level to "+level;
    }

    @Override
    public String getName() {
        return "ChangeLevelEvent";
    }

}
