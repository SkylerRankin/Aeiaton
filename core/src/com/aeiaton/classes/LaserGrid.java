package com.aeiaton.classes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class LaserGrid {
    
    private static Vector2[] points;
    private static int[] dir;
    private int end;
    
    public LaserGrid(Vector2[] p, int[] d, int e) {
        points = p;
        dir = d;
        end = e;
    }
    
    public List<List<Vector2>> getLines() {
        List<List<Vector2>> lines = new ArrayList<>();
        for (int i = 0; i < points.length-1; ++i) {
            List<Vector2> line = new ArrayList<>();
            line.add(points[i]);
            line.add(points[i+1]);
            lines.add(line);
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
