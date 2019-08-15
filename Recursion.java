public long fibonacci(int n){
        if(n==0||n==1) return 1;
        else {
            return fibonacci(n-1)+fibonacci(n-2);
        }
}     

public long factor(int n){
	if (n==1) return 1;
	else{
		return n*factor(n-1);
	}
}

public class AllSort{    

    public static void perm(char[] buf, int start, int end) {
        if(start == end) {
            for (char c : buf) {
                System.out.print(c);
            }
            System.out.println("");
        } else {
            for(int i = start; i <= end; i++) {
                swap(buf, start, i);
                perm(buf, start + 1, end);
                swap(buf, start, i);
            }
        }
    }

    public static void swap(char[] buf, int i, int j) {
        char temp = buf[i];
        buf[i] = buf[j];
        buf[j] = temp;
    }
}  
