package org.zerock.threadex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OneToOneServer {

    //bad code
    public static void main(String[] args) throws Exception{

        Scanner keyScanner = new Scanner(System.in);

        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Ready...");
        Socket client = serverSocket.accept();

        DataInputStream din = new DataInputStream(client.getInputStream());
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());

        new Thread(()->{
            while (true) {
                String serverMsg = keyScanner.nextLine();
                try {
                    dos.writeUTF(serverMsg);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            String clientMsg = din.readUTF();
            System.out.println(clientMsg);
        }
    }



}
