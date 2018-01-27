import java.util.ArrayList;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 5/18/2017
 * Assignment:
 */
public class Graph
{
    private ArrayList<Node> vertices;
    private ArrayList<Edge> edges;
    private Node root = null;

    public Graph()
    {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Node node) { vertices.add(node); }

    public void addVertex(String name, int distance)
    {
        vertices.add(new Node(name, distance));
    }

    public void addEdge(Node u, Node v, int edgeWeight)
    {
        Edge temp = new Edge(u, v, edgeWeight);
        u.outDegree.add(temp);
        v.inDegree.add(temp);
        edges.add(temp);
    }

    public Node createVertex(String name)
    {
        return new Node(name);
    }

    public boolean hasVertex(String name)
    {
        return vertices.contains(new Node(name, Node.DVW));
    }

    class Node
    {
        ArrayList<Edge> inDegree = new ArrayList<>();
        ArrayList<Edge> outDegree = new ArrayList<>();
        String name = "";

        static final int DVW = 0;
        int weight = DVW;
        int color = 0;

        static final int WHITE = 0;
        static final int GRAY = 1;
        static final int BLACK = 2;

        // methods
        // 3 constructors
        Node() {this(null, Integer.MAX_VALUE);}

        Node(String name)
        {
            this(name, DVW);
        }

        Node(String name, int weight)
        {
            this.name = name;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o)
        {
            Node temp = (Node) o;
            if(temp.name.equalsIgnoreCase(this.name))
                return true;
            else
                return false;
        }

        @Override
        public int hashCode()
        {
            int result = 17;
            result = 31 * result + this.name.hashCode();
            return result;
        }
    }

    class Edge
    {
        Node parent;
        Node child;
        int edgeWeight = 0;

        Edge()
        {
            this(null, null, 0);
        }

        Edge(Node parent, Node child, int edgeWeight)
        {
            this.parent = parent;
            this.child = child;
            this.edgeWeight = edgeWeight;
        }
    }
}
