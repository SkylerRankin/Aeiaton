package com.aeiaton.ecs.systems;

import java.awt.Point;

import com.aeiaton.classes.LaserGrid;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.LaserEvent;
import com.badlogic.gdx.math.Vector2;

public class CombatSystem extends EntitySystem {
    
    public boolean laser_active = false;
    public LaserGrid lasergrid;

    public CombatSystem() {
        super(5);
        
        //Vector2[] p = {new Vector2(9,26), new Vector2(9, 27), new Vector2(13, 27), new Vector2(13, 26)};
        //lasergrid = new LaserGrid(p, new int[] {}, 3);
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "LaserEvent":
            laser_active = true;
            LaserEvent le = (LaserEvent) e;
            System.out.println(le.pos+" "+le.dir);
            Vector2[] p = {le.pos, new Vector2(le.pos.x+3, le.pos.y)};
            lasergrid = new LaserGrid(p, new int[] {}, 3);
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.CombatSystem;
    }

    @Override
    public void update(float d) {
        
    }

}
