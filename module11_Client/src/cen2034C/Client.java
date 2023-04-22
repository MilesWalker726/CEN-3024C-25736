package cen2034C;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client 
{
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		try
		{
			Socket s = new Socket("localhost",8080);
			DataInputStream dataInput = new DataInputStream(s.getInputStream());
			DataOutputStream dataOutput = new DataOutputStream(s.getOutputStream());
	

			System.out.print("\nEnter a Number : ");
			int userNumber = scan.nextInt();

			dataOutput.writeInt(userNumber);
			String answer = (String)dataInput.readUTF();
			System.out.println("\nIs " + userNumber + " a Prime Number? " + answer);
			dataOutput.flush();
			dataOutput.close();
			s.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		scan.close();
	}
}
