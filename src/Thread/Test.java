package Thread;

import java.util.concurrent.FutureTask;

/**
 * @author hzy
 * @date 2023-03-03
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Resource r=new Resource();
        Producer p=new Producer(r);
        Consumer c=new Consumer(r);
        new Thread(p).start();
        new Thread(c).start();

    }
}
