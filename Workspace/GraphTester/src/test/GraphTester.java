import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 5/20/2017
 * Assignment:
 */
public class GraphTester
{

    @Test
    public void testAddVertex()
    {
        Graph graph = new Graph();

        graph.addVertex("Alex", 5);
        graph.addVertex("Fenya", 2);
        graph.addVertex("Ioan", 0);

       assertTrue(graph.hasVertex("Alex") &&
               graph.hasVertex("Fenya") && graph.hasVertex("Ioan"));
    }

    @Test
    public void testEdges()
    {
        Graph graph = new Graph();

        Graph.Node alex = graph.createVertex("Alex");
        Graph.Node fenya = graph.createVertex("Fenya");
        Graph.Node ioan = graph.createVertex("Ioan");

        graph.addVertex(alex);
        graph.addVertex(fenya);
        graph.addVertex(ioan);

        graph.addEdge(alex, fenya, 4);
        graph.addEdge(fenya, ioan, 6);

        fenya.weight = fenya.inDegree.get(0).edgeWeight + alex.weight;
        ioan.weight = ioan.inDegree.get(0).edgeWeight + fenya.weight;

        assertTrue(fenya.weight == 4 && ioan.weight == 10);
    }
}
