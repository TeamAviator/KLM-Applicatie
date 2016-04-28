
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    //Er wordt een koppeling gemaakt tussen de controller en bijbehorende FXML bestand     
    public AnchorPane getDashboardMedewerkerController() {
        AnchorPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/Views/DashboardMedewerker.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardMedewerkerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    
     //De nodige controllers worden opgehaald en een variabele meegegeven
    CargoLijstController cargoLijstController = new CargoLijstController();

  
    @FXML
    private void cargo(ActionEvent event) throws IOException {

      
        AnchorPane cargoLijst = cargoLijstController.getCargoLijstController();
        Scene scene = new Scene(cargoLijst, 600, 400);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
        
    }
    
}
