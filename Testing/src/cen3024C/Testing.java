package cen3024C;

import java.util.Scanner;

public class Testing 
{

	public static void main(String[] args) 
	{
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Enter First Number: ");
		int num1 = userInput.nextInt();
		
		System.out.println("Enter Second Number: ");
		int num2 = userInput.nextInt();
		
		int sum = num1 * num2;
		
		System.out.println("The sum of " + num1 + " multiplied by " + num2 + " equals " + sum + ".");
		
		userInput.close();
	}

}
