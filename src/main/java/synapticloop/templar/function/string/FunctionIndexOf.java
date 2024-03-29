package synapticloop.templar.function.string;

/*
 * Copyright (c) 2012-2023 synapticloop.
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
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionIndexOf extends Function {

	public FunctionIndexOf() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);
		if(null == argZero || null == argOne) {
			throw new FunctionException("Could not evaluate arguments to String, arguments were: " + args[0] + ", " + args[1] + ", values: " + argZero + ", " + argOne);
		}

		return(argZero.toString().indexOf(argOne.toString()));
	}
}
