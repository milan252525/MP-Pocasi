/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


/**
 *
 * @author bohou
 */
public class MujPanel extends Thread{
   private JPanel panel1;
   private JButton button;
   private JLabel label,labelcas;
   private IkonyPocasi  ikony;
   
   
   private Handler handler;
   private Image img;
   
   private JTextArea textarea;

   private JTextField textfield;
   private WeatherByCity weather;
   private int sirka, vyska;

   
    int hodiny, minuty, sekundy;
     boolean running = true;
   
    public MujPanel(Handler handler,int sirka,int vyska) {
      this.sirka = sirka;
      this.vyska = vyska;
      this.handler = handler;
      
      img  = null;
           
      panel1 = new JPanel(null);
      panel1.setPreferredSize(new Dimension(sirka,vyska));
      panel1.setMaximumSize(new Dimension(sirka,vyska));
      panel1.setMinimumSize(new Dimension(sirka,vyska));
      panel1.setBackground(Color.CYAN);
      
     textfield = new JTextField();
     textfield.setBounds((sirka - 200)/2, 0, 100, 30);
     panel1.add(textfield);
     
     button = new JButton("vyhledat");
     button.setBounds((sirka - 200)/2 + 100, 0, 100, 30);
     button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             try{
                weather = new WeatherByCity(textfield.getText());
                System.out.println(weather.toString());
                textarea.setText(weather.toString());
                ikony = new IkonyPocasi (handler);
                img = ikony.getImg();
                label.setIcon( new ImageIcon(img));
                label.setBounds(0, 0, ikony.getSirka(), ikony.getVyska());
             }
             catch(Exception exc){
                 textarea.setText(textfield.getText() + " nenalezeno!\n");
                 img = null;
                 label.setIcon(null);
             }
         }
     });
     
    panel1.add(button);

    label = new JLabel(); 
    panel1.add(label);  
     
    textarea = new JTextArea();
    textarea.setOpaque(false);
    textarea.setBounds(0,vyska/2,vyska/2,200);
    textarea.setEditable(false);
    textarea.setVisible(true);
    textarea.setFont(new Font("Arial",Font.PLAIN,18));
    panel1.add(textarea);
      
     labelcas = new JLabel();
     labelcas.setBounds(750, 0, 150, 100);
     labelcas.setFont(new Font ("Calibri",Font.BOLD,50));
     panel1.add(labelcas);  
      
    }
    //beh casu
    @Override
    public void run(){
        while(running){
            Calendar c = GregorianCalendar.getInstance();      
            hodiny = c.get(Calendar.HOUR_OF_DAY);
            minuty = c.get(Calendar.MINUTE);
            sekundy = c.get(Calendar.SECOND);
            labelcas.setText(hodiny + ":" + minuty );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("nechce se mu spat");
            }     
        }
    }
    

    public JPanel getPanel() {
        return panel1;
    }

    public WeatherByCity getWeather() {
        return weather;
    }

    public JLabel getLabelcas() {
        return labelcas;
    }
}
