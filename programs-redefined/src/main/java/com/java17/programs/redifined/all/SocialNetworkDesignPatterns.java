package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class SocialNetworkDesignPatterns {
}


// ----------------- User / Profile -----------------
// Factory + Builder
class UserProfile {
    private String name;
    private String type;
    private int age;

    private UserProfile(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.age = builder.age;
    }

    static class Builder {
        private String name;
        private String type;
        private int age;

        Builder setName(String name) { this.name = name; return this; }
        Builder setType(String type) { this.type = type; return this; }
        Builder setAge(int age) { this.age = age; return this; }
        UserProfile build() { return new UserProfile(this); }
    }

    @Override
    public String toString() {
        return "UserProfile{name='" + name + "', type='" + type + "', age=" + age + "}";
    }

    public static void main(String[] args) {
        UserProfile user = new UserProfile.Builder()
                .setName("Alice")
                .setType("Premium")
                .setAge(25)
                .build();
        System.out.println(user);
    }
}

// ----------------- Friendship -----------------
// Mediator + Observer
interface FriendObserver {
    void update(String message);
}

class UserFriend implements FriendObserver {
    private String name;
    UserFriend(String name) { this.name = name; }
    public void update(String message) { System.out.println(name + " got notification: " + message); }
}

class FriendshipMediator {
    private List<FriendObserver> friends = new ArrayList<>();
    void addFriend(FriendObserver f) { friends.add(f); }
    void sendRequest(String message) {
        for (FriendObserver f : friends) f.update(message);
    }

    public static void main(String[] args) {
        FriendshipMediator mediator = new FriendshipMediator();
        UserFriend alice = new UserFriend("Alice");
        UserFriend bob = new UserFriend("Bob");
        mediator.addFriend(alice);
        mediator.addFriend(bob);
        mediator.sendRequest("Charlie sent you a friend request");
    }
}

// ----------------- Post / Feed -----------------
// Observer + Publisher-Subscriber
interface FeedObserver {
    void notify(String post);
}

class Follower implements FeedObserver {
    private String name;
    Follower(String name) { this.name = name; }
    public void notify(String post) { System.out.println(name + " sees post: " + post); }
}

class PostPublisher {
    private List<FeedObserver> followers = new ArrayList<>();
    void subscribe(FeedObserver f) { followers.add(f); }
    void publishPost(String post) {
        for (FeedObserver f : followers) f.notify(post);
    }

    public static void main(String[] args) {
        PostPublisher publisher = new PostPublisher();
        publisher.subscribe(new Follower("Alice"));
        publisher.subscribe(new Follower("Bob"));
        publisher.publishPost("New post: Design Patterns in Social Networks!");
    }
}

// ----------------- Comment -----------------
// Composite
abstract class CommentComponent {
    abstract void display(String prefix);
}

class CommentLeaf extends CommentComponent {
    private String comment;
    CommentLeaf(String comment) { this.comment = comment; }
    void display(String prefix) { System.out.println(prefix + comment); }
}

class CommentComposite extends CommentComponent {
    private List<CommentComponent> children = new ArrayList<>();
    void add(CommentComponent c) { children.add(c); }
    void display(String prefix) {
        for (CommentComponent c : children) c.display(prefix + "--");
    }

    public static void main(String[] args) {
        CommentComposite root = new CommentComposite();
        root.add(new CommentLeaf("Great post!"));
        CommentComposite reply = new CommentComposite();
        reply.add(new CommentLeaf("Thanks!"));
        root.add(reply);
        root.display("");
    }
}

// ----------------- Like / Reaction -----------------
// Flyweight
class Reaction {
    private final String type;
    Reaction(String type) { this.type = type; }
    void display(String user) { System.out.println(user + " reacted: " + type); }
}

class ReactionFactory {
    private static final Map<String, Reaction> reactions = new HashMap<>();
    static Reaction getReaction(String type) {
        reactions.putIfAbsent(type, new Reaction(type));
        return reactions.get(type);
    }

