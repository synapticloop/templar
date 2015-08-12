package synapticloop.templar.render;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class RenderIfConditionalTest {

	@Test
	public void testSimpleIfConditionalRendering() throws ParseException, RenderException {
		File file = new File("src/test/template/render-if-test-conditionals.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		templarContext.add("alwaysTrue", true);
		templarContext.add("alwaysFalse", false);

		String render = parser.render(templarContext);

		if(render.contains("FATAL ERROR")) {
			assertTrue("Found string 'FATAL ERROR' in output", false);
		}
	}
}
