package com.aeiaton.ecs.components;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
 * Parallax components:
 *  - ParallaxLayer: the component used for identification, holds z axis, and position
 *  - Render component is used for the actual textures
 */

public class ParallaxLayer implements Component {
    
    public int x;
    public int z;
    public TextureRegion img;
    
    public ParallaxLayer(int _x, int _z, String i) {
        x = _x;
        z = _z;
        img = new TextureRegion(new Texture(Gdx.files.internal(i)));
    }
}
