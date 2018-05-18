
package pocasi;

import java.awt.Image;
import javax.swing.ImageIcon;

public class IkonyPocasi extends Thread{
   private ImageIcon icon;
   private Image img;
   private Handler handler;
   private String path;
   private final int sirka = 200;
   private final int vyska = 200;
   private boolean running = true;
   

    public IkonyPocasi (Handler handler){
        this.handler = handler;
        path = "src\\icons\\"+handler.getWeatherIcon()+".png";
        icon = new ImageIcon(path);
        img = icon.getImage().getScaledInstance(sirka, vyska,Image.SCALE_DEFAULT);
    }
    
   @Override
    public void run(){
        while(running){
             try {
             Thread.sleep(500);
         } catch (InterruptedException ex) {
             System.out.println("nechce se mu spat");
  
          }
        }
    }

    public Image getImg() {
        return img;
    }

    public int getSirka() {
        return sirka;
    }

    public int getVyska() {
        return vyska;
    }
    
}
