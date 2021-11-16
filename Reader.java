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

public class Reader {
    private final int blockSize = 8192;
    private final int recordSize = 16;
    private final int recordsInBlock = 512;
    
    Reader(RandomAccessFile raf){
        try {

            int recordArrSize = 0;
            int numOfBlocks = 0;
            int blocksRead = 0;

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
                    System.out.println("ID: " + records[recordsIndex]
                        .getId() + " Key: " + records[recordsIndex]
                            .getKey() + "\n");
                    System.out.println("Index: " + recordsIndex);
                    recordsIndex++;
                }

                blocksRead++;
            }

            // The array of records are put into the MinHeap
            MinHeap heap = new MinHeap(records, records.length, 8
                * recordsInBlock);

            // array of records which will later be used for the output buffer
            Record[] recordsOutput = new Record[recordsInBlock];

            // array of records which will later be used for the input buffer
            Record[] recordsInput = new Record[recordsInBlock];

            int numRecordsIn = 0;

            int numRecordsOut = 0;

            // Heap is full and input file contains exactly 8 blocks
            // Or input file contains less than 8 blocks
            if (heap.isFull() && raf.length() == 8 * blockSize || raf
                .length() < 8 * blockSize) {
                // Perform HeapSort

                // One block at a time, move data from heap to output buffer

                // Once output buffer is full, write to run file

            }
            else {
                // Continuously do the following three steps:
                // 1. Read next block into input buffer

                // 2. Perform Replacement Selection and write to output buffer
                
                while(!heap.isEmpty()) {
                    
                     // If output record buffer is empty input the min record
                    if(numRecordsOut == 0) {
                        recordsOutput[numRecordsOut] = heap.removeMin();
                        numRecordsOut++;
                    }
                    else if (numRecordsOut == recordsInBlock) {
                        //write to file
                        
                        //empty out the output record
                        recordsOutput = new Record[recordsInBlock];
                        
                        //set counter to 0
                        numRecordsOut = 0;
                        
                    }

                    else {
                        
                        int recsInserted = 0;
                        
                        // if the input record buffer is empty
                        // read the next block of bytes and convert it into
                        // inputRecord array
                        if(numRecordsIn == 0) {
                            blocksRead++;
                            raf.seek(blocksRead*blockSize);
                            inputBuffer = readBlock(raf);
                            ByteBuffer buffer = ByteBuffer.wrap(inputBuffer);
                            recordsInput = bytesToRecords(buffer,
                                recordArrSize);
                        }
                        
                        // while the size of the output record array is not
                        // equal to 512 records
                        while(numRecordsOut != recordsInBlock) {
      
                            System.out.println("numRecordsOut " + numRecordsOut);
                            
                            System.out.println("numRecordsIn " + numRecordsIn);
                           
                            System.out.println("record input"+recordsInput[numRecordsIn]);
                            
                            System.out.println("record input"+recordsOutput[1]);
                            
                            // if the first element in the inputRecord is
                            // greater than last element outputRecord
                            if (greaterThan(recordsOutput, recordsInput, numRecordsIn, numRecordsOut -1)) {
                                System.out.println("greater");
                                heap.insert(recordsInput[recsInserted]);
                                
                                recordsOutput[numRecordsOut] = heap
                                    .removeMin();

                                numRecordsIn++;
                                numRecordsOut++;
                            }
                            else {
                                System.out.println("less");
                                heap.insert2(recordsInput[recsInserted]);
                                recordsOutput[numRecordsOut-1] = heap
                                    .removeMin();
                                
                                numRecordsIn++;
                                numRecordsOut++;
                            }
                            
                            // if the records inserted are 512 then make empty
                            // record array
                            if (recsInserted == recordsInBlock-1) {
                                recordsInput = new Record[recordsInBlock];
                                
                                numRecordsIn = 0;
                            }
                            
                            recsInserted++;
                            
                        }
                    }
                    
                }
                // 3. Once output buffer is full, write to run file
            }

            raf.close();

        }

        catch (FileNotFoundException e) {
            System.err.println("This shouldn't happen: " + e);
        }
        catch (IOException e) {
            System.err.println("Writing error: " + e);
        }
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
    public boolean greaterThan(Record[] output, Record[] input, int inputIndex, int outputIndex) {
        return input[inputIndex].compareTo(output[outputIndex]) > 0;
    }


    /**
     * Takes in a buffer and converts it into an array of records
     * 
     * @param buffer
     * @param recordArrSize
     * @return records
     */
    public Record[] bytesToRecords(
        ByteBuffer buffer,
        int recordArrSize) {
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
