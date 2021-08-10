package org.zerock.threadex;

public class Horse extends Thread{

    private String name; //말의 이름
    private int pos; // 말의 포지션

    public Horse(String name) {
        this.name = name;
        this.pos = 0;
    }

    @Override
    public void run() { // gallop()를 실행하기 위한 Thread의 메소드(run())를 Override함.
        this.gallop();
    }

    public void gallop() {
        for(int i=0; i <100; i++) { // 랜덤하게 출력하고 pos에 값 저장을 100번 반복
            int range = (int) (Math.random() * 10) + 1; // 1~10까지 랜덤하게 출력

            this.pos += range; // pos = pos + range

            int count = this.pos / 10; // 10개의 데이터가 나올 때마다 count에 저장
            for (int j = 0; j < count; j++) { //count에 저장하는걸 반복
                System.out.print("."); // count에 한 번 저장될 때마다 .을 출력
            }
            System.out.println(this.name + ": " + this.pos);
        }
        try { // 왜 Thread.sleep에 예외처리하는지는 21.08.04에 알려주신다고함.
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Horse h1 = new Horse("a");
        Horse h2 = new Horse("b");
        Horse h3 = new Horse("c");
        Horse h4 = new Horse("d");

        h1.start();
        h2.start();
        h3.start();
        h4.start();
    }


}
