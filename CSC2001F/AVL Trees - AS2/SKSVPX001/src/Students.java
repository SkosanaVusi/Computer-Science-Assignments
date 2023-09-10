/**
* Students data type
* @author Vusi Skosana
*/
public class Students implements Comparable<Students>{
    String studentFullName;
    String studentNumber;
    int insertcount=0;
    
    /**
    * Two parameter Constructor.
    * @param studN
    * student number.
    * @param studFN
    * student full name.
    */
    public Students(String studN, String studFN){
        this.studentFullName = studFN;
        this.studentNumber = studN;
    }

    /**
    * Single parameter Constructor
    * @param studNum
    * student number.
    */
    public Students(String studNum){this.studentNumber = studNum;}

    /**
    * Displays Student full name
    * @return
    * Student full name.
    */
    public String getStudentFullName(){return this.studentFullName;}
    
    /**
    * Displays student number
    * @return
    * Student number.
    */
    public String getStudentNumber(){return this.studentNumber;}

    /**
    * Displays Student number,full name and insert count number down the Binary Search Tree.
    * @return full student name and insert count value.
    */
    public String tostringwithcount(){return getStudentFullName() + " | operation insert count "+ this.insertcount;}

    /**
    * Displays Student number and full name
    */
    public String toString(){return getStudentNumber()+" "+getStudentFullName();}

    /**
    * Compares student objects identity.
    * @param otherObj
    * Student type object
    * @return
    * A positive int value if object making the call is more than the argument.
    * A negative int value if object making the call is less than the argument.
    * Zero if the object making the call is completely the same as the argument. 
    */
    public int compareTo(Students otherObj){
        insertcount++;
        return String.valueOf(studentNumber).compareTo(String.valueOf(otherObj.getStudentNumber()));
    }   
}



