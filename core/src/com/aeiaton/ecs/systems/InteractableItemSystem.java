package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.InteractableComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

public class InteractableItemSystem extends EntitySystem {

    private World world;
    private Entity player;
    
    public InteractableItemSystem(World world, Entity player) {
        super(3, InteractableComponent.class);
        this.world = world;
        this.player = player;
    }

    @Override
    public void update(float d) {
        MovementComponent _mc = player.get(MovementComponent.class);
        //Vector2 dir = _mc.body.getLinearVelocity();
        //world.QueryAABB(AABBCallback, _mc.body.getPosition().x - 10, _mc.body.getPosition().y + 10, _mc.body.getPosition().x + 10, _mc.body.getPosition().y - 10);
    }
    
    private QueryCallback AABBCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.getBody().getUserData() == null || !fixture.getBody().getUserData().equals("player")) {
                System.out.println("InteractableItemSystem: fixture found, data="+fixture.getBody().getUserData());
                return true;
            }
            return false;
        }
    };

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "PunchEvent":
            
            break;
        }
    }

    @Override
    public short getID() {
        // TODO Auto-generated method stub
        return 0;
    }

}
