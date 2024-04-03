package com.synapticloop.templar.token;

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

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;

public class CommentTokenTest {
	@Before
	public void setup() {
	}

	@Test(expected = ParseException.class)
	public void testNoEndComment() throws ParseException {
		new Parser("{--");
	}

	@Test(expected = ParseException.class)
	public void testNoEndCommentWithOtherTokens() throws ParseException {
		new Parser("{-- comment goes here");
	}

	@Test
	public void testComment() throws ParseException, RenderException {
		Parser parser = new Parser("{-- comment goes here}");
		assertEquals("<COMMENT@1:2 ( comment goes here) />", parser.toString());
		assertEquals("", parser.render());
	}
}
