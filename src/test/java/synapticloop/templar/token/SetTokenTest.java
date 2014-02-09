package synapticloop.templar.token;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class SetTokenTest {
	private SetToken setToken;
	private TemplarContext templarContext;
	private Parser parser;

	@Before
	public void setup() throws ParseException {
		Vector<String> testVector = new Vector<String>();
		testVector.add("a");
		testVector.add("b");
		testVector.add("c");
		templarContext = new TemplarContext();
		templarContext.add("testVector", testVector);
	}

	@Test
	public void testParse() throws ParseException {
		parser = new Parser("{set something as somethingElse}");
	}

	@Test(expected = ParseException.class)
	public void testNoEndToken() throws ParseException {
		parser = new Parser("{set something as somethingElse");
	}

	@Test
	public void testContextSet() throws ParseException, RenderException {
		parser = new Parser("{set testVector as anotherVector}");
		String render = parser.render(templarContext);
		assertEquals("", render);
		assertNotNull(templarContext.get("anotherVector"));
	}

	@Test
	public void testContextSetWithFunction() throws ParseException, RenderException {
		parser = new Parser("{set fn:length[testVector] as anotherVector}");
		String render = parser.render(templarContext);
		assertEquals("", render);
		assertNotNull(templarContext.get("anotherVector"));
		assertEquals("3", templarContext.get("anotherVector").toString());
	}

	@Test(expected = ParseException.class)
	public void testInvalidSet() throws ParseException {
		parser = new Parser("{set");
	}

	@Test(expected = ParseException.class)
	public void testInvalidAsStatement() throws ParseException {
		parser = new Parser("{set something where something}");
	}

	@Test
	public void testToString() throws ParseException {
		parser = new Parser("{set something as something}");
		assertEquals("<SET@1:2 (something as something)>", parser.toString());
	}
}
