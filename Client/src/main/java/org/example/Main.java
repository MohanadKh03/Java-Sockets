package org.example;

import java.io.*;
import java.net.Socket;

public class Main {
    private static final String IP = "localhost";
    private static final int port = 3333;

    public static void main(String[] args) throws IOException {
        Socket s = new Socket(IP,port);
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String str="";
        while(!str.equals("0")){
            str=br.readLine();
            dout.writeUTF(str);
            dout.flush();
        }
        dout.close();
        s.close();
    }
}