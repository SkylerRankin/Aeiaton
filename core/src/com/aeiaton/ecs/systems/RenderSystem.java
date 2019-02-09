package com.aeiaton.ecs.systems;

import java.util.ArrayList;
import java.util.List;

import com.aeiaton.ecs.ECSCore;
import com.aeiaton.ecs.Entity;
import com.aeiaton.ecs.components.MovementComponent;
import com.aeiaton.ecs.components.ParallaxLayer;
import com.aeiaton.ecs.components.RawPositionComponent;
import com.aeiaton.ecs.components.RenderComponent;
import com.aeiaton.observer.Event;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends com.aeiaton.ecs.EntitySystem {

    private ECSCore core;
    private Entity player;
    private ArrayList<Entity> above;
    private ArrayList<Entity> below;
    private ShapeRenderer shape_renderer = new ShapeRenderer();
    
    public RenderSystem(Entity p, ECSCore core) {
        super(10, RenderComponent.class);
        this.core = core;
        player = p;
        above = new ArrayList<>();
        below = new ArrayList<>();
    }

    @Override
    public void update(float d) {
        
    }
    
    public void render(SpriteBatch batch, boolean back) {
        if (back) {
          drawBackgrounds(batch);
          return;
        }
        
        partition();
        
        for (Entity e : below) {
            if (e.hasComponent(MovementComponent.class)) {
                MovementComponent mc = e.get(MovementComponent.class);
                RenderComponent rc = e.get(RenderComponent.class);
                batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
            } else if (e.hasComponent(RawPositionComponent.class)) {
                RawPositionComponent rpc = e.get(RawPositionComponent.class);
                RenderComponent rc = e.get(RenderComponent.class);
                batch.draw(rc.texture_region, rpc.x, rpc.y, rc.width, rc.height);
            }
        }
        
        MovementComponent mc = player.get(MovementComponent.class);
        RenderComponent rc = player.get(RenderComponent.class);
        batch.draw(rc.texture_region, mc.body.getPosition().x - (mc.size.x / 2), mc.body.getPosition().y - (mc.size.y / 2), rc.width, rc.height);
        
        for (Entity e : above) {
            if (e.hasComponent(MovementComponent.class)) {
                MovementComponent mc1 = e.get(MovementComponent.class);
                RenderComponent rc1 = e.get(RenderComponent.class);
                batch.draw(rc1.texture_region, mc1.body.getPosition().x - (mc1.size.x / 2), mc1.body.getPosition().y - (mc1.size.y / 2), rc1.width, rc1.height);
            } else if (e.hasComponent(RawPositionComponent.class)) {
                RawPositionComponent rpc = e.get(RawPositionComponent.class);
                RenderComponent rc1 = e.get(RenderComponent.class);
                batch.draw(rc1.texture_region, rpc.x, rpc.y, rc1.width, rc1.height);
            }
            
        }
        if (((CombatSystem) core.getSystem(CombatSystem.class)) != null) {
            for (List<Vector2> line : ((CombatSystem) core.getSystem(CombatSystem.class)).lasergrid.getLines()) {
                drawLine(new Vector2(line.get(0).x, line.get(0).y), new Vector2(line.get(1).x, line.get(1).y), 6, Color.RED, ((CameraSystem) core.getSystem(CameraSystem.class)).getCameraProjectionMatrix(), 0.4f, 1);
            }
        }
    }
    
    private void drawBackgrounds(SpriteBatch batch) {
        List<Entity> sorted = ((ParallaxSystem) core.getSystem(ParallaxSystem.class)).sorted;
        for (Entity e : sorted) {
            RawPositionComponent rpc = e.get(RawPositionComponent.class);
            RenderComponent rc = e.get(RenderComponent.class);
            batch.draw(rc.texture_region, rpc.x, rpc.y, rc.width, rc.height);
        }
    }
    
    private void partition() {
        above.clear();
        below.clear();
        MovementComponent mc = player.get(MovementComponent.class);
        for (Entity e : entities) {
            if (e.hasComponent(MovementComponent.class)) {
                MovementComponent _mc = e.get(MovementComponent.class);
                if (_mc.body.getPosition().y < mc.body.getPosition().y) {
                    above.add(e);
                } else {
                    below.add(e);
                }
            } else if (e.hasComponent(RawPositionComponent.class)) {
                RawPositionComponent _rpc = e.get(RawPositionComponent.class);
                if (_rpc.above_player == -1) {
                    below.add(e);
                } else if (_rpc.above_player == 1) {
                    above.add(e);
                }
                else if (_rpc.y < mc.body.getPosition().y) {
                    above.add(e);
                } else {
                    below.add(e);
                }
            }
        }
    }
    
    
    public void drawLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(lineWidth);
        shape_renderer.setProjectionMatrix(projectionMatrix);
        shape_renderer.begin(ShapeRenderer.ShapeType.Line);
        shape_renderer.setColor(color);
        shape_renderer.line(start, end);
        shape_renderer.end();
        Gdx.gl.glLineWidth(1);
    }
    
    public void drawLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix, float segment, int color_offset) {
        boolean horizontal = Math.abs(start.y - end.y) < 0.01;
        float d = horizontal ? start.x - end.x : start.y - end.y;
        int segments = (int) ((int) d / segment);
        int dir = horizontal ? (start.x < end.x ? 1 : -1) : (start.y < end.y ? 1 : -1);

        Gdx.gl.glLineWidth(lineWidth);
        shape_renderer.setProjectionMatrix(projectionMatrix);
        shape_renderer.begin(ShapeRenderer.ShapeType.Line);
        
        Vector2 t_start;
        Vector2 t_end;
        float jitter;

        for (int i = 0; i < Math.abs(segments); ++i) {
            color.a = (float) (1 - Math.random()*0.5);
            jitter = (float) ((Math.random() - 0.5)*0.04);
            shape_renderer.setColor(color);
            
            if (horizontal) {
                t_start = new Vector2(start.x + dir*segment*i, start.y + jitter);
                t_end = new Vector2(start.x + dir*segment*(i+1), start.y + jitter);
            } else {
                t_start = new Vector2(start.x + jitter, start.y + dir*segment*i);
                t_end = new Vector2(start.x + jitter, start.y + dir*segment*(i+1));
            }
            if (i == Math.abs(segments)-1) {
                t_end = end;
                if (horizontal) t_end.y += jitter;
                else t_end.x += jitter;
            }
            
            shape_renderer.line(t_start, t_end);
        }
        
        shape_renderer.end();
        Gdx.gl.glLineWidth(1);
    }

    @Override
    public void notify(Event e) {
        // TODO Auto-generated method stub
    }

    @Override
    public short getID() {
        // TODO Auto-generated method stub
        return 0;
    }

}
