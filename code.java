//链表法解决冲突
class ChainingHashSet<K, V> {
 
	private int num;       			// 当前散列表中的键值对总数
	private int capacity;  			// 散列表的大小
	private SeqSearchST<K, V>[] st;   // 链表对象数组
	
	// 构造函数
	public ChainingHashSet(int initialCapacity){
		capacity = initialCapacity;
		st = (SeqSearchST<K, V>[]) new Object[capacity];
		for(int i = 0; i < capacity; i++){
			st[i] = new SeqSearchST<>();
		}
	}
	
	// hash()方法
	private int hash(K key){
		return (key.hashCode() & 0x7fffffff) % capacity;
	}
	
	public V get(K key){
		return st[hash(key)].get(key);
	}
	
	public void put(K key, V value){
		st[hash(key)].put(key, value);
	}
}
 
// SeqSearchST基于链表的符号表实现
class SeqSearchST<K, V>{
	
	private Node first;
	
	// 结点类
	private class Node{
		K key;
		V value;
		Node next;
		
		// 构造函数
		public Node(K key, V val, Node next){
			this.key = key;
			this.value = val;
			this.next = next;
		}
	}
	
	// get()方法
	public V get(K key) {
		for(Node node = first; node != null; node = node.next){
			if(key.equals(node.key)){
				return node.value;
			}
		}
		return null;
	}
 
	// put()方法
	public void put(K key, V value) {
		// 先查找表中是否已经存在相应的key
		Node node;
		for(node = first; node != null; node = node.next){
			if(key.equals(key)){
				node.value = value;  // 如果key存在，就把当前value插入node.next中
				return;
			}
		}
		
		// 表中不存在相应的key，直接插入表头
		first = new Node(key, value, first);
	}

}


//散列表实现
public class Table{
	private int manyItems;
	private Object[] keys;
	private Object[] values;
	private boolean[] hasBeenUsed;
 
	public Table(int capacity){
		if(capacity <= 0){
			throw new IllegalArgumentException("Capacity is negative.");
		}
		keys = new Object[capacity];
		values =new Object[capacity];
		hasBeenUsed = new boolean[capacity];
	}
	
	/**
	 * 判断表是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return manyItems == 0;
	}
	
	/**
	 * 清空表
	 */
	public synchronized void clear() {
		if(manyItems !=0){
			for(int i = 0;i < values.length;i++){
				keys[i]=null;
				values[i]=null;
				hasBeenUsed[i]=false;
			}
			manyItems = 0;
		}
	}
 
	/**
	 * 判断是否存在指定的关键字
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {
		
		return findIndex(key)!=-1;
	}
 
	
	public Object get(Object key) {
		int index = findIndex(key);
		if(index!=-1){
			return values[index];
		}
		return null;
	}
 
	
	public synchronized Object put(Object key, Object value) {
		int i = findIndex(hash(key));
		Object temp = null;
		if(i != -1){
			//表中已经存在该关键字
			temp = values[i];
			values[i] = value;
			//返回被替换的内容
			return temp;
		}else if(manyItems < values.length){
			//表中不存在该关键字且表未满
			i = hash(key);
			//检查散列码是否有冲突
			if(keys[i]!= null){
				//散列码有冲突，索引前移
				i = nextIndex(i);
			}
			keys[i] = key;
			values[i]= value;
			hasBeenUsed[i] = true;
			manyItems ++;
			return null;
		}else{
			//表已满
			throw new IllegalStateException("Table is full");
		}
	}
 
	public synchronized Object remove(Object key) {
		int index = findIndex(key);
		Object result = null;
		if(index!=-1){
			result = values[index];
			keys[index]= null;
			values[index]= null;
			hasBeenUsed[index]=false;
			manyItems --;
		}
		return result;
	}
 
	/**
	 * 如果在表中找到了指定的关键字，返回指定关键字的索引。否则返回 -1.
	 * @param key
	 * @return
	 */
	public int findIndex(Object key){
		int count = 0;
		int i = hash(key);
		while((count < values.length) && hasBeenUsed[i]){
			//分配的位置已经被使用，而且存在指定的关键字
			if(keys[i].equals(key)){
				return i;
			}
			//编辑遍历的次数，当全部元素都遍历完之后，退出遍历
			count++;
			i = nextIndex(i);
		}
		return -1;
	}
	
