import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import DataStructures.Queue;

public class Map {
	
	private String path;
	private boolean coordBased;
	public char[][] maze; //3d array to hold multiple rooms
	
	private int rows;
	private int cols;
	private int level;
	
	public Map(String filePath, boolean coordBased) {
		this.path = filePath;
		this.coordBased = coordBased;
	}
	
	
	public void load() {
		try {
			File f = new File(this.path);
			Scanner s = new Scanner(f);
			
			String[] data = s.nextLine().split(" ");
			
			this.rows = Integer.valueOf(data[0]);
			this.cols = Integer.valueOf(data[1]);
			this.level = Integer.valueOf(data[2]);
			this.maze = new char[this.rows][this.cols];
//			System.out.println(rows + ":" + cols + ":" + level);
			
			if (!coordBased) { //loading regular map based mazes
				int r = 0;
				while (s.hasNextLine() && r < this.rows) {
					String line = s.nextLine(); 
					for (int i = 0; i < this.cols; i++) {
						this.maze[r][i] = line.charAt(i);
					}
					r++;
				}
			}else { //loading coordinate based mazes
				while (s.hasNextLine()) {
					char elmn = s.next().toCharArray()[0]; //only 1 char
					int row = s.nextInt();
					int col = s.nextInt();
					int lvl = s.nextInt();
					System.out.println(elmn + " " + row + " " + col + " " + lvl);
					this.maze[row][col] = elmn;
				}
				
				for (int r = 0; r < this.rows; r++) {
					for (int c = 0; c < this.cols; c++) {
						if (this.maze[r][c] == '\u0000') {this.maze[r][c] = '.';}
						
					}
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void printMaze() {
		if (this.maze == null) {System.out.println("You need to load a maze");}
		
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				System.out.print(this.maze[r][c]);
			}
			System.out.println("");
		}
	}
	
	public void solver() { //develop class for
		Queue<Point> q = new Queue<Point>();
		Queue<Point> history = new Queue<Point>();
		Queue<Point> path = new Queue<Point>();
		
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				if (this.maze[r][c] == 'W') {
					q.enqueue(new Point(r, c)); //technically its y,x instead of x,y but r,c is consistent with everything else
				}
			}
		}
		
		solverHelper(q, history, path); // this will invoke the recursive method
		//For each "path" there will be a unique history list to ensure no backtracking
//		System.out.println(history.toStrings());
	}
	
	boolean solverHelper(Queue<Point> q, Queue<Point> history, Queue<Point> path) {
		int x = q.peek().x;
		int y = q.peek().y;
		
		int[] x_c = {-1, 1, 0, 0}; //the x and y coordinate offsets for the box that needs to be checked
		int[] y_c = {0, 0, -1, 1}; 
	
		for (int i = 0; i < x_c.length; i++) { //base case - if any of the accessible tiles are the wolverine coin
			if (inBounds(new Point(x+x_c[i], y+y_c[i]))) {
				if (this.maze[x+x_c[i]][y+y_c[i]] == '$') {
					System.out.println("FOUND WOLVERINE COIN");
					System.out.println(path.toString());
					return true;
				}
			}
		}
		
		for (int i = 0; i < x_c.length; i++) { // for each of the four places to check
			Point p = new Point(x+x_c[i], y+y_c[i]);
			if (inBounds(p)) {
				if (this.maze[x+x_c[i]][y+y_c[i]] == '.') { //if the tile is '.'	
					if (!contains(history, p)) {
						q.enqueue(p);
						history.enqueue(p);
						path.enqueue(p);
						q.dequeue();
	//					System.out.println(q.peek());
	//					System.out.println(history.toString());
						boolean pathWorks = this.solverHelper(q, history, path); //if the path results in finding the wolverine coin
						if (!pathWorks) { //if it doesn't work, remove the elements that stem to that result from the path
							remove(path, p);
						}
					}
				}
			}
		}				
		
		return false;
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
	
	void remove(Queue<Point> data, Point target) { //removes a specific element from the list and returns that element
		if (!contains(data, target)) {return;} //verified and tested
		
		//assuming the item to remove is in the list
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
	
	boolean inBounds(Point p) { //checks to see if a specified point is in the bounds of the maze
		// x is rows, y is columns
		return ((p.x < this.rows) && (0 <= p.x) && (p.y < this.cols) && (0 <= p.y));
	}
	
}
