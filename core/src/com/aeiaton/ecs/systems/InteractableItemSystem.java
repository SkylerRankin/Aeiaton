package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.InteractableComponent;
import com.aeiaton.ecs.components.MirrorComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.ObjectActivationEvent;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

public class InteractableItemSystem extends EntitySystem {

    private TextureAtlas atlas;
    private World world;
    private Integer next_activation = null;
    private boolean init = false;
    private boolean event = false; 

    public InteractableItemSystem(World world) {
        super(3, InteractableComponent.class);
        this.world = world;
        atlas = new TextureAtlas("sprites//sprites.atlas");
    }

    @Override
    public void update(float d) {
        //if (!init) init();
        //if (next_activation == null) return;

        /*if (next_activation == e.id) {
                RenderComponent rc = e.get(RenderComponent.class);
                InteractableComponent ic = e.get(InteractableComponent.class);
                if (event) {
                    ic.active = !ic.active;
                    event = false;
                }
                rc.texture_region = atlas.findRegion(ic.active ? ic.active_texture : ic.inactive_texture);
                if (!ic.active) next_activation = null;
                return;
            } */
    }


@Override
public void notify(Event e) {
    switch (e.getName()) {
    case "ObjectActivationEvent":
        next_activation = ((ObjectActivationEvent) e).id;
        event = true;
        break;
    }
}

@Override
public short getID() {
    return EntitySystem.InteractableItemSystem;
}

private void init() {
    init = true;
    for (Entity e : entities) {
        RenderComponent rc = e.get(RenderComponent.class);
        InteractableComponent ic = e.get(InteractableComponent.class);
        rc.texture_region = atlas.findRegion(ic.inactive_texture);
    }
}

}
