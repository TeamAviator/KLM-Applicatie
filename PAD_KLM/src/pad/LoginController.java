package pad;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class LoginController implements Initializable {

    //De nodige controllers worden opgehaald en een variabele meegegeven
    DashboardManagerController dashboardManagerController = new DashboardManagerController();
    DashboardMedewerkerController dashboardMedewerkerController = new DashboardMedewerkerController();

    
    
    @FXML
    private Label errorID;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorID.setVisible(false);
    }
    
    
    
    //Login controller koppelen aan het bijbehorende FXML bestand
    public AnchorPane getLoginScreen() {
        AnchorPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    //functies van FXML ophalen aan de hand van id
    @FXML
    private ComboBox txtBedrijfsFunctie;
    @FXML
    private TextField txtUsername;

    private String username = "";
    private String gebruikersnaam;
    private String bedrijfsFunctie;
    
    //action event  = button click. Als login geklikt, dan worden de onderstaande stappen uitgevoerd
    @FXML
    private void login(ActionEvent event) throws IOException {
        
        //variabele maken van FXML id 
        username = txtUsername.getText();
        
        //checken of de ingevoerde gegevens gelezen wordt
        
        
        
        try (Connection conn = Database.initDatabase()) {
            //Select the employee with the given username and password
            String selectEmployee
                    = "SELECT gebruikersnaam, status, voornaam, achternaam "
                    + "FROM personeel "
                    + "WHERE gebruikersnaam = ? ";

            //Create prepared statment
            
            PreparedStatement preparedStatement = conn.prepareStatement(selectEmployee);

            //set values
            preparedStatement.setString(1, username);
            
            //execute query and get results
            ResultSet medewerker = preparedStatement.executeQuery();

            //if there are no records found.
            if (medewerker.next()) {
                System.out.println(medewerker.getString("status"));
                bedrijfsFunctie = medewerker.getString("status");
                MITM.naam = medewerker.getString("voornaam");
                MITM.achternaam = medewerker.getString("achternaam");
            } else if(!medewerker.next()){
                errorID.setVisible(true);
            }
           
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        

        //statement waarin wordt bepaald of de user een manager is
        if (bedrijfsFunctie.equals("mg")) {
            MITM.autoriteit = "mg";

                //Als de if wordt uitgevoerd, en de ingevoerde waardes voldoen, moet het manager dashboard komen.
                AnchorPane dashboardManager = dashboardManagerController.getDashboardManagerScreen();
                Scene scene = new Scene(dashboardManager, MITM.screenSizeX, MITM.screenSizeY);
                Stage stage = new Stage();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("KLM Cargo");
                stage.setScene(scene);
                stage.show();
                
                
            //statement waarin wordt bepaald of de user een medewerker is
        } else if (bedrijfsFunctie.equals("mw")) {
            MITM.autoriteit = "mw";

                //Als de if wordt uitgevoerd, en de ingevoerde waardes voldoen, moet het medewerker dashboard komen.
                AnchorPane dashboardMedewerker = dashboardMedewerkerController.getDashboardMedewerkerController();
                Scene scene = new Scene(dashboardMedewerker, MITM.screenSizeX, MITM.screenSizeY);
                Stage stage = new Stage();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("KLM Cargo");
                stage.setScene(scene);
                stage.show();
            
        }
    }

}
