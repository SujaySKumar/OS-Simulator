import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
public class Processor {
	int maxProcess;
	//PriorityQueue<ProcessTable> queue;
	Queue<ProcessTable> queue;
	ProcessTable runningProcess;
	int timeGran;
	int state;
	Processor(int n,int time){
		maxProcess=n;
		/*queue= new PriorityQueue<ProcessTable>(
		        new Comparator<ProcessTable>( ) {
		            // override the compare method
		        	public int compare(ProcessTable i,ProcessTable j) {
		        		  return (i.startTime+i.timeReq) -(j.startTime+j.timeReq);
		        		}});*/
		queue=new LinkedList<ProcessTable>();
		timeGran=time;
		state=0;
	}	        
	public void chooseNextProcess(int i){
		try{
			//System.out.println(i);
			runningProcess.timeUsed=(i-runningProcess.startTime);
			//System.out.println(runningProcess.timeRem);
			runningProcess.timeRem=runningProcess.timeRem-timeGran;
			if(runningProcess.timeRem<=0){
				System.out.println("Killing process"+runningProcess.name);
				if(!queue.isEmpty()){
					ProcessTable process=queue.remove();
					runningProcess=process;
					runningProcess.status=1;
				}
				else{
					state=0;
					runningProcess=null;
				}
			}
			else{
				runningProcess.status=0;
				queue.add(runningProcess);
				ProcessTable process=queue.remove();
				runningProcess=process;
				runningProcess.status=1;
			}
			
		}
		catch(Exception e){
			//System.out.println("No process there yet");
		}
		
	}
	public void printProcessTable(int i) {
		// TODO Auto-generated method stub
		try{
			Iterator<ProcessTable> iter=queue.iterator();
			while(iter.hasNext()){
				ProcessTable entry=iter.next();
				entry.timeUsed=i-entry.startTime;
				entry.display();
			}
			runningProcess.timeUsed=i-runningProcess.startTime;
			runningProcess.display();
		}
		catch(Exception e){System.out.println("Nothing to print");}
	}
	/*public LinkedList<ProcessTable> mopUp() {
		LinkedList<ProcessTable> list=new LinkedList<ProcessTable>();
		Iterator<ProcessTable> iter=queue.iterator();
		while(iter.hasNext()){
			ProcessTable process=iter.next();
			if(process.timeRem<=0){
				list.add(process);
			}
		}
		cleanFromQueue(process);
	}*/
	
}
