package com.aeiaton.ecs.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public MovementComponent(World world, Vector2 p, Vector2 v, Vector2 s, float w, float d, String data, boolean isBoundary, boolean hitsDirectional, Integer id) {
        position = p;
        velocity = v;
        size.x = s.x / Aeiaton.PPM;
        size.y = s.y / Aeiaton.PPM;
        walk_force = w;
        dash_force = d;
        
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();
        
        bdef.type = isBoundary ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bdef.position.set((position.x + (s.x / 2))/Aeiaton.PPM, (position.y + (s.y / 2))/Aeiaton.PPM);
        body = world.createBody(bdef);
        shape.setAsBox(s.x / 2 / Aeiaton.PPM, s.y / 2 / Aeiaton.PPM);
        if (isBoundary) {
            fdef.filter.categoryBits = hitsDirectional ? Aeiaton.BOUNDARY_BIT | Aeiaton.INTERACTABLE_BIT : Aeiaton.BOUNDARY_BIT;
            fdef.filter.maskBits = (short)(hitsDirectional ? (Aeiaton.BOUNDARY_BIT | Aeiaton.DIRECTIONAL_HITBOX_BIT | Aeiaton.PLAYER_BIT) : (Aeiaton.BOUNDARY_BIT | Aeiaton.PLAYER_BIT));
        } else {
            fdef.filter.categoryBits = Aeiaton.PLAYER_BIT;
            fdef.filter.maskBits = Aeiaton.BOUNDARY_BIT;
        }
        
        fdef.shape = shape;
        
        String[] info = {data, id.toString()};
        
        body.setUserData(info);
        body.createFixture(fdef).setUserData(info);
        
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        
        for (Body b : bodies) {
            if (b.getUserData() != null && b.getUserData().equals("ground")) {
                FrictionJointDef frictionJointDef = new FrictionJointDef();
                frictionJointDef.maxForce = 40;
                frictionJointDef.maxTorque = 40;
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
    public void update_mask_bits(short bits) { fdef.filter.maskBits = bits; } 
    
}
