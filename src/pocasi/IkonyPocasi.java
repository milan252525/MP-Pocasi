package pocasi;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Třída pro získávání ikon počasí
 * @author Milan Abrahám, Anh Thai Hoang
 */
public class IkonyPocasi {
    /** Objekt InputStream */
    private InputStream stream;
    /** Objekt BufferedImage pro ikonu */
    private BufferedImage img;
    /** Objekt ImageIcon pro ikonu */
    private ImageIcon icon;
    /** Handler pro nastavování ikon */
    private final Handler handler;
    /** Šířka velkých ikon počasí */
    private final int sirkaVelkychIkon = 100;
    /** Výška velkých ikon počasí */
    private final int vyskaVelkychIkon = 100;
    /** Šířka malých ikon počasí */
    private final int sirkaMalychIkon = 50;
    /** Výška malých ikon počasí */
    private final int vyskaMalychIkon = 50; 
    /** Pole ikon pro předpověď */
    private ImageIcon[] ikonyForecast;
   
    /** 
    * Kontruktor, nastavení handleru
    * @param handler handler
    */
    public IkonyPocasi (Handler handler){
        this.handler = handler;
    }
    
    /** 
    * Ikona pro vyhledávání počasí podle města
    * @return objekt ImageIcon obsahující ikonu
    */
    public ImageIcon getIconWeatherByCity(){
        try {
             stream = getClass().getClassLoader().getResourceAsStream("icons/"+ handler.getWeatherByCityIcon() +".png");
             img = ImageIO.read(stream);
             Image image = img.getScaledInstance(sirkaVelkychIkon, vyskaVelkychIkon,Image.SCALE_SMOOTH);
             icon = new ImageIcon(image);
        } catch (IOException ex) {
            System.out.println("NO IMAGE");
        }
        return icon;
    }
    
    /** 
    * Ikona pro vyhledávání počasí podle souřadnic
    * @return obkejt ImageIcon obsahující ikonu
    */
    public ImageIcon getIconWeatherByCoordinates() {
        try {
             stream = getClass().getClassLoader().getResourceAsStream("icons/"+ handler.getWeatherByCoordinatesIcon() +".png");
             img = ImageIO.read(stream);
             Image image = img.getScaledInstance(sirkaVelkychIkon, vyskaVelkychIkon,Image.SCALE_SMOOTH);
             icon = new ImageIcon(image);
        } catch (IOException ex) {
            System.out.println("NO IMAGE");
        }
        return icon;
    }
    
    /** 
    * Ikona pro předpověď počasí
    * @param x doba pro kterou chceme ikonu
    * @return objekt ImageIcon obsahující ikonu
    */
    public ImageIcon getIkonyForecast(int x) {       
        ikonyForecast = new ImageIcon[6];
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
        return ikonyForecast[x];
    }
    
    /**
     * Metoda pro získání šířky velkých ikon
     * @return int obsahující šířku velkých ikon
     */
    public int getSirkaVelkychIkon() {
        return sirkaVelkychIkon;
    }
    /**
     * Metoda pro získání výšky velkých ikon
     * @return int obsahující výškuu velkých ikon
     */
    public int getVyskaVelkychIkon() {
        return vyskaVelkychIkon;
    }
    /**
     * Metoda pro získání šířky malých ikon
     * @return int obsahující šířku malých ikon
     */
    public int getSirkaMalychIkon() {
        return sirkaMalychIkon;
    }
     /**
     * Metoda pro získání výšky malých ikon
     * @return int obsahující výšku malých ikon
     */
    public int getVyskaMalychIkon() {
        return vyskaMalychIkon;
    }  
}
