import java.lang.Iterable;
import java.util.Iterator;
import java.lang.ArrayIndexOutOfBoundsException;

public class MyArrayListUsingArray<Type> implements Iterable<Type>{

	//fields
	private Type[] myArray;
	private int size;
	private final int ORIGINAL_LENGTH = 10;
	
	//constructor
	public MyArrayListUsingArray(){
		clear();
	}

	//crucial methods
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public void add(Type x){
		add(size(), x);  //default add to tail
	}

	public void trimToSize(){
		ensureCapacity(size());
	}
	public void add(int index, Type x){

		if(index < 0 || index > size())
			throw new ArrayIndexOutOfBoundsException();

		if(myArray.length == size)
			ensureCapacity(size*2);

		if(index < size()){
			for(int i=size();i>index;i--)
				myArray[i] = myArray[i-1];
		}

		myArray[index] = x;
		size++;
	}

	public Type set(int index, Type x){

		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		Type overwrittenItem = myArray[index];
		myArray[index] = x;
		return overwrittenItem;
		
	}
	
	public Type delete(int index){

		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		Type deletedItem = myArray[index];
		for(int i=index; i<size()-1;i++){
			myArray[i] = myArray[i+1];
		}
		size--;
		return deletedItem;

	}

	public Type get(int index){
		if(index < 0 || index >= size())
			throw new ArrayIndexOutOfBoundsException();
		return myArray[index];
	}


	//iterator
	public Iterator<Type> iterator(){
		
		return new ArrayListIterator();
	}

	public class ArrayListIterator implements Iterator<Type>{
		
		//fields
		private int current;
		private boolean okToRemove;

		//constructor
		public ArrayListIterator(){
			current = 0;
			okToRemove = true;
		}		


		//implemented methods
		public boolean hasNext(){
			return current<size();
		}

		public Type next(){
			if(hasNext()) {
				Type next = myArray[current++];
				okToRemove = true;
				return next;
			}
			else {throw new java.util.NoSuchElementException();}
		}

		public void remove(){
			if(okToRemove){
				MyArrayListUsingArray.this.delete(--current);
				okToRemove = false;
			}
			//else??
				
		}
	}

	//maintenance
	private void clear(){
		size = 0;
		ensureCapacity(ORIGINAL_LENGTH);
	}

	private void ensureCapacity(int newSize){
		if(newSize < size())
			return;
		Type[] newArray = (Type[]) new Object[newSize];
		for(int i=0; i<size(); i++){
			newArray[i] = myArray[i];
		}
		myArray = newArray;
		
	}

	//others
	public void printList(){
		for(int i=0;i<size();i++)
			System.out.print(myArray[i]+" ");
	}

}
