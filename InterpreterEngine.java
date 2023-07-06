import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class InterpreterEngine {

	public String printFromTo(String inputData, Process p) {
		System.out.println(inputData); 
		String[] tokens = inputData.split(" ");
		int number1 = 0;
		int number2 = 0;
		String res = "";

		if (p.ownedVariables.contains(tokens[1])) {
			int index = p.ownedVariables.indexOf(tokens[1]);
			number1 = Integer.parseInt( (String) p.ownedVariables.get(index + 1));
		}
		
		if (p.ownedVariables.contains(tokens[2])) {
			int index2 = p.ownedVariables.indexOf(tokens[2]);
			number2 = Integer.parseInt((String) p.ownedVariables.get(index2 + 1));
		}

		while (number1 != number2) {
			res = res.concat(number1 + "\n");
			number1++;
		}
		res = res.concat(number2 + "\n");
		p.countOfInstr++;
		return res;
	}

	public String print(String inputData, Process p) {
		System.out.println(inputData);
		String[] tokens = interpret(inputData);
		int number;
		int index = 0;
		String res = "";
		if (p.ownedVariables.contains(tokens[1])) {
			 index = p.ownedVariables.indexOf(tokens[1]);
			
		}
		p.countOfInstr++;
		return  p.ownedVariables.get(index + 1)+"";
	}

	public String assign(String inputData, Process p) {// assign a "hello"
		System.out.println(inputData);
		String input1 = "";
		String input2 = "";
		String res = "";

		 String[] tokens = inputData.split(" ");
		 if (p.ownedVariables.contains(tokens[2])) {// assign a b 
			int index = p.ownedVariables.indexOf(tokens[2]);
			input1 =  p.ownedVariables.get(index + 1)+""; 

			if (p.ownedVariables.contains(tokens[1])) { //if a already exists
				index = p.ownedVariables.indexOf(tokens[1]);
				p.ownedVariables.remove(index + 1);
				p.ownedVariables.add(index + 1, input1);

			}
			else { //if we need to create(initialise ) a 
				p.ownedVariables.add(tokens[1]);
				p.ownedVariables.add(input1);

			}
		} else if (p.ownedVariables.contains(tokens[1])) { //assign a "b" a already exist
			int index = p.ownedVariables.indexOf(tokens[1]);
			p.ownedVariables.remove(index + 1);
			p.ownedVariables.add(index + 1, tokens[2]);

		} else { //a is a new variable and b is a value (a="b")
			p.ownedVariables.add(tokens[1]);
			p.ownedVariables.add(tokens[2]);

		}

		p.countOfInstr++;
		return "assignment done";
	}

	public String Input(String inputData, Process p) {// assign a input
		System.out.println("Taking user input");
		String[] tokens = inputData.split(" ");
		int index ;
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a value");
		p.tempValue = in.nextLine(); // value= input value , variable =a
		p.tempVariable= tokens[1];
		p.countOfInstr++;
		return "";
	}
	
	public String readFile(String inputData, Process p)  { //assign b readFile a
		System.out.println("Reading the file");
		String[] tokens = interpret(inputData);
		int index = p.ownedVariables.indexOf(tokens[4]); 
		String path = (String) p.ownedVariables.get(index + 1);
		File file = new File(path);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String filecontent = "";

		while (scan.hasNextLine()) {
			filecontent = filecontent.concat(scan.nextLine() + "\n");

		}
		p.tempValue= filecontent;
		p.tempVariable =tokens[1];
		p.countOfInstr++;
		return filecontent;
	}
	
	public String writeFile(String inputData, Process p) {//writeFile x y
		System.out.println(inputData);
		String[] tokens = interpret(inputData);
		int index = p.ownedVariables.indexOf(tokens[2]);// x index
		String filename = (String) p.ownedVariables.get(index + 1);//x
		
		int index2 = p.ownedVariables.indexOf(tokens[3]); //y index
		String data = p.ownedVariables.get(index2 + 1)+"";

		File file=new File(filename);
		try {
			FileWriter fw=new FileWriter(file,true);// true -->continue on old file
			PrintWriter pw= new PrintWriter(fw);
			pw.println(data); // writes data to file
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.countOfInstr++;
		return "data written";
	}

	private String[] interpret(String inputData) {
		String tempInputData = inputData.replaceAll("[^a-z]", " ");
		tempInputData = tempInputData.replaceAll("( )+", " ").trim();
		String[] inputDatatokens = tempInputData.split(" ");
		return inputDatatokens;

	}

}
