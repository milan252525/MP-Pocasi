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
   private Okno okno;
    
    public Handler(Okno okno){
           this.okno = okno;
    }

    public Okno getOkno() {
        return okno;
    }

    public void setOkno(Okno okno) {
        this.okno = okno;
    }

    public WeatherByCity getWeather(){
        return okno.getMujPanel().getWeather();
    }
    
    public String getWeatherIcon(){
    return okno.getMujPanel().getWeather().getWeatherIcon();
    }
    
    public int getSirka(){
    return okno.getSirka();
    }
    
    public int getVyska(){
    return okno.getVyska();
    }  

}
