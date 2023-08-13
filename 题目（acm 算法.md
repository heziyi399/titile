---



---

# 木棒三角形

# （枚举）

```c++
#include <iostream>
#include<stdlib.h>
using namespace std;
int main()//木棒三角形 有n根木棍 挑出其中三根构成直角三角形 输出面积最大的三角形面积 输入n再输入每根三角形长度,n<100
{
	int n;//输入n根木棍 再分别输入每根木棍的长度 限制了n数量小于100
	int len[110];//每个数组元素储存木棍长度 且要求长度由小到大给出
	while (scanf("%d", &n) != EOF) 
	{
		for (int i = 0; i < n; i++)
			cin >> len[i];
		double ans = -1;
		for (int i = 0; i < n ; i++)//枚举最短木棍
			for (int j = i + 1; j < n ; j++)
				for (int z = j + 1; j < n ; j++)
				{
					if(len[i]*len[i] + len[j]*len[j]==len[z]*len[z])
					{
						if ((len[i] * len[i] + len[j] * len[j] + len[z] * len[z]) > ans)
							ans = 0.5 * len[i] * len[j];
					}
				}
		if (ans == -1)
			cout << "no !";
		else
			cout << ans;
	}
}
```

# 顺序查找

```c++
/顺序查找 从第一个开始查找 关键字与给定值相比较 如果相等则退出
	int order(int array[], int n, int key)
	{
		int i;
		array[n] = key;//监视哨
		for (i = 0; array[i] != key; i++);//分号不可少
		return (i < n ? i : -1);
	}
```

# 希尔排序

```c++
struct node {
	int key;//简化
}a[20];
int n;//全局变量 排序的数字数目
void print()
{
	for (int i = 0; i < n; i++)
		cout << a[i].key << " ";
}
void create()
{
	int i; n = 0;
	cout << "input keys:";
	cin >> i;
	while (i != -999)
	{
		cin >> i;
		a[n].key = ; n++;
	}
}

void shell(node a[], int n)
{
	int k = n / 2;
	for (int i = n; i >=1; i--)
		a[i].key = a[i-1].key;
	while (k>=1)
	{
		for (int i = k + 1; i <= n; i++)
		{
			a[0].key = a[i].key; int j = i - k;
			
				while(a[j].key>a[0].key&&(j>=0))
				{
					a[j + k].key = a[j].key; j -= k;
				}
				a[j + k] = a[0];
			
		}
		k /= 2;
	}
	for (int i = 0; i <= n; i++)
		a[i].key = a[i + 1].key;
	cout << endl;
	}




int main()
{
	create(); print();
		shell(a,n); print();
}
```

## 希尔排序2

java实现：

```java
   public static void sort(Comparable a[])
	    {
	    int n = a.length;
	    int h =1;
	    while(h < n/3) h = 3*h+1;//h可以任意设置
	    while(h>=1)
	    {//将数组变为h有序
	    	for(int i = h;i < n;i++)
	    	{
	    		for(int j = i;j >=h;j -=h)
	    			if(less(a[j],a[j-h]))
	    			{
	    				exch(a,j,j-h);
	    			}
	    	}
	    	h /=3;
	    }
	    }
	    
```





# 折半查找

也叫二分查找 可以在最坏的情况下用O(logn)完成任务

```c++
int binarysearch(int array[], int key, int n)
{
	int left = 0;
	int right = n - 1;
	while (left <= right)
	{
		int middle = (left + right) / 2;
		if (array[middle] == key)
			return middle;
		if (left > array[middle])
			left = middle + 1;
		if (right < array[middle])
			right = middle - 1;
	}
	return -1;
}
```

# /字符串统计

每组测试输出两个正整数 第一个是表示重复的次数，第二次是在该重复次数下有几种不同的字符串

```c++
using namespace std;
struct abc
{
	 char str[20];
	///int num;
}que[20000];
int cmp(const void* a, const void* b)
{
	abc* f1 = (abc*)a;
	abc* f2 = (abc*)b;
	return strcmp(f1->str, f2->str);//排序函数用于在qsort函数中将字符串从小到大排序 可以根据cmp的写法来确定从大到小还是从小到大
}
//qsort函数的基本用法：qsort(que,n,sizeof(que[0]),cmp)que为需要排序的序列首地址
//n为序列的元素 sizeof为序列中单个元素所占空间的大小 cmp为排序过程中用到的比较函数 有-1、0、1三种返回值
int main()
{
	int count[20000];//存放种类数 其中[]中的数值是重复的次数
	int n;
	int number = 1;
	while (cin >> n)
	{
		for (int i = 0; i < n; i++)
		{
			cin >>que[i].str;
			
			count[i] = 0;
		}


		qsort(que, n, sizeof(que[0]), cmp);
		////如果后一个元素等于前一个元素则出现次数加一
		int i = 1;
		//while(i < n)
		for (int i = 0; i < n - 1; i++)
		{
			if (strcmp(que[i].str, que[i+1].str) == 0)//比较两个字符串是否相等 不要用==
			{
				number++;
		
				continue;
			}
			
				count[number]++;//如果不相等了 再加上最后这一位本身
				number = 1;
			
			//恢复number
		}
		count[number]++;//
		for (int i = 1; i < n; i++)
		{
			cout << i << " :" << count[i] << "";
			cout << endl;
		}
	}
	
}
```

# 

# 递归:

```c++
//求解组合问题 1~n中任取r个数 求所有组合
//输出一个组合
int r;//全局变量
void display(int a[])
{
	for (int i = 0; i < r; i++)
		cout << a[i]<<" ";
	cout << endl;
}
void mm(int a[], int n, int r)
{

	for (int i = n; i >= r; i--)
	{
		a[r - 1] = i;
		if (r > 1)
			mm(a, i - 1, r - 1);
		else
			display(a);
	}
}
		




int main() {

	int n;
	int a[8];
	
	cin >> n >> r;
	mm(a, n, r);

}
```





# 数组。

## 设计算法高效将数组的奇数元素移到偶数元素后面



```c++
//设计算法尽可能高效地将所有奇数元素移动到偶数元素前面
//设置两个指针 ij i=0,j = n-1,当ij不相等时循环 a[i]找偶数元素 a[j]找奇数元素 当i!=j时发生交换

void swapp(int a[], int n)
{
	int i = 0, j = n - 1;
	int temp;
	while (i != j)
	{
		j--;
		if (a[j] % 2 == 1)
		{
			for (; i != j; i++)
			{
				if (a[i] % 2 == 0 && a[j] % 2 == 1 && i != j)
				{
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
					i++;
					break;

				}
			}
		}

	}
}
int main()
{

	m1 = 3;
	int m[] = { 1,2,3,4,4,5,6 };
	swapp(m, 7);
	for (int i = 0; i < 7; i++)
		cout << m[i];
	
}
```





## 编程将一个字符串中所有空格替换为“%20”



![image-20210515011104714](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210515011104714.png)

```c++
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include<cstring>



using namespace std;


const int maxd = 20;//最大深度
int m[1 << maxd];//最大结点个数为2的maxd次方-1

//编程将字符串中的空格用%20代替，并返回替换后的总长度

int Replace(char* p, int n) {
	//n为源字符串的总字符个数
	int num = 0;//空格个数
	for (int i = 0; i < n; i++) {
		if (p[i] == ' ') {
			num++;

		}
	}
	int TotalLength = n + num * 2;//一个空格用三个字符替代
	p[TotalLength] = '\0';//字符串结尾符号
	int k = TotalLength - 1;
	for (int i = n - 1; i >= 0; i--) {
		//从后向前扫描
		if (p[i] == ' ') {
			p[k] = '0';
			p[k - 1] = '2';
			p[k - 2] = '%';
			k = k - 3;
		}
		else {
			p[k] = p[i];
			k--;
		}
	}
	return TotalLength;
}
int main()
{
	char* str;
	str = new char[200];//分配足够的空间
	strcpy(str, "my name is");
	int n = strlen(str);
	cout<<"total:"<<Replace(str, n);
	cout << str;


}

```









## 以第一个元素为基准 大于该基准的移到后面



```c++
//以a[0]为基准将所有大于a[0]的元素移到该基准后面 小于等于的元素移到该基准前面 得到一个新的数组
void swapp(int a[], int n)
{
	int temp;
	int num = a[0];
	int j = n - 1;//a[j]扫描小于a[0]的 a[i]扫描大于a[0]的 两者发生交换
	int i = 0;
	while (i != j) 
	{
		j--;
		if(a[j] < num)

		for (; i != j; i++)
		{
			if (a[i] >= num)
			{
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				i++;
				break;

				
			}
		}
	}
}
```



## //删除一个已排序好的整数数组的重复元素 返回a[](算法效率问题)

```c++
//删除一个已排序好的整数数组的重复元素 返回a[](算法效率问题)
//重建法
int* delee(int a[], int n)
{
	int k = 0;
	for (int i = 0; i < n; i++)
	{
		if (a[i] != a[i + 1//如果a[i]不是重复的元素则将a[i]重新插入到a中
		{
			a[k] = a[i];
			k++;//保留的元素增一
		}
	}
	return a;
}
```

## //删除给定的有序整数数组 两个或两个以上的 重复元素仅仅保留两个

```c++
#include<iostream>
using namespace std;
int* delee(int a[], int& n)
{
	//删除给定的有序整数数组 两个或两个以上的 重复元素仅仅保留两个
	int k = 0;
	int b[30] = { 0 };
	for (int i = 0; i < n-1; i++)
	{
		if (a[i] != a[i + 1])
		{
			a[k] = a[i];
				k++;//保留的元素增一
		}
		if (a[i] == a[i + 1] && a[i] != a[i + 2] )
		{
			a[k] = a[i];
			
			a[k+1] = a[i + 1];
			k += 2;
			i += 1;
		}
	}
	n = k;//更新数组的个数 用引用传递 这样在主函数中可以获得新数组的大小
	return a;
}
int main()
{
	int a[20] = { 2,2,2,2,3,3,3,4,5,6,7,7,8,8,8 };
	int k = 16;
	int* b= delee(a,k);
	for (int i = 0; i < k; i++)
		cout << b[i] << " ";
}
```

方法二：

用一个变量记录重复元素的个数，另一个变量记录当前要修改的索引位置

```java
public int removeDuplicates(int[] nums) {
int cnt = 0, cur = 1;
for (int i = 1; i < nums.length; ++i) {
if (nums[i] == nums[i - 1]) ++cnt;
else cnt = 0;
if (cnt < 2) nums[cur++] = nums[i];
}
return cur;
}
```



## 找出数组中重复的数字

```java
public int findRepeatNumber(int[] nums) {
for (int i = 0, n = nums.length; i < n; ++i) {
while (nums[i] != i) {
if (nums[i] == nums[nums[i]]) return nums[i];
swap(nums, i, nums[i]);
}
}
return -1;
}
private void swap(int[] nums, int i, int j) {
int t = nums[i];
nums[i] = nums[j];
nums[j] = t;
}
```



## 旋转数组

给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。

若 k=3 ， nums=[1,2,3,4,5,6,7] 。
先将 nums 整体翻转： [1,2,3,4,5,6,7] -> [7,6,5,4,3,2,1]
再翻转 0~k-1 范围内的元素： [7,6,5,4,3,2,1] -> [5,6,7,4,3,2,1]
最后翻转 k~n-1 范围内的元素，即可得到最终结果： [5,6,7,4,3,2,1] -> [5,6,7,1,2,3,4]

```java
  public void rotate(int[] nums, int k) {
if(nums == null) return;
k %=nums.length;
if(nums.length<2||k==0)return;
rotate(nums,0,nums.length-1);
rotate(nums,0,k-1);
rotate(nums,k,nums.length-1);
    }
     public void rotate(int[] nums, int begin,int end)
    {
        if(begin>=end) return;
        while(begin<end)
        {
            int temp = nums[begin];
            nums[begin] = nums[end];
            nums[end] = temp;
            begin++;
            end--;
        }
    }
```

## [154. 寻找旋转排序数组中的最小值 II](https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array-ii/)

已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,4,4,5,6,7] 在变化后可能得到：
若旋转 4 次，则可以得到 [4,5,6,7,0,1,4]
若旋转 7 次，则可以得到 [0,1,4,4,5,6,7]
注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。

给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。

你必须尽可能减少整个过程的操作步骤。

-------------



![image-20220803172902810](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220803172902810.png)

![image-20220803172919187](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220803172919187.png)

![image-20220803172951338](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220803172951338.png)

![image-20220803173014448](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220803173014448.png)





```java
    public int findMin(int[] nums) {
int low = 0,high = nums.length-1;
while(low < high){
    int mid = low +(high - low)/2;
    if(nums[mid] < nums[high]){
        high = mid;
    }
    else if(nums[mid] > nums[high])
    low = mid +1;
    else high =high-1;

}
return nums[low];
    }
```

这里注意：如果数组中的元素互不相同，其他条件不变，则可以去掉上面的  else high =high-1;判断（不存在nums[mid]=nums[high]的情况）



## 螺旋数组

```java
  List<Integer>luo = new ArrayList<>();
    public List<Integer> spiralOrder(int[][] matrix) {
if(matrix==null||matrix.length==0) return luo;
        int m = matrix.length, n = matrix[0].length;
      int i1 = 0,i2 = matrix.length-1;
      int j1 = 0,j2 = matrix[0].length-1;
      while (i1<=i2&&j1<=j2)
      {
          addnum(matrix,i1++,i2--,j1++,j2--);
      }
return luo;
    }
      private void addnum(int[][] matrix,int i1, int i2, int j1, int j2) {
if(i1==i2) {
    for (int i = j1; i <= j2; i++) {
        luo.add(matrix[i1][i]);
       
    }
     return;
}
    if(j1 == j2)
    {
        for (int i = i1; i <= i2; i++) {
            luo.add(matrix[i][j1]);
       
        }
             return;
    }
    for (int i = j1; i < j2; i++) {
        luo.add(matrix[i1][i]);

    }
    for (int i = i1; i <i2 ; i++) {
        luo.add(matrix[i][j2]);
    }
    for (int i = j2; i >j1 ; i--) {
        luo.add(matrix[i2][i]);
    }
    for (int i = i2; i >i1 ; i--) {
        luo.add(matrix[i][j1]);
    }

}
```



## 两数之和

题目描述
给定一个整数数组 nums 和一个目标值 target ，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
示例

给定 nums = [2, 7, 11, 15], target = 9
因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

```java
public int[] twoSum(int[] nums, int target) {
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0, n = nums.length; i < n; ++i) {
int num = target - nums[i];
if (map.containsKey(num)) {
return new int[]{map.get(num), i};
}
map.put(nums[i], i);
}
return null;
}
```



## 四数之和
题目描述
给定一个包含 n 个整数的数组 nums 和一个目标值 target ，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
注意：
答案中不可以包含重复的四元组。

排序 + 双指针”实现。
continue;
}
if (q < n - 1 && nums[q] == nums[q + 1]) {
--q;
continue;
}
if (nums[p] + nums[q] + nums[i] < 0) {
++p;
} else if (nums[p] + nums[q] + nums[i] > 0) {
--q;
} else {
res.add(Arrays.asList(nums[p], nums[q], nums[i]));
++p;
--q;
}
}
}
return res;
}
}
给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
满足要求的四元组集合为：
[
[-1, 0, 0, 1],
[-2, -1, 1, 2],
[-2, 0, 0, 2]
]

```java
class Solution {
public List<List<Integer>> fourSum(int[] nums, int target) {
int n;
if (nums == null || (n = (nums.length)) < 4) {
return Collections.emptyList();
}
Arrays.sort(nums);
List<List<Integer>> res = new ArrayList<>();
for (int i = 0; i < n - 3; ++i) {
if (i > 0 && nums[i] == nums[i - 1]) {
continue;
}
for (int j = i + 1; j < n - 2; ++j) {
if (j > i + 1 && nums[j] == nums[j - 1]) {
continue;
}
int p = j + 1, q = n - 1;
while (p < q) {
if (p > j + 1 && nums[p] == nums[p - 1]) {
++p;
continue;
}
if (q < n - 1 && nums[q] == nums[q + 1]) {
--q;
continue;
}
int t = nums[i] + nums[j] + nums[p] + nums[q];
if (t == target) {
res.add(Arrays.asList(nums[i], nums[j], nums[p],
nums[q]));
++p;
--q;
} else if (t < target) {
++p;
} else {
--q;
}
}
}
}
return res;
}
} 
```



## 较小的三数之和

题目描述
给定一个长度为 n 的整数数组和一个目标值 target，寻找能够使条件 nums[i] + nums[j] + nums[k]< target 成立的三元组 i, j, k **个数**（ 0 <= i < j < k < n ）。
示例：
进阶：是否能在 O(n2) 的时间复杂度内解决？
解法
双指针解决。

```java
public int threeSumSmaller(int[] nums, int target) 
{
    Arrays.sort(nums);
    int count = 0;
    for (int i = 0; i < nums.length; i++) 
    {
 count +=threeSumSmaller(nums,i+1,nums.length-1,target-nums[i]);
    }
    return count;
}
public int threeSumSmaller(int[] nums, int begin,int end,int target) 
{
    int count = 0;
    while (begin<end)
    {
        if(nums[begin]+nums[end] < target)
        {
            count+=end-begin;
            begin++;
        }
        else end--;
    }
    return count;
}
```



## 三数之和

给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。

注意：答案中不可以包含重复的三元组。



```java
  public List<List<Integer>> threeSum(int[] nums) {

  int num = nums.length;

​    Arrays.sort(nums);

 List<List<Integer>> res = new ArrayList<List<Integer>>();



​    for(int first = 0;first < num;++first)

​    {

​      if(first > 0 && nums[first] == nums[first-1]) continue;

​      int c = -nums[first];

​      int third = num-1;

​      for(int second = first+1;second < num;second++)

​      {

​        if(second > first+1 && nums[second] == nums[second-1]) continue;

​        while (third > second && nums[third] + nums[second] > c) --third;

​        if(second == third) break;

​        if(nums[third]+nums[second] == c) {

​          List<Integer> newin = new ArrayList<Integer>();

​          newin.add(nums[first]);

​          newin.add(nums[second]);

​          newin.add(nums[third]);

​          res.add(newin);



​        }

​      }

​    }

​    return res;
```



  }

## 最接近的三数之和

题目描述
给定一个包括 n 个整数的数组 nums 和 一个目标值 target 。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。

```java
 public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
     int res=0,diff=Integer.MAX_VALUE;
        for (int i = 0; i < nums.length-2; ++i) {
            int t = threeSumClosest(nums,i+1,nums.length-1,target-nums[i]);
            if(Math.abs(nums[i]+t-target)<diff)
            {
                res = t+nums[i];
                diff = Math.abs(nums[i]+t-target);
            }
        }
        return res;
    }
    public int threeSumClosest(int[] nums,int begin,int end, int target) {
        int res=0,diff = Integer.MAX_VALUE;
        while (begin<end)
        {
            int val=nums[begin]+nums[end];
            if(nums[begin]+nums[end]==target) return target;
            if(Math.abs(val-target)<diff)
            {
                res = val;
                diff = Math.abs(val-target);
            }
            if(val < target)
              ++begin;
            else
               --end;
        }
        return res;
    }
```

另一种写法：

```java
  public int threeSumClosest(int[] nums, int target) {
Arrays.sort(nums);

int ans =  nums[0]+nums[1]+nums[2];
int minsum = nums[0]+nums[1]+nums[2];
int best = 10000;
if(nums.length==3) return nums[0]+nums[1]+nums[2];
int absmin = Math.abs(target - minsum);
for(int i =0;i <nums.length;i++)
{
   if(i>0 && nums[i]==nums[i-1])
   continue;
   int j = i+1,z = nums.length-1;
   while(j <z)
   {
       int sum = nums[i]+nums[j] +nums[z];
       if(sum == target)
       return target;
       else if(sum >target)
       {
           if(Math.abs(sum - target) < Math.abs(best - target))
           best = sum;
           int z0 = z-1;
           while(z >=0 && nums[z0]==nums[z])
           z--;//排除重复
           z = z0;
         
       }
       else{
             if(Math.abs(sum - target) < Math.abs(best - target))
           best = sum;
           int j0=j+1;
           while(j0 < nums.length && nums[j0]==nums[j])
          
          j0++;
          j = j0;
       }
   }
   
}
return best;
    }
```





## 合并两个有序数组

给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 num1 成为一个有序数组。
说明:
初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
示例:

输入:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6], n = 3
输出: [1,2,2,3,5,6]



关键：从后往前填数字

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
int i = m-1,j = n-1,k=m+n-1;
while(j >=0)
{
if(i>=0&&nums1[i]>nums2[j])
    nums1[k--] = nums1[i--];
else nums1[k--] = nums2[j--];
}
    }
```





## ❗除自身以外数组的乘积

```java
  public int[] productExceptSelf(int[] nums) {
int[]arr = new int[nums.length];

        for (int i = 0,left=1; i < nums.length; i++) {
            arr[i] = left;
            left*=nums[i];
        }
        for (int i =nums.length-1,right = 1; i >=0;i--) {
            arr[i]*=right;
            right*=nums[i];
        }
        return  arr;

    }
```







## 移除值为val的元素

```java
public int removeElement(int[] nums, int val) {

int cnt = 0, n = nums.length;
for (int i = 0; i < n; ++i) {
if (nums[i] == val) {
++cnt;
} else {
nums[i - cnt] = nums[i];
}
}
return n - cnt;
}


```





## 删除排序数组中的重复项 每个元素只出现一次

题目描述
给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
不要使用额外的数组空间，你必须在 原地修改输入数组 并在使用 O(1) 额外空间的条件下完成。

```java
public int removeDuplicates(int[] nums) {
int cnt = 0, n = nums.length;
for (int i = 1; i < n; ++i) {
if (nums[i] == nums[i - 1]) ++cnt;
else nums[i - cnt] = nums[i];
}
return n - cnt;
}
```



## //假设数组a中有m+n个元素空间其中0~m-1存放m个升序 数组b存放n个降序整数 不借助其他数组将这些元素升序存放到a中





```c++
//采用二路归并产生升序数组，用i从a[m-1]的元素开始向前扫描 j从前向后扫描 k=m+n-1指向最后一个位置
void shengxu(int a[], int m,int b[], int n)
{
	int l = 0;
	int r = m - 1;
	int k = m+n-1;
	while (r >= 0 && l < n)
	{
		if (b[l] > a[r])//如果b更大
		{
			a[k] = b[l];
			k--;
			l++;
		}
		else
		{
			a[k] = a[r];
			r--;
			k--;

		}
		while (r >= 0)//此时a的前半部分没有扫完
		{
			a[k] = a[r];
			k--;
			r--;
		}
		while (l < n)//b的后部分没有扫完
		{
			a[k] = b[l];
			l++;
			k--;
		}
}
	
}
//上述算法时间复杂度为m+n空间复杂度为o(1)
int main()
{
	int a[31] = { -2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,19,20,21 };
	int b[10] = { 8,7,6,5,4,3,2,1,-3,-4};
	shengxu(a, 20, b, 10);
	for (int i = 0; i < 30; i++)
		cout << a[i] << " ";
}
```

 

## 高效判断两个数组是否存在相同元素



```c++
//采用二路归并产生升序数组，用i从a[m-1]的元素开始向前扫描 j从前向后扫描 k=m+n-1指向最后一个位置
bool shengxu(int a[], int m,int b[], int n)
{
	int i = 0, j = 0;
	while (i < m && j < n)
	{
		if (a[i] == b[j])
			return true;
		else if (a[i] > b[j])
			j++;
		else
			i++;

	}
	return false;
}
```









# DFS深度优先搜索算法

油田问题

```c++

//油田问题 一个油田是由m*n个单元组成的矩形，有些里面有石油，一个采油机可以把与该单元油田相连的油田采完，问至少
//需要多少台采油机
//@表示有石油
//采用深度优先搜索算法 设变量many表示采油机 把整个地图搜索完毕 当遇到没有标号的油田时用深度优先搜索把与这块油田相连
//的其他油田全部进行标号
#include <iostream>

