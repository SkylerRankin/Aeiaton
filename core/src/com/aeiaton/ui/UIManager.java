package com.aeiaton.ui;

import com.aeiaton.Aeiaton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class UIManager {
    
    private TextureAtlas atlas;
    
    private int pos = 0;
    private float wait = 0;
    private int max = 8;
    private int padding = 3;
    
    private int width = 60;
    private int height = 30;
    
    private Array<TextureRegion> temp;
    
    public UIManager() {
        atlas = new TextureAtlas("sprites//sprites.atlas");
        temp = new Array<>();
        for (int i = 0; i <= max; ++i) {
            TextureRegion r = new TextureRegion(atlas.findRegion("ui"), i*width, 0, width, height);
            temp.add(r);
        }
    }
    
    public void update(float d) {
        wait += d;
        if (wait >= 1f) {
            wait = 0;
            pos = pos >= max ? 0 : pos + 1;
        }
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(temp.get(pos), padding, Aeiaton.DEFAULT_HEIGHT - height - padding);
    }

}
