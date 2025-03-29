import java.awt.Point;
import java.util.Stack;

import DataStructures.Queue;

public class p2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean showTime = false;
		boolean outCoord = false;
		boolean inCoord = false;
		for (String arg: args) {//process modifiers first
			if (arg.equals("--Time")) {showTime = true;}
			if (arg.equals("--Outcoordinate")) {outCoord = true;}
			if (arg.equals("--Incoordinate")) {inCoord = true;}
			if (arg.equals("--Help")) {
				System.out.println("This is a maze solving program that demonstrates the differences between a stack, queue, and optimal path approach to solving the maze."); 
				System.exit(0);
			}
			
		}
		
		Map m = new Map("src/TEST14.txt", false);
		m.load(); //MAKE SURE TO LOAD THE MAP BEFORE DOING ANYTHING WITH IT
		
		boolean choseSolver = false;
		for (String arg: args) {
			if (arg.equals("--Stack")) {
				StackSolver stackSolve = new StackSolver(m, showTime, outCoord);
				stackSolve.solve();	
				choseSolver = true;
			}else if (arg.equals("--Queue")) {
				QueueSolver qs = new QueueSolver(m, showTime, outCoord);
				qs.solve();
				choseSolver = true;
			} else if (arg.equals("--Opt")) {
				OptimalSolver os = new OptimalSolver(m, showTime, outCoord);
				os.solve();
				choseSolver = true;
			}
			
		}
		
		if (!choseSolver){
			OptimalSolver os = new OptimalSolver(m, showTime, outCoord);
			os.solve();
			System.out.println("You need to chose one of the solver methods");
			System.exit(0);
		}
		

	}


}
