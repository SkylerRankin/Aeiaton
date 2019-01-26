package com.aeiaton.ecs.systems;

import java.util.ArrayList;
import java.util.List;

import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.ParallaxLayer;
import com.aeiaton.ecs.components.RawPositionComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends com.aeiaton.ecs.EntitySystem {

    private ECSCore core;
    private Entity player;
    private ArrayList<Entity> above;
    private ArrayList<Entity> below;
    
    public RenderSystem(Entity p, ECSCore core) {
        super(10, RenderComponent.class);
        this.core = core;
        player = p;
        above = new ArrayList<>();
        below = new ArrayList<>();
    }

    @Override
    public void update(float d) {
        
    }
    
    public void render(SpriteBatch batch, boolean back) {
        if (back) {
          drawBackgrounds(batch);
          return;
        }
        
        partition();
        
        for (Entity e : below) {
            if (e.hasComponent(MovementComponent.class)) {
                MovementComponent mc = e.get(MovementComponent.class);
                RenderComponent rc = e.get(RenderComponent.class);
                batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
            } else if (e.hasComponent(RawPositionComponent.class)) {
                RawPositionComponent rpc = e.get(RawPositionComponent.class);
                RenderComponent rc = e.get(RenderComponent.class);
                batch.draw(rc.texture_region, rpc.x, rpc.y, rc.width, rc.height);
            }
        }
        
        MovementComponent mc = player.get(MovementComponent.class);
        RenderComponent rc = player.get(RenderComponent.class);
        batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
        
        for (Entity e : above) {
            if (e.hasComponent(MovementComponent.class)) {
                MovementComponent mc1 = e.get(MovementComponent.class);
                RenderComponent rc1 = e.get(RenderComponent.class);
                batch.draw(rc1.texture_region, mc1.body.getPosition().x - (mc1.size.x / 2), mc1.body.getPosition().y - (mc1.size.y / 2), rc1.width, rc1.height);
            } else if (e.hasComponent(RawPositionComponent.class)) {
                RawPositionComponent rpc = e.get(RawPositionComponent.class);
                RenderComponent rc1 = e.get(RenderComponent.class);
                batch.draw(rc1.texture_region, rpc.x, rpc.y, rc1.width, rc1.height);
            }
            
        }
    }
    
    private void drawBackgrounds(SpriteBatch batch) {
        List<Entity> sorted = ((ParallaxSystem) core.getSystem(ParallaxSystem.class)).sorted;
        for (Entity e : sorted) {
            RawPositionComponent rpc = e.get(RawPositionComponent.class);
            RenderComponent rc = e.get(RenderComponent.class);
            batch.draw(rc.texture_region, rpc.x, rpc.y, rc.width, rc.height);
        }
    }
    
    private void partition() {
        above.clear();
        below.clear();
        MovementComponent mc = player.get(MovementComponent.class);
        for (Entity e : entities) {
            if (e.hasComponent(MovementComponent.class)) {
                MovementComponent _mc = e.get(MovementComponent.class);
                if (_mc.body.getPosition().y < mc.body.getPosition().y) {
                    above.add(e);
                } else {
                    below.add(e);
                }
            } else if (e.hasComponent(RawPositionComponent.class)) {
                RawPositionComponent _rpc = e.get(RawPositionComponent.class);
                if (_rpc.above_player == -1) {
                    below.add(e);
                } else if (_rpc.above_player == 1) {
                    above.add(e);
                }
                else if (_rpc.y < mc.body.getPosition().y) {
                    above.add(e);
                } else {
                    below.add(e);
                }
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
