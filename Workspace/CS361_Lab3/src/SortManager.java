import java.io.IOException;
import java.util.Arrays;

/**
 * File Name: SortManager.java
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS361
 * Created: 5/30/2017
 * Assignment:
 */
public class SortManager
{
    private final int INCREMENTER_MOD = 10;
    private long radixTime = 0;
    private long binTime = 0;



    /**
     *radixSort will perform a Radixsort algorithm on the given array to sort it.
     * @param arr An integer array that will be sorted.
     */
    public int[] radixSort(int[] arr)
    {
        radixTime = getMillis();
        int n = arr.length;

        //Get the largest element in the list to know how long the number is
        int max = getLargestElement(arr, n);

        //Loop through the largest number length
        for(int i = 1; (max / i) > 0; i *= 10)
            arr = radixHelper(arr, n, i);

        radixTime = getMillis() - radixTime;

        return arr;
    }

    //gets the largest integer in the list and returns it
    private int getLargestElement(int[] arr, int size)
    {
        int largest = arr[0];

        for(int i = 0; i < size; i++)
            if(arr[i] > largest)
                largest = arr[i];

        return largest;
    }

    //This is a helper method for the Radixsort method
    private int[] radixHelper(int[] arr, int n, int div)
    {
        //Crete helper array to store temporary output elements
        int[] helperArr = new int[n];
        int[] incrementerArr = new int[INCREMENTER_MOD];
        Arrays.fill(incrementerArr, 0);

        //Store the count of occurrences in incrementArr
        for(int i = 0; i < n; i++)
            incrementerArr[ (arr[i] / div) % INCREMENTER_MOD ]++;

        //Change each index so that it contains the actual position of the current digit
        for(int i = 1; i < incrementerArr.length; i++)
            incrementerArr[i] += incrementerArr[i - 1];

        //Build the output array
        for(int i = n - 1; i >= 0; i--)
        {
            int helperIndex = incrementerArr[ (arr[i] / div) % INCREMENTER_MOD ] - 1;
            helperArr[ helperIndex ] = arr[i];
            incrementerArr[ (arr[i] / div) % INCREMENTER_MOD]--;
        }

        //Copy the helperArr in to the arr to be returned
        for(int i = 0; i < n; i++)
            arr[i] = helperArr[i];

        return arr;
    }

    public double getRadixTime(){return radixTime / 1000000.0;}

    public static void print(int[] arr)
    {
        for(int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + ", ");
    }

    public long getMillis()
    {
        return System.nanoTime();
    }

    ///////////////////////Bin Sort//////////////////////////

    /**
     *
     * @param arr
     * @param n
     * @return
     */
    public int[] binSort(int[] arr)
    {
        binTime = getMillis();
        int n = arr.length;
        int arraySize = getLargestElement(arr, n);
        int[] helperArr = new int[arraySize + 1];
        Arrays.fill(helperArr, 0);

        /*
        This increments the amount of same elements we have in to
        the index of helperArr[with value of arr[i]].
         */
        for(int i = 0; i < n; i++)
            helperArr[arr[i]]++;

        int k = 0;
        //Loop through the array size
        for(int i = 0; i <= arraySize; i++)
        {
            //loop through every index of helperArr
            for(int j = 0; j < helperArr[i]; j++)
            {
                //Set current index of arr to i index
                arr[k] = i;
                k++;
            }
        }

        binTime = getMillis() - binTime;

        return arr;
    }

    public double getBinTime() {return binTime / 1000000.0;}

    public static void main(String[] args)
    {
        int[] testArr = {1, 4, 0, 20, 2, 5, 0, 20, 99, 303, 40, 3, 11, 203, 3345, 0};
    }
}




















