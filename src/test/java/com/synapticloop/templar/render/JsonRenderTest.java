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

import org.json.JSONObject;
import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarConfiguration;
import com.synapticloop.templar.utils.TemplarContext;

public class JsonRenderTest {

	@Test
	public void testJSONObjectRender() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-json-object.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String jsonObjectString = "{\n" + 
				"	\"property\": \"thisIsAProperty\",\n" + 
				"	\"booleanTrue\": true,\n" + 
				"	\"booleanFalse\": false,\n" + 
				"	\"oneHundred\": 100,\n" + 
				"	\"array\": [\n" + 
				"		\"element_one\", \"element_two\", \"element_three\"\n" + 
				"	],\n" + 
				"\n" + 
				"	\"objectArray\": [\n" + 
				"		{ \"name\": \"firstObject\" },\n" + 
				"		{ \"name\": \"secondObject\"}\n" + 
				"	],\n" + 
				"	\"nested\": {\n" + 
				"		\"one\": {\n" + 
				"			\"two\": \"correct\"\n" + 
				"		}\n" + 
				"	}\n" + 
				"}";
		JSONObject jsonObject = new JSONObject(jsonObjectString);
		templarContext.add("jsonObject", jsonObject);
		String render = parser.render(templarContext);
		System.out.println(render);
		assertEquals("correct\n" + 
				"thisIsAProperty\n" + 
				"correct\n" + 
				"correct\n" + 
				"correct\n" + 
				"element_one\n" + 
				"element_two\n" + 
				"element_three\n" + 
				"firstObject\n" + 
				"secondObject\n", 
				render);
	}
}
