public class InputExpression implements Expression {
	private String expression;
	 public InputExpression(String expression,Process p) {
			this.expression=expression;
		}
	public String interpret(InterpreterEngine interpreterEngine,Process p) {
		
		return interpreterEngine.Input(expression,p);
	}
}