
package pocasi;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class URLImage {
   private URL url;
   private Image img;
   private Handler handler;
   

    public URLImage(Handler handler) throws MalformedURLException, IOException {
        this.handler = handler;
        url = new URL("http://openweathermap.org/img/w/" +  handler.getWeatherIcon() +".png");
        img = ImageIO.read(url);
       
    }

    public Image getImg() {
        return img;
    }
    
}
