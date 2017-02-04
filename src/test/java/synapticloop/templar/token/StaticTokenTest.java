package synapticloop.templar.token;

/*
 * Copyright (c) 2012-2017 synapticloop.
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
import static org.mockito.MockitoAnnotations.*;

import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.utils.Tokeniser;

public class StaticTokenTest {
	@Mock StringTokenizer mockStringTokenizer;
	@Mock Tokeniser mockTokeniser;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test(expected = ParseException.class)
	public void testParse() throws ParseException{
		Parser parser = new Parser("{static ./StaticTokenTest.java}");
		assertEquals("<STATIC@1:2 (./StaticTokenTest.java) />", parser.toString());
	}

}
