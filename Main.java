import java.util.concurrent.ForkJoinPool;
import java.io.*;
import java.util.*;

/**
 * This class calculate the median filter sequentially.
 * It calls the median filter method from ParaFilter class 
 * to filter data.
 * @author Bafana Mhlahlo
 */

public class Main{

    /**
     * Instance variables hold data
     */

    static final ForkJoinPool fjPool = new ForkJoinPool();
    static int len;
    static String[] datas;
    static WriteFile pS = new WriteFile(); // Creating an instance of WriteFile

    static long startTime = 0;

    /**
     * Method to start calculating time
     */
    private static void tick(){
        startTime = System.nanoTime();
    }

    /**
     * Method to end time
     * @return time at end
     */
    private static float tock(){
        return (System.nanoTime() - startTime) / 1000000.0f;
    }
 
    /**
     * This method invokes the parallel in fok/join
     * @param double[] arr
     * @return ArrayList<Double> cleaned data with medians
     */

    static ArrayList<Double> medFilter(double[] arr,int size){
        return fjPool.invoke(new ParaFilter(arr,0,arr.length,size));
    }


    public static void main(String[] args) throws IOException{
        
        try{
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter filename with (txt)");
            String filename = keyboard.nextLine();

            System.out.println("\nEnter the filter size");
            int filterSize = keyboard.nextInt();

            File f = new File("./data/"+filename);
            Scanner read = new Scanner(f);
            String datum = read.nextLine();
            len = Integer.parseInt(datum);
            double[] store = new double[len];
            int j = 0;


            while(read.hasNextLine()){
                datas = read.nextLine().split(" ");
                String fnum = datas[1].replace(",",".");
                double save = Double.parseDouble(fnum);

                store[j] = save;
                j++;
            }
            read.close();
           
           // garbage collector
           System.gc();

           // Measure the parallel time
           tick();

           medFilter(store,filterSize); // Referencing the medFilter for pool.invoke so that it goes to parallel
           float time = tock();
           tick(); // end measuring it

           WriteFile ps = new WriteFile();
           ps.write(medFilter(store,filterSize));

           System.out.println("\nparallel took " + time + " milliseconds");
           ParaFilter p = new ParaFilter(store,0,store.length,filterSize);


           tick();
           p.median(store,filterSize); //calls the median method to sequential
           float time2 = tock();


           System.out.println("Sequential took " + time2 + " milliseconds");


           // write out of sequential to file
           WriteFileSequential writefile = new WriteFileSequential();
           writefile.write(store);

           

        }
        


        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}