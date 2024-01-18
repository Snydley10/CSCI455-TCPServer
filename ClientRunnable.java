package com.mycompany.csci455_project1;

import java.net.Socket;

/**
 * ClientRunnable listens to responses from server and has run method that's
 * called when ClientMain is run
 *
 * @author brandon snyder
 */
import java.io.*;

public class ClientRunnable extends Thread {

    private Socket socket;
    private BufferedReader input;

    //instantiate class with socket info and BufferedReader for input
    public ClientRunnable(Socket s) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        try {
            //continuously listens for Server responses
            while (true) {
                String response = input.readLine();
                System.out.println("From Server:\n\t" + response);
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
}
