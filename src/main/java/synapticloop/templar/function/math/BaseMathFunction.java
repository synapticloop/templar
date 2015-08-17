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

	protected Number getNumber(String numberString) throws FunctionException {
		if(numberString.contains(".")) {
			try {
				return(new Double(numberString));
			} catch (NumberFormatException nfex) {
				throw new FunctionException("Could not coerce '" + numberString + "' to a number.");
			}
		} else {
			try {
				return(new Long(numberString));
			} catch (NumberFormatException nfex) {
				throw new FunctionException("Could not coerce '" + numberString + "' to a number.");
			}
		}
	}

}
