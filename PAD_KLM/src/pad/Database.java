
package pad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jeroen
 */
public class Database {
    
    public static Connection initDatabase() {
        Connection conn = null;
        try {
            
            //Load the JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            
            //Connect to a database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pad", "root", "root");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        return conn;
    }
}
