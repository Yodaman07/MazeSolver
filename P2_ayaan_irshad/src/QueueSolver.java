import java.awt.Point;
import java.util.concurrent.TimeUnit;

import DataStructures.Queue;

public class QueueSolver {
	
	Map map;
	Queue<Point> realPath;
	long timeStart;
	boolean showRuntime;
	boolean coordOut;
	
	
	public QueueSolver(Map m, boolean showRuntime, boolean coordOut) {
		realPath = new Queue<Point>();
		this.map = m;
		this.showRuntime = showRuntime;
		this.coordOut = coordOut;
	}
	
	public void solve() {
		
		if (showRuntime) {this.startTimer();}
		System.out.println(" ");
		for (int l = 0; l <this.map.getLevels(); l++) { //treat each "level" as an its own path
			char finding = '|';
			Queue<Point> q = new Queue<Point>();
			Queue<Point> history = new Queue<Point>();
			Queue<Point> path = new Queue<Point>();
			
			for (int r = 0; r < this.map.getRows(); r++) {
				for (int c = 0; c < this.map.getCols(); c++) {
					if (this.map.getMaze()[r][c][l] == 'W') {
						q.enqueue(new Point(r, c)); //technically its y,x instead of x,y but r,c is consistent with everything else
					}
				}
			}//find starting pos
			
			if (l == this.map.getLevels()-1) {finding = '$';}
			
			boolean res = solverHelper(q, history, path, finding, l); // this will invoke the recursive method

			this.printRes(realPath, l);
			
			
			if (!res) {System.out.println("The Wolverine Store is closed.");}
			//For each "path" there will be a unique history list to ensure no backtracking
		}
		
	}
	
	private boolean solverHelper(Queue<Point> q, Queue<Point> history, Queue<Point> path, char finding, int level) {
		//finding is the char to search for - for multi-level mazes, you want to look for | in the 0th level then $
		int x = q.peek().x;
		int y = q.peek().y;
		
		int[] x_c = {-1, 1, 0, 0}; //the x and y coordinate offsets for the box that needs to be checked
		int[] y_c = {0, 0, -1, 1}; 
	
		for (int i = 0; i < x_c.length; i++) { //base case - if any of the accessible tiles are the wolverine coin
			if (this.map.inBounds(new Point(x+x_c[i], y+y_c[i]))) {
				if (this.map.getMaze()[x+x_c[i]][y+y_c[i]][level] == finding) {
//					System.out.println("FOUND WOLVERINE COIN");
					realPath = path;
					//WITH VALID PATH
					
					return true;
				}
			}
		}
		
		for (int i = 0; i < x_c.length; i++) { // for each of the four places to check
			Point p = new Point(x+x_c[i], y+y_c[i]);
			if (this.map.inBounds(p)) {
				if (this.map.getMaze()[x+x_c[i]][y+y_c[i]][level] == '.') { //if the tile is '.'	
					if (!contains(history, p)) {
						q.enqueue(p);
						history.enqueue(p);
						path.enqueue(p);
						q.dequeue();
						boolean pathWorks = this.solverHelper(q, history, path, finding, level); //if the path results in finding the wolverine coin
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
	
	private void printRes(Queue<Point> path, int level) { //prints in map based and coordinate based
		if (this.showRuntime) {this.endTimer();}
		if (!this.coordOut) { //map based
			char[][][] temp = this.map.getMaze().clone();
			
			int iterations = path.size();
			for (int j = 0; j < iterations; j++) {
				Point val = path.dequeue(); 
				temp[val.x][val.y][level] = '+';
			}
			
			for (int r = 0; r < this.map.getRows(); r++) {
				for (int c = 0; c < this.map.getCols(); c++) {
					System.out.print(temp[r][c][level]);
				}
				System.out.println("");
			}
			
		}else { //for coord based
			int size = path.size();
			for (int i = 0; i<size; i++) {
				Point a = path.dequeue();
				System.out.println("+ " + a.x + " " + a.y + " " + level);
			}
			
		}
		
	}
	
	
	boolean contains(Queue<Point> data, Point target) { //Method works as intended - verified by tester
		//Method to find if a Queue contains a specific value
		//data = [(1,2),(3,4),(5,6)]
		//temp will become [(1,2),(3,4),(5,6)] and data = []
		//data = [(1,2),(3,4),(5,6)] and temp = []
		
		boolean found = false;
		Queue<Point> temp = new Queue<Point>();
		int itterations = data.size();

		for (int i = 0; i < itterations; i++) {
			Point curr = data.dequeue();
			if (curr.equals(target)) {found = true;}
			temp.enqueue(curr);
		}
		
		for (int i = 0; i < itterations; i++) {
			data.enqueue(temp.dequeue());
		}
		
		return found;
	}
	
	void remove(Queue<Point> data, Point target) { //removes a specific element from the queue and returns that element
		if (!contains(data, target)) {return;} //verified and tested
		
		//assuming the item to remove is in the queue
		Queue<Point> temp = new Queue<Point>();
		int itterations = data.size();

		for (int i = 0; i < itterations; i++) {
			Point curr = data.dequeue();
			if (!curr.equals(target)) {
				temp.enqueue(curr);
			}
		}
		
		for (int i = 0; i < itterations-1; i++) {
			data.enqueue(temp.dequeue());
		}		
	}
	
	public void startTimer() {
		timeStart = System.nanoTime();
		
	}
	
	public void endTimer() {
		long diff = System.nanoTime() - timeStart;
		double seconds = (double)diff / 1_000_000_000.0;
		System.out.println("Total Runtime: " + seconds + " seconds");
	}
	
}
