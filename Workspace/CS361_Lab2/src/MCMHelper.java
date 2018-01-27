import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 4/22/2017
 * Assignment:
 */
public class MCMHelper
{
    private int[][] mArray;
    private int[] sh = {20, 3, 6, 14};
    private int[] p = {30, 4, 8, 5, 10, 25, 15};
    private int[] pQuiz = {2, 8, 4, 15, 30, 5, 10};
    private SortingHelper sortingHelper;
    private long searchTime = 0;
    private long sortSearchTime = 0;

    ArrayList<int[][]> bothMS;

    MCMHelper()
    {
        //memoizedMC(p1);
        //matrixChainOrder(arrayM);
    }

    public void printNLargest(int[] integerList, int n)
    {
        searchTime = System.nanoTime();

        for(int i = integerList.length - 1; i > integerList.length - (n + 1); i--)
            System.out.print(select(integerList, 0, integerList.length - 1, i) + ", ");

        searchTime = System.nanoTime() - searchTime;
    }

    public void printNSmallest(int[] a, int n)
    {
        if(!(n > a.length - 1))
        {
            a = minN(a, 0, n);

            for(int i = 0; i < n; i++)
            {
                System.out.print(a[i] + " ");
            }
        }
        else
            System.out.println("N must not exceed the array size!");
    }

    public void printNLargestRecur(int[] a, int n)
    {
        if(!(n > a.length - 1))
        {
            a = maxN(a, a.length - 1, a.length - (n + 1));

            for(int i = a.length - 1; i > a.length - (n + 1); i--)
            {
                System.out.print(a[i] + " ");
            }
        }
        else
            System.out.println("N must not exceed the array size!");
    }

    public int[] minN(int[] a, int s, int e)
    {
        if(s < e)
        {
            for(int i = s; i < a.length; i++)
            {
                if(a[s] > a[i])
                    swap(a, s, i);

                minN(a, s + 1, e);
            }
        }

        return a;
    }

    public int[] maxN(int[] a, int s, int e)
    {
        if(s > e)
        {
            for(int i = s; i > 0; i--)
            {
                if(a[s] < a[i])
                    swap(a, s, i);

                minN(a, s - 1, e);
            }
        }

        return a;
    }

    /**
     * select is a method part of the Quickselect algorithm. It finds the element at 'k' index and returns it.
     * If you select the last element in the list it will be the larges element in the set. If you select the
     * first element in the list, it will be the smallest item in the list.
     *
     * @param a     The array to perform the search on.
     * @param left  The starting index of the array.
     * @param right The last index of the list.
     * @param k     The k'th largest or smallest element that you want to be returned.
     * @return An integer representing the element that you searched for.
     */
    public int select(int[] a, int left, int right, int k)
    {
        //Make a copy of the array so we don't modify the original
        int[] copyArr = Arrays.copyOf(a, a.length);
        Random r = new Random();//Random object to randomize the pivot starting point
        while(right >= left)
        {
            int pivotIndex = partition(copyArr, left, right, r.nextInt(right - left + 1) + left);

            //If the pivotIndex has reached the index of k then the k'th element is in place
            // and we can return it If pivot is equal to k, then we have found out k'th largest number
            if(pivotIndex == k)
            {
                searchTime = System.nanoTime() - searchTime;
                return copyArr[pivotIndex];//Return the largest element at k index
            }
            else if(pivotIndex < k)//If pivot is less than k, then increment pivotIndex
            {
                left = pivotIndex + 1;
            }
            else//Otherwise decrement pivotIndex to move closer to the k'th element
            {
                right = pivotIndex - 1;
            }
        }
        searchTime = System.nanoTime() - searchTime;
        return 0;
    }

