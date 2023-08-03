 管理库



# 介绍

从概念上来说，数据库是文件的集合，是依照某种数据模型组织起来并存放于二级存储器中的数据集合；数据库实例是程序，是位于用户与操作系统之间的一层数据管理软件，用户对数据库数据的任何操作，包括数据库定义、数据查询、数据维护、数据库运行控制等都是在数据库实例下进行的，应用程序只有通过数据库实例才能和数据库打交道。 如果这样讲解后读者还是不明白，那这里再换一种更为直白的方式来解释：数据库是由一个个文件组成（一般来说都是二进制的文件）的，要对这些文件执行诸如SELECT、INSERT、UPDATE和DELETE之类的数据库操作是不能通过简单的操作文件来更改数据库的内容，需要通过数据库实例来完成对数据库的操作。所以，用户把Oracle、SQL Server、MySQL简单地理解成数据库可能是有失偏颇的，虽然在实际使用中并不会这么强调两者之间的区别。 

MySQL由以下几部分组成： 

连接池组件管理服务和工具组件 

SQL接口组件 

查询分析器组件 

优化器组件 

缓冲（Cache）组件 

插件式存储引擎 

物理文件

MySQL数据库区别于其他数据库的最重要的一个特点就是其插件式的表存储引擎。MySQL插件式的存储引擎架构提供了一系列标准的管理和服务支持，这些标准与存储引擎本身无关，可能是每个数据库系统本身都必需的，如SQL分析器和优化器等，而存储引擎是底层物理结构的实现，每个存储引擎开发者可以按照自己的意愿来进行开发

主键–唯一标识一条记录，不能有重复的， 不允许为空
外键–表的外键是另一表的主键, 外键可以有重复的, 可以是空值
索引–该字段没有重复值，但可以有一个空值
作用：
主键–用来保证数据完整性
外键–用来和其他表建立联系用的
索引–是提高查询排序的速度

个数：
主键–主键只能有一个
外键–一个表可以有多个外键
索引–一个表可以有多个唯一索引



# sql

🔷show create table xxx(表名) 查看创建表语句

![image-20210830121810684](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830121810684.png)

可以两个列组合成一个主键，此时在添加时只有两个列的值都相同才会报错，如果只有一个列值相同而另一个不同，仍然可以插入数据

删除唯一键
一个表中允许存在多个唯一键：

删除基本语法：alter table 表名 drop index 唯一键名字；

index代表索引，唯一键是索引的一种（提升查询效率 

主键不可以为null，唯一键NULL可以有多个

🔷SHOW CONNECTION_ID（）返回MySQL服务器当前连接的次数，每个连接都有各自唯一的ID。

🔷SHOW PROCESSLIST命令的输出结果显示有哪些线程在运行，不仅可以查看当前所有的连接数，还可以查看当前的连接状态，帮助识别出有问题的查询语句等。 若是root账号，则能看到所有用户的当前连接。若是其他普通账号，则只能看到自己占用的连接。SHOW PROCESSLIST；只列出前100条，若想全部列出，则可使用SHOW FULL PROCESSLIST命令。 

🔷SELECT USER(),CURRENT_USER(),SYSTEM_USER() 

这几个函数返回当前被MySQL服务器验证的用户名和主机名组合。这个值符合确定当前登录用户存取权限的MySQL账户。一般情况下，这几个函数的返回值是相同的。 

![image-20210902175818021](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210902175818021.png)

# SQL Select 语句完整的执行顺序：

1、from 子句组装来自不同数据源的数据；
2、where 子句基于指定的条件对记录行进行筛选；
3、group by 子句将数据划分为多个分组；
4、使用聚集函数进行计算；
5、使用having 子句筛选分组；
6、计算所有的表达式；
7、select 的字段；
8、使用order by 对结果集进行排序。
SQL 语言不同于其他编程语言的最明显特征是处理代码的顺序。在大多数据库语言中，代码按编码顺序被处理。但在SQL 语句中，第一个被处理的子句式FROM，而不是第一出现的SELECT。SQL 查询处理的步骤序号：
(1) FROM <left_table>
(2) <join_type> JOIN <right_table>
(3) ON <join_condition>
(4) WHERE <where_condition>
(5) GROUP BY <group_by_list>

(6) WITH {CUBE | ROLLUP}
(7) HAVING <having_condition>
(8) SELECT
(9) DISTINCT
(9) ORDER BY <order_by_list>
(10) <TOP_specification> <select_list>
以上每个步骤都会产生一个虚拟表，该虚拟表被用作下一个步骤的输入。这些虚拟表对调用者(客户端应用程序或者外部查询)不可用。只有最后一步生成的表才会会给调用者。如果没有在查询中指定某一个子句，将跳过相应的步骤。

如果子查询结果只有单行可以用= 如果子查询结果有多行要用in



InnoDB页级别的压缩是MySQL 5.7的一个新特性，它补充了InnoDB的表级压缩，它们可以在同一个服务实例上并存。用户现在可以选择最适合他们使用场景的压缩方式，甚至基于不同的表选择不同的压缩方式。 对于压缩算法，目前支持Zlib和LZ4。当一个页被写入时，它就被指定的压缩算法压缩。压缩后的数据写到磁盘上，随后“hole punching”机制会在页的末尾处释放空块。如果压缩失败，数据则被原样写入。 InnoDB现在也支持32KB和64KB的页大小设置，这对于页级的压缩来说是一个很好的补充。一般来说，更大的页通常会增加冗余的数据量。MySQL 5.7增加了用户可配置填充因子和页合并抑制的新特性，这样可以让InnoDB更好地使用存储空间。 





# 字符集

MySQL字符集与字符序 不同的字符集支持不同地区的字符，例如latin1支持西欧字符、希腊字符等，gbk支持中文简体字符，big5支持中文繁体字符，utf8几乎支持世界上所有国家的字符。每种字符集占用的存储空间不相同。由于希腊字符数较少，占用一个字节（8位）的存储空间即可表示所有的latin1字符；中文简体字符较多，占用两个字节（16位）的存储空间才可以表示所有的gbk字符；utf8字符数最多，通常需要占用三个字节（24位）的存储空间才可以表示世界上所有国家的所有字符（例如中文简体、中文繁体、阿拉伯文、俄文等）。









# 子查询

按结果集的行列数不同分为4种
标量子查询（结果集只有一行一列）
列子查询（结果集只有一列多行）
行子查询（结果集有一行多列）
表子查询（结果集一般为多行多列）

按子查询出现在主查询中的不同位置分
select后面：仅仅支持标量子查询。
from后面：支持表子查询。
where或having后面：支持标量子查询（单列单行）、列子查询（单列多行）、行子查询（多列多行）
exists后面（即相关子查询）：表子查询（多行、多列
先创建五个表：
departments 部门表
employees 员工信息表
jobs 职位信息表
locations 位置表（部门表中会用到）
job_grades 薪资等级表

## select后面的子查询
子查询位于select后面的，仅仅支持标量子查询。
1.查询每个部门员工个数：
a:部门

```sql
SELECT
a.*,
(SELECT count(*)
FROM employees b
WHERE b.department_id = a.department_id) AS 员工个数
FROM departments a;

```

.查询员工号=102的部门名称

```sql
SELECT (SELECT a.department_name
FROM departments a, employees b
WHERE a.department_id = b.department_id
AND b.employee_id = 102) AS 部门名

```

## from后面的子查询

将子查询的结果集充当一张表，要求必须起别名，否者这个表找不到。
然后将真实的表和子查询结果表进行连接查询。

```sql
-- 查询每个部门平均工资
SELECT
department_id,
avg(a.salary)
FROM employees a
GROUP BY a.department_id;
-- 薪资等级表
SELECT *
FROM job_grades
#合并上面两句话

SELECT
t1.department_id,
sa AS '平均工资',
t2.grade_level
FROM (SELECT
department_id,
avg(a.salary) sa
FROM employees a
GROUP BY a.department_id) t1, job_grades t2
WHERE
t1.sa BETWEEN t2.lowest_sal AND t2.highest_sal;

```