    public static void main(String[] args) {
        Reaction r1 = ReactionFactory.getReaction("Like");
        Reaction r2 = ReactionFactory.getReaction("Love");
        r1.display("Alice");
        r2.display("Bob");
    }
}

// ----------------- Messaging / Chat -----------------
// Mediator + Observer
interface ChatObserver { void receive(String message); }

class ChatUser implements ChatObserver {
    private String name;
    ChatUser(String name) { this.name = name; }
    public void receive(String message) { System.out.println(name + " received: " + message); }
}

class ChatMediator {
    private List<ChatUser> users = new ArrayList<>();
    void addUser(ChatUser u) { users.add(u); }
    void sendMessage(String message) { for(ChatUser u: users) u.receive(message); }

    public static void main(String[] args) {
        ChatMediator chat = new ChatMediator();
        ChatUser alice = new ChatUser("Alice");
        ChatUser bob = new ChatUser("Bob");
        chat.addUser(alice);
        chat.addUser(bob);
        chat.sendMessage("Hello everyone!");
    }
}

// ----------------- Notification -----------------
// Observer + Adapter
interface NotificationServiceSN { void send(String msg); }
class EmailServiceSN implements NotificationServiceSN { public void send(String msg){ System.out.println("Email: " + msg); } }
class SMSServiceSN implements NotificationServiceSN { public void send(String msg){ System.out.println("SMS: " + msg); } }

class NotificationPublisher {
    private List<NotificationServiceSN> services = new ArrayList<>();
    void addService(NotificationServiceSN s){ services.add(s); }
    void notifyAll(String msg){ services.forEach(s -> s.send(msg)); }

    public static void main(String[] args) {
        NotificationPublisher publisher = new NotificationPublisher();
        publisher.addService(new EmailServiceSN());
        publisher.addService(new SMSServiceSN());
        publisher.notifyAll("You have a new message!");
    }
}

// ----------------- Recommendation -----------------
// Strategy + Chain of Responsibility
interface RecommendationStrategySN { void recommend(String user); }

class FriendBasedRecommendation implements RecommendationStrategySN {
    public void recommend(String user) { System.out.println("Friend-based recommendations for " + user); }
}
class TrendingRecommendation implements RecommendationStrategySN {
    public void recommend(String user) { System.out.println("Trending recommendations for " + user); }
}

class RecommendationEngine {
    private List<RecommendationStrategySN> strategies = new ArrayList<>();
    void addStrategy(RecommendationStrategySN s) { strategies.add(s); }
    void recommend(String user) { strategies.forEach(s -> s.recommend(user)); }

    public static void main(String[] args) {
        RecommendationEngine engine = new RecommendationEngine();
        engine.addStrategy(new FriendBasedRecommendation());
        engine.addStrategy(new TrendingRecommendation());
        engine.recommend("Alice");
    }
}

// ----------------- Search / Discovery -----------------
// Facade + Proxy
interface SearchService { void search(String query); }
class RealSearchService implements SearchService {
    public void search(String query){ System.out.println("Searching database for: " + query); }
}
class SearchProxy implements SearchService {
    private RealSearchService service = new RealSearchService();
    public void search(String query) {
        System.out.println("Logging search query: " + query);
        service.search(query);
    }

    public static void main(String[] args) {
        SearchService search = new SearchProxy();
        search.search("Design Patterns");
    }
}

// ----------------- Dating Match -----------------
// Strategy / Template Method
abstract class MatchAlgorithm {
    abstract void filterCandidates();
    abstract void rankCandidates();
    void findMatches() {
        filterCandidates();
        rankCandidates();
        System.out.println("Matches found!");
    }

    public static void main(String[] args) {
        MatchAlgorithm algo = new MatchAlgorithm() {
            void filterCandidates(){ System.out.println("Filtering by age/location"); }
            void rankCandidates(){ System.out.println("Ranking by preferences"); }
        };
        algo.findMatches();
    }
}

