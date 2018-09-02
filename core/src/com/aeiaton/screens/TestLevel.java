package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.*;
import com.aeiaton.ecs.components.CameraFollowComponent.FollowMode;
import com.aeiaton.ecs.systems.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class TestLevel extends Level {

    protected ECSCore core;
    
    public TestLevel(Aeiaton game) {
        super(game, "maps\\control_room_map.tmx");
        core = new ECSCore();
        testECS();
    }
    
    public void testECS() {
        Entity player = new Entity();
        
        core.addSystem(new InputSystem());
        core.addSystem(new MovementSystem(world));
        core.addSystem(new RenderSystem());
        core.addSystem(new CameraSystem(this.camera));
        core.addSystem(new AnimationSystem());
        core.addSystem(new PlayerStateSystem());
        core.addSystem(new InteractableItemSystem(world, player));
        
        player.addComponent(new PlayerInputComponent());
        player.addComponent(new MovementComponent(world, new Vector2(345, 300), new Vector2(20, 20), new Vector2(20, 10), 10f, "player"));
        player.addComponent(new AnimationComponent(.1f, 20, 35, new String[] {"robot_idle_up", "robot_idle_down", "robot_idle_left", "robot_idle_left", "robot_walk_up", "robot_walk_down", "robot_walk_left", "robot_walk_left"}, new int[] {1, 1, 1, 1, 4, 4, 4, 4}, new boolean[] {false, false, false, true, false, false, false, true}));
        player.addComponent(new RenderComponent(20, 35));
        player.addComponent(new CameraFollowComponent(FollowMode.EXACT));
        player.addComponent(new PlayerStateComponent());
        
        core.addEntity(player);
    }
    
    @Override
    public void update(float d) {
        core.update(d);
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
        debug_renderer.render(world, camera.combined);
    }

}
