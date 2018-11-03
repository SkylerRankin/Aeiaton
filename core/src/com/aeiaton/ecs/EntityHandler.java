package com.aeiaton.ecs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityHandler {
    
    int count;
    Map<Integer, Entity> _entities = new HashMap<>();
    Set<Entity> entities = new HashSet<Entity>();
    
    public void removeEntity(Entity e) {
        
    }
    
    public Entity getEntity(int id) {
        return _entities.get(id);
    }
    
    public void addEntity(Entity e) {
        entities.add(e);
        _entities.put(count, e);
        count++;
    }

}
