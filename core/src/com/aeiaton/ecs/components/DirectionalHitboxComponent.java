package com.aeiaton.ecs.components;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class DirectionalHitboxComponent implements Component {
    
    public float offset;
    public Body body;
    public BodyDef bdef;
    public FixtureDef fdef;
    public PolygonShape shape;
    public Vector2 direction;
    
    public DirectionalHitboxComponent(World world, Vector2 s, Vector2 p, float o) {
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();
        direction = new Vector2(1, 1);
        offset = o / Aeiaton.PPM;
        
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(p.x / Aeiaton.PPM, p.y / Aeiaton.PPM);
        body = world.createBody(bdef);
        shape.setAsBox(s.x / Aeiaton.PPM / 2, s.y / Aeiaton.PPM / 2);
        //fdef.filter.categoryBits = Aeiaton.DIRECTIONAL_HITBOX_BIT;
        //fdef.filter.maskBits = Aeiaton.INTERACTABLE_BIT;
        fdef.shape = shape;
        body.setUserData("directional_hitbox");
        body.createFixture(fdef).setUserData("directional_hitbox");
    }
}
