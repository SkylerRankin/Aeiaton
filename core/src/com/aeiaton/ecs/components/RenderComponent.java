package com.aeiaton.ecs.components;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderComponent implements Component {
    
    public float width;
    public float height;
    public TextureRegion texture_region;
    //public Texture texture_region;
    
    public RenderComponent(float w, float h) {
        //texture_region = new TextureRegion();
        width = w / Aeiaton.PPM;
        height = h / Aeiaton.PPM;
    }

}
