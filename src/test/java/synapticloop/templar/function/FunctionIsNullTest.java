package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2013 synapticloop.
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
import org.junit.After;

import org.mockito.Mock;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.TemplarContext;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class FunctionIsNullTest {
	private FunctionIsNull functionIsNull;

	@Before
	public void setup() {
		functionIsNull = new FunctionIsNull();
	}

	@Test
	public void testCorrectArguments() {
		try {
			Object[] objects = new Object[] {"1"};
			functionIsNull.evaluate(objects, new TemplarContext());
			assertTrue(true);
		} catch (FunctionException stefex) {
			assertTrue(false);
		}

		try {
			Object[] objects = new Object[] {"1", "1", "1"};
			functionIsNull.evaluate(objects, new TemplarContext());
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			Object[] objects = new Object[] {"1", "1"};
			functionIsNull.evaluate(objects, new TemplarContext());
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}
	}

	@Test
	public void testEvaluate() throws FunctionException {
		assertTrue(((Boolean)functionIsNull.evaluate(new Object[] {null}, new TemplarContext())).booleanValue());
		// TODO - put this back
//		assertFalse(((Boolean)functionIsNull.evaluate(new Object[] {""}, new TemplarContext())).booleanValue());
	}
}
