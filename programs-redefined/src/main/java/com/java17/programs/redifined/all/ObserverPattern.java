package com.java17.programs.redifined.all;

import java.util.*;

public class ObserverPattern {
    // Observer Pattern (Notify multiple services of an event)

}

interface Observer {
    void update(String event);
}

class EmailService implements Observer {
    public void update(String event) {
        System.out.println("Email: " + event);
    }
}

class SMSService implements Observer {
    public void update(String event) {
        System.out.println("SMS: " + event);
    }
}

class OrderPlacedEvent {
    private List<Observer> observers = new ArrayList<>();

    public void register(Observer obs) {
        observers.add(obs);
    }

    public void notifyAllObservers(String event) {
        observers.forEach(o -> o.update(event));
    }
}
