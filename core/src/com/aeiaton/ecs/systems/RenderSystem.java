package com.aeiaton.ecs.systems;

import java.util.ArrayList;

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

    private Entity player;
    private ArrayList<Entity> above;
    private ArrayList<Entity> below;
    
    public RenderSystem(Entity p) {
        super(10, RenderComponent.class);
        player = p;
        above = new ArrayList<>();
        below = new ArrayList<>();
    }

    @Override
    public void update(float d) {
        
    }
    
    public void render(SpriteBatch batch) {
        /*
        for (Entity e : entities) {
            MovementComponent mc = e.get(MovementComponent.class);
            RenderComponent rc = e.get(RenderComponent.class);
            batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
        }*/
        partition();
        
        for (Entity e : below) {
            MovementComponent mc = e.get(MovementComponent.class);
            RenderComponent rc = e.get(RenderComponent.class);
            batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
        }
        
        MovementComponent mc = player.get(MovementComponent.class);
        RenderComponent rc = player.get(RenderComponent.class);
        batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
        
        for (Entity e : above) {
            MovementComponent mc1 = e.get(MovementComponent.class);
            RenderComponent rc1 = e.get(RenderComponent.class);
            batch.draw(rc1.texture_region, mc1.body.getPosition().x - (mc1.size.x / 2), mc1.body.getPosition().y - (mc1.size.y / 2), rc1.width, rc1.height);
        }
    }
    
    private void partition() {
        above.clear();
        below.clear();
        MovementComponent mc = player.get(MovementComponent.class);
        for (Entity e : entities) {
            MovementComponent _mc = e.get(MovementComponent.class);
            if (_mc.body.getPosition().y < mc.body.getPosition().y) {
                above.add(e);
            } else {
                below.add(e);
            }
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
