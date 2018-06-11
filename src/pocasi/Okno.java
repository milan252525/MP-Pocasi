/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sun.security.x509.EDIPartyName;



public class Okno extends JFrame{
   private int vyska,sirka;
   private JFrame frame;
   private JPanel backgroundImageOnPanel;
   private MujPanel mujpanel;
   private Handler handler;
    
    public Okno(int sirka, int vyska){
        this.sirka = sirka;
        this.vyska = vyska;

        udělatDisplay();
       
    }
    
    
     public void udělatDisplay(){
     frame = new JFrame(); 
     frame.setSize(sirka, vyska);
     frame.setTitle("Weather Forecast");
     frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
     frame.setVisible(true);
     frame.setLocationRelativeTo(null);
     
     handler = new Handler(this);
     mujpanel = new MujPanel(handler,sirka,vyska);   
     mujpanel.start();
     
     //přidání pozadí jako obrázek
     backgroundImageOnPanel = new BackgroundImageOnPanel("src\\obrazky\\heaven.jpg",handler);
     backgroundImageOnPanel.setLayout(new GridBagLayout());
     backgroundImageOnPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
     frame.setContentPane(backgroundImageOnPanel);
     
     
     //zvětšení pozadí
     frame.getContentPane().addComponentListener(new ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent e){
           Dimension newSize = e.getComponent().getBounds().getSize(); 
           vyska = (int)newSize.getHeight();
           sirka = (int)newSize.getWidth();
           backgroundImageOnPanel.repaint();
         }
});
     backgroundImageOnPanel.add(mujpanel.getPanel());
     frame.pack();

     }     

    public int getVyska() {
        return vyska;
    }

    public int getSirka() {
        return sirka;
    }
     
    public MujPanel getMujPanel(){
        return mujpanel;
    }

    
    
}

