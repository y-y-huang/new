class myStack_L{
	class Node{
		Node next;
		int data;
		public Node(int d){
			this.data=d;
		}
	}
	Node head=null;
	int length=0;
	public boolean isEmpty(){
		if (this.length==0){
			System.out.println("Empty!");
			return r=true;
		}
		else
			return false;
	}
	public void push(int num){
		Node n= new Node(10);
		if(isEmpty()){
			this.head=n;
		}
		else{
			n.next=head.next;
			head=n;
		}
		this.length++;	
	}
	public int pop(){
		int pop=-1;
		if(isEmpty()){
			System.out.println("Empty!");
		}
		else{
			pop=this.head.data;
			this.head=this.head.next;
			this.length--;
		}
	}
}