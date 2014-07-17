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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Task task = new Task<Void>() {
            @Override public Void call() {
                final int max = 1000000;
                
                while(true) {
                    int i = 0;
                    if (isCancelled()) {
                       break;
                    }
                    i++;
                    updateMessage(Integer.toString(i));
                }
                return null;
                
            }
        };
         
        LRPMData.textProperty().bind(task.messageProperty());
        RRPMData.setText("Right RPM");
        GPSLat.setText("GPS L");
        GPSLong.setText("GPS R");
        
        Thread t1 = new Thread(task);
        t1.setDaemon(true);
        t1.start();
    }
    
    public Label getLabel() {
        return LRPMData;
    }
    
    public static void loop() {
        
    }
    
}
