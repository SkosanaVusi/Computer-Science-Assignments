import java.util.Scanner;
import java.io.File;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors

public class SerialExperiment {
    static long startTime = 0;
    static int numOflines;
    static float[] file_data = null;
    private static void tick() {startTime = System.currentTimeMillis();}
    private static float tock() {return (System.currentTimeMillis() - startTime) / 1000.0f;}

    public static void main(String[] args) {
        String inputFileName = args[0];
        int inputSize=0;
        try {
            // read file
            File myObj = new File(inputFileName);
            Scanner myReader = new Scanner(myObj);

            // setting length of file_data according to first line
            if (myReader.hasNext()){
                numOflines = Integer.valueOf(myReader.nextLine());
                file_data = new float[numOflines];}

            // load file data to file_data array
            for (int x = 0; x < file_data.length; x++) {
                file_data[x] = Float.valueOf((myReader.nextLine().split(" ")[1]).replaceAll(",", "."));
            }
            inputSize=file_data.length;
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        try{
            FileWriter myWriter = new FileWriter(inputSize+"sampleSerialOutputTest.txt"); // file to save                                                                         // timing collected
            BufferedWriter writer = new BufferedWriter(myWriter);
            float a = 0.00000f;

            for(int filter=3; filter<23; filter+=2){
                System.out.print(String.format("%-2s ", filter));
                writer.write(String.format("%-2s ", filter));
                for(int indx=0; indx<20; indx++){
                    System.gc();
                    tick();
                    SerialFilter serFilteObj = new SerialFilter(file_data, filter, 0, file_data.length); // make for-loop to change filter sizes
                    float[] serFilteArry = serFilteObj.medianFilter();
                    a = a+tock(); 
                }
                a=a/20;
                System.out.print(String.format("%-8.6f %n", a));
                writer.write(String.format("%-8.6f %n", a));
                a=0;
            }
            writer.close();
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
