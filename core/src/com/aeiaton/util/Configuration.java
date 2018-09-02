package com.aeiaton.util;

import java.util.Dictionary;

public class Configuration {
    Dictionary<String, String> dictionary;
    
    public Configuration(Dictionary<String, String> dictionary) {
        this.dictionary = dictionary;
    }
    
    public int getInt(String key, int d) {
        String value = dictionary.get(key);
        if (value == null) return d;
        try {
            int i = Integer.parseInt(value);
            return i;
        } catch (Exception e) {
            return d;
        }
    }
    
}