package synapticloop.templar.parse;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;

public class EvaluationTestMain {
	public static void main(String[] args) {
		Parser parser;
		try {
			parser = new Parser("src/test/template/evaluation-test.templar");
			System.out.println(parser.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
