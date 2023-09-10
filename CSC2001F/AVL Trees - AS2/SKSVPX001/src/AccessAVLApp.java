
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NullPointerException;

/**
* AccessAVLApp program
* Check if a student is on a pre-approved
* list for access to campus during the lockdown
* @author Vusi Skosana
*/

public class AccessAVLApp {

  static AVLTree<Students> avl = new AVLTree<Students>();

    /**
    * Calls readFile method.
    * Displays the operation find and insert count integer value.
    * @param args 
    * Reads the given argument and pass it into
    * printStudent method, if empty calls pirintAllStudents 
    */
    public static void main(String[] args) {
      readFile();

      try {
        if (args.length == 1);
          printStudent(args[0]);
          System.out.printf(" | operation find count = " + avl.opCount + "%n");
   
      } catch (ArrayIndexOutOfBoundsException e){
          if (args.length == 0)
            printAllStudent();
      }
    }  

    /**
    * Displays student number and full names of all the students in the Binary Tree data structure in levelOrder form.
    */
    public static void printAllStudent(){
      avl.levelOrder();
    }

    /**
    * Displays a student full name.
    * @param studentID
    * student number to be searched in the Binary Tree data structure
    */
    public static void printStudent(String studentID){
      try{
        BinaryTreeNode<Students> random = avl.find(new Students(studentID));
          System.out.print(random.data.tostringwithcount());
        }catch(NullPointerException e){
          System.out.println("Access denied!");
        }     
    }

  /**
  * Read contents of a file and process entries in a file into a Binary Tree data structure.
  */
   public static void readFile(){
      int a = 0;
      String fullDetails;  
      try {
          File myObj = new File("../data/oklist.txt");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            fullDetails = myReader.nextLine();
            avl.insert(new Students(fullDetails.substring(0, 9), fullDetails.substring(10)));
            a++;
          }

          myReader.close();
       } catch (FileNotFoundException e) { 
          System.out.println("File was not found");
          e.printStackTrace();
        }

  }





}



