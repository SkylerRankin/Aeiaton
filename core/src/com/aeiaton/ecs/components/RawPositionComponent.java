package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class RawPositionComponent implements Component {

    public float x;
    public float y;
    public int above_player; //-1 for below, 0 for unset, 1 for above
    
    public RawPositionComponent(float x, float y, int ap) {
        this.x = x;
        this.y = y;
        above_player = ap;
    }
    
}
