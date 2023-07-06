public class writeFileExpression implements Expression {

	 private String expression;	
		public writeFileExpression(String expression,Process p) {
			this.expression=expression;
		}
		
		public String interpret (InterpreterEngine interpreterEngine,Process p) {
			return interpreterEngine.writeFile(expression,p);
		}

}
