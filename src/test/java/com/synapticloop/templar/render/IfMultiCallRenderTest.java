package com.synapticloop.templar.render;

import java.io.File;

import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.bean.OuterBean;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarConfiguration;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.TestUtils;

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
