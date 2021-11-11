// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class Externalsort {
    /**
     * @param args
     *     Command line parameters
     */
    public static void main(String[] args){
        
        // Check whether arguments are valid
        if (args.length != 1) {
            throw new IllegalArgumentException(
                "\nThe number of arguments is invalid."
                + "\nThe program should be invoked as '> java Externalsort "
                + "{record file name}'\nWhere record file name is a .txt file.");
        }
        
        
        try {
            
            RandomAccessFile raf = new RandomAccessFile(args[0], "r");
           
            int blockSize = 512;
            int recordSize = 16;
            int outputCounter = 0;

            //Records with a size of 8 blocks
            Record[] records = new Record[8*blockSize];
           
            // Input 8 blocks of records into an array of records
            for(int i = 0; i < 8*blockSize; i++) {
               byte[] bytes = new byte[recordSize]; 
               
               raf.read(bytes);
               
               Record tempRec = new Record(bytes);
               
               records[i] = tempRec;
               
               System.out.println("ID: "+ tempRec.getId()+" Key: " +tempRec.getKey()+"\n");
               System.out.println("Index: "+i);
            }
            
            // The array of records are put into the MinHeap
            MinHeap heap = new MinHeap(records, records.length, 8*blockSize);
            
            // The InputBuffer array
            byte[] inputBuffer = new byte[blockSize];

            
            //The output buffer array 
            byte[] outputBuffer = new byte[blockSize];
            
            
            while(outputCounter != blockSize) 
            {
                
                outputCounter++;
            }
            raf.close();
            
        } 
        
        catch (FileNotFoundException e) {
             System.err.println("This shouldn't happen: " + e);
             } 
        catch (IOException e) {
             System.err.println("Writing error: " + e);
             }
               
    }
}
