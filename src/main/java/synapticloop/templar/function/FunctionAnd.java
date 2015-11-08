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
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionAnd extends Function {

	public FunctionAnd() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			Boolean argZero = ObjectUtils.evaluateObjectToDefaultBoolean(args[0], templarContext);
			// no need to evaluate any further
			if(!argZero.booleanValue()) {
				return(false);
			}

			Boolean argOne = ObjectUtils.evaluateObjectToDefaultBoolean(args[1], templarContext);
			if(null == argZero || null == argOne) {
				throw new FunctionException("Could not evaluate arguments to a Boolean, arguments were: " + args[0] + ", " + args[1] + ", values: " + argZero + ", " + argOne);
			} else {
				return(argZero && argOne);
			}
		} else {
			throw new FunctionException("The function 'and' takes exactly two arguments.");
		}
	}
}
