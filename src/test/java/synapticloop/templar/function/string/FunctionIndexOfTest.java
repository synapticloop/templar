package synapticloop.templar.function.string;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;

public class FunctionIndexOfTest {
	private FunctionIndexOf functionIndexOf;

	@Before
	public void setup() {
		functionIndexOf = new FunctionIndexOf();
	}

	@Test
	public void testIndexOf() throws FunctionException {
		assertEquals(1, functionIndexOf.evaluate("", new Object[] {"\"this\"", "\"his\""}, null));
	}
}
