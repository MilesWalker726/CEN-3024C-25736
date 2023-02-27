package cen3024C;

import java.util.Scanner;

public class Fibonacci 
{

	static void IterativeFunction(int N)
    {
        int num1 = 0, num2 = 1;
 
        int counter = 0;
 
        while (counter < N) 
        {
 
            System.out.print(num1 + " ");
 
            int num3 = num2 + num1;
            num1 = num2;
            num2 = num3;
            counter = counter + 1;
        }
        
        System.out.println("\n");
    }
	
	 static int RecursiveFunction(int N)
	    {

	        if (N <= 1)
	            return N;
	 
	        return RecursiveFunction(N - 1)
	            + RecursiveFunction(N - 2);
	    }
	
	public static void main(String[] args) 
	{
		
		Scanner sequenceLength = new Scanner(System.in);
		System.out.println("What sequence length would you like to test?  Please input a whole number: ");
 
		int N = sequenceLength.nextInt();
        
		System.out.println("Iterative Test: \n");
		long start = System.nanoTime();
		IterativeFunction(N);
		long end = System.nanoTime();
		
		long time = end - start;
		System.out.println("Runtime Efficiency: " + time + "\n");
		
		System.out.println("Recursive Test: \n");
		start = System.nanoTime();
        for (int i = 0; i < N; i++) 
        {
        	System.out.print(RecursiveFunction(i) + " ");
        }
        end = System.nanoTime();
        
        time = end - start;
		System.out.println("\nRuntime Efficiency: " + time + "\n");
        
        sequenceLength.close();
	}

}
