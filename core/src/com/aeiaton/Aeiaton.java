package com.aeiaton;

import com.aeiaton.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Aeiaton extends Game {
    
    public static final String version = "0.0.1";
    public static final boolean debug = true;
    public static final float PPM = 100;
    public static final float DEFAULT_WIDTH = 480;
    public static final float DEFAULT_HEIGHT = 270;
    
    /** Mask Bits **/
    public static final short BOUNDARY_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short DIRECTIONAL_HITBOX_BIT = 8;
    public static final short INTERACTABLE_BIT = 16;
    
    public SpriteBatch batch;
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        Gdx.graphics.setTitle("Aeiaton v"+version);
        this.setScreen(new TestLevel(this));
    }

    @Override
    public void render () {
        super.render();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
    }
}
