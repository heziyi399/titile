package Exam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author hzy
 * @date 2023-03-11
 */
public class N {
    static class TimeRange{
        public int from;
        public int end;
        public int number;
        public TimeRange(int from, int end,int number){
            this.from=from;
            this.end=end;
            this.number=number;
        }
    }
    public static List<TimeRange>  merge(TimeRange r1, TimeRange r2){
        if(r1.from>r2.from||(r1.from==r2.from&&r1.end>r2.end)){
            return merge(r2,r1);
        }
        List<TimeRange>res=new ArrayList<>();
        if(r2.from<=r1.end){
            if(r1.from<=r2.from-1){
                res.add(new TimeRange(r1.from,r2.from-1,r1.number));
            }
            res.add(new TimeRange(r2.from,r1.end,r2.number+r1.number));
            if(r1.end<r2.end){
                res.add(new TimeRange(r1.end+1,r2.end,r2.number));
            }else{
                res.add(new TimeRange(r2.end,r1.end,r1.number));
            }
        }else{
            res.add(r1);
            res.add(r2);
        }
        return res;
    }
    public static  void merge(LinkedList<TimeRange>result,TimeRange t2){
        if(result.isEmpty()){
            result.add(t2);
            return;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    }
}

