
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
        
        heap.removeMin();
        
        assertEquals(0, heap.heapsize());
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

    
    public void testremoveMin() {
        Record[] records = new Record[3];
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7);
        buffer.putDouble(8, 1);
        ByteBuffer buffer2 = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer2.putLong(7);
        buffer2.putDouble(8, 9);
        ByteBuffer buffer3 = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer2.putLong(7);
        buffer2.putDouble(8, 8);
        
        byte[] aBite = buffer.array();
        byte[] aBite2 = buffer2.array();
        byte[] aBite3 = buffer3.array();
        
        
        Record r1 = new Record(aBite);
        Record r2 = new Record(aBite2);
        Record r3 = new Record(aBite3);
        
        records[0] = r1;
        records[1] = r2;
        records[2] = r3;
        
        MinHeap heap = heap = new MinHeap(records, records.length, 512);
        
        assertEquals(r1, heap.removeMin());

  

    }

}
