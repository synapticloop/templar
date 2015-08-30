package synapticloop.templar.token;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;

public class PreTokenTest {
	@Before
	public void setup() {
	}

	@Test
	public void testCorrectToken() throws ParseException {
		Parser parser = new Parser("{pre all sorts of stuff here pre}");
		assertEquals("<PRE@1:2 (all sorts of stuff here)/>", parser.toString());
	}

	@Test
	public void testLotsOfPreToken() throws ParseException, RenderException {
		Parser parser = new Parser("{pre pre pre pre something, something, pre pre}");
		assertEquals("<PRE@1:2 (pre pre pre something, something, pre)/>", parser.toString());
		assertEquals("pre pre pre something, something, pre", parser.render());
	}

	@Test
	public void testJavascriptToken() throws ParseException, RenderException {
		Parser parser = new Parser("{pre some_javascript_function() {} pre}");
		assertEquals("<PRE@1:2 (some_javascript_function() {})/>", parser.toString());
		assertEquals("some_javascript_function() {}", parser.render());
	}

	@Test
	public void testNewLinesAndTabsToken() throws ParseException, RenderException {
		Parser parser = new Parser("{pre \nnoindent\n\tpre\n\tpre\n\tpre\n\tsomething, something\n\tpre\nnoindent pre}");
		assertEquals("<PRE@1:2 (\n" + 
				"noindent\n" + 
				"	pre\n" + 
				"	pre\n" + 
				"	pre\n" + 
				"	something, something\n" + 
				"	pre\n" + 
				"noindent)/>", parser.toString());
		assertEquals("\nnoindent\n" + 
				"	pre\n" + 
				"	pre\n" + 
				"	pre\n" + 
				"	something, something\n" + 
				"	pre\n" + 
				"noindent", parser.render());
	}

}
