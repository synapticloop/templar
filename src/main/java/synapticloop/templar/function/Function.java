package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2016 synapticloop.
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
import synapticloop.templar.helper.NumberHelper;
import synapticloop.templar.utils.TemplarContext;

public abstract class Function {
	private int numArgs = 0;
	private int numArgsMax = -1;

	protected Function() {}

	/**
	 * Instantiate the function
	 * 
	 * @param numArgs the number of requested arguments that this function takes
	 */
	public Function(int numArgs) {
		this.numArgs = numArgs;
	}

	/**
	 * Create a function with a minimum and maximum number of arguments
	 *  
	 * @param numArgs the minimum number of arguments allowed
	 * @param numArgsMax the maximum number of arguments allowed
	 */
	public Function(int numArgs, int numArgsMax) {
		this.numArgs = numArgs;
		this.numArgsMax  = numArgsMax;
	}

	/**
	 * Evaluate the function, first confirming the number of arguments
	 * 
	 * @param alias the alias that was used to invoke this function
	 * @param args the array of arguments that are passed in
	 * @param templarContext the templar context for any lookups
	 * 
	 * @return the evaluation object
	 * 
	 * @throws FunctionException if something went horribly wrong with the evaluation
	 */

	public Object evaluate(String alias, Object[] args, TemplarContext templarContext) throws FunctionException {
		// we check to ensure that the args are correct
		if(!verifyArgumentLength(args)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("The '");
			stringBuilder.append(alias);
			stringBuilder.append("' function requires ");
			if(getNumArgsMax() != -1) {
				stringBuilder.append("between ");
			} else {
				stringBuilder.append("exactly ");
			}
			stringBuilder.append(NumberHelper.convert(numArgs));
			stringBuilder.append(" (");
			stringBuilder.append(numArgs);
			if(getNumArgsMax() != -1) {
				stringBuilder.append(") and ");
				stringBuilder.append(NumberHelper.convert(numArgsMax));
				stringBuilder.append(" (");
				stringBuilder.append(numArgsMax);
			}
			stringBuilder.append(") arguments.");
			throw new FunctionException(stringBuilder.toString());
		}

		// if we get to this point - do the actual function
		return(evaluateFunction(args, templarContext));
	}

	/**
	 * Evaluate the function
	 * 
	 * @param args the array of arguments that are passed in
	 * @param templarContext the templar context for any lookups
	 * 
	 * @return the evaluation object
	 * 
	 * @throws FunctionException if something went horribly wrong with the evaluation
	 */
	protected abstract Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException;

	/**
	 * Verify that the arguments match the required number
	 * 
	 * @param args the array of arguments
	 * 
	 * @return whether the number of args equals the passed in number
	 */
	private boolean verifyArgumentLength(Object[] args) {
		if(null == args) {
			return(false);
		} else {
			int argsLength = args.length;
			if(numArgsMax == -1) {
				return(argsLength == getNumArgs());
			} else {
				return(argsLength >= getNumArgs() && argsLength <= getNumArgsMax());
			}
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

	/**
	 * Return the number of arguments that this function expects
	 * 
	 * @return the number of arguments that needs to be passed in to the function
	 */
	public int getNumArgs() { return numArgs; }

	/**
	 * Return the maximum number of arguments that this function expects, or -1 
	 * if the maximum number of arguments is not set.
	 * 
	 * @return the maximum number of arguments that this function will allow, or 
	 *   -1 if not set.
	 */
	public int getNumArgsMax() { return numArgsMax; }

}
