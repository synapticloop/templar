package synapticloop.templar.parse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

public class TextTestMain {

	@Test
	public void textTest() {
		Parser parser;
		try {
			parser = new Parser("src/test/template/text-test.templar");
			assertEquals("These are text\n\ntokens\n\nand should come\nout\n\nexactly as it is input\n  even with whitespace\n<COMMENT@10:2 ( a comment goes here)>\n", parser.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
