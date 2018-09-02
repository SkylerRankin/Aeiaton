package com.aeiaton.ecs;

import java.util.HashSet;
import java.util.Set;

public class Entity {
    
    protected Set<Component> components;
    public int priority;
    
    public Entity() {
        components = new HashSet<>();
    }
    
    public Entity(int p) {
        priority = p;
        components = new HashSet<>();
    }
    
    public boolean addComponent(Component c) {
        if (get(c.getClass()) == null) {
            components.add(c);
            return true;
        }
        return false;
    }
    
    public <T extends Component> boolean removeComponent(Class<T> component) {
        for (Component c : components) {
            if (c.getClass().equals(component)) {
                components.remove(c);
                return true;
            }
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Component> T get(Class<T> component) {
        for (Component c : components) {
            if (c.getClass() == component)
                return (T) c;
        }
        return null;
    }
    
    public <T extends Component> boolean hasComponent(Class<T> component) {
        for (Component c : components) {
            if (c.getClass() == component)
                return true;
        }
        return false;
    }
    
    public Set<Component> getAll() {
        return components;
    }
    
}
