/**
* Hussein's AVL Tree
* 2 April 2017
* @author Hussein Suleman
* reference: kukuruku.co/post/avl-trees/
*/

public class AVLTree<dataType extends Comparable<? super dataType>> extends BinaryTree<dataType>{
	int opCount=0;

   /**
   * Displays the height of the object node calling it.
   * @param node
   * Object node to get height from.
   * @return the node height
   */

   public int height ( BinaryTreeNode<dataType> node )
   {
      if (node != null)
         return node.height;
      return -1;
   }
   /**
   * Displays the difference between the height of the right-hand side subtree
   * and left-hand side subtree of the object node calling it.
   * @param node
   * Object node to calculate the height difference from.
   * @return height difference.
   */
   public int balanceFactor ( BinaryTreeNode<dataType> node )
   {
      return height (node.right) - height (node.left);
   }
   /**
   * Re-calculates the height of an object node by comparing 
   * heights of left-hand subtree and right-hand subtree, then
   * equating to the highest of the two after adding one to it
   * @param node
   * Object node to re-calculate height for.
   */
   public void fixHeight ( BinaryTreeNode<dataType> node )
   {
      node.height = Math.max (height (node.left), height (node.right)) + 1;
   }
   /**
   * Adjusts object node by shifting it to the right in the Binary Tree.
   * @param p
   * Object node to shift in the tree.
   * @return the shifted node
 
   */
   public BinaryTreeNode<dataType> rotateRight ( BinaryTreeNode<dataType> p )
   {
      BinaryTreeNode<dataType> q = p.left;
      p.left = q.right;
      q.right = p;
      fixHeight (p);
      fixHeight (q);
      return q;
   }
   /**
   * Adjusts object node by shifting it to the left in the Binary Tree.
   * @param q
   * Object node to shift in the tree.
   * @return the shifted node
   */
   public BinaryTreeNode<dataType> rotateLeft ( BinaryTreeNode<dataType> q )
   {
      BinaryTreeNode<dataType> p = q.right;
      q.right = p.left;
      p.left = q;
      fixHeight (q);
      fixHeight (p);
      return p;
   }
   /**
   * Utilizes the rotateRight and rotateLeft methods to shift nodes 
   * accordingly, after there is an imbalance detected by the balanceFactor 
   * method, when it returns 2. It  balances the tree according to appropriate
   * balancing propertie.
   * @param p
   * Object node to start balancing tree below from.
   * @return node which subtree was balanced
   */
   public BinaryTreeNode<dataType> balance ( BinaryTreeNode<dataType> p )
   {
      fixHeight (p);
      if (balanceFactor (p) == 2)
      {
         if (balanceFactor (p.right) < 0)
            p.right = rotateRight (p.right);
         return rotateLeft (p);
      }
 
      if (balanceFactor (p) == -2)
      {
         if (balanceFactor (p.left) > 0)
            p.left = rotateLeft (p.left);
         return rotateRight (p);
      }
      return p;
   }

   /**
    * Adds node item into the tree. 
    * @param d datatype to insert.
    */
   public void insert ( dataType d )
   {
      root = insert (d, root);
   }

   /**
   * Inserts nodes down the Binary Tree and
   * increments insertCount variable after a comparison is made.
   * @param d
   * Generic type object.
   * @param node
   * Object node to start traversing/inserting other nodes from.
   * @return node which subtree was balanced

   */
   public BinaryTreeNode<dataType> insert ( dataType d, BinaryTreeNode<dataType> node )
   {
      
      if (node == null)
         return new BinaryTreeNode<dataType> (d, null, null);
      if (d.compareTo (node.data) <= 0)
         node.left = insert (d, node.left);
      else
         node.right = insert (d, node.right);
      return balance (node);
   }

   /**
   * Deletes a node with specific properties in the Binary Tree.
   * @param d
   * The generic type object
   */
   public void delete ( dataType d )
   {
      root = delete (d, root);
   }   

   /**
   * Deletes a node with specific properties in the Binary Tree.
   * @param d
   * The generic type object
   * @param node
   * A node to delete/traverse from to delete a node with the correct properties.
   * @return node which subtree was balanced
   */
   public BinaryTreeNode<dataType> delete ( dataType d, BinaryTreeNode<dataType> node )
   {
      if (node == null) return null;
      if (d.compareTo (node.data) < 0)
         node.left = delete (d, node.left);
      else if (d.compareTo (node.data) > 0)
         node.right = delete (d, node.right);
      else
      {
         BinaryTreeNode<dataType> q = node.left;
         BinaryTreeNode<dataType> r = node.right;
         if (r == null)
            return q;
         BinaryTreeNode<dataType> min = findMin (r);
         min.right = removeMin (r);
         min.left = q;
         return balance (min);
      }
      return balance (node);
   }

   /**
   * Finds and returns an object node with most minimum properties in the Binary Tree.
   * @param node
   * An object node to start traversing from.
   * @return the node with minimum propterties.
   */

   public BinaryTreeNode<dataType> findMin ( BinaryTreeNode<dataType> node )
   {
      if (node.left != null)
         return findMin (node.left);
      else
         return node;
   }
   /**
   * Removes the object node with the most minimum properties in the Binary Tree.
   * @param node
   * An object node to start traversing from towards that object node with minimum properties.
   * @return node which subtree was balanced.
   */
   public BinaryTreeNode<dataType> removeMin ( BinaryTreeNode<dataType> node )
   {
      if (node.left == null)
         return node.right;
      node.left = removeMin (node.left);
      return balance (node);
   }
   /**
   * Finds an object node with specific properties
   * @param d
   * A generic type object, containing certain properties.
   * @return node with the same properties
   */

   public BinaryTreeNode<dataType> find ( dataType d )
   {
      if (root == null)
         return null;
      else
         return find (d, root);
   }
   /**
   * Finds an object node with specific properties
   * @param d
   * A generic type object, containing 
   * @param node
   * node to start transversing from searchning.
   * @return the node with those properties.
   */

   public BinaryTreeNode<dataType> find ( dataType d, BinaryTreeNode<dataType> node )
   {
   	opCount=opCount+2;
      if (d.compareTo (node.data) == 0){
         opCount=opCount-1;
           return node;
        }
      else if (d.compareTo (node.data) < 0)
         return (node.left == null) ? null : find (d, node.left);
      else
         return (node.right == null) ? null : find (d, node.right);
   }
   /**
    * Reorders the tree. 
    */
   public void treeOrder ()
   {
      treeOrder (root, 0);
   }
   /**
    * Reorders all the items in the tree at a given level.
    * @param level  The level at which it must be ordered. 
    * @param node  BinaryTreeNode with specified datatype.
    */
   public void treeOrder ( BinaryTreeNode<dataType> node, int level )
   {
      if (node != null)
      {
         for ( int i=0; i<level; i++ )
            System.out.print (" ");
         System.out.println (node.data);
         treeOrder (node.left, level+1);
         treeOrder (node.right, level+1);
      }
   }
}

