package Pingdd;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author hzy
 * @date 2023-03-12
 */
public class Main1 {

    public static void main(String[] args) {

String a="abc";
String b="ab"+"c";
        System.out.println(a==b);
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
