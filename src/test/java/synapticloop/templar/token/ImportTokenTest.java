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

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class ImportTokenTest {
	@Before
	public void setup() {
	}

	@Test(expected = ParseException.class)
	public void testNoEndToken() throws ParseException {
		new Parser("{import ");
	}

	@Test(expected = ParseException.class)
	public void testImport() throws ParseException, RenderException {
		new Parser("{import some/file/here}");
	}

	@Test
	public void testImportFromFileSystem() throws ParseException, RenderException {
		File file = new File("src/test/resources/import-test.templar");
		Parser parser = new Parser(file);
		assertEquals("<IMPORT@1:2 (src/test/resources/import/hello-world.templar)>\n", parser.toString());
	}

	@Test
	public void testImportFromFileSystemWithFunctions() throws ParseException, RenderException {
		File file = new File("src/test/resources/import-test-functions.templar");
		Parser parser = new Parser(file);
		TemplarContext templarContext = new TemplarContext();
		String[] array = {"one", "two", "three"};
		templarContext.add("array", array);
		String render = parser.render(templarContext);
		assertEquals("This is an import test with some functions before\n" + 
				"and after the import...\n" + 
				"3\n" + 
				"This value comes from inside the imported template: '3'\n" + 
				"\n" + 
				"3", render);
	}

}
