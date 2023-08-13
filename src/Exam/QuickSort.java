package Exam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author hzy
 * @date 2023-03-29
 */
public class QuickSort {
    private static int x=100;
    public static int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int rk = -1;
        HashSet<Character> hash = new HashSet<Character>(); //保存字符
        List<String>list=new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            //每一次循环，都从下一位开始
            if (i != 0) {
                hash.remove(s.charAt(i - 1));//每遍历一次，都将起始位置后移，此时先前保存的元素必定丢弃
            }

            while (rk + 1 < s.length() && !hash.contains(s.charAt(rk + 1))) {//下移的元素在集合中不存在
                hash.add(s.charAt(rk + 1));
                rk++;
            }
            if(rk-i+1>ans){
                list.clear();
                ans = Math.max(ans, rk - i + 1);//比较改变初始位置后的哪个不重复数据最多
                list.add(s.substring(i,rk+1));
            }else if(rk-i+1==ans){
                list.add(s.substring(i,rk+1));
            }



        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        return ans;


    }

    public static void main(String[] args) {
        lengthOfLongestSubstring("abcdabcde");
    }
    public static void sort(int[]nums){

    }
    public static void dos(Integer i)
    {
        i=new Integer(2);
    }
    public static int sort(int[]nums,int left,int right){
        int temp=nums[left];
        while(left<right) {
            while (left < right && nums[right] > temp) {
                right--;
            }
            if(left<right){
                nums[left++]=nums[right];
            }
            while (left < right && nums[left] <= temp) {
                left++;
            }
            if(left<right){
                nums[right--]=nums[left];
            }
        }
        nums[left]=temp;
        return left;
    }
}
