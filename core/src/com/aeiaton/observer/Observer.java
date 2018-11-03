package com.aeiaton.observer;

import java.util.ArrayDeque;
import java.util.Queue;
import com.aeiaton.ecs.EntitySystem;
import com.aeiaton.ecs.SystemHandler;


public class Observer {
    
    private static final int max_messages = 10;
    private SystemHandler system_handler;
    private Queue<Event> queue;
    private boolean debug = true;
    
    public Observer(SystemHandler sh) {
        system_handler = sh;
        queue = new ArrayDeque<Event>();
    }
    
    public void process() {
        int count = 0;
        while (count < max_messages && !queue.isEmpty()) {
            Event e = queue.poll();
            dispatch(e);
            count++;
        }
    }
    
    public void recieve(Event e) {
        queue.add(e);
        if (debug) System.out.println("Observer: Event Recieved: "+e.getMessage());
        process();
    }
    
    private void dispatch(Event e) {
        for (EntitySystem system : system_handler.getAll()) {
            if ((e.getID() & system.getID()) > 0) {
                if (debug) System.out.println("Observer: Dispatch "+e.getMessage());
                system.notify(e);
            }
        }
            
    }

}
