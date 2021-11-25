
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
 * Driver class for CS 3114 Project 3
 */

/**
 * The class containing the main method.
 *
 * @author Jonathan Reynosa Emilio Rivera
 * @version 11.24.2021
 */
public class Externalsort {

    /**
     * Entry method for program
     * 
     * @param args
     *            Command line parameters
     */
    public static void main(String[] args) {

        // Check whether arguments are valid
        if (args.length != 1) {
            throw new IllegalArgumentException(
                "\nThe number of arguments is invalid."
                    + "\nThe program should be invoked as '> java Externalsort "
                    + "{record file name}'\nWhere record file name is a .txt file.");
        }

        try {

            RandomAccessFile raf = new RandomAccessFile(args[0], "r");
            
            Reader reader = new Reader(raf, args[0]);

        }

        catch (FileNotFoundException e) {
            System.err.println("This shouldn't happen: " + e);
        }

    }
}
