package com.java17.programs.redifined.all;

import java.io.Serial;
import java.io.Serializable;

public class SingletonImmutableClassWithPreventionSafety {

    public static void main(String[] args) {

    }

}
final class AppConfigWithPreventionSafety implements Cloneable, Serializable {
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

    private static final long serialVersionUUID = 1L;
    private static final AppConfigWithPreventionSafety INSTANCE = new AppConfigWithPreventionSafety("v1.0", 8080);

    private final String appVersion;
    private final int port;

    // Private constructor
    private AppConfigWithPreventionSafety(String appVersion, int port) {
        this.appVersion = appVersion;
        this.port = port;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CloneNotSupportedException("CloneNotSupportedException");
    }
    @Serial
    private Object readResolve(){
        return getInstance();
    }

    public static AppConfigWithPreventionSafety getInstance() {
        return INSTANCE;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public int getPort() {
        return port;
    }
}