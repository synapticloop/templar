package synapticloop.templar.utils;

import static org.junit.Assert.*;

public class TestUtils {
	public static void checkRenderContents(String render) {
		assertFalse(render.contains("FATAL ERROR"));
	}
}
