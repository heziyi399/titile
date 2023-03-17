package Thread;

import lombok.SneakyThrows;

/**
 * @author hzy
 * @date 2023-03-03
 */
public class Producer implements Runnable {
    private Resource resource;
    public Producer(Resource resource) {
        this.resource = resource;
    }
    @SneakyThrows
    @Override
    public void run() {
        while (true){
            resource.put();//消费者类改成put即可
            Thread.sleep(1);
        }
    }
}
