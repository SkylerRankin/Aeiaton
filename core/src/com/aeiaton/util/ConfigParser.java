package com.aeiaton.util;

import java.util.Dictionary;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ConfigParser {
    
    public static Configuration parse(String path, String name) {
        FileHandle handle = Gdx.files.internal(path);
        String raw;
        try {
            raw = handle.readString();
        } catch (GdxRuntimeException e) {
            e.printStackTrace();
            return null;
        }
        
        Dictionary<String, String> dictionary = new Hashtable<String, String>();
        
        String[] lines = raw.split(System.lineSeparator());
        boolean in_section = false;
        
        for (String line : lines) {
            
            if (line.length() < 1)
                continue;
            
            if (line.substring(0, 1).equals("#")) {
                in_section = line.equals("#"+name);
                continue;
            }
            
            if (in_section) {
                int e = line.indexOf("=");
                if (e != -1) {
                    dictionary.put(line.substring(0, e), line.substring(e+1, line.length()));
                } else {
                    System.err.println("ConfigParser: Invalid config line \""+line+"\"");
                }
            }
            
        }
        return new Configuration(dictionary);
    }
    
    

}
