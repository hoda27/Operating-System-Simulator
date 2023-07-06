import java.io.FileNotFoundException;

public class readFileExpression implements Expression {
	 private String expression;
	
		public readFileExpression(String expression,Process p) {
			this.expression=expression;
		}
		
		public String interpret (InterpreterEngine interpreterEngine,Process p)  {
			return interpreterEngine.readFile(expression,p);
		}
}
