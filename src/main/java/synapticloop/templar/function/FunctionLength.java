package synapticloop.templar.function;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.TemplarContext;

public class FunctionLength extends Function {
	protected static final String[] FIELDS = {"size", "length"};
	protected static final String[] METHODS = {"getSize", "getLength"};

	public FunctionLength() {
		super(1);
	}

	@SuppressWarnings("rawtypes")
	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		if(verifyArgumentLength(args)) {
			Object object = args[0];
			// so what do we have here...
			if (object instanceof Collection) {
				return(((Collection)object).size());
			} else if (object instanceof Object[]) {
				return(((Object[])object).length);
			} else {
				// let us find the methods/accessors that are size or length
				int length = getLength(object);
				if(length != -1) {
					return(length);
				} else {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("Don't know how to find the length of a '");
					stringBuilder.append(object.getClass().getCanonicalName());
					stringBuilder.append("', we looked for the following fields: {");

					for (int i = 0; i < FIELDS.length; i++) {
						String string = FIELDS[i];
						stringBuilder.append("'");
						stringBuilder.append(string);
						stringBuilder.append("'");

						if(i < FIELDS.length -1) {
							stringBuilder.append(", ");
						}
					}

					stringBuilder.append("}, and the following methods: {");

					for (int i = 0; i < METHODS.length; i++) {
						String string = METHODS[i];
						stringBuilder.append("'");
						stringBuilder.append(string);
						stringBuilder.append("()'");

						if(i < METHODS.length -1) {
							stringBuilder.append(", ");
						}
					}

					stringBuilder.append("}.");
					throw new FunctionException(stringBuilder.toString());
				}
			}
		} else {
			throw new FunctionException("The 'length' function requires exactly one (1) argument.");
		}
	}

	/**
	 * Tries to find the length or size method/fields on an object
	 *
	 * @param object the object to reflect upon
	 * @return the length, or -1 if not found.
	 */

	private int getLength(Object object) {
		int length = findMethod(object);
		if(length != -1) {
			return(length);
		}

		return(findField(object));
	}

	private int findMethod(Object object) {
		for (int i = 0; i < METHODS.length; i++) {
			String methodString = METHODS[i];
			try {
				Method method = object.getClass().getMethod(methodString);
				Object invoke = method.invoke(object, (Object[])null);
				if(invoke instanceof Integer) {
					return((Integer)invoke);
				}
			} catch (SecurityException jlsex) {
			} catch (NoSuchMethodException jlnsmex) {
			} catch (IllegalArgumentException jliaex) {
			} catch (IllegalAccessException jliacex) {
			} catch (InvocationTargetException jlitex) {
			}
		}
		return(-1);
	}

	private int findField(Object object) {
		for (int i = 0; i < FIELDS.length; i++) {
			String fieldString = FIELDS[i];
			try {
				Field field = object.getClass().getField(fieldString);
				return(field.getInt(object));
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return(-1);
	}

}
