package com.synapticloop.templar.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.synapticloop.templar.helper.FileHelper;

public class FileHelperTest {
	@Mock private File mockFile;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void testCanReadFile() {
		when(mockFile.canRead()).thenReturn(true);
		when(mockFile.exists()).thenReturn(true);
		when(mockFile.isFile()).thenReturn(true);
		assertTrue(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(true);
		when(mockFile.exists()).thenReturn(false);
		when(mockFile.isFile()).thenReturn(true);
		assertFalse(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(true);
		when(mockFile.exists()).thenReturn(true);
		when(mockFile.isFile()).thenReturn(false);
		assertFalse(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(true);
		when(mockFile.exists()).thenReturn(false);
		when(mockFile.isFile()).thenReturn(false);
		assertFalse(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(false);
		when(mockFile.exists()).thenReturn(true);
		when(mockFile.isFile()).thenReturn(true);
		assertFalse(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(false);
		when(mockFile.exists()).thenReturn(false);
		when(mockFile.isFile()).thenReturn(true);
		assertFalse(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(false);
		when(mockFile.exists()).thenReturn(true);
		when(mockFile.isFile()).thenReturn(false);
		assertFalse(FileHelper.canReadFile(mockFile));

		when(mockFile.canRead()).thenReturn(false);
		when(mockFile.exists()).thenReturn(false);
		when(mockFile.isFile()).thenReturn(false);
		assertFalse(FileHelper.canReadFile(mockFile));
	}
}
