package ClassTest;

import java.lang.reflect.Field;

/**
 * @author hzy
 * @date 2023-01-26
 */
public class ClassSon1 extends ClassFather {

int a=1;



    public  static void method(){
        System.out.println("my son"+1111111);
    }
final  int bb;
{
      bb = 10;
  }
  public void normalMethod(){
      System.out.println("normal method");
  }
    String str="abc";
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println(String.class.getClassLoader());
        //System.out.println(System.identityHashCode(a));
        Field value=String.class.getDeclaredField("value");
        value.setAccessible(true);
       // char[] str=(char[])value.get(a);
      //  str[1]='2';
      //  System.out.println(a);
       // System.out.println(System.identityHashCode(a));
        String s3 = new String("1") + new String("1");
        String s4 = "11";
      //  s3.intern();
        System.out.println(s3 == s4);
ClassSon1 son1=new ClassSon1();
son1.normalMethod();

    }
}
