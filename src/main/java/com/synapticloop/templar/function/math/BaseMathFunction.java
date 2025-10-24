package com.synapticloop.templar.function.math;

/*
 * Copyright (c) 2012-2025 synapticloop.
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

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.Function;

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
				return(Double.valueOf(numberString));
			} catch (NumberFormatException nfex) {
				throw new FunctionException("Could not coerce '" + numberString + "' to a number.");
			}
		} else {
			try {
				return(Long.valueOf(numberString));
			} catch (NumberFormatException nfex) {
				throw new FunctionException("Could not coerce '" + numberString + "' to a number.");
			}
		}
	}

}
