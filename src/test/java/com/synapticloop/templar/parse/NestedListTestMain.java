package com.synapticloop.templar.parse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;

public class NestedListTestMain {
	@Test
	public void testParse() {
		Parser parser;
		try {
			parser = new Parser("src/test/template/nested-list-test.templar");
			assertEquals("This is a nested list\n<LOOP@2:2 (something as somethingElse)>\n\nso we list\n<LOOP@5:2 (somethingElse as somethingElseAgain)>\n\tthen we should see these tokens...\n</LOOP@7:2>\n\n</LOOP@9:2>\n\nand that is about it\n", parser.toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
