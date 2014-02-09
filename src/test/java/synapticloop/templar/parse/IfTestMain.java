package synapticloop.templar.parse;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;


public class IfTestMain {
	public static void main(String[] args) {
		try {
			new Parser("src/test/template/if-test.templar");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
