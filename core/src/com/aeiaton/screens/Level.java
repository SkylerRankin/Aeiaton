package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.observer.Observer;
import com.aeiaton.ui.UIManager;
import com.aeiaton.util.BodyContactListener;
import com.aeiaton.util.WorldBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level implements Screen {
    
    /** Global access to game **/
    public Aeiaton game;
    
    protected ECSCore core;
    
    /** Camera **/
    protected OrthographicCamera camera;
    protected OrthographicCamera ui_camera;
    protected Viewport viewport;
    
    /** Box2D **/
    protected World world;
    protected Box2DDebugRenderer debug_renderer;
    protected BodyContactListener contact_listener;
    
    /** Tiled **/
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer map_renderer;
    
    /** UI **/
    protected UIManager ui;
    protected Stage stage;
    
    /** File paths **/
    protected String map_path;
    
    /** Observer **/
    protected Observer observer;
    
    public Level(Aeiaton game, String m) {
        this.game = game;
        map_path = m;
        core = new ECSCore();
        world = new World(new Vector2(0, 0), false);
        ui = new UIManager();
        stage = new Stage();
        debug_renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Aeiaton.DEFAULT_WIDTH, Aeiaton.DEFAULT_HEIGHT);
        ui_camera = new OrthographicCamera(Aeiaton.DEFAULT_WIDTH, Aeiaton.DEFAULT_HEIGHT);
        camera.setToOrtho(false, Aeiaton.DEFAULT_WIDTH, Aeiaton.DEFAULT_HEIGHT);
        ui_camera.setToOrtho(false, Aeiaton.DEFAULT_WIDTH, Aeiaton.DEFAULT_HEIGHT);        
        viewport = new FitViewport(Aeiaton.DEFAULT_WIDTH / Aeiaton.PPM, Aeiaton.DEFAULT_HEIGHT / Aeiaton.PPM, camera);
        //stage.setViewport(viewport);
        map = WorldBuilder.loadMap(this, map_path);
        map_renderer = new OrthogonalTiledMapRenderer(map, 1 / Aeiaton.PPM);
        map_renderer.setView(camera);
        observer = new Observer(core.getSystemHandler());
        contact_listener = new BodyContactListener(observer);
        world.setContactListener(contact_listener);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {}
    
    @Override
    public void render(float delta) {}
    
    public void update(float d) {}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        //stage.setViewport(viewport);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
    
    /** Set Methods **/
    public void setMap(TiledMap m) { map = m; }
    
    /** Get Methods **/
    public World getWorld() { return world; }
    public String getMapPath() { return map_path; }

}
