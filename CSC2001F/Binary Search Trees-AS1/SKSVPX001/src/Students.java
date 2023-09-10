/** ... */
public class Students implements Comparable<Students>
{
    String studentFullName;
    String studentNumber;
    
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

    public Students(String studNum){
        this.studentNumber = studNum;
    }

    /**
    * Displays Student number and full name
    * @return 
    * Student number and student full name.
    */

    public String toString(){
        return getStudentNumber()+" "+getStudentFullName();
    }

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
    * Compares student objects identity.
    * @param otherObj
    * Student type object
    * @return
    * A positive int value if object making the call is more than the argument.
    * A negative int value if object making the call is less than the argument.
    * Zero if the object making the call is completely the same as the argument. 
    */
    
    public int compareTo(Students otherObj){
        return String.valueOf(studentNumber).compareTo(String.valueOf(otherObj.getStudentNumber()));
    }   
}



