import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;

/**
* This class writes the output from the parallel program to txt file called parallel.txt
 */

public class WriteFile {
    /** This method write to a file
    * @param medians
     */
   public void write(ArrayList<Double> medians) throws IOException {
     try{
         int i = 0;
        FileWriter writer = new FileWriter("./data/parallel.txt"); 
        writer.write(medians.size() + System.lineSeparator());

        for(Double str: medians) {
        writer.write(i +" " +Math.round(str*100000.0)/100000.0 + System.lineSeparator());
        i++;
    }
        writer.close(); 
    }
    catch(FileNotFoundException e){
        System.out.println();
    }
   } 
}