using namespace std;
int dfs(int i, int j);
int mmap[55][55] = { 0 };
char s[50][50] = { '0' };
int main()
{
	int m, n;
	cin >> m >> n;
	
	for(int i = 0;i < m;i++)
		for (int j = 0; j < n; j++)
		{
			cin >> s[i][j];
		}
	int many = 0;
	for (int i = 0; i < m; i++)
		for (int j = 0; j < n; j++)
		{
			if (mmap[i][j] == 0 && s[i][j] == '@')
				many += dfs(i, j);
		}
	cout << many;
}
int dfs(int i, int j)
{
	mmap[i][j] = 1;//搜索过的地方置为1
	if (mmap[i + 1][j] == 0 && s[i + 1][j] == '@')
		dfs(i + 1, j);
	if (mmap[i][j + 1] == 0 && s[i][j + 1] == '@')
		dfs(i, j + 1);
	if (mmap[i - 1][j] == 0 && s[i - 1][j] == '@')
		dfs(i - 1, j);
	if (mmap[i][j - 1] == 0 && s[i][j - 1] == '@')
		dfs(i, j - 1);//将该点周围上下左右四个点全部搜索并且标记为1
	return 1;//搜到一个符合条件的点时加一

}
```



## 2.油田问题 一个m行n列的字符矩阵 统计字符@组成多少个八连块 

```c++
//油田问题 一个m行n列的字符矩阵 统计字符@组成多少个八连块 DFS更容易编写  递归遍历它周围的@
//每次访问一个格子就给它写上一个“连通分量编号”，这样可以在访问之前检查它是否已经有了编号，避免多次访问
const int maxx = 105;
char pic[maxx][maxx];
int col, row, idx[maxx][maxx];//一个数组是编号一个数组是字符
void dfs(int r, int c, int id)//id是连通分量编号
{
	if (r < 0 || r >= row || c < 0 || c >= col) return;//越界检测
	if (pic[r][c] != '@' || idx[r][c] > 0) return;//如果不是@或者是已经访问过的格子
	idx[r][c] = id;//进行标记 将未访问过的含@格子编号 注意在一个八连块的格子编号相同
	for(int i = -1;i<=1;i++)
		for (int j = -1; j <= 1; j++)//上下左右寻找
		{
			if (i != 0 || j != 0)
				dfs(r + i, c + j, id);
		}
}

int main()
{
	
	
	cin >> col >> row;
	memset(idx, 0, sizeof(idx));
	for (int i = 0; i < row; i++)
		for (int j = 0; j < col; j++)
			cin >> pic[i][j];
	int cns = 0;
	for (int i = 0; i < row; i++)
		for (int j = 0; j < col; j++)
			if(pic[i][j] == '@' && idx[i][j] == 0)
			dfs(i, j, ++cns);
	cout << cns;
}
```

## 3.n件物品放入若干背包

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-19.png)

```c++
const int maxn = 30;
int c[maxn], w[maxn];
int v, n, maxvalue = 0;//v:背包容量 n:物品件数 最大价值maxvalue index:当前物品编号
void dfs(int index, int sumw, int sumc)
{//sumw为当前的背包容量 sumc为当前背包价值
	if (index == n) //已经完成对n件物品的选择 死路
	{
		if (sumw <= v && sumc > maxvalue)
			maxvalue = sumc;
		return;
	}
	dfs(index + 1, sumw, sumc);
	dfs(index + 1, sumw + c[index], sumc + w[index]);
}
int main()
{
	char m;
	cin >> n >> v;
	for (int i = 0; i < n; i++)
		cin >> c[i] ;//每件物品的重量和价值
	for (int i = 0; i < n; i++)
		cin >> w[i];
		}
```

<img src="C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-20 (1).png" style="zoom:150%;" />



# BFS广度优先搜索算法

## 1.基本概述

主要思想是建立一个队列，并把第一个顶点加入队列，之后每次都取队首结点进行访问，并把从该顶点出发能到达的未加入过队列的顶点全部加入队列，直到队列为空。

如果要遍历图，则要对所有连通块遍历。

## 2.bfs邻接矩阵实现

```c++
//bfs邻接矩阵版 遍历u所在的连通块

const int maxv = 1000;//最大顶点数
const int INF = 1000000000000;
int n, G[maxv][maxv];//n为顶点数
bool inq[maxv] = { false };//未访问过 初始化为false


void bfs(int u)
{
    queue<int>q;
    q.push(u);//第一个点入队
    inq[u] = true;//访问以后加上标记 
    while (!q.empty())//只要队列非空则进行循环
    {
        u = q.front();
        q.pop();//出队
        for (int v = 0; v < n; v++)
        {
            if (inq[v] == false && G[u][v] != INF)//如果u的邻接点未加入过队列
            {
                q.push(v);
                inq[v] = true;
            }

        }
    }

}
void bfstrave()//遍历整个图
{
    for (int u = 0; u < n; u++)
    {
        if (inq[u] == false)
            bfs(u);//枚举所有结点 如果u未加入过队列 遍历u所在的连通块
    }
}

```

## 3.邻接表法用vector<int>adj[maxv];//max[u]存从u出发可以到达的所有顶点

```c++
vector<int>adj[maxv];
void bfs(int u)
{
queue<int>q;
q.push(u);
inq[u]=true;
while(!q.empty())
{
int u = q.front();
q.pop();
for(int i = 0;i < adj[u].size;i++)
{
int v = adj[u][i];
if(inq[v]==false)
{
q.push(u);
inq[v]=true;//访问过以后改为true
}
}
}
}
```

## 单词接龙

图的套路问题：先判断queue是否为空进入最外层循环，然后先int sz = queue.size()获得队列的长度，再遍历队列的元素内层循环，在遍历用object ob = queue.poll(),获得队列中的元素。此时如果有“步数”需要求，是在最外层循环中加的，这样队列中内层循环取出的都属于“同一层级”

字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：

每一对相邻的单词只差一个字母。
 对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord 不需要在 wordList 中。
sk == endWord
给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。


示例 1：

输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
输出：5
解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。



```java
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 第 1 步：先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
        Set<String> wordSet = new HashSet<>(wordList);
    //如果Wordlist本身不含endword，直接返回0即可
if(!wordList.contains(endWord)||wordList.size()==0) return 0;
//为什么要用hashset:经过实验发现如果用list,时间复杂度会高很多而且导致超时，只能使用hashset,它底层是一个hashmap,判断Contains时为o(1)复杂度
        
        // 第 2 步：图的广度优先遍历，必须使用队列和表示是否访问过的 visited 哈希表
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        
        // 第 3 步：开始广度优先遍历，包含起点，因此初始化的时候步数为 1
        int step = 1;
        while (!queue.isEmpty()) {
            //while:外层循环
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                //for:内层循环
                // 依次遍历当前队列中的单词
                String currentWord = queue.poll();
                // 如果 currentWord 能够修改 1 个字符与 endWord 相同，则返回 step + 1
                if (changeWordEveryOneLetter(currentWord, endWord, queue, visited, wordSet)) {
                    return step + 1;
                }
            }
            step++;
        }
        return 0;
    }

    /**
     * 尝试对 currentWord 修改每一个字符，看看是不是能与 endWord 匹配
     *
     * @param currentWord
     * @param endWord
     * @param queue
     * @param visited
     * @param wordSet
     * @return
     */
    private boolean changeWordEveryOneLetter(String currentWord, String endWord,
                                             Queue<String> queue, Set<String> visited, Set<String> wordSet) {
        //尝试改变currentword中的每一个字母，看是否会与目标字符串相等，如果相等则直接返回，如果不相等则加入到visited数组中表示已经访问
        char[] charArray = currentWord.toCharArray();
        for (int i = 0; i < endWord.length(); i++) {
            // 先保存，然后恢复
            char originChar = charArray[i];
            for (char k = 'a'; k <= 'z'; k++) {
                if (k == originChar) {
                    continue;
                }
                charArray[i] = k;
                String nextWord =new String(charArray);
                if (wordSet.contains(nextWord)) {//如果这个变化了一个字母的nextword包含在字典中，代表是可以变化为这个字符串的【题目要求只能变成字典中的字符串】
                    if (nextWord.equals(endWord)) {
                        return true;
                    }
                    if (!visited.contains(nextWord)) {
                        queue.add(nextWord);
                        // 注意：添加到队列以后，必须马上标记为已经访问
                        visited.add(nextWord);
                    }
                }
            }
            // 注意记得要恢复
            charArray[i] = originChar;
        }
        return false;
    }


```



# 螺旋矩阵 子矩阵之和（递归和非递归算法）



![](C:\Users\14172\Pictures\数组.jpg)

![](C:\Users\14172\Pictures\数组2.jpg)

![](C:\Users\14172\Pictures\子矩阵.jpg)

![](C:\Users\14172\Pictures\子矩阵解答.jpg)



# 求一个a矩阵以（i,j)和(m,n)为对角线的子矩阵元素之和



```c++
//求一个a矩阵以（i,j)和(m,n)为对角线的子矩阵元素之和
int a[][100];
void sum(int a[][100], int b[][100], int m, int n)
{
    b[0][0] = a[0][0];
    for (int i = 1; i < m; i++)
        b[0][i] = b[0][i - 1] + a[0][i];
    for (int i = 1; i < m; i++)
        b[i][0] = b[i - 1][0] + a[i][0];
        
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; i++)
            b[i][j] = b[i - 1][j] + b[i][j - 1] - b[i - 1][j - 1] + a[i][j];//引入数组b b(i,j)存储的是以a[0][0] a[i][j]
    //为对角线的矩阵元素之和
}
int ziju(int b[][100], int s, int t, int m, int n)

{
    if(m == 0 && n == 0)
        return b[m][n];
    int sum = b[m][n] - b[m][t - 1] - b[s - 1][n] + b[s - 1][t - 1];
    return sum;
}

```





## 递归和非递归创建螺旋矩阵

```c++
//创建一个n阶螺旋矩阵并输出
//对于左上角（ix,iy)右下角(ex,ey)起始元素为start的那一圈，通过调用函数1产生 函数2用于创建整个螺旋矩阵
//设f(x,y,start,n)用于创建左上角为(x,y)起始元素为start的n阶螺旋矩阵，是大问题，则f(x+1,y+1,start,n-2)是小问题
//（去掉最外面一大圈后剩下的部分）
//有递归算法和非递归算法
int a[15][15] = { 0 };
void create(int& start,int ix,int iy,int ex,int ey)//最外面的大圈
{
    if (ix == ex)
        a[ix][iy] = start++;//当该圈只有一个元素时
    else
    {
        int curl = iy;
        while (curl != ey)
            a[ix][curl++] = start ++;
        curl = ix;
        while (curl != ex)
            a[curl++][ey] = start++;
        curl = ey;
        while (curl != iy)
            a[ex][curl--] = start++;
        curl = ex;
        while (curl != ix)
            a[curl--][iy] = start++;
    }
    
}
void spira(int x,int y,int n,int start)//递归调用螺旋矩阵
{
    if (n <= 0)//退出递归条件
        return;
    if (n == 1)
    {
        a[x][y] = start;//矩阵大小为1时
        return;
    }
    else
    {
        for (int i = y; i < y+n-1; i++)//上一行
            a[x][i] = start++;
        for (int i = x; i < x+n-1; i++)//右一列
            a[i][x +n-1] = start++;
        for (int i = x +n-1;i > y; i--)//下一行
            a[x + n-1][i] = start++;
        for (int i =x+ n-1; i > x; i--)//左一列
            a[i][y] = start++;
        spira(x+1,y + 1, n-2,start);//递归调用自身
    }

}
void spira2(int n)//非递归调用螺旋矩阵
{
    int start = 1;
  
        int i = 0, j = 0, ex = n - 1, ey = n - 1;
        while (i <= ex)
            create(start, i++, j++, ex--, ey--);// 不断循环调用创建最外圈元素的函数
    
}
void display(int n)
{
    for (int i = 0; i < n; i++)
    { for (int j = 0; j < n; j++)
        
            cout << a[i][j] << " ";
    cout << endl;//输出
        }
}
```

![](C:\Users\14172\Pictures\屏幕截图 2020-10-28 210335.png)



## //有两个有序数组ab 元素个数m,n 设计一个高效算法求a和b的差集

```c++
//设a-b的元素数组为c
int a[5], b[5];
int c[8] = { 0 };
void chaji(int m,int n,int a[],int b[])
{
    int i = 0, k = 0, j = 0;//k来维护c中元素个数
    while (i < m && j < n)//注意这个while循环很重要不能遗漏否则无法得到正确结果
    {
        if (a[i] < b[j])
        {
            c[k] = a[i];
            i++;
            k++;//只将a中较小的元素放入c中
        }
        else if (a[i] > b[j])//如果b中元素更小则跳过
        {
            j++;
        }
        else//如果元素相等 不能放入C中 下标加一
        {
            i++;
            j++;
        }
    }
        while (i < n)//注意要将如果a没有遍历完则全部放入C中
        {
            c[k] = a[i];
            i++;
            k++;
        }
    
}
int main()
{


    
    int a[5] = { 3,4,9,10,77};
    int b[5] = { 2,3,5,6,4 };
    chaji(5, 5, a, b);
    for (int i = 0; i < 8; i++)
        cout << c[i]<<" ";
}
```





# 基于链表的算法设计

- 通常单链表是带头结点的结构，单链表如果有头结点，通常用头结点的地址即头指针来标识整个单链表，第一个**数据结点**称为首结点，指向首结点的指针称为首指针，
- **头结点**是在链表的首元结点之前附设的一个结点；数据域内只放空表标志和表长等信息，它不计入表长度。
   **首元结点**是指链表中存储线性表第一个数据元素a０的结点。
   其中头指针只是指针，它指向头结点或首元结点所对应的地址，在程序中，并没有给它分配固定的内存；而头结点和首元结点却对应着固定的内存。头结点和首元结点的区别在于数据域中是否存放了数据元素，头结点中的数据域为空，或存放表长信息，引入它是为了方便链表的插入和删除操作；而首元结点的数据域中会存储第一个数据元素的值。
- ![](C:\Users\14172\Pictures\==.png)



- Head指针为单链表的头指针，单链表L：L既是单链表的名字，也是其头指针。链表中的最后一个结点的指针域定义为空指针(NULL)。那么什么是头指针呢？我们把**指向第一个结点的指针称为头指针**，那么每次访问链表时都可以从这个头指针依次遍历链表中的每个元素，例如：

- struct node first;

  struct node *head = &first;这个head指针就是头指针。
   这个头指针的意义在于，在访问链表时，总要知道链表存储在什么位置（从何处开始访问），由于链表的特性（next指针），知道了头指针，那么整个链表的元素都能够被访问，也就是说头指针是必须存在的。示例如下

  ```c++
  示例如下：
  
  
  
  #include <stdio.h>  
    
  struct node {  
      int data;  
      struct node *next;  
  };  
    
  int main(void)  
  {  
      struct node *head, first, second;  
    
      head = &first;  
      first.data = 1;  
      first.next = &second;  
        
      second.data = 2;  
      second.next = NULL;  
        
      while (head) {  
          printf("%d\n", head->data);  
          head = head->next;  
      }  
      return 0;  
  }  
  ```

  需要着重注意的是while那部分（通过头指针遍历完整个链表）。   

   

  单链表有带头结点和不带头结点之分。

  ![](C:\Users\14172\Pictures\111.jpg)

```c++
1.单链表的初始化，即建立一个空链表。
[plain] view plain copy


//不带头结点的单链表的初始化  
void LinkedListInit1(LinkedList L)  
{  
  L=NULL;  
}  
//带头结点的单链表的初始化  
void LinkedListInit2(LinkedList L)  
{  
  L=(LNode *)malloc(sizeof(LNode));  
  if(L==NULL)  
  {  
    printf("申请空间失败！");  
    exit(0);  
  }  
  L->next=NULL;  
}  
 


```

那么什么又是头结点呢？很多时候，会在链表的头部附加一个结点，该结点的数据域可以不存储任何信息，这个结点称为头结点，
头结点的指针域指向第一个结点，例如：

 **头结点有指针域，头指针只是一个指针**

struct node head, first;

head.next = &first;

那么这里的头指针又是谁呢，不再是指向第一个结点的指针，而是指向头结点的指针，例如：

```html
struct node *root = &head;
```

 

即root指针才是头指针。示例如下：



1. \#include <stdio.h> 
2.  
3. **struct** node { 
4.   **int** data; 
5.   **struct** node *next; 
6. }; 
7.  
8. **int** main(**void**) 
9. { 
10.   **struct** node *root, head, first, second; 
11.    
12.   root = &head; 
13.   root->data = 0; 
14.   root->next = &first; 
15.    
16.   first.data = 1; 
17.   first.next = &second; 
18.    
19.   second.data = 2; 
20.   second.next = NULL; 
21.    
22.   **while** (root) { 
23. ​    printf("%d\n", root->data); 
24. ​    root = root->next; 
25.   } 
26.    
27.   **return** 0; 
28. } 

## 1.删除非空链表中值最大的结点

```c++
struct linklist
{
	int data;
	linklist* next;
};
void shanchu(linklist* p)
{
	linklist* head = new linklist;
	p = head->next;
linklist* L = head;//p的前驱结点
	int max = p->data;
	while (p != NULL)//查找最大的结点
	{
		p = p->next;
		if (p->data > max)
			max = p->data;
	}
	p = head->next;
	while (p != NULL)
	{
		if (p->data == max)
		{
			L->next = p->next;
			delete p;
			p = L->next;//让p继续指向L结点的后继结点
		}
		else
		{
			L = p;
			p = p->next;//L和p都同时前移
		}
	}
}
```

## 2.将两个递增有序单链表合并为一个递减有序单链表

空间复杂度为o(1)的头插法:

```c++
struct linknode
{
	int data;
	linknode* next;
};
void guibing(linknode* &L1, linknode* &L2, linknode*& L3)
{
	linknode* p1 = L1->next,*p2 = L2->next,*p3;
	delete L1;//释放L1头结点并置为NULL
	delete L2;
	L1 = NULL;
	L2 = NULL;
	L3 = new linknode;
	L3->next = NULL;
	while (p1 != nullptr && p2 != nullptr)
	{
		if (p1->data > p2->data)
		{
			p3 = p1->next;//临时保存p1结点的后继结点
			p1->next = L3->next;//采用头插法将p1插入到L3中
			L3->next = p1;
			p1 = p3;//p1指向下一个结点
		}
		else
		{
			p3 = p2->next;
			p2->next = L3->next;
			L3->next = p2;
			p2 = p3;
		}
	}
	if (p2 != nullptr)
	{
		p1 = p2;//如果p2没有扫完 让p1指向p2结点
	}
	while (p1 != nullptr)
	{
		p3 = p1->next;//临时保存p1结点的后继结点
		p1->next = L3->next;//采用头插法将p1插入到L3中
		L3->next = p1;
		p1 = p3;//p1指向下一个结点
	}
}

```

![](C:\Users\14172\Pictures\22.jpg)





将长度为n的单链表连接在长度为m的单链表之后，时间复杂度为m.（先通过遍历找到尾结点p 再让p结点的指针域指向长度为n的单链表首结点。）



## 3.递归算法逆置非空单链表

```c++
struct linknode
{
	int data;
	linknode* next;
};
//注意下面这个函数参数是值参数(对应的实参不变）而不是引用参数。如果改为引用是错误的
linknode* nizhi(linknode* first)//first为不带头结点的
{
	linknode* p;
	if (first == nullptr || first->next == nullptr)
		return first;
	p = nizhi(first->next);
    first->next->next = first;//first结点连接到first->next结点的后面 注意二者顺序
	first->next = NULL;//first结点作为逆置后的尾结点
	return p;
}
//引用作为参数
void nizhi1(linknode* &L)//L为带头结点的单链表
{
	L->next = nizhi(L->next);//L->next为不带头结点的单链表 调用第一个函数
}
```



## 4.逆置单链表中序号为i到j的结点

------

注意ij参数可能错误

```c++
struct linknode
{
	int data;
	linknode* next;
};
//逆置非空单链表中序号从i到j的结点
//为了防止i与j超过范围要先求出长度 且第二个函数为bool型
int length(linknode* m)
{
	int k = 0;
	linknode* p = m;
	while (p != nullptr)
	{
		p = p->next;
		k++;
	}
	return k;//先求出单链表长度
}
bool nizhiij(linknode* &L,int i,int j)
{
	linknode* r;
	int len = length(L);
	if (i < 0 || i > len || j < 0 || j> len)
		return false;
	else
	{
		int n = 0;
		linknode* p = L,*q;
			
		while (n < i - 1 && p != NULL)
		{
			n++;
			p = p->next;
		}//p为第i-1个结点
		linknode* m = p;
		linknode*r = p->next;
		p1 = r;//用p1保存逆置的第一个结点
		p->next = NULL;//断开第i和第i-1个结点
		while (n < j && r != NULL)
		{
            n++;
			q = r->next;//不断将r结点到第j个结点采用头插法插入到m结点之后
			r->next = m->next;
			m->next = r;
			r = q;
		}
		p1->next = q;//将第j个结点的后继结点接到p1结点之后
		return true;

	}
}
```

## 5.对于一个带头结点的单链表以第一个结点为基准 将所有小于其值的结点移动到它前面 将所有大于等于其值的结点移动到它后面

```c++
void yidong(linknode* a)
{
	linknode* p = a->next;//p指向首结点
	int data1 = p->data;//cache指向其后继结点 如果cache值小于p值 通过p将cache删除
	linknode* cache = p->next;
	if (a != nullptr || a->next == nullptr)
		return;
	while (cache != nullptr)
	{

		int da2 = cache->data;//da2存放基准值
		if (da2 >= data1)//此时p和cache同步后移
		{
			p = cache;
			cache = p->next;
		}
		else
		{
			p->next = cache->next;//如果cache结点的值小于基准值 删除cache结点 头插法查到表a中
			cache->next = a->next;
			a->next = cache;
			cache = p->next;//cache继续指向p结点的后继结点
		}
	}
}
```

## 6.设计一个算法求非空单链表中间位置结点

(1,2,3,4)的中间位置是结点2 （1，2，3，4，5）中间位置是5

```c++
//方法：用快慢两个指针 快指针一次移动两个结点 循环结束后slow指向的结点即目标结点
linknode* middle(linknode* L)
{
	if (L == nullptr)
		return NULL;
	if (L->next == nullptr)
		return L;
	linknode* fast = L->next,*slow = L->next;
	while (fast != nullptr && fast->next != nullptr)
	{
		fast = fast->next->next;
		slow = slow->next;
	}
	return slow;

}
```

### 尾插法

![](C:\Users\14172\Pictures\untitled.png)

![]()![image-20201031140417053](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20201031140417053.png)

![](C:\Users\14172\Pictures\21.png)

注意是r->next = L1->next;(注意这两个链表都有头结点)

## 7.将一个非空单链表（a1,a2,..an,b1,b2..bn)重新排列为(a1,b1,a2,b2...,an,bn)

```c++
//空间复杂度为O(1)
//采用尾插法 建立新表L r作为尾指针 利用上一题求中间结点的函数找到中间结点
void rearrange(linknode* &L)
{
	if (L == NULL)
		return;
	linknode* mid = middle(L);//此题结点个数是偶数
	linknode* r = L;//新链表的尾结点
	linknode* p = r->next;//p指向结点a1
	linknode* q = mid->next;//q指向结点b1
	while (p != nullptr && q != nullptr)
	{
	
		r->next = p;//p结点链接到新链表的表尾
		r = p;
		r->next = q;
		r = q;
		p = p->next;
		q = q->next;

	}
	r->next = NULL;//尾结点的指针域置空（尽管这里不需要，但这是一个好习惯）

}
```

## 8.设计一个算法判断一个非空单链表是否为回文

```c++
//非递归算法 找到中间结点 后半部分构成带头结点的单链表p 将p逆置 然后依次按照结点顺序判断数据是否相等

