package com.mycompany.csci455_project1;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * ServerThread class that receives the server socket and any active threads.
 * Run method is called from TCPServer when new thread is needed.
 *
 * @author brandon snyder
 */
public class ServerThread extends Thread {

    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;
    private InetAddress address;
    private int port;
    public int messageCount = 0;

    //Constructor that recieves socket info and list of active threads from TCPServer
    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;

        //get IP address and remote port numbers
        this.address = this.socket.getInetAddress();
        this.port = this.socket.getPort();
    }

    @Override
    public void run() {
        try {
            //Input and Output for thread
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Connection to Server established");
            //recieves and sends messages to client until "exit" is received
            while (true) {
                String outputString = input.readLine();
                //if thread is to be closed
                if (outputString.equals("exit")) {
                    Thread.sleep(2100);
                    socket.close();
                    System.out.println("Connection to " + address + " on port number " + port + " closed.");
                    break;
                }

                ++messageCount;
                //prints client message to console and responds with total message count
                System.out.println("Message from " + address + " on port number " + port + ": " + outputString);
                output.println("Total messages recieved from all threads: " + totalMessages());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
          }  
    }

    //method to get message count from each thread and total them
    public int totalMessages() {
        int total = 0;
        for (ServerThread sT : threadList) {
            total += sT.messageCount;
        }
        return total;
    }
}
