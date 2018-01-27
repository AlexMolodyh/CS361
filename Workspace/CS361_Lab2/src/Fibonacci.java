/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 4/24/2017
 * Assignment:
 */
public class Fibonacci
{
    private int n = 0;

    public Fibonacci(int n)
    {
        this.n = n;
    }

    public long fibRecur(int n)
    {
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return fibRecur(n - 1) + fibRecur(n - 2);
    }

    public long fib(int n) {
        return fibNumber(0, 1, n);
    }

    private long fibNumber(long m, long e, int n) {
        if(n <= 0)
            return m;

        return fibNumber(e, m + e, n - 1);
    }
}
