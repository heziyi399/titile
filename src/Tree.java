import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * @author hzy
 * @date 2023-02-17
 */
public class Tree {
    List<Integer>list=new ArrayList<>();
    public boolean findTarget(TreeNode root, int k) {

        Map<Integer,Integer>map=new HashMap<>();
        for(int i:list)
        {
            System.out.println(i);
            map.put(i,null);
        }


        for(int i:list)
        {
            if(map.containsKey(k-i)) return true;
        }
        return false;
    }
    public void dfs(TreeNode root)
    {
        if(root==null) return;
        dfs(root.left);
        list.add(root.val);
        dfs(root.right);
    }

    public static void main(String[] args) {
        TreeNode root=new TreeNode(2);
        root.left=new TreeNode(3);
        root.right=new TreeNode(5);
        root.left.left=new TreeNode(1);
        root.left.right=new TreeNode(2);
        root.right.left=new TreeNode(3);
        root.right.right=new TreeNode(4);
        Tree t=new Tree();
        t.findTarget(root,5);

    }
}
 class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
