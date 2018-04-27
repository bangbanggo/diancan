package edu.black.util;

public class DBData {
    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static String driver = null;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DBData.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DBData.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DBData.password = password;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        DBData.driver = driver;
    }
}
