
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jonathan Reynosa, Emilio Rivera
 * @version 9.22.2021
 */
public class Run {
    private int indexOfFirst;
    private int indexOfLast;
    private int indexOfLastRecRead;
    private List<Record> currentBlockOfRecord;
    private boolean hasEverRead = false;

    /**
     * constructor
     * 
     * @param indexOfFirst
     *              index of first
     */
    Run(int indexOfFirst) {
        this.indexOfFirst = indexOfFirst;
        this.indexOfLastRecRead = this.indexOfFirst - 1;
        currentBlockOfRecord = new LinkedList<Record>();

    }


    /**
     * setHasRead()
     */
    public void setHasRead() {
        hasEverRead = true;
    }


    /**
     * getHasRead()
     * 
     * @return true if read before
     */
    public boolean getHasRead() {
        return hasEverRead;
    }


    /**
     * getIndexOfFirst()
     * 
     * @return index
     */
    public int getIndexOfFirst() {
        return indexOfFirst;
    }


    /**
     * getIndexOfLast()
     * 
     * @return index
     */
    public int getIndexOfLast() {
        return indexOfLast;
    }


    /**
     * getIndexOfLastRecRead()
     * 
     * @return index of last
     */
    public int getIndexOfLastRecRead() {
        return indexOfLastRecRead;
    }


    /**
     * getBlockOfRecord()
     * 
     * @return list
     */
    public List<Record> getBlockOfRecord() {
        return currentBlockOfRecord;
    }


    /**
     * setLastRecRead
     * 
     * @param curr
     *          current
     */
    public void setLastRecRead(int curr) {
        indexOfLastRecRead = curr;
    }


    /**
     * setIndexOfLast
     * 
     * @param last index
     */
    public void setIndexOfLast(int last) {
        indexOfLast = last;
    }


    /**
     * setBlockOfRecord
     * 
     * @param recs
     *              Records
     */
    public void setBlockOfRecord(List<Record> recs) {
        currentBlockOfRecord = recs;
    }
}
