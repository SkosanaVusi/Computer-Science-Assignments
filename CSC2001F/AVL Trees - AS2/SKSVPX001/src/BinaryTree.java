/**
* Hussein's Binary Tree
* 26 March 2017
* @author Hussein Suleman
*/

public class BinaryTree<dataType>
{
   BinaryTreeNode<dataType> root;
   /**
   * Constructor
   */
   public BinaryTree ()
   {
      root = null;
   }
   /**
   * Displays height of the BinaryTree
   * @return height of the tree
   */
   public int getHeight ()
   {
      return getHeight (root);
   } 
   /**
   * Displays height of the BinaryTree
   * @param node
   * A node to start counting from.
   * @return
   * The height of the Binary Tree
   *
   */  
   public int getHeight ( BinaryTreeNode<dataType> node )
   {
      if (node == null)
         return -1;
      else
         return 1 + Math.max (getHeight (node.getLeft ()), getHeight (node.getRight ()));
   }
   /**
   * Displays size of the tree
   * @return the number of nodes in the tree.
   */

   public int getSize ()
   {
      return getSize (root);
   }  
   /**
   * Prints the number of nodes in the tree
   * @param node
   * A node to start counting from.
   * @return
   * The number of nodes in the tree.
   */
   
   public int getSize ( BinaryTreeNode<dataType> node )
   {
      if (node == null)
         return 0;
      else
         return 1 + getSize (node.getLeft ()) + getSize (node.getRight ());
   }
   /**
   * Prints attribute generic data of the node
   * @param node
   * A node to display the attribute data of.
   */
   
   public void visit ( BinaryTreeNode<dataType> node )
   {
      System.out.println (node.data);
   }

   /**
   * Displays data within all tree nodes in Binary Tree
   * starting with the parent of the children nodes.
   */
   public void preOrder ()
   {
      preOrder (root);
   }

   /**
   *
   * Prints all the nodes in the Binary Tree
   * starting with the parent of the children nodes.
   * @param node
   * A node to start trasnversing from to down the tree.
   */
   public void preOrder ( BinaryTreeNode<dataType> node )
   {
      if (node != null)
      {
         visit (node);
         preOrder (node.getLeft ());
         preOrder (node.getRight ());
      }   
   }

   /**
   * 
   * Prints all the nodes in the Binary Tree
   * ending with the parents of the children nodes.
   */
   public void postOrder ()
   {
      postOrder (root);
   }

   /**
   * 
   * Prints all the nodes in the Binary Tree
   * ending with the parents of the children nodes.
   * @param node
   * A node to start trasnversing from to down the tree.
   */
   public void postOrder ( BinaryTreeNode<dataType> node )
   {
      if (node != null)
      {
         postOrder (node.getLeft ());
         postOrder (node.getRight ());
         visit (node);
      }   
   }

   /**
   * Prints all the nodes in the Binary Tree, with the parent node in between the children nodes
   */
   public void inOrder ()
   {
      inOrder (root);
   }

   /**
   * Prints all the nodes in the Binary Tree, with the parent node in between the children nodes
   * @param node
   * A node to start trasnversing from to down the tree.
   */
   public void inOrder ( BinaryTreeNode<dataType> node )
   {
      if (node != null)
      {
         inOrder (node.getLeft ());
         visit (node);
         inOrder (node.getRight ());
      }   
   }

   /**
   * Prints all the nodes in the Binary Tree staring from the parent node to the children nodes, from the left to right child node.
   */
   public void levelOrder ()
   {
      if (root == null)
         return;
      BTQueue<dataType> q = new BTQueue<dataType> ();
      q.enQueue (root);
      BinaryTreeNode<dataType> node;
      while ((node = q.getNext ()) != null)
      {
         visit (node);
         if (node.getLeft () != null)
            q.enQueue (node.getLeft ());
         if (node.getRight () != null)
            q.enQueue (node.getRight ());
      }
   }
}
