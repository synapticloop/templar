package synapticloop.templar.token.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class CommandNotToken extends CommandLineToken {

	public CommandNotToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
		List<CommandLineToken> commandTokens = new ArrayList<CommandLineToken>();
		// at this point we are looking for either a function or an evaluation
		if(stringTokenizer.hasMoreElements()) {
			String nextToken = stringTokenizer.nextToken().trim();
			if(TOKEN_LOOKUP.containsKey(nextToken)) {
				switch(TOKEN_LOOKUP.get(nextToken)) {
				case EXCLAMATION_MARK:
					childTokens.add(new CommandNotToken(stringTokenizer));
					break;
				case DOUBLE_QUOTE:
				case SINGLE_QUOTE:
					throw new ParseException("Cannot have a '!' before a simgle quote (') or double quote (\")");
				case FUNCTION:
					childTokens.add(new CommandFunctionToken(stringTokenizer));
					break;
				default:
					break;
				}
			} else {
				childTokens.add(new CommandEvaluationToken(new StringTokenizer(nextToken)));
			}
		} else {
			throw new ParseException("Could not find next token after '!'");
		}
		commandTokens.add(this);
		this.evaluateCommand = "";
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		if(childTokens.size() != 1) {
			throw new RenderException("Can only evaluate the not '!' of one child token.");
		}

		CommandLineToken commandToken = childTokens.get(0);
		Object evaluate = commandToken.evaluate(templarContext);
		if(evaluate instanceof Boolean) {
			return(!((Boolean)evaluate).booleanValue());
		} else {
			throw new RenderException("Can only evaluate the not '!' of boolean evaluation.");
		}
	}

}