// ----------------- Group / Community -----------------
// Composite + Observer
abstract class GroupComponent {
    abstract void display();
}
class GroupLeaf extends GroupComponent {
    private String name; GroupLeaf(String name){ this.name=name; }
    void display(){ System.out.println("Member: " + name); }
}
class GroupComposite extends GroupComponent {
    private String groupName;
    private List<GroupComponent> children = new ArrayList<>();
    GroupComposite(String groupName){ this.groupName = groupName; }
    void add(GroupComponent c){ children.add(c); }
    void display(){
        System.out.println("Group: " + groupName);
        for(GroupComponent c: children) c.display();
    }
    public static void main(String[] args){
        GroupComposite group = new GroupComposite("Java Enthusiasts");
        group.add(new GroupLeaf("Alice"));
        group.add(new GroupLeaf("Bob"));
        group.display();
    }
}

// ----------------- Payment (ecommerce) -----------------
// Strategy
interface PaymentStrategySN { void pay(int amount); }
class PaypalPayment implements PaymentStrategySN { public void pay(int amount){ System.out.println("Paid " + amount + " via Paypal"); } }
class StripePayment implements PaymentStrategySN { public void pay(int amount){ System.out.println("Paid " + amount + " via Stripe"); } }

class PaymentContextSN {
    private PaymentStrategySN strategy;
    PaymentContextSN(PaymentStrategySN strategy){ this.strategy = strategy; }
    void pay(int amount){ strategy.pay(amount); }

    public static void main(String[] args){
        PaymentContextSN contextPayPal = new PaymentContextSN(new PaypalPayment());
        contextPayPal.pay(100);
        PaymentContextSN contextStripe = new PaymentContextSN(new StripePayment());
        contextStripe.pay(300);

    }
}

// ----------------- Order (ecommerce) -----------------
// State
interface OrderStateSN { void handle(); }
class PendingState implements OrderStateSN { public void handle(){ System.out.println("Order pending"); } }
class ShippedState implements OrderStateSN { public void handle(){ System.out.println("Order shipped"); } }
class DeliveredState implements OrderStateSN { public void handle(){ System.out.println("Order delivered"); } }

class OrderContextSN {
    private OrderStateSN state;

    public OrderStateSN getState() {
        return state;
    }

    public void setState(OrderStateSN state) {
        this.state = state;
    }

    void handle(){ state.handle(); }

    public static void main(String[] args){
        OrderContextSN order = new OrderContextSN();
        order.setState(new PendingState()); order.handle();
        order.setState(new ShippedState()); order.handle();
        order.setState(new DeliveredState()); order.handle();
    }
}

// ----------------- Cart (ecommerce) -----------------
// Composite
abstract class CartComponent { abstract void showItem(); }
class CartItemSN extends CartComponent {
    private String name; CartItemSN(String name){ this.name=name; }
    void showItem(){ System.out.println("Item: " + name); }
}
class CartComposite extends CartComponent {
    private List<CartComponent> items = new ArrayList<>();
    void add(CartComponent c){ items.add(c); }
    void showItem(){ items.forEach(CartComponent::showItem); }

    public static void main(String[] args){
        CartComposite cart = new CartComposite();
        cart.add(new CartItemSN("Laptop"));
        cart.add(new CartItemSN("Mouse"));
        cart.showItem();
    }
}

// ----------------- Inventory (trading) -----------------
// Singleton + Proxy
class InventorySN {
    private static InventorySN instance;
    private InventorySN() {}
    public static synchronized InventorySN getInstance(){
        if(instance==null) instance=new InventorySN();
        return instance;
    }
    void checkStock(){ System.out.println("Checking inventory stock"); }
}

class InventoryProxySN {
    private InventorySN inventory = InventorySN.getInstance();
    void checkStock(){ System.out.println("Proxy logging"); inventory.checkStock(); }

    public static void main(String[] args){
        InventoryProxySN proxy = new InventoryProxySN();
        proxy.checkStock();
    }
}

