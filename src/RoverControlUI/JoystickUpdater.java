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

public class JoystickUpdater {
    Component[] componentList;
    Controller controller = null;
    
    public int leftStickX = 0;
    public int leftStickY = 0;
    
    public void searchForControllers() {
        Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
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
                if(componentId == Component.Identifier.Axis.Y){
                    leftStickY = scaleAxisValue(component.getPollData()); 
                    continue;
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
        return (int)(axisValue*127);
    }
}
