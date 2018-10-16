import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by marcin on 09.09.2018.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę miasta");
        String city = scanner.nextLine();


        URL url = null;
        try {

            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(city, "UTF-8") + "&appid=791581d4ed2ba5d5f948aad0190501c9");
            URLConnection yc = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));

            Gson gson = new Gson();
            ForecastRoot root = gson.fromJson(in, ForecastRoot.class);
            in.close();

            System.out.println(root.getMain().getTemp());
            System.out.println(root.getMain().getPressure());

        } catch (FileNotFoundException e) { // 404 not found
            System.err.println("No such city");

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


/**
 * Created by marcin on 09.09.2018.
 */
public class ForecastMain {
    private float temp;
    private int pressure;

    public float getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }
}
/**
 * Created by marcin on 09.09.2018.
 */
public class ForecastRoot {
    ForecastMain main;

    public ForecastMain getMain() {
        return main;
    }
}
