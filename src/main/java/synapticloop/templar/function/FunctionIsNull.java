package synapticloop.templar.function;

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

public class FunctionIsNull extends Function {
	public FunctionIsNull() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(null != args[0]) {
			// see if we can get if from the context
			Object evaluateObject = ObjectHelper.evaluateObjectToDefault(args[0].toString(), templarContext);
			return(null == evaluateObject);
		} else {
			return(true);
		}
	}
}
