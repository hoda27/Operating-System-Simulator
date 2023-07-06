import java.util.ArrayList;
import java.util.Queue;
import java.util.*;

import java.io.*;

public class Process {
	public ArrayList ownedVariables;
	int countOfInstr; // Next Instruction to be executed
	private int maximumInstr;
	static int pnum;
	int pid;
	public File ProgramFile;
	InterpreterEngine interpreterEngine;
	InterpreterClient interpreterClient;
	public Scanner sc;
	boolean isBlocked ;
	int arrival;
	String tempValue;
	String tempVariable;
	
	public Process(String programPath,int arrival) throws FileNotFoundException {
		ownedVariables = new ArrayList<String>();
		this.countOfInstr = 1;
		this.ProgramFile = new File(programPath);
		this.maximumInstr = this.countProgramsize();
		this.sc = new Scanner(ProgramFile);
		pnum++;
		this.pid=pnum;
		this.isBlocked=false;
		this.arrival=arrival;

	}

	// METHODS
	public int countProgramsize() {

		int count = 0;
		try {
			BufferedReader counting = new BufferedReader(new FileReader(this.ProgramFile));
			String line=counting.readLine();
			while (line!= null) {
				if(line.contains("assign")&&(line.contains("read") || line.contains("input")))
				{count+=2;
				}
				else {count++;
				}
				line=counting.readLine();
			}
			counting.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public int getMaximumInstr() {
		return maximumInstr;
	}

	public void setMaximumInstr(int maximumInstr) {
		this.maximumInstr = maximumInstr;
	}

	public void execute(mutex userInput ,mutex userOutput ,mutex file) {
		InterpreterEngine interpreterEngine = new InterpreterEngine();
		InterpreterClient interpreterClient = new InterpreterClient(interpreterEngine);
		
		//check if tempvalue is not null 
		//so we can pass its value to whatever assign operation 
		if(this.tempValue!=null)
		{
			System.out.println("Process: "+ pid +" is executing" );
			System.out.println(interpreterClient.interpret("assign " + this.tempVariable +" "+ this.tempValue, this));
			this.tempValue=null;
			this.tempVariable=null;
		}
		
		else if (sc.hasNextLine()) {
			System.out.println("Process: "+ pid +" is executing" );
			
				String line = sc.nextLine();
		        if(line.contains("semWait")||line.contains("semSignal")) {
		        	mutex_method(line, userInput , userOutput , file);
		        	
		        }
		        else {
				System.out.println(interpreterClient.interpret(line, this));
		        }
				
				
			} 
		else {this.countOfInstr++ ;}
		}

	public void mutex_method(String line,mutex userInput ,mutex userOutput ,mutex file) {
		if(line.contains("semWait")) {
			if (line.contains("userInput"))
			{
				System.out.println("semWait userInput");
				this.semWait(userInput);

			}
			if (line.contains("userOutput")) {
				System.out.println("semWait userOutput");
				this.semWait(userOutput);
			}
			if (line.contains("file")) {
				System.out.println("semWait file");
				this.semWait(file);
			}
		}
		
		if(line.contains("semSignal")) {
			if (line.contains("userInput")) {
				System.out.println("semSignal userInput");
				this.semSignal(userInput);
			}
			if (line.contains("userOutput")) {
				System.out.println("semSignal userOutput");
				this.semSignal(userOutput);
			}
			if (line.contains("file")) { 
				System.out.println("semSignal file");
				this.semSignal(file);
			}
		}
			
	}
		
	public void getblocked(Queue<Process> GnrlBlocked, Queue<Process> resourceBlocked) {
		GnrlBlocked.add(this);
		resourceBlocked.add(this);
		
	}
	
	public void addtoReadyQueue(Queue<Process> ReadyQueue) {
		
	}
	
	//Mutex METHODS
	
	//SEMWAIT
	
	public void semWait (mutex m) {
		if(!m.lock) {
			m.lock=true;
			m.ownerID=this.pid;
		}
		else {
			this.getblocked(m.Gnrl, m.blocked);
			m.printBlocked();
			this.isBlocked=true;
			
		}
		this.countOfInstr++;
	}
	
	//SEMSIGNAL
	
	public void semSignal(mutex m) {
		
		if(m.ownerID==this.pid)
		{
			if(m.blocked.isEmpty())
				m.lock=false;
			else
			{
				Process removed=(Process)m.blocked.remove();
				int size = m.Gnrl.size();
				for(int i=0;i<size;i++)
				{
					Process g =(Process)m.Gnrl.remove();
					if(!removed.equals(g))
						m.Gnrl.add(g);
				}
				m.ownerID= removed.pid;
				removed.isBlocked=false;
				m.readylist.add(removed);	
			}
		}
		this.countOfInstr++;
	}
}
