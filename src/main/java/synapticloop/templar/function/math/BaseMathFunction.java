package synapticloop.templar.function.math;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;

public abstract class BaseMathFunction extends Function {

	public BaseMathFunction(int numArgs) {
		super(numArgs);
	}

	public BaseMathFunction(int numArgs, int numArgsMax) {
		super(numArgs, numArgsMax);
	}

	/**
	 * Get a number from the passed in string, either a double, or a Long
	 * 
	 * @param numberString the number, expressed as a string
	 * 
	 * @return the parsed number
	 * 
	 * @throws FunctionException if the string could not be parsed into a number
	 */
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
