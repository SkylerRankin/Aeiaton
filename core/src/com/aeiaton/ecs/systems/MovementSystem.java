package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.DirectionalHitboxComponent;
import com.aeiaton.ecs.components.EnemyComponent;
import com.aeiaton.ecs.components.EnemyComponent.EnemyState;
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
    public void update(float d) {
        for (Entity e : entities) {
            if (e.hasComponent(PlayerInputComponent.class)) {
                PlayerStateComponent psc = e.get(PlayerStateComponent.class);
                PlayerInputComponent pic = e.get(PlayerInputComponent.class);
                MovementComponent mc = e.get(MovementComponent.class);
                if (pic.up) { mc.body.setLinearVelocity(0, mc.velocity); }
                if (pic.down) { mc.body.setLinearVelocity(0, -mc.velocity); }
                if (pic.left) { mc.body.setLinearVelocity(-mc.velocity, 0); }
                if (pic.right) { mc.body.setLinearVelocity(mc.velocity, 0); }
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
            
            if (e.hasComponent(EnemyComponent.class)) {
                MovementComponent mc = e.get(MovementComponent.class);
                EnemyComponent ec = e.get(EnemyComponent.class);
                switch (ec.state) {
                case Random:
                    Vector2 v = new Vector2();
                    if (Math.random()>0.5) v.x = mc.velocity;
                    else v.y = mc.velocity;
                    if (Math.random()>0.5) v = v.scl(-1);
                    mc.body.setLinearVelocity(v);
                    break;
                case Chase:
                    if (ec.chase_id == -1 || ec.chase_body == null) break;
                    //get difference between position and target's position. make magnitude 1, then scale by velocity
                    Vector2 target = ec.chase_body.getPosition();
                    Vector2 diff = target.sub(mc.body.getPosition());
                    diff = diff.scl(1 / diff.len());
                    diff = diff.scl(mc.velocity);
                    mc.body.setLinearVelocity(diff);
                    break;
                case Idle:
                    break;
                default:
                    break;
                }
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
