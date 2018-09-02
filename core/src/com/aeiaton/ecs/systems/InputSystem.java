package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputSystem extends com.aeiaton.ecs.EntitySystem {

    public InputSystem() {
        super(1, PlayerInputComponent.class);
    }

    @Override
    public void update(float d) {
        for (Entity e : this.entities) {
            e.get(PlayerInputComponent.class).up = Gdx.input.isKeyPressed(Input.Keys.W);
            e.get(PlayerInputComponent.class).down = Gdx.input.isKeyPressed(Input.Keys.S);
            e.get(PlayerInputComponent.class).left = Gdx.input.isKeyPressed(Input.Keys.A);
            e.get(PlayerInputComponent.class).right = Gdx.input.isKeyPressed(Input.Keys.D);
        }
    }

    @Override
    public void notify(Event e) {
        
    }

    @Override
    public short getID() {
        return 0;
    }
}
