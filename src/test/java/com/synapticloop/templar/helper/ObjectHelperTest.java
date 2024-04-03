package com.synapticloop.templar.helper;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;

import org.junit.Before;
import org.junit.Test;

import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.helper.ObjectHelper;

public class ObjectHelperTest {
	@Before
	public void setup() {
		initMocks(this);
	}

	@Test(expected=RenderException.class)
	public void testNoMoreTokens() throws RenderException {
		ObjectHelper.evaluateObject("", null);
	}

	@Test
	public void testIsQuoted() {
		assertTrue(ObjectHelper.isQuoted("\"\""));
		assertTrue(ObjectHelper.isQuoted("''"));
		assertTrue(ObjectHelper.isQuoted("\"quote 'me'\""));
		assertTrue(ObjectHelper.isQuoted("'quote me '\"\"'too'"));

		assertFalse(ObjectHelper.isQuoted("'"));
		assertFalse(ObjectHelper.isQuoted("\""));
		assertFalse(ObjectHelper.isQuoted("'hello\""));
		assertFalse(ObjectHelper.isQuoted("\"hello'"));
		assertFalse(ObjectHelper.isQuoted("'this only starts with a quote"));
		assertFalse(ObjectHelper.isQuoted("\"this only starts with a quote"));
		assertFalse(ObjectHelper.isQuoted("this only ends with a quote'"));
		assertFalse(ObjectHelper.isQuoted("this only ends with a quote\""));
	}

	@Test
	public void testDeQuote() {
		assertEquals("hello", ObjectHelper.deQuote("\"hello\""));
		assertEquals("hello", ObjectHelper.deQuote("'hello'"));
		assertEquals("hello", ObjectHelper.deQuote("hello"));
	}
}
