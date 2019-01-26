package com.aeiaton.ecs.systems;


import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.observer.Event;
import com.aeiaton.observer.FreezeEvent;
import com.aeiaton.observer.TerminalEvent;
import com.aeiaton.observer.TitleEvent;
import com.aeiaton.ui.TerminalWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class UISystem extends EntitySystem {
    
    private boolean debug = true;
    private TextureAtlas atlas;
    
    private int pos = 0;
    private float wait = 0;
    private int max = 8;
    private int padding = 3;
    
    private int width = 60;
    private int height = 30;
    
    private Array<TextureRegion> temp;
    
    //Current UI objects
    private TerminalEvent terminal_event = null;
    private boolean terminal_open = false;
    private TitleEvent title_event = null;
    
    private Stage stage;
    private BitmapFont font = new BitmapFont();

    public UISystem(Stage stage) {
        super(0);
        atlas = new TextureAtlas("sprites//sprites.atlas");
        temp = new Array<>();
        this.stage = stage;
        for (int i = 0; i <= max; ++i) {
            TextureRegion r = new TextureRegion(atlas.findRegion("ui"), i*width, 0, width, height);
            temp.add(r);
        }
    }

    @Override
    public void notify(Event e) {
        if (debug) System.out.println(e.getName()+" recieved");
        switch (e.getName()) {
        case "Punch Event":
            pos = pos < max ? pos+1 : max;
            break;
        case "TerminalEvent":
            terminal_event = (TerminalEvent) e;
            break;
        case "TitleEvent":
            title_event = (TitleEvent) e;
            break;
        }
        
    }

    @Override
    public short getID() {
        return EntitySystem.UISystem;
    }

    @Override
    public void update(float d) {
        if (Gdx.input.isKeyJustPressed(Keys.Q) && ( terminal_open || terminal_event != null)) {
            if (!terminal_open) {
                stage.addActor(new TerminalWindow(terminal_event.id));
                terminal_open = true;
                terminal_event = null;
                observer.recieve(new FreezeEvent(true));
            } else {
                stage.clear();
                terminal_open = false;
                observer.recieve(new FreezeEvent(false));
            }
        }
        if (title_event != null && title_event.duration >= 0) {
            title_event.duration -= d;
            if (title_event.duration <= 0) {
                System.out.println("Title ended: "+title_event.text);
                title_event = null;
            }
        }
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(temp.get(pos), padding, Aeiaton.DEFAULT_HEIGHT - height - padding);
        if (title_event != null) {
            font.draw(batch, title_event.text, Aeiaton.DEFAULT_WIDTH/2, Aeiaton.DEFAULT_HEIGHT/2);
        }
    }

}
