package com.aeiaton.screens;

import com.aeiaton.Aeiaton;
import com.aeiaton.classes.Constants;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.*;
import com.aeiaton.ecs.components.CameraFollowComponent.FollowMode;
import com.aeiaton.ecs.systems.*;
import com.aeiaton.observer.TitleEvent;
import com.aeiaton.ui.TerminalWindow;
import com.aeiaton.util.ComputerTerminalData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TestLevel extends Level {

    public TestLevel(Aeiaton game) {
        super(game, "demo_maps\\Control Room [Kavrohs Mine].tmx");
        //super(game, "maps\\control_room_map.tmx");
        
        testECS();
        //sounds();
    }
    
    public void sounds() {
        Music m = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
        m.setLooping(true);
        m.play();
    }
    
    public void testECS() {
        Entity player = new Entity();
        Entity guard = new Entity();
        Entity computer1 = new Entity();
        Entity computer2 = new Entity();
        Entity door = new Entity();
        Entity ceiling_hack = new Entity();
        
        int mirror_count = 6;
        Entity[] mirrors = new Entity[mirror_count];
        for (int i = 0; i < mirror_count; ++i) mirrors[i] = new Entity();

        //120x165, 12 frames
        camera.position.x = 12;
        camera.position.y= 26;
        
        core.addSystem(new InputSystem());
        core.addSystem(new MovementSystem(world));
        core.addSystem(new RenderSystem(player, core));
        core.addSystem(new CameraSystem(this.camera));
        core.addSystem(new AnimationSystem());
        core.addSystem(new PlayerStateSystem());
        core.addSystem(new InteractableItemSystem(world));
        core.addSystem(new UISystem(stage));
        core.addSystem(new DoorSystem());
        core.addSystem(new GameStateSystem(game));
        core.addSystem(new EnemySystem(world, player));
      
        int count = 0;
        core.addSystem(new CombatSystem());
      
        player.addComponent(new PlayerInputComponent());
        player.addComponent(new MovementComponent(world, new Vector2(1200, 2670), new Vector2(20, 20), new Vector2(10, 10), .7f, 20f, "0:player", false, false, count++));
        //player.addComponent(new AnimationComponent(.05f, 15, 37, new String[] {"robot_walk_up", "robot_walk_down", "robot_walk_right", "robot_walk_right", "robot_walk_up", "robot_walk_down", "robot_walk_right", "robot_walk_right", "punch"}, new int[] {1, 1, 1, 1, 14, 12, 13, 13, 1}, new boolean[] {false, false, true, false, false, false, true, false, false}));
        player.addComponent(new AnimationComponent(.05f, 15, 37, 
                new String[] {"robot_back_walk", "robot_front_walk", "robot_side_walk", "robot_side_walk", "robot_back_walk", "robot_front_walk", "robot_side_walk", "robot_side_walk", "robot_energy_blast", "robot_front_energy_shot", "robot_side_energy_shot", "robot_side_energy_shot"}, 
                new int[] {1, 1, 1, 1, 12, 12, 12, 12, 12, 10, 10, 10}, 
                new boolean[] {false, false, true, false, false, false, true, false, false, false, true, false}, true));
        player.addComponent(new RenderComponent(15, 37));
        player.addComponent(new CameraFollowComponent(FollowMode.LERP));
        player.addComponent(new PlayerStateComponent());
        player.addComponent(new DirectionalHitboxComponent(world, new Vector2(20,20), new Vector2(1228, 1669), 20));
        
        //.addComponent(new MovementComponent(world, new Vector2(1300, 2680), new Vector2(10, 10), new Vector2(10, 10), 500, 0, "1:npc", true, false));
        guard.addComponent(new MovementComponent(world, new Vector2(1300, 2770), new Vector2(20, 20), new Vector2(10, 10), .7f, 20f, "0:player", false, false, count++));
        guard.addComponent(new AnimationComponent(.05f, 28, 40, new String[] {"purple_guard"}, new int[] {12}, new boolean[] {false}, true));
        guard.addComponent(new RenderComponent(28, 40));
        guard.addComponent(new EnemyComponent(10, 10));
        /*
        computer1.addComponent(new MovementComponent(world, new Vector2(1042, 1692), new Vector2(22, 18), new Vector2(22, 18), 0, 0, "2:T0", true, true, count++));
        computer1.addComponent(new RenderComponent(22, 18));
        computer1.addComponent(new InteractableComponent("computer", "computer_yellow"));
        
        computer2.addComponent(new MovementComponent(world, new Vector2(1386, 1692), new Vector2(22, 18), new Vector2(22, 18), 0, 0, "3:T1", true, true, count++));
        computer2.addComponent(new RenderComponent(22, 18));
        computer2.addComponent(new InteractableComponent("computer", "computer_yellow"));
        */
        door.addComponent(new RenderComponent(120, 165));
        door.addComponent(new AnimationComponent(0.5f, 120, 165, new String[] {"door", "door", "door_empty"}, new int[] {1, 12, 1}, new boolean[] {false, false, false}, false));
        door.addComponent(new MovementComponent(world, new Vector2(1155, 2730), new Vector2(0, 0), new Vector2(120, 165), 0, 0, "2:door:Test2Level", true, true, count++));
        door.addComponent(new DoorComponent());
        
        count++;
        RenderComponent ceiling_rc = new RenderComponent(120, 75);
        ceiling_rc.texture_region = new TextureRegion(new Texture(Gdx.files.internal("ceiling_hack.png")));
        ceiling_hack.addComponent(ceiling_rc);
        ceiling_hack.addComponent(new RawPositionComponent(1155, 2895, 1));
        
        Vector2[] positions = {new Vector2(1100, 2700), new Vector2(1200, 2700), new Vector2(1300, 2700), new Vector2(1100, 2600), new Vector2(1200, 2600), new Vector2(1300, 2600)};
        int[] directions = {Constants.BOTTOM_RIGHT, Constants.BOTTOM_RIGHT, Constants.BOTTOM_RIGHT, Constants.BOTTOM_RIGHT, Constants.BOTTOM_RIGHT, Constants.BOTTOM_RIGHT};
        ((CombatSystem) core.getSystem(CombatSystem.class)).setLaserGridPositions(positions, directions, 0, door.id);
        for (int i = 0; i < mirrors.length; ++i) {
            RenderComponent mirror_rc = new RenderComponent(22,18);
            mirror_rc.texture_region = new TextureRegion(new Texture(Gdx.files.internal("sprites//mirrorsprite.png")));
            MirrorComponent mc = new MirrorComponent(directions[i]);
            mc.index = i;
            mirrors[i].addComponent(mc);
            mirrors[i].addComponent(new InteractableComponent(null, null));
            mirrors[i].addComponent(mirror_rc);
            mirrors[i].addComponent(new MovementComponent(world, positions[i],
                                new Vector2(0,0), new Vector2(22, 18), 0, 0, "mirror", true, true, count++));
        }
        

        core.addEntity(player);
        core.addEntity(guard); 
        //core.addEntity(computer1); 
        //core.addEntity(computer2);
        core.addEntity(door);
        core.addEntity(ceiling_hack);
        for (Entity e : mirrors) core.addEntity(e);
    }
    
    @Override
    public void update(float d) {
        core.update(d);
        ui.update(d);
        world.step(1/60f, 1, 1);
        stage.act();
        map_renderer.setView(camera);
        debug_window.update();
    }
    
    @Override
    public void render(float d) {
        update(d);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map_renderer.render();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        core.render(game.batch, false);
        game.batch.end();
        game.batch.setProjectionMatrix(ui_camera.combined);
        game.batch.begin();
        ((UISystem) core.getSystem(UISystem.class)).draw(game.batch);
        //ui.draw(game.batch);
        game.batch.end();
        stage.setDebugAll(true);
        stage.draw();
        debug_renderer.render(world, camera.combined);
    }

}
