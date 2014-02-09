package synapticloop.templar.function;

/*
 * Copyright (c) 2012-2013 synapticloop.
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class FunctionFormatDate extends Function {

	public Object evaluate(Object[] args, TemplarContext templarContext) throws FunctionException {
		boolean hasError = false;
		Date date = null;
		String dateFormat = null;

		if(null != args) {
			switch (args.length) {
			case 1:
				// we only have a format string
				date = new Date(System.currentTimeMillis());
				dateFormat = (String)args[0];
				break;
			case 2:
				// we have a possible date and a format string
				Object dateOrTimestamp = ObjectUtils.evaluateObjectToDefault(args[0], templarContext);
				if(dateOrTimestamp instanceof Date) {
					date = (Date)dateOrTimestamp;
				} else if (dateOrTimestamp instanceof java.sql.Date) {
					date = new Date(((java.sql.Date)dateOrTimestamp).getTime());
				} else if (dateOrTimestamp instanceof String) {
					// try to coerce to Long
					date = new Date(Long.decode((String)ObjectUtils.evaluateObjectToDefault(args[0].toString().trim(), templarContext)));
				} else if (dateOrTimestamp instanceof Long) {
					date = new Date((Long)dateOrTimestamp);
				} else if (dateOrTimestamp instanceof Timestamp) {
					date = new Date(((Timestamp)dateOrTimestamp).getTime());
				} else {
					hasError = true;
				}
				dateFormat = (String)args[1];
				break;
			default:
				hasError = true;
				break;
			}
		} else {
			hasError = true;
		}

		// now to do the processing
		if(hasError) {
			throw new FunctionException("The function 'fmtDate' takes either one argument which is the format string, or two which are the date/long timestamp followed by the format string.");
		}

		if(null == dateFormat) {
			throw new FunctionException("Cannot format with 'null' argument.");
		}

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
			return(simpleDateFormat.format(date));
		} catch(IllegalArgumentException iaex) {
			throw new FunctionException("Could not format date, message was: " + iaex.getMessage());
		}
	}

}
