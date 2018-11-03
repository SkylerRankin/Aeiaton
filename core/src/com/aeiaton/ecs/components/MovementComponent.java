package com.aeiaton.ecs.components;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.utils.Array;

public class MovementComponent implements Component {
    
    public Body body;
    public BodyDef bdef;
    public FixtureDef fdef;
    public PolygonShape shape;
    
    public MovementComponent(World world, Vector2 p, Vector2 v, Vector2 s, float w, float d, String data, boolean isBoundary, boolean hitsDirectional) {
        position = p;
        velocity = v;
        size = s;
        walk_force = w;
        dash_force = d;
        
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();
        
        bdef.type = isBoundary ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bdef.position.set(position.x + (s.x / 2), position.y + (s.y / 2));
        body = world.createBody(bdef);
        shape.setAsBox(s.x / 2, s.y / 2);
        if (isBoundary) {
            fdef.filter.categoryBits = hitsDirectional ? Aeiaton.BOUNDARY_BIT | Aeiaton.INTERACTABLE_BIT : Aeiaton.BOUNDARY_BIT;
            fdef.filter.maskBits = hitsDirectional ? Aeiaton.BOUNDARY_BIT | Aeiaton.DIRECTIONAL_HITBOX_BIT : Aeiaton.BOUNDARY_BIT;
        } else {
            fdef.filter.categoryBits = Aeiaton.PLAYER_BIT;
            fdef.filter.maskBits = Aeiaton.BOUNDARY_BIT;
        }
        
        fdef.shape = shape;
        body.setUserData(data);
        body.createFixture(fdef).setUserData(data);;
        
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        
        for (Body b : bodies) {
            if (b.getUserData() != null && b.getUserData().equals("ground")) {
                FrictionJointDef frictionJointDef = new FrictionJointDef();
                frictionJointDef.maxForce = 400;
                frictionJointDef.maxTorque = 400;
                frictionJointDef.initialize(b, body, new Vector2(0, 0));
                world.createJoint(frictionJointDef);
            }
        }
    }
    
    public float dash_force;
    public float walk_force;
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Vector2 size = new Vector2();
    
}
