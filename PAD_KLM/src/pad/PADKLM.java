
package pad;

import java.util.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jeroen
 */
public class PADKLM extends Application {
    
    LoginController loginController = new LoginController();
    
public void cargo(){
    ArrayList cargoLijst = new ArrayList();
    Cargo cargo1 = new Cargo(4,"bloemen",20,20,20,"nee");
    
    cargoLijst.add(cargo1);
    
    }
    
    
    @Override 
    public void start(Stage primaryStage) {
        
        AnchorPane login = loginController.getLoginScreen();
       
        
        Scene scene = new Scene(login, 800, 600);
        
        primaryStage.setTitle("KLM Cargo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
