package com.java17.programs.redifined.all;

public class ConfigServiceSingleton {
}
class ConfigService {
    private static ConfigService instance;
    private ConfigService(){}
    public static synchronized ConfigService getInstance(){
        if(instance==null) instance=new ConfigService();
        return instance;
    }
}