package com.java17.programs.redifined.all;

public class AuthSecurityProxyDecorator {
}
interface Service { void execute(); }
class RealService implements Service { public void execute(){ System.out.println("Executing Service"); } }
class ServiceProxy implements Service {
    private Service service; public ServiceProxy(Service s){ this.service=s; }
    public void execute(){ System.out.println("Auth check"); service.execute(); }
}