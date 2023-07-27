

jvm学习



# 基础概念

编译期：是指把源码交给编译器编译成计算机可以执行的文件的过程。在Java中也就是把Java代码编成class文件的过程.编译期只是做了一些翻译功能，并没有把代码放在内存中运行起来，而只是把代码当成文本进行操作,比如检查错误。

运行期：是把编译后的文件交给计算机执行，直到程序运行结束。所谓运行期就把在磁盘中的代码放到内存中执行起来，在Java中把磁盘中的代码放到内存中就是类加载过程，类加载是运行期的开始部分。 

编译期分配内存并不是说在编译期就把程序所需要的空间在内存中分配好，**而是说在编译期生成的代码中产生一些指令，在运行代码时通过这些指令把程序所需的内存分配好**。只不过在编译期的时候就知道分配的大小，并且知道这些内存的位置。而运行期分配内存是指只有在运行期才确定内存的大小、存放的位置。





_Java中的绑定：绑定指的是把一个方法的调用与方法所在的类(方法主体)关联起来(这个方法被哪个类调用)。对Java来说，分为静态绑定和动态绑定。

(1) 静态调用：在程序执行前方法已经被绑定，也就是在编译期方法明确知道被哪个类调用。java当中的方法只有final，static，private和构造方法是前期绑定的。 

(2) 动态调用：在运行时根据具体对象的类型进行绑定(只有运行时才知道方法被哪个类调用)。在java中，几乎所有的方法都是后期绑定的

在编译期，将java代码翻译为字节码文件的过程经过了四个步骤，词法分析，语法分析，语义分析，代码生成四个步骤。

![img](https://img2018.cnblogs.com/blog/660329/201909/660329-20190904112945751-751952762.jpg)

# jvm的架构模型

jvm基本上是一种基于栈的指令集架构

![image-20230131223229348](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230131223229348.png)

class文件通过类加载子系统处理之后加载到jvm的运行时数据区（即方法区 堆 栈 本地方法栈 程序计数器），类加载子系统中就是执行加载连接（有验证准备解析）初始化几个步骤

# 执行引擎

![image-20230206164103131](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206164103131.png)

执行引擎在执行过程中究竟需要执行什么样的字节码完全依赖于pc寄存器，每当执行完一项指令操作后，pc寄存器就会更新下一条需要被执行的指令地址，当然方法在执行过程中，执行引擎有可能通过存储在局部变量表中的对象引用准确定位到存储在java堆区中的对象实例信息，以及通过对象头中的元数据指针定位到目标对象的类型信息

# 解释器和编译器

解释器：java虚拟机启动的时候会根据预定义的规范对字节码采用逐行解释的方式执行，将每条字节码文件中的内容“翻译”为对应平台的本地机器指令执行

编译器：虚拟机将源代码直接编译成和本地机器平台相关的机器语言

java是半编译半解释型语言，现在jvm在执行java代码的时候，通常都会将解释执行与编译执行二者结合起来进行

**javac**就是前端编译器，可以将java文件编译成字节码组成的class文件。

执行**javac Info.java**生成Info.class文件,再使用**javap -c Info.class来**查看其中的字节码。

解释器是一行一行地将字节码解析成机器码，解释到哪就执行到哪，狭义地说，就是for循环100次，你就要将循环体中的代码逐行解释执行100次。当程序需要迅速启动和执行时，解释器可以首先发挥作用，省去编译的时间，立即执行。

即时编译器按照我的理解就是：以方法为单位，将热点代码的字节码一次性转为机器码，并在本地缓存起来的工具。避免了部分代码被解释器逐行解释执行的效率问题。

# 静态语言和动态语言

![image-20230203205704749](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203205704749.png)

像python这种只有执行完这局info=130.5才知道这个是Double类型的，就是动态语言（根据等号右边来决定类型就是动态的）



# Java字节码

计算机只认识0和1。这意味着任何语言编写的程序最终都需要经过编译器编译成机器码才能被计算机执行。所以，我们所编写的程序在不同的平台上运行前都要经过重新编译才能被执行。 而Java刚诞生的时候曾经提过一个非常著名的宣传口号: "一次编写，到处运行"。为了实现该目的，Sun公司以及其他虚拟机提供商发布了许多可以运行在不同平台上的JVM虚拟机，而这些虚拟机都拥有一个共同的功能，那就是可以载入和执行同一种**与平台无关的字节码(ByteCode)**。 于是，我们的源代码不再必须根据不同平台翻译成0和1，而是间接翻译成字节码，储存字节码的文件再交由运行于不同平台上的JVM虚拟机去读取执行，从而实现一次编写，到处运行的目的。 如今，JVM也不再只支持Java，由此衍生出了许多基于JVM的编程语言，如Groovy, Scala, Koltin等等。



源代码中的各种变量，关键字和运算符号的语义最终都会编译成多条字节码命令。而字节码命令所能提供的语义描述能力是要明显强于Java本身的，所以有其他一些同样基于JVM的语言能提供许多Java所不支持的语言特性。

![image-20210903161355174](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210903161355174.png)

下面以一个简单的例子来逐步讲解字节码。

public class Main {
private int m;
public int inc() {
return m + 1;
}
}
 通过以下命令, 可以在当前所在路径下生成一个Main.class 文件。以文本的形式打开生成的class文件，内容如下:


![image-20210903232538561](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210903232538561.png)

文件中的为16进制代码， 文件开头的4个字节称之为 魔数，唯有以"cafe babe"开头的class文件方可被虚拟机所接受，这4个字节就是字节码文件的身份识别。0000是编译器jdk版本的次版本号0，0034转化为十进制是52,是主版本号，java的版本号从45开始，除1.0和1.1都是使用45.x外,以后每升一个大版本，版本号加一。也就是说，编译生成该class文件的jdk版本为1.8.0。 通过java -version 命令稍加验证, 可得结果。

反编译字节码文件
使用到java内置的一个反编译工具javap 可以反编译字节码文件。 通过javap -help 可了解javap的基本用法

![image-20210903232652892](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210903232652892.png)

输入命令javap -verbose -p Main.class 查看输出内容:

```
E:\20180807_learn>javap -v -p Main.class
Classfile /E:/20180807_learn/Main.class
  Last modified 2018-8-7; size 265 bytes
  MD5 checksum db7ad8ef61aa899dd0a8b9516155244e
  Compiled from "Main.java"
public class Main
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#15         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#16         // Main.m:I
   #3 = Class              #17            // Main
   #4 = Class              #18            // java/lang/Object
   #5 = Utf8               m
   #6 = Utf8               I
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               inc
  #12 = Utf8               ()I
  #13 = Utf8               SourceFile
  #14 = Utf8               Main.java
  #15 = NameAndType        #7:#8          // "<init>":()V
  #16 = NameAndType        #5:#6          // m:I
  #17 = Utf8               Main
  #18 = Utf8               java/lang/Object
{
  private int m;
    descriptor: I
    flags: ACC_PRIVATE

  public Main();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>
":()V
         4: return
      LineNumberTable:
        line 1: 0

  public int inc();
    descriptor: ()I
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: getfield      #2                  // Field m:I
         4: iconst_1
         5: iadd
         6: ireturn
    LineNumberTable:
        line 4: 0
}
SourceFile: "Main.java"
```

字节码文件信息

开头的7行信息包括:Class文件当前所在位置，最后修改时间，文件大小，MD5值，编译自哪个文件，类的全限定名，jdk次版本号，主版本号。 然后紧接着的是该类的访问标志：ACC_PUBLIC, ACC_SUPER，访问标志的含义如下:

![image-20210903232842094](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210903232842094.png)

class文件的内容：1.魔数 2.版本号（不同jdk版本对应的版本号也不一样）

3.常量池 整体分为常量池计数器和常量池数据区

![image-20220303160415030](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220303160415030.png)

4.访问标志

常量池结束后的两个字节，描述的是类还是接口，以及是否被public final等修饰符修饰

5.当前类索引 

访问标志后的两个字节，描述的是当前类的全限定名，这两个字节保存的值是常量池中索引值，根据索引值能够在常量池中找到这个类的全限定名

6.父类索引

7.接口索引

8.字段表

![image-20220303163446832](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220303163446832.png)

9.方法表

字段表结束后为方法表，方法表也分两部分 第一部分是方法个数，第二部分是每个方法的详细信息

![image-20220303163729397](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220303163729397.png)

10.附加属性

字节码的最后一部分，存放了该文件中类或接口所定义属性的基本信息

## 常量池

Jdk1.6及之前：有永久代, 常量池在方法区
Jdk1.7：有永久代，但已经逐步“去永久代”，常量池在堆
Jdk1.8及之后： 无永久代，运行时常量池在元空间

常量池中主要存放两大类常量：字面量（Literal）和符号引用（Symbolic References）。字面量比较接近于Java语言层面的常量概念，如文本字符串、被声明为**final的常量值**等。而**符号引用则属于编译原理方面的概念**，主要包括下面几类常量： 

·被模块导出或者开放的包（Package）

·类和接口的全限定名（Fully Qualified Name） 

·字段的名称和描述符（Descriptor） 

·方法的名称和描述符 

·方法句柄和方法类型（Method Handle、Method Type、Invoke Dynamic） 

·动态调用点和动态常量（Dynamically-Computed Call Site、Dynamically-Computed Constant）

 Java代码在进行Javac编译的时候，并不像C和C++那样有“连接”这一步骤，而是在虚拟机加载Class文件的时候进行动态连接（具体见第7章）。也就是说，在Class文件中不会保存各个方法、字段最终在内存中的布局信息，这些字段、方法的符号引用不经过虚拟机在运行期转换的话是无法得到真正的内存入口地址，也就无法直接被虚拟机使用的。**当虚拟机做类加载时，将会从常量池获得对应的符号引用**，再在类创建时或运行时解析、翻译到具体的内存地址中。
1）、类和接口的全限定名(Fully Qualified Name)
2）、字段的名称和描述符号(Descriptor)
3）、方法的名称和描述符
不同于C/C++, JVM是在加载Class文件的时候才进行的动态链接，也就是说这些字段和方法符号引用只有**在运行期转换后才能获得真正的内存入口地址**。当虚拟机运行时，==需要从常量池获得对应的符号引用，再在类创建或运行时解析并翻译到具体的内存地址中==。
直接通过反编译文件来查看字节码内容：
#1 = Methodref          #4.#15         // java/lang/Object."\<init>":()V
#4 = Class              #18            // java/lang/Object
#7 = Utf8               \<init>
#8 = Utf8               ()V
#15 = NameAndType        #7:#8          // "\<init>":()V
#18 = Utf8               java/lang/Object

第一个常量是一个方法定义，指向了第4和第15个常量。以此类推查看第4和第15个常量。最后可以拼接成第一个常量右侧的注释内容:

java/lang/Object."\<init>":()V

这段可以理解为该类的实例构造器的声明，由于Main类没有重写构造方法，所以调用的是父类的构造方法。此处也说明了Main类的直接父类是Object。 该方法默认返回值是V, 也就是void，无返回值。

同理可分析第二个常量:

#2 = Fieldref           #3.#16         // Main.m:I
#3 = Class              #17            // Main
#5 = Utf8               m
#6 = Utf8               I
#16 = NameAndType        #5:#6          // m:I
#17 = Utf8               Main

此处声明了一个字段m，类型为I, I即是int类型。关于字节码的类型对应如下：


通过以上一个最简单的例子，可以大致了解源码被编译成字节码后是什么样子的。 下面利用所学的知识点来分析一些Java问题:

public class TestCode {
public int foo() {
int x;
try {
x = 1;
return x;
} catch (Exception e) {
x = 2;
return x;
} finally {
x = 3;
}
}
}
E:\20180807_learn>javap -v -p TestCode.class
Classfile /E:/20180807_learn/TestCode.class
  Last modified 2018-8-7; size 417 bytes
  MD5 checksum 388303755886516af0472f05542c886a
  Compiled from "TestCode.java"
public class TestCode
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#16         // java/lang/Object."<init>":()V
   #2 = Class              #17            // java/lang/Exception
   #3 = Class              #18            // TestCode
   #4 = Class              #19            // java/lang/Object
   #5 = Utf8               \<init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Utf8               LineNumberTable
   #9 = Utf8               foo
  #10 = Utf8               ()I
  #11 = Utf8               StackMapTable
  #12 = Class              #17            // java/lang/Exception
  #13 = Class              #20            // java/lang/Throwable
  #14 = Utf8               SourceFile
  #15 = Utf8               TestCode.java
  #16 = NameAndType        #5:#6          // "\<init>":()V
  #17 = Utf8               java/lang/Exception
  #18 = Utf8               TestCode
  #19 = Utf8               java/lang/Object
  #20 = Utf8               java/lang/Throwable
{
  public TestCode();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>
":()V
         4: return
      LineNumberTable:
        line 1: 0

  public int foo();
    descriptor: ()I
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=5, args_size=1
         0: iconst_1
         1: istore_1
         2: iload_1
         3: istore_2
         4: iconst_3
         5: istore_1
         6: iload_2
         7: ireturn
         8: astore_2
         9: iconst_2
        10: istore_1
        11: iload_1
        12: istore_3
        13: iconst_3
        14: istore_1
        15: iload_3
        16: ireturn
        17: astore        4
        19: iconst_3
        20: istore_1
        21: aload         4
        23: athrow
      Exception table:
         from    to  target type
             0     4     8   Class java/lang/Exception
             0     4    17   any
             8    13    17   any
            17    19    17   any
      LineNumberTable:
        line 5: 0
        line 6: 2
        line 11: 4
        line 6: 6
        line 7: 8
        line 8: 9
        line 9: 11
        line 11: 13
        line 9: 15
        line 11: 17
      StackMapTable: number_of_entries = 2
        frame_type = 72 /* same_locals_1_stack_item */
          stack = [ class java/lang/Exception ]
        frame_type = 72 /* same_locals_1_stack_item */
          stack = [ class java/lang/Throwable ]
}
SourceFile: "TestCode.java"

## 解释执行和编译执行

。我们有一个文件x.java->执行javac->变成x.class，当我们调用Java命令的时候class会被装载到内存叫classLoader。一般的情况下我们写自己类文件的时候也会用到java的类库，所以它要把java类库相
关类也装载到内存里，装载完成之后会调用字节码解释器或者是即时编译器来进行解释或编译，编译完之后由执行引擎开始执行,执行引擎下面面对就是操作系统的硬件了。这块的内容叫jvm。下图是大体的流程，大家要存到脑子里。**java编译好了之后变成class，class会被加载到内存**与此同时像String、Object这些class也会加载到内存。

：java是解释执行的还是编译执行啊，其实解释和编译是可以混合的，特别常用的一些代码，代码用到的次数特别多，这时候它会把一个即时的编译做成一个本地的编译，就像C语言在windows上执行的时候把它编译成exe一样，**那么下次再执行这段代码的时候就不需要解释器来一句一句的解释来执行**，执行引擎可以直接交给操作系统去让他调用，这个效率会高很多。不是所有的代码都会被JIT进行及时编译的。如果是这样的话，整个java就变成了不能跨平台了，有一些特定的，执行次数好多好多的时候，会进行及时编译器的编译，这一块叫java虚拟机。

![image-20220418004659450](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220418004659450.png)

java虚拟机怎么才能做到这么多语言都可以往上跑呢，关键的原因就是class这个东西，任何语言只要你能编译成class，符合class文件的规范你就可以扔在java虚拟机上去执行。所以，从jvm的角度来讲，它是不看你任何的语言的，只和class文件有关系，不管你是谁，只要你变成class，那就是我的菜

## 什么是class文件？class文件主要信息结构有？

![image-20210811175244548](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811175244548.png)

**Class文件解析时，严格按照ClassFile的定义进行，顺序不允许打乱**

每一个 Class 文件对应于一个如下所示的 ClassFile 结构体。

![image-20211213011255618](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211213011255618.png)

ClassFile 结构体中，各项的含义描述如下：
1，无符号数，以u1、u2、u4、u8分别代表1个字节、2个字节、4个字节、8个字节的无符号数

2，表是由多个无符号数或者其它表作为数据项构成的复合数据类型，所以表都以“_info”结尾，由多个无符号数或其它表构成的复合数据类型

每个部分出现的次数和数量见下表：

![image-20211213011323322](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211213011323322.png)

magic
     魔数，魔数的唯一作用是确定这个文件是否为一个能被虚拟机所接受的 Class 文件。魔数值固定为 0xCAFEBABE，不会改变。

minor_version、major_version
     副版本号和主版本号，minor_version 和 major_version 的值分别表示 Class 文件的副、主版本。它们共同构成了 Class 文件的格式版本号。譬如某个 Class 文件的主版本号为 M，副版本号为 m，那么这个 Class 文件的格式版本号就确定为 M.m。Class 文件格式版本号大小的顺序为：1.5 < 2.0 < 2.1。
一个 Java 虚拟机实例只能支持特定范围内的主版本号（Mi 至 Mj）和 0 至特定范围内（0 至 m）的副版本号。假设一个 Class 文件的格式版本号为 V，仅当 Mi.0 ≤ v ≤ Mj.m成立时，这个 Class 文件才可以被此 Java 虚拟机支持。不同版本的 Java 虚拟机实现支持的版本号也不同，高版本号的 Java 虚拟机实现可以支持低版本号的 Class 文件，反之则不成立 。

注意：Oracle 的 JDK 在 1.0.2 版本时，支持的 Class 格式版本号范围是 45.0 至 45.3；JDK 版本在 1.1.x时，支持的 Class 格式版本号范围扩展至 45.0 至 45.65535；JDK 版本为 1. k 时（k ≥2）时，对应的 Class文件格式版本号的范围是 45.0 至 44+k.0

constant_pool_count
常量池计数器，constant_pool_count的值等于constant_pool表中的成员数加1。constant_pool 表的索引值只有在大于 0 且小于 constant_pool_count 时才会被认为是有效的 ，对于 long 和 double 类型有例外情况，后续在讲解。

注意：虽然值为 0 的 constant_pool 索引是无效的，但其他用到常量池的数据结构可以使用索引 0 来表示“不引用任何一个常量池项”的意思。

**constant_pool[ ]**
**常量池**，constant_pool 是一种表结构（这里需要列举一下表就会明白，这个在下面的例子中会有讲解这个结构，返回来在读就会明白），它包含 Class 文件结构及其子结构中引用的所有字符串常量、类或接口名、字段名和其它常量。常量池中的每一项都具备相同的格式特征——第一个字节作为类型标记用于识别该项是哪种类型的常量，称为“tagbyte”。常量池的索引范围是 1 至 constant_pool_count−1。

1常量池的项目类型

![image-20211213011417526](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211213011417526.png)



2每一种类型的格式特征：这里用CONSTANT_Class_info举个例子：

![image-20211213011455771](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211213011455771.png)

常量池主要存放两大类常量：字面量（literal）和符号引用。**字面量比较接近java语言层面的常量概念，比如文本字符串、声明的final的常量值等**。符号引用属于编译原理方面概念。包括下面三类常量：类和接口的全局限定名。字段的名称和描述符。方法的名称和描述符。

 **access_flags**
**访问标志**，access_flags 是一种掩码标志，用于表示某个类或者接口的访问权限及基础属性。access_flags 的取值范围和相应含义见表 4.1 所示。

![image-20211213011551873](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211213011551873.png)

带有 ACC_SYNTHETIC 标志的类，意味着它是由编译器自己产生的而不是由程序员编写的源代码生成的。

 带有 ACC_ENUM 标志的类，意味着它或它的父类被声明为枚举类型。

 带有 ACC_INTERFACE 标志的类，意味着它是接口而不是类，反之是类而不是接口。如果一个 Class 文件被设置了 ACC_INTERFACE 标志，那么同时也得设置ACC_ABSTRACT 标志（JLS §9.1.1.1）。同时它不能再设置 ACC_FINAL、ACC_SUPER 和 ACC_ENUM 标志。

 注解类型必定带有 ACC_ANNOTATION 标记，如果设置了 ANNOTATION 标记，ACC_INTERFACE 也必须被同时设置。如果没有同时设置 ACC_INTERFACE 标记，那么这个Class文件可以具有表4.1中的除ACC_ANNOTATION外的所有其它标记。当然 ACC_FINAL 和 ACC_ABSTRACT 这类互斥的标记除外（JLS §8.1.1.2）。

 ACC_SUPER 标志用于确定该 Class 文件里面的 invokespecial 指令使用的是哪一种执行语义。目前 Java 虚拟机的编译器都应当设置这个标志。ACC_SUPER 标记是为了向后兼容旧编译器编译的 Class 文件而存在的，在 JDK1.0.2 版本以前的编译器产生的 Class 文件中，access_flag 里面没有 ACC_SUPER 标志。同时，JDK1.0.2 前的 Java 虚拟机遇到 ACC_SUPER 标记会自动忽略它。

 在表 4.1 中没有使用的 access_flags 标志位是为未来扩充而预留的，这些预留的标志为在编译器中会被设置为 0， Java 虚拟机实现也会自动忽略它们。


 this_class

类索引，this_class 的值必须是对 constant_pool 表中项目的一个有效索引值。constant_pool 表在这个索引处的项必须为 CONSTANT_Class_info 类型常量，表示这个 Class 文件所定义的类或接口。

super_class

父类索引，对于类来说，super_class 的值必须为 0 或者是对 constant_pool 表中项目的一个有效索引值。如果它的值不为 0，那 constant_pool 表在这个索引处的项

必须为 CONSTANT_Class_info 类型常量（§4.4.1），表示这个 Class 文件所定义的类的直接父类。当前类的直接父类，以及它所有间接父类的 access_flag 中都不能带有 ACC_FINAL 标记。对于接口来说，它的 Class 文件的 super_class 项的值必须是对 constant_pool 表中项目的一个有效索引值。constant_pool 表在这个索引处的
项必须为代表 java.lang.Object 的 CONSTANT_Class_info 类型常量（§4.4.1）。如果 Class 文件的 super_class 的值为 0，那这个 Class 文件只可能是定义的是java.lang.Object 类，只有它是唯一没有父类的类。


interfaces_count
接口计数器，interfaces_count 的值表示当前类或接口的直接父接口数量。

 **interfaces[]**
接口表，interfaces[]数组中的每个成员的值必须是一个对 constant_pool 表中项目的一个有效索引值，它的长度为 interfaces_count。每个成员 interfaces[i] 必须为 CONSTANT_Class_info 类型常量（§4.4.1），其中 0 ≤ i <interfaces_count。在 interfaces[]数组中，成员所表示的接口顺序和对应的源代码中给定的接口顺序（从左至右）一样，即 interfaces[0]对应的是源代码中最左边的接口。

**fields_count**
字段计数器，fields_count 的值表示当前 Class 文件 fields[]数组的成员个数。fields[]数组中每一项都是一个 field_info 结构（§4.5）的数据项，它用于表示该类或接口声明的类字段或者实例字段 。

注意：：类字段即被声明为 static 的字段，也称为类变量或者类属性，同样，实例字段是指未被声明为static 的字段。由于《Java 虚拟机规范》中，“Variable”和“Attribute”出现频率很高且在大多数场景中具备其他含义，所以译文中统一把“Field”翻译为“字段”，即“类字段”、“实例字段”。

**fields[]**
字段表，fields[]数组中的每个成员都必须是一个 fields_info 结构（§4.5）的数据项，用于表示当前类或接口中某个字段的完整描述。fields[]数组描述当前类或接口声明的所有字段，但不包括从父类或父接口继承的部分。

 **methods_count**
方法计数器，methods_count 的值表示当前 Class 文件 methods[]数组的成员个数。Methods[]数组中每一项都是一个 method_info 结构（§4.5）的数据项。

**methods[]**
方法表，methods[]数组中的每个成员都必须是一个 method_info 结构（§4.6）的数据项，用于表示当前类或接口中某个方法的完整描述。如果某个 method_info 结构的 access_flags 项既没有设置 ACC_NATIVE 标志也没有设置 ACC_ABSTRACT 标志，那么它所对应的方法体就应当可以被 Java 虚拟机直接从当前类加载，而不需要引用其它类。method_info 结构可以表示类和接口中定义的所有方法，包括实例方法、类方法、实例初始化方法方法（§2.9）和类或接口初始化方法方法（§2.9）。methods[]数组
只描述当前类或接口中声明的方法，不包括从父类或父接口继承的方法。

**attributes_count**
属性计数器，attributes_count 的值表示当前 Class 文件 attributes 表的成员个数。attributes 表中每一项都是一个 attribute_info 结构（§4.7）的数据项。

**attributes[]**
属性表，attributes 表的每个项的值必须是 attribute_info 结构（§4.7）。在本规范里，Class 文件结构中的 attributes 表的项包括下列定义的属性：
InnerClasses（§4.7.6）、EnclosingMethod（§4.7.7）、Synthetic（§4.7.8）、Signature（§4.7.9）、SourceFile（§4.7.10），SourceDebugExtension（§4.7.11）、Deprecated（§4.7.15）、RuntimeVisibleAnnotations（§4.7.16）、RuntimeInvisibleAnnotations（§4.7.17）以及BootstrapMethods（§4.7.21）属性。对于支持 Class 文件格式版本号为 49.0 或更高的 Java 虚拟机实现，必须正确识别并读取 attributes 表中的 Signature（§4.7.9）、RuntimeVisibleAnnotations（§4.7.16）和RuntimeInvisibleAnnotations（§4.7.17）属性。对于支持 Class 文件格式版本号为 51.0 或更高的 Java 虚拟机实现，必须正确识别并读取 attributes 表中的
BootstrapMethods（§4.7.21）属性。本规范要求任一 Java 虚拟机实现可以自动忽略 Class 文件的 attributes 表中的若干（甚至全部）它不可识别的属性项。任何本规范未定义的属性不能影响 Class 文件的语义，只能提供附加的描述信息（§4.7.1）。

[jdk源码解析（六）——类（class）文件结构_sinat_38259539的博客-CSDN博客_jdk源码文件](https://blog.csdn.net/sinat_38259539/article/details/78248454)





对象不可能再被任何途径使用，称为==对象已死==。判断对象已死的方法有：引用计数法和可达性分析算法。



## 加载验证准备解析初始化

一个class文件怎么从硬盘上到内存中：

有一个class文件，它默默的躺在了硬盘上 ，需要经过一个什么样的过程才能到内存里准备好呢。 class怎么进入内存有三大步。
第一大步叫Loading
第二大步叫Linking（连接）
Linking又分为三小步，第一小步Verification，第二小步Preparation，第三小步Resolution(验证准备解析)

第三大步叫Initlalizing
我们今天把这个过程尝试着给大家讲清楚，内容比较多，首先来看Loading的过程，给大家解释一下每一步是什么意思。
首先Loading是什么意思呢？是把一个class文件load内存装到内存里去，他本来是是class文件上的一个一个的二进制，一个一个的字节，装完之后就是接下来Linking。

Linking的过程分为三小步
Verification是校验装进来的class文件是不是符合class文件的标准，**假如你装进来的不是这个CAFE BA BE，在这步骤就被拒掉了。**
Preparation是把class文件静态变量赋默认值，不是赋初始值，比如你static int i = 8，注意在这个步骤8并不是在把i值赋成8，而是先赋成0
Resolution是把class文件常量池里面用到的符号引用，要给它转换为直接内存地址，直接可以访问到的内容

Initlalizing意思是静态变量这时候赋值才成为初始值

当我们的Java代码编译完成后，会生成对应的 class 文件。接着我们运行`java Demo`命令的时候，我们其实是启动了JVM 虚拟机执行 class 字节码文件的内容。而 JVM 虚拟机执行 class 字节码的过程可以分为七个阶段：**加载、验证、准备、解析、初始化、使用、卸载。**

加载-连接（验证 准备 解析）-初始化：

加载阶段：

加载是类加载过程中的一个阶段，这个阶段会在内存中生成一个代表这个类的java.lang.Class 对象，作为方法区这个类的各种数据的入口。注意这里不一定非得要从一个Class 文件获取，这里既
可以从ZIP 包中读取（比如从jar 包和war 包中读取），也可以在运行时计算生成（动态代理），也可以由其它文件生成（比如将JSP 文件转换成对应的Class 类）。

![image-20220213045012125](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220213045012125.png)

有关连接阶段：

![image-20220213044839517](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220213044839517.png)

我们知道，class文件是可以被认为篡改的，虚拟机如果直接拿来执行，可能会把系统给搞崩溃了，所以一定要先对Class文件做严格的验证。验证阶段主要完成以下检测动作：

1.2.1.1、文件格式验证
主要按照Class文件16进制背后的秘密文章中的阐述的格式，严格的进行校验。

1.2.1.2、元数据验证
主要是语义校验，保证不存在不符合Java语言规范的元数据信息，如：没有父类，继承了final类，接口的非抽象类实现没有完整实现方法等。

1.2.1.3、字节码验证
主要对数据流和控制流进行分析，确定成行语义是否合法，符合逻辑。不合法的例子：

操作数栈放置了int类型数据，却当成long类型使用；
把父类对象赋值给了子类数据类型；

a) 遇到一个新的类时，首先会到方法区去找class文件，如果没有找到就会去硬盘中找class文件，找到后会返回，**将class文件加载到方法区中**，在类加载的时候，静态成员变量会被分配到方法区的静态区域，非静态成员变量分配到非静态区域，然后开始给静态成员变量初始化，赋默认值，赋完默认值后，会根据静态成员变量书写的位置赋显式值，然后执行静态代码。当所有的静态代码执行完，类加载才算完成



==加载==：类加载过程的一个阶段：通过一个类的完全限定查找此类字节码文件，并利用字节码文件创建一个Class对象，在这个阶段，JVM 的主要目的是将字节码从各个位置（网络、磁盘等）转化为二进制字节流加载到内存中，**接着会为这个类在 JVM 的方法区创建一个对应的 Class 对象，这个 Class 对象就是这个类各种数据的访问入口**

==验证==：目的在于确保Class文件的字节流中包含信息符合当前虚拟机要求，不会危害虚拟机自身安全。主要包括四种验证，文件格式验证，元数据验证，字节码验证，符号引用验证。当 JVM 加载完 Class 字节码文件并在方法区创建对应的 Class 对象之后，JVM 便会启动对该字节码流的校验，只有符合 JVM 字节码规范的文件才能被 JVM 正确执行。

==准备==：当完成字节码文件的校验之后，JVM 便会开始为类变量分配内存并初始化。这里需要注意两个关键点，即内存分配的对象以及初始化的类型。

- **内存分配的对象。**Java 中的变量有「类变量」和「类成员变量」两种类型，「类变量」指的是被 static 修饰的变量，而其他所有类型的变量都属于「类成员变量」。在准备阶段，JVM 只会为「类变量」分配内存，而不会为「类成员变量」分配内存。「类成员变量」的内存分配需要等到初始化阶段才开始。

例如下面的代码在准备阶段，只会为 factor 属性分配内存，而不会为 website 属性分配内存。

```javascript
public static int factor = 3;public String website = "www.cnblogs.com/chanshuyi";
```

- **初始化的类型。**在准备阶段，JVM 会为类变量分配内存，并为其初始化。但是这里的初始化指的是为变量赋予 Java 语言中该数据类型的零值，而不是用户代码里初始化的值。

例如下面的代码在准备阶段之后，sector 的值将是 0，而不是 3。

```javascript
public static int sector = 3;
```

**但如果一个变量是常量（被 static final 修饰）的话，那么在准备阶段，属性便会被赋予用户希望的值。**例如下面的代码在准备阶段之后，number 的值将是 3，而不是 0。

```javascript
public static final int number = 3;
```

之所以 static final 会直接被赋值，而 static 变量会被赋予零值。其实我们稍微思考一下就能想明白了。

两个语句的区别是一个有 final 关键字修饰，另外一个没有。而 final 关键字在 Java 中代表不可改变的意思，意思就是说 number 的值一旦赋值就不会在改变了。**既然一旦赋值就不会再改变，那么就必须一开始就给其赋予用户想要的值**，因此被 final 修饰的类变量在准备阶段就会被赋予想要的值。而没有被 final 修饰的类变量，其可能在初始化阶段或者运行阶段发生变化，所以就没有必要在准备阶段对它赋予用户想要的值。



==解析==：当通过准备阶段之后，JVM 针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符 7 类引用进行解析。**这个阶段的主要任务是将其在常量池中的符号引用替换成直接其在内存中的直接引用。**

其实这个阶段对于我们来说也是几乎透明的，了解一下就好。

==初始化（重点）==

若该类具有超类，则对其进行初始化，执行静态初始化器和静态初始化成员变量(如前面只初始化了默认值的static变量将会在这个阶段赋值，成员变量也将被初始化)。

到了初始化阶段，用户定义的 Java 程序代码才真正开始执行。在这个阶段，JVM 会根据语句执行顺序对类对象进行初始化，一般来说当 JVM 遇到下面 5 种情况的时候会触发初始化：

- 遇到 new、getstatic、putstatic、invokestatic 这四条字节码指令时，如果类没有进行过初始化，则需要先触发其初始化。生成这4条指令的最常见的Java代码场景是：使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译器把结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法的时候。
- 使用 java.lang.reflect 包的方法对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化。
- 当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化。
- 当虚拟机启动时，用户需要指定一个要执行的主类（包含main()方法的那个类），虚拟机会先初始化这个主类。
- 当使用 JDK1.7 动态语言支持时，如果一个 java.lang.invoke.MethodHandle实例最后的解析结果 REF_getstatic,REF_putstatic,REF_invokeStatic 的方法句柄，并且这个方法句柄所对应的类没有进行初始化，则需要先出触发其初始化。

看到上面几个条件你可能会晕了，但是不要紧，不需要背，知道一下就好，后面用到的时候回到找一下就可以了。



==使用==

当 JVM 完成初始化阶段之后，JVM 便开始从入口方法开始执行用户的程序代码。这个阶段也只是了解一下就可以。

==卸载==

当用户程序代码执行完毕后，JVM 便开始销毁创建的 Class 对象，最后负责运行的 JVM 也退出内存。这个阶段也只是了解一下就可以。

解释2：

当Java虚拟机遇到一条字节码new指令时，首先将去检查这个指令的参数是否能在常量池中定位到一个类的符号引用，并且检查这个符号引用代表的类是否已被加载、解析和初始化过。如果没有，那必须先执行相应的类加载过程， 在类加载检查通过后，接下来虚拟机将为新生对象分配内存。对象所需内存的大小在类加载完成后便可完全确定，为对象分配空间的任务实际上便等同于把一块确定大小的内存块从Java堆中划分出来。假设Java堆中内存是绝对规整的，所有被使用过的内存都被放在一边，空闲的内存被放在另一边，中间放着一个指针作为分界点的指示器，那所分配内存就仅仅是把那个指针向空闲空间方向挪动一段与对象大小相等的距离，**这种分配方式称为“指针碰撞”**（Bump The Pointer）。但如果Java堆中的内存并不是规整的，已被使用的内存和空闲的内存相互交错在一起，那就没有办法简单地进行指针碰撞了，虚拟机就必须维护一个列表，记录上哪些内存块是可用的，在分配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的记录，**这种分配方式称为“空闲列表”**（Free List）。选择哪种分配方式由Java堆是否规整决定，而Java堆是否规整又由所采用的垃圾收集器是否带有空间压缩整理（Compact）的能力决定。因此，当使用Serial、ParNew等带压缩整理过程的收集器时，系统采用的分配算法是指针碰撞，既简单又高效；而当使用CMS这种基于清除（Sweep）算法的收集器时，理论上就只能采用较为复杂的空闲列表来分配内存。 



1.描述一下JVM 加载class ：

JVM 中类的装载是由类加载器（ClassLoader）和它的子类来实现的，Java 中的类加载器是一个重要的Java 运行时系统组件，它负责在运行时查找和装入类文件中的类。

![image-20210811173920714](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811173920714.png)

2.第二步：验证


验证的目的是为了确保 Class 文件的字节流中的信息不回危害到虚拟机.在该阶段主要完成以下四钟验证:
文件格式验证：验证字节流是否符合 Class 文件的规范，如主次版本号是否在当前虚拟机范围内，常量池中的常量是否 有不被支持的类型.
元数据验证:对字节码描述的信息进行语义分析，如这个类是 否有父类，是否集成了不被继承的类等。

字节码验证：是整个验证过程中最复杂的一个阶段，通过验证数据流和控制流的分析，确定程序语义是否正确，主要针对方法体的验证。如：方法中的类型转换是否正确，跳转指令是否正确等。
符号引用验证：这个动作在后面的解析过程中发生，主要是为了确保解析动作能正确执行。

3、准备
准备阶段是为类的静态变量分配内存并将其初始化为默认值，这些 内存都将在方法区中进行分配。准备阶段不分配类中的实例变量的 内存，实例变量将会在对象实例化时随着对象一起分配在 Java 堆中。



```java
public static int value=123;//在准备阶段 value 初始值为 0 。在初 始化阶段才会变为 123 。
```



4、解析
该阶段主要完成符号引用到直接引用的转换动作。解析动作并不一 定在初始化动作完成之前，也有可能在初始化之后。
初始化 初始化时类加载的最后一步，前面的类加载过程，除了在加载阶段 用户应用程序可以通过自定义类加载器参与之外。

5、初始化
初始化时类加载的最后一步，前面的类加载过程，除了在加载阶段用户应用程序可以通过自定义类加载器参与之外，其余动作完全由虚拟机主导和控制。到了初始化阶段，才真正开始执行类中定义的Java 程序代码。


初始化过程的主要操作是<font color="red">执行静态代码块和初始化静态域。</font>
在一个类被初始化之前，它的直接父类也需要被初始化。但是，一个接口的初始化，不会引起其父接口的初始化。
在初始化的时候，会按照源代码中从上到下的顺序依次执行静态代码块和初始化静态域 

6、使用
7、卸载



由于Java 的跨平台性，经过编译的 Java 源程序并不是一个可执行程序，而是一个或多个类文件。当Java 程序需要使用某个类时,JVM 会确保这个类已经被加载、连接（验证、准备和解析）和初始化。**类的加载是指把类的.class文件中的数据读入到内存中**，通常是创建一个字节数组读入.class 文件，然后产生与所加载类对应的 Class 对象。加载完成后，Class 对象还不完整，所以此时的类还不可用。当类被加载后就进入连接阶段，这一阶段包括验证、准备（为静态变量分配内存并设置默认的初始值）和解析（将符号引用替换为直接引用)三个步骤。最后 JVM 对类进行初始化，包括：

