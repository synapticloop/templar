package com.synapticloop.templar.function.bool;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.templar.function.Function;
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.utils.TemplarContext;

public abstract class FunctionBoolean extends Function {

	public FunctionBoolean(int numArgs) {
		super(numArgs);
	}

	protected Object getEvaluatedArgument(Object[] args, TemplarContext templarContext, boolean testValue) {
		if(null != args[0]) {
			Object evaluateObject = null;
			// if the evaluated object is already a boolean, then we will just test the value, rather than trying to 
			// look it up in the context.
			if(args[0] instanceof Boolean) {
				evaluateObject = (Boolean)args[0];
			} else {
				// see if we can get if from the context
				evaluateObject = ObjectHelper.evaluateObjectToDefault(args[0].toString(), templarContext);
			}

			if(evaluateObject instanceof Boolean) {
				return(testValue == ((Boolean) evaluateObject).booleanValue());
			} else {
				return(false);
			}
		} else {
			return(false);
		}
	}

}
