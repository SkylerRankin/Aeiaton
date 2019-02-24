package com.aeiaton.classes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class LaserGrid {
    
    private static Vector2[] points;
    private static List<Vector2> grid;
    private static int[] dir;
    private int end;
    public float percent;
    private float total_length;
    
    public LaserGrid(Vector2[] p, int[] d, int e) {
        points = p;
        System.out.println(points[0]);
        dir = d;
        grid = compute(points[0], dir[0]);
        end = e;
    }
    
    public int totalLength() {
        int total_length = 0;
        for (int i = 0; i < grid.size()-1; ++i) {
            total_length += Math.sqrt(Math.pow(grid.get(i).x - grid.get(i+1).x, 2) + Math.pow(grid.get(i).y - grid.get(i+1).y, 2));
        }
        return total_length;
    }
    
    public List<List<Vector2>> getLines() {
        
        if (grid == null) return null;
        
        float remaining = percent*totalLength();
        List<List<Vector2>> lines = new ArrayList<>();
        
        List<Vector2> l = new ArrayList<>();
        List<Vector2> g  = LaserGrid.compute(points[0], dir[0]);

        l.add(points[0]);
        l.add(g.get(0));
        lines.add(l);
        
        for (int i = 0; i < grid.size()-1; ++i) {
            boolean vert = grid.get(i).x == grid.get(i+1).x;
            float d = vert ? grid.get(i).y - grid.get(i+1).y : grid.get(i).x - grid.get(i+1).x;
            List<Vector2> line = new ArrayList<>();
            remaining -= Math.abs(d);
            if (remaining >= 0) {
                line.add(grid.get(i));
                line.add(grid.get(i+1));
            } else {
                line.add(grid.get(i));
                if (vert) {
                    line.add(new Vector2(grid.get(i).x, grid.get(i).y+remaining+Math.abs(d)));
                } else {
                    line.add(new Vector2(grid.get(i).x+remaining+Math.abs(d), grid.get(i).y));
                }
                remaining = 0;
            }
            lines.add(line);
            if (remaining <= 0) break;
        }
        return lines;
    }
    
    
    public static int willCollide(Vector2 v1, int dir) {
        float e = 0.01f;
        Vector2 v;
        for (int i = 0; i < points.length; i++) {
            v = points[i];
            if (v1.equals(v)) continue;
            if (Math.abs(v.x - v1.x) < e) {
                if (dir == Constants.UP && v.y > v1.y) return i;
                else if (dir == Constants.DOWN && v.y < v1.y) return i;
            } else if (Math.abs(v.y - v1.y) < e) {
                if (dir == Constants.LEFT && v.x < v1.x) return i;
                else if (dir == Constants.RIGHT && v.x > v1.x) return i;
            }
        }
        return -1;
    }
    
    public static List<Vector2> compute(Vector2 v1, int dir1) {
        int i = willCollide(v1, dir1);
        ArrayList<Vector2> l = new ArrayList<Vector2>();
        l.add(v1);
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
    
    public void setDirection(int index, int value) {
        if (index < LaserGrid.dir.length) {
            LaserGrid.dir[index] = value;
            System.out.println("LaserGrid: mirror "+index+" updated to "+value);
            grid = compute(points[0], dir[0]);
        }
    }
    
}