where或having后面，
可以使用

标量子查询（单行单列行子查询）
列子查询（单列多行子查询）
行子查询（一行多列）
列子查询，一般搭配着多行操作符使用：
any或者some：和子查询返回的“某一个值”比较，比如a>some(10,20,30)，a大于子查询中任意一个即可，a大于子查询中最小值即可，等同于a>min(10,20,30)。
all：和子查询返回的“所有值”比较，比如a>all(10,20,30)，a大于子查询中所有值，换句话说，a大于子查询中最大值即可满足查询条件，等同于a>max(10,20,30);
子查询的执行优先于主查询执行，因为主查询的条件用到了子查询的结果。
如：

```sql
SELECT *
FROM employees a
WHERE a.salary > (SELECT salary
FROM employees
WHERE last_name = "abc")

```

多个标量子查询：
返回job_id与141号员工相同，salary比143号员工多的员工、姓名、job_id和工资

```sql
select
 a.name,
 a.job_id,
 a.salary
 from employees a where (SELECT job_id
FROM employees
WHERE employee_id = 141) and a.salary>(select salary from employees where job_id == 143)

```







一些例子：

![image-20210825212410136](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210825212410136.png)

![image-20210825212427095](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210825212427095.png)

![image-20210825212448820](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210825212448820.png)

![image-20210830120606442](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830120606442.png)

![image-20210830120623133](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830120623133.png)





如果子查询中仅仅使用了自己定义的数据源，这种查询是**非相关子查询**。非相关子查询是独立于外部查询的子查询，子查询总共执行一次，执行完毕后将值传递给主查询。 **如果子查询中使用了主查询的数据源，这种查询是相关子查询**，此时主查询的执行与相关子查询的执行相互依赖。 

使用关键字EXISTS时，内层查询语句不返回查询的记录。而是返回一个真假值。如果内层查询语句查询到满足条件的记录，就返回一个真值（true），否则将返回一个假值（false）。当返回的值为true时，外层查询语句将进行查询；当返回的值为false时，外层查询语句不进行查询或者查询不出任何记录。

例如，检索没有申请选修课的教师的信息，还可以使用下面的SQL语句。该示例中的子查询是一个相关子查询。

```sql
 select * from teacher where not exists ( select * from course where course.teacher_no=teacher.teacher_no ); 
```

统计没有任何学生选修的课程的信息，可以使用下面的SQL语句。 

```sql
select course.course_no,course_name,teacher_name,teacher_contact,0 from course join teacher on teacher.teacher_no=course.teacher_no where not exists ( select * from choose where course.course_no=choose.course_no ); 
```

下面使用子查询查询tb_book表中是否存在id值为27的记录，如果存在则查询tb_row表中的记录，如果不存在则不执行外层查询 

select * from tb_row where exists (select * from tb_book where id=27);

如果select * from tb_book where id=27查出来的结果为true则执行select * from tb_row

MySQL中为字段取别名的基本形式如下。 字段名 [AS] 别名 

查询tb_book表中row字段的值大于tb_row表中row字段最大值的记录，首先使用子查询，查询出tb_row表中row字段的值，然后使用ALL关键字（“>=ALL”表示大于等于所有值）判断，查询语句如下。 select books,row from tb_book where row>=ALL(select row from tb_row);
select user from tb_book
UNION
select user from tb_login;
结果显示，合并后将所有结果合并到了一起，**并去除了重复值**。 

union all不去除重复值

　带关键字ANY的子查询 

关键字ANY表示满足其中任意一个条件。使用关键字ANY时，只要满足内层查询语句返回的结果中的任意一个，就可以通过该条件来执行外层查询语句。 

查询tb_book表中row字段的值小于tb_row表中row字段最小值的记录，首先查询出tb_row表中row字段的值，然后使用关键字ANY（“<ANY”表示小于所有值）判断，查询语句如下。

 select books,row from tb_book where row<ANY(select row from tb_row); 



# row_number() OVER

[简单的说row_number()从1开始，为每一条分组记录返回一个数字，这里的ROW_NUMBER() OVER (ORDER BY xlh DESC) 是先把xlh列降序，再为降序以后的每条xlh记录返回一个序号。 ](https://jingyan.baidu.com/article/javascript:;)

row_number() OVER (PARTITION BY COL1 ORDER BY COL2) 表示根据COL1分组，在分组内部根据 COL2排序，而此函数计算的值就表示每组内部排序后的顺序编号（组内连续的唯一的)

数据显示为

