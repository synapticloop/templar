package synapticloop.templar.utils;

import static org.junit.Assert.*;

public class TestUtils {
	public static void checkRenderContents(String render) {
		if(render.contains("FATAL ERROR")) {
			assertTrue("Found string 'FATAL ERROR' in rendered content", false);
		} else {
			assertTrue(true);
		}
	}
}
