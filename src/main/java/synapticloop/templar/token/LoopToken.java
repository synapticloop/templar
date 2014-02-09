package synapticloop.templar.token;

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

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.LoopStatusBean;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;
import synapticloop.templar.utils.ObjectUtils;

public class LoopToken extends CommandToken {

	public LoopToken(String value, StringTokenizer stringTokenizer) throws ParseException {
		super(value, stringTokenizer);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String token = stringTokenizer.nextToken();
				if(token.equals("}")) {
					// now we need to go through and tokenise the inner tokeniser...;
					this.commandLine = stringBuilder.toString().trim();
					this.addChildTokens(Tokeniser.tokenise(stringTokenizer));

					// here the next token should be a '}'
					if(stringTokenizer.hasMoreTokens()) {
						stringTokenizer.nextToken();
					} else {
						throw new ParseException("Could not find the end loop token '}'", this);
					}

					// check to make sure that we have returned on an endloop token
					Token endingToken = childTokens.get(childTokens.size() -1);
					if(!(endingToken instanceof EndLoopToken)) {
						throw new ParseException("Could not find the end loop token 'endloop'", this);
					}

					return;
				} else {
					stringBuilder.append(token);
				}
			}
		}
		this.commandLine = stringBuilder.toString().trim();
	}

	@SuppressWarnings({ "rawtypes" })
	public String render(TemplarContext templarContext) throws RenderException {
		StringBuilder stringBuilder = new StringBuilder();

		String[] commandSplit = commandLine.split(" as ");

		if(commandSplit.length != 2) {
			throw new RenderException("command in incorrect format '" + commandLine + "', should be 'something as somethingElse'.");
		}

		Object object = ObjectUtils.evaluateObject(commandSplit[0], templarContext);

		String contextAs = commandSplit[1];
		if(object instanceof Collection) {
			Collection collection = (Collection)object;
			boolean first = true;
			int offset = 0;

			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Object object2 = iterator.next();
				templarContext.add(contextAs, object2);
				templarContext.add(contextAs + "Status", new LoopStatusBean(first, !iterator.hasNext(), offset + 1, offset));
				for (Token token : this.childTokens) {
					stringBuilder.append(token.render(templarContext));
				}
				first = false;
				offset++;
			}
		} else if (object instanceof Object[]) {
			Object[] objectArray = (Object[])object;
			boolean first = true;
			int offset = 0;

			for (int i = 0; i < objectArray.length; i++) {
				Object object3 = objectArray[i];
				templarContext.add(contextAs, object3);
				templarContext.add(contextAs + "Status", new LoopStatusBean(first, (i+1 == objectArray.length), offset + 1, offset));
				for (Token token : this.childTokens) {
					stringBuilder.append(token.render(templarContext));
				}
				first = false;
				offset++;
				
			}
			
		} else {
			throw new RenderException("Don't know how to iterate over an object of type '" + object.getClass().getCanonicalName() + "'.");
		}

		return (stringBuilder.toString());
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<LOOP");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(commandLine.trim());
		stringBuilder.append(")>");
		for (Token token : this.childTokens) {
			stringBuilder.append(token.toString());
		}
		return (stringBuilder.toString());
	}
}
