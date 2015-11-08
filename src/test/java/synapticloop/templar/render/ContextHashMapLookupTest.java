package synapticloop.templar.render;

import java.io.File;

import org.junit.Test;

import synapticloop.templar.Parser;
import synapticloop.templar.bean.EvaluationBean;
import synapticloop.templar.bean.EvaluationChildBean;
import synapticloop.templar.bean.OuterBean;
import synapticloop.templar.bean.SessionBean;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.TestUtils;

public class ContextHashMapLookupTest {
	@Test
	public void testSimpleIfRendering() throws ParseException, RenderException {
		File file = new File("src/test/resources/render-hashmap-lookup-test.templar");
		Parser parser = new Parser(file);

		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);
		TemplarContext templarContext = new TemplarContext(templarConfiguration);

		EvaluationChildBean evaluationChildBean = new EvaluationChildBean("childName");
		EvaluationBean evaluationBean = new EvaluationBean("test name", true, 19, System.currentTimeMillis(), evaluationChildBean);

		EvaluationChildBean evaluationChildBean2 = new EvaluationChildBean("childName2");
		EvaluationBean evaluationBean2 = new EvaluationBean("test name", false,17, System.currentTimeMillis(), evaluationChildBean2);

		SessionBean sessionBean = new SessionBean();
		sessionBean.addToSession("hello", "baby");
		sessionBean.addToSession("bean", evaluationBean);
		sessionBean.addToSession("bean2", evaluationBean2);
		sessionBean.addToSession("outerBean", new OuterBean());

		templarContext.add("session", sessionBean);

		String render = parser.render(templarContext);
		TestUtils.checkRenderContents(render);
	}

}
