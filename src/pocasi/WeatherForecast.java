package pocasi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class WeatherForecast {
 private final String API_KEY = "353b379036c2911483cfb9147c1ec9f0";
    
    private String response_json, url;
    private int response_code;
    private boolean funguje = true;
    
    //promenne pro data ze zadosti
    private String[][] array;


    public WeatherForecast(String city){
        try{
            //vytvoreni url
            URL request_url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&APPID=%s", city, API_KEY));
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
                int date = one_day.getInt("dt");
                java.util.Date time = new java.util.Date((long)date*1000);
                String icon = w.getString("icon");

                if(i % 8 == 0){ 
                    this.array[x][0] = Double.toString(tepl);
                    this.array[x][1] = Double.toString(time.getHours());
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
    
    public String getArrayValue(int x, int y){
       return this.array[x][y]; 
    }
//    public static void main(String[] args) throws IOException{
//        WeatherForecast x = new WeatherForecast("Plzeň");
//        System.out.println(x.response_json);
//        System.out.println(Arrays.deepToString(x.array));
//        System.out.println(x.array[3][3]);
//    }   
}  