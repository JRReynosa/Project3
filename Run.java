
import java.util.LinkedList;
import java.util.List;

/**
 * Holds a single record
 * 
 * @author Jonathan Reynosa Emilio Rivera
 * @version 11.24.2021
 */
public class Run {
    private int indexOfFirst;
    private int indexOfLast;
    private int indexOfLastRecRead;
    private List<Record> currentBlockOfRecord;
    private boolean hasEverRead = false;
    
    Run(int indexOfFirst) {
        this.indexOfFirst = indexOfFirst;
        this.indexOfLastRecRead = this.indexOfFirst - 1;
        currentBlockOfRecord = new LinkedList<Record>();
        
    }
    
    /**
     * Sets hasRead
     */
    public void setHasRead() {
        hasEverRead = true;
    }
    
    /**
     * returns hasRead
     * 
     * @return hasRead
     */
    public boolean getHasRead() {
        return hasEverRead;
    }
    
    /**
     * returns indexOfFirst
     * 
     * @return index
     */
    public int getIndexOfFirst() {
        return indexOfFirst;
    }
    
    /**
     * returns indexOfLast
     * 
     * @return index
     */
    public int getIndexOfLast() {
        return indexOfLast;
    }
    
    /**
     * returns indexOfLastRecRead
     * 
     * @return index
     */
    public int getIndexOfLastRecRead() {
        return indexOfLastRecRead;
    }
    
    /**
     * returns record list of block
     * 
     * @return record list
     */
    public List<Record> getBlockOfRecord() {
        return currentBlockOfRecord;
    }
    
    /**
     * Set lastRecRead
     * 
     * @param curr
     * 				new value
     */
    public void setLastRecRead(int curr) {
        indexOfLastRecRead = curr;
    }
    
    /**
     * Set IndexOfLast
     * 
     * @param last
     * 				new value
     */
    public void setIndexOfLast(int last) {
        indexOfLast = last;
    }
    
    /**
     * Set records in block
     * 
     * @param recs
     * 				List of records
     */
    public void setBlockOfRecord(List<Record> recs) {
        currentBlockOfRecord = recs;
    }
}

