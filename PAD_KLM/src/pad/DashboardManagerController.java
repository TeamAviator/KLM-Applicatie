package pad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class DashboardManagerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    //Er wordt een koppeling gemaakt tussen de controller en bijbehorende FXML bestand
    public AnchorPane getDashboardManagerScreen() {
        AnchorPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/Views/DashboardManager.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    @FXML
    private Label txtVoornaam;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtVoornaam.setText(MITM.naam + " " + MITM.achternaam);
    }

    //De nodige controllers worden opgehaald en een variabele meegegeven
    CargoLijstController cargoLijstController = new CargoLijstController();
        
  
    @FXML
    public void cargo(ActionEvent event) throws IOException {

      
        AnchorPane cargoLijst = cargoLijstController.getCargoLijstController();
        Scene scene = new Scene(cargoLijst, MITM.screenSizeX, MITM.screenSizeY);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
        
        getSymulatieScherm();
//        AnchorPane meetgegevens = MITM.meetgegevensController.getMeetgegevens();
//        Scene scene2 = new Scene(meetgegevens, MITM.screenSizeX, MITM.screenSizeY);
//        Stage stage2 = new Stage();
//        stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        stage2.setTitle("KLM Cargo");
//        stage2.setScene(scene2);
//        stage2.show();
        
        
    }
       @FXML
    
    private void logout(ActionEvent event) throws IOException {
        AnchorPane getLoginScreen  = MITM.loginController.getLoginScreen();
        Scene scene = new Scene(getLoginScreen, 800, 600);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
   }
    public void getSymulatieScherm() {
        //screen2 = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane screen2 = FXMLLoader.load(getClass().getResource("/Views/SymulatieScherm.fxml"));
            
            MITM.stage1.setTitle("KLM cargo");
            MITM.stage1.setScene(new Scene(screen2, MITM.screenSizeX, MITM.screenSizeY));
            MITM.stage1.show();
        } catch (IOException ex) {
            Logger.getLogger(SymulatieSchermController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}

// getString("ID");

// ? gebruiken  
// string naam = rs.getString("naam");
// rs.setString(1,"naam"
// select status from personeel where naam = ?
// preparedstatement.setString(1,username),      username is i  deit geval een variable die je zelf hebt aangemaakt