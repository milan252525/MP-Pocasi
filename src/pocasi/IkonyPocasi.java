
package pocasi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IkonyPocasi {
   private InputStream stream;
   private BufferedImage img;
   private ImageIcon icon;
   private Handler handler;
   private final int sirkaVelkychIkon = 200;
   private final int vyskaVelkychIkon = 200;
   private final int sirkaMalychIkon = 50;
   private final int vyskaMalychIkon = 50; 
   private ImageIcon[] ikonyForecast;
   

    public IkonyPocasi (Handler handler){
        this.handler = handler;
        
         
        try {
             stream = getClass().getClassLoader().getResourceAsStream("icons/"+ handler.getWeatherByCityIcon() +".png");
             img = ImageIO.read(stream);
             Image image = img.getScaledInstance(sirkaVelkychIkon, vyskaVelkychIkon,Image.SCALE_SMOOTH);
             icon = new ImageIcon(image);
            
           
           
        } catch (IOException ex) {
            System.out.println("NO IMAGE");
        }
            
        ikonyForecast = new ImageIcon[3];
        for (int i = 0; i < ikonyForecast.length; i++) {
        try {
            stream = getClass().getClassLoader().getResourceAsStream("icons/"+ handler.getWeatherForecastIcon(i) +".png");
            img = ImageIO.read(stream);
            Image image = img.getScaledInstance(sirkaMalychIkon, vyskaMalychIkon,Image.SCALE_DEFAULT);
            ikonyForecast[i] = new ImageIcon(image);
        } catch (IOException ex) {
            System.out.println("NO IMAGE");
        }
        } 
    }

    public ImageIcon getIcon() {
        return icon;
    }
    
    public int getSirkaVelkychIkon() {
        return sirkaVelkychIkon;
    }

    public int getVyskaVelkychIkon() {
        return vyskaVelkychIkon;
    }

    public int getSirkaMalychIkon() {
        return sirkaMalychIkon;
    }

    public int getVyskaMalychIkon() {
        return vyskaMalychIkon;
    }

    public ImageIcon getIkonyForecast(int x) {
        return ikonyForecast[x];
    }
    
}
