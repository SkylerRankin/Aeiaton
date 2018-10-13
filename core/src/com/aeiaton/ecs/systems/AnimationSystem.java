package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.AnimationComponent;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationSystem extends EntitySystem {

    public AnimationSystem() {
        super(9, AnimationComponent.class, RenderComponent.class);
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            AnimationComponent ac = e.get(AnimationComponent.class);
            //PlayerInputComponent pic = e.get(PlayerInputComponent.class);
            RenderComponent rc = e.get(RenderComponent.class);
            rc.texture_region = ac.getAnimationFrame(d);
        }
    }

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
