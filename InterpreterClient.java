public class InterpreterClient {

	private InterpreterEngine interpreterEngine;

	public InterpreterClient(InterpreterEngine interpreterEngine) {
		this.interpreterEngine = interpreterEngine;
	}

	public String interpret(String inputData,Process p) {
		Expression expression = null;
		if (inputData.contains("printFromTo")) {
			expression = new printFromToExpression(inputData, p);
		}
		else if (inputData.contains("print")) {
			expression = new PrintExpression(inputData, p);
		}
		
		else if(inputData.contains("input")){
			expression = new InputExpression(inputData, p);
		}

		else if(inputData.contains("readFile")){
			expression = new readFileExpression(inputData, p);

		}	
		
		else if(inputData.contains("assign")){
			expression = new AssignExpression(inputData, p);
		}
		
		else if(inputData.contains("writeFile")){
			expression = new writeFileExpression(inputData, p);

		}
	
		else {
			throw new RuntimeException(inputData + " is not valid expression!!");
		}

		String result = null;
		try {
			result = expression.interpret(interpreterEngine, p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}