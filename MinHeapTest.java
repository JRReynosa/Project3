
import java.nio.ByteBuffer;
import student.TestCase;

public class MinHeapTest extends TestCase {
    private byte[] aBite;
    private MinHeap heap;
    private Record r1;
    private Record[] records;

    public void setUp() {
        
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7);
        buffer.putDouble(8, 1);
        aBite = buffer.array();
        r1 = new Record(aBite);
        records = new Record[1];
        records[0] = r1;
        heap = new MinHeap(records, records.length,512);
    }
    
    public void testMinHeap() {
        assertNotNull(heap);
    }

    public void testHeapsize() {
        assertEquals(1, heap.heapsize());
    }


    public void testIsLeaf() {
        fail("Not yet implemented");
    }


    public void testLeftchild() {
        fail("Not yet implemented");
    }
    
    public void testRightchild() {
        fail("Not yet implemented");
    }

    
    public void testParent() {
        Record[] records = new Record[3];
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7);
        buffer.putDouble(8, 1);
        
        System.out.println(buffer.get(1));
        byte[] aBite = buffer.array();
       
        System.out.println();
  

    }

}
