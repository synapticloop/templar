package com.synapticloop.templar.utils;

import static org.junit.Assert.*;

import com.synapticloop.templar.utils.TemplarContext;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.comparison.FunctionGreaterThan;

public class TemplarContextTest {
	private TemplarContext templarContext;

	@Before
	public void setup() {
		templarContext = new TemplarContext();
	}

	@Test(expected = FunctionException.class)
	public void testOverrideOfExistingFunction() throws FunctionException {
		templarContext.addFunction(">", new FunctionGreaterThan());
	}

	@Test
	public void testOverrideOfFunction() throws FunctionException {
		templarContext.addFunction("GreaterThan", new FunctionGreaterThan());
		assertTrue(true);
	}

	@Test
	public void testNotNullContext() {
		assertNotNull(templarContext.getContext());
		assertNotNull(templarContext.getTemplarConfiguration());
		assertTrue(templarContext.getContext().keySet().size() == 0);
	}

	@Test(expected = FunctionException.class)
	public void testInvokeMissingFunction() throws FunctionException {
		templarContext.invokeFunction("missingFunctionName", null, null);
	}

	@Test(expected = FunctionException.class)
	public void testNullFunctionArgs() throws FunctionException {
		templarContext.invokeFunction(">", null, null);
	}

	@Test(expected = FunctionException.class)
	public void testFunctionNullArgs() throws FunctionException {
		templarContext.invokeFunction(">", new Object[] {null, "string"}, null);
	}

	@Test
	public void testCloneContext() {
		templarContext.add("hello", "baby");
		TemplarContext duplicate = new TemplarContext(templarContext);
		assertEquals("baby", duplicate.get("hello"));

		duplicate.add("hello", "there");
		assertEquals("there", duplicate.get("hello"));
		assertEquals("baby", templarContext.get("hello"));

		duplicate.add("what", "ever");
		assertEquals("ever", duplicate.get("what"));
		assertNull(templarContext.get("what"));
	}

	@Test
	public void getFunctionMap() throws FunctionException {
		templarContext.addFunction("something", new FunctionGreaterThan());
		assertTrue(templarContext.getFunctionMap().containsKey("something"));
	}

	@Test
	public void testClearContext() {
		templarContext.add("hello", "baby");
		assertEquals("baby", templarContext.get("hello"));
		templarContext.clear();
		assertNull(templarContext.get("hello"));
		assertEquals(0, templarContext.getContext().entrySet().size());
	}

	@Test(expected = FunctionException.class)
	public void testNonRegisteredFunction() throws FunctionException {
		Object[] args = {};
		templarContext.invokeFunction("not-registered-function-name", args, null);
	}

}
