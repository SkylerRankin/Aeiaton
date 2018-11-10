package com.aeiaton.util;
import com.aeiaton.observer.ChangeLevelEvent;
import com.aeiaton.observer.DoorOpenEvent;
import com.aeiaton.observer.ObjectActivationEvent;
import com.aeiaton.observer.Observer;
import com.aeiaton.observer.TerminalEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class BodyContactListener implements ContactListener {
    
    private final boolean debug = true;
    private boolean directional_hitbox_clear;
    private Observer observer;
    
    public BodyContactListener(Observer o) {
        observer = o;
        directional_hitbox_clear = true;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        
        if (directional_hitbox_clear && (a.getUserData() != null && a.getUserData().equals("directional_hitbox")) || (b.getUserData() != null && b.getUserData().equals("directional_hitbox"))) {
            Fixture hitbox = (a.getUserData() != null && a.getUserData().equals("directional_hitbox")) ? a : b;
            Fixture other = a == hitbox ? b : a;
            directional_hitbox_clear = false;
            if (debug) System.out.println("BodyContactListener: begin contact");
            String object = (String) other.getUserData();
            if (debug) System.out.println("BodyContactListener: object="+object);
            if (object.length() > 1 && object.charAt(2) == 'T') {
                int id = Integer.parseInt(object.substring(0,1));
                observer.recieve(new ObjectActivationEvent(id));
                int i = Integer.parseInt(object.substring(object.indexOf("T")+1, object.indexOf("T")+2));
                if (debug) System.out.println("BodyContactListener: terminal "+i);
                observer.recieve(new TerminalEvent(i));
            } else if (object.length() > 1 && object.substring(2, 6).equals("door")) {
                String l = object.substring(7, object.length());
                observer.recieve(new DoorOpenEvent(Integer.parseInt(object.substring(0, 1))));
            }
        } else if ((a.getUserData() != null && a.getUserData().equals("0:player")) || (b.getUserData() != null && b.getUserData().equals("0:player"))) {
            Fixture player = (a.getUserData() != null && a.getUserData().equals("0:player")) ? a : b;
            Fixture other = a == player ? b : a;
            String object = (String) other.getUserData();
            if (object.length() > 1 && object.substring(1, 4).equals("door")) {
                String l = object.substring(4, object.length());
                //observer.recieve(new ChangeLevelEvent(l));
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        
        if ((a.getUserData() != null && a.getUserData().equals("directional_hitbox")) || (b.getUserData() != null && b.getUserData().equals("directional_hitbox"))) {
            Fixture head = (a.getUserData() != null && a.getUserData().equals("directional_hitbox")) ? a : b;
            Fixture other = a == head ? b : a;
            directional_hitbox_clear = true;
            if (debug) System.out.println("BodyContactListener: end contact");
            
            String object = (String) other.getUserData();
            
            if (object.length() > 1 && object.charAt(2) == 'T') {
                int id = Integer.parseInt(object.substring(0,1));
                observer.recieve(new ObjectActivationEvent(id));
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