如果类存在直接的父类并且这个类还没有被初始化，那么就先初始化父类；
如果类中存在初始化语句，就依次执行这些初始化语句。类的加载是由类加载器完成的，类加载器包括：根加载器（BootStrap）、扩展加载器（Extension）、系统加载器（System）和用户自定义类加载器（java.lang.ClassLoader的子类）。
从 Java 2（JDK 1.2）开始，类加载过程采取了父亲委托机制（PDM）。PDM 更好的保证了 Java 平台的安全性，在该机制中，JVM 自带的 Bootstrap 是根加载器，其他的加载器都有且仅有一个父类加载器。类的加载首先请求父类加载器加载，父类加载器无能为力时才由其子类加载器自行加载。==JVM不会向Java 程序提供对 Bootstrap 的引用。==
下面是关于几个类加载器的说明：
• Bootstrap：一般用本地代码实现，负责加载 JVM 基础核心类库（rt.jar）；
• Extension：从 java.ext.dirs 系统属性所指定的目录中加载类库，它的父加载器是 Bootstrap；
• System：又叫应用类加载器，其父类是 Extension。它是应用最广泛的类加载器。它从环境变量classpath或者系统属性 java.class.path 所指定的目录中记载类，是用户自定义加载器的默认父加载器。

![image-20210811172042637](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811172042637.png)

![image-20210811172115442](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811172115442.png)

![image-20210811172709114](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811172709114.png)

![image-20210811173817812](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811173817812.png)









![image-20211128005734409](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211128005734409.png)

我们来看这个类加载器的内容，**首先第一点jvm它本身有一个类加载器的层次，这个类加载器本身就是一个普通的class，jvm有一个类加载器的层次分别来加载不同的class**，jvm所有的class都是被类加载器加载到内存的，那么这个类加载器可以叫做ClassLoader，

例子

```java
package com.mashibing.jvm.c2_classloader;
public class T002_ClassLoaderLevel {
public static void main(String[] args) {
//查看是谁load到内存的，执行结果null，为什么为空呢，Bootstrap使用c++实现的，Java里
并没有class和他对应
System.out.println(String.class.getClassLoader());
//这个是我们核心类库某个包里的类执行结果null，为什么他也是空值呢，因为他也是被我们
Bootstrap类加载器加载的
System.out.println(sun.awt.HKSCS.class.getClassLoader());
//这个类是位于ext目录下某个jar文件里面,当你调用它执行结果也就是
sun.misc.launcher$ExtClassLoader
System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLo
ader());
//这个是我们自己写的ClassLoader加载器，它是由sun.misc.launcher$AppClassLoader加载的
System.out.println(T002_ClassLoaderLevel.class.getClassLoader());
//他是一个Ext的ClassLoader调用他的getclass（）他本身也是一个class，然后调用他的getClassLoader，他的ClassLoader又是谁，就这个ClassLoader的ClassLoader使我们最顶级的ClassLoaderBootstrap，执行结果为null
System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader().getClass().getClassLoader());
System.out.println(T002_ClassLoaderLevel.class.getClassLoader().getClass().getClassLoader());
}
```

第一个类加载器层次
类加载器的加载过程，加载过程是分成不同的层次来加载，不同的类加载器来加载不同的class，最顶层的是Bootstrap,他是来加载lib里jdk最核心的的内容比如说rt.jar charset.jar等核心类。所以说什么时候我调用getClassLoader()拿到这个加载器的结果是一个空值的时候代表的你已经到达了最顶层的加载器。
第二个类加载器层次
这个是Extension加载器扩展类，加载扩展包里的各种各样文件，这些扩展包在jdk安装目录jre/lib/ext下的jar。
第三个类加载器层次
这个就是平时用的加载器application，他用于加载classpath指定的内容。
第四个类加载器层次
这个就是自定义加载器ClassLoader，加载自己自定义的加载器。



每一个class在java虚拟机里面到内存里面任何一个class都是被ClassLoader load内存的，那么这个
ClassLoader 其实就是顶级有一个父类，这个父类就叫ClassLoader ，他是一个abstract抽象类，相当于这个类是被谁领到内存里去了，他一定是ClassLoader 这个类的子类，如果你想知道你的class是被谁
弄到内存的，其实很简单就是下面代码 System.out.println(String.class.getClassLoader());
从大面上给大家讲class对象到底是什么样的东西呢？
**一个class文件平时躺在硬盘上，这个内容被load内存之后，内存发生了什么呢，创建两块内容，第一块内容是把String.class二进制扔到内存中，第二块内容是于此生成了一个class类的对象，这一块对象指向了第一块内容**
内存分区有同学问到了，不过还没讲到这，有同学问了那就说一下，内存分区像存常量存class的各种各样信息时候，实际上它这块内容逻辑上叫method Area叫方法区，1.8之前这个方法区的实现落地在什么地方呢？ PermGen 永久代，1.8之后呢就叫Metaspace，所以这两块呢说的都是方法区，只不过1.8之前叫PermGen ，1.8之后叫Metaspace



## JVM加载class文件的原理机制？

java虚拟机统一了文件存储格式,那就是字节码,所有的操作系统平台的JVM都使用这种统一的格式。JVM不强调你使用哪种语言编写程序,只要最后的程序能够翻译成JVM能够识别、加载、并运行的字节码格式即可

字节码是一种中间状态的（中间码）的二进制代码（文件），字节码（Bytecode）是一种包含执行程序、由一序列 op 代码/数据对 组成的二进制文件。需要直译器转译后才能成为机器码；字节码通常情况下它是已经经过编译，但与特定机器码无关。字节码通常不像源码一样可以让人阅读，而是编码后的数值常量，引用，指令等构成的序列。字节码与特定的硬件环境无关；字节码的实现方式是通过编译器和虚拟机器。编译器将源码编译成字节码，特定平台上的虚拟机器将字节码转译为可以直接执行的指令。字节码的典型应用为Java bytecode



**「类加载机制」**:

- Java 虚拟机**「把描述类的数据从 Class 文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被 Jvm 可以直接使用的类型」**，这个过程就可以成为虚拟机的类加载机制。

这个阶段会在内存中生成一个代表这个类的java.lang.Class 对象，作为方法区这个类的各种数据的入口。注意这里不一定非得要从一个Class 文件获取，这里既可以从ZIP 包中读取（比如从jar 包和war 包中读取），也可以在运行时计算生成（动态代理），也可以由其它文件生成（比如将JSP 文件转换成对应的Class 类）。

我们有一个文件x.java->执行javac->变成x.class，当我们调用Java命令的时候**class会被装载到内存叫classLoader**。一般的情况下我们写自己类文件的时候也会用到java的类库，所以它要把java类库相关类也装载到内存里，装载完成之后会调用字节码解释器或者是即时编译器来进行解释或编译，编译完之后由执行引擎开始执行,执行引擎下面面对就是操作系统的硬件了。这块的内容叫jvm。



java编译好了之后变成class，class会被加载到内存与此同时像String、Object这些class也会加载到内存。
咱们原来老师会问一个问题，说：java是解释执行的还是编译执行啊，其实解释和编译是可以混合的，特别常用的一些代码，代码用到的次数特别多，这时候它会把一个即时的编译做成一个本地的编译，**就像C语言在windows上执行的时候把它编译成exe一样，那么下次再执行这段代码的时候就不需要解释**
**器来一句一句的解释来执行，执行引擎可以直接交给操作系统去让他调用，这个效率会高很多。**不是所有的代码都会被JIT进行及时编译的。如果是这样的话，整个java就变成了不能跨平台了，有一些特定的，执行次数好多好多的时候，会进行及时编译器的编译，这一块叫java虚拟机。 

JVM中类的装载是由类加载器（ClassLoader）和它的子类来实现的，Java中的类加载器是一个重要的Java运行时系统组件，它负责在运行时查找和装入类文件中的类。
由于Java的跨平台性，经过编译的Java源程序并不是一个可执行程序，而是一个或多个类文件。当Java程序需要使用某个类时，JVM会确保这个类已经被加载、连接（验证、准备和解析）和初始化。类的加载是指把类的.class文件中的数据读入到内存中，通常是创建一个字节数组读入.class文件，然后产生与所加载类对应的Class对象。加载完成后，Class对象还不完整，所以此时的类还不可用。当类被加载后就进入连接阶段，这一阶段包括验证、准备（为静态变量分配内存并设置默认的初始值）和解析（将符号引用替换为直接引用或者说内存地址，即可以直接用访问到的内容）三个步骤。最后JVM对类进行初始化，包括：
1、如果类存在直接的父类并且这个类还没有被初始化，那么就先初始化父类；
2、如果类中存在初始化语句，就依次执行这些初始化语句。

从Java 2（JDK 1.2）开始，类加载过程采取了父亲委托机制（PDM）。PDM更好的保证了Java平台的安全性，在该机制中，JVM自带的Bootstrap是根加载器，其他的加载器都有且仅有一个父类加载器。**类的加载首先请求父类加载器加载，父类加载器无能为力时才由其子类加载器自行加载**。JVM不会向Java程序提供对Bootstrap的引用。下面是关于几个类加载器的说明：
1、Bootstrap：一般用本地代码实现，负责加载JVM基础核心类库（rt.jar）；
2、Extension：从java.ext.dirs系统属性所指定的目录中加载类库，它的父加载器是Bootstrap；
3、System：又叫应用类加载器，其父类是Extension。它是应用最广泛的类加载器。它从环境变量classpath或者系统属性java.class.path所指定的目录中记载类，是用户自定义加载器的默认父加载器



## 类加载器

描述一下JVM 加载class 文件的原理机制?
答：JVM 中类的装载是由类加载器（ClassLoader） 和它的子类来实现的，Java 中的类加载器是一个重要的Java 运行时系统组件，它负责在运行时查找和装入类文件中的类

类加载器的一个重要用途是在JVM中为相同名称的Java类创建隔离空间。在JVM中，判断两个类是否相同，不仅是根据该类的二进制名称，还需要根据两个类的定义类加载器。只有两者完全一样，才认为两个类的是相同的。因此，即便是同样的Java字节代码，被两个不同的类加载器定义之后，所得到的Java类也是不同的。如果试图在两个类的对象之间进行赋值操作，会抛出java.lang.ClassCastException。这个特性为同样名称的Java类在JVM中共存创造了条件。


java 是一种**类型安全的语言**，它有四类称为安全沙箱机制的安全机制来保证语言的安全性，这四类安全沙箱分别是：
1） 类加载体系
2） .class 文件检验器

3） 内置于Java 虚拟机（及语言）的安全特性
4） 安全管理器及Java API

java 程序中的 .java 文件编译完会生成 .class 文件，而 .class 文件就是通过被称为类加载器的ClassLoader加载的，而ClassLoder 在加载过程中会使用“双亲委派机制”来加载 .class 文件



1.由于Java 的跨平台性，经过编译的Java 源程序并不是一个可执行程序，而是一个或多个类文件。当Java 程序需要使用某个类时，JVM 会确保这个类已经被加载、连接(验证、准备和解析)和初始化。**类的加载是指把类的.class 文件中的数据读入到内存中，通常是创建一个字节数组读入.class 文件，然后产生与所加载类对应的Class 对象**。类的加载是由类加载器完成的，类加载器包括：根加载器（BootStrap）、扩展加载器（Extension）、系统加载器（System）和用户自定义类加载器（java.lang.ClassLoader 的子类）。从JDK 1.2 开始，类加载过程采取了父亲委托机制(PDM)。PDM 更好的保证了Java 平台的安全性，在该机制中，JVM 自带的Bootstrap 是根加载器，其他的加载器都有且仅有一个父类加载器。类的加载首先请求父类加载器加载，父类加载器无能为力时才由其子类加载器自行加载。JVM 不会向Java 程序提供对Bootstrap 的引用。

1）当AppClassLoader 加载一个class 时，它首先不会自己去尝试加载这个类，而是把类加载请求委派给父类加载器ExtClassLoader 去完成。
2）当ExtClassLoader 加载一个class 时，它首先也不会自己去尝试加载这个类，而是把类加载请求委派给BootStrapClassLoader 去完成。
3）如果BootStrapClassLoader 加载失败（例如$JAVA_HOME$/jre/lib 里未查找到该class），会使用ExtClassLoader 来尝试加载；
4）若ExtClassLoader 也加载失败，则会使用AppClassLoader 来加载，如果AppClassLoader 也加载失败，则会报出异常ClassNotFoundException。

·启动类加载器（Bootstrap Class Loader）：这个类加载器负责加载存放在<JAVA_HOME>\lib目录，或者被-Xbootclasspath参数所指定的路径中存放的，而且是Java虚拟机能够识别的（按照文件名识别，如rt.jar、tools.jar，名字不符合的类库即使放在lib目录中也不会被加载）类库加载到虚拟机的内存中。启动类加载器无法被Java程序直接引用，用户在编写自定义类加载器时，如果需要把加载请求委派给引导类加载器去处理，那直接使用null代替即可

·扩展类加载器（Extension Class Loader）：这个类加载器是在类sun.misc.Launcher$ExtClassLoader中以Java代码的形式实现的。它负责加载<JAVA_HOME>\lib\ext目录中，或者被java.ext.dirs系统变量所指定的路径中所有的类库。根据“扩展类加载器”这个名称，就可以推断出这是一种Java系统类库的扩展机制，JDK的开发团队允许用户将具有通用性的类库放置在ext目录里以扩展Java SE的功能，在JDK 9之后，这种扩展机制被模块化带来的天然的扩展能力所取代。由于扩展类加载器是由Java代码实现的，开发者可以直接在程序中使用

·应用程序类加载器（Application Class Loader）：这个类加载器由sun.misc.Launcher$AppClassLoader来实现。它负责加载用户类路径（ClassPath）上所有的类库，开发者同样可以直接在代码中使用这个类加载器。如果应用程序中没有自定义过自己的类加载器，一般情况下这个就是程序中默认的类加载器。

如果用户认为有必要，还可以加入自定义的类加载器来进行拓展，典型的如增加除了磁盘位置之外的Class文件来源，或者通过类加载器实现类的隔离、重载等功能

加载完成后，Class 对象还不完整，所以此时的类还不可用。当类被加载后就进入连接阶段，这一阶段包括验证(校验字节码文件)、准备(为静态变量分配内存并设置默认的初始值)和解析(将符号引用替换为直接引用)三个步骤。

**符号引用：**

就是class 文件中
1. CONSTANT_Class_info
2. CONSTANT_Field_info
3. CONSTANT_Method_info

符号引用与虚拟机实现的布局无关，引用的目标并不一定要已经加载到内存中。各种虚拟机实现的内存布局可以各不相同，但是它们能接受的符号引用必须是一致的，因为符号引用的字面量形式明确定义在Java 虚拟机规范的Class 文件格式中。
 **直接引用**
􀂄 直接引用可以是指向目标的指针，相对偏移量或是一个能间接定位到目标的句柄。如果有了直接引用，那引用的目标必定已经在内存中存在。

最后JVM 对类进行初始化，包括：

1 如果类存在直接的父类并且这个类还没有被初始化，那么就先初始化父类；

2 如果类中存在初始化语句，就依次执行这些初始化语句。



下面贴下ClassLoader 的loadClass(String name, boolean resolve)的源码：

````java
protected synchronized Class<?> loadClass(String name,boolean resolve){
    //先查找缓存中是否有class
    Class c = findLoadedClass(name);
    if(c == null)
    {
        //如果没有的话判断父类
        try {
 if (parent != null) {
 //有的话，用父类递归获取class
 c = parent.loadClass(name, false);
 } else {
 //没有父类。通过这个方法来加载
 c = findBootstrapClassOrNull(name);
 }
 } catch (ClassNotFoundException e) {
 // ClassNotFoundException thrown if class not found
 // from the non-null parent class loader
 }
 if (c == null) {
 // 如果还是没有找到，调用findClass(name)去找这个类
 c = findClass(name);
 }
 }
 if (resolve) {
 resolveClass(c);
 }
 return c;
    }
}
````

代码很明朗：首先找缓存（findLoadedClass），没有的话就判断有没有parent，有的话就用parent 来递归的loadClass，然而ExtClassLoader 并没有设置parent，则会通过findBootstrapClassOrNull 来加载class，而
findBootstrapClassOrNull 则会通过JNI 方法”private native Class findBootstrapClass(String name)“来使用BootStrapClassLoader 来加载class。
然后如果parent 未找到class，则会调用findClass 来加载class，findClass 是一个protected 的空方法，可以覆盖它以便自定义class 加载过程。另外， 虽然ClassLoader 加载类是使用loadClass 方法， 但是鼓励用 ClassLoader 的子类重写findClass(String)，而不是重写loadClass，这样就不会覆盖了类加载默认的双亲委派机制。

自定义类加载器

```java
public class MyClassLoader extends ClassLoader{
    pub
        
}
```

## 双亲委派托机制为什么安全

类加载的代码：

```java
public abstract class ClassLoader {
//加载具有指定二进制名称的类。此方法的默认实现按以下顺序搜索类：调用 findLoadedClass(String) 以检查该类是否已加载。在父类加载器上调用 loadClass 方法。如果 parent 为 null，则使用虚拟机内置的类加载器。调用 findClass(String) 方法来查找 class
private final ClassLoader parent;
 protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            //首先检查是否已经加载过
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    //从非空父类加载器
                }

                if (c == null) {
                    // 如果仍未找到，则调用 findClass 以查找该类
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
```

举个例子，ClassLoader 加载的class 文件来源很多，比如编译器编译生成的class、或者网络下载的字节码。

而一些来源的class 文件是不可靠的，比如我可以自定义一个java.lang.Integer 类来覆盖jdk 中默认的Integer

例如下面这样：

```java
 package java.lang;
 public class Integer {
 public Integer(int value) {
System.exit(0);
}
}
```

初始化这个Integer 的构造器是会退出JVM，破坏应用程序的正常进行，如果使用双亲委派机制的话该Integer 类永远不会被调用，以为委托BootStrapClassLoader 加载后会加载JDK 中的Integer 类而不会加载自定义的这个

## 如何破坏双亲委派模型



![image-20210906160538880](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906160538880.png)

![image-20210906161312854](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906161312854.png)

关于线程上下文类加载器的更详细解释：一个典型的例子便是JNDI服务，JNDI现在已经是Java的标准服务，它的代码由启动类加载器来完成加载（在JDK 1.3时加入到rt.jar的），肯定属于Java中很基础的类型了。但JNDI存在的目的就是对资源进行查找和集中管理，它需要调用由其他厂商实现并部署在应用程序的ClassPath下的JNDI服务提供者接口（Service Provider Interface，SPI）的代码，现在问题来了，启动类加载器是绝不可能认识、加载这些代码的，那该怎么办？为了解决这个困境，Java的设计团队只好引入了一个不太优雅的设计：线程上下文类加载器（Thread Context ClassLoader）。这个类加载器可以通过java.lang.Thread类的setContext-ClassLoader()方法进行设置，如果创建线程时还未设置，它将会从父线程中继承一个，如果在应用程序的全局范围内都没有设置过的话，那这个类加载器默认就是应用程序类加载器。 有了线程上下文类加载器，程序就可以做一些“舞弊”的事情了。JNDI服务使用这个线程上下文类加载器去加载所需的SPI服务代码，这是一种父类加载器去请求子类加载器完成类加载的行为，这种行为实际上是打通了双亲委派模型的层次结构来逆向使用类加载器，已经违背了双亲委派模型的一般性原则，但也是无可奈何的事情。Java中涉及SPI的加载基本上都采用这种方式来完成，例如JNDI、JDBC、JCE、JAXB和JBI等。不过，当SPI的服务提供者多于一个的时候，代码就只能根据具体提供者的类型来硬编码判断，为了消除这种极不优雅的实现方式，在JDK 6时，JDK提供了java.util.ServiceLoader类，以META-INF/services中的配置信息，辅以责任链模式，这才算是给SPI的加载提供了一种相对合理的解决方案。

**为什么要搞双亲委派这是一个类加载器面试题必问的问题。**
主要是为了安全，加入用反正法，如果任何一个class都可以把它load内存的话，那我就可以给你java.lang.string交给自定义ClassLoader，把这个stringload进内存，打包给客户，然后把密码存
储成string类型对象，我可以偷偷摸摸的把密码发给我自己，那就不安全了。
双亲委派的就不会出现过这样，自定义ClassLoader加载一个java.lang.string他就产生了警惕，他先去上面查有没有加载过，上面有加载过直接返回给你，不给你重新加载。

父加载器不是“类加载器的加载器”也不是“类加载器的父类加载器“

如何实现自定义类加载器：

![image-20211128011032216](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211128011032216.png)

![image-20220126155101085](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126155101085.png)

loadclass()的作用是通过指定的全限定类名加载Class，重写loadclass方法后这个方法可以指定类通过什么加载器来进行加载，默认的过程是判断这个类是不是已经被当前层的类加载器加载过，如果没有加载过就将该类委派给父类加载器，如果父类加载器无法加载再向下加载，回来由自己来进行加载，重写了这个方法以后就能自己定义使用什么加载器了，也可以自定义加载委派机制，也就打破了双亲委派模型

