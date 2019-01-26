package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.AnimationComponent;
import com.aeiaton.ecs.components.DoorComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.ChangeLevelEvent;
import com.aeiaton.observer.DoorOpenEvent;
import com.aeiaton.observer.Event;

public class DoorSystem extends EntitySystem {
    
    private Entity door;

    public DoorSystem() {
        super(10, DoorComponent.class);
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "DoorOpenEvent":
            DoorOpenEvent doe = (DoorOpenEvent) e;
            Entity d = _entities.get(doe.id);
            door = d;
            AnimationComponent ac = d.get(AnimationComponent.class);
            ac.current_animation = 1;
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.DoorSystem;
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            AnimationComponent ac = e.get(AnimationComponent.class);
            if (ac.current_animation == 1 && ac.isCurrentAnimationFinished(d)) {
                ac.current_animation = 2;
            }
        }
    }

}
