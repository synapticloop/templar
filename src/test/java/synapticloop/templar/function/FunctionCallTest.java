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

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;

public class FunctionCallTest {
	private FunctionCall functionCall;

	@Before
	public void setup() {
		functionCall = new FunctionCall();
	}

	@Test
	public void testIncorrectArgs() {
		try {
			functionCall.evaluate(null, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}

		try {
			functionCall.evaluate(new Object[] { null }, null);
			assertTrue(false);
		} catch (FunctionException stefex) {
			assertTrue(true);
		}
	}
//	functionCall.evaluate(new Object[] {"java.lang.System.currentTimeMillis"});

}
