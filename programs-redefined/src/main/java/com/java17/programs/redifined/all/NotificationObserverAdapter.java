package com.java17.programs.redifined.all;



public class NotificationObserverAdapter{

    public static void main(String[] args) {

    }
}
 class NotificationObserverAdapterPattern {
    private Notification notification;
    public NotificationObserverAdapterPattern(Notification notification){ this.notification=notification; }
    public void send(String msg){ notification.send(msg); }

}
interface Notification { void send(String msg); }
class EmailNotification implements Notification { public void send(String msg){ System.out.println("Email: "+msg); } }
class SmsNotification implements Notification { public void send(String msg){ System.out.println("SMS: "+msg); } }