[【JVM笔记】如何打破双亲委派机制？_小七的博客-CSDN博客_如何打破双亲委派机制](https://blog.csdn.net/cy973071263/article/details/104129163)

关于spi机制：

**Java 提供了很多服务提供者接口(Service Provider Interface，SPI)，允许第三方为这些接口提供实现。常见的 SPI 有 JDBC、JNDI、JAXP 等，这些SPI的接口由核心类库提供，却由第三方实现，这样就存在一个问题：SPI 的接口是 Java 核心库的一部分，是由BootstrapClassLoader加载的；SPI实现的Java类一般是由AppClassLoader来加载的。BootstrapClassLoader是无法找到 SPI 的实现类的，因为它只加载Java的核心库。它也不能代理给AppClassLoader，因为它是最顶层的类加载器。也就是说，双亲委派模型并不能解决这个问题**

SPI的思想：系统里抽象的各个模块，往往有很多不同的实现方案，比如日志模块的方案，xml解析模块、jdbc模块的方案等。面向的对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。为了实现在模块装配的时候能不在程序里动态指明，这就需要一种服务发现机制。**java spi就是提供这样的一个机制：为某个接口寻找服务实现的机制**





如果不做任何的设置，Java应用的线程的上下文类加载器默认就是AppClassLoader。在核心类库使用SPI接口时，传递的类加载器使用线程上下文类加载器，就可以成功的加载到SPI实现的类。线程上下文类加载器在很多SPI的实现中都会用到。

通常我们可以通过Thread.currentThread().getClassLoader()和Thread.currentThread().getContextClassLoader()获取线程上下文类加载器。

类加载器除了加载class外，还有一个非常重要功能，就是加载资源，它可以从jar包中读取任何资源文件，比如，ClassLoader.getResources(String name)方法就是用于读取jar包中的资源文件



```php
//获取资源的方法
public Enumeration<URL> getResources(String name) throws IOException {
    Enumeration<URL>[] tmp = (Enumeration<URL>[]) new Enumeration<?>[2];
    if (parent != null) {
        tmp[0] = parent.getResources(name);
    } else {
        tmp[0] = getBootstrapResources(name);
    }
    tmp[1] = findResources(name);
    return new CompoundEnumeration<>(tmp);
}
```

它的逻辑其实跟类加载的逻辑是一样的，首先判断父类加载器是否为空，不为空则委托父类加载器执行资源查找任务，直到BootstrapClassLoader，最后才轮到自己查找。而不同的类加载器负责扫描不同路径下的jar包，就如同加载class一样，最后会扫描所有的jar包，找到符合条件的资源文件。



```csharp
// 使用线程上下文类加载器加载资源
public static void main(String[] args) throws Exception{
    // Array.class的完整路径
    String name = "java/sql/Array.class";
    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(name);
    while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        System.out.println(url.toString());
    }
}
```





![image-20220126160740962](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126160740962.png)

![image-20220126160912653](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126160912653.png)

![image-20220126161125665](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126161125665.png)

![image-20220126161212261](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126161212261.png)

![image-20220126161425892](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126161425892.png)

![image-20220126161549048](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126161549048.png)

![image-20220126161827262](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126161827262.png)

![image-20220126162013147](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126162013147.png)

![image-20220126162124492](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220126162124492.png)

## tomcat打破双亲委派：

图灵的笔记：

[有道云笔记 (youdao.com)](https://note.youdao.com/ynoteshare/index.html?id=35faf7c95e69943cdbff4642fcfd5318&type=note&_time=1645767498771)

以Tomcat类加载为例，Tomcat 如果使用默认的双亲委派类加载机制行不行？

我们思考一下：Tomcat是个web容器， 那么它要解决什么问题： 

1. 一个web容器可能需要部署两个应用程序，不同的应用程序可能会**依赖同一个第三方类库的不同版本**，不能要求同一个类库在同一个服务器只有一份，因此要保证每个应用程序的类库都是独立的，保证相互隔离。 

2. 部署在同一个web容器中**相同的类库相同的版本可以共享**。否则，如果服务器有10个应用程序，那么要有10份相同的类库加载进虚拟机。 

3. **web容器也有自己依赖的类库，不能与应用程序的类库混淆**。基于安全考虑，应该让容器的类库和程序的类库隔离开来。 

4. web容器要支持jsp的修改，我们知道，jsp 文件最终也是要编译成class文件才能在虚拟机中运行，但程序运行后修改jsp已经是司空见惯的事情， web容器需要支持 jsp 修改后不用重启。

再看看我们的问题：**Tomcat 如果使用默认的双亲委派类加载机制行不行？** 

答案是不行的。为什么？

第一个问题，如果使用默认的类加载器机制，那么是无法加载两个相同类库的不同版本的，默认的类加器是不管你是什么版本的，只在乎你的全限定类名，并且只有一份。

第二个问题，默认的类加载器是能够实现的，因为他的职责就是保证**唯一性**。

第三个问题和第一个问题一样。

我们再看第四个问题，我们想我们要怎么实现jsp文件的热加载，jsp 文件其实也就是class文件，那么如果修改了，但类名还是一样，类加载器会直接取方法区中已经存在的，修改后的jsp是不会重新加载的。那么怎么办呢？我们可以直接卸载掉这jsp文件的类加载器，所以你应该想到了，每个jsp文件对应一个唯一的类加载器，当一个jsp文件修改了，就直接卸载这个jsp类加载器。重新创建类加载器，重新加载jsp文件。

比如：

```java
package test01;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * Created
 */
public class MyClassLoader {
    static class MyClassLoader1 extends ClassLoader{
        private String classPath;

        public MyClassLoader1(String classPath){
            this.classPath = classPath;

        }

        private byte[] loadByte(String name)throws Exception{
            name = name.replaceAll("\\.","/");
            FileInputStream fis = new FileInputStream(classPath+"/"+name+".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name,data,0,data.length);
            }catch (Exception e){
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }
        public static void main(String[] arg) throws Exception{
            loadTest("E:/jvm","com.huigu.stu.Student1","study");
            loadTest("E:/jvm2","com.huigu.stu.Student1","study");
        }

        public static void  loadTest(String classPath,String name,String methodName) throws Exception{
            //初始化自定义类加载器，会先初始化父类ClassLoader，
            // 其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
            MyClassLoader1 classLoader1 = new MyClassLoader1(classPath);
            Class clazz = classLoader1.loadClass(name);
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(methodName,null);
            method.invoke(obj,null);
            System.out.println(clazz.getClassLoader().getClass().getName());
        }


        //重写类加载方法，实现自己的加载逻辑，不委派给双亲加载


        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if(!name.startsWith("com.huigu.stu")){
                    c = this.getParent().loadClass(name);
                   // c = super.loadClass(name,resolve);
                }else{
                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();
                        c = findClass(name);
                    }
                    if (resolve) {
                        resolveClass(c);
                    }
                }
                return c;
            }
        }
    }


}
//这个Student1 class文件放在E:/jvm/com/huigu/stu下
package com.huigu.stu;

public class Student1 {
    private String name;

    public Student1() {
    }

    public void study() {
        System.out.println("第一个版本学生！");
    }
}
//这个Student1 class文件放在E:/jvm2/com/huigu/stu下
package com.huigu.stu;

public class Student1 {
    private String name;

    public Student1() {
    }

    public void study() {
        System.out.println("第二个版本学生！");
    }
}


```



这样就实现相同包名和类名的类对象可以共存与隔离，这两个包名和类名的类都相同的对象都是由不同的类加载器加载的。

  

## 自定义类加载器

```java

//定义自己新的classloader，从ClassLoader继承
private static class MyLoader extends ClassLoader {
@Override
public Class<?> loadClass(String name) throws ClassNotFoundException {
//首先去找你要load class文件，如果没找到让我父亲去load，如果找到了自己就load，我们把是不是已经加载过了这段逻辑去掉了，如果要加载同一个class是覆盖不了的，但是我直接把classloader整体干掉就行了
File f = new File("C:/work/ijprojects/JVM/out/production/JVM/" +
name.replace(".", "/").concat(".class"));
if(!f.exists()) return super.loadClass(name);
try {
InputStream is = new FileInputStream(f);
byte[] b = new byte[is.available()];
is.read(b);
return defineClass(name, b, 0, b.length);
} catch (IOException e) {
e.printStackTrace();
}
return super.loadClass(name);
}
}
//所以tomcat这么干的，他直接webapplication的整个classloader全部干掉，重新再加载一遍
public static void main(String[] args) throws Exception {
MyLoader m = new MyLoader();
Class clazz = m.loadClass("com.mashibing.jvm.Hello");
m = new MyLoader();
Class clazzNew = m.loadClass("com.mashibing.jvm.Hello");
}

```





## 类加载器和osgi

**类加载器与 OSGi** 
   OSGi 是 Java 上的动态模块系统。它为开发人员提供了面向服务和基于组件的运行环境，并提供标准的方式用来管理软件的生命周期。OSGi 已经被实现和部署在很多产品上，在开源社区也得到了广泛的支持。Eclipse 就是基于 OSGi 技术来构建的。 
   OSGi 中的每个模块（bundle）都包含 Java 包和类。模块可以声明它所依赖的需要导入（import）的其它模块的 Java 包和类（通过 Import-Package），也可以声明导出（export）自己的包和类，供其它模块使用（通过 Export-Package）。也就是说需要能够隐藏和共享一个模块中的某些 Java 包和类。这是通过 OSGi 特有的类加载器机制来实现的***\*。OSGi 中的每个模块都有对应的一个类加载器。它负责加载模块自己包含的 Java 包和类。当它需要加载 Java\****  ***\**\*核心库的类时（以 java 开头的包和类），它会代理给父类加载器（通常是启动类加载器）来完成。当它需要加载所导入的 Java 类时，它会代理给导出此\*\* \*\*Java 类的模块来完成加载。模块也可以显式的声明某些 Java 包和类，必须由父类加载器来加载。只需要设置系统属性\*\* \*\*org.osgi.framework.bootdelegation 的值即可。\*\**\*** 
   假设有两个模块 bundleA 和 bundleB，它们都有自己对应的类加载器 classLoaderA 和 classLoaderB。在 bundleA 中包含类 com.bundleA.Sample，并且该类被声明为导出的，也就是说可以被其它模块所使用的。bundleB 声明了导入  bundleA 提供的类 com.bundleA.Sample，并包含一个类 com.bundleB.NewSample 继承自 com.bundleA.Sample。在 bundleB 启动的时候，其类加载器 classLoaderB 需要加载类 com.bundleB.NewSample，进而需要加载类 com.bundleA.Sample。由于 bundleB 声明了类 com.bundleA.Sample 是导入的，classLoaderB 把加载类 com.bundleA.Sample 的工作代理给导出该类的  bundleA 的类加载器 classLoaderA。classLoaderA 在其模块内部查找类 com.bundleA.Sample 并定义它，所得到的类 com.bundleA.Sample 实例就可以被所有声明导入了此类的模块使用。对于以 java 开头的类，都是由父类加载器来加载的。如果声明了系统属性 org.osgi.framework.bootdelegation=com.example.core.*，那么对于包 com.example.core 中的类，都是由父类加载器来完成的。 
   OSGi 模块的这种类加载器结构，使得一个类的不同版本可以共存在 Java 虚拟机中，带来了很大的灵活性。不过它的这种不同，也会给开发人员带来一些麻烦，尤其当模块需要使用第三方提供的库的时候。下面提供几条比较好的建议： 

- 如果一个类库只有一个模块使用，把该类库的 jar 包放在模块中，在 Bundle-ClassPath 中指明即可。 
- 如果一个类库被多个模块共用，可以为这个类库单独的创建一个模块，把其它模块需要用到的 Java 包声明为导出的。其它模块声明导入这些类。

   　如果类库提供了 SPI 接口，并且利用线程上下文类加载器来加载 SPI 实现的 Java 类，有可能会找不到 Java 类。如果出现了  NoClassDefFoundError 异常，首先检查当前线程的上下文类加载器是否正确。通过 Thread.currentThread().getContextClassLoader() 就可以得到该类加载器。该类加载器应该是该模块对应的类加载器。如果不是的话，可以首先通过 class.getClassLoader() 
   来得到模块对应的类加载器，再通过 Thread.currentThread().setContextClassLoader()  来设置当前线程的上下文类加载器。 
      　***\*总结\**** 
      　类加载器是 Java 语言的一个创新。它使得动态安装和更新软件组件成为可能。本文详细介绍了类加载器的相关话题，包括基本概念、代理模式、线程上下文类加载器、与 Web 容器和 OSGi 的关系等。开发人员在遇到 ClassNotFoundException 和 NoClassDefFoundError  等异常的时候，应该检查抛出异常的类的类加载器和当前线程的上下文类加载器，从中可以发现问题的所在。在开发自己的类加载器的时候，需要注意与已有的类加载器组织结构的协调。

## 类的动态加载

**注意，**主类在运行过程中如果使用到其它类，会逐步加载这些类。

jar包或war包里的类不是一次性全部加载的，是使用到时才加载。

  ```java
public class TestDynamicLoad {

    static {
        System.out.println("*************load TestDynamicLoad************");
    }

    public static void main(String[] args) {
        new A();
        System.out.println("*************load test************");
        B b = null;  //B不会加载，除非这里执行 new B()
    }
}

class A {
    static {
        System.out.println("*************load A************");
    }

    public A() {
        System.out.println("*************initial A************");
    }
}

class B {
    static {
        System.out.println("*************load B************");
    }

    public B() {
        System.out.println("*************initial B************");
    }
}

运行结果：
*************load TestDynamicLoad************
*************load A************//先是静态代码块再是初始化方法
*************initial A************
*************load test************
  ```



   

# java创建对象的过程

![image-20220204203431347](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220204203431347.png)

![image-20220204203600555](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220204203600555.png)

init方法：程序中类的初始化方法

分配内存时的注意事项：

在类加载检查通过后，接下来虚拟机将为新生对象分配内存。对象所需内存的大小在类 加载完成后便可完全确定，为对象分配空间的任务等同于把 一块确定大小的内存从Java堆中划分出来。
这个步骤有两个问题：
1.如何划分内存。
2.在并发情况下， 可能出现正在给对象A分配内存，指针还没来得及修改，对象B又同时使用了原来的指针来分配内存的情况。
划分内存的方法：
**“指针碰撞”**（Bump the Pointer）(默认用指针碰撞)
如果Java堆中内存是绝对规整的，所有用过的内存都放在一边，空闲的内存放在另一边，中间放着一个指针作为分界点的指示器，那所分配内存就仅仅是把那个指针向空闲空间那边挪动一段与对象大小相等的距离。
**“空闲列表”**（Free List）
如果Java堆中的内存并不是规整的，已使用的内存和空 闲的内存相互交错，那就没有办法简单地进行指针碰撞了，虚拟机就必须维护一个列表，记 录上哪些内存块是可用的，在分配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的记录
解决并发问题的方法：
CAS（compare and swap）
虚拟机采用CAS配上失败重试的方式保证更新操作的原子性来对分配内存空间的动作进行同步处理。
本地线程分配缓冲（Thread Local Allocation Buffer,TLAB）





# 内存管理

![image-20230205164756680](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230205164756680.png)

![image-20210923192915725](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210923192915725.png)

Java内存分配
寄存器：我们无法控制。
静态域：static 定义的静态成员。
常量池：编译时被确定并保存在 .class 文件中的（final）常量值和一些文本修饰的符号引用（类和接口的全限定名，字段的名称和描述符，方法和名称和描述符）。
非 RAM 存储：硬盘等永久存储空间。
堆内存：new 创建的对象和数组，由 Java 虚拟机自动垃圾回收器管理,存取速度慢。
栈内存：基本类型的变量和对象的引用变量（堆内存空间的访问地址），速度快，但是大小与生存期必须确定，缺乏灵活性。

当在一段代码块定义一个变量时，Java 就在栈中为这个变量分配内存空间，当超过变量的作用域后，Java 会自动释放掉为该变量分配的内存空间，该内存空间可以立即被另作它用。

在堆中产生了一个数组或者对象之后，==**还可以在栈中定义一个特殊的变量，让栈中的这个变量的取值等于数组或对象在堆内存中的首地址**==，栈中的这个变量就成了数组或对象的引用变量，以后就可以在程序中使用栈中的引用变量来访问堆中的数组或者对象，引用变量就相当于是为数组或者对象起的一个名称。

程序中的字面量（literal）如直接书写的100、”hello”和常量都是放在常量池中，**常量池是方法区的一部分**。栈空间操作起来最快但是栈很小，通常大量的对象都是放在堆空间，栈和堆的大小都可以通过JVM的启动参数来进行调整，栈空间用光了会引发StackOverflowError，而堆和
常量池空间不足则会引发OutOfMemoryError。

堆：存放所有new的对象和数组，可以被所有线程共享，不会存放别的对象的引用，JVM的堆是运行时数据区，<font color="green">所有类的实例和数组都是在堆上分配内存。它在JVM启动的时候被创建。对象所占的堆内存是由自动内存管理系统也就是垃圾收集器回收。</font>堆内存是由存活和死亡的对象组成的。存活的对象是应用可以访问的，不会被垃圾回收。死亡的对象是应用不可访问尚且还没有被垃圾收集器回收掉的对象。一直到垃圾收集器把这些对象回收掉之前，他们会一直占据堆内存空间。

栈：存放基本变量类型（包含这个基本类型的具体数值），引用对象的变量（**会存放这个引用在堆里面的具体地址**）。JVM栈（JVM Stacks）,与程序计数器一样，Java虚拟机栈（Java Virtual Machine Stacks）也是线程私有的，它的生命周期与线程相同。虚拟机栈描述的是Java方法执行的内存模型：每个方法被执行的时
候都会同时创建一个栈帧（Stack Frame）用于**存储局部变量表、操作栈、动态链接、方法出口等信息**。每一个方法被调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。



Java 虚拟机将其管辖的内存大致分三个逻辑部分：方法区(Method Area)、Java 栈和Java 堆。
1、方法区是静态分配的，编译器将变量绑定在某个存储位置上，而且这些绑定不会在运行时改变。

2、Java Stack 是一个逻辑概念，特点是后进先出。一个栈的空间可能是连续的，也可能是不连续的。
**最典型的Stack 应用是方法的调用，Java 虚拟机每调用一次方法就创建一个方法帧（frame），退出该方法则对应的 方法帧被弹出(pop)**。栈中存储的数据也是运行时确定的。
3、Java 堆分配(heap allocation)意味着以随意的顺序，在运行时进行存储空间分配和收回的内存管理模型。
堆中存储的数据常常是大小、数量和生命期在编译时无法确定的。Java 对象的内存总是在heap 中分配。
我们每天都在写代码，每天都在使用JVM的内存。





## 方法区

方法区：可以被所有的线程共享，包含了所有class和static变量

方法区(Method Area)与Java堆一样，是各个线程共享的内存区域，它用于存储**已被虚拟机加载**的类信息、常量、静态变量、即时编译器编译后的代码等数据。1.8中静态变量移到堆中。**虽然Java虚拟机规范把方法区描述为堆的一个逻辑部分，但是它却有一个别名叫做Non-Heap(非堆)**，目的应该是与Java堆区分开来。**很多人都更愿意把方法区称为“永久代”(Permanent Generation**)。从jdk1.7已经开始准备“去永久代”的规划，jdk1.7的HotSpot中，已经把原本放在方法区中的静态变量、字符串常量池等移到堆内存中。

在jdk1.8中，永久代已经不存在，存储的类信息、编译后的代码数据等已经移动到了元空间(MetaSpace)中，元空间并没有处于堆内存上，而是直接占用的本地内存(NativeMemory)。

元空间的本质和永久代类似，都是对JVM规范中方法区的实现。不过元空间与永久代之间最大的区别在于：**元空间并不在虚拟机中，而是使用本地内存**。因此，默认情况下，元空间的大小仅受本地内存限制，这解决了空间不足的问题。但可以通过以下参数来指定元空间的大小：

-XX:MetaspaceSize，初始空间大小，达到该值就会触发垃圾收集进行类型卸载，同时GC会对该值进行调整：如果释放了大量的空间，就适当降低该值；如果释放了很少的空间，那么在不超过MaxMetaspaceSize时，适当提高该值。

-XX:MaxMetaspaceSize，最大空间，默认是没有限制的。

注意：如果不设置JVM将会根据一定的策略自动增加本地元内存空间。

如果你设置的元内存空间过小，你的应用程序可能得到以下错误：

java.lang.OutOfMemoryError: Metadata space

在Java7之前，HotSpot虚拟机中将GC分代收集扩展到了方法区，使用永久代来实现了方法区。这个区域的内存回收目标主要是针对常量池的回收和对类型的卸载。而在Java8中，已经彻底没有了永久代，将方法区直接放在一个与堆不相连的本地内存区域，这个区域被叫做元空间。

常量池里存储着字面量和符号引用。

符号引用包括：1.类的全限定名，2.字段名和属性，3.方法名和属性。

字符串池里的内容是在类加载完成，经过验证，准备阶段之后在堆中生成字符串对象实例，然后将该字符串对象实例的引用值存到string pool中(记住：string pool中存的是引用值而不是具体的实例对象，具体的实例对象是在堆中开辟的一块空间存放的。)。 在HotSpot VM里实现的string pool功能的是一个StringTable类，**它是一个哈希表，里面存的是驻留字符串(也就是我们常说的用双引号括起来的)的引用(而不是驻留字符串实例本身)**，也就是说在堆中的某些字符串实例被这个StringTable引用之后就等同被赋予了”驻留字符串”的身份。这个StringTable在每个HotSpot VM的实例只有一份，被所有的类共享。

1.字符串池常量池在每个VM中只有一份，存放的是字符串常量的引用值,存放在<font color="red">堆</font>中.

2.class常量池是在编译的时候每个class都有的，在编译阶段，存放的是常量的符号引用。

3.运行时常量池是在类加载完成之后，将每个class常量池中的符号引用值转存到运行时常量池中，也就是说，**每个class都有一个运行时常量池，类在解析之后，将符号引用替换成直接引用，与全局常量池中的引用值保持一致**。

其中方法区中存放的信息详细解释如下：

![image-20230206110706674](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206110706674.png)

![image-20230206110640543](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206110640543.png)

![image-20230206122042789](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206122042789.png)

![image-20230206122015974](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206122015974.png)

1.7中字符串常量池放在永久代，但是1.8移到堆中，为什么：

![image-20230206122516347](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206122516347.png)

### 方法区垃圾回收

![image-20230206124934210](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206124934210.png)

常量池中的回收很简单，只要常量没有被任何地方引用，就可以被回收

![image-20230206125335105](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206125335105.png)

### 方法区溢出

![image-20230205165720652](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230205165720652.png)

方法区：已经加载的类信息、常量、静态变量、即时编译后的代码等数据

方法区用于存放Class的相关信息，**如类名、访问修饰符、常量池、字段描述、方法描述等**。也有可能是方法区中保存的class对象没有被及时回收掉或者class信息占用的内存超过了我们配置。

方法区：包含了虚拟机可以通过反射获取到的数据，比如class和method对象，jvm运行时会用到多少空间取决于应用程序用到了多少类，full gc会回收持久代的类（如果发现有的类是废弃的类）

异常信息：==java.lang.OutOfMemoryError:metaspace==
方法区溢出也是一种常见的内存溢出异常，一个类如果要被垃圾收集器回收，判定条件是很苛刻的。在经常动态生成大量Class的应用中，要特别注意这点。
SOF（堆栈溢出StackOverflow）：
StackOverflowError  的定义：当应用程序递归太深而发生堆栈溢出时，抛出该错误。
因为栈一般默认为1-2m，一旦出现死循环或者是大量的递归调用，在不断的压栈过程中，造成栈容 量超过1m而导致溢出。
栈溢出的原因：递归调用，大量循环或死循环，全局变量是否过多，数组、List、map数据过大。

方法区的内存回收目标主要是针对常量池的回收和对类型的卸载。

方法区的垃圾收集主要回收两部分内容：废弃的常量和不再使用的类。

回收废弃常量与回收Java堆中的对象非常类似。举例：

常量池中字面量回收，假如一个字符串“java”曾经进入常量池中，但是当前系统又没有任何一个字符串对象的值是“java”，换句话说，已经没有任何字符串对象引用常量池中的“java”常量，且虚拟机中也没有其他地方引用这个字面量。如果在这时发生内存回收，而且垃圾收集器判断确有必要的话，这个“java”常量就将会被系统清理出常量池。常量池中其他类（接口）、方法、字段的符号引用也与此类似。

运行期间将数据写入常量池：

```java
// 使用StringBuilder在堆上创建字符串abc，再使用intern将其放入运行时常量池
String str = new StringBuilder("abc");
str.intern();
// 直接使用字符串字面量xyz，其被放入运行时常量池
String str2 = "xyz";

```



### 为什么把永久代改为元空间

### ![image-20220204155348091](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220204155348091.png)

只有 Hotspot才有" Permgen space",而对于其他类型的虚拟机,如 Jrockit( Oracle)、J9(IBM)井没有" Permgen space"

1、 java7之前，方法区位于永久代(PermGen)，永久代和堆相互隔离，永久代的大小在启动JVM时可以设置一个固定值，不可变；

2、 java7中，[static](https://so.csdn.net/so/search?q=static&spm=1001.2101.3001.7020)变量从永久代移到堆中；.

3、 java8中，取消永久代，方法存放于元空间(Metaspace)，元空间仍然与堆不相连，但与堆共享物理内存，逻辑上可认为在堆中

![image-20220215065807275](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220215065807275.png)

针对JDK6、」DK7、JDK8三个版本的jvM内存模型调整说明
对永久代 Permgen的说明
・永久代是方法区在 hotspot的个具体实现。通过在运行时数据区域开辟空间实现方法区。hotspot jdk7之前的永久代,比较完整
・从jdk7以后方法区就四分五裂了",不再是在单一的一个去区域内进行存储
在jdk8,移除了永久代,被 Metsspacee取代了,**且 Metsspace不在JVM堆内,放入了本地内存,元空间也就成了方法区的主要存放位置**
绝大部分Java程序员应该都见过java. lang, Outofmemoryerror; Permgen space这个异常
这里的“ Permgen space"其实指的就是方法区。不过方法区和 Permgen space"又有着本质的区別。前者是JVM的规范,而后者则是JVM规范的一种实现,并且只有 Hotspot才有" Permgen space",而对于其他类型的虚拟机,如 Jrockit( Oracle)、J9(IBM)井没有" Permgen space"。由于方法区主要存储类的相关信息,所以对于动态生成类的情況比较容易出现永久代的内存溢出。最典型的场景就是,在jsp页面比较多的情况,容易出现永久代内存溢出
==对 Metaspace元空间的说明==
1.元空间的本质和永久代类似,都是对JVM规范中方法区的实现。**元空间通过在本地内存区域开辟空间实现方法区**。元空间并不在虚拟机中,而是使用本地内存,所以默认情况下,元空间的大小仅受本地内存限制,但可以通过一些参数指定大小





方法区和堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译后的代码等数据。
「什么是永久代？它和方法区有什么关系呢？」
如果在HotSpot虚拟机上开发、部署，很多程序员都把方法区称作永久代。可以说**方法区是规范，永久代是Hotspot针对该规范进行的实现**。在Java7及以前的版本，方法区都是永久代实现的。
「什么是元空间？它和方法区有什么关系呢？」
对于Java8，HotSpots取消了永久代，取而代之的是元空间(Metaspace)。元空间使用的是本地内存，且存放的是类的元数据，==*而常量、类的静态变量转移到了堆中*==。换句话说，就是方法区还是在的，只是实现变了，从永久代变为元空间了。
**「为什么使用元空间替换了永久代？」**

永久代的大小是启动时固定好的，所以很难进行调优，比如无法确定maxpermsize设置为多少好，还可以在gc不进行暂停的情况下并发地释放类数据（因为堆外内存不是由jvm管理而是操作系统释放）

永久代的方法区，和堆使用的物理内存是连续的。

![image-20210909131451957](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210909131451957.png)

「永久代」是通过以下这两个参数配置大小的~
-XX:PremSize：设置永久代的初始大小
-XX:MaxPermSize: 设置永久代的最大值，默认是64M
对于「永久代」，如果动态生成很多class的话，就很可能出现「java.lang.OutOfMemoryError:PermGen space错误」，因为永久代空间配置有限嘛。最典型的场景是，在web开发比较多jsp页面的时候。
JDK8之后，方法区存在于元空间(Metaspace)。物理内存不再与堆连续，**而是直接存在于本地内存中，理论上机器「内存有多大，元空间就有多大」。**

可以通过以下的参数来设置元空间的大小：
-XX:MetaspaceSize，初始空间大小，达到该值就会触发垃圾收集进行类型卸载，**同时GC会对该值进行调整**：如果释放了大量的空间，就适当降低该值；如果释放了很少的空间，那么在不超过MaxMetaspaceSize时，适当提高该值。
-XX:MaxMetaspaceSize，最大空间，默认是没有限制的。
-XX:MinMetaspaceFreeRatio，在GC之后，最小的Metaspace剩余空间容量的百分比，减少为分配空间所导致的垃圾收集
-XX:MaxMetaspaceFreeRatio，在GC之后，最大的Metaspace剩余空间容量的百分比，减少为释放空间所导致的垃圾收集

元空间特点
1.类及相关的元数据的生命周期与类加載器的一致,如果GC发现某个类加载器不再存活了,オ会把相关的空间整个回收掉
2.每个类加载器有专门的存储空间
3.只进行线性分配,省掉了GC扫描及压缩的时间
4.元空间里的对象的位置是固定的

5.不会单独回收某个类

JVM内存划分调整的几个原因点分析
1.字符串存在永久代中,容易出现性能题和内存溢出
2.类及方法的信息等比較难确定其大小,因此对于永久代的大小指定比较困难,太小容易出现永久代溢出,太大则容易导致老年代溢出
3.永久代会为GC带来不必要的复杂度,并且回收效率偏低

「所以，为什么使用元空间替换永久代？」
表面上看是为了避免OOM异常。因为通常使用PermSize和MaxPermSize设置永久代的大小就决定了永久代的上限，但是不是总能知道应该设置为多大合适, 如果使用默认值很容易遇到OOM错误**。当使用元空间时，可以加载多少类的元数据就不再由MaxPermSize控制, 而由系统的实际可用空间来控制啦。**

## 程序计数器

- 程序计数器是**「程序控制流的指示器，循环，跳转，异常处理，线程的恢复等工作都需要依赖程序计数器去完成」**。程序计数器是**「线程私有」**的，它的**「生命周期是和线程保持一致」**的，我们知道，N 个核心数的 CPU 在同一时刻，最多有  N个线程同时运行，在我们真实的使用过程中可能会创建很多线程，JVM 的多线程其实是通过线程轮流切换，分配处理器执行时间来实现的。既然涉及的线程切换，所以每条线程必须有一个独立的程序计数器。

程序计数器是一块较小的内存空间，它可以看作是当前线程所**执行的字节码的行号指示器**

<font color="red">线程私有数据区域生命周期与线程相同</font>, 依赖用户线程的启动/结束 而 创建/销毁(在HotspotVM 内, 每个线程都与操作系统的本地线程直接映射, 因此这部分内存区域的存/否跟随本地线程的生/死对应)。

正在执行java 方法的话，计数器记录的是虚拟机字节码指令的地址，如
果还是Native 方法，则为空。

**程序计数器为什么是线程私有的**

程序计数器的作用：主要有两个，1.字节码解释器通过程序计数器来依次读取指令，从而实现代码的流程控制如顺序执行、选择、循环、异常处理2.多线程情况下，程序计数器记录当前线程执行的位置，从而当线程被切换回来的时候能够知道该线程上次运行到哪了

一个注意点：如果执行的是native方法，程序计数器记录的是undefined地址，只有执行java方法的时候程序计数器记录才是下一条指令的地址

所以程序计数器私有【主要是为了线程切换后能够恢复到正确的执行位置】

程序计数器是唯一一个不会出现oom的内存区域，它的生命周期随着线程的创建而创建，随着线程的结束而死亡

## 虚拟机栈和本地方法栈为什么是私有的

![image-20220129235922606](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220129235922606.png)

局部变量表主要存放了编译器可知的各种数据类型（boolean byte char int long float double short)、对象引用（不同于对象本身，可能是一个指向对象起始地址的引用指针）

栈帧也叫过程活动记录,是编译器用来实现过程/函数调用的一种数据结构。 实际上,可以简单理解为:栈帧就是存储在用户栈上的(当然内核栈同样适用)每一次函数调用涉及的相关信息的记录单元。

下面举例：

String s1 = "abc"

String s2 = new String("abc")

String s3 = new String("abc")

String s4 = new String("abc").intern()

问 s1 == s2 ,s1 == s4, s2==s3,s2 == s4的结果

考虑一下 分别为 false true false false

究竟是为什么呢

首先在类编译时，会产生4个符号引用 s1，s2，s3，s4和一个字面量“abc”,这些内容 会在类加载时解析符号引用并把字面量进入到字符串变量池中，

s1是java栈中的变量，存储的是对字符串常量池中的“abc”的引用即地址，

s2，s3会先在java堆中创建一个对象，存储对字符串常量池中的“abc”的引用，然后在java栈中的引用变量指向这个对象，

s4 intern()方法直接返回“abc”常量的引用

虚拟机栈会出现两种异常：stackoverflowerror和oom

stackoverflowerror:如果Java虚拟机栈==不允许==动态扩展，当线程请求栈的深度超过当前java虚拟机的最大深度时，就抛出stackoverflowerror

oom:如果Java虚拟机栈==允许==动态扩展，当线程请求栈的时候内存用完了无法再动态拓展了，抛出这个异常

## 解释内存中的栈（stack）、堆(heap)和静态存储区的用法。

- 虚拟机栈，其描述的就是线程内存模型，**「也可以称作线程栈」**，也是每个**「线程私有」**的，**「生命周期与线程保持一致」**。在每个方法执行的时候，jvm 都会同步创建一个栈帧去存储局部变量表，操作数栈，动态连接，方法出口等信息。一个方法的生命周期就贯彻了一个栈帧从入栈到出栈的全部过程。
- **「本地方法栈」**本地方法栈的概念很好理解，我们知道，java底层用了很多c的代码去实现，而其调用c端的方法上都会有native，代表本地方法服务，而本地方法栈就是为其服务

栈是运行时单位，代表着逻辑，内含基本数据类型和堆中对象引用，**所在区域连续，没有碎片**；堆是存储单位，代表着数据，可被多个栈共享（包括成员中基本数据类型、引用和引用对象），**所在区域不连续，会有碎片。**

通常我们定义一个基本数据类型的变量，一个对象的引用，还有就是函数调用的现场保存(?什么意思）都使用内存中的栈空间；而通过new 关键字和构造器创建的对象放在堆空间；程序中的字面量（literal）如直接书写的100、“hello”和常量都是放在静态存储区中。**栈空间操作最快但是也很小，通常大量的对象都是放在堆空间，整个内存包括硬盘上的虚拟内存都可以被当成堆空间来使用**。

🆚堆栈区别：

1. 申请方式
   stack:由系统自动分配。例如，声明在函数中一个局部变量 int b; 系统自动在栈中为b 开辟空间
   heap:需要程序员自己申请，并指明大小，在c 中malloc 函数，对于Java 需要手动new Object()的形式开辟

2. 申请后系统的响应
   stack：只要栈的剩余空间大于所申请空间，系统将为程序提供内存，否则将报异常提示栈溢出。

  heap：<font color="red">首先应该知道操作系统有一个记录空闲内存地址的链表，当系统收到程序的申请时，会遍历该链表，寻找第一个空间大于所申请空间的堆结点，然后将该结点从空闲结点链表中删除，并将该结点的空间分配给程序。</font>另外，由于找到的堆结点的大小不一定正好等于申请的大小，系统会自动的将多余的那部分重新放入空闲链表中。

3. 申请大小的限制
   stack：**栈是向低地址扩展的数据结构，是一块连续的内存的区域**。这句话的意思是栈顶的地址和栈的最大容量是系统预先规定好的，在 WINDOWS 下，栈的大小是2M（也有的说是1M，总之是一个编译时就确定的常数），如果申请的空间超过栈的剩余空间时，将提示overflow。因此，能从栈获得的空间较小。
   heap：堆是向高地址扩展的数据结构，是不连续的内存区域。这是由于系统是用链表来存储的空闲内存地址的，自然是不连续的，而链表的遍历方向是由低地址向高地址。<font color="red">堆的大小受限于计算机系统中有效的虚拟内存。由此可见，堆获得的空间比较灵活，也比较大。</font>
4. 申请效率的比较：
   stack：由系统自动分配，速度较快。但程序员是无法控制的。
   heap：由new 分配的内存，一般速度比较慢，而且容易产生内存碎片,不过用起来最方便。
5. heap 和stack 中的存储内容
   stack： 在函数调用时，第一个进栈的是主函数中后的下一条指令（函数调用语句的下一条可执行语句）的地址，然后是函数的各个参数，在大多数的C 编译器中，参数是由右往左入栈的，然后是函数中的局部变量。==注意静态变量是不入栈的==。
   当本次函数调用结束后，局部变量先出栈，然后是参数，最后栈顶指针指向最开始存的地址，也就是主函数中的下一条指令，程序由该点继续运行。
   heap：一般是在堆的头部用一个字节存放堆的大小。堆中的具体内容由程序员安排。
6. 数据结构层面的区别
   还有就是数据结构方面的堆和栈，这些都是不同的概念。**这里的堆实际上指的就是（满足堆性质的）优先队列的一种数据结构**，第1 个元素有最高的优先权；**栈实际上就是满足先进后出的性质的数学或数据结构。**
   虽然堆栈，堆栈的说法是连起来叫，但是他们还是有很大区别的，连着叫只是由于历史的原因。

7. 拓展知识（Java 中堆栈的应用）
   1). 栈(stack)与堆(heap)都是Java 用来在Ram 中存放数据的地方。与C++不同，Java 自动管理栈和堆，程序员不能直接地设置栈或堆。
   2). **栈的优势是，存取速度比堆要快，仅次于直接位于CPU 中的寄存器**。但缺点是，存在栈中的数据大小与生存期必须是确定的，缺乏灵活性。另外，栈数据可以共享，详见第3 点。堆的优势是可以动态地分配内存大小，生存期也不必事先告诉编译器，Java 的垃圾回收器会自动收走这些不再使用的数据。但缺点是，由于要在运行时动态分配内存，存取速度较慢。
   3). Java 中的数据类型有两种。
   一种是基本类型(primitive types), 共有8 种，即int, short, long, byte, float, double, boolean, char(注意，并没有string 的基本类型)。这种类型的定义是通过诸如int a = 3; long b = 255L;的形式来定义的，称为自动变量（自动变量：只在定义它们的时候才创建，在定义它们的函数返回时系统回收变量所占存储空间。对这些变量存储空间的分配和回收是由系统自动完成的。）。值得注意的是，**自动变量存的是字面值，不是类的实例，即不是类的引用，**
   这里并没有类的存在。**如int a = 3; 这里的a 是一个指向int 类型的引用，指向3 这个字面值**。这些字面值的数据，由于大小可知，生存期可知(这些字面值固定定义在某个程序块里面，程序块退出后，字段值就消失了)，出于追求速度的原因，就存在于栈中。
   另外，栈有一个很重要的特殊性，就是存在栈中的数据可以共享。假设我们同时定义int a = 3; int b = 3；
     编译器先处理int a = 3；首先它会在栈中创建一个变量为a 的引用，然后查找有没有字面值为3 的地址，没找到，就开辟一个存放3 这个字面值的地址，然后将a 指向3 的地址。接着处理int b = 3；在创建完b 的引用变量后，由于在栈中已经有3 这个字面值，便将b 直接指向3 的地址。这样，就出现了a 与b 同时均指向3 的情况。
     特别注意的是，这种字面值的引用与类对象的引用不同。假定两个类对象的引用同时指向一个对象，如果一个对象引用变量修改了这个对象的内部状态，那么另一个对象引用变量也即刻反映出这个变化。相反，通过字面值的引用来修改其值，不会导致另一个指向此字面值的引用的值也跟着改变的情况。如上例，我们定义完a 与 b 的值后，再令a=4；那么，b 不会等于4，还是等于3。在编译器内部，遇到a=4；时，它就会重新搜索栈中是否有4 的字面值，如果没有，重新开辟地址存放4 的值；如果已经有了，则直接将a 指向这个地址。因此a 值的改变不会影响到b的值。
     另一种是包装类数据，如Integer, String, Double 等将相应的基本数据类型包装起来的类。这些类数据全部存在于堆中，Java 用new()语句来显示地告诉编译器，在运行时才根据需要动态创建，因此比较灵活，但缺点是要占用更多的时间。
     4).**每个JVM的线程都有自己的私有的栈空间**，随线程创建而创建，java 的stack 存放的是frames，java 的stack和c 的不同，只是存放本地变量，返回值和调用方法，不允许直接push 和pop frames ，因为frames 可能是由heap分配的，所以java 的stack 分配的内存不需要是连续的。java 的heap是所有线程共享的，堆存放所有 runtime data ，里面是所有的对象实例和数组，heap 是JVM启动时创建。
   ==栈内存是线程私有的。 堆内存是所有线程共有的。==
     5). String 是一个特殊的包装类数据。即可以用String str = new String("abc");的形式来创建，也可以用String str = "abc"；的形式来创建(作为对比，在JDK 5.0 之前，你从未见过Integer i = 3;的表达式，因为类与字面值是不能通用的，除了String。而在JDK 5.0 中，这种表达式是可以的！因为编译器在后台进行Integer i = new Integer(3)的转换)。前者是规范的类的创建过程，即在Java 中，一切都是对象，而对象是类的实例，全部通过new()的形式来创建。那为什么在String str = "abc"；中，并没有通过new()来创建实例，是不是违反了上述原则？其实没有。<font color="red">关于String str = "abc"的内部工作。Java 内部将此语句转化为以下几个步骤：</font>
     (1)先定义一个名为str 的对String 类的对象引用变量：String str；
     (2)在栈中查找有没有存放值为"abc"的地址，如果没有，则开辟一个空间存放字面值为"abc"的地址，接着创建一个新的String 类的对象o，并将o 的字符串值指向这个地址，而且在栈中这个地址旁边记下这个引用的对象o。如果已经有了值为"abc"的地址，则查找对象o，并返回o 的地址。
     (3)将str 指向对象o 的地址。
     值得注意的是，一般String 类中字符串值都是直接存值的。但像String str = "abc"；这种场合下，其字符串值却是保存了一个指向存在栈中数据的引用！
     为了更好地说明这个问题，我们可以通过以下的几个代码进行验证。

  ```java
String str1 = "abc";
String str2 = "abc";
System.out.println(str1==str2); //true

  ```

  

  注意，我们这里并不用str1.equals(str2)；的方式，因为这将比较两个字符串的值是否相等。==号，根据JDK的说明，只有在两个引用都指向了同一个对象时才返回真值。而我们在这里要看的是，str1 与str2 是否都指向了同一个对象。
  结果说明，JVM创建了两个引用str1 和str2，但只创建了一个对象，而且两个引用都指向了这个对象。
  我们再来更进一步，将以上代码改成：

  ```java
String str1 = "abc";
String str2 = "abc";
str1 = "bcd";
System.out.println(str1 + "," + str2); //bcd, abc
System.out.println(str1==str2); //false

  ```

  这就是说，赋值的变化导致了类对象引用的变化，str1 指向了另外一个新对象！而str2 仍旧指向原来的对象。
  上例中，当我们将str1 的值改为"bcd"时，JVM 发现在栈中没有存放该值的地址，便开辟了这个地址，并创建了一个新的对象，其字符串的值指向这个地址

  事实上，String 类被设计成为不可改变(immutable)的类。如果你要改变其值，可以，但JVM 在运行时根据新值悄悄创建了一个新对象，然后将这个对象的地址返回给原来类的引用。这个创建过程虽说是完全自动进行的，但它毕竟占用了更多的时间。在对时间要求比较敏感的环境中，会带有一定的不良影响。
  再修改原来代码：

  ```java
String str1 = "abc";
String str2 = "abc";
str1 = "bcd";
String str3 = str1;
System.out.println(str3); //bcd
String str4 = "bcd";
System.out.println(str1 == str4); //true
  ```

  str3 这个对象的引用直接指向str1 所指向的对象(注意，str3 并没有创建新对象)。当str1 改完其值后，再创建一个String 的引用str4，并指向因str1 修改值而创建的新的对象。可以发现，这回str4 也没有创建新的对象，从而再次实现栈中数据的共享。

