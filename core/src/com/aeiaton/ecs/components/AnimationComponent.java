package com.aeiaton.ecs.components;

import java.util.ArrayList;
import java.util.List;

import com.aeiaton.ecs.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationComponent implements Component {
    
    public List<Animation<TextureRegion>> animations = new ArrayList<>();
    public int current_animation = 0;
    public float delay;
    public int width;
    public int height;
    public String[] files;
    public int[] lengths;
    public boolean[] flips;
    public TextureAtlas atlas;
    public float time = 0;
    
    public Animation<TextureRegion> animation;
    
    public AnimationComponent(float delay, int width, int height, String[] files, int[] lengths, boolean[] flips) {
        this.delay = delay;
        current_animation = 0;
        this.width = width;
        this.height = height;
        this.lengths = lengths;
        this.files = files;
        this.flips = flips;
        atlas = new TextureAtlas("sprites//sprites.atlas");
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<files.length; ++i) {
            for (int j=0; j<lengths[i]; ++j) {
                TextureRegion r = new TextureRegion(atlas.findRegion(files[i]), j*width, 0, width, height);
                if (flips[i]) r.flip(true, false);
                frames.add(r);
            }
            animations.add(new Animation<TextureRegion>(delay, frames));
            frames.clear();
        }
    }
    
    public TextureRegion getAnimationFrame(float d) {
        time += d;
        return animations.get(current_animation).getKeyFrame(time, true);
    }
    
    public boolean isCurrentAnimationFinished(float d) {
        return animations.get(current_animation).isAnimationFinished(d);
    }

}
