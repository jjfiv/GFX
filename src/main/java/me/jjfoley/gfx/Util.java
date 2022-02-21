package me.jjfoley.gfx;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Designed F2019 for S2019.
 * 
 * This class contains methods that are annoying to write but are very useful!
 */
public class Util {
    private Util() {
        throw new UnsupportedOperationException();
    }

    public static Color getRandomColor(double brightness, Random rand) {
        float brightf = (float) brightness;
        return Color.getHSBColor(rand.nextFloat(), brightf, brightf);
    }

    public static <T> List<T> makeList() {
        return new ArrayList<T>();
    }

    public static <T> Set<T> makeSet() {
        return new HashSet<T>();
    }

    public static <K, V> Map<K, V> makeMap() {
        return new HashMap<K, V>();
    }
}