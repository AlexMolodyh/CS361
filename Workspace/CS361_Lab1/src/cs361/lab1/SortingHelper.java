package cs361.lab1;

/**
 * File Name: SortingHelper.java
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS361
 * Created: 4/6/2017
 * Assignment:
 */
public class SortingHelper
{
    int[] arrayContainer;
    int[] arrayHelper;
    private long mergeSortTime = 0;
    private long quickSortTime = 0;
    private long checkSortTime = 0;


    public SortingHelper()
    {
        arrayContainer = new int[1000000];
        arrayHelper = new int[1000000];
    }

    public SortingHelper(int size)
    {
        arrayContainer = new int[size];
        arrayHelper = new int[size];
    }

    public SortingHelper(int[] array)
    {
        arrayContainer = array;
        arrayHelper = new int[array.length];
    }

    public void mergeSort(int[] array)
    {
        arrayContainer = array;
        arrayHelper = new int[array.length];

        auxMergeSort(arrayContainer, 0, arrayContainer.length - 1);
    }

    public void randomizeArray()
    {
        for(int i = 0; i < arrayContainer.length; i++)
        {
            arrayContainer[i] = (int) (Math.random() * 1001 + 1);
        }
    }

    public int get(int i)
    {
        if(arrayContainer == null)
            return -1;
        else
            return arrayContainer[i];
    }

    public int getArrayLength()
    {
        if(arrayContainer == null)
            return -1;
        else
            return arrayContainer.length;
    }

    /**
     * mergeSort performs a Mergesort sorting algorithm on the array that was passed in to
     * the SortingHelper object.
     */
    public void mergeSort()
    {
        mergeSortTime = getMillis();//Start the mergesort clock
        auxMergeSort(arrayContainer, 0, arrayContainer.length - 1);
        mergeSortTime = getMillis() - mergeSortTime;//Store the time it took to sort the array
    }

    /**
     * auxMergeSort takes and array and splits it in to two arrays and calls on it self on each of the
     * array half's.
     *
     * @param start The start of the array.
     * @param end   Indicated the end of the array.
     */
    private void auxMergeSort(int[] array, int start, int end)
    {
        if(start < end)
        {
            //Get the midpoint of the array
            int mid = (int) Math.floor((start + end) / 2);

            auxMergeSort(array, start, mid);//Start Mergesort on left side array
            auxMergeSort(array, mid + 1, end);//Start Mergesort on right side array
            merge(array, start, mid, end);//Start merging the arrays
        }
    }

    /**
     * merge takes two arrays and merges them together and at the same time sorts
     * it in ascending order.(It doesn't take two arrays, it just uses the starting, mid, and ending points
     * to traverse through the array).
     *
     * @param start The start of the first array.
     * @param mid   The end of the first array and the beginning of the second array(mid + 1) is the beginning of
     *              the second array.
     * @param end   The end of the second array.
     */
    private void merge(int[] array, int start, int mid, int end)
    {
        //Variables to travers left and right side of array
        int k = start;
        int g = start;
        int j = mid + 1;

        //Populate arrayHelper with the section of values from arrayContainer
        for(int i = start; i <= end; i++)
        {
            arrayHelper[i] = array[i];
        }

        /*
        This section traverses through the left array and the right array and stores the lowest values
        from the arrayHelper in to the arrayContainer.
         */
        while(k <= mid && j <= end)//loop through left and right array
        {
            if(arrayHelper[k] <= arrayHelper[j])//Check if left side element is smaller than right side element
            {
                array[g] = arrayHelper[k];//Read element from arrayHelper and store it in arrayContainer
                k++;
            }
            else//Right side is smaller, so store right side element from arrayHelper in to arrayContainer
            {
                array[g] = arrayHelper[j];
                j++;
            }
            g++;
        }

        /*
         * Loop through the rest of the remaining elements and store them in the arrayContainer
         */
        while(k <= mid)
        {
            array[g] = arrayHelper[k];
            k++;
            g++;
        }
    }

