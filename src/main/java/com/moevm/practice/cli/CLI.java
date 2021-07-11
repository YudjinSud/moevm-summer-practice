package com.moevm.practice.cli;

import com.moevm.practice.core.graph.Graph;

import java.io.*;
import java.util.*;
import java.util.Scanner;

public class CLI {
    public static Scanner in = new Scanner(System.in);

    private Graph graphInput() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int numberOfEdges = 0;
        System.out.println("\nYou have selected input via the console.\n Enter type of vertices you want to use ::\n (0) Character\n(1) Integer\n");
        int type = -1;
        while (type == -1) {
            try {
                String s = br.readLine();
                type = Integer.parseInt(s);
                if (type > 1 || s.length() > 1) {
                    throw new EmptyStackException();
                }
            } catch (Exception e) {
                System.out.println("\nSomething went wrong...\nPlease enter type of vertices you want to use ::\n (0) Character\n(1) Integer\n");
            }
        }
        System.out.println("\nNow enter number of edges you want to add \n");
        while (numberOfEdges == 0) {
            try {
                String s = br.readLine();
                numberOfEdges = Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("Something went wrong...\nPlease enter number of edges again\n");
            }
        }
        System.out.println("\nEnter edges int format <from,to> ::\n");
        if (type == 0) {
            Graph<Character> graph = new Graph<>();
            while (numberOfEdges > 0) {
                numberOfEdges--;
                try {
                    String[] splited = br.readLine().split(",");
                    if (splited.length != 2 || splited[0].length() != 1 || splited[1].length() != 1)
                        throw new EmptyStackException();
                    Character from = splited[0].charAt(0);
                    Character to = splited[1].charAt(0);
                    graph.addEdge(from, to);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return graph;
    } else

    {
        Graph<Integer> graph = new Graph<>();
        while (numberOfEdges > 0) {
            numberOfEdges--;
            try {
                String[] splited = br.readLine().split(",");
                if (splited.length != 2) throw new EmptyStackException();
                Integer from = Integer.parseInt(splited[0]);
                Integer to = Integer.parseInt(splited[1]);
                graph.addEdge(from, to);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return graph;
    }

}

    public void mainCLI() {
        Graph graph = graphInput();
        graph.mainAlgo();
    }


}
