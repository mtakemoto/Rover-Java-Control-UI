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
import java.net.*;
import java.io.*;

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
            ControllerStatus = new SimpleStringProperty(gamepad.getName());
        }
        else {
            CircleColor = new SimpleObjectProperty(Color.RED);
            ControllerStatus = new SimpleStringProperty("No gamepad found");
        }
        
        udpSocket = new RoverUDP(30001);
    }
    
    @Override
    protected Void call() {
        udpSocket.setDaemon(true);
        udpSocket.start();
        
        while (true) {
            if (isCancelled()) {
                break;
            }
            
            if(!gamepad.isConnected()) {
                updateColor(CircleColor, Color.RED);
                updateString(ControllerStatus, "Controller Disconnected!");
                
                gamepad.searchForControllers();
            }
            else {
                gamepad.updateController();   
                updateString(leftStickYData, Integer.toString(gamepad.leftStickY));
                updateString(rightStickYData, Integer.toString(gamepad.rightStickY));
                updateString(LRPMValue, udpSocket.leftRPM);
                updateString(RRPMValue, udpSocket.rightRPM);
                updateColor(CircleColor, Color.LIME);
                updateString(ControllerStatus, gamepad.getName());
            }
                
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                udpSocket.closeSocket();
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
    
    /*public void openSocket(int port) {
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(30000);
            System.out.println("Socket Initialized!");
        } catch(IOException ex) {
            System.out.println("No Socket Connection!");
        }
    }
    
    public void monitorSocket() {
        try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            System.out.println(serverSocket.isClosed());
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            //System.out.println(in.readShort());
            //System.out.println(in.readShort());
            //DataOutputStream out =  new DataOutputStream(server.getOutputStream());
            //out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
         }catch(IOException e)
         {
            e.printStackTrace();
         }
    }*/
    
}
