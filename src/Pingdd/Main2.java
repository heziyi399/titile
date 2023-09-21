package Pingdd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * @author hzy
 * @date 2023-03-12
 */
class Meeting {
    private static final int PRINT_COUNT = 10;
    private static Semaphore semaphoreA = new Semaphore(1);
    private static Semaphore semaphoreB = new Semaphore(0);
    private static Semaphore semaphoreC = new Semaphore(0);

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> print("A", semaphoreA, semaphoreB));
        Thread threadB = new Thread(() -> print("B", semaphoreB, semaphoreC));
        Thread threadC = new Thread(() -> print("C", semaphoreC, semaphoreA));

        threadA.start();
        threadB.start();
        threadC.start();
    }

    private static void print(String message, Semaphore current, Semaphore next) {
        for (int i = 0; i < PRINT_COUNT; i++) {
            try {
                current.acquire();
                System.out.print(message);
                next.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // 当某个线程打印完成后，释放下一个线程的许可，确保线程执行顺序
        next.release();
    }
}
