package synapticloop.templar.parse;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;


public class EvaluationTest {
	Parser parser;

	@Before
	public void setup() {
	}

	@Test
	public void testRender() throws ParseException {
		parser = new Parser("src/test/template/evaluation-test.templar");
		assertEquals("<EVAL@1:2 (this.should.all)/>\n" +
				"\n" +
				"be\n" +
				"eval<EVAL@4:6 (uation)/> tokens\n",
				parser.toString());
	}
}
