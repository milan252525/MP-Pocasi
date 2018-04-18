/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;



public class Okno extends JFrame{
   private String nadpis;
   private int vyska,sirka;
   private JFrame frame;
   private JPanel backgroundPanel;

    
    public Okno(String nadpis, int sirka, int vyska){
        this.nadpis = nadpis;
        this.sirka = sirka;
        this.vyska = vyska;
        

        udělatDisplay();
       
    }
    
    
     public void udělatDisplay(){
     frame = new JFrame(nadpis); 
     frame.setSize(sirka, vyska);
     frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
     frame.setVisible(true);
     frame.setLocationRelativeTo(null);
     frame.setResizable(true); 
     
     
     
     backgroundPanel = new JPanel(new GridBagLayout());
     backgroundPanel.setBackground(Color.blue);
     frame.setContentPane(backgroundPanel);   

     frame.add(new MujPanel(sirka,vyska).getPanel());
   
     frame.pack();

     
     } 
}