6). 数据类型包装类的值不可修改。不仅仅是String 类的值不可修改，所有的数据类型包装类都不能更改其内部的值。(❓没理解)
7). 结论与建议：
(1)我们在使用诸如String str = "abc"；的格式定义类时，总是想当然地认为，我们创建了String 类的对象str。担心陷阱！**对象可能并没有被创建！唯一可以肯定的是，指向 String 类的引用被创建了。**至于这个引用到底是否指向了一个新的对象，必须根据上下文来考虑，除非你通过new()方法来显要地创建一个新的对象。因此，更为准确的说法是，我们创建了一个指向String 类的对象的引用变量str，这个对象引用变量指向了某个值为"abc"的String 类。
清醒地认识到这一点对排除程序中难以发现的bug 是很有帮助的。
(2)使用String str = "abc"；的方式，可以在一定程度上提高程序的运行速度，因为JVM会自动根据栈中数据的实际情况来决定是否有必要创建新对象。而对于String str = new String("abc")；的代码，则一概在堆中创建新对象，而不管其字符串值是否相等，是否有必要创建新对象，从而加重了程序的负担。这个思想应该是享元模式的思想，但JDK 的内部在这里实现是否应用了这个模式，不得而知。

(4)由于String 类的immutable 性质，当String 变量需要经常变换其值时，应该考虑使用StringBuffer 类，==如果java 不能成功分配heap 的空间，将抛出OutOfMemoryError。==，栈空间用光了会引发 StackOverflowError

通常我们定义一个基本数据类型的变量，一个对象的引用，还有就是函数调用的现场保存都使用 JVM 中的栈空间；而通过new 关键字和构造器创建的对象则放在堆空间，堆是垃圾收集器管理的主要区域，==由于现在的垃圾收集器都采用分代收集算法，所以堆空间还可以细分为新生代和老生代，再具体一点可以分为 Eden、Survivor（又可分为
From Survivor 和 To Survivor）、Tenured==；方法区和堆都是各个线程共享的内存区域，用于存储已经被JVM 加载的类信息、常量、静态变量、JIT 编译器编译后的代码等数据**；程序中的字面量（literal）如直接书写的 100、"hello"**和常量都是放在常量池中，常量池是方法区的一部分。栈空间操作起来最快但是栈很小，通常大量的对象都是放在堆空间，栈和堆的大小都可以通过 JVM 的启动参数来进行调整



String str = new String(“hello”);（为什么栈比堆快？）
上面的语句中str 放在栈上，用new 创建出来的字符串对象放在堆上，而“hello”这个字面量放在静态存储区。（怎么理解str放在栈上？）

其他解释：

第一种: 在方法中声明的变量，即该变量是局部变量，每当程序调用方法时，系统都会为该方法建立一个方法栈，**其所在方法中声明的变量就放在方法栈中，**当方法结束系统会释放方法栈，其对应在该方法中声明的变量随着栈的销毁而结束，这就局部变量只能在方法中有效的原因

(1)当声明是基本类型的变量的时，其变量名及值(变量名及值是两个概念)是放在方法栈中

(2)当声明的是引用变量时**，所声明的变量(该变量实际上是在方法中存储的是内存地址值)是放在方法的栈中，该变量所指向的对象是放在堆类存中的**。

第二种 在类中声明的变量是成员变量(全局变量),放在堆中

1 声明的是基本类型的变量,其变量名及其值放在堆内存中

2. 声明的是引用类型时，其声明的变量仍然会存储一个内存地址值，该内存地址值指向所引用的对象。引用变量名和对应的对象仍然存储在相应的堆中

记住一个原则即可：方法体中的引用变量和基本类型的变量都在栈上，其他都在堆上

## 运行时栈帧

运行时栈帧结构 Java虚拟机以方法作为最基本的执行单元，“栈帧”（Stack Frame）则是用于支持虚拟机进行方法调用和方法执行背后的数据结构，它也是虚拟机运行时数据区中的虚拟机栈（Virtual Machine Stack）的栈元素。栈帧存储了方法的**局部变量表、操作数栈、动态连接和方法返回地址**等信息，如果读者认真阅读过第6章，应该能从Class文件格式的方法表中找到以上大多数概念的静态对照物。每一个方法从调用开始至执行结束的过程，都对应着一个栈帧在虚拟机栈里面从入栈到出栈的过程。 每一个栈帧都包括了局部变量表、操作数栈、动态连接、方法返回地址和一些额外的附加信息。在编译Java程序源码的时候，栈帧中需要多大的局部变量表，需要多深的操作数栈就已经被分析计算出来，并且写入到方法表的Code属性之中。换言之，一个栈帧需要分配多少内存，并不会受到程序运行期变量数据的影响，而仅仅取决于程序源码和具体的虚拟机实现的栈内存布局形式。 一个线程中的方法调用链可能会很长，以Java程序的角度来看，同一时刻、同一条线程里面，在调用堆栈的所有方法都同时处于执行状态。而对于执行引擎来讲，在活动线程中，只有位于栈顶的方法才是在运行的，只有位于栈顶的栈帧才是生效的，其被称为“当前栈帧”（Current Stack Frame），与这个栈帧所关联的方法被称为“当前方法”（Current Method）。执行引擎所运行的所有字节码指令都只针对当前栈帧进行操作，在概念模型上，典型的栈帧结构如图8-1所示。 图8-1所示的就是虚拟机栈和栈帧的总体结构，接下来，我们将会详细了解栈帧中的局部变量表、操作数栈、动态连接、方法返回地址等各个部分的作用和数据结构。

![image-20230202163255643](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230202163255643.png)

![image-20230203122030044](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203122030044.png)

![image-20230203122048665](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203122048665.png)

这也是为什么static方法中不能使用this,因为static方法中没有把this放在它的局部变量表里面

操作数栈：

![image-20230203140631677](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203140631677.png)

![image-20230203140703141](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203140703141.png)

![image-20230203140721266](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203140721266.png)

动态链接：

![image-20230203140736384](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230203140736384.png)

也就是这个栈帧需要知道当前自己是哪个方法，这个方法就存储在运行时常量池中，所以动态链接就是指向了这个方法名

方法返回地址：用这个地址可以知道调用了一个方法之后下一条该执行哪条指令

本地方法栈可以实现成固定大小或者可拓展的内存大小。

![image-20230204094432902](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230204094432902.png)

## 堆区

**在jvm的堆内存中有三个区域：**

1、年轻代：用于存放新产生的对象。

2、老年代：用于存放被长期引用的对象。

3、持久带：用于存放Class，method元信息（1.8之后改为元空间）。

 **内存分配策略**

**1.3.1、 优先在Eden区分配** 

在大多数情况下, 对象在新生代Eden区中分配, 当Eden区没有足够空间分配时, VM发起一次Minor GC, 将Eden区和其中一块Survivor区内尚存活的对象放入另一块Survivor区域, 如果在Minor GC期间发现新生代存活对象无法放入空闲的Survivor区, 则会通过空间分配担保机制使对象提前进入老年代(空间分配担保见下).

**1.3.2、大对象直接进入老年代**

Serial和ParNew两款收集器提供了-XX:PretenureSizeThreshold的参数, 令大于该值的大对象直接在老年代分配, 这样做的目的是避免在Eden区和Survivor区之间产生大量的内存复制(大对象一般指 需要大量连续内存的Java对象, 如很长的字符串和数组), 因此大对象容易导致还有不少空闲内存就提前触发GC以获取足够的连续空间.

**1.3.3、长期存活对象进入老年区**

 如果对象在Eden出生并经过第一次Minor GC后仍然存活，并且能被Survivor容纳的话，将被移动到Survivor空间中，并将对象年龄设为1，对象在Survivor区中每熬过一次 Minor GC，年龄就增加1，当它的年龄增加到一定程度(默认为15)_时，就会被晋升到老年代中。

**1.3.4、对象年龄动态判定**

如果在 Survivor空间中相同年龄所有对象大小的综合大于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代

**1.3.5、空间分配担保**

 在发生Minor GC之前，虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果这个条件成立，那么Minor GC可以确保是安全的。如果不成立，则虚拟机会查看HandlePromotionFailure设置值是否允许担保失败。如果允许，那么会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小，如果大于，将尝试着进行一次Minor GC，尽管这次Minor GC是有风险的，如果担保失败则会进行一次Full GC；如果小于，或者HandlePromotionFailure设置不允许冒险，那这时也要改为进行一次Full GC。

HotSpot默认是开启空间分配担保的。

![image-20230204113253274](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230204113253274.png)

**堆内存为什么对属性重排序**

在64位操作系统中，内存分配颗粒为8byte，不可以跳跃分配，上一次内存分配中不够下一个属性使用，就需要对齐填充，填充会浪费内存，所以要挪动属性的先后顺序，尽可能充分利用空间 

### [新生代Eden与两个Survivor区的解释](https://www.cnblogs.com/zhuyeshen/p/12622086.html)

**1.为什么会有年轻代**

我们先来屡屡，为什么需要把堆分代？不分代不能完成他所做的事情么？其实不分代完全可以，**分代的唯一理由就是优化GC性能。**你先想想，如果没有分代，那我们所有的对象都在一块，GC的时候我们要找到哪些对象没用，这样就会对堆的所有区域进行扫描。而我们的很多对象都是朝生夕死的，如果分代的话，我们把新创建的对象放到某一地方，当GC的时候先把这块存“朝生夕死”对象的区域进行回收，这样就会腾出很大的空间出来。

 

**2.年轻代中的GC**

  HotSpot JVM把年轻代分为了三部分：1个Eden区和2个Survivor区（分别叫from和to）。默认比例为8：1,为啥默认会是这个比例，接下来我们会聊到。一般情况下，新创建的对象都会被分配到Eden区(一些大对象特殊处理),这些对象经过第一次Minor GC后，如果仍然存活，将会被移到Survivor区。对象在Survivor区中每熬过一次Minor GC，年龄就会增加1岁，当它的年龄增加到一定程度时，就会被移动到年老代中。

因为年轻代中的对象基本都是朝生夕死的(80%以上)，所以在年轻代的垃圾回收算法使用的是复制算法，复制算法的基本思想就是将内存分为两块，每次只用其中一块，当这一块内存用完，就将还活着的对象复制到另外一块上面。复制算法不会产生内存碎片。

在GC开始的时候，对象只会存在于Eden区和名为“From”的Survivor区，Survivor区“To”是空的。紧接着进行GC，Eden区中所有存活的对象都会被复制到“To”，而在“From”区中，仍存活的对象会根据他们的年龄值来决定去向。年龄达到一定值(年龄阈值，可以通过-XX:MaxTenuringThreshold来设置)的对象会被移动到年老代中，没有达到阈值的对象会被复制到“To”区域。经过这次GC后，Eden区和From区已经被清空。这个时候，“From”和“To”会交换他们的角色，也就是新的“To”就是上次GC前的“From”，新的“From”就是上次GC前的“To”。不管怎样，都会保证名为To的Survivor区域是空的。Minor GC会一直重复这样的过程，直到“To”区被填满，“To”区被填满之后，会将所有对象移动到年老代中。

**3.一个对象的这一辈子**

我是一个普通的java对象，我出生在Eden区，在Eden区我还看到和我长的很像的小兄弟，我们在Eden区中玩了挺长时间。有一天Eden区中的人实在是太多了，我就被迫去了Survivor区的“From”区，自从去了Survivor区，我就开始漂了，有时候在Survivor的“From”区，有时候在Survivor的“To”区，居无定所。直到我18岁的时候，爸爸说我成人了，该去社会上闯闯了。于是我就去了年老代那边，年老代里，人很多，并且年龄都挺大的，我在这里也认识了很多人。在年老代里，我生活了20年(每次GC加一岁)，然后被回收。

**4.有关年轻代的JVM参数**

1)-XX:NewSize和-XX:MaxNewSize

用于设置年轻代的大小，建议设为整个堆大小的1/3或者1/4,两个值设为一样大。

2)-XX:SurvivorRatio

用于设置Eden和其中一个Survivor的比值，这个值也比较重要。

3)-XX:+PrintTenuringDistribution

这个参数用于显示每次Minor GC时Survivor区中各个年龄段的对象的大小。

4).-XX:InitialTenuringThreshol和-XX:MaxTenuringThreshold

用于设置晋升到老年代的对象年龄的最小值和最大值，每个对象在坚持过一次Minor GC之后，年龄就加1。

## 为什么要把堆和栈区分出来呢？栈中不是也可以存储数据吗？

1. 从软件设计的角度看，栈代表了处理逻辑，而堆代表了数据。这样分开，使得处理逻辑更为清晰。分而治之的思想。这种隔离、模块化的思想在软件设计的方方面面都有体现。
2. 堆与栈的分离，使得堆中的内容可以被多个栈共享（也可以理解为多个线程访问同一个对象）。这种共享的收益是很多的。一方面这种共享提供了一种有效的数据交互方式(如：共享内存)，另一方面，堆中的共享常量和缓存可以被所有栈访问，节省了空间。
3. 栈因为运行时的需要，比如保存系统运行的上下文，需要进行地址段的划分。由于栈只能向上增长，因此就会限制住栈存储内容的能力。而堆不同，堆中的对象是可以根据需要动态增长的，因此栈和堆的拆分，使得动态增长成为可能，相应栈中只需记录堆中的一个地址即可。
4. 面向对象就是堆和栈的完美结合。其实，面向对象方式的程序与以前结构化的程序在执行上没有任何区别。但是，面向对象的引入，使得对待问题的思考方式发生了改变，而更接近于自然方式的思考。当我们把对象拆开，你会发现，对象的属性其实就是数据，存放在堆中；而对象的行为（方法），就是运行逻辑，放在栈中。我们在编写对象的时候，其实即编写了数据结构，也编写的处理数据的逻辑。不得不承认，面向对象的设计，确实很美。

## 为什么不把基本类型放堆中呢？

（不考虑逃逸分析的情况下)堆中存的是对象。栈中存的是基本数据类型和堆中对象的引用。一个对象的大小是不可估计的，或者说是可以动态变化的，但是在栈中，一个对象只对应了一个4btye的引用（堆栈分离的好处：））。

因为其占用的空间一般是1~8个字节——需要空间比较少，而且因为是基本类型，所以不会出现动态增长的情况——长度固定，因此栈中存储就够了，如果把他存在堆中是没有什么意义的（还会浪费空间，后面说明）。可以这么说，基本类型和对象的引用都是存放在栈中，而且都是几个
字节的一个数，因此在程序运行时，他们的处理方式是统一的。但是基本类型、对象引用和对象本身就有所区别了，因为一个是栈中的数据一个是堆中的数据。最常见的一个问题就是，Java中参数传递时的问题。

## 内存区域之栈

与程序计数器一样，Java虚拟机栈（Java Virtual Machine Stack）也是线程私有的，它的生命周期与线程相同。虚拟机栈描述的是Java方法执行的线程内存模型：每个方法被执行的时候，Java虚拟机都会同步创建一个栈帧[1]（Stack Frame）用于存储局部变量表、操作数栈、动态连接、方法出口等信息。每一个方法被调用直至执行完毕的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程

经常有人把Java内存区域笼统地划分为堆内存（Heap）和栈内存（Stack），这种划分方式直接继承自传统的C、C++程序的内存布局结构，在Java语言里就显得有些粗糙了，实际的内存区域划分要比
这更复杂。不过这种划分方式的流行也间接说明了程序员最关注的、与对象内存分配关系最密切的区域是“堆”和“栈”两块。其中，“堆”在稍后笔者会专门讲述，而“栈”通常就是指这里讲的虚拟机栈，或
者更多的情况下只是指虚拟机栈中局部变量表部分。
局部变量表存放了编译期可知的各种Java虚拟机基本数据类型（boolean、byte、char、short、int、float、long、double）、对象引用（reference类型，它并不等同于对象本身，可能是一个指向对象起始地址的引用指针，也可能是指向一个代表对象的句柄或者其他与此对象相关的位置）和returnAddress类型（指向了一条字节码指令的地址）。
这些数据类型在局部变量表中的存储空间以局部变量槽（Slot）来表示，其中64位长度的long和double类型的数据会占用两个变量槽，其余的数据类型只占用一个。局部变量表所需的内存空间在编
译期间完成分配，**当进入一个方法时，这个方法需要在栈帧中分配多大的局部变量空间是完全确定的，在方法运行期间不会改变局部变量表的大小。请读者注意，这里说的“大小”是指变量槽的数量，**
虚拟机真正使用多大的内存空间（譬如按照1个变量槽占用32个比特、64个比特，或者更多）来实现一个变量槽，这是完全由具体的虚拟机实现自行决定的事情。

本地方法栈（Native Method Stacks）与虚拟机栈所发挥的作用是非常相似的，其区别只是虚拟机栈为虚拟机执行Java方法（也就是字节码）服务，而本地方法栈则是为虚拟机使用到的本地（Native）
方法服务。

![image-20210811185500222](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185500222.png)



## 说一说对象的栈上分配吧?/逃逸分析

如果所有对象都分配在堆中那么会给 GC 带来许多不必要的压力,比如有些对象的生命周期只是在当前线程中，为了减少临时对象在堆内分配的数量，就**「可以在在栈上分配」**，随着线程的消亡而消亡。 当然栈上空间必须充足,否则也无法分配，在判断是否能分配到栈上的另一条件就是要经过逃逸分析，JVM通过逃逸分析确定该对象不会被外部访问。如果不会逃逸可以将该对象在栈上分配内存，这样该对象所占用的内存空间就可以随栈帧出栈而销毁，就减轻了垃圾回收的压力。
**对象逃逸分析：就是分析对象动态作用域，当一个对象在方法中被定义后，它可能被外部方法所引用**，例如作为调用参数传递到其他地方中。

栈上分配

  2.1 本质：Java虚拟机提供的一项优化技术

  2.2 基本思想： 将线程私有的对象打散分配在栈上

  2.3 优点：

​    2.3.1 可以在函数调用结束后自行销毁对象，不需要垃圾回收器的介入，有效避免垃圾回收带来的负面影响

​    2.3.2 栈上分配速度快，提高系统性能

  2.4 局限性： 栈空间小，对于大对象无法实现栈上分配

  2.4 技术基础： 逃逸分析

​    2.4.1 逃逸分析的目的： 判断对象的作用域是否超出函数体

例如：

```java
//u只在函数内部生效，不是逃逸对象
//当函数调用结束，会自行销毁对象u
public void createUser(){
    User u = new User();
    u.setId(2);
    u.setName("JVM");
}

public User test1() {//很显然test1方法中的user对象被返回了，这个对象的作用域范围不确定
2 User user = new User();
3 user.setId(1);
4 user.setName("zhuge");
5 //TODO 保存到数据库
6 return user;
7 }
```

**标量替换**：通过逃逸分析确定该对象不会被外部访问，并且对象可以被进一步分解时**，JVM不会创建该对象，而是将该对象成员变量分解若干个被这个方法使用的成员变量所代替**，这些代替的成员变量在栈帧或寄存器上分配空间，这样就不会因为没有一大块连续空间导致对象内存不够分配。开启标量替换参数(-XX:+EliminateAllocations)，JDK7之后默认开启。
**标量与聚合量**：标量即不可被进一步分解的量，而JAVA的基本数据类型就是标量（如：int，long等基本数据类型以及reference类型等），标量的对立就是可以被进一步分解的量，而这种量称之为聚合量。而在JAVA中对象就是可以被进一步分解的聚合量。

栈上分配示例：

 * ```java
   /**
     栈上分配，标量替换
    3 * 代码调用了1亿次alloc()，如果是分配到堆上，大概需要1GB以上堆空间，如果堆空间小于该值，必然会触发GC。
   *
     使用如下参数不会发生GC
     ‐Xmx15m ‐Xms15m ‐XX:+DoEscapeAnalysis ‐XX:+PrintGC ‐XX:+EliminateAllocations
   * 使用如下参数都会发生大量GC
   * ‐Xmx15m ‐Xms15m ‐XX:‐DoEscapeAnalysis ‐XX:+PrintGC ‐XX:+EliminateAllocations
   * ‐Xmx15m ‐Xms15m ‐XX:+DoEscapeAnalysis ‐XX:+PrintGC ‐XX:‐EliminateAllocations
   */
     public class AllotOnStack {
     
   public static void main(String[] args) {
   long start = System.currentTimeMillis();
   for (int i = 0; i < 100000000; i++) {
     alloc();
     }
   long end = System.currentTimeMillis();
   System.out.println(end ‐ start);
     }
     
   private static void alloc() {
   User user = new User();
   user.setId(1);
   user.setName("zhuge");
   ```

**「逃逸分析(Escape Analysis)」**:

- 简单来讲就是：Java Hotspot 虚拟机判断这个新对象是否只会被当前线程引用，并且决定是否能够在 Java 堆上分配内存。

关于逃逸分析的概念，可以参考[对象和数组并不是都在堆上分配内存的。](http://www.hollischuang.com/archives/2398)一文，这里简单回顾一下：
逃逸分析的基本行为就是分析对象动态作用域：当一个对象在方法中被定义后，它可能被外部方法所引用，例如作为调用参数传递到其他地方中，称为方法逃逸。
例如以下代码：

  ```
```java
public static StringBuffer craeteStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb;
}

public static String createStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb.toString();
}

  ```

第一段代码中的sb就逃逸了，而第二段代码中的sb就没有逃逸。

使用逃逸分析，编译器可以对代码做如下优化：

一、同步省略。如果一个对象被发现只能从一个线程被访问到，那么对于这个对象的操作可以不考虑同步，即锁消除。

消除锁是虚拟机另外一种锁的优化，这种优化更彻底，Java虚拟机在JIT编译时(可以简单理解为当某段代码即将第一次被执行时进行编译，又称即时编译)，通过对运行上下文的扫描，**去除不可能存在共享资源竞争的锁**，通过这种方式消除没有必要的锁，可以节省毫无意义的请求锁时间，如下StringBuffer的append是一个同步方法，但是在add方法中的StringBuffer属于一个局部变量，并且不会被其他线程所使用，因此StringBuffer不可能存在共享资源竞争的情景，JVM会自动将其锁消除。**锁消除的依据是逃逸分析的数据支持**。

二、将堆分配转化为栈分配。如果一个对象在子程序中被分配，要使指向该对象的指针永远不会逃逸，对象可能是栈分配的候选，而不是堆分配。

三、分离对象或标量替换。有的对象可能不需要作为一个连续的内存结构存在也可以被访问到，那么对象的部分（或全部）可以不存储在内存，而是存储在CPU寄存器中。

在Java代码运行时，通过JVM参数可指定是否开启逃逸分析，

-XX:+DoEscapeAnalysis ： 表示开启逃逸分析

-XX:-DoEscapeAnalysis ： 表示关闭逃逸分析 从jdk 1.7开始已经默认开始逃逸分析，如需关闭，需要指定-XX:-DoEscapeAnalysis

同步省略
在动态编译同步块的时候，JIT编译器可以借助逃逸分析来判断同步块所使用的锁对象是否只能够被一个线程访问而没有被发布到其他线程。

如果同步块所使用的锁对象通过这种分析被证实只能够被一个线程访问，那么JIT编译器在编译这个同步块的时候就会取消对这部分代码的同步。这个取消同步的过程就叫同步省略，也叫锁消除。

如以下代码：

```java
public void f() {
    Object hollis = new Object();
    synchronized(hollis) {
        System.out.println(hollis);
    }
}
```

代码中对hollis这个对象进行加锁，但是hollis对象的生命周期只在f()方法中，并不会被其他线程所访问到，所以在JIT编译阶段就会被优化掉。优化成：

```java
public void f() {
    Object hollis = new Object();
    System.out.println(hollis);
}
```

所以，在使用synchronized的时候，如果JIT经过逃逸分析之后发现并无线程安全问题的话，就会做锁消除。

标量替换
标量（Scalar）是指一个无法再分解成更小的数据的数据。Java中的原始数据类型就是标量。相对的，那些还可以分解的数据叫做聚合量（Aggregate），Java中的对象就是聚合量，因为他可以分解成其他聚合量和标量。

在JIT阶段，如果经过逃逸分析，**发现一个对象不会被外界访问的话，那么经过JIT优化，就会把这个对象拆解成若干个其中包含的若干个成员变量来代替。这个过程就是标量替换。**

```java
public static void main(String[] args) {
   alloc();
}

private static void alloc() {
   Point point = new Point（1,2）;
   System.out.println("point.x="+point.x+"; point.y="+point.y);
}
class Point{
    private int x;
    private int y;
}
```

以上代码中，point对象并没有逃逸出alloc方法，并且point对象是可以拆解成标量的。那么，JIT就会不会直接创建Point对象，而是直接使用两个标量int x ，int y来替代Point对象。

以上代码，经过标量替换后，就会变成：

private static void alloc() {
   int x = 1;
   int y = 2;
   System.out.println("point.x="+x+"; point.y="+y);
}

可以看到，Point这个聚合量经过逃逸分析后，发现他并没有逃逸，就被替换成两个聚合量了。那么标量替换有什么好处呢？就是可以大大减少堆内存的占用。因为一旦不需要创建对象了，那么就不再需要分配堆内存了。

标量替换为栈上分配提供了很好的基础。

栈上分配
在Java虚拟机中，对象是在Java堆中分配内存的，这是一个普遍的常识。但是，有一种特殊情况，那就是如果经过逃逸分析后发现，一个对象并没有逃逸出方法的话，那么就可能被优化成栈上分配。这样就无需在堆上分配内存，也无须进行垃圾回收了。

关于栈上分配的详细介绍，可以参考对象和数组并不是都在堆上分配内存的。。

这里，还是要简单说一下，其实在现有的虚拟机中，并没有真正的实现栈上分配，在对象和数组并不是都在堆上分配内存的。中我们的例子中，对象没有在堆上分配，其实是标量替换实现的。

逃逸分析并不成熟
关于逃逸分析的论文在1999年就已经发表了，但直到JDK 1.6才有实现，而且这项技术到如今也并不是十分成熟的。

其根本原因就是无法保证逃逸分析的性能消耗一定能高于他的消耗。虽然经过逃逸分析可以做标量替换、栈上分配、和锁消除。但是逃逸分析自身也是需要进行一系列复杂的分析的，这其实也是一个相对耗时的过程。

一个极端的例子，就是经过逃逸分析之后，发现没有一个对象是不逃逸的。那这个逃逸分析的过程就白白浪费掉了。



## JIT即时编译

【JIT的出现】

在Java的编译体系中，一个Java的源代码文件变成计算机可执行的机器指令的过程中，需要经过两段编译：

第一段编译，指前端编译器把*.java文件转换成*.class文件(字节码文件)。编译器产品可以是JDK的Javac、Eclipse JDT中的增量式编译器。

第二编译阶段，JVM 通过解释字节码将其翻译成对应的机器指令，逐条读入，逐条解释翻译。很显然，经过解释执行，其执行速度必然会比可执行的二进制字节码程序慢很多。这就是传统的JVM的解释器（Interpreter）的功能。为了解决这种效率问题，引入了JIT（即时编译器，Just In Time Compiler）技术。



Java程序最初是通过解释器进行解释执行的**，当虚拟机发现某个方法或代码块运行的特别频繁时，会把这些代码认定为“热点代码**”（Hot Spot Code）。为了提高热点代码的执行效率，在运行时，虚拟机会把这些代码编译成本地平台相关的机器码，并进行各种层次的优化，完成这个任务的编译器称为即时编译器（JIT编译器，不是Java虚拟机内必须的部分）。有一部分优化的目的就是减少内存堆分配压力，其中JIT优化中一种重要的技术叫做逃逸分析。

**解释器是一条一条的解释执行源语言**。比如php，postscritp，javascript就是典型的解释性语言。（直接执行）

**编译器是把源代码整个编译成目标代码，执行时不再需要编译器，直接在支持目标代码的平台上运行**，这样执行效率比解释执行快很多。比如C语言代码被编译成二进制代码（exe程序），在windows平台上执行

　当程序需要快速启动和执行的时候，解释器可以首先发挥作用，省去编译的时间，立即执行。在程序运行后，随着时间的推移，编译器逐渐发挥作用，越来越多的代码被编译成本地代码，可以获取更好的执行效率。解释器比较节约内存，编译器的效率比较高。解释器还可以作为编译器激进优化操作的“逃生门”，当激进优化的假设不成立，就退回到解释状态继续执行。

1、动态编译（dynamic compilation）指的是“在运行时进行编译”；与之相对的是事前编译（ahead-of-time compilation，简称AOT），也叫静态编译（static compilation）。

2、JIT编译（just-in-time compilation）狭义来说是当某段代码即将第一次被执行时进行编译，因而叫“即时编译”。JIT编译是动态编译的一种特例。JIT编译一词后来被泛化，时常与动态编译等价；但要注意广义与狭义的JIT编译所指的区别。

3、自适应动态编译（adaptive dynamic compilation）也是一种动态编译，但它通常执行的时机比JIT编译迟，先让程序“以某种式”先运行起来，收集一些信息之后再做动态编译。这样的编译可以更加优化。

原文链接：https://blog.csdn.net/sunxianghuang/article/details/52094859

在部分商用虚拟机中（如HotSpot），Java程序最初是通过解释器（Interpreter）进行解释执行的，当虚拟机发现某个方法或代码块的运行特别频繁时，就会把这些代码认定为“热点代码”。为了提高热点代码的执行效率，在运行时，虚拟机将会把这些代码编译成与本地平台相关的机器码，并进行各种层次的优化，完成这个任务的编译器称为即时编译器（Just In Time Compiler，下文统称JIT编译器）。

即时编译器并不是虚拟机必须的部分，Java虚拟机规范并没有规定Java虚拟机内必须要有即时编译器存在，更没有限定或指导即时编译器应该如何去实现。但是，即时编译器编译性能的好坏、代码优化程度的高低却是衡量一款商用虚拟机优秀与否的最关键的指标之一，它也是虚拟机中最核心且最能体现虚拟机技术水平的部分。

由于Java虚拟机规范并没有具体的约束规则去限制即使编译器应该如何实现，所以这部分功能完全是与虚拟机具体实现相关的内容，如无特殊说明，我们提到的编译器、即时编译器都是指Hotspot虚拟机内的即时编译器，虚拟机也是特指HotSpot虚拟机。
==为什么HotSpot虚拟机要使用解释器与编译器并存的架构？==
尽管并不是所有的Java虚拟机都采用解释器与编译器并存的架构，但许多主流的商用虚拟机（如HotSpot），都同时包含解释器和编译器。解释器与编译器两者各有优势：

当程序需要迅速启动和执行的时候，解释器可以首先发挥作用，省去编译的时间，立即执行。在程序运行后，随着时间的推移，编译器逐渐发挥作用，把越来越多的代码编译成本地代码之后，可以获取更高的执行效率。

当程序运行环境中内存资源限制较大（如部分嵌入式系统中），可以使用解释器执行节约内存，反之可以使用编译执行来提升效率。此外，如果编译后出现“罕见陷阱”，可以通过逆优化退回到解释执行。
解释器的执行，抽象的看是这样的：

**输入的代码 -> [ 解释器 解释执行 ] -> 执行结果**

而要JIT编译然后再执行的话，抽象的看则是：

**输入的代码 -> [ 编译器 编译 ] -> 编译后的代码 -> [ 执行 ] -> 执行结果**

说JIT比解释快，其实说的是“执行编译后的代码”比“解释器解释执行”要快，并不是说“编译”这个动作比“解释”这个动作快。

JIT编译再怎么快，至少也比解释执行一次略慢一些，而要得到最后的执行结果还得再经过一个“执行编译后的代码”的过程。
所以，对“只执行一次”的代码而言，解释执行其实总是比JIT编译执行要快。

怎么算是“只执行一次的代码”呢？粗略说，下面两个条件同时满足时就是严格的“只执行一次”

1、只被调用一次，例如类的构造器（class initializer，<clinit>()）

2、没有循环

**对只执行一次的代码做JIT编译再执行，可以说是得不偿失**。

对只执行少量次数的代码，JIT编译带来的执行速度的提升也未必能抵消掉最初编译带来的开销。

只有对频繁执行的代码，JIT编译才能保证有正面的收益。

编译的空间开销
对一般的Java方法而言，编译后代码的大小相对于字节码的大小，膨胀比达到10x是很正常的。同上面说的时间开销一样，这里的空间开销也是，只有对执行频繁的代码才值得编译，如果把所有代码都编译则会显著增加代码所占空间，导致“代码爆炸”。

这也就解释了为什么有些JVM会选择不总是做JIT编译，而是选择用解释器+JIT编译器的混合执行引擎。
程序中的代码只有是热点代码时，才会编译为本地代码，那么什么是热点代码呢？

运行过程中会被即时编译器编译的“热点代码”有两类：

1、被多次调用的方法。

2、被多次执行的循环体。

对第一种情况，由于是方法调用触发的编译，因此编译器会以整个方法作为编译对象，即标准的JIT编译方式。**后一种，虽然是循环体触发的编译动作，但编译器依然按照整个方法**（而不是单独的循环体）作为编译对象。这种编译方式称为栈上替换（On Stack Replacement，简称为OSR编译）。

判断一段代码是不是热点代码，是不是需要触发即时编译，这样的行为称为热点探测（Hot Spot Detection），目前有两种方法：

1. 基于采样的热点探测：采用这样的方法的虚拟机会周期性的检查各个线程的栈顶，如果发现某个（或某些）方法经常出现在栈顶，那这个方法就是“热点方法”。其好处就是实现简单、高效，还可以很容易的获取方法调用关系（将调用栈展开即可），缺点是很难精确的确认一个方法的热度，容易因为受到线程阻塞或别的外界因素的影响。
2. 基于计数器的热点探测：为每一个方法（甚至是代码块）建立计数器，统计方法的执行次数，超过一定的阈值就认为是“热点方法”。缺点是实现起来更麻烦，需要为每个方法建立并维护计数器，并且不能直接获取到方法的调用关系，优点是它的统计结果相对来说更加精确和严谨。

HotSpot虚拟机使用第二种，它为每个方法准备了两类计数器：方法调用计数器（Invocation Counter）和回边计数器（Back Edge Counter，用于统计一个方法中循环体代码执行的次数）。

## Java中的参数传递时传值呢？还是传引用？

要说明这个问题，先要明确两点：

1. 不要试图与C进行类比，Java中没有指针的概念
2. 程序运行永远都是在栈中进行的，因而参数传递时，只存在传递基本类型和对象引用的问题。不会直接传对象本身。
明确以上两点后。Java在方法调用传递参数时，因为没有指针，所以它都是进行传值调用（这点可以参考C的传值调用）。因此，很多书里面都说Java是进行传值调用，这点没有问题，而且也简化的C中复杂性。
但是传引用的错觉是如何造成的呢？在运行栈中，基本类型和引用的处理是一样的，都是传值，所以，如果是传引用的方法调用，也同时可以理解为“传引用值”的传值调用，即引用的处理跟基本类型是完全一样的**。但是当进入被调用方法时，被传递的这个引用的值，被程序解释（或者查找）到堆中的对象，这个时候才对应到真正的对象**。如果此时进行修改，修改的是引用对应的对象，而不是引用本身，即：修改的是堆中的数据。所以这个修改是可以保持的了。
对象，从某种意义上说，是由基本类型组成的。可以把一个对象看作为一棵树，对象的属性如果还是对象，则还是一颗树（即非叶子节点），基本类型则为树的叶子节点。程序参数传递时，被传递的值本身都是不能进行修改的，但是，如果这个值是一个非叶子节点（即一个对象引用），则可以修改这个节点下面的所有内容。
堆和栈中，栈是程序运行最根本的东西。程序运行可以没有堆，但是不能没有栈。而堆是为栈进行数据存储服务，说白了堆就是一块共享的内存。不过，正是因为堆和栈的分离的思想，才使得Java的垃圾回收成为可能。
Java中，栈的大小通过-Xss来设置，当栈中存储数据比较多时，需要适当调大这个值，否则会出现java.lang.StackOverflowError异常。常见的出现这个异常的是无法返回的递归，因为此时栈中保存的信息都是方法返回的记录点

## 常量池是什么

[java字面量和符号引用_彻底弄懂java中的常量池_从夏的博客-CSDN博客](https://blog.csdn.net/weixin_29357243/article/details/112181473?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_utm_term~default-0.pc_relevant_default&spm=1001.2101.3001.4242.1&utm_relevant_index=3)

JVM常量池主要分为**Class文件常量池、运行时常量池，全局字符串常量池，以及基本类型包装类对象常量池**。

**Java中的常量池**，实际上分为两种形态：**静态常量池**和**运行时常量池。**

  1）所谓**静态常量池**，即*.class文件中的常量池，class文件中的常量池不仅仅包含字符串(数字)字面量，还包含类、方法的信息，占用class文件绝大部分空间。

  2）而**运行时常量池**，则是jvm虚拟机在完成类装载操作后，将class文件中的常量池载入到内存中，并保存在方法区中,运行时常量池中的内容除了是静态常量池中的内容外还将静态常量池中的符号引用变为直接引用

运行时常量池是方法区的一部分。CLass文件中除了有**类的版本、字段、方法、接口等描述信息**外，还有一项信息是**常量池**，用于存放**编译期**生成的**各种字面量和符号引用**，这部分内容将在类加载后进入方法区的**运行时常量池**中存放

运行时常量池相对于CLass文件常量池的另外一个重要特征是具备**动态性**，Java语言并不要求常量一定只有编译期才能产生，也就是并非预置入CLass文件中常量池的内容才能进入方法区运行时常量池，运行期间也可能将新的常量放入池中，这种特性被开发人员利用比较多的就是String类的intern()方法（在jdk1.8后将string常量池放到了堆中）  

==常量池的好处==

常量池是为了避免频繁的创建和销毁对象而影响系统性能，其实现了对象的共享。例如字符串常量池，在编译阶段就把所有的字符串文字放到一个常量池中。

（1）节省内存空间：常量池中所有相同的字符串常量被合并，只占用一个空间。

（2）节省运行时间：比较字符串时，\==比equals()快。对于两个引用变量，只用==判断引用是否相等，也就可以判断实际值是否相等

class文件是一组以字节为单位的二进制数据流，在java代码的编译期间，我们编写的java文件就被编译为.class文件格式的二进制数据存放在磁盘中，其中就包括class文件常量池。 class文件中存在常量池(非运行时常量池)，其在编译阶段就已经确定，jvm规范对class文件结构有着严格的规范，必须符合此规范的class文件才能被jvm任何和装载。

 class文件常量池(静态常量池）主要存放两大常量：**字面量和符号引用**。

\1) 字面量： 字面量接近java语言层面的常量概念，主要包括：

- **文本字符串**，也就是我们经常申明的： public String s = "abc";中的"abc"
- 用final修饰的成员变量，包括静态变量、实例变量和局部变量

**2) 符号引用**符号引用主要设涉及编译原理方面的概念，包括下面三类常量:

- 类和接口的全限定名，也就是java/lang/String;这样，将类名中原来的"."替换为"/"得到的，主要用于在运行时解析得到类的直接引用
- 字段的名称和描述符（private protected)，字段也就是类或者接口中声明的变量，包括类级别变量和实例级的变量
- 方法中的名称和描述符，也即参数类型+返回值

## 运行时常量池溢出

jdk1.7之后的版本的jvm已经将运行时常量池从方法区中移了出来，在java堆中开辟了一块区域存放运行时常量池

异常信息：java.lang.OutOfMemoryError:PermGenspace
如果要向运行时常量池中添加内容，**最简单的做法就是使用String.intern()这个Native方法**。该方法 的作用是：如果池中已经包含一个等于此String的字符串，则返回代表池中这个字符串的String对  象；否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用。由于常量 池分配在方法区内

![image-20230206104552998](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230206104552998.png)







## 直接内存

直接内存并不是虚拟机运行时数据库的一部分，也不是虚拟机规范中定义的内存区域，但是这部分内存也被频繁地使用，而且也可能导致oom异常

 直接内存的使用: 常见于NIO,用于数据缓冲区 分配回收的代价较高,但是速度很快 不属于JVM内存回收管理

jdk1.4中新加入的nio类引入了一种基于通道与缓存区的io方式（channel与buffer）它可以直接使用native函数库直接分配堆外内存，然后通过一个存储在java堆中的directbytebuffer对象作为对这块内存的引用进行操作，这样可以在一些场景中显著提高性能，避免了/java堆与native堆之间来回复制数据

普通内存：当`java`程序需要读取文件时，首先会在java堆内存中`new`一个缓冲区，然后系统内存从磁盘中读取文件，再然后在将系统缓冲区中的[字节流](https://so.csdn.net/so/search?q=字节流&spm=1001.2101.3001.7020)复制到java堆内存的缓冲区中，然后在由java程序调用。

这样做有一个缺点，就是需要开启两块内存，效率会很低。

直接内存：当java程序使用直接内存时，首先java程序在系统内存中分配一块直接内存块，这一内存块是系统内存和java堆内存可以共享的，那么系统内存读取到的磁盘文件就可以直接由java堆内存使用，这样就省去了复制的操作，大大节约了时间开销。

但是，由于`ByteBuffer`对象的回收，是遵循`JVM`回收机制的，也就是说，得达到一定的回收条件才会回收`ByteBuffer`对象。那么直接内存也不会被回收，这样就会导致内存不足。所以建议使用手动调用`Unsafe`的方法释放直接内存。

代码：

```java
private static class Deallocator
        implements Runnable
    {

        private static Unsafe unsafe = Unsafe.getUnsafe();

        private long address;
        private long size;
        private int capacity;

        private Deallocator(long address, long size, int capacity) {
            assert (address != 0);
            this.address = address;
            this.size = size;
            this.capacity = capacity;
        }

        public void run() {
            if (address == 0) {
                // Paranoia
                return;
            }
            unsafe.freeMemory(address);//主动释放直接内存
            address = 0;
            Bits.unreserveMemory(size, capacity);
        }

    }

