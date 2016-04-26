
package pad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Jeroen
 */
public class CargoLijstController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        //Er wordt een koppeling gemaakt tussen de controller en bijbehorende FXML bestand
        public AnchorPane getCargoLijstController() {
        AnchorPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/Views/CargoLijst.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }

    
}
