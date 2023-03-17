import java.util.*;

/**
 * @author hzy
 * @date 2023-02-20
 */
public class Title {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别


       String s="fdsjs**dsfs";
       s=s.replaceAll("\\*{2,}","\\*");
        System.out.println(match("a*?*c",
                "a@c",0,0));

        }
    public static boolean match(String s,String p,int i,int j)
    {
        if(i==s.length()&&j==p.length()) return true;
        if(i>=s.length()||j>=p.length()) return false;
        if(s.charAt(i)==p.charAt(j)||p.charAt(j)=='?') return match(s,p,i+1,j+1);
        else if(p.charAt(j)=='*')
            return match(s,p,i,j+1)||match(s,p,i+1,j)||match(s,p,i+1,j+1);
        else return false;
    }
    public static int getBeauty(String word)
    {
        Map<Character,Integer>chCount=new HashMap<>();
        for(int i=0;i<word.length();i++)
            chCount.put(word.charAt(i),chCount.getOrDefault(word.charAt(i),0)+1);
        List<Map.Entry<Character,Integer>>list=new ArrayList<>(chCount.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<Character,Integer>>(){

            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });
        int sum=0;
        int max=26;
        for(Map.Entry<Character,Integer>entry:list)
        {
            sum+=max*entry.getValue();
            max--;
        }
        return sum;
    }
}
