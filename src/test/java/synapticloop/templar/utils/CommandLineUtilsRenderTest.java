package synapticloop.templar.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseCommandException;
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
	public void testNotSingleQuote() throws ParseCommandException, RenderException, ParseException {
		File file = new File("src/test/template/render-comment-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		String render = parser.render(templarContext);

		assertEquals("this is not a comment\nthis is not a comment", render);
	}

	@Test(expected=ParseCommandException.class)
	public void testNotDoubleQuote() throws ParseCommandException {
		CommandLineUtils.parseCommandLine("!'hello'");
	}

	@Test(expected=ParseCommandException.class)
	public void testNotNot() throws ParseCommandException {
		CommandLineUtils.parseCommandLine("!!!!!");
	}

	@Test(expected=ParseCommandException.class)
	public void testNoEndingDoubleQuote() throws ParseCommandException {
		CommandLineUtils.parseCommandLine("\"\\\" something");
	}

	@Test(expected=ParseCommandException.class)
	public void testNoEndingSingleQuote() throws ParseCommandException {
		CommandLineUtils.parseCommandLine("'\" something else \"");
	}

	@Test
	public void testSingleQuote() throws ParseCommandException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("'something else'"));
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("'\"something else\"'"));
	}

	@Test
	public void testDoubleQuote() throws ParseCommandException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("\"something else\""));
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("\"\\\"something else\\\"\""));
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("\"\\\"something\\\" else\""));
	}

	@Test
	public void testFunction() throws ParseCommandException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine(" fn:and[true, true]"));
	}

	@Test
	public void testFunctionInFunction() throws ParseCommandException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine(" fn:and[true, fn:and[true, true]]"));
	}

	@Test
	public void testNotFunction() throws ParseCommandException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("! fn:and[true, true]"));
	}

	@Test
	public void testFunctionInNotFunction() throws ParseCommandException {
		assertOnlyOneCommand(CommandLineUtils.parseCommandLine("fn:and[true, !fn:and[true, true]]"));
	}

	private void assertOnlyOneCommand(List<CommandLineToken> commandTokens) {
		for (CommandLineToken commandToken : commandTokens) {
			System.out.println(commandToken);
		}
		assertEquals(1, commandTokens.size());
	}
}
