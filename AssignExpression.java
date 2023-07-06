public class AssignExpression implements Expression {
	 private String expression;
	 public AssignExpression(String expression,Process p) {
			this.expression=expression;
		}
	
	public String interpret(InterpreterEngine interpreterEngine,Process p) {
		
		return interpreterEngine.assign(expression,p);
	}	
}
