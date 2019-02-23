package com.aeiaton.classes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class LaserGrid {
    
    private Vector2[] points;
    private int[] dir;
    private int end;
    public float percent = 0.0f;
    public float total_length = 0;
    
    public LaserGrid(Vector2[] p, int[] d, int e) {
        points = p;
        dir = d;
        end = e;
        for (int i = 0; i < points.length-1; ++i) {
            total_length += Math.sqrt(Math.pow(p[i].x - p[i+1].x, 2) + Math.pow(p[i].x - p[i+1].x, 2));
        }
    }
    
    public List<List<Vector2>> getLines() {
        int sections = (int)((points.length-1)*percent) + 1;
        List<List<Vector2>> lines = new ArrayList<>();
        for (int i = 0; i < sections-1; ++i) {
            List<Vector2> line = new ArrayList<>();
            line.add(points[i]);
            line.add(points[i+1]);
            lines.add(line);
        }
        return lines;
    }
    
    public boolean willCollide(float x, float y, int dir) {
        for (Vector2 v : points) {
            if (Math.abs(v.x - x) < 0.1 && (v.y - y)*dir > 0) {
                return true;
            } else if (Math.abs(v.y - y) < 0.1 && (v.x - x)*dir > 0) {
                return true;
            }
        }
        return false;
    }

}
