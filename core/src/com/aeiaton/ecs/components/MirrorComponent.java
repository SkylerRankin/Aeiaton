package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MirrorComponent implements Component {
    
    public int dir;
    public int counter = 0;
    public boolean last_flip;
    
    /**
     * @param dir direction mirror is facing: 0 = up, 1 = right, 2 = down, 3 = left
     */
    public MirrorComponent (int dir) {
        this.dir = dir;
        last_flip = (dir % 2 != 0);
    }
}
