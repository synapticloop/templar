package synapticloop.templar.function;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public abstract class FunctionNumericComparison extends Function {
	protected boolean hasError = false;
	protected Long arg1 = null;
	protected Long arg2 = null;

	public FunctionNumericComparison() {
		super(2);
	}

	public void evaluateNumeric(Object[] args, TemplarContext templarContext) throws FunctionException {
		hasError = false;
		if(verifyNonNullArgumentLength(args)) {
			try {
				Object arg1Object = ObjectUtils.evaluateObjectToDefault(args[0].toString().trim(), templarContext);
				if(arg1Object instanceof Number) {
					arg1 = new Long(((Number)arg1Object).longValue());
				} else {
					arg1 = Long.decode((String)arg1Object);
				}

				Object arg2Object = ObjectUtils.evaluateObjectToDefault(args[1].toString().trim(), templarContext);
				if(arg2Object instanceof Number) {
					arg2 = new Long(((Number)arg2Object).longValue());
				} else {
					arg2 = Long.decode((String)arg2Object);
				}
			} catch(NumberFormatException nfex) {
				hasError = true;
			} catch(ClassCastException ccex) {
				hasError = true;
			}
		} else {
			hasError = true;
		}

		if(hasError) {
			throw new FunctionException("The function '>=, >, <, <=' takes exactly two arguments, both of which must be coercible to a number.");
		}
	}

}
