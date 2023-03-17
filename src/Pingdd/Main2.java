package Pingdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author hzy
 * @date 2023-03-12
 */
public class Main2 {
  static  int[]top=new int[3];//人数上限
  static  int[]pay=new int[3];
  static List<String> users =new ArrayList<String>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int total=Integer.valueOf(scanner.nextLine());//参加活动总人数

        for (int i = 0; i < total; i++) {
            users.add(scanner.nextLine());
        }

        for (int i = 0; i < 3; i++) {
            String line=scanner.nextLine();
            top[i]= Integer.parseInt(line.split(" ")[0]);
            pay[i]=Integer.parseInt(line.split(" ")[1]);


        }
        int ans=calculate();
        if(ans!=-1){
            System.out.println("YES");
            System.out.println(ans);
        }
        else{
            int a=0,b=0,c=0;
            for (int i = 0; i < users.size(); i++) {
                if(users.get(i).contains("A"))a++;
                if(users.get(i).contains("B"))b++;
                if(users.get(i).contains("C"))c++;
            }
            int sum=0;
          sum+=Math.min(top[0],a);
            sum+=Math.min(top[1],b);
            sum+=Math.min(top[2],c);

        }
    }
    public static  int calculate(){
        init();
        for (int a=top[0];a>=0;--a) {
            for(int b=top[1];b>=0;--b)
            {
                int c= users.size()-a-b;
                if(c>=0&&c<=top[2]&&dfs(a,b,c,0)){
                    return pay[0]*a+pay[1]*b+pay[2]*c;
                }
            }
        }
        return -1;
    }
    public static void init(){
        if(pay[0]<=pay[1]&&pay[1]<=pay[2]) {
            users.sort((o1, o2) -> o1.length() - o2.length());
        return;
        }
        else if(pay[0]>pay[1]){
            int temp=top[0];
            top[0]=top[1];
            top[1]=temp;
           temp=pay[0];
           pay[0]=pay[1];
           pay[1]=temp;
            for (int i = 0; i < users.size(); i++) {
                users.set(i, users.get(i).replaceAll("A","T"));
                users.set(i, users.get(i).replaceAll("B","A"));
                users.set(i, users.get(i).replaceAll("T","B"));
            }

        }else  if(pay[1]>pay[2]){
            int temp=top[1];
            top[1]=top[2];
            top[2]=temp;
            temp=pay[1];
            pay[1]=pay[2];
            pay[2]=temp;
            for (int i = 0; i < users.size(); i++) {
                users.set(i, users.get(i).replaceAll("C","T"));
                users.set(i, users.get(i).replaceAll("B","C"));
                users.set(i, users.get(i).replaceAll("T","B"));
            }
        }
        init();
    }
    public static boolean dfs(int leftA,int leftB,int leftC,int indexUser)
    {
        if(indexUser>= users.size()){
            return true;
        }
        String options= users.get(indexUser);
        if(options.contains("A")&&leftA>0){
            leftA--;
            boolean r=dfs(leftA,leftB,leftC,indexUser+1);
            if(r) return true;
            leftA++;
        }
        if(options.contains("B")&&leftB>0){
            leftB--;
            boolean r=dfs(leftA,leftB,leftC,indexUser+1);
            if(r) return true;
            leftB++;
        }
        if(options.contains("C")&&leftC>0){
            leftC--;
            boolean r=dfs(leftA,leftB,leftC,indexUser+1);
            if(r) return true;
            leftC++;
        }
        return false;
    }
}
