package com.java17.programs.redifined.all;

public class LoggingMonitoringDecorator {
}
interface Logger { void log(String msg); }
class SimpleLogger implements Logger { public void log(String msg){ System.out.println(msg); } }
class LoggerDecorator implements Logger {
    private Logger logger;
    public LoggerDecorator(Logger logger){ this.logger=logger; }
    public void log(String msg){ System.out.println("LOG: "); logger.log(msg); }
}
