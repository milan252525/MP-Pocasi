/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pocasi;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Třída pro vykreslení uživatelského rozhraní
 * @author Milan Abrahám, Anh Thai Hoang
 */
public class PocasiMain extends javax.swing.JFrame {
    /** Objekt s výsledkem vyhledávání počasí podle města */
    WeatherByCity weatherByCity;
    /** Objekt s výsledkem předpovědi počasí podle města */
    WeatherForecast weatherForecast;
    /** Objekt s výsledkem vyhledávání počasí podle souřadnic */
    WeatherByCoordinates weatherByCoordinates;
    /** Handler */
    Handler handler;
    /** Objekt s ikonami */
    IkonyPocasi ikony;
    
    /**
     * Konstruktor, hlavní okno aplikace
     */
    public PocasiMain() {
        initComponents();
        handler = new Handler(this);
        vyhledavciOkno.setVisible(false);
        vyhledavciOkno.setOpaque(false);
        vyhledavciOkno.setBackground(new Color(0,0,0,100));
        pozice.setVisible(false);
        ikony = new IkonyPocasi(handler);
        chyba.setVisible(false);
        chyba.setOpaque(false);
        chyba.setBackground(new Color(0,0,0,0));

        try{
            HashMap<String, Double> data = getData();
            praha.setText(Double.toString(data.get("praha")) + " °C");
            plzen.setText(Double.toString(data.get("plzen")) + " °C");
            karlovy_vary.setText(Double.toString(data.get("karlovy vary")) + " °C");
            usti_nad_labem.setText(Double.toString(data.get("usti nad labem")) + " °C");
            liberec.setText(Double.toString(data.get("liberec")) + " °C");
            hradec_kralove.setText(Double.toString(data.get("hradec kralove")) + " °C");
            pardubice.setText(Double.toString(data.get("pardubice")) + " °C");
            jihlava.setText(Double.toString(data.get("jihlava")) + " °C");
            ceske_budejovice.setText(Double.toString(data.get("ceske budejovice")) + " °C");
            brno.setText(Double.toString(data.get("brno")) + " °C");
            olomouc.setText(Double.toString(data.get("olomouc")) + " °C");
            zlin.setText(Double.toString(data.get("zlin")) + " °C");
            ostrava.setText(Double.toString(data.get("ostrava")) + " °C");
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * Metoda pro získání uložených teplot pro krajská města z databáze MySQL
     * 
     * @return objekt HashMap obsahující název města a teplotu
     */
    private HashMap getData(){
        HashMap<String, Double> result = new HashMap<>();
        MySQLConnect mysqlConnect = new MySQLConnect();
        String sql = "SELECT * FROM `mesta`";
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String nazev = rs.getString("nazev");
                double tepl = rs.getDouble("teplota");
                result.put(nazev, tepl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }
        return result;
    }
    
    /**
     * Metoda sloužící k vytvoření menšího vyskakovacího okna
     * Okno slouží k zobrazení detailních informací o počasí v dané obci
     * @param mesto String obsahující název vyhledávaného města
     */
    private void showInfo(String mesto){
        chyba.setVisible(false);
        try{
             weatherByCity = new WeatherByCity(mesto);
             weatherForecast = new WeatherForecast("q=" + mesto); 
        }
        catch (NullPointerException e){
            chyba.setVisible(true);
            return;
        }

        String detail = "";
        detail += "Vlhkost:\t\t" + Math.round(weatherByCity.getMainHumidity())+ "%\n";
        detail += "Tlak:\t\t" + weatherByCity.getMainPressure()+ " hPa\n";
        detail += "Rychlost větru:\t" + weatherByCity.getWindSpeed()+ " m/s\n";
        detail += "Oblačnost:\t\t" + Math.round(weatherByCity.getCloudsAll())+ "%\n";
        detail += "\nVýchod slunce:\t" + new SimpleDateFormat("HH:mm").format(weatherByCity.getSysSunrise())+ "\n";
        detail += "Západ slunce:\t\t" + new SimpleDateFormat("HH:mm").format(weatherByCity.getSysSunset())+ "\n";
        
        
        nazevMesta.setText(mesto);
        cas.setText(new SimpleDateFormat("dd.MM. HH:mm").format(Calendar.getInstance().getTime()));
        teplota.setText(weatherByCity.getMainTemp() + " °C");
        dalsiInfo.setText(detail);

        info.setTitle(mesto);
        info.setSize(488, 515);
        info.setVisible(true);
        int[] xy = coordinatesToXY(weatherByCity.getCoordLat(), weatherByCity.getCoordLon());
        pozice.setLocation(xy[0]-25, xy[1]-40);
        pozice.setVisible(true);
        obrazek.setIcon(ikony.getIconWeatherByCity());
        pridatIkony();
    }
    
    /**
     * Vypočtení souřadnic místa, na které uživatel klikne
     * 
     * @param x souřadnice X bodu, na který bylo kliknuto myší 
     * @param y souřadnice Y bodu, na který bylo kliknuto myší  
     * 
     * @return pole obsahující zeměpisnou šířku na 1. pozici a délku na 2. pozici
     */
    private double[] getCoordinates(int x, int y){
        double xdif = 0.00740171605;
        double ydif = 0.00493237334;
        
        int[] zaklad = {408, 87};
        double[] coord = {50.870592121261794, 14.82327197732559};
        
        double lon = coord[1] + (x - zaklad[0]) * xdif;
        double lat = coord[0] - (y - zaklad[1]) * ydif;
        
        double[] result = {lat, lon};
        return result;
    }
    
    /**
     * Vypočtení pozice místa na mapě podle souřadnic
     * 
     * @param lat zeměpisná šířka místa
     * @param lon zeměpisná délka místa
     * 
     * @return pole obsahující X souřadnici na 1. pozici a Y souřadnici na 2. pozici
     */
    private int[] coordinatesToXY(double lat, double lon){
        int[] zaklad = {408, 87};
        double[] coord = {50.870592121261794, 14.82327197732559};
        double xdif = 0.00740171605;
        double ydif = 0.00493237334;
        
        int x = (int) ((lon - coord[1]) / xdif + zaklad[0]);
         
        int y = (int) ((coord[0] - lat) / ydif + zaklad[1]);
        
        int[] result = {x, y};
        return result;
    }
    
    /**
     * Přidání ikon a infa o počasí na následující dny do menšího vyskakovacího okna
     */
    private void pridatIkony(){
        String vzor = "<html><div style='text-align: center;'>%s °C<br/>%s<br/>%s</html>";

        jLabel1.setIcon(ikony.getIkonyForecast(0));
        jLabel1.setText(String.format(vzor, weatherForecast.getArrayValue(0, 0), weatherForecast.getArrayValue(0, 1), weatherForecast.getArrayValue(0, 2)));
        jLabel1.setHorizontalTextPosition(JLabel.CENTER);
        jLabel1.setVerticalTextPosition(JLabel.BOTTOM);
        
        jLabel4.setIcon(ikony.getIkonyForecast(1));
        jLabel4.setText(String.format(vzor, weatherForecast.getArrayValue(1, 0), weatherForecast.getArrayValue(1, 1), weatherForecast.getArrayValue(1, 2)));
        jLabel4.setHorizontalTextPosition(JLabel.CENTER);
        jLabel4.setVerticalTextPosition(JLabel.BOTTOM);
        
        jLabel2.setIcon(ikony.getIkonyForecast(2));
        jLabel2.setText(String.format(vzor, weatherForecast.getArrayValue(2, 0), weatherForecast.getArrayValue(2, 1), weatherForecast.getArrayValue(2, 2)));
        jLabel2.setHorizontalTextPosition(JLabel.CENTER);
        jLabel2.setVerticalTextPosition(JLabel.BOTTOM);
        
        jLabel5.setIcon(ikony.getIkonyForecast(3));
        jLabel5.setText(String.format(vzor, weatherForecast.getArrayValue(3, 0), weatherForecast.getArrayValue(3, 1), weatherForecast.getArrayValue(3, 2)));
        jLabel5.setHorizontalTextPosition(JLabel.CENTER);
        jLabel5.setVerticalTextPosition(JLabel.BOTTOM);
        
        jLabel3.setIcon(ikony.getIkonyForecast(4));
        jLabel3.setText(String.format(vzor, weatherForecast.getArrayValue(4, 0), weatherForecast.getArrayValue(4, 1), weatherForecast.getArrayValue(4, 2)));
        jLabel3.setHorizontalTextPosition(JLabel.CENTER);
        jLabel3.setVerticalTextPosition(JLabel.BOTTOM);
        
        jLabel6.setIcon(ikony.getIkonyForecast(5));
        jLabel6.setText(String.format(vzor, weatherForecast.getArrayValue(5, 0), weatherForecast.getArrayValue(5, 1), weatherForecast.getArrayValue(5, 2)));
        jLabel6.setHorizontalTextPosition(JLabel.CENTER);
        jLabel6.setVerticalTextPosition(JLabel.BOTTOM);
    }
    
    @SuppressWarnings("unchecked")
    /**
     * Metoda pro inicializacikomponent vytvořená vývojovým prostředím
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        info = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        nazevMesta = new javax.swing.JLabel();
        cas = new javax.swing.JLabel();
        teplota = new javax.swing.JLabel();
        dalsiInfo = new javax.swing.JTextArea();
        obrazek = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        vyhledavciOkno = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        nehledej = new javax.swing.JLabel();
        vyhledavaniText = new javax.swing.JTextField();
        vyhledavaniButton = new javax.swing.JButton();
        chyba = new javax.swing.JTextArea();
        praha = new javax.swing.JLabel();
        plzen = new javax.swing.JLabel();
        karlovy_vary = new javax.swing.JLabel();
        usti_nad_labem = new javax.swing.JLabel();
        liberec = new javax.swing.JLabel();
        hradec_kralove = new javax.swing.JLabel();
        pardubice = new javax.swing.JLabel();
        jihlava = new javax.swing.JLabel();
        ceske_budejovice = new javax.swing.JLabel();
        brno = new javax.swing.JLabel();
        olomouc = new javax.swing.JLabel();
        zlin = new javax.swing.JLabel();
        ostrava = new javax.swing.JLabel();
        hledej = new javax.swing.JLabel();
        pozice = new javax.swing.JLabel();
        pozadi = new javax.swing.JLabel();

        info.setBounds(new java.awt.Rectangle(0, 0, 560, 420));
        info.setMinimumSize(new java.awt.Dimension(488, 515));
        info.setResizable(false);
        info.getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        nazevMesta.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        nazevMesta.setText("Nazev mesta");

        cas.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cas.setForeground(new java.awt.Color(102, 102, 102));
        cas.setText("32. 13. 3002");

        teplota.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        teplota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        teplota.setText("5°C");

        dalsiInfo.setEditable(false);
        dalsiInfo.setColumns(20);
        dalsiInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dalsiInfo.setRows(5);
        dalsiInfo.setBorder(null);

        obrazek.setMaximumSize(new java.awt.Dimension(200, 200));
        obrazek.setMinimumSize(new java.awt.Dimension(200, 200));
        obrazek.setPreferredSize(new java.awt.Dimension(200, 200));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel1.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel1.setPreferredSize(new java.awt.Dimension(120, 120));

        jLabel2.setText("jLabel2");
        jLabel2.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel2.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 120));

        jLabel3.setText("jLabel3");
        jLabel3.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel3.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 120));

        jLabel4.setText("jLabel4");
        jLabel4.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel4.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 120));

        jLabel5.setText("jLabel5");
        jLabel5.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel5.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel5.setPreferredSize(new java.awt.Dimension(120, 120));

        jLabel6.setText("jLabel6");
        jLabel6.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel6.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 120));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cas)
                    .addComponent(dalsiInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nazevMesta, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(obrazek, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(teplota, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(nazevMesta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(cas)
                        .addGap(18, 18, 18)
                        .addComponent(dalsiInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(obrazek, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(teplota, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        info.getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 490, 510);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 980, 610));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 980, 610));
        setMaximumSize(new java.awt.Dimension(980, 610));
        setMinimumSize(new java.awt.Dimension(980, 610));
        setName("Pocasi"); // NOI18N
        setPreferredSize(new java.awt.Dimension(980, 610));
        setResizable(false);
        getContentPane().setLayout(null);

        vyhledavciOkno.setBackground(new java.awt.Color(153, 153, 255));

        nehledej.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        nehledej.setText("<");
        nehledej.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nehledejMouseClicked(evt);
            }
        });

        vyhledavaniText.setMinimumSize(new java.awt.Dimension(150, 25));
        vyhledavaniText.setPreferredSize(new java.awt.Dimension(150, 25));

        vyhledavaniButton.setText("Hledat");
        vyhledavaniButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vyhledavaniButtonActionPerformed(evt);
            }
        });

        chyba.setEditable(false);
        chyba.setColumns(20);
        chyba.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chyba.setForeground(new java.awt.Color(255, 255, 255));
        chyba.setLineWrap(true);
        chyba.setRows(5);
        chyba.setText(" Nelze najít hledanou obec!\n\n Zkontrolujte zda je název \n správný!");
        chyba.setAlignmentX(4.0F);
        chyba.setAlignmentY(4.0F);
        chyba.setBorder(null);
        chyba.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        chyba.setOpaque(false);
        chyba.setSelectionColor(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout vyhledavciOknoLayout = new javax.swing.GroupLayout(vyhledavciOkno);
        vyhledavciOkno.setLayout(vyhledavciOknoLayout);
        vyhledavciOknoLayout.setHorizontalGroup(
            vyhledavciOknoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vyhledavciOknoLayout.createSequentialGroup()
                .addGroup(vyhledavciOknoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vyhledavciOknoLayout.createSequentialGroup()
                        .addGroup(vyhledavciOknoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(vyhledavciOknoLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(vyhledavaniText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(vyhledavciOknoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(nehledej, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vyhledavciOknoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chyba, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(vyhledavciOknoLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(vyhledavaniButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vyhledavciOknoLayout.setVerticalGroup(
            vyhledavciOknoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vyhledavciOknoLayout.createSequentialGroup()
                .addComponent(nehledej, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vyhledavaniText, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vyhledavaniButton)
                .addGap(47, 47, 47)
                .addComponent(chyba, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 354, Short.MAX_VALUE))
        );

        getContentPane().add(vyhledavciOkno);
        vyhledavciOkno.setBounds(0, 0, 230, 610);

        praha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        praha.setText("Praha");
        praha.setMaximumSize(new java.awt.Dimension(99, 16));
        praha.setMinimumSize(new java.awt.Dimension(99, 16));
        praha.setPreferredSize(new java.awt.Dimension(99, 16));
        praha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prahaMouseClicked(evt);
            }
        });
        getContentPane().add(praha);
        praha.setBounds(340, 240, 99, 16);

        plzen.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        plzen.setText("Plzen");
        plzen.setMaximumSize(new java.awt.Dimension(99, 16));
        plzen.setMinimumSize(new java.awt.Dimension(99, 16));
        plzen.setPreferredSize(new java.awt.Dimension(99, 16));
        plzen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plzenMouseClicked(evt);
            }
        });
        getContentPane().add(plzen);
        plzen.setBounds(190, 290, 80, 40);

        karlovy_vary.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        karlovy_vary.setText("Karlovy Vary");
        karlovy_vary.setMaximumSize(new java.awt.Dimension(99, 16));
        karlovy_vary.setMinimumSize(new java.awt.Dimension(99, 16));
        karlovy_vary.setPreferredSize(new java.awt.Dimension(99, 16));
        karlovy_vary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                karlovy_varyMouseClicked(evt);
            }
        });
        getContentPane().add(karlovy_vary);
        karlovy_vary.setBounds(110, 210, 90, 16);

        usti_nad_labem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        usti_nad_labem.setText("Usti nad Labem");
        usti_nad_labem.setMaximumSize(new java.awt.Dimension(99, 16));
        usti_nad_labem.setMinimumSize(new java.awt.Dimension(99, 16));
        usti_nad_labem.setPreferredSize(new java.awt.Dimension(99, 16));
        usti_nad_labem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usti_nad_labemMouseClicked(evt);
            }
        });
        getContentPane().add(usti_nad_labem);
        usti_nad_labem.setBounds(260, 140, 99, 16);

        liberec.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        liberec.setText("Liberec");
        liberec.setMaximumSize(new java.awt.Dimension(99, 16));
        liberec.setMinimumSize(new java.awt.Dimension(99, 16));
        liberec.setPreferredSize(new java.awt.Dimension(99, 16));
        liberec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                liberecMouseClicked(evt);
            }
        });
        getContentPane().add(liberec);
        liberec.setBounds(410, 110, 99, 16);

        hradec_kralove.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        hradec_kralove.setText("Hradec Kralove");
        hradec_kralove.setMaximumSize(new java.awt.Dimension(99, 16));
        hradec_kralove.setMinimumSize(new java.awt.Dimension(99, 16));
        hradec_kralove.setPreferredSize(new java.awt.Dimension(99, 16));
        hradec_kralove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hradec_kraloveMouseClicked(evt);
            }
        });
        getContentPane().add(hradec_kralove);
        hradec_kralove.setBounds(520, 190, 99, 16);

        pardubice.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pardubice.setText("Pardubice");
        pardubice.setMaximumSize(new java.awt.Dimension(99, 16));
        pardubice.setMinimumSize(new java.awt.Dimension(99, 16));
        pardubice.setPreferredSize(new java.awt.Dimension(99, 16));
        pardubice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pardubiceMouseClicked(evt);
            }
        });
        getContentPane().add(pardubice);
        pardubice.setBounds(520, 260, 99, 16);

        jihlava.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jihlava.setText("Jihlava");
        jihlava.setMaximumSize(new java.awt.Dimension(99, 16));
        jihlava.setMinimumSize(new java.awt.Dimension(99, 16));
        jihlava.setPreferredSize(new java.awt.Dimension(99, 16));
        jihlava.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jihlavaMouseClicked(evt);
            }
        });
        getContentPane().add(jihlava);
        jihlava.setBounds(500, 380, 99, 16);

        ceske_budejovice.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ceske_budejovice.setText("Ceske Budejovice");
        ceske_budejovice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ceske_budejoviceMouseClicked(evt);
            }
        });
        getContentPane().add(ceske_budejovice);
        ceske_budejovice.setBounds(310, 450, 100, 30);

        brno.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        brno.setText("Brno");
        brno.setMaximumSize(new java.awt.Dimension(99, 16));
        brno.setMinimumSize(new java.awt.Dimension(99, 16));
        brno.setPreferredSize(new java.awt.Dimension(99, 16));
        brno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brnoMouseClicked(evt);
            }
        });
        getContentPane().add(brno);
        brno.setBounds(640, 420, 99, 16);

        olomouc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        olomouc.setText("Olomouc");
        olomouc.setMaximumSize(new java.awt.Dimension(99, 16));
        olomouc.setMinimumSize(new java.awt.Dimension(99, 16));
        olomouc.setPreferredSize(new java.awt.Dimension(99, 16));
        olomouc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                olomoucMouseClicked(evt);
            }
        });
        getContentPane().add(olomouc);
        olomouc.setBounds(720, 340, 99, 16);

        zlin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        zlin.setText("Zlin");
        zlin.setMaximumSize(new java.awt.Dimension(99, 16));
        zlin.setMinimumSize(new java.awt.Dimension(99, 16));
        zlin.setPreferredSize(new java.awt.Dimension(99, 16));
        zlin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zlinMouseClicked(evt);
            }
        });
        getContentPane().add(zlin);
        zlin.setBounds(780, 420, 99, 16);

        ostrava.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ostrava.setText("Ostrava");
        ostrava.setMaximumSize(new java.awt.Dimension(99, 16));
        ostrava.setMinimumSize(new java.awt.Dimension(99, 16));
        ostrava.setPreferredSize(new java.awt.Dimension(99, 16));
        ostrava.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ostravaMouseClicked(evt);
            }
        });
        getContentPane().add(ostrava);
        ostrava.setBounds(830, 290, 99, 16);

        hledej.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        hledej.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hledej.setText(">");
        hledej.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hledejMouseClicked(evt);
            }
        });
        getContentPane().add(hledej);
        hledej.setBounds(0, 0, 50, 50);

        pozice.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pozice.setForeground(new java.awt.Color(255, 0, 0));
        pozice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pozice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pocasi/lokace30.png"))); // NOI18N
        pozice.setToolTipText("");
        pozice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pozice.setMaximumSize(new java.awt.Dimension(30, 30));
        pozice.setMinimumSize(new java.awt.Dimension(30, 30));
        pozice.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(pozice);
        pozice.setBounds(370, 360, 50, 50);

        pozadi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pocasi/mapa.png"))); // NOI18N
        pozadi.setMaximumSize(new java.awt.Dimension(980, 610));
        pozadi.setMinimumSize(new java.awt.Dimension(980, 610));
        pozadi.setPreferredSize(new java.awt.Dimension(980, 610));
        pozadi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pozadiMouseClicked(evt);
            }
        });
        getContentPane().add(pozadi);
        pozadi.setBounds(0, 0, 980, 610);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Schování okna pro vyhledávání
     * @param evt event kliknutí myši
     */
    private void hledejMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hledejMouseClicked
        vyhledavciOkno.setVisible(true);
        hledej.setVisible(false);
    }//GEN-LAST:event_hledejMouseClicked
    /**
     * Zobrazení okna pro vyhledávání
     * @param evt event kliknutí myši
     */
    private void nehledejMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nehledejMouseClicked
        vyhledavciOkno.setVisible(false);
        hledej.setVisible(true);
    }//GEN-LAST:event_nehledejMouseClicked
    /**
     * Zobrazení infa o počasí pro město Praha
     * @param evt event kliknutí myši
     */
    private void prahaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prahaMouseClicked
        showInfo("Praha");
    }//GEN-LAST:event_prahaMouseClicked
    /**
     * Zobrazení infa o počasí pro město Plzeň
     * @param evt event kliknutí myši
     */
    private void plzenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plzenMouseClicked
        showInfo("Plzen");
    }//GEN-LAST:event_plzenMouseClicked
    /**
     * Zobrazení infa o počasí pro město Karlovy Vary
     * @param evt event kliknutí myši
     */
    private void karlovy_varyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karlovy_varyMouseClicked
        showInfo("Karlovy Vary");
    }//GEN-LAST:event_karlovy_varyMouseClicked
    /**
     * Zobrazení infa o počasí pro město Ústí nad Labem
     * @param evt event kliknutí myši
     */
    private void usti_nad_labemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usti_nad_labemMouseClicked
        showInfo("Usti nad Labem");
    }//GEN-LAST:event_usti_nad_labemMouseClicked
    /**
     * Zobrazení infa o počasí pro město Liberec
     * @param evt event kliknutí myši
     */
    private void liberecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liberecMouseClicked
        showInfo("Liberec");
    }//GEN-LAST:event_liberecMouseClicked
    /**
     * Zobrazení infa o počasí pro město Hradec Králové
     * @param evt event kliknutí myši
     */
    private void hradec_kraloveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hradec_kraloveMouseClicked
        showInfo("Hradec Kralove");
    }//GEN-LAST:event_hradec_kraloveMouseClicked
    /**
     * Zobrazení infa o počasí pro město Pardubice
     * @param evt event kliknutí myši
     */
    private void pardubiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pardubiceMouseClicked
        showInfo("Pardubice");
    }//GEN-LAST:event_pardubiceMouseClicked
    /**
     * Zobrazení infa o počasí pro město Jihlava
     * @param evt event kliknutí myši
     */
    private void jihlavaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jihlavaMouseClicked
        showInfo("Jihlava");
    }//GEN-LAST:event_jihlavaMouseClicked
    /**
     * Zobrazení infa o počasí pro město České Budějovice
     * @param evt event kliknutí myši
     */
    private void ceske_budejoviceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ceske_budejoviceMouseClicked
        showInfo("Ceske Budejovice");
    }//GEN-LAST:event_ceske_budejoviceMouseClicked
    /**
     * Zobrazení infa o počasí pro město Brno
     * @param evt event kliknutí myši
     */
    private void brnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnoMouseClicked
        showInfo("Brno");
    }//GEN-LAST:event_brnoMouseClicked
    /**
     * Zobrazení infa o počasí pro město Olomouc
     * @param evt event kliknutí myši
     */
    private void olomoucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_olomoucMouseClicked
        showInfo("Olomouc");
    }//GEN-LAST:event_olomoucMouseClicked
    /**
     * Zobrazení infa o počasí pro město Zlín
     * @param evt event kliknutí myši
     */
    private void zlinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zlinMouseClicked
        showInfo("Zlín");
    }//GEN-LAST:event_zlinMouseClicked
    /**
     * Zobrazení infa o počasí pro město Ostrava
     * @param evt event kliknutí myši
     */
    private void ostravaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ostravaMouseClicked
        showInfo("Ostrava");
    }//GEN-LAST:event_ostravaMouseClicked
    /**
     * Získání hledaného místa z textového pole a předání metodě pro vykreslení detailů
     * @param evt event kliknutí myši
     */
    private void vyhledavaniButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vyhledavaniButtonActionPerformed
        showInfo(vyhledavaniText.getText());
    }//GEN-LAST:event_vyhledavaniButtonActionPerformed
     /**
     * Zobrazení infa o počasí pro místo, na které uživatel klikne
     * @param evt event kliknutí myši
     */
    private void pozadiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pozadiMouseClicked
        int x = evt.getX();
        int y = evt.getY();
        double[] coord = getCoordinates(x, y);
        
        try{
             weatherByCoordinates = new WeatherByCoordinates(coord[0], coord[1]);
             weatherForecast = new WeatherForecast("lat=" + coord[0] + "&lon=" + coord[1]); 
        }
        catch (NullPointerException e){
            chyba.setVisible(true);
            return;
        }
      
        String detail = "";
        detail += "Vlhkost:\t\t" + Math.round(weatherByCoordinates.getMainHumidity())+ "%\n";
        detail += "Tlak:\t\t" + weatherByCoordinates.getMainPressure()+ " hPa\n";
        detail += "Rychlost větru:\t" + weatherByCoordinates.getWindSpeed()+ " m/s\n";
        detail += "Oblačnost:\t\t" + Math.round(weatherByCoordinates.getCloudsAll())+ "%\n";
        detail += "\nVýchod slunce:\t" + new SimpleDateFormat("HH:mm").format(weatherByCoordinates.getSysSunrise())+ "\n";
        detail += "Západ slunce:\t\t" + new SimpleDateFormat("HH:mm").format(weatherByCoordinates.getSysSunset())+ "\n";

        nazevMesta.setText(weatherByCoordinates.getName());
        cas.setText(new SimpleDateFormat("dd.MM. HH:mm").format(Calendar.getInstance().getTime()));
        teplota.setText(weatherByCoordinates.getMainTemp() + " °C");
        dalsiInfo.setText(detail);

        info.setTitle(weatherByCoordinates.getName());
        info.setSize(488, 515);
        info.setVisible(true);
        int[] xy = coordinatesToXY(weatherByCoordinates.getCoordLat(), weatherByCoordinates.getCoordLon());
        pozice.setLocation(xy[0]-25, xy[1]-40);
        pozice.setVisible(true);
        obrazek.setIcon(ikony.getIconWeatherByCoordinates());
        pridatIkony();
    }//GEN-LAST:event_pozadiMouseClicked

    /**
     * Metoda main pro spuštění programu
     * @param args argumenty z příkazové řádky
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code - vygenerováno Netbeans návrhářem ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PocasiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PocasiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PocasiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PocasiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Zobrazení formuláře */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PocasiMain().setVisible(true);
            }
        });
    }

    /**
     * Vrátí objekt weatherByCity, který byl vyhledán jako poslední
     * @return objekt weatherByCity
     */
    public WeatherByCity getWeatherByCity() {
        return weatherByCity;
    }

    /**
     * Vrátí objekt WeatherForecast, který byl vyhledán jako poslední
     * @return objekt WeatherForecast
     */
    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    /**
     * Vrátí objekt weatherByCoordinates, který byl vyhledán jako poslední
     * @return objekt weatherByCoordinates
     */
    public WeatherByCoordinates getWeatherByCoordinates() {
        return weatherByCoordinates;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** Label zobrazující teplotu pro město Brno */
    private javax.swing.JLabel brno;
    /** Čas ve kterém bylo hledání spuštěno */
    private javax.swing.JLabel cas;
    /** Label zobrazující teplotu pro město České Budějovice */
    private javax.swing.JLabel ceske_budejovice;
    /** Label zobrazující chybové hlášení */
    private javax.swing.JTextArea chyba;
    /** Label obsahující další informace o počasí */
    private javax.swing.JTextArea dalsiInfo;
    /** Label, který po kliknutí zobrazí vyhledávací panel */
    private javax.swing.JLabel hledej;
    /** Label zobrazující teplotu pro město Hradec Králové */
    private javax.swing.JLabel hradec_kralove;
    /** Okno pro zobrazování detailů o počasí */
    private javax.swing.JDialog info;
    /** Label obsahující předpověď na následující dny */
    private javax.swing.JLabel jLabel1;
    /** Label obsahující předpověď na následující dny */
    private javax.swing.JLabel jLabel2;
    /** Label obsahující předpověď na následující dny */
    private javax.swing.JLabel jLabel3;
    /** Label obsahující předpověď na následující dny */
    private javax.swing.JLabel jLabel4;
    /** Label obsahující předpověď na následující dny */
    private javax.swing.JLabel jLabel5;
    /** Label obsahující předpověď na následující dny */
    private javax.swing.JLabel jLabel6;
    /** Panel obsahující předpověď na následující dny */
    private javax.swing.JPanel jPanel2;
    /** Panel obsahující všechny komponenty v okně info */
    private javax.swing.JPanel jPanel3;
    /** Label zobrazující teplotu pro město Jihlava */
    private javax.swing.JLabel jihlava;
    /** Label zobrazující teplotu pro město Karovy Vary */
    private javax.swing.JLabel karlovy_vary;
    /** Label zobrazující teplotu pro město Liberec */
    private javax.swing.JLabel liberec;
    /** Název města */
    private javax.swing.JLabel nazevMesta;
    /** Label, který po kliknutí schová vyhledávací panel */
    private javax.swing.JLabel nehledej;
    /** Label zobrazující ikonu počasí */
    private javax.swing.JLabel obrazek;
    /** Label zobrazující teplotu pro město Olomouc */
    private javax.swing.JLabel olomouc;
    /** Label zobrazující teplotu pro město Ostrava */
    private javax.swing.JLabel ostrava;
    /** Label zobrazující teplotu pro město Pardubice */
    private javax.swing.JLabel pardubice;
    /** Label zobrazující teplotu pro město Plzeň */
    private javax.swing.JLabel plzen;
    /** Label obsahující mapu */
    private javax.swing.JLabel pozadi;
    /** Label zobrazující polohu hledané obce/místa  */
    private javax.swing.JLabel pozice;
    /** Label zobrazující teplotu pro město Praha */
    private javax.swing.JLabel praha;
    /** Teplota */
    private javax.swing.JLabel teplota;
    /** Label zobrazující teplotu pro město Ústí nad Labem */
    private javax.swing.JLabel usti_nad_labem;
    /** Tlačítko pro potvrzení vyhledávání */
    private javax.swing.JButton vyhledavaniButton;
    /** Text field pro zadání hledané obce */
    private javax.swing.JTextField vyhledavaniText;
    /** Vyhledávací okno */
    private javax.swing.JPanel vyhledavciOkno;
    /** Label zobrazující teplotu pro město Zlín */
    private javax.swing.JLabel zlin;
    // End of variables declaration//GEN-END:variables
}
