package com.ratteler50.facebook;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.dampcake.gson.immutable.ImmutableAdapterFactory;


public class Main {

  private static final String WITH_PHOTOS = "/Users/dlorant/Downloads/david.lorant.json";
  private static final String WITHOUT_PHOTOS = "/Users/dlorant/Downloads/david.lorant (1).json";

  public static void main(String[] args) throws IOException {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(FacebookTypeAdapter.create())
        .registerTypeAdapterFactory(ImmutableAdapterFactory.forGuava()).create();
    String json = Files.readAllLines(Paths.get(WITHOUT_PHOTOS)).get(0);
    System.out.println(gson.fromJson(json, FacebookGraph.class));
  }
}
