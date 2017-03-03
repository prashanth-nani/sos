package in.ac.mnnit.sos.services;

/**
 * Created by prashanth on 21/2/17.
 */

public class Config {
    private static final String BASE_URL = "http://172.31.74.249/sos/";
    public static final String REGISTER_URL = BASE_URL.concat("register.php");
    public static final String PROCESS_EMAIL_URL = BASE_URL.concat("process_email.php");
    public static final String LOGIN_URL = BASE_URL.concat("login.php");
}