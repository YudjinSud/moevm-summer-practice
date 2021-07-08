package com.moevm.practice.core;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//https://www.vogella.com/tutorials/JUnit/article.html

public class GraphTest {
    private Graph graph;

    @Test
    public void test() {
        this.graph = new Graph();
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        v1.toInt = 0;
        v2.toInt = 1;
        this.graph.addEdge(v1, v2);
        assertEquals(graph.numberOfEdges,1);
        //assertEquals(1, 1);

    }

}
