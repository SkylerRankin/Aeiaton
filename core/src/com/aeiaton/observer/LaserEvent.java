package com.aeiaton.observer;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.PlayerStateComponent;
import com.aeiaton.ecs.components.PlayerStateComponent.PlayerDirection;
import com.badlogic.gdx.math.Vector2;

public class LaserEvent extends Event {
    
    public Vector2 pos;
    public PlayerDirection dir;
    
    public LaserEvent(Entity e) {
        if (e.hasComponent(MovementComponent.class) && e.hasComponent(PlayerStateComponent.class)) {
            MovementComponent mc = e.get(MovementComponent.class);
            PlayerStateComponent psc = e.get(PlayerStateComponent.class);
            pos = mc.body.getPosition();
            dir = psc.direction;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.CombatSystem;
    }

    @Override
    public String getMessage() {
        return "LaserEvent";
    }

    @Override
    public String getName() {
        return "LaserEvent";
    }

}
