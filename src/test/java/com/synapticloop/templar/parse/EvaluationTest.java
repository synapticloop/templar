package com.synapticloop.templar.parse;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;


public class EvaluationTest {
	Parser parser;

	@Before
	public void setup() {
	}

	@Test
	public void testRender() throws ParseException {
		File file = new File("src/test/resources/evaluation-test.templar");
		parser = new Parser(file);
		assertEquals("<EVAL@1:2 (this.should.all) />\n" +
				"\n" +
				"be\n" +
				"eval<EVAL@4:6 (uation) /> tokens\n",
				parser.toString());
	}
}
