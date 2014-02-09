package synapticloop.templar.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class IfTokenTest {

	@Test
	public void testCommandLineParsing() throws ParseException {
		Parser parser = new Parser("{if something.value & fn:something[1,3] | (fn:something[1, 2] & fn:somethingelse[3,4])}something{endif}");
		assertEquals("<IF@1:2 (something.value & fn:something[1,3] | (fn:something[1, 2] & fn:somethingelse[3,4]))>something< /IF@1:96>", parser.toString());
	}

	@Test
	public void testRendering() throws ParseException, RenderException {
		Parser parser = new Parser("{if fn:>['3','1'] | (fn:something[1, 2] & fn:somethingelse[3,4])}something{endif}");
		TemplarContext templarContext = new TemplarContext();
		String render = parser.render(templarContext);
		assertEquals("something", render);
	}
}
