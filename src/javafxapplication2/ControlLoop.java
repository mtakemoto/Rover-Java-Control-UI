/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.concurrent.Task;

/**
 *
 * @author Matt
 */
public class ControlLoop {
        
    Task<Integer> task = new Task<Integer>() {
        @Override
        protected Integer call() throws Exception {
            int iterations;
            for (iterations = 0; iterations < 1000; iterations++) {
                if (isCancelled()) {
                    updateMessage("Cancelled");
                    break;
                }
                updateMessage("Iteration " + iterations);
                updateProgress(iterations, 1000);

                //Block the thread for a short time, but be sure
                //to check the InterruptedException for cancellation
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interrupted) {
                    if (isCancelled()) {
                        updateMessage("Cancelled");
                        break;
                    }
                }
            }
            return iterations;
        }
    };
       
}
