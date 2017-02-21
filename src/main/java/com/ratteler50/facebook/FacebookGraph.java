
package com.ratteler50.facebook;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GsonTypeAdapter;
import java.util.List;

@AutoValue
public abstract class FacebookGraph {


  @SerializedName("userId")
  abstract String getUserId();

  @SerializedName("userName")
  abstract String getUserName();

  @SerializedName("nodes")
  abstract ImmutableList<Node> getNodes();

  @SerializedName("links")
  abstract ImmutableList<Link> getLinks();

  @GsonTypeAdapter(FacebookGraph.class)
  public static TypeAdapter<FacebookGraph> typeAdapter(Gson gson) {
    return new AutoValue_FacebookGraph.GsonTypeAdapter(gson);
  }
}
