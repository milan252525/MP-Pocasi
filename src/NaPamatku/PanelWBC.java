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
import javax.swing.JTextArea;

/**
 *
 * @author bohou
 */
public class PanelWBC {
    private JPanel panelWBC;
    private Handler handler;
    private JTextArea textarea;
    private Point pPoint;
    private MouseEvent pressed;
    private JLabel label;
    private Font font;

    public PanelWBC(Handler handler) {
        this.handler = handler;
        
        panelWBC = new JPanel(null){
    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
};
        panelWBC.setOpaque(false);
        panelWBC.setVisible(true);
        panelWBC.setBounds(0, 0, 250, 460);
       
        
        font = new Font("Arial",Font.PLAIN,18);
        label = new JLabel(); 
        label.setBounds(0, 0,250,200);
        label.setForeground(Color.white);
//    label.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        panelWBC.add(label);  
     
        textarea = new JTextArea();
        textarea.setBackground(new Color(0,0,0,50));
        textarea.setBounds(0,210,250,250);
        textarea.setForeground(Color.white);
//    textarea.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        textarea.setEditable(false);
        textarea.setVisible(true);
        textarea.setFont(font);
        panelWBC.add(textarea);
        panelWBC.addMouseListener(new MouseAdapter()
     {
   @Override
     public void mousePressed(MouseEvent e)
     {
       if (e.getSource() == panelWBC)
       {
         pressed = e;
       }
     }
     @Override
     public void mouseClicked(MouseEvent e){
     //panelS.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
     }
     
     });

   panelWBC.addMouseMotionListener(new MouseMotionAdapter()
     {@Override
     public void mouseDragged(MouseEvent e)
     {
       if (e.getSource() == panelWBC)
       {
         pPoint = panelWBC.getLocation(pPoint);
         int x = pPoint.x - pressed.getX() + e.getX();
         int y = pPoint.y - pressed.getY() + e.getY();
         panelWBC.setLocation(x,y);
       }
     }
     });
    }
    
    

    public JTextArea getTextarea() {
        return textarea;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public JPanel getPanelBackground() {
        return panelWBC;
    }
    
    
}
