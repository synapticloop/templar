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
		assertEquals("start import<NEWLINE@1:14 />\n" + 
				"<IMPORT@2:2 (src/test/resources/import/recursive.templar)><EVAL@1:2 (something) /><NEWLINE@1:3 />\n" + 
				"<IF@2:2 (fn:=[something, \"else\"])>\n" + 
				"correct<NEWLINE@3:9 />\n" + 
				"<ELSE@4:2 />\n" + 
				"setting \"else\" to variable named something<NEWLINE@5:44 />\n" + 
				"<SET@6:2 (\"else\" as something) />\n" + 
				"<IMPORT@7:2 *CACHED* (src/test/resources/import/recursive.templar) />\n" + 
				"</IF@8:2>\n" + 
				"</IMPORT@2:2>\n" + 
				"end import<NEWLINE@3:12 />\n", parser.toString());

		String render = parser.render(templarContext);

		assertEquals("start import\n" + 
				"something\n" + 
				"setting \"else\" to variable named something\n" + 
				"else\n" + 
				"correct\n" + 
				"end import\n",
				render);
	}

}

