import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
//	     throw new UnsupportedOperationException();
        //add nodes and egde
       return g.addNode(e.getSrc())&&g.addNode(e.getDst())&&g.addEdge(e.getSrc(),e.getDst());
    }

    public boolean hasNode(String n) {
	     return g.hasNode(n);
    }

    public boolean hasEdge(Edge e) {
        return g.hasEdge(e.getSrc(),e.getDst());
//	     throw new UnsupportedOperationException()
    }

    public boolean removeEdge(Edge e) {
//	     throw new UnsupportedOperationException();
        return g.removeEdge(e.getSrc(),e.getDst());
    }

    public List<Edge> outEdges(String n) {
//      throw new UnsupportedOperationException();
        List<String> n2 = g.succ(n);
        ArrayList<Edge> edges = new ArrayList<>();
        for(String node : n2){
            edges.add(new Edge(n,node));
        }
        return edges;
    }

    public List<Edge> inEdges(String n) {
//      throw new UnsupportedOperationException();
        List<String> n2 = g.pred(n);
        ArrayList<Edge> edges = new ArrayList<>();
        for(String node : n2){
            edges.add(new Edge(node,n));
        }
        return edges;
    }

    public List<Edge> edges() {
//      throw new UnsupportedOperationException();
        ArrayList<Edge> edges = new ArrayList<>();
        List<String> nodes = g.nodes();
        for(String n:nodes){
            edges.addAll(this.outEdges(n));
        }
        return edges;
    }

    public EdgeGraph union(EdgeGraph g) {
//      throw new UnsupportedOperationException();
        for(Edge edge:this.edges()){
            if(!g.hasEdge(edge)){
                g.addEdge(edge);
            }
        }
        return g;
    }

    public boolean hasPath(List<Edge> e) {
	     if(e.isEmpty()) return true;
	     //check Badpath
        for(int i=0;i<e.size()-1;i++){
            if(!e.get(i).getDst().equals(e.get(i+1).getSrc())){
                throw new BadPath();
            }
        }
        //check p
        for(Edge edge:e){
            if(!this.hasEdge(edge)){
                return false;
            }
        }
        return true;
    }

}
