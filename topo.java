public int topologicalSort() {
    int index = 0;
    int num = mVexs.size();
    int[] ins;               // 入度数组
    char[] tops;             // 拓扑排序结果数组，记录每个节点的排序后的序号。
    Queue<Integer> queue;    // 辅组队列

    ins   = new int[num];
    tops  = new char[num];
    queue = new LinkedList<Integer>();

    // 统计每个顶点的入度数
    for(int i = 0; i < num; i++) {

        ENode node = mVexs.get(i).firstEdge;
        while (node != null) {
            ins[node.ivex]++;
            node = node.nextEdge;
        }
    }

    // 将所有入度为0的顶点入队列
    for(int i = 0; i < num; i ++)
        if(ins[i] == 0)
            queue.offer(i);                 // 入队列

    while (!queue.isEmpty()) {              // 队列非空
        int j = queue.poll().intValue();    // 出队列。j是顶点的序号
        tops[index++] = mVexs.get(j).data;  // 将该顶点添加到tops中，tops是排序结果
        ENode node = mVexs.get(j).firstEdge;// 获取以该顶点为起点的出边队列

        // 将与"node"关联的节点的入度减1；
        // 若减1之后，该节点的入度为0；则将该节点添加到队列中。
        while(node != null) {
            // 将节点(序号为node.ivex)的入度减1。
            ins[node.ivex]--;
            // 若节点的入度为0，则将其"入队列"
            if( ins[node.ivex] == 0)
                queue.offer(node.ivex);    // 入队列

            node = node.nextEdge;
        }
    }

    if(index != num) {
        System.out.printf("Graph has a cycle\n");
        return 1;
    }

    // 打印拓扑排序结果
    System.out.printf("== TopSort: ");
    for(int i = 0; i < num; i ++)
        System.out.printf("%c ", tops[i]);
    System.out.printf("\n");

    return 0;
}