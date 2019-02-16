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
    
    private int entity_count = 0;
    
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
    
    public void render(SpriteBatch batch, boolean back) {
        for (EntitySystem s : system_handler.system_queue) {
            if (s.getClass() == RenderSystem.class) {
                ((RenderSystem) s).render(batch, back);
                return;
            }
        }
    }
    
    public Entity getEntity(int id) {
        return entity_handler.getEntity(id);
    }
    
    public void addEntity(Entity e) {
        e.id = entity_count;
        entity_count++;
        entity_handler.addEntity(e);
        system_handler.addEntity(e);
    }
    
    public void removeEntity(Entity e) {
        entity_handler.removeEntity(e);
    }
    
    public void addSystem(EntitySystem s) {
        s.setCore(this);
        s.setObserver(observer);
        system_handler.addSystem(s);
    }
    
    public void removeSystem(EntitySystem s) {
        system_handler.removeSystem(s);
    }
    
    public EntitySystem getSystem(Class<?> c) {
        return system_handler.getSystem(c);
    }
    
    public SystemHandler getSystemHandler() {
        return this.system_handler;
    }

}
