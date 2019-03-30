package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.FreezeEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputSystem extends com.aeiaton.ecs.EntitySystem {
    
    private boolean listen = true;

    public InputSystem() {
        super(1, PlayerInputComponent.class);
    }

    @Override
    public void update(float d) {
        if (!listen) return;
        for (Entity e : this.entities) {
            e.get(PlayerInputComponent.class).up = Gdx.input.isKeyPressed(Input.Keys.W);
            e.get(PlayerInputComponent.class).down = Gdx.input.isKeyPressed(Input.Keys.S);
            e.get(PlayerInputComponent.class).left = Gdx.input.isKeyPressed(Input.Keys.A);
            e.get(PlayerInputComponent.class).right = Gdx.input.isKeyPressed(Input.Keys.D);
            e.get(PlayerInputComponent.class).i = Gdx.input.isKeyJustPressed(Input.Keys.I);
            e.get(PlayerInputComponent.class).o = Gdx.input.isKeyJustPressed(Input.Keys.O);
            e.get(PlayerInputComponent.class).p = Gdx.input.isKeyJustPressed(Input.Keys.P);
            e.get(PlayerInputComponent.class).enter = Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
            e.get(PlayerInputComponent.class).space = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        }
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "FreezeEvent":
            listen = ((FreezeEvent) e).frozen;
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.InputSystem;
    }
}
