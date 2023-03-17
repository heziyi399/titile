import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.lang.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.spi.CalendarDataProvider;

/**
 * @author hzy
 * @date 2022-09-14
 */


public class Shuihu {

    public static void main(String[] args) {
        Deque<Integer> queue = new ArrayDeque<Integer>();
        queue.offerLast(1);
        String s;
        queue.offerLast(2);
        System.out.println(queue.pollLast());
        System.out.println(33>>2);

        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(5);
        int[][]intervals=new int[2][2];
        StringBuilder sb=new StringBuilder();

        Shuihu hu = new Shuihu();

        //a:x坐标减少 s:y坐标减少 w:y坐标增加 d:x坐标增加
        Scanner in = new Scanner(System.in);

        // 注意 hasNext 和 hasNextLine 的区别

     //   System.out.println(Arrays.toString(str));
  int m=(10<<24)+(3<<8)+(193<<0);
        System.out.println((192&255));




        int[]memo=new int[366];
        Arrays.fill(memo,-1);
        Map<Integer,List<Integer>>map=new HashMap<>();


        int[]should = new int[]{1,10,-1,-2,10,3,4};
        int l=0,r=should.length;

        int max = Arrays.stream(should).max().getAsInt();
        int x=0xAA;
        System.out.println(x);
        int[] arry = {10,9,8,7,6,5,11,3,2,1};
        quickSort(arry,0, arry.length-1);

Character.isLowerCase('a');
        //a:x坐标减少 s:y坐标减少 w:y坐标增加 d:x坐标增加
hu.findLongestSubarray(new String[]{"A","1"});


    }
    int count=0;

    public String[] findLongestSubarray(String[] array) {
        Map<Integer, Integer>index=new HashMap<Integer, Integer>();
        int sum=0;
        int len=0;
        int left=0,right=0;
        index.put(0,0);
        for (int i = 0; i < array.length; i++) {
             sum+= Character.isDigit(array[i].charAt(0))?1:-1;
            if(index.size()==0||!index.containsKey(sum))
                index.put(sum,i);
            else if(index.containsKey(sum))
            {
                if(i-index.get(sum)+1>len){
                    left=index.get(sum);
                    right=i;
                    len=Math.max(len,i-index.get(sum)+1);
                }

            }
        }
        return Arrays.copyOfRange(array,left,right);
    }
    boolean[]used;
    List<List<Integer>>ans=new ArrayList<>();

    private void dfs(int[] candidates, int target, List<Integer> list,int curSum) {
    if(curSum==target){
        ans.add(new ArrayList<>(list));
        return;
    }

        for (int i = 0; i < candidates.length; i++) {
            if(used[i]) continue;
            if(i>0&&candidates[i]==candidates[i-1]&&!used[i-1]) continue;
            used[i]=true;
            list.add(candidates[i]);
            dfs(candidates,target,list,curSum+candidates[i]);
            used[i]=false;
            list.remove(list.size()-1);
        }
    }

    class Trie {
        Trie[]children;
        boolean isEnd;
        int num=0;
        public Trie() {
            children=new Trie[26];

        }

        public boolean search(String word)
        {
            Trie trie=this;
            for(int i=0;i<word.length();i++)
            {
                char ch=word.charAt(i);
                if(trie.children[word.charAt(i)-'a']==null) return false;
                trie=trie.children[ch-'a'];
            }
            return trie.isEnd;
        }
        public void insert(String word) {
            Trie node=this;
            boolean branch=false;
            for(int i = 0;i<word.length();i++){
                char ch=word.charAt(i);
                node.num++;
                if(node.children[ch-'a']==null){
                    node.children[ch-'a']=new Trie();

                    branch=true;
                }
                node=node.children[ch-'a'];
                if(node.isEnd&&i!=word.length()-1)
                    node.isEnd=false;
            }
        node.isEnd=true;
        }
    }

    public static void quickSort(int[] arry,int left,int right){
        //运行判断，如果左边索引大于右边是不合法的，直接return结束次方法
        if(left>right){
            return;
        }
        //定义变量保存基准数
        int base = arry[left];
        //定义变量i，指向最左边
        int i = left;
        //定义j ,指向最右边
        int j = right;
        //当i和j不相遇的时候，再循环中进行检索
        while(i!=j){
            //先由j从右往左检索比基准数小的，如果检索到比基准数小的就停下。
            //如果检索到比基准数大的或者相等的就停下
            while(arry[j]>=base && i<j){
                j--; //j从右往左检索

            }
            while(arry[i]<=base && i<j){
                i++; //i从左往右检索
            }
            //代码走到这里i停下，j也停下，然后交换i和j位置的元素
            int tem = arry[i];
            arry[i] = arry[j];
            arry[j] = tem;


        }
        //如果上面while条件不成立就会跳出这个循环，往下执行
        //如果这个条件不成立就说明 i和j相遇了
        //如果i和j相遇了，就交换基准数这个元素和相遇位置的元素
        //把相遇元素的值赋给基准数这个位置的元素
        arry[left] = arry[i];
        //把基准数赋给相遇位置的元素
        arry[i] = base;
        //基准数在这里递归就为了左边的数比它小，右边的数比它大
        //排序基准数的左边
        quickSort(arry,left,i-1);
        //排右边
        quickSort(arry,j+1,right);

    }


}
