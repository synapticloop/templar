package synapticloop.templar.render;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.bean.OuterBean;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.TestUtils;

public class IfMultiCallRenderTest {
	@Test
	public void testSimpleIfRendering() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-if-multi-call-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		templarContext.add("outerBean", new OuterBean());

		String render = parser.render(templarContext);
		TestUtils.checkRenderContents(render);
	}
}
