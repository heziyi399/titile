package Thread;

import lombok.SneakyThrows;

/**
 * @author hzy
 * @date 2023-03-03
 */
public class Consumer implements Runnable {
    private Resource resource;
    public Consumer(Resource resource) {
        this.resource = resource;
    }
    @SneakyThrows
    @Override
    public void run() {
        while (true){
            resource.remove();//消费者类改成put即可
            Thread.sleep(1);
        }
    }
}
