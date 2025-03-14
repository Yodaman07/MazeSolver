import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import DataStructures.Queue;

class MapTester {

	@Test
	void testContains() { //test the contains method in the Map Class 
		Map m = new Map("src/TEST01.txt", false);
		m.load();
		Queue<Point> q = new Queue<Point>();
		QueueSolver qs = new QueueSolver(m);

		
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
//		System.out.println(q.toString());
		String str1 = q.toString();
		assertTrue(qs.contains(q, new Point(1,2)) == true); //Edge cases
		String str2 = q.toString();
		assertTrue(str1.equals(str2));
		
		assertTrue(qs.contains(q, new Point(3,4)) == true); //normal case
		assertTrue(qs.contains(q, new Point(5,6)) == true); //Edge cases
		
		assertTrue(str1.equals(q.toString()));
	}
	
	@Test
	void testRemove() { //test the remove method from the Map Class
		Map m = new Map("src/TEST01.txt", false);
		m.load();
		QueueSolver qs = new QueueSolver(m);
		Queue<Point> q = new Queue<Point>();
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
		qs.remove(q, new Point(1,2)); 
//		System.out.println(q.toString()); //middle elmn
		assertTrue(q.toString().equals("[java.awt.Point[x=3,y=4], java.awt.Point[x=5,y=6]]"));
		
		q.dequeue();q.dequeue(); // --------

		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
		qs.remove(q, new Point(3,4));
//		System.out.println(q.toString()); //edge case
		assertTrue(q.toString().equals("[java.awt.Point[x=1,y=2], java.awt.Point[x=5,y=6]]"));
		
	
		q.dequeue();q.dequeue(); // --------
		
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
		qs.remove(q, new Point(5,6));
//		System.out.println(q.toString()); // edge case
		assertTrue(q.toString().equals("[java.awt.Point[x=1,y=2], java.awt.Point[x=3,y=4]]"));
		
		qs.remove(q, new Point(1,1));
		assertTrue(q.toString().equals("[java.awt.Point[x=1,y=2], java.awt.Point[x=3,y=4]]"));
	}
	
	@Test
	void testInBounds() {
		Map m = new Map("src/TEST05.txt", false);
		m.load();
		QueueSolver qs = new QueueSolver(m);
		
		assertTrue(qs.inBounds(new Point(0, 0)) == true); // left before the map ends
		assertTrue(qs.inBounds(new Point(0, -1)) == false); // to the left after the map ends
		assertTrue(qs.inBounds(new Point(0, 4)) == true); //far right before the map ends
		assertTrue(qs.inBounds(new Point(0, 6)) == false); // far right past the map
		
		
		assertTrue(qs.inBounds(new Point(0, 0)) == true); // up before the map ends
		assertTrue(qs.inBounds(new Point(-1, 0)) == false); // up after the map ends
		assertTrue(qs.inBounds(new Point(7, 0)) == true);  // down before the map ends
		assertTrue(qs.inBounds(new Point(8, 0)) == false); // down after the map ends
	}

}
