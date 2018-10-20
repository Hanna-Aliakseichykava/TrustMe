
Present:
+Google Places API;


TODO:
-Google Prediction API
-Weather
-Afisha


Materials:

https://github.com/windy1/google-places-api-java



 
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
 
public class Ipapi {
 
    public static void main(String[] args) throws IOException {
        URL ipapi = new URL("https://ipapi.co/1.2.3.4/json/");
 
        URLConnection c = ipapi.openConnection();
        c.setRequestProperty("User-Agent", "java-ipapi-client");
        BufferedReader reader = new BufferedReader(
          new InputStreamReader(c.getInputStream())
        );
        String location = reader.readLine();
        reader.close();
 
        System.out.println(location);
    }
 
}




