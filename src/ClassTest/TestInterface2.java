package ClassTest;

/**
 * @author hzy
 * @date 2023-01-28
 */
public interface TestInterface2 {
    default String methodInterface(String method) {
        System.out.println("interface2");
        return "11";
    }

}
