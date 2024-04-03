package com.synapticloop.templar.function.string;
import static org.junit.Assert.*;

import com.synapticloop.templar.function.string.FunctionSubString;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;

public class FunctionSubStringTest {
	private FunctionSubString functionSubString = null;

	@Before
	public void setup() {
		functionSubString = new FunctionSubString();
	}

	@Test
	public void testSubstring() throws FunctionException {
		Object evaluate = functionSubString.evaluate("substring", new Object[] { "\"hello\"", "\"1\"" }, null);
		assertEquals("ello", evaluate);
	}

	@Test
	public void testSubstringRange() throws FunctionException {
		Object evaluate = functionSubString.evaluate("substring", new Object[] { "\"hello\"", "\"1\"", "\"3\"" }, null);
		assertEquals("el", evaluate);
	}
}
