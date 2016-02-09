package synapticloop.templar.function.string;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.FunctionLength;

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
