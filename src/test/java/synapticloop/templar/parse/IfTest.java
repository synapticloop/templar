package synapticloop.templar.parse;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;


public class IfTest {
	Parser parser;

	@Before
	public void setup() {
	}

	@Test
	public void testRender() throws ParseException {
		parser = new Parser("src/test/template/if-test.templar");
		assertEquals("These are text\n" +
				"\n" +
				"tokens with\n" +
				"some commands <IF@4:16 (something)>\n" +
				"\n" +
				"\tthen we should see these tokens...\n" +
				"\n" +
				"< /IF@8:2>\n" +
				"\n" +
				"and should come\n" +
				"out\n" +
				"\n" +
				"as command <IF@13:13 (something)> elements < /IF@13:35>\n" +
				"\n" +
				"now for some functions\n" +
				"<IF@16:2 (something)> something <ELSE@16:25 /> something else < /IF@16:43>\n",
				parser.toString());
	}
}
