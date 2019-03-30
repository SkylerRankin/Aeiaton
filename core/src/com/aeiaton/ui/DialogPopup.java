package com.aeiaton.ui;

import com.aeiaton.Aeiaton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * 
 * A generic class for displaying a text window on the lower third of the screen.
 * The constructor takes in an instance of the stage so that the "X" button
 * can register the event to remove itself when clicked.
 */
public class DialogPopup extends Table {

    private Stage stage;
    private Skin skin;
    private String title;
    private String text;
    private BitmapFont font_Prompt;
    private BitmapFont font_Source_Sans_Pro_50;
    private BitmapFont font_Source_Sans_Pro_25;
    
    /**
     * @param title     The large text displayed as the title
     * @param text      The content of the dialog pop up
     * @param s         The Stage object to display the window on
     */
    public DialogPopup(String title, String text, Stage s) {
        skin = new Skin(Gdx.files.internal("skins\\menu_skin.json"));
        this.title = title;
        this.text = text;
        stage = s;
        loadFonts();
        init();
    }
    
    private void init() {
        
        int width = Gdx.graphics.getWidth()/2;
        int height = Gdx.graphics.getHeight()/2;
        
        //setFillParent(true);
        setBounds(Gdx.graphics.getWidth()/2 - width/2f, Gdx.graphics.getHeight()/3f - height/2, width, height);
        setVisible(true);
        setDebug(Aeiaton.debug);
        
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(1/255, 1/255, 1/255, 1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        setBackground(textureRegionDrawableBg);
        
        LabelStyle label_style = new LabelStyle();
        label_style.font = font_Source_Sans_Pro_50;
        
        Label title_label = new Label(title, label_style);
        
        label_style.font = font_Source_Sans_Pro_25;
        Label text_label = new Label(text, label_style);
        
        TextButtonStyle style = new TextButtonStyle();
        style.font = font_Prompt;
        style.up = skin.getDrawable("default-round");
        style.down = skin.getDrawable("default-round-down");
        
        TextButton close_button = new TextButton("X", style);
        //new_game_button.setPosition(0, 0);
        close_button.addListener(new DialogCloseListener(this));
        
        add(title_label).width(width-50).pad(10).left();
        add(close_button).width(50).height(50).pad(10);
        row();
        add(text_label).width(width).pad(10);
        pack();
    }
    
    private class DialogCloseListener extends ClickListener {
        private DialogPopup dp;
        public DialogCloseListener(DialogPopup d) { dp = d; }
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dp.remove();
        }
    }
    
    /**
     * Display the window on the stage
     */
    public void show() {
        stage.addActor(this);
    }
    
    /**
     * Generate the fonts in various sizes and save references to them for styling.
     */
    private void loadFonts() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\Prompt\\Prompt-ExtraBold.ttf"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 50;
        font_Prompt = gen.generateFont(param);
        font_Prompt.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\Source_Sans_Pro\\SourceSansPro-ExtraLight.ttf"));
        param = new FreeTypeFontParameter();
        param.size = 50;
        font_Source_Sans_Pro_50 = gen.generateFont(param);
        font_Source_Sans_Pro_50.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        param.size = 25;
        font_Source_Sans_Pro_25 = gen.generateFont(param);
        font_Source_Sans_Pro_25.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        gen.dispose();
        //skin.add("font_Prompt", font_Prompt, BitmapFont.class);
    }
    
}
