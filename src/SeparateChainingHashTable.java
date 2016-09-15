import java.util.LinkedList;
import java.util.List;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate intial size or default of 101
//
// ****************PUBLIC OPERATIONS*************************
// void insert(x)			--> Insert x
// void remove(x)			--> Remove x
// boolean contains(x)		--> Return true if x is present
// void makeEmpty()			--> Remove all items

/**
 * Separate chaining table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author SMO1SZH
 */
public class SeparateChainingHashTable<Type> {

	//fields
	private int size;
	private final static int DEFAULT_TABLE_SIZE = 101;
	private List<Type>[] lists;

	/**
	 * Construct the hash table.
	 */
	public SeparateChainingHashTable(){

		this(DEFAULT_TABLE_SIZE);
	}
	
	/**
	 * Construct the hash table.
	 * @param size approximate table size.
	 */
	@SuppressWarnings("unchecked")
	public SeparateChainingHashTable(int size){
		
		lists = new LinkedList[nextPrime(size)];
		for(int i = 0; i < lists.length; i++)
			lists[i] = new LinkedList<>();
	}

	/**
	 * Insert into the hash table. If the item is
	 * already present, then do nothing.
	 * @param x the item to insert.
	 */
	public void insert(Type x){
		
		List<Type> whichList = lists[ myhash(x) ];
		
		if(whichList == null)
			System.out.println("whichList == null");
		
		if( !whichList.contains(x)){
			
			whichList.add(x);
			size++;
			
			//Rehash
			if(size == lists.length)
				rehash();
		}
		
	}
	
	/**
	 * Returns true if the table is empty.
	 */
	public boolean isEmpty(){
		
		return size == 0;
	}

	/**
	 * Remove the items in table.
	 */
	public void makeEmpty(){

		for(int i = 0; i < lists.length; i++)
			lists[i].clear();
		size = 0;
	}

	/**
	 * Returns true if x is present in table.
	 * @param x item to be searched.
	 * @return true if x is present in table.
	 */
	public boolean contains(Type x){
		
		List<Type> list = lists[myhash(x)];

		return list.contains(x);
		
	}

	/**
	 * Remove x from list. If x is not present, 
	 * do nothing and return false. If x is successfully
	 * removed, return true.
	 * @param x the item to be removed.
	 */
	public boolean remove(Type x){
	
		if(lists == null || !contains(x))
			return false;
		
		List<Type> list = lists[myhash(x)];

		
		list.remove(x);	
		size--;

		return true;
	}

	/**
	 * Calculate the hash value of x 
	 * by calling hashCode() on x
	 * and then getting modulos of 
	 * this value on the table size.
	 * @param x 
	 * @return hash value of x.
	 */
	private int myhash(Type x){

		int hashVal = x.hashCode();

		while(hashVal >= lists.length)
			hashVal -= lists.length;
		
		while(hashVal < 0) 
			hashVal+= lists.length;
	
//		System.out.println("myhash("+x+") = "+hashVal);
		return hashVal;
	}
	
	/**
	 * Rehash the table to approximately twice the current
	 * size.
	 */
	private void rehash(){
		rehash( 2*lists.length );
	}
	
	/**
	 * Rehash the table to approximately
	 * the given size.
	 * @param size new size of hash table.
	 */
	@SuppressWarnings("unchecked")
	private void rehash(int newTableSize){

		if(!isPrime(newTableSize))		
			newTableSize = nextPrime(newTableSize);
		System.out.println("Rehashing from " + lists.length + " to " + newTableSize + "...");
		
		LinkedList<Type>[] oldLists = (LinkedList<Type>[]) lists;
		lists = new LinkedList[newTableSize];
		size = 0;
		
		for(int i = 0; i < newTableSize; i++)
			lists[i] = new LinkedList<>();
			
		for(LinkedList<Type> list: oldLists){
			for(Type x: list)
				insert(x);
		}
//		System.out.print("\nNew hash table: ");
//		this.printHashTable();
	}

	/**
	 * Returns true if x is a prime number.
	 */
	private boolean isPrime(int x){

		if(x == 2 || x == 3)
			return true;

		if(x < 2 || x % 2 == 0)
			return false;
		
		for(int i = 3; i*i < x; i += 2){
			if(x % i == 0)
				return false;
		}
		return true;

	}

	/**
	 * Find the next prime number of x.
	 */
	private int nextPrime(int x){

		if(x % 2 == 0) 
			x += 1;

		while(!isPrime(x)){
			x += 2;
		}

		return x;
	}
	
	public void printHashTable(){
		
		System.out.println("\nSize = "+ size);
		for(int i = 0; i < lists.length; i++){
			if(lists[i] != null && lists[i].size() > 0){
				System.out.print("\n List ["+ i +"]: ");
				for(Type x: lists[i])
					System.out.print(x + " ");
			}
		}
	}
	
	public static void main(String[] args){
		
//		SeparateChainingHashTable<String> table = new SeparateChainingHashTable<>(1);
//		table.insert("Chocolate-cake");
//		table.insert("Sweet-Onion");
//		table.insert("American-Coffee");
//		table.insert("Apple-Pie");
//		table.insert("Pommes");
//		table.insert("Coca-cola");
//		table.insert("Fried-chicken");
//		for(int i = 1; i <= 10; i++)
//		table.insert("Delicious-"+i);
//		table.remove("doesn't-exist");
//		table.printHashTable();
		
		SeparateChainingHashTable<Integer> H = new SeparateChainingHashTable<>( );

        long startTime = System.currentTimeMillis( );
        
        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert( i );
        for( int i = 1; i < NUMS; i+= 2 )
            H.remove( i );

        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( i ) )
                System.out.println( "Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( i ) )
                System.out.println( "OOPS!!! " +  i  );
        }
        
        long endTime = System.currentTimeMillis( );
        
        System.out.println( "Elapsed time: " + (endTime - startTime) );
		
	}
}
