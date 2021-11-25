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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;

/**
 * The class containing reader method for processing data
 *
 * @author Jonathan Reynosa Emilio Rivera
 * @version 11.24.2021
 */
public class Reader {
    private final int blockSize = 8192;
    private final int recordSize = 16;
    private final int recordsInBlock = 512;

    private int blocksRead = 0;
    private int numRecordsThatSkippedTheInputBuffer = 0;

    /**
     * Main Reader method
     *
     * @param raf File
     * @param name File name
     */
    @SuppressWarnings({ "unchecked", "resource" })
    Reader(RandomAccessFile raf, String name) {
        try {
            RandomAccessFile runFile = new RandomAccessFile("runFile.bin",
                "rw");
            RandomAccessFile outputFile = new RandomAccessFile(
                name, "rw");
            
            outputFile.setLength(raf.length());

            int recordArrSize = 0;
            int numOfBlocks = 0;

            int numRecordsEverWrittenToInputBuffer = 0;
            int numRecordsEverWrittenToOutputBuffer = 0;

            int writePos = 0;

            int numRecordsInInputBuffer = 0;

            int recordsOutIndex = 0;

            int indexOfInputBuffer = 0;

            int numTimesDoingTheLoop = 0;

            boolean stop = false;

            boolean isFirstTimeEver = true;

            Record lastRecordReadFromHeap = null;

            ArrayList<Run> runs = new ArrayList<Run>();

            // Input file has 8 or less blocks of data
            if (raf.length() <= 8 * blockSize) {
                numOfBlocks = (int)(raf.length() / blockSize);
                recordArrSize = numOfBlocks * recordsInBlock;
            }
            else {
                numOfBlocks = 8;
                recordArrSize = numOfBlocks * recordsInBlock;
            }

            // Records array with size to hold 8 blocks of data
            Record[] records = new Record[recordArrSize];

            // The Input Buffer Array
            byte[] inputBuffer = new byte[blockSize];

            // The Output Buffer Array
            byte[] outputBuffer = new byte[blockSize];

            // Input first 8 or n blocks of records into an array of records
            MinHeap heap = fillHeap(raf, inputBuffer, records,
                numOfBlocks);

            // array of records which will later be used for the output buffer
            Record[] recordsOutput = new Record[recordsInBlock];

            // array of records which will later be used for the input buffer
            Record[] recordsInput = new Record[recordsInBlock];

            // Heap is full and input file contains exactly 8 blocks
            // Or input file contains less than 8 blocks
            if (raf.length() == 8 * blockSize || raf.length() < 8
                * blockSize) {
                // Perform HeapSort
                heap.buildheap();
                
                // One block at a time, move data from heap to output buffer
                // and write to run file
                for (int i = 0; i < numOfBlocks; i++) {

                    // Read records from heap to the output buffer
                    // while buffer is less than 512 records
                    int outBufferSize = 0;
                    while (outBufferSize < recordsInBlock) {
                        recordsOutput[outBufferSize] = heap.removeMin();
                        outBufferSize++;
                    }

                    // Write to run file
                    writeOutputToFile(outputBuffer, recordsOutput, outputFile,
                        i);
                    recordsOutput = new Record[recordsInBlock];
                }

            }
            else {
                while (!stop) {
                    numTimesDoingTheLoop++;
                    if (numTimesDoingTheLoop > 10000) {
                        return;
                    }

                    // Continuously do the following three steps:
                    // 1. Read next block into input buffer

                    int indexOfCurrentRun = runs.size() - 1;

                    if (indexOfCurrentRun >= 0) {
                        Run currentRun = runs.get(indexOfCurrentRun);
                        currentRun.setIndexOfLast(
                            numRecordsEverWrittenToOutputBuffer - 1);
                    }

                    Run newRun = new Run(
                        numRecordsEverWrittenToOutputBuffer);

                    runs.add(newRun);

                    while (!heap.isEmpty()) {

                    	// Output buffer is full
                        if (recordsOutIndex == recordsInBlock) {

                            writeOutputToFile(outputBuffer, recordsOutput,
                                runFile, writePos);

                            recordsOutIndex = 0;

                            writePos++;

                        }
                        // Input buffer is empty
                        else if (numRecordsInInputBuffer == 0 && blocksRead
                            * blockSize != raf.length()) {
                            recordsInput = refillInputBuffer(raf,
                                blocksRead, inputBuffer);
                            blocksRead++;

                            indexOfInputBuffer = 0;
                            numRecordsInInputBuffer = recordsInBlock;
                            numRecordsEverWrittenToInputBuffer +=
                                recordsInBlock;

                        }
                        else if (numRecordsInInputBuffer == 0 && blocksRead
                            * blockSize == raf.length()) {

                            if (heap.isEmpty()) {
                                break;
                            }
                            else {

                                recordsOutput[recordsOutIndex] = heap
                                    .removeMin();

                                lastRecordReadFromHeap =
                                    recordsOutput[recordsOutIndex];

                                numRecordsEverWrittenToOutputBuffer++;
                                recordsOutIndex++;
                            }
                        }
                        // First time ever reading record in
                        else if (recordsOutIndex == 0 && isFirstTimeEver) {
                            isFirstTimeEver = false;

                            recordsOutput[recordsOutIndex] = heap
                                .removeMin();

                            lastRecordReadFromHeap =
                                recordsOutput[recordsOutIndex];
                            recordsOutIndex++;
                            numRecordsEverWrittenToOutputBuffer++;

                        }

                        else if ((recordsOutIndex != 0
                            && recordsOutIndex != recordsInBlock)
                            || (recordsOutIndex == 0
                                && !isFirstTimeEver)) {

                            // if the element in the inputRecord is
                            // greater than last element outputRecord
                            if (isCurrentGreaterThanLast(recordsInput,
                                indexOfInputBuffer,
                                lastRecordReadFromHeap)) {
                                heap.insert(
                                    recordsInput[indexOfInputBuffer]);

                            }
                            else {
                                heap.insert2(
                                    recordsInput[indexOfInputBuffer]);

                            }

                            recordsOutput[recordsOutIndex] = heap
                                .removeMin();

                            lastRecordReadFromHeap =
                                recordsOutput[recordsOutIndex];

                            numRecordsEverWrittenToOutputBuffer++;
                            numRecordsInInputBuffer--;
                            recordsOutIndex++;
                            indexOfInputBuffer++;

                        }

                    }

                    int totalNumberOfInputs =
                        numRecordsThatSkippedTheInputBuffer
                            + numRecordsEverWrittenToInputBuffer;

                    // Check if we are done
                    if (numRecordsEverWrittenToOutputBuffer == totalNumberOfInputs) {

                        writeOutputToFile(outputBuffer, recordsOutput,
                            runFile, writePos);

                    }

                    int numStrays = totalNumberOfInputs
                        - numRecordsEverWrittenToOutputBuffer;

                    if (numStrays == 0) {

                        stop = true;
                        break;
                    }

                    List<Record> straysList = addStrayRecordsToList(heap,
                        numStrays);

                    Record[] straysArray = straysList.toArray(
                        new Record[straysList.size()]);

                    MinHeap hp = new MinHeap(straysArray,
                        straysArray.length, straysArray.length + 1);

                    heap = hp;

                    heap.buildheap();

                    isFirstTimeEver = true;
                }
            }

            heap = null;

            raf.close();
            raf = null;

            int indexOfCurrentRun = runs.size() - 1;

            if (indexOfCurrentRun >= 0) {
                Run currentRun = runs.get(indexOfCurrentRun);
                currentRun.setIndexOfLast(
                    numRecordsEverWrittenToOutputBuffer - 1);
            }

            // merge
            writePos = 0;
            recordsOutput = new Record[recordsInBlock];
            recordsInput = new Record[recordsInBlock];

            List<Run> destroyableListOfRuns = (List<Run>)runs.clone();

            for (int i = 0; i < destroyableListOfRuns.size(); i++) {

                ReadInRecordsForMerging(runFile, inputBuffer,
                    destroyableListOfRuns, i, recordsInput);

            }

            int indexOfOut = 0;

            boolean doOneLastTime = false;
            while (!destroyableListOfRuns.isEmpty() || doOneLastTime) {

                if (indexOfOut == recordsInBlock) {

                    writeOutputToFile(outputBuffer, recordsOutput,
                        outputFile, writePos);

                    if (doOneLastTime) {
                        doOneLastTime = false;
                    }

                    indexOfOut = 0;

                    writePos++;
                }
                else {
                    List<Record> arrayOfSmallestRecInEachRun =
                        makeArrayOfSmallestRecs(destroyableListOfRuns);

                    int indexOfRunWithLeastVal = findLeastValRecords(
                        arrayOfSmallestRecInEachRun);

                    Run run = destroyableListOfRuns.get(
                        indexOfRunWithLeastVal);
                    recordsOutput[indexOfOut] = run.getBlockOfRecord().get(
                        0);

                    run.getBlockOfRecord().remove(0);

                    indexOfOut++;

                    if (run.getBlockOfRecord().size() == 0) {

                        int numRecordsRemainingInThisRun = run
                            .getIndexOfLast() - run
                                .getIndexOfLastRecRead();

                        if (numRecordsRemainingInThisRun > 0) {
                            ReadInRecordsForMerging(runFile, inputBuffer,
                                destroyableListOfRuns,
                                indexOfRunWithLeastVal, recordsInput);
                        }
                        else {
                            destroyableListOfRuns.remove(run);
                            if (destroyableListOfRuns.isEmpty()) {

                                doOneLastTime = true;
                            }
                        }
                    }
                }

            }
            
            printFirstRecordInBlock(outputFile);
           

            runFile.close();
            outputFile.close();

        }

        catch (

        FileNotFoundException e) {
            System.err.println("This shouldn't happen: " + e);
        }
        catch (IOException e) {
            System.err.println("Writing error: " + e);
        }
    }


