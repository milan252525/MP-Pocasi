/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class Okno extends JFrame{
   private String nadpis;
   private int vyska,sirka;
   private JFrame frame;
   private JPanel mainPanel;
   private JPanel panel1;
   private JPanel panel2;
   private JButton button;
   
   private JTextArea textarea;

   private JTextField textfield;
   
    WeatherByCity x;
    
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
     frame.setResizable(false);
     
     
     mainPanel = new JPanel(new BorderLayout());
     mainPanel.setPreferredSize(new Dimension(sirka,vyska));
         
     panel1 = new JPanel(new FlowLayout());     
     panel1.setBackground(Color.red);
     panel1.setPreferredSize(new Dimension(sirka,vyska/2));
     mainPanel.add(panel1,BorderLayout.NORTH);
     
     textfield = new JTextField();
     textfield.setPreferredSize(new Dimension(100,20));
     panel1.add(textfield);
    
     button = new JButton("vyhledat");
     button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
              try {
            x = new WeatherByCity(textfield.getText());
            System.out.println(x.toString());
            
            textarea.setText(x.toString());
            
        } catch (IOException ex) {
            System.out.println("IOException:\n" + ex);
        }
         }
     });
     panel1.add(button);

    panel2 = new JPanel(new SpringLayout()); 
    panel2.setBackground(Color.green);
    panel2.setPreferredSize(new Dimension(sirka,vyska/2));
    mainPanel.add(panel2,BorderLayout.SOUTH);
    
   
    textarea = new JTextArea();
    textarea.setOpaque(false);
    panel2.add(textarea);
         

     frame.add(mainPanel);
     frame.pack();
     
     
     }
}
