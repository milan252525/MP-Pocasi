package pocasi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Třída pro získání předpovědi pro město/lokaci
 * @author Milan Abrahám, Anh Thain Hoang
 */
public class WeatherForecast {
    /** Klíč sloužící k autentizaci při připojení k serveru */
    private final String API_KEY = "353b379036c2911483cfb9147c1ec9f0";
    
    /** Proměnná pro uložení dat ze žádosti */
    private String response_json, url;
    /** Proměnná pro uložení Kódu odpovědi serveru */
    private int response_code;
    /** Proměnná pro určení zda se vše uložilo správně */
    private boolean funguje = true;
    
    /** Pole polí obsahující informace o počasí pro následující dny */
    public String[][] array;

    /**
     * Konstruktor třídy pro získání předpovědi pro město/lokaci
     * @param city řetězec obsahující název města
     */
    public WeatherForecast(String city){
        try{
            //vytvoreni url
            URL request_url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast?%s&units=metric&APPID=%s&lang=cz", city, API_KEY));
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
        }
        catch(MalformedURLException me){
            this.funguje = false;
        }
        catch(IOException ie){
            this.funguje = false;
        }
        try{
            //ziskani dat z odpovedi
            JSONObject jo = new JSONObject(this.response_json);
            JSONArray ja = jo.getJSONArray("list");

            this.array = new String[10][4];

            int x = 0;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject one_day = ja.getJSONObject(i);
                JSONArray weather = one_day.getJSONArray("weather");
                JSONObject main = one_day.getJSONObject("main");
                JSONObject w = weather.getJSONObject(0);
                double tepl = main.getDouble("temp");
                String popis = w.getString("description");
                String time = new SimpleDateFormat("dd. MM. HH:mm").format((long)one_day.getInt("dt") * 1000);
                String icon = w.getString("icon");

                if(i % 4 == 0){ 
                    this.array[x][0] = Double.toString(tepl);
                    this.array[x][1] = time;
                    this.array[x][2] = popis;
                    this.array[x][3] = icon;
                    //System.out.println(this.array[x][0] + " " + this.array[x][1]);
                    x++;
                }
            }
        }
        catch(JSONException je){
            this.funguje = false;
        }
    }
    
    /**
     * Metoda pro umožnění práce s polem s předpovědí, které tato třída vytváří
     * @param x index vněšího pole
     * @param y index vnotřního pole
     * @return String na indexexh x a y
     */
    public String getArrayValue(int x, int y){
       return this.array[x][y]; 
    }   
}  