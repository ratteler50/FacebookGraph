package com.ratteler50.facebook;

import java.io.IOException;
import java.io.PrintWriter;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Main {

  public static void main(String[] args) throws IOException {
    UndirectedGraph<Node, DefaultEdge> graph = FacebookGraph.readWithoutPhotos();
    printGraphData(graph, "/Users/dlorant/dlorant_data.txt");
  }

  private static void printGraphData(UndirectedGraph<Node, DefaultEdge> graph, String path)
      throws IOException {
    System.out.println("Reading Graph");
    System.out.println("Done Reading Graph");
    PrintWriter writer = new PrintWriter(path);
    Algorithms algorithms = new Algorithms(graph, writer);

    System.out.println("Writing Degree");
    algorithms.degree();
    System.out.println("Done Writing Degree");
    System.out.println("Writing Page Rank");
    algorithms.pageRank();
    System.out.println("Done Writing Page Rank");
    System.out.println("Writing Connectivity");
    algorithms.connectivity();
    System.out.println("Done Writing Connectivity");
    System.out.println("Writing Clique");
    algorithms.clique();
    System.out.println("Done Writing Clique");
    System.out.println("Writing Shortest Path Lengths");
    algorithms.shortestPathLengths();
    System.out.println("Done Writing Shortest Path Lengths");
    writer.flush();
    writer.close();
  }
}
