package com.synapticloop.templar.function.math;

import static org.junit.Assert.*;

import com.synapticloop.templar.function.math.FunctionDivide;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionDivideTest {
	private FunctionDivide functionDivide;
	private TemplarContext templarContext;
	@Before
	public void setup() {
		functionDivide = new FunctionDivide();
		templarContext  = new TemplarContext();
	}

	@Test
	public void testDivideLongLong() throws FunctionException {
		Object[] test = {"'100'", "'10'"};
		assertEquals(10L, functionDivide.evaluate("", test, templarContext));
	}

	@Test
	public void testDivideLongDouble() throws FunctionException {
		Object[] test = {"'100'", "'10.0'"};
		assertEquals(10.0, functionDivide.evaluate("", test, templarContext));
	}

	@Test
	public void testDivideDoubleLong() throws FunctionException {
		Object[] test = {"'100.0'", "'10'"};
		assertEquals(10.0, functionDivide.evaluate("", test, templarContext));
	}

}
