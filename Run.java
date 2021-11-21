
import java.util.LinkedList;
import java.util.List;

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
    
    public void setHasRead() {
        hasEverRead = true;
    }
    
    public boolean getHasRead() {
        return hasEverRead;
    }
    
    public int getIndexOfFirst() {
        return indexOfFirst;
    }
    
    public int getIndexOfLast() {
        return indexOfLast;
    }
    
    public int getIndexOfLastRecRead() {
        return indexOfLastRecRead;
    }
    
    public List<Record> getBlockOfRecord() {
        return currentBlockOfRecord;
    }
    
    
    public void setLastRecRead(int curr) {
        indexOfLastRecRead = curr;
    }
    
    public void setIndexOfLast(int last) {
        indexOfLast = last;
    }
    
    public void setBlockOfRecord(List<Record> recs) {
        currentBlockOfRecord = recs;
    }
}

