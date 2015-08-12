package synapticloop.templar.token.command;

import java.util.StringTokenizer;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class CommandFunctionToken extends CommandLineToken {

	public CommandFunctionToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
		// at this point - we must have a ':'
		if(!stringTokenizer.hasMoreElements() || !stringTokenizer.nextToken().equals(":")) {
			throw new ParseException("Could not find the ':' for the function");
		}

		// now we are at the function name, delimeters and values...
		this.evaluateCommand = stringTokenizer.nextToken().trim();

		// at this point - we must have a '['
		if(!stringTokenizer.hasMoreElements() || !stringTokenizer.nextToken().equals("[")) {
			throw new ParseException("Could not find argument start delimiter '[' for the function");
		}

		while(stringTokenizer.hasMoreTokens()) {
			String nextToken = stringTokenizer.nextToken().trim();
			if(nextToken.startsWith(",")) {
				// we have two arguments that are also functions
				nextToken = nextToken.substring(1).trim();
			}
			// we may have a dangling ',' which was just removed between two quoted values, which are actually tokens
			if(nextToken.length() == 0) {
				nextToken = stringTokenizer.nextToken().trim();
			}

			if(TOKEN_LOOKUP.containsKey(nextToken)) {
				switch(TOKEN_LOOKUP.get(nextToken)) {
				case EXCLAMATION_MARK:
					childTokens.add(new CommandNotToken(stringTokenizer));
					break;
				case CommandLineToken.DOUBLE_QUOTE:
					childTokens.add(new CommandDoubleQuoteToken(stringTokenizer));
					break;
				case CommandLineToken.SINGLE_QUOTE:
					childTokens.add(new CommandSingleQuoteToken(stringTokenizer));
					break;
				case FUNCTION:
					childTokens.add(new CommandFunctionToken(stringTokenizer));
					break;
				case FUNCTION_END:
					return;
				default:
					throw new ParseException("Found token '" + nextToken + "', without a case statement");
				}
			} else {
				// these will be arguments
				String[] arguments = nextToken.split(",");
				for (int i = 0; i < arguments.length; i++) {
					String argument = arguments[i].trim();
					if("fn".equals(argument)) {
						childTokens.add(new CommandFunctionToken(stringTokenizer));
					} else {
						childTokens.add(new CommandEvaluationToken(new StringTokenizer(argument)));
					}

				}
			}

		}
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		// we want to evaluate the function
		Object[] args = new Object[childTokens.size()];
		int i = 0;
		for (CommandLineToken commandToken : childTokens) {
			args[i] = commandToken.evaluate(templarContext);
			i++;
		}
		try {
			return(templarContext.invokeFunction(evaluateCommand, args, templarContext));
		} catch (FunctionException fex) {
			throw new RenderException("Could not render function, message was: " + fex.getMessage(), fex);
		}
	}

}
