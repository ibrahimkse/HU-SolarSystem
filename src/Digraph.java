// A Java program to print topological sorting of a DAG
import java.util.*;

// This class represents a directed graph using adjacency
// list representation
public class Digraph
{
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency List

    //Constructor
    Digraph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    void addEdge(int v,int w) { adj[v].add(w); }

    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean[] visited,
                             Stack stack)
    {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent to this
        // vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        // Push current vertex to stack which stores result
        stack.push(v);
    }

    // The function to do Topological Sort. It uses
    // recursive topologicalSortUtil()
    public int[] topologicalSort()
    {
        int[] arr = new int[V];
        Stack stack = new Stack();

        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Call the recursive helper function to store
        // Topological Sort starting from all vertices
        // one by one
        for (int i = 0; i < V; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);

        int counter = 0;
        while (!stack.empty()){
            arr[counter] = (int) stack.pop();
            counter++;
        }
        return arr;
    }
}
// This code is contributed by Aakash Hasija
