/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoverControlUI;

import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Matt
 */
public class ControlLoop extends Task {
    private final int SOCKET = 30001;
    public StringProperty RRPMValue;
    public StringProperty LRPMValue;
    public StringProperty leftStickYData;
    public StringProperty rightStickYData;
    public StringProperty ControllerStatus;
    public ObjectProperty CircleColor;
    
    JoystickUpdater gamepad = new JoystickUpdater();
    RoverUDP udpSocket;
    
    ControlLoop() {
        RRPMValue = new SimpleStringProperty("No Data");
        LRPMValue = new SimpleStringProperty("No Data");
        leftStickYData = new SimpleStringProperty("N/C");
        rightStickYData = new SimpleStringProperty("N/C");
        
    //Do not touch.-----------------------
        gamepad.searchForControllers();
    //-------------------------------------
        
        if(gamepad.isConnected()) {
            CircleColor = new SimpleObjectProperty(Color.GREEN);
            ControllerStatus = new SimpleStringProperty(gamepad.getControllerName());
        }
        else {
            CircleColor = new SimpleObjectProperty(Color.ORANGE);
            ControllerStatus = new SimpleStringProperty("No gamepad found");
        }
        udpSocket = new RoverUDP(SOCKET, "192.168.240.1");
    }
    
    @Override
    protected Void call() {
        udpSocket.setDaemon(true);
        udpSocket.start();
        gamepad.setDaemon(true);
        gamepad.start();
        
        while (true) {
            if (isCancelled()) {
                break;
            }
            
            if(!gamepad.isConnected() && gamepad.isInitialized()) { //Was connected but is now disconnected
                updateColor(CircleColor, Color.RED);
                updateString(ControllerStatus, "Controller Disconnected!");
            }
            else {  
                updateString(leftStickYData, Integer.toString(-gamepad.leftStickY));
                updateString(rightStickYData, Integer.toString(-gamepad.rightStickY));
                updateString(LRPMValue, udpSocket.leftRPM);
                updateString(RRPMValue, udpSocket.rightRPM);
                updateColor(CircleColor, Color.LIME);
                updateString(ControllerStatus, gamepad.getControllerName());
            }
                
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                udpSocket.closeSocket(); //Open socket will keep program open, even if daemon.
                System.exit(0);
            }
        }
        return null;
    }
    
    public void updateString(final StringProperty string, final String value) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                string.setValue(value);
            }
        });
    }
    
    public void updateColor(final ObjectProperty shape, final Color newColor) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                shape.setValue(newColor);
            }
        });
    }
    
}
