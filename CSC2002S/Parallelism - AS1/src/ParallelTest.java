import java.util.concurrent.ForkJoinPool;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Apply median filtering to input file data
 * Along with input filter window
 * Save output to speciefied output file name
 */

public class ParallelTest {
    static long startTime=0;
    static int numOflines;
    static final ForkJoinPool fjPool = new ForkJoinPool();
    private static void tick() {startTime = System.currentTimeMillis();} // records current time
    private static float tock() {return (System.currentTimeMillis() - startTime) / 1000.0f;} // records time afterwards, from nano to seconds

    static float[] sum(float[] arr, int filter_size, int seq_cuttOFF) {
        return fjPool.invoke(new ParallelFilter(arr, 0, arr.length, filter_size, seq_cuttOFF));
    }
    /**
     * Filteres input data, and saves filtered data to output file name
     * @param args Takes input file name, fitlter Window and name of sample output file
     * 
     */
    public static void main(String[] args) {
        String sampleInput = args[0]; // name of input file
        int filterSize = Integer.valueOf(args[1]);
        String sampleOutput = args[2]; // name of file to output the filtered array results

        float[] file_data = null; // contains data from input file
        try{
            // read input file
            File myObj = new File(sampleInput);
            Scanner myReader = new Scanner(myObj);

            // setting length of file_data according to first line of input file
            if (myReader.hasNext()){
                numOflines = Integer.valueOf(myReader.nextLine());
                file_data = new float[numOflines];}
                
            // load input file data to file_data array
            for (int x = 0; x < file_data.length; x++) {
                file_data[x] = Float.valueOf((myReader.nextLine().split(" ")[1]).replaceAll(",", "."));
            }
            myReader.close();
            
        }catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        // run garbage collector
        // run the parralle program
        System.gc();
        tick();
        float[] filtered_array = sum(file_data, filterSize, 200);
        System.out.println(tock() + "s");

        // write data to file
        try{
            DecimalFormat df = new DecimalFormat("#.#####"); //format to 5 decimal places
            FileWriter myWriter = new FileWriter(sampleOutput);
            myWriter.write(numOflines+"\n"); // in first line, add the number of lines to follow 

            for(int x=0; x<filtered_array.length; x++){
                myWriter.write(x + " " + df.format((filtered_array[x])) + "\n"); // write data to ouput file
            }
            myWriter.close();

        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }   
}
