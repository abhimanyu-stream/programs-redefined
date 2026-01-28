package com.java17.programs.redifined.all;

public class OrderServiceStatePattern {
    public static void main(String[] args) {
        OrderContext order = new OrderContext();
        order.setState(new NewOrder()); order.process();
        order.setState(new ShippedOrder()); order.process();
        order.setState(new DeliveredOrder()); order.process();
    }
}
interface OrderState { void handle(); }

class NewOrder implements OrderState { public void handle() { System.out.println("Order Created"); } }
class ShippedOrder implements OrderState { public void handle() { System.out.println("Order Shipped"); } }
class DeliveredOrder implements OrderState { public void handle() { System.out.println("Order Delivered"); } }

class OrderContext {
    private OrderState state;
    public void setState(OrderState state) { this.state = state; }
    public void process() { state.handle(); }
}