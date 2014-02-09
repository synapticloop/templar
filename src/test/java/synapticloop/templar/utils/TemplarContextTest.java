package synapticloop.templar.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import org.mockito.Mock;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.FunctionGreaterThan;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

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

}
