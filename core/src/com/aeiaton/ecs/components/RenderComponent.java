package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderComponent implements Component {
    
    public int width;
    public int height;
    public TextureRegion texture_region;
    //public Texture texture_region;
    
    public RenderComponent(int w, int h) {
        //texture_region = new TextureRegion();
        width = w;
        height = h;
    }

}
