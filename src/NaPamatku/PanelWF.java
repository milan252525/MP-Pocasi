/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NaPamatku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author bohou
 */
public class PanelWF {
    private Handler handler;
    private JPanel panelWF;
    private Font font;
    private JLabel label1,label2,label3;
    private Point pPoint;
    private MouseEvent pressed;

    public PanelWF(Handler handler) {
        this.handler = handler;
        
        panelWF = new JPanel(null){
    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
};
        panelWF.setOpaque(false);
        panelWF.setVisible(true);
        panelWF.setBounds(0, 0, 750, 200);
       // panelWF.setPreferredSize(new Dimension(750,200));
        
        
        font = new Font("Arial",Font.PLAIN,18);
        label1 = new JLabel();
        label1.setOpaque(false);
        label1.setBounds(0, 0,200,200);
        label1.setForeground(Color.white);
//     label1.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        label1.setFont(font);
        panelWF.add(label1);
     
        label2 = new JLabel();
        label2.setOpaque(false);
        label2.setBounds(250, 0,200,200);
        label2.setForeground(Color.white);
//     label2.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        label2.setFont(font);
        panelWF.add(label2);  
     
        label3 = new JLabel();
        label3.setOpaque(false);
        label3.setBounds(500, 0,200,200);
        label3.setForeground(Color.white);
//     label3.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        label3.setFont(font);
        panelWF.add(label3);  
        panelWF.addMouseListener(new MouseAdapter()
     {
   @Override
     public void mousePressed(MouseEvent e)
     {
       if (e.getSource() == panelWF)
       {
         pressed = e;
       }
     }
     @Override
     public void mouseClicked(MouseEvent e){
     //panelS.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     }
     
     });

   panelWF.addMouseMotionListener(new MouseMotionAdapter()
     {@Override
     public void mouseDragged(MouseEvent e)
     {
       if (e.getSource() == panelWF)
       {
         pPoint = panelWF.getLocation(pPoint);
         int x = pPoint.x - pressed.getX() + e.getX();
         int y = pPoint.y - pressed.getY() + e.getY();
         panelWF.setLocation(x,y);
       }
     }
     });
   
    }
    
   

    public JLabel getLabel1() {
        return label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JLabel getLabel3() {
        return label3;
    }
    
    public JPanel getPanelBackground() {
        return panelWF;
    }
}
