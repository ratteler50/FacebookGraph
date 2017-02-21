package com.ratteler50.facebook;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class FacebookTypeAdapter implements TypeAdapterFactory {

  // Static factory method to access the package
  // private generated implementation
  public static FacebookTypeAdapter create() {
    return new AutoValueGson_FacebookTypeAdapter();
  }

}