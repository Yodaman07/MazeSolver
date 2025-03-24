import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import DataStructures.Queue;

public class Map {
	
	private String path;
	private boolean coordBased;
	private char[][][] maze; //3d array to hold multiple rooms
	
	private int rows;
	private int cols;
	private int levels;
	
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
			this.levels = Integer.valueOf(data[2]);
			this.maze = new char[this.rows][this.cols][this.levels];
//			System.out.println(rows + ":" + cols + ":" + levels);
			
			if (!coordBased) { //loading regular map based mazes
				for (int l = 0; l < levels; l++) {
					int r = 0;
					while (s.hasNextLine() && r < this.rows) {
						String line = s.nextLine(); 
						for (int i = 0; i < this.cols; i++) {
							this.maze[r][i][l] = line.charAt(i);
						}
						r++;
					}
				}
				this.printMaze();
			}else { //loading coordinate based mazes
				while (s.hasNextLine()) {
					char elmn = s.next().toCharArray()[0]; //only 1 char
					int row = s.nextInt();
					int col = s.nextInt();
					int lvl = s.nextInt();
					System.out.println(elmn + " " + row + " " + col + " " + lvl);
					this.maze[row][col][lvl] = elmn;
				}
				
				for (int l = 0; l <this.levels; l++) {
					
					for (int r = 0; r < this.rows; r++) {
						for (int c = 0; c < this.cols; c++) {
							if (this.maze[r][c][l] == '\u0000') {this.maze[r][c][l] = '.';} // \u0000 is ascii null value						
						}
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
		
		for (int l = 0; l <this.levels; l++) {
			for (int r = 0; r < this.rows; r++) {
				for (int c = 0; c < this.cols; c++) {
					System.out.print(this.maze[r][c][l]);
					
				}
				System.out.println("");
			}
		}
	}
	
	boolean inBounds(Point p) { //checks to see if a specified point is in the bounds of the maze
		// x is rows, y is columns
		return ((p.x < this.getRows()) && (0 <= p.x) && (p.y < this.getCols()) && (0 <= p.y));
	}
	public char[][][] getMaze(){return this.maze;}
	public boolean isCoordBased() { return coordBased; }
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public int getLevels() { return levels; }
	
}
