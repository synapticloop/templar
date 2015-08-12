package synapticloop.templar.token.command;

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;

public class CommandSingleQuoteToken extends CommandQuoteToken {

	public CommandSingleQuoteToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
		// now consume until we get the end token
		tokeniseQuote("'", stringTokenizer);
	}
}
