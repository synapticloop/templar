package synapticloop.templar.render;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;


public class RenderImportTest {
	@Test
	public void testRender() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-import-recursive-test.templar");
		Parser parser = new Parser(file);
		TemplarContext templarContext = new TemplarContext();

		templarContext.add("something", "something");

		String render = parser.render(templarContext);
		System.out.println(render);
		assertEquals("start import\n" + 
				"something\n" + 
				"setting something as else\n" + 
				"else\n" + 
				"correct\n" + 
				"end import\n",
				render);
	}

}

