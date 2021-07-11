package com.moevm.practice.core;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//https://www.vogella.com/tutorials/JUnit/article.html

public class GraphTest {
    Graph graph;

    private void addIntegerEdge(Integer v1, Integer v2){
        graph.addEdge(v1, v2);
        assertTrue(graph.hasEdge(v1, v2));
    }

    private void addCharEdge(Character v1, Character v2){
        graph.addEdge(v1, v2);
        assertTrue(graph.hasEdge(v1, v2));
    }

    @Test
    public void testAddEdge() {
        graph = new Graph<Integer>();
        Integer v1 = 0;
        Integer v2 = 3;
        graph.addEdge(v1, v2);
        assertTrue(graph.hasVertex(v1));
        assertTrue(graph.hasVertex(v2));
        assertEquals(graph.numberOfEdges,1);
    }

    @Test
    public void testAddEdgeChar() {
        graph = new Graph<Integer>();
        Character v1 = 'b', v2 = 'c';
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v1);
        assertTrue(graph.hasVertex(v1));
        assertTrue(graph.hasVertex(v2));
        assertEquals(graph.numberOfEdges,2);
        graph.mainAlgo();
    }

    @Test
    public void testAlgorithm() {
        graph = new Graph<Integer>();
        addIntegerEdge(0,1);
        addIntegerEdge(1,2);
        addIntegerEdge(2,0);
        addIntegerEdge(5,9);
        System.out.print(graph.toString());
        graph.mainAlgo();
    }

    @Test
    public void testSashaChervyak() {
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
    public void algoTest() {

    }

}
