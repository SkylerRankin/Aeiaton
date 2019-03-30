package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.InteractableComponent;
import com.aeiaton.ecs.components.MirrorComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.MirrorRotateEvent;
import com.aeiaton.observer.ObjectActivationEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

public class InteractableItemSystem extends EntitySystem {

    private TextureAtlas atlas;
    private World world;
    
    private int interacted_id = -1;

    public InteractableItemSystem(World world) {
        super(3, InteractableComponent.class);
        this.world = world;
        atlas = new TextureAtlas("sprites//sprites.atlas");
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            //mirror components
            if (e.hasComponent(MirrorComponent.class)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && interacted_id != -1 && e.id == interacted_id) {
                    rotate_mirror();
                }
            }
        }
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "MirrorRotateEvent":
            MirrorRotateEvent mre = (MirrorRotateEvent) e;
            if (!mre.clicked) interacted_id = mre.id;
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.InteractableItemSystem;
    }
    
    //** Entity Specific Methods **//
    
    private void rotate_mirror() {
        Entity en = core.getEntity(interacted_id);  
        MirrorComponent mc = en.get(MirrorComponent.class);
        RenderComponent rc = en.get(RenderComponent.class);

        if (!mc.last_flip) {
            rc.texture_region.flip(false, true);
        } else {
            rc.texture_region.flip(true, false);
        }
        mc.dir = (mc.dir + 1);
        if (mc.dir > 7) mc.dir = 4;
        
        mc.last_flip = !mc.last_flip;
        
        //send to combat system once actual rotation has occurred
        MirrorRotateEvent mre = new MirrorRotateEvent(interacted_id, mc.last_flip);
        mre.direction = mc.dir;
        mre.index = mc.index;
        mre.clicked = true;
        observer.recieve(mre);
    }

}
