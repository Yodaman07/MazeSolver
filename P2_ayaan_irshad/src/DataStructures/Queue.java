package DataStructures;

import java.util.Stack;

public class Queue<E> {
	
	//Data must be stored in Stack(s)
	private Stack<E> stackA;
	private Stack<E> tempStack;
	
	
	
	//FIFO instead of LIFO
	public Queue() {
		//create the stack! it's only a declaration right now
		stackA = new Stack<E>();
		tempStack = new Stack<E>();
	}

	/*
	 * adding to the Queue
	 */
	public void enqueue(E el) {
		stackA.push(el);
	}
	
	
	/*
	 * removes and returns from the Queue
	 */
	public E dequeue() {
		if (stackA.size() == 0) {return null;}
		
		//shift all elements but the first one from stackA to tempStack then back to stackA
		// 1, 2, 3, 4, 5 --> 5, 4, 3, 2 --> 2, 3, 4, 5
		int itterations = stackA.size() - 1;
		for (int i = 0; i < itterations; i++) {
			tempStack.push(stackA.pop());
		}
		
		E popped = stackA.pop(); //flush out stackA
		
		
		for (int i = 0; i < itterations; i++) {//transfer tempStack back to stackA
			stackA.push(tempStack.pop());
		}
		
		return popped;
		
	}
	
	
	
	public String toString() {
		String res = "[";
		
		int itterations = stackA.size();
		for (int i = 0; i < itterations; i++) { //Shift everything over to the tempStack
			tempStack.push(stackA.pop());
		}
		
		for (int i = 0; i < itterations; i++) {//shift it all back in the right way
			E val = tempStack.pop();
			res += val;
			stackA.push(val);
			
			if (i < itterations-1) {
				res+= ", ";
			}
		}
		
		res += "]";
		return res;
	}
	
	/* size of the Queue */
	public int size() {
		return stackA.size();
	}
	
	/* returns true if empty, false otherwise*/
	public boolean empty() {
		return stackA.empty();
	}
	
	public E peek() {
		if (stackA.empty()) {return null;}
		
		
		//shift all elements but the first one from stackA to tempStack then back to stackA, finding the front element
		// 1, 2, 3, 4, 5 --> 5, 4, 3, 2, 1 --> 1, 2, 3, 4, 5
		E element = stackA.peek(); // Some filler element
		
		int itterations = stackA.size();
		for (int i = 0; i < itterations; i++) {
			
			if (i == (itterations - 1)) {
				element = stackA.pop();
				tempStack.push(element);
			}else {
				tempStack.push(stackA.pop());
			}
			
		}
		
		
		for (int i = 0; i < itterations; i++) {//transfer tempStack back to stackA
			stackA.push(tempStack.pop());
		}
		
		return element;
	}
	
}
