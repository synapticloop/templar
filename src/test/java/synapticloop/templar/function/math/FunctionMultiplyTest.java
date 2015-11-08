package synapticloop.templar.function.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.TemplarContext;

public class FunctionMultiplyTest {
	private FunctionMultiply functionMultiply;
	private TemplarContext templarContext;

	@Before
	public void setup() {
		functionMultiply = new FunctionMultiply();
		templarContext  = new TemplarContext();
	}

	@Test
	public void testSubtractLongLong() throws FunctionException {
		Object[] test = {"'100'", "'10'"};
		assertEquals(1000L, functionMultiply.evaluate("", test, templarContext));
	}

	@Test
	public void testSubtractLongDouble() throws FunctionException {
		Object[] test = {"'100'", "'10.0'"};
		assertEquals(1000.0, functionMultiply.evaluate("", test, templarContext));
	}

	@Test
	public void testSubtractDoubleLong() throws FunctionException {
		Object[] test = {"'100.0'", "'10'"};
		assertEquals(1000.0, functionMultiply.evaluate("", test, templarContext));
	}

}
