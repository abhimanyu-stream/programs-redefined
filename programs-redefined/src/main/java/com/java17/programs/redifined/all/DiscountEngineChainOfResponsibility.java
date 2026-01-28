package com.java17.programs.redifined.all;

public class DiscountEngineChainOfResponsibility {
}
abstract class DiscountHandler {
    protected DiscountHandler next;
    public void setNext(DiscountHandler next){ this.next=next; }
    public abstract double apply(double price);
}

class TenPercentDiscount extends DiscountHandler{
    public double apply(double price){ price*=0.9; return next!=null?next.apply(price):price; }
}
class FiveHundredOff extends DiscountHandler{
    public double apply(double price){ price-=500; return next!=null?next.apply(price):price; }
}