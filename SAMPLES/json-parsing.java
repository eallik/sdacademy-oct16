import com.google.gson.Gson;



/**

 * Created by marcin on 09.09.2018.

 */

public class Main {

    public static void main(String[] args) {

        String json = "{\"coord\":{\"lon\":-73.99,\"lat\":40.73},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"base\":\"stations\",\"main\":{\"temp\":288.02,\"pressure\":1022,\"humidity\":76,\"temp_min\":285.95,\"temp_max\":289.85},\"visibility\":11265,\"wind\":{\"speed\":3.85,\"deg\":47.0027},\"rain\":{\"1h\":0.66},\"clouds\":{\"all\":90},\"dt\":1536480900,\"sys\":{\"type\":1,\"id\":1969,\"message\":0.0042,\"country\":\"US\",\"sunrise\":1536489067,\"sunset\":1536534864},\"id\":5128581,\"name\":\"New York\",\"cod\":200}";



        Gson gson = new Gson();

        ForecastRoot root = gson.fromJson(json, ForecastRoot.class);



        System.out.println(root.getMain().getTemp());

        System.out.println(root.getMain().getPressure());

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
