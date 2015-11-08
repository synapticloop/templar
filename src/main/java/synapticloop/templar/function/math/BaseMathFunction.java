package synapticloop.templar.function.math;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;

public abstract class BaseMathFunction extends Function {

	public BaseMathFunction(int numArgs) {
		super(numArgs);
	}

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
