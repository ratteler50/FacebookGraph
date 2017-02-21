package com.ratteler50.facebook;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
abstract class FacebookTypeAdapter implements TypeAdapterFactory {

  // Static factory method to access the package
  // private generated implementation
  static FacebookTypeAdapter create() {
    return new AutoValueGson_FacebookTypeAdapter();
  }
}
