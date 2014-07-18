/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RoverControlUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class RCUIController implements Initializable {
    @FXML
    private Tab graphTab;
    @FXML
    private AnchorPane LRPMContainer;
    @FXML
    private Label LRPMData;
    @FXML
    private Label RRPMData;
    @FXML
    private Label GPSLat;
    @FXML
    private Label GPSLong;
    @FXML
    private Tab webTab;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    @FXML
    private Circle joyStatusLight;
    @FXML
    private Label joyStatus;

    /**
     * Initializes the controller class.
     */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControlLoop task = new ControlLoop();
         
        LRPMData.textProperty().bind(task.LRPMValue);
        RRPMData.textProperty().bind(task.RRPMValue);
        joyStatus.textProperty().bind(task.ControllerStatus);
        joyStatusLight.fillProperty().bind(task.CircleColor);
        GPSLat.setText("GPS L");
        GPSLong.setText("GPS R");
        
        Thread t1 = new Thread(task);
        t1.setDaemon(true);
        t1.start();
    }    
    
}
