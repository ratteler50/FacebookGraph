package com.ratteler50.facebook;

import java.io.IOException;
import java.io.PrintWriter;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Main {

  public static void main(String[] args) throws IOException {
    System.out.println("Reading Graph");
    UndirectedGraph<Node, DefaultEdge> graph = FacebookGraph.readWithoutPhotos();
    System.out.println("Done Reading Graph");
    PrintWriter writer = new PrintWriter("/Users/dlorant/david_data.txt");
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
    writer.flush();
    writer.close();
  }
}
