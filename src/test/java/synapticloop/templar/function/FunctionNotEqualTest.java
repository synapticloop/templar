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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.equality.FunctionNotEqual;

public class FunctionNotEqualTest {
	private FunctionNotEqual functionNotEqual;

	@Before
	public void setup() {
		functionNotEqual = new FunctionNotEqual();
	}

	@Test
	public void testNullArguments() {
		try {
			functionNotEqual.evaluate("", null, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"'1'", null};
			assertTrue((Boolean)functionNotEqual.evaluate("", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {null, "'1'"};
			assertTrue((Boolean)functionNotEqual.evaluate("", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {null, null};
			assertFalse((Boolean)functionNotEqual.evaluate("", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
}

	@Test
	public void testNonCoerce() {
		try {
			Object[] objects = new Object[] {"'1'", "'sjh'"};
			assertTrue((Boolean)functionNotEqual.evaluate("", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {"'sjh'", "'1'"};
			assertTrue((Boolean)functionNotEqual.evaluate("", objects, null));
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
	}

	@Test
	public void testArgumentNumbers() {
		try {
			Object[] objects = new Object[] {"'1'"};
			functionNotEqual.evaluate("", objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"'1'", "'1'", "'1'"};
			functionNotEqual.evaluate("", objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"'1'", "'1'"};
			functionNotEqual.evaluate("", objects, null);
			assertTrue(true);
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
	}

	@Test
	public void testEvaluate() throws FunctionException {
		Object[] objects = new Object[] {"'3'", "'1'"};
		assertTrue(((Boolean)functionNotEqual.evaluate("", objects, null)).booleanValue());

		objects = new Object[] {"'1'", "'3'"};
		assertTrue(((Boolean)functionNotEqual.evaluate("", objects, null)).booleanValue());

		objects = new Object[] {"'3'", "'3'"};
		assertFalse(((Boolean)functionNotEqual.evaluate("", objects, null)).booleanValue());
	}
}
