package com.synapticloop.templar.function.string;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionPluraliseTest {
	@Test
	public void testPluralise() throws RenderException, ParseException {
		TemplarContext templarContext = new TemplarContext();
		templarContext.add("one", new Object[] { "one" });
		templarContext.add("two", new Object[] { "one", "two" });
		templarContext.add("zero", new Object[] { });

		Parser parser = new Parser("{fn:pluralise[fn:length[one], \"s\", \"''\"]}");
		String render = parser.render(templarContext);

		assertEquals("", render);

		parser = new Parser("{fn:pluralise[fn:length[two], \"s\", \"''\"]}");
		render = parser.render(templarContext);
		assertEquals("s", render);

		parser = new Parser("{fn:pluralise[fn:length[zero], \"s\", \"''\"]}");
		render = parser.render(templarContext);
		assertEquals("s", render);

		parser = new Parser("{fn:pluralize[fn:length[zero], \"s\", \"''\"]}");
		render = parser.render(templarContext);
		assertEquals("s", render);

		parser = new Parser("{fn:pluralize[fn:length[one], \"s\", \"''\"]}");
		render = parser.render(templarContext);
		assertEquals("", render);

		parser = new Parser("There {fn:pluralize[fn:length[one], \"are\", \"is\"]} object{fn:pluralize[fn:length[one], \"s\", \"''\"]}");
		render = parser.render(templarContext);
		assertEquals("There is object", render);

		parser = new Parser("There {fn:pluralize[fn:length[two], \"are\", \"is\"]} object{fn:pluralize[fn:length[two], \"s\", \"''\"]}");
		render = parser.render(templarContext);
		assertEquals("There are objects", render);

	}
}
