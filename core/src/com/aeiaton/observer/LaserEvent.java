package com.aeiaton.observer;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.PlayerStateComponent;
import com.aeiaton.ecs.components.PlayerStateComponent.PlayerDirection;
import com.badlogic.gdx.math.Vector2;

public class LaserEvent extends Event {
    
    public Vector2 pos;
    public int dir;
    
    public LaserEvent(Entity e) {
        if (e.hasComponent(MovementComponent.class) && e.hasComponent(PlayerStateComponent.class)) {
            MovementComponent mc = e.get(MovementComponent.class);
            PlayerStateComponent psc = e.get(PlayerStateComponent.class);
            pos = mc.body.getPosition();
            switch (psc.direction) {
            case Up:
                dir = 0;
                break;
            case Right:
                dir = 1;
                break;
            case Down:
                dir = 2;
                break;
            case Left:
                dir = 3;
                break;
            default:
                dir = -1;
            }
            
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
