package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class EnemyComponent implements Component {

    public int damage;
    public int chase_id = 0;
    public Body chase_body = null;
    public EnemyState state;
    public enum EnemyState {Idle, Chase, Random};

    public EnemyComponent(int damage, EnemyState state) {
        this.damage = damage;
        this.state = state;
    }

}