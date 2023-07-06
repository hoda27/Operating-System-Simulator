import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.lang.Math.*;

public class Scheduler {

	Queue GnrlBlocked;
	Queue readyList;
	int Quantum;
	mutex UserInput;
	mutex UserOutput;
	mutex File;

	public Scheduler(int Quantum) {
		GnrlBlocked = new LinkedList<Process>();
		readyList = new LinkedList<Process>();
		this.UserInput = new mutex(this.GnrlBlocked, this.readyList,"UserInput");
		this.UserOutput = new mutex(this.GnrlBlocked, this.readyList,"UserOutput");
		this.File = new mutex(this.GnrlBlocked, this.readyList,"File");
		this.Quantum = Quantum;
	}

	// Methods

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Quantum");
		int Quantum = in.nextInt();

		int rtime[] = new int[3];
		int Arrival[] = new int[3];
		System.out.println("Enter Arrival time ");
		for (int i = 0; i < 3; i++) {
			System.out.print("\nP[" + (i + 1) + "]:");
			Arrival[i] = in.nextInt();
		}

		Scheduler s = new Scheduler(Quantum);
		Process p1 = new Process("Program_1.txt", Arrival[0]);
		Process p2 = new Process("Program_2.txt", Arrival[1]);
		Process p3 = new Process("Program_3.txt", Arrival[2]);

		int i = 0; // timer
		if (p1.arrival == 0) {
			s.readyList.add(p1);
		} else if (p2.arrival == 0) {
			s.readyList.add(p2);
		} else {
			s.readyList.add(p3);
		}
		while (!s.readyList.isEmpty()) {
			s.printReadyList();
			s.UserInput.printGeneralBlocked();
			Process p = (Process) s.readyList.remove();
			int lasttobeexecuted = p.countOfInstr + s.Quantum; // last to be executed in this time slice
			while (p.countOfInstr < lasttobeexecuted && !p.isBlocked) {

				p.execute(s.UserInput, s.UserOutput, s.File);
				i++;
				if (p1.arrival == i) {
					s.readyList.add(p1);
				} else if (p2.arrival == i) {
					s.readyList.add(p2);
				} else if (p3.arrival ==i){
					s.readyList.add(p3);

					if (p.countOfInstr > p.getMaximumInstr())
						break;
				}	
			}
			
			if (!p.isBlocked && (p.countOfInstr<=p.getMaximumInstr())) {
				s.readyList.add(p);

			}
			
			else if(p.countOfInstr>p.getMaximumInstr())
			{
				System.out.println("Process "+ p.pid +" is finished");
			}
		}
	}

	public void printReadyList() {
		int size =this.readyList.size();
		System.out.print("ReadyList: ");
		for (int i = 0; i <size ; i++) {
			Process p = (Process) this.readyList.remove();
			System.out.print("Process "+ p.pid +" ,");
			this.readyList.add(p);
		}
		System.out.println();
	}
}
