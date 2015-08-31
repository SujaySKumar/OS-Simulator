import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class ProcessorIntel{
	public static void main(String args[]){
		try {
			System.setIn(new FileInputStream(
					"C:\\Users\\Kumar BN\\Desktop\\Processor\\InputProcessor.txt"));
			System.setOut(new PrintStream(
					"C:\\Users\\Kumar BN\\Desktop\\Processor\\out1.txt"));
		} catch (Exception e) {
			System.out.print("CATCH BLOCK");
		}
		Scanner in=new Scanner(System.in);
		HashMap<Integer,LinkedList<ProcessTable>> mapProcess=new HashMap<>();
		HashMap<Integer,String> timeList=new HashMap<Integer,String>();
		int id=0;
		int max=in.nextInt();
		int timeGran=in.nextInt();
		System.out.println("Time Gran is "+timeGran);
		int totalTimeInFile=0;
		int start=0;
		Processor processor=new Processor(max,timeGran);
		while(in.hasNext()){
			String action=in.next();
			if(action.equals("SUBMIT")){
				String name=in.next();
				int totalTime=in.nextInt();
				int startTime=in.nextInt();
				totalTimeInFile=startTime+totalTime;
				LinkedList<ProcessTable> list=mapProcess.get(startTime);
				if(list==null){
					LinkedList<ProcessTable> temp=new LinkedList<>();
					temp.add(new ProcessTable(id,name,startTime,totalTime,0,totalTime));
					id=id+1;
					mapProcess.put(startTime,temp);
				}
				else{
					LinkedList<ProcessTable> temp=mapProcess.get(startTime);
					temp.add(new ProcessTable(id,name,startTime,totalTime,0,totalTime));
					id=id+1;
					mapProcess.put(startTime,temp);
				}
			}
			else if(action.equals("PRINT")){
				String unwanted=in.next();
				int time=in.nextInt();
				totalTimeInFile=time;
				timeList.put(time,"Print");
			}
		}
		System.out.println(mapProcess);
		System.out.println(timeList);
		int i=1;
		while(!(processor.queue.isEmpty())||i<totalTimeInFile||processor.state==1){
			if(i%timeGran==0){
				processor.chooseNextProcess(i);
				try{
				if(processor.state==1){
					//System.out.println("Running process is "+processor.runningProcess.name);
				}
				}
				catch(Exception e){}
				//System.out.println("Here");
				if(mapProcess.containsKey(i)){
					if(start==0)
						start=i;
					//System.out.println("Got a process at "+i);
					LinkedList<ProcessTable> toStartList=mapProcess.get(i);//You'll have to take the first porcess and enqueue the remaining in the queue if in case more than one process comes at a time
					Iterator<ProcessTable> listiter=toStartList.iterator();
					ProcessTable toStart=listiter.next();
					if(processor.queue.isEmpty() && processor.state==0){
						//System.out.println("Putting the process inside the core directly");
						toStart.status=1;
						processor.state=1;
						processor.runningProcess=toStart;
					}
					else{
						//System.out.println("Enquing other process");
						toStart.status=0;
						processor.queue.add(toStart);
					}
					while(listiter.hasNext()){
						ProcessTable other=listiter.next();
						other.status=0;
						processor.queue.add(other);
					}
				}
				if(timeList.containsKey(i)){
					//System.out.println("Printing table");
					processor.printProcessTable(i);
				}
			}
			//processor.mopUp();
			if(processor.state==0){
				processor.chooseNextProcess(i);
			}
			i=i+1;
		}
		System.out.println("Time taken is"+(i-start));
	}
}
