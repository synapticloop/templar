package com.synapticloop.templar.parse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;

public class ListTestMain {
	@Test
	public void testMain(){
		Parser parser;
		try {
			parser = new Parser("src/test/template/list-test.templar");
			assertEquals("These are text tokens with\nsome commands <LOOP@2:16 (something as somethingElse)>\n\n\tthen we should see these tokens...\n\n</LOOP@6:2>\n\nand should come\nout\n\nas command <LOOP@11:13 (something as somethingElse)> elements </LOOP@11:24>\n", parser.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
