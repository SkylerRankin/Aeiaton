package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
import com.aeiaton.util.ConfigParser;
import com.aeiaton.util.Configuration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TitleScreen implements Screen {
    
    private Aeiaton game;
    private Stage stage;
    private Skin skin;
    private BitmapFont font_Prompt;
    private BitmapFont font_Source_Sans_Pro;
    
    private Configuration config;
    
    public TitleScreen(Aeiaton game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins\\menu_skin.json"));
        
        config = ConfigParser.parse("menu.config", "title");
        
        loadFonts();
        loadUI();
        Gdx.input.setInputProcessor(stage);
        
    }
    
    private void loadUI() {
      //table to hold ui elements
        Table table = new Table();
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setVisible(true);
        table.setDebug(Aeiaton.debug);
        
        LabelStyle label_style = new LabelStyle();
        label_style.font = font_Source_Sans_Pro;
        
        Label version = new Label("v"+Aeiaton.version, label_style);
        stage.addActor(version);
        
        TextButtonStyle style = new TextButtonStyle();
        style.font = font_Prompt;
        style.up = skin.getDrawable("default-round");
        style.down = skin.getDrawable("default-round-down");
        
        TextButton new_game_button = new TextButton("NEW GAME", style);
        //new_game_button.setPosition(0, 0);
        new_game_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TitleScreen: New Game Button");
                game.setScreen(new TestLevel(game));
            }
        });
        
        TextButton load_game_button = new TextButton("LOAD GAME", style);
        //new_game_button.setPosition(0, 0);
        load_game_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TitleScreen: Load Game Button");
            }
        });
        
        TextButton settings_button = new TextButton("SETTINGS", style);
        //settings_button.setPosition(0, 0);
        settings_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TitleScreen: Settings Button");
            }
        });
        
        TextButton quit_button = new TextButton("QUIT", style);
        //quit_button.setPosition(0, 0);
        quit_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TitleScreen: Quit Button");
            }
        });
        
        
        table.add(new_game_button).width(config.getInt("width", 10)).height(config.getInt("height", 10)).pad(config.getInt("pad", 10));
        table.row();
        table.add(load_game_button).width(config.getInt("width", 10)).height(config.getInt("height", 10)).pad(config.getInt("pad", 10));
        table.row();
        table.add(settings_button).width(config.getInt("width", 10)).height(config.getInt("height", 10)).pad(config.getInt("pad", 10));
        table.row();
        table.add(quit_button).width(config.getInt("width", 10)).height(config.getInt("height", 10)).pad(config.getInt("pad", 10));
        
        stage.addActor(table);
    }
    
    private void loadFonts() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\Prompt\\Prompt-ExtraBold.ttf"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = config.getInt("font-size", 10);
        font_Prompt = gen.generateFont(param);
        font_Prompt.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\Source_Sans_Pro\\SourceSansPro-ExtraLight.ttf"));
        param = new FreeTypeFontParameter();
        param.size = config.getInt("font-size-version", 10);
        font_Source_Sans_Pro = gen.generateFont(param);
        font_Source_Sans_Pro.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        gen.dispose();
        skin.add("font_Prompt", font_Prompt, BitmapFont.class);
    }

    @Override
    public void show() {
        
    }
    
    public void update(float d) {
        stage.act();
    }

    @Override
    public void render(float delta) {
        update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }

}
