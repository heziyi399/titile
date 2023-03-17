package Exam;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author hzy
 * @date 2023-03-11
 */
public class T2 {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        String line=in.nextLine();
        char[]ch=line.toCharArray();
        int[]change=new int[ch.length];
        for (int i = 0; i < ch.length; i++) {
            change[i]=ch[i]-'0';
        }
        int[][]dp=new int[line.length()][10];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j]=Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < 10; i++) {
            dp[0][i]=1;
        }
        dp[0][change[0]]=0;
        int min=Integer.MAX_VALUE;
        for (int i = 1; i < line.length(); i++) {
            for (int j = 0; j < 10; j++) {

                for (int k = 0; k < 10; k++) {
                 if(j==k) continue;
                 if(j==change[i])
                     dp[i][j]=Math.min(dp[i-1][k],dp[i][j]);
                 else if(j!=change[i])
                     dp[i][j]=Math.min(dp[i-1][k]+1,dp[i][j]);
                }
            if(i==ch.length-1) min=Math.min(min,dp[i][j]);
            }

        }
        System.out.println(min);
    }
}
