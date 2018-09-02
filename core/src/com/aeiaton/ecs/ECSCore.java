package com.aeiaton.ecs;

import java.util.HashMap;
import java.util.Map;

import com.aeiaton.ecs.systems.RenderSystem;
import com.aeiaton.observer.Observer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ECSCore {
    
    private SystemHandler system_handler;
    private EntityHandler entity_handler;
    private Observer observer;
    
    public Map<Short, Component> component_map;
    
    public ECSCore() {
        system_handler = new SystemHandler();
        entity_handler = new EntityHandler();
        component_map = new HashMap<>();
        observer = new Observer(system_handler);
    }
    
    public void update(float d) {
        for (EntitySystem system : system_handler.getAll()) {
            system.update(d);
        }
    }
    
    public void render(SpriteBatch batch) {
        for (EntitySystem s : system_handler.system_queue) {
            if (s.getClass() == RenderSystem.class) {
                ((RenderSystem) s).render(batch);
                return;
            }
        }
    }
    
    public void addEntity(Entity e) {
        entity_handler.addEntity(e);
        system_handler.addEntity(e);
    }
    
    public void removeEntity(Entity e) {
        entity_handler.removeEntity(e);
    }
    
    public void addSystem(EntitySystem s) {
        s.setObserver(observer);
        system_handler.addSystem(s);
    }
    
    public void removeSystem(EntitySystem s) {
        system_handler.removeSystem(s);
    }

}
