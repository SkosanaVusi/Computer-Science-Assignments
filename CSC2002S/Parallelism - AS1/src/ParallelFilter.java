import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * ParalleFilter applies median filteriring in parrallel to a given array within specified indexes.
 */

public class ParallelFilter extends RecursiveTask<float[]> {
    int lo;
    int hi;
    float[] arr;
    int filter_size;
    static int SEQUENTIAL_CUTOFF;
    
    /**
     * Constructor for median ParallelFilter
     * @param array       Array to start apply filtering to.
     * @param l           Lower index on array to start filtering onwards.
     * @param h           Upper index on array to stop filtering.
     * @param filter_size Filter window size to apply filtering within lower and upper index.
     * @param seq_cuttOFF Piece of the array to start filtering.
     */
    ParallelFilter(float[] array, int l, int h, int filter_size, int seq_cuttOFF) {
        this.lo = l;
        this.hi = h;
        this.arr = array;
        this.filter_size = filter_size;
        this.SEQUENTIAL_CUTOFF=seq_cuttOFF;
    }

    /**
     * Splits array through threads to be median filtered by SerialFilter program, then combine results
     * @return Resulting filtered array from lower to upper index of filtering.
     */
    protected float[] compute() {
        if ((hi - lo) <= SEQUENTIAL_CUTOFF) { // if number of elements from lower to upper index is equal or lower than sequantial cut off, then apply SerialFiltering
            return (new SerialFilter(arr, filter_size, lo, hi)).medianFilter(); 

        } else { // split array in hal(left and right) and run two separate threads on each half  
            ParallelFilter left = new ParallelFilter(arr, lo, (hi + lo)/ 2, filter_size, SEQUENTIAL_CUTOFF); // recursively create new threads
            ParallelFilter right = new ParallelFilter(arr, (hi + lo) / 2, hi, filter_size, SEQUENTIAL_CUTOFF); // recursively create new threads
           
            left.fork(); // gives left half independant execution
            float[] rightAns = right.compute(); // execute and encapusates behavior for right thread
            float[] leftAns = left.join(); // return left thread, waits for right thread to finish
            
            // combine the left and right array to one
            float[] both = Arrays.copyOf(leftAns, leftAns.length + rightAns.length);
            System.arraycopy(rightAns, 0, both, leftAns.length, rightAns.length);

            return both;
        }
    }
}
