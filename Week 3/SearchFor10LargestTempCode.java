 public int[] getNLargest(int n, int s, int e, int[] a)
    {
        int[] largestN = new int[n];
        int mid = (e + s) / 2;

        if((e - s) <= n)
        {
            largestN = copy(largestN, s, e, a);
            return sort(largestN);
        }

        int[] l = getNLargest(n, s, mid, a);
        int[] l2 = getNLargest(n, mid + 1, e, a);

        l = sort(l);
        l2 = sort(l2);

        return merge(l, l2);
    }

    private int[] sort(int[] l)
    {
        sortingHelper = new SortingHelper(l.length);
        sortingHelper.quickSort(l);
        return l;
    }

    private int[] merge(int[] l, int[] l2)
    {
        int n = l.length - 1;
        int i = n, j = n;

        System.out.print("\nThe l before merging array is: ");
        for(int k = 0; k < n; k++)
        {
            System.out.print(l[k] + " ");
        }

        System.out.print("\nThe l2 before merging arra is: ");
        for(int k = 0; k < n; k++)
            System.out.print(l2[k] + " ");

        while(i >= 0 && j >= 0)
        {
            if(l2[j] > l[i])
            {
                int temp = l[i];
                l[i] = l2[j];
                l2[j] = temp;
                i--;
            }
            else if(j > 1 && l2[j] > l2[j - 1])
            {
                j--;
            }
            else
            {
                i--;
                j--;
            }
        }

        System.out.print("\nThe l array after merging is: ");
        for(int k = 0; k < n; k++)
        {
            System.out.print(l[k] + " ");
        }

        return l;
    }

    private int[] copy(int[] l, int s, int e, int[] a)
    {
        int tempI = s;

        System.out.print("\nThe a before copying array is: ");
        for(int k = s; k < e; k++)
        {
            System.out.print(a[k] + " ");
        }

        for(int i = 0; i < (e - s); i++)
        {
            l[i] = a[tempI];
            tempI++;
        }

        System.out.print("\nThe l after copying array is: ");
        for(int k = 0; k < l.length; k++)
        {
            System.out.print(l[k] + " ");
        }

        return l;
    }
	
	
	
	
	 private int lookupChain(int[] p, int i, int j, int c)
    {
        String s = "";
        for(int l = 0; l < c; l++)
            s += "----=";
        System.out.print("\n" + s + "Call:" + c + "  (I: " + i + ")  (J: " + j + ")");

        //When we hit m[1, 1] or m[2, 2]...etc. Return the element which is a zero needed for the first
        //chain calculation
        if(mArray[i][j] > -1)
        {
            System.out.print("\n" + s + "Returning: " + mArray[i][j]);
            return mArray[i][j];
        }

        if(i == j)
        {
            System.out.print("\n" + s + " Setting m[" + i + "][" + j + "] to 0 ");
            mArray[i][j] = 0;
        }
        else
        {
            c++;
            for(int k = i; k < j; k++)
            {
                System.out.print(" (K: " + k + ")");
                int q = lookupChain(p, i, k, c) + lookupChain(p, k + 1, j, c) + (p[i - 1] * p[k] * p[j]);
                System.out.print("\n" + s + "Setting m[" + i + "][" + j + "] to: " + mArray[i][k] + " + " + mArray[k + 1][j] + " + " + p[i - 1] + " * " +  p[k] + " * " + p[j]);
                if(mArray[i][j] == -1 || q < mArray[i][j])
                {
                    System.out.print("\n" + s + "Setting m[" + i + "][" + j + "] to q: " + q + " With K = " + k);
                    mArray[i][j] = q;
                }
            }
        }
        return mArray[i][j];
    }