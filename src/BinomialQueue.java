import java.lang.Comparable;
public class BinomialQueue <Type extends Comparable<? super Type>>{

	private static class BTNode<Type>{

		Type data;
		BTNode<Type> leftChild;
		BTNode<Type> nextSibling;

		public BTNode(Type x, BTNode<Type> left, BTNode<Type> next){
			data = x; leftChild = left; nextSibling = next;
		}		
		
		public BTNode(Type x){
			this(x, null, null);
		}
	}

	private BTNode<Type>[] theTrees;
	private int size;
	private static final int DEFAULT_CAPACITY = 10;

	@SuppressWarnings("unchecked")
	public BinomialQueue(int queueCapacity){
	
		theTrees =  new BTNode[queueCapacity];
		size = 0;	
	}

	public BinomialQueue(){
		this(DEFAULT_CAPACITY);
	}

	public int size(){
		return size;
	}

	public int capacity(){
		return (1 << theTrees.length - 1);
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public void makeEmpty(){

		for(int i=0; i<theTrees.length; i++)
			theTrees[i] = null;
		size = 0;
	}

	public void merge(BinomialQueue<Type> rhs){  //?

		if(this == rhs)	return;
		if(rhs == null || rhs.isEmpty()) 	return;	
		
		size = this.size() + rhs.size();
		if(size > capacity()) //?
			enlargeTheTrees(Math.max(this.theTrees.length, rhs.theTrees.length) + 1);
	
		BTNode<Type> carry = null;
		for(int i=0; i<theTrees.length; i++){
			int index = 0;
//			if(i < this.theTrees.length){
				if(this.theTrees[i] != null)	index += 1;
//			}
			if(i<rhs.theTrees.length){
				if(rhs.theTrees[i] != null)	index += 2;  //rhs.theTrees??
			}
			if(carry != null)	index += 4;

			switch(index){
				case 0:
				case 1:	break;
				case 2:	this.theTrees[i] = rhs.theTrees[i]; rhs.theTrees[i] = null; break;
				case 4:	this.theTrees[i] = carry; carry = null; break;
				case 3:	carry = mergeTrees(this.theTrees[i], rhs.theTrees[i]); this.theTrees[i] = rhs.theTrees[i] = null; break;
				case 5:	carry = mergeTrees(this.theTrees[i], carry); this.theTrees[i] = null; break;
				case 6:	carry = mergeTrees(rhs.theTrees[i], carry); rhs.theTrees[i] = null; break;
				case 7:	BTNode<Type> tmp = this.theTrees[i]; this.theTrees[i] = carry; 
					carry = mergeTrees(rhs.theTrees[i], tmp); rhs.theTrees[i] = null; break;
			}			
		}	
		rhs.size = 0;
	}

	public void insert(Type x){

		BTNode<Type> newNode = new BTNode<>(x);
		BinomialQueue<Type> queueToBeMerged = new BinomialQueue<>(1);
		queueToBeMerged.theTrees[0] = newNode;
		queueToBeMerged.size++;
		this.merge(queueToBeMerged);
	}
	
	public Type findMin() throws Exception{
		
		BTNode<Type> minimum = theTrees[findMinNode()];
		return minimum.data;
	}

	public Type deleteMin() throws Exception{
		
		int minIndex = findMinNode();
		BTNode<Type> minimum = theTrees[minIndex];
		Type minValue = minimum.data;
		
		BTNode<Type> deletedTree = minimum.leftChild;
		BinomialQueue<Type> deletedQueue = new BinomialQueue<>(minIndex);
		for(int i=minIndex-1; i>=0; i--){
			deletedQueue.theTrees[i] = deletedTree;
			deletedTree = deletedTree.nextSibling;
			deletedQueue.theTrees[i].nextSibling = null;
		}
		deletedQueue.size = (1 << minIndex) - 1;
		theTrees[minIndex] = null; 
		size -= deletedQueue.size;
		merge(deletedQueue);
		return minValue;		
	}

	private int findMinNode() throws Exception{

		if(isEmpty())	throw new Exception();
		int i;
		int minIndex;
		
		for(i=0; theTrees[i]==null; i++)
			;
		for(minIndex=i; i<theTrees.length;i++){
			if(theTrees[i]!=null && theTrees[i].data.compareTo(theTrees[minIndex].data)<0)
				minIndex = i;
		}	
		return minIndex;
	}

	private BTNode<Type> mergeTrees(BTNode<Type> lhs, BTNode<Type> rhs){

		if(lhs == rhs)	return lhs;
		if(lhs == null)	return rhs;
		if(rhs == null)	return lhs;
		if(rhs.data.compareTo(lhs.data) < 0)
			return mergeTrees(rhs, lhs);

		rhs.nextSibling = lhs.leftChild;
		lhs.leftChild = rhs;
		return lhs;
	}

	@SuppressWarnings("unchecked")
	private void enlargeTheTrees(int newLength){
		
		BTNode<Type>[] oldTrees = theTrees;
		theTrees = new BTNode[newLength];
		for(int i=0; i<oldTrees.length; i++)
			theTrees[i] = oldTrees[i];

	}
	
	 public static void main( String [ ] args ) throws Exception
	    {
	        int numItems = 10000;
	        BinomialQueue<Integer> h  = new BinomialQueue<>( );
	        BinomialQueue<Integer> h1 = new BinomialQueue<>( );
	        int i = 37;

	        System.out.println( "Starting check." );

	        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
	            if( i % 2 == 0 )
	                h1.insert( i );
	            else{
	            	if(i == 1)
	            		System.out.println(i);
	                h.insert( i );
	            }

	        h.merge( h1 );
	        for( i = 1; i < numItems; i++ )
	            if( h.deleteMin( ) != i )
	                System.out.println( "Oops! " + i );
	 
	        System.out.println( "Check done." );
	    }
}
