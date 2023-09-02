package Pingdd;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author hzy
 * @date 2023-03-12
 */
class Meeting {
    int id;
    int time;

    public Meeting(int id, int time) {
        this.id = id;
        this.time = time;
    }
}
public class Main2 {
  static  int[]top=new int[3];//人数上限
  static  int[]pay=new int[3];
  static List<String> users =new ArrayList<String>();
        static int[][] grid;
        static int rows, cols;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] prices = new int[n];
        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }
        List<Discount> discounts = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            discounts.add(new Discount(b, c));
        }
        scanner.close();

        long result = calculateMinimumCost(n, prices, discounts);
        System.out.println(result);
    }

    static class Discount {
        int b;
        int c;

        Discount(int b, int c) {
            this.b = b;
            this.c = c;
        }
    }

    public static long calculateMinimumCost(int n, int[] prices, List<Discount> discounts) {
        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + prices[i - 1];
            for (Discount discount : discounts) {
                if (i >= discount.b) {
                    dp[i] = Math.min(dp[i], dp[i - discount.b] + prices[i - 1] - discount.c);
                }
            }
        }

        return dp[n];
    }

    public static String arrayToString(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int num : arr) {
            builder.append(num);
        }
        return builder.toString();
    }
}
