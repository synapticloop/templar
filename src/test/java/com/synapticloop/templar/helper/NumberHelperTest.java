package com.synapticloop.templar.helper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.synapticloop.templar.helper.NumberHelper;

public class NumberHelperTest {
	
	@Test
	public void testMethodName() {
		assertEquals("one hundred", NumberHelper.convert(100));
		assertEquals("thirty one thousand seven hundred forty five", NumberHelper.convert(31745));
		assertEquals("nine thousand eight hundred seventy five", NumberHelper.convert(9875));
		assertEquals("zero", NumberHelper.convert(0));
	}
	
}
