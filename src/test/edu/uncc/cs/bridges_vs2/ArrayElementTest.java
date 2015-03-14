package edu.uncc.cs.bridges_vs2;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.uncc.cs.bridgesV2.base.ArrayElement;
import edu.uncc.cs.bridgesV2.base.DLelement;


public class ArrayElementTest {
	static ArrayElement<String> a0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		a0 = new ArrayElement<String>("A", "a");
	}
	
	@Test
	public void testArrayElement() {
		assertNotNull(a0);
		assertEquals("a", a0.getValue());
	}

}
