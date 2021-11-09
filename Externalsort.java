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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

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
        	 byte numRecords = 0;
        	 int recordOffset = 0;

        	 RandomAccessFile raf = new RandomAccessFile(args[0], "r");
        	 
        	 // Get the number of records in the file:
        	 numRecords = raf.readByte();
        	 System.out.println("The number of words is: " + numRecords); 
        	 
        	 for (int readRecords = 0; readRecords < numRecords; readRecords++) {
        		 // Get the offset of the record:
        		 recordOffset = raf.readUnsignedShort();
        		 
        		 // Save offset to return for next offset:
        		 long offsetOfNextOffset = raf.getFilePointer();

        		 // Go to that position.
        		 raf.seek(recordOffset);
        		 
        		 // Get the record (all records are 16 bytes/128 bits):
        		 long recordId = raf.readLong(); // Read first 8 bytes/64 bits
        		 double recordKey = raf.readDouble();
        		 
        		 // Report results:
        		 System.out.println("The next record offset is: " +
        		 recordOffset);
        		 System.out.println("The next record ID is: " + recordId);
        		 System.out.println("The next record Key is: " + recordKey);

        		 // Seek back to position of next offset:
        		 raf.seek(offsetOfNextOffset);
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