	/**
	 * 获取散列码，大小不超过表的大小
	 * @param key
	 * @return
	 */
	public int hash(Object key){
		return Math.abs(key.hashCode())%values.length;
	}
	
	public int nextIndex(int index){
		if(index+1 == values.length){
			return 0;
		}else{
			return index + 1;
		}
	}
	
	/**
	 * 判断指定的位置是否已经被使用
	 * @param index
	 * @return
	 */
	public boolean hasBeenUsed(int index){
		return hasBeenUsed[index];
	}
	
	/**
	 * 返回该表中有多少对键值对。
	 * @return
	 */
	public int size() {
		return manyItems;
	}
}

//LinkedHashMasp实现LRU(简单，但是看不太懂)
package cn.lzrabbit.structure.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuzhao on 14-5-15.
 */
public class LRUCache2<K, V> extends LinkedHashMap<K, V> {
    private final int MAX_CACHE_SIZE;

    public LRUCache2(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        MAX_CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_CACHE_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : entrySet()) {
            sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
//单词树Trie
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
/**
 * 一个只能处理26个字母的单词树（trie)
 * 空间换时间 T(n) = O(n)
 * ps:如果缺陷 欢迎留言
 * @author shundong
 * @data 2018-10-13
 */
 
public class FindWordsTrie{
    //一个Trie树有一个根节点
    private Vertex root;
 
    //内部类or节点类
    protected class Vertex{
        protected int words;
        protected int prefixes;
        //每个节点包含26个子节点(类型为自身)
        protected Vertex[] edges;
        Vertex() {
            words = 0;
            prefixes = 0;
            edges = new Vertex[26];
            for (int i = 0; i < edges.length; i++) {
                edges[i] = null;
            }
        }
    }
 
    public FindWordsTrie () {
        root = new Vertex();
    }
 
    /**
     * 列出List所有单词
     * @return
     */
    public List< String> listAllWords() {
 
        List< String> words = new ArrayList< String>();
        Vertex[] edges = root.edges;
 
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != null) {
                String word = "" + (char)('a' + i);
                depthFirstSearchWords(words, edges[i], word);
            }
        }       
        return words;
    }
 
    /**
     * Depth First在Trie中搜索单词并将它们添加到List中。
     * @param words
     * @param vertex
     * @param wordSegment
     */
    private void depthFirstSearchWords(List words, Vertex vertex, String wordSegment) {
        Vertex[] edges = vertex.edges;
        boolean hasChildren = false;
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != null) {
                hasChildren = true;
                String newWord = wordSegment + (char)('a' + i);               
                depthFirstSearchWords(words, edges[i], newWord);
            }           
        }
        if (!hasChildren) {
            words.add(wordSegment);
        }
    }
 
    public int countPrefixes(String prefix) {
        return countPrefixes(root, prefix);
    }
 
    private int countPrefixes(Vertex vertex, String prefixSegment) {
        if (prefixSegment.length() == 0) { //到达单词的最后一个字符
            return vertex.prefixes;
        }
        char c = prefixSegment.charAt(0);
        int index = c - 'a';
        if (vertex.edges[index] == null) { // 这个词不存在
            return 0;
        } else {
            return countPrefixes(vertex.edges[index], prefixSegment.substring(1));
        }       
    }
 
    public int countWords(String word) {
        return countWords(root, word);
    }   
 
    private int countWords(Vertex vertex, String wordSegment) {
        if (wordSegment.length() == 0) { //到达单词的最后一个字符
            return vertex.words;
        }
        char c = wordSegment.charAt(0);
        int index = c - 'a';
        if (vertex.edges[index] == null) { // 这个词不存在
            return 0;
        } else {
            return countWords(vertex.edges[index], wordSegment.substring(1));
        }       
 
    }
    /**
     * 在Trie上添加一个单词
     * @param word 要添加的词
     */
    public void addWord(String word) {
        addWord(root, word);
    }
    /**
     * 添加指定顶点的单词
     * @param vertex 指定的顶点
     * @param word 要添加的词
     */
    private void addWord(Vertex vertex, String word) {
        if (word.length() == 0) { //如果已添加该单词的所有字符
            vertex.words ++;
        } else {
            vertex.prefixes ++;
            char c = word.charAt(0);
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (vertex.edges[index] == null) { //如果边缘不存在
                vertex.edges[index] = new Vertex();
            }
            addWord(vertex.edges[index], word.substring(1)); //去下一个
        }
    }
    //简单的测试测试
    public static void main(String args[]) 
    {
        FindWordsTrie trie = new FindWordsTrie();
        trie.addWord("cabbage");
        trie.addWord("cabbage");
        trie.addWord("cabbage");
        trie.addWord("cabbage");
        trie.addWord("cabin");
        trie.addWord("berte");
        trie.addWord("cabbage");
        trie.addWord("english");
        trie.addWord("establish");
        trie.addWord("good");
 
        //              System.out.println(trie.root.prefixes);
        //              System.out.println(trie.root.words);
        //              List< String> list = trie.listAllWords();
        //              Iterator listiterator = list.listIterator();
        //              //遍历
        //              while(listiterator.hasNext())
        //              {
        //                  String str = (String)listiterator.next();
        //                  System.out.println(str);
        //              }
        int count = trie.countPrefixes("c");//此处传参
        int count1=trie.countWords("cabbage");
        System.err.println("单词c 前缀个数为:"+count);
        System.err.println("cabbage 单词的个数为:"+count1);
    }
}

