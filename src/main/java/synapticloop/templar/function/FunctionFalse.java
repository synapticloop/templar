package synapticloop.templar.function;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.TemplarContext;

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

public class FunctionFalse extends FunctionBoolean {
	public FunctionFalse() {
		super(1);
	}

	@Override
	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			return(getEvaluatedArgument(args, templarContext, false));
		} else {
			throw new FunctionException("The function 'false' takes exactly one argument.");
		}
	}
}