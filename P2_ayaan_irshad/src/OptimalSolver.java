import java.awt.Point;
import java.util.ArrayList;


public class OptimalSolver {

	Map map;
	public OptimalSolver(Map m) {
		this.map = m;
	}
	// The optimal solver uses very similar logic to the queue solver and stack solver
	//The difference is that it uses an array list to hold data meaning
	
	public void solve() {
		System.out.println(" ");
		for (int l = 0; l <this.map.getLevels(); l++) { //treat each "level" as an its own path
			char finding = '|';
			ArrayList<Point> history = new ArrayList<Point>();
			ArrayList<Point> path = new ArrayList<Point>();
			Point pos = new Point(0,0);
			Point goal = new Point(0,0);
			if (l == this.map.getLevels()-1) {finding = '$';}

			for (int r = 0; r < this.map.getRows(); r++) {
				for (int c = 0; c < this.map.getCols(); c++) {
					if (this.map.getMaze()[r][c][l] == 'W') {
						pos = new Point(r, c);
					}
					
					if (this.map.getMaze()[r][c][l] == finding) {
						goal = new Point(r, c);
					}
				}
			}//find starting pos and goal pos
			
			
			boolean res = solverHelper(pos, goal, l, history, path);
//			System.out.println(res);
			if (!res) {System.out.println("The Wolverine Store is closed.");}
			//For each "path" there will be a unique history list to ensure no backtracking
		}
		
	}
	
	
	private boolean solverHelper(Point pos, Point goal, int level, ArrayList<Point> history, ArrayList<Point> path) {
		//instead of recursively looping through every possible case to get to the goal, 
		//we should decide it more "intelligently"
		//This can be done by assigning a "point" value to each move in relation to how close to the goal it gets us
		int x = pos.x;
		int y = pos.y;
		
		//north south east west
		int[] x_c = {-1, 1, 0, 0}; //the x and y coordinate offsets for the box that needs to be checked
		int[] y_c = {0, 0, -1, 1}; 
		int[] points_c = {0,0,0,0}; //if something has the same point value, then we can just chose it in the north south east west order
		
		for (int i = 0; i < x_c.length; i++) { //base case - if any of the accessible tiles are the wolverine coin
			Point a = new Point(x+x_c[i], y+y_c[i]);
			if (this.map.inBounds(a)) {
				if (this.map.getMaze()[x+x_c[i]][y+y_c[i]][level] == this.map.getMaze()[goal.x][goal.y][level]) {
//					System.out.println("WE DID IT!!!!!");
					this.printRes(path, level);
					return true;
				}
			}
		}
		
		
		
		
		int maxIndex = 0;
		for (int i = 0; i < x_c.length; i++) { // for each of the four places to check
			Point p = new Point(x+x_c[i], y+y_c[i]);
			if (this.map.inBounds(p)) {
				if (this.map.getMaze()[p.x][p.y][level] == '.') { //if the tile is '.'
					if (!history.contains(p)) { //check before you can actually consider a point

					
						if (Math.abs(pos.x - goal.x) > Math.abs(p.x - goal.x)) { //if this move gets us closer to the goal
							points_c[i]++;
						}
						
						if (Math.abs(pos.y - goal.y) > Math.abs(p.y - goal.y)) { //if this move gets us closer to the goal
							points_c[i]++;
						} 
						
						if (points_c[i] > maxIndex) {maxIndex = i;}
						
					}
				}
			}
		}
		
		if (allEmpty(points_c)) {//happens when there is no "optimal" move to make
			//In this event we want to take the first north,south,east,west move that hasn't been taken (like the original alg)
			
			for (int i = 0; i < x_c.length; i++) { // for each of the four places to check
				Point p = new Point(x+x_c[i], y+y_c[i]);
				if (this.map.inBounds(p)) {
					if (this.map.getMaze()[p.x][p.y][level] == '.' && !history.contains(p)) { //if the tile is '.'
						System.out.println(p + "Oop");
						history.add(p);
						path.add(p);
						boolean works = this.solverHelper(p, goal, level, history, path);
						if (works) {return true;}
						if (!works) { //if it doesn't work, remove the elements that stem to that result from the path
							System.out.println("Removed");
							path.remove(p);
						}

					}
				}
			}
			
		}if (!allEmpty(points_c)) {//general case
			Point nextPos = new Point(x+x_c[maxIndex], y+y_c[maxIndex]);
			System.out.println(nextPos);
			history.add(nextPos);
			path.add(nextPos);
			boolean works = this.solverHelper(nextPos, goal, level, history, path);
			if (works) {return true;}
		}
		
		
		return false;
	}
	
	boolean allEmpty(int[] pointsArray) {
		boolean empty = true;
		for (int a: pointsArray) {
			if (a != 0) {empty = false;}
		}
		
		return empty;
	}
	
	void printRes(ArrayList<Point> path, int level) { //prints in map based and coordinate based
		if (!map.isCoordBased()) { //map based
			char[][][] temp = this.map.getMaze().clone();
			
			int iterations = path.size();
			for (int j = 0; j < iterations; j++) {
				Point val = path.removeFirst(); 
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
				Point a = path.removeFirst();
				System.out.println("+ " + a.x + " " + a.y + " " + level);
			}
			
		}
		
	}
	
}
