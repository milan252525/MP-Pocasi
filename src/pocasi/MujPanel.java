/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


/**
 *
 * @author bohou
 */
public class MujPanel {
   private JPanel mainPanel,panel1;
   private JButton button;
   private JLabel label;
   private URLImage ui;
   
   private Handler handler;
   private Image img;
   
   private JTextArea textarea;

   private JTextField textfield;
   private WeatherByCity weather;
   private int sirka, vyska;
   
    public MujPanel(int sirka,int vyska) {
      this.sirka = sirka;
      this.vyska = vyska;
      handler = new Handler(this);
      img  = null;
      
      mainPanel = new JPanel();
      mainPanel.setBorder(new TitledBorder("Počasí"));
      mainPanel.setPreferredSize(new Dimension(sirka,vyska));
      mainPanel.setMaximumSize(new Dimension(sirka,vyska));
      mainPanel.setMinimumSize(new Dimension(sirka,vyska));
      
      panel1 = new JPanel(null);
      panel1.setPreferredSize(new Dimension(sirka - 50,vyska));
      panel1.setMaximumSize(new Dimension(sirka - 50,vyska));
      panel1.setMinimumSize(new Dimension(sirka - 50,vyska));
      mainPanel.add(panel1);
      
     textfield = new JTextField();
     textfield.setBounds((sirka - 200)/2, 0, 100, 30);
     panel1.add(textfield);
     
     button = new JButton("vyhledat");
     button.setBounds((sirka - 200)/2 + 100, 0, 100, 30);
     button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
              try {
            weather = new WeatherByCity(textfield.getText());
            System.out.println(weather.toString());
            
            textarea.setText(weather.toString()); 
            ui = new URLImage(handler);
            
            img = ui.getImg();
            label.setIcon( new ImageIcon(img));
            
            
    
            
        } catch (IOException ex) {
            System.out.println("IOException:\n" + ex);
        }
         }
     });
     
    panel1.add(button);

        
   
    textarea = new JTextArea();
    textarea.setOpaque(false);
    textarea.setBounds(0,vyska/2,vyska/2,200);
    textarea.setFont(new Font("Arial",Font.PLAIN,18));
    panel1.add(textarea);
         
    label = new JLabel();
    label.setBounds(0, 0, 200, 200);
    panel1.add(label);
    } 

    public JPanel getPanel() {
        return mainPanel;
    }

    public WeatherByCity getWeather() {
        return weather;
    }

}