```



# java反射中，Class.forName和classloader的区别

java中class.forName()和classLoader都可用来对类进行加载。
class.forName()前者除了将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块。
而classLoader只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容,只有在newInstance才会去执行static块。
Class.forName(name, initialize, loader)带参函数也可控制是否加载static块。并且只有调用了newInstance()方法采用调用构造函数，创建类的对象

看下Class.forName()源码

```java
	//Class.forName(String className)  这是1.8的源码
    public static Class<?> forName(String className) throws ClassNotFoundException {
        Class<?> caller = Reflection.getCallerClass();
        return forName0(className, true, ClassLoader.getClassLoader(caller), caller);
    }
	//注意第二个参数，是指Class被loading后是不是必须被初始化。 不初始化就是不执行static的代码即静态代码
```

1.父类【静态成员】和【静态代码块】，按在代码中出现的顺序依次执行。
2.子类【静态成员】和【静态代码块】，按在代码中出现的顺序依次执行。
3.父类的【普通成员变量被普通成员方法赋值】和【普通代码块】，按在代码中出现的顺序依次执行。







# 



# 线上问题排查的一般流程

![image-20211222214447317](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211222214447317.png)





# 如何排查 OOM 的问题？

- 1.增加两个参数 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heapdump.hprof，当 OOM 发生时自动 dump 堆内存信息到指定目录；
- 2.同时 jstat 查看监控 JVM 的内存和 GC 情况，先观察问题大概出在什么区域；
- 3.使用工具载入到 dump 文件，分析大对象的占用情况





# 如何实现 StrackOverflowError?

public static void main(String[] args) { eat();
}
public static void eat () { eat();
}
如何设置直接内存容量？
通过-XX:MaxDirectMemorySize指定，如果不指定，则默认与Java堆的最大值一样。



# jvm内存模型jmm是如何分配的

我们常说的JVM内存结构指的是JVM的内存分区；而Java内存模式是一种虚拟机规范。Java 的内存模型定义了程序中各个变量的访问规则，即在虚拟机中将变量存储到内存和从内存中取出这样的底层细节。(此处的变量包括实例字段、静态字段和构成数组对象的元素，但是不包括局部变量和方法参数，因为这些是线程私有的，不会被共享，所以不存在竞争问题。)

Java虚拟机规范中定义了Java内存模型（Java Memory Model，JMM），用于屏蔽掉各种硬件和操作系统的内存访问差异，以实现让Java程序在各种平台下都能达到一致的并发效果，**JMM规范了Java虚拟机与计算机内存是如何协同工作的**：规定了一个线程如何和何时可以看到由其他线程修改过后的共享变量的值，以及在必须时如何同步的访问共享变量。

从抽象的角度来看，JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（main memory）中，每个线程都有一个私有的本地内存（local memory），本地内存中存储了该线程以读/写共享变量的副本。本地内存是JMM的一个抽象概念，并不真实存在。它涵盖了缓存，写缓冲区，寄存器以及其他的硬件和编译器优化。

原始的Java内存模型存在一些不足，因此Java内存模型在Java1.5时被重新修订。这个版本的Java内存模型在Java8中仍然在使用。

![image-20211130231539285](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211130231539285.png)

![img](https://img-blog.csdnimg.cn/20200703093756336.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpdV81NDI0NDk2MzA=,size_16,color_FFFFFF,t_70)

JDK 1.8中则把永久代给完全删除了，取而代之的是MetaSpace，将类元数据放到了本地内存中，将常量池和静态变量放到了Java堆里，HotSpot VM将会为类的元数据明确的分配与释放本地内存，在这种架构下，类元数据就突破了-XX:MaxPermSize的限制，所以此配置已经失效，现在可以使用更多的本地内存。这样一定程度上解决了原来在运行时生成大量的类，从而经常Full GC的问题——如运行时使用反射、代理等。
方法区是JVM 的规范，永久代Perm是Hotspot 对这种规范的实现
为什么Jdk1.8要把方法区从JVM里（永久代）移到直接内存（元空间）?
字符串存在永久代中，容易出现性能问题和内存溢出。
类及方法的信息等比较难确定其大小，因此对于永久代的大小指定比较困难。
永久代会为 GC 带来不必要的复杂度，并且回收效率偏低。

**JMM模型下的线程间通信：**

线程间通信必须要经过主内存。

如下，如果线程A与线程B之间要通信的话，必须要经历下面2个步骤：

1）线程A把本地内存A中更新过的共享变量刷新到主内存中去。

2）线程B到主内存中去读取线程A之前已更新过的共享变量。

关于主内存与工作内存之间的具体交互协议，即一个变量如何从主内存拷贝到工作内存、如何从工作内存同步到主内存之间的实现细节，Java内存模型定义了以下八种操作来完成（以下：八种操作有原子性）

- **lock（锁定）**：作用于主内存的变量，把一个变量标识为一条线程独占状态。
- **unlock（解锁）**：作用于主内存变量，把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定。
- **read（读取）**：作用于主内存变量，把一个变量值从主内存传输到线程的工作内存中，以便随后的load动作使用
- **load（载入）**：作用于工作内存的变量，它把read操作从主内存中得到的变量值放入工作内存的变量副本中。
- **use（使用）**：作用于工作内存的变量，把工作内存中的一个变量值传递给执行引擎，每当虚拟机遇到一个需要使用变量的值的字节码指令时将会执行这个操作。
- **assign（赋值）**：作用于工作内存的变量，它把一个从执行引擎接收到的值赋值给工作内存的变量，每当虚拟机遇到一个给变量赋值的字节码指令时执行这个操作。
- **store（存储）**：作用于工作内存的变量，把工作内存中的一个变量的值传送到主内存中，以便随后的write的操作。
- **write（写入）**：作用于主内存的变量，它把store操作从工作内存中一个变量的值传送到主内存的变量中。

Java内存模型还规定了在执行上述八种基本操作时，必须满足如下规则：

- 如果要把一个变量从主内存中复制到工作内存，就需要按顺寻地执行read和load操作， 如果把变量从工作内存中同步回主内存中，就要按顺序地执行store和write操作。但Java内存模型只要求上述操作必须按顺序执行，而没有保证必须是连续执行。
- 不允许read和load、store和write操作之一单独出现
- 不允许一个线程丢弃它的最近assign的操作，即变量在工作内存中改变了之后必须同步到主内存中。
- 不允许一个线程无原因地（没有发生过任何assign操作）把数据从工作内存同步回主内存中。
- 一个新的变量只能在主内存中诞生，不允许在工作内存中直接使用一个未被初始化（load或assign）的变量。即就是对一个变量实施use和store操作之前，必须先执行过了assign和load操作。
- 一个变量在同一时刻只允许一条线程对其进行lock操作，但lock操作可以被同一条线程重复执行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才会被解锁。lock和unlock必须成对出现
- 如果对一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎使用这个变量前需要重新执行load或assign操作初始化变量的值
- 如果一个变量事先没有被lock操作锁定，则不允许对它执行unlock操作；也不允许去unlock一个被其他线程锁定的变量。
- 对一个变量执行unlock操作之前，必须先把此变量同步到主内存中（执行store和write操作）。

# User user = new User() 申请了哪些内存



User user = new User() 做了什么操作，申请了哪些内存？
1. new User(); 创建一个User对象，内存分配在堆上
2. User user; 创建一个引用，内存分配在栈上
3. = 将User对象地址赋值给引用

# [Object o = new Object()占多少个字节？-对象的内存布局](https://www.cnblogs.com/dijia478/p/14677243.html)



# 

这个问题有坑，有两种回答

第一种解释：

object实例对象，占16个字节。

第二种解释：

`Object o`：普通对象指针（ordinary object pointer），占4个字节。
`new Object()`：object实例对象，占16个字节。

`Object o`占用大小分为两种情况：

- 未开启压缩对象指针

  8字节

- 开启压缩对象指针（默认是开启的）

  4字节

`new Object()`占用大小分为两种情况：

- 未开启压缩类指针

  8字节(Mark Word) + 8字节(Klass Pointer) = 16字节

- 开启压缩类指针（默认是开启的）

  8字节(Mark Word) + 4字节(Klass Pointer) + 4字节(Padding) = 16字节

所以一共占：4+16=20个字节。

这个答案只适用于现在一般默认情况。



准确的说，只适用于**HotSpot实现**的**64位**虚拟机，默认开启了**压缩类指针**和**压缩普通对象指针**的情况下。



![image-20220303213414218](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220303213414218.png)

`-XX:+UseCompressedClassPointers`：使用**压缩类指针**

`-XX:+UseCompressedOops`：使用**压缩普通对象指针**

可以看到，这两个配置是默认开启的。

注意：*32位HotSpot VM是不支持`UseCompressedOops`参数的，只有64位HotSpot VM才支持*

验证结论：

```xml
<dependency>
	<groupId>org.openjdk.jol</groupId>
	<artifactId>jol-core</artifactId>
	<version>0.14</version>
</dependency>
   public static void main(String[] args) {
        Test01 t = new Test01();
        System.out.println(ClassLayout.parseInstance(t).toPrintable());
    }