    /**
     * quickSort performs a Quicksort sorting algorithm on the array that was passed in to
     * the SortingHelper object.
     */
    public void quickSort()
    {
        quickSortTime = getMillis();
        auxQuickSort(arrayContainer, 0, arrayContainer.length - 1);
        quickSortTime = getMillis() - quickSortTime;
    }

    private void auxQuickSort(int[] array, int start, int end)
    {
        if(start < end)
        {
            //After partitioning the current array, return the next pivot
            int pivot = partition(array, start, end);

            auxQuickSort(array, start, pivot);//Call Quicksort on left array
            auxQuickSort(array, pivot + 1, end);//Call Quicksort on right array
        }
    }

    private int partition(int[] array, int start, int end)
    {
        int pivot = array[start];//Pick the pivot to be the first element in array
        boolean done = false;
        int sIndex = start - 1;
        int eIndex = end + 1;

        while(!done)//Loop until low index and high index meet each other
        {
            do{//While element in start index is less than pivot increment start index
                sIndex++;
            }
            while(array[sIndex] < pivot);

            do{//While element in end index is greater than pivot decrement end index
                eIndex--;
            }
            while(array[eIndex] > pivot);

            if(sIndex >= eIndex)//If start and end are the same index then return the start index
                return eIndex;

            swap(array, sIndex, eIndex);//Swap the start and end index elements
        }
        return eIndex;//Return the midpoint
    }

    private void swap(int[] array, int index1, int index2)
    {
        int temp = array[index2];
        array[index2] = array[index1];
        array[index1] = temp;
    }

    /**
     * isSorted checks if the array is in ascending sorted order.
     * @param array The array you want to check weather it's sorted or not.
     * @return True if the array is sorted and false if it's not.
     */
    public boolean isSorted(int[] array)
    {
        checkSortTime = getMillis();
        boolean sorted = flgIsSorted(array);
        checkSortTime = getMillis() - checkSortTime;
        return sorted;
    }

    /**
     * flsIsSorted checks if the array is in ascending sorted order.
     * @param array The array you want to check weather it's sorted or not.
     * @return True if the array is sorted and false if it's not.
     */
    public boolean flgIsSorted(int[] array)
    {
        checkSortTime = getMillis();//Start the sorting check timer
        boolean sorted = auxFlgIsSorted(array, 0, array.length - 1);
        checkSortTime = getMillis() - checkSortTime;//Stop the sorting check timer
        return sorted;
    }

    private boolean auxFlgIsSorted(int[] array, int start, int end)
    {
        if(start == end)//Base case, if we've reached the end, then return true as it is sorted.
            return true;

        int mid = (start + end) / 2;

        //If the middle element is less than the (middle + 1) element then split the array and continue
        if(array[mid] <= array[mid + 1])
            return auxFlgIsSorted(array, start, mid) && auxFlgIsSorted(array, mid + 1, end);
        else//If the middle element is not less than or equal to the (middle + 1) element, then return false
            return false;
    }

    public long getMillis()
    {
        return System.nanoTime();
    }

    public double getMergeSortTime()
    {
        return mergeSortTime / 1000000.0;
    }

    public double getQuickSortTime()
    {
        return quickSortTime / 1000000.0;
    }

    public double getCheckSortTime()
    {
        return checkSortTime / 1000000.0;
    }

    /*
     * This method allocates more space for the array.
     * Precisely it double the space every time.
     */
    private void resizeArray()
    {
        int[] temp = new int[arrayContainer.length * 2];//Create new array with twice the size.
        for(int i = 0; i < arrayContainer.length; i++)//Populate new array with data from old array
        {
            temp[i] = arrayContainer[i];
        }
        arrayContainer = temp;//Set the integerList to point to the new array.
    }

    public static void main(String[] args)
    {
        int[] array = {2, 11, 7, 5, 5, 6, 4, 8, 9, 25, 3, 12, 13, 14, 15, 23, 24, 10};

        SortingHelper sortingHelper = new SortingHelper(1000000);
        sortingHelper.randomizeArray();

        sortingHelper.quickSort();

        System.out.println("Mergesort time: " + sortingHelper.getMergeSortTime());
        System.out.println("Is the array sorted? " + sortingHelper.isSorted(sortingHelper.arrayContainer));
    }
}












