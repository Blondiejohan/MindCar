package mindcar.testing.util;

/**
 * Created by Darkthronen on 13/05/2016.
 */
public class UserData {

    private static String Username;
    private static String Password;

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
