import java.lang.Thread.State;
import java.util.Arrays;

/**
 * SerialFilter applies median filtering sequentially to a given array within specified indexes.
 */
public class SerialFilter {
    float[] full_data;
    int filter_size;
    int l;
    int h;

    /**
     * Constructor for median SerialFilter.
     * @param file_data Array to start apply filtering to.
     * @param filter_size Filter window size to apply filtering within lower and upper index.
     * @param start Lower index on array to start filtering onwards.
     * @param end Upper index on array to stop filtering.
     */
    public SerialFilter(float[] file_data ,int filter_size, int start, int end){
        this.full_data = file_data;
        this.filter_size = filter_size;
        this.l = start;
        this.h = end;
    }
    /**
     * Apply median filtering.
     * @return Resulting filtered array from lower to upper index of filtering.
     */

    public float[] medianFilter(){
        float[] result = new float[h-l]; // array with filtered data
        float[] piece_data = new float[filter_size]; // array to manipulate data within filter window
        result[0] = full_data[l]; // unchanged first element on the lower index

        for(int x=l; x<h; x++){  //
            if( (x+filter_size) <= h ){ // if there's still a filter window from the current index to the upper index.
                for (int a = 0; a < filter_size; a++) { 
                    piece_data[a] = full_data[x+a]; // fill piece_data array with elements within filtering window from the current element
                }
                Arrays.sort(piece_data); // sort array in ascending order.
                result[x-l+1] = piece_data[piece_data.length/2]; // append median in array
            }else{ // if filter window not open from the current index then append all elements from the current element
                for(int b=x+1; b<h; b++){
                    result[b-l] = full_data[b];
                }
                break;
            }
        }
        return result;
    }
}



















// int[] a = new int[4];

// for(int x=0; x<a.length; x++){
//     a[x] = x+6;
//     System.out.println(x+6);
// }
// Arrays.sort(a);

// array to have values from file