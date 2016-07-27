package synapticloop.templar.utils;

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

public class TemplarConfiguration {
	private boolean explicitNewLines = true;
	private boolean explicitTabs = true;
	private boolean ignoreWhitespace = true;

	public TemplarConfiguration() { }
	
	public TemplarConfiguration(boolean explicitNewLines, boolean explicitTabs, boolean ignoreWhitespace) {
		this.explicitNewLines = explicitNewLines;
		this.explicitTabs = explicitTabs;
		this.ignoreWhitespace = ignoreWhitespace;
	}

	public void setExplicitNewLines(boolean explicitNewLines) {
		this.explicitNewLines = explicitNewLines;
	}

	public boolean getExplicitNewLines() {
		return(this.explicitNewLines);
	}

	public void setExplicitTabs(boolean explicitTabs) {
		this.explicitTabs = explicitTabs;
	}

	public boolean getExplicitTabs() {
		return(this.explicitTabs);
	}

	public boolean getIgnoreWhitespace() {
		return(ignoreWhitespace);
	}

	public void setIgnoreWhitespace(boolean ignoreWhitespace) {
		this.ignoreWhitespace = ignoreWhitespace;
	}
}
