package com.synapticloop.templar.function.math;

import static org.junit.Assert.*;

import com.synapticloop.templar.function.math.FunctionAdd;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionAddTest {
	private FunctionAdd functionAdd;
	private TemplarContext templarContext;
	@Before
	public void setup() {
		functionAdd = new FunctionAdd();
		templarContext  = new TemplarContext();
	}

	@Test
	public void foundInTemplarContext() throws FunctionException {
		templarContext.add("argone", 123);
		templarContext.add("argtwo", 456);
		Object[] test = { "argone", "argtwo" };
		assertEquals(579L, functionAdd.evaluate("", test, templarContext));
	}

	@Test(expected=FunctionException.class)
	public void notFoundInTemplarContext() throws FunctionException {
		templarContext.add("argone", 123);
		templarContext.add("argtwo", 456);
		Object[] test = { "argone", "notfoundargument" };
		functionAdd.evaluate("", test, templarContext);
	}

	@Test(expected=FunctionException.class)
	public void wrongNumberOfArguments() throws FunctionException {
		Object[] test = { "'123'", "'456'", "sdkfjhkdsjfh" };
		functionAdd.evaluate("", test, templarContext);
	}

	@Test(expected=FunctionException.class)
	public void notCoercibleArguments() throws FunctionException {
		Object[] test = { "'one'", "'two'" };
		functionAdd.evaluate("", test, templarContext);
	}

	@Test
	public void testAddLongLong() throws FunctionException {
		Object[] test = {"'123'", "'456'"};
		assertEquals(579L, functionAdd.evaluate("", test, templarContext));
	}

	@Test
	public void testAddLongDouble() throws FunctionException {
		Object[] test = {"'123'", "'45.6'"};
		assertEquals(168.6, functionAdd.evaluate("", test, templarContext));
	}

	@Test
	public void testAddDoubleLong() throws FunctionException {
		Object[] test = {"'45.6'", "'123'"};
		assertEquals(168.6, functionAdd.evaluate("", test, templarContext));
	}

}
