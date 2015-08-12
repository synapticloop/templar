package synapticloop.templar.exception;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.token.BasePositionToken;
import synapticloop.templar.token.ParseExceptionToken;

public class ParseExceptionTest {
private ParseException parseException;
	
	@Before
	public void setup() {
		parseException = new ParseException("This is a message");
	}
	
	@Test
	public void testDefaultMessage() {
		assertEquals("This is a message", parseException.getMessage());
	}

	@Test
	public void testNullExceptionToken() {
		BasePositionToken exceptionToken = parseException.getExceptionToken();
		// make sure that we get a token back, but the message is not affected
		assertNotNull(exceptionToken);
		assertEquals("This is a message", parseException.getMessage());
	}

	@Test
	public void testParseExceptionToken() {
		try {
			parseException = new ParseException("Message", new ParseExceptionToken());
			assertEquals("[Line: 1, Character: 1] Message", parseException.getMessage());
			assertNotNull(parseException.getExceptionToken());
		} catch (ParseException stepex) {
			assertFalse(true);
		}
	}
}
