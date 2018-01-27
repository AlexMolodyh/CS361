import java.util.Random;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 4/24/2017
 * Assignment:
 */
public class TestClass
{
    public static void main(String[] args)
    {
        int n = 100;
        int counter = 0;

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < Math.floor((double) i/2); j++)
            {
                counter++;
            }
        }

        System.out.println("The counter is: " + counter);

       /* int N = 75;

        Fibonacci fibonacci = new Fibonacci(N);

        System.out.println("The Fibonacci of " + N + " is: " + fibonacci.fib(N));*/
/*        Random random = new Random();

        System.out.print(random.nextInt(29 - 0 + 1) + 0);*/
    }
}
