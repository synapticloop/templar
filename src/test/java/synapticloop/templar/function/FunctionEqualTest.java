package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2015 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.equality.FunctionEqual;
import synapticloop.templar.utils.TemplarContext;

public class FunctionEqualTest {
	private FunctionEqual functionEqual;
	private FunctionLength functionLength;

	public class TestBean {
		public TestBean() { }

		public String getDescription() { return("description"); }
	}

	@Before
	public void setup() {
		functionEqual = new FunctionEqual();
		functionLength = new FunctionLength();
	}

	@Test
	public void testStringLengthFromContext() throws FunctionException {
		TemplarContext templarContext = new TemplarContext();
		templarContext.add("hello", "hello");
		templarContext.add("testBean", new TestBean());
		
		Object length = functionLength.evaluate("length", new Object[] {"testBean.description"}, templarContext);
		assertTrue((Boolean)functionEqual.evaluate("=", new Object[] { length, "\"11\"" }, templarContext));
	}

	@Test
	public void testNullArguments() {
		try {
			functionEqual.evaluate("=", null, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"'1'", null};
			assertFalse((Boolean)functionEqual.evaluate("=", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {null, "'1'"};
			assertFalse((Boolean)functionEqual.evaluate("=", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {null, null};
			assertTrue((Boolean)functionEqual.evaluate("=", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
	}

	@Test
	public void testNonCoerce() {
		try {
			Object[] objects = new Object[] {"'1'", "'sjh'"};
			assertFalse((Boolean)functionEqual.evaluate("=", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {"'sjh'", "'1'"};
			assertFalse((Boolean)functionEqual.evaluate("=", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
	}

	@Test
	public void testArgumentNumbers() {
		try {
			Object[] objects = new Object[] {"'1'"};
			functionEqual.evaluate("=", objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"'1'", "'1'", "'1'"};
			functionEqual.evaluate("=", objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"'1'", "'1'"};
			functionEqual.evaluate("=", objects, null);
			assertTrue(true);
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
	}

	@Test
	public void testEvaluate() throws FunctionException {
		Object[] objects = new Object[] {"'3'", "'1'"};
		assertFalse(((Boolean)functionEqual.evaluate("=", objects, null)).booleanValue());

		objects = new Object[] {"'1'", "'3'"};
		assertFalse(((Boolean)functionEqual.evaluate("=", objects, null)).booleanValue());

		objects = new Object[] {"'3'", "'3'"};
		assertTrue(((Boolean)functionEqual.evaluate("=", objects, null)).booleanValue());
	}

	@Test
	public void testArrayLength() throws FunctionException {
		FunctionLength functionLength = new FunctionLength();

		List<String> theList = new ArrayList<String>();
		Object listLength = functionLength.evaluate("=", new Object[] { theList }, null);
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { listLength, 0 }, null)).booleanValue());
		theList.add("one");
		listLength = functionLength.evaluate("=", new Object[] { theList }, null);
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { listLength, 1}, null)).booleanValue());
	}

	@Test
	public void testIntegerEquals() throws FunctionException {
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { new Integer(1), "1" }, null)));
	}

	@Test
	public void testDoubleEquals() throws FunctionException {
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { new Double(1.89), "1.89" }, null)));
	}

	@Test
	public void testFloatEquals() throws FunctionException {
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { new Float(1.89), "1.89" }, null)));
	}

	@Test
	public void testBooleanEquals() throws FunctionException {
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { new Boolean(true), "true" }, null)));
		assertFalse(((Boolean)functionEqual.evaluate("=", new Object[] { new Boolean(false), "true" }, null)));
		assertFalse(((Boolean)functionEqual.evaluate("=", new Object[] { new Boolean(true), "something-non-coercible" }, null)));
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { new Boolean(false), "something-non-coercible" }, null)));
		assertTrue(((Boolean)functionEqual.evaluate("=", new Object[] { new Boolean(false), "false" }, null)));
	}
}
