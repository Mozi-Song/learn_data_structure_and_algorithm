import java.lang.Iterable;
import java.util.Iterator;

public class MyArrayListUsingLinkedList<Type> implements Iterable<Type>{

	//fields
	private TypeNode<Type> beginMarker;
	private TypeNode<Type> endMarker;
	private int size;
	private int modCount = 0;

	//constructor
	public MyArrayListUsingLinkedList(){
		
		clear();		

	}

	public void clear(){

		beginMarker = new TypeNode<Type>(null,null,null);
		endMarker = new TypeNode<Type>(null,beginMarker,null);
		beginMarker.next = endMarker;
		size = 0;
		modCount++;

	}

	//crucial methods
	public boolean isEmpty(){

		return size() == 0;
	}

	public int size(){
		
		return size;
	}

	public boolean add(Type x){  //??

		TypeNode<Type> newNode = new TypeNode<Type>(x, endMarker.prev, endMarker);
		endMarker.prev.next = newNode;
		endMarker.prev = newNode;

		size++;
		modCount++;
		
		return true;

	}

	public void add(int index, Type x){
	
		if(index >= size() || index < 0)
			throw new java.lang.IndexOutOfBoundsException();

		TypeNode<Type> pointer = getNode(index);		
		TypeNode<Type> newNode = new TypeNode<Type>(x, pointer.prev,pointer);		
		pointer.prev.next = newNode;
		pointer.prev = newNode;

		size++;
		modCount++;
	}

	public Type get(int index){

		TypeNode<Type> pointer = getNode(index);
		
		return pointer.data;		


	}

	public Type delete(int index){
		TypeNode<Type> pointer = getNode(index);

		return delete(pointer);
	}



	public Type set(int index, Type x){

		TypeNode<Type> pointer = getNode(index);	
			
		Type overwritten = pointer.data;
		pointer.data = x;
		modCount++;
	
		return overwritten;

	}

	public void printList(){
		
		TypeNode<Type> n = beginMarker.next;
		while(n != endMarker){
			if(n == endMarker.prev)
				System.out.println(n.data);
			else{
				System.out.print(n.data+", ");
			}
			n = n.next;
		}
		System.out.println("");
	}


	//nested class
	private static class TypeNode<Type>{
	
		Type data;
		TypeNode<Type> prev;
		TypeNode<Type> next;

		public TypeNode(Type data, TypeNode<Type> prev, TypeNode<Type> next){
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	//implementations

	public Iterator<Type> iterator(){
			
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<Type>{ //private??

		private TypeNode<Type> current;
		private boolean okToRemove;
		private int expectedModCount;

		public ArrayListIterator(){
			
			current =  beginMarker.next;
			okToRemove = false;
			expectedModCount = modCount;
		}

		public boolean hasNext(){

			return current != endMarker;
		}

		public Type next(){

			if(expectedModCount != modCount)
				throw new java.util.ConcurrentModificationException();
		
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			
			TypeNode<Type> next = current;
			current = current.next;
			okToRemove = true;
		
			return next.data;		

		}

		public void remove(){
			
			if(!okToRemove)
				throw new java.util.ConcurrentModificationException();
			if(expectedModCount != modCount)
				throw new java.lang.IllegalStateException();
			
			delete(current.prev);
			okToRemove = false;
			expectedModCount++;
			

		}

	}
	


	//private methods


	private TypeNode<Type> getNode(int index){

		if(index >= size() || index < 0)
			throw new java.lang.IndexOutOfBoundsException();

		TypeNode<Type> pointer;
		if(index < size()/2){
			pointer = beginMarker;
			for(int i=-1;i<index;i++)
				pointer = pointer.next;
		}
		else{
			pointer = endMarker;
			for(int i=size();i>index;i--)
				pointer = pointer.prev;
		}
		return pointer;
	}

	private Type delete(TypeNode<Type> node){
		
		Type deletedData = node.data;
		node.prev.next = node.next;
		node.next.prev = node.prev;

		modCount++;
		size--;

		return deletedData;
		
	}
	
}
