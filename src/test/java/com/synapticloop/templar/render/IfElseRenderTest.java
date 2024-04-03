package com.synapticloop.templar.render;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.synapticloop.templar.Parser;
import com.synapticloop.templar.bean.EvaluationBean;
import com.synapticloop.templar.bean.EvaluationChildBean;
import com.synapticloop.templar.bean.OuterBean;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarConfiguration;
import com.synapticloop.templar.utils.TemplarContext;

public class IfElseRenderTest {
	@Test
	public void testSimpleIfRendering() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-if-else-test.templar");
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
		templarContext.add("outerBean", new OuterBean());

		assertEquals("<IF@1:2 (fn:>['3','1'])>3 > 1<ELSE@1:23 />3 !> 1</IF@1:31><NEWLINE@1:32 />\n<IF@2:2 (fn:>['1','3'])>1 > 3<ELSE@2:23 />1 !> 3</IF@2:31>\n", parser.toString());
		String render = parser.render(templarContext);
		assertEquals("3 > 1\n1 !> 3", render);
	}
}