// ----------------- Trading / Marketplace -----------------
// Observer + Strategy
interface TradingObserver { void notifyTrade(String trade); }
class Trader implements TradingObserver {
    private String name; Trader(String name){ this.name=name; }
    public void notifyTrade(String trade){ System.out.println(name + " notified: " + trade); }
}

interface PricingStrategy { int price(int base); }
class DiscountStrategySN implements PricingStrategy { public int price(int base){ return base - 10; } }

class Marketplace {
    private List<TradingObserver> observers = new ArrayList<>();
    void addObserver(TradingObserver o){ observers.add(o); }
    void executeTrade(String trade){ observers.forEach(o -> o.notifyTrade(trade)); }

    public static void main(String[] args){
        Marketplace market = new Marketplace();
        market.addObserver(new Trader("Alice"));
        market.executeTrade("Stock XYZ bought");
        PricingStrategy strategy = new DiscountStrategySN();
        System.out.println("Price after discount: " + strategy.price(100));
    }
}

// ----------------- Privacy / Security -----------------
// Proxy / Decorator
interface ServiceSN { void execute(); }
class RealServiceSN implements ServiceSN { public void execute(){ System.out.println("Executing service"); } }
class SecurityDecorator implements ServiceSN {
    private ServiceSN service; SecurityDecorator(ServiceSN service){ this.service=service; }
    public void execute(){
        System.out.println("Checking access...");
        service.execute();
    }

    public static void main(String[] args){
        ServiceSN service = new SecurityDecorator(new RealServiceSN());
        service.execute();
    }
}

// ----------------- Content Moderation -----------------
// Chain of Responsibility
abstract class Filter { protected Filter next; void setNext(Filter f){ next=f; } abstract void handle(String content); }
class SpamFilter extends Filter { void handle(String content){
    if(content.contains("spam")) System.out.println("Filtered spam");
    else if(next!=null) next.handle(content);
} }
class AbusiveFilter extends Filter { void handle(String content){
    if(content.contains("abuse")) System.out.println("Filtered abuse");
    else if(next!=null) next.handle(content);
} }

class ContentModerationDemo {
    public static void main(String[] args){
        Filter spam = new SpamFilter();
        Filter abuse = new AbusiveFilter();
        spam.setNext(abuse);
        spam.handle("This is spam message");
        spam.handle("This is abuse message");
        spam.handle("Clean content");
    }
}

// ----------------- Ad / Monetization -----------------
// Strategy + Observer
interface AdStrategy { void displayAd(String user); }
class CPCAd implements AdStrategy { public void displayAd(String user){ System.out.println("CPC Ad for " + user); } }

interface AdObserver { void notifyAd(String ad); }
class AdPublisher { private List<AdObserver> observers = new ArrayList<>();
    void addObserver(AdObserver o){ observers.add(o); }
    void publishAd(String ad){ observers.forEach(o -> o.notifyAd(ad)); }
}

class AdSubscriber implements AdObserver {
    private String name; AdSubscriber(String name){ this.name=name; }
    public void notifyAd(String ad){ System.out.println(name + " got ad: " + ad); }

    public static void main(String[] args){
        AdPublisher publisher = new AdPublisher();
        publisher.addObserver(new AdSubscriber("Alice"));
        AdStrategy strategy = new CPCAd();
        strategy.displayAd("Alice");
        publisher.publishAd("Buy now!");
    }
}

// ----------------- Analytics -----------------
// Decorator + Observer
interface Analytics { void track(String event); }
class BaseAnalytics implements Analytics { public void track(String event){ System.out.println("Tracking event: " + event); } }
class AnalyticsDecorator implements Analytics {
    private Analytics analytics; AnalyticsDecorator(Analytics analytics){ this.analytics = analytics; }
    public void track(String event){ System.out.println("Logging analytics"); analytics.track(event); }

    public static void main(String[] args){
        Analytics analytics = new AnalyticsDecorator(new BaseAnalytics());
        analytics.track("UserLogin");
    }
}

