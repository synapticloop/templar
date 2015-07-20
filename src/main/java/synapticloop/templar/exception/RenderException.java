package synapticloop.templar.exception;

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

/**
 * Thrown when there was a problem rendering the parsed templar template.
 */

public class RenderException extends Exception {
	private static final long serialVersionUID = -5781431903609494963L;

	public RenderException(String message) {
		super(message);
	}

	public RenderException(String message, Exception rootCause) {
		super(message, rootCause);
	}
}
