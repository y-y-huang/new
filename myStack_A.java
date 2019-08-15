
public class myStack_A {
	int stack[]=new int [10];
	int length=0;
	int base=-1;
	public boolean isFull() {
		if(this.length==10) {
			System.out.print("The stack is full!");
			return true;
		}
		else
			return false;
	}
	public boolean isEmpty() {
		if(this.length==0) {
			System.out.print("The stack is Empty!");
			return true;
		}
		else
			return false;
	}
	public void push(int num) {
		if (this.isFull()) {
			System.out.print("Full!Can't push!");
		}
		else {
			this.stack[++base]=num;
			this.length++;
		}


	}
	public int pop(){
		int pop=-1;
		if (this.isEmpty()) {
			System.out.print("Empty!Can't pop!");
		}
		else {
			pop=this.stack[--base];
		}
		return pop;
	}
}
