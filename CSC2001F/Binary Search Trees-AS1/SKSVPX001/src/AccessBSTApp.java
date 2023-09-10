/** ... */
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NullPointerException;

public class AccessBSTApp {

  static BinarySearchTree<Students> bst = new BinarySearchTree<Students>();

    /**
    * Calls readFile method.
    * Displays the operation count value.
    * @param args 
    * Reads the given argument and pass it into
    * printStudent method, if empty calls pirintAllStudents 
    */
    
    public static void main(String[] args) {
      readFile();

      try {
        if (args.length == 1);
          printStudent(args[0]);
   
      } catch (ArrayIndexOutOfBoundsException e){
          if (args.length == 0)
            printAllStudent();
      }
    
    System.out.printf(" | operation find count = " + bst.opCount+"%n");

    }  

    /**
    * Displays student number and full names of all the students in the Binary Tree data structure.
    */

    public static void printAllStudent(){
      bst.levelOrder();
    }
    /**
    * Displays a student full name.
    * @param studentID
    * student number to be searched in the Binary Tree data structure
    */

    public static void printStudent(String studentID){
      try{
        BinaryTreeNode<Students> random = bst.find(new Students(studentID));
          System.out.print(random.data.getStudentFullName());
        }catch(NullPointerException e){
          System.out.println("Access denied!");
        }
        
  }
  /**
  * Read contents of a file and process them into a Binary Tree data structure.
  */
    
   public static void readFile(){
      int a = 0;
      String fullDetails;  
      try {
          File myObj = new File("../data/oklist.txt");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            fullDetails = myReader.nextLine();
            bst.insert(new Students(fullDetails.substring(0, 9), fullDetails.substring(10)));
            a++;
          }

          myReader.close();
       } catch (FileNotFoundException e) { 
          System.out.println("File was not found");
          e.printStackTrace();
        }

  }





}



