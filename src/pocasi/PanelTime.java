/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;


/**
 *
 * @author bohou
 */
public class PanelTime extends Thread{
   private JPanel panelTime;
   private JLabel labelcas;
   private Handler handler;
   private int hodiny, minuty, sekundy;
   private boolean running = true;     
   private Point pPoint;
   private MouseEvent pressed;
   
   
    public PanelTime(Handler handler) {
      this.handler = handler;
           
      panelTime = new JPanel(null){
          @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
};
      panelTime.setBounds(0, 0, 130, 40);
      panelTime.setOpaque(false);
      panelTime.setVisible(true);
      panelTime.addMouseListener(new MouseAdapter()
     {
   @Override
     public void mousePressed(MouseEvent e)
     {
       if (e.getSource() == panelTime)
       {
         pressed = e;
       }
     }
     @Override
     public void mouseClicked(MouseEvent e){
     //panelTime.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     }
     });

   panelTime.addMouseMotionListener(new MouseMotionAdapter()
     {@Override
     public void mouseDragged(MouseEvent e)
     {
       if (e.getSource() == panelTime)
       {
         pPoint = panelTime.getLocation(pPoint);
         int x = pPoint.x - pressed.getX() + e.getX();
         int y = pPoint.y - pressed.getY() + e.getY();
         panelTime.setLocation(x,y);
       }
     }
     });
     labelcas = new JLabel();
     labelcas.setOpaque(false);
     labelcas.setBounds(0, 0, 130, 40);
     labelcas.setForeground(Color.white);
     labelcas.setFont(new Font ("Calibri",Font.BOLD,50));
//     labelcas.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     panelTime.add(labelcas);

    }
    //beh casu
    @Override
    public void run(){
        while(running){
            Calendar c = GregorianCalendar.getInstance();      
            hodiny = c.get(Calendar.HOUR_OF_DAY);
            minuty = c.get(Calendar.MINUTE);
            sekundy = c.get(Calendar.SECOND);
            labelcas.setText(String.format("%02d:%02d",hodiny,minuty));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("nechce se mu spat");
            }     
        }
    }
    

    public JPanel getPanelBackground() {
        return panelTime;
    }

    public JLabel getLabelcas() {
        return labelcas;
    }
}
