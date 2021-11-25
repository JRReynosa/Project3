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

/**
 * Implementation of MinHeap. A MinHeap keeps the minimum value
 * in the root.
 * 
 * @author Jonathan Reynosa Emilio Rivera
 * @version 11.24.2021
 */
class MinHeap {
    private Record[] heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap

    /**
     * Entry method for program
     * 
     * @param h
     *            Record array for heap
     * @param num
     *            Number of initial items in heap
     * @param max
     *            Max things in heap  
     * @return new MinHeap
     */
    public MinHeap(Record[] h, int num, int max) {
        heap = h;
        n = num;
        size = max;
        buildheap();
    }
    
    /**
     * Returns if heap is full
     * 
     * @return if heap is full
     */
    public boolean isFull() {
        return n == size;
    }
    
    /**
     * Returns heap
     * 
     * @return heap
     */
    public Record[] getHeap() {
        return heap;
    }
    
    /**
     * Returns if heap is empty
     * 
     * @return if heap is empty
     */
    public boolean isEmpty() {
        return n == 0;
    }
    
    /**
     * Returns heap size
     * 
     * @return heap size
     */
    public int heapsize() {
        return n;
    }

    /**
     * Return true if pos a leaf position, false otherwise
     * 
     * @param pos
     * 			position considered
     * 
     * @return if pos a leaf position
     */
    public boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }

    /**
     * Return position for left child of pos
     * 
     * @param pos
     * 			position considered
     * 
     * @return position for left child of pos
     */
    public int leftchild(int pos) {
        if (pos >= n / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }

    /**
     * Return position for right child of pos
     * 
     * @param pos
     * 			position considered
     * 
     * @return position for right child of pos
     */
    public int rightchild(int pos) {
        if (pos >= (n - 1) / 2) {
            return -1;
        }
        return 2 * pos + 2;
    }

    /**
     * Return position for parent of pos
     * 
     * @param pos
     * 			position considered
     * 
     * @return position for parent of pos
     */
    public int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }

    /**
     * Insert value into heap
     * 
     * @param key
     * 			new Record to add
     */
    public void insert(Record key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        int curr = n++;
        heap[curr] = key; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && heap[curr].compareTo(heap[parent(
            curr)]) < 0) {
            swap(heap, curr, parent(curr));
            curr = parent(curr);
        }
    }
    
    /**
     * Insert value into root and don't sort
     * 
     * @param key
     * 			new Record to add
     */
    public void insertRootAndNoSwap(Record key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        n++;
        heap[0] = key;
        siftdown(0);
    }
    
    /**
     * Insert value into root and switch
     * 
     * @param key
     * 			new Record to add
     */
    public void insert2(Record key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        heap[0] = key;
        
        // Swap new root with last value
        swap(heap, 0, --n);
        
        siftdown(0);
    }
    
    /**
     * Insert value into root and swap
     * 
     * @param key
     * 			new Record to add
     */
    public void insertRootAndSwap(Record key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        n++;
        heap[0] = key;
        
        // Swap new root with last value
        swap(heap, 0, --n);
        
        siftdown(0);
    }

    /**
     * Heapify contents of Heap
     */
    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }

    /**
     * Put element in its correct place
     * 
     * @param pos
     * 			position considered
     */
    private void siftdown(int pos) {
        if ((pos < 0) || (pos >= n)) {
            return;
        } // Illegal position
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (n - 1)) && ((heap[j]).compareTo(heap[j + 1]) > 0)) {
                j++; // j is now index of child with lesser value
            }
            if ((heap[pos]).compareTo(heap[j]) <= 0) {
                return;
            }
            swap(heap, pos, j);
            pos = j; // Move down
        }
    }


    /**
     * Remove and return minimum value
     * 
     * @return min record
     */
    public Record removeMin() {
        if (n == 0) {
            return null;
        } // Removing from empty heap
        swap(heap, 0, --n); // Swap Minimum with last value
        siftdown(0); // Put new heap root val in correct place
        return heap[n];
    }

    /**
     * Remove and return root value but leave root empty
     * 
     * @return min record
     */
    public Record removeRoot() {
        if (n == 0) {
            return null;
        } // Removing from empty heap
        Record temp = heap[0];
        heap[0] = null;

        n--;
        return temp;
    }

    /**
     * Remove and return element at specified position
     * 
     * @param pos
     * 			position considered
     * 
     * @return Record at pos
     */
    public Record remove(int pos) {
        if ((pos < 0) || (pos >= n)) {
            return null;
        } // Illegal heap position
        if (pos == (n - 1)) {
            n--;
        } // Last element, no work to be done
        else {
            swap(heap, pos, --n); // Swap with last value
            update(pos);
        }
        return heap[n];
    }

    /**
     * Swap Records in heap
     * 
     * @param arr
     *            Given heap
     * @param left
     *            Record one
     * @param right
     *            Record two
     */
    private void swap(Record[] arr, int left, int right) {
        Record temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    /**
     * The value at pos has been changed, restore the heap property
     * 
     * @param pos
     * 			position considered
     */
    private void update(int pos) {
        // If it is a big value, push it up
        while ((pos > 0) && ((heap[pos]).compareTo(heap[parent(pos)]) < 0)) {
            swap(heap, pos, parent(pos));
            pos = parent(pos);
        }
        siftdown(pos); // If it is little, push down
    }
}
