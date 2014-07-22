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

public class JoystickUpdater extends Thread {

    Controller[] controllerList;
    Controller controller = null;
    Component[] componentList;

    public static int leftStickX;
    public static int leftStickY;
    public static int rightStickX;
    public static int rightStickY;

    public void searchForControllers() { 
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

    public void run() {
        //Loop through controller components
        int timeOut;
        while (true) {
            if (isConnected()) {
                scanComponents();
                timeOut = 50;
            }
            else {
                searchForControllers();
                timeOut = 2000;
            }
            
            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException ex) {
                System.exit(0);
            }
        }
    }

    public void scanComponents() {
        for (int i = 0; i < componentList.length; i++) {
            Component component = componentList[i];
            Identifier componentId = component.getIdentifier();

            if (component.isAnalog()) {
                // X axis
                if (componentId == Component.Identifier.Axis.X) {
                    leftStickX = scaleAxisValue(component.getPollData());
                    continue;
                }
                // Y Axis
                else if (componentId == Component.Identifier.Axis.Y) {
                    leftStickY = scaleAxisValue(component.getPollData());
                    continue;
                }
                else if (componentId == Component.Identifier.Axis.RX) {
                    rightStickX = scaleAxisValue(component.getPollData());
                }
                else if (componentId == Component.Identifier.Axis.RY) {
                    rightStickY = scaleAxisValue(component.getPollData());
                }
            }
        }
    }

    public boolean isConnected() {
        if (controller == null) {
            return false;
        }
        else {
            return controller.poll();
        }
    }

    public boolean isInitialized() {
        return controller != null;
    }

    public String getControllerName() {
        return controller.getName();
    }

    public int scaleAxisValue(float axisValue) {
        return (int) (axisValue * 3000);
    }

    private static ControllerEnvironment createDefaultEnvironment() throws ReflectiveOperationException {
        // Find constructor (class is package private, so we can't access it directly)
        Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>) Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

        // Constructor is package private, so we have to deactivate access control checks
        constructor.setAccessible(true);

        // Create object with default constructor
        return constructor.newInstance();
    }
}
