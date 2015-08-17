package synapticloop.templar.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.command.CommandLineToken;

public class CommandLineUtilsParseTest {
	@Test(expected=ParseException.class)
	public void testNotSingleQuote() throws ParseException {
		CommandLineUtils.parseCommandLine("!\"hello\"");
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

	@Test(expected=ParseException.class)
	public void testInvalidFunctionMissingColon() throws ParseException {
		CommandLineUtils.parseCommandLine("fn[something, something]");
	}
	@Test(expected=ParseException.class)
	public void testInvalidFunctionMissingFunctionStart() throws ParseException {
		CommandLineUtils.parseCommandLine("fn:something something, something]");
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
		assertEquals(1, commandTokens.size());
	}
}
