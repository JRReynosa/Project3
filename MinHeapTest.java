
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
    
    public void testIsLeaf() {
        int len = Long.BYTES + Double.BYTES;
        Record[] records2 = new Record[6];
        MinHeap hp = new MinHeap(records2, 0, 6);
        ByteBuffer buffer1 = ByteBuffer.allocate(len);
        ByteBuffer buffer2 = ByteBuffer.allocate(len);
        ByteBuffer buffer3 = ByteBuffer.allocate(len);
 
        buffer1.putLong(7);
        buffer1.putDouble(8, 1);
        Record rec1 = new Record(buffer1.array());
        hp.insert(rec1);
        
        buffer2.putLong(6);
        buffer2.putDouble(0);
        Record rec2 = new Record(buffer2.array());
        hp.insert(rec2);
        
        buffer3.putLong(8);
        buffer3.putDouble(2);
        Record rec3 = new Record(buffer3.array());
        hp.insert(rec3);
        
        assertTrue(hp.isLeaf(1));
        assertTrue(hp.isLeaf(2));
        assertFalse(hp.isLeaf(0));
        hp.isLeaf(-1);
        hp.isLeaf(7);

    }
    
    public void testLeftChild() {
        int len = Long.BYTES + Double.BYTES;
        Record[] records2 = new Record[6];
        MinHeap hp = new MinHeap(records2, 0, 6);
        ByteBuffer buffer1 = ByteBuffer.allocate(len);
        ByteBuffer buffer2 = ByteBuffer.allocate(len);
        ByteBuffer buffer3 = ByteBuffer.allocate(len);
 
        buffer1.putLong(7);
        buffer1.putDouble(3);
        Record rec1 = new Record(buffer1.array());
        hp.insert(rec1);
        
        buffer2.putLong(6);
        buffer2.putDouble(0);
        Record rec2 = new Record(buffer2.array());
        hp.insert(rec2);
        
        buffer3.putLong(8);
        buffer3.putDouble(2);
        Record rec3 = new Record(buffer3.array());
        hp.insert(rec3);
        
        assertEquals(1, hp.leftchild(0));
        
        assertEquals(2, hp.rightchild(0));
        hp.leftchild(2);
        hp.rightchild(2);
        hp.parent(0);
    }
    
    public void testIsFull() {
        assertFalse(heap.isFull());
        MinHeap hp = new MinHeap(records, 1, 1);
        
        assertTrue(hp.isFull());
    }
    public void testHeapsize() {
        assertEquals(1, heap.heapsize());
        
        heap.removeMin();
        
        assertEquals(0, heap.heapsize());
    }
    
    public void testGetHeap() {
        assertEquals(r1, heap.getHeap()[0]);
    }
    
    public void testIsEmpty() {
        MinHeap hp = new MinHeap(records, 1, 1);
        assertFalse(hp.isEmpty());
        hp.removeMin();
        assertTrue(hp.isEmpty());
       
    }
    public void testRemove() {
        
        assertEquals(r1, heap.remove(0));
        int len = Long.BYTES + Double.BYTES;
        Record[] records2 = new Record[6];
        MinHeap hp = new MinHeap(records2, 0, 6);
        ByteBuffer buffer1 = ByteBuffer.allocate(len);
        ByteBuffer buffer2 = ByteBuffer.allocate(len);
        ByteBuffer buffer3 = ByteBuffer.allocate(len);
        ByteBuffer buffer4 = ByteBuffer.allocate(len);
        ByteBuffer buffer5 = ByteBuffer.allocate(len);
        ByteBuffer buffer6 = ByteBuffer.allocate(len);
        ByteBuffer buffer7 = ByteBuffer.allocate(len);
        
        
        buffer1.putLong(7);
        buffer1.putDouble(8, 1);
        Record rec1 = new Record(buffer1.array());
        hp.insert(rec1);
        
        buffer2.putLong(6);
        buffer2.putDouble(0);
        Record rec2 = new Record(buffer2.array());
        hp.insert(rec2);
        
        buffer3.putLong(8);
        buffer3.putDouble(2);
        Record rec3 = new Record(buffer3.array());
        hp.insert(rec3);
        
        buffer4.putLong(7);
        buffer4.putDouble(5);
        Record rec4 = new Record(buffer4.array());
        hp.insert(rec4);
        
        buffer5.putLong(7);
        buffer5.putDouble(20);
        Record rec5 = new Record(buffer5.array());
        hp.insert(rec5);
        
        buffer6.putLong(1);
        buffer6.putDouble(30);
        Record rec6 = new Record(buffer6.array());
        hp.insert(rec6);
        
        buffer7.putLong(1);
        buffer7.putDouble(2);
        Record rec7 = new Record(buffer7.array());
        hp.insert(rec7);
        
        assertTrue(hp.isFull());
        hp.remove(5);
        hp.remove(6);
        hp.remove(7);
        hp.remove(0);
        hp.remove(0);
        hp.remove(1);
        hp.remove(2);
        hp.remove(3);
        hp.remove(4);
        hp.remove(-1);
        hp.remove(0);
        hp.heapSort();
    }
    
    public void testInsert() {
        int len = Long.BYTES + Double.BYTES;
        Record[] records2 = new Record[6];
        MinHeap hp1 = new MinHeap(records2, -1, 6);
        hp1.heapSort();
        MinHeap hp = new MinHeap(records2, 0, 6);
        hp.heapSort();
        ByteBuffer buffer1 = ByteBuffer.allocate(len);
        ByteBuffer buffer2 = ByteBuffer.allocate(len);
        ByteBuffer buffer3 = ByteBuffer.allocate(len);
        ByteBuffer buffer4 = ByteBuffer.allocate(len);
        ByteBuffer buffer5 = ByteBuffer.allocate(len);
        ByteBuffer buffer6 = ByteBuffer.allocate(len);
        ByteBuffer buffer7 = ByteBuffer.allocate(len);
        
        
        buffer1.putLong(7);
        buffer1.putDouble(8, 1);
        Record rec1 = new Record(buffer1.array());
        hp.insert(rec1);
        
        buffer2.putLong(6);
        buffer2.putDouble(0);
        Record rec2 = new Record(buffer2.array());
        hp.insert(rec2);
        
        buffer3.putLong(8);
        buffer3.putDouble(2);
        Record rec3 = new Record(buffer3.array());
        hp.insert(rec3);
        
        buffer4.putLong(7);
        buffer4.putDouble(1);
        Record rec4 = new Record(buffer4.array());
        hp.insert(rec4);
        
        buffer5.putLong(7);
        buffer5.putDouble(7);
        Record rec5 = new Record(buffer5.array());
        hp.insert(rec5);
        
        buffer6.putLong(1);
        buffer6.putDouble(1);
        Record rec6 = new Record(buffer6.array());
        hp.insert(rec6);
        
        buffer7.putLong(1);
        buffer7.putDouble(1);
        Record rec7 = new Record(buffer7.array());
        hp.insert(rec7);
        
        assertTrue(hp.isFull());
    }
    public void testInsert2() {
        int len = Long.BYTES + Double.BYTES;
        Record[] records2 = new Record[6];
        MinHeap hp = new MinHeap(records2, 0, 6);
        ByteBuffer buffer1 = ByteBuffer.allocate(len);
        ByteBuffer buffer2 = ByteBuffer.allocate(len);
        ByteBuffer buffer3 = ByteBuffer.allocate(len);
        ByteBuffer buffer4 = ByteBuffer.allocate(len);
        ByteBuffer buffer5 = ByteBuffer.allocate(len);
        ByteBuffer buffer6 = ByteBuffer.allocate(len);
        ByteBuffer buffer7 = ByteBuffer.allocate(len);
        
        
        buffer1.putLong(7);
        buffer1.putDouble(8, 1);
        Record rec1 = new Record(buffer1.array());
        hp.insert(rec1);
        
        buffer2.putLong(6);
        buffer2.putDouble(0);
        Record rec2 = new Record(buffer2.array());
        hp.insert(rec2);
        
        buffer3.putLong(8);
        buffer3.putDouble(2);
        Record rec3 = new Record(buffer3.array());
        hp.insert(rec3);
        
        buffer4.putLong(7);
        buffer4.putDouble(1);
        Record rec4 = new Record(buffer4.array());
        hp.insert2(rec4);
        
        buffer5.putLong(7);
        buffer5.putDouble(7);
        Record rec5 = new Record(buffer5.array());
        hp.insert2(rec5);
        
        buffer6.putLong(1);
        buffer6.putDouble(1);
        Record rec6 = new Record(buffer6.array());
        hp.insert2(rec6);

        ByteBuffer buffer8= ByteBuffer.allocate(len);
        ByteBuffer buffer9 = ByteBuffer.allocate(len);
        buffer7.putLong(9);
        buffer7.putDouble(8, 9);
        Record rec7 = new Record(buffer7.array());
        hp.insert(rec7);
        
        buffer8.putLong(0);
        buffer8.putDouble(0);
        Record rec8 = new Record(buffer8.array());
        hp.insert(rec8);
        
        buffer9.putLong(10);
        buffer9.putDouble(1);
        Record rec9 = new Record(buffer9.array());
        hp.insert(rec9);
        
        ByteBuffer buffer10 = ByteBuffer.allocate(len);
        buffer10.putLong(10);
        buffer10.putDouble(3);
        hp.insert2(new Record(buffer10.array()));

    }
    public void testremoveMin() {
        MinHeap hp = new MinHeap(records, 0, 6);
        assertNull(hp.removeMin());
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
        
    }

}
