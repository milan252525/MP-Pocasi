/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NaPamatku;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author bohou
 */
public class PanelSearch {
    private JButton button;
    private JPanel panelSearch;
    private Font font;
    private JTextField textfield;
    private IkonyPocasi  ikony;
    private WeatherByCity weatherByCity;
    private WeatherForecast weatherForecast;
    private Point pPoint;
    private MouseEvent pressed;
    
    private Handler handler;

    public PanelSearch(Handler handler) {
        this.handler = handler;
        
        
        panelSearch = new JPanel(null){
       @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
};    
        panelSearch.setBounds(0, 0, 250,50);
        panelSearch.setOpaque(false); 
        panelSearch.setVisible(true);
        
        
        font = new Font("Arial",Font.PLAIN,18);
        textfield = new JTextField();
        textfield.setBounds(25, 10, 100, 30);
        panelSearch.add(textfield);
     
        button = new JButton("Search");
        button.setBounds(125, 10, 100, 30);
        button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             try{
//                new pocasi.Mapa().setVisible(true);
                weatherByCity = new WeatherByCity(textfield.getText());
                weatherForecast = new WeatherForecast(textfield.getText());
                handler.getTextArea().setText(weatherByCity.toString());
                ikony = new IkonyPocasi(handler);

                handler.getLabel().setIcon(ikony.getIcon());
                handler.getLabel().setBounds(0, 0, ikony.getSirkaVelkychIkon(), ikony.getVyskaVelkychIkon());
                
                
                handler.getLabel1().setIcon(ikony.getIkonyForecast(0));
                handler.getLabel1().setText("<html><div style='text-align: center;'>" + "Temperature: "+weatherForecast.getArrayValue(0, 0)+ 
                                                                            "<br/>" + "Time: "+ weatherForecast.getArrayValue(0, 1)+
                                                                            "<br/>" +  weatherForecast.getArrayValue(0, 2) + "</html>");
                handler.getLabel1().setHorizontalTextPosition(JLabel.CENTER);
                handler.getLabel1().setVerticalTextPosition(JLabel.BOTTOM);
                
                handler.getLabel2().setIcon(ikony.getIkonyForecast(1));
                handler.getLabel2().setText("<html><div style='text-align: center;'>" + "Temperature: "+weatherForecast.getArrayValue(1, 0)+ 
                                                                            "<br/>" + "Time: "+weatherForecast.getArrayValue(1, 1)+ 
                                                                            "<br/>" + weatherForecast.getArrayValue(1, 2)+ "</html>");
                handler.getLabel2().setHorizontalTextPosition(JLabel.CENTER);
                handler.getLabel2().setVerticalTextPosition(JLabel.BOTTOM);
                
                handler.getLabel3().setIcon(ikony.getIkonyForecast(2));
                handler.getLabel3().setText("<html><div style='text-align: center;'>" + "Temperature: "+weatherForecast.getArrayValue(2, 0)+ 
                                                                            "<br/>" + "Time: "+weatherForecast.getArrayValue(2, 1)+ 
                                                                            "<br/>" + weatherForecast.getArrayValue(2, 2)+ "</html>");
                handler.getLabel3().setHorizontalTextPosition(JLabel.CENTER);
                handler.getLabel3().setVerticalTextPosition(JLabel.BOTTOM);
             }
             catch(Exception exc){
                 handler.getTextArea().setText(textfield.getText() + "\n" + " NOT FOUND!\n");
                 handler.getLabel1().setIcon(null);
                 handler.getLabel2().setIcon(null);
                 handler.getLabel3().setIcon(null);
                 handler.getLabel1().setText(null);
                 handler.getLabel2().setText(null);
                 handler.getLabel3().setText(null);
                 handler.getLabel().setIcon(null);
             }
         }
     });     
    panelSearch.add(button);
    
    panelSearch.addMouseListener(new MouseAdapter()
     {
   @Override
     public void mousePressed(MouseEvent e)
     {
       if (e.getSource() == panelSearch)
       {
         pressed = e;
       }
     }
     @Override
     public void mouseClicked(MouseEvent e){
     //panelS.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     }
     
     });

   panelSearch.addMouseMotionListener(new MouseMotionAdapter()
     {@Override
     public void mouseDragged(MouseEvent e)
     {
       if (e.getSource() == panelSearch)
       {
         pPoint = panelSearch.getLocation(pPoint);
         int x = pPoint.x - pressed.getX() + e.getX();
         int y = pPoint.y - pressed.getY() + e.getY();
         panelSearch.setLocation(x,y);
       }
     }
     });
   
    }

    public JPanel getPanelBackground() {
        return panelSearch;
    }

    public WeatherByCity getWeatherByCity() {
        return weatherByCity;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }
    
    

}
