package ClassTest;

/**
 * @author hzy
 * @date 2023-01-26
 */
public class ClassSon2  extends ClassFather {
    public ClassSon2(){

    }

    public  static void method(){
        System.out.println("i am son");
    }

    public static void main(String[] args) throws InterruptedException {
    ClassFather cls = new ClassSon2();




        Thread.sleep(3);
    }


}
