package synapticloop.templar.render;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.bean.EvaluationBean;
import synapticloop.templar.bean.EvaluationChildBean;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class IfRenderTest {
	@Test
	public void testSimpleIfRendering() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-if-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		EvaluationChildBean evaluationChildBean = new EvaluationChildBean("childName");
		EvaluationBean evaluationBean = new EvaluationBean("test name", true, 19, System.currentTimeMillis(), evaluationChildBean);

		EvaluationChildBean evaluationChildBean2 = new EvaluationChildBean("childName2");
		EvaluationBean evaluationBean2 = new EvaluationBean("test name", false,17, System.currentTimeMillis(), evaluationChildBean2);

		templarContext.add("bean", evaluationBean);
		templarContext.add("bean2", evaluationBean2);

		String render = parser.render(templarContext);

		assertEquals("Simple if test\n" + 
			"--------------\n" +
			"\n" +
			"isAlive: true\n" +
			"the first test is in the positive: this bean isAlive\n" +
			"and you should see the text above 'this bean isAlive'\n" +
			"the next test is in the negative: \n" +
			"and you should NOT see the above text 'this bean is dead'\n" +
			"isAlive 2nd time is a charm: false\n" +
			"the first test is in the positive: \n" +
			"and you should NOT see the text above 'this bean2 isAlive'\n" +
			"the next test is in the negative: this bean2 is dead\n" +
			"and you should see the above text 'this bean2 is dead'\n" +
			"Nested If Test\n" +
			"--------------\n" +
			"\n" +
			"bean is alive\n" +
			"now test for age\n" +
			"this bean is of age\n" +
			"this bean2 is NOT of age\n" +
			"3 is greater than 1\n" +
			"1 is less than 3\n" +
			"3 is not less than 1\n"
			, render);
	}

	@Test
	public void testIncorrectParsing() {
		try {
			new Parser("{if");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here}");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here}something");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here}something{");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here}something{endif");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here}something{endif}");
			// we should have got an exception
			assertTrue(true);
		} catch (ParseException stepex) {
			assertTrue(false);
		}
	}

	@Test
	public void testIncorrectElseIfRendering() {
		try {
			new Parser("{if statement here}something\n{else{endif}");
			// we should have got an exception
			assertTrue(false);
		} catch (ParseException stepex) {
			assertTrue(true);
		}

		try {
			new Parser("{if statement here}something\n{else}{endif}");
			// we should have got an exception
			assertTrue(true);
		} catch (ParseException stepex) {
			assertTrue(false);
		}
}
}
