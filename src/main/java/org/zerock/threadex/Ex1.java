package org.zerock.threadex;

public class Ex1 {
    //bad code
    public static void main(String[] args) throws Exception{
        // Thread 새로 생성 ( 실행해보면 이름이 Thread-0 )
        new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A" + i); // Thread의 이름을 출력
                try {
                    Thread.sleep(100); // 0.1초 동안은 잠깐 Thread가 CPU를 잡는 것을 멈췄다가 다시실행(잡을 수 있을지 없을지는 몰라)
                    // CPU의 여유가 없으면 잡지 못 할 가능성도 있다.
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // for에 대한 Thread를 생성

        // mainthread 생성
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " B" + i);
            Thread.sleep(100);
        }

    }
}
