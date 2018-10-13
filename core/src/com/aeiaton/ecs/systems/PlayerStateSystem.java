package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.AnimationComponent;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.ecs.components.PlayerStateComponent;
import com.aeiaton.ecs.components.PlayerStateComponent.PlayerDirection;
import com.aeiaton.ecs.components.PlayerStateComponent.PlayerState;
import com.aeiaton.observer.Event;

public class PlayerStateSystem extends EntitySystem {

    public PlayerStateSystem() {
        super(1, PlayerInputComponent.class, PlayerStateComponent.class, AnimationComponent.class);
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            PlayerInputComponent pic = e.get(PlayerInputComponent.class);
            PlayerStateComponent psc = e.get(PlayerStateComponent.class);
            AnimationComponent ac = e.get(AnimationComponent.class);
            
            if (!pic.up && !pic.down && !pic.left && !pic.right) {
                psc.state = PlayerState.Idle;
                switch (psc.direction) {
                case Up:
                    ac.current_animation = 0;
                    break;
                case Down:
                    ac.current_animation = 1;
                    break;
                case Left:
                    ac.current_animation = 2;
                    break;
                case Right:
                    ac.current_animation = 3;
                    break;
                }
            } else if (pic.up) {
                psc.state = PlayerState.Walk;
                psc.direction = PlayerDirection.Up;
                ac.current_animation = 4;
            } else if (pic.down) {
                psc.state = PlayerState.Walk;
                psc.direction = PlayerDirection.Down;
                ac.current_animation = 5;
            } else if (pic.left) {
                psc.state = PlayerState.Walk;
                psc.direction = PlayerDirection.Left;
                ac.current_animation = 6;
            } else if (pic.right) {
                psc.state = PlayerState.Walk;
                psc.direction = PlayerDirection.Right;
                ac.current_animation = 7;
            }
            if (pic.o && psc.state != PlayerStateComponent.PlayerState.Punch) {
                System.out.println("punch");
                psc.state = PlayerState.Punch;
                ac.current_animation = 8;
            }
        }
    } //28 40

    @Override
    public void notify(Event e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public short getID() {
        // TODO Auto-generated method stub
        return 0;
    }

}
