
package com.ratteler50.facebook;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ryanharter.auto.value.gson.GsonTypeAdapter;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Nullable;

@AutoValue
public abstract class Node {

  @SerializedName("id")
  abstract String getId();

  @SerializedName("name")
  abstract String getName();

  @SerializedName("profile")
  abstract String getProfile();

  @SerializedName("userName")
  abstract String getUserName();

  @SerializedName("dataUrl")
  @Nullable
  abstract String getDataUrl();

  @GsonTypeAdapter(Node.class)
  public static TypeAdapter<Node> typeAdapter(Gson gson) {
    return new AutoValue_Node.GsonTypeAdapter(gson);
  }
}
