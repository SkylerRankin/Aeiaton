package com.aeiaton.ecs.systems;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.AnimationComponent;
import com.aeiaton.ecs.components.DoorComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.ChangeLevelEvent;
import com.aeiaton.observer.DoorOpenEvent;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.LaserActivatedEvent;

public class DoorSystem extends EntitySystem {
    
    private Entity door;

    public DoorSystem() {
        super(10, DoorComponent.class);
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "LaserActivatedEvent":
            LaserActivatedEvent lae = (LaserActivatedEvent) e;
            this.core.getEntity(lae.id).get(MovementComponent.class).update_category_bits(Aeiaton.INTERACTABLE_BIT);
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.DoorSystem;
    }

    @Override
    public void update(float d) {}

}
