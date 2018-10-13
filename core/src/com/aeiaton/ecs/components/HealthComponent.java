package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class HealthComponent implements Component {
    
    public int max;
    public int health;
    
    public HealthComponent(int m, int h) {
        max = m;
        health = h;
    }

}
