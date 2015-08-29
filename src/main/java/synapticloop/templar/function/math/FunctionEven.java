package synapticloop.templar.function.math;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionEven extends BaseMathFunction {

	public FunctionEven() {
		super(1);
	}

	@Override
	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			Object argZero = ObjectUtils.evaluateObjectToDefault(args[0], templarContext);
			Number argZeroNumber = getNumber(argZero.toString());
			return((argZeroNumber.longValue() & 1) == 0);
		}

		throw new FunctionException("The function 'even' takes exactly one arguments, which must be coercible to a Number.");
	}

}
