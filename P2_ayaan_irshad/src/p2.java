import java.awt.Point;
import java.util.Stack;

import DataStructures.Queue;

public class p2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map m = new Map("src/TEST11.txt", false);
		m.load(); //MAKE SURE TO LOAD THE MAP BEFORE DOING ANYTHING WITH IT
		m.printMaze();
//		QueueSolver qs = new QueueSolver(m);
//		qs.solve();
//		StackSolver ss = new StackSolver(m);
//		ss.solve();

	
	}

}
