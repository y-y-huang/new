//Binary Tree
public class Node {
	public int value;
	public Node leftChild;
	public Node rightChild;

	public Node(int v) {
		this.value = v;
	}
}
public class BinarySearchTree {
	public Node root;
	//查找
	public boolean find(int key){
		if(root == null){
			System.out.println("The tree is empty!");
			return false;
		}
		Node current = root;
		while(current.value != key){
			if(key > current.value)
				current = current.rightChild;
			else
				current = current.leftChild;
			if(current == null)
				return false;
		}
		return true;
	}
	//插入
	public boolean insert(Node node){
		if(root == null){
			root = node;
			return true;
		}
		//树中不允许插入重复的数据项
		if(this.find(node.value) != false){
			System.out.println(This Node has already existed!");
			return false;
		}
		Node current = root;
		while(current != null){
			if(node.value > current.value){
				if(current.rightChild == null){
					current.rightChild = node;
					return true;
				}
				current = current.rightChild;
			}
			else{
				if(current.leftChild == null){
					current.leftChild = node;
					return true;
				}
				current = current.leftChild;
			}
		}
	}
	
		//删除节点需要分3种情况进行讨论
	public boolean delete(int key){
		if(root == null){
			System.out.println("The tree is empty!");
			return false;
		}
		Node targetParent = root;
		Node target = root;
		boolean isLeftChild = true;
		while(target.value != key){
			if(key > target.value){
				targetParent = target;
				target = target.rightChild; 
				isLeftChild = false;
			}
			else{
				targetParent = target;
				target = target.leftChild;
				isLeftChild = true;
			}
			if(target == null)
				break;
		}
		if(target == null){
			System.out.println("Node dosen't exist!" 
					+ "Can not delete.");
			return false;
		}
		//被删除节点为叶子节点
		if(target.leftChild == null &&
				target.rightChild == null){
			if(target.value == root.value){
				root = null;
				return true;
			}
			if(isLeftChild)
				targetParent.leftChild = null;
			else
				targetParent.rightChild = null;
		}
		//被删除节点有1个子节点
		//被删除节点只有右子节点
		else if(target.leftChild == null && 
				target.rightChild != null){
			if(target.value == root.value){
				root = root.rightChild;
				return true;
			}
			if(isLeftChild)
				targetParent.leftChild = target.rightChild;
			else
				targetParent.rightChild = target.rightChild;
		}
		//被删除节点只有左子节点
		else if(target.leftChild != null && 
				target.rightChild == null){
			if(target.value == root.value){
				root = root.leftChild;
				return true;
			}
			if(isLeftChild)
				targetParent.leftChild = target.leftChild;
			else
				targetParent.rightChild = target.leftChild;
		}
		//被删除节点有左右子节点，先找到后续节点，将，然后将后续节点插入至待删除节点的位置
		else{
			Node followingNode = this.getFollowingNode(target);
			if(target.value == root.value)
				root = followingNode;
			else if(isLeftChild)
				targetParent.leftChild = followingNode;
			else
				targetParent.rightChild = followingNode;
			followingNode.leftChild = target.leftChild;
			followingNode.rightChild = target.rightChild;
		}
		return true;
	}
	
	//获取被删除节点的后续节点
	private Node getFollowingNode(Node node2Del){
		Node nodeParent = node2Del;
		//只有被删除节点有左右子节点时，才会调用该方法
		//这里直接调用rightChild是没有问题的
		Node node = node2Del.rightChild;
		while(node.leftChild != null){
			nodeParent = node;
			node = node.leftChild;
		}
		if(node.value != node2Del.rightChild.value)
			nodeParent.leftChild = node.rightChild;
		else
			nodeParent.rightChild = node.rightChild;
		return node;
	}
	
	//前序遍历
	public void preorder_iterator(Node node){
		System.out.print(node.value + " ");
		if(node.leftChild != null)
			this.preorder_iterator(node.leftChild);
		if(node.rightChild != null)
			this.preorder_iterator(node.rightChild);
	}
	
	//中序遍历
	public void inorder_iterator(Node node){
		if(node.leftChild != null)
			this.inorder_iterator(node.leftChild);
		System.out.print(node.value + " ");
		if(node.rightChild != null)
			this.inorder_iterator(node.rightChild);
	}
	
	//后序遍历
	public void postorder_iterator(Node node){
		if(node.leftChild != null)
			this.postorder_iterator(node.leftChild);
		if(node.rightChild != null) 
			this.postorder_iterator(node.rightChild);
		System.out.print(node.value + " ");
	}
	
	//层次遍历
	//利用辅助队列先将根节点入队，当前节点是队头节点，将其出队并访问。
	//如果当前节点的左节点不为空将左节点入队，如果当前节点的右节点不为空将其入队。
    public void levelIterator(BinarySearchTree root)
    {
	  if(root == null)
	  {
		  return ;
	  }
	  LinkedList<BiTree> queue = new LinkedList<BiTree>();
	  BiTree current = null;
	  queue.offer(root);//将根节点入队
	  while(!queue.isEmpty())
	  {
		  current = queue.poll();//出队队头元素并访问
		  System.out.print(current.value +"-->");
		  if(current.left != null)//如果当前节点的左节点不为空入队
		  {
			  queue.offer(current.left);
		  }
		  if(current.right != null)//如果当前节点的右节点不为空，把右节点入队
		  {
			  queue.offer(current.right);
		  }
	  }
	  
    }




