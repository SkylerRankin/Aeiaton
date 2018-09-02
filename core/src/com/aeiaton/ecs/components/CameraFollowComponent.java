package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class CameraFollowComponent implements Component {
    
    public static enum FollowMode {EXACT, LERP}
    public FollowMode mode;
    public float LERP_CONSTANT;
    
    public CameraFollowComponent(CameraFollowComponent.FollowMode mode) {
        this.mode = mode;
        LERP_CONSTANT = 0.5f;
    }
    
    public CameraFollowComponent(CameraFollowComponent.FollowMode mode, float c) {
        this.mode = mode;
        LERP_CONSTANT = c;
    }
    
}
