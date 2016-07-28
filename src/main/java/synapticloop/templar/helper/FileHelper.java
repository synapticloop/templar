package synapticloop.templar.helper;

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

import java.io.File;

/**
 * The file helper is a simple utility class for file information
 * 
 * @author synapticloop
 *
 */
public class FileHelper {
	private FileHelper() {}

	/**
	 * Return whether a file can be read - which ensures that the file exists, is
	 * readable and is actually a file
	 * 
	 * @param file the file to check
	 * 
	 * @return whether the file exists, is readable and is a file
	 */
	public static boolean canReadFile(File file) {
		return(file.exists() && file.canRead() && file.isFile());
	}
}
