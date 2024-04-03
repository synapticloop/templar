package com.synapticloop.templar.render;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;


public class RenderSetTest {
	@Test
	public void testRender() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-set-string-as-variable.templar");
		Parser parser = new Parser(file);
		TemplarContext templarContext = new TemplarContext();

		Assert.assertEquals("\"this is a string\" (without the quotes) should appear between the >< characters: >this is a string<", parser.render(templarContext));
	}
	
	@Test
	public void renderFunctionalSetCommand() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-set-function-as-variable.templar");
		Parser parser = new Parser(file);
		TemplarContext templarContext = new TemplarContext();
		templarContext.add("alwaysTrue", true);
		templarContext.add("alwaysFalse", false);
		parser.render(templarContext);
	}
}
