
package com.ratteler50.facebook;

import com.dampcake.gson.immutable.ImmutableAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GsonTypeAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

@AutoValue
abstract class FacebookGraph {

  private static final String WITH_PHOTOS = "/Users/dlorant/Downloads/david.lorant.json";
  private static final String WITHOUT_PHOTOS = "/Users/dlorant/Downloads/david.lorant (1).json";

  static UndirectedGraph<Node, DefaultEdge> readWithoutPhotos() throws IOException {
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapterFactory(FacebookTypeAdapter.create())
            .registerTypeAdapterFactory(ImmutableAdapterFactory.forGuava())
            .create();
    return getGraph(
        gson.fromJson(Files.readAllLines(Paths.get(WITHOUT_PHOTOS)).get(0), FacebookGraph.class));
  }

  static UndirectedGraph<Node, DefaultEdge> readWithPhotos() throws IOException {
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapterFactory(FacebookTypeAdapter.create())
            .registerTypeAdapterFactory(ImmutableAdapterFactory.forGuava())
            .create();
    return getGraph(
        gson.fromJson(Files.readAllLines(Paths.get(WITH_PHOTOS)).get(0), FacebookGraph.class));
  }

  private static UndirectedGraph<Node, DefaultEdge> getGraph(FacebookGraph jsonGraph) {
    UndirectedGraph<Node, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    ImmutableList<Node> nodes = jsonGraph.getNodes();
    Graphs.addAllVertices(graph, nodes);
    for (Link edge : jsonGraph.getLinks()) {
      Node source = nodes.get(edge.getSource());
      Node target = nodes.get(edge.getTarget());
      graph.addEdge(source, target);
    }
    return graph;
  }

  @SerializedName("userId")
  abstract String getUserId();

  @SerializedName("userName")
  abstract String getUserName();

  @SerializedName("nodes")
  abstract ImmutableList<Node> getNodes();

  @SerializedName("links")
  abstract ImmutableList<Link> getLinks();

  @SuppressWarnings("WeakerAccess")
  @GsonTypeAdapter(FacebookGraph.class)
  public static TypeAdapter<FacebookGraph> typeAdapter(Gson gson) {
    return new AutoValue_FacebookGraph.GsonTypeAdapter(gson);
  }
}
