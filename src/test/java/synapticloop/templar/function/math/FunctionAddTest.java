package synapticloop.templar.function.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.TemplarContext;

public class FunctionAddTest {
	private FunctionAdd functionAdd;
	private TemplarContext templarContext;
	@Before
	public void setup() {
		functionAdd = new FunctionAdd();
		templarContext  = new TemplarContext();
	}

	@Test
	public void testAddLongLong() throws FunctionException {
		Object[] test = {"'123'", "'456'"};
		assertEquals(579L, functionAdd.evaluate(test, templarContext));
	}

	@Test
	public void testAddLongDouble() throws FunctionException {
		Object[] test = {"'123'", "'45.6'"};
		assertEquals(168.6, functionAdd.evaluate(test, templarContext));
	}

	@Test
	public void testAddDoubleLong() throws FunctionException {
		Object[] test = {"'45.6'", "'123'"};
		assertEquals(168.6, functionAdd.evaluate(test, templarContext));
	}

}
