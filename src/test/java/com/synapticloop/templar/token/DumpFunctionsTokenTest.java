package com.synapticloop.templar.token;

import static org.junit.Assert.*;

import java.util.StringTokenizer;

import com.synapticloop.templar.token.DumpFunctionsToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.function.FunctionFormatDate;
import com.synapticloop.templar.function.FunctionIsNotNull;
import com.synapticloop.templar.function.FunctionIsNull;
import com.synapticloop.templar.function.FunctionLength;
import com.synapticloop.templar.function.bool.FunctionAnd;
import com.synapticloop.templar.function.bool.FunctionFalse;
import com.synapticloop.templar.function.bool.FunctionOr;
import com.synapticloop.templar.function.bool.FunctionTrue;
import com.synapticloop.templar.function.comparison.FunctionGreaterThan;
import com.synapticloop.templar.function.comparison.FunctionGreaterThanEqual;
import com.synapticloop.templar.function.comparison.FunctionLessThan;
import com.synapticloop.templar.function.comparison.FunctionLessThanEqual;
import com.synapticloop.templar.function.equality.FunctionEqual;
import com.synapticloop.templar.function.equality.FunctionNotEqual;
import com.synapticloop.templar.function.math.FunctionAdd;
import com.synapticloop.templar.function.math.FunctionDivide;
import com.synapticloop.templar.function.math.FunctionModulus;
import com.synapticloop.templar.function.math.FunctionMultiply;
import com.synapticloop.templar.function.math.FunctionPower;
import com.synapticloop.templar.function.math.FunctionSubtract;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;


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
		System.out.println(render);
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
