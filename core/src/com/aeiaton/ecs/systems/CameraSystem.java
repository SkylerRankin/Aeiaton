package com.aeiaton.ecs.systems;

import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.components.CameraFollowComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;

public class CameraSystem extends EntitySystem {

    private Camera camera;
    
    public CameraSystem(Camera c) {
        super(0, CameraFollowComponent.class, MovementComponent.class);
        camera = c;
    }

    @Override
    public void update(float d) {
        for (Entity e : entities) {
            CameraFollowComponent cfc = e.get(CameraFollowComponent.class);
            MovementComponent mc = e.get(MovementComponent.class);
            camera.position.x -= cfc.offset.x;
            camera.position.y -= cfc.offset.y;
            switch (cfc.mode) {
            case EXACT:
                camera.position.x = mc.body.getPosition().x + cfc.offset.x;
                camera.position.y = mc.body.getPosition().y + cfc.offset.y;
                break;
            case LERP:
                camera.position.x = camera.position.x + (mc.body.getPosition().x - camera.position.x) * cfc.LERP_CONSTANT  + cfc.offset.x;
                camera.position.y = camera.position.y + (mc.body.getPosition().y - camera.position.y) * cfc.LERP_CONSTANT  + cfc.offset.y;
                break;
            }
            camera.update();
            break;
        }
    }

    @Override
    public void notify(Event e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public short getID() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public Matrix4 getCameraProjectionMatrix() {
        return camera.combined;
    }

}
