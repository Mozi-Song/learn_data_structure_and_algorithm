
public class QuadraticProbingHashTable<Type>{

	private int currentSize;
	private int occupied;
	private final static int DEFAULT_TABLE_SIZE = 101;
	private HashEntry<Type>[] theArray;
	

	public QuadraticProbingHashTable(){
		
		this(DEFAULT_TABLE_SIZE);
	}

	@SuppressWarnings("unchecked")
	public QuadraticProbingHashTable(int size){

		if(!isPrime(size))		
			size = nextPrime(size);

		theArray = new HashEntry[size]; //!!
		doClear();
	}	

	private static class HashEntry<Type>{

		public boolean isActive;
		public Type data;

		@SuppressWarnings("unused")
		public HashEntry(Type x){

			this(x, true);
		}

		public HashEntry(Type x, boolean b){
	
			data = x; isActive = b;
		}
	
	}

	public int size(){
		
		return currentSize;
	}
	
	public void makeEmpty(){

		doClear();
	}
	
	private void doClear(){
		
		currentSize = 0;
		occupied = 0;
		
		for(int i = 0; i < theArray.length; i++)
			theArray[i] = null;
	}

	public boolean insert(Type x){

		int pos = findPos(x);

		if(theArray[pos] != null && theArray[pos].isActive)
			return false;
		if(theArray[pos] == null)
			occupied++;
		
		theArray[pos] = new HashEntry<Type>(x, true);
		currentSize++;
	
		if(occupied > 0.5 * theArray.length)
			rehash();
		
		return true;	
	}
	
	@SuppressWarnings("unchecked")
	private void rehash(){
		
		HashEntry<Type>[] oldArray = theArray;
		theArray = new HashEntry[nextPrime(2 * oldArray.length)];
		currentSize = 0;
		occupied = 0;
		
		for(int i = 0; i < oldArray.length; i++){	
			if(isActive(oldArray[i]))
				insert(oldArray[i].data);
		}
		
	}

	public boolean remove(Type x){
		
		int pos = findPos(x);
		if(theArray[pos] != null && theArray[pos].isActive){
			
			theArray[pos].isActive = false;
			currentSize--;
			return true;
		}
		else{
			return false;
		}
		
	}

	public boolean contains(Type x){

		int pos = findPos(x);
		return (isActive(theArray[pos]));
	}


	private int myhash(Type x){

		int hashVal = x.hashCode();
		
		hashVal %= theArray.length;
		
		if(hashVal < 0)
			hashVal += theArray.length;
		
		return hashVal;
	}

	private int findPos(Type x){ //?
	
		int hop = 1;
		int nextPos = myhash(x);
		

		while(theArray[nextPos] != null && 
				!theArray[nextPos].data.equals(x)){

			nextPos = (nextPos + hop);
			hop += 2;
			if(nextPos >= theArray.length)
				nextPos -= theArray.length;
//			System.out.println("checking pos of "+x+": "+nextPos);
		}
		return nextPos;

	}

	private static boolean isPrime(int i){

		if(i == 2 || i == 3)
			return true;

		if(i == 1 || i % 2 == 0)
			return false;

		for(int j = 3; j*j < i; j += 2){

			if(i % j == 0)
				return false;
		}
		return true;
		
	}

	private int nextPrime(int i){

		if(i % 2 == 0) i++;

		while(!isPrime(i))
			i += 2;

		return i;
	}
	
	public void printTable(){
		
		System.out.println("\nTotal Capacity: "+theArray.length
				+"; "+"Size = "+currentSize+", Occupied = "+occupied);
		for(int i = 0; i < theArray.length; i++){
			if(isActive(theArray[i]))
				System.out.print("\nEntry ["+i+"]: "+theArray[i].data);
		}
		
	}
	
	private boolean isActive(HashEntry<Type> h){
		
		return (h != null) && (h.isActive == true);
	}
	
	public static void main(String[] args){
		
//		QuadraticProbingHashTable<Integer> table = new QuadraticProbingHashTable<>();
//		table.insert(5);
//		table.insert(10);
//		table.printTable();
//		table.remove(4);
//		table.remove(5);
//		System.out.println(table.contains(5));
//		table.printTable();
		
		 QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<>( );

	        
	        long startTime = System.currentTimeMillis( );
	        
	        final int NUMS = 2000000;
	        final int GAP  =   37;

	        System.out.println( "Checking... (no more output means success)" );

	        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ){
	            H.insert( ""+i );	
	        }
	        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
	            if( H.insert( ""+i ) )
	                System.out.println( "OOPS!!! " + i );
	        for( int i = 1; i < NUMS; i+= 2 )
	            H.remove( ""+i );

	        for( int i = 2; i < NUMS; i+=2 )
	            if( !H.contains( ""+i ) )
	                System.out.println( "Find fails " + i );

	        for( int i = 1; i < NUMS; i+=2 )
	        {
	            if( H.contains( ""+i ) )
	                System.out.println( "OOPS!!! " +  i  );
	        }
	        
	        long endTime = System.currentTimeMillis( );
	        
	        System.out.println( "Elapsed time: " + (endTime - startTime) );
		
	}
	
	

}
