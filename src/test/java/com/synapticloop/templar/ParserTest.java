package com.synapticloop.templar;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.synapticloop.templar.Parser;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarConfiguration;
import com.synapticloop.templar.utils.TemplarContext;

public class ParserTest {
	private Parser parser;

	@Before
	public void setup() {
	}

	@Test(expected = ParseException.class)
	public void testNullFilePath() throws ParseException {
		String hello = null;
		parser = new Parser(hello);
	}

	@Test
	public void testAvailableParserFilePath() throws ParseException {
		File file = new File("src/test/resources/empty.templar");
		parser = new Parser(file);
	}

	@Test(expected = ParseException.class)
	public void testNullFile() throws ParseException {
		File file = null;
		parser = new Parser(file);
	}

	@Test(expected = ParseException.class)
	public void testMissinglFile() throws ParseException {
		File file = new File("non/existent/file/path");
		parser = new Parser(file);
	}

	@Test(expected = ParseException.class)
	public void testNullContents() throws ParseException {
		String string = null;
		parser = new Parser(string);
	}

	@Test
	public void testAvailableParserFile() throws ParseException {
		parser = new Parser(new File("src/test/resources/empty.templar"));
		assertEquals("", parser.toString());
	}

	@Test
	public void testEmptyContextRendering() throws ParseException, RenderException {
		parser = new Parser(new File("src/test/resources/simple.templar"));
		parser.render(null);
	}

	@Test
	public void testTextTemplateParsing() throws ParseException, RenderException {
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(false);
		templarConfiguration.setExplicitTabs(false);

		TemplarContext templarContext = new TemplarContext();
		templarContext.setTemplarConfiguration(templarConfiguration);

		parser = new Parser(new File("src/test/resources/simple.templar"));
		assertEquals("This is a simple template<NEWLINE@1:27 />\nwith two lines.\n", parser.toString());
		assertEquals("This is a simple template\n\nwith two lines.\n", parser.render(templarContext));
	}

	@Test
	public void testParseAsInputStream() throws ParseException, RenderException, FileNotFoundException {
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(false);
		templarConfiguration.setExplicitTabs(false);

		TemplarContext templarContext = new TemplarContext();
		templarContext.setTemplarConfiguration(templarConfiguration);

		File file = new File("src/test/resources/simple.templar");
		InputStream inputStream = new FileInputStream(file);

		parser = new Parser(inputStream);
		assertEquals("This is a simple template<NEWLINE@1:27 />\nwith two lines.\n", parser.toString());
		assertEquals("This is a simple template\n\nwith two lines.\n", parser.render(templarContext));
	}

	@Test(expected = ParseException.class)
	public void testParseNullAsInputStream() throws ParseException, RenderException, FileNotFoundException {
		InputStream inputStream = null;

		parser = new Parser(inputStream);
	}

	@Test
	public void testRenderToStreamNullContext() throws ParseException, RenderException, IOException {
		File tempFile = File.createTempFile("temp", ".templar");
		tempFile.deleteOnExit();
		Parser parser = new Parser(tempFile);
		parser.renderToStream(null, null);
	}

	@Test
	public void testRenderToStream() throws ParseException, RenderException, IOException {
		File tempFile = File.createTempFile("temp", ".templar");
		tempFile.deleteOnExit();
		Parser parser = new Parser(tempFile);
		parser.renderToStream(new TemplarContext(), null);
	}

	@Test
	public void testRenderToFileNullContext() throws RenderException, ParseException, IOException {
		File tempFile = File.createTempFile("temp", ".templar");
		tempFile.deleteOnExit();
		Parser parser = new Parser(tempFile);

		parser.renderToFile(null, tempFile);
	}

	@Test
	public void testRenderToFile() throws RenderException, ParseException, IOException {
		File tempFile = File.createTempFile("temp", ".templar");
		tempFile.deleteOnExit();
		Parser parser = new Parser(tempFile);
		parser.renderToFile(new TemplarContext(), tempFile);
	}
}
