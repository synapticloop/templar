package com.synapticloop.templar.utils;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

public class LoopStatusBean {
	private boolean first;
	private boolean last;
	private int index;
	private int offset;

	/**
	 * Constructor for the loop status bean, which holds all of the information
	 * about the status of the loop as it is iterated.
	 * 
	 * @param first whether this is the first iteration
	 * @param last whether this is the last iteration
	 * @param index what the index is
	 * @param offset what the offset is
	 */
	public LoopStatusBean(boolean first, boolean last, int index, int offset) {
		this.first = first;
		this.last = last;
		this.index = index;
		this.offset = offset;
	}

	public boolean getFirst() { return(first); }
	public void setFirst(boolean first) { this.first = first; }
	public boolean getLast() { return(last); }
	public void setLast(boolean last) { this.last = last; }
	public int getIndex() { return(index); }
	public void setIndex(int index) { this.index = index; }
	public int getOffset() { return(offset); }
	public void setOffset(int offset) { this.offset = offset; }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("first: " + first + "\n");
		stringBuilder.append("last: " + last + "\n");
		stringBuilder.append("index: " + index + "\n");
		stringBuilder.append("offset: " + offset + "\n");
		return(stringBuilder.toString());
	}
}
