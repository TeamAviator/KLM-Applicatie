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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    @FXML
    private Label VolumeMeet;
    @FXML
    private Label GewichtMeet;
    @FXML
    private Label Beschadigd;
    @FXML
    private Label DatumTijdMeet;
    @FXML
    private Label ExceedVolume;
    @FXML
    private Label ExceedGewicht;

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
    private int volumeMeet;
    private int gewichtMeet;
    private String beschadigd;
    private String datumTijdMeet;
    private String exceedVolume;
    private String exceedGewicht;
    private String rfc_bericht;
    private String foh_bericht;
    private String mis_bericht;
    private boolean erIsNiks = false;
    /**
     * Initializes the controller class.
     *
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
        VolumeMeet.setText("");
        GewichtMeet.setText("");
        Beschadigd.setText("");
        DatumTijdMeet.setText("");

        ExceedVolume.setVisible(false);
        ExceedGewicht.setVisible(false);
        ExceedVolume.setText("");
        ExceedGewicht.setText("");
        rfc_bericht = "";
        foh_bericht = "";
        mis_bericht = "";

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

        if (MITM.autoriteit == "mg") {
            AnchorPane dashboardManager = MITM.dashboardManagerController.getDashboardManagerScreen();
            Scene scene = new Scene(dashboardManager, MITM.screenSizeX, MITM.screenSizeY);
            Stage stage = new Stage();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("KLM Cargo Dashboard");
            stage.setScene(scene);
            stage.show();
        } else if (MITM.autoriteit == "mw") {
            AnchorPane dashboardMedewerker = MITM.dashboardMedewerkerController.getDashboardMedewerkerController();
            Scene scene = new Scene(dashboardMedewerker, MITM.screenSizeX, MITM.screenSizeY);
            Stage stage = new Stage();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("KLM Cargo Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        MITM.stage1.close();

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
        MITM.stage1.close();
    }

    @FXML
    private void zoeken(ActionEvent event) throws IOException {
        resetData();
        getData();
        calculateData();
        getData();
        setData();
    }

    @FXML
    private void foh(ActionEvent event) throws IOException {

        if (vrachtID != 0 && "nee".equals(foh_bericht)) {

            try (Connection conn = Database.initDatabase()) {

                conn.setAutoCommit(false);
                //Select the employee with the given username and password
                String setFOH
                        = "UPDATE vracht "
                        + "SET foh = 'ja', foh_bericht = 'ja' "
                        + "WHERE vracht_id = ? ";

                //Create prepared statment
                PreparedStatement setFoH = conn.prepareStatement(setFOH);

                //set values
                setFoH.setInt(1, vrachtID);

                //execute update
                setFoH.executeUpdate();
                conn.commit();

                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
            }

            taal();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("FOH message");
            alert.setHeaderText(null);
            alert.setContentText("Dear Mr/Miss " + klantnaam + ",\n\n"
                    + "Your cargo just arrived at " + datumTijd + ", at KLM cargo. Delivered by " + bezorger + "\n"
                    + "Information about the following cargo:\n"
                    + "Barcode: "+ vrachtID + "\n"
                    + "Product: " + product + "\n"
                    + "Weight: " + gewicht + " KG\n"
                    + "Volume: " + volume + " m3\n"
                    + "Chilled: " + gekoeld + "\n"
                    + "\n"
                    + "After futher inspections, you will be notified.\n"
                    + "\n"
                    + "Sincerely,\n"
                    + "KLM Cargo");

            alert.showAndWait();
            getData();
            setData();
        }
    }

    MeetgegevensController meetgegevensController = new MeetgegevensController();

    @FXML
    private void addMeetgegevens(ActionEvent event) throws IOException {

        AnchorPane getMeetGegevens = meetgegevensController.getMeetegevensController();
        Scene scene = new Scene(getMeetGegevens, 800, 600);
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("KLM Cargo Measures");
        stage.setScene(scene);
        stage.show();

    }

    private void resetData() {
        Volume.setText("");
        Gewicht.setText("");
        Product.setText("");
        Klantnaam.setText("");
        Bezorger.setText("");
        Gekoeld.setText("");
        DatumTijd.setText("");

        VolumeMeet.setText("");
        GewichtMeet.setText("");
        Beschadigd.setText("");
        DatumTijdMeet.setText("");

        rfc_bericht = "";
        foh_bericht = "";
        mis_bericht = "";
    }

    private void getData() {
        if (txtCargoNummer.getText() == null || txtCargoNummer.getText().trim().isEmpty()) {
            vrachtID = 0;
        } else {
            vrachtID = Integer.parseInt(txtCargoNummer.getText());
        }

        if (vrachtID != 0) {
            try (Connection conn = Database.initDatabase()) {
                //Select the employee with the given username and password
                String selectVracht
                        = "SELECT vracht_id, product, gewicht, volume, gekoeld, datum_tijd, klant_klant_id, bezorger, foh, rfc, rfc_bericht, foh_bericht, mis_bericht  "
                        + "FROM vracht "
                        + "WHERE vracht_id = ? ";

                String selectKlant
                        = "SELECT voornaam, tussenvoegsel, achternaam "
                        + "FROM klant "
                        + "WHERE klant_id = ?";

                String selectMeetgegevens
                        = "SELECT vracht_id, gewicht, volume, datum_tijd, beschadigd "
                        + "FROM meetgegevens "
                        + "WHERE vracht_id = ?";

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

                    rfc_bericht = vracht.getString("rfc_bericht");

                    foh_bericht = vracht.getString("foh_bericht");

                    mis_bericht = vracht.getString("mis_bericht");

                } else if (!vracht.next()) {
                    errorCargoID.setText("Barcode doesn't exist!");
                    errorCargoID.setVisible(true);
                    rfcID.setVisible(false);
                    conn.close();
                         
                }

                //Create prepared statment
                PreparedStatement preparedStatement2 = conn.prepareStatement(selectKlant);
                //set values
                preparedStatement2.setInt(1, klantID);
                //execute query and get results
                ResultSet klant = preparedStatement2.executeQuery();
                //if there are records found.
                if (klant.next()) {
                    voornaam = klant.getString("voornaam");
                    tussenvoegsel = klant.getString("tussenvoegsel");
                    achternaam = klant.getString("achternaam");

                    if (tussenvoegsel == null) {
                        tussenvoegsel = "";
                    }

                    klantnaam = voornaam + " " + tussenvoegsel + " " + achternaam;
                }

                //Create prepared statment
                PreparedStatement preparedStatement3 = conn.prepareStatement(selectMeetgegevens);
                //set values
                preparedStatement3.setInt(1, vrachtID);
                //execute query and get results
                ResultSet meetgegevens = preparedStatement3.executeQuery();
                //if there are records found.
                if (meetgegevens.next()) {
                    erIsNiks = false;
                    volumeMeet = meetgegevens.getInt("volume");
                    gewichtMeet = meetgegevens.getInt("gewicht");
                    datumTijdMeet = meetgegevens.getString("datum_tijd");
                    beschadigd = meetgegevens.getString("beschadigd");
                }else{ erIsNiks = true;}
            } catch (SQLException ex) {
                Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);

            }
        } else {
            rfcID.setVisible(false);
            errorCargoID.setVisible(true);
            errorCargoID.setText("Enter a barcode please!");
        }
    }

    private void calculateData() {
        if ("ja".equals(FoH)&& erIsNiks == false) {
            boolean rfcStatus = true;
            double exceedVolumePercentage = ((double) volumeMeet / (double) volume) * 100;
            int exceedVolumeRound = (int) exceedVolumePercentage - 100;

            if (exceedVolumePercentage > 120 || exceedVolumePercentage < 80) {
                ExceedVolume.setVisible(true);
                ExceedVolume.setText("Exceeds by " + exceedVolumeRound + "%");
                RFC = "mis";

                rfcStatus = false;
            }

            double exceedGewichtPercentage = ((double) gewichtMeet / (double) gewicht) * 100;
            int exceedGewichtRound = (int) exceedGewichtPercentage - 100;

            if (exceedGewichtPercentage > 120 || exceedGewichtPercentage < 80) {
                rfcID.setFill(Color.YELLOW);
                ExceedGewicht.setVisible(true);
                ExceedGewicht.setText("Exceeds by " + exceedGewichtRound + "%");
                RFC = "mis";

                rfcStatus = false;
            }

            if ("ja".equals(beschadigd)) {
                rfcStatus = false;
                RFC = "mis";
            }

            if (rfcStatus) {
                RFC = "ja";
            }

            try (Connection conn = Database.initDatabase()) {
                conn.setAutoCommit(false);
                String setRFC
                        = "UPDATE vracht "
                        + "SET rfc = ? "
                        + "WHERE vracht_id = ? ";

                //Create prepared statment
                PreparedStatement setRfc = conn.prepareStatement(setRFC);

                //set values
                setRfc.setString(1, RFC);
                setRfc.setInt(2, vrachtID);

                //execute update
                setRfc.executeUpdate();
                conn.commit();
            } catch (SQLException ex) {
                Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            rfcID.setFill(Color.RED);
            rfcID.setVisible(true);
        }

        getData();

        if ("mis".equals(RFC) && "nee".equals(mis_bericht) || "ja".equals(beschadigd) && "nee".equals(mis_bericht)) {
            
            taal();
            Alert warning = new Alert(AlertType.WARNING);
            warning.setTitle("Probleem bericht");
            warning.setHeaderText(null);
            warning.setContentText("Dear Mr/Miss " + klantnaam + ",\n\n"
                    + "Upon further inspection, we concluded that your cargo does not meet the requirements necessary to be loaded in the plane. \n "
                    + "Information about the following cargo:\n"
                    + "Barcode: "+ vrachtID + "\n"
                    + "Product: " + product + "\n"
                    + "Weight: " + gewicht + " KG\n"
                    + "Volume: " + volume + " m3\n"
                    + "Chilled: " + gekoeld + "\n"
                    + "Damaged:" + beschadigd +"\n"
                    + "\n"
                    + "Sincerely,\n"
                    + "KLM Cargo");

            warning.showAndWait();
            getData();
            try (Connection conn = Database.initDatabase()) {
                conn.setAutoCommit(false);
                String setMISbericht
                        = "UPDATE vracht "
                        + "SET mis_bericht = 'ja' "
                        + "WHERE vracht_id = ? ";

                //Create prepared statment
                PreparedStatement setMisBericht = conn.prepareStatement(setMISbericht);

                //set values
                setMisBericht.setInt(1, vrachtID);

                //execute update
                setMisBericht.executeUpdate();
                conn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if ("nee".equals(rfc_bericht) && "ja".equals(RFC)) {
            taal();
            Alert rfc = new Alert(AlertType.INFORMATION);
            rfc.setTitle("RFC bericht");
            rfc.setHeaderText(null);
            rfc.setContentText("Dear Mr/Miss " + klantnaam + ",\n\n"
                    + "Upon further inspection, we concluded that your cargo meets the requirements necessary to be loaded in the plane. \n "
                    + "Information about the following cargo:\n"
                    + "Barcode: "+ vrachtID + "\n"
                    + "Product: " + product + "\n"
                    + "Weight: " + gewicht + " KG\n"
                    + "Volume: " + volume + " m3\n"
                    + "Chilled: " + gekoeld + "\n"
                    + "Damaged:" + beschadigd +"\n"
                    + "\n"
                    + "Sincerely,\n"
                    + "KLM Cargo");

            rfc.showAndWait();
            getData();
            try (Connection conn = Database.initDatabase()) {
                conn.setAutoCommit(false);
                String setRFCbericht
                        = "UPDATE vracht "
                        + "SET rfc_bericht = 'ja' "
                        + "WHERE vracht_id = ? ";

                //Create prepared statment
                PreparedStatement setRfcBericht = conn.prepareStatement(setRFCbericht);

                //set values
                setRfcBericht.setInt(1, vrachtID);

                //execute update
                setRfcBericht.executeUpdate();
                conn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(CargoLijstController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setData() {
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
        if ("ja".equals(FoH)) {
            switch (RFC) {
                case "nee":
                    rfcID.setFill(Color.RED);
                    rfcID.setVisible(true);
                    break;
                case "ja":
                    if ("ja".equals(FoH)) {
                        rfcID.setFill(Color.GREEN);
                        rfcID.setVisible(true);
                    } else {
                        rfcID.setFill(Color.RED);
                    }
                    break;
                case "mis":
                    rfcID.setFill(Color.YELLOW);
                    rfcID.setVisible(true);
                    break;
            }
        } else if ("nee".equals(FoH)) {
            rfcID.setFill(Color.RED);
            rfcID.setVisible(true);
        }

        taal();

        Product.setText(product);
        Gewicht.setText(String.valueOf(gewicht) + " KG");
        Volume.setText(String.valueOf(volume) + " m3");
        Gekoeld.setText(gekoeld);
        DatumTijd.setText(datumTijd);
        Bezorger.setText(bezorger);
        Klantnaam.setText(klantnaam);

        DatumTijdMeet.setText(datumTijdMeet);
        GewichtMeet.setText(String.valueOf(gewichtMeet) + " KG");
        VolumeMeet.setText(String.valueOf(volumeMeet) + " m3");

        Beschadigd.setText(beschadigd);

    }
    private void taal(){
        if (beschadigd == "ja") {
                beschadigd = "yes";
            } else if (beschadigd == "nee") {
                beschadigd = "no";
            }
            if (gekoeld == "ja") {
                gekoeld = "yes";
            } else if (gekoeld == "nee") {
                gekoeld = "no";
            }
    }

}
