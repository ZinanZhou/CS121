import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    private HashSet<String> visited;

    public boolean addNode(String n) {
        if(this.nodes.containsKey(n)){
            return false;
        }
        else{
            nodes.put(n,new LinkedList<String>());
            return true;
        }
	     //throw new UnsupportedOperationException();
    }

    public boolean addEdge(String n1, String n2) {
//	     throw new UnsupportedOperationException();
        if(!this.nodes.containsKey(n1)||!this.nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }

        if(n1.equals(n2)) return false;

        else{
            if(!nodes.get(n1).contains(n2)){
                nodes.get(n1).add(n2);
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean hasNode(String n) {
        return(this.nodes.containsKey(n));
//	     throw new UnsupportedOperationException();
    }

    public boolean hasEdge(String n1, String n2) {
        if(!this.nodes.containsKey(n1)){
            return false;
        }
        return(this.nodes.get(n1).contains(n2));
//	     throw new UnsupportedOperationException();
    }

    public boolean removeNode(String n) {
//	     throw new UnsupportedOperationException();
        if(!this.nodes.containsKey(n)){
            return false;
        }
        else{
            nodes.remove(n);
            for(String key:nodes.keySet()){
                nodes.get(key).remove(n);
            }
            return true;
        }
    }

    public boolean removeEdge(String n1, String n2) {
        if(!this.nodes.containsKey(n1)||!this.nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }
        else{
            if(this.nodes.get(n1).contains(n2)){
                this.nodes.get(n1).remove(n2);
                return true;
            }
            return false;
        }
//	     throw new UnsupportedOperationException();
    }

    public List<String> nodes() {
//	     throw new UnsupportedOperationException();
        return new ArrayList<String>(this.nodes.keySet());

    }

    public List<String> succ(String n) {
//	     throw new UnsupportedOperationException();
        if(!this.nodes.containsKey(n)){
            throw new NoSuchElementException();
        }
        else{
            return this.nodes.get(n);
        }
    }

    public List<String> pred(String n) {
//	     throw new UnsupportedOperationException();
        if(!this.nodes.containsKey(n)){
            throw new NoSuchElementException();
        }
        else{
            LinkedList<String> pred = new LinkedList<String>();
            for(String key:nodes.keySet()){
                if(nodes.get(key).contains(n)){
                    pred.add(key);
                }
            }
            return pred;
        }
    }

    public Graph union(Graph g) {
        //add nodes and edges
        for(String key:this.nodes.keySet()){
            if(!g.hasNode(key)){
                g.addNode(key);
            }
            for(String n2 :this.nodes.get(key)){
                if(!g.hasEdge(key,n2)){
                    g.addEdge(key,n2);
                }
            }
        }
        return g;
//	     throw new UnsupportedOperationException();
    }

    public Graph subGraph(Set<String> nodes) {
//	     throw new UnsupportedOperationException();
        for(String key : this.nodes.keySet()){
            if(!nodes.contains(key)){
                this.removeNode(key);
            }
        }
        return this;
    }

    public boolean connected(String n1, String n2) {
        if(!this.nodes.containsKey(n1)||!this.nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }
        if(n1.equals(n2)) return true;

        HashSet<String> visited = new HashSet<String>();
//	     throw new UnsupportedOperationException();
        visited = this.DFS(visited,n1);
        if(visited.contains(n2)){
            return true;
        }
        else return false;
    }

    public  HashSet<String> DFS (HashSet<String> visited,String n){
        if(visited.contains(n)) return visited;
        visited.add(n);
        if(this.nodes.get(n).isEmpty()){
            return visited;
        }
        else {
            for(String key: nodes.get(n)){
                DFS(visited,key);
            }
        }
        return visited;
    }
}
