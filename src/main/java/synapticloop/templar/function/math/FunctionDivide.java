package synapticloop.templar.function.math;

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
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionDivide extends BaseMathFunction {

	public FunctionDivide() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		// the first thing we want to do is to determine the argument types

		Object argZero = ObjectUtils.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectUtils.evaluateObjectToDefault(args[1], templarContext);

		Number argZeroNumber = getNumber(argZero.toString());
		Number argOneNumber = getNumber(argOne.toString());

		// if either of the numbers are doubles, return a double
		if(argZeroNumber instanceof Double || argOneNumber instanceof Double) {
			return(argZeroNumber.doubleValue() / argOneNumber.doubleValue());
		} else {
			return(argZeroNumber.longValue() / argOneNumber.longValue());
		}
	}
}
