package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class InteractableComponent implements Component {
    
    public boolean active = false;
    public String inactive_texture;
    public String active_texture;
    
    public InteractableComponent(String i, String a) {
        inactive_texture = i;
        active_texture = a;
    }

}
