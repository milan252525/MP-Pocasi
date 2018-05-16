/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anh-thai.hoang
 */
public class Cas extends Thread{
     int hodiny, minuty, sekundy;
       
     boolean running = true;
     
    @Override
    public void run(){
        
    while(running){
    Calendar c = GregorianCalendar.getInstance();      
    hodiny = c.get(Calendar.HOUR_OF_DAY);
    minuty = c.get(Calendar.MINUTE);
    sekundy = c.get(Calendar.SECOND);
         try {
             Thread.sleep(1000);
         } catch (InterruptedException ex) {
             System.out.println("nechce se mu spat");
         }
    System.out.println("hod: " + hodiny + " min: " + minuty + " sek: " + sekundy);
          }
        
    }

    public int getHodiny() {
        return hodiny;
    }

    public int getMinuty() {
        return minuty;
    }

    public int getSekundy() {
        return sekundy;
    }
    
    
}
