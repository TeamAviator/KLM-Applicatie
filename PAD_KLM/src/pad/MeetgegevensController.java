/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pad;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author florian_2
 */
public class MeetgegevensController implements Initializable {

    /**
     * Initializes the controller class.
     * @return 
     */
    @FXML
    private TextField cargoNummer;
    @FXML
    private TextField volume;
    @FXML
    private TextField gewicht;
    @FXML 
    private TextField aankomstTijd;
    @FXML 
    private TextField beschadigd;
    
    private int CargoNummer;
    private int Volume;
    private int Gewicht;
    private String DatumTijd;
    private String Beschadigd;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     *
     * @param event
     * @throws IOExeption
     */
    public void addCargo(ActionEvent event) throws IOException{
        
        CargoNummer = Integer.parseInt(cargoNummer.getText());
        Volume = Integer.parseInt(volume.getText());
        Gewicht = Integer.parseInt(gewicht.getText());
        DatumTijd = aankomstTijd.getText();
        Beschadigd = beschadigd.getText();
        
        
        try (Connection conn = Database.initDatabase()) {
            conn.setAutoCommit(false);
        String addCargo 
                 = "INSERT INTO meetgegevens (vracht_id, gewicht, volume, datum_tijd, beschadigd) "
                 + "VALUES (?,?,?,?,?) " ;
        
        //Create prepared statment
            PreparedStatement ADDCARGO = conn.prepareStatement(addCargo);

            //set values
            ADDCARGO.setInt(1, CargoNummer);
            ADDCARGO.setInt(2, Gewicht);
            ADDCARGO.setInt(3, Volume);
            ADDCARGO.setString(4, DatumTijd);
            ADDCARGO.setString(5, Beschadigd);
            
            ADDCARGO.execute();
            conn.commit();
        
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
}