// ----------------- Logging / Monitoring -----------------
// Decorator
interface LoggerSN { void log(String message); }
class BaseLogger implements LoggerSN { public void log(String message){ System.out.println("LOG: " + message); } }
class LoggerDecoratorSN implements LoggerSN {
    private LoggerSN logger; LoggerDecoratorSN(LoggerSN logger){ this.logger=logger; }
    public void log(String message){ System.out.println("Decorating log"); logger.log(message); }

    public static void main(String[] args){
        LoggerSN logger = new LoggerDecoratorSN(new BaseLogger());
        logger.log("User action");
    }
}

// ----------------- API Gateway -----------------
// Facade + Proxy
class ApiGateway {
    private RealService service = new RealService();
    void request(String endpoint){
        System.out.println("Logging request for " + endpoint);
        service.execute();
    }
    public static void main(String[] args){
        ApiGateway gateway = new ApiGateway();
        gateway.request("/users");
    }
}

// ----------------- Config Service -----------------
// Singleton
class ConfigServiceSN {
    private static ConfigServiceSN instance;
    private ConfigServiceSN() {}
    public static synchronized ConfigServiceSN getInstance(){
        if(instance==null) instance=new ConfigServiceSN();
        return instance;
    }
    void showConfig(){ System.out.println("Loading config..."); }
    public static void main(String[] args){
        ConfigServiceSN config = ConfigServiceSN.getInstance();
        config.showConfig();
    }
}

// ----------------- Event Bus / Stream -----------------
// Observer + Mediator
interface EventListenerSN { void onEvent(String event); }
class EventBusSN {
    private List<EventListenerSN> listeners = new ArrayList<>();
    void register(EventListenerSN l){ listeners.add(l); }
    void publish(String event){ listeners.forEach(l -> l.onEvent(event)); }
    public static void main(String[] args){
        EventBusSN bus = new EventBusSN();
        bus.register(e -> System.out.println("Listener got: " + e));
        bus.publish("UserPosted");
    }
}

// ----------------- Discount / Offer Engine -----------------
// Chain of Responsibility
abstract class Discount { protected Discount next; void setNext(Discount d){ next=d; } abstract int apply(int price); }
class PercentageDiscount extends Discount {
    int apply(int price){
        int discounted = price - price/10;
        System.out.println("Percentage discount applied: " + discounted);
        return next!=null? next.apply(discounted): discounted;
    }
}
class FixedDiscount extends Discount {
    int apply(int price){
        int discounted = price - 5;
        System.out.println("Fixed discount applied: " + discounted);
        return next!=null? next.apply(discounted): discounted;
    }
}
class DiscountEngine {
    public static void main(String[] args){
        Discount d1 = new PercentageDiscount();
        Discount d2 = new FixedDiscount();
        d1.setNext(d2);
        int finalPrice = d1.apply(100);
        System.out.println("Final price: " + finalPrice);
    }
}

// ----------------- Search Index Updater -----------------
// Adapter + Observer
interface DBEvent { String getData(); }
class MySQLEvent implements DBEvent{

