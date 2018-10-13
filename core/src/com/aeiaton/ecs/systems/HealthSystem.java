package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.HealthComponent;
import com.aeiaton.observer.Event;

public class HealthSystem extends EntitySystem {

    public HealthSystem() {
        super(10, HealthComponent.class);
    }
    
    @Override
    public void update(float d) {
    }
    
    @Override
    public void notify(Event e) {
        System.out.println("HealthSystem: someone took some damage");
    }

    @Override
    public short getID() {
        return 0;
    }

}
