import lombok.extern.slf4j.Slf4j;

import javax.management.remote.rmi.RMIServer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author hzy
 * @date 2022-12-22
 */

public class Jmm03_CodeVisibility {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static class Surmount implements Runnable{
        @Override
        public void run() {

            try {
                for(int i = 1; i < 4; i++){
                    Random rand = new Random();
                    int randomNum = rand.nextInt((3000 - 1000) + 1) + 1000;//产生1000到3000之间的随机整数
                    Thread.sleep(randomNum);
                    String name = Thread.currentThread().getName();
                    System.out.println(name+"翻过了第" + i +"个障碍");
                    cyclicBarrier.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        for (int i = 1; i < 6; i++){
            Thread thread = new Thread(new Surmount(),"选手"+ i );
            thread.start();
        }
        System.out.println("main is end");
    }
}
