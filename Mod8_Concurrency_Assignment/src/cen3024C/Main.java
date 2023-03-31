package cen3024C;

import java.util.Random;

public class Main 
{
	private static Random randomNum = new Random();
	private static final int arraySize = 200000000;
	private static int[] randomArray = new int[arraySize];
	private static long sum = 0;
	 
    private static final int threadCount = 4;
 
    private static int[] sumArray = new int[threadCount];
    private static int part = 0;
	
	static void randromArrayGenerator()
	{
		for(int i = 0; i < randomArray.length; i++)
		{
			randomArray[i] = randomNum.nextInt(10)+1;
			sum += randomArray[i];
		}
	}
	
	static class SumArray implements Runnable 
	{
		@Override
        public void run() 
		{
            int thread_part = part++;
 
            for (int i = thread_part * (arraySize / threadCount); i < (thread_part + 1) * (arraySize / threadCount); i++) 
            {
                sumArray[thread_part] += randomArray[i];
            }
        }
    }
	
	public static void main(String[] args) throws InterruptedException
	{
		long start = System.currentTimeMillis();
		
		randromArrayGenerator();
		
		for(int i = 0; i < randomArray.length; i++)
		{
			sum += randomArray[i];
		}
		System.out.println("Single Thread Sum: " + sum);
		
		long end = System.currentTimeMillis();
		long runTime = end - start;
		
		System.out.println("Single Thread Runtime: " + runTime);
		
		start = System.currentTimeMillis();
		
		Thread[] threads = new Thread[threadCount];
		 
        for (int i = 0; i < threadCount; i++) 
        {
            threads[i] = new Thread(new SumArray());
            threads[i].start();
        }
 
        for (int i = 0; i < threadCount; i++) 
        {
            threads[i].join();
        }
 
        int total_sum = 0;
        for (int i = 0; i < threadCount; i++) 
        {
            total_sum += sumArray[i];
        }
 
        System.out.println("Paralle Thread Sum: " + total_sum);
        
		end = System.currentTimeMillis();
		runTime = end - start;
		
		System.out.println("Paralle Thread Runtime: " + runTime);

	}

}
