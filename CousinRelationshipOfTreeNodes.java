/* 判断2个TreeNode之间是否为Cousin关系，返回boolean值。是Cousin关系的充要条件有两个：
1. 从整个树的root开始算，它们处于同一个level
2. 它们的parent不是同一个node

这一题是给root的。另外会给要找的2个nodes。假设：
1. The tree is not null.
2. The 2 nodes do exist in the binary tree whose root is the given root。
3. 这两个nodes不是同一个node。  */


// 方法1：Laioffer很巧妙的方法！！！ 较难想到

public class Solution {

    boolean isCousinOrNot = false;

    public boolean isCousin(TreeNode root, TreeNode one, TreeNode two) {
	getLevelWithSpecialTreatment(root, one, two, 1);
	return isCousinOrNot;
    }
    
    private int getLevelWithSpecialTreatment(TreeNode root, TreeNode one, TreeNode two, int curLevel) {
    	if (root == null) {
	    return -1;
	}
	
	if (root == a || root == b) {
	    return curLevel;
	}
	    
	int resultL = getLevelWithSpecialTreatment(root.left, one, two, curLevel + 1);
	int resultR = getLevelWithSpecialTreatment(root.right, one, two, curLevel + 1);
	
	if (resultL == resultR && 
	    resultL - curLevel >= 2) { // 关键 1 ！！！ 这一句可以判断出，找到Node one和two的level，是不是当前node的孙子或更下层，
		                       // 如果是，则意味着，one和two不可能共爹 ！！！
	    isCousinOrNot = true;
	}
	
	// 关键 2 ！！！
	// 如果 L 和 R 中的任何一个是-1，即意味着找不到，则返回另一个；
	// 如果 L 和 R 都不是-1，但是一大一小，即意味着one和two都找到了，但在不同层，这样的话，其实返回谁都无所谓了，
	// 因为这样的话，到了最后汇总答案的时候，L和R也不可能一致，那时的L和R二者必有一个是-1
	return resultL > resultR ? resultL : resultR;
    }

}


// 方法2：我用了一个 custom class 来wrap每一个TreeNode的两种信息：parent和level
// Time: O(n), n is the number of nodes in the tree. Since we have to walk through each node in the tree.
// Space: O(height of tree), since the level of call stacks would be at most the height of the tree, 
// and each call stack uses O(1) extra space.

class ResultType {
	TreeNode parent;
	int level;
	
	public ResultType(TreeNode p, int l) {
		parent = p;
		level = l;
	}
}
 
public class Solution {
 
	public boolean isCousin(TreeNode root, TreeNode one, TreeNode two) {
		TreeNode parent = new TreeNode(Integer.MIN_VALUE);
		parent.right = root;
 
		ResultType rt1 = findNode(root, parent, 1, one);
		ResultType rt2 = findNode(root, parent, 1, two);
	 
		if (rt1.level == rt2.level && rt1.parent != rt2.parent) {
			return true;
		} else {
			return false;
		}
	}
 
	private ResultType findNode(TreeNode root, TreeNode parent, int curLevel, TreeNode target) {	
		
		if (root == null) { // 这意味着在当前这条path上没有找到target node ！！
			return new ResultType(null, Integer.MAX_VALUE);
		}
		 
		if (root == target) {
			return new ResultType(parent, curLevel);
		}	
		 
		ResultType resultL = findNode(root.left, root, curLevel + 1, target);
		ResultType resultR = findNode(root.right, root, curLevel + 1, target);	
		 
		if (resultL.parent == null && resultR.parent == null) {
			return resultL; // = new ResultType(null, Integer.MAX_VALUE);
		} else if (resultL.parent == null) {
			return resultR;
		} else {
			return resultL;
		}
	}
}