    @Override
    public String getData() {
        return null;
    }
}
/**
 * For a social network backend microservices (friendship, posts, likes, comments, messaging, dating, ecommerce, trading, recommendation, etc.), we can extend the same approach with patterns suited to social interactions, feeds, and scalability.
 *
 * Here’s an extended summary table for a social network microservices ecosystem:
 *
 * 📌 Social Network Microservices & Patterns (Extended)
 * Service / Domain	Pattern(s) Used	Why?
 * User / Profile	Factory / Builder	Different profile types (basic, premium, business), flexible creation
 * Friendship	Mediator / Observer	Handle bi-directional relationships, notify on friend requests
 * Post / Feed	Observer + Publisher-Subscriber (Event Sourcing)	Notify followers, async event-driven feed updates
 * Comment	Composite	Nested comments (tree structure), easy traversal
 * Like / Reaction	Flyweight	Optimize memory for millions of likes/reactions
 * Messaging / Chat	Mediator + Observer	Route messages between users/groups, real-time updates
 * Notification	Observer + Adapter	Multi-channel (email, SMS, push, in-app) notifications
 * Recommendation	Strategy + Chain of Responsibility	Multiple recommendation algorithms + filtering (content-based, friend-based, trending)
 * Search / Discovery	Facade + Proxy	Hide complexity of Elasticsearch/Graph queries, caching
 * Dating Match	Strategy / Template Method	Different match-making algorithms (preferences, location, ML-based)
 * Group / Community	Composite + Observer	Nested groups, notify members of updates
 * Payment (ecommerce)	Strategy	Multiple payment providers (PayPal, Stripe, UPI, etc.)
 * Order (ecommerce)	State	Lifecycle of orders (pending, shipped, delivered, returned)
 * Cart (ecommerce)	Composite	Multiple items, discounts, coupons
 * Inventory (trading)	Singleton + Proxy	Ensure consistent inventory across nodes with caching
 * Trading / Marketplace	Observer + Strategy	Notify buyers/sellers, dynamic pricing strategies
 * Privacy / Security	Proxy / Decorator	Access control, logging, rate-limiting
 * Content Moderation	Chain of Responsibility	Sequential filtering (spam, abusive content, NSFW)
 * Ad / Monetization	Strategy + Observer	Different bidding strategies, notify when ads are served
 * Analytics	Decorator + Observer	Collect metrics without changing business logic
 * Logging / Monitoring	Decorator	Cross-cutting concerns (trace, audit, metrics)
 * API Gateway	Facade + Proxy	Simplify external API, route requests securely
 * Config Service	Singleton	Centralized config (feature flags, toggles)
 * Event Bus / Stream	Observer + Mediator	Async event-driven communication (Kafka, Pulsar, RabbitMQ)
 * Discount / Offer Engine	Chain of Responsibility	Apply multiple discount rules in sequence
 * Search Index Updater	Adapter + Observer	Normalize different DB events, update Elasticsearch
 * 🔑 Key Insights:
 *
 * Observer / Pub-Sub is central in social networks (friends, followers, events).
 *
 * Composite is useful for hierarchical data (comments, groups).
 *
 * Strategy + Chain of Responsibility power recommendations, dating, and moderation.
 *
 * Proxy / Decorator handle cross-cutting (security, rate-limiting, logging).
 *
 * Flyweight saves memory for high-volume entities (likes, emoji reactions).
 *
 * Builder / Factory for flexible object creation (profiles, posts, groups).
 */


