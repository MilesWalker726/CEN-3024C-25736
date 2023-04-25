package cen3024C;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class multiplyTest 
{

	@Test
	public void test() 
	{
		JunitTesting test = new JunitTesting();
		int output = test.multiply(5,2);
		assertEquals(10, output);
	}

}
