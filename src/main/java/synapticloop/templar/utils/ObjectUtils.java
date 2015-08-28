package synapticloop.templar.utils;

/*
 * Copyright (c) 2012-2015 synapticloop.
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringTokenizer;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.RenderException;

public class ObjectUtils {
	private static final String[] METHOD_PREFIXES = {"get", "is", "has", ""};

	private ObjectUtils() {}


	public static Object evaluateObject(String commandLine, TemplarContext templarContext) throws RenderException {
		if(null == templarContext) {
			throw new RenderException("TemplarContext is null, no lookups will be available.");
		}

		StringTokenizer stringTokenizer = new StringTokenizer(commandLine, ".");
		Object object = null;

		// the first one is the reference to the bean
		if(stringTokenizer.hasMoreTokens()) {
			String beanToken = stringTokenizer.nextToken();

			if(!templarContext.containsKey(beanToken)) {
				// we couldn't find the object in the context
				throw new RenderException("Could not find object in context '" + commandLine + "'.");
			} else {
				object = templarContext.get(beanToken);
			}

			if(!stringTokenizer.hasMoreTokens()) {
				// we don't want to call a method on it
				return(object);
			}

			// bit of reflection here
			boolean foundMethod = false;

			while(stringTokenizer.hasMoreTokens()) {
				String nextToken = stringTokenizer.nextToken();

				Method invokeMethod = findMethod(object, nextToken);
				if(null != invokeMethod) {
					foundMethod = true;
					int parameterCount = invokeMethod.getParameterCount();
					switch (parameterCount) {
					case 0:
						object = invokeObjectMethod(object, invokeMethod);
						break;
					case 1:
						object = invokeObjectMethod(object, invokeMethod, new Object[] { nextToken });
						break;
					default:
						// at this point, the method that we have found has more than 1 parameter - we
						// don't support this as the do notation doesn't handle it
						throw new RenderException("Cannot invoke method with more than 1 parameter, tryied to invoke method: '" + invokeMethod.toGenericString() + ".");
					}
				} else {
					foundMethod = false;
				}
			}

			if(!foundMethod) {
				throw new RenderException("Could not find method for command instruction '" + commandLine + "'.");
			}
		}

		return(object);
	}


	private static Object invokeObjectMethod(Object object, Method invokeMethod) throws RenderException {
		try {
			return(invokeMethod.invoke(object, new Object[] {}));
		} catch (IllegalArgumentException iaex) {
			throw new RenderException(iaex.getMessage(), iaex);
		} catch (IllegalAccessException iaex) {
			throw new RenderException(iaex.getMessage(), iaex);
		} catch (InvocationTargetException itex) {
			throw new RenderException(itex.getMessage(), itex);
		}
	}

	private static Object invokeObjectMethod(Object object, Method invokeMethod, Object[] parameters) throws RenderException {
		try {
			return(invokeMethod.invoke(object, parameters));
		} catch (IllegalArgumentException iaex) {
			throw new RenderException(iaex.getMessage(), iaex);
		} catch (IllegalAccessException iaex) {
			throw new RenderException(iaex.getMessage(), iaex);
		} catch (InvocationTargetException itex) {
			throw new RenderException(itex.getMessage(), itex);
		}
	}

	public static Object evaluateObjectToDefault(Object object, TemplarContext templarContext) {
		if(null == object) {
			return(null);
		} else {
			// we need to know whether it starts and ends with a "'" or '"' character, if so
			// we will return it as a string, else we look it up in the context
			if(isQuoted(object)) {
				return(deQuote((String)object));
			}

			Object evaluatedObject = null;
			try {
				evaluatedObject = evaluateObject(object.toString(), templarContext);
			} catch (RenderException sterex) {
				// WARNING - THIS IS GOING TO BE REMOVED AT SOME POINT IN TIME!
				// at this point all actual literal values should have been quoted...
				// maybe we will leave this in
				if(null == evaluatedObject && null != object) {
					return(object);
				}
			}
			return(evaluatedObject);
		}
	}

	public static Boolean evaluateObjectToDefaultBoolean(Object object, TemplarContext templarContext) {
		Object evaluateObjectToDefault = null;
		boolean inverse = false;
		if(object instanceof String) {
			String temp = (String)object;
			if(null != temp && temp.startsWith("!")) {
				inverse = true;
				temp = temp.substring(1);
			}
			evaluateObjectToDefault = evaluateObjectToDefault(temp, templarContext);
		} else {
			evaluateObjectToDefault = evaluateObjectToDefault(object, templarContext);
		}

		if(evaluateObjectToDefault instanceof Boolean) {
			if(inverse) {
				return(!((Boolean) evaluateObjectToDefault).booleanValue());
			} else {
				return(((Boolean) evaluateObjectToDefault).booleanValue());
			}
		}

		return(null);
	}

	public static Object parseAndExecuteCommandLine(TemplarContext templarContext, String commandLine) throws RenderException {

		String parseableCommandline = commandLine;
		if(commandLine.startsWith("!")) {
			parseableCommandline = commandLine.substring(1);
		}

		Object object = null;
		if(parseableCommandline.startsWith("fn:")) {
			// yay - we have a function - get the functionName
			int startArgs = parseableCommandline.indexOf("[");
			if(startArgs == -1) {
				throw new RenderException("Could not find function argument start token of '[' for function '" + parseableCommandline + "'.");
			}
			int endArgs = parseableCommandline.indexOf("]");
			if(endArgs == -1) {
				throw new RenderException("Could not find function argument end token of ']' for function '" + parseableCommandline + "'.");
			}

			// get the arguments as strings, then convert them into objects
			String functionName = parseableCommandline.substring(3, startArgs);
			String[] args = parseableCommandline.substring(startArgs +1, endArgs).split(",");
			if(null == args || args.length == 0) {
				throw new RenderException("Could not parse arguments for function '" + commandLine + "'.");
			}

			Object[] objectArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				String string = args[i];

				if(null != string) {
					string = string.trim();
				}
				// at this point - evaluate the token
				objectArgs[i] = ObjectUtils.evaluateObject(string, templarContext);
			}

			try {
				object = templarContext.invokeFunction(functionName, objectArgs, templarContext);
			} catch (FunctionException fex) {
				throw new RenderException("Command " + parseableCommandline + "' exception, " + fex.getMessage(), fex);
			}
		} else {
			object = ObjectUtils.evaluateObject(parseableCommandline, templarContext);
		}

		return(object);
	}

	/**
	 * Go through the METHOD_PREFIXES array and find the method that is called
	 * METHOD_PREFIXES[i] + reference
	 *
	 * @param object the object to look up the methods on
	 * @param reference the reference
	 *
	 * @return the method, or null if not found
	 * @throws RenderException 
	 */
	private static Method findMethod(Object object, String reference) throws RenderException {
		Method returnMethod = null;
		if(object instanceof Map<?, ?>) {
			try {
				returnMethod = object.getClass().getMethod(reference);
				if(null != returnMethod) {
					return(returnMethod);
				}
			} catch (NoSuchMethodException nsmex) {
				// fall through
			} catch (SecurityException sex) {
				// fall through
			}

			try {
				// it could be that we are looking up a direct call on the object

				returnMethod = object.getClass().getMethod("get", Object.class);
				return(returnMethod);
			} catch (NoSuchMethodException nsmex) {
				throw new RenderException("Could not find 'get' method on Map object instance", nsmex);
			} catch (SecurityException sex) {
				throw new RenderException("Could not find 'get' method on Map object instance", sex);
			}
		}

		for (int i = 0; i < METHOD_PREFIXES.length; i++) {
			String methodPrefix = METHOD_PREFIXES[i];

			returnMethod = getMethod(object, methodPrefix + reference);

			if(null != returnMethod) {
				return(returnMethod);
			}
		}
		return(returnMethod);
	}

	private static Method getMethod(Object object, String methodReference) throws RenderException {
		// look up the reference...
		try {
			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				if(method.getName().compareToIgnoreCase(methodReference) == 0) {
					return(method);
				}
			}
		} catch (NullPointerException npex) {
			throw new RenderException("Got Null Pointer for method '" + methodReference + "' on object '" + object + "'.", npex);
		}
			

		return(null);
	}

	public static boolean isQuoted(Object object) {
		if(object instanceof String) {
			String string = (String)object;
			return((string.startsWith("'") && string.endsWith("'")) || (string.startsWith("\"") && string.endsWith("\"")));
		}
		return(false);
	}

	public static String deQuote(String quotedString) {
		if((quotedString.startsWith("'") && quotedString.endsWith("'")) || (quotedString.startsWith("\"") && quotedString.endsWith("\""))) {
			return(quotedString.substring(1, quotedString.length() -1));
		}
		return(quotedString);
	}
}
