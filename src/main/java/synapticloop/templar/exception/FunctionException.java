package synapticloop.templar.exception;

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

/**
 * Thrown when there was a problem with the function, which could include
 * <ul>
 *   <li>verifying the number of arguments passed through to a function, or</li>
 *   <li>the evaluation process failed, or</li>
 *   <li>the registration of the function failed</li>
 * </ul>
 */
public class FunctionException extends Exception {
	private static final long serialVersionUID = 5066739168531962194L;

	/**
	 * Create a function exception
	 * 
	 * @param message the exception message
	 */
	public FunctionException(String message) {
		super(message);
	}

	/**
	 * Create a function exception
	 * 
	 * @param message the message
	 * @param throwable the root cause
	 */
	public FunctionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
