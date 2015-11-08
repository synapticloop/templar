package synapticloop.templar.function;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;

public class FunctionInstanceOfTest {
	private FunctionInstanceOf functionInstanceOf;

	@Before
	public void setup() {
		functionInstanceOf = new FunctionInstanceOf();
	}

	@Test
	public void testInstanceOfString() throws FunctionException {
		assertTrue((Boolean)functionInstanceOf.evaluate("", new Object[] {"\"this is a string\"", "String"}, null));
		assertTrue((Boolean)functionInstanceOf.evaluate("", new Object[] {"\"this is a string\"", "java.lang.String"}, null));
		assertFalse((Boolean)functionInstanceOf.evaluate("", new Object[] {"\"this is a string\"", "java.lang.Long"}, null));
	}

	@Test(expected = FunctionException.class)
	public void testInvalidNumParameters() throws FunctionException {
		functionInstanceOf.evaluate("", new Object[] {}, null);
	}

}
