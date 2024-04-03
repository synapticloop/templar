package com.synapticloop.templar.render;

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

import java.io.File;

import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;

public class LinesAndCharsTest {

	@Test
	public void testInvalidSetToken() {
		try {
			File file = new File("src/test/resources/line-numbers-set.templar");
			new Parser(file);
		} catch (ParseException stepex) {
			assertEquals(stepex.getExceptionToken().getLineNumber(), 8);
			assertEquals(stepex.getExceptionToken().getCharacterNumber(), 12);
		}

	}

	@Test
	public void testInvalidCurlyBrace() {
		try {
			File file = new File("src/test/resources/line-numbers-curly-brace.templar");
			new Parser(file);
		} catch (ParseException stepex) {
			assertEquals(stepex.getExceptionToken().getLineNumber(), 7);
			assertEquals(stepex.getExceptionToken().getCharacterNumber(), 5);
		}

	}

	@Test
	public void testInvalidCurlyBraceNoMoreTokens() {
		try {
			File file = new File("src/test/resources/line-numbers-curly-brace-no-more-tokens.templar");
			new Parser(file);
		} catch (ParseException stepex) {
			assertEquals(stepex.getExceptionToken().getLineNumber(), 9);
			assertEquals(stepex.getExceptionToken().getCharacterNumber(), 5);
		}

	}

}
