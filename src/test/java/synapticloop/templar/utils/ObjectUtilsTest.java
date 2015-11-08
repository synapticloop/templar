package synapticloop.templar.utils;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;

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

	@Test
	public void testIsQuoted() {
		assertTrue(ObjectUtils.isQuoted("\"\""));
		assertTrue(ObjectUtils.isQuoted("''"));
		assertTrue(ObjectUtils.isQuoted("\"quote 'me'\""));
		assertTrue(ObjectUtils.isQuoted("'quote me '\"\"'too'"));

		assertFalse(ObjectUtils.isQuoted("'"));
		assertFalse(ObjectUtils.isQuoted("\""));
		assertFalse(ObjectUtils.isQuoted("'hello\""));
		assertFalse(ObjectUtils.isQuoted("\"hello'"));
		assertFalse(ObjectUtils.isQuoted("'this only starts with a quote"));
		assertFalse(ObjectUtils.isQuoted("\"this only starts with a quote"));
		assertFalse(ObjectUtils.isQuoted("this only ends with a quote'"));
		assertFalse(ObjectUtils.isQuoted("this only ends with a quote\""));
	}

	@Test
	public void testDeQuote() {
		assertEquals("hello", ObjectUtils.deQuote("\"hello\""));
		assertEquals("hello", ObjectUtils.deQuote("'hello'"));
		assertEquals("hello", ObjectUtils.deQuote("hello"));
	}
}
