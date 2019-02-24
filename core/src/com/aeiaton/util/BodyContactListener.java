package com.aeiaton.util;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.MirrorComponent;
import com.aeiaton.observer.ChangeLevelEvent;
import com.aeiaton.observer.DoorOpenEvent;
import com.aeiaton.observer.MirrorRotateEvent;
import com.aeiaton.observer.ObjectActivationEvent;
import com.aeiaton.observer.Observer;
import com.aeiaton.observer.TerminalEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class BodyContactListener implements ContactListener {
    
    private final boolean debug = true;
    private boolean directional_hitbox_clear;
    private ECSCore core;
    private Observer observer;
    
    public BodyContactListener(ECSCore c, Observer o) {
        core = c;
        observer = o;
        directional_hitbox_clear = true;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
      
        String[] a_userdata = (String[]) a.getUserData();
        String[] b_userdata = (String[]) b.getUserData();
        if (a_userdata[0].equals("mirror") || b_userdata[0].equals("mirror")) {
            int id = a_userdata[0].equals("mirror") ? Integer.parseInt(a_userdata[0]) : Integer.parseInt(b_userdata[1]);
            Entity mirror = core.getEntity(id);
            if (mirror != null && mirror.hasComponent(MirrorComponent.class)) {
                MirrorComponent mc = mirror.get(MirrorComponent.class);
                MirrorRotateEvent mre = new MirrorRotateEvent(id, mc.last_flip);
                observer.recieve(mre);
            } else {
                System.out.println("Mirror entity not found, id="+id);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        String[] a_userdata = (String[]) a.getUserData();
        String[] b_userdata = (String[]) b.getUserData();
        
        //collision with rotate-able mirror
        if (a_userdata[0].equals("mirror") || b_userdata[0].equals("mirror")) {
            int id = a_userdata[0].equals("mirror") ? Integer.parseInt(a_userdata[0]) : Integer.parseInt(b_userdata[1]);
            Entity mirror = core.getEntity(id);
            if (mirror != null && mirror.hasComponent(MirrorComponent.class)) {
                MirrorComponent mc = mirror.get(MirrorComponent.class);
                //indicate the mirror is no longer in front my removing its id, setting to -1
                MirrorRotateEvent mre = new MirrorRotateEvent(-1, mc.last_flip);
                observer.recieve(mre);
            } else {
                System.out.println("Mirror entity not found, id="+id);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
        
    }

}
