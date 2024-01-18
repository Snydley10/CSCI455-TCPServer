package com.mycompany.csci455_project1;
 
/**
 * Client class with main method. Connects to server socket. Sends data to
 * server using PrintWriter
 *
 * @author brandon snyder
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientMain {

    public static void main(String[] args) {
        try ( Socket socket = new Socket("localhost", 6789)) {

            //PrintWriter to send data to server
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            //get user console input
            Scanner scanner = new Scanner(System.in);
            String userInput;

            //creates new ClientRunnable and new Thread and starts
            ClientRunnable clientRunnable = new ClientRunnable(socket);
            new Thread(clientRunnable).start();

            do {
                //gives server time to respond before running
                Thread.sleep(100);

                //receives user message and sends to server
                System.out.println("Enter message: ");
                userInput = scanner.nextLine();
                output.println(userInput);

                //if "exit", sleeps for 2 seconds, and closes connection
                if (userInput.equals("exit")) {
                    Thread.sleep(2000);
                    socket.close();
                    break;
                }
            } while (!userInput.equals("exit"));
            scanner.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
