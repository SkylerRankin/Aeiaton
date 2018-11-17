package com.aeiaton.ecs.systems;

import java.lang.reflect.Constructor;

import com.aeiaton.Aeiaton;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.observer.ChangeLevelEvent;
import com.aeiaton.observer.Event;
import com.aeiaton.screens.Level;

public class GameStateSystem extends EntitySystem {
    
    private Aeiaton game;

    public GameStateSystem(Aeiaton g) {
        super(12);
        game = g;
    }

    @Override
    public void notify(Event e) {
        switch (e.getName()) {
        case "ChangeLevelEvent":
            try {
                ChangeLevelEvent cle = (ChangeLevelEvent) e;
                Class<?> c = Class.forName(cle.level);
                Constructor<?> constructor = c.getConstructor(Aeiaton.class);
                Level level = (Level) constructor.newInstance(game);
                System.out.println("GameStateSystem: changing level to "+level.getClass());
                game.setScreen(level);
            } catch (Exception err) {
                System.out.println("Error changing levels: "+err.getLocalizedMessage());
                err.printStackTrace();
            }
            
            break;
        }
    }

    @Override
    public short getID() {
        return EntitySystem.GameStateSystem;
    }

    @Override
    public void update(float d) {}

}