bool ispal(linknode* L)
{
	if (L->next == nullptr || L->next->next == nullptr)
		return true;
	linknode *q,*q2,* p = new linknode;
	linknode* mid = middle(L);//中间结点
	p->next = mid->next;//将后半部分结点构成带头结点的单链表
	
	reverse(p);//逆置带头结点的单链表
	mid->next = p->next;//重新连接
	q = p->next;
	q2 = L->next;
	while (q != nullptr && q2 != mid->next)
	{
		if (q->data !=q2->data)
			return false;
		q = q->next;
		q2 = q2->next;
	}
	return true;
}

```

## 9.判断序列B是否是A序列的子序列

```c++
//先在A中找到与B结点值相等的结点pa,pb指向B的首结点然后比较值是否相等 相等则同步后移
bool subseq(linknode* A, linknode* B)
{
	linknode* pa = A->next;
	
	while (pa != nullptr)
	{
		linknode* pb = B->next;//每次循环开始pb都是指向B的首结点
		while(pa->next != nullptr && pa->data != pb->data)//只用来求出与B第一个结点相等的A中结点pa 
			
		{
			pa = pa->next;
			
		}
		linknode* q = pa;//用q来保存pa  ,以便pa进行下一轮循环（如果进入下一轮循环仍要匹配A中与B第一个结点相等的其他结点）
		while (q != nullptr && pb != nullptr && q->data == pb->data)
		{
			q = q->next;//如果值相等同步移动
			pb = pb->next;
	}
			if (pb == nullptr)
				return true;
			else if(pa->next != nullptr)//如果A序列没有遍历完 ，则从下一个结点开始重复进行
			{
				pa = pa->next;
			}
		
	}
	return false;
}
```

## 不同颜色排列（数量未知） 【有无头结点的写法】

L有头结点 L1,L2无  

![](C:\Users\14172\Pictures\untitled.png)

![](C:\Users\14172\Pictures\00.png)



## 11.将三个递增序列合并为一个

```c++
//高效将三个递增有序单链表的所有结点归并为一个递增有序单链表
//三个有序单链表用L[3]指针数组标识，x[3]存放要比较的结点值，当i扫描完后x[i]取值为无穷
#define INF 32767
int Min(int x[], int k)
{
	int i ,mink = 0;//mink存放下标标号
	for (i = 0; i < k; i++)
	{

		if (x[i] < x[mink])
			mink = i;
	}
	if (x[mink] == INF)
		return -1;
	else
		return mink;
}

void merge3(linknode* L[3], linknode*& L1)//L1为结果单链表
{
	linknode* r1[3], * r;
	L1 = new linknode;
	r = L1;//r为尾结点指针
	int x[3];
	for (int i = 0; i < 3; i++)
		r1[i] = L[i]->next;//r1[i]指向L[i]单链表的首结点
	for (int i = 0; i < 3; i++)
		x[i] = (r1[i] != NULL )? r1[i]->data : INF;//先判断是否为空 非空则存放结点值
	
	while (true)
	{
		int k = Min(x, 3);//存储标号
		if (k == -1)//此时全部归并完毕
			break;
		else
		{
			linknode* s = new linknode;
			s->data = x[k];//新建一个结点 存放的数值是最小结点的数值
			r->next = s;
			r = s;//尾插法 最小结点值插到新表表尾
			r1[k] = r1[k]->next;//后移较小结点的扫描指针
			x[k] = (r1[k] != NULL)?r1[k]->data:INF;//先判断链表是否为空
		}
		
	}
	r->next = NULL;
}
```

## 12.设计一个算法由单链表A产生单链表B 

```c++
//非递归：扫描A的所有结点，通过复制来创建B的结点 尾插法将结点链接起来
void copy1(linknode* A, linknode* &B)
{
	linknode* a1 = A->next;
	
	B = new linknode;
	linknode* r = B;
	while (a1 != nullptr)//一边扫描A的结点一边复制
	{
		linknode* hb = new linknode;
		hb->data = a1->data;
		r->next = hb;//将hb结点连接到B的末尾
		hb->prior = r;
		r = hb;
		a1 = a1->next;
	}
	r->next = NULL;//尾结点next置空
}
//递归：若pa是A的首指针，首先创建双链表B的头结点hb(无数据）,由pa创建的递归模型如下:
//f(pa,hb,pb)==pb=null 当pa=null
void copy(linknode* pa, linknode* hb, linknode* &pb)
{
	if (pa == nullptr)
		pb = nullptr;
	else
	{
		pb = new linknode;
		pb->data = pa->data;
		pb->prior = hb;
		copy(pa->next, pb, pb->next);
	}
}
void copy2(linknode* A, linknode* &B)//从链表A产生链表B
{
	B = new linknode;//头结点
	copy(A->next, B, B->next);//A.next为首结点
}
```

## 13.找到有环单链表的入口处

```c++
//设从起点到环入口处为a 两者相遇点距入口处距离b
//先设置一个快慢指针 可以通过画图和数学转化得到当两者相遇时 再走a步就到环入口处 可再设一个从起点出发的指针
linknode* findcycle(linknode* L)
{
	linknode* slow = L;
	linknode* fast = L;
	while (fast != nullptr && fast->next != nullptr)
	{
		fast = fast->next->next;
		slow = slow->next;
		if (fast == slow)//相遇处
		{
			linknode* p = L;
			while (L != slow)//p slow都指向同一个结点时即入口处
			{
				L = L->next;
				slow = slow->next;
			}
			return slow;
		}
	}
}
```

## 14删除两个循环双链表中所有data相同的结点

```c++
bool deletenode(linknode*&phead,int value)//删除循环双链表中值为Value的结点 有则返回真
{
	if (phead == nullptr)
		return false;
	bool flag = false;
	linknode* pre = phead;
	linknode* p = pre->next;
	while (p != nullptr)
	{
		if (p->data == value)
		{
			p->next->prior = pre;//删除p指向的结点
			pre->next = p->next;
			delete p;
			p = pre->next;
			flag = true;
		}
		else
		{
			pre = p;//pre p指针
			p = p->next;//同步后移
	
		}
	}
	return flag;
}
void deletenode1(linknode* a1, linknode* a2)//a1,a2是头指针
{

	if (a1->next == a1 || a2->next == a2)//有一个表为空表时直接返回
		return;
	linknode* pre1 = a1, * p = pre1->next;
	linknode* pre2 = a2, * q = pre2->next;
	while (p!= a1)//扫描a1的结点p
	{
		int val = p->data;
		if (deletenode(q,val))
		{
			
			linknode* r = p;
			linknode* m = pre1;
			while (r != a1)//再扫描 删除表a1中所有这样的结点
			{
			
				if (r->data == val)
				{
					r->next->prior = m;
					m->next = r->next;
					free(r);
					r = m->next;

				}
				else
				{
					m = r;
					r = r->next;//同步后移
				}
			}
			p = pre1->next;//如果链表a1结点被删除了的话p要从上一个结点现在的下一个结点开始
		}
		else
		{
			pre1 = p;
			p = p->next;//p和指向p的前一个结点的指针同步后移


		}
	}

}
```

-  解法2

- ```c++
  //解放2 先链表排序 再二路归并
  void sort(linknode*& L)//双链表递增排序
  	{
  	linknode*q,*pre;
  	linknode* p = L->next->next;
  	L->next->next = L;
  	L->prior = L->next;//建立只有一个头结点的循环双链表
  	while (p != L)
  	{
  		pre = L;
  		while (p != L && pre->next->data < p->data)
  			pre = pre->next;//如果符合这个条件则pre指针后移继续搜寻 目的是找到能正确插入p结点的位置 找到后p插在pre结点之后
  		q = p->next;//要先保存p.next的地址
  		p->next = pre->next;
  		pre->next->prior = p;
  		pre->prior = p;
  		p->next = pre;
  		p = q;
  	}
  }
  void deletn2(linknode*& a1, linknode*& a2)
  {
  	int val;
  	
  	sort(a1);
  	sort(a2);
  	linknode* p1 = a1->next, * p2 = a2->next, * pre=a1,*prb=a2;
  	while (p1 != a1 && p2 != a2)
  	{
  		if (p1->data < p2->data)
  		{
  			pre = p1;
  			p1 = p1->next;
  		}
  		else if (p1->data < p2->data)
  
  		{
  			prb = p2;
  			p2 = p2->next;
  		}
  		else
  		{
  			val = p1->data;
  			while (p1 != a1)//删除a1中所有相同的结点
  			{
  				
  				if (p1->data == val)
  				{
  					
  					pre->next = p1->next;
  					p1->next->prior = pre;
  					delete(p1);
  					p1 = pre->next;
  				}
  				else break;//由于已经是排序之后 如果无相同元素则可退出
  			}
  			while (p2 != a2)//删除a2中所有相同的结点
  			{
  				if (p2->data == val)
  				{
  
  					prb->next = p2->next;
  					p2->next->prior = prb;
  					delete(p2);
  					p2 = prb->next;
  				}
  				else break;
  			}
  		}
  	}
  }
  ```

  

## 15.指针异常的链表翻转

![](C:\Users\14172\Pictures\异常链表.jpg)

```c++
struct node {
    node* p1;
    node* p2;
    int data;
};
//p2指针逆置：
void reverse(node*& p)
{
    node* r = p->p2;
    node* q;
    p->p2 = p;//建立一个空循环链表
    while (r != p)
    {
        q = r->p2;//临时保存p2方向的后继结点
        r->p2 = p->p2;//将r结点插入到首部
        p->p2 = r;
        r = q;//后移
    }
}
node* revert(node* head)//翻转链表head并返回头指针
{
    if (head->p1 == nullptr || head == nullptr)
        return NULL;
    reverse2(head);//逆置p1指针
    reverse(head);//逆置p2指针
    return head;
}
```

![](C:\Users\14172\Pictures\异常指针2.jpg)

### 删除一个双向链表中的所有结点 

```c++
//删除一个双向链表中的所有结点 有prior和next两个指针域 设p指向链表中的一个结点：
p->next->prior = p->prior;
p-prior->next = p->next;
free(p);

```

## 16.删除一个单链表中倒数第k个结点（倒数不是正数） 有头结点

```c++
//假设p q指向头结点 先移动p指针
bool delerk(node*& L, int k)
{
    node* p = L;
    node* q = L;
    int i = 0;
    while (p != nullptr)
    {
        i++;
        p = p->next;
    }
    //p是第k个结点
    if (p == nullptr) return false;
    while (p->next != nullptr)
    {
        q = q->next;
        p = p->next;
    }//退出循环时p指向尾结点
    p = q->next;//p此时指向倒数第k个结点
     p->next = q->next  ;
     q->next = p;
     delete p;//删除p结点
     return true;

}
```

## 17.直接插入法将一个单链表递增排序

```c++
struct node {
    node* next;
    
    int data;
};
//采用直接插入法将一个单链表递增排序
void sort(node*& L)
{
    node *q,* p = L->next->next;//p指向第二个数据结点
    L->next->next = NULL;//构造一个只有一个数据结点的有序表
    while (p != nullptr)
    {
        node* pre = L;//从有序表开头进行比较 pre指向插入结点的前驱结点
        
        while (pre->next != NULL && pre->next->data <  p->data)
            pre = pre->next;//在表中寻找p所指结点的前驱结点（即pre)
        q = p->next;//用于临时保存
        p->next = pre->next;
        pre->next = p;//在pre所指结点之后插入p所指结点
        p = q;//后移一个 扫描原单链表剩余的结点
    }

}
```

## 18.约瑟夫问题

```C++
//约瑟夫问题 一共n人，从编号k的人开始从1报数，报m的人出列 它的下一位又从1开始 直到所有人出列
//先建立一个循环链表 无头结点 首结点为first 从first开始移动k个人 找到第k个再循环n次 
typedef struct node
{
    int data;
    struct node* next;
}linknode;
void createlist(linknode*& first, int n)
{
    linknode* s, * r;
    first = new linknode;
    first->data = 1;
    r = first;//尾插法建立循环单链表
    for (int i = 2; i <= n; i++)
    {
        s = new linknode;//创建新节点s
        s->data = i;
        r->next = s;
        r = s;
    }
    r->next = first;//设置为循环单链表


}
void move(linknode*& first, int k)//移动k个节点
{

    for (int i = 1; i < k; i++)
    {
        first = first->next;
    }
}
void jose(int n, int k, int m)
{
    int j;
    linknode* first, * q;
    createlist(first, n);
    move(first, k);
    for (j = 1; j <= n; j++) 
    {
        move(first, m - 1);
        q = first->next;
        cout << q->data << " ";
        first->next = q->next;
        delete q;
        first = first->next;
    }
}

int main()
{
    int n = 8, k = 4, m = 4;
    jose(n, k, m);
}
```

7 3 8 5 4 6 2 1
C:\Users\14172\source\repos\ConsoleApplication10\Debug\ConsoleApplication10.exe (进程 20664)已退出，代码为 0。
按任意键关闭此窗口. .



## 19.【未完全弄懂】▲▲数组实现链表 悲剧文本

```
最简单的想法便是用数组来保存这段文本，然后用一个变量pos保存“光标位置”。这
样，输入一个字符相当于在数组中插入一个字符（需要先把后面的字符全部右移，给新字符
腾出位置）。
很可惜，这样的代码会超时。为什么？因为每输入一个字符都可能会引起大量字符移
动。在极端情况下，例如，2500000个a和“[”交替出现，则一共需要0+1+2
```

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-15 (2).png)

为了方便起见，假设字符串s的最前面还有一个虚拟的s[0]，则next[0]就可以表示显示屏 中最左边的字符。再用一个变量cur表示光标位置：即当前光标位于s[cur]的右边。cur=0说明 光标位于“虚拟字符”s[0]的右边，即显示屏的最左边。为了移动光标，还需要用一个变量last表示显示屏的最后一个字符是s[last]。代码如下：

```c++
/解决方案是采用链表（linked list）。每输入一个字符就把它存起来，设输入字符串是
//s[1～n]，则可以用next[i]表示在当前显示屏中s[i]右边的字符编号（即在s中的下标）(1)
int nextt[1000];
char s[1000];
int main()
{
	
	while (cin >> s+1)
	{
		int len = strlen(s+1);
		int i, last = 0, cur = 0;//光标位于cur号字符的后面
		nextt[0] = 0;//注意这几个变量赋值的位置不能在while之外或者main之外，这样才能保证下次输入新字符串时结果仍然正确
		for (i = 1; i <= len; i++) 
		{
			if (s[i] == '[') cur = 0;
			else if (s[i] == ']') cur = last;//光标移动到最后的位置
			else
			{
				nextt[i] = nextt[cur];
				nextt[cur] = i;
				if (cur == last)last = i; //更新"最后一个字符"编号
				cur = i;
			}
		}
		for (i = nextt[0]; i != 0; i = nextt[i])
			cout << s[i];
	}
}

```



## 20.双向链表

双向链表（doubly linked list）：用left[i]和right[i]分别表示编号为i的盒 子左边和右边的盒子编号（如果是0，表示不存在），则下面的过程可以让两个结点相互连 接： void link(int L, int R) { right[L] = R; left[R] = L; }

## 21.给出两条链表的首地址，找两条链表首个共用结点的地址

```c++
const int maxn = 100010;
//给出两条链表的首地址和N个结点的地址 数据 下一个结点地址，找两条链表首个共用结点的地址 若无相同结点返回-1
struct Node {
	int next;//下一个结点地址 指针域
	char ch;//数据域
	bool flag;//是否在这个链表中出现过
}node[maxn];

int main()
{
	for (int i = 0; i < maxn; i++)
	{
		node[i].flag = false;//先初始化均为未出现
		node[i].next = -1;
	}
	int add1, add2,n;
	cin >> add1 >> add2>>n;//首地址两个，一共有n行数据
	int addresspre, nextaddress;
	char data;
	for (int i = 0; i <= n; i++)
	{
		cin >> addresspre >> data >> nextaddress;
		node[addresspre].next = nextaddress;//注意下标中的数字即当前结点自己的地址
		node[addresspre].ch = data;
	}
	int p1;
	for (p1 = add1;p1 != -1; p1 = node[p1].next)
	{
		node[p1].flag = true;
	}
	int p2;
	for (p2 = add2; p2 != -1; p2 = node[p2].next)
	{
		if (node[p2].flag)
		{
			if (p2 != -1)
				cout << p2;
			else
				cout << -1;
			return 0;
		}
	}

		
			cout << -1;
	


	
}
```

## 22.给出N个结点的地址，链表的首地址，按照链表结点数据值由小到大排序



```c++
//给出N个结点的地址 数据域 指针域 ，链表的首地址，按照链表结点数据值由小到大排序输出结点的地址
struct Node {
	int next,address,data;//指针域 数据域
	bool flag;//是否在这个链表中出现过
}node[maxn];
bool cmp(Node a, Node b)
{
	if (a.flag == 0 || b.flag == 0)
		return a.flag > b.flag;//只要ab有一个是无效结点，就把它放后面去
	else
		return a.data < b.data;//按规定的从小到大排序
}	/*0001 5
111 100 - 1
0001 0 222
3333 100 111
12345 - 1 3333
222 1000 12345*/
int main()
{
	for (int i = 0; i < maxn; i++)
	{
		node[i].flag = false;//先初始化均为未出现
		node[i].next = -1;
	}
	int begin,n;
	//格式为[adress,data,next]
	cin >> begin>>n;//首地址，一共有n个结点
	int addresspre, nextaddress;
	int data;
	for (int i = 0; i < n; i++)
	{
		cin >> addresspre >> data >> nextaddress;
		node[addresspre].next = nextaddress;//注意下标中的数字即当前结点自己的地址
		node[addresspre].data = data;
		node[addresspre].address = addresspre;
	}

		
	int p1;
	int count = 0;
	for (p1 = begin;p1 != -1; p1 = node[p1].next)
	{
		node[p1].flag = true;//枚举链表
		count++;//结点个数
	}
	if (count == 0)//特判，链表中没有结点时
		cout << "0 -1";
	else {
		sort(node, node+maxn, cmp);
		cout << node[0].address<<"的data<";
		for (int i = 1; i <= count; i++)
			if (i < count)cout << node[i].address << "的data<";
			else cout << node[i].address << ".";
	}
	
}
```



## 23.删除排序链表中的重复元素

给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。

```java
public ListNode deleteDuplicates(ListNode head) {
ListNode cur = head;
while (cur != null && cur.next != null) {
if (cur.val == cur.next.val) {
cur.next = cur.next.next;
    } else {
cur = cur.next;
}
}
return head;
}
}
```

删除排序链表中的重复元素 II
题目描述
给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字

```java
public ListNode deleteDuplicates(ListNode head) {
ListNode dummy = new ListNode(-1, head);
ListNode cur = dummy;
while (cur.next != null && cur.next.next != null) {
if (cur.next.val == cur.next.next.val) {
int val = cur.next.val;
while (cur.next != null && cur.next.val == val) {
cur.next = cur.next.next;
}
} else {
cur = cur.next;
}
}
return dummy.next;
}
```



## 24.反转链表

递归版本：

```java
public ListNode reverseList(ListNode head) {
if (head == null || head.next == null) {
return head;
}
ListNode res = reverseList(head.next);
head.next.next = head;
head.next = null;
return res;
}
```

迭代版本：

```java
public ListNode reverseList(ListNode head) {
ListNode pre = null, p = head;
while (p != null) {
ListNode q = p.next;
p.next = pre;
pre = p;
p = q;
}
return pre;
}
```

## 寻找有环链表的入口
```java
//修改上面的循环部分：
public static node getEntrance(node<String>first){
node<String> fast = first;
node<String> slow = first;
while(fast != null && fast.next != null)
{
fast = fast.next.next;
slow = slow.next;
if(fast.equals(slow))
{
node<String>temp = slow;
break;//如果两个指针相遇
}

}
while(temp!=null && temp != slow)
{
temp = temp.next;
slow = slow.next;
if(temp.equals(slow))
break;
}
return temp;
}


```
判断链表是否有环：用快慢指针判断二者是否会相等，如果有相等则有环





# 字符串



结果:bbfuewrhnbdwuauf(输入)
bfuewrhndad

## 1处理一个字符串 只在字符第一次出现时保留其他的删除

```c++
bool dupchar(char* s, int m, char c)//判断s[0..m]中是否有字符c
{
    for (int i = 0; i <= m; i++)
    {
        if (s[i] == c)
            return true;
    }
    return false;
}

void deldupchar(char* s)//重建法 k记录保留的字符个数 
{
    int k = 1,i;
    if (!s[0] || !s[1])//s为空或单个字符时直接返回
        return;
    for (i = 1; s[i]; i++)
    {
        if (!dupchar(s, i - 1, s[i]))//当s[i]不是单个字符时直接插入
        {
            s[k] = s[i];
            k++;
        }
    }
    s[k + 1] = '\0';//设置结尾符
}

int main()
{
    char s[48];
    cin >> s;
    deldupchar(s);
    cout << s;
}
```

## 2原地压缩字符串

eeeeffagg压缩为e4f2ag2

```c++
void rebuild(char* s)
{
    int i = 0, index = 0;
    while (s[i])//当字符不为空
    {
        int k = i, num = 1;
        if (s[k + 1] != s[k])
        {
            s[index] = s[k];
            index++;
            i++;
            continue;
        }
        else 
        {
            while (s[k + 1] == s[k])
            {
                num++;
                k++;
            }
            s[index] = s[k];
            s[index + 1] = num+'0';//注意这个+'0'非常重要!不这样会乱码！！！！！
            index += 2;
            i = i + num;
        }
    }
    s[index] = '\0';
}

int main()
{
    char s[48];
    cin >> s;
   rebuild(s);
    cout << s;
}
```



## 3▲▲编程将字符串中空格字符换成%20，求出新字符串长度 

[^]: 

原字符串：my johh smith

新：my%20johh%20smith17



【思路】先求出原字符串空格个数以便得到新的长度，对于新字符串**从后向前扫描**（从前向后扫描会覆盖原来字符串）

主函数中定义字符指针，分配足够空间，用strcpy得到原字符串

- **！！！注意**：如果主函数中直接用s[]="my jogh smith"定义数组s,由于数组是固定的不会扩大，会导致程序崩溃。可以用s[max]="my jogh smith",效果一样。max为一个足够大的数字。

```c++
#define _CRT_SECURE_NO_WARNINGS//写在开头
#include<string>
#include<cstdio>
#include<malloc.h>
#include<string.h>

