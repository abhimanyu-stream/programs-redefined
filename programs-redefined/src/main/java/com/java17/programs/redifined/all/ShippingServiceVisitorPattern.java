package com.java17.programs.redifined.all;

import java.util.List;

public class ShippingServiceVisitorPattern {
    public static void main(String[] args) {
        List<ShippingElement> shippings = List.of(new StandardShipping(), new ExpressShipping());
        ShippingVisitor costVisitor = new ShippingCostVisitor();
        shippings.forEach(s -> s.accept(costVisitor));
    }
}
interface ShippingElement { void accept(ShippingVisitor visitor); }
class StandardShipping implements ShippingElement {
    public void accept(ShippingVisitor visitor) { visitor.visit(this); }
}
class ExpressShipping implements ShippingElement {
    public void accept(ShippingVisitor visitor) { visitor.visit(this); }
}

interface ShippingVisitor { void visit(StandardShipping s); void visit(ExpressShipping e); }

class ShippingCostVisitor implements ShippingVisitor {
    public void visit(StandardShipping s) { System.out.println("Standard Shipping: ₹50"); }
    public void visit(ExpressShipping e) { System.out.println("Express Shipping: ₹200"); }
}