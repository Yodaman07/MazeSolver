import java.awt.Point;

import DataStructures.Queue;
import DataStructures.Stack;

public class StackSolver {
	Map map;
	
	public StackSolver(Map m) {
		this.map = m;
	}
	
	public void solve() {
		Stack<Point> s = new Stack<Point>(); //stacks push everything to the back and take from the back
		Stack<Point> history = new Stack<Point>();
		Stack<Point> path = new Stack<Point>();
		
		for (int r = 0; r < this.map.getRows(); r++) {
			for (int c = 0; c < this.map.getCols(); c++) {
				if (this.map.getMaze()[r][c] == 'W') {
					s.push(new Point(r, c)); //technically its y,x instead of x,y but r,c is consistent with everything else
				}
			}
		}
		
		boolean res = solverHelper(s, history, path); // this will invoke the recursive method
		if (!res) {System.out.println("The Wolverine Store is closed.");}
		//For each "path" there will be a unique history list to ensure no backtracking
	}
	
	private boolean solverHelper(Stack<Point> s, Stack<Point> history, Stack<Point> path) {
		int x = s.peek().x;
		int y = s.peek().y;
		
		int[] x_c = {-1, 1, 0, 0}; //the x and y coordinate offsets for the box that needs to be checked
		int[] y_c = {0, 0, -1, 1}; 
	
		for (int i = 0; i < x_c.length; i++) { //base case - if any of the accessible tiles are the wolverine coin
			if (this.map.inBounds(new Point(x+x_c[i], y+y_c[i]))) {
				if (this.map.getMaze()[x+x_c[i]][y+y_c[i]] == '$') {
					System.out.println("FOUND WOLVERINE COIN");
					//WITH VALID PATH
					this.map.printMaze();
					System.out.println("");
					this.printRes(path);
					
					return true;
				}
			}
		}
		
		for (int i = 0; i < x_c.length; i++) { // for each of the four places to check
			Point p = new Point(x+x_c[i], y+y_c[i]);
			if (this.map.inBounds(p)) {
				if (this.map.getMaze()[x+x_c[i]][y+y_c[i]] == '.') { //if the tile is '.'	
					if (!contains(history, p)) {
						s.pop();
						s.push(p);
						history.push(p);
						path.push(p);
						
						
						boolean pathWorks = this.solverHelper(s, history, path); //if the path results in finding the wolverine coin
						if (pathWorks) {return true;}
						if (!pathWorks) { //if it doesn't work, remove the elements that stem to that result from the path
							remove(path, p);
						}
					}
				}
			}
		}				
		
		return false;
	}
	
	void printRes(Stack<Point> path) {
		char[][] temp = this.map.getMaze().clone();
		
		int iterations = path.size();
		for (int j = 0; j < iterations; j++) {
			Point val = path.pop(); 
			temp[val.x][val.y] = '+';
		}
		
		for (int r = 0; r < this.map.getRows(); r++) {
			for (int c = 0; c < this.map.getCols(); c++) {
				System.out.print(temp[r][c]);
			}
			System.out.println("");
		}
	}
	
	void remove(Stack<Point> data, Point target) { //removes a specific element from the stack and returns that element
		if (!contains(data, target)) {return;} //verified and tested
		
		//assuming the item to remove is in the stack
		Stack<Point> temp = new Stack<Point>();
		int itterations = data.size();

		for (int i = 0; i < itterations; i++) {
			Point curr = data.pop();
			if (!curr.equals(target)) {
				temp.push(curr);
			}
		}
		
		for (int i = 0; i < itterations-1; i++) {
			data.push(temp.pop());
		}		
	}
	
	
	boolean contains(Stack<Point> data, Point target) { //Method works as intended - verified by tester
		//Method to find if a Stack contains a specific value
		//data = [(1,2),(3,4),(5,6)]
		//temp will become [(5,6),(3,4),(1,2)] and data = []
		//data = [(1,2),(3,4),(5,6)] and temp = []
		
		boolean found = false;
		Stack<Point> temp = new Stack<Point>();
		int itterations = data.size();

		for (int i = 0; i < itterations; i++) {
			Point curr = data.pop();
			if (curr.equals(target)) {found = true;}
			temp.push(curr);
		}
		
		for (int i = 0; i < itterations; i++) {
			data.push(temp.pop());
		}
		
		return found;
	}
}
