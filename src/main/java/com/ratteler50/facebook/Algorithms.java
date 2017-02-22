package com.ratteler50.facebook;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableListMultimap.toImmutableListMultimap;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static org.jgrapht.alg.scoring.PageRank.DAMPING_FACTOR_DEFAULT;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultEdge;

class Algorithms {
  private static final Comparator<String> COMPARING_LAST_NAME =
      comparing(s -> s.substring(s.lastIndexOf(" ") + 1));
  private static final Function<Set<Node>, ImmutableList<String>> GET_SORTED_NAMES_FROM_NODES =
      n -> n.stream().map(Node::getName).sorted(COMPARING_LAST_NAME).collect(toImmutableList());
  private final PrintWriter writer;
  private final UndirectedGraph<Node, DefaultEdge> graph;

  Algorithms(UndirectedGraph<Node, DefaultEdge> graph, PrintWriter writer) {
    this.graph = graph;
    this.writer = writer;
  }

  private static String getNameFromEntry(Entry<Node, ?> entry) {
    return entry.getKey().getName();
  }

  void pageRank() {
    writer.println("---------------<PAGE_RANK>---------------");
    writePageRank();
    writer.println("---------------</PAGE_RANK>---------------");
    writer.flush();
  }

  private void writePageRank() {
    Comparator<Entry<String, Double>> comparingRank = comparingDouble(Entry::getValue);
    Comparator<Entry<String, Double>> compareRankThenLastName =
        comparingRank.reversed().thenComparing(Entry::getKey, COMPARING_LAST_NAME);
    ImmutableListMultimap<String, Double> ranks =
        new PageRank<>(graph, DAMPING_FACTOR_DEFAULT, Integer.MAX_VALUE, 1.0E-10)
            .getScores()
            .entrySet()
            .stream()
            .collect(toImmutableListMultimap(Algorithms::getNameFromEntry, Entry::getValue));
    ranks.entries().stream().sorted(compareRankThenLastName).forEach(writer::println);
  }

  void degree() {
    writer.println("---------------<DEGREE>---------------");
    writeDegree();
    writer.println("---------------</DEGREE>---------------");
    writer.flush();
  }

  private void writeDegree() {
    Comparator<Entry<String, Integer>> comparingDegree = comparingInt(Entry::getValue);
    Comparator<Entry<String, Integer>> compareDegreeThenLastName =
        comparingDegree.reversed().thenComparing(Entry::getKey, COMPARING_LAST_NAME);
    ImmutableListMultimap<String, Integer> degreeMap =
        graph.vertexSet().stream().collect(toImmutableListMultimap(Node::getName, graph::degreeOf));
    degreeMap.entries().stream().sorted(compareDegreeThenLastName).forEach(writer::println);
  }

  void clique() {
    writer.println("---------------<CLIQUES>---------------");
    writeClique();
    writer.println("---------------</CLIQUES>---------------");
    writer.flush();
  }

  private void writeClique() {
    new StringBuilder();
    BronKerboschCliqueFinder<Node, DefaultEdge> cliqueFinder =
        new BronKerboschCliqueFinder<>(graph);
    cliqueFinder
        .getAllMaximalCliques()
        .stream()
        .map(GET_SORTED_NAMES_FROM_NODES)
        .sorted(comparingInt(List<String>::size).reversed())
        .peek(set -> writer.println(set.size()))
        .forEach(writer::println);
  }

  void clique(String name) {
    BronKerboschCliqueFinder<Node, DefaultEdge> cliqueFinder =
        new BronKerboschCliqueFinder<>(graph);
    cliqueFinder
        .getAllMaximalCliques()
        .stream()
        .map(GET_SORTED_NAMES_FROM_NODES)
        .filter(names -> names.stream().anyMatch(s -> s.toLowerCase().contains(name.toLowerCase())))
        .sorted(comparingInt(List<String>::size).reversed())
        .peek(set -> writer.println(set.size()))
        .forEach(writer::println);
    writer.flush();
  }

  void connectivity() {
    writer.println("---------------<CONNECTED_SUBGRAPHS>---------------");
    writeConnectivity();
    writer.println("---------------</CONNECTED_SUBGRAPHS>---------------");
    writer.flush();
  }

  private void writeConnectivity() {
    ConnectivityInspector<Node, DefaultEdge> ci = new ConnectivityInspector<>(graph);
    ci.connectedSets()
        .stream()
        .map(GET_SORTED_NAMES_FROM_NODES)
        .sorted(comparingInt(List<String>::size).reversed())
        .peek(set -> writer.println(set.size()))
        .forEach(writer::println);
  }

  void removeDisconnectedNodes(int size) {
    new ConnectivityInspector<>(graph)
        .connectedSets()
        .stream()
        .filter(set -> set.size() < size)
        .forEach(set -> set.forEach(graph::removeVertex));
  }
}