//字符串的朴素（暴力）匹配算法
    /**
     * 朴素匹配算法
     *
     * @param sStr 父串
     * @param dStr 子串
     * @return 子串在父串中下标index[int]
     */
    public static int violentMatch(String sStr, String dStr) {
        int sLength = sStr.length();
        int dLength = dStr.length();
        int sIndex = 0, dIndex = 0;

        while (sIndex < sLength && dIndex < dLength) {
            //当前字符匹配
            if (sStr.charAt(sIndex) == dStr.charAt(dIndex)) {
                //父串和子串同时后移一个字符
                sIndex++;
                dIndex++;
            } else {//不匹配则sIndex回溯，dIndex被置为0
                sIndex = sIndex - (dIndex - 1);
                dIndex = 0;
            }
        }
        if (dIndex == dLength) {
            return sIndex - dLength;
        }
        return -1;
    }
//字符串KMP算法（关键求解next数组）
    /**
     * KMP匹配算法
     *
     * @param sStr 父串
     * @param dStr 子串
     * @return 子串在父串中下标index[int]
     */
    public static int KMPMatch(String sStr, String dStr) {
        int sLength = sStr.length();
        int dLength = dStr.length();
        int sIndex = 0, dIndex = 0;
        int[] next = getNextArray(dStr);

        while (sIndex < sLength && dIndex < dLength) {
            //当前字符匹配
            if (dIndex==-1||sStr.charAt(sIndex) == dStr.charAt(dIndex)) {
                //父串和子串同时后移一个字符
                sIndex++;
                dIndex++;
            } else {//不匹配 sIndex不变dIndex取next[j]
                dIndex = next[dIndex];
            }
        }
        if (dIndex == dLength) {
            return sIndex - dLength;
        }
        return -1;
    }
   /**
     * 获取next数组
     *
     * @param destStr 目的字符串
     * @return next数组
     */
    public static int[] getNextArray(String destStr) {
        int[] nextArr = new int[destStr.length()];
        nextArr[0] = -1;
        int k = -1, j = 0;
        while (j < destStr.length() - 1) {
            if (k == -1 || (destStr.charAt(j) == destStr.charAt(k))) {
                ++k;
                ++j;
                nextArr[j] = k;
            } else {
                k = nextArr[k];
            }
        }
        return nextArr;
    }


class ChainingHashSet<K, V> {
 
	private int num;       			// 当前散列表中的键值对总数
	private int capacity;  			// 散列表的大小
	private SeqSearchST<K, V>[] st;   // 链表对象数组
	
	// 构造函数
	public ChainingHashSet(int initialCapacity){
		capacity = initialCapacity;
		st = (SeqSearchST<K, V>[]) new Object[capacity];
		for(int i = 0; i < capacity; i++){
			st[i] = new SeqSearchST<>();
		}
	}
	
	// hash()方法
	private int hash(K key){
		return (key.hashCode() & 0x7fffffff) % capacity;
	}
	
	public V get(K key){
		return st[hash(key)].get(key);
	}
	
	public void put(K key, V value){
		st[hash(key)].put(key, value);
	}
}
 
// SeqSearchST基于链表的符号表实现
class SeqSearchST<K, V>{
	
	private Node first;
	
