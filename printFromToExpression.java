public class printFromToExpression implements Expression {
  private String expression;
	public printFromToExpression(String expression,Process p) {
		this.expression=expression;
	}
	public String interpret (InterpreterEngine interpreterEngine,Process p) {
		return interpreterEngine.printFromTo(expression,p);
	}
	}


