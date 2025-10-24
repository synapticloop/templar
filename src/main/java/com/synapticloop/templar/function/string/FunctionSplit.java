package com.synapticloop.templar.function.string;

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

import java.util.ArrayList;
import java.util.Arrays;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.Function;
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionSplit extends Function {

	public FunctionSplit() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		String argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext).toString();
		String argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext).toString();

		return(new ArrayList<String>(Arrays.asList(argZero.split(argOne))));
	}

}
