package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
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
    
    /** Camera **/
    protected OrthographicCamera camera;
    protected Viewport viewport;
    
    /** Box2D **/
    protected World world;
    protected Box2DDebugRenderer debug_renderer;
    
    /** Tiled **/
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer map_renderer;
    
    /** UI **/
    protected Stage stage;
    
    /** File paths **/
    protected String map_path;
    
    public Level(Aeiaton game, String m) {
        this.game = game;
        map_path = m;
        world = new World(new Vector2(0, 0), false);
        debug_renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Aeiaton.DEFAULT_WIDTH, Aeiaton.DEFAULT_HEIGHT);
        camera.setToOrtho(false, Aeiaton.DEFAULT_WIDTH, Aeiaton.DEFAULT_HEIGHT);
        viewport = new FitViewport(Aeiaton.DEFAULT_WIDTH / Aeiaton.PPM, Aeiaton.DEFAULT_HEIGHT / Aeiaton.PPM, camera);
        map = WorldBuilder.loadMap(this, map_path);
        map_renderer = new OrthogonalTiledMapRenderer(map, 1 / Aeiaton.PPM);
        map_renderer.setView(camera);
    }

    @Override
    public void show() {}
    
    @Override
    public void render(float delta) {}
    
    public void update(float d) {}

    @Override
    public void resize(int width, int height) {}

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
