package synapticloop.templar.function.math;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

/**
 * Test whether the passed in number (or something that can be coerced to a 
 * number) is even.  In the case of a double, this is coerced to a long 
 * and then tested
 */
public class FunctionEven extends BaseMathFunction {

	public FunctionEven() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			Object argZero = ObjectUtils.evaluateObjectToDefault(args[0], templarContext);
			Number argZeroNumber = getNumber(argZero.toString());
			return((argZeroNumber.longValue() & 1) == 0);
		}

		throw new FunctionException("The function 'even' takes exactly one argument, which must be coercible to a Number.");
	}

}
