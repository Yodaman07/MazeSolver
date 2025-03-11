import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import DataStructures.Queue;

public class Maze {
	
	private String path;
	private boolean coordBased;
	private char[][] maze; //3d array to hold multiple rooms
	
	private int rows;
	private int cols;
	private int level;
	
	public Maze(String filePath, boolean coordBased) {
		this.path = filePath;
		this.coordBased = coordBased;
	}
	
	
	public void loadMaze() {
		try {
			File f = new File(this.path);
			Scanner s = new Scanner(f);
			
			String[] data = s.nextLine().split(" ");
			
			this.rows = Integer.valueOf(data[0]);
			this.cols = Integer.valueOf(data[1]);
			this.level = Integer.valueOf(data[2]);
			this.maze = new char[this.rows][this.cols];
			System.out.println(rows + ":" + cols + ":" + level);
			int r = 0;
			while (s.hasNextLine() && r < this.rows) {
				String line = s.nextLine(); 
				for (int i = 0; i < this.cols; i++) {
					this.maze[r][i] = line.charAt(i);
				}
				r++;
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printMaze() {
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
					q.enqueue(new Point(r, c)); //technically its y,x instead of x,y but r,c is consistent with everything elese
				}
			}
			System.out.println("");
		}
	}
	
	void solverHelper() {
		
	}
	
}
