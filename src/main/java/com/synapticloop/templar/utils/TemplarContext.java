package com.synapticloop.templar.utils;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.Function;
import com.synapticloop.templar.function.FunctionFormatDate;
import com.synapticloop.templar.function.FunctionIsNotNull;
import com.synapticloop.templar.function.FunctionIsNull;
import com.synapticloop.templar.function.FunctionLength;
import com.synapticloop.templar.function.bool.FunctionAnd;
import com.synapticloop.templar.function.bool.FunctionFalse;
import com.synapticloop.templar.function.bool.FunctionOr;
import com.synapticloop.templar.function.bool.FunctionTrue;
import com.synapticloop.templar.function.comparison.FunctionGreaterThan;
import com.synapticloop.templar.function.comparison.FunctionGreaterThanEqual;
import com.synapticloop.templar.function.comparison.FunctionLessThan;
import com.synapticloop.templar.function.comparison.FunctionLessThanEqual;
import com.synapticloop.templar.function.equality.FunctionEqual;
import com.synapticloop.templar.function.equality.FunctionNotEqual;
import com.synapticloop.templar.function.math.FunctionAdd;
import com.synapticloop.templar.function.math.FunctionDivide;
import com.synapticloop.templar.function.math.FunctionEven;
import com.synapticloop.templar.function.math.FunctionModulus;
import com.synapticloop.templar.function.math.FunctionMultiply;
import com.synapticloop.templar.function.math.FunctionOdd;
import com.synapticloop.templar.function.math.FunctionPower;
import com.synapticloop.templar.function.math.FunctionSubtract;
import com.synapticloop.templar.function.string.*;
import com.synapticloop.templar.function.util.FunctionInstanceOf;

public class TemplarContext {
	private Map<String, Object> context = new HashMap<String, Object>();
	private TemplarConfiguration templarConfiguration = new TemplarConfiguration();

	private static Map<String, Function> functionMap = new HashMap<String, Function>();
	static {

		// null operators
		functionMap.put("null", new FunctionIsNull()); // test whether the passed in parameter is null
		functionMap.put("notNull", new FunctionIsNotNull()); // test whether the passed in parameter is not null

		// boolean function operators
		functionMap.put("=", new FunctionEqual()); // test whether the passed in parameters are equal
		functionMap.put("<>", new FunctionNotEqual()); // test whether the passed in parameters are not equal
		functionMap.put(">", new FunctionGreaterThan()); // test whether the the first parameter is greater than the second
		functionMap.put(">=", new FunctionGreaterThanEqual()); // test whether the the first parameter is greater than or equal to the second
		functionMap.put("<", new FunctionLessThan());  // test whether the the first parameter is less than the second
		functionMap.put("<=", new FunctionLessThanEqual());  // test whether the the first parameter is less than or equal to the second

		// size operators
		functionMap.put("length", new FunctionLength()); // return the length/size of the passed in parameter

		// date operators
		functionMap.put("fmtDate", new FunctionFormatDate()); // format the date with the two parameters date, and format as a string

		// boolean test operators
		functionMap.put("false", new FunctionFalse()); // test whether the parameter is false
		functionMap.put("true", new FunctionTrue()); // test whether the parameter is true

		// logical operators
		functionMap.put("and", new FunctionAnd()); // logical AND function for the two parameters
		functionMap.put("&", new FunctionAnd()); // logical AND function for the two parameters
		functionMap.put("or", new FunctionOr()); // logical OR function for the two parameters
		functionMap.put("|", new FunctionOr()); // logical OR function for the two parameters

		// mathematical operators
		functionMap.put("+", new FunctionAdd()); // Mathematical ADDITION of two numbers
		functionMap.put("-", new FunctionSubtract()); // Mathematical SUBTRACTION of two numbers
		functionMap.put("*", new FunctionMultiply()); // Mathematical MULTIPLICATION of two numbers
		functionMap.put("/", new FunctionDivide()); // Mathematical DIVISION of two numbers
		functionMap.put("^", new FunctionPower()); // Mathematical EXPONENT of two numbers
		functionMap.put("%", new FunctionModulus()); // Mathematical MODULUS of two numbers

		// even and odd
		functionMap.put("even", new FunctionEven()); // Test whether the passed in number is even
		functionMap.put("odd", new FunctionOdd()); // Test whether the passed in number is odd

		// utilties
		functionMap.put("instanceOf", new FunctionInstanceOf()); // Test whether it is an instance of

		// string
		functionMap.put("indexOf", new FunctionIndexOf()); // get the index of strings
		functionMap.put("toJson", new FunctionToJson()); // convert a string into a JSON object
		functionMap.put("startsWith", new FunctionStartsWith()); // determine whether a string starts with another string
		functionMap.put("substring", new FunctionSubString()); // return a substring of a string
		functionMap.put("uppercase", new FunctionUppercase()); // return the uppercase of the string
		functionMap.put("lowercase", new FunctionLowercase()); // return the lowercase of the string
		functionMap.put("pluralise", new FunctionPluralise()); // return the lowercase of the string

	}

