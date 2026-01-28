package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceObserverPattern {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();
        Customer c1 = new Customer("Alice");
        Customer c2 = new Customer("Bob");
        service.subscribe(c1); service.subscribe(c2);
        service.notifyAll("Flash Sale 50% OFF!");
    }
}
interface ObserverOP { void update(String msg); }
class Customer implements ObserverOP {
    private String name;
    public Customer(String name) { this.name = name; }
    public void update(String msg) { System.out.println(name + " received: " + msg); }
}

class NotificationService {
    private List<ObserverOP> observers = new ArrayList<>();
    public void subscribe(ObserverOP o) { observers.add(o); }
    public void notifyAll(String msg) { observers.forEach(o -> o.update(msg)); }
}