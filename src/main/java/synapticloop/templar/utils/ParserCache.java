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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import synapticloop.templar.token.Token;

public class ParserCache {
	private static Map<String, List<Token>> parser_cache = new ConcurrentHashMap<String, List<Token>>();

	private ParserCache() {}

	public static boolean getIsInCache(String md5Hash) {
		return(parser_cache.containsKey(md5Hash));
	}

	public static List<Token> getCached(String md5Hash) {
		return(parser_cache.get(md5Hash));
	}
}
