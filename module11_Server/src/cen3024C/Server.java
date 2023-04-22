package cen3024C;

import java.io.*;
import java.net.*;

public class Server 
{
	public static String isPrime(int num) 
	{
		if(num<2) 
		{
			return "No";
		}
		
		int i=2;
		
		while(i<num) 
		{
			if(num%i==0) 
			{
				return "No";
			}
			i++;
		}
		
		return "Yes";
	}
		
	public static void main(String[] args)
	{
		try
		{
			ServerSocket ss=new ServerSocket(8080);

			Socket s=ss.accept();
			DataInputStream dataInput=new DataInputStream(s.getInputStream());
			DataOutputStream dataOutput=new DataOutputStream(s.getOutputStream());
	
			int userNumber = (int)dataInput.readInt();
			dataOutput.writeUTF(isPrime(userNumber));
			dataOutput.flush();
	
			dataOutput.close();
			ss.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
