//���������ͻ
class ChainingHashSet<K, V> {
 
	private int num;       			// ��ǰɢ�б��еļ�ֵ������
	private int capacity;  			// ɢ�б�Ĵ�С
	private SeqSearchST<K, V>[] st;   // �����������
	
	// ���캯��
	public ChainingHashSet(int initialCapacity){
		capacity = initialCapacity;
		st = (SeqSearchST<K, V>[]) new Object[capacity];
		for(int i = 0; i < capacity; i++){
			st[i] = new SeqSearchST<>();
		}
	}
	
	// hash()����
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
 
// SeqSearchST��������ķ��ű�ʵ��
class SeqSearchST<K, V>{
	
	private Node first;
	
	// �����
	private class Node{
		K key;
		V value;
		Node next;
		
		// ���캯��
		public Node(K key, V val, Node next){
			this.key = key;
			this.value = val;
			this.next = next;
		}
	}
	
	// get()����
	public V get(K key) {
		for(Node node = first; node != null; node = node.next){
			if(key.equals(node.key)){
				return node.value;
			}
		}
		return null;
	}
 
	// put()����
	public void put(K key, V value) {
		// �Ȳ��ұ����Ƿ��Ѿ�������Ӧ��key
		Node node;
		for(node = first; node != null; node = node.next){
			if(key.equals(key)){
				node.value = value;  // ���key���ڣ��Ͱѵ�ǰvalue����node.next��
				return;
			}
		}
		
		// ���в�������Ӧ��key��ֱ�Ӳ����ͷ
		first = new Node(key, value, first);
	}

}


//ɢ�б�ʵ��
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
	 * �жϱ��Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return manyItems == 0;
	}
	
	/**
	 * ��ձ�
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
	 * �ж��Ƿ����ָ���Ĺؼ���
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
			//�����Ѿ����ڸùؼ���
			temp = values[i];
			values[i] = value;
			//���ر��滻������
			return temp;
		}else if(manyItems < values.length){
			//���в����ڸùؼ����ұ�δ��
			i = hash(key);
			//���ɢ�����Ƿ��г�ͻ
			if(keys[i]!= null){
				//ɢ�����г�ͻ������ǰ��
				i = nextIndex(i);
			}
			keys[i] = key;
			values[i]= value;
			hasBeenUsed[i] = true;
			manyItems ++;
			return null;
		}else{
			//������
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
	 * ����ڱ����ҵ���ָ���Ĺؼ��֣�����ָ���ؼ��ֵ����������򷵻� -1.
	 * @param key
	 * @return
	 */
	public int findIndex(Object key){
		int count = 0;
		int i = hash(key);
		while((count < values.length) && hasBeenUsed[i]){
			//�����λ���Ѿ���ʹ�ã����Ҵ���ָ���Ĺؼ���
			if(keys[i].equals(key)){
				return i;
			}
			//�༭�����Ĵ�������ȫ��Ԫ�ض�������֮���˳�����
			count++;
			i = nextIndex(i);
		}
		return -1;
	}
	
	/**
	 * ��ȡɢ���룬��С��������Ĵ�С
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
	 * �ж�ָ����λ���Ƿ��Ѿ���ʹ��
	 * @param index
	 * @return
	 */
	public boolean hasBeenUsed(int index){
		return hasBeenUsed[index];
	}
	
	/**
	 * ���ظñ����ж��ٶԼ�ֵ�ԡ�
	 * @return
	 */
	public int size() {
		return manyItems;
	}
}

//LinkedHashMaspʵ��LRU(�򵥣����ǿ���̫��)
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
//������Trie
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
/**
 * һ��ֻ�ܴ���26����ĸ�ĵ�������trie)
 * �ռ任ʱ�� T(n) = O(n)
 * ps:���ȱ�� ��ӭ����
 * @author shundong
 * @data 2018-10-13
 */
 
public class FindWordsTrie{
    //һ��Trie����һ�����ڵ�
    private Vertex root;
 
