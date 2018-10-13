package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.*;
import com.aeiaton.ecs.components.CameraFollowComponent.FollowMode;
import com.aeiaton.ecs.systems.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TestLevel extends Level {

    protected ECSCore core;
    
    public TestLevel(Aeiaton game) {
        super(game, "demo_maps\\control_room.tmx");
        //super(game, "maps\\control_room_map.tmx");
        core = new ECSCore();
        testECS();
        sounds();
    }
    
    public void sounds() {
        //Music m = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
        //m.setLooping(true);
        //m.play();
    }
    
    public void testECS() {
        Entity player = new Entity();
        Entity guard = new Entity();
        
        core.addSystem(new InputSystem());
        core.addSystem(new MovementSystem(world));
        core.addSystem(new RenderSystem());
        core.addSystem(new CameraSystem(this.camera));
        core.addSystem(new AnimationSystem());
        core.addSystem(new PlayerStateSystem());
        core.addSystem(new InteractableItemSystem(world, player));
        
        player.addComponent(new PlayerInputComponent());
        player.addComponent(new MovementComponent(world, new Vector2(1228, 1669), new Vector2(20, 20), new Vector2(10, 10), 10f, 500f, "player"));
        player.addComponent(new AnimationComponent(.05f, 15, 37, new String[] {"robot_walk_up", "robot_walk_down", "robot_walk_right", "robot_walk_right", "robot_walk_up", "robot_walk_down", "robot_walk_right", "robot_walk_right", "punch"}, new int[] {1, 1, 1, 1, 14, 12, 13, 13, 1}, new boolean[] {false, false, true, false, false, false, true, false, false}));
        player.addComponent(new RenderComponent(15, 37));
        player.addComponent(new CameraFollowComponent(FollowMode.LERP));
        player.addComponent(new PlayerStateComponent());
        player.addComponent(new DirectionalHitboxComponent(world, new Vector2(20,20), new Vector2(1228, 1669), 20));
        
        guard.addComponent(new MovementComponent(world, new Vector2(1228, 1669), new Vector2(10, 10), new Vector2(10, 10), 0, 0, "npc"));
        guard.addComponent(new AnimationComponent(.05f, 28, 40, new String[] {"purple_guard"}, new int[] {12}, new boolean[] {false}));
        guard.addComponent(new RenderComponent(28, 40));
        
        core.addEntity(player);
        core.addEntity(guard);
    }
    
    @Override
    public void update(float d) {
        core.update(d);
        ui.update(d);
        world.step(1/60f, 1, 1);
        map_renderer.setView(camera);
    }
    
    @Override
    public void render(float d) {
        update(d);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map_renderer.render();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        core.render(game.batch);
        game.batch.end();
        game.batch.setProjectionMatrix(ui_camera.combined);
        game.batch.begin();
        ui.draw(game.batch);
        game.batch.end();
        debug_renderer.render(world, camera.combined);
    }

}
