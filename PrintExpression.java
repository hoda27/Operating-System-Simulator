public class PrintExpression implements Expression {
	 private String expression;
		
		public PrintExpression(String expression,Process p) {
			this.expression=expression;
		}
		public String interpret (InterpreterEngine interpreterEngine,Process p) {
			return interpreterEngine.print(expression,p);
		}
}
