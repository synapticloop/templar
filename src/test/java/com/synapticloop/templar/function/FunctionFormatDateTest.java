package com.synapticloop.templar.function;

/*
 * Copyright (c) 2012-2016 synapticloop.
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

import java.text.SimpleDateFormat;
import java.util.Date;

import com.synapticloop.templar.function.FunctionFormatDate;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.FunctionException;

public class FunctionFormatDateTest {
private FunctionFormatDate functionFormatDate;
	
	@Before
	public void setup() {
		functionFormatDate = new FunctionFormatDate();
	}

	@Test(expected = FunctionException.class)
	public void testNullFormat() throws FunctionException {
		functionFormatDate.evaluate("fmt", new Object[] {null}, null);
	}

	@Test(expected = FunctionException.class)
	public void testNullArguments() throws FunctionException {
		functionFormatDate.evaluate("fmt", null, null);
	}

	@Test(expected = FunctionException.class)
	public void testInvalidFormat() throws FunctionException {
		functionFormatDate.evaluate("fmt", new Object[] {"invalid-format-string"}, null);
	}

	@Test
	public void testValidFormat() throws FunctionException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String expected = simpleDateFormat.format(date);
		assertEquals(expected, functionFormatDate.evaluate("fmt", new Object[] {"yyyy-MM-dd"}, null));
	}

}
