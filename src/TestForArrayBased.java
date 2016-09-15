import java.util.Iterator;

public class TestForArrayBased{

  public static void main(String[] args){

	MyArrayListUsingArray<Integer> newArrayList = new MyArrayListUsingArray<Integer>();

	//test crucial methods
	
	//add(int, Type)
	for(int i=0; i<10; i++){
		newArrayList.add(i,i);  //0,1,2,3,4,5,6,7,8,9
	}
	
	//size
	System.out.println("size after setup = "+newArrayList.size());
	newArrayList.printList();

	//isEmpty
	System.out.println("Current ArrayList is "+ (newArrayList.isEmpty()?"":"not ")+"empty.");

	//add(Type) and expandSizeTo()
	newArrayList.add(10);
	newArrayList.printList();
	System.out.println("size after adding 10 = "+newArrayList.size()); //0,1,2,3,4,5,6,7,8,9,10

	//delete
	newArrayList.delete(5);
	newArrayList.printList();
	System.out.println("size after deleting 5 = "+newArrayList.size()); //0,1,2,3,4,6,7,8,9,10

	//set
	newArrayList.set(1,55);  //0,55,2,3,4,6,7,8,9,10
	newArrayList.printList();
	System.out.println("size after setting 55 = "+newArrayList.size()); 
	//trimToSize
	newArrayList.trimToSize();
	newArrayList.add(11); //0,55,2,3,4,6,7,8,9,10,11
	newArrayList.printList();
	System.out.println("size after adding 11 = "+newArrayList.size()); 
	
	//iterator
	Iterator<Integer> myIterator = newArrayList.iterator();
	while(myIterator.hasNext()){
		int a = myIterator.next();
		if(a == 9) myIterator.remove();
	}  //0,55,2,3,4,6,7,8,10,11
	newArrayList.printList();
	System.out.println("size after removing 9 = "+newArrayList.size()); 
	
	//test wrong next
	myIterator.next();
	//get wrong index
	newArrayList.get(10);

	//delete wrong index
	newArrayList.delete(10);
	

  }
		
}
