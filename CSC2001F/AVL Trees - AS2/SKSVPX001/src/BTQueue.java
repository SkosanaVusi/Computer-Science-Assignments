/**
* Hussein's Binary Tree
* 26 March 2017
* @author Hussein Suleman
*/
public class BTQueue<dataType>{     
	/**
    * Binary Tree Queue head node. 
    */
   BTQueueNode<dataType> head;
    
    /**
    * Binary Tree Queue tail node. 
    */
   BTQueueNode<dataType> tail;
   
    /**
    * Sets the head and tail to null 
    */ 
   public BTQueue ()
   {
      head = null;
      tail = null;
   }
    
    /** 
    * Gets the next head node in Binary Tree.
    * @return the next head node.
    */
   public BinaryTreeNode<dataType> getNext ()
   {
      if (head == null)
         return null;
      BTQueueNode<dataType> qnode = head;
      head = head.next;
      if ( head == null )
         tail = null;
      return qnode.node;
   }

    /** 
    * Sets the next tail node in Binary Tree.
    * @param node Binary Tree node with specified datatype.
    */
   public void enQueue ( BinaryTreeNode<dataType> node )
   {
      if (tail == null)
      {   
         tail = new BTQueueNode<dataType> (node, null);
         head = tail;
      }   
      else
      {
         tail.next = new BTQueueNode<dataType> (node, null);
         tail = tail.next;
      }   
   }   
}
