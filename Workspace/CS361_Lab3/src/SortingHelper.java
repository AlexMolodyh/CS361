import com.sun.javafx.collections.SortHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 5/31/2017
 * Assignment:
 */
public class SortingHelper
{
    private static long checkSortTime = 0;


    /**
     * isSorted checks if the array is in ascending sorted order.
     * @param array The array you want to check weather it's sorted or not.
     * @return True if the array is sorted and false if it's not.
     */
    public static boolean isSorted(int[] array)
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
    private static boolean flgIsSorted(int[] array)
    {
        checkSortTime = getMillis();//Start the sorting check timer
        boolean sorted = auxFlgIsSorted(array, 0, array.length - 1);
        checkSortTime = getMillis() - checkSortTime;//Stop the sorting check timer
        return sorted;
    }

    private static boolean auxFlgIsSorted(int[] array, int start, int end)
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

    public static long getMillis()
    {
        return System.nanoTime();
    }


    public static void main(String[] args)
    {
        int arraySize = 10000000;
        InputRoutine inputRoutine = new InputRoutine(arraySize);
        QuickMergeSort qmSort = new QuickMergeSort();
        SortManager sm = new SortManager();
        FA dfa;

        State q0 = State.makeState(0, true);
        State q1 = State.makeState(1);

        ArrayList<State> Q = new ArrayList<>();
        Q.add(q0);
        Q.add(q1);

        ArrayList<State> F = new ArrayList<>();
        F.add(q0);

        String language = "00001001";

        State[][] states = {{q0, q1}, {q0, q1}};

        int[] largeArray = inputRoutine.getIntegerList();
        boolean done = false;

        while(!done)
        {
            String sizeResponse = "";
            int[] integerList = inputRoutine.getIntegerList();

            System.out.println("\nPlease select an option from the following list");
            System.out.println("Run RadixSort(R)\nRun BinSort(B)");
            System.out.println("Set array size(S)");
            System.out.println("Run DFA(D)");
            System.out.println("To exit, enter(Q) ");
            System.out.print("Enter your choice here: ");

            Scanner scan = new Scanner(System.in);
            String response = scan.next();

            //This will change the array size
            if(response.equalsIgnoreCase("S"))
            {
                try {arraySize = scan.nextInt();}
                catch(Exception e){};

                System.out.println("Array size is now: " + arraySize);
                inputRoutine.setArraySize(arraySize);
                inputRoutine.reReadFile();
                largeArray = inputRoutine.getIntegerList();

                System.out.println("New size is: " + largeArray.length);
            }

            //This will run the Radixsort
            if(response.equalsIgnoreCase("R"))
            {
                int[] copyList = Arrays.copyOf(integerList, integerList.length);
                integerList = sm.radixSort(integerList);

                System.out.println("Is array sorted after RadixSort? " + ((isSorted(integerList) ? " Yes" : " No")));
                System.out.println("Time it took to sort: " + sm.getRadixTime() + "\n");

                //Make a copy of the original array to be sorted in the Mergesort
                copyList = qmSort.mergeSort(copyList);

                System.out.println("Is array sorted after MergeSort? " + ((isSorted(copyList) ? " Yes" : " No")));
                System.out.println("Time it took to sort: " + qmSort.getMergeSortTime());
            }
            else if(response.equalsIgnoreCase("B"))//This will run the Binsort
            {
                int[] copyList = Arrays.copyOf(integerList, integerList.length);
                integerList = sm.binSort(integerList);

                System.out.println("Is array sorted after BinSort? " + ((isSorted(integerList) ? " Yes" : " No")));
                System.out.println("Time it took to sort: " + sm.getBinTime() + "\n");

                //Make a copy of the original array to be sorted in the Mergesort
                copyList = qmSort.mergeSort(copyList);

                System.out.println("Is array sorted after MergeSort? " + ((isSorted(copyList) ? " Yes" : " No")));
                System.out.println("Time it took to sort: " + qmSort.getMergeSortTime());
            }
            else if(response.equalsIgnoreCase("D"))
            {
                System.out.print("Enter language String: ");
                language = scan.next();
                dfa = new FA(Q, language, states, q0, F);

                System.out.println("Does DFA accept string " + language + ": " +
                        ((dfa.isValidString(language, true) ? " Yes" : " No")));
            }
            else if(response.equalsIgnoreCase("Q"))//Quit the program
                done = true;
            else
                System.out.println("You must enter a valid decision!!");
        }
    }
}





















