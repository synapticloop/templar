package synapticloop.templar.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberUtilTest {
	
	@Test
	public void testMethodName() {
		assertEquals("one hundred", NumberUtil.convert(100));
		assertEquals("thirty one thousand seven hundred forty five", NumberUtil.convert(31745));
		assertEquals("nine thousand eight hundred seventy five", NumberUtil.convert(9875));
		assertEquals("zero", NumberUtil.convert(0));
	}
	
}
