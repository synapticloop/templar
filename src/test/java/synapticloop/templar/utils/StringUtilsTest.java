package synapticloop.templar.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StringUtilsTest {
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testEscapeHtml() {
		assertEquals("&quot;&amp;&lt;&gt;", StringUtils.escapeHtml("\"&<>"));
	}

	@Test
	public void testEscapeHtmlWithText() {
		assertEquals("This &quot;so called&quot; &lt;html&gt; is nice &amp; easy", StringUtils.escapeHtml("This \"so called\" <html> is nice & easy"));
	}
}
