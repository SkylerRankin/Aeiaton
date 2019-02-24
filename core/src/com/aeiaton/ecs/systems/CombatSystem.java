package com.aeiaton.ecs.systems;

import com.aeiaton.Aeiaton;
import com.aeiaton.classes.Constants;
import com.aeiaton.classes.LaserGrid;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.LaserEvent;
import com.aeiaton.observer.MirrorRotateEvent;
import com.badlogic.gdx.math.Vector2;

public class CombatSystem extends EntitySystem {
    
    public boolean laser_active = false;
    public LaserGrid lasergrid;

    public CombatSystem() {
        super(5);
        
        //Vector2[] p = {new Vector2(12,26), new Vector2(12, 27), new Vector2(13, 27), new Vector2(13, 26), new Vector2(13, 28)};
        //lasergrid = new LaserGrid(p, new int[] {Constants.UP, Constants.RIGHT, Constants.UP, Constants.RIGHT, Constants.RIGHT}, 3);
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "LaserEvent":
            laser_active = !laser_active;
            lasergrid.percent = 0;
            break;
        case "MirrorRotateEvent":
            MirrorRotateEvent mre = (MirrorRotateEvent) e;
            if (mre.clicked) {
                lasergrid.setDirection(mre.index, mre.direction);
            }
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.CombatSystem;
    }

    @Override
    public void update(float d) {
        if (laser_active) {
            if (lasergrid.percent < 1) {
                lasergrid.percent+=0.01;
            }
        }
    }
    
    //**  Getters and Setters for Combat Data  **//
    
    //scales positions by ppm
    public void setLaserGridPositions(Vector2[] p, int[] d, int end) {
        Vector2[] scaled = new Vector2[p.length];
        for (int i = 0; i < p.length; ++i) {
            scaled[i] = new Vector2(p[i].x / Aeiaton.PPM, p[i].y / Aeiaton.PPM);
        }
        lasergrid = new LaserGrid(scaled, d, end);
    }

}