[![img](https://images2017.cnblogs.com/blog/1128165/201712/1128165-20171212092441259-1352521352.png)](http://jingyan.baidu.com/album/9989c74604a644f648ecfef3.html?picindex=2)

需求：根据部门分组，显示每个部门的工资等级

预期结果：

[![img](https://images2017.cnblogs.com/blog/1128165/201712/1128165-20171212092441384-633943197.png)](http://jingyan.baidu.com/album/9989c74604a644f648ecfef3.html?picindex=3)

SQL脚本：

SELECT *, Row_Number() OVER (partition by deptid ORDER BY salary desc) rank FROM employee



# 为什么要用is null而不是= null

-- 查询NULL 时出错的SQL 语句
SELECT *
FROM tbl_A
WHERE col_1 = NULL;
通过这条 SQL 语句，我们无法得到正确的结果。因为正确的写法是
col_1 IS NULL 。这和刚学 C 语言时写出的 if (hoge = 0) 的错
误非常相似。那么为什么用“＝”去进行比较会失败呢？表示相等关系
时用“＝”，这明明是我们在小学里就学过的常识。
这当然是有原因的。那就是，对 NULL 使用比较谓词后得到的结果总
是 unknown 。而查询结果只会包含 WHERE 子句里的判断结果为 true
的行，不会包含判断结果为 false 和 unknown 的行。不只是等号，
对 NULL 使用其他比较谓词，结果也都是一样的。所以无论 col_1 是
不是 NULL ，比较结果都是 unknown 。
-- 以下的式子都会被判为 unknown
1 = NULL
2 > NULL
3 < NULL
4 <> NULL
NULL = NULL

那么，为什么对 NULL 使用比较谓词后得到的结果永远不可能为真
呢？这是因为，NULL 既不是值也不是变量。NULL 只是一个表示“没
有值”的标记，而比较谓词只适用于值。因此，对并非值的 NULL 使
用比较谓词本来就是没有意义的 。
可能有人会认为“难道 NULL 不是值吗？怎么会不是呢！？你说的话我不相信！”为了使他
们信服，这里特意引用一下 Codd 和 C.J. Date 的话以示权威。
“我们先从定义一个表示‘虽然丢失了，但却适用的值’的标记开始。我们把它叫作 AMark
。这个标记在关系数据库里既不被当作值（value），也不被当作变量
（variable）。”（E.F. Codd，The Relational Model for Database Management ：Version 2 ,P.173）
“关于 NULL 的很重要的一件事情是，NULL 并不是值。

# 几个常用操作符

在使用长的合法选项清单时，IN操作符的语法更清楚且更直观。 在使用IN时，计算的次序更容易管理（因为使用的操作符更少）。 IN操作符一般比OR操作符清单执行更快。 IN的最大优点是可以包含其他SELECT语句，使得能够更动态地建立WHERE子句。

📣 IN ：WHERE子句中用来指定要匹配值的清单的关键字，功能与OR 相当。 

📣 百分号（%）通配符 最常使用的通配符是百分号（%）。在搜索串中，%表示任何字符出现任意次数。 

重要的是要注意到，除了一个或多个字符外，%还能匹配0个字符。%代表搜索模式中给定位置的0个、1个或多个字符。 

📣下划线（\_）通配符 另一个有用的通配符是下划线（_）。下划线的用途与%一样，但下划线只匹配单个字符而不是多个字符。 

📣正则表达式的作用是匹配文本，将一个模式（正则表达式）与一个文本串进行比较。MySQL用WHERE子句对正则表达式提供了初步的支持，允许你指定正则表达式，过滤SELECT检索出的数据。 仅为正则表达式语言的一个子集如果你熟悉正则表达式，需要注意：MySQL仅支持多数正则表达式实现的一个很小的子集。本章介绍MySQL支持的大多数内容。 



正则表达式：

正则表达式中，^表示字符串的开始位置，$表示字符串的结束位置。 

使用方括号（[]）可以将需要查询字符组成一个字符集。只要记录中包含方括号中的任意字符，该记录将会被查询出来。 

下面从info表name字段中查询包含c、e和o 3个字母中任意一个的记录。SQL代码如下。

 SELECT * FROM info WHERE name REGEXP ‘[ceo]’; 

要实现查询姓名（name）字段中以L开头、以y结束的，中间包含两个字符的学生成绩，可以通过正则表达式查询来实现。其中正则表达式中，^表示字符串的开始位置，$表示字符串的结束位置，.表示除“\n”以外的任何单个字符，具体代码如下。

 SELECT * FROM computer_stu WHERE name REGEXP '^L..y$'; 

MySQL中的两个谓词distinct和limit可以过滤记录。 

（1）使用谓词distinct过滤结果集中的重复记录。 数据库表中不允许出现重复的记录，但这不意味着select的查询结果集中不会出现记录重复的现象。如果 需要过滤结果集中重复的记录，可以使用谓词关键字distinct，语法格式如下。 distinct字段名 例如，检索classes表中的院系名信息，要求院系名不能重复，可以使用下面的SQL语句。 select distinct department_name from classes; 

不能将“score is NULL”写成“score = NULL”，原因是NULL是一个不确定的数，不能使用“=”、“！=”等比较运算符与NULL进行比较。 

检索课程上限不是60人的所有课程信息，可以使用下面的SQL语句，执行结果如图5-16所示。 select * from course where !(up_limit=60); 该SQL语句等效于：select * from course where up_limit!=60; 



# distinct

select distinct country from person

结果如下：

![image-20211001082622408](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211001082622408.png)

1.2 对多列进行操作
select distinct country, province from person

结果如下：

![image-20211001082635949](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211001082635949.png)


从上例中可以发现，当distinct应用到多个字段的时候，其应用的范围是其后面的所有字段，而不只是紧挨着它的一个字段，而且distinct只能放到所有字段的前面，如下语句是错误的：

SELECT country, distinct province from person; // 该语句是错误的


`distinct`对`NULL`是不进行过滤的，即返回的结果中是包含`NULL`值的。

`*`代表整列，使用distinct对`*`操作

```
select DISTINCT * from person
```

相当于

```sql
select DISTINCT id, `name`, country, province, city from person
```







# CHAR 和 VARCHAR 的区别？

CHAR 和 VARCHAR 类型在存储和检索方面有所不同。
CHAR 列长度固定为创建表时声明的长度，长度值范围是 1 到 255。
当 CHAR 值被存储时，它们被用空格填充到特定长度，检索 CHAR 值时需删除尾随空格。

存储引擎称为表类型，数据使用各种技术存储在文件中。
技术涉及：
Storage mechanism
Locking levels
Indexing
Capabilities and functions.

数据库的三范式？
第一范式：数据库表的每一个字段都是不可分割的。
第二范式：数据库表中的非主属性只依赖于主键。
第三范式：不存在非主属性对关键字的传递函数依赖关系。

VARCHAR和TEXT类型与5.1.5小节将要讲解的BLOB一样是变长类型，其存储需求取决于列值的实际长度（在前面的表格中用L表示），而不是取决于类型的最大可能尺寸。例如，一个VARCHAR（10）列能保存最大长度为10个字符的一个字符串，实际的存储需要是字符串的长度L，加上1字节以记录字符串的长度。对于字符“abcd”，L是4，而存储要求是5字节。 

VARCHAR的最大实际长度由最长的行的大小和**使用的字符集**确定，而其实际占用的空间为字符串的实际长度加1。例如，VARCHAR（50）定义了一个最大长度为50的字符串，若插入的字符串只有10个字符，则实际存储的字符串为10个字符和一个字符串结束字符。VARCHAR在值保存和检索时尾部的空格仍保留。 

CHAR是固定长度，所以它的处理速度比VARCHAR快，但是它的缺点是浪费存储空间。所以对存储不大，但在速度上有要求的可以使用CHAR类型，反之可以使用VARCHAR类型来实现。 存储引擎对于选择CHAR和VARCHAR的影响如下。 ·对于MyISAM存储引擎，最好使用固定长度的数据列代替可变长度的数据列。这样可以使整个表静态化，从而使数据检索更快，用空间换时间。 ·对于InnoDB存储引擎，使用可变长度的数据列，因为InnoDB数据表的存储格式不分固定长度和可变长度，因此使用CHAR不一定比使用VARCHAR更好，但由于VARCHAR是按照实际的长度存储的，比较节省空间，因此对磁盘I/O和数据存储总量比较好。 



# 如何区分FLOAT和DOUBLE？
以下是FLOAT和DOUBLE的区别：
浮点数以8位精度存储在FLOAT中，并且有四个字节。
浮点数存储在DOUBLE中，精度为18位，有八个字节。

 # 区分CHAR_LENGTH和LENGTH？
CHAR_LENGTH是字符数，而LENGTH是字节数。Latin字符的这两个数据是相同的，但是对于Unicode和其他编码，它们是不同的



如何获取当前的Mysql版本？
SELECT VERSION();用于获取当前Mysql的版本。



##  varchar(10)和int(10)代表什么含义



varchar的10代表了申请的空间长度，也是可以存储的数据的最大长度，而int的10只是代表了展示的长度，不 足10位以。填充.也就是说,int(1)和int(10)所能存储的数字大小以及占用的空间都是相同的，只是在展示时 按照长度展示。



# 如果一个表有一列定义为TIMESTAMP，将发生什么？
每当行被更改时，时间戳字段将获取当前时间戳。



# 一些数据类型

🔷在MySQL 5.7中，新增了一种新的数据类型，用来在MySQL的表中存储JSON格式的数据。原生支持JSON数据类型主要有如下好处： （1）文档校验：只有符合JSON规范的数据段才能被写入类型为JSON的列中，相当于有了自动JSON语法校验。 （2）高效访问：在JSON类型的列中存储JSON文档时，数据不会被视为纯文本进行存储，而是以一种优化后的二进制格式进行存储，以便可以更快速地访问其对象成员和数组元素。 （3）提升性能：通过在JSON类型的列上创建索引，可以提升数据查询性能。

🔷浮点数FLOAT、DOUBLE相对于定点数DECIMAL的优势是：在长度一定的情况下，浮点数能表示更大的数据范围。但是由于浮点数容易产生误差，**因此对精确度要求比较高时，建议使用DECIMAL来存储**。DECIMAL在MySQL中是以字符串存储的，用于定义货币等对精确度要求较高的数据。在数据迁移中，float（M，D）是非标准SQL定义，数据库迁移可能会出现问题，最好不要这样使用。另外，两个浮点数进行减法和比较运算时也容易出现问题，因此在进行计算的时候，一定要小心。如果进行数值比较，最好使用DECIMAL类型。 

🔷ENUM和SET的值是以字符串形式出现的，但在内部，MySQL以数值的形式存储它们。 

疑问1：MySQL中如何使用特殊字符？ 诸如单引号（‘）、双引号（“）、反斜线（\）等符号，这些符号在MySQL中不能直接输入使用，否则会产生意料之外的结果。在MySQL中，这些特殊字符称为转义字符，在输入时需要以反斜线（\）开头，所以在使用单引号和双引号时应分别输入（\‘）或者（\“），输入反斜线时应该输入（\\），其他特殊字符还有回车符（\r）、换行符（\n）、制表符（\tab）、退格符（\b）等。在向数据库中插入这些特殊字符时，一定要进行转义处理。 

疑问2：MySQL中可以存储文件吗？ MySQL中的BLOB和TEXT字段类型可以存储数据量较大的文件，可以使用这些数据类型存储图像、声音或者大容量的文本内容，例如网页或者文档。虽然使用BLOB或者TEXT可以存储大容量的数据，但是对这些字段的处理会降低数据库的性能。如果并非必要，可以选择只存储文件的路径。 

疑问3：MySQL中如何执行区分大小写的字符串比较？ 在Windows平台下，MySQL是不区分大小的，因此字符串比较函数也不区分大小写。如果想执行区分大小写的比较，可以在字符串前面添加BINARY关键字。例如，默认情况下，‘a‘＝‘A‘返回结果为1，如果使用BINARY关键字，BINARY ‘a‘＝‘A‘返回结果为0，在区分大小写的情况下，‘a‘与‘A‘并不相同。 

# truncate delete drop 的区别。

drop(DDL 语句)：是不可逆操作，会将表所占用空间全部释放掉；

truncate(DDL 语句)：只针对于删除表的操作，在删除过程中不会激活与表有关的删除触发器并且不会把删除记录放在日志中；当表被 truncate 后，这个表和索引会恢复到初始大小；

delete(DML 语句)：可以删除表也可以删除行，但是删除记录会被计入日志保存，而且表空间大小不会恢复到原来；



# 查询当前数据库版本、当前登录用户、当前数据库名称

mysql> select  version(),@@version,user(),database();



+-----------+-----------+----------------+------------+
| version() | @@version | user()         | database() |
+-----------+-----------+----------------+------------+
| 5.7.26    | 5.7.26    | root@localhost | NULL       |
+-----------+-----------+----------------+------------+



# 你可以用什么来确保表格里的字段只接受特定范围里的值?

答： Check 限制，它在数据库表格里被定义，用来限制输入该列的值。
触发器也可以被用来限制数据库表格里的字段能够接受的值，但是这种办法要求触发器在表格里被定义，这可能会在某些情况下影响到性能。

注意：在MySQL8.0之前所有的存储引擎均**不支持check约束**，MySQL会对check子句进行分析，但是在插入数据时会忽略，

在MySQL8.0中，添加了对check约束的操作

如

  CONSTRAINT `testtable_chk_1` CHECK ((`salary` > 29000)) 

约束 (constraint）表在设计的时候加入约束的目的就是为了保证表中的记录完整和有效 如not null约束 ，unique约束

# MySQL时间类型datetime、bigint、timestamp的查询效率比较

- 结论 在InnoDB存储引擎下，通过时间排序，性能bigint > timestamp > datetime

如果需要对时间字段进行操作(如通过时间范围查找或者排序等)，推荐使用bigint，如果时间字段不需要进行任何操作，推荐使用timestamp，使用4个字节保存比较节省空间，但是只能记录到2038年记录的时间有限

CURDATE()函数和CURRENT_DATE()函数用于获取当前日期。将当前日期按照‘YYYY-MM-DD‘或YYYYMMDD格式的值返回，具体格式根据函数在字符串或数字语境中而定。 

获取当前日期和时间的函数 CURRENT_TIMESTAMP（）、LOCALTIME（）、NOW（）和SYSDATE（）4个函数的作用相同，均返回当前日期和时间值，格式为‘YYYY-MM-DD HH：MM：SS‘或YYYYMMDDHHMMSS，具体格式根据函数在字符串或数字语境中而定。 

![image-20210902132855055](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210902132855055.png)

可以看到，两个函数的作用相同，返回了相同的系统当前日期，“CURDATE（）＋0”将当前日期值转换为数值型。 

# 隐式转化CURTIME()函数和CURRENT_TIME()函数用于获取当前时间。

# 

![image-20210821083647034](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210821083647034.png)

![image-20210821083735515](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210821083735515.png)



# 改子查询为join

![image-20210821084129413](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210821084129413.png)

```sql
update operation o join (
    select o.id,
    o.status 
    from operation o 
    where o.group = 123 
    and o.status not in('done') 
   order by o.parent,o.id limit 1)t on o.id = t.id
set status = "applying"
```





# 自动装配

例子：在使用redistemplate时，我们并没有通过XML形式或者注解形式把RedisTemplate注入IoC容器中，但是在HelloController中却可以直接使用＠Autowired来注入redisTemplate实例，这就说明，IoC容器中已经存在RedisTemplate。这就是Spring Boot的自动装配机制。 在往下探究其原理前，可以大胆猜测一下，如何只添加一个Starter依赖，就能完成该依赖组件相关Bean的自动注入？不难猜出，这**个机制的实现一定基于某种约定或者规范，只要Starter组件符合Spring Boot中自动装配约定的规范，就能实现自动装配。**

EnableAutoConfiguration 

进入＠EnableAutoConfiguration注解里，可以看到除＠Import注解之外，还多了一个＠AutoConfigurationPackage注解（它的作用是把使用了该注解的类所在的包及子包下所有组件扫描到Spring IoC容器中）。并且，＠Import注解中导入的并不是一个Configuration的配置类，而是一个AutoConfigurationImportSelector类。从这一点来看，它就和其他的＠Enable注解有很大的不同。





# 聚合函数



聚合函数是对一组值进行计算并返回单一的值的函数，它经常与select 语句中的group by 子句一同使用。
a. avg()：返回的是指定组中的平均值，空值被忽略。
b. count()：返回的是指定组中的项目个数。
c. max()：返回指定数据中的最大值。
d. min()：返回指定数据中的最小值。
e. sum()：返回指定数据的和，只能用于数字列，空值忽略。
f. group by()：对数据进行分组，对执行完group by 之后的组进行聚合函数的运算，计算每一组的值。
最后用having去掉不符合条件的组，having子句中的每一个元素必须出现在select 列表中（只针对于mysql）。

SQL 之连接查询（左连接和右连接的区别）（2017-11-15-lyq）
外连接：
左连接（左外连接）：以左表作为基准进行查询，左表数据会全部显示出来，右表如果和左表匹配的数据则显示相应字段的数据，如果不匹配则显示为null。
右连接（右外连接）：以右表作为基准进行查询，右表数据会全部显示出来，左表如果和右表匹配的数据则显示相应字段的数据，如果不匹配则显示为null。
全连接：先以左表进行左外连接，再以右表进行右外连接。

内连接：显示表之间有连接匹配的所有行。

[find_in_set()函数的使用](https://www.cnblogs.com/xiaoxi/p/5889486.html)

首先举个例子来说：
有个文章表里面有个type字段，它存储的是文章类型，有 1头条、2推荐、3热点、4图文等等 。
现在有篇文章他既是头条，又是热点，还是图文，type中以 1,3,4 的格式存储。那我们如何用sql查找所有type中有4的图文类型的文章呢？？
这就要我们的 find_in_set 出马的时候到了。以下为引用的内容：

```
select * from article where FIND_IN_SET('4',type)
```

mysql> SELECT FIND_IN_SET('b', 'a,b,c,d');
-> 2 因为b 在strlist集合中放在2的位置 从1开始

select FIND_IN_SET('1', '1'); 返回 就是1 这时候的strlist集合有点特殊 只有一个字符串 其实就是要求前一个字符串 一定要在后一个字符串集合中才返回大于0的数
select FIND_IN_SET('2', '1，2'); 返回2
select FIND_IN_SET('6', '1'); 返回0



select * from treenodes where FIND_IN_SET(id, '1,2,3,4,5');
使用find_in_set函数一次返回多条记录
id 是一个表的字段，然后每条记录分别是id等于1，2，3，4，5的时候
有点类似in （集合）
select * from treenodes where id in (1,2,3,4,5);

**find_in_set()和in的区别：**

弄个测试表来说明两者的区别

```sql
CREATE TABLE `tb_test` (
  `id` int(8) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  `list` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
);

INSERT INTO `tb_test` VALUES (1, 'name', 'daodao,xiaohu,xiaoqin');
INSERT INTO `tb_test` VALUES (2, 'name2', 'xiaohu,daodao,xiaoqin');
INSERT INTO `tb_test` VALUES (3, 'name3', 'xiaoqin,daodao,xiaohu');

```

原来以为mysql可以进行这样的查询：

```
SELECT id,name,list from tb_test WHERE 'daodao' IN(list); -- (一) 
```

实际上这样是不行的，这样只有当list字段的值等于'daodao'时（和IN前面的字符串完全匹配），查询才有效，否则都得不到结果，即使'daodao'真的在list中。

再来看看这个：

```
SELECT id,name,list from tb_test WHERE 'daodao' IN ('libk', 'zyfon', 'daodao'); -- (二)
```

![img](https://images2015.cnblogs.com/blog/249993/201609/249993-20160921001750731-486093364.png)

这样是可以的。

这两条到底有什么区别呢？为什么第一条不能取得正确的结果，而第二条却能取得结果。原因其实是（一）中 (list) list是变量， 而（二）中 ('libk', 'zyfon', 'daodao')是常量。
所以如果要让（一）能正确工作，需要用find_in_set():

**总结：**
所以如果list是常量，则可以直接用IN， 否则要用find_in_set()函数。

**注意**：mysql字符串函数 find_in_set(str1,str2)函数是返回str2中str1所在的位置索引，str2必须以","分割开。



## group by



having与where的区别:
having是在分组后对数据进行过滤
where是在分组前对数据进行过滤

分组前对数据进行筛选，使用where关键字
需求：需要查询2018年每个用户下单数量，输出：用户id、下单数量，如下：
mysql> SELECT
user_id 用户id, COUNT(id) 下单数量
FROM
t_order
GROUP BY user_id;
+----------+--------------+
| 用户id | 下单数量 |
+----------+--------------+
| 1001 | 3 |
| 1002 | 4 |
| 1003 | 2 |
+----------+--------------+
3 rows in set (0.00 sec)
mysql> SELECT
user_id 用户id, the_year 年份, COUNT(id) 下单数量
FROM
t_order
GROUP BY user_id , the_year;
+----------+--------+--------------+
| 用户id | 年份 | 下单数量 |
+----------+--------+--------------+
| 1001 | 2017 | 1 |
| 1001 | 2018 | 2 |
| 1002 | 2018 | 3 |
| 1002 | 2019 | 1 |
| 1003 | 2018 | 1 |
| 1003 | 2019 | 1 |
+----------+--------+--------------+
6 rows in set (0.00 sec)
mysql> SELECT
user_id 用户id, COUNT(id) 下单数量
FROM
t_order t
WHERE
t.the_year = 2018
GROUP BY user_id;

having后面可以使用聚合函数
where后面不可以使用聚合

使用：

![image-20210707204104514](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210707204104514.png)

where是在分组（聚合）前对记录进行筛选，而having是在分组结束后的结果里筛选，最后返回整个sql的查询结果。
可以把having理解为两级查询，即含having的查询操作先获得不含having子句时的sql查询结果表，然后在这个结果表上使用having条件筛选出符合的记录，最后返回这些记录，因此，having后是可以跟聚合函数的，并且这个聚集函数不必与select后面的聚集函数相同。





SELECT job,max(salary) as 薪水 FROM `testsql` GROUP BY job ORDER BY 薪水

![image-20210707204031066](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210707204031066.png)

![image-20210707204621091](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210707204621091.png)

![image-20210707204654636](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210707204654636.png)

发现在AVG(salary)中只有job的每一条数据的salary都大于4200的才会被查出来（为什么不是平均salary?



在查询过程中执行顺序：from>where>group（含聚合）>having>order>select。 



sql命令

select avg(lasttime) from wash

select sum(lasttime) from wash

![image-20210830120933828](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830120933828.png)

group by常常与聚合函数搭配使用



## 查找第二低的薪水：

```sql
select
(
    select distinct salary 
    from Employee
    Order by salary desc
    limit 1 offset 1
)
AS SecondHighestSalary
```



```sql
GROUP BY 语句用于结合合计函数，根据一个或多个列对结果集进行分组。

SQL GROUP BY 语法
SELECT column_name, aggregate_function(column_name)
FROM table_name
WHERE column_name operator value
GROUP BY column_name
```

我们拥有下面这个 "Orders" 表：

| O_Id | OrderDate  | OrderPrice | Customer |
| :--- | :--------- | :--------- | :------- |
| 1    | 2008/12/29 | 1000       | Bush     |
| 2    | 2008/11/23 | 1600       | Carter   |
| 3    | 2008/10/05 | 700        | Bush     |
| 4    | 2008/09/28 | 300        | Bush     |
| 5    | 2008/08/06 | 2000       | Adams    |
| 6    | 2008/07/21 | 100        | Carter   |

现在，我们希望查找每个客户的总金额（总订单）。

我们想要使用 GROUP BY 语句对客户进行组合。

我们使用下列 SQL 语句：

```
SELECT Customer,SUM(OrderPrice) FROM Orders
GROUP BY Customer
```

结果集类似这样：

| Customer | SUM(OrderPrice) |
| :------- | :-------------- |
| Bush     | 2000            |
| Carter   | 1700            |
| Adams    | 2000            |

很棒吧，对不对？

让我们看一下如果省略 GROUP BY 会出现什么情况：

```
SELECT Customer,SUM(OrderPrice) FROM Orders
```

结果集类似这样：

| Customer | SUM(OrderPrice) |
| :------- | :-------------- |
| Bush     | 5700            |
| Carter   | 5700            |
| Bush     | 5700            |
| Bush     | 5700            |
| Adams    | 5700            |
| Carter   | 5700            |

上面的结果集不是我们需要的。

那么为什么不能使用上面这条 SELECT 语句呢？解释如下：上面的 SELECT 语句指定了两列（Customer 和 SUM(OrderPrice)）。"SUM(OrderPrice)" 返回一个单独的值（"OrderPrice" 列的总计），而 "Customer" 返回 6 个值（每个值对应 "Orders" 表中的每一行）因此，我们得不到正确的结果。

我们也可以对一个以上的列应用 GROUP BY 语句，就像这样：

```sql
SELECT Customer,OrderDate,SUM(OrderPrice) FROM Orders
GROUP BY Customer,OrderDate
```



## 编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。

重复的电子邮箱存在多次。要计算每封电子邮件的存在次数，我们可以使用以下代码。

MySQL

select Email, count(Email) as num
from Person
group by Email;

| Email   | num  |
| ------- | ---- |
| a@b.com | 2    |
| c@d.com | 1    |

以此作为临时表，我们可以得到下面的解决方案。

MySQL

select Email from
(
  select Email, count(Email) as num
  from Person
  group by Email
) as statistic
where num > 1
;

作者：LeetCode


方法二：使用 GROUP BY 和 HAVING 条件
向 GROUP BY 添加条件的一种更常用的方法是使用 HAVING 子句，该子句更为简单高效。

所以我们可以将上面的解决方案重写为：

MySQL

select Email
from Person
group by Email
having count(Email) > 1;

作者：LeetCode

## 左连接（LEFT JOIN）实例

现在，我们希望列出所有的人，以及他们的定购 - 如果有的话。

您可以使用下面的 SELECT 语句：

```sql
SELECT Persons.LastName, Persons.FirstName, Orders.OrderNo
FROM Persons
LEFT JOIN Orders
ON Persons.Id_P=Orders.Id_P
ORDER BY Persons.LastName
```

LEFT JOIN 关键字会从左表 (Persons) 那里返回所有的行，即使在右表 (Orders) 中没有匹配的行。

## SQL FULL JOIN 关键字

只要其中某个表存在匹配，FULL JOIN 关键字就会返回行。



```sql
SELECT column_name(s)
FROM table_name1
FULL JOIN table_name2 
ON table_name1.column_name=table_name2.column_name
```

## 全连接（FULL JOIN）实例

现在，我们希望列出所有的人，以及他们的定单，以及所有的定单，以及定购它们的人。

"Persons" 表：

| Id_P | LastName | FirstName | Address        | City     |
| :--- | :------- | :-------- | :------------- | :------- |
| 1    | Adams    | John      | Oxford Street  | London   |
| 2    | Bush     | George    | Fifth Avenue   | New York |
| 3    | Carter   | Thomas    | Changan Street | Beijing  |

"Orders" 表：

| Id_O | OrderNo | Id_P |
| :--- | :------ | :--- |
| 1    | 77895   | 3    |
| 2    | 44678   | 3    |
| 3    | 22456   | 1    |
| 4    | 24562   | 1    |
| 5    | 34764   | 65   |

您可以使用下面的 SELECT 语句：

```
SELECT Persons.LastName, Persons.FirstName, Orders.OrderNo
FROM Persons
FULL JOIN Orders
ON Persons.Id_P=Orders.Id_P
ORDER BY Persons.LastName
```

结果集：

| LastName | FirstName | OrderNo |
| :------- | :-------- | :------ |
| Adams    | John      | 22456   |
| Adams    | John      | 24562   |
| Carter   | Thomas    | 77895   |
| Carter   | Thomas    | 44678   |
| Bush     | George    |         |
|          |           | 34764   |

FULL JOIN 关键字会从左表 (Persons) 和右表 (Orders) 那里返回所有的行。如果 "Persons" 中的行在表 "Orders" 中没有匹配，或者如果 "Orders" 中的行在表 "Persons" 中没有匹配，这些行同样会列出。

## SQL UNION 操作符

UNION 操作符用于合并两个或多个 SELECT 语句的结果集。

请注意，UNION 内部的 SELECT 语句必须拥有相同数量的列。列也必须拥有相似的数据类型。同时，每条 SELECT 语句中的列的顺序必须相同。

![image-20210830121130325](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830121130325.png)





### SQL UNION 语法

```
SELECT column_name(s) FROM table_name1
UNION
SELECT column_name(s) FROM table_name2
```

**注释：**默认地，UNION 操作符选取不同的值。如果允许重复的值，请使用 UNION ALL

### Employees_China:

| E_ID | E_Name         |
| :--- | :------------- |
| 01   | Zhang, Hua     |
| 02   | Wang, Wei      |
| 03   | Carter, Thomas |
| 04   | Yang, Ming     |

### Employees_USA:

| E_ID | E_Name         |
| :--- | :------------- |
| 01   | Adams, John    |
| 02   | Bush, George   |
| 03   | Carter, Thomas |
| 04   | Gates, Bill    |



### 实例

列出所有在中国和美国的不同的雇员名：

```
SELECT E_Name FROM Employees_China
UNION
SELECT E_Name FROM Employees_USA
```

### 结果

| E_Name         |
| :------------- |
| Zhang, Hua     |
| Wang, Wei      |
| Carter, Thomas |
| Yang, Ming     |
| Adams, John    |
| Bush, George   |
| Gates, Bill    |

**注释：**这个命令无法列出在中国和美国的所有雇员。在上面的例子中，我们有两个名字相同的雇员，他们当中只有一个人被列出来了。UNION 命令只会选取不同的值。

UNION ALL 命令和 UNION 命令几乎是等效的，不过 UNION ALL 命令会列出所有的值。

```sql
SQL Statement 1
UNION ALL
SQL Statement 2
```

## SQL SELECT INTO 实例 - 制作备份复件

SELECT INTO 语句从一个表中选取数据，然后把数据插入另一个表中。

SELECT INTO 语句常用于创建表的备份复件或者用于对记录进行存档。

下面的例子会制作 "Persons" 表的备份复件：

```sql
SELECT *
INTO Persons_backup
FROM Persons
```



## 约束

![image-20210830121220190](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830121220190.png)

![image-20210830121345968](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830121345968.png)

外键只能作为表约束添加，默认和非空只能作为列约束

![image-20210830121436545](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210830121436545.png)

语法：constraint xx【约束名】foreign key【列名】 references xx【表名】(xx)【列名】

SQL CHECK CHECK 约束用于限制列中的值的范围。（mysql不支持check)

如果对单个列定义 CHECK 约束，那么该列只允许特定的值。

如果对一个表定义 CHECK 约束，那么此约束会在特定的列中对值进行限制。

### 

```sql
CREATE TABLE Persons
(
Id_P int NOT NULL,
LastName varchar(255) NOT NULL,
FirstName varchar(255),
Address varchar(255),
City varchar(255),
CHECK (Id_P>0)
)
```

如果在表已存在的情况下为 "Id_P" 列创建 CHECK 约束，请使用下面的 SQL：



```sql
ALTER TABLE Persons
ADD CHECK (Id_P>0)
```

如需撤销 CHECK 约束，请使用下面的 SQL：

```sql
ALTER TABLE Persons
DROP CHECK chk_Person
```

🔷例子：

创建表，保证邮箱地址唯一(列级约束)

```sql
mysql> create table t_user(
-> id int(10),
-> name varchar(32) not null,
-> email varchar(128) unique
-> );
Query OK, 0 rows affected (0.03 sec)
```


1、表级约束

```sql
mysql> create table t_user(
-> id int(10),
-> name varchar(32) not null,
-> email varchar(128），
-> unique(email)
-> );
```

2、使用表级约束，给多个字段联合约束
联合约束，表示两个或以上的字段同时与另一条记录相等，则报错

```sql
mysql> create table t_user(
-> id int(10),
-> name varchar(32) not null,
-> email varchar(128),
-> unique(name,email)
-> );
```

3、表级约束可以给约束起名字(方便以后通过这个名字来删除这个约束)



```sql
mysql> create table t_user(
-> id int(10),
-> name varchar(32) not null,
-> email varchar(128),
-> constraint t_user_email_unique unique(email)
-> );
Query OK, 0 rows affected (0.06 sec)
```

t_user_email_unique是自己取的名字

主键约束(primary key)与“not null unique”区别
给某个字段添加主键约束之后，该字段不能重复也不能为空，效果和”not null unique”约束相同，但是本质不同。

主键约束除了可以做到”not null unique”之外，还会默认添加”索引——index”

、外键约束(foreign key)FK
只能是表级定义（如以下例子）

```sql
foreign key(classno) references t_class(cno)
```

主键约束
主键字段
主键值
2、以上三种术语关系
表中的某个字段添加主键约束后，该字段为主键字段，主键字段中出现的每一个数据都称为主键值





## 连接mysql

连接MySQL操作是一个连接进程和MySQL数据库实例进行通信。从程序设计的角度来说，本质上是进程通信。如果对进程通信比较了解，可以知道常用的进程通信方式有管道、命名管道、命名字、TCP/IP套接字、UNIX域套接字。MySQL数据库提供的连接方式从本质上看都是上述提及的进程通信方式。 

TCP/IP TCP/IP套接字方式是MySQL数据库在任何平台下都提供的连接方式，也是网络中使用得最多的一种方式。这种方式在TCP/IP连接上建立一个基于网络的连接请求，一般情况下客户端（client）在一台服务器上，而MySQL实例（server）在另一台服务器上，这两台机器通过一个TCP/IP网络连接。例如用户可以在Windows服务器下请求一台远程Linux服务器下的MySQL实例，如下所示：

 C：\＞mysql-h 192.168.0.101-u david-p 

Enter password： 



## 真题：如何连接到MySQL数据库？

 答案：连接到MySQL数据库有多种写法，假设MySQL服务器的地址为192.168.59.130，可以通过如下几种方式来连接MySQL数据库： 

1）mysql-p。 2）mysql-uroot-p。 3）mysql-u root-h192.168.59.130 -p。 



真题19：哪个命令可以查看所有数据库？ 

答案：运行命令：show databases; 

真题20：如何切换到某个特定的数据库？ 

use +数据库名



## 用哪些命令可以查看MySQL数据库中的表结构？ 



答案：查看MySQL表结构的命令有如下几种：

 1）DESC表名； 

2）SHOW COLUMNS FROM表名；

 3）SHOW CREATE TABLE表名；（查看建表语句） 

4）查询information_schema.tables系统表。 

如何创建TABB表，完整复制TABA表的结构和索引，而且不要数据？

 答案：CREATE TABLE TABB LIKE TABA

;真题25：如何查看某一用户的权限？

 答案：SHOW GRANTS FOR USERNAME; 

真题26：如何得知当前BINARY LOG文件和POSITION值？

答案：SHOW MASTER STATUS; 

真题27：用什么命令切换BINARY LOG？ 答案：FLUSH LOGS;。 

真题28：用什么命令整理表数据文件的碎片？ 答案：OPTIMIZE TABLE TABLENAME; 

真题29：如何得到TA_LHR表的建表语句？ 答案：SHOW CREATE TABLE TA_LHR; 



## MySQL中如何快速地复制一张表及其数据？ 

答案：可以使用like关键字，但是like只复制了表结构及其索引，而其数据没有复制，所以，需要使用insert来插入，如下所示：

```sql
create table a2 like a1;
insert into a2 select * from a1;
```







## 创建库

create database [if not exists] 库名;

## 删除库

drop databases [if exists] 库名;

## 建库通用的写法

：
drop database if exists 旧库名;
create database 新库名;

## 创建表

create table 表名(
字段名1 类型[(宽度)] [约束条件] [comment '字段说明'],
字段名2 类型[(宽度)] [约束条件] [comment '字段说明'],
字段名3 类型[(宽度)] [约束条件] [comment '字段说明']
)[表的一些设置];

```sql
create table test2(
-> a int not null comment '字段a',
-> b int not null default 0 comment '字段b'
-> );
```
default value：为该字段设置默认值，默认值为value
primary key：标识该字段为该表的主键，可以唯一的标识记录，插入重复的会报错
foreign key：为表中的字段设置外键





CREATE xcc;

USE xcc

CREATE TABLE xc

(

 -> 学号   char(6)    not null primary key,

-> 姓名   char(8)    not null,

 -> 专业   char(10)   null,

-> 性别   tinyint(1) not null default 1,

-> 出生   date       not null,

 -> 照片   blob       null

-> );



SHOW TABLES xc

DESCIBE xc;

INSERT INTO xc

## 新建

```mysql
mysql> create database class character set gbk;
mysql> use class
Database changed
mysql> create table student(
    -> 雇员 int(20) primary key
    -> 姓名    char(10) not null
    -> 部门 char(10) not null
    -> 部门经理 char(10) not null
    -> );


mysql> create table student(
    -> 雇员   int(20)   primary key,
    -> 姓名   char(10)  not null,
    -> 部门   char(10)  not null,
    -> 经理   char(10)  not null
    -> );
```



## 创建用户
语法：
说明：

1. 主机名默认值为%，表示这个用户可以从任何主机连接mysql服务器
2. 密码可以省略，表示无密码登录
示例1：不指定主机名
不指定主机名时，表示这个用户可以从任何主机连接mysql服务器
| test4 | 127.0.0.% |
| test4 | 192.168.11.% |
| mysql.session | localhost |
| mysql.sys | localhost |
| root | localhost |
| test2 | localhost |
+---------------+--------------+
6 rows in set (0.00 sec)
create user 用户名[@主机名] [identified by '密码'];

create user 'test2'@'localhost' identified by '123';

给用户授权
创建用户之后，需要给用户授权，才有意义。
语法：
grant命令说明：
priveleges (权限列表)，可以是all ，表示所有权限，也可以是select、update 等权限，多个权限之间用逗号分开。
ON 用来指定权限针对哪些库和表，格式为数据库.表名 ，点号前面用来指定数据库名，点号后面用来指定表名， *.* 表示所有数据库所有表。
TO 表示将权限赋予某个用户, 格式为username@host ，@前面为用户名，@后面接限制的主机，可以是IP、IP段、域名以及%，%表示任何地方。
WITH GRANT OPTION 这个选项表示该用户可以将自己拥有的权限授权给别人。注意：经常有人在创建操作用户的时候不指定WITH GRANT OPTION选项导致后来该用户不能使用GRANT命令创建用户或者给其它用户授权。 备注：可以使用GRANT重复给用户添加权限，权限叠加，比如你先给用户添加一个select权限，然后又给用户添加一个insert权限，那么该用户就同时拥有了select和insert权限。

grant all on *.* to 'test1'@‘%’;

说明：给test1授权可以操作所有库所有权限，相当于dba

grant select,update on seata.* to 'test1'@'%';

说明：test1可以对seata库中所有的表执行select、update





## 触发事件

MySQL 包含对触发器的支持。触发器是一种与表操作有关的数据库对象，当触发器所在表上出现指定事件时，将调用该对象，即表的操作事件触发表上的触发器的执行。
在MySQL 中，创建触发器语法如下：
CREATE TRIGGER trigger_name
trigger_time
trigger_event ON tbl_name
FOR EACH ROW
trigger_stmt
其中：
trigger_name：标识触发器名称，用户自行指定；
trigger_time：标识触发时机，取值为 BEFORE 或 AFTER；
trigger_event：标识触发事件，取值为 INSERT、UPDATE 或 DELETE；

tbl_name：标识建立触发器的表名，即在哪张表上建立触发器；
trigger_stmt：触发器程序体，可以是一句SQL 语句，或者用 BEGIN 和 END 包含的多条语句。
由此可见，可以建立6 种触发器，即：BEFORE INSERT、BEFORE UPDATE、BEFORE DELETE、AFTER INSERT、AFTER UPDATE、AFTER DELETE。
另外有一个限制是不能同时在一个表上建立2 个相同类型的触发器，因此在一个表上最多建立6 个触发器。

1)班级表 class(班级号 classID, 班内学生数 stuCount)
2)学生表 student(学号 stuID, 所属班级号 classID)
要创建触发器来使班级表中的班内学生数随着学生的添加自动更新，代码如下：

```sql
create trigger tri_stuInsert after insert
on student for each row
begin
declare c int;
set c = (select stuCount from class where classID = new.classID);
update class set stuCount = c + 1 where classID = new.classID;
```







```mysql

create database class character set gbk;
use class;
create table student(
雇员      int(20)    primary key,
姓名      char(10)   not null,
部门      char(10)   not null,
部门经理  char(10)   not null
 );

insert into student values(0001,"合肥","工业","大学");
```



## 插入

```mysql

插入数据库：
mysql> insert into students values(NULL,“钟无艳”,“女”,100,“987654321”);
Query OK, 1 row affected (0.35 sec)
mysql> insert into student values(00020,"小李","财务","王总");
Query OK, 1 row affected (0.06 sec)

mysql> insert into student values(002220,"王艳","技术","李总");
Query OK, 1 row affected (0.04 sec)

mysql> insert into student values(234020,"张燕","人事","王总");
Query OK, 1 row affected (0.04 sec)
show columns from student;
mysql> select * from students;
```

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-22 (9).png)





取某两列：

mysql> select 雇员,姓名 from student;
+--------+------+

| 雇员 | 姓名 |
| ---- | ---- |
|      |      |
+--------+------+
| 20   | 小李 |
| ---- | ---- |
|      |      |
| 2220 | 王艳 |
| ---- | ---- |
|      |      |
| 234020 | 张燕 |
| ------ | ---- |
|        |      |
+--------+------+

## 查询（以输入要求的方式）



```mysql

mysql> select *from student where 姓名="王艳";
+------+------+------+------+
| 雇员 | 姓名 | 部门 | 经理 |
+------+------+------+------+
| 2220 | 王艳 | 技术 | 李总 |
+------+------+------+------+
1 row in set (0.04 sec)



```

## 修改 删除



```mysql
mysql> update student set 雇员=2227 where 姓名="王艳";
Query OK, 1 row affected (0.05 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> select *from student where 姓名="王艳";
+------+------+------+------+
| 雇员 | 姓名 | 部门 | 经理 |
+------+------+------+------+
| 2227 | 王艳 | 技术 | 李总 |
+------+------+------+------+
1 row in set (0.04 sec)


mysql> delete from student where 姓名="王艳";
Query OK, 1 row affected (0.05 sec)

mysql> select *from student;
+--------+------+------+------+
| 雇员   | 姓名 | 部门 | 经理 |
+--------+------+------+------+
|     20 | 小李 | 财务 | 王总 |
| 234020 | 张燕 | 人事 | 王总 |
+--------+------+------+------+

```



## <u>增加某一列</u>

alter table employ add column 年龄 varchar(20) not null;

可以使用SQL语句“alter table ai3 add id0 int auto_increment primary key first;”**来添加主键**列



select * from employee.employ into outfile "C:\emm.txt"

```mysql

create database employee character set gbk;
use employee;
create table employ(
    -> 雇员     char(20)    primary key,
    -> 姓名     char(10)    not null,
    -> 部门     char(10)    not null,
    -> 部门经理 char(20)    not null
    -> );

insert into employ values("0121010","张明","文娱部","刘经理");
insert into employ values("01244120","孙俪","总结部","胡经理");
insert into employ values("01210110","孙明","体育部","张经理");
insert into employ values("0101010","李明","财务部","王经理");
 insert into employ values("0121010","赵明","劳动部","张经理");
 select * from employ;
 alter table employ add column "性别" varchar(20) not null;
 update employ set 性别="女" where 姓名="李明";
  update employ set 性别="男" where 姓名="赵明";
  update employ set 性别="男" where 姓名="孙明";
  update employ set 性别="男" where 姓名="张明";
  update employ set 性别="女" where 姓名="孙俪";
  select * from employ;
```

# 创建视图

```sql
/*案例1：查询姓名中包含a字符的员工名、部门、工种信息*/
/*①创建视图myv1*/
CREATE VIEW myv1
AS
SELECT
t1.last_name,
t2.department_name,
t3.job_title
FROM employees t1, departments t2, jobs t3
WHERE t1.department_id = t2.department_id
AND t1.job_id = t3.job_id;
/*②使用视图*/
SELECT * FROM myv1 a where a.last_name like 'a%';
```

上面我们创建了一个视图： myv1 ，我们需要看员工姓名、部门、工种信息的时候，不用关心这个视图内部是什么样的，只需要查询视图就可以了，sql简单多了。

案例2：查询各部门的平均工资级别

```sql
CREATE VIEW myv2
AS
SELECT
t1.department_id 部门id,
t1.ag 平均工资,
t2.grade_level 工资级别
FROM (SELECT
department_id,
AVG(salary) ag
FROM employees
GROUP BY department_id)
t1, job_grades t2
WHERE t1.ag BETWEEN t2.lowest_sal AND t2.highest_sal;
```

SELECT * FROM myv2

![image-20220721161532171](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220721161532171.png)



# 21、模糊查询 like 语句该怎么写

select 列名 from 表名 where 列 like pattern;

%：表示匹配任意一个或多个字符
_：表示匹配任意一个字符。

IN 操作符允许我们在 WHERE 子句中规定多个值。

select 列名 from 表名 where 字段 in (值1,值2,值3,值4);



39、ISAM 是什么？
ISAM 简称为索引顺序访问方法。它是由 IBM 开发的，用于在磁带等辅助存储系统上存储和检索数据。
40、InnoDB 是什么？
lnnoDB 是一个由 Oracle 公司开发的 Innobase Oy 事务安全存储引擎。
41、Mysql 如何优化 DISTINCT？【？】
DISTINCT 在所有列上转换为 GROUP BY，并与 ORDER BY 子句结合使用。

1
SELECT DISTINCT t1.a FROM t1,t2 where t1.a=t2.a;
42、如何输入字符为十六进制数字？
如果想输入字符为十六进制数字，可以输入带有单引号的十六进制数字和前缀（X），或者只用（Ox）前缀输入十六进制数字。
如果表达式上下文是字符串，则十六进制数字串将自动转换为字符串。 

 VIEW

# 47.、Mysql 表中允许有多少个 TRIGGERS？

在 Mysql 表中允许有六个触发器，如下：
 BEFORE INSERT
 AFTER INSERT
 BEFORE UPDATE
 AFTER UPDATE
 BEFORE DELETE and
 AFTER DELETE

# where 子句中可以对字段进行null 值判断吗？

可以，比如select id from t where num is null 这样的sql 也是可以的。但是最好不要给数据库留NULL，尽可能的使用 NOT NULL 填充数据库。不要以为 NULL 不需要空间，比如：char(100) 型，在字段建立时，空间就固定了，不管是否插入值（NULL 也包含在内），都是占用100 个字符的空间的，如果是varchar 这样的变长字段，null 不占用空间。可以在num 上设置默认值0，确保表中num 列没有null 值，然后这样查询：select id from t where num= 0。



==查询运算符、like、between and、in、not in对NULL值查询不起效。==

<=>（安全等于）
<=>：既可以判断NULL值，又可以判断普通的数值，可读性较低，用得较少

mysql> select * from test8 t where t.a<=>null;

```sql
+------+------+
| a | b |
+------+------+
| NULL | b |
| NULL | NULL |
+------+------+
```

可以看到<=>可以将NULL查询出来。



select * from admin left join log on admin.admin_id = log.admin_id where log.admin_id>10 如何优化?

优化为： select * from (select * from admin where admin_id>10) T1 left join log on T1.admin_id = log.admin_id。
使用JOIN 时候，应该用小的结果驱动大的结果（left join 左边表结果尽量小如果有条件应该放到左边先处理，right join 同理反向），同时尽量把牵涉到多表联合的查询拆分多个query（**多个连表查询效率低，容易到之后锁表和**
**阻塞**）。

limit 的基数比较大时使用between
例如：select * from admin order by admin_id limit 100000,10
优化为：select * from admin where admin_id between 100000 and 100010 order by admin_id。

4 尽量避免在列上做运算，这样导致索引失效
例如：select * from admin where year(admin_time)>2014
优化为： select * from admin where admin_time> '2014-01-01′

MySQL 中文乱码问题完美解决方案（2017-12-07-lwl）
解决乱码的核心思想是统一编码。我们在使用MySQL 建数据库和建表时应尽量使用统一的编码，强烈推荐的是utf8 编码，因为该编码几乎可以兼容世界上所有的字符。
数据库在安装的时候可以设置默认编码，在安装时就一定要设置为utf8 编码。设置之后再创建的数据库和表,如果不指定编码，默认都会使用utf8 编码，省去了很多麻烦。

![image-20210812092903379](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210812092903379.png)

修改数据库默认编码为utf8
SET character_set_client='utf8';
SET character_set_connection='utf8';
SET character_set_results='utf8';



# LENGTH和CHAR_LENGTH的区别

LENGTH和CHAR_LENGTH是MySQL中获取字符串长度的两个函数。函数LENGTH是计算字段的长度，==单位为字节==，1个汉字算3个字节，1个数字或字母算1个字节。CHAR_LENGTH(str)返回值为字符串str的长度，==单位为字符==。CHARACTER_LENGTH()是CHAR_LENGTH()的同义词。对于函数CHAR_LENGTH来说，一个多字节字符算作一个单字符。Latin1字符的这两个函数返回结果是相同的，但是对于Unicode和其他编码来说，它们是不同的。例如，对于一个包含5个2字节字符集的字符串来说，LENGTH()返回值为10，而CHAR_LENGTH()的返回值为5。 





# mysql查询的题目

## 超过经理收入的员工



![image-20211207103854038](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211207103854038.png)

答案



select b.name AS 'Employee' from Employee as a,Employee as b where a.Id = b.ManagerId and b.salary>a.Salary

或者：

SELECT
     a.NAME AS Employee
FROM Employee AS a JOIN Employee AS b
     ON a.ManagerId = b.Id
     AND a.Salary > b.Salary
;



## 删除重复的电子邮箱

编写一个 SQL 查询，来删除 Person 表中所有重复的电子邮箱，重复的邮箱里只保留 Id 最小 的那个。

+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+



答案

delete p1.* from person as p1,person as p2 where p1.email = p2.email and p1.id > p2.id
