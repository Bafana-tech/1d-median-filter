import java.util.concurrent.RecursiveTask;
import java.io.*;
import java.util.*;

/** This Class is parallel. It takes data and get medianS depending on the filter size and cutOff values.
 *It can run sequential depending on (hiBound - loBound)
 *@author Bafana Mhlahlo
 */

public class ParaFilter extends RecursiveTask<ArrayList<Double>>{

    /**
     * Instance variables hold data 
     */
    private int loBound;
    private int hiBound;
    private double[] noiseyData;
    private static int filter;
    private int len;
    private static int cutOff = 500000; // change the cut off size manually
    
    
    

    /**
     * Class constructor initialises the instance variables 
     */

    public ParaFilter(double[] noiseyData,int loBound, int hiBound, int filter){
        
        this.noiseyData = noiseyData;
        this.loBound = loBound;
        this.hiBound = hiBound;
        this.filter = filter;

    }

    /**
    * This method allows use to execute your solution parallel or sequential depending on the loBound and hiBound.
    * If cutOff is higher than hiBound - loBOund then it runs parallel if not it runs sequential
    * @return ArrayList<Double> which is the filtered medians
     */
    protected ArrayList<Double> compute(){


        if((hiBound - loBound) < cutOff+1){
            return median(noiseyData,filter);

        }

        else{

            ParaFilter left = new ParaFilter(noiseyData,loBound,(hiBound+loBound)/2,filter);
            ParaFilter right = new ParaFilter(noiseyData,(hiBound+loBound)/2,hiBound, filter);

            left.fork();
			
            ArrayList<Double> rightAns = right.compute();
			ArrayList<Double> leftAns  = left.join();

            leftAns.addAll(rightAns); 
            return leftAns;

        }
    }
        /** This method takes chunks of filtered arrayList and sort them
         * @param  sorts
         * 
         */
        public void sort(ArrayList<Double> sorts){
            Collections.sort(sorts);

        } 
        /** This method takes in our original data and filter size. It then compute the chucks which are sorted by sort method
         *  Then it takes the chunks get median then save it to the arrayList
         *  @param noiseyData
         *  @param filter
         *  @return the median filtered arrayList
         */
        public ArrayList<Double> median(double[] noiseyData, int filter){

            ArrayList<Double> filterValues = new ArrayList<Double>(filter);
            ArrayList<Double> seq = new ArrayList<Double>();
            int middle = (filter + 1)/2;
            int window = (filter - 1)/2;
                     
            for(int i = 0; i<window;i++){
                seq.add(noiseyData[i]);
            }

            int len = noiseyData.length - filter;
            for(int j = 0; j <len+1;j ++){
                for(int k = j; k < filter+j;k++){
                    filterValues.add(noiseyData[k]);
                }
                                
                sort(filterValues);
                seq.add(filterValues.get(middle-1));
                filterValues.clear();
            }

            for(int n = noiseyData.length - window; n<noiseyData.length;n++){
                seq.add(noiseyData[n]);
            }
            return seq;
        }
}