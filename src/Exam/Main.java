package Exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ForkJoinPool;

/**
 * @author hzy
 * @date 2023-03-07
 */
public class Main {
   static int max;
   static boolean[][]vis;
    public static void main(String[] args) {
        //1 7 2
        //BBRBRBR
        //0 3 2 4 1 1 1
        Scanner in=new Scanner(System.in);
     //   String l1=in.nextLine();
     //  int row=Integer.valueOf(l1.split(" ")[0]),col=Integer.valueOf(l1.split(" ")[1]),pay=Integer.valueOf(l1.split(" ")[2]);
      int row=1,col=7,pay=2;
       int[][]dp=new int[row+1][col+1];
       char[][]color=new char[row][col];
       vis=new boolean[row][col];
        for (int i = 0; i < row; i++) {
            String temp="BBRBRBR";
            for (int j = 0; j < temp.length(); j++) {
                color[i][j]=temp.charAt(j);
            }
        }
        int[][]number=new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                number[i][j]=in.nextInt();
            }
        }

       dfs(number,pay,color,color[0][0], 0, 0,number[0][0]);

        System.out.println(max);
    }
    public  static void dfs(int[][]number,int pay,char[][]ch,char color,int i,int j,int sum)
    {
        int[][]dirs=new int[][]{{0,1},{1,0}};
        for (int k = 0; k < 2; k++) {
         int newr=i+dirs[k][0],newc=j+dirs[k][1];
         if(newr>=0&&newc>=0&&newr<number.length&&newc<number[0].length&&!vis[newr][newc]){
             vis[i][j]=true;
             if(ch[newr][newc]!=color) {
                 sum-=pay;
                 max=Math.max(max,sum+number[newr][newc]);
                 dfs(number,pay,ch,ch[newr][newc],newr,newc,sum+number[newr][newc]);
             }else{
                 max=Math.max(max,sum+number[newr][newc]);
                 dfs(number,pay,ch,ch[newr][newc],newr,newc,sum+number[newr][newc]);
             }
         }
        }
    }


}
