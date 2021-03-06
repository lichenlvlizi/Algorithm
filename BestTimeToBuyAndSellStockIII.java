/* Say you have an array for which the ith element is the price of a given stock on day i.
Design an algorithm to find the maximum profit. You may complete at most two transactions.
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

Example
Given an example [4,4,6,1,1,4,2,5], return (4 - 1) + (5 - 2) = 6. */

// 不容易想到！用DP做！从左到右一个DP，从右到左一个DP！
// Ref: http://www.jiuzhang.com/solutions/best-time-to-buy-and-sell-stock-iii/
class Solution {

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int[] left = new int[prices.length];
        int[] right = new int[prices.length];

        // DP from left to right;
        // 在 i 以及 i的左边，进行一次交易，所能得到的最大利润
        left[0] = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            min = Math.min(prices[i], min);
            left[i] = Math.max(left[i - 1], prices[i] - min);
        }

        // DP from right to left;
        // 在 i 以及 i的右边，进行一次交易，所能得到的最大利润
        right[prices.length - 1] = 0;
        int max = prices[prices.length - 1];
        for (int i = prices.length - 2; i >= 0; i--) {
            max = Math.max(prices[i], max);
            right[i] = Math.max(right[i + 1], max - prices[i]);
        }

        int profit = 0;
        for (int i = 0; i < prices.length; i++){
            profit = Math.max(left[i] + right[i], profit);  
        }

        return profit;
    }

}
