package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Thread{

    Socket clientSocket;

    public Main(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(3333);
        System.out.println("Chat server is running...");
        while (true) {
            new Main(serverSocket.accept()).start();
        }
    }

    public void run() {
        System.out.println(this.clientSocket.getInetAddress().getHostName() + " has just connected!");
        try (DataInputStream din = new DataInputStream(clientSocket.getInputStream())) {
            String str;
            while (true) {
                try {
                    str = din.readUTF();
                    if (str.equals("0")) {
                        break; // Client requested to disconnect
                    }
                    displayMessage(str);
                } catch (IOException e) {
                    System.out.println("Client " + clientSocket.getInetAddress().getHostName() + " has disconnected!");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                this.clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void displayMessage(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String senderInfo = "[" + clientSocket.getInetAddress().getHostName() + "]";
        System.out.println(timestamp + " " + senderInfo + ": " + message);
    }
}
