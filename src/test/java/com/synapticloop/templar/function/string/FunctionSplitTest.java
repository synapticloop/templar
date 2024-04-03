package com.synapticloop.templar.function.string;
import static org.junit.Assert.*;

import com.synapticloop.templar.function.string.FunctionSplit;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.FunctionLength;

public class FunctionSplitTest {
	private FunctionSplit functionSplit = null;
	private FunctionLength functionLength = null;

	@Before
	public void setup() {
		functionSplit = new FunctionSplit();
		functionLength = new FunctionLength();
	}

	@Test
	public void testMethodName() throws FunctionException {
		Object evaluate = functionSplit.evaluate("split", new Object[] { "1,1,1,1,1", "," }, null);
		assertEquals(5, functionLength.evaluate("length", new Object[] { evaluate }, null));
	}
}
