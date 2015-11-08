package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2015 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionEqual extends Function {
	public FunctionEqual() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);

		if(null == argZero) {
			if(null == argOne) {
				return(true);
			} else {
				return(false);
			}
		} else {
			if(null == argOne) {
				return(false);
			} else {
				return coercion(argZero, argOne);
			}
		}
	}

	private Object coercion(Object argZero, Object argOne) {
		// at this point - we need to be able to determine which type the first 
		// object is, and whether we need to be able to do integers as well 
		if(argZero instanceof Integer) {
			// try to coerce the second arg to an integer
			try {
				return (((Integer)argZero).intValue() == new Integer(argOne.toString()).intValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		} else if(argZero instanceof Float) {
			// try to coerce the second arg to an float
			try {
				return (((Float)argZero).floatValue() == new Float(argOne.toString()).floatValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		} else if(argZero instanceof Double) {
			// try to coerce the second arg to an double
			try {
				return (((Double)argZero).doubleValue() == new Double(argOne.toString()).doubleValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		} else if(argZero instanceof Boolean) {
			// try to coerce the second arg to an double
			try {
				return (((Boolean)argZero).booleanValue() == new Boolean(argOne.toString()).booleanValue());
			} catch(NumberFormatException nfex) {
				return(false);
			}
		}
		return(argOne.equals(argZero));
	}
}
