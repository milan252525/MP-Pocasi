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
   private MujPanel mujPanel;
    
    public Handler(MujPanel mujPanel){
           this.mujPanel = mujPanel;
    
    }

    public MujPanel getMujpanel() {
        return mujPanel;
    }

    public void setMujpanel(MujPanel mujpanel) {
        this.mujPanel = mujpanel;
    }
    
    public  WeatherByCity getWeatherByCity(){
       return mujPanel.getWeather();
    }

    
}
