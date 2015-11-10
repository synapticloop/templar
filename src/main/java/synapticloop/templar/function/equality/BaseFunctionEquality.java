package synapticloop.templar.function.equality;

import synapticloop.templar.function.Function;

public abstract class BaseFunctionEquality extends Function {
	public BaseFunctionEquality(int numArgs) {
		super(numArgs);
	}

	protected boolean coercion(Object argZero, Object argOne) {
		// at this point - we need to be able to determine which type the first 
		// object is, and whether we need to be able to do integers as well 
		if(argZero instanceof Integer) {
			// try to coerce the second arg to an integer
			try {
				return (((Integer)argZero).intValue() == Integer.valueOf(argOne.toString()).intValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		} else if(argZero instanceof Float) {
			// try to coerce the second arg to an float
			try {
				return (((Float)argZero).floatValue() == Float.valueOf(argOne.toString()).floatValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		} else if(argZero instanceof Double) {
			// try to coerce the second arg to an double
			try {
				return (((Double)argZero).doubleValue() == Double.valueOf(argOne.toString()).doubleValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		} else if(argZero instanceof Boolean) {
			// try to coerce the second arg to an double
			try {
				return (((Boolean)argZero).booleanValue() == Boolean.valueOf(argOne.toString()).booleanValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		}
		return(argOne.equals(argZero));
	}
}
