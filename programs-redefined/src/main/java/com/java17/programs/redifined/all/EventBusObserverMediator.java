package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.List;

public class EventBusObserverMediator {
}
interface EventListener{ void onEvent(String msg); }
class EventBus{
    private List<EventListener> listeners = new ArrayList<>();
    public void register(EventListener l){ listeners.add(l); }
    public void publish(String msg){ listeners.forEach(l->l.onEvent(msg)); }
}