    //�ڲ���or�ڵ���
    protected class Vertex{
        protected int words;
        protected int prefixes;
        //ÿ���ڵ����26���ӽڵ�(����Ϊ����)
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
     * �г�List���е���
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
     * Depth First��Trie���������ʲ���������ӵ�List�С�
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
        if (prefixSegment.length() == 0) { //���ﵥ�ʵ����һ���ַ�
            return vertex.prefixes;
        }
        char c = prefixSegment.charAt(0);
        int index = c - 'a';
        if (vertex.edges[index] == null) { // ����ʲ�����
            return 0;
        } else {
            return countPrefixes(vertex.edges[index], prefixSegment.substring(1));
        }       
    }
 
    public int countWords(String word) {
        return countWords(root, word);
    }   
 
    private int countWords(Vertex vertex, String wordSegment) {
        if (wordSegment.length() == 0) { //���ﵥ�ʵ����һ���ַ�
            return vertex.words;
        }
        char c = wordSegment.charAt(0);
        int index = c - 'a';
        if (vertex.edges[index] == null) { // ����ʲ�����
            return 0;
        } else {
            return countWords(vertex.edges[index], wordSegment.substring(1));
        }       
 
    }
    /**
     * ��Trie�����һ������
     * @param word Ҫ��ӵĴ�
     */
    public void addWord(String word) {
        addWord(root, word);
    }
    /**
     * ���ָ������ĵ���
     * @param vertex ָ���Ķ���
     * @param word Ҫ��ӵĴ�
     */
    private void addWord(Vertex vertex, String word) {
        if (word.length() == 0) { //�������Ӹõ��ʵ������ַ�
            vertex.words ++;
        } else {
            vertex.prefixes ++;
            char c = word.charAt(0);
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (vertex.edges[index] == null) { //�����Ե������
                vertex.edges[index] = new Vertex();
            }
            addWord(vertex.edges[index], word.substring(1)); //ȥ��һ��
        }
    }
    //�򵥵Ĳ��Բ���
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
        //              //����
        //              while(listiterator.hasNext())
        //              {
        //                  String str = (String)listiterator.next();
        //                  System.out.println(str);
        //              }
        int count = trie.countPrefixes("c");//�˴�����
        int count1=trie.countWords("cabbage");
        System.err.println("����c ǰ׺����Ϊ:"+count);
        System.err.println("cabbage ���ʵĸ���Ϊ:"+count1);
    }
}

//�ַ��������أ�������ƥ���㷨
    /**
     * ����ƥ���㷨
     *
     * @param sStr ����
     * @param dStr �Ӵ�
     * @return �Ӵ��ڸ������±�index[int]
     */
    public static int violentMatch(String sStr, String dStr) {
        int sLength = sStr.length();
        int dLength = dStr.length();
        int sIndex = 0, dIndex = 0;

        while (sIndex < sLength && dIndex < dLength) {
            //��ǰ�ַ�ƥ��
            if (sStr.charAt(sIndex) == dStr.charAt(dIndex)) {
                //�������Ӵ�ͬʱ����һ���ַ�
                sIndex++;
                dIndex++;
            } else {//��ƥ����sIndex���ݣ�dIndex����Ϊ0
                sIndex = sIndex - (dIndex - 1);
                dIndex = 0;
            }
        }
        if (dIndex == dLength) {
            return sIndex - dLength;
        }
        return -1;
    }
