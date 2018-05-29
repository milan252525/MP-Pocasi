package pocasi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherByCity {
    private final String API_KEY = "353b379036c2911483cfb9147c1ec9f0";
    
    private String response_json, url;
    private int response_code;
    private boolean funguje = true;
    
    //promenne pro data ze zadosti
    private double lon, lat, temp, pressure, humidity, temp_min, temp_max, visibility, speed, all, message;
    private int weather_id, type, sys_id, cod;
    private String main, description, icon, base, country, name;
    private long id, dt, sunrise, sunset;

    public WeatherByCity(String city){
        try{
            //vytvoreni url
            URL request_url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=%s", city, API_KEY));
            //otevreni spojeni
            HttpURLConnection connection = (HttpURLConnection) request_url.openConnection();
            //kod odpovedi
            int responseCode = connection.getResponseCode();
            this.response_code = responseCode;
            //kontrola response kodu, cteni odpovedi
            if(responseCode == 200){
                String input;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    input = reader.readLine();
                    reader.close();
                }
                this.response_json = input;
            }
            //ziskani dat z odpovedi
            JSONObject jo = new JSONObject(this.response_json);

            JSONObject j_coord = jo.getJSONObject("coord");
            this.lon = j_coord.getDouble("lon");
            this.lat = j_coord.getDouble("lat");

            JSONArray j_weather = jo.getJSONArray("weather"); 
            JSONObject jo_weather = j_weather.getJSONObject(0);
            this.weather_id = jo_weather.getInt("id");
            this.main = jo_weather.getString("main");
            this.description = jo_weather.getString("description");
            this.icon = jo_weather.getString("icon");

            this.base = jo.getString("base");

            JSONObject j_main = jo.getJSONObject("main");
            this.temp = j_main.getDouble("temp");
            this.pressure = j_main.getDouble("pressure");
            this.humidity = j_main.getDouble("humidity");
            this.temp_min = j_main.getDouble("temp_min");
            this.temp_max = j_main.getDouble("temp_max");

            this.visibility = jo.getDouble("visibility");

            JSONObject j_wind = jo.getJSONObject("wind");
            this.speed = j_wind.getDouble("speed");

            JSONObject j_clouds = jo.getJSONObject("clouds");
            this.all = j_clouds.getDouble("all");

            this.dt = jo.getLong("dt");

            JSONObject j_sys = jo.getJSONObject("sys");
            this.type = j_sys.getInt("type");
            this.sys_id = j_sys.getInt("id");
            this.message = j_sys.getDouble("message");
            this.country = j_sys.getString("country");
            this.sunrise = j_sys.getLong("sunrise");
            this.sunset = j_sys.getLong("sunset");

            this.id = jo.getLong("id");
            this.name = jo.getString("name");
            this.cod = jo.getInt("cod");
    
        }
        catch(JSONException je){
            this.funguje = false;
        }
        catch(MalformedURLException mu){
            this.funguje = false;
        }
        catch(IOException ie){
            this.funguje = false;
        }
    }

    //get funkce
    public double getCoordLon() {
        return this.lon;
    }

    public double getCoordLat() {
        return this.lat;
    }

    public int getWeatherId() {
        return this.weather_id;
    }

    public String getWeatherMain() {
        return this.main;
    }

    public String getWeatherDescription() {
        return this.description;
    }

    public String getWeatherIcon() {
        return this.icon;
    }

    public String getBase() {
        return this.base;
    }

    public double getMainTemp() {
        return this.temp;
    }

    public double getMainPressure() {
        return this.pressure;
    }

    public double getMainHumidity() {
        return this.humidity;
    }

    public double getMainTempMin() {
        return this.temp_min;
    }

    public double getMainTempMax() {
        return this.temp_max;
    }

    public double getVisibility() {
        return this.visibility;
    }

    public double getWindSpeed() {
        return this.speed;
    }

    public double getCloudsAll() {
        return this.all;
    }

    public long getDt() {
        return this.dt;
    }

    public int getSysType() {
        return this.type;
    }

    public int getSysId() {
        return this.sys_id;
    }

    public double getSysMessage() {
        return this.message;
    }

    public String getSysCountry() {
        return this.country;
    }

    public long getSysSunrise() {
        return this.sunrise;
    }

    public long getSysSunset() {
        return this.sunset;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCod() {
        return this.cod;
    }

    @Override
    public String toString() {
        String r = "";
        r += "City: " + this.getName() + "\n";
        r += "Country: " + this.getSysCountry() + "\n";
        r += "Temperature: " + this.getMainTemp() + " °C\n";
        r += "Pressure: " + this.getMainPressure()+ " hPa\n";
        r += "Weather: " + this.getWeatherDescription() + "\n";
        r += "Wind speed: " + this.getWindSpeed() + " m/s\n";
        return r;
    }
//    
//    public static void main(String[] args){
//        try {
//            WeatherByCity x = new WeatherByCity("Plzen");
//            System.out.println(x.toString());
//        } catch (IOException ex) {
//            System.out.println("IOException:\n" + ex);
//        }
//    }
}
