package synapticloop.templar.render;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.TestUtils;

public class RenderIfConditionalTest {

	@Test
	public void testSimpleIfConditionalRendering() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-if-conditional-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		templarContext.add("alwaysTrue", true);
		templarContext.add("alwaysFalse", false);

		String render = parser.render(templarContext);

		TestUtils.checkRenderContents(render);
	}
}
