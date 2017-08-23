/* 这一题先看看 扔两个蛋 那一题。

一共3个蛋，考察n层楼梯，问最坏的情况下，需要最少扔多少次，当然3个蛋都要被最优化地利用 */

// 比较详细的讲述整个 飞蛋问题：
// Ref: http://datagenetics.com/blog/july22012/index.html

// 注意：如果一个蛋在某次摔时没碎，它是可以被重复利用的
// 一个蛋摔一次，不管碎不碎，都算是一次drop
// 不管一开始有几个蛋，当只剩最后一个蛋时，只能老老实实在当前的目标区间内从低到高一层一层试了

/* 如果要考虑3个蛋扔5次最多能check多少层楼，我们的思路如下图，从这张图的最下面开始，从下往上看：

--------------------------------------------------
|        第五步：如果第1个蛋在24楼没碎              |
|        我们在第25层楼第5次扔第1个蛋               |                       扔1次
--------------------------------------------------
      5次仍蛋都用完了。后2个蛋不能再扔了
最后这一次在第25层扔的第1个蛋，能说明25层碎还是不碎（前面的过程说明了24不碎）
 所以第1个蛋扔了5次，后2个蛋不能再扔了，一共还是扔5次
 
--------------------------------------------------
|        第四步：如果第1个蛋在22楼没碎              |
|        我们在第24层楼第4次扔第1个蛋               |                       扔1次
--------------------------------------------------
    如果第1个蛋在24楼碎了，24层和22层之间间隔了1层楼，
           后2个蛋可以通过扔1次来检验1层楼                               最多扔1次
 所以第1个蛋扔了4次，后2个蛋最多扔1次，一共还是最多扔5次
 
--------------------------------------------------
|        第三步：如果第1个蛋在18楼没碎              |
|        我们在第22层楼第3次扔第1个蛋               |                       扔1次
--------------------------------------------------
    如果第1个蛋在22楼碎了，22层和18层之间间隔了3层楼，
           后2个蛋可以通过扔2次来检验3层楼                               最多扔2次
 所以第1个蛋扔了3次，后2个蛋最多扔2次，一共还是最多扔5次

--------------------------------------------------
|        第二步：如果第1个蛋在11楼没碎              |
|        我们在第18层楼第2次扔第1个蛋               |                       扔1次
--------------------------------------------------
    如果第1个蛋在18楼碎了，18层和11层之间间隔了6层楼，
           后2个蛋可以通过扔3次来检验6层楼                               最多扔3次
 所以第1个蛋扔了2次，后2个蛋最多扔3次，一共还是最多扔5次

--------------------------------------------------      
|            第一步：第1个蛋扔到11楼               |                        扔1次
--------------------------------------------------
        如果第1个蛋在11楼碎了，下面还有10层楼，
           后2个蛋可以通过扔4次来检验10层楼                              最多扔4次
 所以第1个蛋扔了1次，后2个蛋最多扔4次，一共最多扔5次

上面的数加起来是： (1+10) + (1+6) + (1+3) + (1+1) + (1+0) = 25层楼 
所以，上面的等号左边，从右往左看，可以得到：用3个蛋检测楼层的话：在最差的情况下需要最少这么多次：
1层楼，扔1次
2-3层楼，扔2次
4-7层楼，扔3次
8-14层楼，扔4次
15-25层楼，扔5次
......                                                                     */


// 下面的代码虽然看起来不短，但其实思路很简明直接，就是上面的图示的简单实现
public class Solution {

    // n 是楼层数
    public static int droppEggs_3Eggs(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        
        // 先预备好只有2个蛋的时候，检测n层楼需要多少次drop。这些数据是计算3个蛋的情况的基础
        // 这个list将会是：0, 1, 3, 6, 10, 15, 21... 即能处理多少层楼房
        // 对应的index是： 0, 1, 2, 3, 4,  5,  6 ... 即多少个drops（两个蛋）
        List<Integer> dropsForNFloors_2Eggs = new ArrayList<Integer>();
        dropsForNFloors_2Eggs.add(0); // 0 drops for floor 0
          
        int floors = 1; // 2个蛋扔1次能确定1层楼
        int drops = 1; // 2个蛋从扔1次开始
        // 当list里某一项的数字>=n的时候，我们可以确定，2个egg都已经达到了n层楼，
        // 那么以此为基础，一定足以发现3个egg要多少个drop才能达到n层楼了
        while (floors < n) { 
            dropsForNFloors_2Eggs.add(floors);
            drops ++;
            floors += drops;
        }
        
        for (int num : dropsForNFloors_2Eggs) {
        	System.out.println(num);
        }
        System.out.println();
        
        floors = 1; // 3个蛋扔1次能确定1层楼
        
        // 从2个蛋扔1次开始，即从3个蛋扔2次开始 ！！！
        for (int dropsOf2Eggs = 1; dropsOf2Eggs <= dropsForNFloors_2Eggs.size() - 1; dropsOf2Eggs++) {
            // 3个蛋能cover几层楼？是在2个蛋的基础上+1
            floors += dropsForNFloors_2Eggs.get(dropsOf2Eggs) + 1;
            
            if (floors >= n) {
                return dropsOf2Eggs + 1;
            }
        }
        
        // actually we will never reach here
        return -1;
    }
}
