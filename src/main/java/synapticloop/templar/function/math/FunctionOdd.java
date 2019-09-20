package synapticloop.templar.function.math;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

/**
 * Test whether the passed in number (or something that can be coerced to a 
 * number) is odd.  In the case of a double, this is coerced to a long 
 * and then tested
 */

public class FunctionOdd extends BaseMathFunction {

	public FunctionOdd() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Number argZeroNumber = getNumber(argZero.toString());
		return((argZeroNumber.longValue() & 1) == 1);
	}

}