int rebuild(char* s, int n)
{
    int num =0;
    int b = n;
    for (int i = 0; i < n; i++)
    {
        if (s[i] == ' ')
            num++;
    }
    int newl = n + num * 2;//新字符串的长度
    s[newl] = '\0';
    int k = newl-1;//最大的下标为长度-1
    for (int i = n-1; i >= 0; i--)
    {
       
        if (s[i] == ' ')
        {
            s[k] = '0';
            s[k - 1] = '2';
            s[k - 2] = '%';
            k -= 3;//逆着插入字符%20
            

        }
        else if (s[i] != '\0')
        {
            s[k] = s[i];
            k--;//复制原来的非空格内容到新字符串
        }//注意K一直都大于等于i
    }
   
    return newl;//新长度
}
int main()
{
    char* s = new char[100];
    strcpy(s, "my johh smith");
    int num = strlen(s);
    int k = rebuild(s, num);
    cout << s;

    cout << k;

}
```





```c++
//判断一个字符串中是否所有字符都是唯一出现的，假设该字符串仅包含ASII字符
//由于ASII字符编号为0-127，设定一个布尔类型的数组charset[i]，若当前字符p[i]的chareset[p[i]]为0表明是重复字符
bool unique(char* p)
{
    bool charset[128]= { false };
 
    for (int i = 0; p[i]; i++)
    {
        if (charset[p[i]])
            return false;
        else
            charset[p[i]] = true;

    }
    return true;
}

   int main()
{
    char s[] = "abcd efgh";
    
   cout<< unique(s);



}
```

## 4判断两个字符串的后n位是否相等

```c++
其中一个字符串给定 另外有一个结构体数组类似pb[i]={{"aberwt"},{"rewrtwe"},{"bcc"}...}
//令结构体数组最后一个为{0}
struct 
{
    char* name;
}pb[10];
void findi(char* s,int n)
{
    char* p;
    int len = strlen(s);
    s = s + len - n;//按后缀的n位匹配 取s的后n位
    bool flag = false;
    for (int i = 0; pb[i].name != 0; i++)
    {
        if (strlen(pb[i].name) < len)
            continue;
        else
        {
            p = pb[i].name;
            p += strlen(pb[i].name) - n;//p的后n位
        }
        if (strcmp(s, p) == 0)
        {
            cout << "有匹配的";
            flag = true;
        }
    }
    if (!flag)
        cout << "没有匹配";
}
int main()
{
    for(int i = 0;i < 10;i++)
    {
        pb[i].name = new char[20];//分配空间
        cin >> pb[i].name;
    }
    char* s = new char[20];
    cin >> s;
    findi(s, 3);
    delete[]s;
    for (int i = 0; i < 10; i++)
        delete[]pb[i].name;

}
```

## 5//求一个字符串最大对称子串长度 如roorer长度为4

```c++
int maxsub(char* s)
{
    if (!s || s[1] == '\0')
        return -1;  //空串或单个字符串返回-1
    int len = strlen(s);
    char* p = s + 1;
    int manxlen = -1;
    while (*p != '\0')
    {
        char* r = p + 1;
        char* l = p - 1;
        
        while (l >= s && r < &s[len - 1] && *l == *r)//分别查找以p为中心的前后最大对称子串 如果为奇数个字符时如xyyayyx
        {
            l--;
            r++;
        }
        if (r - l - 1 > manxlen)
            manxlen = r - l - 1;

         r = p;
         l = p - 1;
         while (l >= s && r < &s[len - 1] && *l == *r)//如果为偶数个字符时
         {
             l--;
             r++;
         }
         if (r - l - 1 > manxlen)//r-l-1为当前对称子串长度
             manxlen = r - l - 1;
         p++;
    }
    return manxlen;
}
```

## 6.两个字符串的最大子串

```c++
//编程找出两个字符串的最大公共子串，问什么情况下算法最优？
//采用典型BF算法，找到第一个相等的字符后逐个比较
//时间复杂度是o(m*n)最优情况是两个串相同时，时间复杂度为o(n)
char* maxcomstr(char* s, char* r)
{
    int k,slen = 0,i = 0,maxlen = 0;
    while(s[i])
    {
        int j = 0;//注意初始化j的位置 每次i移动一个字符后r都要从第一个字符开始扫描
        while (r[j])
        {
            if (s[i] == r[j])
            {
                slen = 1;
                while (s[i+slen] && r[j+slen] && s[i + slen] == r[j + slen])
                    slen++;
                if (slen > maxlen)
                {
                    maxlen = slen;
                    k = i;
                }
                j += slen;//注意这一步中j的变化 要扫描r中剩余公共子串后面的内容
                
            }
            else
                j++;
        }
        i++;


    }
    char* str = new char[maxlen+1];
    for (int m = 0; m < maxlen; m++)
        str[m] = s[k + m];//将子串的字符复制到新串中
    str[maxlen] = '\0';
    return str;

}

int main()
{
    char* s = new char[30];
    char* r = new char[30];
    cin >> s >> r;
    char* str = maxcomstr(s, r);
    cout << str;

}
```

## （未解决）一个字符串中子串出现次数

```c++
//一个字符串s查找子串t出现的次数
int subcount(char* s, char* t)
{
    int count = 0, i = 0, len=0;
    int len2 = strlen(s);
    int m = strlen(t);
    while (s[i]&&i<=len2 - m)//注意i的取值范围
    {
        int j = 0;
       
        if (s[i] = t[j])
        {
            int k = i;
            while (s[i] && t[j] && s[i] == t[j])
            {
               
                i++;
                j++;
                len++;               
                
            }
            
            if (j == m)
            {
                count++;
              
              
            }
           


        }
        else 
        i++;
    }
    return count;
}
```

## 7.编程求字符串通配符匹配的算法 

```c++
//t包含一个或多个*,*可以与s中0个或多个字符匹配 
//如（“there are","*e*e")返回真
bool subcount(char* s, char* t)
{
    int i = 0, j = 0, len = 0;
    while (s[i] && t[j])
    {
        if (s[i] == t[j])
        {
            i++;
            j++;
        }
        else if(t[j] == '*')
        {
            j++;
            while(s[i] && s[i] != t[j])
            {
                i++;//连续跳过字符直到s中的字符与t再次相等
            }
        }
        else
        {
            i = i - j + 1;
            j = 0;
        }
    }
    if (t[j] == '\0')
        return true;
    else
        return false;
}
```



## 8.统计一个字符串所有大写字母出现次数（只有大写）

```c++
void getnext(char* p,int count[])
{ int i = 0;
    int len = strlen(p);
    while (i < len)
    {
        count[p[i] - 'A']++;//注意这种写法
        i++;

        
    }

}
int main()
{
    char* s = new char[30];
  //  char* r = new char[30];
    cin >> s;
    int num[26];
    memset(num, 0, sizeof(num));//初始化的方法
    getnext(s,num);
    for (int i = 0; i < 26; i++)
        cout <<(char)('A'+i)<<":"<< num[i] << " ";
//强制转换输出ABCD..
}
```



## 9.串置换函数与匹配函数

```c++
int index(char* s, char* t, int pos);
void replace(char* s, char* t, char* v);//串置换函数 本题中感觉只能实现t与v大小一样时的置换


int index(char* s, char* t, int pos)//串匹配函数 pos为从第pos个字符开始从S中寻找t出现的第一次位置 
//注意第一个字符下标为0
{
	int iindex, s1, t1, k,j=1;
	s1 = strlen(s);
	t1 = strlen(t);
	k = pos;
	while(s[k])
	{
		if (s[k - 1] == t[j - 1])
		{
			while (s[k - 1] == t[j - 1]) { k++; j++; }
			if (j > t1)
				return k - t1 ;
			else
			{
				k = k - j + 2;
				j = 1;
			}
		}
		else
			k++;
	}
	return -1;
}

void replace(char* s, char* t, char* v)//s串中的t串换成v串
{
	
	int s1, t1, v1, k, j,po,i;
	s1 = strlen(s);
	t1 = strlen(t);
	v1 = strlen(v);
	k = 0;
	i = 0;
	
	while (k < s1 - t1 + 1)
	{
		po = index(s, t, k);
		if (po == -1)
			break;

		else 
		{
			j = po - 1;
			for (i = 0; i < v1 && s[i + v1]; i++)
			{
				s[j] = v[i];

				j++;
			}
			k = po + t1;
		}
	}
	
}
int main()
{
	
	
	char* s = new char[50];
	char m[] ="you",  v[] = "***";
	
	cin >> s;

	replace(s, m,v);
	cout << s;
	
}
```



## 给你两个二进制字符串，返回它们的和（用二进制表示）。

输入为 非空 字符串且只包含数字 1 和 0。


size_t是一些C/C++标准在stddef.h中定义的。这个类型足以用来表示对象的大小。size_t的真实类型与操作系统有关。
string中的push_back函数,作用是字符串之后插入一个字符。
具体的，我们可以取 n = \max\{ |a|, |b| \}n=max{∣a∣,∣b∣}，循环 nn 次，从最低位开始遍历。我们使用一个变量carry，carry 表示上一个位置的进位，初始值为 0。最后如果 \rm carrycarry 的最高位不为 00，则将最高位添加到计算结果的末尾。（比如1+1=2 

注意，为了让各个位置对齐，你可以先反转这个代表二进制数字的字符串，然后低下标对应低位，高下标对应高位。当然你也可以直接把 aa 和 bb 中短的那一个补 00 直到和长的那个一样长，然后从高位向低位遍历，对应位置的答案按照顺序存入答案字符串内，最终将答案串反转。这里的代码给出第一种的实现。
法二：
我们可以设计这样的算法来计算：

把 aa 和 bb 转换成整型数字 xx 和 yy，在接下来的过程中，xx 保存结果，yy 保存进位。
当进位不为 00 时
计算当前 xx 和 yy 的无进位相加结果：answer = x ^ y
计算当前 xx 和 yy 的进位：carry = (x & y) << 1
完成本次循环，更新 x = answer，y = carry
返回 xx 的二进制形式

```cpp
class Solution {
public:
    string addBinary(string a, string b) {
int len = max(a.size(),b.size());
reverse(a.begin(),a.end());
reverse(b.begin(),b.end());
int carry = 0;
string ans;
for(size_t i  = 0;i < len;++i){
carry += i < a.size()?(a.at(i)=='1'):0;
carry += i < b.size()?(b.at(i)=='1'):0;
ans.push_back((carry % 2)?'1':'0');
carry = carry/2;
}
if(carry)
ans.push_back('1');
reverse(ans.begin(),ans.end());//由于一开始对字符串进行了翻转，最后记得要翻转回来。
return ans;
    }
};
```
**因为a.at(i)=='1'中的外面的单引号没加，导致看了好久也没看出来，不知道为什么不通过。唉，太不细心了。**
Java：
在Integer类中有静态方法toBinaryString(int i)方法,此方法返回int变量的二进制表示的字符串。
Integer.parseInt("123");其实默认是调用了int i =Integer.parseInt("123",10); 其中10代表的默认是10进制的

## 位1的个数
1.一开始，掩码 m=1 m=1 因为 11 的二进制表示是

0000\ 0000\ 0000\ 0000\ 0000\ 0000\ 0000\ 0001
0000 0000 0000 0000 0000 0000 0000 0001

显然，任何数字跟掩码 1 进行逻辑与运算，都可以让我们获得这个数字的最低位。检查下一位时，我们将掩码左移一位。

0000\ 0000\ 0000\ 0000\ 0000\ 0000\ 0000\ 0010
0000 0000 0000 0000 0000 0000 0000 0010

2.二进制表示中，数字 n 中最低位的 1 总是对应 n - 1 中的 0 。因此，将 n 和 n - 1 与运算总是能把 n 中最低位的 1 变成0 ，并保持其他位不变。

```cpp
  int hammingWeight(uint32_t n) {
        int sum = 0;
        while(n){
            sum++;
            n = n & (n-1);
        }
        return sum;
    }
```



## 判断一个字符串是否由它的子串重复构成
如ababab 返回true
abb返回false.
关键的地方 ：1.子串必是字符串的前缀（从第一个字符开始就要匹配） 2.子串的长度倍数必须是字符串的长度（否则不可能由子串重复构成）

按照题解的思路一开始写了一个代码：

```cpp
 bool repeatedSubstringPattern(string s) {
        int n = s.size();
        bool match;
        for (int i = 1; i < n/2; i++) {
            if (n % i == 0)
            {
                 match = true;
                for (int j = i; j < n ; j++)
                {
                    if (s[j] != s[j - i]) {
                        match = false; break;
                    }
                }
            }
            if (match) return true;
        }
       
        return false;
    }
```
运行时却没有通过，然后对照才知道是因为int i = 1; i < n/2; i++这句话中应该是<=，比方说ababa n = 5,n/2 = 2, i < 2则i只能最大取1 但此时由于子串存在重叠部分，子串长度大于n/2，会导致输出为False。所以无法通过。且要注意match = true写在for前面，因为当n不是i的倍数时可以直接不进行匹配判断。
应改为:
```cpp
 bool repeatedSubstringPattern(string s) {
 int n = s.size();
        bool match;
        for (int i = 1; i <= n/2; i++) {
            if (n % i == 0)
            {
                 match = true;
                for (int j = i; j < n ; j++)
                {
                    if (s[j] != s[j - i]) {
                        match = false; break;
                    }
                }
            }
            if (match) return true;
        }
       
        return false;
    }
```
题解有一种非常巧妙的方法，是自己很少用的方法，

```cpp
 bool repeatedSubstringPattern(string s) {
        return (s + s).find(s, 1) != s.size();
    }
```

size_type find(const string & str, size_type pos = 0) const	从字符串的pos位置开始，查找子字符串str。如果找到，则返回该子字符串首次出现时其首字符的索引；否则，返string::npos

bitCount实现的功能是计算一个（byte,short,char,int统一按照int方法计算）int,long类型的数值在二进制下“1”的数量。



## 判断第一个字符串能不能由第二个字符串里面的字符构成。



如果可以构成，返回 true ；否则返回 false。

```java
  public boolean canConstruct(String ransomNote, String magazine) {
int[]ch=new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            int idx = magazine.charAt(i)-'a';
            ++ch[idx];
        }
        for (int i = 0; i < ransomNote.length(); i++) {
          int idx = ransomNote.charAt(i) - 'a';
if (ch[idx] == 0) return false;
--ch[idx];
        }
        return true;

    }
```



## 两数相加

给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

 

```
输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.
```



```java
int cur = 0;
ListNode node = new ListNode(-1);
ListNode now = node;
while (l1!= null || l2 != null)
{
    int val1 = l1 == null?0:l1.val;
    int val2 = l2 == null?0:l2.val;
  
    int t = (val1+val2+cur);
    cur = t/10;
    node.next = new ListNode(t%10);
    node = node.next;
    l1 = l1.next == null?null:l1.next;
    l2 = l2.next == null?null:l2.next;
}
return now.next;
```

#### [剑指 Offer II 025. 链表中的两数相加](https://leetcode-cn.com/problems/lMSNwu/)

难度中等12

给定两个 **非空链表** `l1`和 `l2` 来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。

可以假设除了数字 0 之外，这两个数字都不会以零开头。



```java
 public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    Deque<Integer> deque1 = new ArrayDeque<>();
        Deque<Integer> deque2 = new ArrayDeque<>();
        ListNode cur  = new ListNode(-1);
        ListNode now = cur;
        while (l1!=null)
        {
            deque1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null)
        {
            deque2.push(l2.val);
            l2 = l2.next;
        }
        int carry = 0 ;
        while (!deque1.isEmpty()||!deque2.isEmpty()||carry!=0)
        {
            int t1 = deque1.isEmpty()?0:deque1.pop();
            int t2 = deque2.isEmpty()?0:deque2.pop();
            carry+=t1+t2;
            ListNode thenode = new ListNode(carry%10);
          thenode.next = cur.next;
          cur.next = thenode;
            carry /=10;
        }
        return cur.next;

    }
```

注意判断条件while (!deque1.isEmpty()||!deque2.isEmpty()||carry!=0) 如果漏了carry!=0会导致结果错误，比如5+5，输出0而不是10

 如果采用把字符串转数字的方法会出现溢出现象（超过最大整数）

# 栈

## 1.设计顺序栈 定义栈的数据结构

```c++
//设计顺序栈 其中data[]存放栈的元素，top为栈顶指针（初始为-1)添加一个getmin()函数能够得到栈的最小元素
//设置一个辅助栈minidex[],它保存的是data[]中最小元素的下标，当栈空或者进栈元素val小于当前栈的最小元素时
//将val的下标进minidex[]栈退栈时如果栈顶为最小元素，则下标从minidex[]退栈，否则不退栈
class overflow{};//异常处理类
#define maxsize 100
class Stack {
public:
    Stack() :ttop(-1), minpos(-1) {};
    int getmin();
   int top();
   int pop();
   int push(int val);
   bool isempty() {
       return ttop == -1;
   }
   bool isfull() { return ttop == maxsize-1; }



private:
    int minn;
    int ttop;
    int data[maxsize];
    int minidex[maxsize];
    int minpos;
};
int Stack::getmin()
{
    if (isempty())
        throw overflow();
    return data[minidex[minpos]];//返回栈中最小元素 最小元素的下标是minidex[minpos]中的值
}
int Stack::top()
{
    if (isempty())
        throw overflow();//如果为空抛出异常类
    return data[ttop];

}
int Stack::push(int val)
{
    if (isfull())
        throw overflow();
    if (isempty() || val < getmin())
    {
        minpos++;
        minidex[minpos] = ttop + 1;//如果栈空或者val小于进栈前最小值，将val的下标ttop+1放入栈minidex中
       
    }
    ttop++;
    data[ttop] = val;//这两句放在if语句的外面 元素val进栈
    return ttop;
}
int Stack::pop()
    {
    if (isempty())
        throw overflow();
    int num = data[ttop];//保存元素值
    if (ttop == minidex[minpos])//如果栈顶元素为最小元素 将其下标从minidex中退栈
        minpos--;
    ttop--;//栈顶元素退栈
    return num;
        

    }

```

**注意:由于STL的stack栈容器不能顺序遍历，有些算法需要顺序遍历栈时可以用数组或者链表放栈元素，并设计相应的栈运输算法，即自己实现栈或复制创建一个临时栈再对临时栈的元素退栈遍历**



## 2.一个算法删除栈中所有元素e保持其他元素次序不变

```c++
#include<stack>
``
void dele(stack<int>& st, int e)
{
    stack<int>ioo;
    int num;
    while (!st.empty())
    {
        num = st.top();
        if(num != e)
        ioo.push(num);
        st.pop();
    }
    while (!ioo.empty())
    {
        num = ioo.top();
        ioo.pop();
        st.push(num);
    }
}
```

## 3.//将一个栈从栈顶到栈底递增排序 利用辅助栈

```c++
void sorta(stack<int>&st)
{
    stack<int>stlm;//辅助栈
    
    while (!st.empty())
    {
        int a = st.top();
        st.pop();
        while (!stlm.empty() && a > stlm.top())
        {
            st.push(stlm.top()); stlm.pop();//退栈stlm中大于a的元素并进St栈
        }
        stlm.push(a);
    }
    while (!stlm.empty())
    {
        st.push(stlm.top());//所有辅助栈元素进入原栈
        stlm.pop();
    }
}
```





## 4.寻找一个数组下一个更大的元素 若无更大的则为-1.（输出下一个更大的）

```c++
void findneg(int a[], int n)
{
    stack<int>st;
    st.push(a[0]);
    for (int i = 1; i < n; i++) 
    {
        int ttop = st.top();

        while (!st.empty() && ttop < a[i])
        {
            cout << st.top() << "->" << a[i]<<",";
            st.pop();
            if (!st.empty())
                ttop = st.top();

        }
        st.push(a[i]);
    }
    while (!st.empty())
    {
        cout << st.top() << "->" << -1;
        st.pop();
    }
}




int main()
{
    int a[5] = { 4,5,2,25,26 };
    findneg(a, 5);

}
```

## 5.//设输入序列是1，2，。。n 判断能否通过一个栈得到num[0...n-1]指定的出栈序列

```c++
bool validseq(int num[], int n)
{
	stack<int>st;
	int i, k = 0, e;
	for (i = 1; i <= n; i++)
	{
		st.push(i);
		while (!st.empty())
		{
			e = st.top();
			if (num[k] == e)
			{
				st.pop();
				cout << e;
				k++;

			}
			else
				break;//不匹配时退出while循环
		}
	}
	if (st.empty())
		return true;
	else
		return false;
}
```

## 6.所有出栈序列



![](C:\Users\14172\Pictures\115.jpg)

![](C:\Users\14172\Pictures\116.jpg)

![image-20201105134316959](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20201105134316959.png)





```c++
//以字符序列abcd作为栈st的输入，利用出栈操作输出所有可能的序列
//递归模型


char c[] = "abcd";
int total = 4;
#define maxsize 10
stack<int>st;
int count1 = 0;

void process(int m, int a[], int k)
{
	if (m == total && st.empty())
	{
		for (int i = 0; i < k; i++)
			cout << c[a[i]];//输出a的元素序列 构成一种出栈序列
		cout << endl;
		count1++;
	}
	//两种递归的情况
	if (m < total)
	{
		st.push(m);
		process(m + 1, a, k);
		st.pop();
	}
	if (!st.empty())
	{
		int num = st.top();
		st.pop();
		a[k] = num;
		process(m, a, k + 1);
		st.push(num);
	}
}
int main()
{
	cout << "所有出栈序列\n";
	int a[10];
	process(0, a, 0);
	cout << "出栈序列个数" << count1;
}

```

## 7.序列括号是否合法

```c++
//序列括号[]{}() 判断一个括号序列是否合法 如([])合法
bool match(char* exp)
{
	stack<int>st;
	int i = 0;
	while (exp[i])
	{
		if (exp[i] == '[' || exp[i] == '{' || exp[i] == '(')
			st.push(exp[i]);
		else
			if (st.empty())
				return false;
			else
			{
				if (exp[i] == '}')
				{
					char m = st.top();
					if (m != '{')
						return false;
					else
						st.pop();
				}
				if (exp[i] == ']')
				{
					char m = st.top();
					if (m != '[')
						return false;
					else
						st.pop();
				}
				if (exp[i] == ')')
				{
					char m = st.top();
					if (m != '(')
						return false;
					else
						st.pop();
				}

			}
		i++;
	}
	return st.empty();//为空则匹配
}
```

## 8.//求一个只含()括号的序列exp最长的有效括号串

```c++
//设最长有效括号串是exp[lastpos-maxlen+1,lastpos]子串，扫描所有字符，将左括号的位置进栈 last记录没有匹配的右括号的位置
//当扫描到')'若栈空 则令last = i,否则退栈，e为当前匹配的括号对的前一个左括号位置表示exp[e+1,i]是一个匹配序列

