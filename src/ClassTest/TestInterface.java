package ClassTest;

/**
 * @author hzy
 * @date 2023-01-28
 */
public interface TestInterface {
    default String methodInterface(String method) {
        System.out.println("interface");
        return "11";
    }

static String staticMethodInterface(){
    System.out.println("static method");
    return "11";
}
}
