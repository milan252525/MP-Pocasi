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

/**
 * Třída pro vyhledávání dat o počasí podle města
 * @author Milan Abrahám, Anh Thai Hoang
 */
public class WeatherByCity {
    /** Klíč sloužící k autentizaci při připojení k serveru */
    private final String API_KEY = "353b379036c2911483cfb9147c1ec9f0";
    
    /** Proměnná pro uložení dat ze žádosti */
    private String response_json, url;
    /** Proměnná pro uložení Kódu odpovědi serveru */
    private int response_code;
    /** Proměnná pro určení zda se vše uložilo správně */
    private boolean funguje = true;
    
    /** Proměnná pro uložení dat ze žádosti */
    private double lon, lat, temp, pressure, humidity, temp_min, temp_max, visibility, speed, all, message;
    /** Proměnná pro uložení dat ze žádosti */
    private int weather_id, type, sys_id, cod;
    /** Proměnná pro uložení dat ze žádosti */
    private String main, description, icon, base, country, name;
    /** Proměnná pro uložení dat ze žádosti */
    private long id, dt, sunrise, sunset;

    /**
     * Konstruktor třídy pro vyhledávání dat o počasí podle města
     * @param city hledané město
     */
    public WeatherByCity(String city){
        try{
            //vytvoreni url
            URL request_url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=%s&lang=cz", city, API_KEY));
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

    /**
     * Get metoda pro získání zeměpisné délky
     * @return double obsahující zeměpisnou délku
     */
    public double getCoordLon() {
        return this.lon;
    }
    /**
     * Get metoda pro získání zeměpisné šířky
     * @return double obsahující zeměpisnou šířku
     */
    public double getCoordLat() {
        return this.lat;
    }   
    /**
     * Get metoda pro získání ID počasí
     * @return int obsahující ID počasí
     */
    public int getWeatherId() {
        return this.weather_id;
    }   
    /**
     * Get metoda pro získání popisu počasí
     * @return String obsahující popis počasí
     */
    public String getWeatherMain() {
        return this.main;
    }
    /**
     * Get metoda pro získání detailnějšího popisu počasí
     * @return String obsahující detailnější popis počasí
     */
    public String getWeatherDescription() {
        return this.description;
    }
     /**
     * Get metoda pro získání ikony počasí
     * @return String obsahující ikonu počasí
     */
    public String getWeatherIcon() {
        return this.icon;
    }
    /**
     * Get metoda pro získání parametru base, nemá žádné využití, je jen pro potřebu serveru
     * @return String obsahující parametr base
     */
    public String getBase() {
        return this.base;
    }
    /**
     * Get metoda pro získání teploty
     * @return double obsahující teplotu
     */
    public double getMainTemp() {
        return this.temp;
    }
    /**
     * Get metoda pro získání atmosférického tlaku
     * @return double obsahující atmosférický tlak
     */
    public double getMainPressure() {
        return this.pressure;
    }
    /**
     * Get metoda pro získání vlhkosti vzduchu
     * @return double obsahující vlhkost vzduchu
     */
    public double getMainHumidity() {
        return this.humidity;
    }
    /**
     * Get metoda pro získání minimální teploty
     * @return double obsahující minimální teplotu
     */
    public double getMainTempMin() {
        return this.temp_min;
    }
    /**
     * Get metoda pro získání maximální teploty
     * @return double obsahující maximální teplotu
     */
    public double getMainTempMax() {
        return this.temp_max;
    }
    /**
     * Get metoda pro získání viditelnosti
     * @return double obsahující viditelnost
     */
    public double getVisibility() {
        return this.visibility;
    }
    /**
     * Get metoda pro získání rychlosti větru
     * @return double obsahující rychlost větru
     */
    public double getWindSpeed() {
        return this.speed;
    }
    /**
     * Get metoda pro získání oblačnosti
     * @return double obsahující procento oblačnosti
     */
    public double getCloudsAll() {
        return this.all;
    }
    /**
     * Get metoda pro získání času získání dat
     * @return longobsahující čas získání dat ve formátu unix
     */
    public long getDt() {
        return this.dt;
    }
    /**
     * Get metoda pro získání parametru type, nemá žádné využití, je jen pro potřebu serveru
     * @return int obsahující parametr type
     */     
    public int getSysType() {
        return this.type;
    }
    /**
     * Get metoda pro získání parametru sysid, nemá žádné využití, je jen pro potřebu serveru
     * @return int obsahující parametr sysid
     */  
    public int getSysId() {
        return this.sys_id;
    }
    /**
     * Get metoda pro získání parametru sysmessage, nemá žádné využití, je jen pro potřebu serveru
     * @return int obsahující parametr sysmessage
     */  
    public double getSysMessage() {
        return this.message;
    }
    /**
     * Get metoda pro získání kódu země
     * @return String obsahující kód země
     */  
    public String getSysCountry() {
        return this.country;
    }
    /**
     * Get metoda pro získání času východu slunce
     * @return long obsahující čas východu slunce
     */  
    public long getSysSunrise() {
        return this.sunrise * 1000;
    }
    /**
     * Get metoda pro získání času západu slunce
     * @return long obsahující čas západu slunce
     */  
    public long getSysSunset() {
        return this.sunset * 1000;
    }
    /**
     * Get metoda pro získání ID města
     * @return long obsahující ID města
     */  
    public long getId() {
        return this.id;
    }
    /**
     * Get metoda pro získání názvu města
     * @return long obsahující název města
     */  
    public String getName() {
        return this.name;
    }
    /**
     * Get metoda pro získání parametru cod, nemá žádné využití, je jen pro potřebu serveru
     * @return int obsahující parametr cod
     */ 
    public int getCod() {
        return this.cod;
    }
    /**
     * Metoda pro vypsání základních informací o počasí
     * @return String obsahující základních informace o počasí
     */ 
    @Override
    public String toString() {
        String r = "";
        r += "City: " + this.getName() + "\n";
        r += "Country: " + this.getSysCountry() + "\n\n";
        r += "Temperature: " + this.getMainTemp() + " °C\n";
        r += "Pressure: " + this.getMainPressure()+ " hPa\n";
        r += "Weather: " + this.getWeatherDescription() + "\n";
        r += "Wind speed: " + this.getWindSpeed() + " m/s\n";
        return r;
    }
}