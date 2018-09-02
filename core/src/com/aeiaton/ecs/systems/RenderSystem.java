package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends com.aeiaton.ecs.EntitySystem {

    public RenderSystem() {
        super(10, RenderComponent.class);
    }

    @Override
    public void update(float d) {
        
    }
    
    public void render(SpriteBatch batch) {
        for (Entity e : entities) {
            MovementComponent mc = e.get(MovementComponent.class);
            RenderComponent rc = e.get(RenderComponent.class);
            batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
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
