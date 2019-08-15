class recurrL {   
    int[] a;   
    int front;     
    int rear;     
    public recurrL(int size){   
        a = new int[size];   
        front = 0;   
        rear =0;   
    }   
 
    public boolean enqueue(int obj){   
        if((rear+1)%a.length==front){   
            return false;   
        }   
        a[rear]=obj;   
        rear = (rear+1)%a.length;   
        return true;   
    }   

    public int dequeue(){   
        if(rear==front){   
            return null;   
        }   
        int obj = a[front];   
        front = (front+1)%a.length;   
        return obj;   
    }   
} 

