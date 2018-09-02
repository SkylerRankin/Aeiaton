package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MovementSystem extends com.aeiaton.ecs.EntitySystem {

    private World world;
    
    public MovementSystem(World world) {
        super(2, MovementComponent.class);
    }
    
    @Override
    public void addEntity(Entity e) {
        super.addEntity(e);
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            if (e.hasComponent(PlayerInputComponent.class)) {
                PlayerInputComponent pic = e.get(PlayerInputComponent.class);
                MovementComponent mc = e.get(MovementComponent.class);
                if (pic.up) { mc.body.applyLinearImpulse(new Vector2(0, mc.walk_force), mc.body.getWorldCenter(), true); }
                if (pic.down) { mc.body.applyLinearImpulse(new Vector2(0, -mc.walk_force), mc.body.getWorldCenter(), true); }
                if (pic.left) { mc.body.applyLinearImpulse(new Vector2(-mc.walk_force, 0), mc.body.getWorldCenter(), true); }
                if (pic.right) { mc.body.applyLinearImpulse(new Vector2(mc.walk_force, 0), mc.body.getWorldCenter(), true); }
//                if (pic.up) { mc.body.setLinearVelocity(0, mc.velocity.y); }
//                if (pic.down) { mc.body.setLinearVelocity(0, -mc.velocity.y); }
//                if (pic.left) { mc.body.setLinearVelocity(-mc.velocity.x, 0); }
//                if (pic.right) { mc.body.setLinearVelocity(mc.velocity.x, 0); }
            } else {
                
            }
        }
    }

    @Override
    public void notify(Event e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public short getID() {
        return 1;
    }

}
