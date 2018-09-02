package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;

public class PlayerStateComponent implements Component {
    
    public enum PlayerState {Idle, Walk, Dash, Punch, Beam}
    public enum PlayerDirection {Up, Down, Left, Right}
    
    public PlayerState state = PlayerState.Idle;
    public PlayerDirection direction = PlayerDirection.Up;

}
