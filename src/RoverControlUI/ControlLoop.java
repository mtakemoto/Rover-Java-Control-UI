/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoverControlUI;

import javafx.concurrent.Task;
import java.util.Observable;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.java.games.input.*;
import net.java.games.input.Component.Identifier;

/**
 *
 * @author Matt
 */
public class ControlLoop extends Task {

    public StringProperty RRPMValue;
    public StringProperty LRPMValue;
    public StringProperty ControllerStatus;
    public ObjectProperty CircleColor;
    
    JoystickUpdater gamepad = new JoystickUpdater();
    
    ControlLoop() {
        RRPMValue = new SimpleStringProperty("");
        LRPMValue = new SimpleStringProperty("");
        
    //Do not touch-------------------------
        gamepad.searchForControllers();
    //-------------------------------------
        
        if(gamepad.isConnected()) {
            CircleColor = new SimpleObjectProperty(Color.GREEN);
            ControllerStatus = new SimpleStringProperty(gamepad.getName());
        }
        else {
            CircleColor = new SimpleObjectProperty(Color.RED);
            ControllerStatus = new SimpleStringProperty("No gamepad found");
        }
        
        
    }
    
    @Override
    protected Void call() {
        while (true) {
            if (isCancelled()) {
                break;
            }
            updateString(LRPMValue, Integer.toString(gamepad.leftStickX));
            updateString(RRPMValue, Integer.toString(gamepad.leftStickY));
            
            if(!gamepad.isConnected()) {
                updateColor(CircleColor, Color.RED);
                updateString(ControllerStatus, "Controller Disconnected!");
            }
            else {
                gamepad.updateController();   
            }
                
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.exit(0);
            }
        }
        return null;
    }
    
    public void updateString(StringProperty string, String value) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                string.setValue(value);
            }
        });
    }
    
    public void updateColor(ObjectProperty shape, Color newColor) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                shape.setValue(newColor);
            }
        });
    }
    
}
