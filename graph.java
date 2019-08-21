//邻接矩阵实现图
public class EdgeElement {
	int fromvex;
	int endvex;
	int weight;
	
	public EdgeElement(int v1,int v2){
		//对于无权重图的初始化
		fromvex=v1;
		endvex=v2;
		weight=1;
	}
	public EdgeElement(int v1,int v2,int wgt){
		//对于有权重图的初始化
		fromvex=v1;
		endvex=v2;
		weight=wgt;
	}
	public void creatGraph(EdgeElement[] d) {
		int i;
		for(i=0;i<d.length;i++){
			if(d[i]==null) break;
			int v1,v2;
			v1=d[i].fromvex;
			v2=d[i].endvex;
			if(v1<0 || v1>n-1 || v2<0 || v2>n-1 || v1==v2){
				System.out.println("边的顶点序号无效，退出运行");
				System.exit(0);
			}
			if(type==GraphType.NoDirectionNoWeight){
				a[v1][v2]=a[v2][v1]=1;
			}else if(type==GraphType.NoDirectionWeight){
				a[v1][v2]=a[v2][v1]=d[i].weight;
			}else if(type==GraphType.DirectionNoWeight){
				a[v1][v2]=1;
			}else{
				a[v1][v2]=d[i].weight;
			}
		}
		e=i;			//边的数目
	}
}

//邻接表实现图

public class EdgeNode{
	//需要一个存储自身结点
	int adjvex;
	int weight;
	EdgeNode next;
	//无权图
	public EdgeNode(int adj,EdgeNode nt){
		this.adjvex=adj;
		this.next=nt;
		this.weight=1;
	}
	//有权图
	public EdgeNode(int adj,int wgt,EdgeNode nt){
		this.adjvex=adj;
		this.weight=wgt;
		this.next=nt;
	}
	public void creatGraph(EdgeElement[] d) {
		int i;
		for(i=0;i<d.length;i++){//处理边集合  如果边集合重复 那程序不就有问题了么  这点要处理
			if(d[i]==null) break;
			int v1,v2,weight;
			v1=d[i].fromvex;
			v2=d[i].endvex;
			weight=d[i].weight;
			if(v1<0||v1>n-1||v2<0||v2>n-1||v1==v2){
				System.out.println("边的顶点序号无效，退出运行");
				System.exit(0);
			}
			if(type==GraphType.NoDirectionNoWeight){//处理无方向 无权重的图
				a[v1]=new EdgeNode(v2,a[v1]);//把边挂载在主干上,a为EdgeNode类型的一维数组
				a[v2]=new EdgeNode(v1,a[v2]);//处理第二条边
			}else if(type==GraphType.NoDirectionWeight){//处理无向有权图
				a[v1]=new EdgeNode(v2,weight,a[v1]);
				a[v2]=new EdgeNode(v1,weight,a[v2]);
			}else if(type==GraphType.DirectionNoWeight){//处理有向无权图
				a[v1]=new EdgeNode(v2,a[v1]);
			}else {
				a[v1]=new EdgeNode(v2,weight,a[v1]);
			}
		}
		e=i;

	}

}