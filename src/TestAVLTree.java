
public class TestAVLTree {

	public static void main(String[] args){

		MyAVLTree<String> tree = new MyAVLTree<>(new StringIgnoreCaseComparator());
		//isEmpty
		System.out.println(tree.isEmpty());
		//contains
		System.out.println(tree.contains("zebra"));

		//add
		tree.insert("Avery");
		tree.insert("bean");
		tree.insert("carly");
		tree.insert("Mary");
		tree.insert("santi");
		tree.printTree();

		//double rotation
		tree.insert("David");
		tree.printTree();

		//single rotation
		tree.insert("Terry");
		tree.insert("zebra");
		tree.printTree();

		//contains
		System.out.println(tree.contains("zebra"));
		System.out.println(tree.contains("Zebra"));
	
		//findmax,findmin
		System.out.println(tree.findMax());
		System.out.println(tree.findMin());

		//remove
		tree.remove("CARLY");
		tree.printTree();
		
		//get
		System.out.println(tree.get("bean").data);

		//makeEmpty
		tree.makeEmpty();
		System.out.println(tree.isEmpty());
		

	}
	
	public void print(Object s){
		System.out.println(s.toString());
	}
	
}
