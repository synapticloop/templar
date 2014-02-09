package synapticloop.templar.render;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.bean.EvaluationBean;
import synapticloop.templar.bean.EvaluationChildBean;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class RenderEvaluationTest {
	@Test
	public void testParse() throws ParseException, RenderException {
		Parser parser = new Parser("src/test/template/render-evaluation-test.templar");

		TemplarContext templarContext = new TemplarContext();
		EvaluationChildBean evaluationChildBean = new EvaluationChildBean("childName");
		EvaluationBean evaluationBean = new EvaluationBean("test name", true, 30, 1348083911423L, evaluationChildBean);
		templarContext.add("bean", evaluationBean);

		String render = parser.render(templarContext);
		assertEquals(render, "\n" + 
				"A Simple evaluation bean example/test\n" + 
				"-------------------------------------\n" + 
				"\n" + 
				"name: test name\n" + 
				"isAlive: true\n" + 
				"age: 30\n" + 
				"timeBorn: 1348083911423\n" + 
				"\n" + 
				"and for the simple child bean\n" + 
				"-----------------------------\n" + 
				"\n" + 
				"child name: childName\n");
	}

	@Test(expected = RenderException.class)
	public void testBadNestedEvaluationParse() throws ParseException, RenderException {
		Parser parser = new Parser("{bean.evaluationChildBean.name.missingMethod}");

		TemplarContext templarContext = new TemplarContext();
		EvaluationChildBean evaluationChildBean = new EvaluationChildBean("childName");
		EvaluationBean evaluationBean = new EvaluationBean("test name", true, 30, 1348083911423L, evaluationChildBean);
		templarContext.add("bean", evaluationBean);

		parser.render(templarContext);
	}

	@Test
	public void testFunctionEvaluationParse() throws ParseException, RenderException {
		Parser parser = new Parser("src/test/template/render-evaluation-function-test.templar");

		TemplarContext templarContext = new TemplarContext();
		String[] array = {"one", "two", "three"};
		templarContext.add("array", array);
		String render = parser.render(templarContext);
		assertEquals("3", render);
	}

}
