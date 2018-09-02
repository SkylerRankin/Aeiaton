package com.aeiaton.ecs;

import java.util.HashSet;
import java.util.Set;

public class EntityHandler {
    
    Set<Entity> entities = new HashSet<Entity>();
    
    public void removeEntity(Entity e) {
        
    }
    
    public void addEntity(Entity e) {
        entities.add(e);
    }

}
