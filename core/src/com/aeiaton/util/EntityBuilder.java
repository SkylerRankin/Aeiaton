package com.aeiaton.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.aeiaton.ecs.Component;
import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;

public class EntityBuilder {
    
    public static String[] component_classes = {
            "MovementComponent",
            "RenderComponent",
            "AnimationComponent",
            "CameraFollowComponent",
            "PlayerStateComponent",
            "PlayerInputComponent"
    };
    
    public static void loadEntities(ECSCore core, String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                core.addEntity(buildEntity(line));
            }
        } catch (Exception e) {
            System.out.println("EntityBuilder: error reading file: "+path);
        }
    }
    
    private static Entity buildEntity(String data) {
        Entity e = new Entity();
        String[] comps = data.split("__");
        for (String comp : comps) {
            try {
                int index = Integer.parseInt(comp.substring(0, comp.indexOf(":")));
                //String[] values = comp.substring(comp.indexOf(":"), comp.length()).split(",");
                
                Class<?> _class = Class.forName(component_classes[index]);
                @SuppressWarnings("unchecked")
                Constructor<? extends Component> constructor = (Constructor<? extends Component>) _class.getConstructor(String.class);
                //Object[] args = new Object[values.length];
                
                String temp = comp.substring(comp.indexOf(":", comp.length()));
                Object object = constructor.newInstance(new Object[] { temp });
                
                Component c = (Component) constructor.newInstance(object);
                e.addComponent(c);
            } catch (Exception err) {
                System.out.println("EntityBuilder: error building entity: component="+comp+", "+err.getMessage() + " - " + err.toString());
            }
            
        }
        return e;
    }

}
