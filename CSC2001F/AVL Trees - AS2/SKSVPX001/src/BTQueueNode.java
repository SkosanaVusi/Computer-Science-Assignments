/** Hussein's Binary Tree Queue Node
* 26 March 2017
* @author Hussein Suleman
*/

public class BTQueueNode<dataType>{
	/**
    * Sets Binary Tree Node. 
    */
   BinaryTreeNode<dataType> node;

    /**
    * Sets Binary Tree Node. 
    */
   BTQueueNode<dataType> next;
    
    /**
    * Constructs Binary Tree Node.
    * @param n  Binary Tree node with specified datatype.
    * @param nxt  Binary Tree Queue node with specified datatype.  
    */
   public BTQueueNode ( BinaryTreeNode<dataType> n, BTQueueNode<dataType> nxt )
   {
      node = n;
      next = nxt;
   }
}
