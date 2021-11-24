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

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import student.TestCase;

/**
 * Test class for the Run class
 * @author Jonathan Reynosa, Emilio Rivera
 * @version 9.22.2021
 */
public class RunTest extends TestCase {

    private Run run;


    /**
     * The setup for the tests
     */
    public void setUp() {
        int indexOfFirst = 1;
        run = new Run(indexOfFirst);

    }


    /**
     * Test for getHasRead and setHasRead methods
     */
    public void testHasRead() {
        assertFalse(run.getHasRead());
        run.setHasRead();
        assertTrue(run.getHasRead());
    }


    /**
     * Test for getIndexOfFirst
     */
    public void testGetIndexOfFirst() {
        assertEquals(1, run.getIndexOfFirst());
    }


    /**
     * Test for getIndexOfLast and setIndexOfLast
     */
    public void testGetIndexOfLast() {
        run.setIndexOfLast(10);
        assertEquals(10, run.getIndexOfLast());
    }


    /**
     * Test for getIndexOfLastRecRead and setLastRecRead
     */
    public void testGetIndexOfLastRecRead() {
        assertEquals(0, run.getIndexOfLastRecRead());
        run.setLastRecRead(10);
        assertEquals(10, run.getIndexOfLastRecRead());
    }


    /**
     * Test for getBlockOfRecord and setBlockOfRecord
     */
    public void testGetBlockOfRecord() {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7);
        buffer.putDouble(8, 1);
        byte[] aBite = buffer.array();
        Record rec = new Record(aBite);

        List<Record> r = new LinkedList<Record>();
        r.add(rec);

        run.setBlockOfRecord(r);
        assertEquals(0, run.getBlockOfRecord().get(0).compareTo(rec));
    }
}
