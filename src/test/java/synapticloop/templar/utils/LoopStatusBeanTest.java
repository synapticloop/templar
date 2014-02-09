package synapticloop.templar.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import org.mockito.Mock;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class LoopStatusBeanTest {
	private LoopStatusBean loopStatusBean;

	@Before
	public void setup() {
	}

	@Test
	public void testSetterGetter() {
		loopStatusBean = new LoopStatusBean(true, false, 100, 58);
		assertEquals(true, loopStatusBean.getFirst());
		assertEquals(false, loopStatusBean.getLast());
		assertEquals(100, loopStatusBean.getIndex());
		assertEquals(58, loopStatusBean.getOffset());
		assertEquals(loopStatusBean.toString(), "first: true\nlast: false\nindex: 100\noffset: 58\n");
	}
}
