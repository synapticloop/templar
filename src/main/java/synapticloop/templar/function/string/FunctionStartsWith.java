package synapticloop.templar.function.string;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

/*
 * Copyright (c) 2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

public class FunctionStartsWith extends Function {

	public FunctionStartsWith() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);
		if(null == argZero || null == argOne) {
			throw new FunctionException("Could not evaluate arguments to String, arguments were: " + args[0] + ", " + args[1] + ", values: " + argZero + ", " + argOne);
		}

		return(argOne.toString().startsWith(argZero.toString()));
	}

}
