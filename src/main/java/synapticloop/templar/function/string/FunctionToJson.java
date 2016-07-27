package synapticloop.templar.function.string;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionToJson extends Function {

	public FunctionToJson() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);

		if(argZero instanceof String) {
			String contents = ((String)argZero).trim();
			char charAt = contents.charAt(0);
			switch (charAt) {
			case '{':
				try {
					return(new JSONObject(contents));
				} catch(JSONException ex) {
					// fall through
				}
			case '[':
				try {
					return(new JSONArray(contents));
				} catch(JSONException ex) {
					// fall through
				}
			default:
				// fall through
			}
		}
		throw new FunctionException("Could not parse JSON, arguments were: " + args[0] + ", values: " + argZero);
	}
}
