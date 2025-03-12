import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import DataStructures.Queue;

public class Map {
	
	private String path;
	private boolean coordBased;
	private char[][] maze; //3d array to hold multiple rooms
	
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
//		ArrayLit
		
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				if (this.maze[r][c] == 'W') {
					q.enqueue(new Point(r, c)); //technically its y,x instead of x,y but r,c is consistent with everything else
				}
			}
			System.out.println("");
		}
		
		solverHelper(q, history); // this will invoke the recursive method
		//For each "path" there will be a unique history list to ensure no backtracking
		
	}
	
	void solverHelper(Queue<Point> q, Queue<Point> history) {
		int x = q.peek().x;
		int y = q.peek().y;
		
		int[] x_c = {-1, 1, 0, 0}; //the x and y coordinate offsets for the box that needs to be checked
		int[] y_c = {0, 0, -1, 1}; 
		
		
		for (int i = 0; i < x_c.length; i++) {
			if (this.maze[x+x_c[i]][y+y_c[i]] == '.') {
				Point p = new Point(x+x_c[i], y+y_c[i]);
				if (contains(history, p)) {return;}
				
				q.enqueue(p);
				history.enqueue(p);
				q.dequeue();
				this.solverHelper(q, history);
			}

		}
		
		
//		if (this.maze[x-1][y] == '.') { // north
//			System.out.println("N");
//			Point p = new Point(x-1, y);
//			q.enqueue(p);
//			history.enqueue(p);
//			q.dequeue();
//			this.solverHelper(q, history);
//			
//		}else if (this.maze[x+1][y] == '.'){ //south
//			System.out.println("S");
//			Point p = new Point(x+1, y);
//			q.enqueue(p);
//			history.enqueue(p);
//			q.dequeue();
//			this.solverHelper(q, history);
//			
//		}else if (this.maze[x][y-1] == '.') {//east
//			System.out.println("E");
//			
//			Point p = new Point(x, y-1);
//			q.enqueue(p);
//			history.enqueue(p);
//			q.dequeue();
//			this.solverHelper(q, history);
//			
//		}else if (this.maze[x][y+1] == '.') {//west
//			System.out.println("W");
//			Point p = new Point(x, y+1);
//			
//			q.enqueue(p);
//			history.enqueue(p);
//			q.dequeue();
//			this.solverHelper(q, history);
//			
//		}else {
//			System.out.println("dead end?");
//		}
				
	}
	
	boolean contains(Queue<Point> data, Point target) {
		
		return false;
		
	}
	
}
