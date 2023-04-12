import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;

/**
* This class writes the output from the sequential program to txt file called sequential.txt
 */

public class WriteFileSequential {
    /** This method write to a file
    * @param medians
     */
   public void write(double[] medians) throws IOException {
     try{
         int i = 0;
        FileWriter writer = new FileWriter("./data/sequential.txt"); //Change parallel file to sequential 
        writer.write(medians.length + System.lineSeparator());

        for(double str: medians) {
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