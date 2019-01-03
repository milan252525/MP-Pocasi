/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author bohou
 */
public class Handler {
   private static Pocasi pocasi;
    
    public Handler(Pocasi pocasi){
           this.pocasi = pocasi;
    }

    public Pocasi getPocasi() {
        return pocasi;
    }
    

    public void setPocasi(Pocasi pocasi) {
        this.pocasi = pocasi;
    }

    public WeatherByCity getWeatherByCity(){
        return pocasi.getWeatherByCity();
    }
    
    public String getWeatherByCityIcon(){
        return pocasi.getWeatherByCity().getWeatherIcon();
    }
    
    public WeatherForecast getWeatherForecast(){
        return pocasi.getWeatherForecast();
    }
    
    public String getWeatherForecastIcon(int x){
        return pocasi.getWeatherForecast().getArrayValue(x,3);
    }
    
    public WeatherByCoordinates getWeatherByCoordinates(){
       return pocasi.getWeatherByCoordinates();
    }
    
      public String getWeatherByCoordinatesIcon(){
        return pocasi.getWeatherByCoordinates().getWeatherIcon();
    }
}
