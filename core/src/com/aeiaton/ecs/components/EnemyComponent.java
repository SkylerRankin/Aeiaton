package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class EnemyComponent implements Component{

    public int speed;
    public int damage;
    public enum EnemyState {Idle, Searching, Chasing, Attacking};
    public enum EnemyDirection {Up, Down, Left, Right}

    public EnemyState state = EnemyState.Chasing;
    public EnemyDirection direction = EnemyDirection.Up;


    public EnemyComponent(int speed, int damage){
        this.speed = speed;
        this.damage = damage;
    }

}