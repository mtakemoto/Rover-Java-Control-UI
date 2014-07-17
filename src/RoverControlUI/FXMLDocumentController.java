/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxapplication2;

import java.util.Timer;
import java.net.URL;
import java.util.ResourceBundle;
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
    private Tab webTab;
    @FXML
    private SubScene webScene;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LRPMData.setText("9001");
    }    
    
}
