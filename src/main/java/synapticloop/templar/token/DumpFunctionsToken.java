package synapticloop.templar.token;

/*
 * Copyright (c) 2012-2017 synapticloop.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ParserHelper;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class DumpFunctionsToken extends CommandToken {
	private static final long serialVersionUID = -5395690577077526893L;

	public DumpFunctionsToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		boolean foundEndToken = ParserHelper.consumeToEndToken(stringTokenizer, stringBuilder);

		if(!foundEndToken) {
			throw new ParseException("Unable to find the closing dump function token '}'", this);
		}
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		if(null == templarContext) {
			templarContext = new TemplarContext();
		}

		// time to dump the templar context function map
		StringBuilder stringBuilder = new StringBuilder();

		// go through the context and grab all of the objects
		Map<String, Function> functionMap = templarContext.getFunctionMap();
		Iterator<String> iterator = functionMap.keySet().iterator();
		while(iterator.hasNext()) {
			String fnName = iterator.next();
			Function function = functionMap.get(fnName);

			stringBuilder.append(function.getClass().getSimpleName());
			stringBuilder.append(" fn:");
			stringBuilder.append(fnName);
			stringBuilder.append("[ <#numArgs: ");
			stringBuilder.append(function.getNumArgs());
			if(function.getNumArgsMax() != -1) {
				stringBuilder.append(" or ");
				stringBuilder.append(function.getNumArgsMax());
			}
			stringBuilder.append("> ]");
			List<String> aliasesForFunction = getAliasesForFunction(fnName, templarContext);
			for(int i = 0; i < aliasesForFunction.size(); i++) {
				if(i == 0) {
					stringBuilder.append(" aliased as (");
				}

				stringBuilder.append(aliasesForFunction.get(i));

				if(i == aliasesForFunction.size() -1) {
					stringBuilder.append(")");
				} else {
					stringBuilder.append(", ");
				}
			}
			stringBuilder.append("\n");
		}

		return(stringBuilder.toString());
	}

	private List<String> getAliasesForFunction(String name, TemplarContext templarContext) {
		List<String> aliasList = new ArrayList<String>();

		Map<String, String> functionAliasMap = templarContext.getFunctionAliasMap();

		// go through the function alias map and find all of the values that match the alias
		Set<String> keySet = functionAliasMap.keySet();
		for (String key : keySet) {
			if(name.equals(functionAliasMap.get(key))) {
				aliasList.add(key);
			}
		}

		return(aliasList);
	}

	@Override
	public String toString() {
		return(super.toString("DUMPFUNCTIONS"));
	}

}
