import java.util.Iterator;
public class TestForLinkedListBased {
	
	public void print(String text){
		System.out.println(text);
	}
	
	public static void main(String[] args){

		
		TestForLinkedListBased x = new TestForLinkedListBased();
		MyArrayListUsingLinkedList<String> list = new MyArrayListUsingLinkedList<String>();
		
		//constructor and printList() and isEmpty()
		list.printList();
		x.print("original size = "+list.size());
		x.print("original list "+(list.isEmpty()?"is":"is not")+" empty.");
		
		//add(x)
		x.print("a new string \"hello world\" is "+
			 (list.add("hello world")?"successfully":"unsuccessfully")+" added.");
		list.add("string1");
		list.add("string2");
		x.print("size should be 3: "+(list.size()==3));
		
		
		//add(index,x)
		list.add(0,"hello java");
		list.printList();
		x.print("size shoudl be 4: "+(list.size()==4));
		//list.add(4,""); //should cause out of bounds exception

		//get
		x.print(list.get(1)); //should be hello world

		//delete
		list.delete(2);
		list.delete(2);
		list.printList(); //should be "hello java, hello world"
		x.print("size should be 2: "+(list.size()==2));
		
		//set
		x.print(list.set(1,"byebye world")); //"hello world" 
		list.printList(); //hello java, byebye world

		//iterator
		Iterator i = list.iterator();
		while(i.hasNext()){
			
			String s = (String) i.next();
			x.print(s);
			if(s=="hello java") list.delete(0);
		}

		list.printList(); //shoudl be "hello java"
		System.out.println(list.size()); //shoud be 1

		
		
		
		
	
	
	}
	
}
