package cs361.lab1;

import java.io.File;
import java.io.FileNotFoundException;
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
    private String fileAddress = ".//lab1_data.txt";
    private Long compareSum = new Long("49999995000000");
    private int[] integerList;
    Scanner fileScanner;
    File file;

    ////////////////Public Constructors///////////////////////
    public InputRoutine()
    {
        //If no getArrayLength is given for the array getArrayLength then we set it to 10100000 as the default value(which is
        //ten million one hundred thousand).
        integerList = new int[10100000];
        readInFile();
    }

    public InputRoutine(int arraySize, String fileAddress)
    {
        this.fileAddress = fileAddress;
        integerList = new int[arraySize];
        readInFile();
    }

    /**
     * This method gets the value from the index provided.
     *
     * @param i The index that you want to pull the value from.
     * @return Returns the value at the index provided. If the integer list array
     *         is null then it will return a -1.
     */
    public int get(int i)
    {
        if(integerList != null)
            return integerList[i];
        else
            return -1;
    }

    /**
     * Returns the getArrayLength of the current array.
     * @return Returns an integer representing the current getArrayLength of the array.
     */
    public int length()
    {
        if(integerList == null)
            return 0;
        else
            return integerList.length;
    }

    /**
     * getCompareSum returns the amount we want to compare our sum to.
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

    /*
     * This method allocates more space for the array.
     * Precisely it double the space every time.
     */
    private void resizeArray()
    {
        int[] temp = new int[integerList.length * 2];//Create new array with twice the size.
        for(int i = 0; i < integerList.length; i++)//Populate new array with data from old array
        {
            temp[i] = integerList[i];
        }
        integerList = temp;//Set the integerList to point to the new array.
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

        int i = 0;

            for(int j = 0; j < 1000; j++)
            {
                integerList[j] = fileScanner.nextInt();//Store the next integer from the file in to the integerList

                //Check to see if the index is at the end of the array and the file still has more integers,
                //then we reallocate more space for the array.

           /* if(i > (integerList.length - 1) && fileScanner.hasNextInt())
                resizeArray();
            i++;*/
            }

        /*while(fileScanner.hasNextInt())//Iterate through the file until there are no more integers
        {
            integerList[i] = fileScanner.nextInt();//Store the next integer from the file in to the integerList

            //Check to see if the index is at the end of the array and the file still has more integers,
            //then we reallocate more space for the array.

            if(i >= (integerList.length - 1) && fileScanner.hasNextInt())
                resizeArray();
            i++;
        }*/

        fileScanner.close();//close the scanner
    }

    public static void main(String[] args)
    {
        InputRoutine inputRoutine = new InputRoutine(1000, ".//lab1_data.txt");
        SortingHelper sortingHelper = new SortingHelper(inputRoutine.integerList);
        sortingHelper.quickSort();

        for(int i = 0; i < inputRoutine.integerList.length; i++)
        {
            System.out.println(inputRoutine.integerList[i]);
        }

        System.out.println("Quicksort time is: " + sortingHelper.getQuickSortTime());
        System.out.println("Is the array sorted? " + ((
                sortingHelper.flgIsSorted(inputRoutine.integerList)) ? " Yes" : "No"));
        System.out.println("Sorted check time is: " + sortingHelper.getCheckSortTime());


    }
}
