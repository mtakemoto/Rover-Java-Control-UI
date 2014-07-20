package RoverControlUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matt
 */

import net.java.games.input.*;
import net.java.games.input.Component.Identifier;
import java.lang.reflect.*;

public class JoystickUpdater {
    Controller[] controllerList;
    Controller controller = null;
    Component[] componentList;
    
    
    public static int leftStickX;
    public static int leftStickY;
    public static int rightStickX;
    public static int rightStickY;
    
    public void searchForControllers() { //Needs to be optimized to not consume crazy CPU when controller == D/C
        try {
            ControllerEnvironment ce = createDefaultEnvironment();
            controllerList = ce.getControllers();
        } catch (ReflectiveOperationException ex) {
            System.err.print(ex);
        }
        
        for (int i = 0; i < controllerList.length; i++) {
            if (controllerList[i].getType() == Controller.Type.GAMEPAD) {
                controller = controllerList[i];
                componentList = controller.getComponents();
            }
        }
    }
    
    public void updateController() {
        if(controller == null || !controller.poll()) {
            searchForControllers();
        }
           
        //Loop through controller components
        for(int i = 0; i<componentList.length; i++) {
            Component component = componentList[i];
            Identifier componentId = component.getIdentifier();
                   
            if(component.isAnalog()) {
                // X axis
                if(componentId == Component.Identifier.Axis.X){
                    leftStickX = scaleAxisValue(component.getPollData()); 
                    continue;
                }
                // Y Axis
                else if(componentId == Component.Identifier.Axis.Y){
                    leftStickY = scaleAxisValue(component.getPollData()); 
                    continue;
                }
                else if(componentId == Component.Identifier.Axis.RX) {
                    rightStickX = scaleAxisValue(component.getPollData());
                }
                else if(componentId == Component.Identifier.Axis.RY) {
                    rightStickY = scaleAxisValue(component.getPollData());
                }
            }
        }     
    }
    
    public boolean isConnected() {
        if(controller == null) {
            return false;
        }
        else {
            return controller.poll();
        }
    }
    
    public String getName() {
        return controller.getName();
    }
    
     public int scaleAxisValue(float axisValue) {
        return (int)(axisValue*3000);
    }
     
     private static ControllerEnvironment createDefaultEnvironment() throws ReflectiveOperationException {
        // Find constructor (class is package private, so we can't access it directly)
        Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
        Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

        // Constructor is package private, so we have to deactivate access control checks
        constructor.setAccessible(true);

        // Create object with default constructor
        return constructor.newInstance();
    }
}
