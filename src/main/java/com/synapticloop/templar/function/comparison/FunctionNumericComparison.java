package com.synapticloop.templar.function.comparison;

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
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.utils.TemplarContext;

/**
 * This is the abstract base for all number comparisons
 *
 * @author synapticloop
 */
public abstract class FunctionNumericComparison extends Function {
	protected boolean hasError = false;
	protected Number arg1 = null;
	protected Number arg2 = null;

	public FunctionNumericComparison() {
		super(2);
	}

	/**
	 * Evaluate whether the objects are numeric or not
	 *
	 * @param args The arguments to evaluate
	 * @param templarContext The templar context to look up the values (if required)
	 * @throws FunctionException If the arguments are not coercible to a number
	 */
	public void evaluateNumeric(Object[] args, TemplarContext templarContext) throws FunctionException {
		hasError = false;
		if(verifyNonNullArgumentLength(args)) {
			try {
				Object arg1Object = ObjectHelper.evaluateObjectToDefault(args[0].toString().trim(), templarContext);
				if(arg1Object instanceof Number) {
					arg1 = ((Number) arg1Object).longValue();
				} else {
					arg1 = Long.decode((String)arg1Object);
				}

				Object arg2Object = ObjectHelper.evaluateObjectToDefault(args[1].toString().trim(), templarContext);
				if(arg2Object instanceof Number) {
					arg2 = ((Number) arg2Object).longValue();
				} else {
					arg2 = Long.decode((String)arg2Object);
				}
			} catch(NumberFormatException | ClassCastException ex) {
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
