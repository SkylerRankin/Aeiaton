package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.AnimationComponent;
import com.aeiaton.ecs.components.CameraFollowComponent;
import com.aeiaton.ecs.components.DirectionalHitboxComponent;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.PlayerInputComponent;
import com.aeiaton.ecs.components.PlayerStateComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.ecs.components.CameraFollowComponent.FollowMode;
import com.aeiaton.ecs.systems.AnimationSystem;
import com.aeiaton.ecs.systems.CameraSystem;
import com.aeiaton.ecs.systems.InputSystem;
import com.aeiaton.ecs.systems.InteractableItemSystem;
import com.aeiaton.ecs.systems.MovementSystem;
import com.aeiaton.ecs.systems.PlayerStateSystem;
import com.aeiaton.ecs.systems.RenderSystem;
import com.aeiaton.ecs.systems.UISystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class Test2Level extends Level {

    public Test2Level(Aeiaton game) {
        super(game, "demo_maps\\control_room.tmx");
        testECS();
    }
    
    private void testECS() {
        Entity player = new Entity();
        
        core.addSystem(new InputSystem());
        core.addSystem(new MovementSystem(world));
        core.addSystem(new RenderSystem(player));
        core.addSystem(new CameraSystem(this.camera));
        core.addSystem(new AnimationSystem());
        core.addSystem(new PlayerStateSystem());
        core.addSystem(new InteractableItemSystem(world));
        core.addSystem(new UISystem(stage));
        
        player.addComponent(new PlayerInputComponent());
        player.addComponent(new MovementComponent(world, new Vector2(1228, 1669), new Vector2(20, 20), new Vector2(10, 10), 10f, 500f, "0:player", false, false));
        //player.addComponent(new AnimationComponent(.05f, 15, 37, new String[] {"robot_walk_up", "robot_walk_down", "robot_walk_right", "robot_walk_right", "robot_walk_up", "robot_walk_down", "robot_walk_right", "robot_walk_right", "punch"}, new int[] {1, 1, 1, 1, 14, 12, 13, 13, 1}, new boolean[] {false, false, true, false, false, false, true, false, false}));
        player.addComponent(new AnimationComponent(.05f, 15, 37, new String[] {"robot_back_walk", "robot_front_walk", "robot_side_walk", "robot_side_walk", "robot_back_walk", "robot_front_walk", "robot_side_walk", "robot_side_walk", "robot_energy_blast"}, new int[] {1, 1, 1, 1, 12, 12, 12, 12, 12}, new boolean[] {false, false, true, false, false, false, true, false, false}, true));
        player.addComponent(new RenderComponent(15, 37));
        player.addComponent(new CameraFollowComponent(FollowMode.LERP));
        player.addComponent(new PlayerStateComponent());
        player.addComponent(new DirectionalHitboxComponent(world, new Vector2(20,20), new Vector2(1228, 1669), 20));
        
        core.addEntity(player);
    }
    
    @Override
    public void update(float d) {
        core.update(d);
        ui.update(d);
        world.step(1/60f, 1, 1);
        stage.act();
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
        ((UISystem) core.getSystem(UISystem.class)).draw(game.batch);
        //ui.draw(game.batch);
        game.batch.end();
        //stage.setDebugAll(true);
        stage.draw();
        //debug_renderer.render(world, camera.combined);
    }

}