//�ַ���KMP�㷨���ؼ����next���飩
    /**
     * KMPƥ���㷨
     *
     * @param sStr ����
     * @param dStr �Ӵ�
     * @return �Ӵ��ڸ������±�index[int]
     */
    public static int KMPMatch(String sStr, String dStr) {
        int sLength = sStr.length();
        int dLength = dStr.length();
        int sIndex = 0, dIndex = 0;
        int[] next = getNextArray(dStr);

        while (sIndex < sLength && dIndex < dLength) {
            //��ǰ�ַ�ƥ��
            if (dIndex==-1||sStr.charAt(sIndex) == dStr.charAt(dIndex)) {
                //�������Ӵ�ͬʱ����һ���ַ�
                sIndex++;
                dIndex++;
            } else {//��ƥ�� sIndex����dIndexȡnext[j]
                dIndex = next[dIndex];
            }
        }
        if (dIndex == dLength) {
            return sIndex - dLength;
        }
        return -1;
    }
   /**
     * ��ȡnext����
     *
     * @param destStr Ŀ���ַ���
     * @return next����
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
 
	private int num;       			// ��ǰɢ�б��еļ�ֵ������
	private int capacity;  			// ɢ�б�Ĵ�С
	private SeqSearchST<K, V>[] st;   // �����������
	
	// ���캯��
	public ChainingHashSet(int initialCapacity){
		capacity = initialCapacity;
		st = (SeqSearchST<K, V>[]) new Object[capacity];
		for(int i = 0; i < capacity; i++){
			st[i] = new SeqSearchST<>();
		}
	}
	
	// hash()����
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
 
// SeqSearchST��������ķ��ű�ʵ��
class SeqSearchST<K, V>{
	
	private Node first;
	
	// �����
	private class Node{
		K key;
		V value;
		Node next;
		
		// ���캯��
		public Node(K key, V val, Node next){
			this.key = key;
			this.value = val;
			this.next = next;
		}
	}
	
	// get()����
	public V get(K key) {
		for(Node node = first; node != null; node = node.next){
			if(key.equals(node.key)){
				return node.value;
			}
		}
		return null;
	}
 
	// put()����
	public void put(K key, V value) {
		// �Ȳ��ұ����Ƿ��Ѿ�������Ӧ��key
		Node node;
		for(node = first; node != null; node = node.next){
			if(key.equals(key)){
				node.value = value;  // ���key���ڣ��Ͱѵ�ǰvalue����node.next��
				return;
			}
		}
		
		// ���в�������Ӧ��key��ֱ�Ӳ����ͷ
		first = new Node(key, value, first);
	}

}


//ɢ�б�ʵ��
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
	 * �жϱ��Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return manyItems == 0;
	}
	
	/**
	 * ��ձ�
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
	 * �ж��Ƿ����ָ���Ĺؼ���
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
			//�����Ѿ����ڸùؼ���
			temp = values[i];
			values[i] = value;
			//���ر��滻������
			return temp;
		}else if(manyItems < values.length){
			//���в����ڸùؼ����ұ�δ��
			i = hash(key);
			//���ɢ�����Ƿ��г�ͻ
			if(keys[i]!= null){
				//ɢ�����г�ͻ������ǰ��
				i = nextIndex(i);
			}
			keys[i] = key;
			values[i]= value;
			hasBeenUsed[i] = true;
			manyItems ++;
			return null;
		}else{
			//������
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
	 * ����ڱ����ҵ���ָ���Ĺؼ��֣�����ָ���ؼ��ֵ����������򷵻� -1.
	 * @param key
	 * @return
	 */
	public int findIndex(Object key){
		int count = 0;
		int i = hash(key);
		while((count < values.length) && hasBeenUsed[i]){
			//�����λ���Ѿ���ʹ�ã����Ҵ���ָ���Ĺؼ���
			if(keys[i].equals(key)){
				return i;
			}
			//�༭�����Ĵ�������ȫ��Ԫ�ض�������֮���˳�����
			count++;
			i = nextIndex(i);
		}
		return -1;
	}
	
	/**
	 * ��ȡɢ���룬��С��������Ĵ�С
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
	 * �ж�ָ����λ���Ƿ��Ѿ���ʹ��
	 * @param index
	 * @return
	 */
	public boolean hasBeenUsed(int index){
		return hasBeenUsed[index];
	}
	
	/**
	 * ���ظñ����ж��ٶԼ�ֵ�ԡ�
	 * @return
	 */
	public int size() {
		return manyItems;
	}
}

//LinkedHashMaspʵ��LRU(�򵥣����ǿ���̫��)
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