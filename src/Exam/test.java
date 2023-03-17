package Exam;

import java.util.Scanner;

/**
 * @author hzy
 * @date 2023-03-07
 */
public class test {
    public static void main(String[] args) {
Scanner in=new Scanner(System.in);
int v0=in.nextInt();
int x=in.nextInt();
int y=in.nextInt();
double t=((double)Math.sqrt(v0*y)-v0)/x;
double ans=t+(double)y/(v0+t*x);
double left=0,right=(y-v0)/x;
while(left<right)
{
double mid=(left+right)/2;
if(mid+y/(mid*x+v0)<x){
    left=mid+0.000001;
}
else{
    ans=mid;
    right=mid-0.000001;
}
}
        System.out.println(ans);
    }
    public static int maxLen(int[]arr)
    {
        int left=0,right=1;
        int len=arr.length;
        int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
        int ans=0;
        int cur=0;
        while (left <len) {
            int diff=Math.abs(arr[left]-arr[right]);
            if(diff<=1){
                right++;
                left++;
                ans=Math.max(ans,right-cur);
            }
            else{
               right++;
               cur=right;
               left=right;
            }

        }
        return ans;
    }
}
