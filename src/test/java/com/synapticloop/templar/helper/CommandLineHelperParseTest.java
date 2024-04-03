package com.synapticloop.templar.helper;

import static org.junit.Assert.*;

import java.util.List;

import com.synapticloop.templar.helper.CommandLineHelper;
import org.junit.Test;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.token.command.CommandLineToken;

public class CommandLineHelperParseTest {
	@Test(expected=ParseException.class)
	public void testNotSingleQuote() throws ParseException {
		CommandLineHelper.parseCommandLine("!\"hello\"");
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

	@Test(expected=ParseException.class)
	public void testInvalidFunctionMissingColon() throws ParseException {
		CommandLineHelper.parseCommandLine("fn[something, something]");
	}
	@Test(expected=ParseException.class)
	public void testInvalidFunctionMissingFunctionStart() throws ParseException {
		CommandLineHelper.parseCommandLine("fn:something something, something]");
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
