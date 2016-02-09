package synapticloop.templar.function.string;

import java.util.ArrayList;
import java.util.Arrays;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionSplit extends Function {

	public FunctionSplit() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		String argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext).toString();
		String argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext).toString();

		return(new ArrayList<String>(Arrays.asList(argZero.split(argOne))));
	}

}
