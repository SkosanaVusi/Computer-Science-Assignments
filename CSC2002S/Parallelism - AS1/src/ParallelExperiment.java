import java.util.concurrent.ForkJoinPool;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.BufferedWriter;
import java.io.FileWriter; // Import the FileWriter class
import java.text.DecimalFormat;

public class ParallelExperiment {
    static long startTime = 0; // when timing median filtering operation
    static int numOflines;  // the first line of input file 
    static float[] file_data = null; // contains data read from input file
    static final ForkJoinPool fjPool = new ForkJoinPool();
    private static void tick() {startTime = System.currentTimeMillis();} // records current time when starting median filtering operation
    private static float tock() {return (System.currentTimeMillis() - startTime) / 1000.00000f;} // records time afterwards, from nano to seconds


    static float[] toParallelFilter(float[] arr, int filter_size, int seq_cuttOFF) {
        return fjPool.invoke(new ParallelFilter(arr, 0, arr.length, filter_size, seq_cuttOFF));
    }

    /**
     * Read data from input file into array
     * @param args - sequentiall_cutoff integers to be used in runner method.
     */
    public static void main(String[] args){
        int seqLwr=Integer.valueOf(args[0]);
        int seqUppr=Integer.valueOf(args[1]);
        int seqIncr=Integer.valueOf(args[2]);
        String inputFileName = args[3];
        Stiring outputFileName = args[4];

        try {
            // read file
            File myObj = new File(inputFileName);
            Scanner myReader = new Scanner(myObj);

            // set length of file_data according to first line
            if (myReader.hasNext()) {
                numOflines = Integer.valueOf(myReader.nextLine());
                file_data = new float[numOflines];}
            // load data from input file to file_data array
            for (int x = 0; x < file_data.length; x++) {
                file_data[x] = Float.valueOf((myReader.nextLine().split(" ")[1]).replaceAll(",", "."));}
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        runner(seqLwr, seqUppr, seqIncr, outputFileName);
    }
    
    /**
     * Collect timing of parallel median filtering operation. 
     * 
     * Contains lower, upper, and increaments values of sequential_cuttoff, for each sequential_cutoff
     * median filtering is applied from a filtering window of 3 to 21 with increments of 2, i.e (3, 5, 7,...,21).
     * 
     * @param sL Lower value of sequential_cutoff
     * @param sU Upper value of sequential_cutoff
     * @param sI Increments applied to Lower value to reach Upper value
     */

    public static void runner(int sL, int sU, int sI, String outPut){ 
        try{
            float a = 0.00000f; // timer
            FileWriter myWriter = new FileWriter(outPut); // file to save timing collected 
            BufferedWriter writer = new BufferedWriter(myWriter);

            for (int seq = sL; seq < sU; seq += sI) {
                writer.write(String.valueOf(String.format("%-9s", seq))); // write sequence_cutoff value to file
                System.out.print(String.format("%-9s", seq));
                for (int filter = 3; filter < 23; filter += 2) {
                    for (int indx = 0; indx < 20; indx++) { // for each filter size, execute filtering operttion run it 20 times, store time values
                        System.gc();
                        tick();
                        float[] b = toParallelFilter(file_data, filter, seq);
                        a = a + tock();
                    }
                    a = a / 20; // the avarage time for filtering for that sequential_cuttOff and filter 
                    System.out.print(" " + String.format("%-8.6f", a));
                    writer.write(" " + String.format("%-8.6f", a)); // write the avarage time to output file
                    a = 0; // clear timing for new filter timing to begin.
                    
                }
                writer.write("\n"); 
                System.out.println("\n");
            }
            writer.close(); 
            myWriter.close();



        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
