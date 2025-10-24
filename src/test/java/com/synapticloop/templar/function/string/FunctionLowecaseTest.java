package com.synapticloop.templar.function.string;

import com.synapticloop.templar.exception.FunctionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionLowecaseTest {
	private FunctionLowercase functionLowercase;

	@Before
	public void setup() {
		functionLowercase = new FunctionLowercase();
	}

	@Test
	public void testLowercase() throws FunctionException {
		assertEquals("this is a string", functionLowercase.evaluate("", new Object[] {"\"ThIs is A STRING\""}, null));
	}
}
