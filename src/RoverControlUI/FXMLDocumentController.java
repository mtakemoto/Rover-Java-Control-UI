/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RoverControlUI;

import java.util.Timer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    public Tab graphTab;
    @FXML
    public AnchorPane LRPMContainer;
    @FXML
    public Label LRPMData;
    @FXML
    public Label RRPMData;
    @FXML
    public Label GPSLat;
    @FXML
    public Label GPSLong;
    @FXML
    public Tab webTab;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControlLoop task = new ControlLoop();
         
        LRPMData.textProperty().bind(task.LRPMValue);
        RRPMData.textProperty().bind(task.RRPMValue);
        GPSLat.setText("GPS L");
        GPSLong.setText("GPS R");
        
        Thread t1 = new Thread(task);
        t1.setDaemon(true);
        t1.start();
    }
    
    
}
