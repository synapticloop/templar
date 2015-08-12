package synapticloop.templar.token.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public abstract class CommandLineToken {
	public static final Map<String, Integer> TOKEN_LOOKUP = new HashMap<String, Integer>();
	public static final int EXCLAMATION_MARK = 0;
	public static final int DOUBLE_QUOTE = 1;
	public static final int SINGLE_QUOTE = 2;
	public static final int COLON = 3;
	public static final int FUNCTION = 4;
	public static final int FUNCTION_START = 5;
	public static final int FUNCTION_END = 6;
	static {
		TOKEN_LOOKUP.put("!", EXCLAMATION_MARK);
		TOKEN_LOOKUP.put("\"", DOUBLE_QUOTE);
		TOKEN_LOOKUP.put("'", SINGLE_QUOTE);
		TOKEN_LOOKUP.put(":", COLON);
		TOKEN_LOOKUP.put("fn", FUNCTION);
		TOKEN_LOOKUP.put("[", FUNCTION_START);
		TOKEN_LOOKUP.put("]", FUNCTION_END);
	}

	protected List<CommandLineToken> childTokens = new ArrayList<CommandLineToken>();
	protected String evaluateCommand = null;

	public CommandLineToken(StringTokenizer stringTokenizer) throws ParseException {}

	public abstract Object evaluate(TemplarContext templarContext) throws RenderException;

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		String className = this.getClass().getSimpleName();
		stringBuilder.append(className);
		stringBuilder.append("(");
		stringBuilder.append(evaluateCommand);
		stringBuilder.append(")");
		stringBuilder.append(">");
		for (CommandLineToken commandToken : childTokens) {
			stringBuilder.append(commandToken.toString());
		}
		stringBuilder.append("</");
		stringBuilder.append(className);
		stringBuilder.append(">");
		return(stringBuilder.toString());
	}
}
