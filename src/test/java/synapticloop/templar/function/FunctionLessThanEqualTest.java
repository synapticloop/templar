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

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;

public class FunctionLessThanEqualTest {
	private FunctionLessThanEqual functionLessThanEqual;

	@Before
	public void setup() {
		functionLessThanEqual = new FunctionLessThanEqual();
	}

	@Test
	public void testNullArguments() {
		try {
			functionLessThanEqual.evaluate(null, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"1", null};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {null, "1"};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}
	}

	@Test
	public void testNonCoerce() {
		try {
			Object[] objects = new Object[] {"1", "sjh"};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"sjh", "1"};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}
	}

	@Test
	public void testArgumentNumbers() {
		try {
			Object[] objects = new Object[] {"1"};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"1", "1", "1"};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"1", "1"};
			functionLessThanEqual.evaluate(objects, null);
			assertTrue(true);
		} catch (FunctionException stefex) {
			assertTrue(false);
		}
	}

	@Test
	public void testEvaluate() throws FunctionException {
		Object[] objects = new Object[] {"1", "3"};
		assertTrue(((Boolean)functionLessThanEqual.evaluate(objects, null)).booleanValue());

		objects = new Object[] {"3", "1"};
		assertFalse(((Boolean)functionLessThanEqual.evaluate(objects, null)).booleanValue());

		objects = new Object[] {"3", "3"};
		assertTrue(((Boolean)functionLessThanEqual.evaluate(objects, null)).booleanValue());
	}
}
