package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class PunchEvent extends Event {

    @Override
    public short getID() {
        return EntitySystem.UISystem;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String getName() {
        return "Punch Event";
    }

}
