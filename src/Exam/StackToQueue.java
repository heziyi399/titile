package Exam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * @author hzy
 * @date 2023-04-27
 */
public class StackToQueue {
    public static void main(String[] args) {

   List<String>list= Arrays.asList("A","B","C");
   String[]arr=list.toArray(new String[0]);
   arr[0]="1";
        System.out.println(list.get(0));
    }

    //前序遍历的方法
    public static void preOrder(Node root){

        if (root != null){
            root.prefixOrder();
        }else {
            System.out.println("树为空");
        }
    }

    public static Node createHuffmanTree(int[] arr){
        // 第一步为了操作方便
        // 1. 遍历 arr 数组
        // 2. 将 arr 的每个元素构成成一个 Node
        // 3. 将 Node 放入到 ArrayList 中
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            nodeList.add(new Node(arr[i]));
        }
        //一、从小到大排序
        Collections.sort(nodeList);
        //测试是否排序成功
        System.out.println("nodeList = " + nodeList);
        while (nodeList.size() > 1) {
            //二、取出权值最小的两颗二叉树节点
            Node left = nodeList.get(0);
            Node right = nodeList.get(1);
            //三、构建一颗新的二叉树
            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;
            //四、从集合中删除已经处理过的节点,并将新的二叉树加进去
            nodeList.remove(left);
            nodeList.remove(right);
            nodeList.add(parent);
            //五、重新排序
            Collections.sort(nodeList);
            //测试是否排序成功
            //System.out.println("nodeList = " + nodeList);
        }
        //返回哈夫曼树的根节点
        return nodeList.get(0);
    }

}
 class Node implements Comparable<Node>{

    public Integer value;
    public Node left;
    public Node right;

    //前序遍历
    public void prefixOrder(){
        //根
        System.out.println(this);
        //左
        if (this.left != null){
            this.left.prefixOrder();
        }
        //右
        if (this.right != null){
            this.right.prefixOrder();
        }
    }

    public Node(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大排序
        return this.value - o.value;
    }

}
