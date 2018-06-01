/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;
/**
 *
 * @author bohou
 */
public class Handler {
   private static Okno okno;
    
    public Handler(Okno okno){
           this.okno = okno;
    }

    public Okno getOkno() {
        return okno;
    }

    public void setOkno(Okno okno) {
        this.okno = okno;
    }

    public WeatherByCity getWeatherByCity(){
        return okno.getMujPanel().getWeatherByCity();
    }
    
    public String getWeatherByCityIcon(){
        return okno.getMujPanel().getWeatherByCity().getWeatherIcon();
    }
    
    public WeatherForecast getWeatherForecast(){
        return okno.getMujPanel().getWeatherForecast();
    }
    
    public String getWeatherForecastIcon(int x){
    return okno.getMujPanel().getWeatherForecast().getArrayValue(x,3);
    }
    
    public int getSirka(){
    return okno.getSirka();
    }
    
    public int getVyska(){
    return okno.getVyska();
    } 
     
}
