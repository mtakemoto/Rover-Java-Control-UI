/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RoverControlUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 *
 * @author Matt
 */
public class RoverControlUI extends Application {
    
    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage cameraStage1 = new Stage();
        Stage cameraStage2 = new Stage();
        Stage cameraStage3 = new Stage();
        
        Scene scene1 = new Scene(root);
        Scene frontCamLeft = new Scene(new Browser("http://192.168.1.132/axis-cgi/mjpeg/video.cgi"));
        Scene frontCamRight = new Scene(new Browser("http://192.168.1.120/axis-cgi/mjpeg/video.cgi"));
        Scene rearCam = new Scene(new Browser("http://192.168.1.227"));
        
        
        /*stageInit(cameraStage1, frontCamLeft, "Front Left Cam");
        stageInit(cameraStage2, frontCamRight, "Front Cam Right");
        stageInit(cameraStage3, rearCam, "Rear Camera");*/
        stageInit(mainStage, scene1, "PISCES Rover");
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void stageInit(Stage stage, Scene scene, String windowName) {
        stage.setTitle(windowName);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
