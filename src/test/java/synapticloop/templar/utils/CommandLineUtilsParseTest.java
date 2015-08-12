package synapticloop.templar.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.ParseCommandException;
import synapticloop.templar.token.command.CommandLineToken;

public class CommandLineUtilsParseTest {
	private CommandLineUtils commandLineUtils;

	@Before
	public void setup() {
		commandLineUtils = new CommandLineUtils();
	}

	@Test(expected=ParseCommandException.class)
	public void testNotSingleQuote() throws ParseCommandException {
		CommandLineUtils.parseCommandLine("!\"hello\"");
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
		assertEquals(1, commandTokens.size());
	}
}
