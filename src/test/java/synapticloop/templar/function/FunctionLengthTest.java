package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2014 synapticloop.
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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.bean.length.FieldLength;
import synapticloop.templar.bean.length.FieldNone;
import synapticloop.templar.bean.length.FieldSize;
import synapticloop.templar.bean.length.MethodGetLength;
import synapticloop.templar.bean.length.MethodGetSize;
import synapticloop.templar.exception.FunctionException;

public class FunctionLengthTest {
	private FunctionLength functionLength;

	@Before
	public void setup() {
		functionLength = new FunctionLength();
	}

	@Test(expected = FunctionException.class)
	public void testTooManyArgsFormat() throws FunctionException {
		functionLength.evaluate(new Object[] {null, null}, null);
	}

	@Test(expected = FunctionException.class)
	public void testNullArguments() throws FunctionException {
		functionLength.evaluate(null, null);
	}

	@Test(expected = FunctionException.class)
	public void testInvalidLength() throws FunctionException {
		functionLength.evaluate(new Object[] {new FieldNone()}, null);
	}

	@Test
	public void testValidLength() throws FunctionException {
		assertEquals(0, functionLength.evaluate(new Object[] {new ArrayList<String>()}, null));
		assertEquals(0, functionLength.evaluate(new Object[] {new ArrayList<String>()}, null));
		assertEquals(5, functionLength.evaluate(new Object[] {new String[5]}, null));

		assertEquals(MethodGetLength.RETURN_VALUE, functionLength.evaluate(new Object[] {new MethodGetLength()}, null));
		assertEquals(MethodGetSize.RETURN_VALUE, functionLength.evaluate(new Object[] {new MethodGetSize()}, null));
		assertEquals(FieldLength.RETURN_VALUE, functionLength.evaluate(new Object[] {new FieldLength()}, null));
		assertEquals(FieldSize.RETURN_VALUE, functionLength.evaluate(new Object[] {new FieldSize()}, null));
	}
}
