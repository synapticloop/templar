package synapticloop.templar.token.command;

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class CommandQuoteToken extends CommandLineToken {

	public CommandQuoteToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
	}

	protected void tokeniseQuote(String quotationMark, StringTokenizer stringTokenizer) throws ParseException {
		StringBuilder stringBuilder = new StringBuilder();
		boolean foundFinalQuote = false;
		boolean isEscaped = false;
		while(stringTokenizer.hasMoreElements()) {
			String nextToken = stringTokenizer.nextToken();
			if("\\".equals(nextToken) || nextToken.endsWith("\\")) {
				isEscaped = true;
			}

			if(quotationMark.equals(nextToken)) {
				if(isEscaped) {
					// we have an escape character quotation mark - ignore
					isEscaped = false;
				} else {
					foundFinalQuote = true;
					break;
				}
			}
			stringBuilder.append(nextToken);
		}

		if(!foundFinalQuote) {
			throw new ParseException("Could not find ending quotation mark (" + quotationMark + ")");
		}

		evaluateCommand = stringBuilder.toString();
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		return(evaluateCommand);
	}

}
