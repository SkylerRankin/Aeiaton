package com.aeiaton.util;

import java.util.HashMap;
import java.util.Map;

import com.aeiaton.Aeiaton;
import com.aeiaton.screens.Level;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class WorldBuilder {
    
    public static TiledMap loadMap(Level level, String path) {
        TiledMap map = new TmxMapLoader().load(path);
        level.setMap(map);
        BodyDef bdef = new BodyDef();
        Body body;
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        
        // Add ground to box2d world
        
        Map<String, Integer> names = new HashMap<>();
        
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            names.put(map.getLayers().get(i).getName(), i);
        }
        
        if (names.containsKey("Ground")) {
            for (MapObject object : map.getLayers().get(names.get("Ground")).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / Aeiaton.PPM, (rect.getY() + rect.getHeight() / 2) / Aeiaton.PPM);
                body = level.getWorld().createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2 / Aeiaton.PPM, rect.getHeight() / 2 / Aeiaton.PPM);
                fdef.shape = shape;
                fdef.friction = 1f;
                //fdef.filter.categoryBits = Aeiaton.BOUNDARY_BIT;
                //fdef.filter.maskBits = Aeiaton.PLAYER_BIT | Aeiaton.OBJECT_BIT;
                body.createFixture(fdef).setUserData("ground");
                body.setUserData("ground");
            }
        }

        /*
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Aeiaton.PPM, (rect.getY() + rect.getHeight() / 2) / Aeiaton.PPM);
            body = level.getWorld().createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / Aeiaton.PPM, rect.getHeight() / 2 / Aeiaton.PPM);
            fdef.shape = shape;
            fdef.friction = 1f;
            //fdef.filter.categoryBits = Aeiaton.BOUNDARY_BIT;
            //fdef.filter.maskBits = Aeiaton.PLAYER_BIT | Aeiaton.OBJECT_BIT;
            body.createFixture(fdef).setUserData("ground");
            body.setUserData("ground");
        }
        */
     // Add world edges to box2d world
        
        if (names.containsKey("Boundaries")) {
            for (MapObject object : map.getLayers().get(names.get("Boundaries")).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / Aeiaton.PPM, (rect.getY() + rect.getHeight() / 2) / Aeiaton.PPM);
                body = level.getWorld().createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2 / Aeiaton.PPM, rect.getHeight() / 2 / Aeiaton.PPM);
                fdef.shape = shape;
                fdef.filter.categoryBits = Aeiaton.BOUNDARY_BIT;
                fdef.filter.maskBits = Aeiaton.PLAYER_BIT | Aeiaton.OBJECT_BIT;
                Object data;
                if (object.getProperties().containsKey("data")) {
                    body.setUserData(object.getProperties().get("data"));
                    data = object.getProperties().get("data");
                } else {
                    body.setUserData("boundary");
                    data = "boundary";
                }
                body.createFixture(fdef).setUserData("boundary");
            }
        }
        
        // Add objects to ECS and box2d world
        if (names.containsKey("Interactives")) {
            for (MapObject object : map.getLayers().get(names.get("Interactives")).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / Aeiaton.PPM, (rect.getY() + rect.getHeight() / 2) / Aeiaton.PPM);
                body = level.getWorld().createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2 / Aeiaton.PPM, rect.getHeight() / 2 / Aeiaton.PPM);
                fdef.shape = shape;
                fdef.filter.categoryBits = Aeiaton.BOUNDARY_BIT;
                fdef.filter.maskBits = Aeiaton.PLAYER_BIT | Aeiaton.OBJECT_BIT;
                String data = "";
                if (object.getProperties().containsKey("object_data")) {
                    data = (String) object.getProperties().get("object_data");
                } else {
                    System.out.println("WorldBuilder: failed to load object_data from map object");
                }
                body.createFixture(fdef).setUserData(data);
            }
        }
        
        return map;
    }

}
