package synapticloop.templar.utils;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.exception.RenderException;

public class ObjectUtilsTest {
	@Before
	public void setup() {
		initMocks(this);
	}

	@Test(expected=RenderException.class)
	public void testNoMoreTokens() throws RenderException {
		ObjectUtils.evaluateObject("", null);
	}

}
