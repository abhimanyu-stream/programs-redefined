package com.java17.programs.redifined.all;

public class SingletonImmutableClass {
    public static void main(String[] args) {

        AppConfig instance = AppConfig.getInstance();
        System.out.println(instance);

    }
}
final class AppConfig {
    /**
     * Characteristics:
     * final class → can't be subclassed.
     *
     * private final fields → can't be modified.
     *
     * no setters → immutable.
     *
     * private constructor + static final instance → singleton.
     */
    private static final AppConfig INSTANCE = new AppConfig("v1.0", 8080);

    private final String appVersion;
    private final int port;

    // Private constructor
    private AppConfig(String appVersion, int port) {
        this.appVersion = appVersion;
        this.port = port;
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public int getPort() {
        return port;
    }
}