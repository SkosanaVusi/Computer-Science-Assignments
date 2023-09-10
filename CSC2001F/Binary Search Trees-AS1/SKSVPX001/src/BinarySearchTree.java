// Hussein's Binary Search Tree
// 27 March 2017
// Hussein Suleman
/** ... */
public class BinarySearchTree<dataType extends Comparable<? super dataType>> extends BinaryTree<dataType>{
   static int opCount=0;
   static int insertCounter=0;
   /**
   *Increments the opCount attribute
   * @return boolean.
   */
   public static boolean upCouter(){
      opCount++;
      return true;
   }

   
   public void insert ( dataType d )
   {
      if (root == null)
         root = new BinaryTreeNode<dataType> (d, null, null);
      else
         insert (d, root);
   }
   /**
   * Inserts nodes down the Binary Tree
   * @param d
   * Generic type object
   * @param node
   * A node to start transversing from.
   */
   public void insert ( dataType d, BinaryTreeNode<dataType> node )
   {  
      insertCounter++;
      if (d.compareTo (node.data) <= 0)
      {
         if (node.left == null)
            node.left = new BinaryTreeNode<dataType> (d, null, null);
         else
            insert (d, node.left);
      }
      else
      {
         if (node.right == null)
            node.right = new BinaryTreeNode<dataType> (d, null, null);
         else
            insert (d, node.right);
      }
   }
   

   public BinaryTreeNode<dataType> find ( dataType d )
   {
      if (root == null)
         return null;
      else
         return find (d, root);
   }
   /**
   * Finds a specific node that has a certain attribute of the parameter in the Binary Tree.
   * @param d
   * Generic type object
   * @param node
   * 
   */
   public BinaryTreeNode<dataType> find ( dataType d, BinaryTreeNode<dataType> node )
   {
      opCount++;
      if (d.compareTo (node.data) == 0) 
         return node;
      else if (upCouter() & d.compareTo (node.data) < 0)
         return (node.left == null) ? null : find (d, node.left);
      else
         return (node.right == null) ? null : find (d, node.right);
   }
   
   public void delete ( dataType d )
   {
      root = delete (d, root);
   }
   /**
   * Deletes a node in the Binary Tree.
   * @param d
   * Generic type object
   * @param node
   * A node to start transversing from.
   *
   */   
   public BinaryTreeNode<dataType> delete ( dataType d, BinaryTreeNode<dataType> node )
   {
      if (node == null) return null;
      if (d.compareTo (node.data) < 0)
         node.left = delete (d, node.left);
      else if (d.compareTo (node.data) > 0)
         node.right = delete (d, node.right);
      else if (node.left != null && node.right != null )
      {
         node.data = findMin (node.right).data;
         node.right = removeMin (node.right);
      }
      else
         if (node.left != null)
            node = node.left;
         else
            node = node.right;
      return node;
   }
   /**
   * Finds a node with the most minimum properties
   * @return
   * A node
   *
   */
   
   public BinaryTreeNode<dataType> findMin ( BinaryTreeNode<dataType> node )
   {
      if (node != null)
         while (node.left != null)
            node = node.left;
      return node;
   }
   /**
   * Removes a node with the most minimum properties
   * @return 
   * A node
   *
   *
   */

   public BinaryTreeNode<dataType> removeMin ( BinaryTreeNode<dataType> node )
   {
      if (node == null)
         return null;
      else if (node.left != null)
      {
         node.left = removeMin (node.left);
         return node;
      }
      else
         return node.right;
   }
   
}
