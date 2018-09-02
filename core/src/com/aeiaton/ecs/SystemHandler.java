package com.aeiaton.ecs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SystemHandler {
    
    public PriorityQueue<EntitySystem> system_queue;
    
    public SystemHandler() {
        system_queue = new PriorityQueue<EntitySystem>(new Comparator<EntitySystem>() {
            @Override
            public int compare(EntitySystem a, EntitySystem b) {
                return a.priority == b.priority ? 0 : (a.priority < b.priority ? 1 : -1);
            }
        });
    }
    
    public EntitySystem[] getAll() {
        EntitySystem[] systems = new EntitySystem[system_queue.size()];
        int i = 0;
        for (EntitySystem s : system_queue) {
            systems[i++] = s;
        }
        return systems;
    }
    
    public <T extends Component> EntitySystem[] get(Class<T>...components) {
        ArrayList<EntitySystem> systems = new ArrayList<>();
        return null;
    }
    
    public void removeSystem(EntitySystem s) {
        system_queue.remove(s);
    }
    
    public void addSystem(EntitySystem s) {
        system_queue.add(s);
    }
    
    public void addEntity(Entity e) {
        for (EntitySystem s : system_queue) {
            if (s.uses(e.getAll())) {
                s.addEntity(e);
            }
        }
    }
    
}
