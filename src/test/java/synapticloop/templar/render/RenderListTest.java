package synapticloop.templar.render;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.bean.EvaluationBean;
import synapticloop.templar.bean.EvaluationChildBean;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class RenderListTest {
	@Test
	public void render() throws RenderException, ParseException {
		Parser parser = new Parser("src/test/template/render-list-test.templar");

		TemplarContext templarContext = new TemplarContext();
		EvaluationChildBean evaluationChildBean = new EvaluationChildBean("childName");
		EvaluationBean evaluationBean = new EvaluationBean("test name", true, 19, System.currentTimeMillis(), evaluationChildBean);

		EvaluationChildBean evaluationChildBean2 = new EvaluationChildBean("childName2");
		EvaluationBean evaluationBean2 = new EvaluationBean("test name", false,17, System.currentTimeMillis(), evaluationChildBean2);

		templarContext.add("bean", evaluationBean);
		templarContext.add("bean2", evaluationBean2);

		assertEquals(parser.toString(), "Simple loop test<NEWLINE@1:18 />\n" + 
				"\n" + 
				"----------------<NEWLINE@2:18 />\n" + 
				"\n" + 
				"<NEWLINE@3:2 />\n" + 
				"\n" + 
				"<LOOP@4:2 (bean.ArrayList as value)>\n" + 
				"	<EVAL@5:3 (value)><NEWLINE@5:4 />\n" + 
				"\n" + 
				"</LOOP@6:2>\n" + 
				"\n" + 
				"<LOOP@8:2 (bean.childBeans as childBean)>\n" + 
				"	<EVAL@9:3 (childBean.name)><NEWLINE@9:4 />\n" + 
				"\n" + 
				"</LOOP@10:2>\n");

		assertEquals(parser.render(templarContext), "Simple loop test\n" + 
				"----------------\n" + 
				"\n" + 
				"one\n" + 
				"two\n" + 
				"three\n" + 
				"four\n" + 
				"5\n" + 
				"vi\n" + 
				"jose\n" + 
				"hose b\n");

	}
}
