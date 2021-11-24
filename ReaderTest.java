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
// anyone other than the instructor, ACM/UPE tutors, programming
// partner (if allowed in this class), or the TAs assigned to
// this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Random;

import student.TestCase;

/**
 * Test class for the Reader class
 * @author Jonathan Reynosa, Emilio Rivera
 * @version 9.22.2021
 */
public class ReaderTest extends TestCase {
    private RandomAccessFile otherFile;
    private Reader r;

    /**
     * Set up for test cases
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
        RandomAccessFile file = new RandomAccessFile("file.bin", "rw");
        int blocks = 10;
        int recInBlock = 512;
        Random ran = new Random();
        ran.setSeed(10);
        for (int i = 0; i < blocks; i++) {
            for (int j = 0; j < recInBlock; j++) {
                long val = ran.nextLong();
                file.writeLong(val);
                double val2 = ran.nextDouble();
                file.writeDouble(val2);
            }
        }
        file.seek(0);
        r = new Reader(file);

        otherFile = new RandomAccessFile("otherFile.bin", "rw");
        for (int i = 0; i < blocks; i++) {
            for (int j = 0; j < recInBlock; j++) {
                long val = ran.nextLong();
                otherFile.writeLong(val);
                double val2 = ran.nextDouble();
                otherFile.writeDouble(val2);
            }
        }
        otherFile.seek(0);
    }


    /**
     * Test for constructor
     */
    public void testReader() {
        assertNotNull(r);
    }


    /**
     * Test for recordsToBytes method
     */
    public void testRecordsToBytes() {
        Record[] arr = new Record[1];
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7);
        buffer.putDouble(8, 1);
        byte[] aByte = buffer.array();
        arr[0] = new Record(aByte);
        byte[] b = r.recordsToBytes(arr);
        assertEquals(0, b[0]);
    }

    /**
     * Test for readBlock method
     * 
     * @throws IOException
     */
    public void testReadBlock() throws IOException {
        System.out.println(otherFile.getFilePointer());
        assertEquals(-71, r.readBlock(otherFile)[0]);
        assertEquals(65, r.readBlock(otherFile)[511]);
    }


    /**
     * Test for readBlock2 method
     * 
     * @throws IOException
     */
    public void testReadBlock2() throws IOException {
        assertEquals(-102, r.readBlock2(otherFile, 10)[0]);
        assertEquals(-124, r.readBlock2(otherFile, 10)[512]);
    }
}
