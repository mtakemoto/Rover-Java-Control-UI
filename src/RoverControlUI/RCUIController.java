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
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


/**
 * FXML Controller class
 *
 * @author Matt
 */
public class RCUIController implements Initializable {
    @FXML private Label leftRPMLabel;
    @FXML private Label rightRPMLabel;
    @FXML private Label gpsLatLabel;
    @FXML private Label gpsLongLabel;
    @FXML private Font x3;
    @FXML private Circle joyStatusLight;
    @FXML private Label joyStatusLabel;
    @FXML private Label leftYAxisLabel;
    @FXML private Label rightYAxisLabel;
    @FXML private TextArea consoleOutput;
    @FXML private TextField commandInput;
    @FXML private TextArea commandOutput;
    @FXML private Label commStatusLabel;
    @FXML private Rectangle txLight;
    @FXML private Rectangle rxLight;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControlLoop task = new ControlLoop();
         
        leftRPMLabel.textProperty().bind(task.LRPMValue);
        rightRPMLabel.textProperty().bind(task.RRPMValue);
        joyStatusLabel.textProperty().bind(task.ControllerStatus);
        joyStatusLight.fillProperty().bind(task.CircleColor);
        leftYAxisLabel.textProperty().bind(task.leftStickYData);
        rightYAxisLabel.textProperty().bind(task.rightStickYData);
        gpsLatLabel.setText("GPS L");
        gpsLongLabel.setText("GPS R");
        
        Thread t1 = new Thread(task);
        t1.setDaemon(true);
        t1.start();
    }    
    
}
