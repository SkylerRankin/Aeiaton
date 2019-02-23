package com.aeiaton.ecs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.aeiaton.observer.Event;
import com.aeiaton.observer.Observer;

public abstract class EntitySystem {
    
    public final static short UISystem = 1;
    public final static short MovementSystem = 2;
    public final static short InteractableItemSystem = 4;
    public final static short DoorSystem = 8;
    public final static short GameStateSystem = 16;
    public final static short ParallaxSystem = 32;
    public final static short InputSystem = 64;
    public final static short EnemySystem = 128;
    
    public final int priority;
    protected Observer observer;
    
    //private int count;
    
    protected Map<Integer, Entity> _entities = new HashMap<>();
    
    protected Set<Entity> entities = new HashSet<Entity>();
    protected Set<Class<?>> dependencies = new HashSet<>();
    
    public EntitySystem(int p) {
        priority = p;
    }
    
    public EntitySystem(int p, Class<?>...c) {
        priority = p;
        for (Class<?> _c : c)
            dependencies.add(_c);
    }
    
    public void addDependency(Class<?>...c) {
        for (Class<?> _c : c)
            dependencies.add(_c);
    }
    
    public void addEntity(Entity e) {
        entities.add(e);
        _entities.put(e.id,  e);
        //count++;
    }
    
    public void setObserver(Observer o) {
        observer = o;
    }
    
    public abstract void notify(Event e);
    public abstract short getID();
    
    public boolean uses(Set<Component> components) {
        for (Class<?> c : dependencies) {
            boolean clear = false;
            for (Component comp : components) {
                if (comp.getClass() == c) {
                    clear = true;
                    break;
                }
            }
            if (!clear) return false;
        }
        return true;
    }
    
    public abstract void update(float d);
    
}
