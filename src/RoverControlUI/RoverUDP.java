/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RoverControlUI;

import java.net.*;
import java.io.*;
/**
 *
 * @author Matt
 */


public class RoverUDP extends Thread {
    
    private DatagramSocket datagramSocket;
    public String leftRPM;
    public String rightRPM;
    public String localAddress;
    private String localPort;
    private InetAddress remoteAddr;
    
    public RoverUDP(int port, String ipAddress) {
        try{
            datagramSocket = new DatagramSocket(port);
            System.out.println("Socket initialized on port  " + Integer.toString(port) + " :D");
            remoteAddr = InetAddress.getByName(ipAddress);
        } catch(IOException ex) {
            System.out.println("Unable to initialize socket on UDP port " + Integer.toString(port));
        }
        
        localAddress = datagramSocket.getLocalAddress().toString();
        localPort = Integer.toString(datagramSocket.getLocalPort());
    }
    
    public void run() {
        System.out.println("Listening on UDP " + localAddress + ":" + localPort);
        byte[] buffer = new byte[128];
        DatagramPacket rxPacket = new DatagramPacket(buffer, buffer.length);
        
        while(true) {
            try {
                //--Recieve data--
                datagramSocket.receive(rxPacket);
                buffer = rxPacket.getData();
                convertRPM(buffer);
                System.out.println("Left: " + leftRPM + " Right: " + rightRPM);
                //--Send data--
                DatagramPacket sendPacket = packJoystickData();
                datagramSocket.send(sendPacket);
            } catch (IOException ex) {
                System.err.print(ex);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.exit(0);
            }
        }
    }
    
    private void convertRPM(byte[] data) {
        leftRPM = Integer.toString(data[0] + (data[1] << 8));
        rightRPM = Integer.toString(data[2] + (data[3] << 8));
    }
    
    public void closeSocket() {
        datagramSocket.close();
    }
    
    public DatagramPacket packJoystickData() throws IOException{
        String sendString = JoystickUpdater.leftStickY + ":" + JoystickUpdater.rightStickY;
        //String sendString = "100:100";
        byte[] sendData = sendString.getBytes();
        return new DatagramPacket(sendData, sendData.length, remoteAddr, 30001);
    }
}
