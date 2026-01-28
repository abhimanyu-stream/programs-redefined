package com.java17.programs.redifined.all;

public class RecommendationServiceStrategyChainOfResponsibility {
    public static void main(String[] args) {
        RecommendationStrategy strategy = new CollaborativeStrategy();
        strategy.recommend("Alice");

        FilterHandler availability = new AvailabilityFilter();
        FilterHandler nextFilter = new FilterHandler(){ public void handle(String user){ System.out.println("Next filter applied for "+user); }};
        availability.setNext(nextFilter);
        availability.handle("Alice");
    }

}
interface RecommendationStrategy { void recommend(String user); }
class CollaborativeStrategy implements RecommendationStrategy { public void recommend(String user) { System.out.println("Collaborative recommendations for " + user); } }
class ContentBasedStrategy implements RecommendationStrategy { public void recommend(String user) { System.out.println("Content-based recommendations for " + user); } }

abstract class FilterHandler {
    protected FilterHandler next;
    public void setNext(FilterHandler next) { this.next = next; }
    public abstract void handle(String user);
}

class AvailabilityFilter extends FilterHandler {
    public void handle(String user) { System.out.println("Availability filter applied for " + user); if(next!=null) next.handle(user); }
}