/**
 * Ecommerce for SN
 *
 *
 * Yes — in a real-world e-commerce microservices platform (Amazon, Flipkart, Myntra scale), we’ll need many services, and each one can benefit from different design patterns for maintainability, scalability, and extensibility.
 *
 * Let me expand the list 🚀
 *
 * 🛒 Extended E-Commerce Microservices + Design Patterns
 * ✅ Core Business Services
 *
 * Payment Service → Strategy Pattern
 *
 * Different payment methods (Credit Card, UPI, Wallet, Net Banking).
 *
 * Order Service → State Pattern
 *
 * Order transitions through states (Created → Confirmed → Shipped → Delivered → Cancelled).
 *
 * Cart Service → Composite Pattern
 *
 * Cart holds multiple items, promotions, bundles.
 *
 * Customer Service → Observer Pattern
 *
 * Customers get notified of offers, status updates, loyalty points.
 *
 * Product Service → Factory / Abstract Factory Pattern
 *
 * Different product types (Books, Electronics, Fashion).
 *
 * Inventory Service → Singleton + Proxy Pattern
 *
 * Singleton → ensure one source of truth.
 *
 * Proxy → cache inventory reads to reduce DB load.
 *
 * ✅ Supporting Services
 *
 * Shipping / Logistics Service → Visitor Pattern
 *
 * Add new shipping rules (weight-based, express delivery, partner carriers) without touching core model.
 *
 * Catalog Service → Builder Pattern
 *
 * Products are built with many optional attributes (size, color, material, specs).
 *
 * Recommendation / Search Service → Strategy + Chain of Responsibility
 *
 * Strategy → plug in different recommendation algorithms (collaborative, content-based).
 *
 * Chain of Responsibility → filtering pipeline (availability → location → personalization).
 *
 * Notification Service → Observer + Adapter Pattern
 *
 * Observer → notify multiple customers.
 *
 * Adapter → unify SMS/Email/Push APIs.
 *
 * Authentication / Authorization Service → Proxy / Decorator Pattern
 *
 * Proxy → security check before service call.
 *
 * Decorator → add logging, throttling.
 *
 * ✅ Infrastructure / Cross-cutting Services
 *
 * Logging / Monitoring → Decorator Pattern
 *
 * Add monitoring, tracing, metrics without changing business logic.
 *
 * API Gateway → Facade Pattern
 *
 * Provides a unified API while hiding multiple microservices.
 *
 * Config Service → Singleton Pattern
 *
 * Centralized configuration.
 *
 * Event Service (Kafka/RabbitMQ) → Observer + Mediator Pattern
 *
 * Event-driven system where services communicate asynchronously.
 *
 * Discount / Promotion Engine → Chain of Responsibility Pattern
 *
 * Multiple discount rules applied sequentially.
 *
 * 📌 Summary Table (Extended)
 * Service	Pattern(s) Used	Why?
 * Payment	Strategy	Multiple payment methods
 * Order	State	Order lifecycle states
 * Cart	Composite	Holds multiple items
 * Customer	Observer	Customer notifications
 * Product	Factory / Abstract Factory	Different product creation
 * Inventory	Singleton + Proxy	Consistent inventory, cache access
 * Shipping	Visitor	Add shipping policies without modifying classes
 * Catalog	Builder	Complex product creation
 * Recommendation	Strategy + Chain of Responsibility	Different recommendation + filtering
 * Notification	Observer + Adapter	Notify via multiple channels
 * Auth / Security	Proxy / Decorator	Access control, logging, throttling
 * Logging/Monitoring	Decorator	Add cross-cutting concerns
 * API Gateway	Facade	Simplify API for clients
 * Config Service	Singleton	Central configuration
 * Event Bus	Observer + Mediator	Asynchronous service communication
 * Discount Engine	Chain of Responsibility	Sequential discount rules
 *
 * ✅ This covers most of the major patterns in an E-commerce Microservices ecosystem.
 *
 *
 *
 * Why Visitor in E-Commerce Microservices?
 *
 * In an e-commerce system, we have different order types or services (PayIn, PayOut, Shipping, Returns, Loyalty, etc.).
 * We often need to add operations like:
 *
 * Calculate tax / shipping
 *
 * Generate invoices
 *
 * Log / Audit / Export JSON
 *
 * Apply Discounts
 *
 * 👉 Instead of changing each service class whenever we add a new operation, the Visitor Pattern lets us add operations without modifying service/domain classes.
 *
 * Key Benefits in E-commerce
 *
 * Add new operations (e.g., ExportToExcelVisitor, FraudDetectionVisitor) without changing service classes.
 *
 * Keeps services focused on core responsibilities.
 *
 * Supports extensibility → as new requirements (compliance, reporting, analytics) come in, just add new Visitors.
 *
 * 👉 Would you like me to extend this with a new Visitor for Notifications (Email/SMS to customers), so you see how we can plug in new functionality without touching Shipping/Payment/Inventory classes?
 *
 * Summary Table
 * Service	Suitable Pattern	Why?
 * Payment	Strategy	Multiple payment methods (flexibility)
 * Order	State	Different states of order lifecycle
 * Cart	Composite	Cart contains multiple items/products
 * Customer	Observer	Notify customers about updates/offers
 * Product	Factory	Create different product types easily
 * Inventory	Singleton	Maintain a single inventory instance
 */