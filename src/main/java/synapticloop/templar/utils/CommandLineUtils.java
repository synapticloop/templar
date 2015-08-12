package synapticloop.templar.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.command.CommandDoubleQuoteToken;
import synapticloop.templar.token.command.CommandEvaluationToken;
import synapticloop.templar.token.command.CommandFunctionToken;
import synapticloop.templar.token.command.CommandLineToken;
import synapticloop.templar.token.command.CommandNotToken;
import synapticloop.templar.token.command.CommandSingleQuoteToken;

public class CommandLineUtils {
	public static List<CommandLineToken> parseCommandLine(String commandLine) throws ParseException {
		List<CommandLineToken> commandTokens= new ArrayList<CommandLineToken>();
		StringTokenizer stringTokenizer = new StringTokenizer(commandLine, "!:\"'[]", true);

		while(stringTokenizer.hasMoreElements()) {
			String nextToken = stringTokenizer.nextToken().trim();
			if(CommandLineToken.TOKEN_LOOKUP.containsKey(nextToken)) {
				switch (CommandLineToken.TOKEN_LOOKUP.get(nextToken)) {
				case CommandLineToken.EXCLAMATION_MARK:
					commandTokens.add(new CommandNotToken(stringTokenizer));
					break;
				case CommandLineToken.SINGLE_QUOTE:
					commandTokens.add(new CommandSingleQuoteToken(stringTokenizer));
					break;
				case CommandLineToken.DOUBLE_QUOTE:
					commandTokens.add(new CommandDoubleQuoteToken(stringTokenizer));
					break;
				case CommandLineToken.FUNCTION:
					commandTokens.add(new CommandFunctionToken(stringTokenizer));
					break;
				case CommandLineToken.FUNCTION_START:
				case CommandLineToken.FUNCTION_END:
				case CommandLineToken.COLON:
					throw new ParseException("Failure parsing command line, unexpected token '" + nextToken + "'.");
				default:
					commandTokens.add(new CommandEvaluationToken(new StringTokenizer(nextToken)));
					break;
				}
			} else {
				commandTokens.add(new CommandEvaluationToken(new StringTokenizer(nextToken)));
			}
		}
		return(commandTokens);
	}
}
