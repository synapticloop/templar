package synapticloop.templar.helper;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.helper.CommandLineHelper;
import synapticloop.templar.token.command.CommandLineToken;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class CommandLineHelperRenderTest {

	@Test
	public void testNotSingleQuote() throws ParseException, RenderException, ParseException {
		File file = new File("src/test/resources/render-comment-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String render = parser.render(templarContext);

		assertEquals("this is not a comment\nthis is not a comment", render);
	}

	@Test(expected=ParseException.class)
	public void testNotDoubleQuote() throws ParseException {
		CommandLineHelper.parseCommandLine("!'hello'");
	}

	@Test(expected=ParseException.class)
	public void testNotNot() throws ParseException {
		CommandLineHelper.parseCommandLine("!!!!!");
	}

	@Test(expected=ParseException.class)
	public void testNoEndingDoubleQuote() throws ParseException {
		CommandLineHelper.parseCommandLine("\"\\\" something");
	}

	@Test(expected=ParseException.class)
	public void testNoEndingSingleQuote() throws ParseException {
		CommandLineHelper.parseCommandLine("'\" something else \"");
	}

	@Test
	public void testSingleQuote() throws ParseException {
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("'something else'"));
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("'\"something else\"'"));
	}

	@Test
	public void testDoubleQuote() throws ParseException {
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("\"something else\""));
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("\"\\\"something else\\\"\""));
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("\"\\\"something\\\" else\""));
	}

	@Test
	public void testFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine(" fn:and[true, true]"));
	}

	@Test
	public void testFunctionInFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine(" fn:and[true, fn:and[true, true]]"));
	}

	@Test
	public void testNotFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("! fn:and[true, true]"));
	}

	@Test
	public void testFunctionInNotFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineHelper.parseCommandLine("fn:and[true, !fn:and[true, true]]"));
	}

	private void assertOnlyOneCommand(List<CommandLineToken> commandTokens) {
		assertEquals(1, commandTokens.size());
	}
}
