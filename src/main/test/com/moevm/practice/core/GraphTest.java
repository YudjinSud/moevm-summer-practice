package com.moevm.practice.core;

import com.moevm.practice.core.graph.Graph;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

//https://www.vogella.com/tutorials/JUnit/article.html

public class GraphTest {
    Graph graph;

    private void addIntegerEdge(Integer v1, Integer v2) {
        graph.addEdge(v1, v2);
        assertTrue(graph.hasEdge(v1, v2));
    }

    private void addCharEdge(Character v1, Character v2) {
        graph.addEdge(v1, v2);
        assertTrue(graph.hasEdge(v1, v2));
    }

    @Test
    public void testAddEdge() {
        graph = new Graph<Integer>();
        addIntegerEdge(1, 3);
        assertTrue(graph.hasVertex(1));
        assertTrue(graph.hasVertex(3));
        assertFalse(graph.hasVertex(2));
        assertEquals(graph.numberOfEdges, 1);
    }

    @Test
    public void testAddEdgeChar() {
        graph = new Graph<Integer>();
        Character v1 = 'b', v2 = 'c';
        addCharEdge(v1, v2);
        addCharEdge(v2, v1);
        assertTrue(graph.hasVertex(v1));
        assertTrue(graph.hasVertex(v2));
        assertEquals(graph.numberOfEdges, 2);
        graph.mainAlgo();
    }

    @Test
    public void testAlgorithm() {
        graph = new Graph<Integer>();
        addIntegerEdge(0, 1);
        addIntegerEdge(1, 2);
        addIntegerEdge(2, 0);
        addIntegerEdge(5, 9);
        System.out.print(graph.toString());
        graph.mainAlgo();
    }

    @Test
    public void testCircle() {
        graph = new Graph<Integer>();
        addIntegerEdge(0, 0);
        System.out.print(graph.toString());
        graph.mainAlgo();
    }

    @Test
    public void testBigGraph() {
        graph = new Graph<Integer>();
        addIntegerEdge(2, 1);
        addIntegerEdge(4, 1);
        addIntegerEdge(3, 4);
        addIntegerEdge(4, 3);
        addIntegerEdge(6, 3);
        addIntegerEdge(6, 8);
        addIntegerEdge(8, 7);
        addIntegerEdge(7, 5);
        addIntegerEdge(5, 2);
        addIntegerEdge(2, 5);
        addIntegerEdge(5, 4);
        addIntegerEdge(7, 3);
        addIntegerEdge(7, 6);
        System.out.print(graph.toString());
        graph.mainAlgo();
    }

    @Test
    public void testEmpty() {
        graph = new Graph<Integer>();
        System.out.print(graph.toString());
        graph.mainAlgo();
    }

}