package synapticloop.templar.token;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;


public class DumpContextTokenTest {
	DumpContextToken dumpContextToken;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testNullContext() {
		try {
			dumpContextToken = new DumpContextToken(null, null, new Tokeniser());
			assertEquals("<DUMPCONTEXT@1:1 />", dumpContextToken.toString());
		} catch (ParseException e) {
			assertFalse(true);
		}
	}

	@Test
	public void testRender() throws ParseException, RenderException {
		dumpContextToken = new DumpContextToken(null, null, new Tokeniser());
		TemplarContext templarContext = new TemplarContext();
		templarContext.add("key", "value");
		assertEquals("TemplarContext[{key:value}]", dumpContextToken.render(templarContext));
	}

	@Test
	public void testRenderObjects() throws ParseException, RenderException {
		dumpContextToken = new DumpContextToken(null, null, new Tokeniser());
		TemplarContext templarContext = new TemplarContext();
		templarContext.add("key", "value");
		templarContext.add("stringArray", new String[8]);
		String render = dumpContextToken.render(templarContext);
		assertTrue(render.contains("TemplarContext["));
		assertTrue(render.contains("{stringArray:[Ljava.lang.String;"));
		assertTrue(render.contains("{key:value}"));
	}

	@Test
	public void testRenderNullContext() throws ParseException, RenderException {
		dumpContextToken = new DumpContextToken(null, null, new Tokeniser());
		assertEquals("TemplarContext[]", dumpContextToken.render(null));
	}

}
