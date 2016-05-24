/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pad;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 *
 * @author florian_2
 */
public class Cargo {
    public int cargoNumber;
    public String product;
    public int weight;
    public int volume;
    public int klantId;
    public String foh;

    public Cargo(int cargoNumber, String product, int weight, int volume, int klantId, String foh) {
        this.cargoNumber = cargoNumber;
        this.product = product;
        this.weight = weight;
        this.volume = volume;
        this.klantId = klantId;
        this.foh = foh;
    }
//    public static void getMeetgegevens() {
//        //screen2 = null;
//        try {
//           AnchorPane screen2 = FXMLLoader.load(getClass().getResource("/Views/Meetgegevens.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("KLM cargo");
//            stage.setScene(new Scene(screen2, MITM.screenSizeX, MITM.screenSizeY));
//        } catch (IOException ex) {
//            Logger.getLogger(MeetgegevensController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       
//    }
    
    
    
    
    
   

    
}