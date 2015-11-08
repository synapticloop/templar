package synapticloop.templar.function.string;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionIndexOf extends Function {

	public FunctionIndexOf() {
		super(2);
	}

	@Override
	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(!verifyArgumentLength(args)) {
			throw new FunctionException("The 'indexOf' function requires exactly two (2) argument.");
		}

		Object argZero = ObjectUtils.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectUtils.evaluateObjectToDefault(args[1], templarContext);
		if(null == argZero || null == argOne) {
			throw new FunctionException("Could not evaluate arguments to String, arguments were: " + args[0] + ", " + args[1] + ", values: " + argZero + ", " + argOne);
		}

		return(argZero.toString().indexOf(argOne.toString()));
	}
}
