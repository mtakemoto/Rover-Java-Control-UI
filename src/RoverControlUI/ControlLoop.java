/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoverControlUI;

import javafx.concurrent.Task;
import java.util.Observable;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Matt
 */
public class ControlLoop extends Task {

    public StringProperty RRPMValue;
    public StringProperty LRPMValue;
    
    ControlLoop() {
        RRPMValue = new SimpleStringProperty("");
        LRPMValue = new SimpleStringProperty("");
    }
    
    @Override
    protected Void call() {
        
        int i = 0;
        while (true) {
            if (isCancelled()) {
                break;
            }
            i++;
            updateString(LRPMValue, Integer.toString(i));
            updateString(RRPMValue, Integer.toString(i));
            try {
                Thread.sleep(300);
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
    
}
