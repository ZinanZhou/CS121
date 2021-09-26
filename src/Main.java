import java.util.List;

public class Main {

    // Run "java -ea Main" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)
    
    public static void test1() {
	Graph g = new ListGraph();
	assert g.addNode("a");
	assert g.addNode("b");
	assert g.addNode("c");
	assert g.addNode("d");
	assert g.addNode("e");
	assert g.addEdge("a","b");
	assert g.addEdge("a","c");
	assert g.addEdge("c","e");
	g.addEdge("b","c");
	List<String> pred = g.succ("c");
	for(String p:pred){
		System.out.println(p);
	}



    }
    public static void test2() {
	Graph g = new ListGraph();
	EdgeGraph eg = new EdgeGraphAdapter(g);
	Edge e = new Edge("a", "b");
	assert eg.addEdge(e);
	assert eg.hasEdge(e);
    }
    
    public static void main(String[] args) {
	test1();

	test2();
    }

}

//	public static void test_1() {
//		Graph g1 = new ListGraph();
//		//test node
//		assert g1.addNode("a");
//		assert g1.addNode("b");
//		assert g1.addNode("c");
//		assert g1.addNode("d");
//		assert g1.removeNode("a");
//		assert g1.hasNode("a");
//		assert g1.hasNode("b");
//		assert g1.hasNode("c");
//
//		//remove
//		assert g1.removeNode("d");
//		assert !g1.hasNode("d");
//
//		//test edge
//		assert g1.addEdge("a", "b");
//		assert g1.addEdge("b","c");
//		assert g1.connected("a", "c");
//	}