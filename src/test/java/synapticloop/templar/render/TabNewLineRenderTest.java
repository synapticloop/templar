package synapticloop.templar.render;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class TabNewLineRenderTest {

	@Test
	public void testExplicitNewLineTabRendering() throws ParseException, RenderException {
		File file = new File("src/test/template/tab-newline.templar");
		Parser parser = new Parser(file);
		assertEquals("<TAB@1:2 /><TAB@1:3 />\t\t\n<NEWLINE@2:2 />\n<NEWLINE@2:3 />\n\n\n<TAB@4:2 />\t<NEWLINE@4:4 />\n<NEWLINE@4:5 />\n\n", parser.toString());
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String render = parser.render(templarContext);
		assertEquals("\t\t\n\n\t\n\n", render);
	}

	@Test
	public void testImplicitNewLineTabRendering() throws ParseException, RenderException {
		File file = new File("src/test/template/tab-newline.templar");
		Parser parser = new Parser(file);
		assertEquals("<TAB@1:2 /><TAB@1:3 />\t\t\n<NEWLINE@2:2 />\n<NEWLINE@2:3 />\n\n\n<TAB@4:2 />\t<NEWLINE@4:4 />\n<NEWLINE@4:5 />\n\n", parser.toString());
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(false);
		templarConfiguration.setExplicitTabs(false);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String render = parser.render(templarContext);
		assertEquals("\t\t\t\t\n\n\n\n\n\t\t\n\n\n", render);
	}
}
