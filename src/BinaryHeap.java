import java.lang.Comparable;

public class BinaryHeap<Type extends Comparable<? super Type>> {


	Type[] theArray;
	int currentSize;
	int capacity;
	final static int DEFAULT_ARRAY_SIZE = 10;

	public BinaryHeap(int size){

		allocateArray(size);		
		clear();
	
	}

	public BinaryHeap(){

		this(DEFAULT_ARRAY_SIZE);

	}

	public int size(){

		return currentSize;
	
	}

	public boolean isEmpty(){
	
		return size() == 0;
	
	}

	public void makeEmpty(){

		clear();

	}

	public void insert(Type x){
	
		if(currentSize == capacity - 1)
			expandHeap();

		int hole = currentSize + 1;

		while(hole/2 > 0 && theArray[hole/2]!= null && myCompare(theArray[hole/2], x) > 0){

				theArray[hole] = theArray[hole/2];
				hole /= 2;
		}

		theArray[hole] = x;
		currentSize++;		
		
	
	}


	public Type deleteMin() throws Exception{

		if(currentSize == 0)	

			throw new Exception();

		Type minValue = theArray[1];

		theArray[1] = theArray[currentSize];

		currentSize--;
		
		percolateDown(1);
		
		return minValue;
	
	}

	private void percolateDown(int hole){

		Type tmp = theArray[hole];

		int child;

		while(hole*2 <= currentSize){

			child = hole * 2;
			if(child + 1 <= currentSize &&
					myCompare(theArray[child+1], theArray[child]) < 0)
				child++;

			if(myCompare(theArray[child], tmp) < 0){

				theArray[hole] = theArray[child];
				hole = child;		
			}

			else{
				break;
			}
 
		}
		
		theArray[hole] = tmp;

	}

	private void expandHeap(){

		Type[] oldArray = theArray;
		allocateArray(2*capacity());
		clear();
		
		for(int i=1; i<oldArray.length; i++){
	
			insert(oldArray[i]);

		}
	}


	private int capacity(){
	
		return capacity;
	
	}

	private int myCompare(Type x, Type y){

		return x.compareTo(y);
	}
	@SuppressWarnings("unchecked")
	private void allocateArray(int arraySize){

		theArray = (Type[])new Comparable[arraySize];
		capacity = arraySize;
	
	}

	private void clear(){

		if(theArray != null){

			for(int i=0; i < theArray.length; i++)
				theArray[i] = null;

			currentSize = 0;

		}
		

	}

	
	public static void main( String [ ] args ) throws Exception
    {
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
        
        h = new BinaryHeap<>( );
    }

}
