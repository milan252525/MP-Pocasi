/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


/**
 *
 * @author bohou
 */
public class MujPanel extends Thread{
   private JPanel panel1;
   private JButton button;
   private JLabel label,labelcas,label1,label2,label3;
   private IkonyPocasi  ikony;
   
   
   private Handler handler;
   private Image img;
   
   private JTextArea textarea;

   private Font font;
   private JTextField textfield;
   
   private WeatherByCity weatherByCity;
   private WeatherForecast weatherForecast;
   
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
      
     font = new Font("Arial",Font.PLAIN,18);
     textfield = new JTextField();
     textfield.setBounds(350, 80, 100, 30);
     panel1.add(textfield);
     
     button = new JButton("Search");
     button.setBounds(350 + 100, 80, 100, 30);
     button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             try{
                weatherByCity = new WeatherByCity(textfield.getText());
                weatherForecast = new WeatherForecast(textfield.getText());
                System.out.println(weatherByCity.toString());
                textarea.setText(weatherByCity.toString());
                ikony = new IkonyPocasi(handler);
                img = ikony.getImg();
                label.setIcon( new ImageIcon(img));
                label.setBounds(0, 0, ikony.getSirkaVelkychIkon(), ikony.getVyskaVelkychIkon());
                
                
                label1.setIcon(new ImageIcon(ikony.getIkonyForecast(0)));
                label1.setText("<html><div style='text-align: center;'>" + "Temperature: "+weatherForecast.getArrayValue(0, 0)+ 
                                                                            "<br/>" + "Time: "+ weatherForecast.getArrayValue(0, 1)+
                                                                            "<br/>" +  weatherForecast.getArrayValue(0, 2) + "</html>");
                label1.setHorizontalTextPosition(JLabel.CENTER);
                label1.setVerticalTextPosition(JLabel.BOTTOM);
                
                label2.setIcon(new ImageIcon(ikony.getIkonyForecast(1)));
                label2.setText("<html><div style='text-align: center;'>" + "Temperature: "+weatherForecast.getArrayValue(1, 0)+ 
                                                                            "<br/>" + "Time: "+weatherForecast.getArrayValue(1, 1)+ 
                                                                            "<br/>" + weatherForecast.getArrayValue(1, 2)+ "</html>");
                label2.setHorizontalTextPosition(JLabel.CENTER);
                label2.setVerticalTextPosition(JLabel.BOTTOM);
                
                label3.setIcon(new ImageIcon(ikony.getIkonyForecast(2)));
                label3.setText("<html><div style='text-align: center;'>" + "Temperature: "+weatherForecast.getArrayValue(2, 0)+ 
                                                                            "<br/>" + "Time: "+weatherForecast.getArrayValue(2, 1)+ 
                                                                            "<br/>" + weatherForecast.getArrayValue(2, 2)+ "</html>");
                label3.setHorizontalTextPosition(JLabel.CENTER);
                label3.setVerticalTextPosition(JLabel.BOTTOM);
             }
             catch(Exception exc){
                 textarea.setText(textfield.getText() + " nenalezeno!\n");
                 img = null;
                 label1.setIcon(null);
                 label2.setIcon(null);
                 label3.setIcon(null);
                 label1.setText(null);
                 label2.setText(null);
                 label3.setText(null);
                 label.setIcon(null);
             }
         }
     });
     
    panel1.add(button);

    label = new JLabel(); 
//    label.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
    panel1.add(label);  
     
    textarea = new JTextArea();
    textarea.setOpaque(false);
    textarea.setBounds(10,250,250,200);
//    textarea.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
    textarea.setEditable(false);
    textarea.setVisible(true);
    textarea.setFont(font);
    panel1.add(textarea);
      
     labelcas = new JLabel();
     labelcas.setBounds(750, 60, 150, 100);
     labelcas.setFont(new Font ("Calibri",Font.BOLD,50));
//     labelcas.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     panel1.add(labelcas);     
     
     
     //weather forecast
     label1 = new JLabel();
     label1.setBounds(250, 250,200,200);
//     label1.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     label1.setFont(font);
     panel1.add(label1);
     
     label2 = new JLabel();
     label2.setBounds(450, 250,200,200);
//     label2.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     label2.setFont(font);
     panel1.add(label2);  
     
     label3 = new JLabel();
     label3.setBounds(650, 250,200,200);
//     label3.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     label3.setFont(font);
     panel1.add(label3);  
     
  
     
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

    public WeatherByCity getWeatherByCity() {
        return weatherByCity;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public JLabel getLabelcas() {
        return labelcas;
    }
}
