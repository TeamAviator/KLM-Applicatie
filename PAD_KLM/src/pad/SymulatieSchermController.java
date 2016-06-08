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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author florian_2
 */
public class SymulatieSchermController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url 
     * @param rb 
     */
    @FXML
   private TextField Barcode;
    @FXML
   private TextField Product;
    @FXML
   private TextField Gewicht;
    @FXML
   private TextField Volume;
    @FXML
   private TextField Gekoeld;
    @FXML
   private TextField AankomstTijd;
    @FXML
   private TextField KlantID;
    @FXML
   private TextField Bezorger;
    @FXML
   private Label error;
    @FXML
   private Label Added;
   
   
   private int barcode;
   private String product;
   private int gewicht;
   private int volume;
   private String gekoeld;
   private String aankomstTijd;
   private int klantID;
   private String bezorger;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      error.setText("");
      Added.setVisible(false);
      
    }    

    /**
     *
    
     * @throws java.io.IOException
     */
    @FXML
    private void addCargo (ActionEvent event) throws IOException {
        error.setVisible(false);
        barcode = Integer.parseInt(Barcode.getText());
        product = Product.getText();
        gewicht = Integer.parseInt(Gewicht.getText());
        volume = Integer.parseInt(Volume.getText());
        gekoeld = Gekoeld.getText();
        aankomstTijd = AankomstTijd.getText();
        klantID = Integer.parseInt(KlantID.getText());
        bezorger = Bezorger.getText();
        
        
         try (Connection conn = Database.initDatabase()) {
             conn.setAutoCommit(false);
              String checkBarcode
                 = "SELECT * "
                 + "FROM vracht "
                 + "WHERE vracht_id = ? " ;
             
              String addCargo 
                 = "INSERT INTO vracht (vracht_id, product, gewicht, volume, gekoeld, datum_tijd, klant_klant_id, bezorger) "
                 + "VALUES (?,?,?,?,?,?,?,?) " ;
              
              PreparedStatement CheckBarcode = conn.prepareStatement(checkBarcode);
              CheckBarcode.setInt(1, barcode);
              ResultSet barCode = CheckBarcode.executeQuery();
              
              System.out.println(barcode);

              if(!barCode.next()){
              PreparedStatement addcargo = conn.prepareStatement(addCargo);
              
              addcargo.setInt(1, barcode);
              addcargo.setString(2, product);
              addcargo.setInt(3, gewicht);
              addcargo.setInt(4, volume);
              addcargo.setString(5, gekoeld);
              addcargo.setString(6, aankomstTijd);
              addcargo.setInt(7, klantID);
              addcargo.setString(8, bezorger);
              
              addcargo.execute();
              conn.commit();
              
              Added.setVisible(true);
              }else{
                  error.setText("Barcode already exists!");
                  error.setVisible(true);
                  Added.setVisible(false);
                  
              }
                          
             conn.close();
         }catch (SQLException ex) {
            Logger.getLogger(SymulatieSchermController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
