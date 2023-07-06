import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
public class mutex {
	
	boolean lock ;//true= taken false= available
	int ownerID;
	Queue blocked;
	Queue Gnrl;
	Queue readylist;
	String name;
	
	public mutex(Queue Gnrl,Queue readylist,String name) {
		blocked=new LinkedList<Process>();
		this.Gnrl= Gnrl;
		this.lock=false;
		this.readylist=readylist;
		this.name=name;
	}
	public void printBlocked() {
		int size =this.blocked.size();
		System.out.print(this.name+" Blocked Queue: ");
		for (int i = 0; i <size ; i++) {
			Process p = (Process) this.blocked.remove();
			System.out.print("Process "+p.pid +" , ");
			this.blocked.add(p);
			
		}
		System.out.println();

	}
	public void printGeneralBlocked() {
		int size =this.Gnrl.size();
		System.out.print("General Blocked Queue: ");

		for (int i = 0; i <size ; i++) {
			Process p = (Process) this.Gnrl.remove();
			System.out.print("Process "+p.pid +" , ");
			this.Gnrl.add(p);

		}
		System.out.println();

	}
}
