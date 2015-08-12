package synapticloop.templar.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.token.command.CommandLineToken;

public class CommandLineUtilsRenderTest {
	private CommandLineUtils commandLineUtils;

	@Before
	public void setup() {
		commandLineUtils = new CommandLineUtils();
	}

	@Test
	public void testNotSingleQuote() throws ParseException, RenderException, ParseException {
		File file = new File("src/test/template/render-comment-test.templar");
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
		CommandLineUtils.parseCommandLine("!'hello'");
	}

	@Test(expected=ParseException.class)
	public void testNotNot() throws ParseException {
		CommandLineUtils.parseCommandLine("!!!!!");
	}

	@Test(expected=ParseException.class)
	public void testNoEndingDoubleQuote() throws ParseException {
		CommandLineUtils.parseCommandLine("\"\\\" something");
	}

	@Test(expected=ParseException.class)
	public void testNoEndingSingleQuote() throws ParseException {
		CommandLineUtils.parseCommandLine("'\" something else \"");
	}

	@Test
	public void testSingleQuote() throws ParseException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("'something else'"));
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("'\"something else\"'"));
	}

	@Test
	public void testDoubleQuote() throws ParseException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("\"something else\""));
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("\"\\\"something else\\\"\""));
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("\"\\\"something\\\" else\""));
	}

	@Test
	public void testFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine(" fn:and[true, true]"));
	}

	@Test
	public void testFunctionInFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine(" fn:and[true, fn:and[true, true]]"));
	}

	@Test
	public void testNotFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("! fn:and[true, true]"));
	}

	@Test
	public void testFunctionInNotFunction() throws ParseException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("fn:and[true, !fn:and[true, true]]"));
	}

	private void assertOnlyOneCommand(List<CommandLineToken> commandTokens) {
		for (CommandLineToken commandToken : commandTokens) {
			System.out.println(commandToken);
		}
		assertEquals(1, commandTokens.size());
	}
}
