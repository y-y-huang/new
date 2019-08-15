myQueue_L{
	class Node{
		Node next;
		int data;
		public Node(int d){
			this.data=d;
		}
	}
	Node head=null;
	Node tail=null;
	int length=0;
	public boolean isEmpty(){
	if(this.length==0)
	{
		System.out.println("the queue is Empty!");
		return true;
	}
	else
		return false;
	}
	public void push(int num){
		Node n=new Node(num);
		if(isEmpty()){
			this.head=n;
			this.tail=n;
			n.next=null;
		}
		else{
			this.tail.next=n;
			n.next=null;
			this.tail=n;
		}
		this.length++;
	}
	pubic int pop(){
		int pop=-1;
		if(isEmpty()){
			System.out.println("the queue is Empty!");
		}
		else{
			pop=head.data;
			head=head.next;
		}
	}
}