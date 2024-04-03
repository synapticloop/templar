package com.synapticloop.templar.render;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarConfiguration;
import com.synapticloop.templar.utils.TemplarContext;

public class CommentRenderTest {
	@Test
	public void testSimpleIfRendering() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-comment-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String render = parser.render(templarContext);

		assertEquals("this is not a comment\nthis is not a comment", render);
	}

}