    /**
     * Add stray records to a list
     * 
     * @param heap MinHeap with stray records
     * @param numStrays Number of strays
     * @return List of Strays
     */
    public List<Record> addStrayRecordsToList(
        MinHeap heap,
        int numStrays) {
        List<Record> straysList = new ArrayList<Record>();

        int strayStartingIndex = heap.getHeap().length - numStrays;
        for (int i = strayStartingIndex; i < heap.getHeap().length; i++) {
            straysList.add(heap.getHeap()[i]);
        }
        return straysList;
    }


    /**
     * Return record list of smallest records
     * 
     * @param destroyableListOfRuns Runs with records
     * @return List of smallest records
     */
    public List<Record> makeArrayOfSmallestRecs(
        List<Run> destroyableListOfRuns) {
        List<Record> arrayOfSmallestRecInEachRun = new ArrayList<Record>();
        for (Run r : destroyableListOfRuns) {
            arrayOfSmallestRecInEachRun.add(r.getBlockOfRecord().get(0));
        }
        return arrayOfSmallestRecInEachRun;
    }


    /**
     * Print records in the blocks
     * 
     * @param file File read from
     * @throws IOException
     */
    public void printFirstRecordInBlock(RandomAccessFile file)
        throws IOException {
        int numOfBlocks = (int)(file.length() / blockSize);
        int counter = 0;
        for (int i = 0; i < numOfBlocks; i++) {

            byte[] bytes = new byte[recordSize];

            file.seek(i * blockSize);
            file.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);

            buffer.get(bytes);

            Record tempRecord = new Record(bytes);
            
            if (counter == 5) {
                System.out.println("");
                counter = 0;
            }
            
            long id = tempRecord.getId();
            double key = tempRecord.getKey();
            
            if (counter == 4 || i == numOfBlocks - 1) {
            	System.out.print(id + " " + key);
            }
            else {
            	System.out.print(id + " " + key + " ");
            }

            counter++;
        }
    }
    
    public void printAllRecordsInBlock(RandomAccessFile file)
        throws IOException {

    }


    /**
     * Fill heap with initial data
     * 
     * @param raf File
     * @param inputBuffer Input buffer
     * @param records Records array reading from
     * @param numOfBlocks Blocks of data
     * @return Filled heap
     * @throws IOException
     */
    public MinHeap fillHeap(
        RandomAccessFile raf,
        byte[] inputBuffer,
        Record[] records,
        int numOfBlocks)
        throws IOException {
        int recordsIndex = 0;

        for (int i = 0; i < numOfBlocks; i++) {

            // Read one block
            inputBuffer = readBlock2(raf, blockSize * i);
            ByteBuffer buffer = ByteBuffer.wrap(inputBuffer);

            // Input records into record array
            for (int offset = 0; offset < blockSize; offset += 16) {
                byte[] recordBytes = new byte[recordSize];
                buffer.get(recordBytes);
                records[recordsIndex] = new Record(recordBytes);
                recordsIndex++;
                numRecordsThatSkippedTheInputBuffer++;
            }

            blocksRead++;

        }
        MinHeap heap = new MinHeap(records, records.length, 8
            * recordsInBlock);
        return heap;
    }


    /**
     * Write to output file
     * 
     * @param outputBuffer Output Buffer
     * @param recordsOutput Records output buffer
     * @param outputFile File
     * @param writePos Position in File
     * @throws IOException
     */
    public void writeOutputToFile(
        byte[] outputBuffer,
        Record[] recordsOutput,
        RandomAccessFile outputFile,
        int writePos)
        throws IOException {

        // convert records array to bytes
        outputBuffer = recordsToBytes(recordsOutput);

        // return;
        // write to the run file

        outputFile.seek(writePos * blockSize);

        outputFile.write(outputBuffer);

        // empty out the output record
        recordsOutput = new Record[recordsInBlock];

    }


    /**
     * Refill Input Buffer
     * 
     * @param raf File
     * @param blocksRead Blocks read from file
     * @param inputBuffer Input Buffer
     * @return Record array for buffer
     * @throws IOException
     */
    public Record[] refillInputBuffer(
        RandomAccessFile raf,
        int blocksRead,
        byte[] inputBuffer)
        throws IOException {

        raf.seek(blocksRead * blockSize);

        inputBuffer = readBlock(raf);

        ByteBuffer buffer = ByteBuffer.wrap(inputBuffer);

        Record[] temp = bytesToRecords(buffer, recordsInBlock);

        return temp;
    }


    /**
     * Read in records to merge runs
     * 
     * @param runFile File containing runs
     * @param inputBuffer Input buffer
     * @param destroyableListOfRuns List of runs considered
     * @param indexOfRun Current Run
     * @param recordsInput Input Buffer of Records
     * @throws IOException
     */
    public void ReadInRecordsForMerging(
        RandomAccessFile runFile,
        byte[] inputBuffer,
        List<Run> destroyableListOfRuns,
        int indexOfRun,
        Record[] recordsInput)
        throws IOException {

        Run currRun = destroyableListOfRuns.get(indexOfRun);

        int indexToReadFrom;
        if (currRun.getHasRead()) {
            indexToReadFrom = currRun.getIndexOfLastRecRead() + 1;
        }
        else {
            indexToReadFrom = currRun.getIndexOfFirst();
            currRun.setHasRead();
        }

        int numRecordsToRead;
        if (indexToReadFrom + recordsInBlock - 1 <= currRun
            .getIndexOfLast()) {
            numRecordsToRead = recordsInBlock;
        }
        else {
            numRecordsToRead = currRun.getIndexOfLast() - indexToReadFrom
                + 1;
        }

        int bytesToRead = numRecordsToRead * recordSize;

        byte[] tempRecBytes = new byte[bytesToRead];

        runFile.seek(indexToReadFrom * recordSize);

        long val = runFile.getFilePointer();

        runFile.read(tempRecBytes);

        if (indexToReadFrom * recordSize != runFile.length()) {
            inputBuffer = readBlock2(runFile, val);
            ByteBuffer buffer = ByteBuffer.wrap(inputBuffer);
            recordsInput = bytesToRecords(buffer, recordsInBlock);
        }

        LinkedList<Record> newLink = new LinkedList<Record>(Arrays.asList(
            recordsInput));
        currRun.setBlockOfRecord(newLink);
        currRun.setLastRecRead(currRun.getIndexOfLastRecRead()
            + numRecordsToRead);

    }


    /**
     * Find least record values in a given list
     * 
     * @param recs List considered
     * 
     * @return least record values
     */
    public int findLeastValRecords(List<Record> recs) {
        int indexOfSmallest = 0;
        for (int i = 1; i < recs.size(); i++) {
            if (recs.get(i).compareTo(recs.get(indexOfSmallest)) < 0) {
                indexOfSmallest = i;
            }
        }

        return indexOfSmallest;
    }


    /**
     * compare the first record in the input array with the
     * last record in the output array. If input array is greater than
     * output array return true.
     * 
     * @param inputIndex Index in buffer
     * @param input Record array buffer
     * @param last Last record
     * @return
     */
    public boolean isCurrentGreaterThanLast(
        Record[] input,
        int inputIndex,
        Record last) {

        return input[inputIndex].compareTo(last) > 0;
    }


    /**
     * Convert Records array to byte array 
     * 
     * @param records Records to convert
     * @return byte array of records
     */
    public byte[] recordsToBytes(Record[] records) {
        ByteBuffer buffer = ByteBuffer.allocate(blockSize);

        for (int i = 0; i < records.length; i++) {
            buffer.putLong(records[i].getId());
            buffer.putDouble(records[i].getKey());

        }
        byte[] outputBuffer = buffer.array();

        return outputBuffer;
    }


    /**
     * Takes in a buffer and converts it into an array of records
     * 
     * @param buffer buffer with data
     * @param recordArrSize size of data
     * @return records
     */
    public Record[] bytesToRecords(ByteBuffer buffer, int recordArrSize) {
        int recordsIndex = 0;
        Record[] records = new Record[recordArrSize];
        
        for (int offset = 0; offset < blockSize; offset += 16) {
            byte[] recordBytes = new byte[recordSize];
            buffer.get(recordBytes);
            records[recordsIndex] = new Record(recordBytes);
            recordsIndex++;
        }
        return records;
    }


    /**
     * Read block from file
     * 
     * @param raf file
     * @return byte array
     * @throws IOException
     */
    public byte[] readBlock(RandomAccessFile raf) throws IOException {
        byte[] block = new byte[blockSize];

        raf.read(block);

        return block;
    }


    /**
     * Read block starting at pos
     * 
     * @param raf file
     * @param pos file position
     * @return byte array
     * @throws IOException
     */
    public byte[] readBlock2(RandomAccessFile raf, long pos)
        throws IOException {

        byte[] block = new byte[blockSize];
        raf.seek(pos);
        raf.read(block);

        return block;
    }
}
