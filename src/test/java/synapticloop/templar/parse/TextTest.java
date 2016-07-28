package synapticloop.templar.parse;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

public class TextTest {

	@Test
	public void textTest() throws ParseException {
		Parser parser;
			File file = new File("src/test/resources/text-test.templar");
			parser = new Parser(file);
			assertEquals("These are text\n\n"
					+ "tokens\n\n"
					+ "and should come\n"
					+ "out\n\n"
					+ "exactly as it is input\n"
					+ "  even with whitespace\n"
					+ "<COMMENT@10:2 ( a comment goes here) />\n", 
					parser.toString());
	}
}
