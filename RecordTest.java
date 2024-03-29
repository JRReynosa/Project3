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
import java.nio.ByteBuffer;
import student.TestCase;

/**
 * Test class for Record class
 * 
 * @author Jonathan Reynosa Emilio Rivera
 * @version 11.24.2021
 */
public class RecordTest extends TestCase {

    private byte[] aBite;
    private Record rec;


    /**
     * The setup for the tests
     */
    public void setUp() {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7);
        buffer.putDouble(8, 1);
        aBite = buffer.array();
        }


    /**
     * Tests the constructor
     */
    public void testRecord() {
        Record rec = new Record(aBite);
        assertEquals((double)1, rec.getKey(), 0.00);
        assertEquals((double)7, rec.getId(), 0.00);
        assertEquals(aBite, rec.getCompleteRecord());
        assertTrue(rec.toString().equals("1.0"));
    }
    
    /**
     * Tests the compareTo()
     */
    public void testCompareTo() {
        Record rec = new Record(aBite);
        Record recToBeCompared = new Record(aBite);
        assertEquals(rec.compareTo(recToBeCompared), 0);
    }
    
    /**
     * Tests the toString()
     */
    public void testToString() {
        Record rec = new Record(aBite);
        assertEquals("1.0", rec.toString());
    }

}
