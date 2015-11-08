package synapticloop.templar.function.bool;

import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public abstract class FunctionBoolean extends Function {

	public FunctionBoolean(int numArgs) {
		super(numArgs);
	}

	protected Object getEvaluatedArgument(Object[] args, TemplarContext templarContext, boolean testValue) {
		if(null != args[0]) {
			Object evaluateObject = null;
			// if the evaluated object is already a boolean, then we will just test the value, rather than trying to 
			// look it up in the context.
			if(args[0] instanceof Boolean) {
				evaluateObject = (Boolean)args[0];
			} else {
				// see if we can get if from the context
				evaluateObject = ObjectHelper.evaluateObjectToDefault(args[0].toString(), templarContext);
			}

			if(evaluateObject instanceof Boolean) {
				return(testValue == ((Boolean) evaluateObject).booleanValue());
			} else {
				return(false);
			}
		} else {
			return(false);
		}
	}

}