void maxmatch(char* exp, int& maxlen, int& lastpos)
{
	stack<int>st;
	int last = -1, e;
		maxlen = 0;
	for (int i = 0; exp[i]; i++)//扫描字符串
	{
		
		if (exp[i] == '(')
		{
			st.push(i);
		}
		else if (exp[i] == ')')
		{
			if (st.empty())
				last = i;//栈空，没有匹配的左括号 last记录没有匹配的右括号的位置
			else {
				st.pop();
				if (st.empty())
				{



					if (maxlen < i - last)//栈空时exp[last+1,..i]是一个匹配序列
					{
						maxlen = i - last;
						lastpos = i;
					}
				}
				else
				{
					e = st.top();
					if (maxlen < i - e)//栈非空时exp[e+1,..i]是一个匹配序列
					{
						maxlen = i - e;
						lastpos = i;
					}
				}
			}
		}
	}
}
void display(char* p, int maxlen, int lastpos)
{
	for (int i = lastpos-maxlen+1; p[i]; i++)
	{
		cout << p[i];
	}
}
int main()
{
	char *r = new char[20];
	cin >> r;
	int maxlen ,lastpos = 0;
	maxmatch(r,maxlen,lastpos);
	display(r,maxlen,lastpos);//在主函数中给lastpos赋初值
```



## 9.用栈实现迷宫

```c++
#define maxsize 7
int v[4] = { 0,1,0,-1 };//水平偏移量 下标对应方位号0~3
int h[4] = { -1,0,1,0 };//竖直偏移量
char maze[maxsize][maxsize] = {
    { 'X','X','X','X','X','X','X'},
    {'O','O','O','O','X','X','X' },
    {'O','O','X','O','X','X','X' },
    {'O','O','O','O','O','X','X' },
    {'O','X','X','O','O','O','X' },
    {'X','O','O','O','X','O','O' },
    {'O','O','O','O','X','X','O' },


};
typedef struct {
    int x;
    int y;
    int di;//下一个相邻可走方块的方位号
}box;
bool findpath(int x, int y)
{
    stack<box>st;
    int i, j, i1, j1, k, di=-1;
    box e;//一个方块
    bool finn;
    e.x = x;
    e.y = y;
    e.di = -1;
    maze[x][y] = ' ';//入口方块进栈
    st.push(e);//推一个方块进入栈
    while (!st.empty())
    {
        e = st.top();
        di = e.di;
        i = e.x;
        j = e.y;
        if (e.x == maxsize - 1 && e.y == maxsize - 1)//找到一条路径
        {
            for (i = 0; i < maxsize; i++)
            {
                for (j = 0; j < maxsize; j++)
                    cout << maze[i][j];
                cout << endl;
            }
            return true;
        }
        else
        {
           
            finn = false;
            for (k = 1 + di; k <= 3; k++)
            {
                i1 = i + v[k];
                j1 = j + v[k];
                
                if (i1>=0 && i + v[k] < maxsize && j + h[k] < maxsize && maze[i + v[k]][j + v[k]] == 'O')
                {
                    st.top().di = k;//修改原栈顶元素di为-1
                    e.x = i + v[k];
                    e.y = j + h[k];
                    e.di = -1;//新块的di为-1
                    st.push(e);//找到一个可走的相邻方块 推入栈
                    finn = true;
                    break;
                 }
              
                
            }
            if (finn)
            {
                maze[i1][j1] = ' ';
            }
            else
            {
                st. pop();
                maze[i][j] = '0';//恢复退栈方块的迷宫值
            }

        }
    }
    return false;
}
int main()
{
    int x = 6, y = 6,j;
    if (maze[x][y] != 'O' || maze[maxsize - 1][maxsize - 1] != 'O')
        cout << "入口或者出口不可走\n";
    else
    {
        bool np = findpath(x, y);           
        if (!np)
            cout << "没有路径";
    }
}
```

## 10.中缀表达式变后缀

```c++
#include <iostream>
#include<stack>

using namespace std;

void trans(char* exp, char* post)
{
    stack<char>st;
    int k = 0;
    while (*exp)
    {
        switch (*exp)
        {
        case '(':
            st.push(*exp);
            exp++;
            break;
        case ')':
            while (!st.empty() && st.top() != '(')
            {
                post[k++] = st.top();
                st.pop();
            }
            st.pop();//弹出“（”符号
            exp++;
            break;
        case '+':
        case '-':
            while (!st.empty())
            {
                if (st.top() != ')')
                {
                    post[k++] = st.top();//若栈顶不是（则将栈顶放入post中
                    st.pop();//出栈
                }
                else
                    break;//如果栈顶元素是'('时退出循环
            }
            st.push(*exp);
            exp++;
            break;
        case '*':
        case '/':
            while (!st.empty())
            {
                if (st.top() == '*' || st.top() == '/')
                {
                    post[k++] = *exp;
                    st.pop();
                }
                else
                    break;
            }
            st.push(*exp);
            exp++;
            break;

        default:
            while (*exp >= '0' && *exp <= '9')
            {
                post[k++] = *exp;
                exp++;

            }
            post[k++] = '#';//用#标识一个数字串结束 比如32这样的多位数
        }
    }
    while (!st.empty())//将运算符放入post中
    {
        post[k++] = st.top();
        st.pop();
    }
    post[k] = '\0';//给post表达式添加结束符
}
double comppost(char* post)//计算后缀表达式的值
{
    double a, b, c;
    stack<double>st;
    while (*post)
    {
        switch (*post)
        {
        case '+':
            a = st.top(); st.pop();
            b = st.top(); st.pop();
            c = a + b;
            st.push(c);
            break;
        case '-':
            a = st.top(); st.pop();
            b = st.top(); st.pop();
            c = b - a;
            st.push(c);
            break;
        case '*':
            a = st.top(); st.pop();
            b = st.top(); st.pop();
            c = a * b;
            st.push(c);
            break;
        case '/':
            a = st.top(); st.pop();
            b = st.top(); st.pop();
            if (a == '0')
            {
                cout << "error!";
                exit(1);
            }
            else
            c = b / a;
            st.push(c);
            break;
        default:
            double d = 0;
            while(*post >= '0' && *post <= '9')
            {
                d = d * 10 + *post - '0';
            post++;
            }
            st.push(d);
            break;
        }
        post++;
    }
    return st.top();
}

```

## 11.提供另一种基本运算peekmedian 返回栈的中位值

```c++
//提供另一种基本运算peekmedian 返回栈的中位值 如果N是奇数 返回第（N+1)/2个最小元素
//用string代表输入的命令
//求中位数用vector数组维护 该数组用升序排列
using namespace std;
int N;//N个正整数 一共显示N行 每行是一种操作命令
stack<int>st;
vector<int>myv;


void pushest(int key)//进栈元素key的操作
{
    st.push(key);
    int e, count = 0;
    if (myv.size() == 0)//先考虑如果数组无数据的情况
        myv.push_back(key);
    else
    {
        while(myv.back()>key)//先删除所有尾部大于key的元素 让这些元素进入栈中
           
            {
            st.push(myv.back());
                myv.pop_back();
                
                count++;//计数 计入一共多少个这样的元素
            }
        myv.push_back(key);//现在添加key到数组中
        for (int i = 0; i < count; i++)
        {
            e = st.top();
            myv.push_back(e);//将原来的元素再次进入数组中栈恢复原状
            st.pop();
        }
    }

}
void popst()//出栈操作
{
    int e,count=0;
    if (st.empty())
    {
        cout << "invalid" << endl;
        return;
    }

    e = st.top();
    cout << e<< endl;
    st.pop();
    while (myv.back() != e)
    {
        st.push(myv.back());
        myv.pop_back();//先删除数组中所有不为栈顶元素的数据
        count++;
    }
    myv.pop_back();//myv删除该元素
    for (int i = 0; i < count; i++)
    {
        e = st.top();
        myv.push_back(e);//将原来的元素再次进入数组中
        st.pop();
    }
}
void peekmedian()
{
    int key;
    if (st.empty())
        cout << "invalid" << endl;
    else
    {
        int n = st.size();
        if (n % 2 == 0)
        {
            cout << myv[n / 2-1] << endl;

        }
        else
            cout << myv[(n + 1) / 2-1]<<endl;//奇数个元素时
    }
}


int main()
{
 
    string command;
    int key;
    cin >> N;
    while (N--)
    {
        cin >> command;
        if (command == "push")
        {
            cin >> key;//注意必须是push 8这样的格式 中间要空格
            pushest(key);
        }
        else if (command == "pop")
        {
            popst();
        }
        else
            peekmedian();
    }
}
```

## 12.求一个直方图的最大矩形面积

```c++
#define max(a,b) (a) > (b)?(a):(b)
using namespace std;
//求一个直方图的最大矩形面积 令每个矩形宽度为1 以i[矩形i的高度]表示一个矩形 栈空或输入矩形的高度大于栈顶
//将i进栈 hei栈中储存的是下标 第一个下标是0
int maxarea(vector<int>height)
{
	stack<int>hei;
	int toparea,maxarea = 0;//先将最大面积初始化为0
	
	int i = 0;
	height.push_back(0);
	while (i < height.size()) {
		if (hei.empty() || height[i] > height[hei.top()])
			hei.push(i++);
		else
		{
			int tem = hei.top();
			hei.pop();
			toparea = height[tem] * (hei.empty()?i:(i - hei.top() - 1));//注意这一步的写法 要判断栈是否为空
			maxarea = max(maxarea, toparea);
		}
	}
	return maxarea;
}

int main()
{
	int a[] = { 6,2,5,4,5,1,6 };
	int n = sizeof(a) / sizeof(a[0]);
	vector<int>height(a, a + n);//注意这种初始化的方法
	cout << maxarea(height);
  
}
```

## 13.判断能否通过一个栈得到指定的排列

注意：num的数据不能超过N

例如有五个数的排列则不能超过5 且不等于0

如2 1 5 4 3 可以通过栈得到 （栈的输入顺序是1，2，3...N)

```c++
//输入一个正整数序列1，2，3.。。n，判断能否通过一个栈得到指定的排列（排列由键盘输入）
int main()
{
	int i, N;
	stack<int>st;
	int num[105];
	int k = 1;
	while (cin>>N && N)
	{
		for (i = 1; i <= N; i++)
			cin >> num[i];
		for (i = 1; i <= N; i++)
		{
			st.push(i);
			while (!st.empty()&&st.top() == num[k])
			{
				k++;
				st.pop();
			}
		}
		if (st.empty())
			cout << "yes";
		else
			cout << "no";
	}
  
}
```

## 14.输入n个矩阵的维度和一些矩阵链乘表达式，输出乘法的次数。

```c++
//如果乘法无法进行，输出error。假定A是m*n矩阵，B是n*p矩阵，//那么AB是m*p矩阵，乘法次数为m*n*p。如果A的
//列数不等于B的行数，则乘法无法进行。
//例如，A是50*10的，B是10*20的，C是20*5的，则(A(BC))的乘法次数为10*20*5（BC的
//乘法次数）+ 50*10*5（(A(BC))的乘法次数）= 3500。
   // 自己写的：
struct Matrix {
	int row, col;
	Matrix(int row = 0, int col = 0) :row(row), col(col) {}
} m[26];

int main()
{
	for (int i = 0; i < 3; i++)
	{
		cin >> m[i].row >> m[i].col;
	}
	stack<Matrix>st;
	string ex;
	cin >> ex;
	int i = 0,sum=0;
	while (ex[i] != '\0')
	{
		while (ex[i] == '(')
			i++;
		while(ex[i]>='A'&&ex[i]<='Z')
		{
			st.push(m[ex[i]-'A']); i++;
		}
		if(ex[i] == ')')
			
			{
				int m = st.top().col;
				st.pop();
				int n = st.top().row;
				
				sum += m * n*st.top().col;
				st.pop();
				i++;
				Matrix mat(n, m);
				st.push(mat);
			}


	}
	cout << sum;
	}

```

书上的：

```c++
struct Matrix {
int a, b;
Matrix(int a=0, int b=0):a(a),b(b) {}
} m[26];
stack<Matrix> s;
int main() {
int n;
cin >> n;
for(int i = 0; i < n; i++) {
string name;
cin >> name;
int k = name[0] - 'A';
cin >> m[k].a >> m[k].b;
}
string expr;
while(cin >> expr) {
int len = expr.length();
bool error = false;
int ans = 0;
for(int i = 0; i < len; i++) {
if(isalpha(expr[i])) s.push(m[expr[i] - 'A']);
else if(expr[i] == ')') {
Matrix m2 = s.top(); s.pop();
Matrix m1 = s.top(); s.pop();
if(m1.b != m2.a) { error = true; break; }
ans += m1.a * m1.b * m2.b;
s.push(Matrix(m1.a, m2.b));
}
}
if(error) printf("error\n"); else printf("%d\n", ans);
}
return 0;
}
```



# 树

```

```



```

```

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-13.png)

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-13 (1).png)

## 基础![]知识

前序和中序或者后序和中序序列可以唯一确定一棵二叉树。后序遍历:左子树 右子树 根结点。用数组存储即顺序存储

中序遍历中最开始结点是根结点最左下结点 最后结点是根结点最右下结点，后序遍历最开始一定是叶节点，最后一定是根节点



含有n个结点的二叉树最小高度为[log2(n+1)] (完全二叉树时)

一、满二叉树

　　一棵二叉树的结点要么是叶子结点，要么它有两个子结点（如果一个二叉树的层数为K，且结点总数是(2^k) -1，则它就是满二叉树。

![img](https://img2018.cnblogs.com/i-beta/1468919/201911/1468919-20191103194220076-925294362.png)

二、完全二叉树

　　若设二叉树的深度为k，除第 k 层外，其它各层 (1～k-1) 的结点数都达到最大个数，第k 层所有的结点都**连续集中在最左边**，这就是完全二叉树

![img](https://img2018.cnblogs.com/i-beta/1468919/201911/1468919-20191103194739538-2034251878.png)



三、平衡二叉树

　　它或者是一颗空树，或它的左子树和右子树的深度之差(平衡因子)的绝对值不超过1，且它的左子树和右子树都是一颗平衡二叉树。

![img](https://img2018.cnblogs.com/i-beta/1468919/201911/1468919-20191103195149869-1317786481.png)

四、最优二叉树（哈夫曼树）

　　树的带权路径长度达到最小，称这样的二叉树为最优二叉树，也称为哈夫曼树(Huffman Tree)。哈夫曼树是带权路径长度最短的树，权值较大的结点离根较近。



## 伪语言:递归建立二叉树

```c++
tree* create()//递归建立二叉树算法
{
    tree* node;
    elemtype x;
    cin >> x;
    if x == 0; node = null;
    else 
    {
        node = new tree; node.data = x;
        p.left->create();
        p.right->create();//递归调用自身
    }
    return node;
}
```



## 1.求一个二叉树某个结点所在子树一共包括多少结点

![](C:\Users\14172\Pictures\009.jpg)

```c++
//设编号为m的结点子树共k层 有k=[log2(n/m)+1] (大于log2(n/m)+1的最小正整数）在m结点的子树的第k层中第一个结点编号为2的k-1次方*m 它是整个
//二叉树的最后一层 所以该结点及其后面的结点个数为tmp=n - pow(2, k - 1) * m + 1;，
//而属于m结点的子树结点最多pow(2,k-1)个 若tmp<pow(2,k-1)说明m结点的子树不是满的（前k-1层是满的）一共pow(2,k-1)-1个结点
//若相等 则是满的
int getnode(int n, int m)
{
	int result;
	int k = int(log(1.0 * n / m) / log(2.0)) + 1;
	int tmp = n - pow(2, k - 1) * m + 1;
	if (tmp < pow(2, k - 1))
		result = pow(2, k - 1) + tmp - 1;
	else
		result = pow(2, k);
	return result;

}
int main()
{
	int m, n;
	while (true)
	{
		cin >> n >> m;
		if (m == 0 && n == 0)
			break;
		cout << getnode(n, m);

	}
```

递归方法:

```c++
int getnode(int n, int m)//递归方法 设左孩子编号2m(如果 存在）右孩子编号2m+1(若存在）
{
	if (2 * m > n)//左孩子不存在 只有m结点一个
		return 1;
	else if (2 * m + 1 > n)//右孩子不存在 有两个结点
		return 2;
	else
		return getnode(n, 2 * m) + getnode(n, 2 * m + 1) + 1;//递归

}
```



## 2.递归遍历算法 假设二叉树 采用二叉链存储结构存储



```c++
//递归算法求从根结点到值为X的结点路径 若无 则返回false
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;//指向右孩子结点
}btnode;
void display(vector<char>& path)//输入路径的所有元素
{
	vector<char>::iterator it;
	for (it = path.begin(); it != path.end(); it++)
		cout << *it;
	cout << endl;
}
bool findpath(btnode* b, char x, vector<char>tmppath, vector<char>& path)//大问题
{
	//tmppath存放临时路径 path存放二叉链b中从根结点到值为x的结点路径path 
	if (b == nullptr) return false;
	else if (b->data == x)
	{
		tmppath.push_back(x);
		path = tmppath;
		return true;
	}
	else
	{
		tmppath.push_back(b->data);
		bool flag = findpath(b->lchild, x, tmppath, path);//分别在左右子树中查找路径 是小问题
		if (flag)
			return true;
		else
			return findpath(b->rchild, x, tmppath, path);
	}
}

```

## 3.层次遍历算法（队列实现）寻找从根结点到值x的结点路径

利用队列一层一层访问二叉树的所有结点

假设二叉树用二叉链存储结构存储（所有结点值为单个字符且不同）

输出路径

```c++
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;
}btnode;
typedef struct {
	btnode* node;//存放当前结点指针
	int parent;//存放双亲结点在队列中的位置
}qytype;//定义非循环队列元素类型
void display(qytype qy[],int front)//输入路径的所有元素
{
	int p = front;
	vector<char>path;
	while (qy[p].parent != -1)//在qy中搜索逆路径并存放到path中
	{
		path.push_back(qy[p].node->data);
		p = qy[p].parent;//p前移
	}
	path.push_back(qy[p].node->data);//根结点的元素存放到path中 是path的最后一个元素
	vector<char>::reverse_iterator it;
	for (it = path.rbegin(); it != path.rend(); it++)
		cout << *it;//反向遍历 输出从根结点到值为x的结点
}
bool findpath(btnode* b, char x)//大问题 二叉链b中寻找从根结点到值x的结点路径
{
	qytype qu[100];//定义非循环队列
	int front, rear;
	front = rear = -1;

	if (b == nullptr) return false;
	
		rear++;
		qu[rear].node = b;//根结点指针入队
		qu[rear].parent = -1;//根结点没双亲结点
		while (front != rear)
		{
			front++;
			b = qu[front].node;//取出队头
			if (b->data == x)
			{
				display(qu, front);
				return true;
			}
			if(b->lchild != NULL)//左孩子结点进队
			{SS
				rear++;
				b->lchild->data = x;
				qu[rear].node = b->lchild;
				qu[rear].parent = front;
			}
			if (b->lchild != NULL)//右孩子结点进队
			{
				rear++;
				b->rchild->data = x;
				qu[rear].node = b->rchild;
				qu[rear].parent = front;
			}

	}
		return false;
		
}
```

### 3.1层次遍历输出所有结点（自定义队列)

```c++
//层次遍历输出各个层次的结点 层次遍历中结点是一层一层处理，结点进队时记录其层次lno,根结点为第一层，h=1,队不空时循环
//如果层次等于H则存入lnode数组，否则输出lnode数组为上一层结点，将lnode清空，处理第h+1层 p结点进队
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;
}btnode;
typedef struct {
	btnode* m;
	int lno;
}qutype;//定义队列

void leveloutput(btnode* b)
{
	queue<qutype>qu;
	qutype e, e1;
	btnode* p;
	char lnode[50];//存放一层的所有结点 即data
	int h, k=0;
	if (b == nullptr) return;
	e.m = b;
	e.lno = 1;//lno为层次 根结点第一层 h=1
	qu.push(e);
	h = 1;//开始为第一次
	while (!qu.empty())
	{
		e = qu.front(); qu.pop();
		p = e.m;
		if (e.lno == h)//如果该结点属于第h层 将其数据存入lnode中
		{
			lnode[k] = p->data;
			k++;
		}
		else
		{
			for (int i = 0; i < k; i++)
				cout << lnode[i];//如果不属于 则输出上一层的所有结点并且清空lnode 将该数据作为第h+1层的第一个元素
			k = 0; h++;
			lnode[k] = p->data;
			k++;
		}
		if (p->lchild != nullptr)
		{
			e1.m = p->lchild;//p结点有左孩子 进队
			e1.lno = e.lno + 1;
			qu.push(e1);
		}
		if (p->rchild != nullptr)
		{
			e1.m = p->rchild;//p结点有右孩子 进队
			e1.lno = e.lno + 1;
			qu.push(e1);
		}

			
	}
	for (int i = 0; i < k; i++)//最后输出最后一层所有结点
		cout << lnode[i]<<" ";
}

int main()
{
	
	btnode* p=new btnode,*a = new btnode,*b = new btnode,*c = new btnode,*f = new btnode;
	p->data = 'A';
	p->lchild = a;
	a->data = 'G';
	p->rchild = b;
	b->data = 'C';
	a->lchild = c;
	c->data = 'Q';
	a->rchild = nullptr;
	c->lchild = c->rchild = nullptr;
	b->lchild = f;
	f->data = 'M';
	f->lchild = f->rchild = nullptr;
	b->rchild = nullptr;
	leveloutput(p);
	

}

```

### 3.2/设计算法假设采用二叉链结构 一次遍历求出不同种类结点个数

```c++
/先序中序后序都可
int n0 = 0, n2 = 0, n1 = 0, nr = 0;
void count(btnode* p)
{
	if (p->lchild != nullptr)
	{
		if (p->rchild != nullptr) n2++;
		else
			n1++;
	}
	else if (p->rchild != nullptr)
	{
		nr++;
	}
	else
		n0++;
	if (p->lchild != nullptr) count(p->lchild);
	if (p->rchild != nullptr) count(p->rchild);
}

```

### 3.3设计算法判断二叉树b的先序和中序序列是否相同

```c++
//采用先序遍历非递归和层次遍历同步进行，一旦结点不同，就返回false
bool prelevel(btnode* b)
{
	btnode* p,*m;
	stack<btnode*>st;
	queue<btnode*>qu;
	st.push(b);
	qu.push(b);
	while (!qu.empty() && !st.empty())//都不空时循环
	{
		p = st.top();
		m = qu.front();
		st.pop(); qu.pop();
		if (p->data != m->data)
			return false;
		if (p->rchild != nullptr)st.push(p->rchild);//注意顺序不一样 对于栈先右孩子再左孩子
		if (p->lchild != nullptr)st.push(p->lchild);//如果p结点有左孩子 进栈
	
			if (m->lchild != nullptr)qu.push(m->lchild);
			if (m->rchild != nullptr)qu.push(m->rchild);
	}
	return true;
}
```

### 3.4//求二叉树b中结点值为x的结点层次

```c++
//先序遍历的递归方法
int level(btnode* b, char x, int h)//调用时h初始为1
{
	int h1, h2;
	if (b == nullptr)return 0;
	
	else if (b->data == x)return h;

	else {
		cout << b->data << " ";
		h1 = level(b->lchild, x, h + 1);
		if (h1 != 0)//找到了
			return h1;
		else //左子树没找到 右子树找
			return (level(b->rchild, x, h + 1));
	}
}

btnode* b = new btnode;
	btnode* a = new btnode;
	btnode* b2 = new btnode;
	btnode* b3 = new btnode;
	btnode* b4 = new btnode;
	btnode* b5 = new btnode;
	btnode* b6 = new btnode;
	a->data = '1';
	b->data = '2';
	b2->data = '3';
	b3->data = '4';
	b4->data = '5';
	b5->data = '6';
	b6->data = '7';
	a->lchild = b;
	a->rchild = b2;
	b->lchild = b3;
	b->rchild = b6;
	b3->lchild = b4;
	b3->rchild = b5;
	b4->lchild = b4->rchild = b5->lchild = b5->rchild = NULL;
	b6->lchild = b6->rchild = NULL;
	b2->lchild = b2->rchild = NULL;
	int mm = level(a, '5', 1);
	cout << mm;

```

### 3.5求非空二叉树高度并求出层次最大的结点

```c++
int height(btnode* b, char& x)
{
	if (b == nullptr) return 0;//空树
	else if (b->lchild == nullptr && b->rchild == nullptr) { x = b->data; return 1; }//叶子结点
	else {
		char x1, xr;
		int h1, h2;
		if (b->lchild != nullptr)
			 h1 = height(b->lchild, x1);//左子树高度及其最大层次结点
		if (b->rchild != nullptr)
			h2 = height(b->rchild, xr);
		if (h1 > h2) {
			x = x1;
			return h1 + 1;
		}
		else {
			x = xr;
			return h2 + 1;
		}

	}

}
```

### 3.6二叉树b的第k层结点个数（二叉链结构存储

```c++
#include <iostream>
#include<queue>
using namespace std;
//求二叉树b的第k层结点个数（二叉链结构存储）法一先序遍历递归 法而 非递归层次遍历//
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;//指向右孩子结点
}btnode;
int nodenum(btnode* b, int h, int k)
{
	//h为b所指的结点层次 初始调用时b为根结点h为1
	if (b == NULL) return 0;
	else if (h == k)//当前结点层次为k 返回1
		return 1;
	else if (h < k)//返回左右子树第k层的结点个数
		return nodenum(b->lchild, h + 1, k) + nodenum(b->rchild, h + 1, k);
}
typedef struct {
	btnode* node;//结点指针
	int level;//结点层次
}qutype;//队列元素类型
int nodenum2(btnode* b, int k)
{
	queue<qutype>qu;
	int numk = 0;
	qutype e,p1;
	e.node = b; e.level = 1;
	qu.push(e);//根结点入队
	while (!qu.empty())
	{
		p1 = qu.front();
		qu.pop();
		if (p1.level == k)
			numk++;
		else if (p1.level < k)
		{
			if (p1.node->lchild != NULL)
			{
				p1.node = p1.node->lchild;
				p1.level = p1.level + 1;
				qu.push(p1);
			}
			if (p1.node->rchild != NULL)
			{
				p1.node = p1.node->rchild;
				p1.level = p1.level + 1;
				qu.push(p1);
			}
		}

	}
}

