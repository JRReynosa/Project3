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
    private static int blockSize = 8192;
    private static int recordSize = 16;
    
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
          
            //Array of bytes the length of 1 block
            byte[] arr = new byte[blockSize];
            
            //Put the bytes in the random access file into the array
            raf.read(arr);
            
            //Convert array into ByteBuffer
            ByteBuffer buffer = ByteBuffer.wrap(arr);

            //Records with a size of 8 blocks
            Record[] records = new Record[8*blockSize];
            
            // Input 8 blocks of records into an array of records
            for(int i = 0; i < blockSize; i++) {
                byte[] bytes = new byte[blockSize];
                raf.seek(blockSize*i);
                
                raf.read(bytes);
                
                Record tempRec = new Record(bytes);
             
                records[i] = tempRec;
                
                System.out.println("ID: "+ tempRec.getId()+" Key: " +tempRec.getKey()+"\n");
                System.out.println("Index: "+i);
            }
            
            // The array of records are put into the MinHeap
            MinHeap heap = new MinHeap(records, records.length, 8*blockSize);

            if (heap.isFull()) {
                replacementSelection();
            }
            
            // The InputBuffer array
            byte[] inputBuffer = new byte[blockSize];
            buffer.get(inputBuffer);
            
            //The output buffer array 
            byte[] outputBuffer = new byte[blockSize];
            
            raf.close();
            
        } 
        
        catch (FileNotFoundException e) {
             System.err.println("This shouldn't happen: " + e);
             } 
        catch (IOException e) {
             System.err.println("Writing error: " + e);
             }
               
    }
    public static void replacementSelection() {
        
    }
}
