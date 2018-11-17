package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class RawPositionComponent implements Component {

    public float x;
    public float y;
    
    public RawPositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
}
