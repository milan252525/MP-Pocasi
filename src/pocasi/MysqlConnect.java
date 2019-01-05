package pocasi;

import java.sql.*;

import java.util.Properties;

/**
 * Třída pro vytvoření spojení s databází
 * @author Milan Abrahám, Anh Thai Hoang
 */
public class MysqlConnect {
    /** Ovladač pro připojení k MySQL databázi */
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    /** IP adresa databáze */
    private static final String DATABASE_URL = "jdbc:mysql://139.59.139.93:3306/pocasi";
    /** Přihlašovací údaj k databázi */
    private static final String USERNAME = "app";
    /** Heslo k databázi */
    private static final String PASSWORD = "pocasi";
    /** Počet maximálně odeslaných požadavků */
    private static final String MAX_POOL = "250";

    /** Objekt připojení */
    private Connection connection;
    /** Vlastnosti objektu připojení */
    private Properties properties;

    /** Získání vlastností připojení 
    * @return objekt vlastností připojení
    */
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    /** 
    * Metoda sloužící k vytvoření spojení s databází
    * @return objekt připojení
    */
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /** Metoda sloužící k ukončení spojení s databází */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } 
}