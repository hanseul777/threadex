package org.zerock.threadex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class OneToOneClient {
    // bad code
    public static void main(String[] args) throws Exception{

        Scanner keyScanner = new Scanner(System.in);
        Socket socket = new Socket("127.0.0.1",9999);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream din = new DataInputStream(socket.getInputStream());

        new Thread(()-> {
            while (true) {
                String serverMsg;
                try {
                    serverMsg = din.readUTF();
                    System.out.println("SERVER: " + serverMsg);
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        while (true) {
            System.out.println("INSERT YOUR MESSAGE");
            String msg = keyScanner.nextLine();
            dos.writeUTF(msg);
        }

    }
}
