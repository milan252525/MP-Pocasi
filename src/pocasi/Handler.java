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
   private static Manager manager;
    
    public Handler(Manager manager){
           this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
    

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public WeatherByCity getWeatherByCity(){
        return manager.getPanelSearch().getWeatherByCity();
    }
    
    public String getWeatherByCityIcon(){
        return manager.getPanelSearch().getWeatherByCity().getWeatherIcon();
    }
    
    public WeatherForecast getWeatherForecast(){
        return manager.getPanelSearch().getWeatherForecast();
    }
    
    public String getWeatherForecastIcon(int x){
        return manager.getPanelSearch().getWeatherForecast().getArrayValue(x,3);
    }
    
    public JLabel getLabel(){
        return manager.getPanelWBC().getLabel();
    }
    
    public JTextArea getTextArea(){
        return manager.getPanelWBC().getTextarea();
    }
    
    public JLabel getLabel1(){
        return manager.getPanelWF().getLabel1();
    }
    
    public JLabel getLabel2(){
        return manager.getPanelWF().getLabel2();
    }
    
    public JLabel getLabel3(){
        return manager.getPanelWF().getLabel3();
    }
}
