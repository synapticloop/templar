package synapticloop.templar.token;

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.helper.ParserHelper;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;


public class RequiresToken extends CommandToken {
	private static final long serialVersionUID = -5395690577077526893L;

	private String commandLine = null;

	public RequiresToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		boolean foundEndToken = ParserHelper.consumeToEndToken(stringTokenizer, stringBuilder);

		if(!foundEndToken) {
			throw new ParseException("Unable to find the closing requires token '}'", this);
		}

		this.commandLine = stringBuilder.toString().trim();
		
		if(commandLine.length() == 0) {
			throw new ParseException("Cannot have a requires token without a variable to require");
		}
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		if(null == templarContext) {
			templarContext = new TemplarContext();
		}

		ObjectHelper.evaluateObject(commandLine, templarContext);

		return("");
	}

	@Override
	public String toString() {
		return(super.toString("REQUIRES", commandLine));
	}

}