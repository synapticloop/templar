package synapticloop.templar.function.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.TemplarContext;

public class FunctionOddTest {
	private FunctionOdd functionOdd = null;
	private TemplarContext templarContext = null;

	@Before
	public void setup() {
		functionOdd = new FunctionOdd();
		templarContext  = new TemplarContext();
		templarContext.add("oddargumentlong", 123);
		templarContext.add("oddargumentdouble", 123.00d);
		templarContext.add("evenargumentlong", 124);
		templarContext.add("evenargumentdouble", 124.00d);
	}

	@Test(expected=FunctionException.class)
	public void notFoundInTemplarContext() throws FunctionException {
		Object[] test = { "not found argument" };
		functionOdd.evaluate("", test, templarContext);
	}

	@Test(expected=FunctionException.class)
	public void wrongNumberOfArguments() throws FunctionException {
		assertTrue(invokeFunctionOdd(new Object[] { "'123'", "sdkfjhkdsjfh" }));
	}

	@Test(expected=FunctionException.class)
	public void notCoercibleArguments() throws FunctionException {
		assertTrue(invokeFunctionOdd(new Object[] {"'one'"}));
	}

	@Test
	public void testOdd() throws FunctionException {
		assertTrue(invokeFunctionOdd(new Object[] { "oddargumentlong" }));
		assertTrue(invokeFunctionOdd(new Object[] { "oddargumentdouble" }));

		assertTrue(invokeFunctionOdd(new Object[] {"'123'"}));
		assertTrue(invokeFunctionOdd(new Object[] {"'123.9898'"}));
		assertTrue(invokeFunctionOdd(new Object[] {"'45.6'"}));
	}

	@Test
	public void testNotOdd() throws FunctionException {
		assertFalse(invokeFunctionOdd(new Object[] {"'46'"}));
		assertFalse(invokeFunctionOdd(new Object[] {"'46.00'"}));
		assertFalse(invokeFunctionOdd(new Object[] {"'46.13'"}));
		assertFalse(invokeFunctionOdd(new Object[] {"evenargumentlong"}));
		assertFalse(invokeFunctionOdd(new Object[] {"evenargumentdouble"}));
	}

	private boolean invokeFunctionOdd(Object[] test) throws FunctionException {
		Object evaluate = functionOdd.evaluate("", test, templarContext);
		if(evaluate instanceof Boolean) {
			return(((Boolean)evaluate).booleanValue());
		} else {
			throw new FunctionException(evaluate.getClass().getSimpleName() + " is not a boolean object");
		}
		
	}
}
