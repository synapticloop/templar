package com.synapticloop.templar.function.math;

import static org.junit.Assert.*;

import com.synapticloop.templar.function.math.FunctionSubtract;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionSubtractTest {
	private FunctionSubtract functionSubtract;
	private TemplarContext templarContext;
	@Before
	public void setup() {
		functionSubtract = new FunctionSubtract();
		templarContext  = new TemplarContext();
	}

	@Test
	public void testSubtractLongLong() throws FunctionException {
		Object[] test = {"'123'", "'456'"};
		assertEquals(-333L, functionSubtract.evaluate("", test, templarContext));
	}

	@Test
	public void testSubtractLongDouble() throws FunctionException {
		Object[] test = {"'123'", "'45.6'"};
		assertEquals(77.4, functionSubtract.evaluate("", test, templarContext));
	}

	@Test
	public void testSubtractDoubleLong() throws FunctionException {
		Object[] test = {"'45.6'", "'123'"};
		assertEquals(-77.4, functionSubtract.evaluate("", test, templarContext));
	}

}