	private static Map<String, String> functionAliasMap = new HashMap<String, String>();
	static {
		functionAliasMap.put("isnull", "null");
		functionAliasMap.put("isNull", "null");

		functionAliasMap.put("!Null", "notNull");
		functionAliasMap.put("!null", "notNull");
		functionAliasMap.put("isnotnull", "notNull");
		functionAliasMap.put("isNotNull", "notNull");
		functionAliasMap.put("notnull", "notNull");

		functionAliasMap.put("equal", "=");
		functionAliasMap.put("eq", "=");

		functionAliasMap.put("not=", "<>");
		functionAliasMap.put("ne", "<>");
		functionAliasMap.put("notEqual", "<>");

		functionAliasMap.put("gt", ">");
		functionAliasMap.put("gt=", ">=");
		functionAliasMap.put("lt", "<");
		functionAliasMap.put("lte", "<=");

		functionAliasMap.put("size", "length");

		functionAliasMap.put("lowerCase", "lowercase");
		functionAliasMap.put("upperCase", "uppercase");
		functionAliasMap.put("pluralize", "pluralise");


	}

	/**
	 * Create a new templar context with the default configuration
	 */
	public TemplarContext() {
		// do nothing
	}

	/**
	 * Create a new templar context with the passed in configuration
	 * 
	 * @param templarConfiguration the templar configuration
	 */
	public TemplarContext(TemplarConfiguration templarConfiguration) {
		this.templarConfiguration = templarConfiguration;
	}

	/**
	 * Create a new Templar context from an existing one.  The existing context
	 * will be cloned and unchanged.
	 * 
	 * @param existingContext the existing context to clone
	 */
	@SuppressWarnings("unchecked")
	public TemplarContext(TemplarContext existingContext) {
		this.context = (Map<String, Object>) ((HashMap<String, Object>)existingContext.getContext()).clone();

		Set<Entry<String, Object>> entrySet = existingContext.getContext().entrySet();
		for (Entry<String,Object> entry : entrySet) {
			this.context.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Add an object to the templar context.  If there is an existing value for the key, it will be over-ridden.
	 * 
	 * @param key the key to add
	 * @param value the value to store at the key location
	 */
	public void add(String key, Object value) {
		context.put(key, value);
	}


	/**
	 * Get a value from the context with the specified key.  If the key does not exist, null will be returned
	 * 
	 * @param key the key to lookup
	 * @return the value associated with the key or null if it does not exist
	 */
	public Object get(String key) {
		return(context.get(key));
	}

	/**
	 * Test whether the context has a specific key
	 * 
	 * @param key the key to test
	 * @return whether there is an object with the specified key
	 */
	public boolean containsKey(String key) {
		return(context.containsKey(key));
	}

	/**
	 * Return whether the templar context has a particular function registered to
	 * it, or has a function aliased to it
	 * 
	 * @param name the function name to lookup
	 * @return whether the templar context has that function registered to it.
	 */
	public boolean hasFunction(String name) {
		return(functionMap.containsKey(name) || functionAliasMap.containsKey(name));
	}

	/**
	 * Add a function to the context to be used in parsing, this should not
	 * include the 'fn:' part.
	 *
	 * @param name the name of the function to bind to
	 * @param function the function
	 *
	 * @throws FunctionException if the function has already been registered, it
	 *   also prints out a list of currently registered functions just to be nice.
	 */
	public void addFunction(String name, Function function) throws FunctionException {
		if(functionMap.containsKey(name) || functionAliasMap.containsKey(name)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Function with name '");
			stringBuilder.append(name);
			stringBuilder.append("' is already registered to the class '");
			stringBuilder.append(getFunction(name).getClass().getCanonicalName());
			stringBuilder.append("'.\nCurrently registered functions:\n");
			stringBuilder.append(getRegisteredFunctions());
			throw new FunctionException(stringBuilder.toString());
		} else {
			functionMap.put(name, function);
		}
	}

	/**
	 * Get the map of all registered functions keyed on function name
	 * 
	 * @return the map of all registered functions
	 */
	public Map<String, Function> getFunctionMap() { return(functionMap); }

	/**
	 * Get the map of all aliases that is key on the alias and the value is the
	 * actual registered function name in the function map
	 * 
	 * @return the map of all function aliases
	 */
	public Map<String, String> getFunctionAliasMap() { return(functionAliasMap); }

	private String getRegisteredFunctions() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Iterator<String> iterator = functionMap.keySet().iterator(); iterator.hasNext();) {
			String functionName = iterator.next();
			stringBuilder.append("\t'");
			stringBuilder.append(functionName);
			stringBuilder.append("':'");
			stringBuilder.append(functionMap.get(functionName).getClass().getCanonicalName());
			stringBuilder.append("'");
			if(iterator.hasNext()) {
				stringBuilder.append(", \n");
			}
		}
		stringBuilder.append("\n");
		return(stringBuilder.toString());
	}

