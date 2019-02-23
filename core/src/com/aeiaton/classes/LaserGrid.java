package com.aeiaton.classes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class LaserGrid {
    
    private static Vector2[] points;
    private static int[] dir;
    private int end;
    private float percent;
    private float total_length;
    
    public LaserGrid(Vector2[] p, int[] d, int e) {
        points = p;
        dir = d;
        end = e;
    }
    
    private int totalLength() {
        int total_length = 0;
        for (int i = 0; i < points.length-1; ++i) {
            total_length += Math.sqrt(Math.pow(points[i].x - points[i+1].x, 2) + Math.pow(points[i].y - points[i+1].y, 2));
        }
        return total_length;
    }
    
    public List<List<Vector2>> getLines() {
        
        //change to grid later
        float remaining = percent*totalLength();
        List<List<Vector2>> lines = new ArrayList<>();
        
        //change points to grid
        for (int i = 0; i < points.length-1; ++i) {
            boolean vert = points[i].x == points[i+1].x;
            float d = vert ? points[i].y - points[i+1].y : points[i].x - points[i+1].x;
            List<Vector2> line = new ArrayList<>();
            remaining -= Math.abs(d);
            if (remaining >= 0) {
                line.add(points[i]);
                line.add(points[i+1]);
            } else {
                line.add(points[i]);
                if (vert) {
                    line.add(new Vector2(points[i].x, points[i].y+remaining+Math.abs(d)));
                } else {
                    line.add(new Vector2(points[i].x+remaining+Math.abs(d), points[i].y));
                }
                remaining = 0;
            }
            lines.add(line);
            if (remaining <= 0) break;
        }
        return lines;
    }
    
    public static int willCollide(Vector2 v1, int dir) {
        Vector2 v;
        for (int i = 0; i < points.length; i++) {
            v = points[i];
            if (Math.abs(v.x - v1.x) < 0.1 && (v.y - v1.y)*dir > 0) {
                return i;
            } else if (Math.abs(v.y - v1.y) < 0.1 && (v.x - v1.x)*dir > 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static List<Vector2> compute (Vector2 v1, int dir1) {
        int i = willCollide(v1, dir1);
        ArrayList<Vector2> l = new ArrayList<Vector2>();
        Vector2 v;
        int d;
        while (i != -1) {
            v = points[i];
            d = dir[i];
            l.add(v);
            i = willCollide(v, d);
        }
        return l;
    }
    
}
