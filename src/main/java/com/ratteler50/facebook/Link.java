
package com.ratteler50.facebook;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GsonTypeAdapter;

@AutoValue
abstract class Link {

  @SerializedName("source")
  abstract int getSource();

  @SerializedName("target")
  abstract int getTarget();

  @SuppressWarnings("WeakerAccess")
  @GsonTypeAdapter(Link.class)
  public static TypeAdapter<Link> typeAdapter(Gson gson) {
    return new AutoValue_Link.GsonTypeAdapter(gson);
  }
}
