package synapticloop.templar.parse;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

public class EvaluationTestMain {
	public static void main(String[] args) {
		try {
			new Parser("src/test/template/evaluation-test.templar");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
