package synapticloop.templar.function.string;
import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.FunctionException;

public class FunctionToJsonTest {
	private FunctionToJson functionToJson;

	@Before
	public void setup() {
		functionToJson = new FunctionToJson();
	}

	@Test
	public void testJsonArray() throws FunctionException {
		Object evaluateFunction = functionToJson.evaluateFunction(new Object[] {"[]"}, null);
		assertTrue(evaluateFunction instanceof JSONArray);
	}

	@Test
	public void testJsonArrayWithValues() throws FunctionException {
		Object evaluateFunction = functionToJson.evaluateFunction(new Object[] {"[1, 2, 3]"}, null);
		assertTrue(evaluateFunction instanceof JSONArray);
	}

	@Test
	public void testJsonObject() throws FunctionException {
		Object evaluateFunction = functionToJson.evaluateFunction(new Object[] {"{}"}, null);
		assertTrue(evaluateFunction instanceof JSONObject);
	}

	@Test
	public void testJsonObjectWithValues() throws FunctionException {
		Object evaluateFunction = functionToJson.evaluateFunction(new Object[] {"{\"J\": \"S\", \"one\": 2}"}, null);
		assertTrue(evaluateFunction instanceof JSONObject);
	}

}
