package com.aeiaton.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.aeiaton.classes.Constants;
import com.aeiaton.classes.LaserGrid;
import com.badlogic.gdx.math.Vector2;

public class LaserComputeTest {
    
    @Test
    public void testCompute() {
        
        Vector2[] p = {new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1)};
        int[] d = {Constants.BOTTOM_LEFT, Constants.TOP_LEFT, Constants.BOTTOM_LEFT};
        LaserGrid lg = new LaserGrid(p, d, 0);
        
        assertEquals(1, LaserGrid.willCollide(p[0], Constants.RIGHT));
        assertEquals(2, LaserGrid.willCollide(p[1], Constants.UP));
        assertEquals(-1, LaserGrid.willCollide(p[2], Constants.UP));

        List<Vector2> v = LaserGrid.compute(p[0], Constants.RIGHT);
        for (Vector2 u : v) System.out.println(u.x+", "+u.y);
        assertEquals(3, v.size());
        
        d[1] = Constants.BOTTOM_LEFT;

    }

}