```

### 3.7求二叉树b的第k层叶结点个数（新算法 层次遍历



- 根结点是第一层的最右结点 根结点层次为1 第k层的最后结点是该层最后访问的结点，该结点访问完毕，进入第k+1层

- 第k+1层最右结点一定是第k层最右结点的孩子

```c++
#include<queue>
using namespace std;
//求二叉树b的第k层叶结点个数（二叉链结构存储）注意叶结点无左右孩子 
//用一种新算法 队列保存结点的地址 child储存当前层最后一个结点的最后孩子 last储存下一层的最右结点
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;//指向右孩子结点
}btnode;
int leafnode(btnode* b, int k)
{
	queue<btnode*>qu;
	btnode* last, * child, * curp;//curp 当前访问结点,child:第level层最后结点的最后一个孩子结点 last:第level的最后一个结点
	if (b == NULL || k < 0) return 0;
	qu.push(b);//根结点入队
	int level = 1;//当前层
	int count = 0;//累计第k层的叶结点个数
	while (!qu.empty())
	{
		curp = qu.front();
		qu.pop();
		if (curp->lchild == NULL && curp->rchild == NULL && level == k)///第k层的叶结点
		{
			count++;
		}
		if (curp->lchild != NULL)
		{
			child = curp->lchild;
		
			qu.push(curp->lchild);
		}
		if (curp->rchild != NULL)
		{
			child = curp->rchild;
			qu.push(curp->rchild);
		}
		if (curp == last)
		{
			last = child;//last此时指向下一层的最后一个结点
				level++;
		}
		if (level > k) break;
	}
	return count;
}
```

### 3.8求二叉树宽度（结点最大的那一层结点数目

```c++
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;//指向右孩子结点
}btnode;

int num[100] = { 0 };//全局变量 存放各个层次的结点数目
void nodes(btnode* b, int h)
{
	if (b == NULL) return;
	num[h]++;//注意在递归调用之前num[i]的数量已经改变 如果子树为空将return，但在return之前num[h]已经发生了改变
	nodes(b->lchild, h + 1);
	nodes(b->rchild, h + 1);
}
int getheight(btnode*b)
{
	nodes(b, 1);//根结点第一层
	int i;
	int max = 0;i = 1;
	while (num[i] != max)
	{
		if (num[i] > max)
			max += num[i];
		i++;
	}
	return max;

}

```

方法二 ，非递归：

```c++
//方法2 层次遍历 用队列元素类型
typedef struct {
	int lno;
	btnode* p;
}qutype;//定义队列
int btnum2(btnode* b)
{
	int num[100] = { 0 };//仍然存放各层的结点数目
	if (b == NULL) return 0;
	btnode* p;
	qutype qu,pp;
	qu.p = b;
	qu.lno = 1;//根结点
	queue<qutype>que;
	que.push(qu);
	while (!que.empty())
	{
		pp = que.front();
		que.pop();
		
			num[pp.lno]++;
			if (pp.p->lchild != NULL)//有左孩子将其进队
			{
				pp.p = pp.p->lchild;
				pp.lno = pp.lno + 1;
				que.push(pp);
			}
			if (pp.p->rchild != NULL)//有右孩子将其进队
			{
				pp.p = pp.p->rchild;
				pp.lno = pp.lno + 1;
				que.push(pp);
			}

	}
	int max=0, i=0;
	while (num[i] != 0)//求出宽度
	{
		if (num[i] > max)
			max = num[i];
		i++;
	}
	return max;
}
```



### 3.9设计算法判断二叉树是否为完全二叉树

```c++
//对完全二叉树的遍历应该满足以下条件：
//若某结点无左孩子则一定无右孩子
//若某节点缺少左孩子或者右孩子，则其所有后继结点一定无孩子
//若不满足上述条件则非完全二叉树 用cm表述遍历中目前二叉树没有违反上述条件，bj表示遍历中目前二叉树所有结点都有左右孩子 一旦违反 设置为false

bool comtree(btnode* p)
{
	bool cm = true;
	bool bj = true;
	btnode* curp;
	queue<btnode*>qu;
	if (p == NULL) return true;//空树为完全二叉树
	qu.push(p);
	while (!qu.empty()&&cm)
	{
		curp = qu.front();
		if (curp->lchild == NULL)   //一个大的if和else在while循环中
		{
			bj = false;
			if (curp->rchild == NULL)
				cm = false;
		}
		else {
			if (bj)//如果curp结点有左孩子
			{
				qu.push(curp->lchild);//左孩子入队
				if (curp->rchild == NULL)//如果有左孩子但无右孩子 置bj为false
					bj = false;
				else
					qu.push(curp->rchild);//如果有右孩子则右孩子入队 继续判断			
			}
			else
				cm = false;//如果到这一步说明curp->lchild!=null &&!bj,则说明这一个结点无右孩子 违反条件2					
			}
		}
	
	return cm;
}
```

### 3.10 找二叉树上任意两个结点的最近共同结点

递归法:

```c++
using namespace std;
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;//指向右孩子结点
}btnode;

btnode* findcomm(btnode*b,char p,char q)
{
	btnode* m,*n;
	if(b == NULL) return NULL;
	if(b->data == p|| b->data == q)
	{
		return b;
	 } 
	 m=findcomm(b->lchild,p,q);
	 n=findcomm(b->rchild,p,q);
	 if(m != NULL && n != NULL) return b;
	 if(m != NULL) return m;
	 if(n != NULL) return n;
	 return NULL;
}
int main()
{
btnode*bt = new btnode;
btnode* m = new btnode;
btnode* n = new btnode;
bt -> data = 'c';
char ch1,ch2;
cout << bt << endl;

cin >> ch1 >> ch2;
m -> data = ch1;
m -> lchild = new btnode;
bt -> rchild = m;
n -> data = ch2;
bt ->lchild = n;
cout << bt << endl;
m = m->lchild;

cin >> ch1 ;
m -> data = ch1;
cout<< findcomm(bt,'u','a');


}
```

方法二:路径匹配法

```C++
bool findnode( btnode* b, char x, vector<btnode *>&path )
{
	bool find;
	if( b == NULL )
	return false;
	if (b -> data == x)
	{
		path.push_back(b);//找到后在路径中添加b结点并返回true
		return true; 
	}
	path.push_back( b ) ; //如果以上情况都不符合的话 先在路径中添加b结点 
	find = findnode( b -> lchild, x, path );
	if( !find )
	find = find( n -> rchild, x, path );//如果左子树中没找到在右子树中找
	if( !find )
	path.pop_back() ;
	return find;//如果没有找到则回退b结点 
}
btnode *lcal( btnode *b, char x, char y )
{
	btnode *p = NULL;
	bool findx, findy;
	vector< btnode * > pathx ;
	vector< btnode * > pathy ;
	
	findx = findnode( b, x, pathx );
	findy = findnode( b, y, pathy );
	if( !findx || !findy )
	{
		return NULL;//如果其中有一个未找到时 
	}
	vector<btnode *>::iterator itx = pathx.begin();
		vector<btnode *>::iterator ity = pathy.begin();
	while(itx != pathx.end() && ity != pathy.end())
	{
		if( *itx == *ity) p = *itx;//如果找到时 将相同的结点地址赋给p
		else break;
		itx++;
		ity++;,//找最后一个相同的结点 
		
	}
	return p;
	
}
```

### 3.11非空二叉树的最大距离

三种情况：

![](C:\Users\14172\Pictures\最大距离.png)

```c++
#define max(a,b) ( (a) > (b) ? (a) : (b)) //求a b中最大值的宏 

using namespace std;
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;//指向右孩子结点
}btnode;
//求非空二叉树最大距离，即二叉树中相距最远的结点之间的距离
// 注意最大距离并非一定经过根结点
//以b为根结点的二叉树最大距离只有三种情况：b左/右子树中到根结点的最大距离，b的左子树结点中到根结点的最大距离+b的右子树结点中到根结点的最大距离
int maxdistance(btnode* b, int &maxleft, int &maxright )
{
	if(b == NULL)
	{
		maxleft = 0;
		maxright = 0;
		return 0;//二叉树为空 
	}
	int maxll, maxlr, maxRL, maxRR;
	int maxdisleft, maxdisright;//分别表示左右子树中的最大距离 
	if(b -> lchild == NULL)
	{
		maxdisleft = 0;
		maxleft = 0;
	}
	else
	{
		//左子树非空
		maxdisleft = maxdistance( b -> lchild, maxll, maxlr );
		maxleft = max( maxll, maxlr ) + 1; //左子树结点中到根结点的最大距离 注意从根节点到左子树结点之间还有距离1要加上 
	}
	if(b -> rchild != NULL)
	{
		maxdisright = 0;
		maxright = 0;
	}
	else
	{
		maxdisright = maxdistance(b -> rchild,maxRL,maxRR);
		maxright = max(maxRL, maxRR) + 1;
	}
	return max(max(maxdisright, maxdisleft), maxright + maxleft);
	
}

```

### 3.12求从根结点到叶结点所有结点之和等于sum的路径

```c++
//每个结点储存一个整数，求从根结点到叶结点路径上所有结点之和等于sum的路径 
void disppath(vector<int> path)
{//正向输出路径
	vector<int>:: iterator ite;
	for( ite = path.begin(); ite != path.end(); ite++)
	{
		cout<<*ite<<" ";
	}
}
void allpathsum(btnode *b, int sum, vector<int> path)
{
	if(b == NULL) return;
	path.push_back(b->data);
	if(b -> lchild == NULL && b -> rchild == NULL)
	{
		if(b -> data == sum)
		disppath(path);
	}
	//如果左子树右子树并非都空
	allpathsum(b->lchild,sum - b->data, path);//左右子树中查找 
	allpathsum(b->rchild, sum- b->data, path) ;
	
	
}
```

### 3.13对于一个给定的的结点值x,输出所有从x到根结点的逆路径

```c++
//每个结点储存一个整数，对于一个给定的的结点值x,输出所有从x到根结点的逆路径 
//逆路径用栈保存
void display(stack<int>path)//输出一条逆路径 
{
	while(!path.empty())
	{
		cout<<path.top()<<" ";
		path.pop();
	}
 } 
 void allpath(btnode *b, int x,stack<int>path)
 {
 	if(b == NULL) return;
 	path.push(b->data);//注意要先将b的值推入栈中再进行判断 因为值为x的结点也要输出 
 	if(b->data == x)//当找到一个为x的结点，逆向输出 
 	display(path);
 	allpath(b->lchild,x,path);
 	allpath(b->rchild,x,path);//在左右子树中查找 
 }
```

S

### 3.14逆路径输出所有异常结点的

![](C:\Users\14172\Pictures\异常结点.png)



```c++

//有一颗给定根结点的二叉树，现在怀疑这棵二叉树有问题，可能存在某些结点不只有一个父节点，编写函数判断是否存在这样的结点
 //如果存在某些结点不只有一个父亲结点，则说明这些结点的指针域相同，遍历二叉树b，将所有结点的指针域存放到向量p中(即存放所有孩子结点的地址）对p排序
 //扫描p,对于相同地址对应的结点q（说明q结点有多个父亲结点，它就是异常结点），输出其逆路径 

	vector<btnode *> p;//全局变量，存放所有结点的非空指针域 
	void trav(btnode *b)//用于求b的所有结点的指针域 
	{
		if(b == NULL) return;
		if(b->lchild != NULL)
		p.push_back(b->lchild);
		if(b->rchild != NULL)
		p.push_back(b->rchild);
		trav(b->lchild);
		trav(b->rchild); 
		
		}	
		void dispath(vector<btnode *>path)//用于输出逆路径 
		{
			
			vector<btnode *>::reverse_iterator it;//反向迭代器 
			for(it = path.rbegin(); it != path.rend(); it++)
			cout << (*it)->data << " ";
			cout<<endl;
		}
		void disallpath(btnode *b,btnode* q,vector<btnode *>path)
		{
			//求所有根结点到q结点的路径
			if(b == NULL) return;
			path.push_back(b);
			if(b == q)
			dispath(path);//递归出口
			disallpath(b->lchild,q,path);
			disallpath(b->rchild,q,path);//左右子树中查找 
			
		}
		bool Same(btnode *b)
		{
			//输出所有存在两个或多个双亲的逆路径
			bool flag = false;
			vector<btnode* >path;
			if(b == NULL) return false;
			trav(b);
			sort(p.begin(), p.end());//对向量排序
			int i = 1;
			while(i < p.size())
			{
				if(p[i] = p[i-1])//找到一个相同指针
				{
					flag = true;
				disallpath(b, p[i], path);
				while(i < p.size() && p[i] == p[i-1])
				
					i++;//跳过重复指针 
			    }
				i++;//没有找到相同指针也需要前移 
			 } 
			 return flag;
			
		}
		
		btnode *pa, *pb, *pc, *pd, *pe, *pg, *pf;

		btnode* creat()//建立异常二叉树 
		{
			pa = new btnode;pa->data = 'a';
			pb = new btnode;pb->data = 'b';
			pc = new btnode;pc->data = 'c';
			pd = new btnode;pd->data = 'd';
			pe = new btnode;pe->data = 'e';
			pg = new btnode;pg->data = 'g';
			pf = new btnode;pf->data = 'f';
			pa->lchild = pb; pa->rchild = pc;
			pb->lchild = pd; pb->rchild = pe;
			pc->lchild = pe; pc->rchild = pf;
			pd->lchild = NULL; pd->rchild = pg;
			pe->lchild = pg; pe->rchild = NULL;
			pf->lchild = pg; pf->rchild = NULL;
			pg->lchild = NULL; pg->rchild = NULL;
			return pa;			
		}
		void clear()
		{
			//销毁二叉树
			delete pa;
			delete pb;
			delete pc;
		 		delete pe;
	 		delete pd;
			 			 			delete pf;
			 				delete pg;
		}
		
int main()
{	
	btnode *b;
	b = creat();
	cout<<"now:\n";
	cout<<Same(b)?"存在":"不存在";
	clear(); 
}
```

### 3.15每个结点储存一个整数，判断是否存在从根结点到叶结点路径上所有结点值之和为sum

```c++
//每个结点储存一个整数，判断是否存在从根结点到叶结点路径上所有结点值之和为sum
bool pathsum(btnode* b, int sum)
{
	if(b == NULL) return false;
	if(b->lchild == NULL && b->rchild == NULL)
	return b->data == sum;
	return pathsum(b->lchild, sum - b->data)||pathsum(b->rchild, sum - b->data);
 } 
```

### 3.16给定二叉树求其中最大的路径和（即该路径上所有结点值之和）

![](C:\Users\14172\Pictures\最大路径和.png)

```c++
//给定二叉树求其中最大的路径和（即该路径上所有结点值之和）可以以任意结点作为起点和终点
//用ans表示二叉树b中最大路径和，考虑以任何一个结点b为根的子树，求出其左子树的最大路径和l，右子树最大路径和r，1 若b为空，对应最大路径和为0
//若l<0 舍弃左子树路径和 若r<0舍弃右子树路径和 若r<0&&l<0舍弃左右子树路径和 
//ans = max(ans,max(0,l)+max(0,r)+b->data) 以b为根的子树的最大路径和为max(0,max(l,r))+b->data

#define max(a,b) ((a) > (b) ? (a) : (b))
#define inf 32767
int maxpathnum(btnode *b,int &ans)
{
	if(b == NULL) return 0;
	int l = maxpathnum(b->lchild, ans);
	int r = maxpathnum(b->rchild, ans);
	ans = max(ans, max(0,l) + max(0, r) + b->data);
	return max(max(l, r) ,0) + b->data;//该返回值用于递归调用 求解左右子树最大路径和 

}

int solve(btnode* b)
{
	int ans = -inf;//初始为负无穷
	maxpathnum(b, ans);
	return ans; 
}
	btnode *pa, *pb, *pc, *pd, *pe, *pg, *pf, *ph;
		btnode* creat()
		{
			pa = new btnode;pa->data = 2;
			pb = new btnode;pb->data = 3;
			pc = new btnode;pc->data = 1;
			pd = new btnode;pd->data = -1;
			pe = new btnode;pe->data = 4;
			pg = new btnode;pg->data = -6;
			pf = new btnode;pf->data = 6;
			ph = new btnode;ph->data = 20;
			pa->lchild = pb; pa->rchild = pc;
			pb->lchild = pd; pb->rchild = pe;
			pc->lchild = pg; pc->rchild = pf;
			pd->lchild = NULL; pd->rchild = NULL;
			pe->lchild = NULL; pe->rchild = NULL;
			pf->lchild = NULL; pf->rchild = NULL;
			pg->lchild = NULL; pg->rchild = ph;
			ph->lchild = NULL;ph->rchild = NULL;//这句话一定要写 经过调试如果这句话没有则无输出 所以一定要设置ph左右子树为空 
			return pa;			
		}
	void clear()
		{
			//销毁二叉树
			delete pa;
			delete pb;
			delete pc;
		 		delete pe;
	 		delete pd;
			 			 			delete pf;
			 				delete pg;
		}
int main()
{	
btnode*m = creat();
cout << solve(m);
	clear();	
 } 
```

### 3.17路径和为某一个值的所有路径 这里的路径是从根结点出发 可以与3.12对比

```c++
//给定二叉树 二叉树中寻找路径和为某一个值的所有路径 路径从根结点出发 temsum存放临时和（初始为0）结点值可能有负数 
vector<btnode *>path;
void display(vector<btnode *> path)
{
	for(int i = 0;i < path.size(); i++)
	cout<<path[i]->data<<" ";
	cout << endl;
}
void findpath(btnode *b, int sum, int temsum, vector<btnode *>path)
{
	if(b == NULL) return;
	path.push_back( b );
	if(temsum + b->data == sum) display(path);
	findpath(b->lchild,sum , temsum + b->data,path);
	findpath(b->rchild,sum, temsum + b->data, path);
	
}
//经过调试发现这样写结果也是一样的：
void findpath(btnode *b, int sum,  vector<btnode *>path)
{
	if(b == NULL) return;
	path.push_back( b );
	if( b->data == sum) display(path);
	findpath(b->lchild,sum - b->data,path);
	findpath(b->rchild,sum - b->data,  path);
	
}
	
	//以下两部分建树和销毁的过程如上一题
		btnode* creat()
		{		
		}
	void clear()
		{

		}
int main()
{
	
btnode*m = creat();
int tmp = 0;
findpath(m, 9, tmp, path);
 } 

```

### 3.18给定二叉树 结点为正整数或者负整数，如何找到一个子树，它所有结点的和最大？

```c++
//后序遍历求所有结点的sum以及最大sum值m，和对应的结点maxp
//在二叉树的结点类型中再增加一个int sum域，用于储存该结点的子树中所有结点值之和 叶结点的sum值为data 对于非叶结点b
//b->sum = 左右孩子sum+b->data 这里的和要加上这个结点本身的值 
vector<btnode *>path;
int trav(btnode *&b, int &m, btnode * &maxp)
{
	//后序遍历求所有结点的sum以及最大sum值m和对应的结点maxp
	if(b == NULL) return 0;
	if(b->lchild == NULL && b->rchild == NULL) //叶结点
	{
		b->sum = b->data;
		if(b->sum > m)
		{
			m = b->sum;
			maxp = b;
		}
		return b->sum;//后序遍历 这一步直接return 
	 } 
	 else{
	 //如果不是叶结点 
	b->sum =  trav(b->lchild,m,maxp) + trav(b->rchild,m,maxp) + b->data;
	if(b->sum > m)
	{
	m = b->sum;
	maxp = b;
	}//最大的sum值和对应的结点同时更新 
	return b->sum;
}
 } 
void maxtree(btnode* b)
{
	btnode* maxp = b;
	int m = b->data;
	trav(b,m,maxp);
	cout<<maxp->data<<"sum:"<<m ;
	
}
int main()
{
	
btnode*m = creat();
int tmp = 0;
maxtree(m);
clear();
 } 
```

### 3.19 计算法采用先序序列化的方法判断一颗二叉树是否是另外一二叉树的子树

```c++
计算法采用先序序列化的方法判断一颗二叉树是否是另外一二叉树的子树
//求出b1先序序列化的序列s1,b2先序序列号的序列s2,若s2是s1的子串则有相同子树
//串的匹配采用KMP
 char * preorder(btnode *b)
 {
 	//由二叉链b产生先序序列化序列Str
	 char *leftstr,*rightstr;
	 char *str = new char[sizeof(char) * maxx];
	 if(b == NULL)
	 {
	 	*str = '#';
	 	*(str+1) = '\0';
	 	return str;
	  } //strcat函数使得字符串1结束的标志符被2的第一个字符代替 
	  *str = b->data;*(str+1) = '\0';//构造只有b->data的字符串str
	  *leftstr = preorder(b->lchild);//求出左子树的先序序列化序列
	  strcat(str,leftstr);//leftstr连接到str后面 
	  *rightstr = preorder(b->rchild); //右子树的先序序列化序列
	  strcat(str,rightstr);
	  return str;
	  
 }
void getnext(char *t,int next[])
{
	//由模式串t求出next值
	int j,k,n = strlen(t); 
	j = 0;k = -1;next[0] = -1;
	while(j < n)
	{
		if(k == -1)
	}
}
```

### 3.20给定一个二叉树， 判断其是否是一个有效的二叉搜索树。

```c++
 public boolean isValidBST(TreeNode root) {
 return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
 }

 public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
 if (root == null)
 return true;
//每个节点如果超过这个范围，直接返回false
if (root.val >= maxVal || root.val <= minVal)
 return false;
 //这里再分别以左右两个子节点分别判断，
//左子树范围的最小值是minVal，最大值是当前节点的值，也就是root的值，因为左子树的值要比当前节点小
 //右子数范围的最大值是maxVal，最小值是当前节点的值，也就是root的值，因为右子树的值要比当前节点大
 return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
 }
```

### 3.21在每个结点中增加一个next指针，用于指向同一层的右兄弟或者堂兄弟， 设计算法实现

如图

![image-20201214134759993](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20201214134759993.png)

```c++
//先序和层次遍历两种方法
#include<queue>
using namespace std;
#define maxn 100
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;
	node* next;
}btnode;
//假设一颗二叉树 在每个结点中增加一个next指针，用于指向同一层的右兄弟或者堂兄弟，如图所示，设计一个算法实现这种 转化
void trans(btnode* b, btnode* sibling)
{
	//先序遍历，用sibling指向当前结点b的兄弟 若b不空，修改其next指针指向sibling
	if (b == nullptr) return;
	else
		b->next = sibling;//如果b不空，修改b的next 
	trans(b->lchild, b->rchild);//递归处理左子树，b左子树的兄弟为b右子树
	if (sibling != nullptr)
	{
		trans(b->rchild, sibling);//当前结点b的兄弟不为空时 右孩子的next改为兄弟的左孩子
	}
	else
		trans(b->rchild, nullptr);//如果当前结点b的兄弟为空，右孩子的next为空
}
//层次遍历方式，按照h = 1,2,3...处理，将每一层的k个结点放到lnode数组中，当一层遍历完毕后，将lnode数组中的结点用next指针链接
typedef struct
{
	int lno;//结点的层次
	btnode* p;//结点指针
}qytype;//队列元素类型
void process(btnode* lnode[], int k)
{
	for (int i = 0; i < k - 1; i++)
		lnode[i]->next = lnode[i + 1];
	lnode[k - 1]->next = NULL;
}
void trans2(btnode* b)
{
	btnode* lnode[maxn];//用于存放一层的所有结点 其中下标表示的是层 lnode[i]是第i层的结点个数
	queue<qytype>qu;
	btnode* tem;
	int k=0;//k记录lnode中结点个数
	if (b == nullptr) return;
		qytype m;
		m.p = b;
		m.lno = 1;
		qu.push(m);
		int h = 1;//从第一层开始
	while (!qu.empty())
	{
		qytype tt = qu.front();
		qu.pop();
		tem = tt.p;
		if (tt.lno == h)
		{
			lnode[k] = tem;//如果该结点是第h层，则将该结点存入lnode中
			k++;//结点个数加一

		}
		else
		{
			//如果该结点不属于第h层 则输出上一层的所有结点
			process(lnode, k);
			k = 0; h++;//结点个数清空 层数加一
			lnode[k] = tem;//该层第一个结点是tem
			k++;//结点个数加一
		}
		if (tt.p->lchild != nullptr)//p结点有左孩子 将其进队
		{
			tem = tt.p->lchild;
			tt.lno = tt.lno + 1;
			qu.push(tt);
		}
		if (tt.p->rchild != nullptr)//有右孩子 进队
		{
			tem = tt.p->rchild;
			tt.lno = tt.lno + 1;
			qu.push(tt);
		}
	}
	process(lnode, k);;//处理最后一层的所有结点的next指针 注意实在while循环的外面

}

