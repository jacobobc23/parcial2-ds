package singleton;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author Jacobo-bc
 */
public class Singleton {

    private static Singleton INSTANCE;
    private Connection connection;

    private static final String URL = "jdbc:mariadb://localhost:3306/bd_parcial2";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Singleton() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Singleton.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Singleton getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }
}
