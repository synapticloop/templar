package synapticloop.templar.token;

import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;


public class RequiresTokenTest {
	RequiresToken requiresToken;
	DumpContextToken dumpContextToken;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testParsing() throws ParseException {
		new Parser("{requires something}");
	}

	@Test(expected = RenderException.class)
	public void testRendering() throws ParseException, RenderException {
		Parser parser = new Parser("{requires something}");
		parser.render();
	}

	@Test
	public void testRendingCorrect() throws RenderException, ParseException {
		TemplarContext templarContext = new TemplarContext();
		templarContext.add("something", "something");
		Parser parser = new Parser("{requires something}");
		parser.render(templarContext);
	}

	@Test(expected = ParseException.class)
	public void testNullContext() throws ParseException {
		new RequiresToken("", new StringTokenizer("}"), new Tokeniser());
	}
}
