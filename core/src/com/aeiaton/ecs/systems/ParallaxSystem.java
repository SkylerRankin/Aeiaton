package com.aeiaton.ecs.systems;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.ParallaxLayer;
import com.aeiaton.ecs.components.RawPositionComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.math.Vector2;

public class ParallaxSystem extends EntitySystem {
    
    private boolean init = false;
    public List<Entity> sorted;
    private float scale = 0.03f;
    private Entity player;
    private Vector2 prev;
    
    public ParallaxSystem(Entity p) {
        super(12, ParallaxLayer.class, RenderComponent.class);
        player = p;
        prev = new Vector2();
    }

    @Override
    public void notify(Event e) {
        
    }

    @Override
    public short getID() {
        return EntitySystem.ParallaxSystem;
    }

    @Override
    public void update(float d) {
        if (!init) {
            for (Entity e : entities) {
                RenderComponent rc = e.get(RenderComponent.class);
                ParallaxLayer pl = e.get(ParallaxLayer.class);
                rc.texture_region = pl.img;
            }
            sorted = entities.stream().sorted(new ParallaxLayerComparator()).collect(Collectors.toList());
            init = true;
        } else {
            MovementComponent mc = player.get(MovementComponent.class);
            Vector2 pos = new Vector2(mc.body.getPosition().x, mc.body.getPosition().y);
            for (Entity e : sorted) {
                RawPositionComponent rpc = e.get(RawPositionComponent.class);
                ParallaxLayer pl = e.get(ParallaxLayer.class);
                rpc.x += (pos.x - prev.x)*scale*(sorted.size() - pl.z);
            }
            prev = pos;
        }
    }
    
    private class ParallaxLayerComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity a, Entity b) {
            ParallaxLayer pl1 = a.get(ParallaxLayer.class);
            ParallaxLayer pl2 = b.get(ParallaxLayer.class);
            if (pl1 != null && pl2 != null) {
                return pl1.z < pl2.z ? -1 : 1;
            }
            return 0;
        }
        
    }

}