	// 结点类
	private class Node{
		K key;
		V value;
		Node next;
		
		// 构造函数
		public Node(K key, V val, Node next){
			this.key = key;
			this.value = val;
			this.next = next;
		}
	}
	
	// get()方法
	public V get(K key) {
		for(Node node = first; node != null; node = node.next){
			if(key.equals(node.key)){
				return node.value;
			}
		}
		return null;
	}
 
	// put()方法
	public void put(K key, V value) {
		// 先查找表中是否已经存在相应的key
		Node node;
		for(node = first; node != null; node = node.next){
			if(key.equals(key)){
				node.value = value;  // 如果key存在，就把当前value插入node.next中
				return;
			}
		}
		
		// 表中不存在相应的key，直接插入表头
		first = new Node(key, value, first);
	}

}


//散列表实现
public class Table{
	private int manyItems;
	private Object[] keys;
	private Object[] values;
	private boolean[] hasBeenUsed;
 
	public Table(int capacity){
		if(capacity <= 0){
			throw new IllegalArgumentException("Capacity is negative.");
		}
		keys = new Object[capacity];
		values =new Object[capacity];
		hasBeenUsed = new boolean[capacity];
	}
	
	/**
	 * 判断表是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return manyItems == 0;
	}
	
	/**
	 * 清空表
	 */
	public synchronized void clear() {
		if(manyItems !=0){
			for(int i = 0;i < values.length;i++){
				keys[i]=null;
				values[i]=null;
				hasBeenUsed[i]=false;
			}
			manyItems = 0;
		}
	}
 
	/**
	 * 判断是否存在指定的关键字
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {
		
		return findIndex(key)!=-1;
	}
 
	
	public Object get(Object key) {
		int index = findIndex(key);
		if(index!=-1){
			return values[index];
		}
		return null;
	}
 
	
	public synchronized Object put(Object key, Object value) {
		int i = findIndex(hash(key));
		Object temp = null;
		if(i != -1){
			//表中已经存在该关键字
			temp = values[i];
			values[i] = value;
			//返回被替换的内容
			return temp;
		}else if(manyItems < values.length){
			//表中不存在该关键字且表未满
			i = hash(key);
			//检查散列码是否有冲突
			if(keys[i]!= null){
				//散列码有冲突，索引前移
				i = nextIndex(i);
			}
			keys[i] = key;
			values[i]= value;
			hasBeenUsed[i] = true;
			manyItems ++;
			return null;
		}else{
			//表已满
			throw new IllegalStateException("Table is full");
		}
	}
 
	public synchronized Object remove(Object key) {
		int index = findIndex(key);
		Object result = null;
		if(index!=-1){
			result = values[index];
			keys[index]= null;
			values[index]= null;
			hasBeenUsed[index]=false;
			manyItems --;
		}
		return result;
	}
 
	/**
	 * 如果在表中找到了指定的关键字，返回指定关键字的索引。否则返回 -1.
	 * @param key
	 * @return
	 */
	public int findIndex(Object key){
		int count = 0;
		int i = hash(key);
		while((count < values.length) && hasBeenUsed[i]){
			//分配的位置已经被使用，而且存在指定的关键字
			if(keys[i].equals(key)){
				return i;
			}
			//编辑遍历的次数，当全部元素都遍历完之后，退出遍历
			count++;
			i = nextIndex(i);
		}
		return -1;
	}
	
	/**
	 * 获取散列码，大小不超过表的大小
	 * @param key
	 * @return
	 */
	public int hash(Object key){
		return Math.abs(key.hashCode())%values.length;
	}
	
	public int nextIndex(int index){
		if(index+1 == values.length){
			return 0;
		}else{
			return index + 1;
		}
	}
	
	/**
	 * 判断指定的位置是否已经被使用
	 * @param index
	 * @return
	 */
	public boolean hasBeenUsed(int index){
		return hasBeenUsed[index];
	}
	
	/**
	 * 返回该表中有多少对键值对。
	 * @return
	 */
	public int size() {
		return manyItems;
	}
}

//LinkedHashMasp实现LRU(简单，但是看不太懂)
package cn.lzrabbit.structure.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuzhao on 14-5-15.
 */
public class LRUCache2<K, V> extends LinkedHashMap<K, V> {
    private final int MAX_CACHE_SIZE;

    public LRUCache2(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        MAX_CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_CACHE_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : entrySet()) {
            sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}