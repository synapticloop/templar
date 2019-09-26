package synapticloop.templar.function.math;

/*
 * Copyright (c) 2012-2019 synapticloop.
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

/**
 * Test whether the passed in number (or something that can be coerced to a 
 * number) is odd.  In the case of a double, this is coerced to a long 
 * and then tested
 */

public class FunctionOdd extends BaseMathFunction {

	public FunctionOdd() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Number argZeroNumber = getNumber(argZero.toString());
		return((argZeroNumber.longValue() & 1) == 1);
	}

}
