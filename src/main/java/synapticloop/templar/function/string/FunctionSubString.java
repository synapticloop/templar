package synapticloop.templar.function.string;

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
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionSubString extends Function {

	public FunctionSubString() {
		super(2, 3);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);

		int from = 0;
		int to = 0;

		try {
			from = Integer.parseInt(argOne.toString());
		} catch(NumberFormatException ex) {
			throw new FunctionException("Could not evaluate arguments to Integer, arguments was: " + args[1] + ", value: " + argOne);
		}

		Object argTwo = null;
		if(args.length == 3) {
			argTwo = ObjectHelper.evaluateObjectToDefault(args[2], templarContext);
			try {
				to = Integer.parseInt(argTwo.toString());
			} catch(NumberFormatException ex) {
				throw new FunctionException("Could not evaluate arguments to Integer, arguments was: " + args[2] + ", value: " + argTwo);
			}
		}

		// at this point we should be good to go
		if(args.length == 3) {
			return(argZero.toString().substring(from, to));
		} else {
			return(argZero.toString().substring(from));
		}
	}

}
