import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author hzy
 * @date 2023-02-11
 */
public class CyclicBarrierTest {
    /**
     * 工作线程
     */
    private static class Work implements Runnable{
        private CyclicBarrier cyclicBarrier ;

        public Work(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        //只有所有人都完成第一阶段才能开始第二阶段
        public void run() {

            firstwork();
            try {
                //等待完成第一阶段工作
                cyclicBarrier.await();
                secondWork();
                //完成第二阶段工作
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        //work1
        private void firstwork(){
            System.out.println(Thread.currentThread().getName()+"完成第一项工作");
        }
        //work2
        private void secondWork(){
            System.out.println(Thread.currentThread().getName()+"完成第二阶段");
        }
    };

    //通知线程
    private static class NoticeThread implements Runnable{
        boolean [] flag = new boolean[2] ;
        public void run() {
            if (!flag[0]){
                System.out.println("通知:第一阶段完成");
                flag[0] = true;
            }else
            if( !flag[1]){
                System.out.println("通知:第二阶段完成");
            }
        }
    }
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10,new NoticeThread());
        for (int i =0 ;i<10 ;i++){
            new Thread(new Work(cyclicBarrier)).start();
        }
    }

}
