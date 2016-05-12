/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pad;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



/**
 *
 * @author florian_2
 */
public class MITM {
    public MITM(){
        
    }
    public static String naam;
    public static String autoriteit;
    public static DashboardManagerController dashboardManagerController = new DashboardManagerController();
    public static DashboardMedewerkerController dashboardMedewerkerController = new DashboardMedewerkerController();
    public static CargoLijstController cargoLijstController = new CargoLijstController();
    public static int screenSizeX = 800;
    public static int screenSizeY = 600;
    
    
}