
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class DashboardMedewerkerController implements Initializable {

    /**
     * Initializes the controller class.
     */
        
    //Er wordt een koppeling gemaakt tussen de controller en bijbehorende FXML bestand     
    public AnchorPane getDashboardMedewerkerController() {
        AnchorPane screen = null;
        AnchorPane EnterCargo = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/Views/DashboardMedewerker.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardMedewerkerController.class.getName()).log(Level.SEVERE, null, ex);
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
        getMeetgegevens();
        
    }
    public void getMeetgegevens() {
        //screen2 = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane screen2 = FXMLLoader.load(getClass().getResource("/Views/Meetgegevens.fxml"));
            Stage stage = new Stage();
            stage.setTitle("KLM cargo");
            stage.setScene(new Scene(screen2, MITM.screenSizeX, MITM.screenSizeY));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SymulatieSchermController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
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
