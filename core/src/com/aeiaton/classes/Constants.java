package com.aeiaton.classes;

public class Constants {
    
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    
    public static final int TOP_RIGHT = 4;
    public static final int BOTTOM_RIGHT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int TOP_LEFT = 7;

    public static int opposite(int x) {
        return (x + 2) % 4;
    }
    
    /**
     * Returns the components of a direction
     * @param A two-part direction
     * @return integer array of direction components, [horizontal, vertical]
     */
    public static int[] comps(int x) {
        if (x == Constants.TOP_RIGHT) return new int[] {Constants.RIGHT, Constants.UP};
        if (x == Constants.TOP_LEFT) return new int[] {Constants.LEFT, Constants.UP};
        if (x == Constants.BOTTOM_RIGHT) return new int[] {Constants.RIGHT, Constants.DOWN};
        if (x == Constants.BOTTOM_LEFT) return new int[] {Constants.LEFT, Constants.DOWN};
        System.err.println("Constants: comps() -> invalid input "+x);
        return new int[2];
    }
    
    /**
     * Check if a direction is horizontal
     * @param the direction integer
     * @return true if horizontal, false otherwise
     */
    public static boolean hor(int x) {
        return x == Constants.RIGHT || x == Constants.LEFT;
    }


}
