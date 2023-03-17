package Exam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * @author hzy
 * @date 2023-03-11
 */
public class T3 {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int[]show=new int[n];
        int[]end=new int[n];
        int minTime=Integer.MAX_VALUE;
        int maxTime=Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            show[i]=in.nextInt();
            minTime=Math.min(minTime,show[i]);
        }
        for (int i = 0; i < n; i++) {
            end[i]=in.nextInt();
            maxTime=Math.max(maxTime,end[i]);
        }
        int[][]arr=new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0]=show[i];
            arr[i][1]=end[i];

        }
        TreeMap<Integer, List<Integer>>map=new TreeMap<Integer, List<Integer>>(Comparator.reverseOrder());
        for (int i =minTime; i <= maxTime; i++) {
            int total=0;//可以观测的流星数量
            for (int j = 0; j < arr.length; j++) {
                if(i>=arr[j][0]&&i<=arr[j][1])
                    total++;
            }
            List<Integer>list=map.getOrDefault(total,new ArrayList<Integer>());
            list.add(i);
            map.put(total,list);
        }
        for (Map.Entry<Integer, List<Integer>> integerListEntry : map.entrySet()) {
            System.out.println(integerListEntry.getKey()+" "+integerListEntry.getValue().size());
            break;
        }
    }
}
