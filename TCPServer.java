package com.mycompany.csci455_project1;

/**
 * TCPServer class with main method that establishes server socket and accepts
 * and creates new connections on new threads
 *
 * @author brandon snyder
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer {

    public static void main(String[] args) throws IOException {

        //used to store each thread
        ArrayList<ServerThread> threadList = new ArrayList<>();

        //create server socket and then stay open and create new server thread
        //for each attempt to connect
        try ( ServerSocket ss = new ServerSocket(6789)) {
            while (true) {
                Socket socket = ss.accept();
                ServerThread serverThread = new ServerThread(socket, threadList);
                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