```

### 3.22给定一个二叉树及其两其中的两个node求公共父节点到两节点距离之和最小

```c++
//给定一个二叉树及其两其中的两个node(地址均非空） 给出这两个node的一个公共父节点，使得这个父节点
//与两个结点的路径之和最小
 struct treenode
{
	 treenode* left;//指向左右子树
	 treenode* right;
	 treenode* father;//指向父节点
};//二叉树采用三叉链存储结构，根结点的father为NULL,求两个非空结点first second的层次，比较两个层次大小
 //通过Father指针将它们的指针移到相同层次，然后查找公共祖先结点
#define max(x,y) ((x) > (y)?(x):(y))
 int level(treenode* p)
 {
	 int l = 1;
	 if (p->father != nullptr)
	 {
		 p = p->father;
		 l++;
	 }
	 return l;
 }
 treenode* lowestcommom(treenode* first, treenode* second)
 {
	 int i;
	 if (first == nullptr || second == nullptr) return nullptr;
	 int l1 = level(first);
	 int l2 = level(second);
	 if (l1 < l2)
	 {
		 for (int i = l1; i < l2; i++)
			second = second->father;//如果Second层次更大 则second在first下面部分
	 }
	 else if (l1 > l2)
	 {
		 for (int i = 0; i < l1 - l2; i++)
			 first = first->father;
	 }
	 while (first != second)
	 {
		 first = first->father;
		 second = second->father;
	 }
	 return first;
 }

```

### 3.23树中每一个结点放一个整数 函数返回这棵二叉树中相差最大的两个结点间差的绝对值

```c++
//编写函数 树中每一个结点放一个整数 函数返回这棵二叉树中相差最大的两个结点间差的绝对值
 //通过一次先序遍历求出最大结点指max和最小结点指min 返回abs(max - min)
 void maxmin(btnode* p, int& max, int& min)
 {
	 //求最大最小值的函数
	 if (p == nullptr) return;
	 if (p->data > max)
		 max = p->data;
	 if (p->data < min)
		 min = p->data;
	 maxmin(p->lchild, max, min);
	 maxmin(p->rchild, max, min);//递归处理左右子树
 }
 int fun(btnode* b)
 {
	 if (b == nullptr) return 0;
	 int max, min;
	 max = min = b->data;//初始化最大最小值
	 maxmin(b, max, min);
	 return abs(max - min);
 }
```

### 3.24 设计算法用递归实现输出二叉树的层次遍历

```c++
//以二叉链存储的二叉树 设计算法用递归实现输出二叉树的层次遍历
//采用一个全局两层向量result存放先序遍历结果 只是按结点b的层次h将其存放在result[h-1]向量元素中，然后
//一层一层输出result即构成层次遍历序列

vector<vector<int>>result;
void travel(btnode* b, int h)
{
	if (b == NULL) return;
	if (h > result.size())
	{
		result.push_back(vector<int>());
	}
	result[h - 1].push_back(b->data);
	travel(b->lchild, h + 1);
	travel(b->rchild, h + 1);
 
}
void levelorder(btnode* b)
{
	travel(b, 1);
	for (int i = 0; i < result.size(); i++)
		for (int j = 0; j < result[i].size(); j++)
			cout << result[i][j]<<" ";//输出结果

}
```

### 3.25//设计算法删除一个二叉树

```c++
//设计算法释放二叉树
//只能用后序遍历，不能前序或者中序，因为只有将子树空间释放后才能释放根结点的空间，否则会丢失子树
void deletetree(btnode* b)
{
	if (b != nullptr)
	{
		deletetree(b->lchild);
		deletetree(b->rchild);
		delete b;
	}
}
```

### 3.26设计算法删除二叉树中以p为根结点指针的子树

```c++
//设计算法删除二叉树中以p为根结点指针的子树
void deletetree(btnode* b)
{
	if (b != nullptr)
	{
		deletetree(b->lchild);//删除并释放左子树
		deletetree(b->rchild);//..右子树
		delete b;//最后释放根结点
	}
}
void delp(btnode*& b, btnode*p)//采用先序遍历先找到p结点，调用deletetree算法删除以p为根结点指针的子树 并置b为null
{
	if (b != NULL)//该函数删除并释放p结点的子树
	{
		if (b == p)
			deletetree(b);
		b = NULL;
	}
	else
	{
		delp(p->lchild, p);
		delp(p->rchild, p);
	}
}
```

### 3.27设计算法把树b所有结点的左右子树交换 

```c++
//采用递归模型（基于后序遍历）要求空间复杂度为o(1)
void swap(btnode* b)
{
	if (b != NULL)
	{
		swap(b->lchild);
		swap(b->rchild);
		btnode* temp = b->lchild;
		b->lchild = b->rchild;
		b->rchild = temp;//交换指针域
	}
}

```

### 3.28设计算法把树b所有结点的左右子树交换要求不破坏原二叉树的结构

```c++
btnode* swap(btnode* b)//思路：交换b的左右子树，产生新的二叉树t
{
	btnode* t1, *t2,*t;

	if (b == NULL) t =  NULL;

	
	else

	{
		t = new btnode;
		t->data = b->data;//复制根结点
		t1 = swap(b->lchild);//
		t2 = swap(b->rchild);
		t->lchild = t1;
		t->rchild = t2;
	
	}
	return t;
}
```

### 3.29利用结点的右孩子指针将一棵二叉链存储的二叉树的叶节点按从左往右顺序串成单链表

```c++
void link(btnode* b,btnode*&head,btnode*&tail)//head指向单链表的首结点（初始值为NULL） tail指向单链表的尾结点
{

	//先序遍历递归算法，用尾插法构建叶节点的单链表 head指向建立的单链表的首结点 
	if (b != NULL)
	{//初始调用时head = null
		if (b->lchild != NULL && b->rchild != NULL)
		{
			if (head == NULL)
			{
				head = b;
				tail = head;//尾插法 head变为新的尾部 
			}
		}
		else
		{
			if (b->lchild != NULL)
				link(b->lchild, head, tail);
			if (b->rchild != NULL)
				link(b->rchild, head, tail);
		}
			
	}
}
void create(btnode* b)
{
	btnode* head = NULL, *tail;
	link(b, head, tail);//创建由叶节点构成的单链表
	tail->rchild = NULL;
	//尾结点的rchild置空
	btnode* p = head;
	while (p != NULL)//输出叶节点的单链表
	{
		cout << p->data << " ";
		p = p->rchild;
	}


}
```



## 4.经典的用栈实现先序遍历非递归





```c++
using namespace std;
//二叉链：令二叉树的每一个结点对应一个链表结点 链表结点中存放数据信息和左右孩子的指针
//假设二叉树采用二叉链存储 设计先序遍历非递归算法
//经典的用栈实现先序遍历非递归 栈中存放未访问的结点 在出栈时访问
typedef struct node {
	char data;
	struct node* lchild;
	struct node* rchild;
}btnode;
void pretra1(btnode* b)
{
	btnode* p = b;
	stack<btnode*>st;

	
	if (p != nullptr)
	{
		st.push(p);//根进栈
		while (!st.empty())
		{
			p = st.top();//修改p
			cout << p->data;
			st.pop();
			if (p->rchild != nullptr)
				st.push(p->rchild);
			if (p->lchild != nullptr)
				st.push(p->lchild);//先进右孩子结点再将左孩子结点进栈
		
		}
	}
}
//解法2：栈中存放已经访问的结点 沿着根结点左下方一边访问一边进栈
void pretra2(btnode* b)
{
	btnode* p = b;
	stack<btnode*>st;

	while (p != nullptr && !st.empty())
	{
		while (p != nullptr)//先将所有左结点全部进栈 注意这一步循环的条件是p!=null而不是p.lchild!=null
		{
			cout << p->data;//访问结点p			
			st.push(p);
			p = p->lchild;//P变为左下结点 如果左下结点为空将退出循环继续进行出栈转向右子树
		}
		if (!st.empty())//栈不空 出栈并转向右子树 并没有在这一步进行访问操作 也没有进行进栈
		{
			p = st.top();
			st.pop();
			p = p->rchild;
		}
	}
}

```



## 5.morris算法先序遍历非递归

```c++
void pretrav3(btnode* p)//morris算法先序遍历非递归
{
	btnode* b = p;
	btnode* node, *prev;
	
	while (b != nullptr)
	{
	

		if (b->lchild == nullptr)
		{
			cout << b->data;
			prev = b;//prev指向刚刚访问的结点
			b = b->rchild;//转向右子树
		
		}
		else
		{
			node = b->lchild;//左子树中找最右下结点
			while (node->rchild != nullptr && node->rchild !=b)
				node = node->rchild;
			if (node->rchild == nullptr)//如果node没有右孩子表示没有线索化 建立新线索
			{
				cout << b->data;
		
				prev = b;//指向刚刚访问的结点
				node->rchild = b;
				b = b->lchild;//继续指向左结点
			}
			else//如果有右孩子则已经线索化 删除线索
			{
				node->rchild = nullptr;//恢复为空
				b = b->rchild;//继续转向右子树
			}
		}
	}
}
```



![](C:\Users\14172\Pictures\98.jpg)

![](C:\Users\14172\Pictures\99.jpg)

## 6.中序遍历非递归

```c++
void pretrav3(btnode* p)
{
	stack<btnode*>st;
	btnode* b = p;

	while (b != nullptr || !st.empty())
	{
		while (b != nullptr)
		{
			st.push(b);
			b = b->lchild;
		}
		if (!st.empty())
		{

			cout << st.top()->data;
		
			b = st.top()->rchild;
			st.pop();
		}
	}

	
}
```

## 7.//后序遍历非递归

```c++
void pretrav3(btnode* p)
{
	stack<btnode*>st;
	btnode* b = p,*q;
	do {
		while (b != nullptr)//左下结点进栈
		{
			st.push(b);
			b = b->lchild;
		}
		q = nullptr;
		while (!st.empty())
		{
			b = st.top();//取栈顶
			if (b->rchild == q)//如果右孩子为空或者已经遍历
			{
				
				cout << st.top()->data;
				st.pop();
				q = b;

			}
			else//如果右子树没有遍历则进行遍历
			{
				b = b->rchild;
				break;//注意这个
			}
		}
	} while (!st.empty());
	
}
```

另一写法：利用双端队列和列表

```java
public List<Integer> postorderTraversal(TreeNode root) 
{
if (root == null)
{
return Collections.emptyList();
}
Deque<TreeNode> s1 = new ArrayDeque<>();
List<Integer> s2 = new ArrayList<>();
s1.push(root);
while (!s1.isEmpty()) 
{
TreeNode node = s1.pop();
s2.add(node.val);
if (node.left != null) 
{
s1.push(node.left);
}
if (node.right != null) {
s1.push(node.right);
}
}
Collections.reverse(s2);
return s2;
}
```







## 8.小球从结点1处依次开始下落，最后一个小球将会落到哪里呢？

一些小球从结点1处依次开始下落，最后一个小球将会落到哪里呢？输入叶子深度D和 小球个数I，输出第I个小球最后所在的叶子编号。假设I不超过整棵树的叶子个数。D≤20。 输入最多包含1000组数据。

输入4，2 输出12 输入3 4 输出7



![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-13.png)



```c++
struct Node{
bool have_value; //是否被赋值过
int v; //结点值
Node* left, * right;
Node() :have_value(false), left(NULL), right(NULL) {} //构造函数
};
const int maxd = 20;
int s[1 << maxd];//最大结点个数
maxd-1
int main()
{
	int depth, m;
	cin >> depth >> m;
	int num = (1<< depth) - 1;//最大结点的编号num.第一个结点编号为1
	memset(s, 0, sizeof(s));
	
	for (int i = 0; i < m; i++)//连续让I个小球下落
	{
		int k = 1;//注意这句话的位置 每次小球下落都令k初始化为1
		for(; ; )
		{
			s[k] = !s[k];
			if (s[k] == 1)//根据开关状态选择下落方向
				k = 2 * k;
			else
				k = 2 * k + 1;
			
			if (k > num)
				break;
			
		}
		if(i==m-1)
		cout << k / 2<<" ";//出界之前结点编号
	}
	//方法2

		if (m % 2) { m = (m + 1) / 2; k = 2 * k ; }
		else { m = m / 2; k = 2 * k + 1; }
	cout << k/2;

}
```

## 9.给一棵点带权的二叉树找一个叶子使得它到根的路径上的权和最小。

给一棵点带权（权值各不相同，都是小于10000的正整数）的二叉树的中序和后序遍 历，找一个叶子使得它到根的路径上的权和最小。如果有多解，该叶子本身的权应尽量小。 输入中每两行表示一棵树，其中第一行为中序遍历，第二行为后序遍历。

```c++
const int maxv = 10000 + 10;
int in_order[maxv], post_order[maxv], lch[maxv], rch[maxv];//数组 全局变量
int n;
bool gettree(int* a)
{
	string linee;
	if (!getline(cin, linee)) return false;
	stringstream ss(linee);
	int num;
	n = 0;
	while (ss >> num) { a[n] = num;n++; }
	return n > 0;
}
//3 2 1 4 5 7 6
//3 1 2 5 6 7 4 样例输出：1 

//把in_order[L1..R1]和post_order[L2..R2]建成一棵二叉树，返回树根
//因为各个结点的权值各不相同且都是正整数，直接用权值作为结点编号
int build(int L1, int R1, int L2, int R2)
{
	if (L1 > R1)return 0;//空树
	int root = post_order[R2];//后序遍历最后一个是根结点
	int p = L1;
	while(in_order[p] != root) p++;
	int cns = p - L1; //左子树结点个数
	lch[root] = build(L1, p - 1, L2, L2 + cns - 1);
	rch[root] = build(p+1, R1, L2 + cns, R2 - 1);
	return root;
}
int best = 0, bestnum = INT_MAX;//目前为止的最优解和对应的权和
void dfs(int u,int sum)
{	
	sum += u;
	if(!lch[u] && !rch[u]) //到达叶子
	{
		if (sum < bestnum || (sum == bestnum && u < best))
		{
			best = u;
				bestnum = sum;
		}
		return;
	}
	if (lch[u])  dfs(lch[u],sum);
	if (rch[u])  dfs(rch[u],sum);
}
int main()
{
	int sum = 0;
	if (gettree(in_order) && gettree(post_order))
	{
		int root = build(0, n - 1, 0, n - 1);
		dfs(root, sum);
		cout << best;
	}
;
}
```

## 10.天平问题



输入一个树状天平，根据力矩相等原则判断是否平衡。如图6-5所示，所谓力矩相等， 就是WlDl=WrDr，其中Wl和Wr分别为左右两边砝码的重量，D为距离。 采用递归（先序）方式输入：每个天平的格式为Wl，Dl，Wr，Dr，当Wl或Wr为0时，表 示该“砝码”实际是一个子天平，接下来会描述这个子天平。当Wl=Wr=0时，会先描述左子天 平，然后是右子天平。

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-15.png)

//样例输入：
//1
//0 2 0 4
//0 3 0 1
//1 1 1 1
//2 4 4 2
//1 6 3 2   yes

```c++
//输入一个子天平，返回子天平是否平衡，参数W修改为子天平的总重量
bool ifequ(int& w)
{
	int w1,r1,w2,r2;
	cin >> w1 >> r1 >> w2 >> r2;
	bool f1 = true, f2 = true;

	if (!w1) f1 = ifequ(w1);
	if (!w2) f2 = ifequ(w2);//注意W1 W2在调用函数时数值也发生了改变
	w = w1 + w2;//引用作为参数故可以直接改变 这也是非常关键的地方
	return f1 && f2 && (w1 * r1 == w2 * r2);
		
}

int main()
{
	int T, n;
	cin >> T;
	while (T--)
	{
		if (ifequ(n)) cout << "yes";
		else cout << "no\n";
		if (T) cout << "\n";
	}
}
```

## 11.从左向右输出每个水平位置的所有结点的权值之和

```c++
const int maxd = 20;
int sum[maxd];//全局变量 maxd是位置 sum[i]是这一位置的权值之和
//给一棵二叉树，每个结点都有一个水平位置：左子结点在它左边1个单位，右子结点在右
//边1个单位。从左向右输出每个水平位置的所有结点的权值之和。如图6 - 7所示，从左到右的3个
//位置的权和分别为7，11，3。按照递归（先序）方式输入，用 - 1表示空树
void build(int p)//输入并统计一棵子树，树根水平位置为p
{
	int v; cin >> v;
	if (v == -1)return;
	sum[p] += v;
	build(p - 1);//注意这里的顺序很关键 先序遍历 要先左子树 如果先build(p+1)结果会错误
	build(p + 1);
}
bool init()//边读入边统计
{
	int i; cin >> i;
	if (i == -1) return false;
	memset(sum, 0, sizeof(sum));
	int pos = maxd / 2;//树根的水平位置
	sum[pos] = i;
	build(pos - 1);
	build(pos + 1);
}
int main()
{
	int casee=0;
	while (init())
	{
		int p = 0;
		while (sum[p] == 0)p++;//找最左边的叶子
		
			cout << ++casee<<":"<<sum[p++] << " ";
		
		while (sum[p] != 0) { cout << sum[p++] << " "; }
		cout << endl;
	}

}
```



# 哈夫曼树的建构

//构造哈夫曼树的过程中，每次都是合并两棵子树，所以哈夫曼树是一棵二叉树，其中，没有单分支结点

不一定是一棵完全二叉树

//将哈夫曼树的左分支加0 右分支加一 从根结点到叶节点所经过的分支对应的0 和1组成的序列便为该节点对应字符的哈夫曼编码

原则是权值越小的结点离根结点越远，对应的哈夫曼编码越长，权值越大的结点离根结点越近，对应的越短，在一组字符的哈夫曼编码中不存在一个编码是另一个编码的前缀的情况

```c++
//哈夫曼树的建构思想：反复选择两个最小的元素，合并，直到只剩下一个元素}
//用优先级队列 先初始状态下将所有的数压入其中 每次都从顶部取出两个最小的数
//使树的带权路径长度最小
priority_queue<long long, vector<long long>, greater<long long>>q;

int main(void)
{
	long long n,i, temp, x,y,ans=0;
	cin >> n;
	for (i = 0; i < n; i++)
	{
		cin >> temp;
		q.push(temp);

	}
	while (q.size() > 1)//只要优先队列中至少有两个元素
	{
		x = q.top();
		q.pop();
		y = q.top();
		q.pop();
		q.push(x+y);//取出堆顶两个元素 和压入队列
		ans += x + y;//ans累计求和的结果
	}
	cout << ans;
}
```

## 1设计算法求二叉树b的带权路径长度wpl

```c++
//二叉树以二叉链存储 结点为整数 设计算法求二叉树b的带权路径长度wpl
 //先序遍历的思路 用h表示b当前的层次（b指向根结点时h=1)wpl初始为0 
 void wpll(btnode* b, int h, int& wpl)
 {
	 if (b == nullptr) return;
	 if (b->lchild == NULL && b->rchild == NULL)
		 wpl += b->data * (h - 1);//为叶节点时
	 {
		 wpll(b->lchild, h - 1, wpl);//非叶节点
		 wpll(b->rchild, h - 1, wpl);
	 }
 }
 int solve(btnode* b)
 {
	 int h = 1,wpl = 0;
	 wpll(b, h, wpl);
	 return wpl;
 }

```





# 并查集

## 1.压缩

```c++
//把当前查询路径上的所有结点指向根结点
//先找到X的根结点，从X开始走将路径上全部结点指向根结点
int father[1000];
int findfather(int x)
{
	int z = x;//因为x会发生变化先用Z保存
	while (x != father[x])
	{
		x = father[x];
	}
	//到这里，x存放的是根结点 下面把路径上所有节点的father结点改为x
	while (z != father[z])
	{
		int a = z;//因为Z要被父节点覆盖 先提前保存
		z = father[z];//回溯父亲结点
		father[a] = x;//将原先的结点z的父节点指向根结点
	}
	return x;//返回根结点
}
```

## 2.调整结点，建堆，堆排序

```c++
const int maxx = 100;
int heap[maxx],n;
//用数组来存储完全二叉树，这样结点就按层序存储于数组中，第一个结点存储于数组的一号位，若一个结点是i号位置则左孩子是2i号位
//对于heap[low,high]数组进行向下调整，使结点的孩子权值都比结点的权值小或者不存在孩子结点
//low为与调整结点的数组下标 ，heap一般为堆的最后一个元素的数组下标
void downadjust(int low, int high)
{
	int i = low, j = 2 * i;//i为欲调整结点，j为其左孩子
	while (j <= high)//存在孩子结点
	{
		if (j + 1 <= high && heap[j + 1] > heap[j])
		{
			j = j + 1;//让j存储右孩子下标
		}
		if (heap[j] > heap[i])//如果孩子的权值大于欲调整的结点权值
		{
			swap(heap[j], heap[i]);//交换权值最大的孩子与欲调整的结点
			i = j;
			j = 2 * i;//保持i为欲调整结点 j为左孩子
		}
        else
            break;
	
	}
}
//那么建堆的过程就容易了，对于一个序列有n个元素时，叶子结点有[2/n]个，因此下标大于2/n的均无叶子结点
//从[2/n]号位置开始倒着枚举结点，对每个遍历到的结点i进行[i,n]的调整 为什么倒着枚举呢，因为这样的话保证了每个结点都是以其为
//根结点的子树中权值最大的结点
void createheap()
{
	for (int i = 0; i < n / 2; i++)
	{
		downadjust(i, n);
	}
}

//堆排序，堆中只剩一个元素时结束
void sortt()
{
	createheap();
	for (int i = n; i > 1; i--)
	{
		swap(heap[i], heap[1]);//先交换堆顶与第i个结点
		downadjust(1, i - 1);//调整堆顶
	}
}
```





图的遍历伪代码

# 图论

## 1.给出若干人直接的通话长度判断是否是犯罪团伙

![](C:\Users\14172\OneDrive\图片\屏幕快照\2020-11-24.png)

 

```c++
//犯罪头目题
const int maxn = 10000;//总人数
const int INF = 10000000;//无穷大
map<int, string>idtostring;//编号->姓名
map<string, int>stringtoid;//姓名->编号
map<string, int>gang;//姓名->人数
int G[maxn][maxn] = { 0 }, weight[maxn] = { 0 };//零接矩阵G，点权weight
int n, k, numberperosn = 0;//边数n,下限k,总人数
bool vis[maxn] = { false };//标记是否被访问

