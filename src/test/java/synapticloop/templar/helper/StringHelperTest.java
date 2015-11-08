package synapticloop.templar.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import synapticloop.templar.helper.StringHelper;

public class StringHelperTest {
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testEscapeHtml() {
		assertEquals("&quot;&amp;&lt;&gt;", StringHelper.escapeHtml("\"&<>"));
	}

	@Test
	public void testEscapeHtmlWithText() {
		assertEquals("This &quot;so called&quot; &lt;html&gt; is nice &amp; easy", StringHelper.escapeHtml("This \"so called\" <html> is nice & easy"));
	}

	@Test
	public void testNull() {
		assertEquals("", StringHelper.escapeHtml(null));
	}
}
