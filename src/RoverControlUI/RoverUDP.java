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
    private int leftRPM;
    private String localAddress;
    private String localPort;
    
    public RoverUDP(int port) {
        try{
            datagramSocket = new DatagramSocket(port);
            System.out.println("Socket initialized on port  " + Integer.toString(port) + " :D");
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
                datagramSocket.receive(rxPacket);
                buffer = rxPacket.getData();
                //Test Code----------
                String sentence = new String(buffer, 0, buffer.length);
                System.out.println("Recieved: " + sentence);
                //Send data
                InetAddress IPAddress = rxPacket.getAddress();
                String sendString = "Hi Desktop! --Laptop";
                byte[] sendData = sendString.getBytes("UTF-8");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, rxPacket.getPort());
                datagramSocket.send(sendPacket);
            } catch (IOException ex) {
                System.err.print(ex);
            }
        }
    }
    
    private String shiftPacket(byte[] data) {
        return Integer.toString(data[0] + data[1] << 8);
    }
    
    public void closeSocket() {
        datagramSocket.close();
    }
    
    public String test() {
        return "Cake!";
    }
}
