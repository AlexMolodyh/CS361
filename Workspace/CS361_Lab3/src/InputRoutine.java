import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Alexander Molodyh
 * Class: CS361
 * Date: 4/6/2017
 * Description: InputRoutine is an object that reads in a file of integers and populates an array
 * with those integers. Then it iterates through the array and sums up the total or the integers.
 */
public class InputRoutine
{
    private String fileAddress = ".//lab3_data.txt";
    private Long compareSum = new Long("49999995000000");
    private int[] integerList;
    private int[] copyList;
    private int arraySize = 0;
    Scanner fileScanner;
    File file;

    ////////////////Public Constructors///////////////////////
    public InputRoutine()
    {
        //If no getArrayLength is given for the array getArrayLength then we set it to 10100000 as the default value(which is
        //ten million one hundred thousand).
        integerList = new int[10000000];
        copyList = Arrays.copyOf(integerList, integerList.length);
        this.arraySize = integerList.length;
        readInFile();
    }

    public InputRoutine(int arraySize)
    {
        this.arraySize = arraySize;
        this.fileAddress = fileAddress;
        integerList = new int[arraySize];
        copyList = new int[integerList.length];
        readInFile();
    }

    /**
     * getCompareSum returns the amount we want to compare our sum to.
     *
     * @return A Long object that represents the amount our list should sum up to.
     */
    public Long getCompareSum()
    {
        return compareSum;
    }

    /**
     * getSum sums up the total from the integerList.
     *
     * @return Returns an integer representing the sum of all integers from the integerList.
     */
    public long getSum()
    {
        long sum = 0;

        for(int i = 0; i < integerList.length; i++)
        {
            sum = sum + integerList[i];
        }

        return sum;
    }

    public int[] getIntegerList()
    {
        return Arrays.copyOf(integerList, integerList.length);
    }

    public int[] getIntegerList(int start, int end)
    {
        return Arrays.copyOfRange(integerList, start, end);
    }

    public void setArraySize(int arraySize)
    {
        this.arraySize = arraySize;
    }

    public void reReadFile()
    {
        integerList = new int[arraySize];
        readInFile();
    }

    /*
     * readFile opens a file and reads in all of the integers from the given file.
     */
    private void readInFile()
    {
        try
        {
            file = new File(fileAddress);//Create new file from the given file name
            fileScanner = new Scanner(file);//Create a file scanner to scan for all the integers
        }
        catch(FileNotFoundException e) //Throws a FileNotFountException if the given file name doesn't exist
        {
            System.out.println("The file " + fileAddress + " could not be found");
        }

        for(int i = 0; i < arraySize; i++)
        {
            try
            {
                integerList[i] = fileScanner.nextInt();//Store the next integer from the file in to the integerList
            }
            catch(NoSuchElementException e) {}

            i++;
        }

        fileScanner.close();//close the scanner
    }
}

