	/**
	 * Invoke a particular function (whether it is an alias or otherwise) with 
	 * the passed in parameters and the templar context.
	 * 
	 * @param name the name of the function to invoke
	 * @param args the arguments to pass through to the function
	 * @param templarContext the templar context on which the function will be invoked
	 * 
	 * @return the return of the invoked function
	 * 
	 * @throws FunctionException if there was a problem invoking the function, or
	 *   no function was registered with that name
	 */
	public Object invokeFunction(String name, Object[] args, TemplarContext templarContext) throws FunctionException {
		if(null == args) {
			throw new FunctionException("Argument[] to a function cannot be null.");
		}

		if(hasFunction(name)) {
			// the evaluation of the arguments should have already been done
			Object[] parsedArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				Object object = args[i];
				if(null != object) {
					parsedArgs[i] = object;
				} else {
					parsedArgs[i] = null;
				}
			}
			return(getFunction(name).evaluate(name, args, templarContext));
		} else {
			throw new FunctionException("Function with name '" + name + "' does not exist.");
		}
	}

	/**
	 * Return the base function, which will also look up the aliase map for any 
	 * function that has more that one way of being referenced.
	 * 
	 * @param name The name of the function to look up
	 * 
	 * @return The name of the function that was looked up
	 */
	public static String getBaseFunction(String name) {
		if(functionAliasMap.containsKey(name)) {
			return(functionAliasMap.get(name));
		}

		return(name);
	}

	private Function getFunction(String name) {
		if(functionMap.containsKey(name)) {
			return(functionMap.get(name));
		}

		if(functionAliasMap.containsKey(name)) {
			return(functionMap.get(functionAliasMap.get(name)));
		}

		return(null);
	}

	/**
	 * Clear the templar context of all entries
	 */
	public void clear() {
		context.clear();
	}

	/**
	 * Get the complete templar context - with all of the key:value pairs in it
	 * 
	 * @return the templar context
	 */
	protected Map<String, Object> getContext() { return(context); }

	/**
	 * Set the templar configuration
	 * 
	 * @param templarConfiguration the configuration to set
	 */
	public void setTemplarConfiguration(TemplarConfiguration templarConfiguration) { this.templarConfiguration = templarConfiguration; }

	/**
	 * Get the templar configuration for this context
	 * 
	 * @return the templar configuration for this context
	 */
	public TemplarConfiguration getTemplarConfiguration() { return templarConfiguration; }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getClass().getSimpleName());
		stringBuilder.append("[");
		int i = 0;
		Set<Entry<String, Object>> entrySet = context.entrySet();
		for (Entry<String,Object> entry : entrySet) {
			stringBuilder.append("{");
			stringBuilder.append(entry.getKey());
			stringBuilder.append(":");
			stringBuilder.append(entry.getValue());
			stringBuilder.append("}");
			if(i < entrySet.size() -1) {
				stringBuilder.append(", ");
			}
			i++;
		}
		stringBuilder.append("]");
		return(stringBuilder.toString());
	}
}
