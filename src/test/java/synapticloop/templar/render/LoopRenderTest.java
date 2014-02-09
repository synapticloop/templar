package synapticloop.templar.render;

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

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class LoopRenderTest {

	@Test
	public void testLoopStatusBean() throws ParseException, RenderException {
		Parser parser = new Parser("src/test/template/render-loop-test.templar");

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		ArrayList<String> ArrayList = new ArrayList<String>();
		ArrayList.add("one");
		ArrayList.add("two");
		ArrayList.add("three");
		ArrayList.add("four");

		templarContext.add("ArrayList", ArrayList);

		String render = parser.render(templarContext);
		assertEquals("loop-status-first\n" +
				"\tfirst:1:0:one\n" +
				"\tnot first:2:1:two\n" +
				"\tnot first:3:2:three\n" +
				"\tnot first:4:3:four\n" +
				"loop-status-last\n" +
				"\tnot last:1:0:one\n" +
				"\tnot last:2:1:two\n" +
				"\tnot last:3:2:three\n" +
				"\tlast:4:3:four\n" +
				"inverse-loop-status-first\n" +
				"\tfirst:1:0:one\n" +
				"\tnot first:2:1:two\n" +
				"\tnot first:3:2:three\n" +
				"\tnot first:4:3:four\n" +
				"inverse-loop-status-last\n" +
				"\tnot last:1:0:one\n" +
				"\tnot last:2:1:two\n" +
				"\tnot last:3:2:three\n" +
				"\tlast:4:3:four\n" , render);
	}
}
