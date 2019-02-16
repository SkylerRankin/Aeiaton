package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class EnemyComponenet implements Component{

    public int speed;
    public boolean hit;
    public int damage;

    public EnemyComponent(int speed, boolean hit, int damage){
        this.speed = speed;
        this.hit = hit;
        this.damage = damage;
    }

}