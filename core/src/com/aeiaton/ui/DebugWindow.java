package com.aeiaton.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DebugWindow extends Table {
    
    private Skin skin = new Skin(Gdx.files.internal("default_skin\\uiskin.json"));
    private Label mouse_abs;
    private Label mouse_screen;
    
    private Viewport viewport;
    
    public DebugWindow(Viewport vp) {
        viewport = vp;
        mouse_abs = new Label("Mouse Absolute:", skin);
        mouse_screen = new Label("Mouse Screen:", skin);
        setWidth(200);
        setHeight(100);
        setVisible(false);
        setPosition(0, 0);
        add(mouse_abs);
        row();
        add(mouse_screen);
    }
    
    public void update() {
        mouse_abs.setText("Mouse Screen: ("+(int)(Gdx.input.getX())+", "+(int)(Gdx.input.getY())+")");
        Vector2 s = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        mouse_screen.setText("Mouse Viewport: ("+(int)(s.x)+", "+(int)(s.y)+")");
    }

}
