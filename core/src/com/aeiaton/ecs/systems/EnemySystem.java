package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.*;
import com.aeiaton.ecs.components.EnemyComponent.EnemyState;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.FreezeEvent;
import com.aeiaton.observer.PunchEvent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class EnemySystem extends com.aeiaton.ecs.EntitySystem {

    private World world;

    public EnemySystem(World world) {
        super(3, EnemyComponent.class);
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            EnemyComponent ec = e.get(EnemyComponent.class);
            
            if (ec.state == EnemyState.Chase && ec.chase_id != -1 && ec.chase_body == null) {
                ec.chase_body = core.getEntity(ec.chase_id).get(MovementComponent.class).body;
            }
            
            

        }
    }

    @Override
    public void notify(Event e) { }

    @Override
    public short getID() {
        return EntitySystem.EnemySystem;
    }

}
