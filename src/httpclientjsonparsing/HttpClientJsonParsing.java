package httpclientjsonparsing;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class HttpClientJsonParsing {
    public static void main(String[] args) {
        System.out.print("city:     ");
        final Scanner scanner = new Scanner(System.in);
        final String city = scanner.nextLine();

        try {
            final URL url = new URL(
                    "http://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(city, "UTF-8")
                            + "&appid=791581d4ed2ba5d5f948aad0190501c9"
            );
            final URLConnection yc = url.openConnection();

            final BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            final ForecastRoot root = new Gson().fromJson(in, ForecastRoot.class);
            in.close();

            System.out.println("temp:     " + root.getMain().getTemp() + " K");
            System.out.println("pressure: " + root.getMain().getPressure() + " mbar");
        }
        catch (final FileNotFoundException e) { e.printStackTrace(); System.err.println("No such city: " + city); }  // 404 not found
        catch (final IOException           e) { e.printStackTrace(); }

        System.out.print("\ncontinue? [y/n] ");
        if (scanner.nextLine().equals("y")) {
            System.out.println();
            main(args);
        }
    }
}


