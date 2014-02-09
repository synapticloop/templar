package synapticloop.templar.utils;

import java.io.File;

public class FileUtils {
	public static boolean canReadFile(File file) {
		return(file.exists() && file.canRead() && file.isFile());
	}
}
