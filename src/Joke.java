import java.util.*;
public class Joke {
	public static void main(String args[]){
		PriorityQueue<String> queue=new PriorityQueue<String>();
		queue.add("Process");
		queue.add("Job");
		String res=queue.remove();
		System.out.println(res);
	}
}
