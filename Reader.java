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

public class Reader {
    private final int blockSize = 8192;
    private final int recordSize = 16;
    private final int recordsInBlock = 512;

    Reader(RandomAccessFile raf) {
        try {

            System.out.println("Input File len "+ raf.length());
            RandomAccessFile runFile = new RandomAccessFile("runFile.bin",
                "rw");
            System.out.println(" length "+ runFile.length());
            RandomAccessFile outputFile = new RandomAccessFile(
                "output.bin", "rw");
            int recordArrSize = 0;
            int numOfBlocks = 0;
            int blocksRead = 0;

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

            int recordsIndex = 0;

            int numRecordsThatSkippedTheInputBuffer = 0;
            int numRecordsEverWrittenToInputBuffer = 0;
            int numRecordsEverWrittenToOutputBuffer = 0;

            // The Input Buffer Array
            byte[] inputBuffer = new byte[blockSize];

            // The Output Buffer Array
            byte[] outputBuffer = new byte[blockSize];

            // Input first 8 or n blocks of records into an array of records
            for (int i = 0; i < numOfBlocks; i++) {

                // Read one block
                raf.seek(blockSize * i);
                inputBuffer = readBlock(raf);
                ByteBuffer buffer = ByteBuffer.wrap(inputBuffer);

                // Input records into record array
                for (int offset = 0; offset < blockSize; offset += 16) {
                    byte[] recordBytes = new byte[recordSize];
                    buffer.get(recordBytes);
                    records[recordsIndex] = new Record(recordBytes);
// System.out.println("ID: " + records[recordsIndex]
// .getId() + " Key: " + records[recordsIndex]
// .getKey() + "\n");
// System.out.println("Index: " + recordsIndex);
                    recordsIndex++;
                    numRecordsThatSkippedTheInputBuffer++;
                }

                blocksRead++;

            }
// System.out.println("Fill up Heap");
// The array of records are put into the MinHeap
            MinHeap heap = new MinHeap(records, records.length, 8
                * recordsInBlock);
// MinHeap heap = new MinHeap(records, 5, 7
// );

            // array of records which will later be used for the output buffer
            Record[] recordsOutput = new Record[recordsInBlock];

            // array of records which will later be used for the input buffer
            Record[] recordsInput = new Record[recordsInBlock];

            int writePos = 0;

            int numRecordsInInputBuffer = 0;

            int recordsOutIndex = 0;

            int indexOfInputBuffer = 0;

            int numTimesDoingTheLoop = 0;

            int numRecordsMoveInputToHeap = 0;

            int numGreaterThan = 0;

            int numLessThan = 0;

            boolean stop = false;

            boolean isFirstTimeEver = true;

            Record lastRecordReadFromHeap = null;

            // Heap is full and input file contains exactly 8 blocks
            // Or input file contains less than 8 blocks
            if (raf.length() == 8 * blockSize || raf.length() < 8
                * blockSize) {
                // Perform HeapSort

                // One block at a time, move data from heap to output buffer

                // Once output buffer is full, write to run file

            }
            else {
                while (!stop) {
                    numTimesDoingTheLoop++;
                    if (numTimesDoingTheLoop > 10000) {
// System.out.println("Oops, infinite loop");
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

// System.out.println("added new run beginning at index "
// + numRecordsEverWrittenToOutputBuffer
// + " to runs array");

                    // 2. Perform Replacement Selection and write to output
                    // buffer
// System.out.println(
// "Perform Replacement Selection and write to output buffer");
// System.out.println("About to start WHILE heapsize "
// + heap.heapsize());
                    while (!heap.isEmpty()) {

                        // System.out.println("runIndex "+runIndex);
                        // If output record buffer is empty input the min record
// numRecordsInInputBuffer != 0 &&

                        if (recordsOutIndex == recordsInBlock) {
// System.out.println("Output buffer is Full");
// System.out.println("Site F");
// convert records array to bytes
                            outputBuffer = recordsToBytes(recordsOutput);

                            // return;
                            // write to the run file
                            runFile.seek(writePos * blockSize);

                            runFile.write(outputBuffer);
                            // empty out the output record
                            recordsOutput = new Record[recordsInBlock];

                            // set counter to 0
                            recordsOutIndex = 0;
                            
                            System.out.println("RunFile Length "+runFile.length());
                            writePos++;

                        }
                        else if (numRecordsInInputBuffer == 0 && blocksRead
                            * blockSize != raf.length()) {
// System.out.println(
// "numRecordsInInputBuffer == 0 so refilling the input buffer\n");
// System.out.println("File Pointer Position "
// + blocksRead * blockSize);
// System.out.println("Num records read " +
// blocksRead * blockSize / recordSize);

                            blocksRead++;

                            raf.seek(blocksRead * blockSize);

                            inputBuffer = readBlock(raf);

                            if (inputBuffer.length == 0) {
                                return;
                            }
                            ByteBuffer buffer = ByteBuffer.wrap(
                                inputBuffer);

                            recordsInput = bytesToRecords(buffer,
                                recordsInBlock);
// System.out.println("recordsInput.length "
// + recordsInput.length);
// System.out.println("inputBuffer.length "
// + inputBuffer.length);
                            indexOfInputBuffer = 0;
                            numRecordsInInputBuffer = recordsInBlock;
                            numRecordsEverWrittenToInputBuffer +=
                                recordsInBlock;

                        }
                        else if (numRecordsInInputBuffer == 0 && blocksRead
                            * blockSize == raf.length()) {
// System.out.println(
// "20 input buffer empty and bucket empty");
// System.out.println("heapsize " + heap
// .heapsize());
                            if (heap.heapsize() < 4) {
// System.out.println("Small heapsize");
                            }
                            if (heap.isEmpty()) {
// System.out.println(
// "Heap has been cleaned out");
                                break;
                            }
                            else {
                                // System.out.println("Straight-up removing a
                                // min value from heap");
                                recordsOutput[recordsOutIndex] = heap
                                    .removeMin();
                                // System.out.println("Removed record " +
                                // recordsOutput[recordsOutIndex].getKey());

                                lastRecordReadFromHeap =
                                    recordsOutput[recordsOutIndex];

                                numRecordsEverWrittenToOutputBuffer++;
                                recordsOutIndex++;
                            }
                        }
                        else if (recordsOutIndex == 0 && isFirstTimeEver) {
                            isFirstTimeEver = false;
// System.out.println("30 output empty");
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
                            // System.out.println("Normal processing");

// System.out.println("recordsOutIndex "
// + recordsOutIndex);
// System.out.println("numRecordsInInputBuffer "
// + numRecordsInInputBuffer);
// System.out.println("record input "
// + recordsInput[indexOfInputBuffer]);
// System.out.println("record output "
// + recordsOutput[recordsOutIndex]);
// System.out.println("recsInserted "
// + indexOfInputBuffer);
// System.out.println("numRecordsThatSkippedTheInputBuffer " +
// numRecordsThatSkippedTheInputBuffer);
// System.out.println("numRecordsEverWrittenToInputBuffer " +
// numRecordsEverWrittenToInputBuffer);
// System.out.println("numRecordsEverWrittenToOutputBuffer " +
// numRecordsEverWrittenToOutputBuffer);
// System.out.println("File Length "+ raf.length());
// System.out.println("File Pointer Position "+ blocksRead * blockSize);
// System.out.println("");

                            // if the element in the inputRecord is
                            // greater than last element outputRecord

                            // System.out.println("indexOfInputBuffer"+
                            // indexOfInputBuffer);
                            if (indexOfInputBuffer == 512) {
// System.out.println(
// "numRecordsEverWrittenToInputBuffer "
// + numRecordsEverWrittenToInputBuffer);
// System.out.println(
// "numRecordsEverWrittenToOutputBuffer "
// + numRecordsEverWrittenToOutputBuffer);
//
// System.out.println(
// "numRecordsMoveInputToHeap "
// + numRecordsMoveInputToHeap);

                            }

                            if (greaterThan(recordsInput,
                                indexOfInputBuffer,
                                lastRecordReadFromHeap)) {
// System.out.println("greater");

                                heap.insert(
                                    recordsInput[indexOfInputBuffer]);
                                numGreaterThan++;

                            }
                            else {
// System.out.println("less or equal");
                                heap.insert2(
                                    recordsInput[indexOfInputBuffer]);
                                numLessThan++;

                            }

                            recordsOutput[recordsOutIndex] = heap
                                .removeMin();

                            lastRecordReadFromHeap =
                                recordsOutput[recordsOutIndex];

                            numRecordsEverWrittenToOutputBuffer++;
                            numRecordsInInputBuffer--;
                            recordsOutIndex++;

                            indexOfInputBuffer++;
                            numRecordsMoveInputToHeap++;
                        }
                        else {
// System.out.println(
// "Opps we should not get here ");

                        }

                    }

// System.out.println("Site B");

                    int totalNumberOfInputs =
                        numRecordsThatSkippedTheInputBuffer
                            + numRecordsEverWrittenToInputBuffer;

// System.out.println("totalNumberOfInputs"
// + totalNumberOfInputs);
// System.out.println(
// "numRecordsEverWrittenToOutputBuffer "
// + numRecordsEverWrittenToOutputBuffer);
// System.out.println("Num records read " + blocksRead
// * blockSize / recordSize);
// System.out.println("numGreaterThan " + numGreaterThan);
// System.out.println("numLessThan " + numLessThan);

                    // Check if we are done
                    if (numRecordsEverWrittenToOutputBuffer == totalNumberOfInputs) {
                        outputBuffer = recordsToBytes(recordsOutput);

                        // return;
                        // write to the run file
                        runFile.seek(writePos * blockSize);

                        runFile.write(outputBuffer);
// System.out.print("DONE ");
                    }

// int numberRecordsLeftInHeap = totalNumberOfInputs -
// numRecordsEverWrittenToOutputBuffer;

                    // int numStrays = heap.getHeap().length - heap.heapsize();
                    int numStrays = totalNumberOfInputs
                        - numRecordsEverWrittenToOutputBuffer;
// System.out.println("numStrays " + numStrays);

                    if (numStrays == 0) {

// System.out.println("numberRecordsLeftInHeap is 0");
                        stop = true;
                        break;
                    }
                    List<Record> straysList = new ArrayList<Record>();

                    int strayStartingIndex = heap.getHeap().length
                        - numStrays;
                    for (int i = strayStartingIndex; i < heap
                        .getHeap().length; i++) {
                        straysList.add(heap.getHeap()[i]);
                    }

                    Record[] straysArray = straysList.toArray(
                        new Record[straysList.size()]);
//
// System.out.println("straysArray.length "
// + straysArray.length);

                    MinHeap hp = new MinHeap(straysArray,
                        straysArray.length, straysArray.length + 1);
                    heap = hp;
                }
            }

            heap = null;

// byte[] inputBuffer2 = new byte[blockSize];
// inputBuffer2 = readBlock(runFile);
// ByteBuffer bb = ByteBuffer.wrap(inputBuffer2);
//
// Record[] test = bytesToRecords(bb, recordArrSize);
// System.out.println("Record" +test[0].getId());
            raf.close();
            raf = null;
            
            int indexOfCurrentRun = runs.size() - 1;
            if (indexOfCurrentRun >= 0) {
                Run currentRun = runs.get(indexOfCurrentRun);
                currentRun.setIndexOfLast(
                    numRecordsEverWrittenToOutputBuffer - 1);
            }

            // merge
            int indexOutPut = 0;
            writePos = 0;
            @SuppressWarnings("unused")
            List<Run> destroyableListOfRuns = (List<Run>)runs.clone();

            for (int i = 0; i < destroyableListOfRuns.size(); i++) {
// System.out.println("Index " + i + ", value "
// + destroyableListOfRuns.get(i).getIndexOfCurrentRec());

                ReadInRecordsForMerging(runFile, inputBuffer,
                    destroyableListOfRuns, i, recordsInput);

            }

            int numTimesWhiling = 0;

            int indexOfOut = 0;
            boolean doOneLastTime = false;
            while (!destroyableListOfRuns.isEmpty() || doOneLastTime) {
// System.out.println("numTimesWhiling " + numTimesWhiling);

                if (indexOfOut == recordsInBlock) {
 System.out.println("Output buffer is Full");
// System.out.println("Site F");
// convert records array to bytes
                    outputBuffer = recordsToBytes(recordsOutput);

                    // return;
                    // write to the run file
                    outputFile.seek(writePos * blockSize);

                    outputFile.write(outputBuffer);
                    // empty out the output record
                    recordsOutput = new Record[recordsInBlock];
                    
                    if(doOneLastTime) {
                        doOneLastTime = false; 
                    }
                    // set counter to 0
                    indexOfOut = 0;
                    indexOutPut++;
                    writePos++;
                }
                else {
                    List<Record> recs = new ArrayList<Record>();

                    for (Run r : destroyableListOfRuns) {
                        recs.add(r.getBlockOfRecord().get(0));
                    }

                    int indexOfRunWithLeastVal = findLeastValRecords(recs);

                    Run run = destroyableListOfRuns.get(
                        indexOfRunWithLeastVal);
                    recordsOutput[indexOfOut] = run.getBlockOfRecord().get(0);

                    run.getBlockOfRecord().remove(0);
                    
                    indexOfOut++;

                    if (run.getBlockOfRecord().size() == 0) {
// System.out.println("It's empty. Time to refill or quit");
// there is more stuff to fill it with
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
                            if(destroyableListOfRuns.isEmpty()) {
                                doOneLastTime = true;
                            }
                        }
                    }
                }
                numTimesWhiling++;
            }
            System.out.println("Number of times output buffer is full "+ indexOutPut);
            numOfBlocks = (int)(outputFile.length() / blockSize);
            int numOfBlocks2 = (int)(runFile.length() / blockSize);
            System.out.println("runFile length "+numOfBlocks2);
            
            int counter2 = 0;
            for(int i = 0; i < numOfBlocks2; i++) {
               
                byte[] bytes = new byte[recordSize];
                
                runFile.seek(i*blockSize);
                runFile.read(bytes);
                ByteBuffer buffer = ByteBuffer.wrap(
                    bytes);

                buffer.get(bytes);

                Record tempRecord = new Record(bytes);
                if(counter2 == 10) {
                    System.out.println("");
                    
                   counter2 =0;
                }
                System.out.print("RunFile "+tempRecord
                    .getId() +" "+ tempRecord
                    .getKey() +" "); 

               counter2++;
            }
            System.out.println("");
            System.out.println("");
            int counter = 0;
            for(int i = 0; i < numOfBlocks; i++) {
               
                byte[] bytes = new byte[recordSize];
                
                outputFile.seek(i*blockSize);
                outputFile.read(bytes);
                ByteBuffer buffer = ByteBuffer.wrap(
                    bytes);

                buffer.get(bytes);

                Record tempRecord = new Record(bytes);
                if(counter == 10) {
                    System.out.println("");
                   counter =0;
                }
                System.out.print("OutFile "+tempRecord
                    .getId() +" "+ tempRecord
                    .getKey() +" "); 

               counter++;
            }
            System.out.println("\nLen outputfile "+outputFile.length()/blockSize);
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


    public void ReadInRecordsForMerging(
        RandomAccessFile runFile,
        byte[] inputBuffer,
        List<Run> destroyableListOfRuns,
        int indexOfRun,
        Record[] recordsInput) {

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
        if (indexToReadFrom + recordsInBlock <= currRun.getIndexOfLast()) {
            numRecordsToRead = recordsInBlock;
        }
        else {
            numRecordsToRead = currRun.getIndexOfLast() - indexToReadFrom
                + 1;
        }

        int bytesToRead = numRecordsToRead * recordSize;

        byte[] tempRecBytes = new byte[bytesToRead];

        try {
            runFile.seek(indexToReadFrom * recordSize);

            runFile.read(tempRecBytes);
        }
        catch (IOException e) {
            System.err.println("Writing error: " + e);
        }

        inputBuffer = readBlock(runFile);

        ByteBuffer buffer = ByteBuffer.wrap(inputBuffer);

        recordsInput = bytesToRecords(buffer, recordsInBlock);
        LinkedList<Record> newLink = new LinkedList<Record>(Arrays.asList(
            recordsInput));
        currRun.setBlockOfRecord(newLink);
        currRun.setLastRecRead(currRun.getIndexOfLastRecRead()
            + numRecordsToRead);
// PrintRunInfo(indexOfRun, currRun);
    }

// public static void PrintRunInfo(int index, Run run) {
// System.out.println("Run " + index
// + ", Run.indexofFirst " + run.getIndexOfFirst()
// + ", Run.indexofLast " + run.getIndexOfLast()
// + ", Run.indexOfCurrentRec " + run.getIndexOfLastRecRead()
// + ", Run.Block length " + run.getBlockOfRecord().size()
// + ", Run.first.Key " + run.getBlockOfRecord().get(0).getKey());
// }


    /**
     * @return
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
     * @param output
     * @param input
     * @return
     */
    public boolean greaterThan(
        Record[] input,
        int inputIndex,
        Record last) {

        return input[inputIndex].compareTo(last) > 0;
    }


    /**
     * 
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
     * @param buffer
     * @param recordArrSize
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


    public byte[] readBlock(RandomAccessFile raf) {
        byte[] block = new byte[blockSize];

        try {
            raf.read(block);
        }
        catch (IOException e) {
            System.err.println("Writing error: " + e);
            e.printStackTrace();
        }
        return block;
    }
}
