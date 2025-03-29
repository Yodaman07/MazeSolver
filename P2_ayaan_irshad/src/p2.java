import java.awt.Point;
import java.util.Stack;

import DataStructures.Queue;

public class p2 {
	public static void main(String[] args) throws IllegalCommandLineInputsException {
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
		
		Map m = new Map("TEST01.txt", inCoord);
		//MAKE SURE TO LOAD THE MAP BEFORE DOING ANYTHING WITH IT
		try {
			m.load();
		} catch (IncorrectMapFormatException e) {e.printStackTrace();
		} catch (IncompleteMapException e) {e.printStackTrace();
		}catch (IllegalMapCharacterException e) {e.printStackTrace();}
		
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
			throw new IllegalCommandLineInputsException("You need to chose one of the solver methods");
		}
		

	}
	

}


class IllegalCommandLineInputsException extends Exception
{
      // Constructor that accepts a message
      public IllegalCommandLineInputsException(String message)
      {
         super(message);
      }
 }

