import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import DataStructures.Queue;

class MapTester {

	@Test
	void testContains() { //test the contains method in the Map Class 
		Map m = new Map("src/TEST01.txt", false);
//		m.load();
//		m.printMaze();
//		m.solver();
		Queue<Point> q = new Queue<Point>();
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
//		System.out.println(q.toString());
		String str1 = q.toString();
		assertTrue(m.contains(q, new Point(1,2)) == true); //Edge cases
		String str2 = q.toString();
		assertTrue(str1.equals(str2));
		
		assertTrue(m.contains(q, new Point(3,4)) == true); //normal case
		assertTrue(m.contains(q, new Point(5,6)) == true); //Edge cases
		
		assertTrue(str1.equals(q.toString()));
	}

}
