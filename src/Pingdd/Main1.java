package Pingdd;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            binary += Integer.toBinaryString(Integer.parseInt(line[i]));

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

    public static List<List<Integer>> subArrayList(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if (nums == null || nums.length < 1) {
            return list;
        }
        // 递归决策
        process(nums, 0, new ArrayList<>(), list);
        return list;
    }


    public static void process(int[] nums, int index, List<Integer> temp, List<List<Integer>> resultList) {
        // 决策完数组最后一个位置后，将结果记录到resultList
        if (nums.length == index) {
            // 注意：Java中list是引用类型，所以这里需要将决策完的结果【拷贝一份】然后放入结果集
            List<Integer> copy = new ArrayList<>(temp);
            resultList.add(copy);
            return;
        }

        // 对于每个位置都有两种选择，要或者不要，定了index位置后，然后去决策index + 1 位置
        // 1、要当前位置的数
        temp.add(nums[index]);
        process(nums, index + 1, temp, resultList);

        // 2、不要当前位置的数（这里注意要把上面add的数remove掉，注意这里是按照数组下标remove的）
        // 注意这里始终是remove的最后一个元素
        temp.remove(temp.size() - 1);
        process(nums, index + 1, temp, resultList);
    }

}
