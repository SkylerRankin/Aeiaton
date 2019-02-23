package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MirrorComponent implements Component {
    
    public int dir;
    public int counter = 0;
    private TextureAtlas tex_atlas = new TextureAtlas("sprites//sprites.atlas");
    public TextureRegion comp = new TextureRegion(tex_atlas.findRegion("computer"), 0,0, 22, 18);
    public TextureRegion yellow_comp = new TextureRegion(tex_atlas.findRegion("computer_yellow"), 0,0, 22, 18);

    
    /**
     * @param dir direction mirror is facing: 0 = up, 1 = right, 2 = down, 3 = left
     */
    public MirrorComponent (int dir) {
        this.dir = dir;
    }
}
