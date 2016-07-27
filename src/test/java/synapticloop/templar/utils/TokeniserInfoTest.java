package synapticloop.templar.utils;

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

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

public class TokeniserInfoTest {

	@Before
	public void setup() {
	}

	@Test
	public void testLineNumbers() throws ParseException {
		Parser parser = new Parser("\n\n\n\n\n");
		assertEquals(6, parser.getTokeniser().getTokeniserInfo().getLineNumber());
		// make sure it is reset
		parser = new Parser("");
		assertEquals(1, parser.getTokeniser().getTokeniserInfo().getLineNumber());
	}

	@Test
	public void testCharacterNumbers() throws ParseException {
		Parser parser = new Parser("\nthis is a long text token");
		assertEquals(2, parser.getTokeniser().getTokeniserInfo().getLineNumber());
		assertEquals(26, parser.getTokeniser().getTokeniserInfo().getCharacterNumber());

		parser = new Parser("this is a long text token\nthis is a long text token");
		assertEquals(2, parser.getTokeniser().getTokeniserInfo().getLineNumber());
		assertEquals(26, parser.getTokeniser().getTokeniserInfo().getCharacterNumber());

		parser = new Parser("this is a long text token\nthis is a long text token\n");
		assertEquals(3, parser.getTokeniser().getTokeniserInfo().getLineNumber());
		assertEquals(1, parser.getTokeniser().getTokeniserInfo().getCharacterNumber());

		// make sure it is reset
		parser = new Parser("");
		assertEquals(1, parser.getTokeniser().getTokeniserInfo().getLineNumber());
		assertEquals(1, parser.getTokeniser().getTokeniserInfo().getCharacterNumber());
	}
}
