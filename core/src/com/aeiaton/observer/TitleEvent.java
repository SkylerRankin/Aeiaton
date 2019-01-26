package com.aeiaton.observer;

import com.aeiaton.ecs.EntitySystem;

public class TitleEvent extends Event {
    
    public String text;
    public float duration;
    public int size;
    
    public TitleEvent(String t, float d, int s) {
        text = t;
        duration = d;
        size = s;
    }

    @Override
    public short getID() {
        return EntitySystem.UISystem;
    }

    @Override
    public String getMessage() {
        return String.format("%s %s %fs", text, size, duration);
    }

    @Override
    public String getName() {
        return "TitleEvent";
    }

}
