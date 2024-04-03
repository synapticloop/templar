package com.synapticloop.templar.function.math;

import static org.junit.Assert.*;

import com.synapticloop.templar.function.math.FunctionEven;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionEvenTest {
	private FunctionEven functionEven = null;
	private TemplarContext templarContext = null;

	@Before
	public void setup() {
		functionEven = new FunctionEven();
		templarContext  = new TemplarContext();
		templarContext.add("oddargumentlong", 123);
		templarContext.add("oddargumentdouble", 123.00d);

		templarContext.add("evenargumentlong", 124);
		templarContext.add("evenargumentdouble", 124.00d);
	}

	@Test(expected=FunctionException.class)
	public void notFoundInTemplarContext() throws FunctionException {
		Object[] test = { "not found argument" };
		functionEven.evaluate("", test, templarContext);
	}

	@Test(expected=FunctionException.class)
	public void wrongNumberOfArguments() throws FunctionException {
		assertTrue(invokeFunctionEven(new Object[] { "'123'", "sdkfjhkdsjfh" }));
	}

	@Test(expected=FunctionException.class)
	public void notCoercibleArguments() throws FunctionException {
		assertTrue(invokeFunctionEven(new Object[] {"'one'"}));
	}

	@Test
	public void testOdd() throws FunctionException {
		assertFalse(invokeFunctionEven(new Object[] { "oddargumentlong" }));
		assertFalse(invokeFunctionEven(new Object[] { "oddargumentdouble" }));

		assertFalse(invokeFunctionEven(new Object[] {"'123'"}));
		assertFalse(invokeFunctionEven(new Object[] {"'123.9898'"}));
		assertFalse(invokeFunctionEven(new Object[] {"'45.6'"}));
	}

	@Test
	public void testNotOdd() throws FunctionException {
		assertTrue(invokeFunctionEven(new Object[] {"'46'"}));
		assertTrue(invokeFunctionEven(new Object[] {"'46.00'"}));
		assertTrue(invokeFunctionEven(new Object[] {"'46.13'"}));
		assertTrue(invokeFunctionEven(new Object[] {"evenargumentlong"}));
		assertTrue(invokeFunctionEven(new Object[] {"evenargumentdouble"}));
	}

	private boolean invokeFunctionEven(Object[] test) throws FunctionException {
		Object evaluate = functionEven.evaluate("", test, templarContext);
		if(evaluate instanceof Boolean) {
			return(((Boolean)evaluate).booleanValue());
		} else {
			throw new FunctionException(evaluate.getClass().getSimpleName() + " is not a boolean object");
		}
		
	}
}
