package Exam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Stack;

/**
 * @author hzy
 * @date 2023-04-27
 */
public class StackToQueue {
    Stack<Integer> stack = new Stack<Integer>();
    Stack<Integer>temp=new Stack<>();
    public void push(int num){
        stack.push(num);
    }
    public int get(){
        while(stack.size()>1){
            temp.push(stack.pop());
        }
        int ans=stack.pop();
        while(temp.size()>0){
            stack.push(temp.pop());
        }
        return ans;
    }
}
