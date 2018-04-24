/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author bohou
 */
public class BackgroundImageOnPanel extends JPanel{
private Image image;
private ImageIcon icon;
private Handler handler;

    public BackgroundImageOnPanel(String s,Handler handler) {
        icon = new ImageIcon(s);
        image = icon.getImage();
        this.handler = handler;
    }
    
    
    @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
        g.drawImage(image, 0, 0,handler.getSirka(),handler.getVyska(), null);
//        System.out.println(handler.getSirka());
        
   }
}
