package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class InteractableComponent implements Component {
    
    public float distance;
    
    public InteractableComponent(float d) {
        distance = d;
    }

}
