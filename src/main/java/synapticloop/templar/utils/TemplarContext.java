package synapticloop.templar.utils;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.function.FunctionAnd;
import synapticloop.templar.function.FunctionEqual;
import synapticloop.templar.function.FunctionFalse;
import synapticloop.templar.function.FunctionFormatDate;
import synapticloop.templar.function.FunctionGreaterThan;
import synapticloop.templar.function.FunctionGreaterThanEqual;
import synapticloop.templar.function.FunctionIsNotNull;
import synapticloop.templar.function.FunctionIsNull;
import synapticloop.templar.function.FunctionLength;
import synapticloop.templar.function.FunctionLessThan;
import synapticloop.templar.function.FunctionLessThanEqual;
import synapticloop.templar.function.FunctionNotEqual;
import synapticloop.templar.function.FunctionOr;
import synapticloop.templar.function.FunctionSize;
import synapticloop.templar.function.FunctionTrue;

public class TemplarContext {
	private Map<String, Object> context = new HashMap<String, Object>();
	private TemplarConfiguration templarConfiguration = new TemplarConfiguration();

	private static Map<String, Function> functionMap = new HashMap<String, Function>();
	static {
		functionMap.put("null", new FunctionIsNull());
		functionMap.put("notNull", new FunctionIsNotNull());
		functionMap.put("!Null", new FunctionIsNotNull());
		functionMap.put("!null", new FunctionIsNotNull());
		functionMap.put("=", new FunctionEqual());
		functionMap.put("equal", new FunctionEqual());
		functionMap.put("<>", new FunctionNotEqual());
		functionMap.put("not=", new FunctionNotEqual());
		functionMap.put("!=", new FunctionNotEqual());
		functionMap.put("notEqual", new FunctionNotEqual());
		functionMap.put(">", new FunctionGreaterThan());
		functionMap.put("gt", new FunctionGreaterThan());
		functionMap.put(">=", new FunctionGreaterThanEqual());
		functionMap.put("gte", new FunctionGreaterThanEqual());
		functionMap.put("<", new FunctionLessThan());
		functionMap.put("lt", new FunctionLessThan());
		functionMap.put("<=", new FunctionLessThanEqual());
		functionMap.put("lte", new FunctionLessThanEqual());
		functionMap.put("length", new FunctionLength());
		functionMap.put("size", new FunctionSize());
		functionMap.put("fmtDate", new FunctionFormatDate());
		functionMap.put("false", new FunctionFalse());
		functionMap.put("true", new FunctionTrue());
		functionMap.put("and", new FunctionAnd());
		functionMap.put("&", new FunctionAnd());
		functionMap.put("or", new FunctionOr());
		functionMap.put("|", new FunctionOr());
	}

	public TemplarContext() {
		// do nothing
	}

	public TemplarContext(TemplarConfiguration templarConfiguration) {
		this.templarConfiguration = templarConfiguration;
	}

	/**
	 * Create a new Templar context from an existing one.  The existing context
	 * will be cloned and unchanged.
	 * 
	 * @param existingContext
	 */
	@SuppressWarnings("unchecked")
	public TemplarContext(TemplarContext existingContext) {
		this.context = (Map<String, Object>) ((HashMap<String, Object>)existingContext.getContext()).clone();

		Set<Entry<String, Object>> entrySet = existingContext.getContext().entrySet();
		for (Entry<String,Object> entry : entrySet) {
			this.context.put(entry.getKey(), entry.getValue());
		}
	}

	public void add(String key, Object value) {
		context.put(key, value);
	}

	public Object get(String key) {
		return(context.get(key));
	}

	public boolean containsKey(String key) {
		return(context.containsKey(key));
	}

	protected Map<String, Object> getContext() {
		return(context);
	}

	public void setTemplarConfiguration(TemplarConfiguration templarConfiguration) {
		this.templarConfiguration = templarConfiguration;
	}

	public TemplarConfiguration getTemplarConfiguration() {
		return templarConfiguration;
	}

	public boolean hasFunction(String name) {
		return(functionMap.containsKey(name));
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
		if(functionMap.containsKey(name)) {
			throw new FunctionException("Function with name '" + name + "' is already registered to the class '" + functionMap.get(name).getClass().getCanonicalName() + "'.\nCurrently registered functions:\n" + getRegisteredFunctions());
		} else {
			functionMap.put(name, function);
		}
	}

	public Map<String, Function> getFunctionMap() {
		return(functionMap);
	}

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
			return(functionMap.get(name).evaluate(args, templarContext));
		} else {
			throw new FunctionException("Function with name '" + name + "' does not exist.");
		}
	}

	public void clear() {
		context.clear();
	}

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
