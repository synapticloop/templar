package synapticloop.templar.token;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class TextTokenTest {
	private TextToken textToken;
	private TemplarContext templarContext;
	@Mock StringTokenizer mockStringTokenizer;

	@Before
	public void setup() throws ParseException {
		initMocks(this);
		textToken = new TextToken(null, null, new Tokeniser());
		templarContext = new TemplarContext();
	}

	@Test
	public void testRender() throws RenderException, ParseException {
		textToken = new TextToken("something", null, new Tokeniser());
		assertEquals("something", textToken.render(templarContext));

	}

	@Test
	public void testNewLineTokens() throws RenderException, ParseException {
		TemplarConfiguration configuration = new TemplarConfiguration();
		templarContext.setTemplarConfiguration(configuration);
		configuration.setExplicitNewLines(false);
		textToken = new TextToken("\n", null, new Tokeniser());
		assertEquals("\n", textToken.render(templarContext));
		configuration.setExplicitNewLines(true);
		templarContext.setTemplarConfiguration(configuration);

		assertEquals("", textToken.render(templarContext));
	}
}
