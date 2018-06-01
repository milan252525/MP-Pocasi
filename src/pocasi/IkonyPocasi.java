
package pocasi;

import java.awt.Image;
import javax.swing.ImageIcon;

public class IkonyPocasi {
   private ImageIcon icon,icon2;
   private Image img;
   private Handler handler;
   private String path,path2;
   private final int sirkaVelkychIkon = 200;
   private final int vyskaVelkychIkon = 200;
   private final int sirkaMalychIkon = 50;
   private final int vyskaMalychIkon = 50; 
   private Image[] ikonyForecast;
   

    public IkonyPocasi (Handler handler){
        this.handler = handler;
        path = "src\\icons\\"+handler.getWeatherByCityIcon()+".png";
        icon = new ImageIcon(path);
        img = icon.getImage().getScaledInstance(sirkaVelkychIkon, vyskaVelkychIkon,Image.SCALE_DEFAULT);
         ikonyForecast = new Image[3];
        
        for (int i = 0; i < ikonyForecast.length; i++) {
            path2 = "src\\icons\\"+handler.getWeatherForecastIcon(i)+".png";
            icon2 = new ImageIcon(path2);
            ikonyForecast[i] = icon2.getImage().getScaledInstance(sirkaMalychIkon, vyskaMalychIkon,Image.SCALE_DEFAULT);
        } 
    }
    

    public Image getImg() {
        return img;
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

    public Image getIkonyForecast(int x) {
        return ikonyForecast[x];
    }
    
}
