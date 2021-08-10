package org.zerock.threadex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ManyToManyServer {

    //close 문제
    // 중간에 누군가 나가버리면 에러
    // 익명의 문제 누가 채팅을 보내는지 알 수 없음

    //bad code
    public static void main (String[] args) throws Exception {

        ServerSocket server = new ServerSocket(9999);
        System.out.println("ready...");
        List<DataOutputStream> dosList = new ArrayList<>();

        while(true) { // 연결이 들어옴

            Socket socket = server.accept();
            System.out.println(socket);
            DataInputStream din = new DataInputStream(socket.getInputStream()); //입력값을 읽기
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dosList.add(dos);

            //별도의 존재가 din에 접근해서 읽는 것
            new Thread(()->{ // 원래 new Thread를 만들 때에는 runable이 들어가야 하는데 그걸 람다식으로 대체함.
                            // 원래는 new Thread(new Runnable() { @Override run())}.start()
                while (true){
                    try {
                        String str = din.readUTF(); // 별도의 의미(main의 영향이 거의 없는 것)여서 예외처리가 필수적
                        System.out.println(str);

                        for(int i=0;i<dosList.size(); i++){
                            dosList.get(i).writeUTF(str);
                        }

                        //dos : 클라이언트 한 명이랑 연결 된 것.
//                        dos.writeUTF("SERVER: " + str);// 내가 보낸 메세지는 나한테는 오는데 옆사람한테는 안감
                    }catch (Exception e) {
                        dosList.remove(dos); // 문제가 생긴 사람을 리스트에서 빼준다.

                    }
                }
            }).start(); // Thread를 스타트 - > 계속 다음사용자를 받을 수 있다.( Thread를 만들고 다시 만들고 다시 만들고 반복)
            // 들어오는 client마다 Thread(직원)이 계속 붙는 다는 느낌.

            // String msg = din.readUTF(); //block이 걸림 : 사용자가 메세지를 보내지 않으면 다시 while루프를 돌릴 수가 없음.(메세지를 계속 읽을 수가 없음)
            //                                           -> 원래 여기서 while이 돌면 바깥쪽 다른 while은 돌릴 수가 없음 : Thread사용필요

        }// end while
    }
}
