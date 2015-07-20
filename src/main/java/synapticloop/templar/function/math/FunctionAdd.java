package synapticloop.templar.function.math;

/*
 * Copyright (c) 2012-2014 synapticloop.
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
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionAdd extends Function {

	public FunctionAdd() {
		super(2);
	}

	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		Double argZeroDouble = null;
		Double argOneDouble = null;
		Long argZeroLong = null;
		Long argOneLong = null;

		StringBuffer errorMessages = new StringBuffer();
		if(verifyArgumentLength(args)) {
			Object argZero = ObjectUtils.evaluateObjectToDefault(args[0], templarContext);
			Object argOne = ObjectUtils.evaluateObjectToDefault(args[1], templarContext);

			try {
				argZeroDouble = Double.valueOf((String)argZero);
			} catch (ClassCastException ccex) {
				errorMessages.append("The first argument '" + argZero + "' cannot be coerced to a double.");
			}

			try {
				argOneDouble = (Double)argOne;
			} catch (ClassCastException ccex) {
				errorMessages.append("The second argument '" + argOne + "' cannot be coerced to a double.");
			}

			if(errorMessages.length() == 0) {
//				return(argZeroDouble.doubleValue() + argOneDouble.doubleValue())
			}
			if(argZero instanceof Double) 
			// this is wrong - they will be string
			if(argZero instanceof Number && argOne instanceof Number) {
				// all good to go
				return(add((Number)argZero, (Number)argOne));
			}
		}

		// TODO - should really be 
		throw new FunctionException("The function '=', or 'equal' takes exactly two arguments, both of which must be coercible to a Double.");
	}

	private Number add(Number numberOne, Number numberTwo) {
		if(numberOne instanceof Double) {
			
		} else {
			// only can be a long
			if(numberTwo instanceof Long) {
				// long + long
				
			} else {
				// long + double
				return(numberOne.longValue() + numberTwo.longValue());
			}
		}
		if(numberOne instanceof Double || numberTwo instanceof Double){
			return ((Number)numberOne).doubleValue() + ((Number)numberTwo).doubleValue();
		}

		if(numberOne instanceof Float || numberTwo instanceof Float){
			return ((Number)numberOne).floatValue() + ((Number)numberTwo).floatValue();
		}

		if(numberOne instanceof Long || numberTwo instanceof Long){
			return ((Number)numberOne).longValue() + ((Number)numberTwo).longValue();
		}

		return ((Number)numberOne).intValue() + ((Number)numberTwo).intValue();
	}
}
