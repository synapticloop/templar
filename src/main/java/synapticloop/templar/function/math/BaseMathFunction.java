package synapticloop.templar.function.math;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.TemplarContext;

public abstract class BaseMathFunction extends Function {

	public BaseMathFunction(int numArgs) {
		super(numArgs);
	}

	@Override
	public abstract Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException;

	protected Number getNumber(String numberString) {
		if(numberString.contains(".")) {
			return(new Double(numberString));
		} else {
			return(new Long(numberString));
		}
	}

}
