package com.aeiaton.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.aeiaton.ecs.Component;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.observer.Event;

public class SystemTest {
    
    @Test
    public void testGetAllSize() {
        Entity e1 = new Entity();
        e1.addComponent(new Component1());
        e1.addComponent(new Component2());
        e1.addComponent(new Component3());
        
        Entity e2 = new Entity();
        e2.addComponent(new Component3());
        
        assertEquals(3, e1.getAll().size());
        assertEquals(1, e2.getAll().size());
        
    }
    
    @Test
    public void testDependencies() {
        ECSCore core = new ECSCore();
        core.addSystem(new System1());
        core.addSystem(new System2());
        
        Entity e1 = new Entity();
        e1.addComponent(new Component1());
        e1.addComponent(new Component2());
        e1.addComponent(new Component3());
        
        Entity e2 = new Entity();
        e2.addComponent(new Component3());
        
        System1 s1 = new System1();
        System2 s2 = new System2();
        
        assertTrue(s1.uses(e1.getAll()));
        assertTrue(s2.uses(e1.getAll()));
        assertFalse(s1.uses(e2.getAll()));
        assertTrue(s2.uses(e2.getAll()));
        
    }
    
    class System1 extends EntitySystem {
        public System1() {
            super(0, Component1.class, Component2.class);
        }

        @Override
        public void update(float d) {}

        @Override
        public void notify(Event e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public short getID() {
            // TODO Auto-generated method stub
            return 0;
        }
    }
    
    class System2 extends EntitySystem {
        public System2() {
            super(1, Component3.class);
        }

        @Override
        public void update(float d) {}

        @Override
        public void notify(Event e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public short getID() {
            // TODO Auto-generated method stub
            return 0;
        }
    }
    
    class Component1 implements Component {
        public int one = 1;
    }
    
    class Component2 implements Component {
        public int two = 2;
    }
    
    class Component3 implements Component {
        public int three = 3;
    }

}