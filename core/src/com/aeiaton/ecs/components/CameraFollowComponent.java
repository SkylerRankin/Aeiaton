package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.math.Vector2;

public class CameraFollowComponent implements Component {
    
    public static enum FollowMode {EXACT, LERP}
    public FollowMode mode;
    public float LERP_CONSTANT;
    public Vector2 offset;
    
    public CameraFollowComponent(CameraFollowComponent.FollowMode mode) {
        this.mode = mode;
        LERP_CONSTANT = 0.04f;
        offset = new Vector2();
    }
    
    public CameraFollowComponent(CameraFollowComponent.FollowMode mode, float c) {
        this.mode = mode;
        LERP_CONSTANT = c;
        offset = new Vector2();
    }
    
    public CameraFollowComponent(CameraFollowComponent.FollowMode mode, float c, Vector2 offset) {
        this.mode = mode;
        LERP_CONSTANT = c;
        this.offset = offset;
    }
    
}
