package synapticloop.templar.token.command;

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class CommandEvaluationToken extends CommandLineToken {

	public CommandEvaluationToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
		StringBuilder stringBuilder = new StringBuilder();
		while (stringTokenizer.hasMoreTokens()) {
			stringBuilder.append(stringTokenizer.nextToken());
		}
		this.evaluateCommand = stringBuilder.toString();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		String className = this.getClass().getSimpleName();
		stringBuilder.append(className);
		stringBuilder.append("(");
		stringBuilder.append(evaluateCommand);
		stringBuilder.append(")");
		stringBuilder.append(" />");
		return(stringBuilder.toString());
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		return(ObjectUtils.evaluateObjectToDefault(evaluateCommand, templarContext));
	}
}