    private int partition(int[] a, int left, int right, int pivotIndex)
    {
        //Set the current pivot value to a random element.
        int pivotValue = a[pivotIndex];
        //We swap the current pivotValue element with the end of the list
        swap(a, pivotIndex, right);
        int storeIndex = left;//Start the search from the left index

        //loop from the left index to the right index
        for(int i = left; i < right; i++)
        {
            /*
             * If the value in index i is less than the pivotValue, then we swap the value with the storeIndex.
             * Every time that the element in index i is not less than the pivot value, storeIndex does not
             * get incremented.
             */
            if(a[i] < pivotValue)
            {
                //Swap the i'th element with the storeIndex location. This is partially sorting the list.
                swap(a, i, storeIndex);
                storeIndex++;
            }
        }
        swap(a, right, storeIndex);//Swap the storeIndex with the right index

        return storeIndex;
    }

    private void swap(int[] a, int p, int r)
    {
        int temp = a[p];
        a[p] = a[r];
        a[r] = temp;
    }

    public ArrayList<int[][]> matrixChainOrder(int[] p)
    {
        //The list that will hold the m and s arrays to be returned
        bothMS = new ArrayList<>();
        int n = p.length;
        //Create 2d arrays with the size of matrix dimensions length
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];
        n--;

        //Set the 0'th chain to 0
        for(int i = 1; i <= n; i++)
            m[i][i] = 0;

