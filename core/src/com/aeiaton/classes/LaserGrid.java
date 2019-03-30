package com.aeiaton.classes;

import java.util.ArrayList;
import java.util.List;

import com.aeiaton.Aeiaton;
import com.aeiaton.observer.LaserActivatedEvent;
import com.aeiaton.observer.Observer;
import com.badlogic.gdx.math.Vector2;

public class LaserGrid {
    
    private static Vector2[] points;
    private static List<Vector2> grid;
    private static int[] dir;
    private static Vector2 end;
    public float percent;
    private float total_length;
    private static int doorID;
    
    private Vector2 source;
    private int source_direction;
    
    private boolean animated = false;
    
    private List<Vector2> computed_path;
    private static Observer o;
    
    public LaserGrid(Vector2[] p, int[] d, int e, Observer o, int door) {
        points = p;
        dir = d;
        grid = compute(points[0], dir[0]);
        end = points[e];
        this.o = o;
        doorID = door;
    }
    
    public int totalLength() {
        int total_length = 0;
        for (int i = 0; i < grid.size()-1; ++i) {
            total_length += Math.sqrt(Math.pow(grid.get(i).x - grid.get(i+1).x, 2) + Math.pow(grid.get(i).y - grid.get(i+1).y, 2));
        }
        return total_length;
    }
    
    /**
     * Generates a list of lines used to draw the laser in motion
     * @return A list of lists of vector2s. Each sub list is a tuple of the start and end of a segment of the laser
     */
    public List<List<Vector2>> getLines() {
        
        if (grid == null) return null;
        
        //offset to move the line to the center of a sprite
        float offset = 10f / Aeiaton.PPM;
        
        if (!animated) {
            List<List<Vector2>> lines = new ArrayList<>();
            for (int i = 0; i < grid.size()-1; ++i) {
                List<Vector2> line = new ArrayList<>();
                line.add(new Vector2(grid.get(i).x+offset, grid.get(i).y+offset));
                line.add(new Vector2(grid.get(i+1).x+offset, grid.get(i+1).y+offset));
                lines.add(line);
            }
            return lines;
        }
        
        float remaining = percent*totalLength();
        List<List<Vector2>> lines = new ArrayList<>();
        
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
                    line.add(new Vector2(grid.get(i).x-(remaining + Math.abs(d)), grid.get(i).y));
                }
                remaining = 0;
            }
            lines.add(line);
            if (remaining <= 0) break;
        }
                
        //adjust lines to go to center of sprite rather than bottom-left corner
        for (List<Vector2> line : lines) {
            line.set(0, new Vector2(line.get(0).x+offset, line.get(0).y+offset));
            line.set(1, new Vector2(line.get(1).x+offset, line.get(1).y+offset)); 
        }
        
        return lines;
    }
    
    public static int willCollide(Vector2 v1, int d) {
        float e = 0.25f;
        Vector2 v;
        int p = -1;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            v = points[i];
            if (v1.equals(v)) continue;
            if (Math.abs(v.x - v1.x) < e) {
                int dt = (int) Math.abs(v.x - v1.x);
                if (d == Constants.UP && v.y > v1.y && distance > dt) {
                    p = i;
                    distance = dt;
                }
                else if (d == Constants.DOWN && v.y < v1.y && distance > dt) {
                    p = i;
                    distance = dt;
                }
            } else if (Math.abs(v.y - v1.y) < e) {
                int dt = (int) Math.abs(v.y - v1.y);
                if (d == Constants.LEFT && v.x < v1.x && distance > dt) {
                    p = i;
                    distance = dt;
                }
                else if (d == Constants.RIGHT && v.x > v1.x && distance > dt) {
                    p = i;
                    distance = dt;
                }
            }
        }
        if (p == -1) return p;
        //check if direction is correct
        int[] c = Constants.comps(dir[p]);
        if (Constants.hor(d)) {
            if (Constants.opposite(c[0]) == d) return p;
        } else {
            if (Constants.opposite(c[1]) == d) return p;
        }
        
        return -1;
    }
    
    public static List<Vector2> compute(Vector2 v1, int dir1) {
        int i = willCollide(v1, dir1);
        ArrayList<Vector2> l = new ArrayList<Vector2>();
        l.add(v1);
        Vector2 v;
        //direction of the travelling laser
        int prev_d = dir1;
        //direction of the outputted laser
        int d;
        int max = 6;
        while (i != -1 && max > 0) {
            //get next point that is collided with
            v = points[i];
            
            // check if v is same point as end. If it is, send LaserActivatedEvent
            if (v.equals(end)) {
                o.recieve (new LaserActivatedEvent(doorID));
            }
            //direction is a corner: convert to up/down/right/left
            int[] c = Constants.comps(dir[i]);
            if (Constants.hor(prev_d)) {
                //hit the laser horizontally
                d = c[1];
            } else {
                //hit the laser vertically
                d = c[0];
            }
            prev_d = d;
            l.add(v);
            i = willCollide(v, d);
            max--;
        }
        return l;
    }
    
    public void setDirection(int index, int value) {
        if (index < LaserGrid.dir.length) {
            LaserGrid.dir[index] = value;
            //System.out.println("LaserGrid: mirror "+index+" updated to "+value);
            grid = compute(points[0], dir[0]);
        }
    }
    
    public boolean setSource(Vector2 src, int src_dir) {
        source = src;
        source_direction = src_dir;
        //System.out.println("LaserGrid: source updated: "+source+", "+src_dir);
        grid = compute(source, source_direction);
        return grid.size() > 1;
    }
    
}
