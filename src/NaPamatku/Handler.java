/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NaPamatku;

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
        return pocasi.getPanelSearch().getWeatherByCity();
    }
    
    public String getWeatherByCityIcon(){
        return pocasi.getPanelSearch().getWeatherByCity().getWeatherIcon();
    }
    
    public WeatherForecast getWeatherForecast(){
        return pocasi.getPanelSearch().getWeatherForecast();
    }
    
    public String getWeatherForecastIcon(int x){
        return pocasi.getPanelSearch().getWeatherForecast().getArrayValue(x,3);
    }
    
    public JLabel getLabel(){
        return pocasi.getPanelWBC().getLabel();
    }
    
    public JTextArea getTextArea(){
        return pocasi.getPanelWBC().getTextarea();
    }
    
    public JLabel getLabel1(){
        return pocasi.getPanelWF().getLabel1();
    }
    
    public JLabel getLabel2(){
        return pocasi.getPanelWF().getLabel2();
    }
    
    public JLabel getLabel3(){
        return pocasi.getPanelWF().getLabel3();
    }
}
