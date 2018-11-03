package com.aeiaton.ui;

import com.aeiaton.util.ComputerTerminalData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class TerminalWindow extends Table {
    
    private Skin skin = new Skin(Gdx.files.internal("default_skin\\uiskin.json"));
    private TextArea text_area;
    
    private float top = 30f;
    private float edge = 10f;
    private float editor_height = Gdx.graphics.getHeight() * .75f;
    private float editor_width = Gdx.graphics.getWidth() * .75f;
    
    public TerminalWindow(int id) {
        this.setSkin(createSkin());
        //TextFieldStyle style = new TextFieldStyle();
        //style.fontColor = Color.GREEN;
        //style.font = new BitmapFont(Gdx.files.internal("default_skin\\default.fnt"));
        //text_area.setStyle(style);
        text_area = new TextArea(new ComputerTerminalData().getPrompt(id), createSkin());
        setup();
    }
    
    private static Skin createSkin() {
        Skin skin = new Skin(Gdx.files.internal("default_skin\\uiskin.json"));
        skin.getFont("default-font").setColor(Color.GREEN);
        return skin;
    }
    
    private void setup() {
        setBackground(new NinePatchDrawable(getNinePatch(("gray_9patch.png"))));
        
        text_area.setColor(Color.BLACK);
        text_area.setBlinkTime(1f);
        text_area.setVisible(true);
        text_area.setTextFieldListener(new TextFieldListener() {
            @Override
            public void keyTyped(TextField text, char c) {
                text.appendText(Character.toString(c));
            }
        });
        //text_area.setDebug(true);
        
        this.add(new Actor()).width(edge).height(top);
        this.add(new Actor()).width(editor_width).height(top);
        this.add(new Actor()).width(edge).height(top);
        row();
        this.add(new Actor()).width(edge).height(editor_height);
        this.add(text_area).width(editor_width).height(editor_height);
        this.add(new Actor()).width(edge).height(editor_height);
        row();
        this.add(new Actor()).width(edge).height(edge);
        this.add(new Actor()).width(editor_width).height(edge);
        this.add(new Actor()).width(edge).height(edge);
        
        this.setBounds(0, 0, edge*2 + editor_width, edge + top + editor_height);
        Vector2 offset = new Vector2(Gdx.graphics.getWidth() * .25f / 2, Gdx.graphics.getHeight() * .25f / 2);
        this.setPosition(offset.x, offset.y);
        //this.setDebug(true);
    }
    
    private NinePatch getNinePatch(String fname) {
        final Texture t = new Texture(Gdx.files.internal(fname));
        return new NinePatch( new TextureRegion(t, 1, 1 , t.getWidth() - 2, t.getHeight() - 2), 2, 2, 2, 2);
    }

}
