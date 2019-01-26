package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.DirectionalHitboxComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.ecs.components.PlayerStateComponent;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.FreezeEvent;
import com.aeiaton.observer.PunchEvent;
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
                PlayerStateComponent psc = e.get(PlayerStateComponent.class);
                PlayerInputComponent pic = e.get(PlayerInputComponent.class);
                MovementComponent mc = e.get(MovementComponent.class);
                if (psc.state.equals(PlayerStateComponent.PlayerState.Dash)) {
                    mc.body.applyLinearImpulse(new Vector2(0, mc.dash_force), mc.body.getWorldCenter(), true);
                    return;
                }
                if (pic.up) { mc.body.applyLinearImpulse(new Vector2(0, mc.walk_force), mc.body.getWorldCenter(), true); }
                else if (pic.down) { mc.body.applyLinearImpulse(new Vector2(0, -mc.walk_force), mc.body.getWorldCenter(), true); }
                else if (pic.left) { mc.body.applyLinearImpulse(new Vector2(-mc.walk_force, 0), mc.body.getWorldCenter(), true); }
                else if (pic.right) { mc.body.applyLinearImpulse(new Vector2(mc.walk_force, 0), mc.body.getWorldCenter(), true); }
                //if (pic.i) { mc.body.applyLinearImpulse(new Vector2(mc.dash_force, 0), mc.body.getWorldCenter(), true); }
//                if (pic.up) { mc.body.setLinearVelocity(0, mc.velocity.y); }
//                if (pic.down) { mc.body.setLinearVelocity(0, -mc.velocity.y); }
//                if (pic.left) { mc.body.setLinearVelocity(-mc.velocity.x, 0); }
//                if (pic.right) { mc.body.setLinearVelocity(mc.velocity.x, 0); }
            }
            
            if (e.hasComponent(DirectionalHitboxComponent.class)) {
                MovementComponent mc = e.get(MovementComponent.class);
                DirectionalHitboxComponent dhc = e.get(DirectionalHitboxComponent.class);
                PlayerInputComponent pic = e.get(PlayerInputComponent.class);

                if (pic.up) { dhc.direction = new Vector2(0, 1); }
                else if (pic.down) { dhc.direction = new Vector2(0, -1); }
                else if (pic.left) { dhc.direction = new Vector2(-1, 0); }
                else if (pic.right) { dhc.direction = new Vector2(1, 0); }
                
                Vector2 pos = mc.body.getTransform().getPosition();
                Vector2 adj = new Vector2(pos.x + dhc.direction.x*dhc.offset, pos.y + dhc.direction.y*dhc.offset);
                dhc.body.setTransform(adj, 0);
            }
        }
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "Punch Event":
            PunchEvent pe = (PunchEvent) e;
            applyForce(pe.id, pe.force);
            break;
        }
    }
    
    private void applyForce(int id, Vector2 force) {
        if (_entities.get(id) != null) {
            MovementComponent mc = _entities.get(id).get(MovementComponent.class);
            mc.body.applyForceToCenter(force, true);
        }
    }

    @Override
    public short getID() {
        return EntitySystem.MovementSystem;
    }

}
