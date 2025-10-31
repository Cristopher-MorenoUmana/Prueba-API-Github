package util;

public class UserSession {
    
    private static String userName;

    private UserSession() {}

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String pUserName) {
        userName = pUserName;
    }
}
