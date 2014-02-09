package synapticloop.templar.render;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class CommentRenderTest {
	@Test
	public void testSimpleIfRendering() throws ParseException, RenderException {
		Parser parser = new Parser("src/test/template/render-comment-test.templar");

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String render = parser.render(templarContext);

		assertEquals("this is not a comment\nthis is not a comment", render);
	}

}
