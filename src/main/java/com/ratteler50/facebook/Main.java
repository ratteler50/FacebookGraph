package com.ratteler50.facebook;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
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

public class Main {

  private static final Comparator<String> COMPARING_LAST_NAME =
      comparing(s -> s.substring(s.lastIndexOf(" ") + 1));
  private static final Function<Set<Node>, ImmutableList<String>> GET_SORTED_NAMES_FROM_NODES =
      n -> n.stream().map(Node::getName).sorted(COMPARING_LAST_NAME).collect(toImmutableList());

  public static void main(String[] args) throws IOException {
    UndirectedGraph<Node, DefaultEdge> graph = FacebookGraph.readWithoutPhotos();
    clique(graph);
  }

  private static void removeDisconnectedNodes(UndirectedGraph<Node, DefaultEdge> graph) {
    new ConnectivityInspector<>(graph)
        .connectedSets()
        .stream()
        .filter(set -> set.size() < 10)
        .forEach(set -> set.forEach(graph::removeVertex));
  }

  private static void clique(UndirectedGraph<Node, DefaultEdge> graph) {
    BronKerboschCliqueFinder<Node, DefaultEdge> cliqueFinder =
        new BronKerboschCliqueFinder<>(graph);
    cliqueFinder
        .getAllMaximalCliques()
        .stream()
        .map(GET_SORTED_NAMES_FROM_NODES)
        .sorted(comparingInt(List::size))
        .peek(set -> System.out.println(set.size()))
        .forEach(System.out::println);
  }

  private static void clique(UndirectedGraph<Node, DefaultEdge> graph, String name) {
    BronKerboschCliqueFinder<Node, DefaultEdge> cliqueFinder =
        new BronKerboschCliqueFinder<>(graph);
    cliqueFinder
        .getAllMaximalCliques()
        .stream()
        .map(GET_SORTED_NAMES_FROM_NODES)
        .filter(names -> names.stream().anyMatch(s -> s.toLowerCase().contains(name.toLowerCase())))
        .sorted(comparingInt(List::size))
        .peek(set -> System.out.println(set.size()))
        .forEach(System.out::println);
  }

  private static void connectivity(UndirectedGraph<Node, DefaultEdge> graph) {
    ConnectivityInspector<Node, DefaultEdge> ci = new ConnectivityInspector<>(graph);
    ci.connectedSets()
        .stream()
        .map(GET_SORTED_NAMES_FROM_NODES)
        .sorted(comparingInt(List::size))
        .peek(set -> System.out.println(set.size()))
        .forEach(System.out::println);
  }

  private static void pageRank(UndirectedGraph<Node, DefaultEdge> graph) {
    Comparator<Entry<Node, Double>> comparing = comparingDouble(Entry::getValue);
    ImmutableMap<String, Double> ranks =
        new PageRank<>(graph)
            .getScores()
            .entrySet()
            .stream()
            .sorted(comparing.reversed())
            .collect(toImmutableMap(entry -> entry.getKey().getName(), Entry::getValue));
    System.out.println(ranks);
  }
}
