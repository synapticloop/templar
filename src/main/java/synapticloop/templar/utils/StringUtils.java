package synapticloop.templar.utils;

public class StringUtils {
	public static String escapeHtml(String source) {
		if(source==null) {
			return ""; 
		}

		StringBuilder stringBuilder = new StringBuilder(""); 
		char[] chars = source.toCharArray(); 

		for(int i=0;i<chars.length;i++) {
			boolean found=true; 
			switch(chars[i]) {
				case 60:
					stringBuilder.append("&lt;"); // <
					break;
				case 62:
					stringBuilder.append("&gt;"); // >
					break;
				case 34:
					stringBuilder.append("&quot;"); // "
					break;
				case 38:
					stringBuilder.append("&amp;"); // &
					break;
				default:
					found=false;
					break;
			} 
			if(!found) {
				stringBuilder.append(chars[i]); 
			}

		} 
		return stringBuilder.toString();
	}
}
