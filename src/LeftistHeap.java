import java.lang.Comparable;
import java.lang.Math;

public class LeftistHeap<Type extends Comparable<? super Type>> {

	private static class HeapNode<Type>{

		HeapNode<Type> left;
		HeapNode<Type> right;
		int npl;
		Type data;

		public HeapNode(Type x){
		
			data = x;
			left = null;
			right = null;
			npl = 0;
			
		}
	}

	int currentSize;
	HeapNode<Type> root;


	public LeftistHeap(){

		this(null);
	
	}


	public LeftistHeap(HeapNode<Type> n){

		root = n;
		currentSize = 0;

	}

	public int size(){

		return currentSize;
		
	}

	public int getNpl(HeapNode<Type> n){

		if(n == null)
			return -1;

		else
			return Math.min(getNpl(n.left), getNpl(n.right)) + 1;
	
	}
	
	public void mergeWith(LeftistHeap<Type> rhs) throws Exception{
		
		if(rhs == this)
			return;
		currentSize += rhs.size();
		root = merge(this.root, rhs.root);
		rhs.root = null;
	
	}

	public void insert(Type x) throws Exception{
	
		HeapNode<Type> newNode = new HeapNode<Type>(x);
		
		root = merge(root, newNode);

		currentSize++;

	}

	
	public Type findMin() throws Exception{

		if(root == null)		
			throw new Exception();

		return root.data;	

	}

	public Type deleteMin() throws Exception{

		Type deleted = findMin();

		root = merge(root.left, root.right);
		
		currentSize--;		

		return deleted;
	
	}	


	private HeapNode<Type> merge(HeapNode<Type> h1, HeapNode<Type> h2) throws Exception{
	
		if(h1 == null)
			return h2;
		if(h2 == null)
			return h1;

		if(h1.data.compareTo(h2.data) > 0)
			return merge(h2, h1);

		h1.right = merge(h1.right, h2);

		checkLeftist(h1);
		h1.npl = getNpl(h1);

		return h1;

	}

	private void checkLeftist(HeapNode<Type> n){

		if(getNpl(n.left) < getNpl(n.right)){

			HeapNode<Type> tmp = n.left;
			n.left = n.right;
			n.right = tmp;
	
		}
	
	}
	
	public void print(){

		print(root);
		
	}
	
	private void print(HeapNode<Type> node){
		
		if(node == null) return;
		System.out.println(node.data+" ("+node.npl+") left:"+((node.left==null)?"null":node.left.data)
				+" right="+((node.right==null)?"null":node.right.data));
		print(node.left);
		print(node.right);
		
	}

	
	public static void main( String [ ] args ) throws Exception
    {
        int numItems = 100;
        LeftistHeap<Integer> h  = new LeftistHeap<>( );
        LeftistHeap<Integer> h1 = new LeftistHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            if( i % 2 == 0 )
                h1.insert( i );
            else
                h.insert( i );
        h1.print();
        h.mergeWith( h1 );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }

}
