package utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckUtils {
    public static boolean checkNetworkConnection(){
        try {
            URL url = new URL("http://www.wikipedia.org");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
