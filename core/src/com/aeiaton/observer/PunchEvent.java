package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;
import com.badlogic.gdx.math.Vector2;

public class PunchEvent extends Event {
    
    public int id;
    public Vector2 force;
    
    public short _id;
    
    public PunchEvent() {
        _id = EntitySystem.UISystem;
    }
    
    public PunchEvent(int i, Vector2 f) {
        id = i;
        force = f;
        _id = EntitySystem.UISystem;
    }

    @Override
    public short getID() {
        return _id;
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
