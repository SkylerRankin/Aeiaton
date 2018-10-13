package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.observer.Event;

public class UISystem extends EntitySystem {

    public UISystem() {
        super(0);
    }

    @Override
    public void notify(Event e) {
        
    }

    @Override
    public short getID() {
        return EntitySystem.UISystem;
    }

    @Override
    public void update(float d) {
        
    }

}
