public class MinHeap{
	public static void heapSort(int[] array) {
		//初始建堆
		for(int i=array.length/2-1; i>=0; i--) {
			adjustHeap(array, i, array.length);    //构建小顶堆
		}
		//堆排序
		for(int i=array.length-1; i>0; i--) {
			int temp = array[0];
			array[0] = array[i];
			array[i] = temp;
			adjustHeap(array, 0, i);
		}
	}
	
	public static void adjustHeap(int[] array, int i, int length) {
		int temp; //存放待调整的父节点
		int child;
		for(temp = array[i]; i*2 <= length-1; i = child) {
			child = i*2;
			if(child != (length-1) && array[child] > array[child+1])   
				child++;                  //找出两个孩子中比较小的结点
			if(array[child] < temp)                                    
				array[i] = array[child];
			else
				break;
		}
		array[i] = temp;
	}
}