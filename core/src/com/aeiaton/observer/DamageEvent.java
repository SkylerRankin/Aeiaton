package com.aeiaton.observer;

public class DamageEvent extends Event {
    
    public int damage;
    public int entity;
    
    public DamageEvent(int d, int e) {
        damage = d;
        entity = e;
    }

    @Override
    public short getID() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return "DamageEvent";
    }

}