```

jol-core 常用的三个方法：

- `ClassLayout.parseInstance(object).toPrintable()`：查看对象内部信息.
- `GraphLayout.parseInstance(object).toPrintable()`：查看对象外部信息，包括引用的对象.
- `GraphLayout.parseInstance(object).totalSize()`：查看对象总大小.

![image-20220303214113560](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220303214113560.png)

# jvm压缩指针

一 什么是oop
　　OOP = “ordinary object pointer” 普通对象指针。 启用CompressOops后，会压缩的对象：

　　　　1. 每个Class的属性指针（静态成员变量）

　　　　2. 每个对象的属性指针

　　　　3. 普通对象数组的每个元素指针

　　当然，压缩也不是万能的，针对一些特殊类型的指针，JVM是不会优化的。 比如指向 PermGen的Class 对象指针，本地变量，堆栈元素，入参，返回值，NULL指针不会被压缩

二 为什么使用压缩指针
      在堆中，32位的对象引用（指针）占4个字节，而64位的对象引用占8个字节。也就是说，64位的对象引用大小是32位的2倍。64位JVM在支持更大堆的同时，由于对象引用变大却带来了性能问题：

增加了GC开销：64位对象引用需要占用更多的堆空间，留给其他数据的空间将会减少，从而加快了GC的发生，更频繁的进行GC。
降低CPU缓存命中率：64位对象引用增大了，CPU能缓存的oop将会更少，从而降低了CPU缓存的效率。
为了能够保持32位的性能，oop必须保留32位。那么，如何用32位oop来引用更大的堆内存呢？答案是——压缩指针（CompressedOops）。

三 CompressedOops的原理
32位内最多可以表示4GB，64位地址分为堆的基地址+偏移量，当堆内存<32GB时候，在压缩过程中，把偏移量/8后保存到32位地址。在解压再把32位地址放大8倍，所以启用CompressedOops的条件是堆内存要在4GB*8=32GB以内。

所以压缩指针之所以能改善性能，是因为它通过对齐（Alignment），还有偏移量（Offset）将64位指针压缩成32位。换言之，性能提高是因为使用了更小更节省空间的压缩指针而不是完整长度的64位指针，CPU缓存使用率得到改善，应用程序也能执行得更快。

四 零基压缩优化(Zero Based Compressd Oops)
零基压缩是针对压解压动作的进一步优化。 它通过改变正常指针的随机地址分配特性，强制堆地址从零开始分配（需要OS支持），进一步提高了压解压效率。要启用零基压缩，你分配给JVM的内存大小必须控制在4G以上，32G以下。如果GC堆大小在4G以下，直接砍掉高32位，

避免了编码解码过程 如果GC堆大小在4G以上32G以下，则启用UseCompressedOop 如果GC堆大小大于32G，压指失效，使用原来的64位（所以说服务器内存太大不好…）
————————————————
版权声明：本文为CSDN博主「good well」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_33223299/article/details/106354718

# java的对象由什么组成

Java对象存储在堆（Heap）内存。那么一个Java对象到底包含什么呢？概括起来分为对象头、对象体和对齐字节

![image-20211221013107694](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211221013107694.png)



![image-20211214212431043](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211214212431043.png)







对象的几个部分的作用：

1.对象头中的Mark Word（标记字）主要用来表示对象的线程锁状态（sychronized)，另外还可以用来配合GC、存放该对象的hashCode；

- **「MarkWord」**:包含一系列的标记位，比如轻量级锁的标记位，偏向锁标记位,gc记录信息等等。
- **「ClassPointer」**:用来指向对象对应的 Class 对象（其对应的元数据对象）的内存地址。 在 32 位系统占 4 字节，在 64 位系统中占 8 字节。

2.class Word是一个指向方法区中Class信息的指针，意味着该对象可随时知道自己是哪个Class的实例；

3.数组长度也是占用64位（8字节）的空间，这是可选的，只有当本对象是一个数组对象时才会有这个部分；

4.对象体是用于保存对象属性和值的主体部分，占用内存空间取决于对象的属性数量和类型；

5.对齐字是为了减少堆内存的碎片空间（不一定准确）。

了解了对象的总体结构，接下来深入地了解对象头的三个部分。

###  一、Mark Word（标记字）

![image-20211214212544447](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211214212544447.png)

以上是Java对象处于5种不同状态时，Mark Word中64个位的表现形式，上面每一行代表对象处于某种状态时的样子。其中各部分的含义如下：

lock:2位的锁状态标记位，由于希望用尽可能少的二进制位表示尽可能多的信息，所以设置了lock标记。该标记的值不同，整个Mark Word表示的含义不同。biased_lock和lock一起，表达的锁状态含义如下：

![img](https://img-blog.csdnimg.cn/20190115142040348.png)

biased_lock：对象是否启用偏向锁标记，只占1个二进制位。为1时表示对象启用偏向锁，为0时表示对象没有偏向锁。lock和biased_lock共同表示对象处于什么锁状态。

age：4位的Java对象年龄。在GC中，如果对象在Survivor区复制一次，年龄增加1。当对象达到设定的阈值时，将会晋升到老年代。默认情况下，并行GC的年龄阈值为15，并发GC的年龄阈值为6。由于age只有4位，所以最大值为15，这就是-XX:MaxTenuringThreshold选项最大值为15的原因。

identity_hashcode：31位的对象标识hashCode，采用延迟加载技术。调用方法System.identityHashCode()计算，并会将结果写到该对象头中。当对象加锁后（偏向、轻量级、重量级），MarkWord的字节没有足够的空间保存hashCode，因此该值会移动到管程Monitor中。

thread：持有偏向锁的线程ID。

epoch：偏向锁的时间戳。

ptr_to_lock_record：轻量级锁状态下，指向栈中锁记录的指针。

ptr_to_heavyweight_monitor：重量级锁状态下，指向对象监视器Monitor的指针。

 我们通常说的通过synchronized实现的同步锁，真实名称叫做重量级锁。但是重量级锁会造成线程排队（串行执行），且会使CPU在用户态和核心态之间频繁切换，所以代价高、效率低。为了提高效率，不会一开始就使用重量级锁，JVM在内部会根据需要，按如下步骤进行锁的升级：

​    1.初期锁对象刚创建时，还没有任何线程来竞争，对象的Mark Word是下图的第一种情形，这偏向锁标识位是0，锁状态01，说明该对象处于无锁状态（无线程竞争它）。

​    2.当有一个线程来竞争锁时，先用偏向锁，表示锁对象偏爱这个线程，这个线程要执行这个锁关联的任何代码，不需要再做任何检查和切换，这种竞争不激烈的情况下，效率非常高。这时Mark Word会记录自己偏爱的线程的ID，把该线程当做自己的熟人。如下图第二种情形。

​    3.当有两个线程开始竞争这个锁对象，情况发生变化了，不再是偏向（独占）锁了，锁会升级为轻量级锁，两个线程公平竞争，哪个线程先占有锁对象并执行代码，锁对象的Mark Word就执行哪个线程的栈帧中的锁记录。如下图第三种情形。

​    4.如果竞争的这个锁对象的线程更多，导致了更多的切换和等待，JVM会把该锁对象的锁升级为重量级锁，这个就叫做同步锁，这个锁对象Mark Word再次发生变化，会指向一个监视器对象，这个监视器对象用集合的形式，来登记和管理排队的线程。如下图第四种情形。
![img](https://img-blog.csdnimg.cn/20190111091608949.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpdWR1bl9jb29s,size_16,color_FFFFFF,t_70)

### 二、Klass Word（类指针） 

这一部分用于存储对象的类型指针，该指针指向它的类元数据，JVM通过这个指针确定对象是哪个类的实例。该指针的位长度为JVM的一个字大小，即32位的JVM为32位，64位的JVM为64位。

如果应用的对象过多，使用64位的指针将浪费大量内存，统计而言，64位的JVM将会比32位的JVM多耗费50%的内存。为了节约内存可以使用选项+UseCompressedOops开启指针压缩，其中，oop即ordinary object pointer普通对象指针。开启该选项后，下列指针将压缩至32位：

每个Class的属性指针（即静态变量） 每个对象的属性指针（即对象变量） 普通对象数组的每个元素指针 当然，也不是所有的指针都会压缩，一些特殊类型的指针JVM不会优化，比如指向PermGen的Class对象指针(JDK8中指向元空间的Class对象指针)、本地变量、堆栈元素、入参、返回值和NULL指针等。 

### 三、数组长度

 如果对象是一个数组，那么对象头还需要有额外的空间用于存储数组的长度，这部分数据的长度也随着JVM架构的不同而不同：32位的JVM上，长度为32位；64位JVM则为64位。64位JVM如果开启+UseCompressedOops选项，该区域长度也将由64位压缩至32位。

### 四、实际数据和对齐填充



- **「3.Instance data」**: 对象实际数据，对象实际数据包括了对象的所有成员变量，其大小由各个成员变量的大小决定。 (这里不包括静态成员变量，因为其是在方法区维护的)
- **「4.Padding」**:Java 对象占用空间是 8 字节对齐的，即所有 Java 对象占用 bytes 数必须是 8 的倍数,是因为当我们从磁盘中取一个数据时，不会说我想取一个字节就是一个字节，都是按照一块儿一块儿来取的，这一块大小是 8 个字节，所以为了完整，padding 的作用就是补充字节，**「保证对象是 8 字节的整数倍」**。

填充数据

填充数据不是必须存在的，仅仅是为了字节对齐。 由于HotSpot VM的自动内存管理系统要求对象起始地址必须是8字节的整数倍，换句话说，就是对象的大小必须是8字节的整数倍。而对象头部分正好是8字节的倍数（1倍或者2倍），因此，当对象实例数据部分没有对齐时，就需要通过对齐填充来补全。

为什么要对齐数据？

字段内存对齐的其中一个原因，**是让字段只出现在同一CPU的缓存行中**。 如果字段不是对齐的，那么就有可能出现跨缓存行的字段。也就是说，该字段的读取可能需要替换两个缓存行，而该字段的存储也会同时污染两个缓存行。这两种情况对程序的执行效率而言都是不利的。**其实对其填充的最终目的是为了计算机高效寻址。**







-------------------------------------------------------------------------------------------



在 Java 中，你每定义⼀个 java class 实体都会产⽣⼀个 Class 对象。也就是说，当我们编写⼀个类，编译完成后，在⽣成的 .class ⽂件中，就会产⽣⼀个 Class 对象，这个 Class 对象⽤于表示这个类的类型信息。

Class类的对象表示JVM中一个类或者接口，每个java对象被加载到jvm中都会表现为一个Class类型的对象，java中的数组也被映射为Class对象，所有元素类型相同且维数相同的数组都共享一个class对象，通过Class对象可以获取类或者接口中的任何信息，比如：类名、类中声明的泛型信息、类的修饰符、类的父类信息、类的接口信息、类中的任何方法信息、类中任何字段信息等等。

Class 中没有公共的构造器，也就是说 Class 对象不能被实例化。下⾯来简单看⼀下Class 类都包括了哪些⽅法:

# 反射常用的方法



Field[] getFields()

这个方法会返回当前类的以及其所有父类、父类的父类中所有public类型的字段。

Field[] getDeclaredFields()

这个方法会返回当前类中所有字段（和修饰符无关），也就说不管这个字段是public还是private或者是protected，都会返回，有一点需要注意，只返回自己内部定义的字段，不包含其父类中的，这点需要注意，和getFields是有区别的。

Method[] getMethods()

这个方法会返回当前类的以及其所有父类的、父类的父类的、自己实现的接口、父接口继承的接口中的所有public类型的方法，需要注意一下，接口中的方法默认都是public类型的，接口中的方法public修饰符是可以省略的。

Method[] getDeclaredMethods()

返回当前类中定义的所有方法，不管这个方法修饰符是什么类型的，注意只包含自己内部定义的方法，不包含当前类的父类或者其实现的接口中定义的。

Type getGenericSuperclass()

返回父类的类型信息，如果父类是泛型类型，会返回超类中泛型的详细信息，这个方法比较关键，后面会有详细案例。

TypeVariable<Class<T>>[] getTypeParameters()

Class类继承了`java.lang.reflect.GenericDeclaration`接口，上面这个方法是在GenericDeclaration接口中定义的，Class类中实现了这个接口，用于返回当前类中声明的泛型变量参数列表。



java 是一种类型安全的语言，它有四类称为安全沙箱机制的安全机制来保证语言的安全性，这四类安全沙箱分别是：
1） 类加载体系
2） .class 文件检验器





类加载过程：



▶▶▶▶1.加载
加载过程主要完成三件事情

（1）通过类的全限定名来获取 定义此类的二进制字节流
（2）将这个类字节流代表的静态存储结构转为方法区的 运行时数据结构
（3）在堆中生成一个代表此类的java.lang.Class对象，作为访问方法区这些数据结构的入口

将类的.class文件中的二进制数据读到内存中，将其放在运时数据区的方法区内，然后在堆区创建一个java.lang.Class对象，用来封装类在方法区内的数据结构。

　　加载.class文件的方式：

- 从本地系统上直接加载。
- 通过网络下载.class文件。
- 从zip，jar等归档文件中加载.class文件。
- 将java源文件动态编译为.class文件

2. 验证

▶▶▶▶2.连接
（1）校 验：其中一项看字节码的数据是否以“魔数cafe”以及当前的JVM的运行JDK版本是否可以运行。向下兼容，反过来不行。(魔数cafe是什么)
（2）准备：给成员变量（类变量/静态变量给默认值），把常量（final）等值在方法区的常量池准备好
（3）解析：理 解为把类中的对应类型名换成该类型的class对象的地址
String --》String类型对应的Class地址

▶▶▶▶3.初始化\<clinit>，以下两个部分
（1）静态变量的显式初始化代码， 赋值代码
（2）静态代码块哪些操作会导致类的初始化？这句话的意思是，类的加载不一定会发生类初始化。大多数时候都会初始化
（1）main方法所 在的类在加载时，直接初始化。
（2）new一个类的对象
（3）调用该类的静态变量（final常量除外）和静态方法
（4）使用java.lang.reflect包的方法对类进行反射调用
（5）初始化一个类，如果其父类没有被初始化，则会先初始化其父类
==哪些操作不会导致类的初始化？==
（1）引用静态常量时（final）不 会触发此类的初始化
（2）当访问一个静态域时，只有真正声明这个域的类才 会初始化，换句话说，**通过子类访问父类的静态域时，只会初始化父类，不会初始化子类**（静态域是什么）
（3）通过数组定义类引用，不会触发类初始化

▶▶▶▶类加载器
1.类加载器是负责加载类的对象。
2.每个 Class 对象都包含一个对定 义它的 ClassLoader 的引用。
共有四种类加载器：
（1）引导类加载器： 用C++编写的，是JVM自带的类装载器，负责Java平台核心库，用来装载核心类库。该加载器无法直接获取
（2）扩展类加载器：负责jre/lib/ext目录 下的jar包或 –D java.ext.dirs 指定目录下的jar包装入工作库
（3）应用程 序类加载器：负责java –classpath 或 –D java.class.path所指的目录下的类与jar包装入工作 ，是最常用的加载器
（4）自定义类加载器（一般两种情况会用到）：
双亲委派机制
字节文件需要加密解密
加载特定目录下的类

**双亲委派机制:**

当“应用程序类加载器"接到一个加载任务时
(1)先搜素内存中是否已经加載过了,如果加载 过了,就可以找到对应的C1ass对象,那么就不加载了
(2)如 果没有找到,把这个任务先提交给 parent",父加载器接到任务时,也是重复(1)(2)
3)直到传给了根加载器,如果根加载器可以加载,就完成了,如果不能加戟,往回传,依次每 个加載器尝试在自己负责的路径下搜素,如果找到就直接返回Class对象,如果一直回传到“应用程序类加载器”,还是没有找到就会报ClassnotFoundException

为什么用这种机制：

为了安全，防止你写一个核心类。
每一个加载器只负责自己路径下的东西。
类加载器的作用
1、最主要的作用 :加载类
2、辅助的作用:可以用它 来加载“类路径下”的资源文件
例如:bin中src下文件ー->bin目录下Properties.Propertie s类表示了一个持久的属性集, Properties可保存在流中或从中加载属性列表中每个键



![image-20210811175027826](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811175027826.png)

1，BootstrapClassLoader

用于加载JAVA核心类库，也就是环境变量的%JRE_HOME%\lib下的rt.jar、resources.jar、charsets.jar等。

2，Extention ClassLoader

扩展类加载器，加载环境变量%JRE_HOME%\lib\ext目录下的class文件

3，AppclassLoader

对应加载的应用程序classpath目录下的所有jar和class等，通过在JVM启动命令中的-classpath参数指定路径，可以指定绝对路径、相对路径、环境变量等，举例：java –classpath %CLASSPATH%





## Java程序是如何执行的

其实不论是在开发工具中运行还是在 Tomcat 中运行，
Java 程序的执行流程基本都是相同的，它的执行流程如下：
先把 Java 代码编译成字节码，也就是把 .java 类型的文件编译成 .class 类型的文件。这个过程的大致执行流程：**Java 源代码 -> 词法分析器 -> 语法分析器 -> 语义分析器 -> 字符码生成器 ->**最终生成字节码**，其中任何一个节点执行失败就会造成编译失败；
把 class 文件放置到 Java 虚拟机，这个虚拟机通常指的是 Oracle 官方自带的 Hotspot JVM；
Java 虚拟机使用类加载器（Class Loader）装载 class 文件；
类加载完成之后，会进行字节码效验，字节码效验通过之后 JVM 解释器会把字节码翻译成机器码交由操作系统执行。但不是所有代码都是解释执行的，JVM 对此做了优化，比如，**以Hotspot 虚拟机来说，它本身提供了 JIT（Just In Time）也就是我们通常所说的动态编译器，它能够在运行时将热点代码编译为机器码**，这个时候字节码就变成了编译执行。Java 程序执行流程图如下：

![image-20211127144829405](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211127144829405.png)



编译型语言：

编译型语言最大的优势之一就是其执行速度。用C/C++编写的程序运行速度要比用Java编写的相同程序快30%-70%。
编译型程序比解释型程序消耗的内存更少。
不利的一面——编译器比解释器要难写得多。
编译器在调试程序时提供不了多少帮助——有多少次在你的C语言代码中遇到一个“空指针异常”时，需要花费好几个小时来明确错误到底在代码中的什么位置。
**可执行的编译型代码要比相同的解释型代码大许多。例如，C/C++的.exe文件要比同样功能的Java的.class文件大很多**。
编译型程序是面向特定平台的因而是平台依赖的。
编译型程序不支持代码中实现安全性——例如，一个编译型的程序可以访问内存的任何区域，并且可以对你的PC做它想做的任何事情（大部分病毒是使用编译型语言编写的）
由于松散的安全性和平台依赖性，编译型语言不太适合开发因特网或者基于Web的应用。
解释型语言：

解释型语言提供了极佳的调试支持。一名Java程序员只需要几分钟就可以定位并修复一个“空指针异常”，因为Java运行环境不仅指明了异常的性质，而且给出了异常发生位置具体的行号和函数调用顺序（著名的堆栈跟踪信息）。这样的便利是编译型语言所无法提供的。
另一个优势是解释器比编译器容易实现
解释型语言最大的优势之一是其平台独立性
解释型语言也可以保证高度的安全性——这是互联网应用迫切需要的
中间语言代码的大小比编译型可执行代码小很多
平台独立性，以及严密的安全性是使解释型语言成为适合互联网和Web应用的理想语言的2个最重要的因素。
解释型语言存在一些严重的缺点。解释型应用占用更多的内存和CPU资源。这是由于，为了运行解释型语言编写的程序，相关的解释器必须首先运行。解释器是复杂的，智能的，大量消耗资源的程序并且它们会占用很多CPU周期和内存。
由于解释型应用的decode-fetch-execute（解码-抓取-执行）的周期，它们比编译型程序慢很多。
解释器也会做很多代码优化，运行时安全性检查；这些额外的步骤占用了更多的资源并进一步降低了应用的运行速度。

\1. 从执行速度而言，编译型语言更快。

　　编译型语言执行的时候，CPU可直接读取可执行代码(机器语言)，速度很快。

　　解释型语言执行的时候，需要解释器翻译一行，CPU执行一行，速度相对较慢。

\2. 从跨平台而言，解释型语言更便利。

　　编译型语言，不仅要根据不同CPU安装对应编译器，还需要根据不同操作系统选用应不同启动代码，不便利。

　　解释型语言，仅需要根据不同操作系统安装对应解释器，十分便利。



# 垃圾回收

2）根搜索算法（使用）
根搜索算法是通过一些“GC Roots”对象作为起点，从这些节点开始往下搜索，搜索通过的路径成为引用链（Reference Chain），当一个对象没有被GC Roots 的引用链连接的时候，说明这个对象是不可用的。

![image-20210803082518757](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210803082518757.png)

GC Roots 对象包括：
a) 虚拟机栈（栈帧中的本地变量表）中的引用的对象。
b) 方法区域中的类静态属性引用的对象。
c) 方法区域中常量引用的对象。
d) 本地方法栈中JNI（Native 方法）的引用的对象。



在java中，有「**「固定的GC Roots 对象」**」和「**「不固定的临时GC Roots对象」**:」

「**「固定的GC Roots:」**」

- 1.在「**「虚拟机栈(栈帧的本地变量表)中所引用的对象」**」，譬如各个线程被调用的方法堆栈中使用到的参数、局部变量、临时变量等。
- 在方法区中「类静态属性引用的对象」，譬如 Java 类的**「引用静态变量」**。
- 在方法区中「**「常量引用的对象」**」，譬如字符串常量池中的引用。
- 在方法区栈中 **「「JNI (譬如 Native 方法)引用的对象」」**。
- Java **「「虚拟机内部的引用」」**，如基本数据类型对应的 Class 对象，一些常驻的异常对象(空指针异常、OOM等)，还有类加载器。
- 所有「**「被 Synchronized 持有的对象」**」。
- 反应 Java 虚拟机内部情况的 **「「JMXBean、JVMTI 中注册的回调本地代码缓存等」」**。

「**「临时GC Roots:」**」

- 「**「为什么会有临时的 GC Roots ？」**」:目前的垃圾回收大部分都是「**「分代收集和局部回收」**」，如果只针对某一部分区域进行局部回收，那么就必须要考虑的「**「当前区域的对象有可能正被其他区域的对象所引用」**」，这时候就要将这部分关联的对象也添加到 GC Roots 中去来确保根可达算法的准确性。这种算法是利用了「**「逆向思维」**」，找到使用的对象，剩下的就是垃圾，也被称为"间接垃圾收集"。

通过上面的算法搜索到无用对象之后，就是回收过程，==回收算法如下==：
1）标记—清除算法（Mark-Sweep）（DVM 使用的算法）
标记—清除算法包括两个阶段：“标记”和“清除”。在标记阶段，标记所有被引用的对象。清除阶段紧随标记阶段，将标记阶段确定不可用的对象清除。标记—清除算法是基础的收集算法，标记和清除阶段的效率不高，而且清除后会产生大量的不连续空间，这样当程序需要分配大内存对象时，可能无法找到足够的连续空间。



![image-20210803082703968](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210803082703968.png)

![image-20230210115116666](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210115116666.png) 

2）复制算法（Copying）

复制算法是把内存分成大小相等的两块，每次使用其中一块，当垃圾回收的时候，把存活的对象复制到另一块上，然后把这块内存整个清理掉。复制算法实现简单，运行效率高，但是由于每次只能使用其中的一半，造成内存的利用率不高。现在的JVM用复制方法收集新生代，**由于新生代中大部分对象（98%）都是朝生夕死的，所以两块内存的比例不是1:1(大概是8:1)。**

![image-20230210115350504](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210115350504.png)

复制算法适合朝生夕死的对象，如果对象大部分都存活，则复制算法会很浪费时间

3）标记—整理算法（Mark-Compact）
标记—整理算法和标记—清除算法一样，但是标记—整理算法不是把存活对象复制到另一块内存，而是把存活对象往内存的一端移动，然后直接回收边界以外的内存。标记—整理算法提高了内存的利用率，并且它适合在收集对象存活时间较长的老年代。

![image-20210803082813223](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210803082813223.png)

4）分代收集（Generational Collection）
分代收集是根据对象的存活时间把内存分为新生代和老年代，根据各个代对象的存活特点，每个代采用不同的垃圾回收算法。新生代采用复制算法，老年代采用标记—整理算法。垃圾算法的实现涉及大量的程序细节，而且不同的虚拟机平台实现的方法也各不相同。

当前虚拟机的垃圾收集都采用分代收集算法，这种算法没有什么新的思想，只是根据对象存活周期的不同将内存分为几块，一般java堆分为新生代和老年代，这样我们就可以根据各个年代的特点选择合适的垃圾收集算法

![image-20220207022808541](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220207022808541.png)

## hotspot为什么要分新生代和老年代

主要是为了提供垃圾回收效率，可以参考上面的分代收集算法

比如：

==大对象就是需要大量连续内存空间的对象直接进入老年代==(比如：字符串、数组)，这样做的原因是为了避免过大的对象分配Survior区中，**导致from或者to区空间及易满发生频繁的Minor GC，而且该对象体积较大带来的复制而降低效率**。

==长期存活的对象将进入老年代==
虚拟机采用了分代收集的思想来管理内存，那么内存回收时就必须能识别那些对象应放在新生代，那些对象应放在老年代中。为了做到这一点，虚拟机给每个对象一个对象年龄（Age）计数器。

如果对象在 Eden 出生并经过第一次 Minor GC 后仍然能够存活，并且能存活在 Survivor 区的话，将被移动到 Survivor 空间中(from区与to区来回交换复制)，并将对象年龄设为1.对象在 Survivor 中每熬过一次 MinorGC(from区复制到to区)，年龄就增加1岁，当它的年龄增加到一定程度（默认为15岁），就会被移动到老年代中，对象移动到老年代的年龄阈值，可以通过参数 -XX:MaxTenuringThreshold 来设置。

新生代一般用复制算法：复制算法将可用[内存]按容量划分为大小相等的两块，每次只使用其中的一块。当这一块的内存用完了，就将还存活着的对象复制到另外一块上面，然后再把已使用过的内存空间一次清理掉。

在HotSpot中，Eden空间和另外两个Survivor空间默认所占的比例是8∶1，当然开发人员可以通过选项“-XX:SurvivorRatio”调整这个空间比例。当执行一次Minor GC（年轻代的垃圾回收）时，Eden空间中的存活对象会被复制到To空间内，并且之前已经经历过一次Minor GC并在From空间中存活下来的对象如果还年轻的话同样也会被复制到To空间内。**需要注意的是，在满足两种特殊情况下，Eden和From空间中的存活对象将不会被复制到To空间内**。首先是如果存活对象的分代年龄超过选项“-XX：MaxTenuringThreshold”所指定的阈值时，将会直接晋升到老年代中。<font color="red">其次当To空间的容量达到阈值时，存活对象同样也是直接晋升到老年代中</font>。当所有的存活对象都被复制到To空间或者晋升到老年代后，剩下的均为垃圾对象，这就意味着GC可以对这些已经死亡了的对象执行一次Minor GC，释放掉其所占用的内存空间。 

**老年代的对象比较稳定，所以MajorGC 不会频繁执行**。在进行MajorGC 前一般都先进行了一次MinorGC，使得有新生代的对象晋身入老年代，导致空间不够用时才触发。当无法找到足够大的连续空间分配给新创建的较大对象时也会提前触发一次MajorGC 进行垃圾回收腾出空间。
**MajorGC 采用标记清除或者标记整理算法**：首先扫描一次所有老年代，标记出存活的对象，然后回收没有标记的对象。MajorGC 的耗时比较长，因为要扫描再回收。MajorGC 会产生内存碎片，为了减少内存损耗，我们一般需要进行合并或者标记出来方便下次直接分配。当老年代也满了装不下的时候，就会抛出OOM（Out of Memory）异常。

**其实复制算法无非就是使用To Survivor空间作为一个临时的空间交换角色，务必需要保证两块空间中一块必须是空的，这就是复制算法**。<font color="red">尽管复制算法能够高效执行Minor GC，但是它却并不适用于老年代中的内存回收，因为老年代中对象的生命周期都比较长，甚至在某些极端的情况下还能够与JVM的生命周期保持一致，所以如果老年代也采用复制算法执行内存回收，不仅需要额外的时间和空间，而且还会导致较多的复制操作影响到GC的执行效率。</font> 总的来说，由于JVM中的绝大多数对象都是瞬时状态的，生命周期非常短暂，所以复制算法被广泛应用于年轻代中。分区、复制的思路不仅大幅提高了垃圾收集的效率，而且也将原本繁纷复杂的内存分配算法变得前所未有的简明和扼要（既然每次内存回收都是对整个半区的回收，内存分配时也就不用考虑内存碎片等复杂情况，只要移动堆顶指针，按顺序分配内存就可以了），这简直是个奇迹！不过，任何奇迹的出现都有一定的代价，在垃圾收集技术中，复制算法提高效率的代价是人为地将可用内存缩小了一半。 

标记-整理算法
根据老年代的特点特出的一种标记算法，标记过程仍然与“标记-清除”算法一样在中间添加了一步变成“标记-整理-清除”，先把所有存活对象标记出来，如何把存活对象引用地址整理排在一起，然后直接清理掉边界以外的内存，和标记-清除算法一样效率比较低，但是非常适合内存小而且回收次数的场景(老年代区)。

这一次，垃圾收集机器人不再把餐厅分成两个南北区域了。需要执行垃圾收集任务时，机器人先执行标记-清除算法的第一个步骤，为所有使用中的餐巾纸画好标记，然后，机器人命令所有就餐者带上有标记的餐巾纸向餐厅的南面集中，同时把没有标记的废旧餐巾纸扔向餐厅北面。这样一来，机器人只需要站在餐厅北面，怀抱垃圾箱，迎接扑面而来的废旧餐巾纸就行了。 回到算法本身。当成功标记出内存中的垃圾对象后，标**记-整理算法会将所有的存活对象都移动到一个规整且连续的内存空间中，然后执行Full GC（老年代的垃圾回收，或者被称为Major GC）回收无用对象所占用的内存空间**。当成功执行压缩之后，已用和未用的内存都各自一边，彼此之间维系着一个记录下一次分配起始点的标记指针，当为新对象分配内存时，则可以使用指针碰撞（Bump the Pointer）技术修改指针的偏移量将新对象分配在第一个空闲内存位置上，为新对象分配内存带来便捷。

## jvm的新生代、老年代、永久代关系

新生代分为三个区域，一个Eden区和两个Survivor区，它们之间的比例为（8：1：1），这个比例也是可以修改的。通常情况下，对象主要分配在新生代的Eden区上，少数情况下也可能会直接分配在老年代中。[Java](https://so.csdn.net/so/search?q=Java&spm=1001.2101.3001.7020)虚拟机每次使用新生代中的Eden和其中一块Survivor（From），在经过一次Minor GC后，将Eden和Survivor中还存活的对象一次性地复制到另一块Survivor空间上（这里使用的复制算法进行GC），最后清理掉Eden和刚才用过的Survivor（From）空间。将此时在Survivor空间存活下来的对象的年龄设置为1，以后这些对象每在Survivor区熬过一次GC，它们的年龄就加1，当对象年龄达到某个年龄（默认值为15）时，就会把它们移到老年代中。

在新生代中进行GC时，有可能遇到另外一块Survivor空间没有足够空间存放上一次新生代收集下来的存活对象，这些对象将直接通过分配担保机制进入老年代；

**1.Eden区**

  Eden区位于Java堆的年轻代，是**新对象**分配内存的地方，由于堆是所有线程共享的，因此在堆上分配内存需要加锁。而Sun JDK为提升效率，会为每个新建的线程在Eden上分配一块独立的空间由该线程独享，这块空间称为TLAB（Thread Local Allocation Buffer）。在TLAB上分配内存不需要加锁，因此[JVM](https://so.csdn.net/so/search?q=JVM&spm=1001.2101.3001.7020)在给线程中的对象分配内存时会尽量在TLAB上分配。如果对象过大或TLAB用完，则仍然在堆上进行分配。如果Eden区内存也用完了，则会进行一次Minor GC（young GC）。

 

**2.Survival from to**

  Survival区与Eden区相同都在Java堆的年轻代。Survival区有两块，一块称为from区，另一块为to区，这两个区是相对的，在发生一次Minor GC后，from区就会和to区互换。在发生Minor GC时，Eden区和Survivalfrom区会把一些仍然存活的对象复制进Survival to区，并清除内存。Survival to区会把一些存活得足够旧的对象移至年老代。

 

**3.年老代**

  年老代里存放的都是存活时间较久的，大小较大的对象，因此年老代使用标记整理算法。当年老代容量满的时候，会触发一次Major GC（full GC），回收年老代和年轻代中不再被使用的对象资源。

**总结：**

1、Minor GC是发生在新生代中的垃圾收集，采用的复制算法；

2、新生代中每次使用的空间不超过90%，主要用来存放新生的对象；

3、Minor GC每次收集后Eden区和一块Survivor区都被清空；

4、老年代中使用Full GC，采用的标记-清除算法



**注意：**

**堆=新生代+老年代，不包括永久代（方法区）。**

很多人认为方法区（或者HotSpot[虚拟机](https://so.csdn.net/so/search?q=虚拟机&spm=1001.2101.3001.7020)中的永久代）是没有垃圾收集的，Java虚拟机规范中确实说过可以不要求虚拟机在方法区实现垃圾收集，而且在方法区进行垃圾收集的“性价比”一般比较低：在堆中，尤其是在新生代中，常规应用进行一次垃圾收集一般可以回收70%~95%的空间，而永久代的垃圾收集效率远低于此。

永久代的垃圾收集主要回收两部分内容：废弃常量和无用的类。回收废弃常量与回收Java堆中的对象非常类似。以常量池中字面量的回收为例，假如一个字符串“abc”已经进入了常量池中，但是当前系统没有任何一个String对象是叫做“abc”的，换句话说是没有任何String对象引用常量池中的“abc”常量，也没有其他地方引用了这个字面量，如果在这时候发生内存回收，而且必要的话，这个“abc”常量就会被系统“请”出常量池。常量池中的其他类（接口）、方法、字段的符号引用也与此类似

## 不同区域采用不同回收算法

分代收集法是目前大部分JVM 所采用的方法，其核心思想是根据对象存活的不同生命周期将内存划分为不同的域，一般情况下将GC 堆划分为老生代(Tenured/Old Generation)和新生代(YoungGeneration)。老生代的特点是每次垃圾回收时只有少量对象需要被回收，新生代的特点是每次垃圾回收时都有大量垃圾需要被回收，因此可以根据不同区域选择不同的算法。

**新生代与复制算法**
目前大部分JVM的GC 对于新生代都采取Copying 算法，因为新生代中每次垃圾回收都要回收大部分对象，即要复制的操作比较少，但通常并不是按照1：1 来划分新生代。一般将新生代划分为一块较大的Eden 空间和两个较小的Survivor 空间(From Space, To Space)，每次使用Eden 空间和其中的一块Survivor 空间，当进行回收时，将该两块空间中还存活的对象复制到另
一块Survivor 空间中。



## GC如何判断一个对象可以被回收

![image-20230210095921138](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210095921138.png)

引用计数法（已被淘汰的算法）
　　1.每一个对象有一个引用属性，新增一个引用时加一，引用释放时减一，计数为0的时候可以回收。但是这种计算方法，有一个致命的问题，无法解决循环引用的问题

可达性分析算法（根引用）
　1.从GcRoot开始向下搜索，搜索所走过的路径被称为引用链，当一个对象到GcRoot没有任何引用链相连时，则证明此对象是不可用的，那么虚拟机就可以判定回收。
　2.那么GcRoot有哪些？
　　1.虚拟机栈中引用的对象
　　2.方法区中静态属性引用的对象
　　3.方法区中常量引用的对象（猜测是string s = "str")
　　4.本地方法栈中（即一般说的native方法）引用的对象不同的引用类型
**不同的引用类型的回收机制是不一样的**
　　1.强引用：通过关键字new的对象就是强引用对象，强引用指向的对象任何时候都不会被回收，宁愿OOM也不会回收 

　　2.软引用：如果一个对象持有软引用，那么当JVM堆空间不足时，会被回收。如果内存空间足够则不会被回收。一个类的软引用可以通过iava.lang.ref.SoftReference持有
　　3.弱引用：如果一个对象持有弱引用，那么在GC时，只要发现弱引用对象，就会被回收。一个类的弱引用可以通过java.lang.ref.WeakReference持有。弱引用与软引用的区别：弱引用拥有更短的生命周期，在垃圾回收器线程扫描它所管辖的内存区域中，**一旦发现了只具有弱引用的对象，不管当前内存空间是否足够都会回收它的内存**，不过由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象
　　4.虚引用：几乎和没有一样，随时可以被回收。通过PhantomReference持有 。虚引用主要用来跟踪对象被垃圾回收的活动。在程序设计中一般很少用弱引用与虚引用，软引用的情况较多，因为软引用可以加速jvm内存回收速度，可以维护系统的运行安全，防止内存溢出（OOM）问题的产生

## finalize()

![image-20230210100610831](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210100610831.png) 

![image-20230210100631645](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210100631645.png) 



<font color="red">不可达对象不等价于可回收对象，不可达对象变为可回收对象至少要经过两次标记过程</font>。两次标记后仍然是可回收对象，则将面临回收。

**如果对象在进行根搜索后发现对象不可达，**那它将会进行被第一次标记并且进行筛选，筛选的条件是此对象是否有必要执行finalize()方法。当对象没有覆盖finalize()方法或者finalize()方法已经被虚拟机掉用过，这两种情况都视为‘没有必要执行’。

当对象变成(GC Roots)不可达时，GC会判断该对象是否覆盖了finalize方法，若未覆盖，则直接将其回收。否则，若对象未执行过finalize方法，将其放入F-Queue队列，由一低优先级线程执行该队列中对象的finalize方法。执行finalize方法完毕后，GC会再次判断该对象是否可达，若不可达，则进行回收，否则，对象“复活” ,每个对象只能触发一次finalize()方法
由于finalize()方法运行代价高昂，不确定性大，无法保证各个对象的调用顺序，不推荐大家使用，建议遗忘它

   **如果对象被认为有必要执行finalize()方法，那么这个方法会被放置在一个名为F-Queue的队列之中**，并在稍后由一条由虚拟机自动建立的、低优先级的Finalizer线程去执行。这里的‘执行’也只是指虚拟机会触发这个方法，但并不承诺一定会执行。

   **finalize()方法是对象逃脱死亡命运的最后一次机会，稍后GC会对F-Queue中的对象进行第二次小规模的标记，**如果对象在finalize()中重新与引用链上的任何一个对象建立了关联，就会被移出‘即将回收’集合，如果没有移出，那就真的离死亡不远了。

当对象的所有关联都被切断，且系统调用所有对象的finalize方法依然没有使该对象变成可达状态后，这个对象将永久性地失去引用，变成可以回收的对象

   ***\*finalize()方法只会被系统自动调用一次。\****

在Object类中的源代码：
protected void finalize() throws Throwable{ }

GC负责调用finalize()方法。

finalize()方法只有一个方法体，里面没有代码，而且这个方法是protected修饰的。

这个方法不需要程序员手动调用。JVM的垃圾回收期负责调用这个方法。

不像equals()和toString(),equals()和toString()方法是需要你写代码调用的。

finalize()只需要重写，重写完将来自动有人来调用。

finalize()方法的执行时期：
当一个java对象即将被垃圾回收器回收的时候，垃圾回收器负责调用finalize()方法。

finalize()方法实际上市SUN公司为java程序员准备的一个时机，垃圾销毁时机。

如果希望在对象销毁时机执行一段代码的话，这段代码要写在finalize()方法当中

Java 技术允许使用 finalize() 方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作。这个方法是由垃圾收集器在确定这个对象没有被引用时对这个对象调用的。它是在 Object 类中定义的，因此所有的类都继承了它。**子类覆盖 finalize() 方法以整理系统资源或者执行其他清理工作。**finalize() 方法是在垃圾收集器删除对象之前对这个对象调用的。

让将要被回收的对象重新变成可达：

```java
public class FinalizeTest
{
   private static FinalizeTest ft = null;
   public void info()
   {
      System.out.println("测试资源清理的finalize方法");
   }
   public static void main(String[] args) throws Exception
   {
      // 创建FinalizeTest对象立即进入可恢复状态
      new FinalizeTest();
      // 通知系统进行资源回收
      System.gc();  //A 处
      // 强制垃圾回收机制调用可恢复对象的finalize()方法
//    Runtime.getRuntime().runFinalization();   //B处
      System.runFinalization();   //C处
      ft.info();
   }
   public void finalize()
   {
      // 让tf引用到试图回收的可恢复对象，即可恢复对象重新变成可达
      ft = this;
   }
}
```

逃脱回收实验:

![image-20220305205637050](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220305205637050.png)

![image-20220305205653239](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220305205653239.png)

从运行结果可以看到，SAVE_HOOK对象的finalize()方法确实被GC收集器触发过，并且在被收集前成功逃脱了。另外一个值得注意的地方就是，代码中有两段完全一样的代码片段，执行结果却是一次逃脱成功，一次失败，**这是因为任何一个对象的finalize()方法都只会被系统自动调用一次，如果对象面临下一次回收，它的finalize()方法不会被再次执行**，因此第2段代码的自救行动失败了。 



## 如何判断一个常量是废弃常量

运行时常量池主要回收的是废弃的常量，假如在常量池中存在字符串“abc"如果当前**没有任何string对象引用该字符串，说明”abc"是废弃常量**，这时如果发生内存回收的话而且有必要的话“abc"就会被系统清理出常量池

## 如何判断一个类是无用的类

![image-20220207015715068](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220207015715068.png)

满足这三个条件仅仅是可以回收，不是必然回收

## 垃圾回收器是怎样寻找 GC Roots 的？

我们在前面说明了根可达算法是通过 GC Roots 来找到存活的对象的，也定义了 GC Roots，那么垃圾回收器是怎样寻找GC Roots 的呢？首先，**「「为了保证结果的准确性，GC Roots枚举时是要在STW的情况下进行的」」**，但是由于 JAVA 应用越来越大，所以也不能逐个检查每个对象是否为 GC Root，那将消耗大量的时间。一个很自然的想法是，能不能用空间换时间，在某个时候把栈上代表引用的位置全部记录下来，这样到真正 GC 的时候就可以直接读取，而不用再一点一点的扫描了。事实上，大部分主流的虚拟机也正是这么做的，比如 HotSpot ，它使用一种叫做 **「「OopMap」」** 的数据结构来记录这类信息。



## 理解垃圾回收如何回收对象：

当Java对象被创建出来之后，**垃圾回收机制会实时地监控每个对象的运行状态，包括对象的申请、引用、被引用、赋值等**。当垃圾回收机制实时地监控到某个对象<font color="red">不再被引用变量所引用时</font>，垃圾回收机制就会回收它所占用的空间。 基本上，可以把JVM内存中的对象引用理解成一种有向图，把引用变量、对象都当成有向图的顶点，将引用关系当成图的有向边，有向边总是从引用端指向被引用的Java对象。因为Java的所有对象都是由一条条线程 创建出来的，因此可以把线程对象当成有向图的起始顶点。 对于单线程程序而言，整个程序只有一条main线程，那么该图就是以main进程为顶点的有向图。在这个有向图中，main顶点可达的对象都处于可达状态，垃圾回收机制不会回收它们；**如果某个对象在这个有向图中处于不可达状态，那么就认为这个对象不再被引用，接下来垃圾回收机制就会主动回收它了**。

![image-20210907180918742](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210907180918742.png)

![image-20210907180936469](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210907180936469.png)

对于单线程程序而言，整个程序只有一条main线程，那么该图就是以main进程为顶点的有向图。在这个有向图中，main顶点可达的对象都处于可达状态，垃圾回收机制不会回收它们；如果某个对象在这个有向图中处于不可达状态，那么就认为这个对象不再被引用，接下来垃圾回收机制就会主动回收它了。 

上面程序中定义了三个Node对象，并通过合适的引用关系把这三个Node对象组织在一起，应该可以清楚地绘制出三个Node对象在内存中的引用关系图。接下来就可以把它们在JVM中对应的有向图绘制出来，如图4.1所示。从图4.1所示的有向图可以看出，从main顶点开始，有一条路径到达“第一个Node对象”，因此该对象处于可达状态，垃圾回收机制不会回收它；从main顶点开始，有两条路径到达“第二个Node对象”，因此该对象也处于可达状态，垃圾回收机制也不会回收它；从main顶点开始，没有路径可以到达“第三个Node对象”，因此这个Java对象就变成了垃圾，接下来垃圾回收机制就会回收它。 JVM的垃圾回收机制采用有向图方式来管理内存中的对象，因此可以方便地解决循环引用的问题。



![image-20210907181015217](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210907181015217.png)

当一个对象在堆内存中运行时，根据它在对应有向图中的状态，可以把它所处的状态分成如下三种。 

➢ 可达状态：当一个对象被创建后，有一个以上的引用变量引用它。在有向图中可以从起始顶点导航到该对象，那么它就处于可达状态，程序可以通过引用变量来调用该对象的属性和方法。 

➢ 可恢复状态：如果程序中某个对象不再有任何引用变量引用它，它将先进入可恢复状态，此时从有向图的起始顶点不能导航到该对象。在这种状态下，系统的垃圾回收机制准备回收该对象所占用的内存。在回收该对象之前，系统会调用可恢复状态的 对象的finalize方法进行资源清理，如果系统调用finalize方法重新让一个以上的引用变量引用该对象，则这个对象会再次变为可达状态；否则，该对象将进入不可达状态。 

➢ 不可达状态：当对象的所有关联都被切断，且系统调用所有对象的finalize方法依然没有使该对象变成可达状态后，这个对象将永久性地失去引用，最后变成不可达状态。只有当一个对象处于不可达状态时，系统才会真正回收该对象所占有的资源。 

## GC分哪两种，Minor GC 和Full GC有什么区别？

什么时候会触发Full GC？分别采用什么算法？

对象从新生代区域消失的过程，我们称之为 "minor GC"
对象从老年代区域消失的过程，我们称之为 "major GC"
Minor GC
清理整个YouGen的过程，eden的清理，S0\S1的清理都会由于MinorGC Allocation
Failure(YoungGen区内存不足），而触发minorGC

Major GC
OldGen区内存不足，触发Major GC
Full GC
Full GC 是清理整个堆空间—包括年轻代和永久代
Full GC 触发的场景
1）System.gc
2）promotion failed (年代晋升失败,比如eden区的存活对象晋升到S区放不下，又尝试直接晋升到Old区又放不下，那么Promotion Failed,会触发FullGC)
3）CMS的Concurrent-Mode-Failure

由于CMS回收过程中主要分为四步: 1.CMS initial mark 2.CMS Concurrent mark 3.CMS
remark 4.CMS Concurrent sweep。在2中gc线程与用户线程同时执行，那么用户线程依旧可能同时产生垃圾， 如果这个垃圾较多无法放入预留的空间就会产生CMS-Mode-Failure， 切换为SerialOld单线程做mark-sweep-compact。
4）新生代晋升的平均大小大于老年代的剩余空间 （为了避免新生代晋升到老年代失败）
当使用G1,CMS 时，FullGC发生的时候 是 Serial+SerialOld。
当使用ParalOld时，FullGC发生的时候是 ParallNew +ParallOld

# Y-GC和Full GC的触发条件

1 Young GC触发时机

一般在新生代Eden区满后触发，采用复制算法回收新生代垃圾。Young GC 还有种说法就叫做 Minor GC。

2 Old GC和Full GC的触发时机

发生Young GC前检查，若老年代可用连续内存空间<新生代历次younggc后升入老年代的对象总和的平均大小，说明本次y-gc后，可能升入老年代的对象大小超过老年代当前可用内存空间，此时必须触发一次old gc给老年代腾出空间，再执行young gc

 执行Y-GC后，有一批对象需要放入老年代，但此时老年代无足够内存空间存放这些对象，此时必须立即触发一次Old GC。

老年代内存使用率超过92%，直接触发Old GC

这个比例是可以通过参数调整的。

Old GC执行时，一般都会带上一次Y-GC，一般Old GC很可能就是在Young GC之前或之后触发，所以自然Old GC一般都会跟一次Young GC连带关联在一起了。

很多JVM实现里，其实在上述几种条件达到时，他触发的实际上就是Full GC，其实满足上述一些条件时，在GC日志中看到的就是Full GC字样。

但是这个东西其实没办法给大家一个准确的定义，说到底触发Full GC的时候，是先执行Young GC？还是先执行Old GC？不同Full GC触发条件不一样，而且不同JVM版本实现也不同。

所以只能概括：上述条件满足时触发Full GC，Full GC一般会带上一次Young GC 去回收新生代，同时也会有Old GC也回收老年代，还会去回收永久代。

假如存放类信息、常量池的永久代满了后，就会触发一次Full GC。

、Major GC 又称Full GC或老年代GC，指发生在老年代的GC；

   出现Major GC经常会伴随至少一次的Minor GC（不是绝对，Parallel Sacvenge收集器就可以选择设置Major GC策略）；

  Major GC速度一般比Minor GC慢10倍以上；





这样Full GC执行时，就会顺带把永久代(方法区）中的垃圾给回收了**，但永久代中的垃圾一般很少，因为里面存放的都是一些类，还有常量池之类的东西，这些东西通常无需回收。如果永久代真的放满了，回收之后发现没腾出来更多的地方，此时只能抛出内存不够异常**。

Full GC: 收集整个堆，包括新生代，老年代，永久代(在 JDK 1.8 及以后，永久代被移除，换为 metaspace 元空间)等所有部分的模式

## 什么时候会触发FullGC

除直接调用System.gc外，触发Full GC执行的情况有如下四种。 

1. 老生代空间不足 老生代空间只有在新生代对象转入及创建为大对象、大数组时才会出现不足的现象，当执行Full GC后空间仍然不足，则抛出如下错误： java.lang.OutOfMemoryError: Java heap space 为避免以上两种状况引起的FullGC，**调优时应尽量做到让对象在Minor GC阶段被回收、让对象在新生代多存活一段时间及不要创建过大的对象及数组。**
Permanet Generation空间满 PermanetGeneration中存放的为一些class的信息等，当系统中要加载的类、反射的类和调用的方法较多时，Permanet  Generation可能会被占满，在未配置为采用CMS GC的情况下会执行Full GC。如果经过Full GC仍然回收不了，那么JVM会抛出如下错误信息： java.lang.OutOfMemoryError: PermGen space 为避免Perm Gen占满造成Full GC现象，可采用的方法为增大Perm Gen空间或转为使用CMS GC。
CMS GC时出现promotion failed和concurrent mode failure 对于采用CMS进行旧生代GC的程序而言，尤其要注意GC日志中是否有promotion failed和concurrent mode failure两种状况，当这两种状况出现时可能会触发Full GC。 promotionfailed是在进行Minor GC时，survivor space放不下、对象只能放入旧生代，而此时旧生代也放不下造成的；concurrent mode failure是在执行CMS GC的过程中同时有对象要放入旧生代，而此时旧生代空间不足造成的。 应对措施为：增大survivorspace、旧生代空间或调低触发并发GC的比率，但在JDK  5.0+、6.0+的版本中有可能会由于JDK的bug29导致CMS在remark完毕后很久才触发sweeping动作。对于这种状况，可通过设置

XX:CMSMaxAbortablePrecleanTime=5（单位为ms）来避免。
统计得到的Minor GC晋升到旧生代的平均大小大于旧生代的剩余空间 这是一个较为复杂的触发情况，Hotspot为了避免由于新生代对象晋升到旧生代导致旧生代空间不足的现象，在进行Minor GC时，做了一个判断，如果之前统计所得到的Minor GC晋升到旧生代的平均大小大于旧生代的剩余空间，那么就直接触发Full  GC。  例如程序第一次触发MinorGC后，有6MB的对象晋升到旧生代，那么当下一次Minor GC发生时，首先检查旧生代的剩余空间是否大于6MB，如果小于6MB，
则执行Full GC。 当新生代采用PSGC时，方式稍有不同，PS GC是在Minor GC后也会检查，例如上面的例子中第一次Minor GC后，PS GC会检查此时旧生代的剩余空间是否大于6MB，如小于，则触发对旧生代的回收。 除了以上4种状况外，对于使用RMI来进行RPC或管理的Sun JDK应用而言，默认情况下会一小时执行一次Full GC。可通过在启动时通过- java- Dsun.rmi.dgc.client.gcInterval=3600000来设置Full GC执行的间隔时间或通过

-XX:+ DisableExplicitGC来禁止RMI调用System.gc。





## JVM的永久代中会发生垃圾回收么？
永久代现在已经移除，取而代之的是元数据区，垃圾回收不会发生在永久代，如果永久代满了或者是超过了临界值，会触发完全垃圾回收(Full  GC)。如果你仔细查看垃圾收集器的输出信息，就会发现永久代也是被回收的。这就是为什么正确的永久代大小对避免Full GC是非常重要的原因。请参考下Java8：从永久代到元数据区 (注：Java8 中已经移除了永久代，新加了一个叫做元数据区的native内存区)

## 评价gc回收器的指标

![image-20230210155901847](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210155901847.png) 

![image-20230210160510022](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210160510022.png) 

现在设计gc的标准是：在最大吞吐量的情况下，降低停顿时间

## 常见的垃圾回收器?

高吞吐量和暂停时间是矛盾的。为了提高吞吐量，就要尽量减少GC的发生，但是GC发生的次数少了，每次要回收的对象就会多，那暂停时间就会长。

1. 串行回收算法：会停止当前应用进程，回收垃圾，停顿时间久，吞吐量大，响应时间长
2. 并行回收算法： 是**多个线程**同时执行串行回收算法（多核），也会使应用停顿，吞吐量大，响应时间长，用户体验差
3. 并发回收算法：应用和垃圾回收多个线程并发执行，吞吐量相对小，响应时间短，用户体验好

![image-20211222214539048](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211222214539048.png)

![image-20211220213509007](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220213509007.png)



JDK诞生之后第一个垃圾回收器就是Serial，和Serial Old。追随提高效率，诞生了PS，为了配合CMS.诞生了PN.CMS是1.4版本后期引入,CMS是里程碑式的GC,它开启了并发回收的过程,但是CMS毛病较多,因此目前没有任何一个JDK版本默认是CMS。所谓的Serial指的是单线程。Parallel Scavenge指的是多线程。常见的垃圾回收器组合最常用的是有三种（Serial+Serial Old）、（Parallel Scavenge+ParallelOld）、（ParNew+CMS）。

看图中画的红线，但凡是能连接在一起的都可以组合。你仔细看前面几种不仅都是在逻辑上分年轻代和老年代，在物理上也是分年轻代和老年代的。G1只是在逻辑上分年轻代老年代，在物理上他就分成一块一块的了，等我讲到它的时候再说。另外需要注意的是CMS还有一个组合是和Serial Old组合到一起的，下面我一一的解释给大家。

一篇比较详细的博客：

[Java虚拟机垃圾回收(三) 7种垃圾收集器：主要特点 应用场景 设置参数 基本运行原理_tjiyu的博客-CSDN博客](https://blog.csdn.net/tjiyu/article/details/53983650)

- **「1.Serial」**

![image-20211220205556255](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220205556255.png)

Serial是一个「**「单线程」**」的垃圾回收器，「**「采用复制算法负责新生代」**」的垃圾回收工作，可以与 CMS 垃圾回收器一起搭配工作。

在 STW 的时候「**「只会有一条线程」**」去进行垃圾收集的工作，所以可想而知，它的效率会比较慢。但是他确是所有垃圾回收器里面消耗额外内存最小的，没错，就是因为简单。

**虽然Serial收集器的“单线程收集”是有明显的弊端的，但是，他自身的优势也是很明显的：**

即，简单而高效，对于内存资源受限的环境，它是所有收集器里额外内存消耗最小的；对于单核处理器或处理器核心数较少的环境来说，Serial收集器由于没有线程交互的开销，专心做垃圾收集自然可以获得最高的单线程收集效率。

在用户桌面的应用场景以及近年来流行的部分微服务应用中，分配给虚拟机管理的内存一般来说并不会特别大，收集几十兆甚至一两百兆的新生代（仅仅是指新生代使用的内存，桌面应用甚少超过这个容量），垃圾收集的停顿时间完全可以控制在十几、几十毫秒，最多一百多毫秒以内，只要不是频繁发生收集，这点停顿时间对许多用户来说是完全可以接受的。所以，Serial收集器对于运行在客户端模式下的虚拟机来说是一个很好的选择。


- **「2.ParNew」**

ParNew收集器实质上是Serial收集器的多线程并行版本，除了同时使用多条线程进行垃圾收集之外，其余的行为包括Serial收集器可用的所有控制参数（例如：-XX：SurvivorRatio、-XX：PretenureSizeThreshold、-XX：HandlePromotionFailure等）、收集算法、Stop The World、对象分配规则、回收策略等都与Serial收集器完全一致，在实现上这两种收集器也共用了相当多的代码。


ParNew 是一个「**「多线程」**」的垃圾回收器，**「采用复制算法负责新生代」**的垃圾回收工作，可以与CMS垃圾回收器一起搭配工作,目前只有ParNew收集器能与CMS收集器一起搭配工作。

它其实就是 Serial 的多线程版本，主要区别就是在 STW 的时候可以用多个线程去清理垃圾。

- **「3.Pararllel Scavenge」**
- ![image-20211220205532484](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220205532484.png)

Pararllel Scavenge 是一个「**「多线程」**」的垃圾回收器，「**「采用复制算法负责新生代」**」的垃圾回收工作，可以与 Serial Old ， Parallel Old 垃圾回收器一起搭配工作。

是与 ParNew 类似，都是用于年轻代回收的使用复制算法的并行收集器，与 ParNew 不同的是，Parallel Scavenge 的「**「目标是达到一个可控的吞吐量」**」。吞吐量=程序运行时间/（程序运行时间+GC时间）。如程序运行了99s，GC耗时1s，吞吐量=99/（99+1）=99%。Parallel Scavenge 提供了两个参数用以精确控制吞吐量，分别是用以控制最大 GC 停顿时间的 -XX:MaxGCPauseMillis 及直接控制吞吐量的参数 -XX:GCTimeRatio.「**「停顿时间越短就越适合需要与用户交互的程序」**」，良好的响应速度能提升用户体验，而高吞吐量则可以高效的利用 CPU 时间，尽快完成程序的运算任务，主要适合在后台运算而不需要太多交互的任务。

- **「4.Serial Old」**

  他用的是mark-sweep的算法，用的也是单线程。

Serial Old 是一个「**「单线程」**」的垃圾回收器，「**「采用标记整理算法负责老年代」**」的垃圾回收工作，有可能还会配合 「**「CMS」**」 一起工作。

![image-20211220205629752](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220205629752.png)

- **「5.Parallel Old」**

是Parallel Scavenge收集器的老年代版本，用于老年代的垃圾回收，但与Parallel Scavenge不同的是，它使用的是“标记-整理算法”。适用于注重于吞吐量及CPU资源敏感的场合。

Parallel Old 是 Pararllel Scavenge 的老年代版本，它的设计思路也是以吞吐量优先的，ps+po 是很常用的一种组合。

- **「6.CMS」**

CMS可以说是一款具有"跨时代"意义的垃圾回收器，支持了和用户线程一起工作，做到了**「一起并发回收垃圾」**的"壮举"。

![image-20211220215040354](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220215040354.png)

- **「1.初始标记」**
- 初始标记只是标记出来「**「和 GC Roots 直接关联」**」的对象，整个速度是非常快的，为了保证标记的准确，这部分会在 「**「STW」**」 的状态下运行。
- **「2.并发标记」**
- 并发标记这个阶段会直接根据第一步关联的对象找到「**「所有的引用」**」关系，这一部分时刻用户线程「**「并发运行」**」的，虽然耗时较长，但是不会有很大的影响。
- **「3.重新标记」**
- 重新标记是为了解决第二步并发标记所导致的标错情况，这里简单举个例子：并发标记时a没有被任何对象引用，此时垃圾回收器将该对象标位垃圾，在之后的标记过程中，a又被其他对象引用了，这时候如果不进行重新标记就会发生「**「误清除」**」。这部分内容也是在「**「STW」**」的情况下去标记的。也就是重新标记是将那些本来是垃圾对象后来变成非垃圾对象的进行标记，而不会处理本来是非垃圾后来变成垃圾的对象
- **「4.并发清除」**
- 这一步就是最后的清除阶段了，将之前「**「真正确认为垃圾的对象回收」**」，这部分会和用户线程一起并发执行。
- 并发重置：重置本次gc过程中的标记数据

初始标记仅仅只是标记一下GC Roots能直接关联到的对象，速度很快。

并发标记就是进行Gc Roots Tracing的过程。

重新标记则是为了修正并发标记期间，因用户程序继续运行而导致的标记产生变动的那一部分对象的 标记记录，这个阶段停顿时间一般比初始标记时间长，但是远比并发标记时间短。

整个过程中并发标记时间最长，但此时可以和用户线程一起工作

优点：

并发收集、低停顿

缺点：

■对cpu资源非常敏感。

**■无法处理浮动垃圾。**

■**内存碎片问题**。

CMS的「**「三个缺点」**」：

- **「1.影响用户线程的执行效率」**

- - CMS默认启动的回收线程数是（处理器核心数 + 3）/ 4 ,**由于是和用户线程一起并发清理，那么势必会影响到用户线程的执行速度**，并且这个影响「**「随着核心线程数的递减而增加」**」。所以 JVM 提供了一种 "「**「增量式并发收集器」**」"的 CMS 变种，主要是用来减少垃圾回收线程独占资源的时间，所以会感觉到回收时间变长，这样的话「**「单位时间内处理垃圾的效率就会降低」**」，也是一种缓和的方案。

- **「2.会产生"浮动垃圾"」**

- - 之前说到 CMS 真正清理垃圾是和用户线程一起进行的，在「**「清理」**」这部分垃圾的时候和并发清除的时候「**「用户线程会产生新的垃圾」**」，这部分垃圾就叫做浮动垃圾，并且只能等着下一次的垃圾回收再清除。

       这使得并发清除时需要预留一定的内存空间，不能像其他收集器在老年代几乎填满再进行收集；
       也要可以认为CMS所需要的空间比其他垃圾收集器大；
       
        "-XX:CMSInitiatingOccupancyFraction"：设置CMS预留内存空间；
       
        JDK1.5默认值为68%；
       
        JDK1.6变为大约92%；      
  
  <font color="red">Concurrent Mode Failure"失败</font>

        如果CMS预留内存空间无法满足程序需要，就会出现一次"Concurrent Mode Failure"失败；
      这时JVM启用后备预案：临时启用Serail Old收集器，而导致另一次Full GC的产生；
      这样的代价是很大的，所以CMSInitiatingOccupancyFraction不能设置得太大。

- **「3.会产生碎片化的空间」**

- - CMS 是使用了标记删除的算法去清理垃圾的，而这种算法的缺点就是会产生「**「碎片化」**」，后续可能会「**「导致大对象无法分配」**」从而触发「**「和 Serial Old 一起配合使用」**」来处理碎片化的问题，当然这也处于 「**「STW」**」的情况下，所以当 java 应用非常庞大时，如果采用了 CMS 垃圾回收器，产生了碎片化，那么在 STW 来处理碎片化的时间会非常之久。

    


### G1

**G1 (Garbage-First)是一款面向服务器的垃圾收集器,**主要针对配备多颗处理器及大容量内存的机器. 以极高概率满足GC停顿时间要求的同时,还具备高吞吐量性能特征.

G1(Garbage First)：顾名思义，「**「垃圾回收第一」**」，官方对它的评价是在垃圾回收器技术上具有「**「里程碑式」**」的成果。**G1 回收的目标不再是整个新生代，不再是整个老年代，而是整个堆了**。G1 可以「**「面向堆内存的任何空间来进行」**」回收，衡量的标准也不再是根据年代来区分，而是哪块「**「空间的垃圾最多就回收哪」**」块儿空间，这也符合 G1 垃圾回收器的名字，垃圾第一，这就是 G1 的 「**「Mixed GC」**」 模式。当然我的意思是「**「垃圾回收不根据年代来区分」**」，但是 G1 还是「**「根据年代来设计」**」的，我们先来看下 G1 对于堆空间的划分：

![image-20211220215804499](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220215804499.png)

G1 垃圾回收器把堆划分成一个个「**「大小相同的Region」**」，每个 Region 都会扮演一个角色，H、S、E、O。E代表伊甸区，S代表 Survivor 区，H代表的是 Humongous(G1用来分配「**「大对象的区域」**」，对于 Humongous 也分配不下的超大对象，会分配在连续的 N 个 Humongous 中)，剩余的深蓝色代表的是 Old 区，灰色的代表的是空闲的 region。在 HotSpot 的实现中，整个堆被划分成2048左右个 Region。每个 Region 的大小在1-32MB之间，具体多大取决于堆的大小。一般Region大小等于堆大小除以2048，比如堆大小为4096M，则Region大小为2M，当然也可以用参数"-XX:G1HeapRegionSize"手动指定Region大小，但是推荐默认的计算方式。G1保留了年轻代和老年代的概念，但不再是物理隔阂了，它们都是（可以不连续）Region的集合。

在并发标记垃圾时也会产生新的对象，G1 对于这部分对象的处理是这样的：将 Region 「**「新增一块并发回收过程中分配对象的空间」**」，并为此设计了两个 TAMS(Top at Mark Start)指针，这块区域专门用来在并发时分配新对象，有对象新增只需要将 TAMS 指针移动下就可以了，并且这些「**「新对象默认是标记为存活」**」，这样就「**「不会干扰到标记过程」**」。一个Region可能之前是年轻代，如果Region进行了垃圾回收，之后可能又会变成老年代，也就是说Region的区域功能可能会动态变化。

![image-20211220215909648](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211220215909648.png)

但是这种方法也会有个问题，有可能「**「垃圾回收的速度小于新对象分配的速度」**」，这样会导致 "Full GC" 而产生长时间的 STW。在 G1 的设计理念里，「**「最小回收单元是 Region」**」，每次回收的空间大小都是Region的N倍，那么G1是「**「怎么选择要回收哪块儿区域」**」的呢？G1 会跟踪各个 Region 区域内的垃圾价值，和回收空间大小回收时间有关，然后「**「维护一个优先级列表」**」，来收集那些价值最高的Reigon区域。

G1垃圾收集器对于对象什么时候会转移到老年代跟之前讲过的原则一样，**唯一不同的是对大对象的处理**，G1有专门分配大对象的Region叫**Humongous区**，而不是让大对象直接进入老年代的Region中。在G1中，大对象的判定规则就是一个大对象超过了一个Region大小的50%，比如按照上面算的，每个Region是2M，只要一个大对象超过了1M，就会被放入Humongous中，而且一个大对象如果太大，可能会横跨多个Region来存放。

Humongous区专门存放短期巨型对象，不用直接进老年代，可以节约老年代的空间，避免因为老年代空间不够的GC开销。

Full GC的时候除了收集年轻代和老年代之外，也会将Humongous区一并回收。

执行的步骤：

- **「初始标记」**：

- - 标记出来 GC Roots 能「**「直接关联」**」到的对象
  - 修改 TAMS 的值以便于并发回收时新对象分配
  - 是在 Minor GC 时期(**「「STW」」**)完成的

- **「并发标记」**：

- - 根据刚刚关联的对像扫描整个对象引用图，和用户线程**「并发执行」**
  - 记录 SATB(原始快照) 在并发时有引用的值

- **「最终标记」**：

- - 处于 **「「STW」」**，处理第二步遗留下来的少量 SATB(原始快照) 记录,同cms的重新标记

- **「筛选回收」**：

- - 维护之前提到的优先级列表
  
  - 根据「**「优先级列表」**」，「**「用户设置的最大暂停时间」**」来回收 Region
  
  - 将需要回收的 Region 内存活的对象「**「复制」**」到不需要回收的 Region区域内，然后回收需要回收的 Region
  
  - 这部分是处于 「**「STW」**」 下执行，并且是多线程的。筛选回收阶段首先对各个region的回收价值和成本进行排序，根据用户所期望的gc停顿stw时间可以用jvm参数 -XX:MAXGCPauseMillis来制定回收计划。不同于cms的并发清除，是与用户线程一起运行的。
  
    比如说老年代此时有1000个Region都满了，但是因为根据预期停顿时间，本次垃圾回收可能只能停顿200毫秒，那么通过之前回收成本计算得知，可能回收其中800个Region刚好需要200ms，那么就只会回收800个Region(Collection Set，要回收的集合)，尽量把GC导致的停顿时间控制在我们指定的范围内。这个阶段其实也可以做到与用户程序一起并发执行，但是因为只回收一部分Region，时间是用户可控制的，而且停顿用户线程将大幅提高收集效率。不管是年轻代或是老年代，**回收算法主要用的是复制算法，将一个region中的存活对象复制到另一个region中，这种不会像CMS那样回收完因为有很多内存碎片还需要整理一次**，G1采用复制算法回收几乎不会有太多内存碎片。(注意：CMS回收阶段是跟用户线程一起并发执行的，G1因为内部实现太复杂暂时没实现并发回收，不过到了Shenandoah就实现了并发收集，Shenandoah可以看成是G1的升级版本)

**G1收集器在后台维护了一个优先列表，每次根据允许的收集时间，优先选择回收价值最大的Region(这也就是它的名字Garbage-First的由来)，比如一个Region花200ms能回收10M垃圾，另外一个Region花50ms能回收20M垃圾，在回收时间有限情况下，G1当然会优先选择后面这个Region回收**。这种使用Region划分内存空间以及有优先级的区域回收方式，保证了G1收集器在有限时间内可以尽可能高的收集效率。

g1垃圾回收的详细过程：

![image-20230210205819837](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210205819837.png) 

![image-20230210210514430](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210210514430.png) 

![image-20230210210526196](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210210526196.png) 

![image-20230210223002172](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20230210223002172.png)



## 记忆集与卡表

在新生代做GCRoots可达性扫描过程中可能会碰到跨代引用的对象，这种如果又去对老年代再去扫描效率太低了。
为此，在新生代可以引入记录集（Remember Set）的数据结构（记录从非收集区到收集区的指针集合），避免把整个老年代加入GCRoots扫描范围。事实上并不只是新生代、 老年代之间才有跨代引用的问题， 所有涉及部分区域收集（Partial GC） 行为的垃圾收集器， 典型的如G1、 ZGC和Shenandoah收集器， 都会面临相同的问题。
垃圾收集场景中，收集器只需通过记忆集判断出某一块非收集区域是否存在指向收集区域的指针即可，无需了解跨代引用指针的全部细节。
hotspot使用一种叫做“卡表”(cardtable)的方式实现记忆集，也是目前最常用的一种方式。关于卡表与记忆集的关系，可以类比为Java语言中HashMap与Map的关系。
卡表是使用一个字节数组实现：CARD_TABLE[ ]，每个元素对应着其标识的内存区域一块特定大小的内存块，称为“卡页”。
hotSpot使用的卡页是2^9大小，即512字节

## CMS 和G1 的区别

区别一： 使用范围不一样

CMS收集器是老年代的收集器，可以配合新生代的Serial和ParNew收集器一起使用
 G1收集器收集范围是老年代和新生代。不需要结合其他收集器使用

区别二： STW的时间

CMS收集器以最小的停顿时间为目标的收集器。

G1收集器可预测垃圾回收的停顿时间（建立可预测的停顿时间模型）

区别三： 垃圾碎片

CMS收集器是使用“标记-清除”算法进行的垃圾回收，容易产生内存碎片

G1收集器使用的是“标记-整理”算法，进行了空间整合，降低了内存空间碎片。

区别四： 垃圾回收的过程不一样

![image-20220411223345360](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220411223345360.png)

作者：不怕天黑_0819
链接：https://www.jianshu.com/p/ab54489f5d71
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



## 如果对象的引用被置为 null，垃圾收集器是否会立即释放对象占用的内存？

不会，在下一个垃圾回收周期中，这个对象将是可被回收的。

## 垃圾标记阶段？

在GC执行垃圾回收之前，为了区分对象存活与否，当对象被标记为死亡时，GC才回执行垃圾回收，这 个过程就是垃圾标记阶段。 



1、CMS收集器对CPU资源非常敏感

在并发阶段，虽然不会导致用户线程停顿，但是会因为占用了一部分线程使应用程序变慢，总吞吐量会降低，为了解决这种情况，虚拟机提供了一种“增量式并发收集器” 的CMS收集器变种， 就是在并发标记和并发清除的时候让GC线程和用户线程交替运行，尽量减少GC 线程独占资源的时间，这样整个垃圾收集的过程会变长，但是对用户程序的影响会减少。（效果不明显，不推荐）

2、 CMS处理器无法处理浮动垃圾

CMS在并发清理阶段线程还在运行， 伴随着程序的运行自然也会产生新的垃圾，这一部分垃圾产生在标记过程之后，CMS无法再当次过程中处理，所以只有等到下次gc时候在清理掉，这一部分垃圾就称作“浮动垃圾” 。

3、CMS是基于“标记--清除”算法实现的，所以在收集结束的时候会有大量的空间碎片产生。

空间碎片太多的时候，将会给大对象的分配带来很大的麻烦，往往会出现老年代还有很大的空间剩余，但是无法找到足够大的连续空间来分配当前对象的，只能提前触发 full gc。

为了解决这个问题，CMS提供了一个开关参数，用于在CMS顶不住要进行full gc的时候开启内存碎片的合并整理过程，内存整理的过程是无法并发的，空间碎片没有了，但是停顿的时间变长了。

## 什么是OopMap？有什么好处?什么是安全点？



我们知道，一个线程意味着一个栈，一个栈由多个栈帧组成，一个栈帧对应着一个方法，一个方法里面可能有多个安全点。gc 发生时，程序首先运行到最近的一个安全点停下来，然后更新自己的 OopMap ，记下栈上哪些位置代表着引用。枚举根节点时，递归遍历每个栈帧的 OopMap ，通过栈中记录的被引用对象的内存地址，即可找到这些对象（ GC Roots ）。使用 OopMap 可以「**「避免全栈扫描」**」，加快枚举根节点的速度。但这并不是它的全部用意。它的另外一个更根本的作用是，可以帮助 HotSpot 实现准确式 GC (即使用准确式内存管理，虚拟机可用知道内存中某个位置的数据具体是什么类型) 。

在HotSpot中，有个数据结构（映射表）称为「OopMap」。一旦类加载动作完成的时候， HotSpot就会把对象内什么偏移量上是什么类型的数据计算出来，记录到OopMap。在即时编译过 程中，也会在「特定的位置」生成 OopMap，记录下栈上和寄存器里哪些位置是引用。
这些特定的位置主要在：
循环的末尾（非 counted 循环）
方法临返回前 / 调用方法的call指令后
可能抛异常的位置
这些位置就叫作「安全点(safepoint)。」 用户程序执行时并非在代码指令流的任意位置都能够在停顿下来开始垃圾收集，而是必须是执行到安全点才能够暂停。

【安全点】

从线程角度看，安全点可以理解成是在「**「代码执行过程中」**」的一些「**「特殊位置」**」，当线程执行到这些位置的时候，说明「**「虚拟机当前的状态是安全」**」的。比如：「**「方法调用、循环跳转、异常跳转等这些地方才会产生安全点」**」。如果有需要，可以在这个位置暂停，比如发生GC时，需要暂停所有活动线程，但是线程在这个时刻，还没有执行到一个安全点，所以该线程应该继续执行，到达下一个安全点的时候暂停，等待 GC 结束。那么如何让线程在垃圾回收的时候都跑到最近的安全点呢？这里有「**「两种方式」**」：

- **「抢先式中断」**

- - 抢先式中断：就是在stw的时候，先让所有线程「**「完全中断」**」，如果中断的地方不在安全点上，然后「**「再激活」**」，「**「直到运行到安全点的位置」**」再中断。

- **「主动式中断」**

- - 主动式中断：在安全点的位置打一个标志位，每个线程执行都去轮询这个标志位，如果为真，就在最近的安全点挂起.





## 安全区域是什么?解决了什么问题

刚刚说到了主动式中断,但是如果有些线程处于sleep状态怎么办呢？

为了解决这种问题，又引入了安全区域的概念安全区域是指「**「在一段代码片中，引用关系不会发生改变」**」，实际上就是一个安全点的拓展。当线程执行到安全区域时，首先标识自己已进入安全区域，那样，当在这段时间里 JVM 要发起 GC 时，就不用管标识自己为“安全区域”状态的线程了，该线程只能乖乖的等待根节点枚举完或者整个GC过程完成之后才能继续执行。

## 并发与并行

　　二者对比：
　　并发，指的是多个事情，在同一时间段内同时发生了。
　　并行，指的是多个事情，在同一时间点上同时发生了。
　　并发的多个任务之间是互相抢占资源的。
　　并行的多个任务之间是不互相抢占资源的。
　　只有在多CPU或者一个CPU多核的情况中，才会发生并行。
　　否则，看似同时发生的事情，其实都是并发执行的。



## 说说三色标记

这里我们又提到了一个概念叫做 **「「SATB 原始快照」」**，关于SATB会延伸出有一个概念，**「「三色标记算法」」**，也就是垃圾回收器标记垃圾的时候使用的算法，这里我们简单说下：将对象分为「**「三种颜色」**」：

- 白色：没被 GC 访问过的对象(被 GC 标记完后还是白色代表是垃圾)
- 黑色：存活的对象
- 灰色：被 GC 访问过的对象，但是对象引用链上至少还有一个引用没被扫描过

我们知道在 **「「并发标记」」** 的时候 **「「可能会」」** 出现 **「「误标」」** 的情况，这里举两个例子：

- 1.刚开始标记为 **「「垃圾」」** 的对象，但是在并发标记过程中 **「「变为了存活对象」」**
- 2.刚开始标记为 **「「存活」」** 的对象，但是在并发标记过程中  **「「变为了垃圾对象」」**

第一种情况影响还不算很大，只是相当于垃圾没有清理干净，待下一次清理的时候再清理一下就好了。第二种情况就危险了，正在使用的对象的突然被清理掉了，后果会很严重。那么 产生上述第二种情况的原因 是什么呢？

- 1.**「「新增」」** 一条或多条 **「「黑色到白色」」** 对象的新引用
- 2.**「「删除 了」」** 灰色 **「「对象」」** 到该白色对象 **「「的直接」」** 引用或间接引用。

当这两种情况 **「「都满足」」** 的时候就会出现这种问题了。所以为了解决这个问题，引入了 **「「增量更新」」** (Incremental Update)和 **「「原始快照」」** (SATB)的方案：

- 增量更新破坏了第一个条件：**「「增加新引用时记录」」** 该引用信息，在后续 STW 扫描中重新扫描(CMS的使用方案)。
- 原始快照破坏了第二个条件：**「「删除引用时记录下来」」**，在后续 STW 扫描时将这些记录过的灰色对象为根再扫描一次(G1的使用方案)。

在标记阶段中如果指针更新前引用的 oldobj 是白色对象，就将其涂成灰色



## 串行(serial)收集器和吞吐量收集器的区别是什么？

吞吐量收集器使用并行版本的新生代垃圾收集器，它用于中等规模和大规模数据的应用程序。而串行收集器对大多数的小应用(在现代处理器上需要大概100M左右的内存)就足够了。

Serial收集器？
单线程收集器，单线程的含义在于它会stop the world。垃圾回收时需要stop the world，直到它收集结 束。所以这种收集器体验比较差。
PartNew 收集器？
Serial收集器的多线程版本，除了使用采用并行收回的方式回收内存外，其他行为几乎和Serial没区别。
可以通过选项“-XX:+UseParNewGC”手动指定使用ParNew收集器执行内存回收任务。

相比之下parnew是吞吐量优先，是先考虑最少的垃圾收集时间，而cms是总的垃圾收集延长了(有的垃圾收集线程是与用户线程同时使用的，因此用户线程要占用资源 导致垃圾收集线程的时间会延长) 但是垃圾收集时间虽然延长了但对用户是友好的 用户几乎不怎么感知到延长时间(因为cms的stw的过程只有初始标记和重新标记，而这两个过程很快，用户几乎不怎么感知到) 因此cms是响应时间优先度

Serial 收集器是在 DefNewGeneration 新生代上实现收集的，DefNewGeneration上分为3个区：eden 区、 from区和 to 区。Serial 收集器使用复制算法进行回收复制算法的思想是将eden和from区活跃的对象复制到to区，并清空eden区和from区，如果to区满了，那么部分对象将会被晋升移动到老年代，随后交换from和to区。

![image-20211227110147091](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211227110147091.png)

![image-20211227110216041](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211227110216041.png)

![image-20211227110355888](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211227110355888.png)

![image-20211227110427975](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211227110427975.png)

![image-20211227110513621](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211227110513621.png)

Step 5
如果to区满了，eden区中的存活对象也将被提升到高一代（TenuredGeneration）

![image-20211227110619988](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211227110619988.png)

7号对象因to区满了也提升到Old区
Step 6
清除eden区和from区
交换from区和to区地址
小结
进行GC前需要确定（1）to区为空；（2）下一个内存代有足够的内存容纳新生代的所有对象
Step 1 - Step 3被称为可达性分析



## Parallel Scavenge收集器?

*Parallel* *Scavenge*垃圾收集器因为与吞吐量关系密切,也称为吞吐量收集器(Throughput Collector)，是一个新生代收集器，也是复制算法的收集器，同时也是多线程并行收集器，与PartNew不同是，它重 点关注的是程序达到一个可控制的吞吐量（Thoughput, CPU用于运行用户代码的时间/CPU总消耗时 间**，即吞吐量=运行用户代码时间/（运行用户代码时间+垃圾收集时间）），高吞吐量可以最高效率地利用 CPU时间，尽快地完成程序的运算任务，**主要适用于在后台运算而不需要太多交互的任务。
他可以通过2个参数精确的控制吞吐量,更高效的利用cpu。
分别是：-XX:MaxCcPauseMillis 和-XX:GCTimeRatio

例如虚拟机一共运行了 100 分钟，其中垃圾收集花费了 1 分钟，那吞吐量就是 99% 。比如下面两个场景，垃圾收集器每 100 秒收集一次，每次停顿 10 秒，和垃圾收集器每 50 秒收集一次，每次停顿时间 7 秒，虽然后者每次停顿时间变短了，但是总体吞吐量变低了，CPU 总体利用率变低了。

**停顿时间越短就越适合需要和用户交互的程序，响应速度快能提升用户体验；而高吞吐量则可以高效利用CPU时间，适用于后台运算量大而不需要太多交互的任务**。
优点：追求高吞吐量，高效利用CPU，是吞吐量优先，且能进行精确控制。
适用场景：注重吞吐量高效利用CPU，需要高效运算，且不需要太多交互。



一些参数：

-XX:MaxGCPauseMilis。 控制最大垃圾收集停顿时间，参数值是一个大于0的毫秒数，收集器尽可能保证回收花费时间不超过设定值。但将这个值调小，并不一定会使系统垃圾回收速度更快，GC停顿时间是以牺牲吞吐量和新生代空间换来的。
-XX:GCTimeRadio。设置吞吐量大小，参数值是一个(0,100)两侧均为开区间的整数。也是垃圾收集时间占总时间的比率，相当于是吞吐量的倒数。若把参数设置为19，则允许的最大GC时间就占总时间的5%（1/（1+19））。默认值是99，即允许最大1%的垃圾收集时间。
-XX:+UserAdaptiveSizePolicy。这是一个开关函数，当打开这个函数，就不需要手动指定新生代的大小，Eden与Survivor区的比例(-XX:SurvivorRatio，默认是8:1:1)，晋升老年代的对象年龄(-XX:PretenureSizeThreshold)等参数。JVM会动态调整这些参数，以提供最合适的停顿时间或者最大的吞吐量，这种调节方式称为GC自适应的调节策略.





## Parallel Old 收集器？

Parallel Scavenge收集器的老年代版本，使用多线程和标记-整理算法。JDK 1.6中才开始提供。



ParNew，其实就是Parallel New的意思，和Parallel Scavenge没什么区别，就是它的新版本做了
一些增强以便能让它和CMS配合使用，CMS某一个特定阶段的时候ParNew会同时运行。所以这个才是第三个诞生的。 我工作的时候，其余线程不能工作，必须等GC回收器结束才可以



## CMS收集器？

CMS非常重要，因为它诞生了一个里程碑式的东西。原来所有的垃圾回收就是在我干活儿的时候你其它的不能干活儿。我垃圾回收器来了，其它所有工作的线程都得给我停等着我回收，我走了你才能继续工作。CMS的诞生就消除了这种疑问。CMS毛病非常多。以至于目前任何jdk版本默认都不是CMS。

Concurrent Mar Sweep收集器是一种以获取最短回收停顿时间为目标的收集器。重视服务的响应速 度，希望系统停顿时间最短。采用标记-清除的算法来进行垃圾回收。
 CMS垃圾回收的步骤？
初始标记(stop the world)
并发标记
重新标记(stop the world)
并发清除

第一个阶段叫做CMS initial mark(初始标记阶段)。很简单，就是我直接找到最根上的对象，其他的对象我不标记，直接标记最根上的
第二个是CMS concurrent mark(并发标记)，据统计百分之八十的GC的时间是浪费在这里，因此它把这块最浪费时间的和我们的应用程序同时运行，对客户来说感觉可能是慢了一些，但至少你还有反应。并发标记。就是你一边产生垃圾，我这一边跟着标记但是这个过程是很难完成的。
所以最后又有一个CMS remark(重新标记)。这又是一个STW。在并发标记过程中产生的那些新的垃圾
在重新标记里头给它标记一下，这个时候需要你们俩停一下，时间不长。
最后是一个concurrent sweep(并发清理)的过程。并发清理也有它的问题，**并发清理过程也会产生新的垃圾啊，这个时候的垃圾叫做浮动垃圾，浮动垃圾就得等着下一次CMS再一次运行的过程把它给清理掉。**

初始标记仅仅只是标记一下GC Roots能直接关联到的对象，速度很快。
并发标记就是进行Gc Roots Tracing的过程。
重新标记则是为了修正并发标记期间，因用户程序继续运行而导致的标记产生变动的那一部分对象的 标记记录，这个阶段停顿时间一般比初始标记时间长，但是远比并发标记时间短。
整个过程中并发标记时间最长，但此时可以和用户线程一起工作。
CMS收集器优点？缺点？
优点：
并发收集、低停顿
缺点：
-对cpu资源非常敏感。
■无法处理浮动垃圾。
■内存碎片问题。

从线程的角度理解 ，它垃圾回收的线程和工作线程同时进行，叫做concurrent mark sweep
(concurrent 并发)。不管你用几个线程进行垃圾回收这个过程都太长了。在内存比较小的情况下，没有问题，速度很快。但是现在的服务器内存越来越大，大到什么程度，原来是一个房间，现在可以看成一个天安门广场。作为一个这么大的内存无论你多少个线程来清理一遍也得需要特别长的时间。以前大概在有10G内存的时候他用PS+PO停顿时间清理一次，大概需要11秒钟。 有人用CMS，这个最后也会产生碎片之后产生FGC，FGC默认的STW最长的到10几个小时，就直接卡死在哪儿了，大家什么都干不了。

什么条件触发CMS呢？老年代分配不下了，处理不下会触动CMS。初始标记是单线程，重新标记是多线程。
CMS的缺点：cms出现问题会调用Serial Old老年代出来使用单个线程进行标记压缩

**CMS并发更新失败问题**
然而， CMS并不是完美的，在使用CMS的过程中会产生2个最让人头痛的问题：

`2.1 promotion failed（晋升失败）`
promotion failed是在进行Minor GC时，Survivor Space放不下，对象只能放入老年代，而此时老年代也放不下造成的，多数是由于老年带有足够的空闲空间，但是由于碎片较多，这时如果新生代要转移到老年带的对象比较大，所以，必须尽可能提早触发老年带的CMS回收来避免这个问题（promotion failed时老年带CMS还没有机会进行回收，又放不下转移到老年带的对象，因此会出现下一个问题concurrent mode failure，需要stop-the-wold GC- Serail Old）。

优化办法：
让CMS在进行一定次数的Full GC（标记清除）的时候进行一次标记整理算法，CMS提供了以下参数来控制：
-XX:UseCMSCompactAtFullCollection -XX:CMSFullGCBeforeCompaction=5
也就是CMS在进行5此Full GC（标记清除）之后进行一次标记整理算法，从而可以控制老年带的碎片在一定的数量以内，甚至可以配置CMS在每次Full GC的时候都进行内存的整理。

`2.1 concurrent mode failure（并发更新失败）`
concurrent mode failure是在执行CMS GC的过程中同时业务线程将对象放入老年代，而此时老年代空间不足，这时CMS还没有机会回收老年带产生的，或者在做Minor GC的时候，新生代救助空间放不下，需要放入老年带，而老年带也放不下而产生的。

concurrent mode failure出现的频率，这可以通过-XX:+PrintGCDetails来观察，**当出现concurrent mode failure的现象时，就意味着此时JVM将继续采用Stop-The-World的方式来进行Full GC，这种情况下，CMS就没什么意义了**，造成concurrent mode failure的原因是当minor GC进行时，旧生代所剩下的空间小于Eden区域+From区域的空间，或者在CMS执行老年带的回收时有业务线程试图将大的对象放入老年带，**导致CMS在老年代的回收慢于业务对象对老年代内存的分配。**

优化办法：
解决这个问题的通用方法是调低触发CMS GC执行的阀值，CMS GC触发主要由CMSInitiatingOccupancyFraction值决定，默认情况是当老年代已用空间为92%时，即触发CMS GC，在出现concurrent mode failure的情况下，可考虑调小这个值，**提前CMS GC的触发，以保证旧生代有足够的空间。**

CMSInitiatingOccupancyFraction值与Xmn的关系公式

前面介绍了promontion faild产生的原因是EDEN空间不足的情况下将EDEN与From Survivor中的存活对象存入To Survivor区时,To Survivor区的空间不足，再次晋升到Old Gen区，而Old Gen区内存也不够的情况下产生了promontion faild从而导致stop-the-world Full GC.



3. 总结：
Minor GC后， 救助空间容纳不了剩余对象，将要放入老年带，老年带有碎片或者不能容纳这些对象，就产生了concurrent mode failure, 然后进行stop-the-world的Serial Old收集器。

解决办法：-XX:UseCMSCompactAtFullCollection -XX:CMSFullGCBeforeCompaction=5 或者 调大新生代或者救助空间

CMS是和业务线程并发运行的，在执行CMS的过程中有业务对象需要在老年带直接分配，例如大对象，但是老年带没有足够的空间来分配，所以导致concurrent mode failure, 然后需要进行stop-the-world的Serial Old收集器。

解决办法：+XX:CMSInitiatingOccupancyFraction，调大老年带的空间，+XX:CMSMaxAbortablePrecleanTime

总结一句话：使用标记整理清除碎片和提早进行CMS操作。




## G1收集器？

Garbage First收集器是当前收集器技术发展的最前沿成果。jdk 1.6_update14中提供了 g1收集器。
G1收集器是基于标记-整理算法的收集器，它避免了内存碎片的问题。
可以非常精确控制停顿时间，既能让使用者明确指定一个长度为M毫秒的时间片段内，消耗在垃圾收集 上的时间不多超过N毫秒，这几乎已经是实时java(rtsj )的垃圾收集器特征了。
**G1收集器是如何改进收集方式的？**
极力避免全区域垃圾收集，之前的收集器进行收集的范围都是整个新生代或者老年代。而g1将整个Java 堆(包括新生代、老年代)划分为多个大小固定的独立区域，并且跟踪这些区域垃圾堆积程度，**维护一 个优先级，每次根据允许的收集时间，优先回收垃圾最多的区域**。从而获得更高的效率。

## 内存分配和回收策略

java的自动内存管理解决了两个问题：内存的分配和回收内存。
内存的分配，在大方向上讲，就是在堆上分配（也可能通过JIT编译后被拆散为标量类型并间接在栈上分配<标量替换> )，主要分配在Eden区，如果开启了本地线程分配缓冲，则按线程优分配在TLAB上。少数情况会直接分配在老年代，分配情况取决于多个因素。

**对象优先在Eden区分配**
如果Eden区空间不够，则发起一次MinorGC。

**大对象直接进入老年代**
大对象是指，需要大量连续内存空间的Java对象（比如很长的字符串，数组）。大对象对虚拟机来说是一个坏消息（尤其是短命的大对象），会导致虚拟机还有很多内存，但是不得不提前触发GC以获取足够的内存去安置它们。

**长期存活的对象进入老年代**
对象头有个分代年龄还记得么？当对象经过一次MinorGC后仍存活且能被Survior容纳，这个Age就+1，当到一定年龄时（默认15），会晋升到老年代。这个年龄可以通过参数设置

-XX:MaxTenuringThreshold

**动态对象年龄判定**
当然为了更好的适应不同程序的内存情况，虚拟机并不是必须到了最大年龄才进入老年代，如果Survivor空间中相同年龄所有对象的大小总和大于Survivor空间的一半，那么年龄大于或等于该年龄的对象就可以直接进入老年代。

**内存分配担保**
在发生Minor GC之前，虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间。如果这个条件成立，那么Minor GC可以确保是安全的。如果不成立，则虚拟机会查看HandlerPromotionFailure设置是否允许担保失败。如果允许，那么会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小。如果大于，将尝试着进行一次Minor GC，尽管这次GC是有风险的。如果小于，或者HandlerPromotionFailure设置不允许冒险，那这时也要改为进行一次Full GC了。

冒险是冒了什么样的风险？
前面提到过，新生代使用复制收集算法，但是为了内存利用率。只使用其中一个Survivor空间来作为轮换备份，因此当出现大量对象在Minor GC后仍然存活的情况（最极端的情况是内存回收之后，新生代中所有的对象都存活），就需要老年代进行分配担保，把Survivor无法容纳的对象直接进入老年代。老年代要进行这样的担保，前提是老年代本身还有容纳这些对象的剩余空间，一共有多少对象存活下来在实际完成内存回收之前是无法明确知道的，所以只好取之前每一次回收晋升到老年代对象容量的平均大小值作为经验值，与老年代的剩余空间进行比较，决定是否进行Full GC来让老年代腾出更多空间。

取平均值进行比较其实仍然是一种动态概率的手段，也就是说，如果某次Minor GC存活后的对象突增，远远高于平均值的话，依然会导致担保失败。如果出现HandlerPromotionFailure失败，那就只好在失败后重新发起一次FULL GC。虽然担保失败时绕的圈子是最大的，但大部分情况下都还是将HandlerPromotionFailure开关打开，避免Full GC过于频繁。





## 并发标记的算法

怎么样才能进行并发标记，比较复杂。CMS用的是三色标记外加lncremental Update算法。而G1用的是三色算法加SATB算法，主要配合它的Rset来进行。ZGC用的是颜色指针。他们之间就是数量级的一个提升。





# 工具和相关命令



## 查看虚拟机进程状况的工具？

jps (Jvm process status tool ),他的功能与 ps 类似。
可以列出正在运行的虚拟机进程，并显示执行主类(Main Class,main()函数所在的类)的名称，以及执行进程的本地虚拟机的唯一 ID。
语法：jps [options] [hostid]
-q主输出1 vmid,省略主类的名称
-m输出虚拟机进程启动时传递给主类main()函数的参数
-1输出主类全名，如果进程执行是Jar包，输出Jar路径
-v输出虚拟机进程启动时JVM参数

jinfo(Configuration Info for Java)的作用是实时地查看和调整虚拟机的各项参数。

使用jps命令的-v参数可以查看虚拟机启动时显示指定的参数列表。

jinfo 语法:jinfo [option] pid



![image-20211221104814106](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211221104814106.png)





## 内存映像工具？

jmap(Memory Map for Java)命令用于生成堆转储快照(一般称为heapdump或dump文件)。

语法：jmap [option] vmid

它还可以查询finalize执行队列，Java堆和永久代的详细信息，如果空间使用率、当前用的是哪种收集器 等。                                  •

■ -dump生成Java堆转储快照，其中1 ive自参数说明是否只dump出存活对象

■ -finalizerinfo显示在F -Queue中等待Finalizer线程执行finalize方法的对象。只在Linux/Solaris平台 下有效

--heap显示Java堆详细信息，如使用哪种回收器、参数配置、分代状况。

--histo显示堆中对象统计信息、包括类、实例数量和合计容量。

--F当虚拟机进程对-dump选项没有响应时，可使用这个选项强制生成dump快照。

1.Jmap是一个可以输出所有内存中对象的工具，甚至可以将VM 中的heap，以二进制输出成文本。打印出某个java进程（使用pid）内存内的，所有‘对象’的情况（如：产生那些对象，及其数量）。

使用方法 jmap -histo pid。如果使用SHELL ,可采用jmap -histo pid>a.log日志将其保存到文件中，在一段时间后，使用文本对比工具，可以对比出GC回收了哪些对象。jmap -dump:format=b,file=outfile 3024可以将3024进程的内存heap输出出来到outfile文件里，再配合MAT（内存分析工具）。

64位机上使用需要使用如下方式：

jmap -J-d64 -heap pid

2、命令格式

l  jmap [ option ] pid

l  jmap [ option ] executable core

l  jmap [ option ] [server-id@]remote-hostname-or-IP

3、参数说明

1)、options： 

l  executable :产生core dump的java可执行程序;

l  core 将被打印信息的core dump文件;

l  remote-hostname-or-IP 远程debug服务的主机名或ip;

l  server-id 唯一id,假如一台主机上多个远程debug服务;



## 堆栈跟踪工具？ •.'



jstack(Stack Trace for Java)命令用于生成虚拟机当前时刻的线程快照(一般称为thread dump或 javacore文件)。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照的 主要目的是定位线程出现长时间停顿的原因。

jstack [option] vmid

-F当正常输出的请求不被响应时，强制输出线程堆栈

-l除堆栈外，显示关于锁的附加信息

-m如果调用本地方法的花，可以显示C/C++的堆栈



## 除了命令行，还有什么可视化工具？



JConsole和VisualVM，这两个工具是JDK的正式成员。



## 双亲委派机制

![image-20211221012430629](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211221012430629.png)

如果一个类加载器收到了类加载请求，它并不会自己先去加载，**而是把这个请求委托给父类的**
**加载器去执行，如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归**，请求最终
将到达顶层的启动类加载器，如果父类加载器可以完成类加载任务，就成功返回，倘若父类加载
器无法完成此加载任务，子加载器才会尝试自己去加载，这就是双亲委派模式，即每个儿子都不
愿意干活，每次有活就丢给父亲去干，直到父亲说这件事我也干不了时，儿子自己想办法去完
成，这不就是传说中的双亲委派模式。

一个class文件需要被load内存时候是这样的:

如果应用程序类加载器收到了一个类加载的请求，会先给扩展类加载器，然后再给启动类加载器，如果启动类加载器无法完成这个类加载的请求，再返回给扩展类加载器，如果扩展类加载器也无法完成，就返回给应用类加载器。

任何一个class，加入你自定义了ClassLoader，这是时候就先尝试去自定义里面找，他内部维护着缓存，说你有没有已经帮我加载进来了，如果加载进来一遍就不需要加载第二遍，如果没加载进来，就赶
紧把我加载进来。
他如果没有在自己的自定义缓存没找到的话，他并不是直接加载这块内存，他会去他的父亲application父加载器，说爸爸你有没有把这个类加载进来啊，这时候他就会去他的缓存里面找有没有这个类啊，如果有返回，如果没有委托给他的父亲Extension，如果有返回，如果没有委托给他的父亲Bootstrap ，有就返回，如果都没有就往回在委托Extension我这没有你去加载，Extension说我只负责我扩展jar包部分，你的类我找不到啊，那麻烦application去加载，application说我只负责加载classpath指定内容啊，其他的我不知道找不到，然后委托ClassLoader去找，整个过程是经过了一圈转了一圈，才真正把这个类加载进来，当我们能够把这个类加载进来的时候叫做成功，如果加载不进来，抛异常ClassNoffound找不到，这就叫做双亲委派。

**好处:」**

- 说这个问题前我要先和大家说一个概念，**「Jvm 中类的唯一性是由类本身和加载这个类的类加载器决定的」**，简单的说，如果有个a类，如果被两个不同的类加载器加载，那么他们必不相等。 你看到这里会不会想到所有类的父类都是 Object 是怎么实现的了吗？ 是因为无论哪一个类加载器加载 Object 类，都会交给最顶层的启动类加载器去加载，这样就**「保证了 Object 类在 Jvm 中是唯一的」**。

：自己写的String.class类不会被加载，这样便可以防止核心API库被随意篡改
避免类的重复加载：当父亲已经加载了该类时，就没有必要子ClassLoader再加载一次
5、

![image-20210906160438006](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906160438006.png)







## 避免ABA问题

![image-20210906160752973](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906160752973.png)



# java语言怎么实现跨平台的



![image-20210811175511810](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811175511810.png)



# 垃圾收集器

说一下 JVM 有哪些垃圾回收器？
如果说垃圾收集算法是内存回收的方法论，那么垃圾收集器就是内存回收的具体实现。下图展示了7种作用于不同分代的收集器，其中用于回收新生代的收集器包括Serial、PraNew、ParallelScavenge，回收老年代的收集器包括Serial Old、Parallel Old、CMS，还有用于回收整个Java堆的G1收集器。不同收集器之间的连线表示它们可以搭配使用。

![image-20211012152121652](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211012152121652.png)

Serial收集器（复制算法): 新生代单线程收集器，标记和清理都是单线程，优点是简单高效；
ParNew收集器 (复制算法): 新生代收并行集器，实际上是Serial收集器的多线程版本，在多核CPU环境下有着比Serial更好的表现；
Parallel Scavenge收集器 (复制算法): 新生代并行收集器，追求高吞吐量，高效利用 CPU。吞吐量 = 用户线程时间/(用户线程时间+GC线程时间)，高吞吐量可以高效率的利用CPU时间，尽快完成程序的运算任务，适合后台应用等对交互相应要求不高的场景；
Serial Old收集器 (标记-整理算法): 老年代单线程收集器，Serial收集器的老年代版本；
Parallel Old收集器 (标记-整理算法)： 老年代并行收集器，吞吐量优先，Parallel Scavenge收集器的老年代版本；
CMS(Concurrent Mark Sweep)收集器（标记-清除算法）： 老年代并行收集器，以获取最短回收停顿时间为目标的收集器，具有高并发、低停顿的特点，追求最短GC回收停顿时间。
G1(Garbage First)收集器 (标记-整理算法)： Java堆并行收集器，G1收集器是JDK1.7提供的一个新收集器，G1收集器基于“标记-整理”算法实现，也就是说不会产生内存碎片。此外，G1收集器不同于之前的收集器的一个重要特点是：G1回收的范围是整个Java堆(包括新生代，老年代)，而前六种收集器回收的范围仅限于新生代或老年代。

新生代收集器：Serial、ParNew 、Parallel Scavenge
老年代收集器： CMS 、Serial Old、Parallel Old
整堆收集器： G1 ， ZGC (因为不涉年代不在图中)



![image-20210811185101551](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185101551.png)

![image-20210811185145162](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185145162.png)

如何选择垃圾收集器？
1. 如果你的堆大小不是很大（比如 100MB ），选择串行收集器一般是效率最高的。
参数： -XX:+UseSerialGC 。
2. 如果你的应用运行在单核的机器上，或者你的虚拟机核数只有单核，选择串行收集器依然是合适的，这时候启用一些并行收集器没有任何收益。
参数： -XX:+UseSerialGC 。
3. 如果你的应用是“吞吐量”优先的，并且对较长时间的停顿没有什么特别的要求。选择并行收集器是比较好的。
参数： -XX:+UseParallelGC 。

 4.如果你的应用对响应时间要求较高，想要较少的停顿。甚至 1 秒的停顿都会引起大量的请求失败，那么选择G1 、ZGC 、CMS 都是合理的。虽然这些收集器的 GC 停顿通常都比较短，但它需要一些额外的资源去处理这些工作，通常吞吐量会低一些。
参数：
-XX:+UseConcMarkSweepGC 、
-XX:+UseG1GC 、
-XX:+UseZGC 等。



# 调优前需知的一些概念

![image-20211222214827005](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211222214827005.png)



# jvm参数设置



![image-20211222214627904](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211222214627904.png)



![image-20210811185224781](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185224781.png)

![image-20210811185241503](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185241503.png)

![image-20210811185302985](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185302985.png)

# jvm调优的一些原则

![image-20210906161029348](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906161029348.png)

 

  Q：为什么崩溃前垃圾回收的时间越来越长？

  A:根据内存模型和垃圾回收算法，垃圾回收分两部分：内存标记、清除（复制），标记部分只要内存大小固定时间是不变的，变的是复制部分，因为每次垃圾回收都有一些回收不掉的内存，所以增加了复制量，导致时间延长。所以，垃圾回收的时间也可以作为判断内存泄漏的依据

  Q：为什么Full GC的次数越来越多？

  A：因此内存的积累，逐渐耗尽了年老代的内存，导致新对象分配没有更多的空间，从而导致频繁的垃圾回收

  Q:为什么年老代占用的内存越来越大？

  A:因为年轻代的内存无法被回收，越来越多地被Copy到年老代



# 你知道哪些JVM调优参数？

在JVM启动参数中，可以设置跟内存、垃圾回收相关的一些参数设置，默认情况不做任何设置JVM会工作的很好，但对一些配置很好的Server和具体的应用必须仔细调优才能获得最佳性能。通过设置我们希望达到一些目标：

- **GC的时间足够的小**
- **GC的次数足够的少**
- **发生Full GC的周期足够的长**

 前两个目前是相悖的，要想GC时间小必须要一个更小的堆，要保证GC次数足够少，必须保证一个更大的堆，我们只能取其平衡。

  （1）针对JVM堆的设置，一般可以通过-Xms -Xmx限定其最小、最大值，**为了防止垃圾收集器在最小、最大之间收缩堆而产生额外的时间，我们通常把最大、最小设置为相同的值**
  （2）**年轻代和年老代将根据默认的比例（1：2）分配堆内存**，可以通过调整二者之间的比率NewRadio来调整二者之间的大小，也可以针对回收代，比如年轻代，通过 -XX:newSize -XX:MaxNewSize来设置其绝对大小。同样，为了防止年轻代的堆收缩，我们通常会把-XX:newSize -XX:MaxNewSize设置为同样大小

  （3）年轻代和年老代设置多大才算合理？这个我问题毫无疑问是没有答案的，否则也就不会有调优。我们观察一下二者大小变化有哪些影响

- **更大的年轻代必然导致更小的年老代，大的年轻代会延长普通GC的周期，但会增加每次GC的时间；小的年老代会导致更频繁的Full GC**
- **更小的年轻代必然导致更大年老代，小的年轻代会导致普通GC很频繁，但每次的GC时间会更短；大的年老代会减少Full GC的频率**
- 如何选择应该依赖应用程序**对象生命周期的分布情况**：如果应用存在大量的临时对象，应该选择更大的年轻代；如果存在相对较多的持久对象，年老代应该适当增大。但很多应用都没有这样明显的特性，在抉择时应该根据以下两点：（A）本着Full GC尽量少的原则，让年老代尽量缓存常用对象，JVM的默认比例1：2也是这个道理 （B）通过观察应用一段时间，看其他在峰值时年老代会占多少内存，在不影响Full GC的前提下，根据实际情况加大年轻代，比如可以把比例控制在1：1。但应该给年老代至少预留1/3的增长空间

 （4）**在配置较好的机器上（比如多核、大内存），可以为年老代选择并行收集算法**： **-XX:+UseParallelOldGC** ，默认为Serial收集

 （5）线程堆栈的设置：每个线程默认会开启1M的堆栈，用于存放栈帧、调用参数、局部变量等，对大多数应用而言这个默认值太了，一般256K就足用。理论上，在内存不变的情况下，减少每个线程的堆栈，可以产生更多的线程，但这实际上还受限于操作系统。

 （4）可以通过下面的参数打Heap Dump信息

- -XX:HeapDumpPath
- -XX:+PrintGCDetails
- -XX:+PrintGCTimeStamps
- -Xloggc:/usr/aaa/dump/heap_trace.txt

  通过下面参数可以控制OutOfMemoryError时打印堆的信息

- -XX:+HeapDumpOnOutOfMemoryError

 请看一下一个时间的Java参数配置：（服务器：Linux 64Bit，8Core×16G）

 

 **JAVA_OPTS="$JAVA_OPTS -server -Xms3G -Xmx3G -Xss256k -XX:PermSize=128m -XX:MaxPermSize=128m -XX:+UseParallelOldGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/aaa/dump -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/usr/aaa/dump/heap_trace.txt -XX:NewSize=1G -XX:MaxNewSize=1G"**

经过观察该配置非常稳定，每次普通GC的时间在10ms左右，Full GC基本不发生，或隔很长很长的时间才发生一次

通过分析dump文件可以发现，每个1小时都会发生一次Full GC，经过多方求证，只要在JVM中开启了JMX服务，JMX将会1小时执行一次Full GC以清除引用，关于这点请参考附件文档。

「堆栈内存相关」
-Xms 设置初始堆的大小
-Xmx 设置最大堆的大小
-Xmn 设置年轻代大小，相当于同时配置-XX:NewSize和-XX:MaxNewSize为一样的值
-Xss 每个线程的堆栈大小
-XX:NewSize 设置年轻代大小(for 1.3/1.4)
-XX:MaxNewSize 年轻代最大值(for 1.3/1.4)
-XX:NewRatio 年轻代与年老代的比值(除去持久代)
-XX:SurvivorRatio Eden区与Survivor区的的比值
-XX:PretenureSizeThreshold 当创建的对象超过指定大小时，直接把对象分配在老年代。
-XX:MaxTenuringThreshold设定对象在Survivor复制的最大年龄阈值，超过阈值转移到老年代

「垃圾收集器相关」
-XX:+UseParallelGC：选择垃圾收集器为并行收集器。
-XX:ParallelGCThreads=20：配置并行收集器的线程数
-XX:+UseConcMarkSweepGC：设置年老代为并发收集。
-XX:CMSFullGCsBeforeCompaction=5 由于并发收集器不对内存空间进行压缩、整理，所以运行一段时间以后会产生“碎片”，使得运行效率降低。此值设置运行5次GC以后对内存空间进行压缩、整理。
-XX:+UseCMSCompactAtFullCollection：打开对年老代的压缩。可能会影响性能，但是可以消除碎片

「辅助信息相关」
-XX:+PrintGCDetails 打印GC详细信息
-XX:+HeapDumpOnOutOfMemoryError让JVM在发生内存溢出的时候自动生成内存快照,排查问题用
-XX:+DisableExplicitGC禁止系统System.gc()，防止手动误触发FGC造成问题.
-XX:+PrintTLAB 查看TLAB空间的使用情况

# 调优命令

jstat命令

Jstat是JDK自带的一个轻量级小工具。全称“Java Virtual Machine statistics monitoring tool”，它位于java的bin目录下，主要利用JVM内建的指令对Java应用程序的资源和性能进行实时的命令行的监控，包括了对Heap size和垃圾回收状况的监控。可见，Jstat是轻量级的、专门针对JVM的工具，非常适用。

jstat工具特别强大，有众多的可选项，详细查看堆内各个部分的使用量，以及加载类的数量。使用时，需加上查看进程的进程id，和所选参数。参考格式如下：

jstat -options 

可以列出当前JVM版本支持的选项，常见的有

l  class (类加载器) 
l  compiler (JIT) 
l  gc (GC堆状态) 
l  gccapacity (各区大小) 
l  gccause (最近一次GC统计和原因) 
l  gcnew (新区统计)
l  gcnewcapacity (新区大小)
l  gcold (老区统计)
l  gcoldcapacity (老区大小)
l  gcpermcapacity (永久区大小)
l  gcutil (GC统计汇总)
l  printcompilation (HotSpot编译统计)
1、jstat –class\<pid> : 显示加载class的数量，及所占空间等信息。

jstat –class\<pid> : 显示加载class的数量，及所占空间等信息。

jstat -gc \<pid>: 可以显示gc的信息，查看gc的次数，及时间。

**jstat -gccapacity \<pid>:**可以显示，VM内存中三代（young,old,perm）对象的使用和占用大小

jstat -gcutil\<pid>:统计gc信息



# 什么情况下需要jvm调优

![image-20210906160859031](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906160859031.png)

#  OOM 常见原因及解决方案

当 [JVM](https://so.csdn.net/so/search?q=JVM&spm=1001.2101.3001.7020) 内存严重不足时，就会抛出 java.lang.OutOfMemoryError 错误。

常见造成oom的原因
1.内存泄露造成
2.加载的文件或者图片过大造成
三：解决方案
内存泄露是造成内存溢出的一个原因，所以避免内存泄露的那些方法都适用于内存溢出比如及时回收无用的引用对象，资源回收等…

对于图片方面，如果加载过大的图片要将图片转出bitmap要压缩，异步加载图片（需要的图片或者说页面要显示的图片先加载出来），在listview中consView和Viewholder 一起使用


## **1、Java heap space**

当[堆内存](https://so.csdn.net/so/search?q=堆内存&spm=1001.2101.3001.7020)（Heap Space）没有足够空间存放新创建的对象时，就会抛出 `java.lang.OutOfMemoryError:Javaheap space` 错误（根据实际生产经验，可以对程序日志中的 OutOfMemoryError 配置关键字告警，一经发现，立即处理）。

原因分析

`Javaheap space` 错误产生的常见原因可以分为以下几类：

1、请求创建一个超大对象，通常是一个大数组。

2、超出预期的访问量/数据量，通常是上游系统请求流量飙升，常见于各类促销/秒杀活动，可以结合业务流量指标排查是否有尖状峰值。

3、过度使用终结器（Finalizer），该对象没有立即被 GC。

4、[内存](https://so.csdn.net/so/search?q=内存&spm=1001.2101.3001.7020)泄漏（Memory Leak），大量对象引用没有释放，JVM 无法对其自动回收，常见于使用了 File 等资源没有回收。

解决方案

针对大部分情况，通常只需要通过 `-Xmx` 参数调高 JVM 堆内存空间即可。如果仍然没有解决，可以参考以下情况做进一步处理：

1、如果是超大对象，可以检查其合理性，比如是否一次性查询了数据库全部结果，而没有做结果数限制。

2、如果是业务峰值压力，可以考虑添加机器资源，或者做限流降级。

3、如果是内存泄漏，需要找到持有的对象，修改代码设计，比如关闭没有释放的连接。

## 2、GC overhead limit exceeded

当 Java 进程花费 98% 以上的时间执行 GC，但只恢复了不到 2% 的内存，且该动作连续重复了 5 次，就会抛出 `java.lang.OutOfMemoryError:GC overhead limit exceeded` 错误。简单地说，就是应用程序已经基本耗尽了所有可用内存， GC 也无法回收。

此类问题的原因与解决方案跟 `Javaheap space` 非常类似，可以参考上文。

----------------

用工具：

监视:
新建一个远程连接，添加JMX链接，链接上你会看到一些相关信息，可以对它进行监视，总共装了多少类进来，这个线程包括多少个线程，这个线程总运行多长的时间，具体有哪些线程。

![image-20220422112149138](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220422112149138.png)

，刚刚我们观察到会有很多的类，很多内存被占用
了。你只要用这种jvisualvm，是这种图形化看起来就方便多了。你点击了之后它会对远程的那台机器正在跑着的java进程内存，里面的信息给您导出来。你能看到我这个进程里面有那些个类，占了多少个
字节另外有多少个数量。BigDecimal这个类占了好多，多少个实例。看过这些信息之后就能由于那个类产生了OOM。

不过开一个JMX服务的话，会影响原来的运行效率。如果不用jvisulavm还可以用命令：有arthas工具可以用命令行

# **java内存溢出的几种原因和解决办法是什么？**

第一类内存溢出，也是大家认为最多，第一反应认为是的内存溢出，就是堆栈溢出：

那什么样的情况就是堆栈溢出呢？当你看到下面的关键字的时候它就是堆栈溢出了：

java.lang.OutOfMemoryError: ......java heap space.....

也就是当你看到heap相关的时候就肯定是堆栈溢出了，此时如果代码没有问题的情况下，适当调整-Xmx和-Xms是可以避免的，不过一定是代码没有问题的前提，为什么会溢出呢，要么代码有问题，要么访问量太多并且每个访问的时间太长或者数据太多，导致数据释放不掉，因为垃圾回收器是要找到那些是垃圾才能回收，这里它不会认为这些东西是垃圾，自然不会去回收了；这个溢出之前，可能系统会提前先报错关键字为：

java.lang.OutOfMemoryError:GC over head limit exceeded

这种情况是当系统处于高频的GC状态，而且回收的效果依然不佳的情况，就会开始报这个错误，这种情况一般是产生了很多不可以被释放的对象，有可能是引用使用不当导致，或申请大对象导致，但是java heap space的内存溢出有可能提前不会报这个错误，也就是可能内存就直接不够导致，而不是高频GC.

第二类内存溢出，PermGen的溢出，或者PermGen 满了的提示，你会看到这样的关键字：

关键信息为:

java.lang.OutOfMemoryError: PermGen space

原因：系统的代码非常多或引用的第三方包非常多、或代码中使用了大量的常量、或通过intern注入常量、或者通过动态代码加载等方法，导致常量池的膨胀，虽然JDK 1.5以后可以通过设置对永久带进行回收，但是我们希望的是这个地方是不做GC的，它够用就行，所以一般情况下今年少做类似的操作，所以在面对这种情况常用的手段是：PermGen的溢出和-XX:MaxPermSize的大小。

第三类内存溢出：在使用ByteBuffer中的allocateDirect()的时候会用到，很多javaNIO的框架中被封装为其他的方法

溢出关键字：

java.lang.OutOfMemoryError: Direct buffer memory
**如果你在直接或间接使用了ByteBuffer中的allocateDirect方法的时候，而不做clear的时候就会出现类似的问题**，常规的引用程序IO输出存在一个内核态与用户态的转换过程，也就是对应直接内存与非直接内存，如果常规的应用程序你要将一个文件的内容输出到客户端需要通过OS的直接内存转换拷贝到程序的非直接内存（也就是heap中），然后再输出到直接内存由操作系统发送出去，而直接内存就是由OS和应用程序共同管理的，而非直接内存可以直接由应用程序自己控制的内存，jvm垃圾回收不会回收掉直接内存这部分的内存，所以要注意了哦。

如果经常有类似的操作，可以考虑设置参数：-XX:MaxDirectMemorySize

第四类内存溢出错误：

溢出关键字：

java.lang.StackOverflowError

这个参数直接说明一个内容，就是-Xss太小了，我们申请很多局部调用的栈针等内容是存放在用户当前所持有的线程中的，线程在jdk 1.4以前默认是256K，1.5以后是1M，如果报这个错，只能说明-Xss设置得太小，当然有些厂商的JVM不是这个参数，本文仅仅针对Hotspot VM而已；不过在有必要的情况下可以对系统做一些优化，使得-Xss的值是可用的。

第五类内存溢出错误：

溢出关键字：





java.lang.OutOfMemoryError: unable to create new native thread

上面第四种溢出错误，已经说明了线程的内存空间，其实线程基本只占用heap以外的内存区域，也就是这个错误说明除了heap以外的区域，无法为线程分配一块内存区域了，这个要么是内存本身就不够，要么heap的空间设置得太大了，导致了剩余的内存已经不多了，而由于线程本身要占用内存，所以就不够用了。

第六类内存溢出：

溢出关键字

java.lang.OutOfMemoryError: request {} byte for {}out of swap

这类错误一般是由于地址空间不够而导致。

六大类常见溢出已经说明JVM中99%的溢出情况，要逃出这些溢出情况非常困难，除非一些很怪异的故障问题会发生，比如由于物理内存的硬件问题，导致了code cache的错误（在由byte code转换为native code的过程中出现，但是概率极低），这种情况内存 会被直接crash掉，类似还有swap的频繁交互在部分系统中会导致系统直接被crash掉，OS地址空间不够的话，系统根本无法启动，呵呵；JNI的滥用也会导致一些本地内存无法释放的问题，所以尽量避开JNI；socket连接数据打开过多的socket也会报类似：IOException: Too many open files等错误信息。





# 线上排查问题的一般流程

![image-20210906161203450](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906161203450.png)



程序开多少线程合适

![image-20210906161230052](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906161230052.png)







# 老年代问题

![image-20210811185433614](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811185433614.png)

![image-20210811201358426](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210811201358426.png)