        //This loop iterates through every chain
        for(int l = 2; l <= n; l++)
        {
            //Loop through every row in the table
            for(int i = 1; i <= n - l + 1; i++)
            {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                //Loops through every possible dimension for the current cell
                for(int k = i; k < j; k++)
                {
                    //Set q to current calculation
                    int q = m[i][k] + m[k + 1][j] + (p[i - 1] * p[k] * p[j]);

                    //If the q result is the smallest from all others, store the result
                    if(q < m[i][j])
                    {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }

        bothMS.add(m);
        bothMS.add(s);

        return bothMS;
    }

    public void startMCM()
    {
        matrixChainOrder(p);
    }

    public void startMemoized()
    {
        memoizedMC(p);
    }

    public int memoizedMC(int[] p)
    {
        //Set length of matrix to be the size of the dimensions list
        int n = p.length;
        mArray = new int[n][n];

        //populate upper half of matrix with -1 values so we can track where we are in the matrix.
        for(int i = 1; i < n; i++)
        {
            for(int j = i; j < n; j++)
            {
                mArray[i][j] = -1;
            }
        }
        return lookupChain(p, 1, n - 1);
    }

    private int lookupChain(int[] p, int i, int j)
    {
        //When we hit m[1, 1] or m[2, 2]...etc. Return the element which should be a zero or a calculated value
        // that has been stored previously needed for the first chain calculation
        if(mArray[i][j] > -1)
            return mArray[i][j];

        if(i == j)//When we hit m[1, 1] or m[2, 2]...etc for the first time, set it to zero
            mArray[i][j] = 0;
        else
        {
            //Loop through every possible k dimension for the current chain cell
            for(int k = i; k < j; k++)
            {
                //Calculate the current cell and store it in q
                int q = lookupChain(p, i, k) + lookupChain(p, k + 1, j) + (p[i - 1] * p[k] * p[j]);

                //If q is smaller than the previous stored value in m[i][j] then replace it with q
                if(mArray[i][j] == -1 || q < mArray[i][j])
                    mArray[i][j] = q;
            }
        }
        return mArray[i][j];
    }

    public void printMatrix(int[][] matrix)
    {
        System.out.println();
        for(int i = 0; i < matrix.length; i++)
        {
            if(i > 0)//This makes sure we are not printing the 0 index row
            {
                for(int j = 0; j < matrix.length; j++)
                {
                    //The following string and char[] will help keep the distance between
                    // a cell with a zero in it and a cell with large values in it
                    String number = "" + matrix[i][j];
                    char[] space = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

                    //This loops through the string and replaces the s element from
                    // the string into the char[] s element index
                    for(int s = 0; s < number.length(); s++)
                        space[s] = number.charAt(s);
                    String stringSpace = new String(space);
                    if(j > 0)//This makes sure we are not printing the 0 index column
                        System.out.print(stringSpace);
                }
                System.out.println();
            }
        }
    }

    public int getTopRight(int[][] m)
    {
        return m[1][m.length - 1];
    }

    public int[][] getmArray()
    {
        return bothMS.get(0);
    }

    public int[][] getMemoizedM()
    {
        return mArray;
    }

    public long getMillis()
    {
        return System.nanoTime();
    }

    public double getSearchTime()
    {
        return searchTime / 1000000.0;
    }

    public double getSortSearchTime()
    {
        return sortSearchTime / 1000000.0;
    }

    public void sortAndPrint(int[] integerList, SortingHelper sortingHelper, int n)
    {
        sortSearchTime = System.nanoTime();

        sortingHelper.quickSort(integerList);
        for(int j = integerList.length - 1; j > integerList.length - (n + 1); j--)
            System.out.print(integerList[j] + ", ");

        sortSearchTime = System.nanoTime() - sortSearchTime;
    }

    public static void main(String[] args)
    {
        int arraySize = 10000000;
        MCMHelper mcmHelper = new MCMHelper();
        InputRoutine inputRoutine = new InputRoutine(arraySize, ".//lab1_data.txt");
        SortingHelper sortingHelper = new SortingHelper(arraySize);
        boolean done = false;
        int[] a = {2, 9, 4, 40, 3, 1, 0, 3};

        System.out.println("Using search algorithm to find the smallest 5 items.");
        mcmHelper.printNLargestRecur(a, 5);

        /*while(!done)
        {
            int n = 0;
            String sizeResponse = "";
            int[] integerList = inputRoutine.getIntegerList();

            System.out.println("\nPlease select an option from the following list");
            System.out.println("Run MCM Algorithm(D)\nRun Memoized Algorithm(M)");
            System.out.println("Run Largest N elements search. Enter (S) to search list ");
            System.out.println("To exit, enter(Q) ");
            System.out.print("Enter your choice here: ");

            Scanner scan = new Scanner(System.in);

            String response = scan.next();
            if(response.equalsIgnoreCase("S"))
            {
                System.out.println("How many items? ");
                n = scan.nextInt();

                System.out.println("What size of a list? Previous(P) size or new(N)?");
                sizeResponse = scan.next();
                if(sizeResponse.equalsIgnoreCase("N"))
                {
                    System.out.println("Enter a list size: ");
                    arraySize = scan.nextInt();
                }
                integerList = inputRoutine.getIntegerList(0, arraySize);
            }

            if(response.equalsIgnoreCase("D"))
            {
                mcmHelper.startMCM();
                System.out.println("\nTop Right: " + mcmHelper.getTopRight(mcmHelper.getmArray()));
                mcmHelper.printMatrix(mcmHelper.getmArray());
            }
            else if(response.equalsIgnoreCase("M"))
            {
                mcmHelper.startMemoized();
                mcmHelper.printMatrix(mcmHelper.getMemoizedM());
            }
            else if(response.equalsIgnoreCase("S"))
                if(n > 0)
                {
                    System.out.println();
//                    System.out.println("Using search algorithm to find the largest " + n + " items.");
                    System.out.println("Using search algorithm to find the smallest " + n + " items.");
                    mcmHelper.printNLargest(Arrays.copyOf(integerList, integerList.length), n);

                    sortingHelper.mergeSort(integerList);
                    System.out.println("\n");
                    System.out.println("The largest " + n + " items after sorting the list");

                    mcmHelper.sortAndPrint(Arrays.copyOf(integerList, integerList.length), sortingHelper, n);

                    System.out.println();
                    System.out.println("\nTime it took to search " + arraySize + " items in milliseconds: " + mcmHelper.getSearchTime());
                    System.out.println("Time it took to sort then search " + arraySize + " items in milliseconds: " + mcmHelper.getSortSearchTime());
                }
                else
                    System.out.println("You need to enter an integer for the search: ");
            else if(response.equalsIgnoreCase("Q"))
                done = true;
            else
                System.out.println("You must enter a valid decision!!");
        }*/
    }
}
























