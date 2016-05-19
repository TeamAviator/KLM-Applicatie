
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jeroen
 */
public class CargoLijstController implements Initializable {
    
    @FXML
   private TextField txtCargoNummer;
   @FXML
   private Label Volume;
   @FXML
   private Label Gewicht;
   @FXML
   private Label Product;
   @FXML
   private Label Klantnaam;
   @FXML
   private Label Bezorger;
   @FXML
   private Label Gekoeld;
   @FXML
   private Label DatumTijd;
   @FXML 
   private Circle fohID;
   @FXML 
   private Circle rfcID;
   @FXML
   private Label errorCargoID;
   
   
   private int vrachtID;
   private int klantID;
   private String product;
   private String klantnaam;
   private String voornaam;
   private String tussenvoegsel;
   private String achternaam;
   private int gewicht;
   private int volume;
   private String gekoeld;
   private String datumTijd;
   private String bezorger;
   private String FoH;
   private String RFC;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Volume.setText("");
        Gewicht.setText("");
        Product.setText("");
        Klantnaam.setText("");
        Bezorger.setText("");
        Gekoeld.setText("");
        DatumTijd.setText("");
        fohID.setVisible(false);
        rfcID.setVisible(false);
        errorCargoID.setVisible(false);
        
        
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
        
        //De nodige controllers worden opgehaald en een variabele meegegeven

    
        @FXML
    private void home(ActionEvent event) throws IOException {

      if(MITM.autoriteit == "mg"){
        AnchorPane dashboardManager = MITM.dashboardManagerController.getDashboardManagerScreen();
        Scene scene = new Scene(dashboardManager, MITM.screenSizeX, MITM.screenSizeY);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
      }
      else if(MITM.autoriteit == "mw"){
        AnchorPane dashboardMedewerker = MITM.dashboardMedewerkerController.getDashboardMedewerkerController();
        Scene scene = new Scene(dashboardMedewerker, MITM.screenSizeX, MITM.screenSizeY);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
      }
      
      
      
    }
    @FXML
    
   private void logout(ActionEvent event) throws IOException {
        AnchorPane getLoginScreen  = MITM.loginController.getLoginScreen();
        Scene scene = new Scene(getLoginScreen, 600, 400);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
   }
   
   
   
   @FXML
   private void zoeken(ActionEvent event) throws IOException {
       
        Volume.setText("");
        Gewicht.setText("");
        Product.setText("");
        Klantnaam.setText("");
        Bezorger.setText("");
        Gekoeld.setText("");
        DatumTijd.setText("");
        
        
       
      
       
       if(txtCargoNummer.getText() == null || txtCargoNummer.getText().trim().isEmpty()){
           vrachtID = 0;
       }else{ vrachtID = Integer.parseInt(txtCargoNummer.getText());}
       
       if(vrachtID != 0){
           
           
           
       System.out.println(vrachtID);
       try (Connection conn = Database.initDatabase()) {
            //Select the employee with the given username and password
            String selectVracht
                    = "SELECT vracht_id, product, gewicht, volume, gekoeld, datum_tijd, klant_klant_id, bezorger, foh, rfc "
                    + "FROM vracht "
                    + "WHERE vracht_id = ? ";

            String selectKlant
                    = "SELECT voornaam, tussenvoegsel, achternaam "
                    + "FROM klant "
                    + "WHERE klant_id = ?";

            //Create prepared statment
            PreparedStatement preparedStatement1 = conn.prepareStatement(selectVracht);

            //set values
            preparedStatement1.setInt(1, vrachtID);
            
            //execute query and get results
            ResultSet vracht = preparedStatement1.executeQuery();

            //if there are records found.
            if (vracht.next()) {
                errorCargoID.setVisible(false);
                
                klantID = vracht.getInt("klant_klant_id");
                
                product = vracht.getString("product");
                
                gewicht = vracht.getInt("gewicht");
                
                volume = vracht.getInt("volume");
                
                gekoeld = vracht.getString("gekoeld");
                
                datumTijd = vracht.getString("datum_tijd");
                
                bezorger = vracht.getString("bezorger");
                
                FoH = vracht.getString("foh");
                
                RFC = vracht.getString("rfc");
                
                System.out.println(product +" "+ gewicht +" "+ volume +" "+ gekoeld +" "+ datumTijd +" "+ bezorger);
  
            } else if( !vracht.next() ){ 
                errorCargoID.setText("CargoNumber doesn't exist!");
                errorCargoID.setVisible(true);
                conn.close();   
            }
            
            //Create prepared statment
            PreparedStatement preparedStatement2 = conn.prepareStatement(selectKlant);
            //set values
            preparedStatement2.setInt(1, klantID);
            //execute query and get results
            ResultSet klant = preparedStatement2.executeQuery();
            //if there are records found.
            if(klant.next()){
                voornaam = klant.getString("voornaam");
                tussenvoegsel = klant.getString("tussenvoegsel");
                achternaam = klant.getString("achternaam");
                
                if(tussenvoegsel == null){tussenvoegsel = ""; }
                
                klantnaam = voornaam +" "+ tussenvoegsel +" "+ achternaam;
            }
            
            System.out.println(FoH);
           switch (FoH) {
               case "nee":
                   fohID.setFill(Color.RED);
                   fohID.setVisible(true);
                   break;
               case "ja":
                   fohID.setFill(Color.GREEN);
                   fohID.setVisible(true);
                   break;
           }
            
           switch (RFC) {
               case "nee":
                   rfcID.setFill(Color.RED);
                   rfcID.setVisible(true);
                   break;
               case "ja":
                   rfcID.setFill(Color.GREEN);
                   rfcID.setVisible(true);
                   break;
           }
            
            Product.setText(product);
            Gewicht.setText(String.valueOf(gewicht)+ " KG");
            Volume.setText(String.valueOf(volume)+ " m3");
            Gekoeld.setText(gekoeld);
            DatumTijd.setText(datumTijd);
            Bezorger.setText(bezorger);
            Klantnaam.setText(klantnaam);
            
           
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else {errorCargoID.setVisible(true); errorCargoID.setText("Enter a Cargonumber please!"); }
   }
    @FXML
   private void foh(ActionEvent event) throws IOException {
        if(vrachtID != 0){
            System.out.println("Beste "+ klantnaam +",\n" +
                               "Uw vracht is zojuist geariveerd om "+datumTijd+" in de KLM cargo loods. Aangeleverd door "+bezorger+"\n" +
                               "De volgende gegevens zijn bij ons bekent:\n" +
                               "Product: "+product+"\n" +
                               "Gewicht: "+gewicht+" KG\n" +
                               "Volume: "+volume+" m3\n" +
                               "Gekoeld: "+gekoeld+"\n" +
                               "\n" +
                               "Nadat de vracht verder geïnspecteerd is zullen wij uw een melding hierover mededelen.\n" +
                               "\n" +
                               "Met vriendelijke groet,\n" +
                               "KLM Cargo");
        }
        
        try (Connection conn = Database.initDatabase()) {
            
            conn.setAutoCommit(false);
            //Select the employee with the given username and password
            String setFOH
                    = "UPDATE vracht "
                    + "SET foh = 'ja' "
                    + "WHERE vracht_id = ? ";

            //Create prepared statment
            
            PreparedStatement setFoH = conn.prepareStatement(setFOH);

            //set values
            setFoH.setInt(1, vrachtID);
           
            //execute update
             setFoH.executeUpdate();
             conn.commit();
            //if there are no records found.
            System.out.println(FoH);
            
           
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
   
   } 
    
}
