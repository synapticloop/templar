package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2013 synapticloop.
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
import synapticloop.templar.utils.TemplarContext;

public abstract class Function {
	private int numArgs = 0;

	protected Function() {}
	
	public Function(int numArgs) {
		this.numArgs = numArgs;
	}

	/**
	 * Verify that the arguments match the required number
	 * 
	 * @param args the array of arguments
	 * 
	 * @return whether the number of args equals the passed in number
	 */
	protected boolean verifyArgumentLength(Object[] args) {
		if(null == args) {
			return(false);
		} else {
			return(args.length == numArgs);
		}
	}

	/**
	 * Verify that there are the correct number of arguments and that they are all
	 * non-null
	 * 
	 * @param args the arguments
	 * 
	 * @return whether the number of arguments are correct and that they are all
	 *   non-null.
	 */
	protected boolean verifyNonNullArgumentLength(Object[] args) {
		if(verifyArgumentLength(args)) {
			for (Object object : args) {
				if(null == object) {
					return(false);
				} 
			}
			return(true);
		}
		return(false);
	}

	public abstract Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException;

}