//dfs访问单个连通块，nowvisit为当前访问的编号
//head-头目，number-成员编号，totalvalue-连通块总边权 number是具体某一个犯罪团伙的成员
void dfs(int nowvisit, int& head, int& number, int& totalvalue)
{
	number++;//成员人数加一
	vis[nowvisit] = true;//标记为已经访问
	if (weight[nowvisit] > weight[head])
		head = nowvisit;//如果当前访问的点权大于头目点权，更新头目。题目规定头目点权为最大值。
	for (int i = 0;i<numberperosn; i++)//枚举所有人 注意与number区分
	{
		if (G[nowvisit][i] > 0)//如果能从nowvisit到达i
		{
			totalvalue += G[nowvisit][i];//连通块的边权增加该边权
			//由于可能有环所以可能结点已经被访问过 为了防止边权不漏加因此先累加边权 
			G[nowvisit][i] = G[i][nowvisit] = 0;//删除这条边防止回头

			if (!vis[i])//如果未被访问 则递归访问i
				dfs(i, head, number, totalvalue);
		}//注意这个大括号的位置不能错！！！if (!vis[i]是在if (G[nowvisit][i] > 0)里面
	}
	
}
void dfsmap()
{
	//遍历整个图 获取连通块的信息
	for (int i = 0; i < numberperosn; i++)
	{
		if (!vis[i])
		{
			int head = i;
			int number = 0, totalvalue = 0;//头目，成员数和总边权 每个遇到新的!vis[i]都会重置一边
			dfs(i, head, number, totalvalue);//遍历i所在的连通块
			if (number > 2 && totalvalue > k)//如果成员人数大于2且边权大于阈值，则为符合条件的团伙，将其保存下来
				gang[idtostring[head]] = number;//gang是头目为head 成员人数为number的map
		}
	}
}
int change(string r)//返回姓名r对应的编号
{
	//要先检验
	if (stringtoid.find(r) != stringtoid.end())//如果已经出现过
	{
		return  stringtoid[r];//返回编号
	}
	else
	{
		stringtoid[r] = numberperosn;//r的编号是numberperson
		idtostring[numberperosn] = r;//numberperson对应r
		return numberperosn++;//总人数加一

	}
}
int main(void)
{
	int id1,id2,bianshu;
	cin >> n >>k;
	string aa, bb;
	for (int i = 0; i < n; i++)
	{
		cin >> aa >> bb>>bianshu;
		id1 = change(aa);//将姓名aa bb转化为编号
		id2 = change(bb);
		G[id1][id2] += bianshu;
		G[id2][id1] += bianshu;
		weight[id1] +=bianshu;
		weight[id2] +=bianshu;//id1和id2的点权都增加bianshu
		
	}
	dfsmap();//遍历整个图的所有连通块
	cout << gang.size()<<"\n";
	map<string, int>::iterator it;
	int total = 0;
	for (it = gang.begin(); it != gang.end(); it++)//遍历所有的团伙
	{
		cout << it->first << ":" << it->second << "\n";
	}
	
}
```

## 2.微博的最大用户转发数bfs

bfs

```c++
#include<iostream>
#include<vector>
#include<cstring>
#include<queue>
using namespace std;

const int maxn = 10000;
struct node
{
	int layer;
	int id;//结点编号和层号
};
vector<node>adj[maxn];//邻接表的bfs遍历

bool inq[maxn] = { false };
int bfs(int s, int L)//start-起始层 L层数上限
{
	node start;
	int numforward = 0;//转发数
	start.id = s;
	start.layer = 0;
	queue<node>q;
	q.push(start);
	int lay;
	inq[s] = true;//遍历以后标记结点设为true
	while (!q.empty()) {
		node topnode = q.front();
		q.pop();
		int num = topnode.id;//队首结点编号
		
		for (int i = 0; i < adj[num].size(); i++)
		{
			node next = adj[num][i];
			next.layer = topnode.layer+1;
			if (inq[next.id] == false && next.layer<= L)
			{
				inq[next.id] = true;//标记为已经遍历过
				numforward++;
				
				
				q.push(next);
			}
		}
		
	}
	return numforward;
	

}
int main()
{
	int numpeople, shangxian,idnum;//人数和层数上限 编号
	cin >> numpeople >> shangxian;
	node user;
	int number;
	for (int i = 1; i <= numpeople; i++)
	{
		user.id = i;//第i个用户
		cin >> number;//i号用户关注的人数
		for (int j = 0; j < number; j++)
		{
			cin >> idnum;


			adj[idnum].push_back(user);
		}
	}
		int qury,s;//查询的人数 s:起始结点编号
		cin >> qury;
		for (int i = 0; i < qury; i++)
		{
			//对于每一个发信息的用户来说 都要从根开始访问 bool全要重新设置为False
			cin >> s;
			memset(inq, false, sizeof(inq));

			
			int idd = bfs(s, shangxian);
			cout << idd << "  ";

		}
}

```



## 3.狄杰特斯拉算法（单源最短路径

迪杰斯特拉算法主要特点是从起始点开始，采用[贪心算法](https://baike.baidu.com/item/贪心算法/5411800)的[策略](https://baike.baidu.com/item/策略/4006)，每次遍历到始点距离最近且未访问过的顶点的邻接节点，直到扩展到终点为止。

例子：

#### [743. 网络延迟时间](https://leetcode-cn.com/problems/network-delay-time/)

难度中等493

有 `n` 个网络节点，标记为 `1` 到 `n`。

给你一个列表 `times`，表示信号经过 **有向** 边的传递时间。 `times[i] = (ui, vi, wi)`，其中 `ui` 是源节点，`vi` 是目标节点， `wi` 是一个信号从源节点传递到目标节点的时间。

现在，从某个节点 `K` 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 `-1` 

细节问题：初始化时一定要把gragh distance初始化，否则得不到正确结果，Integer.MAX_VALUE/ 2可以换成其他的比题目规定的最长路径更大的数字

```c++
 public int networkDelayTime(int[][] times, int n, int k) {
boolean[]vis = new boolean[n];
int[][]Graph = new int[n][n];//graph[i][j]:从源点i到目标点j
int[]distance = new int[n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(Graph[i], Integer.MAX_VALUE/ 2);
        }
        for (int[] t : times) {
            int x = t[0] - 1, y = t[1] - 1;
            Graph[x][y] = t[2];
        }
//vis[k] =true;
Arrays.fill(distance,Integer.MAX_VALUE/ 2);
distance[k-1] = 0;//到自身距离为0
for(int i = 0;i <n;i++)
{
int u = -1;
for(int j = 0;j <n;j++)
{
    if(vis[j] == false && (u== -1 || distance[j] < distance[u] ))
    {
        u = j;
    }
}
vis[u] = true;

            for (int y = 0; y < n; ++y) {
                distance[y] = Math.min(distance[y], distance[u] + Graph[u][y]);
            }
}

int max = 0;
 
        int ans =Arrays.stream(distance).max().getAsInt();
for(int i = 0;i< distance.length;i++)
    max = Math.max(distance[i],max);
    return ans ==Integer.MAX_VALUE/ 2 ? -1 : ans;

}
```



## 4.多条最小距离的路径时利用其他条件

```c++
//给定N个城市 M个无向边，每个城市都有一定数目的救援小组，边权已知，
//图G（V,E)求出起点到终点的最短路径条数和最短路径的最大边权
int G[maxn][maxn], n,m,st,sd;//n:顶点数 m:边数（自己设置 st起点 sd终点）图一般是全局变量
int d[maxn],w[maxn],weight[maxn];//起点到各顶点的最短路径长度 w[i]:从起点到i的最大点权之和 weight[i]点权
bool inq[maxn] = { false };
int num[maxn];//num[i] 从起点到i的最短路径条数
//邻接矩阵法  该算法只能应对所有边权都是非负数的情况
void dijksra(int begin)//begin为起点
{
	
	memset(num, 0, sizeof(num));
	memset(w, 0, sizeof(w));//一开始全都设置为0
	num[begin] = 1;//只将这条路径设置为1
	w[begin] = weight[begin];
	fill(d, d + maxn, inf);
	d[begin] = 0;//到自身的距离为0
	int u;
	for (int i = 0; i < n; i++)//循环n次
	{
		int min = inf;
		u = -1;//u使得d[u]最小 min存放最小的d[u]
		for (int j = 0; j < n; j++) {//找到未访问的顶点中d[]最小的
			if (inq[j] == false && d[j] < min)
			{
				
				u = j;
				min = d[j];
				
			}
		}


		if (u == -1) return;//如果u未发生变化则找不到小于inf的d[u],说明剩下的顶点和begin不连通
			inq[u] = true;//标记为已经访问过
			for (int j = 0;j < n;j++)//如果j未访问&&u可以到达j&&以u为中介点可以使得d[j]更优
			{
				if (inq[j] == false && G[u][j] != inf )
				{
					if (G[u][j] + d[u] < d[j])
					{
						d[j] = G[u][j] + d[u];
						num[j] = num[u];
						w[j] = w[u] + weight[j];
					}
					else if (G[u][j] + d[u] == d[j])//说明有多条最小距离的路径
					{
						if (w[u] + weight[j] > w[j])//如果以u为中介的点权之和更大时
						{
							w[j] = w[u] + weight[j];
						}

						num[j] += num[u];
					}
					
				}
			}
		}
	}



int main()
{
	int u, v, j;
	cin >> n >> m>>st >> sd;//输入顶点个数 边数 起点编号（点的编号从0开始
	fill(G[0], G[0] + maxn * maxn, inf);//初始化图G一开始全都是无穷大
	for (int i = 0; i < n; i++)
		cin >> weight[i];//读入点权
	for (int i = 0; i <m; i++)
	{
		cin >> u >> v >> j;//输入点u v以及从u到v的边权
		G[u][v] = j;
		G[v][u] = G[u][v];//表示无向边
	}
	dijksra(st);//迪杰斯特拉算法入口
	cout << num[sd] << " " << w[sd];//最短路径条数 最短路径的最大点权

}

```

## 4狄杰特斯拉结合dfs

先找出所有路径，再遍历所有路径，找出一条使第二标尺最优的路径。

递归边界：如果当前结点是叶子结点则达到递归边界，这时temppath存放了一条临时路径，将路径求出第二标尺的值value并与optvalue比较看是否要更新optvalue;

由于递归的原因，存放在temppath的结点是逆序的，因此访问结点需要倒着进行，如果仅仅是对边权或者点权求和，正序访问也是可以的。

- 注意：叶子结点是起始点

  

![](C:\Users\14172\Pictures\突变里.png)

第二标尺最优值的计算：

```c++
int st;
int optvalue;//opt为第二标尺最优值 
vector<int>path, temppath;//一条最优的路径和临时的路径
void dfs(int v)//v为当前访问的结点
{
	if (v == st)//如果到了叶子结点，即起始点
	{
		int value;//value为临时路径上的第二标尺的值
		temppath.push_back(v);//将路径起始点加入到临时路径的最后面
		//计算temmpath路径上的第二标尺value的值
		if (value > optvalue)
		{
			optvalue = value;//更新第二标尺的值和最优路径
			path = temppath;
		}
		temppath.pop_back();//删除刚刚加入的结点
		return;
	}
	//if v != st
	temppath.push_back(v);//当前访问的结点加入到临时路径temppath的后面
	for (int i = 0; i < pre[v].size(); i++)
	{
		int nex = pre[v][i];//对v的前驱结点进行递归访问 前驱结点pre[v][i]
		dfs(nex);
	}
	temppath.pop_back();//遍历完所有的前驱结点 删除刚刚加入的结点
}

```



计算一条路径上边权和点权大小：

```c++
int value=0;//边权
	for (int i = temppath.size() - 1; i > 0; i--)
	{
		int id = temppath[i],nextid = temppath[i-1];//当前结点为id,下一个结点为nextid
		int nextint = temppath[i - 1];//循环条件为i>0
		value +=  G[id][nextid];
	//value增加从id到nextid的边权 这里G表示一个点到另一个点之间边权大小
	}
	int value = 0;//点权之和
	for (int i =  temppath.size()-1; i >= 0; i--)
	{
		int id = temppath[i];//循环条件为i>=0
		value += w[id];//value增加id的点权
	}
```

**注意：顶点下标的范围要根据题目来确定**



### 4.1给出N个城市个M条道路求出距离最短的路径

```c++
#include<iostream>
#include<string>
#include<vector>
#include<Windows.h>
#include <conio.h>
#include<ctime>
#include<stack>
#include<cctype>
#include<cmath>
#include<stdio.h>
#include <windows.h>
#include<algorithm>
#include<cstring>
using namespace std;
int m_direction;
string str;



//使用狄杰特斯拉算法记录所有最短路径
const int maxn = 1000,inf=100000000;
int M, N,st,sd;//st起点 sd终点 N个城市编号为0~N-1 M条道路为无向边 给出M条道路的距离属性以及花费属性
int mincost=inf;
int d[maxn];
bool inq[maxn] = { false };
vector<int> pre[maxn];//存放结点的前驱结点
int optvalue;//opt为第二标尺最优值 
vector<int>path, temppath;//一条最优的路径和临时的路径

int distancee[maxn][maxn],dis;
int costt[maxn][maxn];//花费
void dijksra(int s)//s为起点
{
	int u;

	fill(d, d + maxn, inf);
	d[s] = 0;//到自身的距离为0

	for (int i = 0; i <N; i++)
	{
		u = -1;
		int min = inf;//注意min初始化的位置是在循环里面 每次
		for (int j = 0; j <N; j++)
		{
			if (inq[j] == false && d[j] < min)
			{
				u = j;
				min = d[j];//先找到最小的d[u] u是下标
			}
		}

		if (u == -1) return;//说明剩下的点和起始点不连通
		inq[u] = true;//标记为已经访问过
		for (int v = 0; v < N; v++)
		{
			if (inq[v] == false && distancee[u][v] != inf) {
				if (d[u] + distancee[u][v] < d[v])
				{
					d[v] = d[u] + distancee[u][v];
					pre[v].clear();
					pre[v].push_back(u);
				}
				else if (d[u] + distancee[u][v] == d[v])
					pre[v].push_back(u);
			}
		}

	}
}
void dfs(int v)//v为当前访问的结点
{
	if (v == st)//如果到了叶子结点，即起始点
	{
		int temcost = 0;//当前的花费之和
	
		temppath.push_back(v);//将路径起始点加入到临时路径的最后面
		for (int i = temppath.size() - 1; i > 0; i--)
		{
			int id = temppath[i], nextid = temppath[i - 1];//当前结点为id,下一个结点为nextid
			//循环条件为i>0
			temcost += costt[id][nextid];
			//value增加从id到nextid的边权 这里G表示一个点到另一个点之间边权大小
		}
		
		if (temcost < mincost)
		{
			mincost=temcost;//更新第二标尺的值和最优路径
			path = temppath;
		}
		temppath.pop_back();//删除刚刚加入的结点
		return;
	}
	//if v != st
	temppath.push_back(v);//当前访问的结点加入到临时路径temppath的后面
	for (int i = 0; i < pre[v].size(); i++)
	{
		int nex = pre[v][i];//对v的前驱结点进行递归访问 前驱结点pre[v][i]
	
		dfs(nex);
	}
	temppath.pop_back();//遍历完所有的前驱结点 删除刚刚加入的结点
	return;
}
//4 5 0 3
//0 1 1 20
//1 3 2 30
//0 3 4 10
//0 2 2 20
//2 3 1 20
int main()
{
	int huafei;
	int i, j;
	cin >> N >> M >> st >> sd;
	fill(distancee[0], distancee[0] + maxn * maxn, inf);
	fill(costt[0], costt[0] + maxn * maxn, inf);
	for (int z = 0;z < M; z++)
	{
		cin >> i >> j;
		cin >> distancee[i][j];
		distancee[j][i]= distancee[i][j];
		cin >> costt[i][j];
		costt[j][i] = costt[i][j];
	}

	dijksra(st);
	dfs(sd);
	for (int i = path.size()-1; i >= 0; i--)
		cout << path[i] << " ";
	cout << endl;
	cout << d[sd] << " " <<mincost;//输出最短距离和最小花费
}



```



## 4 判断无向图的所有顶点是否连通

```c++
//n 图的顶点数目 m图中边的数目 n为0时输入结束 有m行数据每行两个值x,y 表示顶点x y相连 顶点编号从1开始计算
//如果所有顶点连通输出YES
#include <iostream>
#define maxnn 1005
int A[maxnn][maxnn];//存放邻接矩阵
int visited[maxnn];//顶点访问标记
int n, m;
int countt;
int root[maxnn];
using namespace std;


void DFS(int i)
{
    int w;
    visited[i] = 1;
    countt++;//累计本次遍历访问的顶点个数
    for (w = 1; w <=n; w++)//查找顶点i的所有邻接点
    {
        if (A[i][w] != 0 && visited[w] == 0)
            DFS(w);//找顶点i未访问过的邻接点W
    }
}
int connect()
{
    memset(visited, 0, sizeof(visited));
    countt = 0;
    DFS(1);
    if (countt == n)
        return 1;
    else
        return 0;
}
int main()
{
    while (1)
    {
        memset(A, 0, sizeof(A));
        int i, j, k, a, b;
        cin >> n;
        if (n == 0) break;
    
        cin >> m;
      for(k = 1;k <= m;k++)
        {
            cin >> a >> b;
         /*   i = findd(a);
            j = findd(b);*/
           /* if (i != j)*/
                A[a][b] = A[b][a] = 1;
        }
        if (connect()) cout << "YES\n";
        else
            cout << "NO\n";
   }
}

```



# 动态规划

## 1.1不下降的最长子序列(可以不连续)

```c++
//在一个序列中，找到一个最长的子序列（可以不连续）使这个序列是不下降的

int a[maxn],dp[maxn];//dp[i]存放以a[i]结尾的lis长度 
 


 
 
 
int main()
{	
int n;
cin >> n;
for(int i = 1; i <= n; i++)
{
	cin >> a[i];
}

int ans = -1;
for(int i = 1; i <= n; i++)
{
dp[i] = 1;//边界初始条件 先假设每个元素自成一个序列

for(int j = 1; j < i; j++)
{	
if(dp[i] <dp[j] + 1 && (a[i] >= a[j]))
     dp[i] = dp[j] + 1;//符合条件则更新长度 
 } 
 
ans = max(ans, dp[i]);//最大值 
}
cout << ans;


}
```

## 1.2最长公共子序列LCS

状态转移方程：

dp[i]\[j] = dp[i-1]\[j-1] + 1;//a[i] = b[j]

dp[i]\[j] = max{dp[i-1]\[j],dp[i]\[j-1]},a[i] != b[j]；a的第i号和b的第j号之前的LCS无法延长，因此dp[i]\[j]会继承dp[i-1]\[j] dp[i]\[j-1]中的较大者，边界:dp[i]\[0] = dp[0]\[j] = 0(0<=i<=n,0<=j<=n)

代码:

```c++
#include <iostream>
#include<algorithm>
#include<stdio.h>
#include<cstring>
#include <cstdio>
using namespace std;
const int n = 100;
char aa[n] = { '0' }, bb[n] = { '0' };
int dp[n][n];

int main()
{
	gets_s(aa+1 ,n);
	gets_s(bb+1 ,n);//下标从1开始

int lena = strlen(aa+1) ;
int lenb = strlen(bb +1);//由于下标从1开始，长度也从+1开始 注意长度是strlen(aa+1)而不是strlen(a)+1
for (int i = 0; i <= lena;i++)
{
	dp[0][i] = 0;
}
for (int j = 0; j <= lenb; j++)
	dp[j][0] = 0;
cout << lena << " " << lenb;
cout << endl;
for (int i = 1; i <= lena; i++)
{
	for (int j = 1; j <= lenb; j++)
		if (aa[i] == bb[j])
		{
			dp[i][j] = dp[i - 1][j - 1] + 1;
		}
		else
			dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]) ;
}
cout << dp[lena ][lenb ];
}


下面的代码自己写的输出公共的子序列部分：
    int k=0;
for (int i = 1; i <= lena; i++)
{
	for (int j = 1; j <= lenb; j++)
		if (aa[i] == bb[j])
		{
			k = i;
			break;
		}
	if (k != 0)
		break;
}
	for (int i = k + 1; i < k + 1 + dp[lena][lenb]; i++)
		cout << aa[i];
```

## 1.3最长回文子串

dp[i]\[j]表示s[i]到s[j]表示的子串是否是回文子串，是则为1 否则为0，如果s[i] = s[j]则只要s[i+1] s[j-1]也是回文子串，则i到j是回文子串，若s[i+1] s[j-1]不是回文子串则i到j不是回文子串。若s[i]!=s[j] 则一定不是回文子串。

边界表示的是长度为1和2的子串，且每次转移时都对子串的 长度减一，考虑按子串长度和子串初始位置进行枚举，第一遍将长度为3的子串的dp值全部求出，第二部利用第一遍的结果计算出长度为4的子串的dp值

枚举串的长度和左端点的位置

```c++
#include <iostream>
#include<cstring>

using namespace std;
const int maxn = 1000;
int dp[maxn][maxn];
char a[maxn], b[maxn];
int main()
{
	cin>>a;

	int len = strlen(a);//注意是用strlen不是sizeof
	cout << len<<endl;
	int ans = 1;//当前最长回文子串长度
	memset(dp, 0, sizeof(dp));//dp数组初始化为0
	for (int i = 0; i < len; i++)

	{
		dp[i][i] = 1;//串长度为1时		
		if (i < len - 1)
		{
			if (a[i] == a[i + 1])
			{
				dp[i][i + 1] = 1;
				ans = 2;//初始化时注意当前最长回文子串的长度
			}
		}
	}
	for (int L = 3; L <= len; L++)
	{
		for (int i = 0; i + L - 1 < len; i++)//枚举串的起始位置（串的最小长度为3
		{
			int j = i + L - 1;//子串的右端点
			if (dp[i + 1][j - 1] == 1 && a[i] == a[j])//如果两个端点的字符相同且a[i+1]到a[j-1]部分为回文子串时
			{
				ans=L;
				dp[i][j] = 1;//更新最长回文子串长度和dp
			}
		}
	}
	cout << ans;
}

```

## 1.4 DAG最长路径

```c++
int n;
const int inf = 1000000;
int G[maxn][maxn];
int DP(int i)
{
	
	if (dp[i]> 0) return dp[i];
	for (int j = 0; j < n; j++)//遍历i的所有边
	{
		if (G[i][j] != inf)//如果两点之间是连通的
			dp[i] = max(dp[i], DP(j) + G[i][j]);//这里用到了递归
	}
return dp[i];//返回计算完毕的dp[i]
		
}
```

```c++
int choice[maxn];//存放i的下一个结点
int DP(int i)
{
	if (dp[i] > 0) return dp[i];
	for (int j = 0; j < n; j++)
	{
		if (G[i][j] != inf)
		{
			int temp = DP(j) + G[i][j];//单独计算，防止if中调用dp函数两次
			if (temp > dp[i])
			{
				dp[i] = temp;
				choice[i] = j;//如果通过j可以获得更长的路径，则i号顶点的的后继顶点是j 同时更新dp[i]
			}
		}
	}
    return dp[i];//返回计算完毕的dp[i]
}
```



## 2.1字符串的hash进阶 最长公共子串长度（未明白

对两个子串都求出hash值，同时记录对应的长度，然后找出子串对应的hash值中相等的那些，便可以找到最大长度，时间复杂度为o(n平方+m平方)

```c++
using namespace std;
typedef long long LL;
const LL mod = 100000007;//计算哈希值的模数
const LL p = 10000019;//计算哈希值的进制数
const LL maxn = 1000;//字符串最长长度
//算法笔记第451页


LL powp[maxn], h1[maxn] = { 0 }, h2[maxn] = { 0 };//h1 h2分别存放两个字符串的hash值

vector<pair<int, int>>pr1, pr2;//pr1存放str1所有<子串hash值，子串长度>
void init(int len)
{
	powp[0] = 1;
	for (int i = 1; i < len; i++)
	{
		powp[i] = (powp[i - 1] * p) % mod;
	}
}
void cal(LL H[], string& str) {
	H[0] = str[0];//单独处理
	for (int i = 1; i < str.length(); i++)
		H[i] = (H[i - 1] * p + str[i]) % mod;

}
```



## 2.2连续输入字符串 统计一共有多少个不同的字符串 用字符串hash

```c++
typedef long long LL;
const LL mod = 100000007;//计算哈希值的模数
const LL p = 10000019;//计算哈希值的进制数
const LL maxn = 1000;//字符串最长长度
//算法笔记第451页


vector<long long>ans;
long long hashlength(string str)
{
	long long h = 0;//避免溢出用long long
	for (int i = 1; i < str.length(); i++)
		h = (h * p + str[i] - 'a') % mod;
	return h;//字符串hash
}
int main()
{
	string str;
	while (getline(cin, str) && str != "#")//用#结束输入

	{
		long long id = hashlength(str);//字符串转化为整数
		ans.push_back(id);
	}
	int count = 0;
	sort(ans.begin(), ans.end());
	for (int i = 0; i < ans.size(); i++)
		if (i==0||ans[i] != ans[i - 1])
			count++;//统计不同的字符串个数
	cout << count;
}

```





