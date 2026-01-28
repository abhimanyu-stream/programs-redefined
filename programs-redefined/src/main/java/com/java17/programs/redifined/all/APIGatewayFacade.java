package com.java17.programs.redifined.all;

public class APIGatewayFacade {
}
class PaymentServiceAPI{ void pay(){ System.out.println("Payment"); } }
class OrderServiceAPI{ void order(){ System.out.println("Order"); } }
class APIGateway {
    private PaymentServiceAPI p=new PaymentServiceAPI();
    private OrderServiceAPI o=new OrderServiceAPI();
    public void process() { p.pay(); o.order(); }
}