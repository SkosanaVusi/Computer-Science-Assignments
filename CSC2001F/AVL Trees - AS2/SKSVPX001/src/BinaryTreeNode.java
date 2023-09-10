/**
* Hussein's Binary Tree Node
* 26 March 2017
* @author Hussein Suleman
*/

public class BinaryTreeNode<dataType>
{
   dataType data;
   BinaryTreeNode<dataType> left;
   BinaryTreeNode<dataType> right;
   int height;

   /**
   * Constructor
   * @param d
   * A generic type object
   * @param l
   * Object of this class
   * @param r
   * object of this class
   */
   
   public BinaryTreeNode ( dataType d, BinaryTreeNode<dataType> l, BinaryTreeNode<dataType> r )
   {
      data = d;
      left = l;
      right = r;
      height = 0;
   }
   /**
   * Returns BinaryTreeNode<dataType> type memory address
   * stored in the right attribute.
   */
   BinaryTreeNode<dataType> getLeft () { return left; }
   /**
   * Returns BinaryTreeNode<dataType> type memory address
   * stored in the left attribute.
   */
   BinaryTreeNode<dataType> getRight () { return right; }
}
