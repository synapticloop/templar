package synapticloop.templar.parse;

import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

public class SetTokenTest {

	@Test
	public void testCommandLineParsing() throws ParseException {
		Parser parser = new Parser("{set fn:length[1] as length}");
		assertEquals("<SET@1:2 (fn:length[1] as length) />", parser.toString());
	}
}
