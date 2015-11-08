package synapticloop.templar.token;

import static org.junit.Assert.*;


import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.function.FunctionAnd;
import synapticloop.templar.function.FunctionEqual;
import synapticloop.templar.function.FunctionFalse;
import synapticloop.templar.function.FunctionFormatDate;
import synapticloop.templar.function.FunctionGreaterThan;
import synapticloop.templar.function.FunctionGreaterThanEqual;
import synapticloop.templar.function.FunctionIsNotNull;
import synapticloop.templar.function.FunctionIsNull;
import synapticloop.templar.function.FunctionLength;
import synapticloop.templar.function.FunctionLessThan;
import synapticloop.templar.function.FunctionLessThanEqual;
import synapticloop.templar.function.FunctionNotEqual;
import synapticloop.templar.function.FunctionOr;
import synapticloop.templar.function.FunctionTrue;
import synapticloop.templar.function.math.FunctionAdd;
import synapticloop.templar.function.math.FunctionDivide;
import synapticloop.templar.function.math.FunctionModulus;
import synapticloop.templar.function.math.FunctionMultiply;
import synapticloop.templar.function.math.FunctionPower;
import synapticloop.templar.function.math.FunctionSubtract;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;


public class DumpFunctionsTokenTest {
	DumpFunctionsToken dumpFunctionsToken;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void testRender() throws ParseException, RenderException {
		dumpFunctionsToken = new DumpFunctionsToken("", new StringTokenizer("}"), new Tokeniser());
		TemplarContext templarContext = new TemplarContext();
		String render = dumpFunctionsToken.render(templarContext);
		assertTrue(render.contains(FunctionLessThanEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionLessThanEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionNotEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionLessThan.class.getSimpleName()));
		assertTrue(render.contains(FunctionAnd.class.getSimpleName()));
		assertTrue(render.contains(FunctionGreaterThanEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionLessThanEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionPower.class.getSimpleName()));
		assertTrue(render.contains(FunctionOr.class.getSimpleName()));
		assertTrue(render.contains(FunctionIsNotNull.class.getSimpleName()));
		assertTrue(render.contains(FunctionFormatDate.class.getSimpleName()));
		assertTrue(render.contains(FunctionModulus.class.getSimpleName()));
		assertTrue(render.contains(FunctionAnd.class.getSimpleName()));
		assertTrue(render.contains(FunctionLength.class.getSimpleName()));
		assertTrue(render.contains(FunctionFalse.class.getSimpleName()));
		assertTrue(render.contains(FunctionNotEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionMultiply.class.getSimpleName()));
		assertTrue(render.contains(FunctionAdd.class.getSimpleName()));
		assertTrue(render.contains(FunctionIsNotNull.class.getSimpleName()));
		assertTrue(render.contains(FunctionGreaterThan.class.getSimpleName()));
		assertTrue(render.contains(FunctionSubtract.class.getSimpleName()));
		assertTrue(render.contains(FunctionDivide.class.getSimpleName()));
		assertTrue(render.contains(FunctionEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionIsNull.class.getSimpleName()));
		assertTrue(render.contains(FunctionTrue.class.getSimpleName()));
		assertTrue(render.contains(FunctionNotEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionNotEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionLessThan.class.getSimpleName()));
		assertTrue(render.contains(FunctionOr.class.getSimpleName()));
		assertTrue(render.contains(FunctionEqual.class.getSimpleName()));
		assertTrue(render.contains(FunctionIsNotNull.class.getSimpleName()));
		assertTrue(render.contains(FunctionGreaterThan.class.getSimpleName()));
		assertTrue(render.contains(FunctionGreaterThanEqual.class.getSimpleName()));
	}

}
