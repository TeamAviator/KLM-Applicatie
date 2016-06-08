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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author florian_2
 */
public class MeetgegevensController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Added.setVisible(false);
    }

    public AnchorPane getMeetegevensController() {
        AnchorPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/Views/Meetgegevens.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MeetgegevensController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }

    /**
     *
     * @param event
     * @throws java.io.IOException
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
    @FXML
    private Label Added;

    private int CargoNummer;
    private int Volume;
    private int Gewicht;
    private String DatumTijd;
    private String Beschadigd;

    public void addCargo(ActionEvent event) throws IOException {

        CargoNummer = Integer.parseInt(cargoNummer.getText());
        Volume = Integer.parseInt(volume.getText());
        Gewicht = Integer.parseInt(gewicht.getText());
        DatumTijd = aankomstTijd.getText();
        Beschadigd = beschadigd.getText();
        
        if(Beschadigd == "yes"|| Beschadigd == "Yes"){
            Beschadigd = "ja";
        }else if(Beschadigd == "no"||Beschadigd == "No"){
            Beschadigd = "nee";
        }

        try (Connection conn = Database.initDatabase()) {
            conn.setAutoCommit(false);
            String isNext
                    = "SELECT vracht_id "
                    + "FROM meetgegevens "
                    + "WHERE vracht_id = ? ";

            PreparedStatement IsNext = conn.prepareStatement(isNext);

            IsNext.setInt(1, CargoNummer);

            ResultSet volgende = IsNext.executeQuery();
            if (!volgende.next()) {
                commit();
                Added.setVisible(true);
            } else if (volgende.next()) {
                update();
            }

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SymulatieSchermController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void commit() {
        try (Connection conn = Database.initDatabase()) {
            conn.setAutoCommit(false);

            String addCargo
                    = "INSERT INTO meetgegevens (vracht_id, gewicht, volume, datum_tijd, beschadigd) "
                    + "VALUES (?,?,?,?,?) ";

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
            Logger.getLogger(SymulatieSchermController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void update() {
        try (Connection conn = Database.initDatabase()) {
            conn.setAutoCommit(false);

            String updateCargo
                    = "UPDATE meetgegevens SET vracht_id = ?, gewicht = ?, volume = ?, datum_tijd = ?, beschadigd = ? "
                    + "VALUES (?,?,?,?,?) ";

            //Create prepared statment
            PreparedStatement updateCARGO = conn.prepareStatement(updateCargo);

            //set values
            updateCARGO.setInt(1, CargoNummer);
            updateCARGO.setInt(2, Gewicht);
            updateCARGO.setInt(3, Volume);
            updateCARGO.setString(4, DatumTijd);
            updateCARGO.setString(5, Beschadigd);

            updateCARGO.executeUpdate();
            conn.commit();

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SymulatieSchermController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML

    private void logout(ActionEvent event) throws IOException {
        AnchorPane getLoginScreen = MITM.loginController.getLoginScreen();
        Scene scene = new Scene(getLoginScreen, 600, 400);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void cargo(ActionEvent event) throws IOException {

        AnchorPane cargoLijst = MITM.cargoLijstController.getCargoLijstController();
        Scene scene = new Scene(cargoLijst, MITM.screenSizeX, MITM.screenSizeY);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo");
        stage.setScene(scene);
        stage.show();

    }

}
