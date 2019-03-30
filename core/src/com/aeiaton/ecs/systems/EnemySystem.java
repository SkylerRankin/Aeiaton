package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.*;
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
    private Entity player;

    public EnemySystem(World world, Entity givenPlayer) {
        super(3, EnemyComponent.class);
        player = givenPlayer;
    }

    @Override
    public void addEntity(Entity e) {
        super.addEntity(e);
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            if (e.hasComponent(MovementComponent.class)) {
                EnemyComponent ec = e.get(EnemyComponent.class);
                MovementComponent mc = e.get(MovementComponent.class);
                if (ec.state.equals(EnemyComponent.EnemyState.Chasing)) {
                    float playerX = player.get(MovementComponent.class).body.getPosition().x;
                    float playerY = player.get(MovementComponent.class).body.getPosition().y;

                    float enemyX = mc.body.getPosition().x;
                    float enemyY = mc.body.getPosition().y;

                    System.out.println("Positions are: " + playerX + " " + playerY + " " + enemyX + " " + enemyY);

                    if (playerY > enemyY) {
                        mc.body.applyLinearImpulse(new Vector2(0, mc.walk_force), mc.body.getWorldCenter(), true);
                    }
                    if (playerY < enemyY) {
                        mc.body.applyLinearImpulse(new Vector2(0, -mc.walk_force), mc.body.getWorldCenter(), true);
                    }
                    if (playerX < enemyX) {
                        mc.body.applyLinearImpulse(new Vector2(-mc.walk_force, 0), mc.body.getWorldCenter(), true);
                    }
                    if (playerX > enemyX) {
                        //System.out.println("will move right");
                        //mc.body.applyLinearImpulse(new Vector2(-mc.walk_force, 0), mc.body.getWorldCenter(), true);
                        mc.body.applyLinearImpulse(new Vector2(mc.walk_force, 0), mc.body.getWorldCenter(), true);
                    }
                    return;
                }
            }

        }
    }

    @Override
    public void notify(Event e) {
        /*
        Need to add attack event for enemy.
        switch (e.getName()) {
            case "Punch Event":
                PunchEvent pe = (PunchEvent) e;
                applyForce(pe.id, pe.force);
                break;
        } */
    }

    private void applyForce(int id, Vector2 force) {
        if (_entities.get(id) != null) {
            MovementComponent mc = _entities.get(id).get(MovementComponent.class);
            mc.body.applyForceToCenter(force, true);
        }
    }

    @Override
    public short getID() {
        return EntitySystem.EnemySystem;
    }

}
