package Pingdd;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author hzy
 * @date 2023-03-12
 */
public class Main1 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            // 注意 hasNext 和 hasNextLine 的区别
            String []line1=in.nextLine().split("\\.");
            String []ip1=in.nextLine().split("\\.");
            String[] ip2=in.nextLine().split("\\.");
            int[]num1=new int[4];
            int[]num2=new int[4];
            boolean flag=false;
            boolean illgea=false;
            for(int i=0;i<4;i++)
            {
                int te1=(Integer.parseInt(ip1[i])&Integer.parseInt(line1[i]));
                int te22=(Integer.parseInt(ip2[i])&Integer.parseInt(line1[i]));
                if(Integer.parseInt(ip1[i])<0||Integer.parseInt(ip1[i])>255||Integer.parseInt(ip2[i])<0||Integer.parseInt(ip2[i])>255|| Integer.parseInt(line1[i])<0||Integer.parseInt(line1[i])>255)
                {
                    illgea=true;
                    break;
                }

                if(te1!=te22) {flag=true;
                    break;}
            }
            if(illgea)System.out.println(1);
            else{
                if(flag)
                    System.out.println(2);
                else System.out.println(0);
            }
        }
    }
    public static boolean judge(String[]line)
    {
        String binary="";
        for (int i = 0; i < 4; i++) {
            binary+=Integer.toBinaryString(Integer.parseInt(line[i]));

        }
        boolean change=true;
        int i=0;
        while ( i < binary.length()) {
            if(binary.charAt(i)=='1'&&!change) return false;
            if(binary.charAt(i)=='1') i++;
            else {
                change=false;
            }

        }
        return true;
    }
}
