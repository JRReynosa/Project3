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
 * Implementation of MinHeap
 * 
 * @author Jonathan Reynosa, Emilio Rivera
 * @version 9.22.2021
 */
// Min-heap implementation
class MinHeap {
    private Record[] heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap

    // Constructor supporting preloading of heap contents
    /**
     * Constructor for the MinHeap class
     * 
     * @param h
     *            heap
     * @param num
     *            number of items in the heap
     * @param max
     *            maximum heap size
     */
    MinHeap(Record[] h, int num, int max) {
        heap = h;
        n = num;
        size = max;
        buildheap();
    }


    /**
     * Determines if heap is full
     * 
     * @return true if full
     */
    public boolean isFull() {
        return n == size;
    }


    /**
     * returns the heap
     * 
     * @return heap
     */
    public Record[] getHeap() {
        return heap;
    }


    /**
     * Determines if the heap is empty
     * 
     * @return true if empty
     * 
     */
    public boolean isEmpty() {
        return n == 0;
    }


    // Return current size of the heap
    /**
     * returns the heap size
     * 
     * @return heap size
     */
    public int heapsize() {
        return n;
    }


    // Return true if pos a leaf position, false otherwise
    /**
     * True if pos is a leaf
     * 
     * @param pos
     *            position
     * @return
     *         true if pos is a leaf
     */
    public boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    // Return position for left child of pos
    /**
     * Returns position for left child
     * 
     * @param pos
     *            Position
     * @return
     *         position of child
     */
    public int leftchild(int pos) {
        if (pos >= n / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }


    // Return position for right child of pos
    /**
     * Returns position for right child
     * 
     * @param pos
     *            Position
     * @return
     *         position of child
     */
    public int rightchild(int pos) {
        if (pos >= (n - 1) / 2) {
            return -1;
        }
        return 2 * pos + 2;
    }


    // Return position for parent
    /**
     * return the pos of parent
     * 
     * @param pos
     *            Position
     * @return
     *         Position
     */
    public int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }


    // Insert val into heap
    /**
     * Insert an element
     * 
     * @param key
     *            element
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
     * Insert an element
     * 
     * @param key
     *            element
     */
    public void insert2(Record key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }

        // put record into the first slot in the heap
        heap[0] = key;

        // Swap the last val with the first val
        swap(heap, 0, n - 1);

        siftdown(0);

    }


    // Heapify contents of Heap
    /**
     * build the heap
     * 
     */
    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }


    // Put element in its correct place
    /**
     * siftdown
     * 
     * @param pos
     *            Position
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


    // Remove and return maximum value
    /**
     * remove the minimum element
     * 
     * @return minimum element
     */
    public Record removeMin() {
        if (n == 0) {
            return null;
        } // Removing from empty heap
        swap(heap, 0, --n); // Swap Minimum with last value
        siftdown(0); // Put new heap root val in correct place
        return heap[n];
    }


    // Remove and return element at specified position
    /**
     * remove element at the position
     * 
     * @param pos
     *            Position
     * @return remove element
     * 
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
     * swap the values
     * 
     * @param arr
     *            array
     * @param left
     *            left
     * @param right
     *            right
     */
    private void swap(Record[] arr, int left, int right) {
        Record temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }


    // The value at pos has been changed, restore the heap property
    /**
     * update the position
     * 
     * @param pos
     *            Position
     */
    private void update(int pos) {
        // If it is a big value, push it up
        while ((pos > 0) && ((heap[pos]).compareTo(heap[parent(
            pos)]) < 0)) {
            swap(heap, pos, parent(pos));
            pos = parent(pos);
        }
        siftdown(pos); // If it is little, push down
    }


    /**
     * heapify
     * 
     * @param arr
     *            record array
     * @param j
     *            j
     * @param i
     *            i
     */
    private void heapify(Record[] arr, int j, int i) {
        int smallest = i; // Initialize smallest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is smaller than root
        if (left < j && arr[left].getKey() < arr[smallest].getKey()) {
            smallest = left;
        }
            
        // If right child is smaller than smallest so far
        if (right < j && arr[right].getKey() < arr[smallest].getKey()) {
            smallest = right;
        }

        // If smallest is not root
        if (smallest != i) {
            Record temp = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = temp;

            // Recursively heapify the affected sub-tree
            heapify(arr, j, smallest);
        }
    }


    /**
     * heapsort
     * 
     */
    public void heapSort() {
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(heap, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {

            // Move current root to end
            Record temp = heap[0];
            heap[0] = heap[i];
            heap[i] = temp;

            // call max heapify on the reduced heap
            heapify(heap, i, 0);
        }
    }
}
