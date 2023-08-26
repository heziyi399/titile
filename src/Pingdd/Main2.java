package Pingdd;

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
        int i=10;
        int j=5+(i++);
        j+=i;
            System.out.println(j++);
        }

    static int eatBeans(int x, int y, int[][] grid, int[][] dp) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return 0;
        }

        if (dp[x][y] != 0) {
            return dp[x][y];
        }

        int eatenBeans = 1;
        int originalPower = grid[x][y];

        if (x + 1 < grid.length && originalPower > grid[x + 1][y]) {
            eatenBeans = Math.max(eatenBeans, 1 + eatBeans(x + 1, y, grid, dp));
        }

        if (x - 1 >= 0 && originalPower > grid[x - 1][y]) {
            eatenBeans = Math.max(eatenBeans, 1 + eatBeans(x - 1, y, grid, dp));
        }

        if (y + 1 < grid[0].length && originalPower > grid[x][y + 1]) {
            eatenBeans = Math.max(eatenBeans, 1 + eatBeans(x, y + 1, grid, dp));
        }

        if (y - 1 >= 0 && originalPower > grid[x][y - 1]) {
            eatenBeans = Math.max(eatenBeans, 1 + eatBeans(x, y - 1, grid, dp));
        }

        dp[x][y] = eatenBeans;
        return eatenBeans;
    }


    public static void countUniqueSubarrays(int[] arr, Set<String> uniqueSubarrays) {
        int n = arr.length;
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int[] subarray = new int[len];
                System.arraycopy(arr, i, subarray, 0, len);
                uniqueSubarrays.add(arrayToString(subarray));
            }
        }
    }

    public static String arrayToString(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int num : arr) {
            builder.append(num);
        }
        return builder.toString();
    }
}
