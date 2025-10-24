package com.synapticloop.templar.function.string;

import com.synapticloop.templar.exception.FunctionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionUppercaseTest {
	private FunctionUppercase functionUppercase;

	@Before
	public void setup() {
		functionUppercase = new FunctionUppercase();
	}

	@Test
	public void testUppercase() throws FunctionException {
		assertEquals("THIS", functionUppercase.evaluate("", new Object[] {"\"this\""}, null));
	}
}
