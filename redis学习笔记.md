redis学习笔记



# 介绍

redis简介
内存的读写速度远远高于硬盘。如果能够把数据放在内存中，那么数据的读写效率就会有一很大的提升。
在程序开发中，如果需要频繁操作数据库中的一些数据，那么比较高效的做法是把这些数据先读出来，用一个或者多个变量来保存。程序只读一次数据库，之后直接操作变量。等到数据处理完成后，再将数据更新回原数据库或者插入新的数据库中。
在不同进程之间共享变量，虽然也可能做到，但是过程非常烦琐。
更不要说在不同机器之间共享变量了。所以，使用程序变量这种方式虽然读写速度快，却有很大的局限性。如果数据库的速度能快到即使频繁读写也不会影响程序的性能，那么在多个机器之间共享数据也变得轻而易举了。这就是Redis。

Redis 是一个 key-value 存储系统。和 Memcached 类似，但是解决了断电后数据完全丢失的情况，而且她支持更多无化的 value 类型，除了和 string 外，还支持lists（链表）、sets（集合）和 zsets（有序集合）几种数据类型。这些数据类型都支持 push/pop、add/remove 及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。
Redis 是一种基于客户端 - 服务端模型以及请求 / 响应协议的 TCP 服务。这意味
着通常情况下一个请求会遵循以下步骤：
客户端向服务端发送一个查询请求，并监听 Socket 返回，通常是以阻塞模式，等待服务端响应。服务端处理命令，并将结果返回给客户端。
在服务端未响应时，客户端可以继续向服务端发送请求，并最终一次性读取所有服务端的响应。
Redis 管道技术最显著的优势是提高了 Redis 服务的性能。
分区是分割数据到多个 Redis 实例的处理过程，因此每个实例只保存 key 的一个子集。
通过利用多台计算机内存的和值，允许我们构造更大的数据库。
通过多核和多台计算机，允许我们扩展计算能力；通过多台计算机和网络适配器，允许我们扩展网络带宽。

Redis有多种数据结构，适合多种不同的应用场景。
1．使用Redis做缓存
Redis的字符串、哈希表两种数据结构适合用来储存大量的键值对信息，从而实现高速缓存。
2．使用Redis做队列
Redis有多几种数据结构适于做队列：
● 使用“列表”数据结构，可以实现普通级和优先级队列的功能。
● 使用“有序集合”数据结构，可以实现优先级队列；
● 使用“哈希表”数据结构，可以实现延时队列。
3．使用Redis去重
Redis有多几种数据结构适于做去重：
● 利用“集合”数据结构，可以实现小批量数据的去重；
● 利用“字符串”数据结构的位操作，可以实现布隆过滤器，从而实现超大规模的数据去重；
● 利用Redis自带的HyperLogLog数据结构，可以实现超大规模数据的去重和计数。
4．使用Redis实现积分板
Redis的“有序集合”功能可以实现积分板功能，还能实现自动排序、排名功能。
5．使用Redis实现“发布/订阅”功能
Redis自带的“发布/订阅”模式可以实现多对多的“发布/订阅”功能。

JacksonJsonRedisSerializer：使用Jackson 1，将对象序列化为JSON；
Jackson2JsonRedisSerializer：使用Jackson 2，将对象序列化为JSON；
JdkSerializationRedisSerializer：使用Java序列化；



# Redis是如何工作的

- `文件事件`：Redis服务器通过套接字与客户端（或者其他Redis服务器）进行连接，而文件事件就是服务器对套接字操作的抽象；服务器与客户端的通信会产生相应的文件事件，而服务器则通过监听并处理这些事件来完成一系列网络通信操作，比如连接`accept`，`read`，`write`，`close`等；
- `时间事件`：Redis服务器中的一些操作（比如serverCron函数）需要在给定的时间点执行，而时间事件就是服务器对这类定时操作的抽象，比如过期键清理，服务状态统计等。

[![图片](https://mmbiz.qpic.cn/mmbiz_png/JdLkEI9sZffIlKE5Qf3etj1Xo5xkc54eLsWGtZ0Ooa4PvLlGxlzetyfFOuOONga6Fb3YOmj3HpKKgcWVgyzEUw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)](https://mp.weixin.qq.com/s?__biz=MzUzMTA2NTU2Ng==&mid=2247487551&idx=1&sn=18f64ba49f3f0f9d8be9d1fdef8857d9&scene=21#wechat_redirect)事件调度

如上图，Redis将文件事件和时间事件进行抽象，时间轮训器会监听I/O事件表，一旦有文件事件就绪，Redis就会优先处理文件事件，接着处理时间事件。在上述所有事件处理上，Redis都是以`单线程`形式处理，所以说Redis是单线程的。此外，如下图，Redis基于Reactor模式开发了自己的I/O事件处理器，也就是文件事件处理器，Redis在I/O事件处理上，采用了I/O多路复用技术，同时监听多个套接字，并为套接字关联不同的事件处理函数，通过一个线程实现了多客户端并发处理

![image-20220127163716959](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220127163716959.png)

正因为这样的设计，在数据处理上避免了加锁操作，既使得实现上足够简洁，也保证了其高性能。当然，Redis单线程只是指其在事件处理上，实际上，Redis也并不是单线程的，比如生成RDB文件，就会fork一个子进程来实现，当然，这不是本文要讨论的内容。





# 布隆过滤器

布隆过滤器**（推荐） 就是引入了k(k>1)k(k>1)个相互独立的哈希函数，保证在给定的空间、误判率下，完成元素判重的过程**。 它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。 Bloom-Filter算法的核心思想就是利用多个不同的Hash函数来解决“冲突”。 Hash存在一个冲突（碰撞）的问题，用同一个Hash得到的两个URL的值有可能相同。为了减少冲突，我们可以多引入几个Hash，如果通过其中的一个Hash值我们得出某元素不在集合中，那么该元素肯定不在集合中。只有在所有的Hash函数告诉我们该元素在集合中时，才能确定该元素存在于集合中。这便是Bloom-Filter的基本思想。 Bloom-Filter一般用于在大数据量的集合中判定某元素是否存在。

原理：

位图 int[10] ,每个int类型的整数是4*8=32个bit,则int[10]一共有320bit,每个bit非0即1.初始化都是0，

添加数据时将数据进行hash，得到hash值对应到bit位，则将该bit改成1，hash函数可以定义多个，则一个数据添加多个bit改为1，多个hash函数的目的是减少hash碰撞的概率

查询时：hash函数计算得到的hash值，对应到bit中，如果有一个为0，则说明数据不在bit中，如果都为1，则可能在bit中

　优点：
占用内存小
增加和查询元素的时间复杂度为：o(K),(K为哈希函数的个数，一般比较小)，与数据量大小无关。
哈希函数相互之间没有关系，方便硬件并行运算
布隆过滤器不需要存储元素本身，在某些对保密要求比较严格的场合有很大优势。数据量很大时，布隆过滤器可以表示全集
使用同一组散列函数的布隆过滤器可以进行交、并、差运算

缺点：
·误判率，即存在假阳性(False Position),不能准确判断元素是否在集合中。不能获取元素本身(哈希函数不能反向运算)
·一般情况下不能从布隆过滤器中删除元素

# redis的字符串

redis没有使用空字符串结尾的C字符串，而是自己构建了一种名为简单动态字符串的抽象类型(简称SDS）

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210610020810160.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMzU4NTc0,size_16,color_FFFFFF,t_70)

通过使用 SDS 而不是 C 字符串，Redis 将获取字符串长度所需的复杂度从 O(N) 降低到了0 (1) ，这确保了获取字符串长度的工作不会成为Redis 的 性能瓶颈。例如，因为字符串键在底层使用SDS 来实现，所以即使我们对一个非常长的字符串键反复执行 STRLEN 命令，也不会对系统性能造成任何影响，因为STRLEN 命令的复杂度仅为 0 (1) 。

□ 键值对的键是一个字符串对象，对象的底层实现是一个保存着字符串 " msg " 的SDS。
□ 键值对的值也是一个字符串对象，对象的底层实现是一个保存着字符串 " he l lo world " 的 SDS。



Redis set对外提供的功能与list类似是一个列表的功能，特殊之处在于set是可以**自动排重**的，当你需要存储一个列表数据，又不希望出现重复数据时，set是一个很好的选择，并且set提供了判断某个成员是否在一个set集合内的重要接口，这个也是list所不能提供的。

Redis的Set是string类型的无序集合。它底层其实是一个value为null的hash表，所以添加，删除，查找的**复杂度都是**O(1)。

```
sadd <key><value1><value2> ..... 
```

将一个或多个 member 元素加入到集合 key 中，已经存在的 member 元素将被忽略

smembers <key>取出该集合的所有值。

sismember <key><value>判断集合<key>是否为含有该<value>值，有1，没有0

scard<key>返回该集合的元素个数。

srem <key><value1><value2> .... 删除集合中的某个元素。

spop <key>**随机从该集合中吐出一个值。**

srandmember <key><n>随机从该集合中取出n个值。不会从集合中删除 。

smove <source><destination>value把集合中一个值从一个集合移动到另一个集合

sinter <key1><key2>返回两个集合的交集元素。

sunion <key1><key2>返回两个集合的并集元素。

sdiff <key1><key2>返回两个集合的**差集**元素(key1中的，不包含key2中的)

# redis链表

Redis 实现了自己的链表结构，Redis 中列表的底层实现之一就是链表。

每个链表节点都有指向前置节点和后置节点的指针，是一个双向链表。每个链表结构，有表头表尾指针和链表长度等信息。
另外表头节点和前置和表尾节点的后置都是 NULL ，所以是无环链表。



压缩列表（ziplist）是列表键和哈希键的底层实现之一。当一个列表键只包含少量列表项并且每个都是小整数值或者长度比较短的字符串时，Redis 就采用压缩列表做底层实现。当一个哈希键只包含少量键值对，并且每个键值对的键和值也是小整数值或者长度比较短的字符串时，Redis 就采用压缩列表做底层实现。

压缩列表是 Redis 为了节约内存而实现的，是一系列特殊编码的连续内存块组成的顺序型数据结构。


# redis key语法

Redis 键命令的基本语法如下：

redis 127.0.0.1:6379> COMMAND KEY_NAME
实例
redis 127.0.0.1:6379> SET w3ckey redis
OK
redis 127.0.0.1:6379> DEL w3ckey
(integer) 1
在以上实例中 DEL 是一个命令， w3ckey 是一个键。 如果键被删除成功，命令执行后输出 (integer) 1，否则将输出 (integer) 0



序号 命令及描述
1 DEL key
该命令用于在 key 存在是删除 key。
2 DUMP key
序列化给定 key ，并返回被序列化的值。
3 EXISTS key
检查给定 key 是否存在。
4 EXPIRE key seconds
为给定 key 设置过期时间。
5 EXPIREAT key timestamp
EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置过期时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
6 PEXPIRE key milliseconds
设置 key 的过期时间亿以毫秒计。
7 PEXPIREAT key milliseconds-timestamp
设置 key 过期时间的时间戳(unix timestamp) 以毫秒计
8 KEYS pattern
查找所有符合给定模式( pattern)的 key 。
9 MOVE key db
将当前数据库的 key 移动到给定的数据库 db 当中。
10 PERSIST key
移除 key 的过期时间，key 将持久保持。
11 PTTL key
以毫秒为单位返回 key 的剩余的过期时间。
12 TTL key
以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
13 RANDOMKEY
从当前数据库中随机返回一个 key 。
14 RENAME key newkey
修改 key 的名称
15 RENAMENX key newkey
仅当 newkey 不存在时，将 key 改名为 newkey 。
16 TYPE key
返回 key 所储存的值的类型。

incr key
对key的值做加加操作,并返回新的值。注意incr一个不是int的value会返回错误，incr一个不存在的key，则设置key为1。范围为64有符号，
-9223372036854775808~9223372036854775807。
decr key
同上，但是做的是减减操作，decr一个不存在key，则设置key为-1
incrby key integer
同incr，加指定值 ，key不存在时候会设置key，并认为原来的value是 0

Redis DUMP 命令
用于序列化给定 key ，并返回被序列化的值。

redis DUMP 命令基本语法如下：

redis 127.0.0.1:6379> DUMP KEY_NAME

如果 key 不存在，那么返回 nil 。 否则，返回序列化之后的值。

实例
首先，我们在 redis 中创建一个 key 并设置值。

redis> SET greeting “hello, dumping world!”
OK
现在使用 DUMP 序列化键值。

redis> DUMP greeting
“\x00\x15hello, dumping world!\x06\x00E\xa0Z\x82\xd8r\xc1\xde”

Redis EXISTS 命令用于检查给定 key 是否存在。

语法
redis EXISTS 命令基本语法如下：

redis 127.0.0.1:6379> EXISTS KEY_NAME


Redis Expire 命令用于设置 key 的过期时间。key 过期后将不再可用。

语法
redis Expire 命令基本语法如下：

redis 127.0.0.1:6379> Expire KEY_NAME TIME_IN_SECONDS

redis 127.0.0.1:6379> EXPIRE w3ckey 60
(integer) 1
以上实例中我们为键 w3ckey 设置了过期时间为 1 分钟，1分钟后该键会自动删除。



# RedisBitMap位图使用



BitMap 原本的含义是用一个比特位来映射某个元素的状态。由于一个比特位只能表示 0 和 1 两种状态，所以 BitMap 能映射的状态有限，但是使用比特位的优势是能大量的节省内存空间。

在 Redis 中，可以把 Bitmaps 想象成一个以比特位为单位的数组，数组的每个单元只能存储0和1，数组的下标在 Bitmaps 中叫做偏移量。

![image-20220123005317451](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220123005317451.png)

需要注意的是：BitMap 在 Redis 中并不是一个新的数据类型，其底层是 Redis 实现。

## BitMap 相关命令

```text
# 设置值，其中value只能是 0 和 1
setbit key offset value

# 获取值
getbit key offset

# 获取指定范围内值为 1 的个数
# start 和 end 以字节为单位
bitcount key start end

# BitMap间的运算
# operations 位移操作符，枚举值
  AND 与运算 &
  OR 或运算 |
  XOR 异或 ^
  NOT 取反 ~
# result 计算的结果，会存储在该key中
# key1 … keyn 参与运算的key，可以有多个，空格分割，not运算只能一个key
# 当 BITOP 处理不同长度的字符串时，较短的那个字符串所缺少的部分会被看作 0。返回值是保存到 destkey 的字符串的长度（以字节byte为单位），和输入 key 中最长的字符串长度相等。
bitop [operations] [result] [key1] [keyn…]

# 返回指定key中第一次出现指定value(0/1)的位置
bitpos [key] [value]
```

在弄清 BitMap 到底占用多大的空间之前，我们再来重申下：**Redis 其实只支持 5 种数据类型，并没有 BitMap 这种类型，BitMap 底层是基于 Redis 的字符串类型实现的。**

我们通过下面的命令来看下 BitMap 占用的空间大小：

```text
# 首先将偏移量是0的位置设为1
127.0.0.1:6379> setbit csx:key:1 0 1
(integer) 0
# 通过STRLEN命令，我们可以看到字符串的长度是1
127.0.0.1:6379> STRLEN csx:key:1
(integer) 1
# 将偏移量是1的位置设置为1
127.0.0.1:6379> setbit csx:key:1 1 1
(integer) 0
# 此时字符串的长度还是为1，以为一个字符串有8个比特位，不需要再开辟新的内存空间
127.0.0.1:6379> STRLEN csx:key:1
(integer) 1
# 将偏移量是8的位置设置成1
127.0.0.1:6379> setbit csx:key:1 8 1
(integer) 0
# 此时字符串的长度编程2，因为一个字节存不下9个比特位，需要再开辟一个字节的空间
127.0.0.1:6379> STRLEN csx:key:1
(integer) 2
```

通过上面的实验我们可以看出，BitMap 占用的空间，就是底层字符串占用的空间。假如 BitMap 偏移量的最大值是 OFFSET_MAX，那么它底层占用的空间就是：

```text
(OFFSET_MAX/8)+1 = 占用字节数
```

因为字符串内存只能以字节分配，所以上面的单位是字节。

但是需要注意，Redis 中字符串的最大长度是 512M，所以 BitMap 的 offset 值也是有上限的，其最大值是：

```text
8 * 1024 * 1024 * 512  =  2^32
```

bitmap 主要就三个操作命令，setbit，getbit以及 bitcount



**a. 设置标记**

即setbit，主要是指将某个索引，设置为 1(设置 0 表示抹去标记)，基本语法如下

```
# 请注意这个index必须是数字，后面的value必须是0/1
setbit key index 0/1
```

对应的 SpringBoot 中，借助 RestTemplate 可以比较容易的实现，通常有两种写法，都可以

```java
@Autowired
private StringRedisTemplate redisTemplate;

/**
 * 设置标记位
 *
 * @param key
 * @param offset
 * @param tag
 * @return
 */
public Boolean mark(String key, long offset, boolean tag) {
    return redisTemplate.opsForValue().setBit(key, offset, tag);
}

public Boolean mark2(String key, long offset, boolean tag) {
    return redisTemplate.execute(new RedisCallback<Boolean>() {
        @Override
        public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            return connection.setBit(key.getBytes(), offset, tag);
        }
    });
}
```

上面两种写法的核心区别，就是 key 的序列化问题，第一种写法使用默认的 jdk 字符串序列化，和后面的getBytes()会有一些区别



**b. 判断存在与否**



即 getbit key index，如果返回 1，表示存在否则不存在

```
/**
 * 判断是否标记过
 *
 * @param key
 * @param offest
 * @return
 */
public Boolean container(String key, long offest) {
    return redisTemplate.opsForValue().getBit(key, offest);
}
```

**c. 计数**

即 bitcount key，统计和

```java
/**
 * 统计计数
 *
 * @param key
 * @return
 */
public long bitCount(String key) {
    return redisTemplate.execute(new RedisCallback<Long>() {
        @Override
        public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
            return redisConnection.bitCount(key.getBytes());
        }
    });
}
```

## scan命令/快速找出前缀为xx的键

需求描述：

Redis中有大量以xxx开头的key，在不使用keys命令的情况下，如何快速获取这些前缀的key（假如Redis里面有1亿个key，其中有10w个key是以某个固定的已知的前缀开头的，如果将它们全部找出来？）

解决方案：

redis自带的scan命令可以解决这个问题，redis的单线程的。keys指令会导致线程阻塞一段时间，线上服务会停顿，直到指令执行完毕，服务才能恢复。这个时候可以使用scan指令，scan指令可以无阻塞的提取出指定模式的key列表，但是会有一定的重复概率，在客户端做一次去重就可以了，但是整体所花费的时间会比直接用keys指令长。

语法：

```css
SCAN cursor [MATCH pattern] [COUNT count] [TYPE type]
```

选项：

MATCH选项
  和KEYS命令类似，后面可以跟一个通配符

COUNT选项
  SCAN不能保证每次迭代返回的元素数量，但可以使用COUNT选项根据经验调整SCAN的行为。基本上，使用COUNT，用户指定了每次调用时从集合中检索元素所需完成的工作量。这只是实现的一个提示，但是一般来说，但是在大多数情况下，这种提示都是有效的（在数据量少的情况下，COUNT值与返回的结果数量不相等）。

TYPE选项
  可以使用 TYPE 选项要求 SCAN 仅返回与给定类型匹配的对象。 TYPE 选项仅在整个数据库 SCAN 上可用，而不是 HSCAN 或 ZSCAN 等。

和scan命令相关的还有另外3个命令，分别是：



- SCAN 迭代当前选定的Redis数据库中的一组键
- SSCAN 迭代 Sets 类型的元素
- HSCAN 迭代 Hash 类型的字段及其相关值
- ZSCAN 迭代 Sorted Set 类型的元素及其相关分数

## **应用场景**

前面的基本使用比较简单，在介绍 String 数据结构的时候也提过，我们重点需要关注的是 bitmap 的使用场景，它可以干嘛用，什么场景下使用它会有显著的优势

- 日活统计
- 点赞
- bloomfilter

上面三个场景虽有相似之处，但实际的应用场景还是些许区别，接下来我们逐一进行说明

统计应用或网站的日活，这个属于比较常见的 case 了，如果是用 redis 来做这个事情，首先我们最容易想到的是 Hash 结构，一般逻辑如下

- 根据日期，设置 key，如今天为 2020/10/13, 那么 key 可以为 app_20_10_13
- 其次当用户访问时，设置 field 为 userId, value 设置为 true
- 判断日活则是统计 map 的个数hlen app_20_10_13

上面这个逻辑有毛病么？当然没有问题，但是想一想，当我们的应用做的很 nb 的时候，每天的日活都是百万，千万级时，这个内存开销就有点吓人了

接下来我们看一下 bitmap 可以怎么做

- 同样根据日期设置 key
- 当用户访问时，index 设置为 userId，setbit app_20_10_13 uesrId 1
- 日活统计 bitcount app_20_10_13

**简单对比一下上面两种方案**

当数据量小时，且 userid 分布不均匀，小的为个位数，大的几千万，上亿这种，使用 bitmap 就有点亏了，因为 userId 作为 index，那么 bitmap 的长度就需要能容纳最大的 userId，但是实际日活又很小，说明 bitmap 中间有大量的空白数据

反之当数据量很大时，比如百万/千万，userId 是连续递增的场景下，bitmap 的优势有两点：1.存储开销小， 2.统计总数快

--------------------



**1. 用户签到**

很多网站都提供了签到功能，并且需要展示最近一个月的签到情况，这种情况可以使用 BitMap 来实现。 根据日期 offset = （今天是一年中的第几天） % （今年的天数），key = 年份：用户id。

如果需要将用户的详细签到信息入库的话，可以考虑使用一个一步线程来完成。

**2. 统计活跃用户（用户登陆情况）**

使用日期作为 key，然后用户 id 为 offset，如果当日活跃过就设置为1。具体怎么样才算活跃这个标准大家可以自己指定。

假如 20201009 活跃用户情况是： [1，0，1，1，0] 20201010 活跃用户情况是 ：[ 1，1，0，1，0 ]

统计连续两天活跃的用户总数：

```text
bitop and dest1 20201009 20201010 
# dest1 中值为1的offset，就是连续两天活跃用户的ID
bitcount dest1
```

统计20201009 ~ 20201010 活跃过的用户：

```text
bitop or dest2 20201009 20201010
```

**3. 统计用户是否在线**

如果需要提供一个查询当前用户是否在线的接口，也可以考虑使用 BitMap 。即节约空间效率又高，只需要一个 key，然后用户 id 为 offset，如果在线就设置为 1，不在线就设置为 0。

**4. 实现布隆过滤器**

# 随机获取集合中的元素

3．随机获得集合中的元素 

SRANDMEMBER key [count] 

 SRANDMEMBER命令用来随机从集合中获取一个元素，如：

 redis＞SRANDMEMBER letters "a" 

redis＞SRANDMEMBER letters "b" 

redis＞SRANDMEMBER letters "a"  还可以传递count参数来一次随机获得多个元素，根据count的正负不同，具体表现也不同。 

（1）当count为正数时，SRANDMEMBER会随机从集合里获得count个不重复的元素。如果count的值大于集合中的元素个数，则SRANDMEMBER会返回集合中的全部元素。

（2）当count为负数时，SRANDMEMBER会随机从集合里获得|count|个的元素，这些元素有可能相同。 为了示例，我们先在letters集合中加入两个元素：  

redis＞SADD letters  c d (integer) 2

目前letters集合中共有“a”、“b”、“c”、“d”4个元素，下面使用不同的参数对SRANDMEMBER命令进行测试：  

redis＞SRANDMEMBER letters 2

(1) "a"

(2) "c"

redis＞SRANDMEMBER letters 2 

(1) "a" 

(2) "b" 

redis＞SRANDMEMBER letter 100 

细心的读者可能会发现SRANDMEMBER命令返回的数据似乎并不是非常的随机，从SRANDMEMBER letters -10这个结果中可以很明显地看出这个问题（b元素出现的次数相对较多①）**，出现这种情况是由集合类型采用的存储结构（散列表）造成的。散列表使用散列函数将元素映射到不同的存储位置（桶）上以实现0(1)时间复杂度的元素查找**，举个例子，当使用散列表存储元素b时，使用散列函数计算出b的散列值是0，所以将b存入编号为0 的桶（bucket）中，下次要查找b时就可以用同样的散列函数再次计算b的散列值并直接到相应的桶中找到b。当两个不同的元素的散列值相同时会出现冲突，Redis使用拉链法来解决冲突，即将散列值冲突的元素以链表的形式存入同一桶中，查找元素时先找到元素对应的桶，然后再从桶中的链表中找到对应的元素。使用SRANDMEMBER命令从集合中获得一个随机元素时，Redis首先会从所有桶中随机选择一个桶，然后再从桶中的所有元素中随机选择一个元素，所以元素所在的桶中的元素数量越少，其被随机选中的可能性就越大



（1）列表类型是通过链表实现的，获取靠近两端的数据速度极快，而当元素增多后，访问中间数据的速度会较慢，所以它更加适合实现如“新鲜事”或“日志”这样很少访问中间元素的应用。 

（2）有序集合类型是使用散列表和跳跃表（Skip list）实现的，所以即使读取位于中间部分的数据速度也很快（时间复杂度是O(log(N))）。 

（3）列表中不能简单地调整某个元素的位置，但是有序集合可以（通过更改这个元素的分数）。 

（4）有序集合要比列表类型更耗费内存。 有序集合类型算得上是 Redis的5种数据类型中最高级的类型了，在学习时可以与列表类型和集合类型对照理解。



# 加锁、锁互斥、释放锁

，现在某个客户端要加锁。如果该客户端面对的是一个redis cluster集群，他首先会根据hash节点选择一台机器。这里注意，仅仅只是选择一台机器！这点很关键！紧接着，就会发送一段lua脚本到redis上，
为啥要用lua脚本呢？因为一大坨复杂的业务逻辑，可以通过封装在lua脚本中发送给redis，保证这段复杂业务逻辑执行的原子性。

这段lua脚本是什么意思呢？这里KEYS[1]代表的是你加锁的那个key，比如说：RLock lock = redisson.getLock("myLock");这里你自己设置了加锁的那个锁key就是“myLock”。ARGV[1]代表的就是锁key的默认生存时间，默认30秒。ARGV[2]代表的是加锁的客户端的ID，类似于下面这样：8743c9c0-0795-4907-87fd-6c719a6b4586:1
给大家解释一下，第一段if判断语句，就是用“exists myLock”命令判断一下，如果你要加锁的那个锁key不存在的话，你就进行加锁。如何加锁呢？很简单，用下面的命令：hset myLock8743c9c0-0795-4907-87fd-6c719a6b4586:1 1，通过这个命令设置一个hash数据结构，上述就代表“8743c9c0-0795-4907-87fd-6c719a6b4586:1”这个客户端对“myLock”这个锁key完成了加锁。接着会执行“pexpire myLock 30000”命令，设置myLock这个锁key的生存时间是30秒。好了，到此为止，ok，加锁完成了。

锁互斥机制
那么在这个时候，如果客户端2来尝试加锁，执行了同样的一段lua脚本，会咋样呢？很简单，第一个if判断会执行“exists myLock”，发现myLock这个锁key已经存在了。接着第二个if判断，判断一下，myLock锁key的hash数据结构中，是否包含客户端2的ID，但是明显不是的，因为那里包含的是客户端1的ID。
所以，客户端2会获取到pttl myLock返回的一个数字，这个数字代表了myLock这个锁key的剩余生存时间。比如还剩15000毫秒的生存时间。此时客户端2会进入一个while循环，不停的尝试加锁。

watch dog自动延期机制
客户端1加锁的锁key默认生存时间才30秒，如果超过了30秒，客户端1还想一直持有这把锁，怎么办呢？
简单！只要客户端1一旦加锁成功，就会启动一个watch dog看门狗，他是一个后台线程，会每隔10秒检查一下，如果客户端1还持有锁key，那么就会不断的延长锁key的生存时间。

可重入加锁机制
那如果客户端1都已经持有了这把锁了，结果可重入的加锁会怎么样呢？
这时我们来分析一下上面那段lua脚本。第一个if判断肯定不成立，“exists myLock”会显示锁key已经存在了。第二个if判断会成立，因为myLock的hash数据结构中包含的那个ID，就是客户端1的那个ID，也就是“8743c9c0-0795-4907-87fd-6c719a6b4586:1” 此时就会执行可重入加锁的逻辑，他会用：
incrby myLock 8743c9c0-0795-4907-87fd-6c71a6b4586:1 1 ，通过这个命令，对客户端1的加锁次数，累加1。



释放锁机制
如果执行lock.unlock()，就可以释放分布式锁，此时的业务逻辑也是非常简单的。其实说白了，就是每次都对myLock数据结构中的那个加锁次数减1。如果发现加锁次数是0了，说明这个客户端已经不再持有锁了，此时就会用：“del myLock”命令，从redis里删除这个key。然后呢，另外的客户端2就可以尝试完成加锁了。这就是所谓的分布式锁的开源Redisson框架的实现机制。
一般我们在生产系统中，可以用Redisson框架提供的这个类库来基于redis进行分布式锁的加锁与释放锁。

上述Redis分布式锁的缺点
其实上面那种方案最大的问题，就是如果你对某个redis master实例，写入了myLock这种锁key的value，此时会异步复制给对应的master slave实例。但是这个过程中一旦发生redis master宕机，主备切换，redis slave变为了redis master。
接着就会导致，客户端2来尝试加锁的时候，在新的redis master上完成了加锁，而客户端1也以为自己成功加了锁。此时就会导致多个客户端对一个分布式锁完成了加锁。这时系统在业务语义上一定会出现问题，导致各种脏数据的产生。
所以这个就是redis cluster，或者是redis master-slave架构的主从异步复制导致的redis分布式锁的最大缺陷：在redis master实例宕机的时候，可能导致多个客户端同时完成加锁。



# watch dog 自动延期机制

客户端 1 加锁的锁 key 默认生存时间才 30 秒，如果超过了 30 秒，客户端 1 还想一直持有这把锁，怎么办呢？
简单！只要客户端 1 一旦加锁成功，就会启动一个 watch dog 看门狗，他是一个后台线程，会每隔 10秒检查一下，如果客户端 1 还持有锁 key，那么就会不断的延长锁 key 的生存时间。



![image-20211219121531745](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211219121531745.png)

![image-20211219121353797](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211219121353797.png)

在上文中,我们使用redis构建了一个分布式锁,但是请注意,该代码在单机环境下没有任何问题,但是我们在生产中往往都是redis集群部署,由于redis主从节点的数据同步是异步的,如果Redis的master节点在锁未同步到Slave节点的时候宕机了怎么办？举例来说:
  1.进程A在master节点获得了锁。
  2.在锁同步到slave之前，master宕机，数据还没有同步到slave
  3.slave变成了新的master节点
  4.进程B也得到了和A相同的锁.
  因此,如果你的业务允许在master宕机期间，多个客户端允许同时都持有锁,那如上的分布式锁是可以接受的,否则就不能使用上述的分布式锁,在这种情况下,redis官方为我们提供了另一种解决方案----RedLock算法.

# redis加锁命令分类

1. redis能用的的加锁命令分表是`INCR`、`SETNX`、`SET`

2. 第一种锁命令`INCR`

这种加锁的思路是， key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作进行加一。 
然后其它用户在执行 INCR 操作进行加一时，如果返回的数大于 1 ，说明这个锁正在被使用当中。

```php
1、 客户端A请求服务器获取key的值为1表示获取了锁



    2、 客户端B也去请求服务器获取key的值为2表示获取锁失败



    3、 客户端A执行代码完成，删除锁



    4、 客户端B在等待一段时间后在去请求的时候获取key的值为1表示获取锁成功



    5、 客户端B执行代码完成，删除锁



 



    $redis->incr($key);



    $redis->expire($key, $ttl); //设置生成时间为1秒
```

3. 第二种锁`SETNX`

这种加锁的思路是，如果 key 不存在，将 key 设置为 value 
如果 key 已存在，则 `SETNX` 不做任何动作

1、 客户端A请求服务器设置key的值，如果设置成功就表示加锁成功
    2、 客户端B也去请求服务器设置key的值，如果返回失败，那么就代表加锁失败
    3、 客户端A执行代码完成，删除锁
    4、 客户端B在等待一段时间后在去请求设置key的值，设置成功
    5、 客户端B执行代码完成，删除锁

    $redis->setNX($key, $value);
    $redis->expire($key, $ttl);



4. 第三种锁SET
   上面两种方法都有一个问题，会发现，都需要设置 key 过期。那么为什么要设置key过期呢？如果请求执行因为某些原因意外退出了，导致创建了锁但是没有删除锁，那么这个锁将一直存在，以至于以后缓存再也得不到更新。于是乎我们需要给锁加一个过期时间以防不测。 
   **但是借助 Expire 来设置就不是原子性操作了。所以还可以通过事务来确保原子性，但是还是有些问题，所以官方就引用了另外一个，使用 SET 命令本身已经从版本 2.6.12 开始包含了设置过期时间的功能。**

 1、 客户端A请求服务器设置key的值，如果设置成功就表示加锁成功
    2、 客户端B也去请求服务器设置key的值，如果返回失败，那么就代表加锁失败
    3、 客户端A执行代码完成，删除锁
    4、 客户端B在等待一段时间后在去请求设置key的值，设置成功
    5、 客户端B执行代码完成，删除锁
    $redis->set($key, $value, array('nx', 'ex' => $ttl));  //ex表示秒

5.redlock加锁

以上的锁完全满足了需求，但是官方另外还提供了一套加锁的算法，这里以PHP为例

```php
$servers = [
    ['127.0.0.1', 6379, 0.01],
      ['127.0.0.1', 6389, 0.01],
        ['127.0.0.1', 6399, 0.01],
 ];
    $redLock = new RedLock($servers);
    //加锁
    $lock = $redLock->lock('my_resource_name', 1000);
    //删除锁
    $redLock->unlock($lock)
```

上面是官方提供的一个加锁方法，就是和第6的大体方法一样，只不过官方写的更健壮。所以可以直接使用官方提供写好的类方法进行调用。官方提供了各种语言如何实现锁。

 # Redis实现分布式锁

Redis为单进程单线程模式，采用队列模式将并发访问变成串行访问，且多客户端对Redis的连接并不存在竞争关系Redis中可以使用SETNX命令实现分布式锁。

当且仅当 key 不存在，将 key 的值设为 value。若给定的 key 已经存在，则 SETNX 不做任何动作

SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。

返回值：设置成功，返回 1 。设置失败，返回 0 。 

使用SETNX完成同步锁的流程及事项如下：

使用SETNX命令获取锁，若返回0（key已存在，锁已存在）则获取失败，反之获取成功

为了防止获取锁后程序出现异常，导致其他线程/进程调用SETNX命令总是返回0而进入死锁状态，需要为该key设置一个“合理”的过期时间

释放锁，使用DEL命令将锁数据删除

![image-20210907094746803](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210907094746803.png)

![image-20210907094801793](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210907094801793.png)

![image-20210907095035196](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210907095035196.png)

第四点多了以下判断：

long currentVal = conn.GETSET("mykey",expireTime);

if(oldVal == curentVal){

conn.EXPIRE("mykey",1000);

return true;}

return false;

GETSET命令：用于设置指定key的值，并返回key的旧值 ，即设置了一个新的值，但返回原来的旧值

redis> GETSET db mongodb  # 没有旧值，返回 nil
(nil)

redis> GET db
"mongodb"

redis> GETSET db redis   # 返回旧值 mongodb
"mongodb"

redis> GET db
"redis" 

5.上面各种优化的根本问题在于SETNX和EXPIRE俩个指令无法保证原子性，redis2.6提供了直接执行lua脚本的方式，通过lua脚本来保证原子性

-----

另一篇回答：

setnx+setex：存在设置超时时间失败的问题，导致死锁

->解决：set(key,value,nx,px):将setnx+setex变成原子操作

问题：任务超时，锁自动释放，导致并发问题。解决：可以使用Redission看门狗监听，自动续期

加锁和释放锁不是同一个线程的问题：在value中存入uuid（线程唯一标识），删除锁时判断该标识（用lua保证原子性）

不可重入的问题：可以用redission解决（实现机制类似aqs计数）

异步复制可能造成锁丢失：使用Redlock解决





# Redlock

普通实现

说道Redis分布式锁大部分人都会想到：`setnx+lua`，或者知道`set key value px milliseconds nx`。后一种方式的核心实现命令如下：

```lua
- 获取锁（unique_value可以是UUID等）
SET resource_name unique_value NX PX 30000

- 释放锁（lua脚本中，一定要比较value，防止误解锁）
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

这种实现方式有3大要点（也是面试概率非常高的地方）：

1. set命令要用`set key value px milliseconds nx`；
2. value要具有唯一性；
3. 释放锁时要验证value值，不能误解锁；

事实上这类琐最大的缺点就是它加锁时只作用在一个Redis节点上，即使Redis通过sentinel保证高可用，如果这个master节点由于某些原因发生了主从切换，那么就会出现锁丢失的情况：

1. 在Redis的master节点上拿到了锁；
2. 但是这个加锁的key还没有同步到slave节点；
3. master故障，发生故障转移，slave节点升级为master节点；
4. 导致锁丢失。

正因为如此，Redis作者antirez基于分布式环境下提出了一种更高级的分布式锁的实现方式：**Redlock**。

它可以保证以下特性：

1. 安全特性：互斥访问，即永远只有一个 client 能拿到锁
2. 避免死锁：最终 client 都可能拿到锁，不会出现死锁的情况，即使原本锁住某资源的 client crash 了或者出现了网络分区
3. 容错性：只要大部分 Redis 节点存活就可以正常提供服务



antirez提出的redlock算法大概是这样的：

在Redis的分布式环境中，我们假设有N个Redis master。这些节点**完全互相独立，不存在主从复制或者其他集群协调机制**。我们确保将在N个实例上使用与在Redis单实例下相同方法获取和释放锁。现在我们假设有5个Redis master节点，同时我们需要在5台服务器上面运行这些Redis实例，这样保证他们不会同时都宕掉。

为了取到锁，客户端应该执行以下操作:

- 获取当前Unix时间，以毫秒为单位。
- 依次尝试从5个实例，使用相同的key和**具有唯一性的value**（例如UUID）获取锁。当向Redis请求获取锁时，客户端应该设置一个网络连接和响应超时时间，这个超时时间应该小于锁的失效时间。例如你的锁自动失效时间为10秒，则超时时间应该在5-50毫秒之间。这样可以避免服务器端Redis已经挂掉的情况下，客户端还在死死地等待响应结果。如果服务器端没有在规定时间内响应，客户端应该尽快尝试去另外一个Redis实例请求获取锁。
- 客户端使用当前时间减去开始获取锁时间（步骤1记录的时间）就得到获取锁使用的时间。**当且仅当从大多数**（N/2+1，这里是3个节点）**的Redis节点都取到锁，并且使用的时间小于锁失效时间时，锁才算获取成功**。
- 如果取到了锁，key的真正有效时间等于有效时间减去获取锁所使用的时间（步骤3计算的结果）。
- 如果因为某些原因，获取锁失败（没有在至少N/2+1个Redis实例取到锁或者取锁时间已经超过了有效时间），客户端应该在**所有的Redis实例上进行解锁**（即便某些Redis实例根本就没有加锁成功，防止某些节点获取到锁但是客户端没有得到响应而导致接下来的一段时间不能被重新获取锁）。
- 只要别人建立了一把分布式锁，你就得不断轮询去尝试获取锁

![image-20220213015248030](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220213015248030.png)

## Redlock源码

redisson已经有对redlock算法封装，接下来对其用法进行简单介绍，并对核心源码进行分析（假设5个redis实例）。

- POM依赖

```xml
<!-- https://mvnrepository.com/artifact/org.redisson/redisson -->
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.3.2</version>
</dependency>
```

#### 用法

首先，我们来看一下redission封装的redlock算法实现的分布式锁用法，非常简单，跟重入锁（ReentrantLock）有点类似：

```java
Config config = new Config();
config.useSentinelServers().addSentinelAddress("127.0.0.1:6369","127.0.0.1:6379", "127.0.0.1:6389")
        .setMasterName("masterName")
        .setPassword("password").setDatabase(0);
RedissonClient redissonClient = Redisson.create(config);
// 还可以getFairLock(), getReadWriteLock()
RLock redLock = redissonClient.getLock("REDLOCK_KEY");
boolean isLock;
try {
    isLock = redLock.tryLock();
    // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
    isLock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
    if (isLock) {
        //TODO if get lock success, do something;
    }
} catch (Exception e) {
} finally {
    // 无论如何, 最后都要解锁
    redLock.unlock();
}
```

#### 唯一ID

实现分布式锁的一个非常重要的点就是set的value要具有唯一性，redisson的value是怎样保证value的唯一性呢？答案是**UUID+threadId**。入口在redissonClient.getLock("REDLOCK_KEY")，源码在Redisson.java和RedissonLock.java中：

```java
protected final UUID id = UUID.randomUUID();
String getLockName(long threadId) {
    return id + ":" + threadId;
}
```

#### 获取锁

获取锁的代码为redLock.tryLock()或者redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS)，两者的最终核心源码都是下面这段代码，只不过前者获取锁的默认租约时间（leaseTime）是LOCK_EXPIRATION_INTERVAL_SECONDS，即30s：

```java
<T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit, long threadId, RedisStrictCommand<T> command) {
    internalLockLeaseTime = unit.toMillis(leaseTime);
    // 获取锁时向5个redis实例发送的命令
    return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, command,
              // 首先分布式锁的KEY不能存在，如果确实不存在，那么执行hset命令（hset REDLOCK_KEY uuid+threadId 1），并通过pexpire设置失效时间（也是锁的租约时间）
              "if (redis.call('exists', KEYS[1]) == 0) then " +
                  "redis.call('hset', KEYS[1], ARGV[2], 1); " +
                  "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                  "return nil; " +
              "end; " +
              // 如果分布式锁的KEY已经存在，并且value也匹配，表示是当前线程持有的锁，那么重入次数加1，并且设置失效时间
              "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                  "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                  "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                  "return nil; " +
              "end; " +
              // 获取分布式锁的KEY的失效时间毫秒数
              "return redis.call('pttl', KEYS[1]);",
              // 这三个参数分别对应KEYS[1]，ARGV[1]和ARGV[2]
                Collections.<Object>singletonList(getName()), internalLockLeaseTime, getLockName(threadId));
}
```

获取锁的命令中，

- **KEYS[1]**就是Collections.singletonList(getName())，表示分布式锁的key，即REDLOCK_KEY；
- **ARGV[1]**就是internalLockLeaseTime，即锁的租约时间，默认30s；
- **ARGV[2]**就是getLockName(threadId)，是获取锁时set的唯一值，即UUID+threadId：

------

#### 释放锁

释放锁的代码为redLock.unlock()，核心源码如下：

```
protected RFuture<Boolean> unlockInnerAsync(long threadId) {
    // 向5个redis实例都执行如下命令
    return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN,
            // 如果分布式锁KEY不存在，那么向channel发布一条消息
            "if (redis.call('exists', KEYS[1]) == 0) then " +
                "redis.call('publish', KEYS[2], ARGV[1]); " +
                "return 1; " +
            "end;" +
            // 如果分布式锁存在，但是value不匹配，表示锁已经被占用，那么直接返回
            "if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " +
                "return nil;" +
            "end; " +
            // 如果就是当前线程占有分布式锁，那么将重入次数减1
            "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " +
            // 重入次数减1后的值如果大于0，表示分布式锁有重入过，那么只设置失效时间，还不能删除
            "if (counter > 0) then " +
                "redis.call('pexpire', KEYS[1], ARGV[2]); " +
                "return 0; " +
            "else " +
                // 重入次数减1后的值如果为0，表示分布式锁只获取过1次，那么删除这个KEY，并发布解锁消息
                "redis.call('del', KEYS[1]); " +
                "redis.call('publish', KEYS[2], ARGV[1]); " +
                "return 1; "+
            "end; " +
            "return nil;",
            // 这5个参数分别对应KEYS[1]，KEYS[2]，ARGV[1]，ARGV[2]和ARGV[3]
            Arrays.<Object>asList(getName(), getChannelName()), LockPubSub.unlockMessage, internalLockLeaseTime, getLockName(threadId));

}
```



## redission的机制

Redisson 是一个高级的分布式协调 Redis 客服端，能帮助用户在分布式环境中轻松实现一些 Java 的对象

java语言中**`redisson`**实现了一种保证锁失效时间绝对大于业务程序执行时间的机制。官方叫做看门狗机制（Watchdog），他的主要原理是，**在程序成功获取锁之后，会fork一条子线程去不断的给该锁续期，直至该锁释放为止**！他的原理图大概如下所示：

![img](https://pic4.zhimg.com/80/v2-18a835cd6f1ae765c3c548e7b422d9f7_1440w.jpg)

redisson使用守护线程来进行锁的续期，（守护线程的作用：当**主线程**销毁，会和**主线程**一起销毁。）防止程序宕机后，线程依旧不断续命，造成死锁！

另外，Redisson还实现并且优化了 RedLock算法、公平锁、可重入锁、连锁等操作，使Redis分布式锁的实现方式更加简便高效！

1. 使用Redisson，代码如下(与使用ReentrantLock类似）

```csharp
// 1. 配置文件


Config config = new Config();
config.useSingleServer()
     .setAddress("redis://127.0.0.1:6379")
     .setPassword(RedisConfig.PASSWORD)
        .setDatabase(0);

//2. 构造RedissonClient

RedissonClient redissonClient = Redisson.create(config);
//3. 设置锁定资源名称
RLock lock = redissonClient.getLock("redlock");
lock.lock();
try {
  System.out.println("获取锁成功，实现业务逻辑");
Thread.sleep(10000);
} catch (InterruptedException e) {
 e.printStackTrace();
} finally {
lock.unlock();

}

```

关于Redlock算法的实现，在Redisson中我们可以使用RedissonRedLock来完成，具体使用细节可以参考大佬的文章： [mp.weixin.qq.com/s/8uhYult2h…](https://mp.weixin.qq.com/s/8uhYult2h_YUHT7q7YCKYQ)

# zk分布式锁

Zookeeper中节点分为4种类型：

**1.持久节点 （PERSISTENT）**

默认的节点类型。创建节点的客户端与zookeeper断开连接后，该节点依旧存在

**2.持久顺序节点（PERSISTENT_SEQUENTIAL）**

所谓顺序节点，就是在创建节点时，Zookeeper根据创建的时间顺序给该节点名称进行编号

**3.临时节点（EPHEMERAL）**

和持久节点相反，当创建节点的客户端与zookeeper断开连接后，临时节点会被删除

**4.临时顺序节点（EPHEMERAL_SEQUENTIAL）**

顾名思义，临时顺序节点结合和临时节点和顺序节点的特点：在创建节点时，Zookeeper根据创建的时间顺序给该节点名称进行编号；当创建节点的客户端与zookeeper断开连接后，临时节点会被删除



Zookeeper实现分布式锁的原理是基于Zookeeper的临时顺序节点，如下图：

![image-20220216011808521](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216011808521.png)

1. 使用zk的临时节点和有序节点，每个线程获取锁就是在zk创建一个临时有序的节点，比如在/lock/目录下。

2. 创建节点成功后，线程获取/lock目录下的所有临时节点，再判断当前线程创建的节点是否是所有的节点的序号最小的节点

3. 如果当前线程创建的节点是所有节点序号最小的节点，则认为获取锁成功。

4. 如果当前线程创建的节点不是所有节点序号最小的节点，则对节点序号的前一个节点添加一个事件监听。

   比如当前线程获取到的节点序号为`/lock/003`,然后所有的节点列表为`[/lock/001,/lock/002,/lock/003]`,则对`/lock/002`这个节点添加一个事件监听器。

如果锁释放了，会唤醒下一个序号的节点，然后重新执行第3步，判断是否自己的节点序号是最小。

比如`/lock/001`释放了，`/lock/002`监听到时间，此时节点集合为`[/lock/002,/lock/003]`,则`/lock/002`为最小序号节点，获取到锁。

![image-20220411100016285](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220411100016285.png)

同时，其可以避免服务宕机导致的锁无法释放，而产生的死锁问题。因为zk宕机，zk服务端发现zk客户端没有发送心跳，而且多次重试都收不到zk客户端的心跳，就把该客户端对应的临时有序节点删除，这样锁就释放了。

锁无法释放？使用Zookeeper可以有效的解决锁无法释放的问题，因为在创建锁的时候，客户端会在ZK中创建一个临时节点，一旦客户端获取到锁之后突然挂掉（Session连接断开），那么这个临时节点就会自动删除掉。其他客户端就可以再次获得锁。

非阻塞锁？使用Zookeeper可以实现阻塞的锁，客户端可以通过在ZK中创建顺序节点，并且在节点上绑定监听器，一旦节点有变化，Zookeeper会通知客户端，客户端可以检查自己创建的节点是不是当前所有节点中序号最小的，如果是，那么自己就获取到锁，便可以执行业务逻辑了。

不可重入？使用Zookeeper也可以有效的解决不可重入的问题，客户端在创建节点的时候，把当前客户端的主机信息和线程信息直接写入到节点中，下次想要获取锁的时候和当前最小的节点中的数据比对一下就可以了。如果和自己的信息一样，那么自己直接获取到锁，如果不一样就再创建一个临时的顺序节点，参与排队。

单点问题？使用Zookeeper可以有效的解决单点问题，ZK是集群部署的，只要集群中有半数以上的机器存活，就可以对外提供服务。
可以直接使用zookeeper第三方库Curator客户端，这个客户端中封装了一个可重入的锁服务。

Curator提供的InterProcessMutex是分布式锁的实现。acquire方法用户获取锁，release方法用于释放锁。

使用ZK实现的分布式锁好像完全符合了本文开头我们对一个分布式锁的所有期望。但是，其实并不是，Zookeeper实现的分布式锁其实存在一个缺点，那就是性能上可能并没有缓存服务那么高。因为每次在创建锁和释放锁的过程中，都要动态创建、销毁瞬时节点来实现锁功能。ZK中创建和删除节点只能通过Leader服务器来执行，然后将数据同步到所有的Follower机器上。
Zookeeper实现分布式锁的优点

有效的解决单点问题，不可重入问题，非阻塞问题以及锁无法释放的问题。实现起来较为简单。

![image-20220213232441587](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220213232441587.png)

- 有序节点：假如当前有一个父节点为`/lock`，我们可以在这个父节点下面创建子节点；

  zookeeper提供了一个可选的有序特性，例如我们可以创建子节点“/lock/node-”并且指明有序，那么zookeeper在生成子节点时会根据当前的子节点数量自动添加整数序号

  也就是说，如果是第一个创建的子节点，那么生成的子节点为`/lock/node-0000000000`，下一个节点则为`/lock/node-0000000001`，依次类推。

- 临时节点：客户端可以建立一个临时节点，在会话结束或者会话超时后，zookeeper会自动删除该节点。

- 事件监听：在读取数据时，我们可以同时对节点设置事件监听，当节点数据或结构变化时，zookeeper会通知客户端。当前zookeeper有如下四种事件：

- 节点创建
- 节点删除
- 节点数据修改
- 子节点变更

基于以上的一些zk的特性，我们很容易得出使用zk实现分布式锁的落地方案：

1. 使用zk的临时节点和有序节点，每个线程获取锁就是在zk创建一个临时有序的节点，比如在/lock/目录下。

2. 创建节点成功后，获取/lock目录下的所有临时节点，再判断当前线程创建的节点是否是所有的节点的序号最小的节点

3. 如果当前线程创建的节点是所有节点序号最小的节点，则认为获取锁成功。

4. 如果当前线程创建的节点不是所有节点序号最小的节点，则对节点序号的前一个节点添加一个事件监听。

   比如当前线程获取到的节点序号为`/lock/003`,然后所有的节点列表为`[/lock/001,/lock/002,/lock/003]`,则对`/lock/002`这个节点添加一个事件监听器。

如果锁释放了，会唤醒下一个序号的节点，然后重新执行第3步，判断是否自己的节点序号是最小。

比如`/lock/001`释放了，`/lock/002`监听到时间，此时节点集合为`[/lock/002,/lock/003]`,则`/lock/002`为最小序号节点，获取到锁

Zookeeper实现分布式锁的缺点

性能上不如使用缓存实现分布式锁。 需要对ZK的原理有所了解。

上面几种方式，哪种方式都无法做到完美。就像CAP一样，在复杂性、可靠性、性能等方面无法同时满足，所以，根据不同的应用场景选择最适合自己的方式才是王道。

# 数据库分布式锁

注意，虽然使用数据库方式可以实现分布式锁，但是这种实现方式还存在如下一些问题：

1、因为是基于数据库实现的，数据库的可用性和性能将直接影响分布式锁的可用性及性能，所以，数据库需要双机部署、数据同步、主备切换；

2、不具备可重入的特性，因为同一个线程在释放锁之前，行数据一直存在，无法再次成功插入数据，所以，需要在表中新增一列，用于记录当前获取到锁的机器和线程信息，在再次获取锁的时候，先查询表中机器和线程信息是否和当前机器和线程相同，若相同则直接获取锁；

3、没有锁失效机制，因为有可能出现成功插入数据后，服务器宕机了，对应的数据没有被删除，当服务恢复后一直获取不到锁，所以，需要在表中新增一列，用于记录失效时间，并且需要有定时任务清除这些失效的数据；

4、不具备阻塞锁特性，获取不到锁直接返回失败，所以需要优化获取逻辑，循环多次去获取。

5、在实施的过程中会遇到各种不同的问题，为了解决这些问题，实现方式将会越来越复杂；依赖数据库需要一定的资源开销，性能问题需要考虑。

　　MySQL如何做分布式锁？
　　在Mysql中创建一张表，设置一个主键或者UNIQUE KEY这个KEY就是要锁的KEY(商品ID),所以同一个KEY在mysql表里只能插入一次了，这样对锁的竞争就交给了数据库，处理同一个KEY数据库保证了只有一个节点能插入成功，其他节点都会插入失败。
　
　　DB分布式锁的实现：通过主键id或者唯一索性的唯一性进行加锁，**说白了就是加锁的形式是向一张表中插入一条数据，该条数据的id就是一把分布式锁**，例如当一次请求插入了一条id为1的数据，其他想要进行插入数据的并发请求必须等第一次请求执行完成后删除这条id为1的数据才能继续插入，实现了分布式锁的功能。
　　这样lock和unlock的思路就很简单了，伪代码：
　def lock：
exec sql:insert into locked-table (xxx)values (xxx)if result =true
　　return true
　　else
　　return false
exec sql:delete from lockedorder where order_id='order_id'def unlock 


# jedis redission lettuce对比

Jedis：是老牌的Redis的Java实现客户端，提供了比较全面的Redis命令的支持，
Redisson：实现了分布式和可扩展的Java数据结构。
Lettuce：高级Redis客户端，用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器。

优点：

Jedis：比较全面的提供了Redis的操作特性
Redisson：促使使用者对Redis的关注分离，提供很多分布式相关操作服务，例如，分布式锁，分布式集合，可通过Redis支持延迟队列
Lettuce：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Lettuce的API是线程安全的，所以可以操作单个Lettuce连接来完成各种操作



Jedis：使用阻塞的I/O，且其方法调用都是同步的，程序流需要等到sockets处理完I/O才能执行，不支持异步。Jedis客户端实例不是线程安全的，所以需要通过连接池来使用Jedis。
Redisson：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Redisson的API是线程安全的，所以可以操作单个Redisson连接来完成各种操作
Lettuce：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Lettuce的API是线程安全的，所以可以操作单个Lettuce连接来完成各种操作
lettuce能够支持redis4，需要java8及以上。 lettuce是基于netty实现的与redis进行同步和异步的通信

# 使用过 Redis 做异步队列么

你是怎么用的？有什么缺点？
一般使用 list 结构作为队列，rpush 生产消息，lpop 消费消息。当 lpop 没有消息的时候，要适当 sleep一会再重试。

Redis通过`list`数据结构来实现消息队列.主要使用到如下命令：

- lpush和rpush入队列
- lpop和rpop出队列
- blpop和brpop阻塞式出队列
- ![image-20211217193852890](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211217193852890.png)

```php
$redis = new Redis();
$redis->connect('127.0.0.1', 6379);

//发送消息
$redis->lPush($list, $value);

//消费消息
while (true) {
    try {
        $msg = $redis->rPop($list);
        if (!$msg) {
            sleep(1);
        }
        //业务处理
     
    } catch (Exception $e) {
        echo $e->getMessage();
    }
}
```

上面代码会有个问题如果队列长时间是空的，那pop就不会不断的循环，这样会导致redis的QPS升高，影响性能。所以我们使用`sleep`来解决，当没有消息的时候阻塞一段时间。但其实这样还会带来另一个问题，就是`sleep`会导致消息的处理延迟增加。这个问题我们可以通过`blpop/brpop` 来阻塞读取队列。

`blpop/brpop`在队列没有数据的时候，会立即进入休眠状态，一旦数据到来，则立刻醒过来。消息的延迟几乎为零。用blpop/brpop替代前面的lpop/rpop，就完美解决了上面的问题。

还有一个需要注意的点是我们需要是用`try/catch`来进行异常捕获，如果一直阻塞在那里，Redis服务器一般会主动断开掉空链接，来减少闲置资源的占用。

缺点：
在消费者下线的情况下，生产的消息会丢失，得使用专业的消息队列如 rabbitmq 等。
能不能生产一次消费多次呢？
使用 pub/sub 主题订阅者模式，可以实现 1:N 的消息队列。

## **redis延迟队列**

你是否在做电商项目的时候会遇到如下场景：

- 订单下单后超过一小时用户未支付，需要关闭订单
- 订单的评论如果7天未评价，系统需要自动产生一条评论

这个时候我们就需要用到延时队列了，顾名思义就是需要延迟一段时间后执行。Redis可通过`zset`来实现。我们可以将有序集合的value设置为我们的消息任务，把value的score设置为消息的到期时间，然后轮询获取有序集合的中的到期消息进行处理。

实现代码如下：

```php
$redis = new Redis();
$redis->connect('127.0.0.1', 6379);

$redis->zAdd($delayQueue,$tts, $value);

while(true) {
    try{
        $msg = $redis->zRangeByScore($delayQueue,0,time(),0,1);
        if($msg){
            continue;
        }
        //删除消息
        $ok = $redis.zrem($delayQueue,$msg);
        if($ok){
            //业务处理
        }
    } catch(\Exception $e) {

    }
}
```


这里又产生了一个问题，同一个任务可能会被多个进程取到之后再使用 zrem 进行争抢，那些没抢到的进程都是白取了一次任务，这是浪费。解决办法：将 `zrangebyscore`和`zrem`使用lua脚本进行原子化操作,这样多个进程之间争抢任务时就不会出现这种浪费了。

# Redis缓存和数据库双写一致性

**1、更新完数据库再更新缓存（不推荐）**
这么做引发的问题有两个
1、如果A,B两个线程同时做数据更新，A先更新了数据库，B后更新数据库，则此时数据库里存的是B的数据。而更新缓存的时候，是B先更新了缓存，而A后更新了缓存，则缓存里是A的数据。这样缓存和数据库的数据也不一致。

2、很多时候，在复杂点的缓存场景，缓存不单单是数据库中直接取出来的值。
  比如可能更新了某个表的一个字段，然后其对应的缓存，是需要查询另外两个表的数据并进行运算，才能计算出缓存最新的值的。
  另外更新缓存的代价有时候是很高的。是不是说，每次修改数据库的时候，都一定要将其对应的缓存更新一份？也许有的场景是这样，但是对于比较复杂的缓存数据计算的场景，就不是这样了。如果你频繁修改一个缓存涉及的多个表，缓存也频繁更新。但是问题在于，这个缓存到底会不会被频繁访问到？

  举个栗子，一个缓存涉及的表的字段，在 1 分钟内就修改了 20 次，或者是 100 次，那么缓存更新 20 次、100 次；但是这个缓存在 1 分钟内只被读取了 1 次，有大量的冷数据。实际上，如果你只是删除缓存的话，那么在 1 分钟内，这个缓存不过就重新计算一次而已，开销大幅度降低。用到缓存才去算缓存。

  其实删除缓存，而不是更新缓存，就是一个 lazy 计算的思想，不要每次都重新做复杂的计算，不管它会不会用到，而是让它到需要被使用的时候再重新计算。像 mybatis，hibernate，都有懒加载思想。查询一个部门，部门带了一个员工的 list，没有必要说每次查询部门，都把里面的 1000 个员工的数据也同时查出来啊。80% 的情况，查这个部门，就只是要访问这个部门的信息就可以了。先查部门，同时要访问里面的员工，那么这个时候只有在你要访问里面的员工的时候，才会去数据库里面查询 1000 个员工。

**2、先删除缓存再更新数据库**
1、并发操作问题
该方案会导致不一致的原因是。同时有一个请求A进行更新操作，另一个请求B进行查询操作。那么会出现如下情形:
（1）请求A进行写操作，删除缓存
（2）请求B查询发现缓存不存在
（3）请求B去数据库查询得到旧值
（4）请求B将旧值写入缓存
（5）请求A将新值写入数据库

上述情况就会导致不一致的情形出现。而且，如果不采用给缓存设置过期时间策略，该数据永远都是脏数据。

2、还有一个问题就是第一步删除缓存成功，第二步写数据库失败**造成cache中无数据，db中是旧数据，此时有请求过来，则会查询旧数据并写入缓存**

那么，如何解决呢？采用延时双删+设置超时时间

```java
public void write(String key,Object data){

        redis.delKey(key);

        db.updateData(data);

        Thread.sleep(1000);

        redis.delKey(key);

    }

```

转化为中文描述就是
（1）先淘汰缓存
（2）再写数据库（这两步和原来一样）
（3）休眠1秒，再次淘汰缓存

这么做，可以将1秒内所造成的缓存脏数据，再次删除。
那么，这个1秒怎么确定的，具体该休眠多久呢？
针对上面的情形，读者应该自行评估自己的项目的读数据业务逻辑的耗时。然后写数据的休眠时间则在读数据业务逻辑的耗时基础上，加几百ms即可。这么做的目的，就是确保读请求结束，写请求可以删除读请求造成的缓存脏数据。
如果你用了mysql的读写分离架构怎么办？
ok，在这种情况下，造成数据不一致的原因如下，还是两个请求，一个请求A进行更新操作，另一个请求B进行查询操作。
（1）请求A进行写操作，删除缓存
（2）请求A将数据写入数据库了，
（3）请求B查询缓存发现，缓存没有值
（4）请求B去从库查询，这时，还没有完成主从同步，因此查询到的是旧值
（5）请求B将旧值写入缓存
（6）数据库完成主从同步，从库变为新值

上述情形，就是数据不一致的原因。还是使用双删延时策略。只是，睡眠时间修改为在主从同步的延时时间基础上，加几百ms。

采用这种同步淘汰策略，吞吐量降低怎么办？
ok，那就将第二次删除作为异步的。自己起一个线程，异步删除。这样，写的请求就不用沉睡一段时间后了，再返回。这么做，加大吞吐量。
第二次删除,如果删除失败怎么办？
这是个非常好的问题，因为第二次删除失败，就会出现如下情形。还是有两个请求，一个请求A进行更新操作，另一个请求B进行查询操作，为了方便，假设是单库：
（1）请求A进行写操作，删除缓存
（2）请求B查询发现缓存不存在
（3）请求B去数据库查询得到旧值
（4）请求B将旧值写入缓存
（5）请求A将新值写入数据库
（6）请求A试图去删除请求B写入对缓存值，结果失败了。

ok,这也就是说。如果第二次删除缓存失败，会再次出现缓存和数据库不一致的问题。如何解决呢？
其实就是要提供一个保证能成功删除的重试机制！！！先看看第3种方案



**3、更新完数据库再删除缓存(推荐)**
首先，先说一下。老外提出了一个缓存更新套路，名为《Cache-Aside pattern》。其中就指出
失效：应用程序先从cache取数据，没有得到，则从数据库中取数据，成功后，放到缓存中。
命中：应用程序从cache中取数据，取到后返回。
更新：先把数据存到数据库中，成功后，再让缓存失效。
另外，知名社交网站facebook也在论文《Scaling Memcache at Facebook》中提出，他们用的也是先更新数据库，再删缓存的策略。
假设这会有两个请求，一个请求A做查询操作，一个请求B做更新操作，那么会有如下情形产生
（1）缓存刚好失效
（2）请求A查询数据库，得一个旧值
（3）请求B将新值写入数据库
（4）请求B删除缓存
（5）请求A将查到的旧值写入缓存

ok，如果发生上述情况，确实是会发生脏数据。
然而，发生这种情况的概率又有多少呢？
发生上述情况有一个先天性条件，就是步骤（3）的写数据库操作比步骤（2）的读数据库操作耗时更短，才有可能使得步骤（4）先于步骤（5）。可是，大家想想，数据库的读操作的速度远快于写操作的（不然做读写分离干嘛，做读写分离的意义就是因为读操作比较快，耗资源少），因此步骤（3）耗时比步骤（2）更短，这一情形很难出现。

假设，有人非要抬杠，有强迫症，一定要解决怎么办？
如何解决上述并发问题？
首先，给缓存设有效时间是一种方案。其次，采用策略（2）里给出的异步延时删除策略，保证读请求完成以后，再进行删除操作。
还有其他造成不一致的原因么？
有的，这也是缓存更新策略（2）和缓存更新策略（3）都存在的一个问题，如果删缓存失败了怎么办，那不是会有不一致的情况出现么。比如一个写数据请求，然后写入数据库了，删缓存失败了，这会就出现不一致的情况了。这也是缓存更新策略（2）里留下的最后一个疑问。

如何解决？
提供一个保障的**重试机制**即可!!!，这里给出两套方案。

方案一：
如下图所示

![image-20220203195740974](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220203195740974.png)

流程如下所示
（1）更新数据库数据；
（2）缓存因为种种问题删除失败
（3）将需要删除的key发送至消息队列
（4）自己消费消息，获得需要删除的key
（5）继续重试删除操作，直到成功

然而，该方案有一个缺点，对业务线代码造成大量的侵入。于是有了方案二，在方案二中，启动一个订阅程序去订阅数据库的binlog，获得需要操作的数据。在应用程序中，另起一段程序，获得这个订阅程序传来的信息，进行删除缓存操作。

方案二：

流程如下图所示：

![image-20220203195834869](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220203195834869.png)

（1）更新数据库数据
（2）数据库会将操作信息写入binlog日志当中
（3）订阅程序提取出所需要的数据以及key
（4）另起一段非业务代码，获得该信息
（5）尝试删除缓存操作，发现删除失败
（6）将这些信息发送至消息队列
（7）重新从消息队列中获得该数据，重试操作。

(即：删除缓存失败的这一信息会发送到消息队列里面去，接收端一旦接收到这样的消息就进行重试)

备注说明： **上述的订阅binlog程序在mysql中有现成的中间件叫canal，可以完成订阅binlog日志的功能**。另外，重试机制，博主是采用的是消息队列的方式。 如果对一致性要求不是很高，直接在程序中另起一个线程，每隔一段时间去重试即可，这些大家可以灵活自由发挥，只是提供一个思路。
————————————————

附录：普通双删和延时双删

普通双删

问题：第一次清空缓存后、更新数据库前：其他事务查询了数据库hang住
第二次清空缓存后：其他事务更新缓存，此时又会把旧数据更新到缓存

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200912164040657.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1ODkwNTcy,size_16,color_FFFFFF,t_70#pic_center)

为什么需要延时双删？

第二次清空缓存之前，多延时一会儿，等B更新缓存结束了，再删除缓存，这样就缓存就不存在了，其他事务查询到的为新缓存。

延时是确保 **修改数据库 -> 清空缓存前，其他事务的更改缓存操作已经执行完。**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200912164842270.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1ODkwNTcy,size_16,color_FFFFFF,t_70#pic_center)

五、以上策略还能不能完善

四中说到，采用延时删最后一次缓存，但这其中难免还是会大量的查询到旧缓存数据的。
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020091216544982.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1ODkwNTcy,size_16,color_FFFFFF,t_70#pic_center)
这时候可以通过加锁来解决，一次性不让太多的线程都来请求，另外从图上看，我们可以尽量缩短`第一次删除缓存`和`更新数据库`的时间差，这样可以使得其他事务第一时间获取到更新数据库后的数据。另外，该方式（第5种，相对第2种，只后删缓存的，可以大大的减少获取到旧缓存的数量）

# 序列化和反序列化



```java
@Data
//序列化、反序列化忽略的属性，多个时用“,”隔开
@JsonIgnoreProperties({"captcha"})
//当属性的值为空（null或者""）时，不进行序列化，可以减少数据传输
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserVoByJson {
// 序列化、反序列化时，属性的名称
@JsonProperty("userName")
private String username;

// 为反序列化期间要接受的属性定义一个或多个替代名称，可以与@JsonProperty一起使用
@JsonAlias({"pass_word", "passWord"})
@JsonProperty("pwd")
private String password;

//序列化、反序列化时，格式化时间
@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
private Date createDate;

//序列化、反序列化忽略属性
@JsonIgnore
private String captcha;
```

}

```java
/*
        $.ajax({
           type:"POST",
           url:"http://localhost:10099/testByJson",
           data:JSON.stringify({
                userName:"sa",
                pass_word:"123fff",
                captcha:"abcd",
                createDate:"2019-08-05 11:34:31"
            }),
           dataType:"JSON",
           contentType:"application/json;charset=UTF-8",
           success:function(data){
               console.log(data);
           },
           error:function(data){
                console.log("报错啦");
           }
        })
     */
    /**
     * 反序列化方式注入，只能post请求
     */
    @PostMapping("testByJson")
    public UserVoByJson testByJson(@RequestBody UserVoByJson userVo) {
        System.out.println(userVo);
        return userVo;
    }
```

调用

```java
@Data
//序列化、反序列化忽略的属性，多个时用“,”隔开
@JsonIgnoreProperties({"captcha"})
//当属性的值为空（null或者""）时，不进行序列化，可以减少数据传输
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserVoByJson {

    // 序列化、反序列化时，属性的名称
//    @JsonProperty("userName")
    private String username;

    // 为反序列化期间要接受的属性定义一个或多个替代名称，可以与@JsonProperty一起使用
//    @JsonAlias({"pass_word", "passWord"})
//    @JsonProperty("pwd")
    private String password;

    //序列化、反序列化时，格式化时间
//    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    //序列化、反序列化忽略属性
//    @JsonIgnore
    private String captcha;

}
```

反序列化（后端控制台打印）

```
UserVoByJson(username=sa, password=123fff, createDate=null, captcha=null)
```

　　序列化（ajax的回调）

```
{username: "sa", password: "123fff"}
```

captcha属性前端已经传值，但设置了@JsonIgnoreProperties注解反序列化时该属性被忽略，因此为空，而序列化的时候@JsonInclude配置的是JsonInclude.Include.NON_EMPTY，当属性的值为空（null或者""）时，不进行序列化，所以序列化的最终结果如上所示





# 应用场景

（1）、会话缓存（Session Cache）

缓存减轻 MySQL 的查询压力，提升系统性能；

最常用的一种使用 Redis 的情景是会话缓存（session cache）。用 Redis 缓存会话比其他存储（如 Memcached）的优势在于：Redis 提供持久化。当维护一个不是严格要求一致性的缓存时，如果用户的购物车信息全部丢失，大部分人都会不高兴的，现在，他们还会这样吗？
幸运的是，随着 Redis 这些年的改进，很容易找到怎么恰当的使用 Redis 来缓存会话的文档。甚至广为人知的商业平台 Magento 也提供 Redis 的插件。
（2）、全页缓存（FPC）
除基本的会话 token 之外，Redis 还提供很简便的 FPC 平台。回到一致性问题，**即使重启了Redis 实例，因为有磁盘的持久化，用户也不会看到页面加载速度的下降，这是一个极大改进**（？为什么可以磁盘持久化？），类似 PHP 本地 FPC。
再次以 Magento 为例，Magento 提供一个插件来使用 Redis 作为全页缓存后端。
此外，对 WordPress 的用户来说，Pantheon 有一个非常好的插件 wp-redis，这个插件能帮助你以最快速度加载你曾浏览过的页面。
（3）队列
Reids 在内存存储引擎领域的一大优点是提供 list 和 set 操作，这使得 Redis 能作为一个很好的消息队列平台来使用。Redis 作为队列使用的操作，就类似于本地程序语言（如 Python）对 list 的 push/pop 操作。
如果你快速的在 Google 中搜索“Redis queues”，你马上就能找到大量的开源项目，这些项目的目的就是利用 Redis 创建非常好的后端工具，以满足各种队列需求。例如，Celery 有一个后台就是使用 Redis 作为 broker，你可以从这里去查看。
（4），排行榜/计数器
Redis 在内存中对数字进行递增或递减的操作实现的非常好。集合（Set）和有序集合（Sorted Set）也使得我们在执行这些操作的时候变的非常简单，Redis 只是正好提供了这两种数据结构。所以，我们要从排序集合中获取到排名最靠前的 10 个用户–我们称之为“user_scores”，

当然，这是假定你是根据你用户的分数做递增的排序。如果你想返回用户及用户的分数，你需要这样执行：
ZRANGE user_scores 0 10 WITHSCORES
Agora Games 就是一个很好的例子，用 Ruby 实现的，它的排行榜就是使用 Redis 来存储数据的，你可以在这里看到。
（5）、发布/订阅
最后（但肯定不是最不重要的）是 Redis 的发布/订阅功能。发布/订阅的使用场景确实非常多。我已看见人们在社交网络连接中使用，还可作为基于发布/订阅的脚本触发器，甚至用Redis 的发布/订阅功能来建立聊天系统！（不，这是真的，你可以去核实）。

1. 好友关系：利用集合的一些命令，比如求交集、并集、差集等。可以方便解决一些共同好友、共同爱好之类的功能；
2. 消息队列：除了 Redis 自身的发布/订阅模式，我们也可以利用 List 来实现一个队列机制，比如：到货通知、邮件发送之类的需求，不需要高可靠，但是会带来非常大的 DB 压力，完全可以用 List 来完成异步解耦；
3. Session 共享：Session 是保存在服务器的文件中，如果是集群服务，同一个用户过来可能落在不同机器上，这就会导致用户频繁登陆；采用 Redis 保存 Session 后，无论用户落在那台机器上都能够获取到对应的 Session 信息。

**Redis 不适合的场景**

数据量太大、数据访问频率非常低的业务都不适合使用 Redis，数据太大会增加成本，访问频率太低，保存在内存中纯属浪费资源。



# 请用Redis和任意语言实现一段恶意登录保护的代码

，限制1小时
内每用户Id最多只能登录5次。具体登录函数或功能用空函数即可，
不用详细写出。
用列表实现:列表中每个元素代表登陆时间,只要最后的第5次登陆时间和现在时间差不超过1小时就禁止
登陆.用Python写的代码如下：

```python
#!/usr/bin/env python3
import redis
import sys
import time
r = redis.StrictRedis(host=’127.0.0.1ʹ, port=6379, db=0)

try:
id = sys.argv[1]
except:
 print(‘input argument error’)
 sys.exit(0)
if r.llen(id) >= 5 and time.time() – float(r.lindex(id, 4)) <= 3600:
 print(“you are forbidden logining”)
else:
 print(‘you are allowed to login’)
 r.lpush(id, time.time())
```







# 代码：redis实现购物车

![image-20210910102626198](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910102626198.png)

![image-20210910102701247](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910102701247.png)

101 102表示商品id

![image-20210910102759387](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910102759387.png)

添加购物车商品

```java
  @Autowired
    private RedisTemplate redisTemplate;

    public static final String CART_KEY="cart:user:";//购物车key的前缀
    @PostMapping("/addCart")
    public void addCart(Cart obj)
    {
        String key = CART_KEY+obj.getUserId();
        Boolean hashkey = redisTemplate.opsForHash().getOperations().hasKey(key);
        if(hashkey)//如果没有过期
this.redisTemplate.opsForHash().put(key,obj.getProductId().toString,obj.getAmount());
        else {
            this.redisTemplate.opsForHash().put(key,obj.getProductId().toString,obj.getAmount());
this.redisTemplate.expire(key,90, TimeUnit.DAYS);//设置过期时间
        }
    }

  @PostMapping("findAll")//购物车列表的所有商品
    public CartPage findAll(Long userId)
  {
      String key = CART_KEY+obj.getUserId();
      CartPage cartPage = new CartPage();
      Long size = this.redisTemplate.opsForHash().size(key);//购物车数量
      cartPage.setCount((int)size);
      Map<String,Integer>map = this.redisTemplate.opsForHash().entries(key);
      List<Cart> carts = new ArrayList<>();
      for(Map.Entry<String,Integer>entry:map.entrySet())
      {
          Cart cart = new Cart();
          cart.setUserId(userId);
          cart.setProductId(Long.parseLong(entry.getKey()));
          cart.setAmount(entry.getValue());
          carts.add(cart);
      }
      cartPage.setCartList(carts);
      return cartPage;
  }
```

商品价格涨价

![image-20210910102842516](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910102842516.png)



# 代码：每分钟最多访问10次

尽管这种情况比较极端，但是在一些场合中还是需要粒度更小的控制方案。如果要精确地保证每分钟最多访问10次，需要记录下用户每次访问的时间。因此对每个用户，我们使用一个列表类型的键来记录他最近10次访问博客的时间。一旦键中的元素超过10个，就判断时间最早的元素距现在的时间是否小于1分钟。如果是则表示用户最近1分钟的访问次数超过了10次；如果不是就将现在的时间加入到列表中，同时把最早的元素删除。 上述流程的伪代码如下：  

listLength=LLEN rate.limiting:IP

 if listLength＜10 LPUSH rate.limiting:IP, now()

else time=LINDEX rate.limiting:IP, -1

 if now()-time＜60 print访问频率超过了限制，请稍后再试。

 else LPUS



实用。 具体的设置方法为：修改配置文件的maxmemory参数，限制Redis最大可用内存大小（单位是字节），当超出了这个限制时Redis会依据maxmemory-policy参数指定的策略来删除不需要的键，直到Redis占用的内存小于指定内存。 maxmemory-policy支持的规则如表4-1所示。

其中的LRU（LeasRecently Used）算法即“最近最少使用”，其认为最近最少使用的键在未来一段时间内也不会被用到，即当需要空间时这些键是可以被删除的。如当maxmemory-policy设置为allkeys-lru时，一旦Redis占用的内存超过了限制值，Redis会不断地删除数据库中最近最少使用的键①，直到占用的内存小于限制值。 注释：①事实上Redis并不会准确地将整个数据库中最久未被使用的键删除，而是每次从数据库中随机取3个键并删除这3个键中最久未被使用的键。删除生存时间最接近的键的实现方法也是这样。“3”这个数字可以通过Redis的配置文件中的maxmemory-samples参数设置。

![image-20211212133059236](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211212133059236.png)

![image-20211212133120527](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211212133120527.png)

# 使用Redis的zset统计在线用户信息

统计在线用户的数量，是应用很常见的需求了。如果需要精准的统计到用户是在线，离线状态，我想只有客户端和服务器通过保持一个TCP长连接来实现。如果应用本身并非一个IM应用的话，这种方式成本极高。

现在的应用都趋向于使用**心跳包**来标识用户是否在线。用户登录后，每隔一段时间，往服务器推送一个消息，表示当前用户在线。服务器则可以定义一个时间差，例如：**5分钟内收到过客户端心跳消息，视为在线用户**。

基于数据库实现

最简单的办法，就是在用户表，添加一个最后心跳包的日期时间字段 `last_active`。服务器收到心跳后，每次都去更新这个字段为当前的最新时间。

如果要**查询最近5分钟活跃的用户数量**，就可以简单的通过一句SQL完成。

```sql
SELECT COUNT(1) AS `online_user_count` FROM `user` WHERE `last_active` BETWEEN  '2020-12-22 13:00:00' AND '020-12-22 13:05:00';
复制代码
```

弊端也是显而易见，为了提高检索效率，不得不为`last_active`字段添加索引，而因为心跳的更新，会导致频繁的重新维护索引树，效率极其低下。

基于Redis实现

这是比较理想的一种实现方式了，Redis基于内存进行读写，性能自然比关系型数据库好得多，而且它所提供的**Zset**可以很方便的构建出一个在线用户的统计服务。

简单说明以下`zset`。它是一个有序的`set`集合，集合中的每个元素由2个东西组成

- member 既然是集合，那么它便是集合中的元素，并且不能重复
- score  既然是有序的，它就是用于排序的**权重**字段

### 

添加元素

```redis
ZADD key score member [score member ...]
```

一次性添加一个或者多个元素到集合，如果`member`已经存在则会使用当前`score `进行覆盖

统计所有的元素数量

```redis
ZCARD key
```

统计score值在min和max之间元素数量

```redis
ZCOUNT key min max
复制代码
```

删除score值在min和max之间的元素

```redis
ZREMRANGEBYSCORE key min max
复制代码
```

一个示例

我打算，用一个`zset`存储我内心中编程语言的评分排名，这个key叫做`lang`

\> zadd lang 999 php 10 java 9 go 8 python 7 javascript "5"

\> zcard lang "5"

\> zcount lang 8 10 "3"

删除评分在8 - 1000的元素，返回删除的个数

```redis
> ZREMRANGEBYSCORE lang 8 1000
"4"
```

知道了`zset`后，就可以实现一个**在线用户**的统计服务了。

实现思路

客户端每隔5分钟发送一个心跳到服务器，服务器根据会话获取到用户的ID，作为`zset`的`member` 存入`zset`，`score`便是当前收到心跳的时间戳，当同一个用户第二次发送心跳的时候，就会更新他对应的`score`值，由于更新是在内存，这个速度相当快。

```redis
zadd users 1608616915109 10000
```

需要统计出在线用户的数量，本质上就是需要统计出，最近5分钟有发送心跳的用户，通过`zcount`可以很轻松的统计出来。通过程序获取到当前的时间戳，作为`maxScore`，时间戳减去5分钟后作为`minScore`。

```redis
zcount users 1608616615109 1608616915109 

```

因为某些用户可能长时间没有登录过了，可以通过`ZREMRANGEBYSCORE`进行清理。通过程序获取到当前的时间戳，减去5分钟后作为`maxScore`，使用`0`， 作为`minScore`，表示清理所有超过5分钟没有发送过心跳包的用户。

```redis
ZREMRANGEBYSCORE users 0 1608616615109 
```

```java
@Component
public class OnlineUserStatsService {
    
    private static final String ONLINE_USERS = "onlie_users";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加用户在线信息
     * @param userId
     * @return 
     */
    public Boolean online(Integer userId) {
        return this.stringRedisTemplate.opsForZSet().add(ONLINE_USERS, userId.toString(), Instant.now().toEpochMilli());
    }
    
    /**
     * 获取一定时间内，在线的用户数量
     * @param duration
     * @return
     */
    public Long count(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return this.stringRedisTemplate.opsForZSet().count(ONLINE_USERS, 
                                    now.minus(duration).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
    
    /**
     * 获取所有在线过的用户数量，不论时间
     * @return
     */
    public Long count() {
        return this.stringRedisTemplate.opsForZSet().zCard(ONLINE_USERS);
    }
    
    /**
     * 清除超过一定时间没在线的用户数据
     * @param duration
     * @return
     */
    public Long clear(Duration duration) {
        return this.stringRedisTemplate.opsForZSet().removeRangeByScore(ONLINE_USERS, 0, LocalDateTime.now().minus(duration).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}


```

```java
System.out.println(conn.zadd("zset-weight",60,"Kelvin"));
```

作用

> 往zset-weight这个有序集合中添加Kelvin-60这个键值对

# redis管道

客户端和Redis使用TCP协议连接。不论是客户端向Redis发送命令还是Redis向客户端返回命令的执行结果，都需要经过网络传输，这两个部分的总耗时称为往返时延。根据网络性能不同，往返时延也不同，大致来说到本地回环地址（loop backaddress）的往返时延在数量级上相当于Redis处理一条简单命令（如LPUSH list 1 2 3）的时间。如果执行较多的命令，每个命令的往返时延累加起来对性能还是有一定影响的。 在执行多个命令时每条命令都需要等待上一条命令执行完（即收到Redis的返回结果）才能执行，即使命令不需要上一条命令的执行结果。如要获得post:1、post:2和post:3这3个键中的title字段，需要执行三条命令，如图4-2所示。

![image-20211212133501220](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211212133501220.png)

Redis的底层通信协议对管道（pipelining）提供了支持。通过管道可以一次性发送多条命令并在执行完后一次性将结果返回，当一组命令中每条命令都不依赖于之前命令的执行结果时就可以将这组命令一起通过管道发出。管道通过减少客户端与Redis的通信次数来实现降低往返时延累计值的目的，如图4-3所示。

![image-20211212133531904](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211212133531904.png)





# Redis 有哪些常见的功能？

1. 数据缓存功能
2. 分布式锁的功能
3. 支持数据持久化
4. 支持事务
5. 支持消息队列

14、Redis 支持的 Java 客户端都有哪些？官方推荐用哪个？
Redisson、Jedis、lettuce 等等，官方推荐使用 Redisson。



# 谈谈Redis都有哪些性能监控指标

## 监控指标

- 性能指标：Performance
- 内存指标: Memory
- 基本活动指标：Basic activity
- 持久性指标: Persistence
- 错误指标：Error
- 性能指标：Performance

------

| Name                      | Description              |
| :------------------------ | :----------------------- |
| latency                   | Redis响应一个请求的时间  |
| instantaneous_ops_per_sec | 平均每秒处理请求总数     |
| hi rate(calculated)       | 缓存命中率（计算出来的） |

内存指标: Memory

| Name                    | Description                                   |
| :---------------------- | :-------------------------------------------- |
| used_memory             | 已使用内存                                    |
| mem_fragmentation_ratio | 内存碎片率                                    |
| evicted_keys            | 由于最大内存限制被移除的key的数量             |
| blocked_clients         | 由于BLPOP,BRPOP,or BRPOPLPUSH而备阻塞的客户端 |

基本活动指标：Basic activity

| Name                       | Description                |
| :------------------------- | :------------------------- |
| connected_clients          | 客户端连接数               |
| conected_laves             | slave数量                  |
| master_last_io_seconds_ago | 最近一次主从交互之后的秒数 |
| keyspace                   | 数据库中的key值总数        |

持久性指标: Persistence

| Name                       | Description                        |
| :------------------------- | :--------------------------------- |
| rdb_last_save_time         | 最后一次持久化保存磁盘的时间戳     |
| rdb_changes_sice_last_save | 自最后一次持久化以来数据库的更改数 |

错误指标：Error

| Name                           | Description                           |
| :----------------------------- | :------------------------------------ |
| rejected_connections           | 由于达到maxclient限制而被拒绝的连接数 |
| keyspace_misses                | key值查找失败(没有命中)次数           |
| master_link_down_since_seconds | 主从断开的持续时间（以秒为单位)       |

监控方式

- redis-benchmark
- redis-stat
- redis-faina
- redislive
- redis-cli
- monitor
- showlog
- 1）get：获取慢查询日志
- 2）len：获取慢查询日志条目数
- 3）reset：重置慢查询日志

相关配置：

```
slowlog-log-slower-than 1000 # 设置慢查询的时间下线，单位：微秒
slowlog-max-len 100 # 设置慢查询命令对应的日志显示长度，单位：命令数
```

- info（可以一次性获取所有的信息，也可以按块获取信息）
- 1）server:服务器运行的环境参数
- 2）clients:客户端相关信息
- 3）memory：服务器运行内存统计数据
- 4）persistence：持久化信息
- 5）stats：通用统计数据
- 6）Replication：主从复制相关信息
- 7）CPU：CPU使用情况
- 8）cluster：集群信息
- 9）Keypass：键值对统计数量信息

终端info命令使用

- ./redis-cli info 按块获取信息 | grep 需要过滤的参数
- ./redis-cli info stats | grep ops

交互式info命令使用

```
 #./redis-cli
> info server
```

性能监控：

```
redis-cli info | grep ops # 每秒操作数
```

![图片](https://mmbiz.qpic.cn/mmbiz_png/eQPyBffYbucyqrQdXzmEhaVVsiaTn5BFG60JXv9BoKXDXTrxiagdDOLuxy31iaxGGKhEmRX2YSLM2e9dpicUrNEV4Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

内存监控：

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep used | grep human
used_memory_human:2.99M  # 内存分配器从操作系统分配的内存总量
used_memory_rss_human:8.04M  #操作系统看到的内存占用，top命令看到的内存
used_memory_peak_human:7.77M # redis内存消耗的峰值
used_memory_lua_human:37.00K   # lua脚本引擎占用的内存大小
```

由于BLPOP,BRPOP,or BRPOPLPUSH而备阻塞的客户端

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep blocked_clients
blocked_clients:0
```

由于最大内存限制被移除的key的数量

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep evicted_keys
evicted_keys:0  #
```

内存碎片率

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep mem_fragmentation_ratio
mem_fragmentation_ratio:2.74
```

已使用内存

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep used_memory:
used_memory:3133624
```

基本活动指标：

redis连接了多少客户端

通过观察其数量可以确认是否存在意料之外的连接。如果发现数量不对劲，就可以使用lcient list指令列出所有的客户端链接地址来确定源头。

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep connected_clients
connected_clients:1
[root@CombCloud-2020110836 src]# ./redis-cli info | grep connected
connected_clients:1   # 客户端连接数量
connected_slaves:1   # slave连接数量
```

持久性指标：

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep rdb_last_save_time
rdb_last_save_time:1591876204  # 最后一次持久化保存磁盘的时间戳
[root@CombCloud-2020110836 src]# ./redis-cli info | grep rdb_changes_since_last_save
rdb_changes_since_last_save:0   # 自最后一次持久化以来数据库的更改数
```

错误指标

由于超出最大连接数限制而被拒绝的客户端连接次数，如果这个数字很大，则意味着服务器的最大连接数设置得过低，需要调整maxclients

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep connected_clients
connected_clients:1
```

key值查找失败(没有命中)次数，出现多次可能是被hei ke gongjji

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep keyspace
keyspace_misses:0
```

主从断开的持续时间（以秒为单位)

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep rdb_changes_since_last_save
rdb_changes_since_last_save:0
```

复制积压缓冲区如果设置得太小，会导致里面的指令被覆盖掉找不到偏移量，从而触发全量同步

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep backlog_size
repl_backlog_size:1048576
```

通过查看`sync_partial_err`变量的次数来决定是否需要扩大积压缓冲区，它表示主从半同步复制失败的次数

```
[root@CombCloud-2020110836 src]# ./redis-cli info | grep sync_partial_err
sync_partial_err:1
```

redis性能测试命令

```
./redis-benchmark -c 100 -n 5000
```

说明：100个连接，5000次请求对应的性能



# epoll模型与select模型的区别



I/O多路复用器**单个进程可以同时处理多个描述符的I/O**，Java应用程序通过调用多路复用器来获取有事件发生的文件描述符，以进行I/O的读/写操作。多路复用器常见的底层实现模型有epoll模型和select模型，本节详细介绍它们各自的特点。

 select模型有以下3个特点。 

（1）select模型只有一个select函数，**每次在调用select函数时，都需要把整个文件描述符集合从用户态拷贝到内核态，当文件描述符很多时，开销会比较大。** 

（2）每次在调用select函数时**，内核都需要遍历所有的文件描述符**，这个开销也很大，尤其是当很多文件描述符根本就无状态改变时，也需要遍历，浪费性能。 

（3）**select可支持的文件描述符有上限**，可监控的文件描述符个数取决于sizeOf(fd_set)的值。如果sizeOf(fd_set)=512，那么此服务器最多支持512×8=4096个文件描述符。 

epoll模型比select模型复杂，epoll模型有三个函数。第一个函数为int epoll_create(int size)，用于创建一个epoll句柄。第二个函数为int epoll_ctl(int epfd,int op,int fd,struct epoll_event*event)，其中，第一个参数为epoll_create函数调用返回的值；第二个参数表示操作动作，由三个宏（EPOLL_CTL_ADD表示注册新的文件描述符到此epfd上，EPOLL_CTL_MOD表示修改已经注册的文件描述符的监听事件，EPOLL_CTL_DEL表示从epfd中删除一个文件描述符）来表示；第三个参数为需要监听的文件描述符；第四个参数表示要监听的事件类型，事件类型也是几个宏的集合，主要是文件描述符可读、可写、发生错误、被挂断和触发模式设置等。epoll模型的第三个函数为epoll_wait，表示等待文件描述符就绪。

epoll模型与select模型相比，在以下这些地方进行了改善。

 · 所有需要监听的文件描述符只需在调用第二个函数int epoll_ctl时拷贝一次即可，当文件描述符状态发生改变时，内核会把文件描述符放入一个就绪队列中，**通过调用epoll_wait函数获取就绪的文件描述符**。 

· 每次调用epoll_wait函数只会遍历状态发生改变的文件描述符，无须全部遍历，降低了操作的时间复杂度。 

· 没有文件描述符个数的限制。 

· **采用了内存映射机制，内核直接将就绪队列通过MMAP的方式映射到用户态，避免了内存拷贝带来的额外性能开销**。

Reactor模式本质上指的是使用”IO[多路复用](https://www.zhihu.com/search?q=多路复用&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A2362137281})(IO multiplexing) + 非阻塞IO(non-blocking  IO)”的模式。所谓“IO多路复用”，指的就是select/poll/epoll这一系列的多路选择器。它支持线程同时在多个文件描述符上阻塞，并在其中某个文件描述符可读写时收到通知。  IO复用其实复用的不是IO连接，而是复用线程，让一个线程能够处理多个连接。

**所谓”非阻塞IO“核心思想是指避免阻塞在read()或者write()或者其他的IO系统调用上**，这样可以最大限度地复用线程，让一个线程能服务于多个socket连接。在Reactor模式中，IO线程只能阻塞在IO  multiplexing函数。

# 内存映射机制（mmap）

内存映射文件，是由一个文件到一块内存的映射。[Win32](https://baike.baidu.com/item/Win32/4894730)提供了允许应用程序把文件映射到一个进程的函数 ([CreateFileMapping](https://baike.baidu.com/item/CreateFileMapping/9621670))。内存映射文件与[虚拟内存](https://baike.baidu.com/item/虚拟内存/101812)有些类似，通过内存映射文件可以保留一个[地址空间](https://baike.baidu.com/item/地址空间/1423980)的区域，同时将[物理存储器](https://baike.baidu.com/item/物理存储器/7414328)提交给此区域，内存文件映射的物理存储器来自一个已经存在于磁盘上的文件，而且在对该文件进行操作之前必须首先对文件进行映射。**使用内存映射文件处理存储于磁盘上的文件时，将不必再对文件执行I/O操作，使得内存映射文件在处理大数据量的文件时能起到相当重要的作用。**

现代意义上的操作系统都处于32位保护模式下。每个进程一般都能寻址4G的物理空间。但是我们的[物理内存]一般都是几百M，进程怎么能获得4G的物理空间呢？这就是使用了[虚拟地址]的好处，通常我们使用一种叫做[虚拟内存](https://baike.baidu.com/item/虚拟内存/101812)的技术来实现，因为可以使用硬盘中的一部分来当作内存使用。

(虚拟内存它使得[应用程序](https://baike.baidu.com/item/应用程序/5985445)认为它拥有连续的可用的[内存](https://baike.baidu.com/item/内存/103614)（一个连续完整的[地址空间](https://baike.baidu.com/item/地址空间/1423980)），而实际上，它通常是被分隔成多个[物理内存](https://baike.baidu.com/item/物理内存/2502263)碎片，还有部分暂时存储在外部[磁盘存储器](https://baike.baidu.com/item/磁盘存储器/2386684)上，在需要时进行[数据交换](https://baike.baidu.com/item/数据交换/1586256)。目前，大多数[操作系统](https://baike.baidu.com/item/操作系统/192)都使用了虚拟内存，如Windows家族的“虚拟内存”；Linux的“交换空间”等

即匀出一部分硬盘空间来充当内存使用。当内存耗尽时，电脑就会自动调用硬盘来充当内存，以缓解内存的紧张。若计算机运行程序或操作所需的[随机存储器](https://baike.baidu.com/item/随机存储器)([RAM](https://baike.baidu.com/item/RAM))不足时，则 Windows 会用[虚拟存储器](https://baike.baidu.com/item/虚拟存储器)进行补偿。)

另外一点现在操作系统都划分为系统空间和用户空间，使用虚拟地址可以很好的保护[内核空间](https://baike.baidu.com/item/内核空间/1128371)不被用户空间破坏。

对于虚拟地址如何转为物理地址,这个转换过程有操作系统和CPU共同完成.操作系统为CPU设置好[页表](https://baike.baidu.com/item/页表/679625)。CPU通过MMU单元进行地址转换。

用户空间是常规进程所在的区域，该区域执行的代码不能直接访问硬件设备。内核空间是操作系统所在的区域，该区域可以与设备控制器通讯，控制用户区域进程的运行状态。

- 内存映射文件技术是操作系统提供的一种新的文件数据存取机制，利用内存映射文件技术，系统可以在内存空间中为文件保留一部分空间，并将文件映射到这块保留空间，一旦文件被映射后，操作系统将管理页映射缓冲以及高速缓冲等任务，而不需要调用分配、释放内存块和文件输入/输出的API函数，也不需要自己提供任何缓冲算法。
- 使用内存映射文件处理存储于磁盘上的文件时，将不必再对文件执行I/O 操作，这意味着在对文件进行处理时将不必再为文件申请并分配缓存，所有的文件缓存操作均由系统直接管理，由于取消了将文件数据加载到内存、数据从内存到文件的回写以及释放内存块等步骤，使得内存映射文件在处理大数据量的文件时能起到相当重要的作用。

# Redis 为什么这么快？



1. 完全基于内存，绝大部分请求是纯粹的内存操作，非常快速；
2. 数据结构简单，对数据操作也简单；
3. 采用单线程，避免了不必要的上下文切换和竞争条件，也不存在多进程或者多线程导致的切换而消耗 CPU，不用去考虑各种锁的问题，不存在加锁释放锁操作，没有因为可能出现死锁而导致的性能消耗；
4. 使用多路 I/O 复用模型，非阻塞 IO。

关于多路复用Io：

打一个比方：小曲在S城开了一家快递店，负责同城快送服务。小曲因为资金限制，雇佣了一批快递员，然后小曲发现资金不够了，只够买一辆车送快递。
经营方式一 客户每送来一份快递，小曲就让一个快递员盯着，然后快递员开车去送快递。慢慢的小曲就发现了这种经营方式存在下述问题
几十个快递员基本上时间都花在了抢车上了，大部分快递员都处在闲置状态，谁抢到了车，谁就能去送快递随着快递的增多，快递员也越来越多，小曲发现快递店里越来越挤，没办法雇佣新的快递员了
经营方式二 小曲只雇佣一个快递员。然后呢，客户送来的快递，小曲按送达地点标注好，然后依次放在一个地方。
最后，那个快递员依次的去取快递，一次拿一个，然后开着车去送快递，送好了就回来拿下一个快递。

于是我们有如下结论 1、经营方式一就是传统的并发模型，每个I/O流(快递)都有一个新的线程(快递员)管理。 2、经营方式二就是I/O多路复用。只有单个线程(一个快递员)，通过跟踪每个I/O流的状态(每个快递的送达地点)，来管理多个I/O流。

![image-20220216020430580](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216020430580.png)

Redis单线程为什么这么快
Redis基于Reactor模式开发了网络事件处理器、文件事件处理器file event handler。.它是单线程的，所以Redis才叫做单线程的模型，它采用lO多路复用机制来同时监听多个Socket,根据Socket.上的事件类型来选择对应的事件处理器来处理这个事件。可以实现高性能的网络通信模型，又可以跟内部其他单线程的模块进行对接，保证了Redis内部的线程模型的简单性。
文件事件处理器的结构包含4个部分：多个socket、IO多路复用程序、文件事件分派器以及事件处理器（命令请求处理器、命令回复处理器、连接应答处理器等)。 

多个Socket可能并发的产生不同的事件，IO多路复用程序会监听多个Socket,会将Socket放入一个队列中排队，每次从队列中有序、同步取出一个Socket给事件分派器，事件分派器把Socket给对应的事件处理器。然后一个Socket的事件处理完之后，IO多路复用程序才会将队列中的下一个Socket给事件分派器。文件事件分派器会根据每个Socket当前产生的事件，来选择对应的事件处理器来处理。
I、Redis启动初始化时，将连接应答处理器跟AE_READABLE事件关联。
2、若一个客户端发起连接，会产生一个AE_READABLE事件，然后由连接应答处理器负责和客户端建立连接，创建客户端对应的socket,同时将这个socket的AE_READABLE事件和命令请求处理器关联，使得客户端可以向主服务器发送命令请求。
3、当客户端向Redis发请求时（不管读还是写请求），客户端socket都会产生一个AE_READABLE事件，触发命令请求处理器。处理器读取客户端的命令内容，然后传给相关程序执行。
4、当Redis服务器准备好给客户端的响应数据后，会将socketf的AE_WRITABLE事件和命令回复处理器关联，当客户端准备好读取响应数据时，会在socket产生一个AE_WRITABLE事件，由对应命令回复处理器处理，即将准备好的响应数据写入socket,供客户端读取。
5、命令回复处理器全部写完到socket后，就会删除该socket的AE_WRITABLE事件和命令回复处理器的映射。
单线程快的原因：
1)纯内存操作

2)核心是基于非阻塞的1O多路复用机制
3）单线程避免了线程上下文切换的开销



![](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211010193645753.png)

5. 丰富的数据结构（全称采用hash结构，读取速度非常快，对数据存储进行了一些优化，比如亚索表，跳表等）
4. C语言实现，效率高

# redis跳表



跳跃表是有序集合的底层实现之一。
Redis的跳跃表实现由zskiplist和zskiplistNode两个结构组成，其中zskiplist用于保存跳跃表信息（比如表头节点、表尾节点、长度），而zskiplistNode则用于表示跳跃表节点。
每个跳跃表节点的层高都是1至32之间的随机数。
在同一个跳跃表中，多个节点可以包含相同的分值，但每个节点的成员对象必须是唯一的。
跳跃表中的节点按照分值大小进行排序，当分值相同时，节点按照成员对象的大小进行排序。
和链表、字典等数据结构被广泛地应用在Redis内部不同，Redis只在两个地方用到了跳跃表，一个是实现有序集合键，另一个是在集群节点中用作内部数据结构，除此之外，跳跃表在Redis里面没有其他用途
查找元素：

对于普通链表的查找，即使有序，我们也不能使用二分法，需要从头开始，一个一个找，时间复杂度为O(n)。而对于跳跃表，从名字可以看出跳跃表的优势就在于可以跳跃。如何做到呢？在于其特殊的层设计。比如我们查找46，普通链表只能从头开始查找，比对-3,2,17...直到46，要比对7次。但是对于跳跃表，我们可以从最高层开始查找：

![image-20220225202718333](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220225202718333.png)

第一步：在L4层直接与55比对，发现大了，退回到第3层

第二步：在L3层与21比对，发现小了，继续往前比对55，发现大了，退回到第二层

第三步：在L2层与37比对，发现小了，往前，与55比对，发现大了，退回到第一层

第四步：在第1层，与46比对，查找成功。

共比对了6次，比普通链表只节省了一次，似乎没什么优势。但如果细想，当链表比较长的时候，在高层查找时，跳过的元素数量将相当可观，提速的效果将非常明显。比如如果元素在55之后，在L4层，我们直接就跳过了7个元素，这是非常大的进步。

```java
/* ZSETs use a specialized version of Skiplists */
typedef struct zskiplistNode {
	// member 对象
    robj *obj;
	// 分值
    double score;
	// 后退指针
    struct zskiplistNode *backward;
	// 层
    struct zskiplistLevel {
		// 前进指针
        struct zskiplistNode *forward;
		// 节点在该层和前向节点的距离
        unsigned int span;
    } level[];
} zskiplistNode;
 
typedef struct zskiplist {
	// 头节点，尾节点
    struct zskiplistNode *header, *tail;
	// 节点数量
    unsigned long length;
	// 目前表内节点的最大层数
    int level;
} zskiplist;
```

下面我们来看跳跃表源码节点的zskiplistNode结构体。 

```c
typedef struct zskiplistNode {
  sds ele;
  double score;
  struct zskiplistNode *backward;
  struct zskiplistLevel {
    struct zskiplistNode *forward;
    unsigned int span;
  } level[];
 zskiplistNode; 
```

该结构体包含如下属性。 

1）ele：用于存储字符串类型的数据。 

2）score：用于存储排序的分值。 

3）backward：后退指针，只能指向当前节点最底层的前一个节点，头节点和第一个节点——backward指向NULL，从后向前遍历跳跃表时使用。 

4）level：为柔性数组。每个节点的数组长度不一样，在生成跳跃表节点时，随机生成一个1～64的值，值越大出现的概率越低。 level数组的每项包含以下两个元素。 

·forward：指向本层下一个节点，尾节点的forward指向NULL。 

·span：forward指向的节点与本节点之间的元素个数。span值越大，跳过的节点个数越多。
跳跃表是Redis有序集合的底层实现方式之一，**所以每个节点的ele存储有序集合的成员member值，score存储成员score值。所有节点的分值是按从小到大的方式排序的，当有序集合的成员分值相同时，节点会按member的字典序进行排序。** 
3.2.2　跳跃表结构 除了跳跃表节点外，还需要一个跳跃表结构来管理节点，Redis使用zskiplist结构体，定义如下： 

```c
typedef struct zskiplist {
  struct zskiplistNode *header, *tail;
  unsigned long length;
  int level;
} zskiplist;
```

 

该结构体包含如下属性。

 1）header：指向跳跃表头节点。头节点是跳跃表的一个特殊节点，它的level数组元素个数为64。头节点在有序集合中不存储任何member和score值，ele值为NULL，score值为0；也不计入跳跃表的总长度。头节点在初始化时，64个元素的forward都指向NULL，span值都为0。 

2）tail：指向跳跃表尾节点。 

3）length：跳跃表长度，表示除头节点之外的节点总数。 

4）level：跳跃表的高度。 通过跳跃表结构体的属性我们可以看到，程序可以在O(1)的时间复杂度下,快速获取到跳跃表的头节点、尾节点、长度和高度。
Redis通过zslRandomLevel函数随机生成一个1～64的值，作为新建节点的高度，值越大出现的概率越低。节点层高确定之后便不会再修改。生成随机层高的代码如下。 #define ZSKIPLIST_P 0.25   /* Skiplist P = 1/4 */
int zslRandomLevel(void) {
  int level = 1;
  while ((random()&0xFFFF) < (ZSKIPLIST_P * 0xFFFF))
    level += 1;
  return (level<ZSKIPLIST_MAXLEVEL) ? level : ZSKIPLIST_MAXLEVEL;
} 上述代码中，level的初始值为1，通过while循环，每次生成一个随机值，取这个值的低16位作为x，当x小于0.25倍的0xFFFF时，level的值加1；否则退出while循环。最终返回level和ZSKIPLIST_MAXLEVEL两者中的最小值。 下面计算节点的期望层高。假设p=ZSKIPLIST_P： 

1）节点层高为1的概率为(1-p)。 

2）节点层高为2的概率为p(1-p)。 

3）节点层高为3的概率为p2(1-p)。 

4）…… 5）节点层高为n的概率为pn-1(1-p)。 

# 熟悉哪些Redis 集群模式？

1. Redis Sentinel
体量较小时，选择 Redis Sentinel ，单主 Redis 足以支撑业务。
2. Redis Cluster
Redis 官方提供的集群化方案，体量较大时，选择 Redis Cluster ，通过分片，使用更多内存。
3. Twemprox
Twemprox 是Twtter 开源的一个 Redis 和 Memcached 代理服务器，主要用于管理 Redis 和Memcached 集群，减少与Cache 服务器直接连接的数量。
4. Codis
Codis 是一个代理中间件，当客户端向Codis 发送指令时， Codis 负责将指令转发到后面的Redis 来执行，并将结果返回给客户端。一个Codis 实例可以连接多个Redis 实例，也可以启动多个Codis 实例来支撑，每个Codis 节点都是对等的，这样可以增加整体的QPS 需求，还能起到容灾功能。
5. 客户端分片
在Redis Cluster 还没出现之前使用较多，现在基本很少热你使用了，在业务代码层实现，起几个毫无关联的Redis 实例，在代码层，对 Key 进行 hash 计算，然后去对应的 Redis 实例操作数据。这种方式对 hash 层代码要求比较高，考虑部分包括，节点失效后的替代算法方案，数据震荡后的自动脚本恢复，实例的监控，等等。



# redis单线程的理解

https://www.cnblogs.com/myseries/p/11733861.html

**单线程模型**

Redis基于Reactor模式开发了网络事件处理器，这个处理器叫做**文件事件处理器** file event handler。这个文件事件处理器，它是单线程的，所以 Redis 才叫做单线程的模型，它采用IO多路复用机制来同时监听多个Socket，根据Socket上的事件类型来选择对应的事件处理器来处理这个事件。可以实现高性能的网络通信模型，又可以跟内部其他单线程的模块进行对接，保证了 Redis 内部的线程模型的简单性。
文件事件处理器的结构包含4个部分：多个Socket、IO多路复用程序、文件事件分派器以及事件处理器
（命令请求处理器、命令回复处理器、连接应答处理器等）。
多个 Socket 可能并发的产生不同的操作，每个操作对应不同的文件事件，但是IO多路复用程序会监听多个 Socket，会将 Socket 放入一个队列中排队，**每次从队列中取出一个 Socket 给事件分派器，事件分派器把 Socket 给对应的事件处理器。**
然后一个 Socket 的事件处理完之后，IO多路复用程序才会将队列中的下一个 Socket 给事件分派器。文件事件分派器会根据每个 Socket 当前产生的事件，来选择对应的事件处理器来处理。
![image-20220302195939822](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220302195939822.png)

　　Redis客户端对服务端的每次调用都经历了发送命令，执行命令，返回结果三个过程。其中执行命令阶段，由于Redis是单线程来处理命令的，所==有每一条到达服务端的命令不会立刻执行，所有的命令都会进入一个队列中，然后逐个被执行==。并且多个客户端发送的命令的执行顺序是不确定的。但是可以确定的是不会有两条命令被同时执行，不会产生并发问题，这就是Redis的单线程基本模型。

![image-20220302200614423](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220302200614423.png)

**1. redis单线程问题**

　　单线程指的是网络请求模块使用了一个线程（所以不需考虑并发安全性），即一个线程处理所有网络请求，其他模块仍用了多个线程。

**2. 为什么说redis能够快速执行**

> (1) 绝大部分请求是纯粹的内存操作（非常快速）

> (2) 采用单线程,避免了不必要的上下文切换和竞争条件

> (3) 非阻塞IO - IO多路复用，Redis采用epoll做为I/O多路复用技术的实现，再加上Redis自身的事件处理模型将epoll中的连接，读写，关闭都转换为了时间，不在I/O上浪费过多的时间。

　　**Redis采用单线程模型，每条命令执行如果占用大量时间，会造成其他线程阻塞，对于Redis这种高性能服务是致命的，所以Redis是面向高速执行的数据库。**

**3. redis的内部实现**

　　内部实现采用epoll，采用了epoll+自己实现的简单的事件框架。epoll中的读、写、关闭、连接都转化成了事件，然后利用epoll的多路复用特性，绝不在io上浪费一点时间 这3个条件不是相互独立的，特别是第一条，如果请求都是耗时的，采用单线程吞吐量及性能可想而知了。应该说redis为特殊的场景选择了合适的技术方案。

**4. Redis关于线程安全问题**

　　 redis实际上是采用了线程封闭的观念，把任务封闭在一个线程，自然避免了线程安全问题，不过对于需要依赖多个redis操作的复合操作来说，依然需要锁，而且有可能是分布式锁。

 

另一篇对redis单线程的理解：[Redis单线程理解](https://www.e-learn.cn/content/redis/969074)

个人理解

​    redis分客户端和服务端，一次完整的redis请求事件有多个阶段（客户端到服务器的网络连接-->redis读写事件发生-->redis服务端的数据处理（单线程）-->数据返回）。平时所说的redis单线程模型，本质上指的是服务端的数据处理阶段，不牵扯网络连接和数据返回，这是理解redis单线程的第一步。接下来，针对不同阶段分别阐述个人的一些理解。

1：客户端到服务器的网络连接

首先，客户端和服务器是socket通信方式，socket服务端监听可同时接受多个客户端请求，这点很重要，如果不理解可先记住。注意这里可以理解为本质上与redis无关，这里仅仅做网络连接，或者可以理解为，为redis服务端提供网络交互api。

​    假设建立网络连接需要30秒（为了更容易理解，所以时间上扩大了N倍）

2：redis读写事件发生并向服务端发送请求数据

​    首先确定一点，redis的客户端与服务器端通信是基于TCP连接（不懂去看，基础很重要），第一阶段仅仅是建立了客户端到服务器的网络连接，然后才是发生第二阶段的读写事件。

​    完成了上一个阶段的网络连接，redis客户端开始真正向服务器发起读写事件，假设是set（写）事件，此时redis客户端开始向建立的网络流中送数据，服务端可以理解为给每一个网络连接创建一个线程同时接收客户端的请求数据。

​    假设从客户端发数据，到服务端接收完数据需要10秒。

3：redis服务端的数据处理

​    服务端完成了第二阶段的数据接收，接下来开始依据接收到的数据做逻辑处理，然后得到处理后的数据。数据处理可以理解为一次方法调用，带参调用方法，最终得到方法返回值。不要想复杂，重在理解流程。

​    假设redis服务端处理数据需要0.1秒

4：数据返回

​    这一阶段很简单，当reids服务端数据处理完后 就会立即返回处理后的数据，没什么特别需要强调的。

​    假设服务端把处理后的数据回送给客户端需要5秒。

**那么什么是Reids的单线程**

​    第一阶段说过，redis是以socket方式通信，socket服务端可同时接受多个客户端请求连接，也就是说，redis服务同时面对多个redis客户端连接请求，而redis服务本身是单线程运行。

​    假设，现在有A,B,C,D,E五个客户端同时发起redis请求，A优先稍微一点点第一个到达，然后是B，C，D，E依次到达，此时redis服务端开始处理A请求，建立连接需要30秒，获取请求数据需要10秒，然后处理数据需要0.1秒，回送数据给客户端需要5秒，总共大概需要45秒。也就是说，下一个B请求需要等待45秒，这里注意，也许这五个几乎同时请求，由于socket可以同时处理多个请求，所以建立网络连接阶段时间差可忽略，但是在第二阶段，服务端需要什么事都不干，坐等10秒中，对于CPU和客户端来说是无法忍受的。所以说单线程效率非常低，但是正是因为这些类似问题，Redis单线程本质上并不是如此运行。接下来讨论redis真正的单线程运行方式。

​    客户端与服务端建立连接交由socket，可以同时建立多个连接（这里应该是多线程/多进程），建立的连接redis是知道的（为什么知道，去看socket编程，再次强调基础很重要），然后redis会基于这些建立的连接去探测哪个连接已经接收完了客户端的请求数据（注意：不是探测哪个连接建立好了，而是探测哪个接收完了请求数据），而且这里的探测动作就是单线程的开始，一旦探测到则基于接收到的数据开始数据处理阶段，然后返回数据，再继续探测下一个已经接收完请求数据的网络连接。注意，从探测到数据处理再到数据返回，全程单线程。这应该就是所谓的redis单线程。至于内部有多复杂我们无需关心，我们追求的是理解流程，苛求原理，但不能把内脏都挖出来。

​    从探测到接受完请求数据的网络连接到最终的数据返回，服务器只需要5.1秒，这个时间是我放大N倍后的数据，实际时间远远小于这个，可能是5.1的N万分之一时间，为什么这么说，因为数据的处理是在本地内存中，速度有多快任你想象，最终的返回数据虽然牵扯到网络，但是网络连接已经建立，这个速度也是非常非常快的，只是比数据处理阶段慢那么一点点。因此单线程方式在效率上其实并不需要担心。

**IO多路复用**

　　参考：https://www.zhihu.com/question/32163005

　要弄清问题先要知道问题的出现原因

原因:

　　由于进程的执行过程是线性的(也就是顺序执行),当我们调用低速系统I/O(read,write,accept等等),进程可能阻塞,此时进程就阻塞在这个调用上,不能执行其他操作.阻塞很正常.

　　接下来考虑这么一个问题:一个服务器进程和一个客户端进程通信,服务器端read(sockfd1,bud,bufsize),此时客户端进程没有发送数据,那么read(阻塞调用)将阻塞，直到客户端调用write(sockfd,but,size)发来数据.在一个客户和服务器通信时这没什么问题；

　　当多个客户与服务器通信时当多个客户与服务器通信时,若服务器阻塞于其中一个客户sockfd1,当另一个客户的数据到达套接字sockfd2时,服务器不能处理,仍然阻塞在read(sockfd1,...)上;此时问题就出现了,不能及时处理另一个客户的服务,咋么办?

　　I/O多路复用来解决!

I/O多路复用:

　　继续上面的问题,有多个客户连接,sockfd1,sockfd2,sockfd3..sockfdn同时监听这n个客户,当其中有一个发来消息时就从select的阻塞中返回,然后就调用read读取收到消息的sockfd,然后又循环回select阻塞;这样就不会因为阻塞在其中一个上而不能处理另一个客户的消息

　　“I/O多路复用”的英文是“I/O multiplexing”,可以百度一下multiplexing，就能得到这个图：

[![img](https://img2018.cnblogs.com/blog/885859/201910/885859-20191025142446687-228003831.gif)](https://img2018.cnblogs.com/blog/885859/201910/885859-20191025142446687-228003831.gif)

**Q:**

　　那这样子，在读取socket1的数据时，如果其它socket有数据来，那么也要等到socket1读取完了才能继续读取其它socket的数据吧。那不是也阻塞住了吗？而且读取到的数据也要开启线程处理吧，那这和多线程IO有什么区别呢？

A:

　　1.CPU本来就是线性的不论什么都需要顺序处理并行只能是多核CPU

　　2.io多路复用本来就是用来解决对多个I/O监听时,一个I/O阻塞影响其他I/O的问题,跟多线程没关系.

　　3.跟多线程相比较,线程切换需要切换到内核进行线程切换,需要消耗时间和资源.而I/O多路复用不需要切换线/进程,效率相对较高,特别是对高并发的应用nginx就是用I/O多路复用,故而性能极佳.但多线程编程逻辑和处理上比I/O多路复用简单.而I/O多路复用处理起来较为复杂.

\-----------------------------------------------------------------------------------------------------------------------------------

　　I/O 指的是网络I/O。
　　多路指的是多个TCP 连接（Socket 或Channel）。
　　复用指的是复用一个或多个线程。


　　它的基本原理就是不再由应用程序自己监视连接，而是由内核替应用程序监视文件描述符。

　　客户端在操作的时候，会产生具有不同事件类型的socket。在服务端，I/O 多路复用程序（I/O Multiplexing Module）会把消息放入队列中，然后通过文件事件分派器（Fileevent Dispatcher），转发到不同的事件处理器中。

[![img](https://img2018.cnblogs.com/blog/885859/201910/885859-20191025163819424-934628585.png)](https://img2018.cnblogs.com/blog/885859/201910/885859-20191025163819424-934628585.png)

　　多路复用有很多的实现，以select 为例，当用户进程调用了多路复用器，进程会被阻塞。内核会监视多路复用器负责的所有socket，当任何一个socket 的数据准备好了，多路复用器就会返回。这时候用户进程再调用read 操作，把数据从内核缓冲区拷贝到用户空间。

　　I/O 多路复用的特点是通过一种机制一个进程能同时等待多个文件描述符，而这些文件描述符（套接字描述符）其中的任意一个进入读就绪（readable）状态，select()函数就可以返回。

**Redis 单线程 是否就代表线程安全**

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

class Demo extends Thread
{
    public void run()
    {
        Jedis jedis1 = new Jedis();
        for (int i=0;i<100;i++){
            int num = Integer.parseInt(jedis1.get("num"));// 1: 代码行1
            num = num + 1; // 2: 代码行2
            jedis1.set("num",num+"");
            System.out.println(jedis1.get("num"));
        }
    }
}

public class test{

    public static void main(String... args){
        Jedis jedis = new Jedis();
        jedis.set("num","1");
        new Demo().start();
        new Demo().start();
    }
}
```

　如代码所示，例如当线程1在代码行读取数值为99时候，此时线程2页执行读取操作也是99，随后同时执行num=num+1，之后更新，导致一次更新丢失，这就是这个代码测试的错误之处。所以Redis本身是线程安全的，但是你还需要保证你的业务必须也是线程安全的。

 

**注意：千万不要以为原子操作是线程安全的，原子操作只能保证命令全执行或者全不执行，并不会保证线程安全操作。例如数据库中的事务就是原子的，依旧还需要提供并发控制！！！！**

**redis I/O多路复用机制**

为什么redis要使用I/O多路复用技术？

​    Redis 是跑在单线程中的，所有的操作都是按照顺序线性执行的，但是由于读写操作等待用户输入或输出都是阻塞的，所以 I/O 操作在一般情况下往往不能直接返回，这会导致某一文件的 I/O 阻塞导致整个进程无法对其它客户提供服务，而 I/O多路复用就是为了解决这个问题而出现的。

　　多路I/O复用模型是利用 select、poll、epoll 可以同时监察多个流的 I/O 事件的能力，在空闲的时候，会把当前线程阻塞掉。当有一个或多个流有 I/O事件时，就从阻塞态中唤醒，于是程序就会轮询一遍所有的流（epoll 是只轮询那些真正发出了事件的流），并且只依次顺序的处理就绪的流，这种做法就避免了大量的无用操作。

这里“多路”指的是多个网络连接，“复用”指的是复用同一个线程。

　　采用多路 I/O 复用技术可以让单个线程高效的处理多个连接请求（尽量减少网络 IO 的时间消耗），且 Redis在内存中操作数据的速度非常快，也就是说内存内的操作不会成为影响Redis性能的瓶颈，主要由以下几点造就了 Redis 具有很高的吞吐量。

　(1) 网络IO都是通过Socket实现，Server在某一个端口持续监听，客户端通过Socket（IP+Port）与服务器建立连接（ServerSocket.accept），成功建立连接之后，就可以使用Socket中封装的InputStream和OutputStream进行IO交互了。针对每个客户端，Server都会创建一个新线程专门用于处理。

　(2) 默认情况下，网络IO是阻塞模式，即服务器线程在数据到来之前处于【阻塞】状态，等到数据到达，会自动唤醒服务器线程，着手进行处理。阻塞模式下，一个线程只能处理一个流的IO事件。

　(3) 为了提升服务器线程处理效率，有以下三种思路

  　　**a.非阻塞[忙轮询]**:采用死循环方式轮询每一个流，如果有IO事件就处理，这样一个线程可以处理多个流，但效率不高，容易导致CPU空转。

 　　 **b.Select代理(无差别轮询)**:可以观察多个流的IO事件，如果所有流都没有IO事件，则将线程进入阻塞状态，如果有一个或多个发生了IO事件，则唤醒线程去处理。但是会遍历所有的流，找出流需要处理的流。如果流个数为N，则时间复杂度为O(N)

  　　**c.Epoll代理**：Select代理有一个缺点，线程在被唤醒后轮询所有的Stream，会存在无效操作。Epoll哪个流发生了I/O事件会通知处理线程，对流的操作都是有意义的，复杂度降低到了O(1)。

举个栗子:

　　每个快递员------------------>每个线程

　　每个快递-------------------->每个socket(I/O流)

　　快递的物流位置-------------->socket的不同状态

　　客户(寄/收)快递请求-------------->来自客户端的请求

   快递公司的经营方式-------------->服务端运行的代码

​    一辆车---------------------->CPU的核数

1.经营方式一就是传统的并发模型，每个I/O流(快递)都有一个新的线程(快递员)管理。

2.经营方式二就是I/O多路复用。只有单个线程(一个快递员)，通过跟踪每个I/O流(快递)的状态(每个快递的送达地点)，来管理多个I/O流。

   redis线程模型:

[![img](https://img2018.cnblogs.com/blog/885859/201912/885859-20191217172616726-267761625.png)](https://img2018.cnblogs.com/blog/885859/201912/885859-20191217172616726-267761625.png)

**epoll IO多路复用模型实现机制**

　　epoll没有这个限制，它所支持的FD上限是最大可以打开文件的数目，这个数字一般远大于2048,一般来说这个数目和系统内存关系很大,具体数目可以 cat /proc/sys/fs/file-max查看，在1GB内存的机器上大约是10万左右

　　场景：有100万个客户端同时与一个服务器进程保持着TCP连接。而每一时刻，通常只有几百上千个TCP连接是活跃的。

　　在select/poll时代，服务器进程每次都把这100万个连接告诉操作系统(从用户态复制句柄数据结构到内核态)，让操作系统内核去查询这些套接字上是否有事件发生，轮询完后，再将句柄数据复制到用户态，让服务器应用程序轮询处理已发生的网络事件，这一过程资源消耗较大，因此，select/poll一般只能处理几千的并发连接。

　　如果没有I/O事件产生，我们的程序就会阻塞在select处。有个问题，我们从select仅知道了有I/O事件发生了，但却不知是哪几个流，只能无差别轮询所有流，找出能读或写数据的流进行操作。

　　使用select，O(n)的无差别轮询复杂度，同时处理的流越多，每一次无差别轮询时间就越长。

　　epoll的设计和实现与select完全不同。epoll通过在Linux内核中申请一个简易的文件系统(文件系统一般用B+树数据结构实现)。把原先的select/poll调用分成了3个部分：

　　1）调用epoll_create()建立一个epoll对象(在epoll文件系统中为这个句柄对象分配资源)

　　2）调用epoll_ctl向epoll对象中添加这100万个连接的套接字

　　3）调用epoll_wait收集发生的事件的连接实现上面场景只需要在进程启动时建立一个epoll对象，在需要的时候向epoll对象中添加或者删除连接。同时epoll_wait的效率也非常高，因为调用epoll_wait时，并没有一股脑的向操作系统复制这100万个连接的句柄数据，内核也不需要去遍历全部的连接。

**底层实现:**
当某一进程调用epoll_create方法时，Linux内核会创建一个eventpoll结构体，这个结构体中有两个成员与epoll的使用方式密切相关。eventpoll结构体如下所示： 

[![img](https://img2018.cnblogs.com/blog/885859/201912/885859-20191217172641238-1930483696.png)](https://img2018.cnblogs.com/blog/885859/201912/885859-20191217172641238-1930483696.png)

　　每一个epoll对象都有一个独立的eventpoll结构体，用于存放通过epoll_ctl方法向epoll对象中添加进来的事件。这些事件都会挂载在红黑树中，通过红黑树可以高效的识别重复事件(红黑树的插入时间效率是lg(n)，其中n为树的高度)。

　　所有添加到epoll中的事件都会与设备(网卡)驱动程序建立回调关系，当相应的事件发生时会调用这个回调方法。这个回调方法在内核中叫ep_poll_callback,它会将发生的事件添加到rdlist双链表中。

在epoll中，对于每一个事件，都会建立一个epitem结构体，如下所示：

[![img](https://img2018.cnblogs.com/blog/885859/201912/885859-20191217172656288-322132610.png)](https://img2018.cnblogs.com/blog/885859/201912/885859-20191217172656288-322132610.png)

　　当调用epoll_wait检查是否有事件发生时，只需要检查eventpoll对象中的rdlist双链表中是否有epitem元素即可。如果rdlist不为空，则把发生的事件复制到用户态，同时将事件数量返回给用户。

**优势：**

　　1. 不用重复传递。我们调用epoll_wait时就相当于以往调用select/poll，但是这时却不用传递socket句柄给内核，因为内核已经在epoll_ctl中拿到了要监控的句柄列表。

　　2. 在内核里，一切皆文件。epoll向内核注册了一个文件系统，用于存储上述的被监控socket。当你调用epoll_create时，就会在这个虚拟的epoll文件系统里创建一个file结点。这个file不是普通文件，它只服务于epoll。epoll在被内核初始化时（操作系统启动），同时会开辟出epoll自己的内核高速cache区，用于安置每一个我们想监控的socket，这些socket会以红黑树的形式保存在内核cache里，以支持快速的查找、插入、删除。这个内核高速cache区，就是建立连续的物理内存页，然后在之上建立slab层，简单的说，就是物理上分配好你想要的size的内存对象，每次使用时都是使用空闲的已分配好的对象。 

　　3. 极其高效的原因：我们在调用epoll_create时，内核除了帮我们在epoll文件系统里建了个file结点，在内核cache里建了个红黑树用于存储以后epoll_ctl传来的socket外，还会再建立一个list链表，用于存储准备就绪的事件，当epoll_wait调用时，仅仅观察这个list链表里有没有数据即可。有数据就返回，没有数据就sleep，等到timeout时间到后即使链表没数据也返回。  

 

　　这个准备就绪list链表是怎么维护的呢？当我们执行epoll_ctl时，除了把socket放到epoll文件系统里file对象对应的红黑树上之外，还会给内核中断处理程序注册一个回调函数，告诉内核，如果这个句柄的中断到了，就把它放到准备就绪list链表里。所以，当一个socket上有数据到了，内核在把网卡上的数据copy到内核中后就来把socket插入到准备就绪链表里了。epoll的基础就是回调呀！    

　　一颗红黑树，一张准备就绪句柄链表，少量的内核cache，就帮我们解决了大并发下的socket处理问题。执行epoll_create时，创建了红黑树和就绪链表，执行epoll_ctl时，如果增加socket句柄，则检查在红黑树中是否存在，存在立即返回，不存在则添加到树干上，然后向内核注册回调函数，用于当中断事件来临时向准备就绪链表中插入数据。执行epoll_wait时立刻返回准备就绪链表里的数据即可。   

　　epoll独有的两种模式LT和ET。无论是LT和ET模式，都适用于以上所说的流程。区别是，LT模式下，只要一个句柄上的事件一次没有处理完，会在以后调用epoll_wait时次次返回这个句柄，而ET模式仅在第一次返回。  

　　LT和ET都是电子里面的术语，ET是边缘触发，LT是水平触发，一个表示只有在变化的边际触发，一个表示在某个阶段都会触发。

　　LT, ET这件事怎么做到的呢？当一个socket句柄上有事件时，内核会把该句柄插入上面所说的准备就绪list链表，这时我们调用epoll_wait，会把准备就绪的socket拷贝到用户态内存，然后清空准备就绪list链表，最后，epoll_wait干了件事，就是检查这些socket，如果不是ET模式（就是LT模式的句柄了），并且这些socket上确实有未处理的事件时，又把该句柄放回到刚刚清空的准备就绪链表了。所以，非ET的句柄，只要它上面还有事件，epoll_wait每次都会返回这个句柄。

# redis主从同步机制

1.从节点执行slaveof masterip port，保存主节点信息

2.从节点中的定时任务发现主节点信息，建立和主节点的socket连接

3.从节点发送信号，主节点返回，两边能互相通信

4.连接建立后，主节点将所有数据发送给从节点(数据同步)

5.主节点把当前数据同步给从节点后，便完成了复制过程，接下来，主节点就会持续把命令发送给从节点，保证主从数据一致性

![image-20220209101653787](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220209101653787.png)

![image-20220209101716695](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220209101716695.png)

![image-20220209101740075](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220209101740075.png)

![image-20220209101755608](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220209101755608.png)

# 说说Redis 的线程模型



> 这问题是因为前面回答问题的时候提到了Redis 是基于非阻塞的IO复用模型。如果这个问题回答不上来，就相当于前面的回答是给自己挖坑，因为你答不上来，面试官对你的印象可能就要打点折扣了。

作为网络服务器，Redis内部操作遵循基本的执行流程，服务器端等待并在特定端口上监听请求连接。如果到达的客户端使用正确的Redis序列化协议（RESP）语法和格式，服务器就会接受此连接。在接受套接字连接后，Redis会为非阻塞读和写操作在数据库的内存状态上产生一个描述符。 对于Redis服务器来说，main函数通过调用aeMain函数创建事件循环，而该函数会创建一个无限while循环。该循环测试事件循环的stop属性，并在测试失败时退出循环。aeMain中while循环的每次迭代都会调用aeProcessEvents函数，并传入事件循环的指针及标志位。aeProcessEvents函数在处理所有文件事件之前会处理所有基于时间的事件。别忘了，POSIX系统将运行中的进程视为文件描述符，因此即便是从内存中读取值，Redis也会将这些读取操作视为事件管理代码中的文件描述符。在Redis中，基于时间的事件会根据传递给aeProcessEvents函数的标志位控制事件循环对事件的处理。事件范围从立即，到尽可能最短时间，再到阻塞，以及永远等待，所有这些可以使用时间值结构进行设置。 在运行中的Redis实例上使用LLDB调试器，我们可以通过查看Redis会话中的每一帧来追踪到目前为止的执行流。 

 

Redis 内部使用文件事件处理器 file event handler ，这个文件事件处理器是单线程的，所以Redis 才叫做单线程的模型。它采用 IO 多路复用机制同时监听多个 socket ，根据 socket 上的事件来选择对应的事件处理器进行处理。
文件事件处理器的结构包含 4 个部分：

1. 多个 socket 。
2. IO 多路复用程序。
3. 文件事件分派器。
4. 事件处理器（连接应答处理器、命令请求处理器、命令回复处理器）



多个 socket 可能会并发产生不同的操作，每个操作对应不同的文件事件，但是 IO 多路复用程序会监听多个 socket，会将 socket 产生的事件放入队列中排队，事件分派器每次从队列中取出一个事件，把该事件交给对应的事件处理器进行处理。

来看客户端与Redis 的一次通信过程：

![image-20211012153126784](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211012153126784.png)

下面来大致说一下这个图：
1. 客户端Socket01 向 Redis 的 Server Socket 请求建立连接，此时 Server Socket 会产生一个AE_READABLE 事件，IO 多路复用程序监听到 server socket 产生的事件后，将该事件压入队列中。文件事件分派器从队列中获取该事件，交给连接应答处理器。连接应答处理器会创建一个能与客户端通信的 Socket01，并将该 Socket01 的AE_READABLE 事件与命令请求处理器关
联。
2. 假设此时客户端发送了一个 set key value 请求，此时 Redis 中的 Socket01 会产生AE_READABLE 事件，IO 多路复用程序将事件压入队列，此时事件分派器从队列中获取到该事件，由于前面 Socket01 的 AE_READABLE 事件已经与命令请求处理器关联，因此事件分派器将事件交给命令请求处理器来处理。命令请求处理器读取 Socket01 的 set key value 并在自己内存中完成 set key value 的设置。操作完成后，它会将 Socket01 的AE_WRITABLE 事件与令回复处理器关联。

3. 如果此时客户端准备好接收返回结果了，那么 Redis 中的 Socket01 会产生一个AE_WRITABLE 事件，同样压入队列中，事件分派器找到相关联的命令回复处理器，由命令回复处理器对 Socket01 输入本次操作的一个结果，比如 ok ，之后解除 Socket01 的AE_WRITABLE 事件与命令回复处理器的关联。
    这样便完成了一次通信。 不要怕这段文字，结合图看，一遍不行两遍，实在不行可以网上查点资料结合着看，一定要搞清楚，否则前面吹的牛逼就白费了。





# redis整合Springcache自动缓存

@CachePut
@CachePut 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用

@CachePut 作用和配置方法

| 参数      | 解释                                                         | example                                                      |
| --------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| value     | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个       | @CachePut(value=”my cache”)                                  |
| key       | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | @CachePut(value=”testcache”,key=”#userName”)                 |
| condition | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | @CachePut(value=”testcache”,condition=”#userName.length()>2”) |



# redis作默认缓存的配置

```java
 @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
  @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
 
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
 
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
 
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);
 
        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
 
        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();
 
        return template;
    }
  /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }
 
    /**
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }
 
    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }
//=====================================业务层===============

 /**
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
     */
  @Autowired
    private RedisTemplate redisTemplate;
    public User findUserById(int id) {
        String key = "user_" + id;
 
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
 
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
 
        if (hasKey) {
            User user = operations.get(key);
            System.out.println("从缓存中获得数据："+user.getUserName());
            System.out.println("------------------------------------");
            return user;
        } else {
            User user = userDao.findUserById(id);
            System.out.println("查询数据库获得数据："+user.getUserName());
            System.out.println("------------------------------------");
 
            // 写入缓存
            operations.set(key, user, 5, TimeUnit.HOURS);
            return user;
        }
    }

  /**
     * 更新用户策略：先更新数据表，成功之后，删除原来的缓存，再更新缓存
     */
    public int updateUser(User user) {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        int result = userDao.updateUser(user);
        if (result != 0) {
            String key = "user_" + user.getUid();
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("删除缓存中的key-----------> " + key);
            }
            // 再将更新后的数据加入缓存
            User userNew = userDao.findUserById(user.getUid());
            if (userNew != null) {
                operations.set(key, userNew, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }
  /**
     * 删除用户策略：删除数据表中数据，然后删除缓存
     */
    public int deleteUserById(int id) {
        int result = userDao.deleteUserById(id);
        String key = "user_" + id;
        if (result != 0) {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("删除了缓存中的key:" + key);
            }
        }
        return result;
    }
 
}　


```





# Redis 是什么？两句话做一下概括。

是一个完全开源免费的 key-value 内存数据库 2. 通常被认为是一个数据结构服务器，主要是因为其有着丰富的数据结构 strings、map、 list、sets、 sorted sets。

Redis 使用最佳方式是全部数据 in-memory。
Redis 更多场景是作为 Memcached 的替代者来使用。
当需要除 key/value 之外的更多数据类型支持时，使用 Redis 更合适。
当存储的数据不能被剔除时，使用 Redis 更合适。

# 谈下你对 Redis 的了解？

Redis（全称：Remote Dictionary Server 远程字典服务）是一个开源的使用 ANSI C 语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value 数据库，并提供多种语言的 API。

# Redis 实现原理或机制。

Redis 是一个 key-value 存储系统。和 Memcached 类似，但是解决了断电后数据完全丢失的情况，而且她支持更多无化的 value 类型，除了和 string 外，还支持 lists（链表）、sets（集合）和 zsets（有序集合）、Hash几种数据类型。这些数据类型都支持 push/pop、add/remove 及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。
Redis 是一种基于客户端 - 服务端模型以及请求 / 响应协议的 TCP 服务。这意味着通常情况下一个请求会遵循以下步骤：

客户端向服务端发送一个查询请求，并监听 Socket 返回，通常是以阻塞模式，等待服务端响应。服务端处理命令，并将结果返回给客户端。
在服务端未响应时，客户端可以继续向服务端发送请求，并最终一次性读取所有服务端的响应。
Redis 管道技术最显著的优势是提高了 Redis 服务的性能。
分区是分割数据到多个 Redis 实例的处理过程，因此每个实例只保存 key 的一个子集。
通过利用多台计算机内存的和值，允许我们构造更大的数据库。
通过多核和多台计算机，允许我们扩展计算能力；通过多台计算机和网络适配器，允许我们扩展网络带宽。





# redis集群方案

Redis支持三种集群方案

- 主从复制模式
- Sentinel（哨兵）模式
- Cluster模式

 Cluster 集群原理：

Redis Cluster 将所有数据划分为 16384 个 slots(槽位)，每个节点负责其中一部分槽位。槽位的信息存储于每个节点中。

当 Redis Cluster 的客户端来连接集群时，它也会得到一份集群的槽位配置信息并将其缓存在客户端本地。这样当客户端要查找某个 key 时，可以直接定位到目标节点。同时因为槽位的信息可能会存在客户端与服务器不一致的情况，还需要纠正机制来实现槽位信息的校验调整。

**槽位定位算法**

Cluster 默认会对 key 值使用 crc16 算法进行 hash 得到一个整数值，然后用这个整数值对 16384 进行取模来得到具体槽位。

HASH_SLOT = CRC16(key) mod 16384

**跳转重定位**

当客户端向一个错误的节点发出了指令，该节点会发现指令的 key 所在的槽位并不归自己管理，这时**它会向客户端发送一个特殊的跳转指令携带目标操作的节点地址**，告诉客户端去连这个节点去获取数据。客户端收到指令后除了跳转到正确的节点上去操作，还会同步更新纠正本地的槽位映射表缓存，后续所有 key 将使用新的槽位映射表。

![image-20220204204120029](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220204204120029.png)

**Redis集群选举原理分析**

当slave发现自己的master变为FAIL状态时，便尝试进行Failover，以期成为新的master。由于挂掉的master可能会有多个slave，从而存在多个slave竞争成为master节点的过程， 其过程如下：

1.slave发现自己的master变为FAIL

2.将自己记录的集群currentEpoch加1，并广播FAILOVER_AUTH_REQUEST 信息

3.其他节点收到该信息，只有master响应，判断请求者的合法性，并发送FAILOVER_AUTH_ACK，对每一个epoch只发送一次ack

4.尝试failover的slave收集master返回的FAILOVER_AUTH_ACK

5.slave收到超过半数master的ack后变成新Master(这里解释了集群为什么至少需要三个主节点，如果只有两个，当其中一个挂了，只剩一个主节点是不能选举成功的)

6.slave广播Pong消息通知其他集群节点。

从节点并不是在主节点一进入 FAIL 状态就马上尝试发起选举，而是有一定延迟，一定的延迟确保我们等待FAIL状态在集群中传播，slave如果立即尝试选举，其它masters或许尚未意识到FAIL状态，可能会拒绝投票

•延迟计算公式：

 DELAY = 500ms + random(0 ~ 500ms) + SLAVE_RANK * 1000ms

•SLAVE_RANK表示此slave已经从master复制数据的总量的rank。Rank越小代表已复制的数据越新。这种方式下，**持有最新数据的slave将会首先发起选举（理论上**

**Redis集群为什么至少需要三个master节点，并且推荐节点数为奇数？**

因为新master的选举需要大于半数的集群master节点同意才能选举成功，如果只有两个master节点，当其中一个挂了，是达不到选举新master的条件的。

 奇数个master节点可以在满足选举该条件的基础上节省一个节点，比如三个master节点和四个master节点的集群相比，大家如果都挂了一个master节点都能选举新master节点，如果都挂了两个master节点都没法选举新master节点了，所以奇数的master节点更多的是**从节省机器资源角度出发**说的。



## 主从复制模式

什么是主从复制？

Redis的持久化保证了即使Redis服务重启也不会丢失数据，因为Redis服务重启后会将硬盘上持久化的数据恢复到内存中，但是当Redis服务器的硬盘损坏了可能会导致数据丢失，不过通过Redis的主从复制机制就可以避免这种单点故障





**1. 基本原理**

主从复制模式中包含一个主数据库实例（master）与一个或多个从数据库实例（slave），如下图



![img](https://pic4.zhimg.com/80/v2-8c5ff51b6aa9f933231e4c3f50cfdd6f_1440w.jpg)



客户端可对主数据库进行读写操作，对从数据库进行读操作，主数据库写入的数据会实时自动同步给从数据库。

主redis中的数据有两个副本，即从redis1和从redis2，即使一台Redis服务器宕机其他两台Redis服务也可以继续提供服务
主redis中的数据和从redis中的数据保持实时同步，当主redis写入数据时通过主从复制机制会复制到两个从redis服务上
只有一个主redis，可以有多个从redis
master和slave都是一个redis实例（redis服务）
通过主从配置可以实现读写分离
主从复制不会阻塞master，在同步数据时，master可以继续处理客户端请求
一个Redis可以既是主又是从，即redis1和redis2下面又可以有多个从redis服务器，而它们又是redis的从服务器，如下：

![img](https://img-blog.csdnimg.cn/202002161743296.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zOTc2NDA1Ng==,size_16,color_FFFFFF,t_70)

对主从模式必须的理解（结论已经验证过，可以自行验证）：

1. 一个Master可以有多个Slaves
2. 默认配置下，master节点可以进行读和写，slave节点只能进行读操作，写操作被禁止
3. 不要修改配置让slave节点支持写操作，没有意义，原因一，写入的数据不会被同步到其他节点；原因二，当master节点修改同一条数据后，slave节点的数据会被覆盖掉
4. slave节点挂了不影响其他slave节点的读和master节点的读和写，重新启动后会将数据从master节点同步过来
5. master节点挂了以后，不影响slave节点的读，Redis将不再提供写服务，master节点启动后Redis将重新对外提供写服务。
6. master节点挂了以后，不会slave节点重新选一个master

对有密码的情况说明一下，当master节点设置密码时：

- 客户端访问master需要密码
- 启动slave需要密码，在配置中进行配置即可
- 客户端访问slave不需要密码

　主从模式的缺点其实从上面的描述中可以得出：

- master节点挂了以后，redis就不能对外提供写服务了，因为剩下的slave不能成为master

　　这个缺点影响是很大的，尤其是对生产环境来说，是一刻都不能停止服务的，所以一般的生产坏境是不会单单只有主从模式的。所以有了下面的sentinel模式。

具体工作机制为：

- slave启动后，向master发送SYNC命令，master接收到SYNC命令后通过bgsave保存快照（即上文所介绍的RDB持久化），并使用缓冲区记录保存快照这段时间内执行的写命令
- master将保存的快照文件发送给slave，并继续记录执行的写命令
- slave接收到快照文件后，加载快照文件，载入数据
- master快照发送完后开始向slave发送缓冲区的写命令，slave接收命令并执行，完成复制初始化
- 此后master每次执行一个写命令都会同步发送给slave，保持master与slave之间数据的一致性
- 部署主从示例：

本示例基于Redis 5.0.3版。

redis.conf的主要配置

```text
###网络相关###
# bind 127.0.0.1 # 绑定监听的网卡IP，注释掉或配置成0.0.0.0可使任意IP均可访问
protected-mode no # 关闭保护模式，使用密码访问
port 6379  # 设置监听端口，建议生产环境均使用自定义端口
timeout 30 # 客户端连接空闲多久后断开连接，单位秒，0表示禁用

###通用配置###
daemonize yes # 在后台运行
pidfile /var/run/redis_6379.pid  # pid进程文件名
logfile /usr/local/redis/logs/redis.log # 日志文件的位置

###RDB持久化配置###
save 900 1 # 900s内至少一次写操作则执行bgsave进行RDB持久化
save 300 10
save 60 10000 
# 如果禁用RDB持久化，可在这里添加 save ""
rdbcompression yes #是否对RDB文件进行压缩，建议设置为no，以（磁盘）空间换（CPU）时间
dbfilename dump.rdb # RDB文件名称
dir /usr/local/redis/datas # RDB文件保存路径，AOF文件也保存在这里

###AOF配置###
appendonly yes # 默认值是no，表示不使用AOF增量持久化的方式，使用RDB全量持久化的方式
appendfsync everysec # 可选值 always， everysec，no，建议设置为everysec

###设置密码###
requirepass 123456 # 设置复杂一点的密码
```

部署主从复制模式只需稍微调整slave的配置，在redis.conf中添加

```text
replicaof 127.0.0.1 6379 # master的ip，port
masterauth 123456 # master的密码
replica-serve-stale-data no # 如果slave无法与master同步，设置成slave不可读，方便监控脚本发现问题
```

本示例在单台服务器上配置master端口6379，两个slave端口分别为7001,7002，启动master，再启动两个slave

```text
[root@dev-server-1 master-slave]# redis-server master.conf
[root@dev-server-1 master-slave]# redis-server slave1.conf
[root@dev-server-1 master-slave]# redis-server slave2.conf
```

进入master数据库，写入一个数据，再进入一个slave数据库，立即便可访问刚才写入master数据库的数据。如下所示

```text
[root@dev-server-1 master-slave]# redis-cli 
127.0.0.1:6379> auth 123456
OK
127.0.0.1:6379> set site blog.jboost.cn
OK
127.0.0.1:6379> get site
"blog.jboost.cn"
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=7001,state=online,offset=13364738,lag=1
slave1:ip=127.0.0.1,port=7002,state=online,offset=13364738,lag=0
...
127.0.0.1:6379> exit

[root@dev-server-1 master-slave]# redis-cli -p 7001
127.0.0.1:7001> auth 123456
OK
127.0.0.1:7001> get site
"blog.jboost.cn"
```

执行info replication命令可以查看连接该数据库的其它库的信息，如上可看到有两个slave连接到master

**主从复制的优缺点**

优点：

- master能自动将数据同步到slave，可以进行读写分离，分担master的读压力
- master、slave之间的同步是以非阻塞的方式进行的，同步期间，客户端仍然可以提交查询或更新请求

缺点：

- 不具备自动容错与恢复功能，master或slave的宕机都可能导致客户端请求失败，需要等待机器重启或手动切换客户端IP才能恢复
- master宕机，如果宕机前数据没有同步完，则切换IP后会存在数据不一致的问题
- 难以支持在线扩容，Redis的容量受限于单机配置

个人使用例子：

输入src/redis-server redis6381.conf

![image-20220124161526504](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220124161526504.png)

redis-cli用于连接客户端

输入info用于读取服务器信息

关闭命令：

root@iZuf64cvuzy0uruzobicmgZ:/www/server/redis# redis-cli -p 6381
127.0.0.1:6381> shutdown
(error) NOAUTH Authentication required.
127.0.0.1:6381> auth redisHH3399
OK
127.0.0.1:6381> shutdown





## **Redis主从工作原理**

文档：02‐VIP‐Redis持久化、主从与哨兵架构详解
2 链接：http://note.youdao.com/noteshare?id=893c138fa39925f86b374fd46db322b4&sub=1973D9FDBADF4E1FB8E7D2BC97783593

如果你为master配置了一个slave，不管这个slave是否是第一次连接上Master，它都会发送一个**PSYNC**命令给master请求复制数据。

master收到PSYNC命令后，会在后台进行数据持久化通过bgsave生成最新的rdb快照文件，持久化期间，master会继续接收客户端的请求，它会把这些可能修改数据集的请求缓存在内存中。当持久化进行完毕以后，master会把这份rdb文件数据集发送给slave，slave会把接收到的数据进行持久化生成rdb，然后再加载到内存中。然后，master再将之前缓存在内存中的命令发送给slave。

当master与slave之间的连接由于某些原因而断开时，slave能够自动重连Master，如果master收到了多个slave并发连接请求，它只会进行一次持久化，而不是一个连接一次，然后再把这一份持久化的数据发送给多个并发连接的slave。

**主从复制(全量复制)流程图：**

![image-20220124173354806](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220124173354806.png)

**数据部分复制**

当master和slave断开重连后，一般都会对整份数据进行复制。但从redis2.8版本开始，redis改用可以支持部分数据复制的命令PSYNC去master同步数据，slave与master能够在网络连接断开重连后只进行部分数据复制(**断点续传**)。

master会在其内存中创建一个复制数据用的缓存队列，缓存最近一段时间的数据，master和它所有的slave都维护了复制的数据下标offset和master的进程id，因此，当网络连接断开后，slave会请求master继续进行未完成的复制，从所记录的数据下标开始。如果master进程id变化了，或者从节点数据下标offset太旧，已经不在master的缓存队列里了，那么将会进行一次全量数据的复制。

**主从复制(部分复制，断点续传)流程图：**

![image-20220124173447947](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220124173447947.png)

如果有很多从节点，为了缓解**主从复制风暴**(多个从节点同时复制主节点导致主节点压力过大)，可以做如下架构，让部分从节点与从节点(与主节点同步)同步数据

​    ![0](https://note.youdao.com/yws/public/resource/893c138fa39925f86b374fd46db322b4/xmlnote/2F8E815BDAE944A7ACBCD3B47F0C0D2B/102435)

**Jedis连接代码示例：**

1、引入相关依赖：

```xml
                <dependency>   
                    <groupId>redis.clients</groupId>    <artifactId>jedis</artifactId>   
                    <version>2.9.0</version> </dependency>         
```

​     

访问代码：

 ```java
 public class JedisSingleTest {
     public static void main(String[] args) throws IOException {
 
         JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
         jedisPoolConfig.setMaxTotal(20);
         jedisPoolConfig.setMaxIdle(10);
         jedisPoolConfig.setMinIdle(5);
 
         // timeout，这里既是连接超时又是读写超时，从Jedis 2.8开始有区分connectionTimeout和soTimeout的构造函数
         JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.0.60", 6379, 3000, null);
 
         Jedis jedis = null;
         try {
             //从redis连接池里拿出一个连接执行命令
             jedis = jedisPool.getResource();
 
             System.out.println(jedis.set("single", "zhuge"));
             System.out.println(jedis.get("single"));
 
             //管道示例
             //管道的命令执行方式：cat redis.txt | redis-cli -h 127.0.0.1 -a password - p 6379 --pipe
             /*Pipeline pl = jedis.pipelined();
             for (int i = 0; i < 10; i++) {
                 pl.incr("pipelineKey");
                 pl.set("zhuge" + i, "zhuge");
             }
             List<Object> results = pl.syncAndReturnAll();
             System.out.println(results);*/
 
             //lua脚本模拟一个商品减库存的原子操作
             //lua脚本命令执行方式：redis-cli --eval /tmp/test.lua , 10
             /*jedis.set("product_count_10016", "15");  //初始化商品10016的库存
             String script = " local count = redis.call('get', KEYS[1]) " +
                             " local a = tonumber(count) " +
                             " local b = tonumber(ARGV[1]) " +
                             " if a >= b then " +
                             "   redis.call('set', KEYS[1], a-b) " +
                             "   return 1 " +
                             " end " +
                             " return 0 ";
             Object obj = jedis.eval(script, Arrays.asList("product_count_10016"), Arrays.asList("10"));
             System.out.println(obj);*/
 
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
             if (jedis != null)
                 jedis.close();
         }
     }
 }
 ```



## Sentinel（哨兵）模式

**1. 基本原理**

哨兵模式基于主从复制模式，只是引入了哨兵来监控与自动处理故障。

**sentinel哨兵是特殊的redis服务，不提供读写服务，主要用来监控redis实例节点。**

![image-20220411174142382](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220411174142382.png)

哨兵架构下client端第一次从哨兵找出redis的主节点，后续就直接访问redis的主节点，不会每次都通过sentinel代理访问redis的主节点，当redis的主节点发生变化，哨兵会第一时间感知到，并且将新的redis主节点通知给client端(这里面redis的client端一般都实现了订阅功能，订阅sentinel发布的节点变动消息)

什么是哨兵

```
Redis-Sentinel是用于管理Redis集群,该系统执行以下三个任务:

1.监控(Monitoring):Sentinel会不断地检查你的主服务器和从服务器是否运作正常

2.提醒(Notification):当被监控的某个Redis服务器出现问题时,Sentinel可以通过API向管理员或者其他应用程序发送通知

3.自动故障迁移(Automatic failover):当一个主服务器不能正常工作时,Sentinel 会开始一次自动故障迁移操作,它会将失效主服务器的其中一个从服务器升级为新的主服务器,并让失效主服务器的其他从服务器改为复制新的主服务器;当客户端试图连接失效的主服务器时,集群也会向客户端返回新主服务器的地址,使得集群可以使用新主服务器代替失效服务器
```

 



如图



![img](https://pic4.zhimg.com/80/v2-719c8f444926fe3c58f76541d0d376df_1440w.jpg)



sentinel的中文含义是哨兵、守卫。也就是说既然主从模式中，当master节点挂了以后，slave节点不能主动选举一个master节点出来，那么我就安排一个或多个sentinel来做这件事，**当sentinel发现master节点挂了以后，sentinel就会从slave中重新选举一个master**。

哨兵顾名思义，就是来为Redis集群站哨的，一旦发现问题能做出相应的应对处理。其功能包括

- 监控master、slave是否正常运行
- 当master出现故障时，能自动将一个slave转换为master（大哥挂了，选一个小弟上位）
- 多个哨兵可以监控同一个Redis，哨兵之间也会自动监控
- **哨兵模式的具体工作机制：**

在配置文件中通过`sentinel monitor <master-name> <ip> <redis-port> <quorum>`来定位master的IP、端口，一个哨兵可以监控多个master数据库，只需要提供多个该配置项即可。哨兵启动后，会与要监控的master建立两条连接：

- 一条连接用来订阅master的*sentinel*:hello频道与获取其他监控该master的哨兵节点信息
- 另一条连接定期向master发送INFO等命令获取master本身的信息

与master建立连接后，哨兵会执行三个操作：

- 定期（一般10s一次，当master被标记为主观下线时，改为1s一次）向master和slave发送INFO命令
- 定期向master和slave的*sentinel*:hello频道发送自己的信息
- 定期（1s一次）向master、slave和其他哨兵发送PING命令

发送INFO命令可以获取当前数据库的相关信息从而实现新节点的自动发现。所以说哨兵只需要配置master数据库信息就可以自动发现其slave信息。获取到slave信息后，哨兵也会与slave建立两条连接执行监控。通过INFO命令，哨兵可以获取主从数据库的最新信息，并进行相应的操作，比如角色变更等。

接下来哨兵向主从数据库的*sentinel*:hello频道发送信息与同样监控这些数据库的哨兵共享自己的信息，发送内容为哨兵的ip端口、运行id、配置版本、master名字、master的ip端口还有master的配置版本。这些信息有以下用处：

- 其他哨兵可以通过该信息判断发送者是否是新发现的哨兵，如果是的话会创建一个到该哨兵的连接用于发送PING命令。
- 其他哨兵通过该信息可以判断master的版本，如果该版本高于直接记录的版本，将会更新
- 当实现了自动发现slave和其他哨兵节点后，哨兵就可以通过定期发送PING命令定时监控这些数据库和节点有没有停止服务。

如果被PING的数据库或者节点超时（通过 sentinel down-after-milliseconds master-name milliseconds 配置）未回复，哨兵认为其主观下线（sdown，s就是Subjectively —— 主观地）。

如果下线的是master，哨兵会向其它哨兵发送命令询问它们是否也认为该master主观下线，如果达到一定数目（即配置文件中的quorum）投票，哨兵会认为该master已经客观下线（odown，o就是Objectively —— 客观地），并选举领头的哨兵节点对主从系统发起故障恢复。

若没有足够的sentinel进程同意master下线，master的客观下线状态会被移除，若master重新向sentinel进程发送的PING命令返回有效回复，master的主观下线状态就会被移除。

**哨兵架构leader选举流程**

当一个master服务器被某sentinel视为下线状态后，该sentinel会与其他sentinel协商选出sentinel的leader进行故障转移工作。每个发现master服务器进入下线的sentinel都可以要求其他sentinel选自己为sentinel的leader，**选举是先到先得**。同时每个sentinel每次选举都会自增配置纪元(选举周期)，每个纪元中只会选择一个sentinel的leader。如果所有超过一半的sentinel选举某sentinel作为leader。之后该sentinel进行故障转移操作，从存活的slave中选举出新的master，这个选举过程跟集群的master选举很类似。

哨兵集群只有一个哨兵节点，redis的主从也能正常运行以及选举master，如果master挂了，那唯一的那个哨兵节点就是哨兵leader了，可以正常选举新master。

不过为了高可用一般都推荐至少部署三个哨兵节点。为什么推荐奇数个哨兵节点原理跟集群奇数个master节点类似。

哨兵认为master客观下线后，**故障恢复的操作需要由选举的领头哨兵来执行，选举采用Raft算法**：

- 发现master下线的哨兵节点（我们称他为A）向每个哨兵发送命令，要求对方选自己为领头哨兵
- 如果目标哨兵节点没有选过其他人，则会同意选举A为领头哨兵
- 如果有超过一半的哨兵同意选举A为领头，则A当选
- 如果有多个哨兵节点同时参选领头，此时有可能存在一轮投票无竞选者胜出，此时每个参选的节点等待一个随机时间后再次发起参选请求，进行下一轮投票竞选，直至选举出领头哨兵

选出领头哨兵后，领头者开始对系统进行故障恢复，从出现故障的master的从数据库中挑选一个来当选新的master,选择规则如下：

- 所有在线的slave中选择优先级最高的，优先级可以通过slave-priority配置
- 如果有多个最高优先级的slave，则选取复制偏移量最大（即复制越完整）的当选
- 如果以上条件都一样，选取id最小的slave

挑选出需要继任的slave后，领头哨兵向该数据库发送命令使其升格为master，然后再向其他slave发送命令接受新的master，最后更新数据。将已经停止的旧的master更新为新的master的从数据库，使其恢复服务后以slave的身份继续运行。

![image-20210910095539123](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910095539123.png)

![image-20210910095620566](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910095620566.png)

![image-20210910095644159](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910095644159.png)

![image-20210910095744948](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910095744948.png)



## **redis哨兵架构搭建步骤：**

```java
1、复制一份sentinel.conf文件
cp sentinel.conf sentinel-26379.conf

2、将相关配置修改为如下值：
port 26379
daemonize yes
pidfile "/var/run/redis-sentinel-26379.pid"
logfile "26379.log"
dir "/usr/local/redis-5.0.3/data"
# sentinel monitor <master-redis-name> <master-redis-ip> <master-redis-port> <quorum>
# quorum是一个数字，指明当有多少个sentinel认为一个master失效时(值一般为：sentinel总数/2 + 1)，master才算真正失效
sentinel monitor mymaster 192.168.0.60 6379 2   # mymaster这个名字随便取，客户端访问时会用到

3、启动sentinel哨兵实例
src/redis-sentinel sentinel-26379.conf

4、查看sentinel的info信息
src/redis-cli -p 26379
127.0.0.1:26379>info
可以看到Sentinel的info里已经识别出了redis的主从

5、可以自己再配置两个sentinel，端口26380和26381，注意上述配置文件里的对应数字都要修改
```

sentinel集群都启动完毕后，会将哨兵集群的元数据信息写入所有sentinel的配置文件里去(追加在文件的最下面)，我们查看下如下配置文件sentinel-26379.conf，如下所示：

sentinel known-replica mymaster 192.168.0.60 6380 #代表redis主节点的从节点信息 

entinel known-replica mymaster 192.168.0.60 6381 #代表redis主节点的从节点信息 

sentinel known-sentinel mymaster 192.168.0.60 26380 52d0a5d70c1f90475b4fc03b6ce7c3c56935760f  #代表感知到的其它哨兵节点 

sentinel known-sentinel mymaster 192.168.0.60 26381 e9f530d3882f8043f76ebb8e1686438ba8bd5ca6  #代表感知到的其它哨兵节点                            

哨兵的Jedis连接代码：

```java
public class JedisSentinelTest {
    public static void main(String[] args) throws IOException {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort("192.168.0.60",26379).toString());
        sentinels.add(new HostAndPort("192.168.0.60",26380).toString());
        sentinels.add(new HostAndPort("192.168.0.60",26381).toString());
        //JedisSentinelPool其实本质跟JedisPool类似，都是与redis主节点建立的连接池
        //JedisSentinelPool并不是说与sentinel建立的连接池，而是通过sentinel发现redis主节点并与其建立连接
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, config, 3000, null);
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            System.out.println(jedis.set("sentinel", "zhuge"));
            System.out.println(jedis.get("sentinel"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
            if (jedis != null)
                jedis.close();
        }
    }
}
```



## Cluster模式

**1. 基本原理**

哨兵模式解决了主从复制不能自动故障转移，达不到高可用的问题，但还是存在难以在线扩容，Redis容量受限于单机配置的问题。Cluster模式实现了Redis的分布式存储，即每台节点存储不同的内容，来解决在线扩容的问题。如图



![img](https://pic3.zhimg.com/80/v2-0a5c207d9800ccc516f89650f7f957a6_1440w.jpg)



Cluster采用无中心结构,它的特点如下：

- 所有的redis节点彼此互联(PING-PONG机制),内部使用二进制协议优化传输速度和带宽
- 节点的fail是通过集群中超过半数的节点检测失效时才生效
- 客户端与redis节点直连,不需要中间代理层.客户端不需要连接集群所有节点,连接集群中任何一个可用节点即可

Cluster模式的具体工作机制：

- 在Redis的每个节点上，都有一个插槽（slot），取值范围为0-16383
- 当我们存取key的时候，Redis会根据CRC16的算法得出一个结果，然后把结果对16384求余数，这样每个key都会对应一个编号在0-16383之间的哈希槽，通过这个值，去找到对应的插槽所对应的节点，然后直接自动跳转到这个对应的节点上进行存取操作
- 为了保证高可用，Cluster模式也引入主从复制模式，一个主节点对应一个或者多个从节点，当主节点宕机的时候，就会启用从节点
- 当其它主节点ping一个主节点A时，如果半数以上的主节点与A通信超时，那么认为主节点A宕机了。如果主节点A和它的从节点都宕机了，那么该集群就无法再提供服务了

Cluster模式集群节点最小配置6个节点(3主3从，因为需要半数以上)，其中主节点提供读写操作，从节点作为备用节点，不提供请求，只作为故障转移使用。

**2. 部署演示**

本示例基于Redis 5.0.3版。

Cluster模式的部署比较简单，首先在redis.conf中

```text
port 7100 # 本示例6个节点端口分别为7100,7200,7300,7400,7500,7600 
daemonize yes # r后台运行 
pidfile /var/run/redis_7100.pid # pidfile文件对应7100,7200,7300,7400,7500,7600 
cluster-enabled yes # 开启集群模式 
masterauth passw0rd # 如果设置了密码，需要指定master密码
cluster-config-file nodes_7100.conf # 集群的配置文件，同样对应7100,7200等六个节点
cluster-node-timeout 15000 # 请求超时 默认15秒，可自行设置 
```

分别以端口7100,7200,7300,7400,7500,7600 启动六个实例(如果是每个服务器一个实例则配置可一样)

```text
[root@dev-server-1 cluster]# redis-server redis_7100.conf
[root@dev-server-1 cluster]# redis-server redis_7200.conf
...
```

然后通过命令将这个6个实例组成一个3主节点3从节点的集群，

```text
redis-cli --cluster create --cluster-replicas 1 127.0.0.1:7100 127.0.0.1:7200 127.0.0.1:7300 127.0.0.1:7400 127.0.0.1:7500 127.0.0.1:7600 -a passw0rd
```

执行结果如图



![img](https://pic2.zhimg.com/80/v2-476d02f1a80d15e89ab6fd5387c4e071_1440w.jpg)



可以看到 7100， 7200， 7300 作为3个主节点，分配的slot分别为 0-5460， 5461-10922， 10923-16383， 7600作为7100的slave， 7500作为7300的slave，7400作为7200的slave。

我们连接7100设置一个值

```text
[root@dev-server-1 cluster]# redis-cli -p 7100 -c -a passw0rd
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
127.0.0.1:7100> set site blog.jboost.cn
-> Redirected to slot [9421] located at 127.0.0.1:7200
OK
127.0.0.1:7200> get site
"blog.jboost.cn"
127.0.0.1:7200>
```

注意添加 -c 参数表示以集群模式，否则报 (error) MOVED 9421 127.0.0.1:7200 错误， 以 -a 参数指定密码，否则报(error) NOAUTH Authentication required错误。

从上面命令看到key为site算出的slot为9421，落在7200节点上，所以有Redirected to slot [9421] located at 127.0.0.1:7200，集群会自动进行跳转。因此客户端可以连接任何一个节点来进行数据的存取。

通过cluster nodes可查看集群的节点信息

```text
127.0.0.1:7200> cluster nodes
```

**3. Cluster模式的优缺点**

优点：

- 无中心架构，数据按照slot分布在多个节点。
- 集群中的每个节点都是平等的关系，每个节点都保存各自的数据和整个集群的状态。每个节点都和其他所有节点连接，而且这些连接保持活跃，这样就保证了我们只需要连接集群中的任意一个节点，就可以获取到其他节点的数据。
- 可线性扩展到1000多个节点，节点可动态添加或删除
- 能够实现自动故障转移，节点之间通过gossip协议交换状态信息，用投票机制完成slave到master的角色转换

缺点：

- 客户端实现复杂，驱动要求实现Smart Client，缓存slots mapping信息并及时更新，提高了开发难度。目前仅JedisCluster相对成熟，异常处理还不完善，比如常见的“max redirect exception”
- 节点会因为某些原因发生阻塞（阻塞时间大于 cluster-node-timeout）被判断下线，这种failover是没有必要的
- 数据通过异步复制，不保证数据的强一致性
- slave充当“冷备”，不能缓解读压力
- 批量操作限制，目前只支持具有相同slot值的key执行批量操作，对mset、mget、sunion等操作支持不友好
- key事务操作支持有线，只支持多key在同一节点的事务操作，多key分布不同节点时无法使用事务功能

不支持多数据库空间，单机redis可以支持16个db，集群模式下只能使用一个，即db 0 Redis Cluster模式不建议使用pipeline和multi-keys操作，减少max redirect产生的场景。

<font color="red">redis cluster节点间采取gossip协议进行通信</font>

　　跟集中式不同，不是将集群元数据（节点信息，故障，等等）集中存储在某个节点上，而是互相之间不断通信，保持整个集群所有节点的数据是完整的

　　集中式：好处在于，元数据的更新和读取，时效性非常好，一旦元数据出现了变更，立即就更新到集中式的存储中，其他节点读取的时候立即就可以感知到; 不好在于，所有的元数据的跟新压力全部集中在一个地方，可能会导致元数据的存储有压力

　　gossip：好处在于，元数据的更新比较分散，不是集中在一个地方，更新请求会陆陆续续，打到所有节点上去更新，有一定的延时，降低了压力; 缺点，元数据更新有延时，可能导致集群的一些操作会有一些滞后

redis cluster的高可用的原理，几乎跟哨兵是类似的

**1、判断节点宕机**

　　如果一个节点认为另外一个节点宕机，那么就是pfail，主观宕机

　　如果多个节点都认为另外一个节点宕机了，那么就是fail，客观宕机，跟哨兵的原理几乎一样，sdown，odown

　　在cluster-node-timeout内，某个节点一直没有返回pong，那么就被认为pfail

　　如果一个节点认为某个节点pfail了，那么会在gossip ping消息中，ping给其他节点，如果超过半数的节点都认为pfail了，那么就会变成fail

 

**2、从节点过滤**

　　对宕机的master node，从其所有的slave node中，选择一个切换成master node

　　检查每个slave node与master node断开连接的时间，如果超过了cluster-node-timeout * cluster-slave-validity-factor，那么就没有资格切换成master

　　这个也是跟哨兵是一样的，从节点超时过滤的步骤

 

**3、从节点选举**

　　哨兵：对所有从节点进行排序，slave priority，offset，run id

　　每个从节点，都根据自己对master复制数据的offset，来设置一个选举时间，offset越大（复制数据越多）的从节点，选举时间越靠前，优先进行选举

　　所有的master node开始slave选举投票，给要进行选举的slave进行投票，如果大部分master node（N/2 + 1）都投票给了某个从节点，那么选举通过，那个从节点可以切换成master

　　从节点执行主备切换，从节点切换为主节点

 

**4、与哨兵比较**

　　整个流程跟哨兵相比，非常类似，所以说，redis cluster功能强大，直接集成了replication和sentinal的功能

## gossip协议

**先简单介绍一下 Gossip 协议的执行过程：**

Gossip 过程是由种子节点发起，当一个种子节点有状态需要更新到网络中的其他节点时，它会**随机**的选择周围几个节点散播消息，收到消息的节点也会重复该过程，直至最终网络中所有的节点都收到了消息。**这个过程可能需要一定的时间，由于不能保证某个时刻所有节点都收到消息，但是理论上最终所有节点都会收到消息，因此它是一个最终一致性协议。**

**Gossip 的特点（优势）**

**1）扩展性**

网络可以允许节点的任意增加和减少，新增加的节点的状态最终会与其他节点一致。

**2）容错**

网络中任何节点的宕机和重启都不会影响 Gossip 消息的传播，Gossip 协议具有天然的分布式系统容错特性。

**3）去中心化**

Gossip 协议不要求任何中心节点，所有节点都可以是对等的，任何一个节点无需知道整个网络状况，只要网络是连通的，任意一个节点就可以把消息散播到全网。

**4）一致性收敛**

Gossip 协议中的消息会以一传十、十传百一样的指数级速度在网络中快速传播，因此系统状态的不一致可以在很快的时间内收敛到一致。消息传播速度达到了 logN。

**5）简单**

Gossip 协议的过程极其简单，实现起来几乎没有太多复杂性。

Gossip 协议跟其他协议一样，也有一些不可避免的缺陷，主要是两个：

**1）消息的延迟**

由于 Gossip 协议中，节点只会随机向少数几个节点发送消息，消息最终是通过多个轮次的散播而到达全网的，因此使用 Gossip 协议会造成不可避免的消息延迟。不适合用在对实时性要求较高的场景下。

**2）消息冗余**

Gossip 协议规定，节点会定期随机选择周围节点发送消息，而收到消息的节点也会重复该步骤，因此就不可避免的存在消息重复发送给同一节点的情况，造成了消息的冗余，同时也增加了收到消息的节点的处理压力。而且，由于是定期发送，因此，即使收到了消息的节点还会反复收到重复消息，加重了消息的冗余。

## Redis Sharding集群（客户端实现数据分片）

即客户端自己计算数据的key应该在哪个机器上存储和查找，此方法的好处是降低了服务器集群的复杂度，客户端实现数据分片时，服务器是独立的，服务器之前没有任何关联。多数redis客户端库实现了此功能，也叫sharding,这种方式的缺点是客户端需要实时知道当前集群节点的联系信息，同时，当添加一个新的节点时，客户端要支持动态sharding.，多数客户端实现不支持此功能，需要重启redis。另一个弊端是redis的HA需要额外考虑。

 

多Redis实例服务，比单Redis实例要复杂的多，这涉及到定位、协同、容错、扩容等技术难题。这里，我们介绍一种轻量级的客户端Redis Sharding技术。

Redis Sharding可以说是Redis Cluster出来之前，业界普遍使用的多Redis实例集群方法。其主要思想是采用哈希算法将数据的key进行散列，然后特定的key会映射到特定的Redis节点上）。这样，客户端就知道该向哪个Redis节点操作数据。


# Redis的字符串是怎么实现的



Redis虽然是用C语言写的，但却没有直接用C语言的字符串，而是自己实现了一套字符串。目的就是为了提升速度，提升性能，可以看出Redis为了高性能也是煞费苦心。

Redis构建了一个叫做简单动态字符串（Simple Dynamic String），简称SDS

### 1.SDS 代码结构

```java
struct sdshdr{
    //  记录已使用长度
    int len;
    // 记录空闲未使用的长度
    int free;
    // 字符数组
    char[] buf;
};
```

SDS ？什么鬼？可能对此陌生的朋友对这个名称有疑惑。只是个名词而已不必在意，我们要重点欣赏借鉴Redis的设计思路。下面画个图来说明，一目了然。

![图片](https://mmbiz.qpic.cn/mmbiz/8KKrHK5ic6XCsqyWO8pRHIiaObEX7mA2RYXae1TicrODmoI20icdJVp447NQ7j05pbVlEmWDWCd2yOMjxWmczTAnmQ/640?wx_fmt=other&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

Redis的字符串也会遵守C语言的字符串的实现规则，即最后一个字符为空字符。然而这个空字符不会被计算在len里头。

### 2.SDS 动态扩展特点

SDS的最厉害最奇妙之处在于它的Dynamic。动态变化长度。举个例子

![图片](https://mmbiz.qpic.cn/mmbiz/8KKrHK5ic6XCsqyWO8pRHIiaObEX7mA2RYF3I2tWEPQc7Nn1OGExkbERltTH7qr8Vic3RLygSHibkepvUlQ4pzQaibQ/640?wx_fmt=other&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如上图所示刚开始s1 只有5个空闲位子，后面需要追加' world' 6个字符，很明显是不够的。那咋办？Redis会做以下三个操作：

1. 计算出大小是否足够
2. 开辟空间至满足所需大小
3. 开辟与已使用大小len相同长度的空闲free空间（如果len < 1M）开辟1M长度的空闲free空间（如果len >= 1M）

看到这儿为止有没有朋友觉得这个实现跟Java的列表List实现有点类似呢？看完后面的会觉得更像了。

Redis字符串的性能优势

- 快速获取字符串长度
- 避免缓冲区溢出
- 降低空间分配次数提升内存使用效率

1.快速获取字符串长度

再看下上面的SDS结构体：

```
struct sdshdr{
    //  记录已使用长度
    int len;
    // 记录空闲未使用的长度
    int free;
    // 字符数组
    char[] buf;
};
```

由于在SDS里存了已使用字符长度len，所以当想获取字符串长度时直接返回len即可，时间复杂度为O(1)。如果使用C语言的字符串的话它的字符串长度获取函数时间复杂度为O(n),n为字符个数，因为他是从头到尾（到空字符'\0'）遍历相加。

2.避免缓冲区溢出

对一个C语言字符串进行strcat追加字符串的时候需要提前开辟需要的空间，如果不开辟空间的话可能会造成缓冲区溢出，而影响程序其他代码。如下图，有一个字符串s1="hello" 和 字符串s2="baby",现在要执行strcat(s1,"world"),并且执行前未给s1开辟空间，所以造成了缓冲区溢出。

![图片](https://mmbiz.qpic.cn/mmbiz/8KKrHK5ic6XCsqyWO8pRHIiaObEX7mA2RYWYdrgTj2vFkJod1xCRAkYlNjs7qQgwMa1TMDjlTPtDemC1cichEsTibw/640?wx_fmt=other&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

而对于Redis而言由于每次追加字符串时都会检查空间是否够用，所以不会存在缓冲区溢出问题。每次追加操作前都会做如下操作：

1. 计算出大小是否足够
2. 开辟空间至满足所需大小

   3.降低空间分配次数提升内存使用效率

字符串的追加操作会涉及到内存分配问题，然而内存分配问题会牵扯内存划分算法以及系统调用所以如果频繁发生的话影响性能，所以对于性能至上的Redis来说这是万万不能忍受的。所以采取了以下两种优化措施

- 空间与分配
- 惰性空间回收

**1. 空间预分配**

对于追加操作来说，Redis不仅会开辟空间至够用而且还会预分配未使用的空间(free)来用于下一次操作。至于未使用的空间(free)的大小则由修改后的字符串长度决定。

当修改后的字符串长度len < 1M,则会分配与len相同长度的未使用的空间(free)

当修改后的字符串长度len >= 1M,则会分配1M长度的未使用的空间(free)

有了这个预分配策略之后会减少内存分配次数，因为分配之前会检查已有的free空间是否够，如果够则不开辟了～

**2. 惰性空间回收**

与上面情况相反，惰性空间回收适用于字符串缩减操作。比如有个字符串s1="hello world"，对s1进行sdstrim(s1," world")操作，执行完该操作之后Redis不会立即回收减少的部分，而是会分配给下一个需要内存的程序。当然，Redis也提供了回收内存的api,可以自己手动调用来回收缩减部分的内存。

-------------------------



# 什么是 redis 的雪崩、穿透和击穿？

其实这是问到缓存必问的，因为缓存雪崩和穿透，是缓存最大的两个问题，要么不出现，一旦出现就是致命性的问题，所以面试官一定会问你。

![image-20210906203443296](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210906203443296.png)







### 缓存雪崩

对于系统 A，假设每天高峰期每秒 5000 个请求，本来缓存在高峰期可以扛住每秒 4000 个请求，但是缓存机器意外发生了全盘宕机（即 redis挂了）。缓存挂了，此时 1 秒 5000 个请求全部落数据库，数据库必然扛不住，它会报一下警，然后就挂了。此时，如果没有采用什么特别的方案来处理这个故障，DBA 很着急，重启数据库，但是数据库立马又被新的流量给打死了。

**存集中在一段时间内失效**，发生大量的缓存穿透，所有的查询都落在数据库上，造成了缓存雪崩。







![图片](https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XDgEqLXadxb8EXsfM7sDdiasaicdbYwcflkG63lVmQ17JOAh5c2Ibib3xTvkITbanwsBDggib3Uf3RD5Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



redis-caching-avalanche



大约在 3 年前，国内比较知名的一个互联网公司，曾因为缓存事故，导致雪崩，后台系统全部崩溃，事故从当天下午持续到晚上凌晨 3~4 点，公司损失了几千万。

缓存雪崩的事前事中事后的解决方案如下：

- 事前：redis 高可用，主从+哨兵，redis cluster，避免全盘崩溃。
- 事中：本地 ehcache 缓存 + hystrix 限流&降级，避免 MySQL 被打死。
- 事后：redis 持久化，一旦重启，自动从磁盘上加载数据，快速恢复缓存数据。

**和缓存击穿不同的是，    缓存击穿指并发查同一条数据，缓存雪崩是不同数据都过期了，很多数据都查不到从而查数据库。**

预防和解决缓存雪崩问题， 可以从以下三个方面进行着手。

1） 保证缓存层服务高可用性，比如使用Redis Sentinel或Redis Cluster。

2） 依赖隔离组件为后端限流熔断并降级。比如使用Sentinel或Hystrix限流降级组件。

比如服务降级，我们可以针对不同的数据采取不同的处理方式。当业务应用访问的是非核心数据（例如电商商品属性，用户信息等）时，暂时停止从缓存中查询这些数据，而是直接返回预定义的默认降级信息、空值或是错误提示信息；当业务应用访问的是核心数据（例如电商商品库存）时，仍然允许查询缓存，如果缓存缺失，也可以继续通过数据库读取。

3） 提前演练。 在项目上线前， 演练缓存层宕掉后， 应用以及后端的负载情况以及可能出现的问题， 在此基础上做一些预案设定。



![图片](https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XDgEqLXadxb8EXsfM7sDdias1XyMPK5yicCMGvD3HKUcx8eziabN6AxbccO3IMP18aFX7trQqLc3dadQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



redis-caching-avalanche-solution



用户发送一个请求，系统 A 收到请求后，先查本地 ehcache 缓存，如果没查到再查 redis。如果 ehcache 和 redis 都没有，再查数据库，将数据库中的结果，写入 ehcache 和 redis 中。

限流组件，可以设置每秒的请求，有多少能通过组件，剩余的未通过的请求，怎么办？**走降级**！可以返回一些默认的值，或者友情提示，或者空白的值。

好处：

- 数据库绝对不会死，限流组件确保了每秒只有多少个请求能通过。
- 只要数据库不死，就是说，对用户来说，2/5 的请求都是可以被处理的。
- 只要有 2/5 的请求可以被处理，就意味着你的系统没死，对用户来说，可能就是点击几次刷不出来页面，但是多点几次，就可以刷出来一次。

-----------------

缓存雪崩解决问题的另一种回答：

1. 加锁排队：在缓存失效后，通过加锁或者队列来控制读数据库写缓存的线程数量。比如对某个 key 只允许一个线程查询数据和写缓存，其他线程等待；
2. 数据预热：可以通过缓存 reload 机制，预先去更新缓存，再即将发生大并发访问前手动触发加载缓存不同的 key，设置不同的过期时间，让缓存失效的时间点尽量均匀；
3. 做二级缓存，或者双缓存策略：Cache1 为原始缓存，Cache2 为拷贝缓存，Cache1 失效时，可以访问 Cache2，Cache1 缓存失效时间设置为短期，Cache2 设置为长期。
4. 在缓存的时候给过期时间加上一个随机值，把缓存失效的时间分散开，这样就会大幅度的减少缓存在同一时间过期。

对缓存设置不同过期时间：

```java
String get(String key) {
    // 从缓存中获取数据
    String cacheValue = cache.get(key);
    // 缓存为空
    if (StringUtils.isBlank(cacheValue)) {
        // 从存储中获取
        String storageValue = storage.get(key);
        cache.set(key, storageValue);
        //设置一个过期时间(300到600之间的一个随机数)
        int expireTime = new Random().nextInt(300)  + 300;
        if (storageValue == null) {
            cache.expire(key, expireTime);
        }
        return storageValue;
    } else {
        // 缓存非空
        return cacheValue;
    }
}
```



### 缓存穿透

对于系统A，假设一秒 5000 个请求，结果其中 4000 个请求是黑客发出的恶意攻击。

黑客发出的那 4000 个攻击，缓存中查不到，每次你去数据库里查，也查不到。

举个栗子。数据库 id 是从 1 开始的，结果黑客发过来的请求 id 全部都是负数。这样的话，缓存中不会有，请求每次都“**视缓存于无物**”，直接查询数据库。这种恶意攻击场景的缓存穿透就会直接把数据库给打死。

![图片](https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XDgEqLXadxb8EXsfM7sDdiasDZqhtSQVa0Gv8cyicaDqONYXXCBXviafhBj0ASIL5eNWsSyeXAViaHZ6g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



redis-caching-penetration



解决方式很简单，每次系统 A 从数据库中只要没查到，就写一个空值到缓存里去，比如 `set -999 UNKNOWN`。然后设置一个过期时间，这样的话，下次有相同的 key 来访问的时候，在缓存失效之前，都可以直接从缓存中取数据。

1.接口层增加校验，如用户鉴权校验，id做基础校验，id<=0的直接拦截；
2.从缓存取不到的数据，在数据库中也没有取到，这时也可以将key-value对写为key-null，缓存有效时间可以设置短点，如30秒（设置太长会导致正常情况也没法使用）。这样可以防止攻击用户反复用同一个id暴力攻击
————————————————

原文链接：https://blog.csdn.net/kongtiao5/article/details/82771694

### 缓存击穿

缓存击穿，就是说某个 key 非常热点，访问非常频繁，处于集中式高并发访问的情况，当这个 key 在失效的瞬间，大量的请求就击穿了缓存，直接请求数据库，就像是在一道屏障上凿开了一个洞。

由于大批量缓存在同一时间失效造成的

不同场景下的解决方式可如下：



- 若缓存的数据是基本不会发生更新的，则可尝试将该热点数据设置为永不过期。
- 若缓存的数据更新不频繁，且缓存刷新的整个流程耗时较少的情况下，则可以采用基于 redis、zookeeper 等分布式中间件的分布式互斥锁，或者本地互斥锁以保证仅少量的请求能请求数据库并重新构建缓存，其余线程则在锁释放后能访问到新缓存。
- 若缓存的数据更新频繁或者缓存刷新的流程耗时较长的情况下，**可以利用定时线程在缓存过期前主动的重新构建缓存或者延后缓存的过期时间**，以保证所有的请求能一直访问到对应的缓存。主动构建缓存



**雪崩是很多 key，击穿是某一个key 缓存。**



缓存降级 当访问量剧增、服务出现问题（如响应时间慢或不响应）或非核心服务影响到核心流程的性能时，仍然需要保证服务还是可用的，即使是有损服务。系统可以根据一些关键数据进行自动降级，也可以配置开关实现人工降级。 降级的最终目的是保证核心服务可用，即使是有损的。而且有些服务是无法降级的（如加入购物车、结算）。 以参考日志级别设置预案： 

（1）一般：比如有些服务偶尔因为网络抖动或者服务正在上线而超时，可以自动降级； 

（2）警告：有些服务在一段时间内成功率有波动（如在95~100%之间），可以自动降级或人工降级，并发送告警； 

（3）错误：比如可用率低于90%，或者数据库连接池被打爆了，或者访问量突然猛增到系统能承受的最大阀值，此时可以根据情况自动降级或者人工降级； 

（4）严重错误：比如因为特殊原因数据错误了，此时需要紧急人工降级。

服务降级的目的，是为了防止Redis服务故障，导致数据库跟着一起发生雪崩问题。因此，对于不重要的缓存数据，可以采取服务降级策略，例如一个比较常见的做法就是，Redis出现问题，不去数据库查询，而是直接返回默认值给用户。

---------------

:缓存穿透是访问一个不存在的key,缓存不起作用,请求会穿透到DB,流量大时DB会挂掉。

​	缓存击穿是访问一个存在的key,在缓存过期的一刻,同时有大量的请求,这些请求都会击穿到DB,造成瞬时DB请求量大、压力骤增。

## **热点缓存key重建优化**

开发人员使用“缓存+过期时间”的策略既可以加速数据读写， 又保证数据的定期更新， 这种模式基本能够满足绝大部分需求。 但是有两个问题如果同时出现， 可能就会对应用造成致命的危害：

- 当前key是一个热点key（例如一个热门的娱乐新闻），并发量非常大。
- 重建缓存不能在短时间完成， 可能是一个复杂计算， 例如复杂的SQL、 多次IO、 多个依赖等。

在缓存失效的瞬间， 有大量线程来重建缓存， 造成后端负载加大， 甚至可能会让应用崩溃。

要解决这个问题主要就是要避免大量线程同时重建缓存。

我们可以利用互斥锁来解决，此方法只允许一个线程重建缓存， 其他线程等待重建缓存的线程执行完， 重新从缓存获取数据即可

示例伪代码：


               String get(String key) {
                   // 从Redis中获取数据
                   String value = redis.get(key);
                   // 如果value为空， 则开始重构缓存
                   if (value == null) {
                       // 只允许一个线程重建缓存， 使用nx， 并设置过期时间ex
                       String mutexKey = "mutext:key:" + key;
                       if (redis.set(mutexKey, "1", "ex 180", "nx")) {
                            // 从数据源获取数据
                           value = db.get(key);
                           // 回写Redis， 并设置过期时间
                           redis.setex(key, timeout, value);
                           // 删除key_mutex
                           redis.delete(mutexKey);
                       }// 其他线程休息50毫秒后重试
                       else {
                           Thread.sleep(50);
                           get(key);
                       }
                   }
                   return value;
               }
               ```



# **@CachePut**

**@Cacheable**

@Cacheble注解表示这个方法有了缓存的功能，方法的返回值会被缓存下来，下一次调用该方法前，会去检查是否缓存中已经有值，如果有就直接返回，不调用方法。如果没有，就调用方法，然后把结果缓存起来。这个注解**「一般用在查询方法上」**。

加了@CachePut注解的方法，会把方法的返回值put到缓存里面缓存起来，供其它地方使用。它**「通常用在新增方法上」**。

@CachePut也可以声明一个方法支持缓存功能，与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。 @CachePut也可以标注在类上和方法上。@CachePut的属性与@Cacheable的属性一样



# **@CacheEvict**

使用了CacheEvict注解的方法，会清空指定缓存。**「一般用在更新或者删除的方法上」**。

# **@Caching**

Java注解的机制决定了，一个方法上只能有一个相同的注解生效。那有时候可能一个方法会操作多个缓存（这个在删除缓存操作中比较常见，在添加操作中不太常见）。(??)❓❓

我们知道，使用了AOP的Bean，会生成一个代理对象，实际调用的时候，会执行这个代理对象的一系列的Interceptor。Spring Cache使用的是一个叫做CacheInterceptor的拦截器。我们如果加了缓存相应的注解，就会走到这个拦截器上。这个拦截器继承了CacheAspectSupport类，会执行这个类的execute方法，这个方法就是我们要分析的核心方法了。

关于[@cacheable(value="cacheName",key="#id")

value属性，确实是指定了缓存的名称，但是并没有强行将value不同的缓存值加以区分（这个是有道理的，因为有些时候，需要把不同业务属性的实体存在一个缓存里，这种情况肯定是有的，所以如果强行通过value区分的话，上述需求反而实现不了了）；
所以，spring的设计者是把——是否通过value区分的决定权——交给我们了，如果要通过value区分，那就再手动用一下#root.caches，向spring表明，我们要用value所表示的缓存名来区分具体的缓存实体；
具体用法示例：
当方法的value属性进行了设置（如@Cacheable(value={"cache1", "cache2"})），则有两个cache；
此时可以使用@Cacheable(value={"cache1", "cache2"},key="#root.caches[0].name")，意思就是使用value为“cache1”的缓存；

如果要使用其它的缓存框架，应该怎么做呢？



通过上面的源码分析我们知道，如果要使用其它的缓存框架，我们只需要重新定义好CacheManager和CacheResolver这两个Bean就行了。

**事实上，Spring会自动检测我们是否引入了相应的缓存框架**，如果我们引入了spring-data-redis，Spring就会自动使用spring-data-redis提供的RedisCacheManager，RedisCache。

在整合redis中:

```java


@Configuration
@EnableCaching
public class RedisConfig {
	@Primary
	@Bean
//@ConditionalOnBean(RedisConnectionFactory.class)

	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration
				//设置缓存的默认超时时间：30分钟
				.entryTtl(Duration.ofMinutes(30L))
				//如果是空值，不缓存
				.disableCachingNullValues()

				//设置key序列化器
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
				//设置value序列化器
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));

		return RedisCacheManager
				.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration)
				.build();
	}

	/**
	 * key序列化器
	 */
	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}
	/**
	 * value序列化器
	 */
	private RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

}


```



如果我们要使用Caffeine框架。只需要引入Caffeine，Spring Cache就会默认使用CaffeineCacheManager和CaffeineCache。



# 定制缓存管理器

在Spring中，我们有两种方法定制缓存管理器，一种是像代码清单7-27那样通过配置消除缓存键的前缀和自定义超时时间的属性来定制生成RedisCacheManager；另一种方法是不采用Spring Boot为我们生成的方式，而是完全通过自己的代码创建缓存管理器，尤其是当需要比较多自定义的时候，更加推荐你采用自定义的代码。 首先我们在代码清单7-27的配置基础上，增加对应的新配置，使得Spring Boot为我们生成的RedisCacheManager对象的时候，消除前缀的设置并且设置超时时间，如代码清单7-35所示。 

代码清单7-35　重置Redis缓存管理器 # 禁用前缀
spring.cache.redis.use-key-prefix=false
\# 允许保存空值
\#spring.cache.redis.cache-null-values=true
\# 自定义前缀
\#spring.cache.redis.key-prefix=
\# 定义超时时间，单位毫秒
spring.cache.redis.time-to-live=600000 

有时候，在自定义时可能存在比较多的配置，也可以不采用Spring Boot自动配置的缓存管理器，而是使用自定义的缓存管理器，这也是没有问题的。首先需要删除代码清单7-27和代码清单7-35中关于Redis缓存管理器的配置，然后在代码清单7-34中添加代码清单7-36所示的代码，给IoC容器增加缓存管理器。 代码清单7-36　自定义缓存管理器 // 注入连接工厂，由Spring Boot自动配置生成

```java
@Autowired
private RedisConnectionFactory connectionFactory = null;
// 自定义Redis缓存管理器
@Bean(name = "redisCacheManager" )
public RedisCacheManager initRedisCacheManager() {
  // Redis加锁的写入器
  RedisCacheWriter writer= RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
  // 启动Redis缓存的默认设置
  RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
  // 设置JDK序列化器
  config = config.serializeValuesWith(
      SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
  // 禁用前缀
  config = config.disableKeyPrefix();
  //设置10 min超时
  config = config.entryTtl(Duration.ofMinutes(10));
  // 创建缓Redis存管理器
  RedisCacheManager redisCacheManager = new RedisCacheManager(writer, config);
  return redisCacheManager;
}
```




这里首先注入了RedisConnectionFactory对象，该对象是由Spring Boot自动生成的。在创建Redis缓存管理器对象RedisCacheManager的时候，首先创建了带锁的RedisCacheWriter对象，然后使用RedisCacheConfiguration对其属性进行配置，这里设置了禁用前缀，并且超时时间为10 min；最后就通过RedisCacheWriter对象和RedisCacheConfiguration对象去构建RedisCacheManager对象了，这样就完成了Redis缓存管理器的自定义。

# **Redis Lua脚本**



Redis在2.6推出了脚本功能，允许开发者使用Lua语言编写脚本传到Redis中执行。使用脚本的好处如下:

1、**减少网络开销**：本来5次网络请求的操作，可以用一个请求完成，原先5次请求的逻辑放在redis服务器上完成。使用脚本，减少了网络往返时延。**这点跟管道类似**。

2、**原子操作**：Redis会将整个脚本作为一个整体执行，中间不会被其他命令插入。**管道不是原子的，不过redis的批量操作命令(类似mset)是原子的。**

3、**替代redis的事务功能**：redis自带的事务功能很鸡肋，而redis的lua脚本几乎实现了常规的事务功能，官方推荐如果要使用redis的事务功能可以用redis lua替代。

**官网文档上有这样一段话：**

从Redis2.6.0版本开始，通过内置的Lua解释器，可以使用EVAL命令对Lua脚本进行求值。EVAL命令的格式如下：

​                EVAL script numkeys key [key ...] arg [arg ...]　              

script参数是一段Lua脚本程序，它会被运行在Redis服务器上下文中，这段脚本**不必(也不应该)定义为一个Lua函数**。numkeys参数用于指定键名参数的个数。键名参数 key [key ...] 从EVAL的第三个参数开始算起，表示在脚本中所用到的那些Redis键(key)，这些键名参数可以在 Lua中通过全局变量KEYS数组，用1为基址的形式访问( KEYS[1] ， KEYS[2] ，以此类推)。

在命令的最后，那些不是键名参数的附加参数 arg [arg ...] ，可以在Lua中通过全局变量**ARGV**数组访问，访问的形式和KEYS变量类似( ARGV[1] 、 ARGV[2] ，诸如此类)。例如

​                127.0.0.1:6379> eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 key2 first second 

1)"key1" 

2)"key2" 

3)"first" 

4)"second"                

其中 "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 是被求值的Lua脚本，数字2指定了键名参数的数量， key1和key2是键名参数，分别使用 KEYS[1] 和 KEYS[2] 访问，而最后的 first 和 second 则是附加参数，可以通过 ARGV[1] 和 ARGV[2] 访问它们。

在 Lua 脚本中，可以使用**redis.call()**函数来执行Redis命令

```lua
jedis.set("product_stock_10016", "15");  //初始化商品10016的库存
String script = " local count = redis.call('get', KEYS[1]) " +
                " local a = tonumber(count) " +
                " local b = tonumber(ARGV[1]) " +
                " if a >= b then " +
                "   redis.call('set', KEYS[1], a-b) " +
                "   return 1 " +
                " end " +
                " return 0 ";
Object obj = jedis.eval(script, Arrays.asList("product_stock_10016"), Arrays.asList("10"));
System.out.println(obj);
```

**注意，不要在Lua脚本中出现死循环和耗时的运算，否则redis会阻塞，将不接受其他的命令， 所以使用时要注意不能出现死循环、耗时的运算。redis是单进程、单线程执行脚本。管道不会阻塞redis**



## 与ehcache对比


EhCache 是一个纯Java的进程内缓存框架，具有快速、精干等特点，是Hibernate中默认CacheProvider。Ehcache是一种广泛使用的开源Java分布式缓存。主要面向通用缓存,Java EE和轻量级容器。它具有内存和磁盘存储，缓存加载器,缓存扩展,缓存异常处理程序,一个gzip缓存servlet过滤器,支持REST和SOAP api等特点。
Spring 提供了对缓存功能的抽象：即允许绑定不同的缓存解决方案（如Ehcache），但本身不直接提供缓存功能的实现。它支持注解方式使用缓存，非常方便。 

特性：
快速
简单
多种缓存策略
缓存数据有两级：内存和磁盘，因此无需担心容量问题
缓存数据会在虚拟机重启的过程中写入磁盘
可以通过RMI、可插入API等方式进行分布式缓存
具有缓存和缓存管理器的侦听接口
支持多缓存管理器实例，以及一个实例的多个缓存区域
提供Hibernate的缓存实现

集成
可以单独使用，一般在第三方库中被用到的比较多（如mybatis、shiro等）ehcache 对分布式支持不够好，多个节点不能同步，通常和redis一块使用

ehcache 和 redis 比较redis是通过socket访问到缓存服务，效率比ecache低，比数据库要快很多，处理集群和分布式缓存方便，有成熟的方案。如果是单个应用或者对缓存访问要求很高的应用，用ehcache。如果是大型系统，存在缓存共享、分布式部署、缓存内容很大的，建议用redis。

ehcache也有缓存共享方案，不过是通过RMI或者Jgroup多播方式进行广播缓存通知更新，缓存共享复杂，维护不方便；简单的共享可以，但是涉及到缓存恢复，大数据缓存，则不合适。 

Ehcache的特点

（1）快速简单，具有多种缓存策略

（2）缓存数据有两级为内存和磁盘，缓存数据会在虚拟机重启的过程中写入磁盘

（3）可以通过RMI、可插入API等方式进行分布式缓存

（4）具有缓存和缓存管理器的侦听接口

（5）支持多缓存管理器实例，以及一个实例的多个缓存区域。并提供Hibernate的缓存实现 

Redis的特点：

（1）速度快，持久化。==并且Redis的所有数据都存储在内存中==。

（2）支持多种数据结构例：String、List、Set、Hash、Zset

（3）支持多种编程语言例：Java、php、Python、Ruby、Lua、Node.js

（4）功能丰富 ，除了支持五种数据结构之外，还支持事务、流水线、发布/订阅、消息队列等功能。

（5）主服务器（master）执行添加、修改、删除，从服务器执行查询。

 





## 1、Redis 和 Redisson 有什么关系？

Redisson 是一个高级的分布式协调 Redis 客服端，能帮助用户在分布式环境中轻松实现一些Java 的 对 象 (Bloom filter, BitSet, Set, SetMultimap, ScoredSortedSet, SortedSet, Map, 
ConcurrentMap, List, ListMultimap, Queue, BlockingQueue, Deque, BlockingDeque, Semaphore, 
Lock, ReadWriteLock, AtomicLong, CountDownLatch, Publish / Subscribe, HyperLogLog)。
16、Jedis 与 Redisson 对比有什么优缺点？
Jedis 是 Redis 的 Java 实现的客户端，其 API 提供了比较全面的 Redis 命令的支持；Redisson实现了分布式和可扩展的 Java 数据结构，和 Jedis 相比，功能较为简单，不支持字符串操作，
不支持排序、事务、管道、分区等 Redis 特性。==Redisson 的宗旨是促进使用者对 Redis 的关注分离，从而让使用者能够将精力更集中地放在处理业务逻辑上。==

##  2.说说 Redis 哈希槽的概念？

Redis 集群没有使用一致性 hash,而是引入了哈希槽的概念，Redis 集群有 16384 个哈希槽，每个 key 通过 CRC16 校验后对 16384 取模来决定放置哪个槽，集群的每个节点负责一部分hash 槽。
19、Redis 集群的主从复制模型是怎样的？
为了使在部分节点失败或者大部分节点无法通信的情况下集群仍然可用，所以集群使用了主从复制模型,每个节点都会有 N-1 个复制品.

# 20、Redis 集群会有写操作丢失吗？为什么？

Redis 并不能保证数据的强一致性，这意味这在实际中集群在特定的条件下可能会丢失写操作。
21、Redis 集群之间是如何复制的？
异步复制
22、Redis 集群最大节点个数是多少？
16384 个。
23、Redis 集群如何选择数据库？
Redis 集群目前无法做数据库选择，默认在 0 数据库。
24、怎么测试 Redis 的连通性？
ping

## 3、Redis 中的管道有什么用？

一次请求/响应服务器能实现处理新的请求即使旧的请求还未被响应。这样就可以将多个命令发送到服务器，而不用等待回复，最后在一个步骤中读取该答复。
这就是管道（pipelining），是一种几十年来广泛使用的技术。例如许多 POP3 协议已经实现支持这个功能，大大加快了从服务器下载新邮件的过程。

## 4、怎么理解 Redis 事务？

**Redis事务的概念**

Redis 事务的本质是通过MULTI、EXEC、WATCH等一组命令的集合。事务支持一次执行多个命令，一个事务中所有命令都会被序列化。在事务执行过程，会按照顺序串行化执行队列中的命令，其他客户端提交的命令请求不会插入到事务执行命令序列中。

1. 事务开始 MULTI
2. 命令入队
3. 事务执行 EXEC



事务执行过程中，如果服务端收到有EXEC、DISCARD、WATCH、MULTI之外的请求，将会把请求放入队列中排队

**显式开启一个事务**

客户端通过 `MULTI` 命令显式地表示开启一个事务，随后的命令将排队缓存，并不会实际执行。

**命令入队**

客户端把事务中的要执行的一系列指令发送到服务端。

需要注意的是，虽然指令发送到服务端，但是 Redis 实例只是把这一系列指令暂存在一个命令队列中，并不会立刻执行。

**执行事务或丢弃**

客户端向服务端发送提交或者丢弃事务的命令，让 Redis 执行第二步中发送的具体指令或者清空队列命令，放弃执行。

Redis 只需在调用 EXEC 时，即可安排队列命令执行。

也可通过 DISCARD 丢弃第二步中保存在队列中的命令。

通过 `MULTI` 和 `EXEC` 执行一个事务过程：

```
# 开启事务
> MULTI
OK
# 开始定义一些列指令
> SET “公众号:码哥字节” "粉丝 100 万"
QUEUED
> SET "order" "30"
QUEUED
> SET "文章数" 666
QUEUED
> GET "文章数"
QUEUED
# 实际执行事务
> EXEC
1) OK
2) OK
3) OK
4) "666"
```

我们看到每个读写指令执行后的返回结果都是 `QUEUED`，表示谢谢操作都被暂存到了命令队列，还没有实际执行。

当执行了 `EXEC` 命令，就可以看到具体每个指令的响应数据。

通过 `MULTI` 和 `DISCARD`丢弃队列命令：

```
# 初始化订单数
> SET "order:mobile" 100
OK
# 开启事务
> MULTI
OK
# 订单 - 1
> DECR "order:mobile"
QUEUED
# 丢弃丢列命令
> DISCARD
OK
# 数据没有被修改
> GET "order:mobile"
"100"
```



总结说：redis事务就是一次性、顺序性、排他性的执行一个队列中的一系列命令。

事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
事务是一个原子操作：事务中的命令要么全部被执行，要么全部都不执行。

Redis的事务总是具有ACID中的一致性和隔离性，其他特性是不支持的。当服务器运行在*AOF*持久化模式下，并且appendfsync选项的值为always时，事务也具有耐久性。

**Redis事务保证原子性吗，支持回滚吗**

Redis中，单条命令是原子性执行的，但事务不保证原子性，且没有回滚。事务中任意命令执行失败，其余的命令仍会被执行。



**Redis事务其他实现**

- 基于Lua脚本，Redis可以保证脚本内的命令一次性、按顺序地执行，
  其同时也不提供事务运行错误的回滚，执行过程中如果部分命令运行错误，剩下的命令还是会继续运行完
- 基于中间标记变量，通过另外的标记变量来标识事务是否执行完成，读取数据时先读取该标记变量判断是否事务执行完成。但这样会需要额外写代码实现，比较繁琐



27、Redis 事务相关的命令有哪几个？
MULTI、EXEC、DISCARD、WATCH
28、Redis key 的过期时间和永久有效分别怎么设置？
EXPIRE 和 PERSIST 命令。

### Redis 事务满足 ACID吗？

**Redis 事务可以一次执行多个命令， 并且带有以下三个重要的保证：**

1. 批量指令在执行 EXEC 命令之前会放入队列暂存；
2. 收到 EXEC 命令后进入事务执行，事务中任意命令执行失败，其余的命令依然被执行；
3. 事务执行过程中，其他客户端提交的命令不会插入到当前命令执行的序列中。

在事务期间，可能遇到两种命令错误：

- 在执行 `EXEC` 命令前，发送的指令本身就错误。如下：

- - 参数数量错误；
  - 命令名称错误，使用了不存在的命令；
  - 内存不足（Redis 实例使用 `maxmemory`指令配置内存限制）。

- 在执行 `EXEC` 命令后，命令可能会失败。例如，命令和操作的数据类型不匹配（对 String 类型 的 value 执行了 List 列表操作）；

- 在执行事务的 `EXEC` 命令时。Redis 实例发生了故障导致事务执行失败。

==EXEC 执行前报错==

在命令入队时，Redis 就会**报错并且记录下这个错误**。

此时，我们**还能继续提交命令操作**。

等到执行了 `EXEC`命令之后，Redis 就会**拒绝执行所有提交的命令操作，返回事务失败的结果**。

这样一来，**事务中的所有命令都不会再被执行了，保证了原子性。**

如下是指令入队发生错误，导致事务失败的例子：

```
#开启事务
> MULTI
OK
#发送事务中的第一个操作，但是Redis不支持该命令，返回报错信息
127.0.0.1:6379> PUT order 6
(error) ERR unknown command `PUT`, with args beginning with: `order`, `6`,
#发送事务中的第二个操作，这个操作是正确的命令，Redis把该命令入队
> DECR b:stock
QUEUED
#实际执行事务，但是之前命令有错误，所以Redis拒绝执行
> EXEC
(error) EXECABORT Transaction discarded because of previous errors.
```

==EXEC 执行后报错==

事务操作入队时，命令和操作的数据类型不匹配，但 Redis 实例没有检查出错误。

但是，在执行完 EXEC 命令以后，Redis 实际执行这些指令，就会报错。

**敲黑板了：Redis 虽然会对错误指令报错，但是事务依然会把正确的命令执行完，这时候事务的原子性就无法保证了！**

> 为什么 Redis 不支持回滚？

其实，Redis 中并没有提供回滚机制。虽然 Redis 提供了 DISCARD 命令。

但是，**这个命令只能用来主动放弃事务执行，把暂存的命令队列清空**，起不到回滚的效果

==【EXEC 执行时，发生故障】==

如果 Redis 开启了 AOF 日志，那么，只会有部分的事务操作被记录到 AOF 日志中。

我们需要使用 redis-check-aof 工具检查 AOF 日志文件，这个工具可以把未完成的事务操作从 AOF 文件中去除。

这样一来，我们使用 AOF 恢复实例后，事务操作不会再被执行，从而保证了原子性。

**简单总结**：

- 命令入队时就报错，会放弃事务执行，保证原子性；
- 命令入队时没报错，实际执行时报错，不保证原子性；
- EXEC 命令执行时实例故障，如果开启了 AOF 日志，可以保证原子性。

### 隔离性

事务执行又可以分成命令入队（EXEC 命令执行前）和命令实际执行（EXEC 命令执行后）两个阶段。

所以在并发执行的时候我们针对这两个阶段分两种情况分析：

1. 并发操作在 `EXEC` 命令前执行，隔离性需要通过 `WATCH` 机制保证；
2. 并发操作在 `EXEC` 命令之后，隔离性可以保证。

> ❝
>
> 码哥，什么是 WATCH 机制？

我们重点来看第一种情况：一个事务的 EXEC 命令还没有执行时，事务的命令操作是暂存在命令队列中的。

此时，如果有其它的并发操作，同样的 key 被修改，需要看事务是否使用了 `WATCH` 机制。

WATCH 机制的作用是：在事务执行前，监控一个或多个键的值变化情况，当事务调用 EXEC 命令执行时，WATCH 机制会先检查监控的键是否被其它客户端修改了。

**如果修改了，就放弃事务执行，避免事务的隔离性被破坏。**

同时，客户端可以再次执行事务，此时，如果没有并发修改事务数据的操作了，事务就能正常执行，隔离性也得到了保证。

![图片](https://mmbiz.qpic.cn/mmbiz_png/EoJib2tNvVtfoPs4n7qZWxz0d3ykWRgQQYTnFAwE1oAne0nOoxksAO8yr1jqfDgsTX7MsUx2sEbxUE8bV6Ekq6w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**没有 WATCH**

如果没有 WATCH 机制， 在 EXEC 命令执行前的并发操作对数据读写。

当执行 EXEC 的时候，事务内部要操作的数据已经改变，Redis 并没有做到事务之间的隔离。

![图片](https://mmbiz.qpic.cn/mmbiz_png/EoJib2tNvVtfoPs4n7qZWxz0d3ykWRgQQtDqGRlQBpvjC3iaoXncZpueuGW3qPB93852PA1zUfMZvsNEvqJ8lazw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**并发操作在 EXEC 之后接收执行**

至于第二种情况，因为 Redis 是用单线程执行命令，而且，EXEC 命令执行后，Redis 会保证先把命令队列中的所有命令执行完再执行之后的指令。

所以，在这种情况下，并发操作不会破坏事务的隔离性。

![图片](https://mmbiz.qpic.cn/mmbiz_png/EoJib2tNvVtfoPs4n7qZWxz0d3ykWRgQQ66icxzpow3fx0lqIvbOHzoTwvkJGBPRqXpNWSJrIfWia7sWiaa9Y6ibg1A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### 持久性

如果 Redis 没有使用 RDB 或 AOF，那么事务的持久化属性肯定得不到保证。

如果 Redis 使用了 RDB 模式，那么，**在一个事务执行后，而下一次的 RDB 快照还未执行前，如果发生了实例宕机，数据丢失**，这种情况下，事务修改的数据也是不能保证持久化的。

如果 Redis 采用了 AOF 模式，因为 AOF 模式的三种配置选项 no、everysec 和 always 都会存在数据丢失的情况。

所以，事务的持久性属性也还是得不到保证。

**不管 Redis 采用什么持久化模式，事务的持久性属性是得不到保证的。**

==总结==

- Redis 具备了一定的原子性，但不支持回滚。
- Redis 具备 ACID 中一致性的概念。点)
- Redis 具备隔离性。
- Redis 无法保证持久性。





## 5、Redis 如何做内存优化？

尽可能使用散列表（hashes），散列表（是说散列表里面存储的数少）使用的内存非常小，
所以你应该尽可能的将你的数据模型抽象到一个散列表里面。比如你的 web 系统中有一个
用户对象，不要为这个用户的名称，姓氏，邮箱，密码设置单独的 key,而是应该把这个用户的所有信息存储到一张散列表里面.（？怎么存？）

## 6、Redis 回收进程如何工作的？

一个客户端运行了新的命令，添加了新的数据。
Redi 检查内存使用情况，如果大于 maxmemory 的限制, 则根据设定好的策略进行回收。
一个新的命令被执行，等等。
所以我们不断地穿越内存限制的边界，通过不断达到边界然后不断地回收回到边界以下。
如果一个命令的结果导致大量内存被使用（例如很大的集合的交集保存到一个新的键），不用多久内存限制就会被这个内存使用量超越。

【（没看懂）】

31、Redis 回收使用的是什么算法？
LRU 算法
32、Redis 如何做大量数据插入？ 

Redis2.6 开始 redis-cli 支持一种新的被称之为 pipe mode 的新模式用于执行大量数据插入工作。



### lru 算法

1、基本介绍
LRU（Least Recently Used）即最近最少使用，是一种常用的页面置换算法，选择最近最久未使用的页面予以淘汰。对于缓存来说，容量是有限的，当容量满时，就需要清理一些对于当前情景来说没用的内容，从而为新来的腾出位置，如何选择就对应了某种策略，而LRU采取的是一种选择最近最久未使用作为淘汰的策略。

最近即当前，最久未使用也即最少使用，要实现如此特征，我们有所规定，每当访问了缓存中存在的数据或向缓存中新增数据，该数据就得移至最前面（表头），如果新增数据时，缓存已满，就要删除当前位于最后（即最近最久未使用）的数据。

我们期望获取缓存中的数据能尽量快即时间复杂度要低，最优为O(1)，这很容易就想到要使用到map这种K-V键值对数据结构；前面说到还需要移动数据至最前面以及删除最后的数据，所有仅有map这个数据结构还不够，还需增加一个链表，并且是双向链表（使得对任意节点的增加或删除操作的时间复杂度为O(1)）；而LRU算法就是基于哈希表+双向链表，使得每个操作的时间复杂度为O(1)。

下面将基于Java语言通过两种形式来实现LRU算法，

2、基于LinkedHashMap
Java语言里已有封装好的数据结构LinkedHashMap，通过继承它再覆盖removeEldestEntry方法即可，代码如下：



```java
class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private int capacity;

public LRUCache(int capacity){
    super(capacity,0.75F,true);
    this.capacity = capacity;
}

public V get(Object key) {
    return super.get(key);
}

public V put(K key, V value) {
    return super.put(key, value);
}

@Override
protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() > capacity;
}
```
}相信很多人会有疑问，就这么几行代码就实现了传说中的LRU算法吗？是的，LinkedHashMap都给我们封装好了，下面来探究LinkedHashMap源码是如何实现的，
上面LRUCache类的构造方法中调用了super(capacity,0.75F,true);这样一行代码，即LinkedHashMap的构造方法，这里先说明下，LinkedHashMap是继承了HashMap。虽然LinkedHashMap增加了时间和空间上的开销，但是它通过维护一个额外的双向链表保证了迭代顺序。

特别地，该迭代顺序可以是插入顺序，也可以是访问顺序。因此，根据链表中元素的顺序可以将LinkedHashMap分为：保持插入顺序的LinkedHashMap 和 保持访问顺序的LinkedHashMap，其中LinkedHashMap的默认实现是按插入顺序排序的。



## 7、为什么要做 Redis 分区？




分区可以让 Redis 管理更大的内存，Redis 将可以使用所有机器的内存。如果没有分区，你最多只能使用一台机器的内存。分区使 Redis 的计算能力通过简单地增加计算机得到成倍提升,Redis 的网络带宽也会随着计算机和网卡的增加而成倍增长。
34、你知道有哪些 Redis 分区实现方案？
客户端分区就是在客户端就已经决定数据会被存储到哪个redis节点或者从哪个redis节点读取。大多数客户端已经实现了客户端分区。
代理分区 意味着客户端将请求发送给代理，然后代理决定去哪个节点写数据或者读数据。
代理根据分区规则决定请求哪些 Redis 实例，然后根据 Redis 的响应结果返回给客户端。redis和 memcached 的一种代理实现就是 Twemproxy
查询路由(Query routing) 的意思是客户端随机地请求任意一个 redis 实例，然后由 Redis 将请求转发给正确的 Redis 节点。Redis Cluster 实现了一种混合形式的查询路由，但并不是直接将请求从一个 redis 节点转发到另一个 redis 节点，而是在客户端的帮助下直接redirected 到正确的 redis 节点。
35、Redis 分区有什么缺点？
涉及多个 key 的操作通常不会被支持。例如你不能对两个集合求交集，因为他们可能被存储到不同的 Redis 实例（实际上这种情况也有办法，但是不能直接使用交集指令）。
同时操作多个 key,则不能使用 Redis 事务.
分区使用的粒度是 key，不能使用一个非常长的排序 key 存储一个数据集（The partitioning 
granularity is the key, so it is not possible to shard a dataset with a single huge key like a very big sorted set）.
当使用分区的时候，数据处理会非常复杂，例如为了备份你必须从不同的 Redis 实例和主机
同时收集 RDB / AOF 文件。
分区时动态扩容或缩容可能非常复杂。Redis 集群在运行时增加或者删除 Redis 节点，能做到最大程度对用户透明地数据再平衡，但其他一些客户端分区或者代理分区方法则不支持这种特性

## 8、Redis 持久化数据和缓存怎么做扩容？



如果 Redis 被当做缓存使用，使用一致性哈希实现动态扩容缩容。
如果 Redis 被当做一个持久化存储使用，必须使用固定的 keys-to-nodes 映射关系，节点的数量一旦确定不能变化。否则的话(即 Redis 节点需要动态变化的情况），必须使用可以在运行时进行数据再平衡的一套系统，而当前只有 Redis 集群可以做到这样。
37、分布式 Redis 是前期做还是后期规模上来了再做好？为什么？
既然 Redis 是如此的轻量（单实例只使用 1M 内存）,为防止以后的扩容，最好的办法就是一开始就启动较多实例。即便你只有一台服务器，你也可以一开始就让 Redis 以分布式的方式运行，使用分区，在同一台服务器上启动多个实例。
一开始就多设置几个 Redis 实例，例如 32 或者 64 个实例，对大多数用户来说这操作起来可能比较麻烦，但是从长久来看做这点牺牲是值得的。
这样的话，当你的数据不断增长，需要更多的 Redis 服务器时，你需要做的就是仅仅将 Redis实例从一台服务迁移到另外一台服务器而已（而不用考虑重新分区的问题）。一旦你添加了另一台服务器，你需要将你一半的 Redis 实例从第一台机器迁移到第二台机器。
38、Twemproxy 是什么？
Twemproxy 是 Twitter 维护的（缓存）代理系统，代理 Memcached 的 ASCII 协议和 Redis 协议。它是单线程程序，使用 c 语言编写，运行起来非常快。它是采用 Apache 2.0 license 的开
源软件。 Twemproxy 支持自动分区，如果其代理的其中一个 Redis 节点不可用时，会自动将该节点排除（这将改变原来的 keys-instances 的映射关系，所以你应该仅在把 Redis 当缓存
时使用 Twemproxy)。 Twemproxy 本身不存在单点问题，因为你可以启动多个 Twemproxy 实例，然后让你的客户端去连接任意一个 Twemproxy 实例。 ==Twemproxy 是 Redis 客户端和服务器端的一个中间层，由它来处理分区功能应该不算复杂，并且应该算比较可靠的。==
39、支持一致性哈希的客户端有哪些？
Redis-rb、Predis 等。



## 9.redis持久化



什么是 RDB 内存快照？

**Redis可以通过创建快照来获得存储在内存里面的数据在某个时间点上的副本。Redis创建快照之后，可以对快照进行备份，可以将快照复制到其他服务器从而创建具有相同数据的服务器副本**（Redis主从结构，主要用来提高Redis性能），还可以将快照留在原地以便重启服务器的时候使用。
快照持久化是Redis默认采用的持久化方式，在redis.conf配置文件中默认有此下配置：
save 900 1 #在900秒(15分钟)之后，如果至少有1个key发生变化，Redis就会自动触发BGSAVE命令创建快照。
save 300 10 #在300秒(5分钟)之后，如果至少有10个key发生变化，Redis就会自动触发BGSAVE命令创建快照。
save 60 10000 #在60秒(1分钟)之后，如果至少有10000个key发生变化，Redis就会自动触发BGSAVE命令创建快照。
复制代码



在 Redis 执行「写」指令过程中，内存数据会一直变化。所谓的内存快照，指的就是 Redis 内存中的数据在某一刻的状态数据。

好比时间定格在某一刻，当我们拍照的，通过照片就能把某一刻的瞬间画面完全记录下来。

Redis 跟这个类似，就是把某一刻的数据以文件的形式拍下来，写到磁盘上。这个快照文件叫做 **RDB 文件，RDB 就是 Redis DataBase 的缩写。**

![图片](https://mmbiz.qpic.cn/mmbiz_png/b95QHPkcOMCRsxNm7XcM6NTqUcVCRkeaVKTGj0eZGqfVttNRSCpXRB6R0wfv2FGptFGYXfpaKgxIvFvkz0HEWg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

在做数据恢复时，直接将 RDB 文件读入内存完成恢复。





1.持久化的⼏种⽅式
Redis 持久化拥有以下三种⽅式：
**快照⽅式**（RDB, Redis DataBase）将某⼀个时刻的内存数据，以⼆进制的⽅式写⼊磁盘；**⽂件追加⽅式**（AOF, Append Only File），记录所有的操作命令，并以⽂本的形式追加到⽂件中；
**混合持久化⽅式，**Redis 4.0 之后新增的⽅式，混合持久化是结合了 RDB 和 AOF 的优点，在写⼊的时候，先把当前的数据以RDB 的形式写⼊⽂件的开头，**再将后续的操作命令以 AOF 的格式存⼊⽂件**，这样既能保证 Redis 重启时的速度，⼜能避免数据丢失的⻛险。
因为每种持久化⽅案，都有特定的使⽤场景，让我们先从 RDB 持久化说起吧。
2.RDB简介
RDB（Redis DataBase）是将某⼀个时刻的内存快照（Snapshot），以⼆进制的⽅式写⼊磁盘的过程。
3.持久化触发
RDB 的持久化触发⽅式有两类：⼀类是⼿动触发，另⼀类是⾃动触发。
<font color="red">1）⼿动触发</font>
⼿动触发持久化的操作有两个： save 和 bgsave ，它们主要区别体现在：是否阻塞 Redis 主线程的执⾏。
① save 命令
在客⼾端中执⾏ save 命令，就会触发 Redis 的持久化，但同时也是使 Redis 处于阻塞状态，直到 RDB 持久化完成，才会响应其他客⼾端发来的命令，所以在⽣产环境⼀定要慎⽤。
save 命令使⽤如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/1a95153d90e04f10b10b0d227c56b0a3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
从图⽚可以看出，当执⾏完 save 命令之后，持久化⽂件 dump.rdb 的修改时间就变了，这就表⽰ save 成功的触发了 RDB 持久化。save 命令执⾏流程，如下图所⽰：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f8c993c0207d469c968546069611cd8b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_10,color_FFFFFF,t_70,g_se,x_16)
② bgsave 命令
bgsave（background save）既后台保存的意思， 它和 save 命令最⼤的区别就是 bgsave 会 fork() ⼀个⼦进程来执⾏持久化，整个过程中只有在 fork() ⼦进程时有短暂的阻塞，当⼦进程被创建之后，Redis 的主进程就可以响应其他客⼾端的请求了，相对于整个流程都阻塞的 save 命令来说，显然 bgsave 命令更适合我们使⽤。bgsave 命令使⽤，如下图所⽰：
![在这里插入图片描述](https://img-blog.csdnimg.cn/c17baa25ece0467fadeec50ce685ba68.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_10,color_FFFFFF,t_70,g_se,x_16)
<font color="red">2)⾃动触发</font>
说完了 RDB 的⼿动触发⽅式，下⾯来看如何⾃动触发 RDB 持久化？RDB ⾃动持久化主要来源于以下⼏种情况。
① save m n
save m
n 是指在 m 秒内，如果有 n 个键发⽣改变，则⾃动触发持久化。参数 m 和 n 可以在 Redis 的配置⽂件中找到，例如，
save601 则表明在 60 秒内，⾄少有⼀个键发⽣改变，就会触发 RDB 持久化。⾃动触发持久化，本质是 Redis 通过判断，如果满⾜设置的触发条件，⾃动执⾏⼀次 bgsave 命令。注意：当设置多个 save m n 命令时，满⾜任意⼀个条件都会触发持久化。例如，我们设置了以下两个 save m n 命令：
save 60 10
save 600 1
当 60s 内如果有 10 次 Redis 键值发⽣改变，就会触发持久化；如果 60s 内 Redis 的键值改变次数少于 10 次，那么 Redis 就会判断600s 内，Redis 的键值是否⾄少被修改了⼀次，如果满⾜则会触发持久化。
② flushall
flushall 命令⽤于清空 Redis 数据库，在⽣产环境下⼀定慎⽤，当 Redis 执⾏了 flushall 命令之后，则会触发⾃动持久化，把 RDB⽂件清空.
③ 主从同步触发
在 Redis 主从复制中，当从节点执⾏全量复制操作时，主节点会执⾏ bgsave 命令，并将 RDB ⽂件发送给从节点，该过程会⾃动触发 Redis 持久化。

<font color="red">配置说明</font>
>  bgsave 失败之后，是否停⽌持久化数据到磁盘，yes 表⽰停⽌持久化，no 表⽰忽略错误继续写⽂件。

stop-writes-on-bgsave-error yes

>  RDB ⽂件压缩

rdbcompression yes

>  写⼊⽂件和读取⽂件时是否开启 RDB ⽂件检查，检查是否有⽆损坏，如果在启动是检查发现损坏，则停⽌启动。

rdbchecksum yes

>  RDB ⽂件名

dbfilename
dump
.rdb
> \# RDB ⽂件压缩

rdbcompression yes
> \# 写⼊⽂件和读取⽂件时是否开启 RDB ⽂件检查，检查是否有⽆损坏，如果在启动是检查发现损坏，则停⽌启动。

rdbchecksum yes

其中⽐较重要的参数如下列表：
① save 参数
它是⽤来配置触发 RDB 持久化条件的参数，满⾜保存条件时将会把数据持久化到硬盘。默认配置说明如下：
save 900 1：表⽰ 900 秒内如果⾄少有 1 个 key 值变化，则把数据持久化到硬盘；
save 300 10：表⽰ 300 秒内如果⾄少有 10 个 key 值变化，则把数据持久化到硬盘；
save 60 10000：表⽰ 60 秒内如果⾄少有 10000 个 key 值变化，则把数据持久化到硬盘。
② rdbcompression 参数
它的默认值是 yes 表⽰开启 RDB ⽂件压缩，Redis 会采⽤ LZF 算法进⾏压缩。如果不想消耗 CPU 性能来进⾏⽂件压缩的话，可以
设置为关闭此功能，这样的缺点是需要更多的磁盘空间来保存⽂件。
③ rdbchecksum 参数
它的默认值为 yes 表⽰写⼊⽂件和读取⽂件时是否开启 RDB ⽂件检查，检查是否有⽆损坏，如果在启动是检查发现损坏，则停⽌启动。

<font color="red">5.配置查询</font>
Redis 中可以使⽤命令查询当前配置参数。查询命令的格式为： configgetxxx ，例如，想要获取 RDB ⽂件的存储名称设置，可以
使⽤ configgetdbfilename ，执⾏效果如下图所⽰：
![在这里插入图片描述](https://img-blog.csdnimg.cn/58e46297349c4d5a8b35bfa5c7edf664.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

<font color="red">6.配置设置</font>
设置 RDB 的配置，可以通过以下两种⽅式：

⼿动修改 Redis 配置⽂件；
使⽤命令⾏设置，例如，使⽤ configsetdir"/usr/data" 就是⽤于修改 RDB 的存储⽬录。

注意：⼿动修改 Redis 配置⽂件的⽅式是全局⽣效的，即重启 Redis 服务器设置参数也不会丢失，⽽使⽤命令修改的⽅式，在Redis 重启之后就会丢失。但⼿动修改 Redis 配置⽂件，想要⽴即⽣效需要重启 Redis 服务器，⽽命令的⽅式则不需要重启 Redis
服务器。
<font color="red">7.RDB ⽂件恢复</font>
当 Redis 服务器启动时，如果 Redis 根⽬录存在 RDB ⽂件 dump.rdb，Redis 就会⾃动加载 RDB ⽂件恢复持久化数据。如果根⽬录没有 dump.rdb ⽂件，请先将 dump.rdb ⽂件移动到 Redis 的根⽬录。
验证 RDB ⽂件是否被加载
Redis 在启动时有⽇志信息，会显⽰是否加载了 RDB ⽂件，我们执⾏ Redis 启动命令：
redis.conf src/redis-server ，如下图所
⽰：
![在这里插入图片描述](https://img-blog.csdnimg.cn/d72b192c211b4e0aa5fb5cf09714cb94.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
<font color="red">8.RDB 优缺点</font>
1）RDB 优点
**RDB 的内容为⼆进制的数据，占⽤内存更⼩**，更紧凑，更适合做为备份⽂件；
RDB 对灾难恢复⾮常有⽤，它是⼀个紧凑的⽂件，可以更快的传输到远程服务器进⾏ Redis 服务恢复；
RDB 可以更⼤程度的提⾼ Redis 的运⾏速度，因为每次持久化时 Redis 主进程都会 fork() ⼀个⼦进程，进⾏数据持久化到磁盘，Redis 主进程并不会执⾏磁盘 I/O 等操作；**与 AOF 格式的⽂件相⽐，RDB ⽂件可以更快的重启。**

2）RDB 缺点
因为 RDB 只能保存某个时间间隔的数据，如果中途 Redis 服务被意外终⽌了，则会丢失⼀段时间内的 Redis 数据；

RDB 需要经常 fork() 才能使⽤⼦进程将其持久化在磁盘上。**如果数据集很⼤，fork() 可能很耗时，并且如果数据集很⼤且CPU 性能不佳，则可能导致 Redis 停⽌为客⼾端服务⼏毫秒甚⾄⼀秒钟。**

9.禁⽤持久化
禁⽤持久化可以提⾼ Redis 的执⾏效率，如果对数据丢失不敏感的情况下，可以在连接客⼾端的情况下，执⾏ 

```java
config set save "" 
```

命令即可禁⽤ Redis 的持久化



### 在生成 RDB 期间，Redis 可以同时处理写请求么？

可以的，Redis 使用操作系统的多进程**写时复制技术 COW(Copy On Write)** 来实现快照持久化，保证数据一致性。

Redis 在持久化时会调用 glibc 的函数`fork`产生一个子进程，快照持久化完全交给子进程来处理，父进程继续处理客户端请求。

当主线程执行写指令修改数据的时候，这个数据就会复制一份副本， `bgsave` 子进程读取这个副本数据写到 RDB 文件。

这既保证了快照的完整性，也允许主线程同时对数据进行修改，避免了对正常业务的影响。

![图片](https://mmbiz.qpic.cn/mmbiz_png/b95QHPkcOMCRsxNm7XcM6NTqUcVCRkeaSUZEHOTib16G5MSwet4MfoXuXv3S6DYLpZRXhdOfib3Mib7Nloc4f145g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)





### RDB AOF对比

Redis是一个支持持久化的内存数据库，通过持久化机制把内存中的数据同步到硬盘文件来保证数据持久化。当Redis重启后通过把硬盘文件重新加载到内存，就能达到恢复数据的目的。 实现：单独创建fork()一个子进程，将当前父进程的数据库数据复制到子进程的内存中，然后由子进程写入到临时文件中，持久化的过程结束了，再用这个临时文件替换上次的快照文件，然后子进程退出，内存释放。

快照持久化
快照持久化，顾名思义，就是通过拍摄快照的方式实现数据的持久化，redis 可以在某个时间点上对内存中的数据创建一个副本文件，副本文件中的数据在 redis 重启时会被自动加载，我们也可以将副本文件拷贝到其他地方一样可以使用。

持久化就是把内存的数据写到磁盘中去，防止服务宕机了内存数据丢失。Redis 提供了两种持久化方式：RDB（默认） 和 AOF。

**RDB**

RDB 是 Redis DataBase 的缩写。**按照一定的时间周期策略把内存的数据以快照的形式保存到硬盘的二进制文件**。即 Snapshot 快照存储，对应产生的数据文件为 dump.rdb，通过配置文件中的 save 参数来定义快照的周期。核心函数：rdbSave（生成 RDB 文件）和 rdbLoad（从文件加载内存）两个函数。

![图片](https://mmbiz.qpic.cn/mmbiz_png/b95QHPkcOMCRsxNm7XcM6NTqUcVCRkeadvpUX6icCKKHgLPQ61y8QkBFcX4KeX4Og7Q2BdrvTbE43WvCF8uLqUA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**AOF**

AOF 是 Append-only file 的缩写。Redis会将每一个收到的写命令都通过 Write 函数追加到文件最后，类似于 MySQL 的 binlog。**当 Redis 重启是会通过重新执行文件中保存的写命令来在内存中重建整个数据库的内容**。每当执行服务器（定时）任务或者函数时，flushAppendOnlyFile 函数都会被调用， 这个函数执行以下两个工作：

- WRITE：根据条件，将 aof_buf 中的缓存写入到 AOF 文件；
- SAVE：根据条件，调用 fsync 或 fdatasync 函数，将 AOF 文件保存到磁盘中。

![图片](https://mmbiz.qpic.cn/mmbiz_png/b95QHPkcOMCRsxNm7XcM6NTqUcVCRkea2PpsLvvBKbqjJrLZo4k87SBHCMH3jiaZCqOxhyn39CxjbXFIV0c2hZg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

说到日志，我们比较熟悉的是数据库的写前日志（Write Ahead Log, WAL），也就是说，**在实际写数据前，先把修改的数据记到日志文件中，以便故障时进行恢复。**不过，AOF 日志正好相反，它是写后日志，***\*“写后”的意思是 Redis 是先执行命令，把数据写入内存，然后才记录日志，\****如下图所示： 

![image-20220308211358179](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308211358179.png)

但是，为了避免额外的检查开销，Redis 在向 AOF 里面记录日志的时候，***\*并不会先去对这些命令进行语法检查。所以，如果先记日志再执行命令的话，日志中就有可能记录了错误的命令，Redis 在使用日志恢复数据时，就可能会出错。\****

***\*而写后日志这种方式，就是先让系统执行命令，只有命令能执行成功，才会被记录到日志中，否则，系统就会直接向客户端报错。\****所以，Redis 使用写后日志这一方式的一大好处是，可以避免出现记录错误命令的情况。

除此之外，AOF 还有一个好处：***\*它是在命令执行后才记录日志，所以不会阻塞当前的写操作。\****

不过，AOF 也有两个潜在的风险。

1. ==首先，如果刚执行完一个命令，还没有来得及记日志就宕机了，那么这个命令和相应的数据就有丢失的风险==。如果此时 Redis 是用作缓存，还可以从后端数据库重新读入数据进行恢复，但是，如果 Redis 是直接用作数据库的话，此时，因为命令没有记入日志，所以就无法用日志进行恢复了。
2. 其次，***\*AOF 虽然避免了对当前命令的阻塞，但可能会给下一个操作带来阻塞风险。\****这是因为，AOF 日志也是在主线程中执行的，如果在把日志文件写入磁盘时，磁盘写压力大，就会导致写盘很慢，进而导致后续的操作也无法执行了。

仔细分析的话，你就会发现，这两个风险都是和 AOF 写回磁盘的时机相关的。这也就意味着，如果我们能够控制一个写命令执行完后 AOF 日志写回磁盘的时机，这两个风险就解除了。

我们以 Redis 收到“set testkey testvalue”命令后记录的日志为例，看看 AOF 日志的内容。其中，“*3”表示当前命令有三个部分，每部分都是由“$+数字”开头，后面紧跟着具体的命令、键或值。这里，“数字”表示这部分中的命令、键或值一共有多少字节。例如，“$3 set”表示这部分有 3 个字节，也就是“set”命令。

![image-20220308211613527](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308211613527.png)

其实，对于这个问题，AOF 机制给我们提供了三个选择，也就是 AOF 配置项 appendfsync 的三个可选值。

- Always，同步写回：每个写命令执行完，立马同步地将日志写回磁盘
- Everysec，每秒写回：每个写命令执行完，只是先把日志写到 AOF 文件的内存缓冲区，每隔一秒把缓冲区中的内容写入磁盘。
- No，操作系统控制的写回：每个写命令执行完，只是先把日志写到 AOF 文件的内存缓冲区，由操作系统决定何时将缓冲区内容写回磁盘。

针对避免主线程阻塞和减少数据丢失问题，这三种写回策略都无法做到两全其美。我们来分析下其中的原因。 

- “同步写回”可以做到基本不丢数据，但是它在每一个写命令后都有一个慢速的落盘操作，**不可避免地会影响主线程性能**； 
- 虽然“操作系统控制的写回”在写完缓冲区后，就可以继续执行后续的命令，但是落盘的时机已经不在 Redis 手中了，只要 AOF 记录没有写回磁盘，一旦宕机对应的数据就丢失了； 
- “每秒写回”采用一秒写回一次的频率，避免了“同步写回”的性能开销，虽然减少了对系统性能的影响，但是如果发生宕机，上一秒内未落盘的命令操作仍然会丢失。所以，这只能算是，在避免影响主线程性能和避免数据丢失两者间取了个折中。

**RDB 和 AOF 的区别：**

1. AOF 文件比 RDB 更新频率高，优先使用 AOF 还原数据；
2. AOF比 RDB 更安全也更大；
3. RDB 性能比 AOF 好；
4. 如果两个都配了优先加载 AOF。



![image-20220326180738639](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220326180738639.png)

![image-20220326180900167](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220326180900167.png)

![image-20220326180917564](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220326180917564.png)

　AOF的整个流程大体来看可以分为两步，一步是命令的实时写入（如果是 `appendfsync everysec` 配置，会有1s损耗），第二步是对aof文件的重写。

　　对于增量追加到文件这一步主要的流程是：命令写入=》追加到aof_buf =》同步到aof磁盘。

　　**那么这里为什么要先写入buf在同步到磁盘呢？**如果实时写入磁盘会带来非常高的磁盘IO，影响整体性能。

　　aof重写是为了减少aof文件的大小，可以手动或者自动触发，关于自动触发的规则请看上面配置部分。

　　fork的操作也是发生在重写这一步，也是这里会对主进程产生阻塞。

　　手动触发： `bgrewriteaof`，自动触发 就是根据配置规则来触发，当然自动触发的整体时间还跟Redis的定时任务频率有关系。

![image-20220326182003416](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220326182003416.png)

1、在重写期间，由于主进程依然在响应命令，为了保证最终备份的完整性；因此它依然会写入旧的AOF file中，如果重写失败，能够保证数据不丢失。

2、为了把重写期间响应的写入信息也写入到新的文件中，因此也会为子进程保留一个buf，防止新写的file丢失数据。

3、重写是直接把当前内存的数据生成对应命令，并不需要读取老的AOF文件进行分析、命令合并。

4、AOF文件直接采用的文本协议，主要是兼容性好、追加方便、可读性高可认为修改修复。

**无论是 RDB 还是 AOF 都是先写入一个临时文件，然后通过 `rename` 完成文件的替换工作。**

如何配置快照持久化
redis中的快照持久化默认是开启的，redis.conf中相关配置主要有如下几项：
save 900 1 

save 300 10 

save 60 10000 

stop-writes-on-bgsave-error yes 

rdbcompression yes 



4dbfilename dump.rdb

快照持久化的缺点
快照持久化有一些缺点，比如 save 命令会发生阻塞，bgsave 虽然不会发生阻塞，但是 fork 一个子进程又要耗费资源（在fork的一瞬间会阻塞主进程），在一些极端情况下，fork 子进程的时间甚至超过数据备份的时间。定期的持久化也会让我们存在数据丢失的风险

![image-20210909094027552](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210909094027552.png)

![image-20210909094042327](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210909094042327.png)

### 混合持久化

重启 Redis 时，**我们很少使用 rdb 来恢复内存状态，因为会丢失大量数据。**

如果使用 AOF 日志重放，性能则相对 rdb 来说要慢很多，这样在 Redis 实例很大的情况下，启动的时候需要花费很长的时间。

Redis 4.0 为了解决这个问题，带来了一个新的持久化选项——混合持久化。

混合持久化同样也是通过`bgrewriteaof`完成的，不同的是当开启混合持久化时，fork出的子进程先将共享的内存副本**全量**的以RDB方式写入aof文件，然后在将`aof_rewrite_buf`重写缓冲区的增量命令以AOF方式写入到文件，写入完成后通知主进程更新统计信息，并将新的含有RDB格式和AOF格式的AOF文件替换旧的的AOF文件。简单的说：新的AOF文件前半段是RDB格式的全量数据后半段是AOF格式的增量数据，

**系统根据策略触发aof rewrite时，fork 一个子线程将内存数据以RDB二进制格式写入AOF文件头部，那些在重写操作执行之后执行的 Redis 命令，则以AOF持久化的方式追加到AOF文件的末尾。**(即：触发aof rewrite时会把当时的数据以rdb形式写到aof文件中，在这个rewrite之后执行的修改操作才会以aof形式写入)

如下图：

![image-20220308213919159](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308213919159.png)

混合持久化配置：

```shell
aof-use-rdb-preamble yes  # yes：开启，no：关闭
```

------

- **RDB和AOF，应该用哪一个？**

如果你可以承受数分钟以内的数据丢失， 那么你可以只使用 RDB 持久化。

有很多用户都只使用 AOF 持久化， 但我们并不推荐这种方式： 因为定时生成 RDB 快照（snapshot）非常便于进行数据库备份， 并且 RDB 恢复数据集的速度也要比 AOF 恢复的速度要快。如果只用AOF持久化，数据量很大时，在redis启动的时候需要花费大量的时间。

如果你非常关心你的数据，建议使用 redis 4.0 以后的`混合持久化特性。`

4.0版本的混合持久化功能 **默认关闭**，5.0 版本 **默认开启**。

![image-20220213013937731](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220213013937731.png)

简单来说，AOF 重写机制就是在重写时，Redis 根据数据库的现状创建一个新的 AOF 文件，也就是说，读取数据库中的所有键值对，然后对每一个键值对用一条命令记录它的写入。比如说，当读取了键值对“testkey”: “testvalue”之后，重写机制会记录 set testkey testvalue 这条命令。这样，当需要恢复时，可以重新执行该命令，实现“testkey”: “testvalue”的写入。

为什么重写机制可以把日志文件变小呢? 实际上，重写机制具有“多变一”功能。所谓的“多变一”，也就是说，旧日志文件中的多条命令，在重写后的新日志中变成了一条命令。

我们知道，***\*AOF 文件是以追加的方式，逐一记录接收到的写命令的。\**\**当一个键值对被多条写命令反复修改时，AOF 文件会记录相应的多条命令。但是，在重写的时候，是根据这个键值对当前的最新状态，为它生成对应的写入命令。这样一来，一个键值对在重写日志中只用一条命令就行了\****，而且，在日志恢复时，只用执行这条命令，就可以直接完成这个键值对的写入了。

![image-20220308212721579](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308212721579.png)

和 AOF 日志由主线程写回不同，重写过程是由后台线程 bgrewriteaof 来完成的，这也是为了避免阻塞主线程，导致数据库性能下降。

我把重写的过程总结为“一个拷贝，两处日志”。

“一个拷贝”就是指，每次执行重写时，主线程 fork 出后台的 bgrewriteaof 子进程。此时，fork 会把主线程的内存拷贝一份给 bgrewriteaof 子进程，这里面就包含了数据库的最新数据。然后，bgrewriteaof 子进程就可以在不影响主线程的情况下，逐一把拷贝的数据写成操作，记入重写日志。

“两处日志”又是什么呢？

因为主线程未阻塞，仍然可以处理新来的操作。此时，如果有写操作，第一处日志就是指正在使用的 AOF 日志，Redis 会把这个操作写到它的缓冲区。这样一来，即使宕机了，这个 AOF 日志的操作仍然是齐全的，可以用于恢复。

而第二处日志，就是指新的 AOF 重写日志。这个操作也会被写到重写日志的缓冲区。这样，重写日志也不会丢失最新的操作。***\*等到拷贝数据的所有操作记录重写完成后，重写日志记录的这些最新操作也会写入新的 AOF 文件\****，以保证数据库最新状态的记录。此时，我们就可以用新的 AOF 文件替代旧文件了。

![image-20220308213206460](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308213206460.png)

AOF文件重写过程与RDB快照bgsave工作过程有点相似，都是通过fork子进程，由子进程完成相应的操作，同样的在fork子进程简短的时间内，redis是阻塞的。

（1）开始`bgrewriteaof`，判断当前有没有`bgsave命令(RDB持久化)/bgrewriteaof`在执行，倘若有，则这些命令执行完成以后在执行。

（2）主进程`fork`出子进程，在这一个短暂的时间内，redis是阻塞的。

（3）主进程`fork`完子进程继续接受客户端请求。此时，客户端的写请求不仅仅写入`aof_buf`缓冲区，还写入`aof_rewrite_buf`重写缓冲区。一方面是写入`aof_buf`缓冲区并根据appendfsync策略同步到磁盘，保证原有AOF文件完整和正确。另一方面写入`aof_rewrite_buf`重写缓冲区，保存fork之后的客户端的写请求，防止新AOF文件生成期间丢失这部分数据。

（4.1）子进程写完新的AOF文件后，向主进程发信号，父进程更新统计信息。

（4.2）主进程把`aof_rewrite_buf`中的数据写入到新的AOF文件。

（5.）使用新的AOF文件覆盖旧的AOF文件，标志AOF重写完成。

如何实现数据尽可能少丢失又能兼顾性能呢？

重启 Redis 时，我们很少使用 rdb 来恢复内存状态，因为会丢失大量数据。我们通常使用 AOF 日志重写，但是重写 AOF 日志性能相对 rdb 来说要慢很多，这样在 Redis 实例很大的情况下，启动需要花费很长的时间。

Redis 4.0 为了解决这个问题，带来了一个新的持久化选项——**混合持久化**。将 rdb 文件的内容和增量的 AOF 日志文件存在一起。这里的 AOF 日志不再是全量的日志，而是**自持久化开始到持久化结束的这段时间发生的增量 AOF 日志**，通常这部分 AOF 日志很小。

![image-20220317194724179](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220317194724179.png)

于是在 Redis 重启的时候，可以先加载 rdb 的内容，然后再重放增量 AOF 日志就可以完全替代之前的 AOF 全量文件重放，重启效率因此得到大幅提升。

因为RDB持久化 **无法实时保存**数据，数据库或者主机down机时，会丢失数据。AOF持久化虽然可以提高数据的安全性，但是在恢复数据时 **需要大量时间**。因此Redis 4.0 推出RDB-AOF混合持久化。

> 持久化时，可以根据AOF的落盘策略实时刷盘。
>
> 恢复时先加载AOF文件中的RDB部分，然后再加载AOF文件部分

于是**在 Redis 重启的时候，可以先加载 rdb 的内容，然后再重放增量 AOF 日志就可以完全替代之前的 AOF 全量文件重放，重启效率因此大幅得到提升**。

## 什么是 Cluster 集群？

Redis 集群是一种分布式数据库方案，集群通过分片（sharding）来进行数据管理（「分治思想」的一种实践），并提供复制和故障转移功能。

将数据划分为 16384 的 slots，每个节点负责一部分槽位。槽位的信息存储于每个节点中。

它是去中心化的，如图所示，该集群由三个 Redis 节点组成，每个节点负责整个集群的一部分数据，每个节点负责的数据多少可能不一样。

![图片](https://mmbiz.qpic.cn/mmbiz_png/b95QHPkcOMCRsxNm7XcM6NTqUcVCRkeaic4q3VFqCG5bBKDw3RQ39Th8ic8oQS9gKbsiatd4l9OXjiceo7NYL2KoXw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

三个节点相互连接组成一个对等的集群，它们之间通过 `Gossip`协议相互交互集群信息，最后每个节点都保存着其他节点的 slots 分配情况。

**使用 Redis Cluster 集群，主要解决了大数据量存储导致的各种慢问题**

### 哈希槽又是如何映射到 Redis 实例上呢？

1. 根据键值对的 key，使用 CRC16 算法，计算出一个 16 bit 的值；
2. 将 16 bit 的值对 16384 执行取模，得到 0 ～ 16383 的数表示 key 对应的哈希槽。
3. 根据该槽信息定位到对应的实例。

键值对数据、哈希槽、Redis 实例之间的映射关系如下：

![图片](https://mmbiz.qpic.cn/mmbiz_png/b95QHPkcOMCRsxNm7XcM6NTqUcVCRkeayEFa68Kre0bRLvBxPBUYVPoD65LhBb4gSic54zh1NibBUcHujTKaju4w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



### Cluster 如何实现故障转移？

Redis 集群节点采用 `Gossip` 协议来广播自己的状态以及自己对整个集群认知的改变。比如一个节点发现某个节点失联了 (PFail)，它会将这条信息向整个集群广播，其它节点也就可以收到这点失联信息。

如果一个节点收到了某个节点失联的数量 (PFail Count) 已经达到了集群的大多数，就可以标记该节点为确定下线状态 (Fail)，然后向整个集群广播，强迫其它节点也接收该节点已经下线的事实，并立即对该失联节点进行主从切换。







## 10、Redis 与其他 key-value 存储有什么不同？

Redis 有着更为复杂的数据结构并且提供对他们的原子性操作，这是一个不同于其他数据库的进化路径。Redis 的数据类型都是基于基本数据结构的同时对程序员透明，无需进行额外的抽象。
Redis 运行在内存中但是可以持久化到磁盘，所以在对不同数据集进行高速读写时需要权衡内存，因为数据量不能大于硬件内存。在内存数据库方面的另一个优点是， 相比在磁盘上相同的复杂的数据结构，在内存中操作起来非常简单，这样 Redis 可以做很多内部复杂性很强的事情。 同时，在磁盘格式方面他们是紧凑的以追加的方式产生的，因为他们并不需要进行随机访问。
41、Redis 的内存占用情况怎么样？
给你举个例子： 100 万个键值对（键是 0 到 999999 值是字符串“hello world”）在我的 32位的 Mac 笔记本上 用了 100MB。同样的数据放到一个 key 里只需要 16MB， 这是因为键值
有一个很大的开销。 在 Memcached 上执行也是类似的结果，但是相对 Redis 的开销要小一点点，因为 Redis 会记录类型信息引用计数等等。
当然，大键值对时两者的比例要好很多。
64 位的系统比 32 位的需要更多的内存开销，尤其是键值对都较小时，这是因为 64 位的系统里指针占用了 8 个字节。 但是，当然，64 位系统支持更大的内存，所以为了运行大型的Redis 服务器或多或少的需要使用 64 位的系统。

## 11、都有哪些办法可以降低 Redis 的内存使用情况呢？

如果你使用的是 32 位的 Redis 实例，可以好好利用 Hash,list,sorted set,set 等集合类型数据，因为通常情况下很多小的 Key-Value 可以用更紧凑的方式存放到一起。

6 Redis的内存占用情况怎么样?
给你举个例子:100万个键值对(键是0到999999值是字符串helo
world"")在我的32位的Mac笔记本上用了100MB。同样的数据放到一个key里只需要16MB,这是因为键值有一个很大的销。在 Memcached上执行也是类似的结果,但是相对 Redis的开销要小点点,因为 Redis会记录类型信息引用计数等等。



## redis分区

Redis是单线程的，如何提高多核CPU的利用率？

可以在同一个服务器部署多个Redis的实例，并把他们当作不同的服务器来使用，在某些时候，无论如何一个服务器是不够的， 所以，如果你想使用多个CPU，你可以考虑一下分片（shard）。

 **为什么要做Redis分区？**

分区可以让Redis管理更大的内存，Redis将可以使用所有机器的内存。如果没有分区，你最多只能使用一台机器的内存。分区使Redis的计算能力通过简单地增加计算机得到成倍提升，Redis的网络带宽也会随着计算机和网卡的增加而成倍增长。



Redis 有两种类型分区。
**最简单的分区方式是按范围分区**，就是映射一定范围的对象到特定的 Redis 实例。
比如，ID 从 0 到 10000 的用户会保存到实例 R0，ID 从 10001 到 20000 的用户会保存到 R1，以此类推。
这种方式是可行的，并且在实际中使用，不足就是要有一个区间范围到实例的映射表。这个表要被管理，同时还需要各 种对象的映射表，通常对 Redis 来说并非是好的方法。
**哈希分区**：另外一种分区方法是 hash 分区。这对任何 key 都适用，也无需是object_name: 这种形式，像下面描述的一样简单：

用一个 hash 函数将 key 转换为一个数字，比如使用 crc32 hash 函数。对 keyfoobar 执行 crc32(foobar) 会输出类似 93024922 的整数。
对这个整数取模，将其转化为 0-3 之间的数字，就可以将这个整数映射到 4 个Redis 实例中的一个了。93024922 % 4 = 2，就是说 key foobar 应该被存到 R2实例中。注意：取模操作是取除的余数，通常在多种编程语言中用 % 操作符实现。实际上，上面的集群模式还存在两个问题：

1. 扩容问题：
    因为使用了一致性哈希进行分片，那么不同的 key 分布到不同的 Redis-
    Server 上，当我们需要扩容时，需要增加机器到分片列表中，这时候会使得同样的 key 算出来落到跟原来不同的机器上，这样如果要取某一个值，会出现取不到的情况，对于这种情况，Redis 的作者提出了一种名为 ==Pre-Sharding== 的方式：
    
    
    

Pre-Sharding 方法是将每一个台物理机上，运行多个不同端口的 Redis 实例，假如有三个物理机，每个物理机运行三个 Redis 实例，那么我们的分片列表中实际有 9 个 Redis 实例，当我们需要扩容时，增加一台物理机，步骤如下：

📣在新的物理机上运行 Redis-Server；

📣该 Redis-Server 从属于 (slaveof) 分片列表中的某一 Redis-Server（假设叫 RedisA）;

📣等主从复制 (Replication) 完成后，将客户端分片列表中 RedisA 的IP 和端口改为新物理机上 Redis-Server 的 IP 和端口；



📣停止 RedisA。

**这样相当于将某一 Redis-Server 转移到了一台新机器上。Prd-Sharding 实际上是一种在线扩容的办法，但还是很依赖 Redis 本身的复制功能的**，如果主库快照数据文件过大，这个复制的过程也会很久，同时会给主库带来压力。
所以做这个拆分的过程最好选择为业务访问低峰时段进行。

2.单点故障问题：
还是用到 Redis 主从复制的功能，两台物理主机上分别都运行有 Redis-
Server，其中一个 Redis-Server 是另一个的从库，采用双机热备技术，客户端通过虚拟 IP 访问主库的物理 IP，当主库宕机时，切换到从库的物理 IP。
只是事后修复主库时，应该将之前的从库改为主库（使用命令 slaveof no one），主库变为其从库（使命令 slaveof IP PORT），这样才能保证修复期间新增数据的一致性。

另一种回答：redis分区实现方案

- 客户端分区 就是在客户端就已经决定数据会被存储到哪个redis节点或者从哪个redis节点读取。大多数客户端已经实现了客户端分区。
- 代理分区 意味着客户端将请求发送给代理，然后代理决定去哪个节点写数据或者读数据。代理根据分区规则决定请求哪些Redis实例，然后根据Redis的响应结果返回给客户端。redis和memcached的一种代理实现就是Twemproxy
- 查询路由(Query routing) 的意思是客户端随机地请求任意一个redis实例，然后由Redis将请求转发给正确的Redis节点。Redis Cluster实现了一种混合形式的查询路由，但并不是直接将请求从一个redis节点转发到另一个redis节点，而是在客户端的帮助下直接redirected到正确的redis节点。





## 13Memcached服务特点及工作原理是什么?

a、完全基于内存缓存的
b、节点之间相互独立
C、C/S模式架构,C语言编写,总共2000行代码。
d、异步I/O模型,使用 libevent 1作为事件通知机制。
e、被缓存的数据以 key/value键值对形式存在的。
f、全部数据存放于内存中,无持久性存储的设计,重启服务器,内存里的数据会丢失
g、当内存中缓存的数据容量达到启动时设定的内值时,就自动使用LRU算法删除过期的缓存数据。
h、可以对存储的数据设置过期时间,这样过期后的数据自动被清除,服务本身不会监控过期,而是在访问的时候查看key的时间戳判断是否过期。
j、 memcache会对设定的内存进行分块,再把块分组,然后再提供服务。



## 14.Pre-Sharding 



因为使用了一致性哈稀进行分片，那么不同的 key 分布到不同的 Redis-Server 上，当我们需要扩容时，需要增加机器到分片列表中，这时候会使得同样的 key 算出来落到跟原来不同的机器上，这样如果要取某一个值，会出现取不到的情况，对于这种情况，Redis 的作者提出了一种名为 Pre-Sharding 的方式：

方法是将每一个台物理机上，运行多个不同端口的 Redis 实例，假如有三个物理机，每个物理机运行三个 Redis 实际，那么我们的分片列表中实际有 9 个 Redis 实例，当我们需要扩容时，增加一台物理机，步骤如下：
1.在新的物理机上运行 Redis-Server；
2.该 Redis-Server 从属于 (slaveof) 分片列表中的某一 Redis-Server（假设叫 RedisA）；

3.等主从复制 (Replication) 完成后，将客户端分片列表中 RedisA 的 IP 和端口改为新物理机上 Redis-Server 的 IP 和端口；
4.停止 RedisA。

这样相当于将某一 Redis-Server 转移到了一台新机器上。Prd-Sharding 实际上是一种在线扩容的办法，但还是很依赖 Redis 本身的复制功能的，如果主库快照数据文件过大，这个复制的过程也会很久，同时会给主库带来压力。所以做这个拆分的过程最好选择为业务访问低峰时段进行。

## 15.单点故障问题：

还是用到 Redis 主从复制的功能，两台物理主机上分别都运行有 Redis-Server，其中一个 Redis-Server 是另一个的从库，采用双机热备份技术，客户端通过虚拟 IP 访问主库的物理 IP，当主库宕机时，切换到从库的物理 IP。只是事后修复主库时，应该将之前的从库改为主库（使用命令 slave of no one），主库变为其从库（使命令 slave of IP PORT），这样才能保证修复期间新增数据的一致性。





## 16.redis和memcached什么区别？为什么高并发下有时单线程的redis比多线程的memcached效率要高？

mongodb 和 memcached 不是一个范畴内的东西。mongodb 是文档型的非关系型数据库，==其优势在于查询功能比较强大，能存储海量数据。==
和 memcached 更为接近的是 Redis。它们都是内存型数据库，数据保存在内存中，通过 tcp 直接存取，==优势是速度快，并发高，缺点是数据类型有限，查询功能不强，一般用作缓存。==

=>性能
Redis 和 memcache 差不多，要大于 mongodb。

=>操作的便利性
memcache 数据结构单一。
Redis 丰富一些，数据操作方面，Redis 更好一些，较少的网络 IO 次数(?)。
mongodb 支持丰富的数据表达，索引，最类似关系型数据库，支持的查询语言非常丰富。（mongodb 的查询语言非常丰富？）、数据支持类型 memcached所有的值均是简单的字符串，redis作为其替代者，支持更为丰富的数据类型 ，提供list，set，zset，hash等数据结构的存储

=>内存空间的大小和数据量的大小
Redis 在 2.0 版本后增加了自己的 VM 特性，突破物理内存的限制；可以对
key value 设置过期时间（类似 memcache）。
memcache 可以修改最大可用内存, 采用 LRU 算法。
mongoDB 适合大数据量的存储，依赖操作系统 VM 做内存管理，吃内存也
比较厉害，服务不要和别的服务在一起。

=>可用性（单点问题）
Redis 对于单点问题，依赖客户端来实现分布式读写；主从复制时，每次从节点重新连接主节点都要依赖整个快照, 无增量复制，因性能和效率问题，所以单点问题比较复杂；不支持自动 sharding, 需要依赖程序设定一致 hash 机制。一种替代方案是，不用 Redis 本身的复制机制，采用自己做主动复制
（多份存储），或者改成增量复制的方式（需要自己实现），一致性问题和性能的权衡。(???)
Memcache 本身没有数据冗余机制，也没必要；对于故障预防，采用依赖成
熟的 hash 或者环状的算法，解决单点故障引起的抖动问题。
mongoDB 支持 master-slave,replicaset（内部采用 paxos 选举算法，自
动故障恢复）,auto sharding 机制，对客户端屏蔽了故障转移和切分机制

=>数据一致性（事务支持）
Memcache 在并发场景下，用 cas 保证一致性。
Redis 事务支持比较弱，只能保证事务中的每个操作连续执行。
mongoDB 不支持事务。

=>应用场景
Redis：数据量较小的更性能操作和运算上。
memcache：用于在动态系统中减少数据库负载，提升性能; 做缓存，提高性
能（适合读多写少，对于数据量比较大，可以采用 sharding）。
MongoDB: 主要解决海量数据的访问效率问题。

=>value 值大小不同：Redis 最大可以达到 1gb；memcache 只有 1mb。

=>使用底层模型不同

 它们之间底层实现方式 以及与客户端之间通信的应用协议不一样。 Redis直接自己构建了VM 机制 ，因为一般的系统调用系统函数的话，会浪费一定的时间去移动和请求



1.mc可缓存图片和视频。rd支持除k/v更多的数据结构;
2.rd可以使用虚拟内存，rd可持久化和aof灾难恢复，rd通过主从支持数据备份;
3.rd可以做消息队列。
原因：mc多线程模型引入了缓存一致性和锁，加锁带来了性能损耗。

1、Redis 不仅仅支持简单的 k/v 类型的数据，同时还提供 list，set，zset，hash等数据结构的存储。而 memcache 只支持简单数据类型，需要客户端自己处理复杂对象
2、Redis 支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用（PS：持久化在 rdb、aof）。 

3、==由于 Memcache 没有持久化机制，因此宕机所有缓存数据失效。Redis 配置为持久化，宕机重启后，将自动加载宕机时刻的数据到缓存系统中。具有更好的灾备机制。==
4、Memcache 可以使用 Magent 在客户端进行一致性 hash 做分布式。Redis支持在服务器端做分布式（PS:Twemproxy/Codis/Redis-cluster 多种分布式实现方式）(?)
5、Memcached 的简单限制就是键（key）和 Value 的限制。最大键长为 250个字符。可以接受的储存数据不能超过 1MB（可修改配置文件变大），因为这是典型 slab 的最大值，不适合虚拟机使用。而 Redis 的 Key 长度支持到 512k。（？不懂）
6、Redis 使用的是单线程模型，保证了数据按顺序提交。Memcache 需要使用cas 保证数据一致性。CAS（Check and Set）是一个确保并发一致性的机制，属于“乐观锁”范畴；原理很简单：拿版本号，操作，对比版本号，如果一致就操作，不一致就放弃任何操作
7.cpu 利用。由于 Redis 只使用单核，而 Memcached 可以使用多核，所以平均每一个核上 Redis 在存储小数据时比 Memcached 性能更 高。而在 100k 以上的数据中，Memcached 性能要高于 Redis 。
7、memcache 内存管理：使用 Slab Allocation。原理相当简单，预先分配一系列大小固定的组，然后根据数据大小选择最合适的块存储。避免了内存碎片。（缺点：不能变长，浪费了一定空间）memcached 默认情况下下一个 slab 的最大值为前一个的 1.25 倍。
redis 内存管理：
Redis 通过定义一个数组来记录所有的内存分配情况，
Redis采用的是包装的 malloc/free，相较于 Memcached 的内存 管理方法来说，要简单很多。由于 malloc 首先以链表的方式搜索已管理的内存中可用的空间分配，导致内存中碎片较多









## 17.在选择缓存时，什么时候选择redis，什么时候选择memcached

选择redis的情况：
1、复杂数据结构，value的数据是哈希，列表，集合，有序集合等这种情况下，会选择redis, 因为memcache无法满足这些数据结构，最典型的的使用场景是，用户订单列表，用户消息，帖子评论等。
2、需要进行数据的持久化功能，但是注意，不要把redis当成数据库使用，如果redis挂了，内存能够快速恢复热数据，不会将压力瞬间压在数据库上，没有cache预热的过程。对于只读和数据一致性要求不高的场景可以采用持久化存储
3、高可用，redis支持集群，可以实现主动复制，读写分离，而对于memcache如果想要实现高可用，需要进行二次开发。
4、存储的内容比较大，memcache存储的value最大为1M。
选择memcache的场景：
1、纯KV,数据量非常大的业务，使用memcache更合适，原因是，
a)memcache的内存分配采用的是预分配内存池的管理方式，能够省去内存分配的时间，redis是临时申请空间，可能导致碎片化。
b)虚拟内存使用，memcache将所有的数据存储在物理内存里，redis有自己的vm机制，理论上能够存储比物理内存更多的数据，当数据超量时，引发swap,把冷数据刷新到磁盘上，从这点上，数据量大时，memcache更快
c)网络模型，memcache使用非阻塞的IO复用模型，redis也是使用非阻塞的IO复用模型，但是redis还提供了一些非KV存储之外的排序，聚合功能，复杂的CPU计算，会阻塞整个IO调度，从这点上由于redis提供的功能较多，memcache更快些

d) 线程模型，memcache使用多线程，主线程监听，worker子线程接受请求，执行读写，这个过程可能存在锁冲突。redis使用的单线程，虽然无锁冲突，但是难以利用多核的特性提升吞吐量。



redis最大可以达到1GB，而memcache只有1MB



## 18.MySQL里有2000w数据，redis中只存20w的数据，如何保证redis中的数据都是热点数据

相关知识：redis 内存数据集大小上升到一定大小的时候，就会施行数据淘汰策略。redis 提供 6种数据淘汰策略：
voltile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰
volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰

volatile-random：从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰
allkeys-lru：从数据集（server.db[i].dict）中挑选最近最少使用的数据淘汰
allkeys-random：从数据集（server.db[i].dict）中任意选择数据淘汰
no-enviction（驱逐）：禁止驱逐数据



## 19.Redis的过期键的删除策略

我们都知道，Redis是key-value数据库，我们可以设置Redis中缓存的key的过期时间。Redis的过期策略就是指当Redis中缓存的key过期了，Redis如何处理。（当指定一个键一小时过期，那么过一小时后发生什么？）

过期策略通常有以下三种：

- 定时过期：每个设置过期时间的key都需要创建一个定时器，到过期时间就会立即清除。该策略可以立即清除过期的数据，对内存很友好；但是会占用大量的CPU资源去处理过期的数据，从而影响缓存的响应时间和吞吐量。
- 惰性过期：只有当访问一个key时（系统再次查这个key），才会判断该key是否已过期，过期则清除。该策略可以最大化地节省CPU资源，却对内存非常不友好。极端情况可能出现大量的过期key没有再次被访问，从而不会被清除，占用大量内存。
- 定期删除：每隔一定的时间，会扫描一定数量的数据库的expires字典中一定数量的key，并清除其中已过期的key。（redis默认每隔100ms随机抽取一些设置了过期时间的key检查是否过期 过期就删除）该策略是前两者的一个折中方案。通过调整定时扫描的时间间隔和每次扫描的限定耗时，可以在不同情况下使得CPU和内存资源达到最优的平衡效果。
  (expires字典会保存所有设置了过期时间的key的过期时间数据，其中，key是指向键空间中的某个键的指针，value是该键的毫秒精度的UNIX时间戳表示的过期时间。键空间是指该Redis集群中保存的所有键。)

Redis中同时使用了**惰性过期和定期过期**两种过期策略。

定期从设置过期时间的键中随机抽取删除—》惰性删除（获取某个键时判断是否过期）—》内存淘汰机制
一、redis 过期策略
　　redis 过期策略是：定期删除+惰性删除。

　　所谓定期删除，指的是 redis 默认是每隔 100ms 就随机抽取一些设置了过期时间的 key，检查其是否过期，如果过期就删除。

　　假设 redis 里放了 10w 个 key，都设置了过期时间，你每隔几百毫秒，就检查 10w 个 key，那 redis 基本上就死了，cpu 负载会很高的，消耗在你的检查过期 key 上了。注意，这里可不是每隔 100ms 就遍历所有的设置过期时间的 key，那样就是一场性能上的灾难。实际上 redis 是每隔 100ms 随机抽取一些 key 来检查和删除的。

　　但是问题是，定期删除可能会导致很多过期 key 到了时间并没有被删除掉，那咋整呢？所以就是惰性删除了。这就是说，在你获取某个 key 的时候，redis 会检查一下 ，这个 key 如果设置了过期时间那么是否过期了？如果过期了此时就会删除，不会给你返回任何东西。



　　但是实际上这还是有问题的，如果定期删除漏掉了很多过期 key，然后你也没及时去查，也就没走惰性删除，此时会怎么样？如果大量过期 key 堆积在内存里，导致 redis 内存块耗尽了，咋整？

　　答案是：走内存淘汰机制。

二、内存淘汰机制
redis 内存淘汰机制有以下几个：

noeviction: 当内存不足以容纳新写入数据时，新写入操作会报错，
allkeys-lru：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的 key（这个是最常用的）
allkeys-random：当内存不足以容纳新写入数据时，在键空间中，随机移除某个 key，
volatile-lru：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，移除最近最少使用的 key（这个一般不太合适）
volatile-random：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，随机移除某个 key。
volatile-ttl：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，有更早过期时间的 key 优先移除。 

## MySQL里有200w数据，redis中只存20w的数据，如何保证redis中的数据都是热点数据？

这个问题主要考察了以下几点内容：

1.Redis的内存淘汰策略。
2.Redis的最大内存设置。

思路：首先计算出20w数据所需的内存空间，设置最大内存，然后选择合适的内存淘汰策略。

内存淘汰机制：

![image-20211010194006786](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211010194006786.png)

![image-20211010194052610](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211010194052610.png)

注意，上面的策略中在4.0以后新增了两个：

volatile-lfu:从已经设置过期时间的数据集中挑选最不常使用的数据淘汰

allkeys-lfu:在所有键中挑选最不常使用的数据淘汰

这八种大体上可以分为4中，lru、lfu、random、ttl。

## 20.Hash 冲突怎么办？

Redis 通过**链式哈希**解决冲突：**也就是同一个 桶里面的元素使用链表保存**。但是当链表过长就会导致查找性能变差可能，所以 Redis 为了追求快，使用了两个全局哈希表。用于 rehash 操作，增加现有的哈希桶数量，减少哈希冲突。

开始默认使用 「hash 表 1 」保存键值对数据，「hash 表 2」 此刻没有分配空间。当数据越来越多触发 rehash 操作，则执行以下操作：

1. 给 「hash 表 2 」分配更大的空间；
2. 将 「hash 表 1 」的数据重新映射拷贝到 「hash 表 2」 中；
3. 释放 「hash 表 1」 的空间。

**值得注意的是，将 hash 表 1 的数据重新映射到 hash 表 2 的过程中并不是一次性的，这样会造成 Redis 阻塞，无法提供服务。**

而是采用了**渐进式 rehash**，每次处理客户端请求的时候，先从「 hash 表 1」 中第一个索引开始，将这个位置的 所有数据拷贝到 「hash 表 2」 中，就这样将 rehash 分散到多次请求过程中，避免耗时阻塞。







## 21.如何实现集群中的 session 共享存储？

Session 是运行在一台服务器上的，所有的访问都会到达我们的唯一服务器上，这样我们可以根据客户端传来的 sessionID，来获取 session，或在对应 Session不存在的情况下（session 生命周期到了/用户第一次登录），创建一个新的Session；但是，如果我们在集群环境下，假设我们有两台服务器 A，B，用户的请求会由 Nginx 服务器进行转发（别的方案也是同理），用户登录时，Nginx将请求转发至服务器 A 上，A 创建了新的 session，并将 SessionID 返回给客户端，用户在浏览其他页面时，客户端验证登录状态，Nginx 将请求转发至服务器B，由于 B 上并没有对应客户端发来 sessionId 的 session，所以会重新创建一个新的 session，并且再将这个新的 sessionID 返回给客户端，这样，我们可以想象一下，用户每一次操作都有 1/2 的概率进行再次的登录，这样不仅对用户体验特别差，还会让服务器上的 session 激增，加大服务器的运行压力。
为了解决集群环境下的 seesion 共享问题，共有 4 种解决方案：
1.粘性 session
粘性 session 是指 Ngnix 每次都将同一用户的所有请求转发至同一台服务器上，即将用户与服务器绑定。
2.服务器 session 复制
即每次 session 发生变化时，创建或者修改，就广播给所有集群中的服务器，使所有的服务器上的 session 相同。
3.session 共享
缓存 session，使用 redis， memcached。
4.session 持久化
将 session 存储至数据库中，像操作数据一样操作 session。



## 如何解决 Redis 的并发竞争 Key 问题

所谓 Redis 的并发竞争 Key 的问题也就是多个系统同时对一个 key 进行操作，但是最后执行的顺序和我们期望的顺序不同，这样也就导致了结果的不同！

推荐一种方案：分布式锁（zookeeper 和 redis 都可以实现分布式锁）。（如果不存在 Redis 的并发竞争 Key 问题，不要使用分布式锁，这样会影响性能）

基于zookeeper临时有序节点可以实现的分布式锁。大致思想为：每个客户端对某个方法加锁时，在zookeeper上的与该方法对应的指定节点的目录下，生成一个唯一的瞬时有序节点。判断是否获取锁的方式很简单，只需要判断有序节点中序号最小的一个。当释放锁的时候，只需将这个瞬时节点删除即可。同时，其可以避免服务宕机导致的锁无法释放，而产生的死锁问题。完成业务流程后，删除对应的子节点释放锁。

在实践中，当然是从以可靠性为主。所以首推Zookeeper。

## 分布式Redis是前期做还是后期规模上来了再做好？为什么？

既然Redis是如此的轻量（单实例只使用1M内存），为防止以后的扩容，最好的办法就是一开始就启动较多实例。即便你只有一台服务器，**你也可以一开始就让Redis以分布式的方式运行，使用分区，在同一台服务器上启动多个实例。**

一开始就多设置几个Redis实例，例如32或者64个实例，对大多数用户来说这操作起来可能比较麻烦，但是从长久来看做这点牺牲是值得的。

这样的话，当你的数据不断增长，需要更多的Redis服务器时，你需要做的就是仅仅将Redis实例从一台服务迁移到另外一台服务器而已（而不用考虑重新分区的问题）。一旦你添加了另一台服务器，你需要将你一半的Redis实例从第一台机器迁移到第二台机器。

## Redis key 的过期时间和永久有效分别怎么设置？

EXPIRE 和 PERSIST 命令
expire 指令可以设置 key 的超时时间，单位秒。即在多少秒后过期。 返回1代表设置成功；返回 0 代表设置不成功，此时是因为key不存在导致的。
3 秒后使用 ttl 命令查询剩余的超时时间：
使用 persist 清除过期时间
127.0.0.1:6379> expire "key-aaa" 10
(integer) 1
127.0.0.1:6379> expire "key-not-exists" 10
(integer) 0
127.0.0.1:6379> ttl "key-aaa"
(integer) 7

127.0.0.1:6379> set "key-aaa" "value-bbb" EX 15
OK
127.0.0.1:6379> ttl "key-aaa"
(integer) 11
127.0.0.1:6379> persist "key-aaa"
(integer) 1
127.0.0.1:6379> ttl "key-aaa"
(integer) -1
127.0.0.1:6379> get "key-aaa"
"value-bbb"

persist 返回值：
1：成功清理过期时间。
0：key 不存在，或者没有设置过期时间



## Redis 如何做内存优化？

尽可能使用散列表（hashes），散列表（是说散列表里面存储的数少）使用的内存非常小，所以你应该尽可能的将你的数据模型抽象到一个散列表里面。
比如你的 web 系统中有一个用户对象，不要为这个用户的名称，姓氏，邮箱，密码设置单独的 key,而是应该把这个用户的所有信息存储到一张散列表里面。



## Redis 回收进程如何工作的？

一个客户端运行了新的命令，添加了新的数据。Redi 检查内存使用情况，如果大于 maxmemory 的限制, 则根据设定好的策略进行回收。一个新的命令被执行，等等。
所以我们不断地穿越内存限制的边界，通过不断达到边界然后不断地回收回到边界以下。
如果一个命令的结果导致大量内存被使用（例如很大的集合的交集保存到一个新的键），不用多久内存限制就会被这个内存使用量超越。









# geohash介绍

⾃Redis 3.2开始，Redis基于geohash和有序集合提供了地理位置相关功能。Redis Geo模块包含了以下6个命令：
▶GEOADD: 将给定的位置对象（纬度、经度、名字）添加到指定的key;
▶GEOPOS: 从key⾥⾯返回所有给定位置对象的位置（经度和纬度）;
▶GEODIST: 返回两个给定位置之间的距离;
▶GEOHASH: 返回⼀个或多个位置对象的Geohash表⽰;
▶GEORADIUS: 以给定的经纬度为中⼼，返回⽬标集合中与中⼼的距离不超过给定最⼤距离的所有位置对象;
▶GEORADIUSBYMEMBER: 以给定的位置对象为中⼼，返回与其距离不超过给定最⼤距离的所有位置对象。
其中，**组合使⽤GEOADD和GEORADIUS可实现“附近的⼈”中“增”和“查”的基本功能**。要实现微信中“附近的⼈”功能，可直接使⽤GEORADIUSBYMEMBER命令。其中“给定的位置对象”即为⽤⼾本⼈，搜索的对象为其他⽤⼾。不过本质
上，GEORADIUSBYMEMBER = GEOPOS + GEORADIUS，即先查找⽤⼾位置再通过该位置搜索附近满⾜位置相互距离条件的其他⽤⼾对象。
以下会从源码⻆度⼊⼿对GEOADD和GEORADIUS命令进⾏分析，剖析其算法原理

Redis geo操作中只包含了“增”和“查”的操作，并没有专⻔的“删除”命令。主要是因为Redis内部使⽤有序集合(zset)保存位置对象，可⽤zrem进⾏删除。
在Redis源码geo.c的⽂件注释中，只说明了该⽂件为GEOADD、GEORADIUSGEORADIUSBYMEMBER的实现⽂件（其实在也实现了另三个命令）。从侧⾯看出其他三个命令为辅助命令。

```java
GEOADD
```

使⽤⽅式
GEOADD key longitude latitude member [longitude latitude member ...]
将给定的位置对象（纬度、经度、名字）添加到指定的key。
其中，key为集合名称，member为该经纬度所对应的对象。在实际运⽤中，当所需存储的对象数量过多时，可通过设置多
key(如⼀个省⼀个key)的⽅式对对象集合变相做sharding，避免单集合数量过多。
成功插⼊后的返回值：
(integer) N
其中N为成功插⼊的个数。

通过源码分析可以看出Redis内部使⽤有序集合(zset)保存位置对象，有序集合中每个元素都是⼀个带位置的对象，元素的score值为其经纬度对应的52位的geohash值。
double类型精度为52位；
geohash是以base32的⽅式编码，52bits最⾼可存储10位geohash值，对应地理区域⼤⼩为0.6*0.6⽶的格⼦。换句话说经Redis geo转换过的位置理论上会有约0.3*1.414=0.424⽶的误差。（？）

简单总结下GEOADD命令都⼲了啥：
1、参数提取和校验；
2、将⼊参经纬度转换为52位的geohash值（score）；
3、调⽤ZADD命令将member及其对应的score存⼊集合key中。

```java
GEORADIUS
```

使⽤⽅式

```java
GEORADIUS key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [ASC|DESC] [COUNT count] [STORE key] [STORedisT key]
```

以给定的经纬度为中⼼，返回⽬标集合中与中⼼的距离不超过给定最⼤距离的所有位置对象。
范围单位：m | km | ft | mi --> ⽶ | 千⽶ | 英尺 | 英⾥
额外外参数：
- WITHDIST：在返回位置对象的同时，将位置对象与中⼼之间的距离也⼀并返回。距离的单位和⽤⼾给定的范围单位保持⼀致。
- WITHCOORD：将位置对象的经度和维度也⼀并返回。
- WITHHASH：以 52 位有符号整数的形式，返回位置对象经过原始 geohash 编码的有序集合分值。这个选项主要⽤于底层应⽤
或者调试，实际中的作⽤并不⼤。
- ASC|DESC：从近到远返回位置对象元素 | 从远到近返回位置对象元素。
- COUNT count：选取前N个匹配位置对象元素。（不设置则返回所有元素） - STORE key：将返回结果的地理位置信息保存到
指定key。
- STORedisT key：将返回结果离中⼼点的距离保存到指定key。

由于 STORE 和 STORedisT 两个选项的存在，GEORADIUS 和 GEORADIUSBYMEMBER 命令在技术上会被标记为写⼊命令，从⽽只会查询（写⼊）主实例，QPS过⾼时容易造成主实例读写压⼒过⼤。为解决这个问题，在 Redis 3.2.10 和 Redis 4.0.0 中，分别新增了 GEORADIUS_RO 和 GEORADIUSBYMEMBER_RO两个只读命令。
不过，在实际开发中笔者发现 在java package Redis.clients.jedis.params.geo 的 GeoRadiusParam 参数类中并不包含STORE 和 STORedisT 两个参数选项，在调⽤georadius时是否真的只查询了主实例，还是进⾏了只读封装。
成功查询后的返回值：
不带WITH限定，返回⼀个member list，如：
["member1","member2","member3"]
带WITH限定，member list中每个member也是⼀个嵌套list，如：
[
["member1", distance1, [longitude1, latitude1]]
["member2", distance2, [longitude2, latitude2]]
]

⼩结
抛开众多可选参数不谈，简单总结下GEORADIUS命令是怎么利⽤geohash获取⽬标位置对象的：
1、参数提取和校验；
2、利⽤中⼼点和输⼊半径计算待查区域范围。这个范围参数包括满⾜条件的最⾼的geohash⽹格等级(精度) 以及 对应的能够覆盖⽬标区域的九宫格位置；（后续会有详细说明）
3、对九宫格进⾏遍历，根据每个geohash⽹格的范围框选出位置对象。进⼀步找出与中⼼点距离⼩于输⼊半径的对象，进⾏返回。
直接描述不太好理解，我们通过如下两张图在对算法进⾏简单的演⽰：
![在这里插入图片描述](https://img-blog.csdnimg.cn/3469f2d265654a87815482ca0bcd7f7e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_9,color_FFFFFF,t_70,g_se,x_16)
令左图的中⼼为搜索中⼼，绿⾊圆形区域为⽬标区域，所有点为待搜索的位置对象，红⾊点则为满⾜条件的位置对象。
在实际搜索时,⾸先会根据搜索半径计算geohash⽹格等级（即右图中⽹格⼤⼩等级），并确定九宫格位置（即红⾊九宫格位置信息）；再依次查找计算九宫格中的点（蓝点和红点）与中⼼点的距离，最终筛选出距离范围内的点（红点）。

如何通过geohash⽹格的范围框选出元素对象？效率如何？
⾸先在每个geohash⽹格中的geohash值都是连续的，有固定范围。所以只要找出有序集合中，处在该范围的位置对象即可。以下是有序集合的跳表数据结构：
![在这里插入图片描述](https://img-blog.csdnimg.cn/50eadc77fa174cb8bebcff5f7153f532.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbWFyY2ggb2YgVGltZQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
其拥有类似⼆叉查找树的查询效率，操作平均时间复杂性为O(log(N))。
且最底层的所有元素都以链表的形式按序排列。
所以在查询时，只要找到集合中处在⽬标geohash⽹格中的第⼀个值，后续依次对⽐即可，不⽤多次查找。九宫格不能⼀起查，要⼀个个遍历的原因也在于九宫格各⽹格对应的geohash值不具有连续性。只有连续了，查询效率才会⾼，不然要多做许多距离运算。







# Redis线程模型

Redis基于Reactor模式开发了网络事件处理器，这个处理器被称为文件事件处理器（file event handler）。它的组成结构为4部分：多个套接字、IO多路复用程序、文件事件分派器、事件处理器。因为文件事件分派器队列的消费是单线程的，所以Redis才叫单线程模型。

- 文件事件处理器使用 I/O 多路复用（multiplexing）程序来**同时监听多个套接字， 并根据套接字目前执行的任务来为套接字关联不同的事件处理器。**
- 当被监听的套接字准备好执行连接应答（accept）、读取（read）、写入（write）、关闭（close）等操作时， 与操作相对应的文件事件就会产生， 这时文件事件处理器就会调用套接字之前关联好的事件处理器来处理这些事件。



**虽然文件事件处理器以单线程方式运行， 但通过使用 I/O 多路复用程序来监听多个套接字， 文件事件处理器既实现了高性能的网络通信模型， 又可以很好地与 redis 服务器中其他同样以单线程方式运行的模块进行对接， 这保持了 Redis 内部单线程设计的简单性。**

redis服务端对于命令的处理是单线程的，但是在I/O层面却可以同时面对多个客户端并发的提供服务，并发到内部单线程的转化通过多路复用框架实现

一个IO操作的完整流程是数据请求先从用户态到内核态，也就是操作系统层面，然后再调用操作系统提供的api，调用相对应的设备去获取相应的数据。

当相应的设备准备好数据后，会将数据复制到内核态，处理方式分为阻塞和非阻塞

阻塞：用户请求会等待数据从操作系统调用相应的设备返回到内核态，如果没有返回则处于阻塞状态

非阻塞：操作系统接收到一组文件描述符，然后操作系统批量处理这些文件描述符，然后不管有没有准备好数据都立即返回，如果没有对应的准备好的文件描述符，则继续轮询获取准备好数据的文件描述符。

数据从内核态复制到用户态的处理方式又分为同步和异步

同步：用户请求等待数据从内核态向用户态复制数据，在此期间不做其他事情

异步：在数据从内核态向用户态复制的过程中，用户请求不会一直处于等待状态而是做其他事情

redis的多路复用框架使用的非阻塞的数据返回模式


redis的io模型主要是基于[epoll]实现的，不过它也提供了 select和kqueue的实现，默认采用epoll。

poll 与 select 不同，通过一个 pollfd 数组向内核传递需要关注的事件，故没有描述符个数的限制，pollfd 中的 events 字段和 revents 分别用于标识关注的事件和发生的事件，故 pollfd 数组只需要被初始化一次。
epoll 还是 poll 的一种优化，返回后不需要对所有的 fd 进行遍历，在内核中维持了 fd 的列表。**select 和 poll 是将这个内核列表维持在用户态，然后传递到内核中；**而与 poll/select 不同，epoll 不再是一个单独的系统调用，而是由 epoll_create/epoll_ctl/epoll_wait 三个系统调用组成，
epoll 有诸多优点：

epoll 没有最大并发连接的限制，上限是最大可以打开文件的数目，这个数字一般远大于 2048, 一般来说这个数目和系统内存关系很大 ，具体数目可以 cat /proc/sys/fs/file-max 察看。
效率提升， Epoll 最大的优点就在于它只管你“活跃”的连接 ，而跟连接总数无关，因此 IO 效率不随 FD 数目增加而线性下降，在实际的网络环境中， Epoll 的效率就会远远高于 select 和 poll 。
内存拷贝， **Epoll 在这点上使用了“共享内存 ”，这个内存拷贝也省略了。**
**Epoll 使用了 mmap 加速内核与用户空间的消息传递**。这点涉及了 epoll 的具体实现。无论是select, poll，还是 epoll，都需要内核把 FD 消息通知给用户空间，如何避免不必要的内存拷贝就很 重要。在这点上，<font color="red">Epoll 是通过内核与用户空间 mmap(内存映射) 同一块内存实现的。</font>

【mmap是操作这些设备的一种方法，所谓操作设备，比如IO端口（点亮一个LED）、LCD控制器、磁盘控制器，实际上就是往设备的物理地址读写数据。

但是，由于应用程序不能直接操作设备硬件地址，所以操作系统提供了这样的一种机制——内存映射，把设备地址映射到进程虚拟地址，mmap就是实现内存映射的接口。mmap的好处是，mmap把设备内存映射到虚拟内存，则用户操作虚拟内存相当于直接操作设备了，省去了用户空间到内核空间的复制过程，相对IO操作来说，增加了数据的吞吐量。】

select/poll的几大缺点：

每次调用 select/poll，都需要把 fd 集合从用户态拷贝到内核态，这个开销在 fd 很多的时候会很大；
同时每次调用 select/poll 都需要在内核遍历传递进来的所有 fd，这个开销在 fd 很多时也很大；
针对 select 支持的文件描述符数量太小了，默认是 1024；
select 返回的是含有整个句柄的数组，应用程序需要遍历整个数组才能发现哪些句柄发生了事件；
select 的触发方式是水平触发，应用程序如果没有完成对一个已经就绪的文件描述符进行 IO 操作，那么之后每次 select 调用还是会将这些文件描述符通知进程

那么epoll到底是个什么东西呢？ 其实只是众多i/o多路复用技术当中的一种而已，但是相比其他io多路复用技术(select, poll等等)。

所有添加到 epoll 中的事件都会与设备（网卡）驱动程序**建立回调关系，也就是说当相应的事件发生时，会调用这个回调方法。这个回调方法在内核中叫 ep_poll_callback，它会将发生的事件添加到 rdlist 双链表中。
这个事件双链表是怎么维护的呢？当我们执行 epoll_ctl 时，除了把 socket 放到 epoll 文件系统里 file 对象对应的红黑树上之外，还会给内核中断处理程序注册一个回调函数**。告诉内核，如果这个句柄的中断到了，就把它放到准备就绪 list 链表里。所以，当一个 socket 上有数据到了，内核在把网卡上的数据 copy 到内核中，然后就把 socket 插入到准备就绪链表里了。由此可见，epoll 的基础就是回调

由于 epoll 的实现机制与 select/poll 机制完全不同，上面所说的 select 的缺点在 epoll 上不复存在。
Epoll 没有这个限制，它所支持的 FD 上限是最大可以打开文件的数目，这个数字一般远大于 2048。举个例子，在 1GB 内存的机器上大约是 10万左右，设想一下如下场景：有 100 万个客户端同时与一个服务器进程保持着 TCP 连接。而每一时刻，通常只有几百上千个 TCP 连接是活跃的（事实上大部分场景都是这种情况）。如何实现这样的高并发？
在 select/poll 时代，主要实现方式是从用户态复制句柄数据结构到内核态。服务器进程每次都把这 100 万个连接告诉操作系统，让操作系统内核去查询这些套接字上是否有事件发生。轮询完后，再将句柄数据复制到用户态，让服务器应用程序轮询处理已发生的网络事件，这一过程资源消耗较大，因此，select/poll一般只能处理几千的并发连接。
此外，如果没有 I/O 事件产生，我们的程序就会阻塞在 select 处。但是依然有个问题，我们从 select 那里仅仅知道了，有 I/O 事件发生了，但却并不知道是那几个流（可能有一个，多个，甚至全部），我们只能无差别轮询所有流，找出能读出数据，或者写入数据的流，对他们进行操作。但是使用 select，我们有 O(n) 的无差别轮询复杂度，同时处理的流越多，每一次无差别轮询时间就越长。
Epoll 的设计和实现与 select 完全不同。Epoll 通过在 Linux 内核中申请一个简易的文件系统（文件系统一般用 B+树实现），把原先的 select/poll 调用分成了3个部分：

epoll_create()：建立一个 epoll对象（在 Epoll 文件系统中，为这个句柄对象分配资源）；
epoll_ctl()：向 epoll 对象中添加这100万个连接的套接字；
epoll_wait()：收集发生的事件的连接；
如此一来，要实现上面所说的场景，只需要在进程启动时建立一个 epoll 对象，然后在需要的时候向这个 epoll 对象中添加或者删除连接。同时，epoll_wait 的效率也非常高，因为调用 epoll_wait 时，并没有一股脑的向操作系统复制这100万个连接的句柄数据，内核也不需要去遍历全部的连接。
每一个 epoll 对象都有一个独立的 eventpoll 结构体，用于存放通过 epoll_ctl 方法向 epoll 对象中添加进来的事件，这些事件都会挂载在用于存储上述的被监控 socket 的红黑树上，即上面源码的 rb_root。当你调用 epoll_create 时，就会在 epoll 注册的一个文件系统中创建一个 file 节点，这个 file 不是普通文件，它只服务于 epoll。epoll 在被内核初始化时（操作系统启动），同时会开辟出 epoll 自己的内核高速缓存区，用于安置每一个我们想监控的 socket，这些 socket 会以红黑树的形式保存在内核缓存里，红黑树的插入时间效率很高，对于高度为 n 的红黑树，查找、插入、删除的效率都是 lgn。如此重复添加的事件就可以通过红黑树高效的识别出来。

注：这个内核高速缓存区，就是建立连续的物理内存页，然后在之上建立 slab 层，简单的说，就是物理上分配好你想要的 size 的内存对象，每次使用时都是使用空闲的已分配好的对象。
7.4.2 事件双链表
所有添加到 epoll 中的事件都会与设备（网卡）驱动程序**建立回调关系，也就是说当相应的事件发生时，会调用这个回调方法。这个回调方法在内核中叫 ep_poll_callback，它会将发生的事件添加到 rdlist 双链表中。
这个事件双链表是怎么维护的呢？当我们执行 epoll_ctl 时，除了把 socket 放到 epoll 文件系统里 file 对象对应的红黑树上之外，还会给内核中断处理程序注册一个回调函数**。告诉内核，如果这个句柄的中断到了，就把它放到准备就绪 list 链表里。所以，当一个 socket 上有数据到了，内核在把网卡上的数据 copy 到内核中，然后就把 socket 插入到准备就绪链表里了。由此可见，epoll 的基础就是回调。
epoll 的每一个事件都会包含一个 epitem 结构体，如下所示：

```c
struct epitem {
  // 红黑树节点
  struct rb_node rbn; 
  // 双向链表节点
  struct list_head rdllink;
  // 事件句柄信息
  struct epoll_filefd ffd;
  // 指向所属的 eventpoll 对象
  struct eventpoll *ep;
  // 期待发生的事件类型
  struct epoll_event event;
}

```

当调用 epoll_wait 检查是否有事件发生时，只需要检查 eventpoll 对象中的 rdlist 双链表中是否有 epitem 元素即可。如果 rdlist 不为空，则把发生的事件复制到用户态，同时将事件数量返回给用户。

综上所述，epoll 的执行过程：

调用 epoll_create 时，内核帮我们在 epoll 文件系统里建立 file 结点，内核缓存中建立 socket 红黑树，除此之外，还会再建立一个用于存储准备就绪事件的 list 链表。
执行 epoll_ctl 时，如果增加就绪事件的 socket 句柄，则需要：
检查在红黑树中是否存在，存在立即返回，不存在则添加到树干上；
然后向内核注册回调函数，用于当中断事件来临时向准备就绪链表中插入数据。
epoll_wait 调用时，仅仅观察这个 list 链表里有没有数据即可，有数据就返回，没有数据就 sleep，等到 timeout 时间到后，即使链表没数据也返回。
epoll_wait 的执行过程相当于以往调用 select/poll，但 epoll 的效率高得多。
注：
epoll 独有的两种模式 LT 和 ET。无论是 LT 和 ET 模式，都适用于以上所说的流程。区别是，LT 模式下只要一个句柄上的事件一次没有处理完，会在以后调用 epoll_wait 时次次返回这个句柄。而ET模式仅在第一次返回。
关于 LT 和 ET 有一端描述，LT 和 ET 都是电子里面的术语，ET 是边缘触发，LT 是水平触发，一个表示只有在变化的边际触发，一个表示在某个阶段都会触发。
对于 epoll 而言，当一个 socket 句柄上有事件时，内核会把该句柄插入上面所说的准备就绪链表，这时我们调用 epoll_wait，会把准备就绪的 socket 拷贝到用户态内存，然后清空准备就绪链表。最后，epoll_wait 检查这些 socket，如果不是 ET 模式（就是LT模式的句柄了），并且这些 socket 上确实有未处理的事件时，又把该句柄放回到刚刚清空的准备就绪链表了。所以，非 ET 的句柄，只要它上面还有事件，epoll_wait 每次都会返回这个句柄。

# Redis 为什么是单线程的

官方FAQ表示，因为Redis是基于内存的操作，CPU不是Redis的瓶颈，Redis的瓶颈最有可能是机器内存的大小或者网络带宽。**既然单线程容易实现，而且CPU不会成为瓶颈，那就顺理成章地采用单线程的方案了**（毕竟采用多线程会有很多麻烦！）Redis利用队列技术将并发访问变为串行访问

 1）绝大部分请求是纯粹的内存操作（非常快速）

2）采用单线程,避免了不必要的上下文切换和竞争条
件 

3）非阻塞IO优点：
速度快，因为数据存在内存中，类似于HashMap，HashMap的优势就是查找和操作的时间复杂度都是O(1)
支持丰富数据类型，支持string，list，set，sorted set，hash
支持事务，操作都是原子性，所谓的原子性就是对数据的更改要么全部执行，要么全部不执行
丰富的特性：可用于缓存，消息，按key设置过期时间，过期后将会自动删除如何解决redis的并发竞争key问题

同时有多个子系统去set一个key。这个时候要注意什么呢？ 不推荐使用redis的事务机制。因为我们的生产环境，基本都是redis集群环境，做了数据分片操作。你一个事务中有涉及到多个key操作的时候，这多个key不一定都存储在同一个redis-server上。因此，redis的事务机制，十分鸡肋。

 (1)如果对这个key操作，不要求顺序： 准备一个分布式锁，大家去抢锁，抢到锁就做set操作即可 

(2)如果对这个key操作，要求顺序： 分布式锁+时间戳。 假设这会系统B先抢到锁，将key1设置为{valueB 3:05}。接下来系统A抢到锁，发现自己的valueA的时间戳早于缓存中的时间戳，那就不做set操作了。以此类推。

 (3) 利用队列，将set方法变成串行访问也可以redis遇到高并发，如果保证
读写key的一致性 对redis的操作都是具有原子性的,是线程安全的操作,你不用考虑并发问题,redis内部已经帮你处理好并发的问题了。





# Redis常见的性能问题和解决方案

1、master最好不要做持久化工作，如RDB内存快照和AOF日志文件
2、如果数据比较重要，某个slave开启AOF备份，策略设置成每秒同步一次
3、为了主从复制的速度和连接的稳定性，master和Slave最好在一个局域网内
4、尽量避免在压力大得主库上增加从库
5、主从复制不要采用网状结构，尽量是线性结构，Master<--Slave1<----Slave2 ....【为什么】

这样的结构方便解决单点故障问题，实现Slave对Master的替换。如果Master挂了，可以立刻启用Slave1做Master，其他不变



（1）、Master 写内存快照，save 命令调度rdbSave 函数，会阻塞主线程的工作，当快照比较大时对性能影响是非常大的，会间断性暂停服务，所以Master 最好不要写内存快照。
（2）、Master AOF 持久化，如果不重写AOF 文件，这个持久化方式对性能的影响是最小的，但是AOF 文件会不断增大，AOF 文件过大会影响Master 重启的恢复速度。Master 最好不要做任何持久化工作，包括内存快照和AOF日志文件，特别是不要启用内存快照做持久化,如果数据比较关键，某个Slave 开启AOF 备份数据，策略为每秒同步一次。
（3）、Master 调用BGREWRITEAOF 重写AOF 文件，AOF 在重写的时候会占大量的CPU 和内存资源，导致服务load 过高，出现短暂服务暂停现象。
（4）、Redis 主从复制的性能问题，为了主从复制的速度和连接的稳定性，Slave 和Master 最好在同一个局域网内



## 为什么Redis的操作是原子性的，怎么保证原子性的？

对于Redis而言，命令的原子性指的是：一个操作的不可以再分，操作要么执行，要么不执行。
Redis的操作之所以是原子性的，是因为Redis是单线程的。
Redis本身提供的所有API都是原子操作，Redis中的事务其实是要保证批量操作的原子性。
多个命令在并发中也是原子性的吗？
不一定， 将get和set改成单命令操作，incr 。使用Redis的事务，或者使用Redis+Lua的方式实现.



## Redis事务

**Redis事务功能是通过MULTI、EXEC、DISCARD和WATCH 四个原语实现的,Redis会将一个事务中的所有命令序列化，然后按顺序执行**。
1.redis 不支持回滚“Redis 在事务失败时不进行回滚，而是继续执行余下的命令”， 所以 Redis 的内部可以保持简单且快速。
2.如果在一个事务中的命令出现错误，那么所有的命令都不会执行；
3.如果在一个事务中出现运行错误，那么正确的命令会被执行。
1）**MULTI命令用于开启一个事务，它总是返回OK。 MULTI执行之后，客户端可以继续向服务器发送任意多条命令，这些命令不会立即被执行，而是被放到一个队列中，当EXEC命令被调用时，所有队列中的命令才会被执行**。
2）EXEC：执行所有事务块内的命令。返回事务块内所有命令的返回值，按命令执行的先后顺序排列。当操作被打断时，返回空值 nil 。
3）通过调用DISCARD，客户端可以清空事务队列，并放弃执行事务， 并且客户端会从事务状态中退出。

4）WATCH 命令可以为 Redis 事务提供 check-and-set （CAS）行为。 可以监控一个或多个键，一旦其中有一个键被修改（或删除），之后的事务就不会执行，监控一直持续到EXEC命令。



# 假如Redis里面有1亿个key，其中有10w个key是以某个固定的已知的前缀开头的，如果将它们全部找出来？
使用keys指令可以扫出指定模式的key列表。

我们可以使用 keys 命令和 scan 命令，但是会发现使用 scan 更好。
1. 使用 keys 命令
直接使用 keys 命令查询，但是如果是在生产环境下使用会出现一个问题，keys 命令是遍历查询的，查询的时间复杂度为 O(n)，数据量越大查询时间越长。而且 Redis 是单线程，keys 指令会导致线程阻塞一段时间，会导致线上 Redis 停顿一段时间，直到 keys 执行完毕才能恢复。这在生产环境是不允许的。除此之外，需要注意的是，这个命令没有分页功能，会一次性查询出所有符合条件的 key 值，会发现查询结果非常大，输出的信息非常多。所以不推荐使用这个命令。
2. 使用 scan 命令
scan 命令可以实现和 keys 一样的匹配功能，但是 scan 命令在执行的过程不会阻塞线程，并且查找的数据可能存在重复，需要客户端操作去重。因为 scan 是通过游标方式查询的，所以不会导致Redis 出现假死的问题。Redis 查询过程中会把游标返回给客户端，单次返回空值且游标不为 0，则说明遍历还没结束，客户端继续遍历查询。scan 在检索的过程中，被删除的元素是不会被查询出来的，但是如果在迭代过程中有元素被修改，scan 不能保证查询出对应元素。相对来说，scan 指令查找花费的时间会比 keys 指令长。



对方接着追问：如果这个redis正在给线上的业务提供服务，那使用keys指令会有什么问题？
这个时候你要回答redis关键的一个特性：redis的单线程的。keys指令会导致线程阻塞一段时间，线上服务会停顿，直到指令执行完毕，服务才能恢复。这个时候可以使用scan指令，scan指令可以无阻塞的提取出指定模式的key列表，但是会有一定的重复概率，在客户端做一次去重就可以了，但是整体所花费的时间会比直接用keys指令长。

# 如果有大量的 key 需要设置同一时间过期，一般需要注意什么？

如果有大量的 key 在同一时间过期，那么可能同一秒都从数据库获取数据，给数据库造成很大的压力，导致数据库崩溃，系统出现 502 问题。也有可能同时失效，那一刻不用都访问数据库，压力不够大的话，那么 Redis 会出现短暂的卡顿问题。所以为了预防这种问题的发生，**最好给数据的过期时间加一个随机值，让过期时间更加分散。**

# 怎么提高缓存命中率？
主要常用的手段有：
提前加载数据到缓存中；
增加缓存的存储空间，提高缓存的数据；
调整缓存的存储数据类型；
提升缓存的更新频率。



# 列表类型

**LPUSH**
将一个或多个值 value 插入到列表 key 的表头，如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头，如

127.0.0.1:6379> lpush k1 ni opp hello
(integer) 3

注意，这里的key不能是之前建过的只含一个值的key

**RPUSH**

 与 LPUSH 的功能基本一致，不同的是 RPUSH 的中的 value 值是按照从右到左的顺序依次插入，如下：

127.0.0.1:6379> RPUSH k2 1 2 3 4 5 (integer) 5 127.0.0.1:6379> LRANGE k2 0 -1 1) "1" 2) "2" 3) "3"



**LRANGE**
返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定，下标 (index) 参数 start 和 stop 都以 0 为底，即 0 表示列表的第一个元素，1 表示列表的第二个元素，以此类推。我们也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。如下：
127.0.0.1:6379> LRANGE k1 0 -1 1) "v3" 2) "v2" 3) "v1"

**LTRIM**
LTRIM 命令可以对一个列表进行修剪，即让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。

127.0.0.1:6379> LRANGE k1 0 -1 

1) "v3" 

2) "v2" 

3) "v1"

弹出元素：（1是timeout)

127.0.0.1:6379> lrange k1 0 -1(0 -1是表示列出所有元素)
1) "hello"
2) "opp"
3) "ni"
127.0.0.1:6379> blpop k1 1
1) "k1"
2) "hello"

# 集合与列表类型

![image-20220213012257236](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220213012257236.png)

列表：

![image-20210725221125792](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210725221125792.png)

接下来我们来看看集合中一些常见的操作命令：

```java
package com.kuang;

import redis.clients.jedis.Jedis;


//基本类型之Set
public class TestSet04 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        System.out.println("============向集合中添加元素（不重复）============");
        System.out.println(jedis.sadd("eleSet", "e1","e2","e4","e3","e0","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("删除一个元素e0："+jedis.srem("eleSet", "e0"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("删除两个元素e7和e6："+jedis.srem("eleSet", "e7","e6"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("eleSet中包含元素的个数："+jedis.scard("eleSet"));
        System.out.println("e3是否在eleSet中："+jedis.sismember("eleSet", "e3"));
        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e1"));
        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e5"));
        System.out.println("=================================");
        System.out.println(jedis.sadd("eleSet1", "e1","e2","e4","e3","e0","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet2", "e1","e2","e4","e3","e0","e8"));
        System.out.println("将eleSet1中删除e1并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e1"));//移到集合元素
        System.out.println("将eleSet1中删除e2并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e2"));
        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
        System.out.println("eleSet3中的元素："+jedis.smembers("eleSet3"));
        System.out.println("============集合运算=================");
        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
        System.out.println("eleSet2中的元素："+jedis.smembers("eleSet2"));
        System.out.println("eleSet1和eleSet2的交集:"+jedis.sinter("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的并集:"+jedis.sunion("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的差集:"+jedis.sdiff("eleSet1","eleSet2"));//eleSet1中有，eleSet2中没有
        jedis.sinterstore("eleSet4","eleSet1","eleSet2");//求交集并将交集保存到dstkey的集合
        System.out.println("eleSet4中的元素："+jedis.smembers("eleSet4"));
    }
}
```







```java
package com.kuang;

import redis.clients.jedis.Jedis;


//基本类型之List
public class TestList03 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        System.out.println("===========添加一个list===========");
        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        jedis.lpush("collections", "HashSet");
        jedis.lpush("collections", "TreeSet");
        jedis.lpush("collections", "TreeMap");
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));//-1代表倒数第一个元素，-2代表倒数第二个元素,end为-1表示查询全部
        System.out.println("collections区间0-3的元素："+jedis.lrange("collections",0,3));
        System.out.println("===============================");
        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈
        System.out.println("删除指定元素个数："+jedis.lrem("collections", 2, "HashMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("删除下表0-3区间之外的元素："+jedis.ltrim("collections", 0, 3));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（左端）："+jedis.lpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections添加元素，从列表右端，与lpush相对应："+jedis.rpush("collections", "EnumMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（右端）："+jedis.rpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("修改collections指定下标1的内容："+jedis.lset("collections", 1, "LinkedArrayList"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("===============================");
        System.out.println("collections的长度："+jedis.llen("collections"));
        System.out.println("获取collections下标为2的元素："+jedis.lindex("collections", 2));
        System.out.println("===============================");
        jedis.lpush("sortedList", "3","6","2","0","7","4");
        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
        System.out.println(jedis.sort("sortedList"));
        System.out.println("sortedList排序后："+jedis.lrange("sortedList", 0, -1));
    }
}
```



**SADD**
SADD 命令可以添加一个或多个指定的 member 元素到集合的 key 中，指定的一个或者多个元素 member 如果已经在集合 key 中存在则忽略，如果集合 key 不存在，则新建集合 key ,并添加 member 元素到集合 key

**SISMEMBER**
SISMEMBER 命令可以返回成员 member 是否是存储的集合 key 的成员。如下：
127.0.0.1:6379> SISMEMBER k1 v3 (integer) 1
**SCARD**
SCARD 命令可以返回集合存储的 key 的基数(集合元素的数量)，如下：
127.0.0.1:6379> SCARD k1



```
127.0.0.1:6379> sadd jihe 784 gjer 34 ewr
(integer) 4

127.0.0.1:6379> sismember jihe 784
(integer) 1
127.0.0.1:6379> sismember jihe o
(integer) 0
127.0.0.1:6379> scard jihe
(integer) 4
```

**SRANDMEMBER**
SRANDMEMBER 仅需我们提供 key 参数,它就会随机返回 key 集合中的一个元素，从 Redis2.6 开始,该命令也可以接受一个可选的 count 参数,如果 count 是整数且小于元素的个数，则返回 count 个随机元素,如果 count 是整数且大于集合中元素的个数时,则返回集合中的所有元素,当 count 是负数,则会返回一个包含 count 的绝对值的个数元素的数组，如果 count 的绝对值大于元素的个数,则返回的结果集里会出现一个元素出现多次的情况。如下：
127.0.0.1:6379> SRANDMEMBER k1 

"v4"

127.0.0.1:6379> SRANDMEMBER k1 2 

1) "v4" 

2) "v1" 

127.0.0.1:6379> SRANDMEMBER k1 5 

1) "v4" 

2) "v1" 

3) "v3"

SDIFF
SDIFF 可以用来返回一个集合与给定集合的差集的元素，如下：
127.0.0.1:6379> SDIFF k1 k2 

1) "v4" 

2) "v3"

**k1 中的元素是 v3、v4，k2 中的元素是 v1，差集就是 v3、v4.**（不太明白）

# 发布订阅

redis 的发布订阅系统有点类似于我们生活中的电台，电台可以在某一个频率上发送广播，而我们可以接收任何一个频率的广播，Android 中的 broadcast 也和这类似。

订阅消息的方式如下:
127.0.0.1:6379> SUBSCRIBE c1 c2 c3 Reading messages... (press Ctrl-C to quit) 

1) "subscribe"

 2) "c1" 

3) (integer) 1 

1) "subscribe" 

2) "c2" 

3) (integer) 2 

1) "subscribe" 

2) "c3" 

3) (integer) 3

这个表示接收 c1，c2，c3 三个频道传来的消息，发送消息的方式如下：
127.0.0.1:6379> PUBLISH c1 "hello redis!" (integer)



tips
redis 中的发布订阅系统在某些场景下还是非常好用的，但是也有一些问题需要注意：由于网络在传输过程中可能会遭遇断线等意外情况，断线后需要进行重连，然而这会导致断线期间的数据丢失。
事务
既然 redis 是一种 NoSQL 数据库，那它当然也有事务的功能，不过这里的事务和我们关系型数据库中的事务有一点点差异。
redis 中事务的用法非常简单，我们通过 MULTI 命令开启一个事务，如下：
127.0.0.1:6379> MULTI OK
在 MULTI 命令执行之后，我们可以继续发送命令去执行，此时的命令不会被立马执行，而是放在一个队列中，如下：
127.0.0.1:6379> set k1 v1 QUEUED 127.0.0.1:6379> set k2 v2 QUEUED 127.0.0.1:6379> set k3 v3 QUEUED

当所有的命令都输入完成后，我们可以通过 EXEC 命令发起执行，也可以通过 DISCARD 命令清空队列

**不同于关系型数据库，redis 中的事务出错时没有回滚**，对此，官方的解释如下：
Redis 命令只会因为错误的语法而失败（并且这些问题不能在入队时发现），或是命令用在了错误类型的键上面：这也就是说，从实用性的角度来说，失败的命令是由编程错误造成的，而这些错误应该在开发的过程中被发现，而不应该出现在生产环境中。因为不需要对回滚进行支持，所以 Redis 的内部可以保持简单且快速。





redis主从复制如何实现的？redis的集群模式如何实现？redis的key是如何寻址的？
主从复制实现：主节点将自己内存中的数据做一份快照，将快照发给从节点，从节点将数据恢复到内存中。之后再每次增加新数据的时候，主节点以类似于mysql的二进制日志方式将语句发送给从节点，从节点拿到主节点发送过来的语句进行重放。

# 熟悉哪些Redis 集群模式？

1. Redis Sentinel
体量较小时，选择 Redis Sentinel ，单主 Redis 足以支撑业务。
2. Redis Cluster
Redis 官方提供的集群化方案，体量较大时，选择 Redis Cluster ，通过分片，使用更多内存。
3. Twemprox
Twemprox 是Twtter 开源的一个 Redis 和 Memcached 代理服务器，主要用于管理 Redis 和Memcached 集群，减少与Cache 服务器直接连接的数量。
4. Codis
Codis 是一个代理中间件，当客户端向Codis 发送指令时， Codis 负责将指令转发到后面的Redis 来执行，并将结果返回给客户端。一个Codis 实例可以连接多个Redis 实例，也可以启动多个Codis 实例来支撑，每个Codis 节点都是对等的，这样可以增加整体的QPS 需求，还能起到容灾功能。
5. 客户端分片
在Redis Cluster 还没出现之前使用较多，现在基本很少热你使用了，**在业务代码层实现，起几个毫无关联的Redis 实例，在代码层，对 Key 进行 hash 计算，然后去对应的 Redis 实例操作数据**。这种方式对 hash 层代码要求比较高，考虑部分包括，节点失效后的替代算法方案，数据震荡后的自动脚本恢复，实例的监控，等等。



# 集群的原理是什么？
使用过 Redis 集群，它的原理是：
所有的节点相互连接
集群消息通信通过集群总线通信，集群总线端口大小为客户端服务端口 + 10000（固定值）
节点与节点之间通过二进制协议进行通信
客户端和集群节点之间通信和通常一样，通过文本协议进行
集群节点不会代理查询
数据按照 Slot 存储分布在多个 Redis 实例上
集群节点挂掉会自动故障转移
可以相对平滑扩/缩容节点



# 操作ZSET

```
zadd board  5 zhangsan
zadd board 72 lisi
zadd board 96 wangwu


# 获取排名前三的用户（默认是升序，所以需要 rev 改为降序）

zrevrange board 0 3
获取某用户的排名
zrank board zhaoliu
```



# 


在一些网站中，经常会有排名，如最热门的商品或者最大的购买买家，都是常常见到的场景。对于这类排名，刷新往往需要及时，也涉及较大的统计，如果使用数据库会太慢。为了支持集合的排序，Redis还提供了有序集合（zset）。有序集合与集合的差异并不大，它也是一种散列表存储的方式，同时它的有序性只是靠它在数据结构中增加一个属性——score（分数）得以支持。为了支持这个变化，Spring提供了TypedTuple接口，它定义了两个方法，并且Spring还提供了其默认的实现类DefaultTypedTuple，

在TypedTuple接口的设计中，value是保存有序集合的值，score则是保存分数，Redis是使用分数来完成集合的排序的，这样如果把买家作为一个有序集合，而买家花的钱作为分数，就可以使用Redis进行快速排序

```java
@RequestMapping("/zset")
@ResponseBody
public Map<String, Object> testZset() {
  Set<TypedTuple<String>> typedTupleSet = new HashSet<>();
  for (int i=1; i<=9; i++) {
    // 分数
    double score = i*0.1;
    // 创建一个TypedTuple对象，存入值和分数
    TypedTuple<String> typedTuple 
      = new DefaultTypedTuple<String>("value" + i, score);
    typedTupleSet.add(typedTuple);
  }
  // 往有序集合插入元素
  stringRedisTemplate.opsForZSet().add("zset1", typedTupleSet);
  // 绑定zset1有序集合操作
  BoundZSetOperations<String, String> zsetOps 
     = stringRedisTemplate.boundZSetOps("zset1");
  // 增加一个元素
  zsetOps.add("value10", 0.26);
  Set<String> setRange = zsetOps.range(1, 6);
  // 按分数排序获取有序集合
  Set<String> setScore = zsetOps.rangeByScore(0.2, 0.6);
  // 定义值范围
  Range range = new Range();
  range.gt("value3");// 大于value3
  // range.gte("value3");// 大于等于value3
  // range.lt("value8");// 小于value8
  range.lte("value8");// 小于等于value8
  // 按值排序，请注意这个排序是按字符串排序
  Set<String> setLex = zsetOps.rangeByLex(range);
  // 删除元素
  zsetOps.remove("value9", "value2");
  // 求分数
  Double score = zsetOps.score("value8");
  // 在下标区间下，按分数排序，同时返回value和score
  Set<TypedTuple<String>> rangeSet = zsetOps.rangeWithScores(1, 6);
  // 在分数区间下，按分数排序，同时返回value和score
  Set<TypedTuple<String>> scoreSet = zsetOps.rangeByScoreWithScores(1, 6);
  // 按从大到小排序
  Set<String> reverseSet = zsetOps.reverseRange(2, 8);
  Map<String, Object> map = new HashMap<String, Object>();
  map.put("success", true);
  return map;
}
```




代码中使用了TypedTuple保存有序集合的元素，在默认的情况下，有序集合是从小到大地排序的，按下标、分数和值进行排序获取有序集合的元素，或者连同分数一起返回，有时候还可以进行从大到小的排序，只是在使用值排序时，我们可以使用Spring为我们创建的Range类，它可以定义值的范围，还有大于、等于、大于等于、小于等于等范围定义，方便我们筛选对应的元素。



# Redis实现分布式Session

方法一：使用SpringSession实现

```

#方便学习，注释掉该行。可以使所有ip访问redis
#bind 127.0.0.1
#关闭保护模式
protected-mode no
#后台启动
daemonize yes
#添加访问认证
requirepass root
./redis-server redis.conf
<!-- spring data redis 依赖 -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!-- commons-pool2 对象池依赖 -->
<dependency>
<groupId>org.apache.commons</groupId>
<artifactId>commons-pool2</artifactId>
</dependency>
<!-- spring-session 依赖 -->
<dependency>
<groupId>org.springframework.session</groupId>
<artifactId>spring-session-data-redis</artifactId>
</dependency>

pom.xml

```

添加配置
application.yml

```yml
redis:
#超时时间
 timeout: 10000ms
#服务器地址
 host: 192.168.10.100
#服务器端口
 port: 6379
#数据库
 database: 0
#密码
 password: root
 lettuce:
  pool:
#最大连接数，默认8
  max-active: 1024
#最大连接阻塞等待时间，默认-1
  max-wait: 10000ms
#最大空闲连接
  max-idle: 200
#最小空闲连接
  min-idle: 5
```

方法二：将用户信息存入Redis

```xml
<!-- spring data redis 依赖 -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!-- commons-pool2 对象池依赖 -->
<dependency>
<groupId>org.apache.commons</groupId>
<artifactId>commons-pool2</artifactId>
</dependency>
```



```yml
spring:
 redis:
#超时时间
  timeout: 10000ms
#服务器地址
  host: 192.168.10.100
#服务器端口
  port: 6379
#数据库
  database: 0
#密码
  password: root
  lettuce:
   pool:
#最大连接数，默认8
   max-active: 1024
#最大连接阻塞等待时间，默认-1
   max-wait: 10000ms
#最大空闲连接
   max-idle: 200
#最小空闲连接
   min-idle: 5
```



RedisConfig.java

```java
@Configuration
public class RedisConfig {
@Bean
public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory
connectionFactory){
RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//key序列器
redisTemplate.setKeySerializer(new StringRedisSerializer());
//value序列器
redisTemplate.setValueSerializer(new
GenericJackson2JsonRedisSerializer());
//Hash类型 key序列器
redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//Hash类型 value序列器
redisTemplate.setHashValueSerializer(new
GenericJackson2JsonRedisSerializer());
redisTemplate.setConnectionFactory(connectionFactory);
return redisTemplate;
}
}
```



# 常用命令

```
GETRANGE key1 0 -1 #获取全部的字符串，等于get key
setrange key1 1 "abc" 替换指定位置的字符串
setex key3 30 #设置过期时间（30秒）
ttl key3 查看剩余时间
expire key1 30 #设置某个键的过期时间
setnx key value #如果不存在就创建这个键
```

![image-20210724094937361](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724094937361.png)

<img src="C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724091023754.png" alt="image-20210724091023754" style="zoom:100%;" />

这个分布式锁的实现存在问题：如果因为意外情况导致del失败，会导致这个锁无法释放，则在redis中会一直占用这个资源

![image-20210724091338136](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724091338136.png)

通过setNx+过期键主动释放资源

存在问题：极端情况可能导致expire(lock,10)没有执行，则又变成了1.0的锁版本

通过写lua脚本可以把多步非原子操作组装到一个事务当中统一提交

![image-20210724091928175](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724091928175.png)

真正的分布式锁

![image-20210724093636452](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724093636452.png)





## hash类型

![image-20210724095117363](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724095117363.png)

![image-20210724095223583](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724095223583.png)

![image-20210724102425657](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724102425657.png)

![image-20210724102807028](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724102807028.png)

![image-20210724102942056](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724102942056.png)

![image-20210724103148697](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210724103148697.png)







![image-20210725220919949](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210725220919949.png)





## 开启事务

Redis 事务的本质是通过MULTI、EXEC、WATCH等一组命令的集合。事务支持一次执行多个命令，一个事务中所有命令都会被序列化。在事务执行过程，会按照顺序串行化执行队列中的命令，其他客户端提交的命令请求不会插入到事务执行命令序列中。

总结说：redis事务就是一次性、顺序性、排他性的执行一个队列中的一系列命令。

![image-20210725221211887](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210725221211887.png)

解决办法：使用watch

![image-20210725220959758](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210725220959758.png)

![image-20210910095234231](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210910095234231.png)





**Redis事务支持隔离性吗**

Redis 是单进程程序，并且它保证在执行事务时，不会对事务进行中断，事务可以运行直到执行完所有事务队列中的命令为止。因此，Redis 的事务是总是带有隔离性的。

**Redis事务保证原子性吗，支持回滚吗**

Redis中，单条命令是原子性执行的，但事务不保证原子性，且没有回滚。事务中任意命令执行失败，其余的命令仍会被执行。

**Redis事务其他实现**

- 基于Lua脚本，Redis可以保证脚本内的命令一次性、按顺序地执行，
  其同时也不提供事务运行错误的回滚，执行过程中如果部分命令运行错误，剩下的命令还是会继续运行完
- 基于中间标记变量，通过另外的标记变量来标识事务是否执行完成，读取数据时先读取该标记变量判断是否事务执行完成。但这样会需要额外写代码实现，比较繁琐









```java
package com.kuang;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;


//Redis的事务
public class TestTX {
    public static void main(String[] args) {
        //连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //清空当前数据库
        jedis.flushDB();

        //通过fastjson获取json字符串
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","world");
        jsonObject.put("name","kuangshen");

        // 1.开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();

        //乐观锁（监控result）
        //jedis.watch(result);
        try {
            //2.命令
            multi.set("user1",result);
            multi.set("user2",result);

            // 代码抛出异常事务，执行失败！
            //int i = 1/0 ;

            // 3.执行事务！
            multi.exec();
        } catch (Exception e) {
            multi.discard(); // 失败就放弃事务
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close(); // 关闭连接
        }
    }
}

```





## 整合springboot

redistemplate:

![image-20210725221034880](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210725221034880.png)



另外一种使用场景是：我只想测试与redis无交互的某些功能

我们想让spring boot没有连接redis，也可以启动。

思路：
就是不使用spring boot的redis自动装配，redisTemplate自动注入的时候，require=false

解决方法：
1. 在application.properties中加入

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\

  org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration
2.自定义了restTemplate，

这里使用了@ConditionalOnBean(RedisConnectionFactory.class)



```java
  @Configuration

public class RedisConfig {
  @Bean

@ConditionalOnBean(RedisConnectionFactory.class)

public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
   RedisTemplate<String, String> redisTemplate = new RedisTemplate();

    redisTemplate.setConnectionFactory(factory);

    RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();

    redisTemplate.setKeySerializer(stringRedisSerializer);

    return redisTemplate;

}
```

}


3. service中使用

@Autowired(required = false)

private RedisTemplate redisTemplate;
这样，系统就可以正常启动了。



# Redis主从工作原理

如果你为master配置了一个slave，不管这个slave是否是第一次连接上Master，它都会发送一个PSYNC命令给master请求复制数据。
master收到PSYNC命令后，会在后台进行数据持久化通过bgsave生成最新的rdb快照文件，持久化期间，master会继续接收客户端的请求，它会把这些可能修改数据集的请求缓存在内存中。当持久化进行完毕以后，master会把这份rdb文件数据集发送给slave，slave会把接收到的数据进行持久化生成rdb，然后再加载到内存中。然后，master再将之前缓存在内存中的命令发送给slave。
当master与slave之间的连接由于某些原因而断开时，slave能够自动重连Master，如果master收到了多个slave并发连接请求，它只会进行一次持久化，而不是一个连接一次，然后再把这一份持久化的数据发送给多个并发连接的slave。

如果某一个slave结点重新连接上了master,master首先会尝试进行部分同步，这意味着它只会尝试获取在断开期间丢失的命令流。

当无法进行部分重同步时，slave会请求进行全量重同步


　　每一个Redis master都有一个replication ID:这是一个较大的伪随机字符串，标记了一个给定的数据集。每个master也持有一个偏移量，master将自己产生的复制流发送给lave时，发送多少个字节的数据，自身的偏移量就会增加多少，目的是当有新的操作修改自己的数据集时，它可以以此更新slave的状态。复制偏移量即使在没有一个slave连接到master时，也会自增，所以基本上每一对给定的

Replication ID,offset
都会标识一个master数据集的确切版本。
　　当slave连接到master时，它们使用PSYNC命令来发送它们记录的旧的master replication ID和它们至今为止处理的偏移量。通过这种方式，master能够仅发送slave所需的增量部分。但是如果master的缓冲区中没有足够的命令积压缓冲记录，或者如果slave引用了不再知道的历史记录(replication ID),则会转而进行一个全量重同步：在这种情况下，slave会得到一个完整的数据集副本，从头开始。
　　下面是一个全量同步的工作细节：
　　master开启一个后台保存进程，以便于生产一个RDB文件。同时它开始缓冲所有从客户端接收到的新的写入命令。当后台保存完成时，master将数据集文件传输给slave,slave将之保存在磁盘上，然后加载文件到内存。
　　再然后master会发送所有缓冲的命令发给slave。这个过程以指令流的形式完成并且和Redis协议本身的格式相同。
　　

# 为什么redis 需要把所有数据放到内存中？

Redis 为了达到最快的读写速度将数据都读到内存中，并通过异步的方式将数据写入磁盘。所以redis 具有快速和数据持久化的特征。如果不将数据放在内存中，磁盘I/O 速度为严重影响redis 的性能。在内存越来越便宜的今天，redis 将会越来越受欢迎。如果设置了最大使用的内存，则数据已有记录数达到内存限值后不能继续插入新值。

Redis 将数据放在内存中有一个好处，那就是可以实现最快的对数据读取，如果数据存储在硬盘中，磁盘 I/O 会严重影响 Redis 的性能。而且 Redis 还提供了数据持久化功能，不用担心服务器重启对内存中数据的影响。其次现在硬件越来越便宜的情况下，Redis 的使用也被应用得越来越多，使得它拥有很大的优势



# redis模拟抢单实战



创建锁的策略：redis的普通key⼀般都允许覆盖，A⽤⼾set某个key后，B在set相同的key时同样能成功，如果是锁场景，
那就⽆法知道到底是哪个⽤⼾set成功的；这⾥jedis的setnx⽅式为我们解决了这个问题，简单原理是：当A⽤⼾先set成功了，那B⽤⼾set的时候就返回失败，满⾜了某个时间点只允许⼀个⽤⼾拿到锁。
锁过期时间：某个抢购场景时候，如果没有过期的概念，当A⽤⼾⽣成了锁，但是后⾯的流程被阻塞了⼀直⽆法释放锁，那其他⽤⼾此时获取锁就会⼀直失败，⽆法完成抢购的活动；当然正常情况⼀般都不会阻塞，A⽤⼾流程会正常释放锁；过期时间只是为了更有保障。

下⾯来上段setnx操作的代码：

```java
public boolean setnx(String key, String val) {
Jedis jedis = null;
try {
jedis = jedisPool.getResource();
if (jedis == null) {
return false;
}
return jedis.set(key, val, "NX", "PX", 1000 * 60).
equalsIgnoreCase("ok");
} catch (Exception ex) {
} finally {
if (jedis != null) {
jedis.close();
}
}
return false;
}
```



如何删除锁
上⾯是创建锁，同样的具有有效时间，但是我们不能完全依赖这个有效时间，场景如：有效时间设置1分钟，本⾝⽤⼾A获取锁后，没遇到什么特殊情况正常⽣成了抢购订单后，此时其他⽤⼾应该能正常下单了才对，但是由于有个1分钟后锁才能⾃动释放，那其他⽤⼾在这1分钟⽆法正常下单（因为锁还是A⽤⼾的），因此我们需要A⽤⼾操作完后，主动去解锁：

```java
public int delnx(String key, String val) {
Jedis jedis = null;
try {
jedis = jedisPool.getResource();
if (jedis == null) {
return 0;
}
//if redis.call('get','orderkey')=='1111' then return redis.call('del','orderkey') else return 0 end
StringBuilder sbScript = new StringBuilder();
sbScript.append("if redis.call('get','").append(key).append("')").append("=='").append(val).append("'").
append(" then ").
append(" return redis.call('del','").append(key).append("')").
append(" else ").
append(" return 0").
append(" end");
return Integer.valueOf(jedis.eval(sbScript.toString()).toString());
} catch (Exception ex) {
} finally {
if (jedis != null) {
jedis.close();
}
}
return 0;
}
```

这⾥也使⽤了jedis⽅式，直接执⾏lua脚本：根据val判断其是否存在，如果存在就del；
其实个⼈认为通过jedis的get⽅式获取val后，然后再⽐较value是否是当前持有锁的⽤⼾，如果是那最后再删除，效果其实相当；只不过直接通过eval执⾏脚本，这样避免多⼀次操作了redis⽽已，缩短了原⼦操作的间隔。(如有不同⻅解请留⾔探讨)；同样这⾥创建个get⽅式的api来测试：

```java
@GetMapping("/delnx/{key}/{val}")
public int delnx(@PathVariable String key, @PathVariable String val) {
return jedisCom.delnx(key, val);
}
```

模拟抢单动作(10w个⼈开抢)
有了上⾯对分布式锁的粗略基础，我们模拟下10w⼈抢单的场景，其实就是⼀个并发操作请求⽽已，由于环境有限，只能如此测试；如下初始化10w个⽤⼾，并初始化库存，商品等信息，如下代码：

```java
//总库存
private long nKuCuen = 0;
//商品key名字
private String shangpingKey = "computer_key";
//获取锁的超时时间 秒
private int timeout = 30 * 1000;
@GetMapping("/qiangdan")
public List<String> qiangdan() {
//抢到商品的⽤⼾
List<String> shopUsers = new ArrayList<>();
//构造很多⽤⼾
List<String> users = new ArrayList<>();
IntStream.range(0, 100000).parallel().forEach(b -> {
users.add("神⽜-" + b);
});
//初始化库存
nKuCuen = 10;
    //模拟开抢
users.parallelStream().forEach(b -> {
String shopUser = qiang(b);
if (!StringUtils.isEmpty(shopUser)) {
shopUsers.add(shopUser);
}
});
return shopUsers;
}
```

模拟抢单方法：

```java
/**

* 模拟抢单动作
  *
* @param b
* @return
  */
  private String qiang(String b) {
  //⽤⼾开抢时间
  long startTime = System.currentTimeMillis();
  //未抢到的情况下，30秒内继续获取锁
  while ((startTime + timeout) >= System.currentTimeMillis()) {
  //商品是否剩余
  if (nKuCuen <= 0) {
  break;
  }
  if (jedisCom.setnx(shangpingKey, b)) {
  //⽤⼾b拿到锁
  logger.info("⽤⼾{}拿到锁...", b);
  try {
  //商品是否剩余
  if (nKuCuen <= 0) {
  break;
  }
  //模拟⽣成订单耗时操作，⽅便查看：神⽜-50 多次获取锁记录
  try {
  TimeUnit.SECONDS.sleep(1);
  } catch (InterruptedException e) {
e.printStackTrace();
}
//抢购成功，商品递减，记录⽤⼾
nKuCuen -= 1;
//抢单成功跳出
logger.info("⽤⼾{}抢单成功跳出...所剩库存：{}", b, nKuCuen);
return b + "抢单成功，所剩库存：" + nKuCuen;
} finally {
logger.info("⽤⼾{}释放锁...", b);
//释放锁
jedisCom.delnx(shangpingKey, b);
}
} else {
//⽤⼾b没拿到锁，在超时范围内继续请求锁，不需要处理
// if (b.equals("神⽜-50") || b.equals("神⽜-69")) {
// logger.info("⽤⼾{}等待获取锁...", b);
// }
}
}
return "";
}
这]

```

这⾥实现的逻辑是：
parallelStream()：并⾏流模拟多⽤⼾抢购
(startTime + timeout) >= System.currentTimeMillis()：判断未抢成功的⽤⼾，timeout秒内继续获取锁
获取锁前和后都判断库存是否还⾜够
jedisCom.setnx(shangpingKey, b)：⽤⼾获取抢购锁
获取锁后并下单成功，最后释放锁：jedisCom.delnx(shangpingKey, b)

![image-20211206172438044](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211206172438044.png)



# 消息的幂等处理

没有实现幂等性的情况：

![image-20220216023448964](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216023448964.png)

这种情况下，支付系统已经扣款，但是订单系统因为网络原因，没有获取到确切的结果，因此订单系统需要重试。由上图可见，支付系统并没有做到接口的幂等性，订单系统第一次调用和第二次调用，用户分别被扣了两次钱，不符合幂等性原则（同一个订单，无论是调用了多少次，用户都只会扣款一次）。

消息的幂等处理
由于网络原因，生产者可能会重复发送消息，因此消费者方必须做消息的幂等处理，常用的解决方案有：

首先有两种操作是天然幂等的，不需要解决，即查询和删除操作。

1. 查询操作：查询一次和查询多次，在数据不变的情况下，查询结果是一样的。select是天然的幂等操作；
2. 删除操作：删除操作也是幂等的，删除一次和多次删除都是把数据删除。(注意可能返回结果不一样，删除的
    数据不存在，返回0，删除的数据多条，返回结果多个) ；
3. 唯一索引，防止新增脏数据。比如：支付宝的资金账户，支付宝也有用户账户，每个用户只能有一个资金账户，怎么防止给用户创建资金账户多个，那么给资金账户表中的用户ID加唯一索引，所以一个用户新增成功一个资金账户记录。要点：唯一索引或唯一组合索引来防止新增数据存在脏数据（当表存在唯一索引，并发时新增报错时，再查询一次就可以了，数据应该已经存在了，返回结果即可）；这种情况是从数据库的层面进行限定，比如在第二次进行新增账户的时候发现表中已经存在这个id的记录了就不会再添加了，因此在多次提交操作之后的结果也是一样的，表中只有一条记录。
4. token机制，防止页面重复提交。业务要求： 页面的数据只能被点击提交一次；发生原因： 由于重复点击或者网络重发，或者nginx重发等情况会导致数据被重复提交；解决办法： 集群环境采用token加redis(redis单线程的，处理需要排队)；单JVM环境：采用token加redis或token加jvm内存。处理流程：

【1、服务端提供了发送token的接口。我们在分析业务的时候，哪些业务是存在幂等问题的，就必须在执行业务前，先去获取token，服务器会把token保存到redis中。

2、然后调用业务接口请求时，把token携带过去，一般放在请求头部。

3、服务器判断token是否存在redis中，存在表示第一次请求，然后删除token,继续执行业务。

4、如果判断token不存在redis中，就表示是重复操作，直接返回重复标记给client，这样就保证了业务代码，不被重复执行。】

或者说是：

   \1. **生成全局唯一的token,token放到redis或jvm内存,token会在页面跳转时获取.存放到pageScope中,支付请求提交先获取token**

   **2. 提交后后台校验token，执行提交逻辑,提交成功同时删除token，生成新的token更新redis ,这样当第一次提交后token更新了,页面再次提交携带的token是已删除的token后台验证会失败不让提交**

1. 1. 

  5. 悲观锁——获取数据的时候加锁获取。select * from table_xxx where id='xxx' for update; 注意：id字段一
     定是主键或者唯一索引，不然是锁表，会死人的悲观锁使用时一般伴随事务一起使用，数据锁定时间可能会
     很长，根据实际情况选用；

2. 乐观锁——乐观锁只是在更新数据那一刻锁表，其他时间不锁表，所以相对于悲观锁，效率更高。乐观锁的
    实现方式多种多样可以通过version或者其他状态条件：1. 通过版本号实现update table_xxx set
    name=#name#,version=version+1 where version=#version#如下图(来自网上)；2. 通过条件限制 update
    table_xxx set avai_amount=avai_amount-#subAmount# where avai_amount-#subAmount# >= 0要求：
    quality-#subQuality# >= ，这个情景适合不用版本号，只更新是做数据安全校验，适合库存模型，扣份额和回滚份额，性能更高；